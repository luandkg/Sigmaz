package Sigmaz.S05_PosProcessamento.Povalorum;

import Sigmaz.S00_Utilitarios.Mensageiro;
import Sigmaz.S05_PosProcessamento.Processadores.Valorador;
import Sigmaz.S05_PosProcessamento.Pronoco.*;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S07_Executor.Debuggers.Simplificador;

import java.util.ArrayList;

public class Valore_ValueComplex {

    Valorador mValorador;
    Valoramento mValoramento;

    public Valore_ValueComplex(Valorador eValorador, Valoramento eValoramento) {
        mValoramento = eValoramento;
        mValorador = eValorador;
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

    public void emInit(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        if (!mAtribuindo.existeStruct(mValue.getNome())) {


            mValorador.errar("Struct nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());
            mValorador.mensagem(ePrefixo + "Struct nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());


        }

    }

    public void emStages(String ePrefixo, AST mValue, Pronoco mAtribuindo) {


        String eStaged = mValue.getBranch("STAGED").getNome();

        mValorador.mensagem(ePrefixo + "Avaliando STAGES " + mValue.getNome() + " :: " + eStaged);


        if (mAtribuindo.existeStage(mValue.getNome())) {

            Pronoco_Stages me = mAtribuindo.getStage(mValue.getNome());

            if (me.existeStage(eStaged)) {


            } else {

                mValorador.errar("Stage nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome() + "::" + eStaged);
                mValorador.mensagem(ePrefixo + "Stage nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome() + "::" + eStaged);


            }

        } else {

            mValorador.errar("Stages nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());
            mValorador.mensagem(ePrefixo + "Stages nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());


        }


    }

    public void emStruct(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Avaliando STRUCT Com Variavel " + mValue.getNome());


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

    public void emExtern(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Avaliando EXTERN " + mValue.getNome());



        if (mAtribuindo.existeExtern(mValue.getNome())) {

            if (mValue.existeBranch("INTERNAL")) {
                emInternalExtern(ePrefixo + "\t", mAtribuindo.getExtern(mValue.getNome()), mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else {
            mValorador.errar(" Extern Desconhecido : " + mValue.getNome());
        }




    }

    public void emExternSemRetorno(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Avaliando EXTERN " + mValue.getNome());



        if (mAtribuindo.existeExtern(mValue.getNome())) {

            if (mValue.existeBranch("INTERNAL")) {
                emInternalExternSemRetorno(ePrefixo + "\t", mAtribuindo.getExtern(mValue.getNome()), mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else {
            mValorador.errar( " Extern Desconhecido : " + mValue.getNome());
        }




    }

    public void emInternalExtern(String ePrefixo,Pronoco_Extern mExtern, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "Avaliando Internal Extern Tipo " + mValue.getValor());
        mValorador.mensagem(ePrefixo + "Avaliando Internal Extern Campo " + mValue.getNome());



        if (mValue.mesmoValor("STRUCT_FUNCT")) {

            mValorador.mensagem(ePrefixo + "Avaliando Internal Extern STRUCT_FUNCT " + mValue.getNome());

            if (mExtern.existeFunction(mValue.getNome())){

            }else{

                mValorador.errar(ePrefixo + " Internal Extern Extern STRUCT_FUNCT " + mValue.getNome() + " Nao existente !");

            }


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
            mValorador.errar(ePrefixo + " Internal Extern Tipo Desconhecido : " + mValue.getValor());
        }

    }

    public void emInternalExternSemRetorno(String ePrefixo,Pronoco_Extern mExtern, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + "\t Tipo " + mValue.getValor());
        mValorador.mensagem(ePrefixo + "\t Campo " + mValue.getNome());



        if (mValue.mesmoValor("STRUCT_FUNCT")) {

            mValorador.mensagem(ePrefixo + "\t STRUCT_FUNCT " + mValue.getNome());

            if (mExtern.existeActionFunction(mValue.getNome())){

            }else{

                mValorador.errar( mExtern.getNome() + " -> " + mValue.getNome() + " : Acao Explicita ou Funcao Explicita Nao existente !");

            }


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
            mValorador.errar(ePrefixo + " Internal Extern Tipo Desconhecido : " + mValue.getValor());
        }

    }


    public void emStart(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo+"Avaliando START TYPE " + mValue.getNome());


        if (mAtribuindo.existeType(mValue.getNome())) {


        } else {

            mValorador.errar("Type nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());
            mValorador.mensagem(ePrefixo+"Type nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());


        }


    }

    public void emStruct_Inits(String ePrefixo, String eStruct, AST eASTPai, Pronoco mAtribuindo) {

        for (AST mAST : eASTPai.getASTS()) {


            mValorador.mensagem(ePrefixo + "Valorando INIT : " + getSimplificador().getAction(mAST));

            Pronoco mAqui = new Pronoco(eStruct);
            mAqui.setSuperior(mAtribuindo);

         getValoramento().   obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);


            getValore_Escopo().  atribuicao_escopo(ePrefixo + "\t\t", mAST.getBranch("BODY"), mAqui);


        }

    }

    public void emStruct_Corpo(String ePrefixo, String eStruct, AST eASTPai, Pronoco mAtribuindo) {

        Simplificador mSimplificador = new Simplificador();


        mAtribuindo.adicionarMockiz(new Pronoco_Moc("this"));

        for (AST mAST : eASTPai.getASTS()) {

            if (mAST.mesmoTipo("FUNCTION")) {

                mAtribuindo.adicionarFunction(new Pronoco_Function(mAST));

            } else if (mAST.mesmoTipo("ACTION")) {

                mAtribuindo.adicionarAction(new Pronoco_Action(mAST));

            }


        }

        for (AST mAST : eASTPai.getASTS()) {
            if (mAST.mesmoTipo("DEFINE")) {


                mValorador.mensagem(ePrefixo + "Valorando DEFINE : " + mSimplificador.getDefine(mAST));

                if (mAST.existeBranch("VALUE")) {
                    Pronoco mAqui = mAtribuindo;

                    AST mValue = mAST.getBranch("VALUE");

                    getValoramento().       valore(ePrefixo + "\t", mValue, mAqui);

                }


                mAtribuindo.adicionarDefine(new Pronoco_Def(mAST.getNome()));


            } else if (mAST.mesmoTipo("MOCKIZ")) {


                mValorador.mensagem(ePrefixo + "Valorando MOCKIZ : " + mSimplificador.getMockiz(mAST));

                if (mAST.existeBranch("VALUE")) {
                    Pronoco mAqui = mAtribuindo;

                    AST mValue = mAST.getBranch("VALUE");

                    getValoramento().     valore(ePrefixo + "\t", mValue, mAqui);

                }

                mAtribuindo.adicionarMockiz(new Pronoco_Moc(mAST.getNome()));
            }

        }


        for (AST mAST : eASTPai.getASTS()) {

            if (mAST.mesmoTipo("ACTION")) {

                mValorador.mensagem(ePrefixo + "\tValorando ACTION : " + eStruct + " -> " + mSimplificador.getAction(mAST));

                Pronoco mAqui = new Pronoco(eStruct);
                mAqui.setSuperior(mAtribuindo);

                getValoramento().     obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);


                getValore_Escopo(). atribuicao_escopo(ePrefixo + "\t\t", mAST.getBranch("BODY"), mAqui);


            } else if (mAST.mesmoTipo("FUNCTION")) {

                mValorador.mensagem(ePrefixo + "\tValorando FUNCTION : " + eStruct + " -> " + mSimplificador.getFuction(mAST));

                Pronoco mAqui = new Pronoco(eStruct);
                mAqui.setSuperior(mAtribuindo);

                getValoramento().    obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);


                getValore_Escopo().  atribuicao_escopo(ePrefixo + "\t\t", mAST.getBranch("BODY"), mAqui);

            } else if (mAST.mesmoTipo("AUTO")) {

                mValorador.mensagem(ePrefixo + "\tValorando AUTO : " + eStruct + " -> " + mSimplificador.getAuto(mAST));

                Pronoco mAqui = new Pronoco(eStruct);
                mAqui.setSuperior(mAtribuindo);

                getValoramento().      obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);

                getValore_Escopo().   atribuicao_escopo(ePrefixo + "\t\t", mAST.getBranch("BODY"), mAqui);

            } else if (mAST.mesmoTipo("FUNCTOR")) {

                mValorador.mensagem(ePrefixo + "\tValorando FUNCTOR : " + eStruct + " -> " + mSimplificador.getFunctor(mAST));

                Pronoco mAqui = new Pronoco(eStruct);
                mAqui.setSuperior(mAtribuindo);

                getValoramento().    obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);

                getValore_Escopo().     atribuicao_escopo(ePrefixo + "\t\t", mAST.getBranch("BODY"), mAqui);

            } else if (mAST.mesmoTipo("INIT")) {

                mValorador.mensagem(ePrefixo + "\tValorando INIT : " + eStruct + " -> " + mSimplificador.getAction(mAST));

                Pronoco mAqui = new Pronoco(eStruct);
                mAqui.setSuperior(mAtribuindo);

                getValoramento().   obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);

                getValore_Escopo(). atribuicao_escopo(ePrefixo + "\t\t", mAST.getBranch("BODY"), mAqui);

            }

            if (mValorador.getErros().size() > 0) {
                break;
            }

        }

    }


}
