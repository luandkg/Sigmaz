package Sigmaz.S08_Utilitarios.Visualizador;

import Sigmaz.S08_Utilitarios.AST;

import java.util.ArrayList;

public class SigmazType {

    private AST mAST;

    public SigmazType(AST eAST) {

        mAST = eAST;

    }


    public boolean mesmoNome(String eNome) {
        return getNome().contentEquals(eNome);
    }


    public String getNome() {
        return mAST.getNome();
    }

    public String getCompletude() {

        String eForma = "COMPLETA";

        if (mAST.getBranch("EXTENDED").mesmoNome("TYPE_UNION")) {
            eForma = "INCOMPLETA";
        }

        return eForma;
    }

    public boolean precisaUnir() {

        boolean ePrecisa = false;

        if (mAST.getBranch("EXTENDED").mesmoNome("TYPE_UNION")) {
            ePrecisa = true;
        }

        return ePrecisa;
    }


    public int getContagem() {
        int r = 1;

        if (mAST.getBranch("GENERIC").mesmoNome("TRUE")) {

            for (AST Sub2 : mAST.getBranch("GENERIC").getASTS()) {
                r += 1;
            }

        }

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("DEFINE")) {
                r += 1;
            } else if (Sub2.mesmoTipo("MOCKIZ")) {
                r += 1;
            }
        }
        return r;
    }

    public boolean isGenerica() {
        return mAST.getBranch("GENERIC").mesmoNome("TRUE");
    }

    public ArrayList<String> getGenericos() {

        ArrayList<String> mLista = new ArrayList<String>();

        for (AST Sub2 : mAST.getBranch("GENERIC").getASTS()) {
            mLista.add(Sub2.getNome());
        }

        return mLista;

    }

    public boolean temBase() {
        return mAST.getBranch("BASES").getASTS().size() > 0;
    }

    public ArrayList<String> getBases() {

        ArrayList<String> mLista = new ArrayList<String>();

        for (AST Sub2 : mAST.getBranch("BASES").getASTS()) {

            String eConteudo = Sub2.getNome();

            if (Sub2.getBranch("GENERIC").getASTS().size() > 0) {

                eConteudo += " < ";

                for (AST iG : Sub2.getBranch("GENERIC").getASTS()) {
                    eConteudo += " " + iG.getNome();
                }

                eConteudo += " > ";

            }


            mLista.add(eConteudo);
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

}
