package Sigmaz.S05_PosProcessamento.Povalorum;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S05_PosProcessamento.Processadores.Valorador;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco_Extern;

public class Valore_External {

    Valorador mValorador;
    Valoramento mValoramento;

    public Valore_External(Valorador eValorador, Valoramento eValoramento) {
        mValoramento = eValoramento;
        mValorador = eValorador;
    }

    public void emExtern(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Avaliando EXTERNAL " + mValue.getNome());


        if (mAtribuindo.existeExtern(mValue.getNome())) {

            if (mValue.existeBranch("INTERNAL")) {
                emInternalExtern_ComRetorno(ePrefixo + "\t", mAtribuindo.getExtern(mValue.getNome()), mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else {
            mValorador.errar(" Extern Desconhecido : " + mValue.getNome());
        }


    }


    public void emExternSemRetorno(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Avaliando EXTERNAL " + mValue.getNome());


        if (mAtribuindo.existeExtern(mValue.getNome())) {

            if (mValue.existeBranch("INTERNAL")) {
                emInternalExternSemRetorno(ePrefixo + "\t", mAtribuindo.getExtern(mValue.getNome()), mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else {
            mValorador.errar(" Extern Desconhecido : " + mValue.getNome());
        }


    }

    public void emInternalExtern_ComRetorno(String ePrefixo, Pronoco_Extern mExtern, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Avaliando Internal Extern Tipo " + mValue.getValor());
        mValorador.mensagem(ePrefixo + "Avaliando Internal Extern Campo " + mValue.getNome());


        if (mValue.mesmoValor("STRUCT_FUNCT")) {

            mValorador.mensagem(ePrefixo + "Avaliando Internal Extern STRUCT_FUNCT " + mValue.getNome());

            if (mExtern.existeFunction(mValue.getNome())) {

            } else {

                mValorador.errar( "Funcao Explicit nao existente : " + mExtern.getNome() + "->" + mValue.getNome());

            }


            for (AST mAST : mValue.getBranch("ARGUMENTS").getASTS()) {

                mValoramento.valore(ePrefixo, mAST, mAtribuindo);

            }

            if (mValue.existeBranch("INTERNAL")) {
                Valore_Struct mValore_Struct = new Valore_Struct(mValorador, mValoramento);
                mValore_Struct.emInternal(ePrefixo, mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else if (mValue.mesmoValor("STRUCT_OBJECT")) {

            if (mExtern.existeCampo(mValue.getNome())) {

            } else {
                mValorador.errar( "Campo Explicit nao existente : " + mExtern.getNome() + "->" + mValue.getNome());
            }

            if (mValue.existeBranch("INTERNAL")) {
                Valore_Struct mValore_Struct = new Valore_Struct(mValorador, mValoramento);
                mValore_Struct. emInternal(ePrefixo, mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else {
            mValorador.errar( " Internal Extern Tipo Desconhecido : " + mValue.getValor());
        }

    }

    public void emInternalExternSemRetorno(String ePrefixo, Pronoco_Extern mExtern, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "\t Tipo " + mValue.getValor());
        mValorador.mensagem(ePrefixo + "\t Campo " + mValue.getNome());


        if (mValue.mesmoValor("STRUCT_FUNCT")) {

            mValorador.mensagem(ePrefixo + "\t STRUCT_FUNCT " + mValue.getNome());

            if (mExtern.existeActionFunction(mValue.getNome())) {

            } else {

                mValorador.errar(mExtern.getNome() + " -> " + mValue.getNome() + " : Acao Explicita ou Funcao Explicita Nao existente !");

            }


            for (AST mAST : mValue.getBranch("ARGUMENTS").getASTS()) {

                mValoramento.valore(ePrefixo, mAST, mAtribuindo);

            }

            if (mValue.existeBranch("INTERNAL")) {
                Valore_Struct mValore_Struct = new Valore_Struct(mValorador, mValoramento);

                mValore_Struct.  emInternal(ePrefixo, mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else if (mValue.mesmoValor("STRUCT_OBJECT")) {

            if (mExtern.existeCampo(mValue.getNome())) {

            } else {
                mValorador.errar( " Campo Explicit nao existente : " + mExtern.getNome() + "->" + mValue.getNome());
            }

            if (mValue.existeBranch("INTERNAL")) {
                Valore_Struct mValore_Struct = new Valore_Struct(mValorador, mValoramento);

                mValore_Struct.   emInternal(ePrefixo, mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else {
            mValorador.errar( " Internal Extern Tipo Desconhecido : " + mValue.getValor());
        }

    }

}
