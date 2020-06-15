package Sigmaz.Executor;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_Action {

    private RunTime mRunTime;
    private Escopo mEscopo;


    public Run_Action(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }


    public void init(AST ASTCorrente) {


        int argumentos = 0;
        ArrayList<AST> mArgumentos = new ArrayList<AST>();

        for (AST a : ASTCorrente.getASTS()) {
            if (a.mesmoTipo("ARGUMENT")) {
                argumentos += 1;
                if (a.mesmoValor("Num")) {

                    AST v = a.copiar();
                    v.setValor("num");

                    mArgumentos.add(v);
                } else if (a.mesmoValor("Text")) {
                    AST v = a.copiar();
                    v.setValor("string");

                    mArgumentos.add(v);
                } else if (a.mesmoValor("FUNCT")) {

                    AST v = new AST("VALUE");

                    Run_Func mAST = new Run_Func(mRunTime, mEscopo);
                    mAST.init(a, "any");

                    if (mAST.getIsNulo()) {
                        v.setNome("NULL");
                    } else if (mAST.getIsPrimitivo()) {
                        v.setNome(mAST.getPrimitivo());
                    } else {
                        mRunTime.getErros().add("AST_Value --> STRUCTURED VALUE !");
                    }

                    v.setValor(mAST.getRetornoFunction());


                    mArgumentos.add(v);

                    //   mRunTime.getErros().add(" Argumento Complexo !");

                }
            }

        }

        //  System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //System.out.println("\t - Argumentos :  " + argumentos);

        boolean enc = false;
        boolean algum = false;
        AST mFunction = null;

        ArrayList<String> mArgumentandoNome = new ArrayList<String>();
        ArrayList<String> mArgumentandoTipos = new ArrayList<String>();

        for (AST a : mEscopo.getGuardadosCompleto()) {

            if (a.mesmoTipo("ACTION") && a.mesmoNome(ASTCorrente.getNome())) {
                enc = true;

                int argumentando = 0;
                mArgumentandoNome.clear();
                mArgumentandoTipos.clear();

                for (AST b : a.getBranch("ARGUMENTS").getASTS()) {
                    if (b.mesmoTipo("ARGUMENT")) {
                        argumentando += 1;

                        mArgumentandoNome.add(b.getNome());
                        mArgumentandoTipos.add(b.getValor());

                        //       System.out.println("\t - Complexo :  " + b.getValor());

                    }

                }


                if (mArgumentos.size() == mArgumentandoTipos.size()) {

                    if (argumentos > 0) {
                        int v = 0;

                        for (int argC = 0; argC < argumentos; argC++) {

                            if (mArgumentos.get(argC).mesmoValor(mArgumentandoTipos.get(argC))) {

                                v += 1;
                            }
                            //   System.out.println("\t - Checando Tipo :  " + mArgumentos.get(argC).getValor() + " e " + mArgumentandoTipos.get(argC));

                        }

                        if (v == argumentos) {
                            algum = true;
                            mFunction = a;
                            break;
                        }
                    } else {

                        algum = true;
                        mFunction = a;
                        break;
                    }


                }

            }

        }

        if (enc) {

            if (algum) {

                //  System.out.println("Ret :: " + eReturne);

                execute(mFunction, argumentos, mArgumentandoNome, mArgumentos);

            } else {
                mRunTime.getErros().add("Action " + ASTCorrente.getNome() + " : Argumentos incompativeis !");
            }

        } else {

            mRunTime.getErros().add("Action " + ASTCorrente.getNome() + " : Nao Encontrada !");
        }


    }

    public void execute(AST mFunction, int argumentos, ArrayList<String> mArgumentandoNome, ArrayList<AST> mArgumentos) {


        //  System.out.println("Function " + mFunction.getNome() + " : " + mFunction.getValor());


        Escopo mEscopoInterno = new Escopo(mRunTime, mRunTime.getEscopoGlobal());

        mEscopoInterno.setNome(mFunction.getNome());


        if (argumentos > 0) {

            for (int argC = 0; argC < argumentos; argC++) {

                /// System.out.println("\t - Alocando : " + mArgumentandoNome.get(argC) + " -> " + mArgumentos.get(argC).getNome() + " :: " + mArgumentos.get(argC).getValor());

                mEscopoInterno.criarDefinicao(mArgumentandoNome.get(argC), mArgumentos.get(argC).getValor(), mArgumentos.get(argC).getNome());
            }

        }

        AST mASTBody = mFunction.getBranch("BODY");

        Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
        mAST.initAction(mASTBody);


    }

}
