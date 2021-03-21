package Sigmaz.S08_Utilitarios.Visualizador;

import Sigmaz.S08_Utilitarios.AST;

import java.util.ArrayList;

public class SigmazModel {

    private AST mAST;

    public SigmazModel(AST eAST) {

        mAST = eAST;

    }

    public String getNome() {
        return mAST.getNome();
    }

    public boolean mesmoNome(String eNome) {
        return getNome().contentEquals(eNome);
    }

    public String getDefinicao() {
        return mAST.getNome();
    }


    public boolean temDefines() {

        boolean tem = false;

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("DEFINE")) {
                tem = true;
            }
        }

        return tem;

    }

    public boolean temMockizes() {

        boolean tem = false;

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("MOCKIZ")) {
                tem = true;
            }
        }

        return tem;

    }


    public boolean temDefinesMockizes() {

        boolean tem = false;

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("DEFINE")) {
                tem = true;
            } else if (Sub2.mesmoTipo("MOCKIZ")) {
                tem = true;
            }
        }

        return tem;

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




    public boolean temActionsFunctions() {

        boolean tem = false;

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("ACTION")) {
                tem = true;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                tem = true;
            }
        }

        return tem;

    }


    public boolean temActions() {

        boolean tem = false;

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("ACTION")) {
                tem = true;
            }
        }

        return tem;

    }

    public boolean temFunctions() {

        boolean tem = false;

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("FUNCTION")) {
                tem = true;
            }
        }

        return tem;

    }



    public ArrayList<SigmazAction> getActions() {

        ArrayList<SigmazAction> mLista = new ArrayList<SigmazAction>();

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("ACTION")) {
                mLista.add(new SigmazAction(Sub2));
            }
        }

        return mLista;
    }


    public ArrayList<SigmazFunction> getFunctions() {

        ArrayList<SigmazFunction> mLista = new ArrayList<SigmazFunction>();

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("FUNCTION")) {
                mLista.add(new SigmazFunction(Sub2));
            }
        }

        return mLista;
    }


    public boolean temGenericos() {
        boolean ret = false;
        if (mAST.getBranch("GENERIC").mesmoNome("TRUE")) {
            ret = true;
        }
        return ret;
    }

    public ArrayList<String> getGenericos() {

        ArrayList<String> mLista = new ArrayList<String>();

        if (mAST.getBranch("GENERIC").mesmoNome("TRUE")) {
            for (AST Sub2 : mAST.getBranch("GENERIC").getASTS()) {
                mLista.add(Sub2.getNome());
            }
        }

        return mLista;

    }

    public int getContagemGenericos() {
        int contador = 0;

        if (mAST.getBranch("GENERIC").mesmoNome("TRUE")) {
            for (AST Sub2 : mAST.getBranch("GENERIC").getASTS()) {
                contador += 1;
            }
        }

        return contador;
    }

    public int getContagemCorpo() {
        int contador = 0;

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("ACTION")) {
                contador += 1;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                contador += 1;
            } else if (Sub2.mesmoTipo("OPERATOR")) {
                contador += 1;
            }
        }

        return contador;

    }


    public int getContagem() {

        int contador = 0;

        if (mAST.getBranch("GENERIC").mesmoNome("TRUE")) {
            for (AST Sub2 : mAST.getBranch("GENERIC").getASTS()) {
                contador += 1;
            }
        }


        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("ACTION")) {
                contador += 1;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                contador += 1;
            } else if (Sub2.mesmoTipo("OPERATOR")) {
                contador += 1;
            }
        }


        return contador;
    }

}
