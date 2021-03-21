package Sigmaz.S08_Utilitarios;

import Sigmaz.S05_Executor.Item;

import java.util.ArrayList;

public class Utilitario {


    public String getDefine_Definicao(AST eAST) {
        return eAST.getNome() + " : " + getTipagem(eAST.getBranch("TYPE"));
    }

    public String getMockiz_Definicao(AST eAST) {
        return eAST.getNome() + " : " + getTipagem(eAST.getBranch("TYPE"));
    }

    public String getFunction_Definicao(AST eAST) {
        return eAST.getNome() + " ( " + getParametragem(eAST.getBranch("ARGUMENTS")) + " ) : " + getTipagem(eAST.getBranch("TYPE"));
    }

    public String getAction_Definicao(AST eAST) {
        return eAST.getNome() + " ( " + getParametragem(eAST.getBranch("ARGUMENTS")) + " ) ";
    }

    public String getParametragem(AST eAST) {
        String ret = "";

        ArrayList<AST> mArgumentos = eAST.getASTS();

        int total = mArgumentos.size();


        for (int ii = 0; ii < total; ii++) {

            if (ii < total - 1) {
                ret += mArgumentos.get(ii).getNome() + " : " + getTipagem(mArgumentos.get(ii).getBranch("TYPE")) + " , ";
            } else {
                ret += mArgumentos.get(ii).getNome() + " : " + getTipagem(mArgumentos.get(ii).getBranch("TYPE")) + " ";
            }

        }

        return ret;
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


    public String getArgumentos(ArrayList<Item> mArgumentos){


        String mTipagem = "";


        int i = 0;
        int o = mArgumentos.size();
        for (Item ie : mArgumentos) {

            i += 1;

            if (i < o) {
                mTipagem += ie.getTipo() + " , ";
            } else {
                mTipagem += ie.getTipo() + " ";
            }


        }

        return " ( " + mTipagem + " ) ";

    }


    public String getArgumentosCol(ArrayList<Item> mArgumentos){


        String mTipagem = "";


        int i = 0;
        int o = mArgumentos.size();
        for (Item ie : mArgumentos) {

            i += 1;

            if (i < o) {
                mTipagem += ie.getTipo() + " , ";
            } else {
                mTipagem += ie.getTipo() + " ";
            }


        }

        return " [ " + mTipagem + " ] ";

    }


    public String getAbastratos(AST mGenericos){

        String mTipagem = "";


        int i = 0;
        int o = mGenericos.getASTS().size();

        for (AST ie : mGenericos.getASTS()) {

            i += 1;

            if (i < o) {
                mTipagem += ie.getNome() + " , ";
            } else {
                mTipagem += ie.getNome() + " ";
            }


        }

        return mTipagem;
    }


    public String getModo(AST eAST) {
        return eAST.getBranch("VISIBILITY").getNome();
    }
}
