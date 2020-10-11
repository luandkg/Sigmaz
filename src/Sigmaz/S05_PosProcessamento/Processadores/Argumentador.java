package Sigmaz.S05_PosProcessamento.Processadores;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S05_PosProcessamento.PosProcessador;
import Sigmaz.S07_Executor.Debuggers.Simplificador;

import java.util.ArrayList;

public class Argumentador {

    private PosProcessador mPosProcessador;
    private Simplificador mSimplificador;

    public Argumentador(PosProcessador ePosProcessador) {

        mPosProcessador = ePosProcessador;
        mSimplificador = new Simplificador();

    }

    public void init(ArrayList<AST> mTodos) {

        mPosProcessador.mensagem("");
        mPosProcessador.mensagem(" ------------------ FASE ARGUMENTADOR ----------------------- ");


        for (AST mAST : mTodos) {

            if (mAST.mesmoTipo("SIGMAZ")) {


                mPosProcessador.mensagem("");

                for (AST ePacote : mAST.getASTS()) {
                    if (ePacote.mesmoTipo("PACKAGE")) {

                        processar(ePacote);

                    }
                }

                processar(mAST);


            }
        }

    }


    public void processar(AST eASTPai) {

        ArrayList<AST> mInserirActions = new ArrayList<AST>();

        for (AST mAST : eASTPai.getASTS()) {

            if (mAST.mesmoTipo("ACTION")) {

                mPosProcessador.mensagem("Action " + mSimplificador.getAction(mAST));
                detalhes(mAST.getBranch("ARGUMENTS"));

                if (opcionalCorreto(mAST.getBranch("ARGUMENTS"))) {

                } else {
                    mPosProcessador.errar("Action " + mSimplificador.getAction(mAST) + " : Apos um Argumento opcional todos os restantes devem ser opcionais !");
                }

            } else if (mAST.mesmoTipo("FUNCTION")) {


                mPosProcessador.mensagem("Function " + mSimplificador.getFuction(mAST));
                detalhes(mAST.getBranch("ARGUMENTS"));

                if (opcionalCorreto(mAST.getBranch("ARGUMENTS"))) {

                } else {
                    mPosProcessador.errar("Function " + mSimplificador.getFuction(mAST) + " : Apos um Argumento opcional todos os restantes devem ser opcionais !");
                }

            } else if (mAST.mesmoTipo("PROTOTYPE_AUTO")) {

                mPosProcessador.mensagem("Auto " + mSimplificador.getAuto(mAST));
                detalhes(mAST.getBranch("ARGUMENTS"));

                if (opcionalCorreto(mAST.getBranch("ARGUMENTS"))) {


                } else {
                    mPosProcessador.errar("Auto " + mSimplificador.getAuto(mAST) + " : Apos um Argumento opcional todos os restantes devem ser opcionais !");
                }

            } else if (mAST.mesmoTipo("PROTOTYPE_FUNCTOR")) {

                mPosProcessador.mensagem("Functor " + mSimplificador.getFunctor(mAST));
                detalhes(mAST.getBranch("ARGUMENTS"));

                if (opcionalCorreto(mAST.getBranch("ARGUMENTS"))) {

                } else {
                    mPosProcessador.errar("Functor " + mSimplificador.getFunctor(mAST) + " : Apos um Argumento opcional todos os restantes devem ser opcionais !");
                }

            } else if (mAST.mesmoTipo("OPERATOR")) {

                mPosProcessador.mensagem("Operator " + mSimplificador.getOperator(mAST));
                detalhes(mAST.getBranch("ARGUMENTS"));

                if (semOpcionais(mAST.getBranch("ARGUMENTS"))) {

                } else {
                    mPosProcessador.errar("Operator " + mSimplificador.getOperator(mAST) + " : Nao pode possuir um argumento opcional !");
                }

                if (getContagemSemOpcional(mAST.getBranch("ARGUMENTS")) == 2){

                } else {
                    mPosProcessador.errar("Operator " + mSimplificador.getOperator(mAST) + " : Deve possuir 2 argumentos !");
                }

            } else if (mAST.mesmoTipo("DIRECTOR")) {

                mPosProcessador.mensagem("Director " + mSimplificador.getDirector(mAST));
                detalhes(mAST.getBranch("ARGUMENTS"));

                if (semOpcionais(mAST.getBranch("ARGUMENTS"))) {

                } else {
                    mPosProcessador.errar("Director " + mSimplificador.getDirector(mAST) + " : Nao pode possuir um argumento opcional !");
                }

                if (getContagemSemOpcional(mAST.getBranch("ARGUMENTS")) == 1){

                } else {
                    mPosProcessador.errar("Director " + mSimplificador.getDirector(mAST) + " : Deve possuir 1 argumento !");
                }

            } else if (mAST.mesmoTipo("MARK")) {

                mPosProcessador.mensagem("Mark " + mSimplificador.getMark(mAST));
                detalhes(mAST.getBranch("ARGUMENTS"));

                if (semOpcionais(mAST.getBranch("ARGUMENTS"))) {

                } else {
                    mPosProcessador.errar("Mark " + mSimplificador.getMark(mAST) + " : Nao pode possuir um argumento opcional !");
                }

                if (getContagemSemOpcional(mAST.getBranch("ARGUMENTS")) == 1){


                } else {
                    mPosProcessador.errar("Mark " + mSimplificador.getMark(mAST) + " : Deve possuir 1 argumento !");
                }
            }


        }

        for (AST mAST : mInserirActions) {
            eASTPai.getASTS().add(mAST);
        }


    }


    public void detalhes(AST eAST) {

        mPosProcessador.mensagem("\t Argumentacao :  " + getArgumentacao(eAST));
        mPosProcessador.mensagem("");
        mPosProcessador.mensagem("\t Valores :  " + contagemValor(eAST));
        mPosProcessador.mensagem("\t Constantes :  " + contagemConstante(eAST));
        mPosProcessador.mensagem("\t Referenciaveis :  " + contagemRef(eAST));

        mPosProcessador.mensagem("\t Opcionais :  " + contagemOpcional(eAST));
        mPosProcessador.mensagem("\t Opcionais Constantes :  " + contagemOpcionalConstante(eAST));
        mPosProcessador.mensagem("\t Opcionais Referenciaveis :  " + contagemOpcionalRef(eAST));


    }

    public boolean semOpcionais(AST eArgumentos) {

        boolean ret = true;

        for (AST mAST : eArgumentos.getASTS()) {

            if (mAST.mesmoValor("VALUE")) {
            } else if (mAST.mesmoValor("MOC")) {
            } else if (mAST.mesmoValor("REF")) {
            } else if (mAST.mesmoValor("OPT")) {
                ret = false;
            } else if (mAST.mesmoValor("OPTMOC")) {
                ret = false;
            } else if (mAST.mesmoValor("OPTREF")) {
                ret = false;
            }

        }

        return ret;

    }

    public String getArgumentacao(AST eArgumentos) {

       String ret = "";

        for (AST mAST : eArgumentos.getASTS()) {

            if (mAST.mesmoValor("VALUE")) {
                ret +=" V";
            } else if (mAST.mesmoValor("MOC")) {
                ret +=" M";
            } else if (mAST.mesmoValor("REF")) {
                ret +=" R";
            } else if (mAST.mesmoValor("OPT")) {
                ret +=" OV";
            } else if (mAST.mesmoValor("OPTMOC")) {
                ret +=" OM";
            } else if (mAST.mesmoValor("OPTREF")) {
                ret +=" OR";
            }

        }

        return ret;

    }

    public int getContagemSemOpcional(AST eArgumentos) {

        int ret = 0;

        for (AST mAST : eArgumentos.getASTS()) {

            if (mAST.mesmoValor("VALUE")) {
                ret+=1;
            } else if (mAST.mesmoValor("MOC")) {
                ret+=1;
            } else if (mAST.mesmoValor("REF")) {
                ret+=1;
            } else if (mAST.mesmoValor("OPT")) {

            } else if (mAST.mesmoValor("OPTMOC")) {

            } else if (mAST.mesmoValor("OPTREF")) {

            }

        }

        return ret;

    }

    public boolean opcionalCorreto(AST eArgumentos) {

        boolean ret = true;
        boolean opt = false;

        for (AST mAST : eArgumentos.getASTS()) {

            if (opt == false) {
                if (mAST.mesmoValor("VALUE")) {

                } else if (mAST.mesmoValor("MOC")) {
                } else if (mAST.mesmoValor("REF")) {
                } else if (mAST.mesmoValor("OPT")) {
                    opt = true;
                } else if (mAST.mesmoValor("OPTMOC")) {
                    opt = true;
                } else if (mAST.mesmoValor("OPTREF")) {
                    opt = true;
                }
            } else {

                if (mAST.mesmoValor("VALUE")) {
                    ret = false;
                } else if (mAST.mesmoValor("MOC")) {
                    ret = false;
                } else if (mAST.mesmoValor("REF")) {
                    ret = false;
                } else if (mAST.mesmoValor("OPT")) {
                } else if (mAST.mesmoValor("OPTMOC")) {
                } else if (mAST.mesmoValor("OPTREF")) {
                }

            }


        }

        return ret;
    }


    public int contagemValor(AST eArgumentos) {
        int o = 0;

        for (AST mAST : eArgumentos.getASTS()) {

            if (mAST.mesmoValor("VALUE")) {
                o += 1;
            }

        }

        return o;
    }

    public int contagemRef(AST eArgumentos) {
        int o = 0;

        for (AST mAST : eArgumentos.getASTS()) {

            if (mAST.mesmoValor("REF")) {
                o += 1;
            }

        }

        return o;
    }

    public int contagemConstante(AST eArgumentos) {
        int o = 0;

        for (AST mAST : eArgumentos.getASTS()) {

            if (mAST.mesmoValor("MOC")) {
                o += 1;
            }

        }

        return o;
    }

    public int contagemOpcional(AST eArgumentos) {
        int o = 0;

        for (AST mAST : eArgumentos.getASTS()) {

            if (mAST.mesmoValor("OPT")) {
                o += 1;
            }

        }

        return o;
    }

    public int contagemOpcionalConstante(AST eArgumentos) {
        int o = 0;

        for (AST mAST : eArgumentos.getASTS()) {

            if (mAST.mesmoValor("OPTMOC")) {
                o += 1;
            }

        }

        return o;
    }

    public int contagemOpcionalRef(AST eArgumentos) {
        int o = 0;

        for (AST mAST : eArgumentos.getASTS()) {

            if (mAST.mesmoValor("OPTREF")) {
                o += 1;
            }

        }

        return o;
    }
}
