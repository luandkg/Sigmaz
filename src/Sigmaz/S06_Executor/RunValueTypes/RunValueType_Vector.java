package Sigmaz.S06_Executor.RunValueTypes;

import Sigmaz.S06_Executor.AST_Implementador;
import Sigmaz.S06_Executor.Escopo;
import Sigmaz.S06_Executor.Item;
import Sigmaz.S06_Executor.RunTime;
import Sigmaz.S06_Executor.Runners.Run_Execute;
import Sigmaz.S06_Executor.Escopos.Run_Struct;
import Sigmaz.S06_Executor.Runners.Run_Value;
import Sigmaz.S00_Utilitarios.AST;

public class RunValueType_Vector {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public RunValueType_Vector(RunTime eRunTime, Escopo eEscopo){

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value -> Vector";

    }

    public void init(Run_Value eRunValue,AST ASTCorrente, String eRetorno) {


        Escopo tmp = new Escopo(mRunTime, mEscopo);

        // System.out.println("VECTOR  -> ");

        long VECTORID = mRunTime.getHeap().getVECTORID();



        String eNomeVector = "{{VECTOR}}::" + VECTORID;


        String eTipado = "";
        boolean eTipadoja = false;

        if (ASTCorrente.getASTS().size() == 0) {
            mRunTime.errar(mLocal, "O retorno de um objeto VETOR nao pode ser vazio !");
            return;
        }


        for (AST eAST : ASTCorrente.getASTS()) {

            Run_Value eRV = new Run_Value(mRunTime, mEscopo);
            eRV.init(eAST, "<<ANY>>");
            if (eTipadoja == false) {
                eTipadoja = true;
                eTipado = eRV.getRetornoTipo();
            }

            break;

        }

        //   eNome = "<Struct::" + "Vetor<>Vetor<" + eTipado + ">>" + ":" + HEAPID + ">";

        // System.out.println(eNome);

        AST_Implementador mImplementador = new AST_Implementador();


        Escopo EachEscopo = new Escopo(mRunTime, tmp);


        AST eAST = mImplementador.criar_InitGenerico("Vetor", eTipado);
        AST eArg = eAST.getBranch("ARGUMENTS").criarBranch("ARGUMENT");
        eArg.setNome(ASTCorrente.getASTS().size() + "");
        eArg.setValor("Num");

        // System.out.println(eAST.ImprimirArvoreDeInstrucoes());

        // ARGUMENTS ->  :
        //   ARGUMENT -> 5 : Num


        Run_Struct mRun_Struct = new Run_Struct(mRunTime);
        mRun_Struct.init(eAST, EachEscopo);

        Item mItem = tmp.criarDefinicao(eNomeVector, "Vetor<>Vetor<" + eTipado + ">", mRun_Struct.getNome());

        //   System.out.println("VETOR -->> " + eNome);
        //System.out.println(eAST.ImprimirArvoreDeInstrucoes());

        mRunTime.getHeap().adicionarStruct(mRun_Struct);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        int eV = 0;

        for (AST oAST : ASTCorrente.getASTS()) {

            Run_Value eRV = new Run_Value(mRunTime, mEscopo);
            eRV.init(oAST, "<<ANY>>");


            if (eRV.getIsPrimitivo()) {

                if (eRV.getRetornoTipo().contentEquals("num")) {

                    if (eRV.getIsNulo()) {

                        AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", "null", "ID");
                        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
                        mASTExecute.init(mExecute);

                    } else {

                        AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", eRV.getConteudo(), "Num");
                        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
                        mASTExecute.init(mExecute);

                    }

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }
                } else if (eRV.getRetornoTipo().contentEquals("int")) {

                    if (eRV.getIsNulo()) {

                        AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", "null", "ID");
                        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
                        mASTExecute.init(mExecute);

                    } else {

                        AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", eRV.getConteudo(), "Num");
                        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
                        mASTExecute.init(mExecute);

                    }

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }
                } else if (eRV.getRetornoTipo().contentEquals("string")) {

                    if (eRV.getIsNulo()) {

                        AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", "null", "ID");
                        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
                        mASTExecute.init(mExecute);

                    } else {

                        AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", eRV.getConteudo(), "Text");
                        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
                        mASTExecute.init(mExecute);

                    }

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }


                } else if (eRV.getRetornoTipo().contentEquals("bool")) {

                    if (eRV.getIsNulo()) {


                        AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", "null", "ID");
                        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
                        mASTExecute.init(mExecute);

                    } else {
                        AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", eRV.getConteudo(), "ID");
                        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
                        mASTExecute.init(mExecute);
                    }

                    // System.out.println(mExecute.ImprimirArvoreDeInstrucoes());

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }

                } else {

                    mRunTime.errar(mLocal, "Tipo primitivo desconhecido : " + eRV.getRetornoTipo());


                    return;

                }

            } else {

                // System.out.println(" OBJ NAO PRIMITIVO :: " + eRV.getRetornoTipo());

                if (eRV.getIsNulo()) {

                    AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", "null", "ID");
                    Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
                    mASTExecute.init(mExecute);

                } else {

                    String eNomeRef = "{REF}::" + mRunTime.getHeap().getVECTORID();

                    Escopo gEscopo = new Escopo(mRunTime, EachEscopo);
                    EachEscopo.criarDefinicaoStruct(eNomeRef, eRV.getRetornoTipo(), eRV.getConteudo());

                    // System.out.println(" -->> " + eRV.getConteudo());

                    AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", eNomeRef, "ID");

                    //System.out.println(mExecute.ImprimirArvoreDeInstrucoes());

                    Run_Execute mASTExecute = new Run_Execute(mRunTime, gEscopo);
                    mASTExecute.init(mExecute);


                }

                if (mRunTime.getErros().size() > 0) {
                    return;
                }
            }


            if (mRunTime.getErros().size() > 0) {
                return;
            }

            //System.out.println(" OBJ :: " + eRV.getRetornoTipo());
            eV += 1;
        }

        if (mRunTime.getErros().size() > 0) {
            return;
        }


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        eRunValue.setNulo( mItem.getNulo());
        eRunValue.setPrimitivo( mItem.getPrimitivo());
        eRunValue.setConteudo( mItem.getValor(mRunTime, mEscopo));
        eRunValue.setRetornoTipo(mItem.getTipo());

    }


}
