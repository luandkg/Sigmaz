package Sigmaz.S05_PosProcessamento.Povalorum;

import Sigmaz.S00_Utilitarios.Mensageiro;
import Sigmaz.S05_PosProcessamento.Processadores.Valorador;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco;
import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class Valore_Value {

    Valorador mValorador;
    Valoramento mValoramento;

    private ArrayList<String> mRegistradores;

    public Valore_Value(Valorador eValorador, Valoramento eValoramento) {
        mValorador = eValorador;
        mValoramento = eValoramento;

        mRegistradores = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            mRegistradores.add("R" + i);
        }
    }

    public void valore_ID(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        if (mValue.mesmoNome("true") || mValue.mesmoNome("false") || mValue.mesmoNome("null")) {


        } else {

            if (!mAtribuindo.existeAteAqui(mValue.getNome())) {

                mValorador.errar("Variavel nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());
                mValorador.mensagem(ePrefixo + "Variavel nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());


            }

        }

    }

    public void valore_ActionFunct(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        if (mAtribuindo.existeActionFunction(mValue.getNome())) {


        } else {

            mValorador.errar("Funcao ou Ação nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());
            mValorador.mensagem(ePrefixo + "Funcao ou Ação nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());


        }

        for (AST mAST : mValue.getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT") && mAST.mesmoValor("FUNCT")) {

                mValoramento.valore(ePrefixo, mAST, mAtribuindo);


            }
        }

    }

    public void valore_Funct(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        if (mAtribuindo.existeFunction(mValue.getNome())) {


        } else {

            mValorador.errar("Funcao nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());
            mValorador.mensagem(ePrefixo + "Funcao nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());


        }

        for (AST mAST : mValue.getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT") && mAST.mesmoValor("FUNCT")) {

                mValoramento.valore(ePrefixo, mAST, mAtribuindo);


            }
        }

    }

    public void valore_Operator(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValoramento.valore(ePrefixo, mValue.getBranch("LEFT"), mAtribuindo);
        mValoramento.valore(ePrefixo, mValue.getBranch("RIGHT"), mAtribuindo);

    }

    public void valore_Director(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValoramento.valore(ePrefixo, mValue.getBranch("VALUE"), mAtribuindo);

    }

    public void valore_This(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + mValue.getImpressao());

    }

    public void valore_Ternal(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValoramento.valore(ePrefixo, mValue.getBranch("CONDITION"), mAtribuindo);

        if (mValue.existeBranch("TRUE")) {
            mValoramento.valore(ePrefixo, mValue.getBranch("TRUE"), mAtribuindo);
        }

        if (mValue.existeBranch("FALSE")) {
            mValoramento.valore(ePrefixo, mValue.getBranch("FALSE"), mAtribuindo);
        }

    }

    public void valore_Reg(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Valorando Registrador : " + mValue.getNome());

        if (mRegistradores.contains(mValue.getNome())) {

        } else {

            mValorador.errar("Registrador Desconhecido : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());
            mValorador.mensagem(ePrefixo + "Registrador : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());


        }


    }

    public void valore_ExecuteLocal(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Valorando Execute Local");

        for (AST mAST : mValue.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {
                mValoramento.valore(ePrefixo, mAST, mAtribuindo);
            }
        }
    }

    public void valore_Marker(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Valorando Marker : " + mValue.getBranch("MARK").getNome());

        mValoramento.valore(ePrefixo+"\t", mValue.getBranch("VALUE"), mAtribuindo);

    }

    public void valore_Default(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Valorando Default ");


    }

    public void valore_Vector(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + " VECTOR : " + mAtribuindo.getRegressivo() );

        for(AST oAST : mValue.getASTS()){

            mValoramento.  valore(ePrefixo+"\t",oAST,mAtribuindo);

        }


    }


}
