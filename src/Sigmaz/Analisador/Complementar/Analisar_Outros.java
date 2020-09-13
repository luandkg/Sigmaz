package Sigmaz.Analisador.Complementar;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Utils.AST;
import Sigmaz.Utils.ItemContador;

import java.util.ArrayList;

public class Analisar_Outros {

    private Analisador mAnalisador;
    private Analisador_Bloco mAnalisador_Bloco;

    private ArrayList<String> mPrimitivos;

    private ArrayList<String> mCasts_Nomes;
    private ArrayList<String> mStructs_Nomes;
    private ArrayList<String> mStages_Nomes;

    private ArrayList<String> mTypes_Nomes;

    private ArrayList<String> mActions_Nomes;
    private ArrayList<String> mActions_ApenasNomes;

    private ArrayList<String> mFunctions_Nomes;
    private ArrayList<String> mFunctions_ApenasNomes;

    public Analisar_Outros(Analisador eAnalisador,Analisador_Bloco eAnalisador_Bloco) {

        mAnalisador = eAnalisador;
        mAnalisador_Bloco=eAnalisador_Bloco;

        mPrimitivos = new ArrayList<>();

        mPrimitivos.add("bool");
        mPrimitivos.add("string");
        mPrimitivos.add("num");
        mPrimitivos.add("any");

        mCasts_Nomes = new ArrayList<String>();
        mStructs_Nomes = new ArrayList<String>();
        mStages_Nomes = new ArrayList<String>();

        mActions_Nomes = new ArrayList<String>();
        mActions_ApenasNomes = new ArrayList<String>();

        mFunctions_Nomes = new ArrayList<String>();
        mFunctions_ApenasNomes = new ArrayList<String>();

        mTypes_Nomes = new ArrayList<String>();

    }





    public ArrayList<String> getFunctions_Nomes() {
        return mFunctions_Nomes;
    }

    public ArrayList<String> getFunctions_ApenasNomes() {
        return mFunctions_ApenasNomes;
    }

    public void limpar() {


        mActions_Nomes.clear();
        mActions_ApenasNomes.clear();

        mFunctions_Nomes.clear();
        mFunctions_ApenasNomes.clear();


        mCasts_Nomes.clear();
        mStructs_Nomes.clear();
        mStages_Nomes.clear();

        mTypes_Nomes.clear();

    }

    public void inclusao(AST ASTPai) {

        ArrayList<ItemContador> Itens = new ArrayList<ItemContador>();

        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("CAST")) {

                mAnalisador.mensagem("  -- Cast " + mAST.getNome());
                mAnalisador_Bloco.getTipados().add(mAST.getNome());
                mAnalisador_Bloco.getPrimitivos().add(mAST.getNome());

            } else if (mAST.mesmoTipo("STRUCT")) {

                mAnalisador_Bloco.getTipados().add(mAST.getNome());
            } else if (mAST.mesmoTipo("STAGES")) {

                mAnalisador_Bloco.getTipados().add(mAST.getNome());
            } else if (mAST.mesmoTipo("TYPE")) {

                mAnalisador_Bloco.getTipados().add(mAST.getNome());
            }

        }


        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("FUNCTION")) {

               // mAnalisador.mensagem("  -- Function " + mAST.getNome());

                mFunctions_Nomes.add(mAST.getNome());

                if (!mFunctions_ApenasNomes.contains(mAST.getNome())){
                    mFunctions_ApenasNomes.add(mAST.getNome());
                }

                unicidade(Itens, mAnalisador_Bloco.getAnalisar_Argumentos().getDefinicao(mAST));

            } else if (mAST.mesmoTipo("ACTION")) {

             //   mAnalisador.mensagem("  -- Action " + mAST.getNome());

                mActions_Nomes.add(mAST.getNome());

                if (!mActions_ApenasNomes.contains(mAST.getNome())){
                    mActions_ApenasNomes.add(mAST.getNome());
                }

                unicidade(Itens, mAnalisador_Bloco.getAnalisar_Argumentos().getDefinicao(mAST));

            } else if (mAST.mesmoTipo("CAST")) {



                if (!mCasts_Nomes.contains(mAST.getNome())) {
                    mCasts_Nomes.add(mAST.getNome());


                } else {
                    mAnalisador.getErros().add("Cast Duplicado : " + mAST.getTipo());
                }

                unicidade(Itens, mAST.getNome());

            } else if (mAST.mesmoTipo("STRUCT")) {


                AST AST_Stages = mAST.getBranch("EXTENDED");
                 if (AST_Stages.mesmoNome("STAGES")){

                }else{

                    mAnalisador_Bloco.getTipados().add(mAST.getNome());

                    if (!mStructs_Nomes.contains(mAST.getNome())) {
                        mStructs_Nomes.add(mAST.getNome());
                    } else {
                        mAnalisador.getErros().add("Struct Duplicado : " + mAST.getTipo());
                    }

                    unicidade(Itens, mAST.getNome());

                }



            } else if (mAST.mesmoTipo("STAGES")) {


                if (!mStages_Nomes.contains(mAST.getNome())) {
                    mStages_Nomes.add(mAST.getNome());
                } else {
                    mAnalisador.getErros().add("Stage Duplicado : " + mAST.getTipo());
                }

                unicidade(Itens, mAST.getNome());

            } else if (mAST.mesmoTipo("TYPE")) {


                if (!mTypes_Nomes.contains(mAST.getNome())) {
                    mTypes_Nomes.add(mAST.getNome());
                } else {
                    mAnalisador.getErros().add("Type Duplicado : " + mAST.getTipo());
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

                if (mAnalisador_Bloco.getTipados().contains(mTipo.getNome())) {

                } else {

                    mAnalisador.getErros().add("Tipo deconhecido y3 : " + mTipo.getNome());
                }

            }

        }


    }


    public void analisandoDefinesParam(AST ASTPai) {

        if (ASTPai.existeBranch("TYPE")) {
            AST mTipo = ASTPai.getBranch("TYPE");

            if (mTipo.mesmoValor("CONCRETE")) {

                if (mAnalisador_Bloco.getTipados().contains(mTipo.getNome())) {

                } else if (mTipo.getNome().contentEquals("any")) {

                } else {

                    mAnalisador.getErros().add("Global Tipos : " + mAnalisador_Bloco.getTipados().toString());

                    mAnalisador.getErros().add("Tipo deconhecido y5 : " + mTipo.getNome());
                }

            }
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

    public ArrayList<String> getActions_Nomes() {
        return mActions_Nomes;
    }

    public ArrayList<String> getActions_ApenasNomes() {
        return mActions_ApenasNomes;
    }


}
