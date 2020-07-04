package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Global {

    private Analisador mAnalisador;

    public Analisar_Global(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

    }

    public void analisarGlobal(AST ASTPai, ArrayList<String> mAlocadosAntes) {

        ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);


        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("REQUIRED")) {

            } else if (mAST.mesmoTipo("FUNCTION")) {

                mAnalisador.getAnalisar_Function().incluirNome(mAST);


            } else if (mAST.mesmoTipo("STRUCT")) {

                mAnalisador.getTipados().add(mAST.getNome());


            } else if (mAST.mesmoTipo("STAGES")) {

                mAnalisador.getTipados().add(mAST.getNome());
            } else if (mAST.mesmoTipo("CAST")) {

                mAnalisador.getTipados().add(mAST.getNome());
            } else if (mAST.mesmoTipo("TYPE")) {

                mAnalisador.getTipados().add(mAST.getNome());
            } else if (mAST.mesmoTipo("PACKAGE")) {

                mAnalisador.getAnalisar_Package().getPackages().add(mAST);

            }
        }

        mAnalisador.getAnalisar_Outros().inclusao(ASTPai);

        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("DEFINE")) {

                if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Define : " + mAST.getNome() + " : Nome Proibido !");
                }

                mAnalisador.analisarAlocacao(mAST, mAlocados);

                mAnalisador.getAnalisar_Outros().analisarTipagem(mAST);
                mAnalisador.analisarValoracao(ASTPai, mAlocadosAntes);


            } else if (mAST.mesmoTipo("MOCKIZ")) {

                if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Mockiz : " + mAST.getNome() + " : Nome Proibido !");
                }

                mAnalisador.analisarAlocacao(mAST, mAlocados);


                mAnalisador.getAnalisar_Outros().analisarTipagem(mAST);
                mAnalisador.analisarValoracao(ASTPai, mAlocadosAntes);


            }

        }

        // USAR PACKAGES
        for (AST ASTC : ASTPai.getASTS()) {
            if (ASTC.mesmoTipo("USING")) {

                mAnalisador.getAnalisar_Package().Usar(ASTC.getNome(), mAlocados);

            }
        }


        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("ACTION")) {

                if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Action : " + mAST.getNome() + " : Nome Proibido !");
                }

                mAnalisador.getAnalisar_Action().analisarAction(mAST, mAlocados);

            } else if (mAST.mesmoTipo("FUNCTION")) {

                if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Function : " + mAST.getNome() + " : Nome Proibido !");
                }

                mAnalisador.getAnalisar_Function().analisarFunction(mAST, mAlocados);

            } else if (mAST.mesmoTipo("CALL")) {
            } else if (mAST.mesmoTipo("INVOKE")) {

            } else if (mAST.mesmoTipo("DEFINE")) {

                if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Define : " + mAST.getNome() + " : Nome Proibido !");
                }


            } else if (mAST.mesmoTipo("MOCKIZ")) {

                if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Mockiz : " + mAST.getNome() + " : Nome Proibido !");
                }


            } else if (mAST.mesmoTipo("OPERATOR")) {

                mAnalisador.getAnalisar_Function().analisarFunction(mAST, mAlocados);

            } else if (mAST.mesmoTipo("DIRECTOR")) {

                mAnalisador.getAnalisar_Function().analisarFunction(mAST, mAlocados);

            } else if (mAST.mesmoTipo("CAST")) {

                if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Cast : " + mAST.getNome() + " : Nome Proibido !");
                }

                mAnalisador.getAnalisar_Cast().init(mAST);

            } else if (mAST.mesmoTipo("STRUCT")) {

                if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Struct : " + mAST.getNome() + " : Nome Proibido !");
                }

                mAnalisador.getAnalisar_Struct().init_Struct(mAST, mAlocados);

            } else if (mAST.mesmoTipo("STAGES")) {

                if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Stage : " + mAST.getNome() + " : Nome Proibido !");
                }

                mAnalisador.getAnalisar_Stage().analisar(mAST);

            } else if (mAST.mesmoTipo("REQUIRED")) {

            } else if (mAST.mesmoTipo("MODEL")) {

            } else if (mAST.mesmoTipo("TYPE")) {

            } else if (mAST.mesmoTipo("PACKAGE")) {

            } else if (mAST.mesmoTipo("USING")) {

            } else {

                mAnalisador.getErros().add("AST x : " + mAST.getTipo());


            }


        }


        mAnalisador.getAnalisar_Outros().exportarOperadores(ASTPai);

    }
}
