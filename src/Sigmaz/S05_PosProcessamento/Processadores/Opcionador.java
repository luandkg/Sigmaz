package Sigmaz.S05_PosProcessamento.Processadores;

import java.util.ArrayList;

import Sigmaz.S05_PosProcessamento.PosProcessador;
import Sigmaz.S00_Utilitarios.AST;

public class Opcionador {


    private PosProcessador mPosProcessador;

    public Opcionador(PosProcessador ePosProcessador) {

        mPosProcessador = ePosProcessador;

    }

    public void init(ArrayList<AST> mTodos) {

        mPosProcessador.mensagem("");
        mPosProcessador.mensagem(" ------------------ FASE OPCIONADOR ----------------------- ");


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


                if (getOpcionais(mAST.getBranch("ARGUMENTS")) > 0) {

                    mPosProcessador.mensagem("Action " + mAST.getNome() + " :: OPT " + getOpcionais(mAST.getBranch("ARGUMENTS")));

                    action_desopcionar(mAST.copiar(), mInserirActions);
                    removerOpcionais(mAST.getBranch("ARGUMENTS"));

                }


            } else if (mAST.mesmoTipo("FUNCTION")) {


                if (getOpcionais(mAST.getBranch("ARGUMENTS")) > 0) {

                    mPosProcessador.mensagem("Function " + mAST.getNome() + " :: OPT " + getOpcionais(mAST.getBranch("ARGUMENTS")));

                    function_desopcionar(mAST.copiar(), mInserirActions);
                    removerOpcionais(mAST.getBranch("ARGUMENTS"));

                }

            } else if (mAST.mesmoTipo("PROTOTYPE_AUTO")) {

                if (getOpcionais(mAST.getBranch("ARGUMENTS")) > 0) {

                    mPosProcessador.mensagem("Auto " + mAST.getNome() + " :: OPT " + getOpcionais(mAST.getBranch("ARGUMENTS")));

                    auto_desopcionar(mAST.copiar(), mInserirActions);
                    removerOpcionais(mAST.getBranch("ARGUMENTS"));

                }


            } else if (mAST.mesmoTipo("PROTOTYPE_FUNCTOR")) {

                if (getOpcionais(mAST.getBranch("ARGUMENTS")) > 0) {

                    mPosProcessador.mensagem("Functor " + mAST.getNome() + " :: OPT " + getOpcionais(mAST.getBranch("ARGUMENTS")));

                    functor_desopcionar(mAST.copiar(), mInserirActions);
                    removerOpcionais(mAST.getBranch("ARGUMENTS"));

                }

            }


        }

        for (AST mAST : mInserirActions) {
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
            }

        }

        return o;
    }


    public void action_desopcionarCom(AST eAction, AST eCom, ArrayList<AST> mArgumentar, ArrayList<AST> mInserirActions) {


        AST nAction = eAction;
        AST segAction = nAction.copiar();


        for (AST mAST : segAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {

                mAST.setValor("VALUE");

                if (getOpcionais(segAction.getBranch("ARGUMENTS")) > 0) {
                    action_desopcionarCom(segAction, mAST, mArgumentar, mInserirActions);
                }

                break;
            } else if (mAST.mesmoValor("OPTREF")) {

                mAST.setValor("REF");

                if (getOpcionais(segAction.getBranch("ARGUMENTS")) > 0) {
                    action_desopcionarCom(segAction, mAST, mArgumentar, mInserirActions);
                }

                break;
            }
        }

        for (AST mAST : nAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {
                mAST.setValor("VALUE");
            } else if (mAST.mesmoValor("OPTREF")) {
                mAST.setValor("REF");
            }

            if (mAST.existeBranch("VALUE")) {
                mAST.getASTS().remove(mAST.getBranch("VALUE"));
            }
        }

        for (AST mAST : nAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoNome(eCom.getNome())) {
                nAction.getBranch("ARGUMENTS").getASTS().remove(mAST);
                break;
            }
        }

        ArrayList<String> mExiste = new ArrayList<String>();
        for (AST mAST : nAction.getBranch("ARGUMENTS").getASTS()) {
            mExiste.add(mAST.getNome());
        }


        mPosProcessador.mensagem("Criando Opcional Action : " + eAction.getNome() + " por " + eCom.getNome());

        nAction.getBranch("BODY").getASTS().clear();

        //  for (AST mAST : mArgumentar) {
        //    mAnalisador.mensagem("ARGUMENTO " + mAST.getNome() + " : " + mAST.getValor());
        // }


        for (AST mAST : mArgumentar) {


            if (!mExiste.contains(mAST.getNome())) {

                AST eDef = nAction.getBranch("BODY").criarBranch("DEF");
                eDef.setNome(mAST.getNome());

                for (AST gAST : segAction.getBranch("ARGUMENTS").getASTS()) {
                    if (gAST.mesmoNome(mAST.getNome())) {
                        eDef.getASTS().add(gAST.getBranch("TYPE").copiar());
                        break;
                    }
                }

                eDef.getASTS().add(mAST.getBranch("VALUE").copiar());

            }
        }


        AST eExecute = nAction.getBranch("BODY").criarBranch("EXECUTE");
        eExecute.setNome(nAction.getNome());
        eExecute.setValor("FUNCT");

        AST eExecuteArguments = eExecute.criarBranch("ARGUMENTS");
        for (AST mAST : mArgumentar) {
            AST eArg = eExecuteArguments.criarBranch("ARGUMENT");
            eArg.setNome(mAST.getNome());
            // if(mExiste.contains(mAST.getNome())){
            eArg.setValor("ID");
            // }
        }


        // System.out.println(nAction.ImprimirArvoreDeInstrucoes());
        mInserirActions.add(nAction);

    }

    public void action_desopcionar(AST eAction, ArrayList<AST> mInserirActions) {

        AST nAction = eAction;
        AST segAction = nAction.copiar();
        AST oAction = nAction.copiar();

        ArrayList<AST> mArgumentar = new ArrayList<AST>();

        ArrayList<String> mExiste = new ArrayList<String>();


        for (AST mAST : nAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {

                AST eArg = mAST.copiar();
                mArgumentar.add(eArg);
            } else if (mAST.mesmoValor("OPTREF")) {

                AST eArg = mAST.copiar();
                mArgumentar.add(eArg);
            } else {
                AST eArg = mAST.copiar();

                mArgumentar.add(eArg);
                mExiste.add(mAST.getNome());
            }
        }


        for (AST mAST : segAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {

                mAST.setValor("VALUE");

                if (getOpcionais(segAction.getBranch("ARGUMENTS")) > 0) {
                    action_desopcionarCom(segAction, mAST, mArgumentar, mInserirActions);
                }

                break;
            } else if (mAST.mesmoValor("OPTREF")) {

                mAST.setValor("REF");

                if (getOpcionais(segAction.getBranch("ARGUMENTS")) > 0) {
                    action_desopcionarCom(segAction, mAST, mArgumentar, mInserirActions);
                }

                break;
            }
        }

        int opt = 0;

        AST tAction = eAction.copiar();


        for (AST mAST : tAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {
                opt += 1;
            } else if (mAST.mesmoValor("OPTREF")) {
                opt += 1;
            }
        }


        while (opt > 0) {

            for (AST mAST : tAction.getBranch("ARGUMENTS").getASTS()) {
                if (mAST.mesmoValor("OPT")) {
                    tAction.getBranch("ARGUMENTS").getASTS().remove(mAST);
                    break;
                } else if (mAST.mesmoValor("OPTREF")) {
                    tAction.getBranch("ARGUMENTS").getASTS().remove(mAST);
                    break;
                }
            }

            opt = 0;
            for (AST mAST : tAction.getBranch("ARGUMENTS").getASTS()) {
                if (mAST.mesmoValor("OPT")) {
                    opt += 1;
                } else if (mAST.mesmoValor("OPTREF")) {
                    opt += 1;
                }
            }
        }

        mPosProcessador.mensagem("Criando Opcional Action : " + eAction.getNome() + " Sem opcionais");


        tAction.getBranch("BODY").getASTS().clear();

        for (AST mAST : mArgumentar) {


            if (!mExiste.contains(mAST.getNome())) {

                AST eDef = tAction.getBranch("BODY").criarBranch("DEF");
                eDef.setNome(mAST.getNome());

                for (AST gAST : oAction.getBranch("ARGUMENTS").getASTS()) {
                    if (gAST.mesmoNome(mAST.getNome())) {
                        eDef.getASTS().add(gAST.getBranch("TYPE").copiar());
                        break;
                    }
                }

                eDef.getASTS().add(mAST.getBranch("VALUE").copiar());

            }
        }


        AST eExecute = tAction.getBranch("BODY").criarBranch("EXECUTE");
        eExecute.setNome(nAction.getNome());
        eExecute.setValor("FUNCT");

        AST eExecuteArguments = eExecute.criarBranch("ARGUMENTS");
        for (AST mAST : mArgumentar) {
            AST eArg = eExecuteArguments.criarBranch("ARGUMENT");
            eArg.setNome(mAST.getNome());
            // if(mExiste.contains(mAST.getNome())){
            eArg.setValor("ID");
            // }
        }

        //  System.out.println(tAction.ImprimirArvoreDeInstrucoes());

        mInserirActions.add(tAction);
    }


    public void removerOpcionais(AST eArgumentos) {


        for (AST mAST : eArgumentos.getASTS()) {

            if (mAST.mesmoValor("OPT")) {
                mAST.setValor("VALUE");
            } else if (mAST.mesmoValor("OPTREF")) {
                mAST.setValor("REF");
            }

        }


    }

    public void function_desopcionarCom(AST eAction, AST eCom, ArrayList<AST> mArgumentar, ArrayList<AST> mInserirActions) {


        AST nAction = eAction;
        AST segAction = nAction.copiar();


        for (AST mAST : segAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {

                mAST.setValor("VALUE");
                if (getOpcionais(segAction.getBranch("ARGUMENTS")) > 0) {

                    function_desopcionarCom(segAction, mAST, mArgumentar, mInserirActions);
                }
                break;
            } else if (mAST.mesmoValor("OPTREF")) {

                mAST.setValor("REF");

                if (getOpcionais(segAction.getBranch("ARGUMENTS")) > 0) {
                    function_desopcionarCom(segAction, mAST, mArgumentar, mInserirActions);
                }

                break;
            }
        }

        for (AST mAST : nAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {
                mAST.setValor("VALUE");

            } else if (mAST.mesmoValor("OPTREF")) {
                mAST.setValor("REF");
            }

            if (mAST.existeBranch("VALUE")) {
                mAST.getASTS().remove(mAST.getBranch("VALUE"));
            }
        }

        for (AST mAST : nAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoNome(eCom.getNome())) {
                nAction.getBranch("ARGUMENTS").getASTS().remove(mAST);
                break;
            }
        }

        ArrayList<String> mExiste = new ArrayList<String>();
        for (AST mAST : nAction.getBranch("ARGUMENTS").getASTS()) {
            mExiste.add(mAST.getNome());
        }


        mPosProcessador.mensagem("Criando Opcional Function : " + eAction.getNome() + " por " + eCom.getNome());

        nAction.getBranch("BODY").getASTS().clear();

        //  for (AST mAST : mArgumentar) {
        //  mAnalisador.mensagem("ARGUMENTO " + mAST.getNome() + " : " + mAST.getValor());
        // }


        for (AST mAST : mArgumentar) {


            if (!mExiste.contains(mAST.getNome())) {

                AST eDef = nAction.getBranch("BODY").criarBranch("DEF");
                eDef.setNome(mAST.getNome());

                for (AST gAST : segAction.getBranch("ARGUMENTS").getASTS()) {
                    if (gAST.mesmoNome(mAST.getNome())) {
                        eDef.getASTS().add(gAST.getBranch("TYPE").copiar());
                        break;
                    }
                }

                eDef.getASTS().add(mAST.getBranch("VALUE").copiar());

            }
        }


        AST eExecute = nAction.getBranch("BODY").criarBranch("RETURN");
        AST eReturn_Value = eExecute.criarBranch("VALUE");

        eReturn_Value.setNome(nAction.getNome());
        eReturn_Value.setValor("FUNCT");

        AST eExecuteArguments = eReturn_Value.criarBranch("ARGUMENTS");

        for (AST mAST : mArgumentar) {
            AST eArg = eExecuteArguments.criarBranch("ARGUMENT");
            eArg.setNome(mAST.getNome());
            // if(mExiste.contains(mAST.getNome())){
            eArg.setValor("ID");
            // }
        }


        // System.out.println(nAction.ImprimirArvoreDeInstrucoes());
        mInserirActions.add(nAction);

    }

    public void function_desopcionar(AST eAction, ArrayList<AST> mInserirActions) {

        AST nAction = eAction;
        AST segAction = nAction.copiar();
        AST oAction = nAction.copiar();

        ArrayList<AST> mArgumentar = new ArrayList<AST>();

        ArrayList<String> mExiste = new ArrayList<String>();


        for (AST mAST : nAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {

                AST eArg = mAST.copiar();
                mArgumentar.add(eArg);
            } else if (mAST.mesmoValor("OPTREF")) {

                AST eArg = mAST.copiar();
                mArgumentar.add(eArg);
            } else {
                AST eArg = mAST.copiar();

                mArgumentar.add(eArg);
                mExiste.add(mAST.getNome());
            }
        }


        for (AST mAST : segAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {

                mAST.setValor("VALUE");
                if (getOpcionais(segAction.getBranch("ARGUMENTS")) > 0) {

                    function_desopcionarCom(segAction, mAST, mArgumentar, mInserirActions);
                }

                break;
            } else if (mAST.mesmoValor("OPTREF")) {

                mAST.setValor("REF");

                if (getOpcionais(segAction.getBranch("ARGUMENTS")) > 0) {
                    function_desopcionarCom(segAction, mAST, mArgumentar, mInserirActions);
                }

                break;
            }
        }

        int opt = 0;

        AST tAction = eAction.copiar();


        for (AST mAST : tAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {
                opt += 1;
            } else if (mAST.mesmoValor("OPTREF")) {
                opt += 1;
            }
        }


        while (opt > 0) {

            for (AST mAST : tAction.getBranch("ARGUMENTS").getASTS()) {
                if (mAST.mesmoValor("OPT")) {
                    tAction.getBranch("ARGUMENTS").getASTS().remove(mAST);
                    break;
                } else if (mAST.mesmoValor("OPTREF")) {
                    tAction.getBranch("ARGUMENTS").getASTS().remove(mAST);
                    break;
                }
            }

            opt = 0;
            for (AST mAST : tAction.getBranch("ARGUMENTS").getASTS()) {
                if (mAST.mesmoValor("OPT")) {
                    opt += 1;
                } else if (mAST.mesmoValor("OPTREF")) {
                    opt += 1;
                }
            }
        }

        mPosProcessador.mensagem("Criando Opcional Function : " + eAction.getNome() + " Sem opcionais");


        tAction.getBranch("BODY").getASTS().clear();

        for (AST mAST : mArgumentar) {


            if (!mExiste.contains(mAST.getNome())) {

                AST eDef = tAction.getBranch("BODY").criarBranch("DEF");
                eDef.setNome(mAST.getNome());

                for (AST gAST : oAction.getBranch("ARGUMENTS").getASTS()) {
                    if (gAST.mesmoNome(mAST.getNome())) {
                        eDef.getASTS().add(gAST.getBranch("TYPE").copiar());
                        break;
                    }
                }

                eDef.getASTS().add(mAST.getBranch("VALUE").copiar());

            }
        }


        AST eExecute = tAction.getBranch("BODY").criarBranch("RETURN");

        AST eReturn_Value = eExecute.criarBranch("VALUE");

        eReturn_Value.setNome(nAction.getNome());
        eReturn_Value.setValor("FUNCT");

        AST eExecuteArguments = eReturn_Value.criarBranch("ARGUMENTS");
        for (AST mAST : mArgumentar) {
            AST eArg = eExecuteArguments.criarBranch("ARGUMENT");
            eArg.setNome(mAST.getNome());
            // if(mExiste.contains(mAST.getNome())){
            eArg.setValor("ID");
            // }
        }


        mInserirActions.add(tAction);
    }

    public void functor_desopcionarCom(AST eAction, AST eCom, ArrayList<AST> mArgumentar, ArrayList<AST> mInserirActions) {


        AST nAction = eAction;
        AST segAction = nAction.copiar();


        for (AST mAST : segAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {

                mAST.setValor("VALUE");
                if (getOpcionais(segAction.getBranch("ARGUMENTS")) > 0) {

                    function_desopcionarCom(segAction, mAST, mArgumentar, mInserirActions);
                }
                break;
            } else if (mAST.mesmoValor("OPTREF")) {

                mAST.setValor("REF");

                if (getOpcionais(segAction.getBranch("ARGUMENTS")) > 0) {
                    function_desopcionarCom(segAction, mAST, mArgumentar, mInserirActions);
                }

                break;
            }
        }

        for (AST mAST : nAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {
                mAST.setValor("VALUE");

            } else if (mAST.mesmoValor("OPTREF")) {
                mAST.setValor("REF");
            }

            if (mAST.existeBranch("VALUE")) {
                mAST.getASTS().remove(mAST.getBranch("VALUE"));
            }
        }

        for (AST mAST : nAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoNome(eCom.getNome())) {
                nAction.getBranch("ARGUMENTS").getASTS().remove(mAST);
                break;
            }
        }

        ArrayList<String> mExiste = new ArrayList<String>();
        for (AST mAST : nAction.getBranch("ARGUMENTS").getASTS()) {
            mExiste.add(mAST.getNome());
        }


        mPosProcessador.mensagem("Criando Opcional Function : " + eAction.getNome() + " por " + eCom.getNome());

        nAction.getBranch("BODY").getASTS().clear();

        //  for (AST mAST : mArgumentar) {
        //  mAnalisador.mensagem("ARGUMENTO " + mAST.getNome() + " : " + mAST.getValor());
        // }


        for (AST mAST : mArgumentar) {


            if (!mExiste.contains(mAST.getNome())) {

                AST eDef = nAction.getBranch("BODY").criarBranch("DEF");
                eDef.setNome(mAST.getNome());

                for (AST gAST : segAction.getBranch("ARGUMENTS").getASTS()) {
                    if (gAST.mesmoNome(mAST.getNome())) {
                        eDef.getASTS().add(gAST.getBranch("TYPE").copiar());
                        break;
                    }
                }

                eDef.getASTS().add(mAST.getBranch("VALUE").copiar());

            }
        }


        AST eExecute = nAction.getBranch("BODY").criarBranch("RETURN");
        AST eReturn_Value = eExecute.criarBranch("VALUE");

        eReturn_Value.setNome(nAction.getNome());
        eReturn_Value.setValor("FUNCT");

        AST eExecuteArguments = eReturn_Value.criarBranch("ARGUMENTS");

        for (AST mAST : mArgumentar) {
            AST eArg = eExecuteArguments.criarBranch("ARGUMENT");
            eArg.setNome(mAST.getNome());
            // if(mExiste.contains(mAST.getNome())){
            eArg.setValor("ID");
            // }
        }


        // System.out.println(nAction.ImprimirArvoreDeInstrucoes());
        mInserirActions.add(nAction);

    }

    public void auto_desopcionar(AST eAction, ArrayList<AST> mInserirActions) {

        AST nAction = eAction;
        AST segAction = nAction.copiar();
        AST oAction = nAction.copiar();

        ArrayList<AST> mArgumentar = new ArrayList<AST>();

        ArrayList<String> mExiste = new ArrayList<String>();

        AST eGenericsCopia = eAction.getBranch("GENERICS").copiar();

        for (AST mAST : nAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {

                AST eArg = mAST.copiar();
                mArgumentar.add(eArg);
            } else if (mAST.mesmoValor("OPTREF")) {

                AST eArg = mAST.copiar();
                mArgumentar.add(eArg);
            } else {
                AST eArg = mAST.copiar();

                mArgumentar.add(eArg);
                mExiste.add(mAST.getNome());
            }
        }


        for (AST mAST : segAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {

                mAST.setValor("VALUE");
                if (getOpcionais(segAction.getBranch("ARGUMENTS")) > 0) {

                    functor_desopcionarCom(segAction, mAST, mArgumentar, mInserirActions);
                }

                break;
            } else if (mAST.mesmoValor("OPTREF")) {

                mAST.setValor("REF");

                if (getOpcionais(segAction.getBranch("ARGUMENTS")) > 0) {
                    functor_desopcionarCom(segAction, mAST, mArgumentar, mInserirActions);
                }

                break;
            }
        }

        int opt = 0;

        AST tAction = eAction.copiar();


        for (AST mAST : tAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {
                opt += 1;
            } else if (mAST.mesmoValor("OPTREF")) {
                opt += 1;
            }
        }


        while (opt > 0) {

            for (AST mAST : tAction.getBranch("ARGUMENTS").getASTS()) {
                if (mAST.mesmoValor("OPT")) {
                    tAction.getBranch("ARGUMENTS").getASTS().remove(mAST);
                    break;
                } else if (mAST.mesmoValor("OPTREF")) {
                    tAction.getBranch("ARGUMENTS").getASTS().remove(mAST);
                    break;
                }
            }

            opt = 0;
            for (AST mAST : tAction.getBranch("ARGUMENTS").getASTS()) {
                if (mAST.mesmoValor("OPT")) {
                    opt += 1;
                } else if (mAST.mesmoValor("OPTREF")) {
                    opt += 1;
                }
            }
        }

        mPosProcessador.mensagem("Criando Opcional Auto : " + eAction.getNome() + " Sem opcionais");


        tAction.getBranch("BODY").getASTS().clear();

        for (AST mAST : mArgumentar) {


            if (!mExiste.contains(mAST.getNome())) {

                AST eDef = tAction.getBranch("BODY").criarBranch("DEF");
                eDef.setNome(mAST.getNome());

                for (AST gAST : oAction.getBranch("ARGUMENTS").getASTS()) {
                    if (gAST.mesmoNome(mAST.getNome())) {
                        eDef.getASTS().add(gAST.getBranch("TYPE").copiar());
                        break;
                    }
                }

                eDef.getASTS().add(mAST.getBranch("VALUE").copiar());

            }
        }


        AST eReturn_Value = tAction.getBranch("BODY").criarBranch("EXECUTE_AUTO");

        eReturn_Value.setNome(nAction.getNome());

        AST eGenerics = eReturn_Value.criarBranch("GENERICS");
        for (AST eG : eGenericsCopia.getASTS()) {
            eGenerics.getASTS().add(eG);
        }

        AST eExecuteArguments = eReturn_Value.criarBranch("ARGUMENTS");
        for (AST mAST : mArgumentar) {
            AST eArg = eExecuteArguments.criarBranch("ARGUMENT");
            eArg.setNome(mAST.getNome());
            eArg.setValor("ID");
        }


        mInserirActions.add(tAction);
    }


    public void functor_desopcionar(AST eAction, ArrayList<AST> mInserirActions) {

        AST nAction = eAction;
        AST segAction = nAction.copiar();
        AST oAction = nAction.copiar();

        ArrayList<AST> mArgumentar = new ArrayList<AST>();

        ArrayList<String> mExiste = new ArrayList<String>();

        AST eGenericsCopia = eAction.getBranch("GENERICS").copiar();

        for (AST mAST : nAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {

                AST eArg = mAST.copiar();
                mArgumentar.add(eArg);
            } else if (mAST.mesmoValor("OPTREF")) {

                AST eArg = mAST.copiar();
                mArgumentar.add(eArg);
            } else {
                AST eArg = mAST.copiar();

                mArgumentar.add(eArg);
                mExiste.add(mAST.getNome());
            }
        }


        for (AST mAST : segAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {

                mAST.setValor("VALUE");
                if (getOpcionais(segAction.getBranch("ARGUMENTS")) > 0) {

                    functor_desopcionarCom(segAction, mAST, mArgumentar, mInserirActions);
                }

                break;
            } else if (mAST.mesmoValor("OPTREF")) {

                mAST.setValor("REF");

                if (getOpcionais(segAction.getBranch("ARGUMENTS")) > 0) {
                    functor_desopcionarCom(segAction, mAST, mArgumentar, mInserirActions);
                }

                break;
            }
        }

        int opt = 0;

        AST tAction = eAction.copiar();


        for (AST mAST : tAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {
                opt += 1;
            } else if (mAST.mesmoValor("OPTREF")) {
                opt += 1;
            }
        }


        while (opt > 0) {

            for (AST mAST : tAction.getBranch("ARGUMENTS").getASTS()) {
                if (mAST.mesmoValor("OPT")) {
                    tAction.getBranch("ARGUMENTS").getASTS().remove(mAST);
                    break;
                } else if (mAST.mesmoValor("OPTREF")) {
                    tAction.getBranch("ARGUMENTS").getASTS().remove(mAST);
                    break;
                }
            }

            opt = 0;
            for (AST mAST : tAction.getBranch("ARGUMENTS").getASTS()) {
                if (mAST.mesmoValor("OPT")) {
                    opt += 1;
                } else if (mAST.mesmoValor("OPTREF")) {
                    opt += 1;
                }
            }
        }

        mPosProcessador.mensagem("Criando Opcional Functor : " + eAction.getNome() + " Sem opcionais");


        tAction.getBranch("BODY").getASTS().clear();

        for (AST mAST : mArgumentar) {


            if (!mExiste.contains(mAST.getNome())) {

                AST eDef = tAction.getBranch("BODY").criarBranch("DEF");
                eDef.setNome(mAST.getNome());

                for (AST gAST : oAction.getBranch("ARGUMENTS").getASTS()) {
                    if (gAST.mesmoNome(mAST.getNome())) {
                        eDef.getASTS().add(gAST.getBranch("TYPE").copiar());
                        break;
                    }
                }

                eDef.getASTS().add(mAST.getBranch("VALUE").copiar());

            }
        }


        AST eExecute = tAction.getBranch("BODY").criarBranch("RETURN");

        AST eReturn_Value = eExecute.criarBranch("VALUE");

        eReturn_Value.setNome(nAction.getNome());
        eReturn_Value.setValor("EXECUTE_FUNCTOR");

        AST eGenerics = eReturn_Value.criarBranch("GENERICS");
        for (AST eG : eGenericsCopia.getASTS()) {
            eGenerics.getASTS().add(eG);
        }

        AST eExecuteArguments = eReturn_Value.criarBranch("ARGUMENTS");
        for (AST mAST : mArgumentar) {
            AST eArg = eExecuteArguments.criarBranch("ARGUMENT");
            eArg.setNome(mAST.getNome());
            eArg.setValor("ID");
        }


        mInserirActions.add(tAction);
    }


}
