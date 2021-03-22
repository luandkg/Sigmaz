package Sigmaz.S05_Executor.Runners;

import Sigmaz.S01_Compilador.Orquestrantes;
import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Escopos.Run_External;
import Sigmaz.S05_Executor.Indexador.Index_Function;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.RunTime;

import java.util.ArrayList;

import Sigmaz.S08_Utilitarios.AST;

public class Run_Context {

    private RunTime mRunTime;
    private String mLocal;

    public Run_Context(RunTime eRunTime) {

        mRunTime = eRunTime;
        mLocal = "Run_Context";

    }



    public AST getPacote(String eNome) {
        AST ret = null;

        boolean mEnc = false;

        for (AST ASTC : mRunTime.getGlobalPackages()) {
            if (ASTC.mesmoNome(eNome)) {
                ret = ASTC;
                mEnc = true;
                break;
            }
        }

        if (!mEnc) {
            mRunTime.errar(mLocal, "Pacote nao encontrado : " + eNome);
        }
        return ret;
    }

    public ArrayList<AST> getStructsContexto(Escopo mEscopo) {
        ArrayList<AST> ret = new ArrayList<AST>();

        for (AST eAST : mEscopo.getGuardadosCompleto()) {
            if (eAST.mesmoTipo(Orquestrantes.COMPLEX)) {
                if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome(Orquestrantes.STRUCT)) {
                    ret.add(eAST);
                }
            }
        }

        for (String Referencia : mEscopo.getRefers()) {

            if (mRunTime.existePacote(Referencia)) {

                for (AST eAST : getPacote(Referencia).getASTS()) {
                    if (eAST.mesmoTipo(Orquestrantes.COMPLEX)) {

                        if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome(Orquestrantes.STRUCT)) {
                            ret.add(eAST);
                        }

                        //  System.out.println(" \t\t - " + eAST.getTipo() + " :  " + eAST.getNome());

                    }
                }

            } else {
                mRunTime.errar(mLocal, "PACKAGE  " + Referencia + " : Nao encontrado x2 !");
            }

        }


        return ret;
    }

    public ArrayList<AST> getModelsContexto(Escopo mEscopo) {
        ArrayList<AST> ret = new ArrayList<AST>();

        for (AST eAST : mEscopo.getGuardadosCompleto()) {
            if (eAST.mesmoTipo(Orquestrantes.COMPLEX)) {
                if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome(Orquestrantes.MODEL)) {
                    ret.add(eAST);
                }
            }
        }

        for (String Referencia : mEscopo.getRefers()) {

            if (mRunTime.existePacote(Referencia)) {

                for (AST eAST : getPacote(Referencia).getASTS()) {
                    if (eAST.mesmoTipo(Orquestrantes.COMPLEX)) {

                        if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome(Orquestrantes.MODEL)) {
                            ret.add(eAST);
                        }

                        //  System.out.println(" \t\t - " + eAST.getTipo() + " :  " + eAST.getNome());

                    }
                }

            } else {
                mRunTime.errar(mLocal, "PACKAGE  " + Referencia + " : Nao encontrado x2 !");
            }

        }


        return ret;
    }


    public ArrayList<AST> getStructsOuStagesContexto(Escopo mEscopo) {
        ArrayList<AST> ret = new ArrayList<AST>();

        for (AST eAST : mEscopo.getGuardadosCompleto()) {
            if (eAST.mesmoTipo(Orquestrantes.COMPLEX)) {
                if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome(Orquestrantes.STRUCT)) {
                    ret.add(eAST);
                } else if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome(Orquestrantes.STAGES)) {
                    ret.add(eAST);
                }
            }
        }

        for (String Referencia : mEscopo.getRefers()) {

            if (mRunTime.existePacote(Referencia)) {

                for (AST eAST : getPacote(Referencia).getASTS()) {
                    if (eAST.mesmoTipo(Orquestrantes.COMPLEX)) {

                        if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome(Orquestrantes.STRUCT)) {
                            ret.add(eAST);
                        } else if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome(Orquestrantes.STAGES)) {
                            ret.add(eAST);
                        }

                        //  System.out.println(" \t\t - " + eAST.getTipo() + " :  " + eAST.getNome());

                    }
                }

            } else {
                mRunTime.errar(mLocal, "PACKAGE  " + Referencia + " : Nao encontrado x2 !");
            }

        }


        return ret;
    }


    public ArrayList<AST> getTypesContexto(Escopo mEscopo) {
        ArrayList<AST> ret = new ArrayList<AST>();


        for (AST eAST : mEscopo.getGuardadosCompleto()) {
            if (eAST.mesmoTipo(Orquestrantes.COMPLEX)) {
                if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome(Orquestrantes.TYPE)) {
                    ret.add(eAST);
                }
            }
        }

        for (String Referencia : mEscopo.getRefers()) {

            if (mRunTime.existePacote(Referencia)) {

                for (AST eAST : getPacote(Referencia).getASTS()) {
                    if (eAST.mesmoTipo(Orquestrantes.COMPLEX)) {

                        if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome(Orquestrantes.TYPE)) {
                            ret.add(eAST);

                        }

                        //  System.out.println(" \t\t - " + eAST.getTipo() + " :  " + eAST.getNome());

                    }
                }

            } else {
                mRunTime.errar(mLocal, "PACKAGE  " + Referencia + " : Nao encontrado x2 !");
            }

        }


        return ret;
    }

    public ArrayList<AST> getStagesContexto(Escopo mEscopo) {
        ArrayList<AST> ret = new ArrayList<AST>();


        for (AST eAST : mEscopo.getGuardadosCompleto()) {
            if (eAST.mesmoTipo(Orquestrantes.COMPLEX)) {
                if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome(Orquestrantes.STAGES)) {
                    ret.add(eAST);
                }
            }
        }


        for (String Referencia : mEscopo.getRefers()) {

            if (mRunTime.existePacote(Referencia)) {

                for (AST eAST : getPacote(Referencia).getASTS()) {
                    if (eAST.mesmoTipo(Orquestrantes.COMPLEX)) {

                        if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome(Orquestrantes.STAGES)) {
                            ret.add(eAST);

                        }

                        //  System.out.println(" \t\t - " + eAST.getTipo() + " :  " + eAST.getNome());

                    }
                }

            } else {
                mRunTime.errar(mLocal, "PACKAGE  " + Referencia + " : Nao encontrado x2 !");
            }

        }


        return ret;
    }

    public ArrayList<AST> getExternalsContexto(Escopo mEscopo) {
        ArrayList<AST> ret = new ArrayList<AST>();

        for (AST eAST : mEscopo.getGuardadosCompleto()) {
            if (eAST.mesmoTipo(Orquestrantes.COMPLEX)) {
                if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome(Orquestrantes.EXTERNAL)) {
                    ret.add(eAST);
                }
            }
        }

        for (String Referencia : mEscopo.getRefers()) {

            if (mRunTime.existePacote(Referencia)) {

                for (AST eAST : getPacote(Referencia).getASTS()) {
                    if (eAST.mesmoTipo(Orquestrantes.COMPLEX)) {

                        if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome(Orquestrantes.EXTERNAL)) {
                            ret.add(eAST);

                        }

                        //  System.out.println(" \t\t - " + eAST.getTipo() + " :  " + eAST.getNome());

                    }
                }

            } else {
                mRunTime.errar(mLocal, "PACKAGE  " + Referencia + " : Nao encontrado x2 !");
            }

        }


        return ret;
    }


    public ArrayList<AST> getCastsContexto(Escopo gEscopo) {


        ArrayList<String> mRefers = gEscopo.getRefers();

        ArrayList<AST> ret = new ArrayList<AST>();


        for (AST eAST : gEscopo.getGuardadosCompleto()) {
            if (eAST.mesmoTipo(Orquestrantes.CAST)) {
                ret.add(eAST);
            }
        }


        for (String Referencia : mRefers) {

            if (mRunTime.existePacote(Referencia)) {

                for (AST eAST : getPacote(Referencia).getASTS()) {
                    if (eAST.mesmoTipo(Orquestrantes.CAST)) {

                        ret.add(eAST);

                    }
                }

            } else {
                mRunTime.errar(mLocal, "PACKAGE  " + Referencia + " : Nao encontrado !");
            }

        }


        return ret;
    }

    public ArrayList<Index_Function> getOperatorsContexto(Escopo gEscopo) {


        ArrayList<String> mRefers = gEscopo.getRefers();

        ArrayList<Index_Function> mRet = new ArrayList<Index_Function>();


        for (Index_Function mIndex_Function : gEscopo.getOperationsCompleto()) {

            mRet.add(mIndex_Function);


        }


        for (AST mStruct : getStructsOuStagesContexto(gEscopo)) {
            for (AST mStructBody : mStruct.getBranch(Orquestrantes.BODY).getASTS()) {
                if (mStructBody.mesmoTipo(Orquestrantes.OPERATOR)) {

                    Index_Function mIndex_Function = new Index_Function(mRunTime, gEscopo, mStructBody);
                    mIndex_Function.resolverTipagem(gEscopo.getRefers());

                    mRet.add(mIndex_Function);
                }
            }
        }

        for (String Referencia : mRefers) {

            if (mRunTime.existePacote(Referencia)) {

                for (AST eAST : getPacote(Referencia).getASTS()) {
                    if (eAST.mesmoTipo(Orquestrantes.COMPLEX)) {


                        for (AST mStructBody : eAST.getBranch(Orquestrantes.BODY).getASTS()) {
                            if (mStructBody.mesmoTipo(Orquestrantes.OPERATOR)) {

                                Index_Function mIndex_Function = new Index_Function(mRunTime, gEscopo, mStructBody);
                                mIndex_Function.resolverTipagem(gEscopo.getRefers());
                                mRet.add(mIndex_Function);


                            }
                        }

                    }
                }

            } else {
                mRunTime.errar(mLocal, "PACKAGE  " + Referencia + " : Nao encontrado !");
            }

        }


        return mRet;
    }


    public ArrayList<Index_Function> getDirectorsContexto(Escopo gEscopo) {
        ArrayList<Index_Function> ret = new ArrayList<Index_Function>();


        ArrayList<String> mRefers = gEscopo.getRefers();

        for (Index_Function eAST : gEscopo.getDirectorsCompleto()) {
            ret.add(eAST);
        }


        for (AST mStruct : getStructsContexto(gEscopo)) {
            for (AST mStructBody : mStruct.getBranch(Orquestrantes.BODY).getASTS()) {
                if (mStructBody.mesmoTipo(Orquestrantes.DIRECTOR)) {

                    Index_Function m = new Index_Function(mRunTime, gEscopo, mStructBody);
                    m.resolverTipagem(gEscopo.getRefers());

                    ret.add(m);

                }
            }
        }

        for (String Referencia : mRefers) {

            if (mRunTime.existePacote(Referencia)) {

                for (AST eAST : getPacote(Referencia).getASTS()) {
                    if (eAST.mesmoTipo(Orquestrantes.COMPLEX)) {


                        for (AST mStructBody : eAST.getBranch(Orquestrantes.BODY).getASTS()) {
                            if (mStructBody.mesmoTipo(Orquestrantes.DIRECTOR)) {

                                Index_Function m = new Index_Function(mRunTime, gEscopo, mStructBody);
                                m.resolverTipagem(gEscopo.getRefers());
                                ret.add(m);

                            }
                        }

                    }
                }

            } else {
                mRunTime.errar(mLocal, "PACKAGE  " + Referencia + " : Nao encontrado !");
            }

        }


        return ret;
    }


    public ArrayList<Run_External> getRunExternContexto(ArrayList<String> mPacotes) {
        ArrayList<Run_External> ret = new ArrayList<Run_External>();

        for (Run_External eAST : mRunTime.getExternals().getExterns()) {
            if (eAST.getPacote().contentEquals("")) {
                ret.add(eAST);
            }
        }

        for (String Referencia : mPacotes) {

            if (mRunTime.existePacote(Referencia)) {

                for (Run_External rAST : mRunTime.getExternals().getExterns()) {
                    if (rAST.getPacote().contentEquals(Referencia)) {
                        ret.add(rAST);
                    }
                }
            } else {
                mRunTime.errar(mLocal, "PACKAGE  " + Referencia + " : Nao encontrado !");
            }

        }


        return ret;
    }

    public boolean existeStage(String eStage, Escopo mEscopo) {
        boolean enc = false;

        //  System.out.println(this.getNome() + " -> Stages : " + mRunTime.getStructsContexto(this.getRefers()).size());
        Run_Context mRun_Context = new Run_Context(mRunTime);

        for (AST mAST : mRun_Context.getStagesContexto(mEscopo)) {
            //  System.out.println(" -->> " + mAST.getNome());
            if (mAST.mesmoNome(eStage)) {
                enc = true;
                break;
            }

        }

        return enc;
    }

    public boolean existeStage_Type(String eStage, Escopo mEscopo) {
        boolean enc = false;

        //  System.out.println(this.getNome() + " -> Stages : " + mRunTime.getStructsContexto(this.getRefers()).size());
        Run_Context mRun_Context = new Run_Context(mRunTime);

        for (AST mAST : mRun_Context.getStagesContexto(mEscopo)) {
            //  System.out.println(" -->> " + mAST.getNome());

            for (AST sAST : mAST.getBranch(Orquestrantes.STAGES).getASTS()) {
                //  System.out.println("\t :: " + sAST.getNome());

                if (sAST.mesmoTipo(Orquestrantes.STAGE)) {
                    String tmp = mAST.getNome() + "::" + sAST.getNome();
                    if (tmp.contentEquals(eStage)) {
                        enc = true;
                        break;
                    }
                }


            }
        }

        return enc;
    }

    public Item obterStage(String eStage, Escopo mEscopo) {
        Item retStage = new Item("ret");

        retStage.setNulo(true,mRunTime);

        //  System.out.println(this.getNome() + " -> Stages : " + mRunTime.getStructsContexto(this.getRefers()).size());

        for (AST mAST : getStagesContexto(mEscopo)) {
            // System.out.println(" -->> " + mAST.getNome());


            for (AST sAST : mAST.getBranch(Orquestrantes.STAGES).getASTS()) {
                //  System.out.println("\t :: " + sAST.getNome());

                if (sAST.mesmoTipo(Orquestrantes.STAGE)) {
                    String tmp = mAST.getNome() + "::" + sAST.getNome();
                    if (tmp.contentEquals(eStage)) {

                        retStage.setNulo(false,mRunTime);
                        retStage.setValor(sAST.getValor(), mRunTime, null);
                        retStage.setTipo(mAST.getValor());
                        break;
                    }
                }

            }
        }

        return retStage;
    }


    public String getQualificador(String aNome, Escopo mEscopo) {


        String ret = "";
        String eNome = "";

        String ePacoteDefinido = "";


        if (aNome.contains("<>")) {

            int i = 0;
            int o = aNome.length();
            while (i < o) {
                String l = String.valueOf(aNome.charAt(i));
                if (l.contentEquals("<")) {
                    i += 1;

                    if (i < o) {
                        String l2 = String.valueOf(aNome.charAt(i));
                        if (l2.contentEquals(">")) {

                        } else {
                            ePacoteDefinido = "";
                        }
                    }
                    break;
                } else {
                    ePacoteDefinido += l;
                }
                i += 1;
            }

        }

        // System.out.println("Pacote :" + ePacoteDefinido);


        if (aNome.contains("<>")) {

            int i = 0;
            int o = aNome.length();

            while (i < o) {
                String l = String.valueOf(aNome.charAt(i));
                if (l.contentEquals(">")) {
                    i += 1;
                    break;
                }
                i += 1;
            }

            while (i < o) {
                String l = String.valueOf(aNome.charAt(i));
                if (l.contentEquals("<")) {
                    break;
                } else if (l.contentEquals(">")) {
                    break;
                } else {
                    eNome += l;
                }
                i += 1;
            }

        } else {

            int i = 0;
            int o = aNome.length();
            while (i < o) {
                String l = String.valueOf(aNome.charAt(i));
                if (l.contentEquals("<")) {
                    break;
                } else {
                    eNome += l;
                }
                i += 1;
            }

        }


        ret = "PRIMITIVE";


        ArrayList<AST> mCasts = new ArrayList<AST>();
        ArrayList<AST> mStages = new ArrayList<AST>();

        ArrayList<AST> mTipos = new ArrayList<AST>();
        ArrayList<AST> mStructs = new ArrayList<AST>();
        ArrayList<AST> mModels = new ArrayList<AST>();

        for (AST eAST : getCastsContexto(mEscopo)) {
            mCasts.add(eAST);
        }

        for (AST eAST : getStagesContexto(mEscopo)) {
            mStages.add(eAST);
        }


        for (AST eAST : getTypesContexto(mEscopo)) {
            mTipos.add(eAST);
        }

        for (AST eAST : getStructsContexto(mEscopo)) {
            mStructs.add(eAST);
        }

        for (AST eAST : getModelsContexto(mEscopo)) {
            mModels.add(eAST);
        }

        if (ePacoteDefinido.length() > 0) {

            incluirDoContexto(ePacoteDefinido, mCasts, mTipos, mStructs);

        }

        boolean enc = false;

        if (!enc) {
            for (AST eAST : mCasts) {
                if (eAST.mesmoNome(eNome)) {
                    ret = "CAST";
                    enc = true;
                    break;
                }
            }
        }

        if (!enc) {

            for (AST eAST : mStages) {
                if (eAST.mesmoNome(eNome)) {
                    ret = "STAGES";
                    enc = true;

                    break;
                }
            }
        }


        if (!enc) {

            for (AST eAST : mTipos) {
                if (eAST.mesmoNome(eNome)) {
                    ret = "TYPE";
                    enc = true;

                    break;
                }
            }
        }


        if (!enc) {

            for (AST eAST : mStructs) {

                if (eAST.mesmoNome(eNome)) {
                    ret =Orquestrantes.STRUCT;
                    enc = true;

                    break;
                }

            }
        }

        if (!enc) {

            for (AST eAST : mModels) {

                if (eAST.mesmoNome(eNome)) {
                    ret =Orquestrantes.STRUCT;
                    enc = true;

                    break;
                }

            }
        }

        return ret;
    }

    public boolean getQualificadorIsStruct(String aNome, Escopo mEscopo) {
        return getQualificador(aNome, mEscopo).contentEquals(Orquestrantes.STRUCT);
    }

    public void incluirDoContexto(String eRefer, ArrayList<AST> mCasts, ArrayList<AST> mTipos, ArrayList<AST> Structs) {


        if (mRunTime.existePacote(eRefer)) {

            for (AST eAST : getPacote(eRefer).getASTS()) {
                if (eAST.mesmoTipo(Orquestrantes.COMPLEX)) {

                    if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome(Orquestrantes.STRUCT)) {
                        Structs.add(eAST);
                    } else if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome(Orquestrantes.TYPE)) {
                        mTipos.add(eAST);
                    }

                } else if (eAST.mesmoTipo("CAST")) {
                    mCasts.add(eAST);
                }
            }

        } else {
            mRunTime.errar(mLocal, "PACKAGE  " + eRefer + " : Nao encontrado x2 !");
        }


    }





    public ArrayList<AST> getDefinidos(Escopo mEscopo) {

        ArrayList<AST> ret = new ArrayList<AST>();

        for (AST eAST : mEscopo.getGuardadosCompleto()) {
            if (eAST.mesmoTipo(Orquestrantes.COMPLEX)) {
                if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome(Orquestrantes.STRUCT)) {
                    ret.add(eAST);
                } else if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome("STAGES")) {
                    ret.add(eAST);
                } else if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome("TYPE")) {
                    ret.add(eAST);
                } else if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome("MODEL")) {
                    ret.add(eAST);
                }
            }
        }

        for (String Referencia : mEscopo.getRefers()) {

            if (mRunTime.existePacote(Referencia)) {

                for (AST eAST : getPacote(Referencia).getASTS()) {
                    if (eAST.mesmoTipo(Orquestrantes.COMPLEX)) {

                        if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome(Orquestrantes.STRUCT)) {
                            ret.add(eAST);
                        } else if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome("STAGES")) {
                            ret.add(eAST);
                        } else if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome("TYPE")) {
                            ret.add(eAST);
                        } else if (eAST.getBranch(Orquestrantes.EXTENDED).mesmoNome("MODEL")) {
                            ret.add(eAST);
                        }

                    }
                }

            } else {
                mRunTime.errar(mLocal, "PACKAGE  " + Referencia + " : Nao encontrado x2 !");
            }

        }


        return ret;
    }


}
