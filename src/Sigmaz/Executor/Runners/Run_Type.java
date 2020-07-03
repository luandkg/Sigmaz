package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Alterador;
import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_Type {

    private RunTime mRunTime;
    private Escopo mEscopo;

    private String mNome;

    private String mStructNome;
    private AST mStructCorpo;
    private String mTipoCompleto;

    public Run_Type(RunTime eRunTime) {

        mRunTime = eRunTime;
        mTipoCompleto = "";

    }

    public boolean mesmoNome(String eNome) {
        return mNome.contentEquals(eNome);
    }

    public void setNome(String eNome) {
        mNome = eNome;
    }

    public String getNome() {
        return mNome;
    }

    public String getTypeNome() {
        return mStructNome;
    }

    public Escopo getEscopo() {
        return mEscopo;
    }

    public String getTipoCompleto() {
        return mTipoCompleto;
    }

    public void init(String eNome, AST ASTCorrente, Escopo BuscadorDeArgumentos) {

        mEscopo = new Escopo(mRunTime, BuscadorDeArgumentos);
        mEscopo.setNome(eNome);
        mEscopo.setEstrutura(true);

        mStructCorpo = null;

        mNome = ASTCorrente.getNome();
        mStructNome = ASTCorrente.getNome();

        mEscopo.setNome(eNome);


        AST mAST_Struct = null;


        boolean enc = false;


        for (AST ASTC : mRunTime.getSigmaz().getASTS()) {


            if (ASTC.mesmoTipo("FUNCTION")) {
                mEscopo.guardar(ASTC);
            } else if (ASTC.mesmoTipo("ACTION")) {
                mEscopo.guardar(ASTC);
            } else if (ASTC.mesmoTipo("OPERATION")) {
                mEscopo.guardar(ASTC);
            } else if (ASTC.mesmoTipo("CAST")) {
                mEscopo.guardar(ASTC);
            }

        }

        for (AST ASTC : mRunTime.getGlobalTypes()) {
            if (ASTC.mesmoTipo("TYPE")) {
                if (ASTC.mesmoNome(mStructNome)) {

                    enc = true;
                    mAST_Struct = ASTC;
                    mTipoCompleto = mStructNome;
                }
            }
        }



        if (!enc) {
            mRunTime.getErros().add("Type " + mStructNome + " : Nao Encontrada !");
            return;
        }

        ArrayList<String> mAlocados = new ArrayList<>();

        for (AST ASTC : mAST_Struct.getASTS()) {


            if (ASTC.mesmoTipo("DEFINE")) {

                mAlocados.add(ASTC.getNome());

                Run_Def mAST = new Run_Def(mRunTime, mEscopo);
                mAST.init(ASTC);

                mEscopo.guardarStruct(ASTC);


            } else if (ASTC.mesmoTipo("MOCKIZ")) {

                mAlocados.add(ASTC.getNome());

                Run_Moc mAST = new Run_Moc(mRunTime, mEscopo);
                mAST.init(ASTC);

                mEscopo.guardarStruct(ASTC);


            }

        }

        for (AST mDentro : ASTCorrente.getBranch("STARTED").getASTS()) {

            if (!mAlocados.contains(mDentro.getBranch("SETTABLE").getNome())) {
                mRunTime.getErros().add(mStructNome + "." + mDentro.getBranch("SETTABLE").getNome() + " : Membro nao existe !");
                return;
            }

            Run_Apply mRUA = new Run_Apply(mRunTime, mEscopo);
            mRUA.init(mDentro);

        }

        //mEscopo.ListarAll();


    }


    public Item init_Object(AST ASTCorrente, Escopo BuscadorDeVariaveis, String eRetorne) {

        if (BuscadorDeVariaveis == null) {
            mRunTime.getErros().add(mStructNome + "." + ASTCorrente.getNome() + " : Membro nao encontrado !");
            return null;
        }

        boolean enc = false;
        Item mRet = null;

        for (Item mItem : mEscopo.getStacks()) {

            if (mItem.getNome().contentEquals(ASTCorrente.getNome())) {
                mRet = mItem;
                enc = true;
                break;
            }

        }

        if (!enc) {
            mRunTime.getErros().add(mStructNome + "." + ASTCorrente.getNome() + " : Membro nao encontrado !");
        }

        return mRet;
    }

}
