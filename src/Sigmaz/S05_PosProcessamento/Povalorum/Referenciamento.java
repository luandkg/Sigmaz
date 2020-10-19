package Sigmaz.S05_PosProcessamento.Povalorum;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Mensageiro;
import Sigmaz.S05_PosProcessamento.Processadores.Valorador;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco_Pacote;

import java.util.ArrayList;

public class Referenciamento {

    private Valorador mValorador;

    public Referenciamento(Valorador eValorador) {

        mValorador = eValorador;


    }

    public void processar_Refers(String ePrefixo, ArrayList<Pronoco_Pacote> mPacotes, AST eASTPai, Pronoco mAtribuindo) {

        ArrayList<String> mRefers = new ArrayList<String>();

        for (AST eAST : eASTPai.getASTS()) {
            if (eAST.mesmoTipo("REFER")) {

                String eRefer = eAST.getNome();

                if (!mRefers.contains(eRefer)) {
                    mRefers.add(eRefer);


                    mValorador.mensagem(ePrefixo+"REFER " + eRefer);

                    boolean enc = false;

                    for (Pronoco_Pacote ePacote : mPacotes) {

                        if (ePacote.getNome().contentEquals(eRefer)) {

                            Simbolismo ms = new Simbolismo(mValorador);
                            ms.realizarSimbolismo(ePrefixo,ePacote.getAST(),mAtribuindo);

                            enc = true;
                            break;
                        }

                    }

                    if (!enc) {

                        mValorador.errar("Pacote nao encontrado : " + eRefer);

                    }
                }

            }
        }


    }


}
