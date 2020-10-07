package Sigmaz.S05_PosProcessamento.Valore;

import Sigmaz.S05_PosProcessamento.Mensageiro;
import Sigmaz.S05_PosProcessamento.Pronoco.*;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S06_Executor.Debuggers.Simplificador;

import java.util.ArrayList;

public class Valore_ValueComplex {

    Mensageiro mMensageiro;
    Valoramento mValorador;

    public Valore_ValueComplex(Mensageiro eMensageiro, Valoramento eValorador) {
        mMensageiro = eMensageiro;
        mValorador = eValorador;
    }

    public Valore_Statements getValore_Statements() {
        return mValorador.getValore_Statements();
    }

    public Valore_ValueComplex getValore_Hiper() {
        return mValorador.getValore_Hiper();
    }

    public Valore_Value getValore_Tipos() {
        return mValorador.getValore_Tipos();
    }

    public Valoramento getValoramento() {
        return mValorador;
    }

    public Simplificador getSimplificador() {
        return mValorador.getSimplificador();
    }

    public ArrayList<String> getRegistradores() {
        return mValorador.getRegistradores();
    }

    public Valore_Escopo getValore_Escopo() {
        return mValorador.getValore_Escopo();
    }

    public void emInit(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        if (!mAtribuindo.existeStruct(mValue.getNome())) {


            mMensageiro.errar("Struct nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());
            mMensageiro.mensagem(ePrefixo + "Struct nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());


        }

    }

    public void emStages(String ePrefixo, AST mValue, Pronoco mAtribuindo) {


        String eStaged = mValue.getBranch("STAGED").getNome();

        mMensageiro.mensagem(ePrefixo + "Avaliando STAGES " + mValue.getNome() + " :: " + eStaged);


        if (mAtribuindo.existeStage(mValue.getNome())) {

            Pronoco_Stages me = mAtribuindo.getStage(mValue.getNome());

            if (me.existeStage(eStaged)) {


            } else {

                mMensageiro.errar("Stage nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome() + "::" + eStaged);
                mMensageiro.mensagem(ePrefixo + "Stage nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome() + "::" + eStaged);


            }

        } else {

            mMensageiro.errar("Stages nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());
            mMensageiro.mensagem(ePrefixo + "Stages nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());


        }


    }

    public void emStruct(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mMensageiro.mensagem(ePrefixo + "Avaliando STRUCT Com Variavel " + mValue.getNome());


        if (mAtribuindo.existeAteAqui(mValue.getNome())) {


            if (mValue.existeBranch("INTERNAL")) {
                emInternal(ePrefixo+"\t", mValue.getBranch("INTERNAL"), mAtribuindo);
            }


        } else if (mAtribuindo.existeAteAqui(mValue.getNome())) {

            if (mValue.existeBranch("INTERNAL")) {
                emInternal(ePrefixo+"\t", mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else {

            mMensageiro.errar("Variavel nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());
            mMensageiro.mensagem(ePrefixo + "Variavel nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());


        }


    }

    public void emInternal(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mMensageiro.mensagem(ePrefixo + "Avaliando Internal Tipo " + mValue.getValor());
        mMensageiro.mensagem(ePrefixo + "Avaliando Internal Campo " + mValue.getNome());

        if (mValue.mesmoValor("STRUCT_FUNCT")) {

            for (AST mAST : mValue.getBranch("ARGUMENTS").getASTS()) {

                mValorador.valore(ePrefixo, mAST, mAtribuindo);

            }

            if (mValue.existeBranch("INTERNAL")) {
                emInternal(ePrefixo, mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else if (mValue.mesmoValor("STRUCT_OBJECT")) {

            if (mValue.existeBranch("INTERNAL")) {
                emInternal(ePrefixo, mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else {
            mMensageiro.errar(" Internal Tipo Desconhecido : " + mValue.getValor());
        }

    }

    public void emExtern(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mMensageiro.mensagem(ePrefixo + "Avaliando EXTERN " + mValue.getNome());



        if (mAtribuindo.existeExtern(mValue.getNome())) {

            if (mValue.existeBranch("INTERNAL")) {
                emInternalExtern(ePrefixo + "\t", mAtribuindo.getExtern(mValue.getNome()), mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else {
            mMensageiro.errar(" Extern Desconhecido : " + mValue.getNome());
        }




    }

    public void emExternSemRetorno(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mMensageiro.mensagem(ePrefixo + "Avaliando EXTERN " + mValue.getNome());



        if (mAtribuindo.existeExtern(mValue.getNome())) {

            if (mValue.existeBranch("INTERNAL")) {
                emInternalExternSemRetorno(ePrefixo + "\t", mAtribuindo.getExtern(mValue.getNome()), mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else {
            mMensageiro.errar( " Extern Desconhecido : " + mValue.getNome());
        }




    }

    public void emInternalExtern(String ePrefixo,Pronoco_Extern mExtern, AST mValue, Pronoco mAtribuindo) {

        mMensageiro.mensagem(ePrefixo + "Avaliando Internal Extern Tipo " + mValue.getValor());
        mMensageiro.mensagem(ePrefixo + "Avaliando Internal Extern Campo " + mValue.getNome());



        if (mValue.mesmoValor("STRUCT_FUNCT")) {

            mMensageiro.mensagem(ePrefixo + "Avaliando Internal Extern STRUCT_FUNCT " + mValue.getNome());

            if (mExtern.existeFunction(mValue.getNome())){

            }else{

                mMensageiro.errar(ePrefixo + " Internal Extern Extern STRUCT_FUNCT " + mValue.getNome() + " Nao existente !");

            }


            for (AST mAST : mValue.getBranch("ARGUMENTS").getASTS()) {

                mValorador.valore(ePrefixo, mAST, mAtribuindo);

            }

            if (mValue.existeBranch("INTERNAL")) {
                emInternal(ePrefixo, mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else if (mValue.mesmoValor("STRUCT_OBJECT")) {

            if (mValue.existeBranch("INTERNAL")) {
                emInternal(ePrefixo, mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else {
            mMensageiro.errar(ePrefixo + " Internal Extern Tipo Desconhecido : " + mValue.getValor());
        }

    }

    public void emInternalExternSemRetorno(String ePrefixo,Pronoco_Extern mExtern, AST mValue, Pronoco mAtribuindo) {

        mMensageiro.mensagem(ePrefixo + "\t Tipo " + mValue.getValor());
        mMensageiro.mensagem(ePrefixo + "\t Campo " + mValue.getNome());



        if (mValue.mesmoValor("STRUCT_FUNCT")) {

            mMensageiro.mensagem(ePrefixo + "\t STRUCT_FUNCT " + mValue.getNome());

            if (mExtern.existeActionFunction(mValue.getNome())){

            }else{

                mMensageiro.errar( mExtern.getNome() + " -> " + mValue.getNome() + " : Acao Explicita ou Funcao Explicita Nao existente !");

            }


            for (AST mAST : mValue.getBranch("ARGUMENTS").getASTS()) {

                mValorador.valore(ePrefixo, mAST, mAtribuindo);

            }

            if (mValue.existeBranch("INTERNAL")) {
                emInternal(ePrefixo, mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else if (mValue.mesmoValor("STRUCT_OBJECT")) {

            if (mValue.existeBranch("INTERNAL")) {
                emInternal(ePrefixo, mValue.getBranch("INTERNAL"), mAtribuindo);
            }

        } else {
            mMensageiro.errar(ePrefixo + " Internal Extern Tipo Desconhecido : " + mValue.getValor());
        }

    }


    public void emStart(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        mMensageiro.mensagem(ePrefixo+"Avaliando START TYPE " + mValue.getNome());


        if (mAtribuindo.existeType(mValue.getNome())) {


        } else {

            mMensageiro.errar("Type nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());
            mMensageiro.mensagem(ePrefixo+"Type nao existente : " + mAtribuindo.getRegressivo() + " :: " + mValue.getNome());


        }


    }

    public void emStruct_Inits(String ePrefixo, String eStruct, AST eASTPai, Pronoco mAtribuindo) {

        for (AST mAST : eASTPai.getASTS()) {


            mMensageiro.mensagem(ePrefixo + "Valorando INIT : " + getSimplificador().getAction(mAST));

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


                mMensageiro.mensagem(ePrefixo + "Valorando DEFINE : " + mSimplificador.getDefine(mAST));

                if (mAST.existeBranch("VALUE")) {
                    Pronoco mAqui = mAtribuindo;

                    AST mValue = mAST.getBranch("VALUE");

                    getValoramento().       valore(ePrefixo + "\t", mValue, mAqui);

                }


                mAtribuindo.adicionarDefine(new Pronoco_Def(mAST.getNome()));


            } else if (mAST.mesmoTipo("MOCKIZ")) {


                mMensageiro.mensagem(ePrefixo + "Valorando MOCKIZ : " + mSimplificador.getMockiz(mAST));

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

                mMensageiro.mensagem(ePrefixo + "\tValorando ACTION : " + eStruct + " -> " + mSimplificador.getAction(mAST));

                Pronoco mAqui = new Pronoco(eStruct);
                mAqui.setSuperior(mAtribuindo);

                getValoramento().     obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);


                getValore_Escopo(). atribuicao_escopo(ePrefixo + "\t\t", mAST.getBranch("BODY"), mAqui);


            } else if (mAST.mesmoTipo("FUNCTION")) {

                mMensageiro.mensagem(ePrefixo + "\tValorando FUNCTION : " + eStruct + " -> " + mSimplificador.getFuction(mAST));

                Pronoco mAqui = new Pronoco(eStruct);
                mAqui.setSuperior(mAtribuindo);

                getValoramento().    obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);


                getValore_Escopo().  atribuicao_escopo(ePrefixo + "\t\t", mAST.getBranch("BODY"), mAqui);

            } else if (mAST.mesmoTipo("AUTO")) {

                mMensageiro.mensagem(ePrefixo + "\tValorando AUTO : " + eStruct + " -> " + mSimplificador.getAuto(mAST));

                Pronoco mAqui = new Pronoco(eStruct);
                mAqui.setSuperior(mAtribuindo);

                getValoramento().      obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);

                getValore_Escopo().   atribuicao_escopo(ePrefixo + "\t\t", mAST.getBranch("BODY"), mAqui);

            } else if (mAST.mesmoTipo("FUNCTOR")) {

                mMensageiro.mensagem(ePrefixo + "\tValorando FUNCTOR : " + eStruct + " -> " + mSimplificador.getFunctor(mAST));

                Pronoco mAqui = new Pronoco(eStruct);
                mAqui.setSuperior(mAtribuindo);

                getValoramento().    obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);

                getValore_Escopo().     atribuicao_escopo(ePrefixo + "\t\t", mAST.getBranch("BODY"), mAqui);

            } else if (mAST.mesmoTipo("INIT")) {

                mMensageiro.mensagem(ePrefixo + "\tValorando INIT : " + eStruct + " -> " + mSimplificador.getAction(mAST));

                Pronoco mAqui = new Pronoco(eStruct);
                mAqui.setSuperior(mAtribuindo);

                getValoramento().   obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);

                getValore_Escopo(). atribuicao_escopo(ePrefixo + "\t\t", mAST.getBranch("BODY"), mAqui);

            }

            if (mMensageiro.getErros().size() > 0) {
                break;
            }

        }

    }


}
