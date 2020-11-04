package Sigmaz.S05_Executor.Debuggers;

import Sigmaz.S00_Utilitarios.AST;

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
                    ret += mAST.getNome() + " , ";
                } else {
                    ret += mAST.getNome() + "";
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


    public String getParametros_SoTipos(AST ASTPai) {
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
                    ret += " " + getTipagem(mAST.getBranch("TYPE")) + " , ";
                } else {
                    ret += " " + getTipagem(mAST.getBranch("TYPE")) + " ";
                }
            }
        }

        return ret;
    }


    public String getParametragemFormas(AST ASTPai) {
        String ret = "";

        int total = 0;

        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {
                total += 1;
            }
        }

        int contando = 0;

        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {
                contando += 1;

                if (contando < total) {
                    ret += mAST.getValor() + " :: " + mAST.getNome() + " : " + getTipagem(mAST.getBranch("TYPE")) + " , ";
                } else {
                    ret += mAST.getValor() + " :: " + mAST.getNome() + " : " + getTipagem(mAST.getBranch("TYPE")) + " ";
                }
            }
        }

        return " ( " + ret + " ) ";
    }


    public String getTipagem(AST eAST) {

        String mTipagem = "";

        if (eAST != null) {
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


    public String getDefine(AST eAST) {
        return eAST.getNome() + " : " + getTipagem(eAST.getBranch("TYPE"));
    }


    public String getMockiz(AST eAST) {
        return eAST.getNome() + " : " + getTipagem(eAST.getBranch("TYPE"));
    }


    public String getAction(AST eAST) {
        return eAST.getNome() + " (" + getParametros(eAST) + ")";
    }

    public String getAlgo_SoTipos(AST eAST) {
        return eAST.getNome() + " (" + getParametros_SoTipos(eAST) + ")";
    }

    public String getInit(AST eAST) {
        return eAST.getNome() + " (" + getParametros(eAST) + ")";
    }

    public String getCall(AST eAST) {
        return eAST.getNome() + " (" + getParametros(eAST.getBranch("SENDING")) + ")";
    }


    public String getFuction(AST eAST) {
        return eAST.getNome() + " (" + getParametros(eAST) + ") : " + getTipagem(eAST.getBranch("TYPE"));
    }

    public String getStruct(AST eAST) {
        return eAST.getNome();
    }

    public String getExternal(AST eAST) {
        return eAST.getNome();
    }

    public String getModel(AST eAST) {
        return eAST.getNome();
    }

    public String getBlocoGetter(String eStructNome, AST eAST) {
        return eStructNome + " [" + getParametros(eAST) + "] : " + getTipagem(eAST.getBranch("TYPE"));
    }

    public String getBlocoSetter(String eStructNome, AST eAST) {
        return eStructNome + " [" + getParametros(eAST) + "] ";
    }

    public String getMark(AST eAST) {
        return eAST.getNome() + " (" + getParametros(eAST) + ") : " + getTipagem(eAST.getBranch("TYPE"));
    }

    public String getAuto(AST eAST) {
        return "< " + getGenericos(eAST) + " > " + eAST.getNome() + " (" + getParametros(eAST) + ")";
    }

    public String getFunctor(AST eAST) {
        return "< " + getGenericos(eAST) + " > " + eAST.getNome() + " (" + getParametros(eAST) + ") : " + getTipagem(eAST.getBranch("TYPE"));
    }

    public String getDirector(AST eAST) {
        return eAST.getNome() + " (" + getParametros(eAST) + ") : " + getTipagem(eAST.getBranch("TYPE"));
    }

    public String getOperator(AST eAST) {
        return eAST.getNome() + " (" + getParametros(eAST) + ") : " + getTipagem(eAST.getBranch("TYPE"));
    }

    public String getCabecalho(AST eAST) {

        return eAST.getTipo() + " " + eAST.getNome() + " (" + getParametros(eAST) + ") ";

    }

    public String getCast(AST eAST) {
        return eAST.getNome();
    }

}
