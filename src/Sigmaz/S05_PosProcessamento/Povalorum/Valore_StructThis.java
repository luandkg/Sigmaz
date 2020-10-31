package Sigmaz.S05_PosProcessamento.Povalorum;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S05_PosProcessamento.Processadores.Valorador;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco;

public class Valore_StructThis {

    Valorador mValorador;
    Valoramento mValoramento;

    public Valore_StructThis(Valorador eValorador, Valoramento eValoramento) {
        mValoramento = eValoramento;
        mValorador = eValorador;
    }
    public void emStruct(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Avaliando STRUCT THIS Com Variavel " + mValue.getNome());


        if (mAtribuindo.existeAteAqui(mValue.getNome())) {


            if (mValue.existeBranch("INTERNAL")) {
                emInternal(ePrefixo+"\t", mValue.getBranch("INTERNAL"), mAtribuindo);
            }


        } else if (mAtribuindo.existeAteAqui(mValue.getNome())) {

            if (mValue.existeBranch("INTERNAL")) {
                emInternal(ePrefixo+"\t", mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else {

            mValorador.errar("Variavel nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());
            mValorador.mensagem(ePrefixo + "Variavel nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());


        }


    }

    public void emInternal(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Avaliando Internal Tipo " + mValue.getValor());
        mValorador.mensagem(ePrefixo + "Avaliando Internal Campo " + mValue.getNome());

        if (mValue.mesmoValor("STRUCT_FUNCT")) {

            for (AST mAST : mValue.getBranch("ARGUMENTS").getASTS()) {

                mValoramento.valore(ePrefixo, mAST, mAtribuindo);

            }

            if (mValue.existeBranch("INTERNAL")) {
                emInternal(ePrefixo, mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else if (mValue.mesmoValor("STRUCT_OBJECT")) {

            if (mValue.existeBranch("INTERNAL")) {
                emInternal(ePrefixo, mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else {
            mValorador.errar(" Internal Tipo Desconhecido : " + mValue.getValor());
        }

    }


}
