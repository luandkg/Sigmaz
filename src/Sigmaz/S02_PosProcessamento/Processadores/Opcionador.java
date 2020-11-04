package Sigmaz.S02_PosProcessamento.Processadores;

import java.util.ArrayList;

import Sigmaz.S02_PosProcessamento.PosProcessador;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S05_Executor.Debuggers.Simplificador;

public class Opcionador {


    private PosProcessador mPosProcessador;

    public Opcionador(PosProcessador ePosProcessador) {

        mPosProcessador = ePosProcessador;

    }

    public void mensagem(String e) {
        if (mPosProcessador.getDebug_Opcionador()) {
            mPosProcessador.mensagem(e);
        }
    }

    public void errar(String e) {
        mPosProcessador.errar(e);
    }


    public void init(ArrayList<AST> mTodos) {

        mensagem("");
        mensagem(" ------------------ FASE OPCIONADOR ----------------------- ");


        for (AST mAST : mTodos) {

            if (mAST.mesmoTipo("SIGMAZ")) {


                mensagem("");

                for (AST ePacote : mAST.getASTS()) {
                    if (ePacote.mesmoTipo("PACKAGE")) {

                        processar(ePacote);

                    }
                }

                processar(mAST);


            }
        }

    }

    public String QualConflito(String eProcurando, String eTipo, ArrayList<AST> lsa, ArrayList<AST> lsb) {

        String ret = "";

        Simplificador mSimplificador = new Simplificador();

        boolean mEnc = false;

        for (AST mAST : lsa) {
            if (mAST.mesmoTipo(eTipo)) {

                if (mSimplificador.getAlgo_SoTipos(mAST).contentEquals(eProcurando)) {
                    ret = mSimplificador.getAction(mAST);
                    mEnc = true;
                    break;
                }

            }
        }
        if (!mEnc) {
            for (AST mAST : lsb) {
                if (mAST.mesmoTipo(eTipo)) {

                    if (mSimplificador.getAlgo_SoTipos(mAST).contentEquals(eProcurando)) {
                        ret = mSimplificador.getAction(mAST);
                        mEnc = true;
                        break;
                    }

                }
            }
        }


        return ret;

    }

    public String QualConflito_Function(String eProcurando, String eTipo, ArrayList<AST> lsa, ArrayList<AST> lsb) {

        String ret = "";

        Simplificador mSimplificador = new Simplificador();

        boolean mEnc = false;

        for (AST mAST : lsa) {
            if (mAST.mesmoTipo(eTipo)) {

                if (mSimplificador.getAlgo_SoTipos(mAST).contentEquals(eProcurando)) {
                    ret = mSimplificador.getFuction(mAST);
                    mEnc = true;
                    break;
                }

            }
        }
        if (!mEnc) {
            for (AST mAST : lsb) {
                if (mAST.mesmoTipo(eTipo)) {

                    if (mSimplificador.getAlgo_SoTipos(mAST).contentEquals(eProcurando)) {
                        ret = mSimplificador.getFuction(mAST);
                        mEnc = true;
                        break;
                    }

                }
            }
        }


        return ret;

    }


    public void emActions(AST mCriando, ArrayList<AST> mTodos, ArrayList<AST> mInserindo, ArrayList<String> mConferindo) {

        String eTipoOpcional = "ACTION";
        String eTipoOpcionalMostrar = "Action";

        Simplificador mSimplificador = new Simplificador();

        mInserindo.add(mCriando);

        String sImplementando = eTipoOpcional + " :: " + mSimplificador.getAlgo_SoTipos(mCriando);
        if (!mConferindo.contains(sImplementando)) {
            mConferindo.add(sImplementando);
        } else {

            String mConflito = QualConflito(mSimplificador.getAlgo_SoTipos(mCriando), eTipoOpcional, mTodos, mInserindo);
            errar(eTipoOpcionalMostrar + " conflitante : " + mSimplificador.getAction(mCriando) + " e " + mConflito);

        }

    }

    public void emGrupo_Action(AST mAST, ArrayList<AST> mTodos, ArrayList<AST> mInserindo, ArrayList<String> mConferindo) {

        Simplificador mSimplificador = new Simplificador();

        mConferindo.add("ACTION :: " + mSimplificador.getAction(mAST));

        if (getOpcionais(mAST.getBranch("ARGUMENTS")) > 0) {

            mensagem("ACTION :: " + mAST.getNome() + " " + mSimplificador.getParametragemFormas(mAST.getBranch("ARGUMENTS")));

            AST mOriginal = mAST.copiar();

            int mOriginal_Argumentos = getOpcionais(mOriginal.getBranch("ARGUMENTS"));
            if (mOriginal_Argumentos > 0) {
                for (AST eArgumento : mOriginal.getBranch("ARGUMENTS").getASTS()) {
                    if (eArgumento.mesmoValor("OPT")) {
                        AST mCriando = reorganizar(mOriginal.copiar(), eArgumento.getNome());
                        emActions(mCriando, mTodos, mInserindo, mConferindo);
                    } else if (eArgumento.mesmoValor("OPTREF")) {
                        AST mCriando = reorganizar(mOriginal.copiar(), eArgumento.getNome());
                        emActions(mCriando, mTodos, mInserindo, mConferindo);
                    } else if (eArgumento.mesmoValor("OPTMOC")) {
                        AST mCriando = reorganizar(mOriginal.copiar(), eArgumento.getNome());
                        emActions(mCriando, mTodos, mInserindo, mConferindo);
                    }
                }
            }


            if (mOriginal_Argumentos > 1) {
                AST mCriando = reorganizarTodos(mOriginal.copiar());
                emActions(mCriando, mTodos, mInserindo, mConferindo);
            }

            removerOpcionais(mAST.getBranch("ARGUMENTS"));


        }

    }


    public void emFunctions(AST mCriando, ArrayList<AST> mTodos, ArrayList<AST> mInserindo, ArrayList<String> mConferindo) {

        String eTipoOpcional = "FUNCTION";
        String eTipoOpcionalMostrar = "Function";

        Simplificador mSimplificador = new Simplificador();

        mInserindo.add(mCriando);

        String sImplementando = eTipoOpcional + " :: " + mSimplificador.getAlgo_SoTipos(mCriando);
        if (!mConferindo.contains(sImplementando)) {
            mConferindo.add(sImplementando);
        } else {

            String mConflito = QualConflito_Function(mSimplificador.getAlgo_SoTipos(mCriando), eTipoOpcional, mTodos, mInserindo);
            errar(eTipoOpcionalMostrar + " conflitante : " + mSimplificador.getFuction(mCriando) + " e " + mConflito);

        }

    }

    public void emGrupo_Function(AST mAST, ArrayList<AST> mTodos, ArrayList<AST> mInserindo, ArrayList<String> mConferindo) {

        Simplificador mSimplificador = new Simplificador();

        mConferindo.add("FUNCTION :: " + mSimplificador.getFuction(mAST));

        if (getOpcionais(mAST.getBranch("ARGUMENTS")) > 0) {

            mensagem("FUNCTION :: " + mAST.getNome() + " " + mSimplificador.getParametragemFormas(mAST.getBranch("ARGUMENTS")));

            AST mOriginal = mAST.copiar();

            int mOriginal_Argumentos = getOpcionais(mOriginal.getBranch("ARGUMENTS"));
            if (mOriginal_Argumentos > 0) {
                for (AST eArgumento : mOriginal.getBranch("ARGUMENTS").getASTS()) {
                    if (eArgumento.mesmoValor("OPT")) {
                        AST mCriando = reorganizar(mOriginal.copiar(), eArgumento.getNome());
                        emFunctions(mCriando, mTodos, mInserindo, mConferindo);
                    } else if (eArgumento.mesmoValor("OPTREF")) {
                        AST mCriando = reorganizar(mOriginal.copiar(), eArgumento.getNome());
                        emFunctions(mCriando, mTodos, mInserindo, mConferindo);
                    } else if (eArgumento.mesmoValor("OPTMOC")) {
                        AST mCriando = reorganizar(mOriginal.copiar(), eArgumento.getNome());
                        emFunctions(mCriando, mTodos, mInserindo, mConferindo);
                    }
                }
            }


            if (mOriginal_Argumentos > 1) {
                AST mCriando = reorganizarTodos(mOriginal.copiar());
                emFunctions(mCriando, mTodos, mInserindo, mConferindo);
            }

            removerOpcionais(mAST.getBranch("ARGUMENTS"));


        }

    }


    public void emAutos(AST mCriando, ArrayList<AST> mTodos, ArrayList<AST> mInserindo, ArrayList<String> mConferindo) {

        String eTipoOpcional = "PROTOTYPE_AUTO";
        String eTipoOpcionalMostrar = "Auto";

        Simplificador mSimplificador = new Simplificador();

        mInserindo.add(mCriando);

        String sImplementando = eTipoOpcional + " :: " + mSimplificador.getAlgo_SoTipos(mCriando);
        if (!mConferindo.contains(sImplementando)) {
            mConferindo.add(sImplementando);
        } else {

            String mConflito = QualConflito(mSimplificador.getAlgo_SoTipos(mCriando), eTipoOpcional, mTodos, mInserindo);
            errar(eTipoOpcionalMostrar + " conflitante : " + mSimplificador.getAuto(mCriando) + " e " + mConflito);

        }

    }

    public void emGrupo_Auto(AST mAST, ArrayList<AST> mTodos, ArrayList<AST> mInserindo, ArrayList<String> mConferindo) {

        Simplificador mSimplificador = new Simplificador();

        mConferindo.add("AUTO :: " + mSimplificador.getAuto(mAST));

        if (getOpcionais(mAST.getBranch("ARGUMENTS")) > 0) {

            mensagem("AUTO :: " + mAST.getNome() + " " + mSimplificador.getParametragemFormas(mAST.getBranch("ARGUMENTS")));

            AST mOriginal = mAST.copiar();

            int mOriginal_Argumentos = getOpcionais(mOriginal.getBranch("ARGUMENTS"));
            if (mOriginal_Argumentos > 0) {
                for (AST eArgumento : mOriginal.getBranch("ARGUMENTS").getASTS()) {
                    if (eArgumento.mesmoValor("OPT")) {
                        AST mCriando = reorganizar(mOriginal.copiar(), eArgumento.getNome());
                        emAutos(mCriando, mTodos, mInserindo, mConferindo);
                    } else if (eArgumento.mesmoValor("OPTREF")) {
                        AST mCriando = reorganizar(mOriginal.copiar(), eArgumento.getNome());
                        emAutos(mCriando, mTodos, mInserindo, mConferindo);
                    } else if (eArgumento.mesmoValor("OPTMOC")) {
                        AST mCriando = reorganizar(mOriginal.copiar(), eArgumento.getNome());
                        emAutos(mCriando, mTodos, mInserindo, mConferindo);
                    }
                }
            }


            if (mOriginal_Argumentos > 1) {
                AST mCriando = reorganizarTodos(mOriginal.copiar());
                emAutos(mCriando, mTodos, mInserindo, mConferindo);
            }

            removerOpcionais(mAST.getBranch("ARGUMENTS"));


        }

    }

    public void emFunctors(AST mCriando, ArrayList<AST> mTodos, ArrayList<AST> mInserindo, ArrayList<String> mConferindo) {

        String eTipoOpcional = "PROTOTYPE_FUNCTOR";
        String eTipoOpcionalMostrar = "Functor";

        Simplificador mSimplificador = new Simplificador();

        mInserindo.add(mCriando);

        String sImplementando = eTipoOpcional + " :: " + mSimplificador.getAlgo_SoTipos(mCriando);
        if (!mConferindo.contains(sImplementando)) {
            mConferindo.add(sImplementando);
        } else {

            String mConflito = QualConflito_Function(mSimplificador.getAlgo_SoTipos(mCriando), eTipoOpcional, mTodos, mInserindo);
            errar(eTipoOpcionalMostrar + " conflitante : " + mSimplificador.getFunctor(mCriando) + " e " + mConflito);

        }

    }

    public void emGrupo_Functor(AST mAST, ArrayList<AST> mTodos, ArrayList<AST> mInserindo, ArrayList<String> mConferindo) {

        Simplificador mSimplificador = new Simplificador();

        mConferindo.add("FUNCTOR :: " + mSimplificador.getFunctor(mAST));

        if (getOpcionais(mAST.getBranch("ARGUMENTS")) > 0) {

            mensagem("FUNCTOR :: " + mAST.getNome() + " " + mSimplificador.getParametragemFormas(mAST.getBranch("ARGUMENTS")));

            AST mOriginal = mAST.copiar();

            int mOriginal_Argumentos = getOpcionais(mOriginal.getBranch("ARGUMENTS"));
            if (mOriginal_Argumentos > 0) {
                for (AST eArgumento : mOriginal.getBranch("ARGUMENTS").getASTS()) {
                    if (eArgumento.mesmoValor("OPT")) {
                        AST mCriando = reorganizar(mOriginal.copiar(), eArgumento.getNome());
                        emFunctors(mCriando, mTodos, mInserindo, mConferindo);
                    } else if (eArgumento.mesmoValor("OPTREF")) {
                        AST mCriando = reorganizar(mOriginal.copiar(), eArgumento.getNome());
                        emFunctors(mCriando, mTodos, mInserindo, mConferindo);
                    } else if (eArgumento.mesmoValor("OPTMOC")) {
                        AST mCriando = reorganizar(mOriginal.copiar(), eArgumento.getNome());
                        emFunctors(mCriando, mTodos, mInserindo, mConferindo);
                    }
                }
            }


            if (mOriginal_Argumentos > 1) {
                AST mCriando = reorganizarTodos(mOriginal.copiar());
                emFunctors(mCriando, mTodos, mInserindo, mConferindo);
            }

            removerOpcionais(mAST.getBranch("ARGUMENTS"));


        }

    }


    public void processar(AST eASTPai) {

        ArrayList<AST> mInserindo = new ArrayList<AST>();

        ArrayList<String> mConferindo = new ArrayList<String>();


        for (AST mAST : eASTPai.getASTS()) {

            if (mAST.mesmoTipo("ACTION")) {

                emGrupo_Action(mAST, eASTPai.getASTS(), mInserindo, mConferindo);

            } else if (mAST.mesmoTipo("FUNCTION")) {

                emGrupo_Function(mAST, eASTPai.getASTS(), mInserindo, mConferindo);

            } else if (mAST.mesmoTipo("PROTOTYPE_AUTO")) {

                emGrupo_Auto(mAST, eASTPai.getASTS(), mInserindo, mConferindo);

            } else if (mAST.mesmoTipo("PROTOTYPE_FUNCTOR")) {

                emGrupo_Functor(mAST, eASTPai.getASTS(), mInserindo, mConferindo);

            }

        }

        for (AST mAST : mInserindo) {
            eASTPai.getASTS().add(mAST);
        }


    }


    public int getOpcionais(AST eArgumentos) {
        int o = 0;

        for (AST mAST : eArgumentos.getASTS()) {

            if (mAST.mesmoValor("OPT")) {
                o += 1;
            } else if (mAST.mesmoValor("OPTREF")) {
                o += 1;
            } else if (mAST.mesmoValor("OPTMOC")) {
                o += 1;
            }

        }

        return o;
    }

    public void removerOpcionais(AST eArgumentos) {


        for (AST mAST : eArgumentos.getASTS()) {

            if (mAST.mesmoValor("OPT")) {
                mAST.setValor("VALUE");
            } else if (mAST.mesmoValor("OPTREF")) {
                mAST.setValor("REF");
            } else if (mAST.mesmoValor("OPTMOC")) {
                mAST.setValor("MOC");
            }

        }


    }

    public AST reorganizar(AST eAction, String eArgumentoNome) {


        AST mCriando = eAction.copiar();

        AST eGuardar = null;
        boolean eGuardado = false;

        String eModo = "DEF";

        for (AST eArgumento : mCriando.getBranch("ARGUMENTS").getASTS()) {

            if (eArgumento.mesmoNome(eArgumentoNome)) {
                eGuardar = eArgumento.copiar();
                eGuardado = true;

                //  mensagem("\t - Criar Por : " + eArgumento.getValor() + " :: " + eArgumento.getNome() + " : " + eArgumento.getNome());

            }

            if (eArgumento.mesmoValor("OPT")) {
                eArgumento.setValor("VALUE");
                eModo = "DEF";
            } else if (eArgumento.mesmoValor("OPTREF")) {
                eArgumento.setValor("REF");
                eModo = "DEF";
            } else if (eArgumento.mesmoValor("OPTMOC")) {
                eArgumento.setValor("MOC");
                eModo = "MOC";
            }

        }

        if (eGuardado) {

            for (AST mAST : mCriando.getBranch("ARGUMENTS").getASTS()) {
                if (mAST.mesmoNome(eArgumentoNome)) {
                    mCriando.getBranch("ARGUMENTS").getASTS().remove(mAST);
                    break;
                }
            }

        }

        Simplificador mSimplificador = new Simplificador();

        mensagem("\t Criando " + eAction.getTipo() + " :: " + mCriando.getNome() + " " + mSimplificador.getParametragemFormas(mCriando.getBranch("ARGUMENTS")));

        AST eDef = new AST(eModo);
        eDef.setNome(eGuardar.getNome());
        eDef.getASTS().add(eGuardar.getBranch("TYPE").copiar());
        eDef.getASTS().add(eGuardar.getBranch("VALUE").copiar());

        mCriando.getBranch("BODY").getASTS().add(0, eDef);

        // mensagem(mCriando.getImpressao());

        return mCriando;

    }

    public AST reorganizarTodos(AST eAction) {


        AST mCriando = eAction.copiar();


        while (getOpcionais(mCriando.getBranch("ARGUMENTS")) > 0) {

            for (AST mAST : mCriando.getBranch("ARGUMENTS").getASTS()) {

                boolean desOpcionalizar = false;
                AST eGuardar = null;
                boolean eGuardado = false;

                String eModo = "DEF";

                if (mAST.mesmoValor("OPT")) {
                    mAST.setValor("VALUE");
                    eModo = "DEF";
                    desOpcionalizar = true;
                } else if (mAST.mesmoValor("OPTREF")) {
                    mAST.setValor("REF");
                    eModo = "DEF";
                    desOpcionalizar = true;

                } else if (mAST.mesmoValor("OPTMOC")) {
                    mAST.setValor("MOC");
                    eModo = "MOC";
                    desOpcionalizar = true;

                }

                if (desOpcionalizar) {
                    eGuardar = mAST.copiar();
                    eGuardado = true;
                    mCriando.getBranch("ARGUMENTS").getASTS().remove(mAST);

                    AST eDef = new AST(eModo);
                    eDef.setNome(eGuardar.getNome());
                    eDef.getASTS().add(eGuardar.getBranch("TYPE").copiar());
                    eDef.getASTS().add(eGuardar.getBranch("VALUE").copiar());

                    mCriando.getBranch("BODY").getASTS().add(0, eDef);
                    break;

                }


            }

        }


        Simplificador mSimplificador = new Simplificador();

        mensagem("\t Criando " + eAction.getTipo() + " :: " + mCriando.getNome() + " " + mSimplificador.getParametragemFormas(mCriando.getBranch("ARGUMENTS")));

        // mensagem(mCriando.getImpressao());

        return mCriando;

    }

}
