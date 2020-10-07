package Sigmaz.S05_PosProcessamento.Pronoco;

import java.util.ArrayList;

public class Pronoco {

    private String mNome;

    private boolean mTemSuperior;
    private Pronoco mSuperior;


    private ArrayList<Pronoco_Pacote> mPacotes;

    private ArrayList<Pronoco_Def> mDefines;
    private ArrayList<Pronoco_Moc> mMockizes;

    private ArrayList<Pronoco_Let> mLet;
    private ArrayList<Pronoco_Voz> mVoz;
    private ArrayList<Pronoco_Mut> mMut;


    private ArrayList<String> mTipos_Primitivo;
    private ArrayList<String> mTipos_Cast;
    private ArrayList<String> mTipos_Stage;
    private ArrayList<String> mTipos_Struct;
    private ArrayList<String> mTipos_Type;
    private ArrayList<String> mTipos_Model;

    private ArrayList<String> mTipos_Genericos;

    private ArrayList<Pronoco_Action> mActions;
    private ArrayList<Pronoco_Function> mFunctions;

    private ArrayList<Pronoco_Auto> mAutos;
    private ArrayList<Pronoco_Functor> mFunctores;

    private ArrayList<Pronoco_Cast> mCasts;

    private ArrayList<Pronoco_Struct> mStructs;
    private ArrayList<Pronoco_Type> mTypes;

    private ArrayList<Pronoco_Stages> mStages;
    private ArrayList<Pronoco_Extern> mExterns;


    public Pronoco(String eNome) {

        mTemSuperior = false;
        mSuperior = null;

        mNome = eNome;

        mPacotes = new ArrayList<Pronoco_Pacote>();


        mDefines = new ArrayList<Pronoco_Def>();
        mMockizes = new ArrayList<Pronoco_Moc>();

        mLet = new ArrayList<Pronoco_Let>();
        mVoz = new ArrayList<Pronoco_Voz>();
        mMut = new ArrayList<Pronoco_Mut>();

        mActions = new ArrayList<Pronoco_Action>();
        mFunctions = new ArrayList<Pronoco_Function>();

        mAutos = new ArrayList<Pronoco_Auto>();
        mFunctores = new ArrayList<Pronoco_Functor>();

        mTipos_Primitivo = new ArrayList<String>();
        mTipos_Cast = new ArrayList<String>();
        mTipos_Stage = new ArrayList<String>();
        mTipos_Struct = new ArrayList<String>();
        mTipos_Type = new ArrayList<String>();
        mTipos_Model = new ArrayList<String>();

        mTipos_Genericos = new ArrayList<String>();

        mCasts = new ArrayList<Pronoco_Cast>();

        mStructs = new ArrayList<Pronoco_Struct>();
        mTypes = new ArrayList<Pronoco_Type>();

        mStages = new ArrayList<Pronoco_Stages>();
        mExterns = new ArrayList<Pronoco_Extern>();


    }

    public void setSuperior(Pronoco eSuperior) {
        mTemSuperior = true;
        mSuperior = eSuperior;
    }

    public boolean temSuperior() {
        return mTemSuperior;
    }

    public Pronoco getSuperior() {
        return mSuperior;
    }


    public String getNome() {
        return mNome;
    }


    public void adicionarPacote(Pronoco_Pacote mPacote) {


        mPacotes.add(mPacote);

        for (Pronoco_Cast ps : mPacote.getCasts()) {
            adicionarCast(ps);
        }
        for (Pronoco_Struct ps : mPacote.getStructs()) {
            adicionarStruct(ps);
        }
        for (Pronoco_Type ps : mPacote.getTypes()) {
            adicionarType(ps);
        }
        for (Pronoco_Stages ps : mPacote.getStages()) {
            adicionarStages(ps);
        }
        for (Pronoco_Extern ps : mPacote.getExterns()) {
            adicionarExtern(ps);
        }

    }

    public boolean existePacote(String e) {

        boolean enc = false;

        for (Pronoco_Pacote mPacote : mPacotes) {
            if (mPacote.getNome().contentEquals(e)) {
                enc = true;
                break;
            }
        }

        return enc;

    }


    public ArrayList<String> copiar(ArrayList<String> eOrigem) {

        ArrayList<String> copia = new ArrayList<String>();
        for (String e : eOrigem) {
            copia.add(e);
        }

        return copia;
    }

    public ArrayList<Pronoco_Stages> copiarStages(ArrayList<Pronoco_Stages> eOrigem) {

        ArrayList<Pronoco_Stages> copia = new ArrayList<Pronoco_Stages>();
        for (Pronoco_Stages e : eOrigem) {
            copia.add(e);
        }

        return copia;
    }


    public boolean existeStage(String eStage) {

        boolean ret = false;
        for (Pronoco_Stages es : getStages()) {
            if (es.getNome().contentEquals(eStage)) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    public Pronoco_Stages getStage(String eStage) {

        Pronoco_Stages ret = null;
        for (Pronoco_Stages es : getStages()) {
            if (es.getNome().contentEquals(eStage)) {
                ret = es;
                break;
            }
        }
        return ret;
    }


    public void adicionarPrimitivo(String ePrimitivo) {
        mTipos_Primitivo.add(ePrimitivo);
    }

    public void adicionarCast(String ePrimitivo) {
        mTipos_Cast.add(ePrimitivo);
    }


    public void adicionarStage(String ePrimitivo) {
        mTipos_Stage.add(ePrimitivo);
    }


    public void adicionarStruct(String ePrimitivo) {
        mTipos_Struct.add(ePrimitivo);
    }

    public void adicionarType(String ePrimitivo) {
        mTipos_Type.add(ePrimitivo);
    }

    public void adicionarModel(String ePrimitivo) {
        mTipos_Model.add(ePrimitivo);
    }

    public void adicionarGenerico(String ePrimitivo) {
        mTipos_Genericos.add(ePrimitivo);
    }


    public void adicionarDefine(Pronoco_Def e) {
        mDefines.add(e);
    }

    public void adicionarMockiz(Pronoco_Moc e) {
        mMockizes.add(e);
    }

    public void adicionarLet(Pronoco_Let e) {
        mLet.add(e);
    }

    public void adicionarVoz(Pronoco_Voz e) {
        mVoz.add(e);
    }

    public void adicionarMut(Pronoco_Mut e) {
        mMut.add(e);
    }


    public void adicionarAction(Pronoco_Action e) {
        mActions.add(e);
    }

    public void adicionarFunction(Pronoco_Function e) {
        mFunctions.add(e);
    }

    public void adicionarAuto(Pronoco_Auto e) {
        mAutos.add(e);
    }

    public void adicionarFunctor(Pronoco_Functor e) {
        mFunctores.add(e);
    }


    public void adicionarExtern(Pronoco_Extern e) {
        mExterns.add(e);
    }

    public void adicionarCast(Pronoco_Cast e) {
        mCasts.add(e);
    }

    public void adicionarStruct(Pronoco_Struct e) {
        mStructs.add(e);
    }

    public void adicionarType(Pronoco_Type e) {
        mTypes.add(e);
    }
    public void adicionarStages(Pronoco_Stages e) {
        mStages.add(e);
    }

    public void adicionarTypes(Pronoco_Type e) {
        mTypes.add(e);
    }


    public ArrayList<String> getTipos_Primitivo() {
        ArrayList<String> ret = new ArrayList<String>();

        for (String e : mTipos_Primitivo) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (String e : mSuperior.getTipos_Primitivo()) {
                ret.add(e);
            }
        }

        return ret;
    }

    public ArrayList<String> getTipos_Cast() {
        ArrayList<String> ret = new ArrayList<String>();

        for (String e : mTipos_Cast) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (String e : mSuperior.getTipos_Cast()) {
                ret.add(e);
            }
        }

        return ret;
    }

    public ArrayList<String> getTipos_Stage() {
        ArrayList<String> ret = new ArrayList<String>();

        for (String e : mTipos_Stage) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (String e : mSuperior.getTipos_Stage()) {
                ret.add(e);
            }
        }

        return ret;
    }

    public ArrayList<String> getTipos_Struct() {
        ArrayList<String> ret = new ArrayList<String>();

        for (String e : mTipos_Struct) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (String e : mSuperior.getTipos_Struct()) {
                ret.add(e);
            }
        }

        return ret;
    }

    public ArrayList<String> getTipos_Type() {
        ArrayList<String> ret = new ArrayList<String>();

        for (String e : mTipos_Type) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (String e : mSuperior.getTipos_Type()) {
                ret.add(e);
            }
        }

        return ret;
    }


    public ArrayList<String> getTipos_Model() {
        ArrayList<String> ret = new ArrayList<String>();

        for (String e : mTipos_Model) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (String e : mSuperior.getTipos_Model()) {
                ret.add(e);
            }
        }

        return ret;
    }

    public ArrayList<String> getTipos_Genericos() {
        ArrayList<String> ret = new ArrayList<String>();

        for (String e : mTipos_Genericos) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (String e : mSuperior.getTipos_Genericos()) {
                ret.add(e);
            }
        }

        return ret;
    }


    public ArrayList<Pronoco_Action> getActions() {
        ArrayList<Pronoco_Action> ret = new ArrayList<Pronoco_Action>();

        for (Pronoco_Action e : mActions) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (Pronoco_Action e : mSuperior.getActions()) {
                ret.add(e);
            }
        }

        return ret;
    }


    public ArrayList<Pronoco_Function> getFunctions() {
        ArrayList<Pronoco_Function> ret = new ArrayList<Pronoco_Function>();

        for (Pronoco_Function e : mFunctions) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (Pronoco_Function e : mSuperior.getFunctions()) {
                ret.add(e);
            }
        }

        return ret;
    }

    public ArrayList<Pronoco_Auto> getAutos() {
        ArrayList<Pronoco_Auto> ret = new ArrayList<Pronoco_Auto>();

        for (Pronoco_Auto e : mAutos) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (Pronoco_Auto e : mSuperior.getAutos()) {
                ret.add(e);
            }
        }

        return ret;
    }

    public ArrayList<Pronoco_Functor> getFunctores() {
        ArrayList<Pronoco_Functor> ret = new ArrayList<Pronoco_Functor>();

        for (Pronoco_Functor e : mFunctores) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (Pronoco_Functor e : mSuperior.getFunctores()) {
                ret.add(e);
            }
        }

        return ret;
    }


    public boolean existeFunction(String eFunction) {

        boolean ret = false;

        for (Pronoco_Function e : getFunctions()) {
            if (e.getNome().contentEquals(eFunction)) {
                ret = true;
                break;
            }
        }

        return ret;
    }

    public boolean existeActionFunction(String eActionFunction) {

        boolean ret = false;

        for (Pronoco_Action e : getActions()) {
            if (e.getNome().contentEquals(eActionFunction)) {
                ret = true;
                break;
            }
        }

        if (false == false) {
            for (Pronoco_Function e : getFunctions()) {
                if (e.getNome().contentEquals(eActionFunction)) {
                    ret = true;
                    break;
                }
            }
        }


        return ret;
    }


    public boolean existeStruct(String eStruct) {

        boolean ret = false;

        for (Pronoco_Struct e : getStructs()) {
            if (e.getNome().contentEquals(eStruct)) {
                ret = true;
                break;
            }
        }

        return ret;
    }

    public boolean existeExtern(String eExtern) {

        boolean ret = false;

        for (Pronoco_Extern e : getExterns()) {
            if (e.getNome().contentEquals(eExtern)) {
                ret = true;
                break;
            }
        }

        return ret;
    }


    public boolean existeDefine(String eExtern) {

        boolean ret = false;

        for (Pronoco_Def e : getDefines()) {
            if (e.getNome().contentEquals(eExtern)) {
                ret = true;
                break;
            }
        }

        return ret;
    }

    public boolean existeType(String eType) {

        boolean ret = false;

        for (Pronoco_Type e : getTypes()) {
            if (e.getNome().contentEquals(eType)) {
                ret = true;
                break;
            }
        }

        return ret;
    }


    public boolean existeNesse(String eExtern) {

        boolean ret = false;

        for (Pronoco_Moc e : mMockizes) {
            if (e.getNome().contentEquals(eExtern)) {
                ret = true;
                break;
            }
        }

        if (ret == false) {
            for (Pronoco_Def e : mDefines) {
                if (e.getNome().contentEquals(eExtern)) {
                    ret = true;
                    break;
                }
            }
        }

        if (ret == false) {
            for (Pronoco_Let e : mLet) {
                if (e.getNome().contentEquals(eExtern)) {
                    ret = true;
                    break;
                }
            }
        }

        if (ret == false) {
            for (Pronoco_Voz e : mVoz) {
                if (e.getNome().contentEquals(eExtern)) {
                    ret = true;
                    break;
                }
            }
        }

        if (ret == false) {
            for (Pronoco_Mut e : mMut) {
                if (e.getNome().contentEquals(eExtern)) {
                    ret = true;
                    break;
                }
            }
        }

        return ret;
    }

    public ArrayList<String> getAteAqui() {

        ArrayList<String> mRet = new ArrayList<String>();

        for (Pronoco_Moc e : getMockizes()) {
            mRet.add(e.getNome());
        }

        for (Pronoco_Def e : getDefines()) {
            mRet.add(e.getNome());
        }

        for (Pronoco_Let e : getLet()) {
            mRet.add(e.getNome());
        }

        for (Pronoco_Voz e : getVoz()) {
            mRet.add(e.getNome());
        }

        for (Pronoco_Mut e : getMut()) {
            mRet.add(e.getNome());
        }

        return mRet;
    }


    public boolean existeAteAqui(String eExtern) {

        boolean ret = false;

        for (String e : getAteAqui()) {
            if (e.contentEquals(eExtern)) {
                ret = true;
                break;
            }
        }


        return ret;
    }


    public ArrayList<Pronoco_Def> getDefines() {
        ArrayList<Pronoco_Def> ret = new ArrayList<Pronoco_Def>();

        for (Pronoco_Def e : mDefines) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (Pronoco_Def e : mSuperior.getDefines()) {
                ret.add(e);
            }
        }

        return ret;
    }


    public ArrayList<Pronoco_Moc> getMockizes() {
        ArrayList<Pronoco_Moc> ret = new ArrayList<Pronoco_Moc>();

        for (Pronoco_Moc e : mMockizes) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (Pronoco_Moc e : mSuperior.getMockizes()) {
                ret.add(e);
            }
        }

        return ret;
    }


    public ArrayList<Pronoco_Let> getLet() {
        ArrayList<Pronoco_Let> ret = new ArrayList<Pronoco_Let>();

        for (Pronoco_Let e : mLet) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (Pronoco_Let e : mSuperior.getLet()) {
                ret.add(e);
            }
        }

        return ret;
    }


    public ArrayList<Pronoco_Voz> getVoz() {
        ArrayList<Pronoco_Voz> ret = new ArrayList<Pronoco_Voz>();

        for (Pronoco_Voz e : mVoz) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (Pronoco_Voz e : mSuperior.getVoz()) {
                ret.add(e);
            }
        }

        return ret;
    }


    public ArrayList<Pronoco_Mut> getMut() {
        ArrayList<Pronoco_Mut> ret = new ArrayList<Pronoco_Mut>();

        for (Pronoco_Mut e : mMut) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (Pronoco_Mut e : mSuperior.getMut()) {
                ret.add(e);
            }
        }

        return ret;
    }


    public ArrayList<Pronoco_Struct> getStructs() {
        ArrayList<Pronoco_Struct> ret = new ArrayList<Pronoco_Struct>();

        for (Pronoco_Struct e : mStructs) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (Pronoco_Struct e : mSuperior.getStructs()) {
                ret.add(e);
            }
        }

        return ret;
    }

    public ArrayList<Pronoco_Stages> getStages() {
        ArrayList<Pronoco_Stages> ret = new ArrayList<Pronoco_Stages>();

        for (Pronoco_Stages e : mStages) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (Pronoco_Stages e : mSuperior.getStages()) {
                ret.add(e);
            }
        }

        return ret;
    }

    public ArrayList<Pronoco_Type> getTypes() {
        ArrayList<Pronoco_Type> ret = new ArrayList<Pronoco_Type>();

        for (Pronoco_Type e : mTypes) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (Pronoco_Type e : mSuperior.getTypes()) {
                ret.add(e);
            }
        }

        return ret;
    }


    public ArrayList<Pronoco_Extern> getExterns() {
        ArrayList<Pronoco_Extern> ret = new ArrayList<Pronoco_Extern>();

        for (Pronoco_Extern e : mExterns) {
            ret.add(e);
        }
        if (mTemSuperior) {
            for (Pronoco_Extern e : mSuperior.getExterns()) {
                ret.add(e);
            }
        }

        return ret;
    }

    public String getRegressivo() {
        String ret = "";

        ret = this.getNome();

        if (mTemSuperior) {
            ret = mSuperior.getRegressivo() + " -> " + ret;
        }


        return ret;
    }


    public Pronoco_Extern getExtern(String eNome) {
        for (Pronoco_Extern pe : getExterns()) {
            if (pe.getNome().contentEquals(eNome)) {
                return pe;
            }
        }
        return null;
    }
}
