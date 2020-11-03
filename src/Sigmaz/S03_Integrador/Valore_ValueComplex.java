package Sigmaz.S03_Integrador;

import Sigmaz.S00_Utilitarios.Visualizador.SigmazStages;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S05_Executor.Debuggers.Simplificador;

import java.util.ArrayList;

public class Valore_ValueComplex {

    Integrador mIntegrador;
    Valoramento mValoramento;

    public Valore_ValueComplex(Integrador eIntegrador, Valoramento eValoramento) {
        mValoramento = eValoramento;
        mIntegrador = eIntegrador;
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

    public void emInit(String ePrefixo, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {

        if (!mIntegralizante.existeStruct(mValue.getNome())) {


            mIntegrador.errar("Struct nao existente : " + mEscopo.getRegressivo() + " :: " + mValue.getNome());
            mIntegrador.mensagem(ePrefixo + "Struct nao existente : " + mEscopo.getRegressivo() + " :: " + mValue.getNome());


        }

    }

    public void emStages(String ePrefixo, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {


        String eStaged = mValue.getBranch("STAGED").getNome();

        mIntegrador.mensagem(ePrefixo + "Avaliando STAGES " + mValue.getNome() + " :: " + eStaged);


        if (mIntegralizante.existeStage(mValue.getNome())) {

            SigmazStages me = mIntegralizante.getStage(mValue.getNome());

            if (me.existeStage(eStaged)) {


            } else {

                mIntegrador.errar("Stage nao existente : " + mEscopo.getRegressivo() + " :: " + mValue.getNome() + "::" + eStaged);
                mIntegrador.mensagem(ePrefixo + "Stage nao existente : " + mEscopo.getRegressivo() + " :: " + mValue.getNome() + "::" + eStaged);


            }

        } else {

            mIntegrador.errar("Stages nao existente : " + mEscopo.getRegressivo() + " :: " + mValue.getNome());
            mIntegrador.mensagem(ePrefixo + "Stages nao existente : " + mEscopo.getRegressivo() + " :: " + mValue.getNome());


        }


    }


    public void emStart(String ePrefixo, AST mValue, Integralizante mIntegralizante, Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + "Avaliando START TYPE " + mValue.getNome());


        if (mIntegralizante.existeType(mValue.getNome())) {


        } else {

            mIntegrador.errar("Type nao existente : " + mEscopo.getRegressivo() + " :: " + mValue.getNome());
            mIntegrador.mensagem(ePrefixo + "Type nao existente : " + mEscopo.getRegressivo() + " :: " + mValue.getNome());


        }


    }

    public void emStruct_Inits(String ePrefixo, String eStruct, AST eASTPai, Integralizante mIntegralizante, Escopante mEscopo) {

        for (AST mAST : eASTPai.getASTS()) {


            mIntegrador.mensagem(ePrefixo + "Valorando INIT : " + getSimplificador().getAction(mAST));

            Escopante mAqui = new Escopante(mAST.getNome());
            mAqui.setAnterior(mEscopo);


            getValoramento().obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);


            getValore_Escopo().atribuicao_escopo(ePrefixo + "\t\t", mAST.getBranch("BODY"),mIntegralizante,mAqui);


        }

    }

    public void emStruct_Corpo(String ePrefixo, String eStruct, AST eASTPai, Integralizante mIntegralizante, Escopante mEscopo) {

        Simplificador mSimplificador = new Simplificador();


        mEscopo.alocar("this");

        for (AST mAST : eASTPai.getASTS()) {

            if (mAST.mesmoTipo("FUNCTION")) {

                mIntegralizante.adicionar_Function(mAST);

            } else if (mAST.mesmoTipo("ACTION")) {

                mIntegralizante.adicionar_Action(mAST);

            }


        }

        for (AST mAST : eASTPai.getASTS()) {
            if (mAST.mesmoTipo("DEFINE")) {
                mEscopo.alocar(mAST.getNome());
            } else if (mAST.mesmoTipo("MOCKIZ")) {
                mEscopo.alocar(mAST.getNome());
            }
        }

        for (AST mAST : eASTPai.getASTS()) {
            if (mAST.mesmoTipo("DEFINE")) {


                mIntegrador.mensagem(ePrefixo + "Valorando DEFINE : " + mSimplificador.getDefine(mAST));

                if (mAST.existeBranch("VALUE")) {

                    AST mValue = mAST.getBranch("VALUE");

                    getValoramento().valore(ePrefixo + "\t", mValue, mIntegralizante, mEscopo);

                }


            } else if (mAST.mesmoTipo("MOCKIZ")) {


                mIntegrador.mensagem(ePrefixo + "Valorando MOCKIZ : " + mSimplificador.getMockiz(mAST));

                if (mAST.existeBranch("VALUE")) {

                    AST mValue = mAST.getBranch("VALUE");

                    getValoramento().valore(ePrefixo + "\t", mValue, mIntegralizante,mEscopo);

                }

            }

        }


        for (AST mAST : eASTPai.getASTS()) {

            if (mAST.mesmoTipo("ACTION")) {

                mIntegrador.mensagem(ePrefixo + "\tValorando ACTION : " + eStruct + " -> " + mSimplificador.getAction(mAST));

                Escopante mAqui = new Escopante(mAST.getNome());
                mAqui.setAnterior(mEscopo);

                getValoramento().obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);


                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t\t", mAST.getBranch("BODY"),mIntegralizante, mAqui);


            } else if (mAST.mesmoTipo("FUNCTION")) {

                mIntegrador.mensagem(ePrefixo + "\tValorando FUNCTION : " + eStruct + " -> " + mSimplificador.getFuction(mAST));

                Escopante mAqui = new Escopante(mAST.getNome());
                mAqui.setAnterior(mEscopo);

                getValoramento().obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);


                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t\t", mAST.getBranch("BODY"),mIntegralizante, mAqui);

            } else if (mAST.mesmoTipo("AUTO")) {

                mIntegrador.mensagem(ePrefixo + "\tValorando AUTO : " + eStruct + " -> " + mSimplificador.getAuto(mAST));

                Escopante mAqui = new Escopante(mAST.getNome());
                mAqui.setAnterior(mEscopo);

                getValoramento().obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mEscopo);

                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t\t", mAST.getBranch("BODY"),mIntegralizante, mAqui);

            } else if (mAST.mesmoTipo("FUNCTOR")) {

                mIntegrador.mensagem(ePrefixo + "\tValorando FUNCTOR : " + eStruct + " -> " + mSimplificador.getFunctor(mAST));

                Escopante mAqui = new Escopante(mAST.getNome());
                mAqui.setAnterior(mEscopo);

                getValoramento().obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mEscopo);

                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t\t", mAST.getBranch("BODY"),mIntegralizante, mAqui);

            } else if (mAST.mesmoTipo("INIT")) {

                mIntegrador.mensagem(ePrefixo + "\tValorando INIT : " + eStruct + " -> " + mSimplificador.getAction(mAST));

                Escopante mAqui = new Escopante(mAST.getNome());
                mAqui.setAnterior(mEscopo);

                getValoramento().obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mEscopo);

                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t\t", mAST.getBranch("BODY"),mIntegralizante, mAqui);

            }

            if (mIntegrador.getErros().size() > 0) {
                break;
            }

        }

    }


}
