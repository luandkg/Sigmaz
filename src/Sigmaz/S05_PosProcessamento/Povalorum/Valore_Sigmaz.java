package Sigmaz.S05_PosProcessamento.Povalorum;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Mensageiro;
import Sigmaz.S05_PosProcessamento.Processadores.Valorador;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco_Def;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco_Moc;
import Sigmaz.S07_Executor.Debuggers.Simplificador;

import java.util.ArrayList;

public class Valore_Sigmaz {

    Valorador mValorador;
    Valoramento mValoramento;

    public Valore_Sigmaz(Valorador eValorador, Valoramento eValoramento) {
        mValorador = eValorador;
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


    public void valore_Call(String ePrefixo, AST mValue, Pronoco mAtribuindo) {


        mValorador.mensagem(ePrefixo + "Valorando CALL : " + getSimplificador().getCall(mValue));

        Pronoco mAqui = new Pronoco(getSimplificador().getCall(mValue));
        mAqui.setSuperior(mAtribuindo);

        getValoramento().obterVarArgumentos(mValue.getBranch("SENDING").getBranch("ARGUMENTS"), mAqui);


        if (mValue.mesmoValor("REFER")) {


            if (mAqui.existeActionFunction(mValue.getBranch("SENDING").getNome())) {

            } else {

                mValorador.errar("Action ou Function nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getBranch("SENDING").getNome());
                mValorador.mensagem(ePrefixo + "Variavel nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getBranch("SENDING").getNome());


            }

        } else if (mValue.mesmoValor("AUTO")) {

            getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mValue.getBranch("BODY"), mAqui);

        } else {

            mValorador.errar("Call com Problemas : " + mAtribuindo.getRegressivo() );

        }


    }


    public void valore_Action(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Valorando ACTION : " + getSimplificador().getAction(mValue));

        Pronoco mAqui = new Pronoco(getSimplificador().getAction(mValue));
        mAqui.setSuperior(mAtribuindo);


        getValoramento().obterVarArgumentos(mValue.getBranch("ARGUMENTS"), mAqui);

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mValue.getBranch("BODY"), mAqui);


    }

    public void valore_Function(String ePrefixo, AST mValue, Pronoco mAtribuindo) {


        mValorador.mensagem(ePrefixo + "Valorando FUNCTION : " + getSimplificador().getFuction(mValue));

        Pronoco mAqui = new Pronoco(getSimplificador().getFuction(mValue));
        mAqui.setSuperior(mAtribuindo);


        getValoramento().obterVarArgumentos(mValue.getBranch("ARGUMENTS"), mAqui);

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mValue.getBranch("BODY"), mAqui);


    }

    public void valore_Director(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Valorando DIRECTOR : " + getSimplificador().getDirector(mValue));

        Pronoco mAqui = new Pronoco(getSimplificador().getDirector(mValue));
        mAqui.setSuperior(mAtribuindo);


        getValoramento().obterVarArgumentos(mValue.getBranch("ARGUMENTS"), mAqui);

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mValue.getBranch("BODY"), mAqui);


    }

    public void valore_Operator(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Valorando OPERATOR : " + getSimplificador().getOperator(mValue));

        Pronoco mAqui = new Pronoco(getSimplificador().getOperator(mValue));
        mAqui.setSuperior(mAtribuindo);


        getValoramento().obterVarArgumentos(mValue.getBranch("ARGUMENTS"), mAqui);

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mValue.getBranch("BODY"), mAqui);


    }

    public void valore_Marker(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Valorando MARKER : " + getSimplificador().getMark(mValue));

        Pronoco mAqui = new Pronoco(getSimplificador().getOperator(mValue));
        mAqui.setSuperior(mAtribuindo);


        getValoramento().obterVarArgumentos(mValue.getBranch("ARGUMENTS"), mAqui);

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mValue.getBranch("BODY"), mAqui);


    }

    public void valore_Auto(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Valorando AUTO : " + getSimplificador().getAuto(mValue));
        Pronoco mAqui = new Pronoco(getSimplificador().getAuto(mValue));
        mAqui.setSuperior(mAtribuindo);


        getValoramento().obterVarArgumentos(mValue.getBranch("ARGUMENTS"), mAqui);

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mValue.getBranch("BODY"), mAqui);


    }

    public void valore_Functor(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Valorando FUNCTOR : " + getSimplificador().getFunctor(mValue));
        Pronoco mAqui = new Pronoco(getSimplificador().getFunctor(mValue));
        mAqui.setSuperior(mAtribuindo);


        getValoramento().obterVarArgumentos(mValue.getBranch("ARGUMENTS"), mAqui);

        //  getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mValue.getBranch("INITS"), mAqui);
        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mValue.getBranch("BODY"), mAqui);


    }

    public void valore_Define(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Valorando DEFINE : " + getSimplificador().getDefine(mValue));

        if (mValue.existeBranch("VALUE")) {
            Pronoco mAqui = mAtribuindo;

            AST mValue2 = mValue.getBranch("VALUE");

            getValoramento().valore(ePrefixo + "\t", mValue2, mAqui);

        }


        mAtribuindo.adicionarDefine(new Pronoco_Def(mValue.getNome()));


    }

    public void valore_Mockiz(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Valorando MOCKIZ : " + getSimplificador().getMockiz(mValue));

        if (mValue.existeBranch("VALUE")) {
            Pronoco mAqui = mAtribuindo;

            AST mValue2 = mValue.getBranch("VALUE");

            getValoramento().valore(ePrefixo + "\t", mValue2, mAqui);

        }

        mAtribuindo.adicionarMockiz(new Pronoco_Moc(mValue.getNome()));

    }
}
