package Sigmaz.S00_Utilitarios.Visualizador;

import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class SigmazStages {

    private AST mAST;

    public SigmazStages(AST eAST) {

        mAST = eAST;

    }

    public String getNome() {
        return mAST.getNome();
    }

    public String getDefinicao() {
        return mAST.getNome();
    }

    public int getContagem() {

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

        for (AST Sub2 : mAST.getBranch("STAGES").getASTS()) {
            contador += 1;
        }

        return contador;
    }

    public int getSeparadores() {

        int contador = 0;

        if (temCorpo()) {
            contador += 1;
        }


        return contador;

    }


    public ArrayList<String> getStages() {
        ArrayList<String> mLista = new ArrayList<String>();

        for (AST Sub2 : mAST.getBranch("STAGES").getASTS()) {
            mLista.add(Sub2.getNome());
        }
        return mLista;
    }


    public ArrayList<SigmazAction> getAction() {
        ArrayList<SigmazAction> mLista = new ArrayList<SigmazAction>();

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("ACTION")) {
                SigmazAction mCorrente = new SigmazAction(Sub2);
                if (mCorrente.isExplicit()) {
                    mLista.add(mCorrente);
                }
            }
        }
        return mLista;
    }

    public ArrayList<SigmazFunction> getFunctions() {
        ArrayList<SigmazFunction> mLista = new ArrayList<SigmazFunction>();

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("FUNCTION")) {
                SigmazFunction mCorrente = new SigmazFunction(Sub2);
                if (mCorrente.isExplicit()) {
                    mLista.add(mCorrente);
                }
            }
        }
        return mLista;
    }


    public boolean temActionsFunctions() {

        boolean mRet = false;

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("ACTION")) {
                SigmazAction mCorrente = new SigmazAction(Sub2);
                if (mCorrente.isExplicit()) {
                    mRet = true;
                    break;
                }
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                SigmazFunction mCorrente = new SigmazFunction(Sub2);
                if (mCorrente.isExplicit()) {
                    mRet = true;
                    break;
                }
            }
        }


        return mRet;

    }


    public ArrayList<SigmazOperator> getOperators() {
        ArrayList<SigmazOperator> mLista = new ArrayList<SigmazOperator>();

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("OPERATOR")) {
                SigmazOperator mCorrente = new SigmazOperator(Sub2);
                if (mCorrente.isExplicit()) {
                    mLista.add(mCorrente);
                }
            }
        }
        return mLista;
    }


    public boolean temCorpo() {

        boolean tem = false;

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("ACTION")) {
                tem = true;
                break;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                tem = true;
                break;
            } else if (Sub2.mesmoTipo("OPERATOR")) {
                tem = true;
                break;
            }
        }
        return tem;

    }


}
