package Sigmaz.S05_PosProcessamento.Processadores;

import Sigmaz.S05_PosProcessamento.Povalorum.*;
import Sigmaz.S05_PosProcessamento.PosProcessador;
import Sigmaz.S05_PosProcessamento.Pronoco.*;
import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class Valorador {

    private PosProcessador mPosProcessador;

    public Valorador(PosProcessador ePosProcessador) {

        mPosProcessador = ePosProcessador;

    }

    public void mensagem(String e) {
        if (mPosProcessador.getDebug_Valorador()) {
            mPosProcessador.mensagem(e);
        }
    }

    public void errar(String e) {
        mPosProcessador.errar(e);
    }

    public ArrayList<String> getErros() {
        return mPosProcessador.getErros();
    }

    public void init(ArrayList<AST> mTodos, ArrayList<AST> mRequisicoes) {

        mensagem("");
        mensagem(" ------------------ FASE VALORADOR ----------------------- ");
        mensagem("");


        Pronoco mAtribuindo = new Pronoco("<<SIGMAZ>>");


        Simbolismo mSimbolismo = new Simbolismo(this);
        Valoramento mValoramento = new Valoramento(this);
        Referenciamento mReferenciamento = new Referenciamento(this);

        ArrayList<Pronoco_Pacote> mPacotes = new ArrayList<Pronoco_Pacote>();


        for (AST mRequisicao : mPosProcessador.getRequisicoes()) {

            mensagem("Biblioteca Externa");


            mSimbolismo.realizarSimbolismoExterno("\t", mRequisicao, mAtribuindo);

            for (AST eAlgumaCoisa : mRequisicao.getASTS()) {
                if (eAlgumaCoisa.mesmoTipo("PACKAGE")) {
                    mPacotes.add(new Pronoco_Pacote(eAlgumaCoisa));
                }
            }


        }


        for (AST mAST : mTodos) {

            if (mAST.mesmoTipo("SIGMAZ")) {

                unidade(mSimbolismo, mValoramento, mReferenciamento, mPacotes, mAtribuindo, mAST);

            }
        }


    }

    public void unidade(Simbolismo mSimbolismo, Valoramento mValoramento, Referenciamento mReferenciamento, ArrayList<Pronoco_Pacote> mPacotes, Pronoco mPronoco, AST mAST) {


        mPosProcessador.mensagem("");

        for (AST eAlgumaCoisa : mAST.getASTS()) {
            if (eAlgumaCoisa.mesmoTipo("PACKAGE")) {
                mPacotes.add(new Pronoco_Pacote(eAlgumaCoisa));
            }
        }

        if (mPacotes.size() > 0) {

            mensagem("PACOTES : " + mPacotes.size());
            mensagem("");
            int pi = 1;

            for (Pronoco_Pacote ePacote : mPacotes) {
                mensagem("\t - " + pi + " : " + ePacote.getNome());
                pi += 1;
            }

            mensagem("");

        }

        mensagem("SIGMAZ");
        mensagem("");


        if (mPosProcessador.temErros()) {
            return;
        }

        mensagem("REFERS : ");

        mReferenciamento.processar_Refers("\t", mPacotes, mAST, mPronoco);

        mensagem("SIMBOLISMO : ");

        mSimbolismo.realizarSimbolismo("\t", mAST, mPronoco);

        mensagem("VALORAMENTO : ");

        mValoramento.realizarValoramento("\t", mAST, mPronoco);


        for (Pronoco_Pacote ePacote : mPacotes) {

            mensagem("Pacote : " + ePacote.getNome());


            mReferenciamento.processar_Refers("\t\t", mPacotes, ePacote.getAST(), mPronoco);

            if (mPosProcessador.temErros()) {
                return;
            }

            mSimbolismo.realizarSimbolismo("\t\t", ePacote.getAST(), mPronoco);
            mValoramento.realizarValoramento("\t\t", ePacote.getAST(), mPronoco);


            if (mPosProcessador.getErros().size() > 0) {
                break;
            }
        }

    }

}
