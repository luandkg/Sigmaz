package Sigmaz.S05_PosProcessamento.Processadores;

import java.util.ArrayList;

import Sigmaz.S05_PosProcessamento.PosProcessador;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Utilitario;

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

        String aFixo = ePrefixo + "\t";


        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("DEFINE")) {

                if (mAlocados_Aqui.contains(mAST.getNome())) {
                    errar(eCaminho + " " + "Definicao Duplicada : " + mAST.getNome());
                    mensagem(  aFixo+ "DEFINE " + mUtilitario.getDefine_Definicao(mAST) + " -->> DUPLICADA");

                } else {
                    mAlocados_Aqui.add(mAST.getNome());
                    mensagem(aFixo + "DEFINE " + mUtilitario.getDefine_Definicao(mAST) + " -->> OK");
                }
            } else if (mAST.mesmoTipo("MOCKIZ")) {

                if (mAlocados_Aqui.contains(mAST.getNome())) {
                    errar(aFixo + " " +"Definicao Duplicada : " + mAST.getNome());
                    mensagem(aFixo +  " " +ePrefixo + "MOCKIZ " + mUtilitario.getDefine_Definicao(mAST) + " -->> DUPLICADA");

                } else {
                    mAlocados_Aqui.add(mAST.getNome());
                    mensagem(aFixo  +ePrefixo + "MOCKIZ " + mUtilitario.getDefine_Definicao(mAST) + " -->> OK");
                }
            } else if (mAST.mesmoTipo("DEF")) {

                if (mAlocados_Aqui.contains(mAST.getNome())) {
                    errar(aFixo  +"Definicao Duplicada : " + mAST.getNome());
                    mensagem(aFixo  + " " +ePrefixo + "DEF " + mUtilitario.getDefine_Definicao(mAST) + " -->> DUPLICADA");

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

                mensagem(aFixo  + " " +ePrefixo + "ACTION " + mUtilitario.getAction_Definicao(mAST));

                checarAlocacao(aFixo + " ACTION " + mUtilitario.getAction_Definicao(mAST) + " -> ",ePrefixo + "\t", mAST.getBranch("BODY"));

            } else if (mAST.mesmoTipo("FUNCTION")) {

                mensagem(aFixo + " " +ePrefixo + "FUNCTION " + mUtilitario.getFunction_Definicao(mAST));

                checarAlocacao(aFixo+ " FUNCTION " + mUtilitario.getFunction_Definicao(mAST) + " -> ",ePrefixo + "\t", mAST.getBranch("BODY"));
            } else if (mAST.mesmoTipo("DIRECTOR")) {

                mensagem(aFixo + " " +ePrefixo + "DIRECTOR " + mUtilitario.getFunction_Definicao(mAST));

                checarAlocacao(aFixo+ " DIRECTOR " + mUtilitario.getFunction_Definicao(mAST) + " -> ",ePrefixo + "\t", mAST.getBranch("BODY"));

            } else if (mAST.mesmoTipo("OPERATOR")) {

                mensagem(aFixo + " " +ePrefixo + "OPERATOR " + mUtilitario.getFunction_Definicao(mAST));

                checarAlocacao(aFixo+ " OPERATOR " + mUtilitario.getFunction_Definicao(mAST) + " -> ",ePrefixo + "\t", mAST.getBranch("BODY"));

            } else if (mAST.mesmoTipo("IF")) {

                checarAlocacao(aFixo + " CONDITION  -> ",ePrefixo + "\t", mAST.getBranch("BODY"));

                for (AST sAST : mAST.getASTS()) {
                    if (sAST.mesmoTipo("OTHER")) {
                        checarAlocacao(aFixo + " CONDITION OTHER -> ",ePrefixo + "\t", sAST.getBranch("BODY"));
                    } else if (sAST.mesmoTipo("OTHERS")) {
                        checarAlocacao(aFixo + " CONDITION OTHERS -> ",ePrefixo + "\t", sAST);
                    }
                }
            } else if (mAST.mesmoTipo("WHILE")) {

                checarAlocacao(aFixo + " " +" WHILE -> ",ePrefixo + "\t", mAST.getBranch("BODY"));

            } else if (mAST.mesmoTipo("STRUCT")) {

                AST mExtended = mAST.getBranch("EXTENDED");
                if (mExtended.mesmoNome("STRUCT")) {

                   mensagem(aFixo  + " " +ePrefixo + "STRUCT : "+ mAST.getNome());

                   checarAlocacao(aFixo + "\t" + " " +"STRUCT " + mAST.getNome() + " -> ",ePrefixo + "\t", mAST.getBranch("BODY"));


                }
            }

        }

    }


    private void errar(String eErro) {
        mPosProcessador.getErros().add(eErro);
    }

    private void mensagem(String eMensagem) {

        if (mPosProcessador.getDebug_Alocador()) {
            mPosProcessador.mensagem(eMensagem);
        }
    }


}
