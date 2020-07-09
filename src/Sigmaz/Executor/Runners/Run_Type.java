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

        Run_Context mRun_Context = new Run_Context(mRunTime);

        for (AST ASTC : mRun_Context.getTypesContexto(BuscadorDeArgumentos.getRefers())) {
            if (ASTC.mesmoTipo("TYPE")) {
                if (ASTC.mesmoNome(mStructNome)) {


                    enc = true;
                    mStructCorpo = ASTC;
                    mTipoCompleto = mStructNome;


                }
            }
        }


        if (!enc) {
            mRunTime.getErros().add("Type " + mStructNome + " : Nao Encontrada !");
            return;
        }


        AST mStructGeneric = mStructCorpo.getBranch("GENERIC");


        AST init_Generic = ASTCorrente.getBranch("GENERIC");
        String structTipagem = "";
        int StructContagem = 0;

        for (AST ASTC : mStructGeneric.getASTS()) {
            structTipagem += "<" + getTipagem(ASTC) + ">";
            StructContagem += 1;
        }


        String initTipagem = "";
        int initContagem = 0;

        for (AST ASTC : init_Generic.getASTS()) {
            initTipagem += "<" + getTipagem(ASTC) + ">";
            initContagem += 1;
        }

        mTipoCompleto = mStructNome + initTipagem;


        if (init_Generic.mesmoNome("TRUE") && mStructGeneric.mesmoNome("TRUE")) {


            if (StructContagem == initContagem) {

                Alterador mAlterador = new Alterador();

                int i = 0;
                for (AST eSub : mStructGeneric.getASTS()) {

                    mAlterador.adicionar(eSub.getNome(), init_Generic.getASTS().get(i).copiar());

                    //   System.out.println("Alterando " + eSub.getNome() + " -> " + getTipagem( init_Generic.getASTS().get(i)) );
                    //   init_Generic.getBranch("TYPE").ImprimirArvoreDeInstrucoes();

                    i += 1;
                }

                mAlterador.alterar(mStructCorpo);

                //  System.out.println(mStructCorpo.ImprimirArvoreDeInstrucoes());

            } else {

                mRunTime.getErros().add("Type " + mStructNome + " : Tipos abstratos nao conferem !");

            }


        } else if (init_Generic.mesmoNome("FALSE") && mStructGeneric.mesmoNome("FALSE")) {

        } else if (init_Generic.mesmoNome("FALSE") && mStructGeneric.mesmoNome("TRUE")) {
            mRunTime.getErros().add("Type " + mStructNome + " : Precisa retornar uma Instancia Generica !");
        } else if (init_Generic.mesmoNome("TRUE") && mStructGeneric.mesmoNome("FALSE")) {


            if (init_Generic.getASTS().size() == 0) {

            } else {
                mRunTime.getErros().add("Type " + mStructNome + " : Nao e Generica !");
            }


        } else {
            mRunTime.getErros().add("Type " + mStructNome + " : Nao e Generica !");
        }


        if (mRunTime.getErros().size() > 0) {
            return;
        }


        ArrayList<String> mAlocados = new ArrayList<>();

        for (AST ASTC : mStructCorpo.getASTS()) {


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


    public String getTipagem(AST eAST) {

        String mTipagem = eAST.getNome();

        if (eAST.mesmoValor("GENERIC")) {

            for (AST eTipando : eAST.getASTS()) {
                mTipagem += "<" + getTipagem(eTipando) + ">";
            }

        }


        return mTipagem;

    }

}
