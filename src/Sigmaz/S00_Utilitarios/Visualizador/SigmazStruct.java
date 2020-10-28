package Sigmaz.S00_Utilitarios.Visualizador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco_Function;
import Sigmaz.S07_Executor.Indexador.Index_Function;

import java.util.ArrayList;

public class SigmazStruct {


    private AST mAST;
    private Organizador mOrganizador;

    public SigmazStruct(AST eAST) {

        mAST = eAST;
        mOrganizador = new Organizador();

    }

    public String getNome() {
        return mAST.getNome();
    }


    public boolean mesmoNome(String eNome) {
        return getNome().contentEquals(eNome);
    }


    public boolean isGenerica() {
        boolean ret = false;

        if (mAST.getBranch("GENERIC").mesmoNome("TRUE")) {
            ret = true;
        }

        return ret;
    }

    public String getCompletude() {
        String eForma = "COMPLETA";

        if (mAST.getBranch("WITH").mesmoValor("TRUE")) {
            eForma = "INCOMPLETA";
        }

        return eForma;
    }

    public boolean isCompleta() {
        return mAST.getBranch("WITH").mesmoValor("FALSE");
    }

    public boolean isIncompleta() {
        return mAST.getBranch("WITH").mesmoValor("TRUE");
    }

    public boolean precisaHeranca() {
        return mAST.getBranch("WITH").mesmoValor("TRUE");
    }

    public String getBase() {
        return mAST.getBranch("WITH").getNome();
    }


    public ArrayList<SigmazOperator> getOperadores() {

        ArrayList<SigmazOperator> mRet = new ArrayList<SigmazOperator>();

        for (AST eAST : mAST.getBranch("BODY").getASTS()) {
            if (eAST.mesmoTipo("OPERATOR")) {
                mRet.add(new SigmazOperator(eAST));
            }

        }


        return mRet;
    }

    public ArrayList<SigmazDirector> getDiretores() {

        ArrayList<SigmazDirector> mRet = new ArrayList<SigmazDirector>();

        for (AST eAST : mAST.getBranch("BODY").getASTS()) {

            if (eAST.mesmoTipo("DIRECTOR")) {
                mRet.add(new SigmazDirector(eAST));
            }
        }


        return mRet;
    }

    public int getContagem() {
        int r = 1;

        for (AST Sub2 : mAST.getBranch("INITS").getASTS()) {
            if (Sub2.mesmoNome(getNome())) {
                r += 1;
            }
        }

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("ACTION")) {
                r += 1;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                r += 1;
            } else if (Sub2.mesmoTipo("OPERATOR")) {
                r += 1;
            } else if (Sub2.mesmoTipo("DIRECTOR")) {
                r += 1;
            } else if (Sub2.mesmoTipo("DEFINE")) {
                r += 1;
            } else if (Sub2.mesmoTipo("MOCKIZ")) {
                r += 1;
            }
        }

        return r;
    }


    public int getSeparadores() {

        int contador = 0;

        if (temInits()) {
            contador += 1;
        }

        if (temGenericos()) {
            contador += 1;
        }

        if (temHeranca()) {
            contador += 1;
        }


        if (temDefines_Internos()) {
            contador += 1;
        }

        if (temDefines_Externos()) {
            contador += 1;
        }

        if (temActions_Internos()) {
            contador += 1;
        }

        if (temActions_Externos()) {
            contador += 1;
        }

        if (temDirectorsOuOperators()) {
            contador += 1;
        }

        if (temGettersOuSetters()) {
            contador += 1;
        }

        return contador;

    }


    public boolean temModelo() {
        return mAST.getBranch("MODEL").mesmoValor("TRUE");
    }

    public String getModelo() {
        return mAST.getBranch("MODEL").getNome();
    }

    public boolean temHeranca() {
        return mAST.getBranch("WITH").mesmoValor("TRUE");
    }

    public ArrayList<String> getBases() {

        ArrayList<String> mLista = new ArrayList<String>();

        for (AST Sub2 : mAST.getBranch("BASES").getASTS()) {
            mLista.add(Sub2.getNome());
        }
        return mLista;
    }

    public boolean temGenericos() {
        return mAST.getBranch("GENERIC").mesmoNome("TRUE");
    }


    public ArrayList<String> getGenericos() {

        ArrayList<String> mLista = new ArrayList<String>();

        for (AST Sub2 : mAST.getBranch("GENERIC").getASTS()) {
            mLista.add(Sub2.getNome());
        }
        return mLista;
    }

    public ArrayList<SigmazInit> getInits() {

        ArrayList<SigmazInit> mLista = new ArrayList<SigmazInit>();

        for (AST Sub2 : mAST.getBranch("INITS").getASTS()) {
            if (Sub2.mesmoNome(this.getNome())) {
                mLista.add(new SigmazInit(Sub2));
            }
        }
        return mLista;
    }


    public ArrayList<SigmazDefine> getDefines() {

        ArrayList<SigmazDefine> mLista = new ArrayList<SigmazDefine>();

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                mLista.add(new SigmazDefine(Sub2));
            }

        }

        return mLista;

    }


    public ArrayList<SigmazMockiz> getMockizes() {

        ArrayList<SigmazMockiz> mLista = new ArrayList<SigmazMockiz>();

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {
                mLista.add(new SigmazMockiz(Sub2));
            }

        }

        return mLista;

    }


    public ArrayList<SigmazAction> getAction() {
        ArrayList<SigmazAction> mLista = new ArrayList<SigmazAction>();

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("ACTION")) {
                SigmazAction mCorrente = new SigmazAction(Sub2);
                mLista.add(mCorrente);

            }
        }
        return mLista;
    }

    public ArrayList<SigmazFunction> getFunctions() {
        ArrayList<SigmazFunction> mLista = new ArrayList<SigmazFunction>();

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("FUNCTION")) {
                SigmazFunction mCorrente = new SigmazFunction(Sub2);
                mLista.add(mCorrente);

            }
        }
        return mLista;
    }


    public ArrayList<SigmazBlocoGetter> getBlocoGetters() {
        ArrayList<SigmazBlocoGetter> mLista = new ArrayList<SigmazBlocoGetter>();

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("GETTER")) {
                SigmazBlocoGetter mCorrente = new SigmazBlocoGetter(this, Sub2);
                mLista.add(mCorrente);

            }
        }
        return mLista;
    }

    public ArrayList<SigmazBlocoSetter> getBlocoSetters() {
        ArrayList<SigmazBlocoSetter> mLista = new ArrayList<SigmazBlocoSetter>();

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("SETTER")) {
                SigmazBlocoSetter mCorrente = new SigmazBlocoSetter(this, Sub2);
                mLista.add(mCorrente);

            }
        }
        return mLista;
    }


    public boolean temDefines_Internos() {
        return mOrganizador.temDefines_Internos(getDefines());

    }

    public boolean temMockizes_Internos() {
        return mOrganizador.temMockizes_Internos(getMockizes());

    }

    public boolean temDefinesOuMockizes_Internos() {
        return mOrganizador.temDefinesOuMockizes_Internos(getDefines(), getMockizes());
    }


    public boolean temDefines_Externos() {
        return mOrganizador.temDefines_Externos(getDefines());
    }

    public boolean temMockizes_Externos() {
        return mOrganizador.temMockizes_Externos(getMockizes());
    }

    public boolean temDefinesOuMockizes_Externos() {
        return mOrganizador.temDefinesOuMockizes_Externos(getDefines(), getMockizes());
    }


    public boolean temActions_Internos() {
        return mOrganizador.temActions_Internos(getAction());
    }

    public boolean temFunctions_Internos() {
        return mOrganizador.temFunctions_Internos(getFunctions());

    }

    public boolean temActionsOuFunctions_Internos() {
        return mOrganizador.temActionsOuFunctions_Internos(getAction(), getFunctions());
    }

    public boolean temActions_Externos() {
        return mOrganizador.temActions_Externos(getAction());
    }

    public boolean temFunctions_Externos() {
        return mOrganizador.temFunctions_Externos(getFunctions());

    }

    public boolean temActionsOuFunctions_Externos() {
        return mOrganizador.temActionsOuFunctions_Externos(getAction(), getFunctions());
    }


    public boolean temDirectorsOuOperators() {
        return mOrganizador.temDirectorsOuOperators(getDiretores(), getOperadores());
    }


    public boolean temGettersOuSetters() {
        return mOrganizador.temGettersOuSetters(getBlocoGetters(), getBlocoSetters());
    }

    public boolean temInits() {
        return this.getInits().size() > 0;
    }

    public boolean temCorpo() {

        boolean ret = false;

        if (mOrganizador.temDefines(getDefines())) {
            ret = true;
        } else if (mOrganizador.temMockizes(getMockizes())) {
            ret = true;
        } else if (mOrganizador.temActions(getAction())) {
            ret = true;
        } else if (mOrganizador.temFunctions(getFunctions())) {
            ret = true;
        }


        return ret;

    }
}
