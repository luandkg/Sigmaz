package Sigmaz.S05_PosProcessamento.Processadores;

import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.Mensageiro;
import Sigmaz.S05_PosProcessamento.Potiparum.Potiparum;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco_Pacote;
import Sigmaz.S07_Executor.Debuggers.Simplificador;
import Sigmaz.S05_PosProcessamento.PosProcessador;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco;
import Sigmaz.S00_Utilitarios.AST;

public class Tipador {

    private PosProcessador mPosProcessador;
    private Mensageiro mMensageiro;

    private ArrayList<String> mPrimitivos;

    private Simplificador mSimplificador;
    private Potiparum mPotiparum;


    public Tipador(PosProcessador eAnalisador) {

        mSimplificador = new Simplificador();
        mPosProcessador = eAnalisador;
        mMensageiro = mPosProcessador.getMensageiro();
        mPotiparum = new Potiparum(this);

        mPrimitivos = new ArrayList<String>();
        mPrimitivos.add("num");
        mPrimitivos.add("string");
        mPrimitivos.add("bool");
        mPrimitivos.add("int");
        mPrimitivos.add("any");


    }

    public Simplificador getSimplificador() {
        return mSimplificador;
    }


    public Potiparum getPotiparum() {
        return mPotiparum;
    }

    public boolean getDebug(){return mPosProcessador.getDebug_Tipador();}
    public void mensagem(String e) {
        if (mPosProcessador.getDebug_Tipador()) {
            mPosProcessador.mensagem(e);
        }
    }

    public void errar(String e) {
        mPosProcessador.errar(e);
    }

    public void init(ArrayList<AST> mASTS) {


        mensagem("");
        mensagem(" ------------------ FASE TIPADOR ----------------------- ");
        mensagem("");

        Pronoco mPronoco = new Pronoco("SIGMAZ");


        for (String e : mPrimitivos) {
            mensagem("\t - TIPO PRIMITIVO :  " + e);

            mPronoco.adicionarPrimitivo(e);
        }

        mensagem("");

        ArrayList<AST> mPacotes = new ArrayList<AST>();

        for (AST mRequisicao : mPosProcessador.getRequisicoes()) {

            mensagem("Biblioteca Externa");


            for (AST eAlgumaCoisa : mRequisicao.getASTS()) {
                if (eAlgumaCoisa.mesmoTipo("PACKAGE")) {
                    mPacotes.add(eAlgumaCoisa);
                }
            }


        }


        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {


                for (AST mAST : ASTCGlobal.getASTS()) {
                    if (mAST.mesmoTipo("PACKAGE")) {
                        mPacotes.add(mAST);
                    }
                }


                // chegarTipagens("\t", mAncestrais, ASTCGlobal);

                getPotiparum().chegarTipagens_novo("\t", mPacotes, mPronoco, ASTCGlobal);

                for (AST mAST : mPacotes) {
                    mensagem("\t" + "Sigmaz Package :  " + mAST.getNome());

                    Pronoco mPronocoPacote = new Pronoco(mAST.getNome());
                    mPronocoPacote.setSuperior(mPronoco);

                    getPotiparum().chegarTipagens_novo("\t\t", mPacotes, mPronocoPacote, mAST);
                }

            } else {

                mMensageiro.errar("Escopo Desconhecido : " + ASTCGlobal.getTipo());

            }

        }


    }


    public void Usar(ArrayList<AST> mPacotes, String ePrefixo, String eNome, Pronoco mPronoco) {


        boolean enc = false;


        for (AST ASTPackage : mPacotes) {

            if (ASTPackage.mesmoNome(eNome)) {

                for (AST mAST : ASTPackage.getASTS()) {

                    if (mAST.mesmoTipo("CAST")) {

                        mPronoco.adicionarCast(mAST.getNome());

                        mensagem(ePrefixo + "- Tipo CAST : " + ASTPackage.getNome() + "<>" + mAST.getNome());

                    } else if (mAST.mesmoTipo("STRUCT")) {


                        AST mExtended = mAST.getBranch("EXTENDED");

                        if (mExtended.mesmoNome("STRUCT")) {

                            mensagem(ePrefixo + "- Tipo STRUCT : " + ASTPackage.getNome() + "<>" + mAST.getNome());
                            mPronoco.adicionarStruct(mAST.getNome());

                        } else if (mExtended.mesmoNome("STAGES")) {
                            mensagem(ePrefixo + "- Tipo STAGE : " + ASTPackage.getNome() + "<>" + mAST.getNome());
                            mPronoco.adicionarStage(mAST.getNome());

                        } else if (mExtended.mesmoNome("TYPE")) {
                            mensagem(ePrefixo + "- Tipo TYPE : " + ASTPackage.getNome() + "<>" + mAST.getNome());
                            mPronoco.adicionarType(mAST.getNome());

                        } else if (mExtended.mesmoNome("MODEL")) {
                            mensagem(ePrefixo + "- Tipo MODEL : " + ASTPackage.getNome() + "<>" + mAST.getNome());
                            mPronoco.adicionarModel(mAST.getNome());

                        }

                    }

                }

                enc = true;
                break;
            }

        }

        if (!enc) {
            mMensageiro.errar("Package " + eNome + " : Nao encontrado !");
        }


    }


}
