package Sigmaz.S05_PosProcessamento.Processadores;

import Sigmaz.S05_PosProcessamento.Valore.*;
import Sigmaz.S05_PosProcessamento.PosProcessador;
import Sigmaz.S05_PosProcessamento.Pronoco.*;
import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class Valorador {

    private PosProcessador mPosProcessador;

    public Valorador(PosProcessador ePosProcessador) {

        mPosProcessador = ePosProcessador;

    }



    public void init(ArrayList<AST> mTodos, ArrayList<AST> mRequisicoes) {

        mPosProcessador.mensagem("");
        mPosProcessador.mensagem(" ------------------ FASE VALORADOR ----------------------- ");
        mPosProcessador.mensagem("");


        Pronoco mAtribuindo = new Pronoco("<<SIGMAZ>>");

        ArrayList<Pronoco_Pacote> mPacotes = new ArrayList<Pronoco_Pacote>();


        Simbolismo mSimbolismo = new Simbolismo(mPosProcessador.getMensageiro());
        Valoramento mValoramento = new Valoramento(mPosProcessador.getMensageiro());
        Referenciamento mReferenciamento = new Referenciamento(mPosProcessador.getMensageiro());

        for (AST mAST : mTodos) {

            if (mAST.mesmoTipo("SIGMAZ")) {

                mPosProcessador.mensagem("");

                for (AST eAlgumaCoisa : mAST.getASTS()) {
                    if (eAlgumaCoisa.mesmoTipo("PACKAGE")) {
                        mPacotes.add(new Pronoco_Pacote(eAlgumaCoisa));
                    }
                }

                if (mPacotes.size() > 0) {

                    mPosProcessador.mensagem("PACOTES : " + mPacotes.size());
                    mPosProcessador.mensagem("");
                    int pi = 1;

                    for (Pronoco_Pacote ePacote : mPacotes) {
                        mPosProcessador.mensagem("\t - " + pi + " : " + ePacote.getNome());
                        pi += 1;
                    }

                    mPosProcessador.mensagem("");

                }

                mPosProcessador.mensagem("SIGMAZ");
                mPosProcessador.mensagem("");

                for (AST eAST : mRequisicoes) {
                    mSimbolismo.realizarSimbolismoExterno("\t", eAST, mAtribuindo);
                }

                if (mPosProcessador.temErros()) {
                    return;
                }

                mReferenciamento.processar_Refers("\t", mPacotes, mAST, mAtribuindo);
                mSimbolismo.realizarSimbolismo("\t", mAST, mAtribuindo);
                mValoramento.realizarValoramento("\t", mAST, mAtribuindo);


                for (Pronoco_Pacote ePacote : mPacotes) {

                    mPosProcessador.mensagem("Pacote : " + ePacote.getNome());


                    mReferenciamento.processar_Refers("\t\t", mPacotes, ePacote.getAST(), mAtribuindo);

                    if (mPosProcessador.temErros()) {
                        return;
                    }

                    mSimbolismo.realizarSimbolismo("\t\t", ePacote.getAST(), mAtribuindo);
                    mValoramento.realizarValoramento("\t\t", ePacote.getAST(), mAtribuindo);


                    if (mPosProcessador.getErros().size() > 0) {
                        break;
                    }
                }

            }
        }


    }


}
