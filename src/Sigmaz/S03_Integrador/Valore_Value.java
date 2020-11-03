package Sigmaz.S03_Integrador;

import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class Valore_Value {

    Integrador mIntegrador;
    Valoramento mValoramento;

    private ArrayList<String> mRegistradores;

    public Valore_Value(Integrador eIntegrador, Valoramento eValoramento) {
        mIntegrador = eIntegrador;
        mValoramento = eValoramento;

        mRegistradores = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            mRegistradores.add("R" + i);
        }
    }

    public void valore_ID(String ePrefixo, AST mValue, Escopante mEscopo) {

        if (mValue.mesmoNome("true") || mValue.mesmoNome("false") || mValue.mesmoNome("null")) {


        } else {

            if (!mEscopo.existeAteAqui(mValue.getNome())) {

                mIntegrador.errar("Variavel nao existente : " + mEscopo.getRegressivo() + " :: " + mValue.getNome());
                mIntegrador.mensagem(ePrefixo + "Variavel nao existente : " + mEscopo.getRegressivo() + " :: " + mValue.getNome());


            }

        }

    }

    public void valore_ActionFunct(String ePrefixo, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {

        if (mIntegralizante.existeActionFunction(mValue.getNome())) {


        } else {

            mIntegrador.errar("Funcao ou Ação nao existente : " + mEscopo.getRegressivo() + " :: " + mValue.getNome());
            mIntegrador.mensagem(ePrefixo + "Funcao ou Ação nao existente : " + mEscopo.getRegressivo() + " :: " + mValue.getNome());


        }

        for (AST mAST : mValue.getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT") && mAST.mesmoValor("FUNCT")) {

                mValoramento.valore(ePrefixo, mAST, mIntegralizante,mEscopo);


            }
        }

    }

    public void valore_Funct(String ePrefixo, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {

        if (mIntegralizante.existeFunction(mValue.getNome())) {


        } else {

            mIntegrador.errar("Funcao nao existente : " + mEscopo.getRegressivo() + " :: " + mValue.getNome());
            mIntegrador.mensagem(ePrefixo + "Funcao nao existente : " + mEscopo.getRegressivo() + " :: " + mValue.getNome());


        }

        for (AST mAST : mValue.getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT") && mAST.mesmoValor("FUNCT")) {

                mValoramento.valore(ePrefixo, mAST, mIntegralizante,mEscopo);


            }
        }

    }

    public void valore_Operator(String ePrefixo, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {

        mValoramento.valore(ePrefixo, mValue.getBranch("LEFT"),mIntegralizante, mEscopo);
        mValoramento.valore(ePrefixo, mValue.getBranch("RIGHT"),mIntegralizante, mEscopo);

    }

    public void valore_Director(String ePrefixo, AST mValue,  Integralizante mIntegralizante, Escopante mEscopo) {

        mValoramento.valore(ePrefixo, mValue.getBranch("VALUE"),mIntegralizante, mEscopo);

    }

    public void valore_This(String ePrefixo, AST mValue,  Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + mValue.getImpressao());

    }

    public void valore_Ternal(String ePrefixo, AST mValue,  Integralizante mIntegralizante, Escopante mEscopo) {

        mValoramento.valore(ePrefixo, mValue.getBranch("CONDITION"),mIntegralizante, mEscopo);

        if (mValue.existeBranch("TRUE")) {
            mValoramento.valore(ePrefixo, mValue.getBranch("TRUE"),mIntegralizante, mEscopo);
        }

        if (mValue.existeBranch("FALSE")) {
            mValoramento.valore(ePrefixo, mValue.getBranch("FALSE"),mIntegralizante, mEscopo);
        }

    }

    public void valore_Reg(String ePrefixo, AST mValue,  Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "Valorando Registrador : " + mValue.getNome());

        if (mRegistradores.contains(mValue.getNome())) {

        } else {

            mIntegrador.errar("Registrador Desconhecido : " + mEscopo.getRegressivo() + " :: " + mValue.getNome());
            mIntegrador.mensagem(ePrefixo + "Registrador : " + mEscopo.getRegressivo() + " :: " + mValue.getNome());


        }


    }

    public void valore_ExecuteLocal(String ePrefixo, AST mValue,  Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "Valorando Execute Local");

        for (AST mAST : mValue.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {
                mValoramento.valore(ePrefixo, mAST, mIntegralizante,mEscopo);
            }
        }
    }

    public void valore_Marker(String ePrefixo, AST mValue,  Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "Valorando Marker : " + mValue.getBranch("MARK").getNome());

        mValoramento.valore(ePrefixo+"\t", mValue.getBranch("VALUE"), mIntegralizante,mEscopo);

    }

    public void valore_Default(String ePrefixo, AST mValue,  Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "Valorando Default ");


    }

    public void valore_Vector(String ePrefixo, AST mValue,  Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + " VECTOR : " + mEscopo.getRegressivo() );

        for(AST oAST : mValue.getASTS()){

            mValoramento.  valore(ePrefixo+"\t",oAST,mIntegralizante,mEscopo);

        }


    }


}
