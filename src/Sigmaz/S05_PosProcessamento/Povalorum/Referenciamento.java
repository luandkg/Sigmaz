package Sigmaz.S05_PosProcessamento.Povalorum;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Mensageiro;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco_Pacote;

import java.util.ArrayList;

public class Referenciamento {

    private Mensageiro mMensageiro;

    public Referenciamento(Mensageiro eMensageiro) {

        mMensageiro = eMensageiro;


    }

    public void processar_Refers(String ePrefixo, ArrayList<Pronoco_Pacote> mPacotes, AST eASTPai, Pronoco mAtribuindo) {

        ArrayList<String> mRefers = new ArrayList<String>();

        for (AST eAST : eASTPai.getASTS()) {
            if (eAST.mesmoTipo("REFER")) {

                String eRefer = eAST.getNome();

                if (!mRefers.contains(eRefer)) {
                    mRefers.add(eRefer);


                    mMensageiro.mensagem(ePrefixo+"REFER " + eRefer);

                    boolean enc = false;

                    for (Pronoco_Pacote ePacote : mPacotes) {

                        if (ePacote.getNome().contentEquals(eRefer)) {

                            Simbolismo ms = new Simbolismo(mMensageiro);
                            ms.realizarSimbolismo(ePrefixo,ePacote.getAST(),mAtribuindo);

                            enc = true;
                            break;
                        }

                    }

                    if (!enc) {

                        mMensageiro.errar("Pacote nao encontrado : " + eRefer);

                    }
                }

            }
        }


    }


}