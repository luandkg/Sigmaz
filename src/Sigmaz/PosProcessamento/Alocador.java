package Sigmaz.PosProcessamento;

import java.util.ArrayList;

import Sigmaz.Utils.AST;
import Sigmaz.Utils.Utilitario;

public class Alocador {

    private PosProcessador mPosProcessador;

    public Alocador(PosProcessador ePosProcessador) {
        mPosProcessador = ePosProcessador;
    }

    public void init(ArrayList<AST> mASTS) {


        mensagem("");
        mensagem(" ------------------ FASE ALOCADOR ----------------------- ");
        mensagem("");

        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {


                checarAlocacao("","", ASTCGlobal);

                for (AST mAST : ASTCGlobal.getASTS()) {
                    if (mAST.mesmoTipo("PACKAGE")) {

                        mensagem("PACKAGE : " + mAST.getNome());

                        checarAlocacao("","", mAST);
                    }
                }


            } else {

                errar("Escopo Desconhecido : " + ASTCGlobal.getTipo());

            }

        }

    }

    public void checarAlocacao(String eCaminho,String ePrefixo, AST ASTPai) {

        ArrayList<String> mAlocados_Aqui = new ArrayList<String>();

        Utilitario mUtilitario = new Utilitario();


        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("DEFINE")) {

                if (mAlocados_Aqui.contains(mAST.getNome())) {
                    errar(eCaminho + " " + "Definicao Duplicada : " + mAST.getNome());
                    mensagem(eCaminho + " " + ePrefixo + "DEFINE " + mUtilitario.getDefine_Definicao(mAST) + " -->> DUPLICADA");

                } else {
                    mAlocados_Aqui.add(mAST.getNome());
                    mensagem(eCaminho + " " +ePrefixo + "DEFINE " + mUtilitario.getDefine_Definicao(mAST) + " -->> OK");
                }
            } else if (mAST.mesmoTipo("MOCKIZ")) {

                if (mAlocados_Aqui.contains(mAST.getNome())) {
                    errar(eCaminho + " " +"Definicao Duplicada : " + mAST.getNome());
                    mensagem(eCaminho + " " +ePrefixo + "MOCKIZ " + mUtilitario.getDefine_Definicao(mAST) + " -->> DUPLICADA");

                } else {
                    mAlocados_Aqui.add(mAST.getNome());
                    mensagem(eCaminho + " " +ePrefixo + "MOCKIZ " + mUtilitario.getDefine_Definicao(mAST) + " -->> OK");
                }
            } else if (mAST.mesmoTipo("DEF")) {

                if (mAlocados_Aqui.contains(mAST.getNome())) {
                    errar(eCaminho + " " +"Definicao Duplicada : " + mAST.getNome());
                    mensagem(eCaminho + " " +ePrefixo + "DEF " + mUtilitario.getDefine_Definicao(mAST) + " -->> DUPLICADA");

                } else {
                    mAlocados_Aqui.add(mAST.getNome());
                    mensagem(eCaminho + " " +ePrefixo + "DEF " + mUtilitario.getDefine_Definicao(mAST) + " -->> OK");
                }
            } else if (mAST.mesmoTipo("MOC")) {

                if (mAlocados_Aqui.contains(mAST.getNome())) {
                    errar(eCaminho + " " +"Definicao Duplicada : " + mAST.getNome());
                    mensagem(eCaminho + " " +ePrefixo + "MOC " + mUtilitario.getDefine_Definicao(mAST) + " -->> DUPLICADA");

                } else {
                    mAlocados_Aqui.add(mAST.getNome());
                    mensagem(eCaminho + " " +ePrefixo + "MOC " + mUtilitario.getDefine_Definicao(mAST) + " -->> OK");
                }
            } else if (mAST.mesmoTipo("ACTION")) {

                mensagem(eCaminho + " " +ePrefixo + "ACTION " + mUtilitario.getAction_Definicao(mAST));

                checarAlocacao(eCaminho + " ACTION " + mUtilitario.getAction_Definicao(mAST) + " -> ",ePrefixo + "\t", mAST.getBranch("BODY"));

            } else if (mAST.mesmoTipo("FUNCTION")) {

                mensagem(eCaminho + " " +ePrefixo + "FUNCTION " + mUtilitario.getFunction_Definicao(mAST));

                checarAlocacao(eCaminho+ " FUNCTION " + mUtilitario.getFunction_Definicao(mAST) + " -> ",ePrefixo + "\t", mAST.getBranch("BODY"));
            } else if (mAST.mesmoTipo("DIRECTOR")) {

                mensagem(eCaminho + " " +ePrefixo + "DIRECTOR " + mUtilitario.getFunction_Definicao(mAST));

                checarAlocacao(eCaminho+ " DIRECTOR " + mUtilitario.getFunction_Definicao(mAST) + " -> ",ePrefixo + "\t", mAST.getBranch("BODY"));

            } else if (mAST.mesmoTipo("OPERATOR")) {

                mensagem(eCaminho + " " +ePrefixo + "OPERATOR " + mUtilitario.getFunction_Definicao(mAST));

                checarAlocacao(eCaminho+ " OPERATOR " + mUtilitario.getFunction_Definicao(mAST) + " -> ",ePrefixo + "\t", mAST.getBranch("BODY"));

            } else if (mAST.mesmoTipo("IF")) {

                checarAlocacao(eCaminho + " CONDITION  -> ",ePrefixo + "\t", mAST.getBranch("BODY"));

                for (AST sAST : mAST.getASTS()) {
                    if (sAST.mesmoTipo("OTHER")) {
                        checarAlocacao(eCaminho + " CONDITION OTHER -> ",ePrefixo + "\t", sAST.getBranch("BODY"));
                    } else if (sAST.mesmoTipo("OTHERS")) {
                        checarAlocacao(eCaminho + " CONDITION OTHERS -> ",ePrefixo + "\t", sAST);
                    }
                }
            } else if (mAST.mesmoTipo("WHILE")) {

                checarAlocacao(eCaminho + " " +" WHILE -> ",ePrefixo + "\t", mAST.getBranch("BODY"));

            } else if (mAST.mesmoTipo("STRUCT")) {

                AST mExtended = mAST.getBranch("EXTENDED");
                if (mExtended.mesmoNome("STRUCT")) {

                   mensagem(eCaminho + " " +ePrefixo + "STRUCT : "+ mAST.getNome());
                    checarAlocacao(eCaminho + " " +"STRUCT " + mAST.getNome() + " -> ",ePrefixo + "\t", mAST.getBranch("BODY"));


                }
            }

        }

    }


    private void errar(String eErro) {
        mPosProcessador.getErros().add(eErro);
    }

    private void mensagem(String eMensagem) {
        mPosProcessador.mensagem(eMensagem);
    }


}
