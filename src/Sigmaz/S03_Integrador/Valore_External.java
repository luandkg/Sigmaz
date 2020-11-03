package Sigmaz.S03_Integrador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Visualizador.SigmazExternal;


public class Valore_External {

    Integrador mIntegrador;
    Valoramento mValoramento;

    public Valore_External(Integrador eIntegrador, Valoramento eValoramento) {
        mValoramento = eValoramento;
        mIntegrador = eIntegrador;
    }

    public void emExtern(String ePrefixo, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "Avaliando EXTERNAL " + mValue.getNome());


        if (mIntegralizante.existeExtern(mValue.getNome())) {

            if (mValue.existeBranch("INTERNAL")) {
                emInternalExtern_ComRetorno(ePrefixo + "\t", mIntegralizante.getExternal(mValue.getNome()), mValue.getBranch("INTERNAL"), mIntegralizante, mEscopo);
            }

        } else {
            mIntegrador.errar(" Extern Desconhecido : " + mValue.getNome());
        }


    }


    public void emExternSemRetorno(String ePrefixo, AST mValue,Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "Avaliando EXTERNAL " + mValue.getNome());


        if (mIntegralizante.existeExtern(mValue.getNome())) {

            if (mValue.existeBranch("INTERNAL")) {
                emInternalExternSemRetorno(ePrefixo + "\t", mIntegralizante.getExternal(mValue.getNome()), mValue.getBranch("INTERNAL"), mIntegralizante,mEscopo);
            }

        } else {
            mIntegrador.errar(" Extern Desconhecido : " + mValue.getNome());
        }


    }

    public void emInternalExtern_ComRetorno(String ePrefixo, SigmazExternal mExtern, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "Avaliando Internal Extern Tipo " + mValue.getValor());
        mIntegrador.mensagem(ePrefixo + "Avaliando Internal Extern Campo " + mValue.getNome());


        if (mValue.mesmoValor("STRUCT_FUNCT")) {

            mIntegrador.mensagem(ePrefixo + "Avaliando Internal Extern STRUCT_FUNCT " + mValue.getNome());

            if (mExtern.existeFunction(mValue.getNome())) {

            } else {

                mIntegrador.errar( "Funcao Explicit nao existente : " + mExtern.getNome() + "->" + mValue.getNome());

            }


            for (AST mAST : mValue.getBranch("ARGUMENTS").getASTS()) {

                mValoramento.valore(ePrefixo, mAST, mIntegralizante, mEscopo);

            }

            if (mValue.existeBranch("INTERNAL")) {
                Valore_Struct mValore_Struct = new Valore_Struct(mIntegrador, mValoramento);
                mValore_Struct.emInternal(ePrefixo, mValue.getBranch("INTERNAL"), mIntegralizante, mEscopo);
            }

        } else if (mValue.mesmoValor("STRUCT_OBJECT")) {

            if (mExtern.existeCampo(mValue.getNome())) {

            } else {
                mIntegrador.errar( "Campo Explicit nao existente : " + mExtern.getNome() + "->" + mValue.getNome());
            }

            if (mValue.existeBranch("INTERNAL")) {
                Valore_Struct mValore_Struct = new Valore_Struct(mIntegrador, mValoramento);
                mValore_Struct. emInternal(ePrefixo, mValue.getBranch("INTERNAL"), mIntegralizante, mEscopo);
            }

        } else {
            mIntegrador.errar( " Internal Extern Tipo Desconhecido : " + mValue.getValor());
        }

    }

    public void emInternalExternSemRetorno(String ePrefixo, SigmazExternal mExtern, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "\t Tipo " + mValue.getValor());
        mIntegrador.mensagem(ePrefixo + "\t Campo " + mValue.getNome());


        if (mValue.mesmoValor("STRUCT_FUNCT")) {

            mIntegrador.mensagem(ePrefixo + "\t STRUCT_FUNCT " + mValue.getNome());

            if (mExtern.existeActionFunction(mValue.getNome())) {

            } else {

                mIntegrador.errar(mExtern.getNome() + " -> " + mValue.getNome() + " : Acao Explicita ou Funcao Explicita Nao existente !");

            }


            for (AST mAST : mValue.getBranch("ARGUMENTS").getASTS()) {

                mValoramento.valore(ePrefixo, mAST,mIntegralizante, mEscopo);

            }

            if (mValue.existeBranch("INTERNAL")) {
                Valore_Struct mValore_Struct = new Valore_Struct(mIntegrador, mValoramento);

                mValore_Struct.  emInternal(ePrefixo, mValue.getBranch("INTERNAL"), mIntegralizante, mEscopo);
            }

        } else if (mValue.mesmoValor("STRUCT_OBJECT")) {

            if (mExtern.existeCampo(mValue.getNome())) {

            } else {
                mIntegrador.errar( " Campo Explicit nao existente : " + mExtern.getNome() + "->" + mValue.getNome());
            }

            if (mValue.existeBranch("INTERNAL")) {
                Valore_Struct mValore_Struct = new Valore_Struct(mIntegrador, mValoramento);

                mValore_Struct.   emInternal(ePrefixo, mValue.getBranch("INTERNAL"), mIntegralizante, mEscopo);
            }

        } else {
            mIntegrador.errar( " Internal Extern Tipo Desconhecido : " + mValue.getValor());
        }

    }

}
