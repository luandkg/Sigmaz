package Sigmaz.S07_Executor;

import Sigmaz.S07_Executor.Escopos.Run_Struct;
import Sigmaz.S07_Executor.Escopos.Run_Type;

import java.util.ArrayList;

public class Heap {

    private ArrayList<Run_Type> mTypes_Instances;
    private ArrayList<Run_Struct> mStructs_Instances;

    private long mHEAPID;
    private long mVECTORID;

    private RunTime mRunTime;
    private String mLocal;

    public Heap(RunTime eRunTime) {

        mStructs_Instances = new ArrayList<Run_Struct>();
        mTypes_Instances = new ArrayList<Run_Type>();

        mHEAPID = 0;
        mVECTORID = 0;

        mLocal = "Heap";
        mRunTime = eRunTime;
    }

    public void limpar() {

        mHEAPID = 0;
        mVECTORID = 0;

        mStructs_Instances.clear();
        mTypes_Instances.clear();

    }


    public long getHEAPID() {
        mHEAPID += 1;
        return mHEAPID;
    }

    public long getVECTORID() {
        mVECTORID += 1;
        return mVECTORID;
    }


    public ArrayList<Run_Struct> getStructs_Instances() {
        return mStructs_Instances;
    }

    public ArrayList<Run_Type> getTypes_Instances() {
        return mTypes_Instances;
    }


    public void adicionarStruct(Run_Struct eEscopo) {
        mStructs_Instances.add(eEscopo);
    }

    public void adicionarType(Run_Type eEscopo) {
        mTypes_Instances.add(eEscopo);
    }


    public void aumentar(String eNome) {

        boolean enc = false;


        if (eNome == null) {
            return;
        }

        if (!enc) {
            for (Run_Struct mRun_Struct : mStructs_Instances) {
                if (mRun_Struct.mesmoNome(eNome)) {
                    mRun_Struct.refAumentar();

                  //  System.out.println("GC Aumentando :: " + eNome);

                    enc = true;
                    break;
                }
            }
        }

        if (!enc) {
            for (Run_Type mRun_Struct : mTypes_Instances) {
                if (mRun_Struct.mesmoNome(eNome)) {
                    mRun_Struct.refAumentar();

                 //   System.out.println("GC Aumentando :: " + eNome);

                    enc = true;
                    break;
                }
            }
        }

    }

    public void reduzir(String eNome) {

        boolean enc = false;

        if (eNome == null) {
            return;
        }

        if (!enc) {
            for (Run_Struct mRun_Struct : mStructs_Instances) {
                if (mRun_Struct.mesmoNome(eNome)) {
                    mRun_Struct.refDiminuir();

                    if (mRun_Struct.getRefs() == 0) {
                        mStructs_Instances.remove(mRun_Struct);
                      //  System.out.println("GC Coletando Lixo :: " + eNome);
                    }else{

                       // System.out.println("GC Reduzindo :: " + eNome);


                    }

                    enc = true;
                    break;
                }
            }
        }

        if (!enc) {
            for (Run_Type mRun_Struct : mTypes_Instances) {
                if (mRun_Struct.mesmoNome(eNome)) {
                    mRun_Struct.refDiminuir();

                    if (mRun_Struct.getRefs() == 0) {
                        mTypes_Instances.remove(mRun_Struct);
                      //  System.out.println("GC Coletando Lixo :: " + eNome);

                    }else{
                   //    System.out.println("GC Reduzindo :: " + eNome);

                    }

                    enc = true;
                    break;
                }
            }
        }

    }

    public void removerStruct(String eNome) {

        for (Run_Struct mRun_Struct : mStructs_Instances) {
            if (mRun_Struct.mesmoNome(eNome)) {
                mStructs_Instances.remove(mRun_Struct);
                break;
            }
        }

    }

    public void removerType(String eNome) {

        for (Run_Type mRun_Struct : mTypes_Instances) {
            if (mRun_Struct.mesmoNome(eNome)) {
                mTypes_Instances.remove(mRun_Struct);
                //  System.out.println("Removendo Object Type : " + eNome);
                break;
            }
        }

    }


    public boolean existeStruct(String eNome) {
        boolean ret = false;

        for (Run_Struct mRun_Struct : mStructs_Instances) {
            if (mRun_Struct.mesmoNome(eNome)) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    public boolean existeType(String eNome) {
        boolean ret = false;

        for (Run_Type mRun_Struct : mTypes_Instances) {
            if (mRun_Struct.mesmoNome(eNome)) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    public Run_Struct getRun_Struct(String eNome) {

        Run_Struct mRet = null;
        boolean enc = false;

        for (Run_Struct mRun_Struct : mStructs_Instances) {
            if (mRun_Struct.mesmoNome(eNome)) {
                mRet = mRun_Struct;
                enc = true;
                break;
            }
        }

        if (!enc) {
            if (eNome.contentEquals("")) {
                mRunTime.errar(mLocal, "Nao foi possivel encontrar a struct - PONTEIRO NULO " + eNome);
            } else {
                mRunTime.errar(mLocal, "Nao foi possivel encontrar a struct : " + eNome);
            }
        }

        return mRet;

    }

    public Run_Type getRun_Type(String eNome) {

        Run_Type mRet = null;
        boolean enc = false;

        for (Run_Type mRun_Struct : mTypes_Instances) {
            if (mRun_Struct.mesmoNome(eNome)) {
                mRet = mRun_Struct;
                enc = true;
                break;
            }
        }

        if (!enc) {
            if (eNome.contentEquals("")) {
                mRunTime.errar(mLocal, "Nao foi possivel encontrar a type - PONTEIRO NULO " + eNome);
            } else {
                mRunTime.errar(mLocal, "Nao foi possivel encontrar a type : " + eNome);
            }
        }


        return mRet;

    }


    public void ListarInstancias() {

        System.out.println(" ######################### GLOBAL - INSTANCES ############################ ");

        for (Run_Type rt : getTypes_Instances()) {

            System.out.println("\t - TYPE :: " + rt.getNome() + " -->> " + rt.getRefs());

        }

        for (Run_Struct rs : getStructs_Instances()) {

            System.out.println("\t - STRUCT :: " + rs.getNome() + " -->> " + rs.getRefs());

        }


        System.out.println(" ######################### ##### ############################ ");


    }


    public void limparEscopo(Escopo mEscopo){

        // System.out.println("Saindo do Escopo : " + mEscopo.getNome());

        //  mRunTime.getHeap().ListarInstancias();

        //System.out.println("Aplicar Reducao");


        for (Item eItem : mEscopo.getStacks()) {

            //  System.out.println("\t - " + mEscopo.getNome() + " --- " + eItem.getNome());

            if (eItem.getTemValor()) {
                if (eItem.getIsEstrutura()) {
                    mRunTime.getHeap().reduzir(eItem.getValor(mRunTime, mEscopo));
                }
            }
        }

        // mRunTime.getHeap().ListarInstancias();

    }

}
