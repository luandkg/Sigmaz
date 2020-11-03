package Sigmaz.S06_Integrador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S08_Executor.Debuggers.Simplificador;

import java.util.ArrayList;

public class Valore_Sigmaz {

    Integrador mIntegrador;
    Valoramento mValoramento;

    public Valore_Sigmaz(Integrador eIntegrador, Valoramento eValoramento) {
        mIntegrador = eIntegrador;
        mValoramento = eValoramento;
    }

    public Valore_Statements getValore_Statements() {
        return mValoramento.getValore_Statements();
    }

    public Valore_ValueComplex getValore_Hiper() {
        return mValoramento.getValore_Hiper();
    }

    public Valore_Value getValore_Tipos() {
        return mValoramento.getValore_Tipos();
    }

    public Valoramento getValoramento() {
        return mValoramento;
    }

    public Simplificador getSimplificador() {
        return mValoramento.getSimplificador();
    }

    public ArrayList<String> getRegistradores() {
        return mValoramento.getRegistradores();
    }

    public Valore_Escopo getValore_Escopo() {
        return mValoramento.getValore_Escopo();
    }


    public void valore_Call(String ePrefixo, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {


        mIntegrador.mensagem(ePrefixo + "Valorando CALL : " + getSimplificador().getCall(mValue));

        Escopante mAqui = new Escopante("Call");
        mAqui.setAnterior(mEscopo);

        getValoramento().obterVarArgumentos(mValue.getBranch("SENDING").getBranch("ARGUMENTS"), mAqui);


        if (mValue.mesmoValor("REFER")) {


            if (mIntegralizante.existeActionFunction(mValue.getBranch("SENDING").getNome())) {

            } else {

                mIntegrador.errar("Action ou Function nao existente : " + mEscopo.getRegressivo() + " :: " + mValue.getBranch("SENDING").getNome());
                mIntegrador.mensagem(ePrefixo + "Action ou Function nao existente  : " + mEscopo.getRegressivo() + " :: " + mValue.getBranch("SENDING").getNome());


            }

        } else if (mValue.mesmoValor("AUTO")) {

            getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mValue.getBranch("BODY"), mIntegralizante, mAqui);

        } else {

            mIntegrador.errar("Call com Problemas : " + mEscopo.getRegressivo());

        }


    }


    public void valore_Action(String ePrefixo, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "Valorando ACTION : " + getSimplificador().getAction(mValue));

        Escopante mAqui = new Escopante(getSimplificador().getAction(mValue));
        mAqui.setAnterior(mEscopo);


        getValoramento().obterVarArgumentos(mValue.getBranch("ARGUMENTS"), mAqui);

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mValue.getBranch("BODY"), mIntegralizante,mAqui);


    }

    public void valore_Function(String ePrefixo, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {


        mIntegrador.mensagem(ePrefixo + "Valorando FUNCTION : " + getSimplificador().getFuction(mValue));

        Escopante mAqui = new Escopante(getSimplificador().getFuction(mValue));
        mAqui.setAnterior(mEscopo);


        getValoramento().obterVarArgumentos(mValue.getBranch("ARGUMENTS"), mAqui);

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mValue.getBranch("BODY"), mIntegralizante,mAqui);


    }

    public void valore_Director(String ePrefixo, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "Valorando DIRECTOR : " + getSimplificador().getDirector(mValue));

        Escopante mAqui = new Escopante(getSimplificador().getDirector(mValue));
        mAqui.setAnterior(mEscopo);


        getValoramento().obterVarArgumentos(mValue.getBranch("ARGUMENTS"), mAqui);

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mValue.getBranch("BODY"),mIntegralizante, mAqui);


    }

    public void valore_Operator(String ePrefixo, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "Valorando OPERATOR : " + getSimplificador().getOperator(mValue));

        Escopante mAqui = new Escopante(getSimplificador().getOperator(mValue));
        mAqui.setAnterior(mEscopo);


        getValoramento().obterVarArgumentos(mValue.getBranch("ARGUMENTS"), mAqui);

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mValue.getBranch("BODY"), mIntegralizante,mAqui);


    }

    public void valore_Marker(String ePrefixo, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "Valorando MARKER : " + getSimplificador().getMark(mValue));

        Escopante mAqui = new Escopante(getSimplificador().getOperator(mValue));
        mAqui.setAnterior(mEscopo);


        getValoramento().obterVarArgumentos(mValue.getBranch("ARGUMENTS"), mAqui);

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mValue.getBranch("BODY"), mIntegralizante,mAqui);


    }

    public void valore_Auto(String ePrefixo, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "Valorando AUTO : " + getSimplificador().getAuto(mValue));
        Escopante mAqui = new Escopante(getSimplificador().getAuto(mValue));
        mAqui.setAnterior(mEscopo);


        getValoramento().obterVarArgumentos(mValue.getBranch("ARGUMENTS"), mAqui);

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mValue.getBranch("BODY"),mIntegralizante, mAqui);


    }

    public void valore_Functor(String ePrefixo, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "Valorando FUNCTOR : " + getSimplificador().getFunctor(mValue));
        Escopante mAqui = new Escopante(getSimplificador().getFunctor(mValue));
        mAqui.setAnterior(mEscopo);


        getValoramento().obterVarArgumentos(mValue.getBranch("ARGUMENTS"), mAqui);

        //  getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mValue.getBranch("INITS"), mAqui);
        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mValue.getBranch("BODY"), mIntegralizante,mAqui);


    }

    public void valore_Define(String ePrefixo, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "Valorando DEFINE : " + getSimplificador().getDefine(mValue));

        if (mValue.existeBranch("VALUE")) {

            AST mValue2 = mValue.getBranch("VALUE");

            getValoramento().valore(ePrefixo + "\t", mValue2,mIntegralizante, mEscopo);

        }


        mEscopo.alocar((mValue.getNome()));


    }

    public void valore_Mockiz(String ePrefixo, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "Valorando MOCKIZ : " + getSimplificador().getMockiz(mValue));

        if (mValue.existeBranch("VALUE")) {

            AST mValue2 = mValue.getBranch("VALUE");

            getValoramento().valore(ePrefixo + "\t", mValue2,mIntegralizante, mEscopo);

        }

        mEscopo.alocar((mValue.getNome()));

    }
}
