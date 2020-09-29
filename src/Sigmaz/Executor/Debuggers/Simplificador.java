package Sigmaz.Executor.Debuggers;

import Sigmaz.Utils.AST;

public class Simplificador {

    public String getGenericos(AST ASTPai) {

        String ret = "";

        int total = 0;

        for (AST mAST : ASTPai.getBranch("GENERICS").getASTS()) {
            if (mAST.mesmoTipo("TYPE")) {
                total += 1;
            }
        }

        int contando = 0;

        for (AST mAST : ASTPai.getBranch("GENERICS").getASTS()) {
            if (mAST.mesmoTipo("TYPE")) {
                contando += 1;

                if (contando < total) {
                    ret += mAST.getNome()  + " , ";
                } else {
                    ret += mAST.getNome()  + "";
                }
            }
        }

        return ret;
    }

    public String getParametros(AST ASTPai) {
        String ret = "";

        int total = 0;

        for (AST mAST : ASTPai.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {
                total += 1;
            }
        }

        int contando = 0;

        for (AST mAST : ASTPai.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {
                contando += 1;

                if (contando < total) {
                    ret += mAST.getNome() + " : " + getTipagem(mAST.getBranch("TYPE")) + " , ";
                } else {
                    ret += mAST.getNome() + " : " + getTipagem(mAST.getBranch("TYPE")) + " ";
                }
            }
        }

        return ret;
    }

    public String getTipagem(AST eAST) {

        String mTipagem = "";

        if (eAST != null){
            mTipagem = eAST.getNome();

            if (eAST.mesmoValor("GENERIC")) {

                for (AST eTipando : eAST.getASTS()) {
                    mTipagem += "<" + getTipagem(eTipando) + ">";
                }

            }
        }



        return mTipagem;

    }

    public String getStages(AST eAST) {

        int i = 0;
        int o = eAST.getASTS().size();

        String ret = " -> { ";

        for (AST mAST : eAST.getASTS()) {
            i += 1;
            if (i == o) {
                ret += mAST.getNome();
            } else {
                ret += mAST.getNome() + ",";

            }
        }

        return ret + " }";
    }

}
