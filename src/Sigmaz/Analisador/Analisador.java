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

                analisarAction(mAST);

            } else if (mAST.mesmoTipo("FUNCTION")) {

                analisarFunction(mAST);

            } else if (mAST.mesmoTipo("CALL")) {

            } else {

                mErros.add("AST : " + mAST.getTipo());


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


            if (mAST.mesmoTipo("DEF")) {
                analisandoDefines(mAST);
            } else if (mAST.mesmoTipo("MOC")) {
                analisandoDefines(mAST);
            } else if (mAST.mesmoTipo("INVOKE")) {

            } else if (mAST.mesmoTipo("RETURN")) {
                retornou = true;
            } else if (mAST.mesmoTipo("APPLY")) {

            } else {
                mErros.add("AST : " + mAST.getTipo());
            }


        }

        if (retornou == false) {
            mErros.add("Function " + ASTPai.getNome() + " : Nao possui retorno !");
        }

    }

    public void analisarAction(AST ASTPai) {

        analisarArguments(ASTPai.getBranch("ARGUMENTS"));


        for (AST mAST : ASTPai.getBranch("BODY").getASTS()) {


            if (mAST.mesmoTipo("DEF")) {
                analisandoDefines(mAST);
            } else if (mAST.mesmoTipo("MOC")) {
                analisandoDefines(mAST);
            } else if (mAST.mesmoTipo("INVOKE")) {
            } else if (mAST.mesmoTipo("APPLY")) {

            } else {
                mErros.add("AST : " + mAST.getTipo());
            }


        }

    }

    public void analisandoDefines(AST ASTPai) {

        if (mPrimitivos.contains(ASTPai.getValor())) {

        } else {
            mErros.add("Tipo deconhecido : " + ASTPai.getValor());
        }

    }

}
