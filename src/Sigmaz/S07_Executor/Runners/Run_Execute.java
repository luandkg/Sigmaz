package Sigmaz.S07_Executor.Runners;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Escopos.Run_External;
import Sigmaz.S07_Executor.Escopos.Run_Struct;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.RunTime;
import Sigmaz.S00_Utilitarios.AST;

public class Run_Execute {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;


    public Run_Execute(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Execute";


    }


    public void init(AST ASTCorrente) {

        if (ASTCorrente.mesmoValor("FUNCT")) {

            Run_Any mAST = new Run_Any(mRunTime);
            mAST.init_ActionFunction(ASTCorrente, mEscopo);

        } else if (ASTCorrente.getValor().contentEquals("STRUCT")) {

            Item mItem = mEscopo.getItem(ASTCorrente.getNome());


            if (mRunTime.getErros().size() > 0) {
                return;
            }

            Run_Context mRun_Context = new Run_Context(mRunTime);

            if (mRun_Context.getQualificadorIsStruct(mItem.getTipo(), mEscopo)) {
                executeStruct(ASTCorrente, mItem);
            } else {
                mRunTime.errar(mLocal, "O tipo " + mItem.getTipo() + " nao e STRUCT !");
                return;
            }


        } else if (ASTCorrente.getValor().contentEquals("STRUCT_EXTERN")) {

            executeExtern(ASTCorrente, mEscopo);


        } else {
            mRunTime.errar(mLocal, "Problema na execucao : " + ASTCorrente.getNome());
        }


    }


    public void executeStruct(AST ASTCorrente, Item mItem) {

        if (mRunTime.getErros().size() > 0) {
            return;
        }


        Run_Struct mEscopoStruct = mRunTime.getHeap().getRun_Struct(mItem.getValor(mRunTime, mEscopo));

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        while (ASTCorrente.existeBranch("INTERNAL")) {

            AST eInternal = ASTCorrente.getBranch("INTERNAL");

            // System.out.println("STRUCT FUNC Chamando : " + ASTCorrente.getBranch("INTERNAL").getNome());

            if (eInternal.mesmoValor("STRUCT_ACT")) {

                // System.out.println("STRUCT ACT Estou : " +mEscopo.getNome() );
                //  System.out.println("STRUCT ACT Preciso : " +mEscopoStruct.getNome() );

                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                mEscopoStruct.init_ActionFunction(eInternal, mEscopo);

                break;
            } else if (eInternal.mesmoValor("STRUCT_FUNCT")) {


                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                //System.out.println("STRUCT FUNCT Estou : " + eInternal.getNome());

                // if (!mItem.getTipo().contentEquals(eRetorno)) {
                mEscopoStruct = mRunTime.getHeap().getRun_Struct(mItem.getValor(mRunTime, mEscopo));

                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                //System.out.println("STRUCT Estou : " + mEscopoStruct.getNome());

                if (mItem.getNome().contentEquals("this")) {

                    if (eInternal.existeBranch("INTERNAL")) {
                        mItem = mEscopoStruct.init_FunctionDireto(eInternal, mEscopo, "<<ANY>>");
                    } else {
                        mEscopoStruct.init_ActionFunctionDireto(eInternal, mEscopo);
                        break;
                    }

                } else {
                    if (eInternal.existeBranch("INTERNAL")) {
                        mItem = mEscopoStruct.init_Function(eInternal, mEscopo, "<<ANY>>");
                    } else {
                        mEscopoStruct.init_ActionFunction(eInternal, mEscopo);
                        break;
                    }
                }


                if (mRunTime.getErros().size() > 0) {
                    return;
                }


            } else if (eInternal.mesmoValor("STRUCT_OBJECT")) {

                //System.out.println("STRUCT OBJECT : " +eInternal.getNome() );

                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                if (mEscopoStruct == null) {
                    mRunTime.errar(mLocal, "Estrutura" + " " + ASTCorrente.getNome() + " : Nula !");
                    return;
                }

                if (mItem.getNome().contentEquals("this")) {
                    mItem = mEscopoStruct.init_ObjectDireto(eInternal, mEscopo, "<<ANY>>");
                } else {
                    mItem = mEscopoStruct.init_Object(eInternal, mEscopo, "<<ANY>>");
                }

                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                mEscopoStruct = mRunTime.getHeap().getRun_Struct(mItem.getValor(mRunTime, mEscopo));

                if (mRunTime.getErros().size() > 0) {
                    return;
                }

            }

            ASTCorrente = eInternal;
        }

    }

    public void executeExtern(AST ASTCorrente, Escopo gEscopo) {


        Run_Context mRun_Context = new Run_Context(mRunTime);

        boolean enc = false;

        for (Run_External mEscopoExtern : mRun_Context.getRunExternContexto(gEscopo.getRefers())) {

            //    System.out.println("\t - PASS EXTERN : " + mRun_Struct.getNome());

            if (mEscopoExtern.getNome().contentEquals(ASTCorrente.getNome())) {



                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                AST eInternal = ASTCorrente.getBranch("INTERNAL");


                if (eInternal.mesmoValor("STRUCT_FUNCT")) {


                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }

                    //  System.out.println("Mudando Para EXTERN - STRUCT_FUNCT " + eInternal.getNome());


                    mEscopoExtern.init_ActionFunction_Extern(eInternal, gEscopo);

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }


                } else if (eInternal.mesmoValor("STRUCT_OBJECT")) {

                    //      System.out.println("STRUCT_OBJECT : " + mEscopoStruct.getNome());

                    Item eItem = mEscopoExtern.init_ObjectExtern(eInternal, gEscopo, "<<ANY>>");

                    while (eInternal.existeBranch("INTERNAL")) {

                        AST sInternal = eInternal.getBranch("INTERNAL");

                        if (sInternal.mesmoValor("STRUCT_ACT")) {

                        } else if (sInternal.mesmoValor("STRUCT_FUNCT")) {

                            //System.out.println("STRUCT OBJECT : " + eItem.getValor());

                            if (mRunTime.getErros().size() > 0) {
                                return;
                            }

                            if (!eItem.getTipo().contentEquals("<<ANY>>")) {
                                //   mEscopoExtern = mRunTime.getRun_Struct(eItem.getValor());

                                if (mRunTime.getErros().size() > 0) {
                                    return;
                                }


                                mEscopoExtern.init_ActionFunction_Extern(sInternal, gEscopo);

                                if (mRunTime.getErros().size() > 0) {
                                    return;
                                }
                            } else {

                                break;
                            }


                        } else if (sInternal.mesmoValor("STRUCT_OBJECT")) {

                        }
                    }


                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }


                } else {

                    mRunTime.errar(mLocal, "AST_Value --> STRUCTURED VALUE !");

                }


                enc = true;
                break;
            }
        }

        //  System.out.println("ENC EXTERN : " + ASTCorrente.getNome());

        if (!enc) {
            mRunTime.errar(mLocal, "External nao encontrado : " + ASTCorrente.getNome());
        }




    }

}
