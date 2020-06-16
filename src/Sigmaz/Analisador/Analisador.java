package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisador {

    private ArrayList<AST> mASTS;

    private ArrayList<String> mErros;

    private ArrayList<String> mPrimitivos;

    private ArrayList<String> mFunctions;

    public Analisador() {

        mASTS = new ArrayList<>();


        mErros = new ArrayList<>();
        mPrimitivos = new ArrayList<>();

        mPrimitivos.add("bool");
        mPrimitivos.add("string");
        mPrimitivos.add("num");

        mFunctions = new ArrayList<String>();
    }

    public ArrayList<String> getErros() {
        return mErros;
    }

    public void init(ArrayList<AST> eASTs) {
        mASTS = eASTs;
        mErros.clear();
        mFunctions.clear();

        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                analisarGlobal(ASTCGlobal);

            } else {

                mErros.add("Escopo Desconhecido : " + ASTCGlobal.getTipo());

            }

        }


    }

    public void analisarGlobal(AST ASTPai) {


        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("DEFINE")) {
                analisandoDefines(mAST);
            } else if (mAST.mesmoTipo("MOCKIZ")) {
                analisandoDefines(mAST);
            } else if (mAST.mesmoTipo("ACTION")) {

              //  analisarAction(mAST);

            } else if (mAST.mesmoTipo("FUNCTION")) {

                analisarFunction(mAST);

            } else if (mAST.mesmoTipo("CALL")) {

            } else {

                mErros.add("AST x : " + mAST.getTipo());


            }


        }

    }

    public String analisarArguments(AST ASTPai) {

        ArrayList<String> mNomes = new ArrayList<String>();

        String mParametragem = "";

        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {

                if (!mNomes.contains(mAST.getNome())) {
                    mNomes.add(mAST.getNome());
                } else {
                    mErros.add("Argumento Duplicado : " + mAST.getNome());
                }

                mParametragem += "<" + mAST.getValor() + "> ";

                analisandoDefines(mAST);
            } else {
                mErros.add("Tipo Desconhecido : " + mAST.getTipo());
            }
        }

        return mParametragem;
    }

    public void analisarFunction(AST ASTPai) {

        analisandoDefines(ASTPai);

        String mParametragem = ASTPai.getNome() + " ( " + analisarArguments(ASTPai.getBranch("ARGUMENTS")) + ") ";


        if (!mFunctions.contains(mParametragem)) {
            mFunctions.add(mParametragem);
        } else {
            mErros.add("Function Duplicada : " + mParametragem);
        }


        boolean retornou = false;

        for (AST mAST : ASTPai.getBranch("BODY").getASTS()) {


            boolean ret = analisarDentroFunction(mAST);
            if (ret) {
                retornou = true;
            }
        }

        if (retornou == false) {
            mErros.add("Function " + ASTPai.getNome() + " : Nao possui retorno !");
        }

    }

    public boolean analisarDentroFunction(AST ASTPai) {

        boolean retornou = false;

        if (ASTPai.mesmoTipo("DEF")) {
            analisandoDefines(ASTPai);
        } else if (ASTPai.mesmoTipo("MOC")) {
            analisandoDefines(ASTPai);
        } else if (ASTPai.mesmoTipo("INVOKE")) {

        } else if (ASTPai.mesmoTipo("RETURN")) {
            retornou = true;
        } else if (ASTPai.mesmoTipo("APPLY")) {
        } else if (ASTPai.mesmoTipo("EXECUTE")) {

        } else if (ASTPai.mesmoTipo("IF")) {
            analisarCondicao(ASTPai, true);
        } else if (ASTPai.mesmoTipo("WHILE")) {
            analisarWhile(ASTPai, true);
        } else {
            mErros.add("AST : " + ASTPai.getTipo());
        }

        return retornou;
    }

    public void analisarDentroAction(AST ASTPai) {

        if (ASTPai.mesmoTipo("DEF")) {
            analisandoDefines(ASTPai);
        } else if (ASTPai.mesmoTipo("MOC")) {
            analisandoDefines(ASTPai);
        } else if (ASTPai.mesmoTipo("INVOKE")) {
        } else if (ASTPai.mesmoTipo("APPLY")) {
        } else if (ASTPai.mesmoTipo("EXECUTE")) {

        } else if (ASTPai.mesmoTipo("RETURN")) {
            mErros.add("Action " + ASTPai.getNome() + " : Nao pode ter RETORNO !");
        } else if (ASTPai.mesmoTipo("IF")) {
            analisarCondicao(ASTPai, false);
        } else if (ASTPai.mesmoTipo("WHILE")) {
            analisarWhile(ASTPai, false);
        } else {
            mErros.add("AST : " + ASTPai.getTipo());
        }

    }

    public void analisarAction(AST ASTPai) {

        analisarArguments(ASTPai.getBranch("ARGUMENTS"));


        for (AST mAST : ASTPai.getBranch("BODY").getASTS()) {

            analisarDentroAction(mAST);


        }

    }

    public void analisarWhile(AST ASTPai, boolean dentroFunction) {

        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("CONDITION")) {

                if (mAST.getValor().length() == 0) {
                    mErros.add("É necessario uma condição !");
                }

            } else if (mAST.mesmoTipo("BODY")) {
                if (dentroFunction) {
                    for (AST sAST : mAST.getASTS()) {
                        analisarDentroFunction(sAST);
                    }
                } else {
                    for (AST sAST : mAST.getASTS()) {
                        analisarDentroAction(sAST);
                    }
                }
            }

        }
    }


    public void analisarCondicao(AST ASTPai, boolean dentroFunction) {

        int condition = 0;
        int other = 0;
        int others = 0;

        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("CONDITION")) {
                condition += 1;

                if (mAST.getValor().length() == 0) {
                    mErros.add("É necessario uma condição !");
                }
            } else if (mAST.mesmoTipo("OTHER")) {
                other += 1;
                if (others > 0) {
                    mErros.add("A condicao OTHERS deve ser a ultima ! !");
                }


                if (dentroFunction) {
                    for (AST sAST : mAST.getBranch("BODY").getASTS()) {
                        analisarDentroFunction(sAST);
                    }
                } else {
                    for (AST sAST : mAST.getBranch("BODY").getASTS()) {
                        analisarDentroAction(sAST);
                    }
                }


            } else if (mAST.mesmoTipo("OTHERS")) {
                others += 1;

                if (dentroFunction) {
                    for (AST sAST : mAST.getASTS()) {
                        analisarDentroFunction(sAST);
                    }
                } else {
                    for (AST sAST : mAST.getASTS()) {
                        analisarDentroAction(sAST);
                    }
                }

            } else if (mAST.mesmoTipo("BODY")) {
                if (dentroFunction) {
                    for (AST sAST : mAST.getASTS()) {
                        analisarDentroFunction(sAST);
                    }
                } else {
                    for (AST sAST : mAST.getASTS()) {
                        analisarDentroAction(sAST);
                    }
                }
            }


        }

        if (condition == 0) {
            mErros.add("É necessario uma condição !");
        }

        if (others > 1) {
            mErros.add("Existe mais de um OTHERS na condição !");
        }

    }


    public void analisandoDefines(AST ASTPai) {

        if (mPrimitivos.contains(ASTPai.getValor())) {

        } else {
            mErros.add("Tipo deconhecido : " + ASTPai.getValor());
        }

    }

}
