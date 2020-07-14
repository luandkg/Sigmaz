package Sigmaz.Executor;

import Sigmaz.Utils.AST;

public class Utils {


    public String getAction(AST eAST){
        return eAST.getNome() + " (" + getParametragem(eAST) + ")";
    }

    public String getModelAction(AST eAST){
        return eAST.getNome() + " (" + getModelParametragem(eAST) + ")";
    }


    public String getFuction(AST eAST){
        return eAST.getNome() + " (" + getParametragem(eAST) + ") : " + getTipagem(eAST.getBranch("TYPE"));
    }

    public String getModelFuction(AST eAST){
        return eAST.getNome() + " (" + getModelParametragem(eAST) + ") : " + getTipagem(eAST.getBranch("TYPE"));
    }


    public String getDefine(AST eAST){
        return eAST.getNome() + " : " + getTipagem(eAST.getBranch("TYPE"));
    }

    public String getMockiz(AST eAST){
        return eAST.getNome() + " : " + getTipagem(eAST.getBranch("TYPE"));
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

    public String getParametragem(AST eAST) {
        String ret = "";

        int total = eAST.getBranch("ARGUMENTS").getASTS().size();

        if (total > 0) {


            for (int ii = 0; ii < total; ii++) {
                AST eArg = eAST.getBranch("ARGUMENTS").getASTS().get(ii);

                if (ii < total - 1) {
                    ret += eArg.getNome() + " : " + getTipagem(eArg.getBranch("TYPE")) + " , ";
                } else {
                    ret += eArg.getNome() + " : " + getTipagem(eArg.getBranch("TYPE")) + "";
                }

            }

        } else {
            ret = " ";

        }


        return ret;
    }

    public String getModelParametragem(AST eAST) {
        String ret = "";

        int total = eAST.getBranch("ARGUMENTS").getASTS().size();

        if (total > 0) {


            for (int ii = 0; ii < total; ii++) {
                AST eArg = eAST.getBranch("ARGUMENTS").getASTS().get(ii);

                if (ii < total - 1) {
                    ret +=  getTipagem(eArg) + " , ";
                } else {
                    ret += getTipagem(eArg) + "";
                }

            }

        } else {
            ret = " ";

        }


        return ret;
    }

}
