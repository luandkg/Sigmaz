package Sigmaz.S06_Integrador;

import Sigmaz.S00_Utilitarios.AST;

public class Valore_StructThis {

    Integrador mIntegrador;
    Valoramento mValoramento;

    public Valore_StructThis(Integrador eIntegrador, Valoramento eValoramento) {
        mValoramento = eValoramento;
        mIntegrador = eIntegrador;
    }
    public void emStruct(String ePrefixo, AST mValue, Integralizante mIntegralizante,Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "Avaliando STRUCT THIS Com Variavel " + mValue.getNome());


        if (mEscopo.existeAteAqui(mValue.getNome())) {


            if (mValue.existeBranch("INTERNAL")) {
                emInternal(ePrefixo+"\t", mValue.getBranch("INTERNAL"), mIntegralizante,mEscopo);
            }


        } else if (mEscopo.existeAteAqui(mValue.getNome())) {

            if (mValue.existeBranch("INTERNAL")) {
                emInternal(ePrefixo+"\t", mValue.getBranch("INTERNAL"),mIntegralizante, mEscopo);
            }

        } else {

            mIntegrador.errar("Variavel nao existente : " + mEscopo.getRegressivo() + " :: " + mValue.getNome());
            mIntegrador.mensagem(ePrefixo + "Variavel nao existente : " + mEscopo.getRegressivo() + " :: " + mValue.getNome());


        }


    }

    public void emInternal(String ePrefixo, AST mValue, Integralizante mIntegralizante,Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "Avaliando Internal Tipo " + mValue.getValor());
        mIntegrador.mensagem(ePrefixo + "Avaliando Internal Campo " + mValue.getNome());

        if (mValue.mesmoValor("STRUCT_FUNCT")) {

            for (AST mAST : mValue.getBranch("ARGUMENTS").getASTS()) {

                mValoramento.valore(ePrefixo, mAST,mIntegralizante, mEscopo);

            }

            if (mValue.existeBranch("INTERNAL")) {
                emInternal(ePrefixo, mValue.getBranch("INTERNAL"), mIntegralizante,mEscopo);
            }

        } else if (mValue.mesmoValor("STRUCT_OBJECT")) {

            if (mValue.existeBranch("INTERNAL")) {
                emInternal(ePrefixo, mValue.getBranch("INTERNAL"), mIntegralizante,mEscopo);
            }

        } else {
            mIntegrador.errar(" Internal Tipo Desconhecido : " + mValue.getValor());
        }

    }


}
