package Sigmaz.S06_Integrador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Visualizador.SigmazPackage;

import java.util.ArrayList;

public class Referenciamento {

    private Integrador mIntegrador;

    public Referenciamento(Integrador eIntegrador) {

        mIntegrador = eIntegrador;


    }

    public void processar_Refers(String ePrefixo, ArrayList<SigmazPackage> mPacotes, AST eASTPai, Integralizante mIntegralizante) {

        ArrayList<String> mRefers = new ArrayList<String>();

        for (AST eAST : eASTPai.getASTS()) {
            if (eAST.mesmoTipo("REFER")) {

                String eRefer = eAST.getNome();

                if (!mRefers.contains(eRefer)) {
                    mRefers.add(eRefer);


                    mIntegrador.mensagem(ePrefixo+"REFER " + eRefer);

                    boolean enc = false;

                    for (SigmazPackage ePacote : mPacotes) {

                        if (ePacote.getNome().contentEquals(eRefer)) {

                            Simbolismo ms = new Simbolismo(mIntegrador);
                            ms.realizarSimbolismo(ePrefixo,ePacote.getAST(),mIntegralizante);

                            enc = true;
                            break;
                        }

                    }

                    if (!enc) {

                        mIntegrador.errar("Pacote nao encontrado : " + eRefer);

                    }
                }

            }
        }


    }


}
