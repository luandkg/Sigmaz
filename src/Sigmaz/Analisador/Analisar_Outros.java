package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Outros {

    private Analisador mAnalisador;

    private ArrayList<String> mPrimitivos;

    private ArrayList<String> mFunctions;

    private ArrayList<String> mFunctions_Nomes;
    private ArrayList<String> mActions_Nomes;


    private ArrayList<String> mCasts_Nomes;
    private ArrayList<String> mStructs_Nomes;
    private ArrayList<String> mStages_Nomes;

    public Analisar_Outros(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

        mPrimitivos = new ArrayList<>();

        mPrimitivos.add("bool");
        mPrimitivos.add("string");
        mPrimitivos.add("num");
        mPrimitivos.add("any");

        mFunctions = new ArrayList<String>();
        mCasts_Nomes = new ArrayList<String>();
        mStructs_Nomes = new ArrayList<String>();
        mStages_Nomes = new ArrayList<String>();

        mActions_Nomes = new ArrayList<String>();
        mFunctions_Nomes = new ArrayList<String>();

    }


    public ArrayList<String> getFunctions() {
        return mFunctions;
    }

    public ArrayList<String> getActions_Nomes() {
        return mActions_Nomes;
    }

    public ArrayList<String> getFunctions_Nomes() {
        return mFunctions_Nomes;
    }

    public void limpar() {

        mActions_Nomes.clear();
        mFunctions_Nomes.clear();

        mFunctions.clear();

        mCasts_Nomes.clear();
        mStructs_Nomes.clear();
        mStages_Nomes.clear();

    }

    public void inclusao(AST ASTPai) {

        ArrayList<ItemContador> Itens = new ArrayList<ItemContador>();

        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("FUNCTION")) {

                mFunctions_Nomes.add(mAST.getNome());

                unicidade(Itens, mAnalisador.getAnalisar_Argumentos().getDefinicao(mAST));

            } else if (mAST.mesmoTipo("ACTION")) {

                mActions_Nomes.add(mAST.getNome());

                unicidade(Itens, mAnalisador.getAnalisar_Argumentos().getDefinicao(mAST));

            } else if (mAST.mesmoTipo("CAST")) {

                if (!mCasts_Nomes.contains(mAST.getNome())) {
                    mCasts_Nomes.add(mAST.getNome());
                } else {
                    mAnalisador.getErros().add("Cast Duplicado : " + mAST.getTipo());
                }

                unicidade(Itens, mAST.getNome());

            } else if (mAST.mesmoTipo("STRUCT")) {

                if (!mStructs_Nomes.contains(mAST.getNome())) {
                    mStructs_Nomes.add(mAST.getNome());
                } else {
                    mAnalisador.getErros().add("Struct Duplicado : " + mAST.getTipo());
                }

                unicidade(Itens, mAST.getNome());

            } else if (mAST.mesmoTipo("STAGES")) {

                if (!mStages_Nomes.contains(mAST.getNome())) {
                    mStages_Nomes.add(mAST.getNome());
                } else {
                    mAnalisador.getErros().add("Stage Duplicado : " + mAST.getTipo());
                }

                unicidade(Itens, mAST.getNome());


            }

        }


        for (ItemContador IC : Itens) {

            if (IC.getQuantidade() > 1) {
                mAnalisador.getErros().add("Estrutura Duplicada : " + IC.getNome());

                // mAnalisador.getErros().add("Estrutura " + IC.getNome() + " = " + IC.getQuantidade());
            }


        }


    }


    public void unicidade(ArrayList<ItemContador> Itens, String eNome) {

        boolean enc = false;
        for (ItemContador IC : Itens) {
            if (IC.mesmoNome(eNome)) {

                IC.aumentar(1);

                enc = true;
                break;
            }
        }

        if (!enc) {
            Itens.add(new ItemContador(eNome, 1));
        }

    }


    public void analisarTipagem(AST ASTPai) {

        if (ASTPai.existeBranch("TYPE")) {

            AST mTipo = ASTPai.getBranch("TYPE");

            if (mTipo.mesmoValor("CONCRETE")) {

                if (mAnalisador.getTipados().contains(mTipo.getNome())) {

                } else {

                    mAnalisador.getErros().add("Tipo deconhecido y : " + mTipo.getNome());
                }

            }

        }


    }


    public void analisandoDefinesParam(AST ASTPai) {

        if (ASTPai.existeBranch("TYPE")) {
            AST mTipo = ASTPai.getBranch("TYPE");

            if (mTipo.mesmoValor("CONCRETE")) {

                if (mAnalisador.getTipados().contains(mTipo.getNome())) {

                } else if (mTipo.getNome().contentEquals("any")) {

                } else {

                    mAnalisador.getErros().add("Tipo deconhecido y : " + mTipo.getNome());
                }

            }
        }


    }

    public void analisandoDefines2(AST ASTPai) {

        if (mPrimitivos.contains(ASTPai.getValor())) {

        } else if (mCasts_Nomes.contains(ASTPai.getValor())) {

        } else if (mStructs_Nomes.contains(ASTPai.getValor())) {

        } else if (mStages_Nomes.contains(ASTPai.getValor())) {

        } else {
            mAnalisador.getErros().add("Tipo deconhecido  2 : " + ASTPai.getValor());
        }

    }


    public void exportarOperadores(AST mTodos) {


        ArrayList<AST> mEstruturas = new ArrayList<AST>();


        for (AST Struct_AST : mTodos.getASTS()) {

            if (Struct_AST.mesmoTipo("STRUCT")) {
                mEstruturas.add(Struct_AST);
            }

        }


        for (AST mAST : mEstruturas) {


            AST mCorpo = mAST.getBranch("BODY");

            for (AST sAST : mCorpo.getASTS()) {

                if (sAST.mesmoTipo("OPERATION")) {


                    mTodos.getASTS().add(sAST);


                }

            }

        }


    }

    public String getModo(AST eAST) {
        return eAST.getBranch("VISIBILITY").getNome();
    }

}
