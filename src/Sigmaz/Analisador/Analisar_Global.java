package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Global {

    private Analisador mAnalisador;

    public Analisar_Global(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

    }

    public void analisarGlobal(AST ASTPai,ArrayList<AST> mReqAST) {

        Analisador_Bloco mAnalisador_Bloco = new Analisador_Bloco(mAnalisador);

        ArrayList<String> mAlocados = new ArrayList<String>();
      //  ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);


        for (AST mReq : mReqAST) {
            mAnalisador_Bloco.getAnalisar_Outros().inclusao(mReq);
        }



        // USAR PACKAGES
        for (AST ASTC : ASTPai.getASTS()) {
            if (ASTC.mesmoTipo("REFER")) {
                mAnalisador.mensagem("Global refering " + ASTC.getNome());

                Usar(mAnalisador_Bloco, ASTC.getNome());

            }
        }


        mAnalisador_Bloco.getAnalisar_Outros().inclusao(ASTPai);


        //mAnalisador.mensagem("Global Actions : " + mAnalisador_Bloco.getAnalisar_Outros().getActions_ApenasNomes().toString());
        mAnalisador.mensagem("Global Functions : " + mAnalisador_Bloco.getAnalisar_Outros().getFunctions_ApenasNomes().toString());


        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("DEFINE")) {

                if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Define : " + mAST.getNome() + " : Nome Proibido !");
                }

                mAnalisador.analisarAlocacao(mAST, mAlocados);

                mAnalisador_Bloco.getAnalisar_Outros().analisarTipagem(mAST);
                mAnalisador_Bloco.analisarValoracao(ASTPai, mAlocados);


            } else if (mAST.mesmoTipo("MOCKIZ")) {

                if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Mockiz : " + mAST.getNome() + " : Nome Proibido !");
                }

                mAnalisador.analisarAlocacao(mAST, mAlocados);


                mAnalisador_Bloco.getAnalisar_Outros().analisarTipagem(mAST);
                mAnalisador_Bloco.analisarValoracao(ASTPai, mAlocados);


            }

        }


        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("ACTION")) {

                if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Action : " + mAST.getNome() + " : Nome Proibido !");
                }

                mAnalisador_Bloco.getAnalisar_Action().analisarAction(mAST, mAlocados);

            } else if (mAST.mesmoTipo("FUNCTION")) {

                if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Function : " + mAST.getNome() + " : Nome Proibido !");
                }

                mAnalisador_Bloco.getAnalisar_Function().analisarFunction(mAST, mAlocados);

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

                mAnalisador_Bloco.getAnalisar_Function().analisarFunction(mAST, mAlocados);

            } else if (mAST.mesmoTipo("DIRECTOR")) {

                mAnalisador_Bloco.getAnalisar_Function().analisarFunction(mAST, mAlocados);

            } else if (mAST.mesmoTipo("CAST")) {

                if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Cast : " + mAST.getNome() + " : Nome Proibido !");
                }

                mAnalisador_Bloco.getAnalisar_Cast().init(mAST);

            } else if (mAST.mesmoTipo("STRUCT")) {

                if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Struct : " + mAST.getNome() + " : Nome Proibido !");
                }

                mAnalisador_Bloco.getAnalisar_Struct().init_Struct(mAST, mAlocados);

            } else if (mAST.mesmoTipo("STAGES")) {

                if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Stage : " + mAST.getNome() + " : Nome Proibido !");
                }

                mAnalisador_Bloco.getAnalisar_Stage().analisar(mAST);

            } else if (mAST.mesmoTipo("REQUIRED")) {

            } else if (mAST.mesmoTipo("MODEL")) {

            } else if (mAST.mesmoTipo("TYPE")) {

            } else if (mAST.mesmoTipo("PACKAGE")) {

            } else if (mAST.mesmoTipo("REFER")) {

            } else {

                mAnalisador.getErros().add("AST x : " + mAST.getTipo());


            }


        }


        mAnalisador_Bloco.getAnalisar_Outros().exportarOperadores(ASTPai);


    }

    public void Usar(Analisador_Bloco eAnalisador_Bloco, String eNome) {


        boolean enc = false;


        for (AST ASTPackage : mAnalisador.getPacotes()) {

            if (ASTPackage.mesmoNome(eNome)) {

                ArrayList<AST> mPackageStructs = new ArrayList<>();

                eAnalisador_Bloco.getAnalisar_Outros().inclusao(ASTPackage);


                enc = true;
                break;
            }

        }

        if (!enc) {
            mAnalisador.getErros().add("Package " + eNome + " : Nao encontrado !");
        }


    }

}
