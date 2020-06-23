package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisador {

    private ArrayList<AST> mASTS;

    private ArrayList<String> mErros;



    private Analisar_Function mAnalisar_Function;
    private Analisar_Action mAnalisar_Action;

    private Analisar_All mAnalisar_All;
    private Analisar_When mAnalisar_When;
    private Analisar_Step mAnalisar_Step;
    private Analisar_Condition mAnalisar_Condition;
    private Analisar_While mAnalisar_While;
    private Analisar_Argumentos mAnalisar_Argumentos;
    private Analisar_Apply mAnalisar_Apply;
    private Analisar_Execute mAnalisar_Execute;

    private Analisar_Outros mAnalisar_Outros;

    private Heranca mHeranca;
    private Estagiador mEstagiador;

    private boolean mExterno;

    public Analisador() {

        mASTS = new ArrayList<>();


        mErros = new ArrayList<>();


        mAnalisar_Function = new Analisar_Function(this);
        mAnalisar_Action = new Analisar_Action(this);

        mAnalisar_All = new Analisar_All(this);
        mAnalisar_When = new Analisar_When(this);
        mAnalisar_Step = new Analisar_Step(this);
        mAnalisar_Condition = new Analisar_Condition(this);
        mAnalisar_While = new Analisar_While(this);
        mAnalisar_Argumentos = new Analisar_Argumentos(this);
        mAnalisar_Apply = new Analisar_Apply(this);
        mAnalisar_Execute = new Analisar_Execute(this);

        mAnalisar_Outros = new Analisar_Outros(this);

        mHeranca = new Heranca(this);
        mEstagiador = new Estagiador(this);


        mExterno = true;

    }

    public Analisar_Function getAnalisar_Function(){return mAnalisar_Function;}
    public Analisar_Action getAnalisar_Action(){return mAnalisar_Action;}


    public Analisar_All getAnalisar_All(){return mAnalisar_All;}
    public Analisar_When getAnalisar_When(){return mAnalisar_When;}
    public Analisar_Step getAnalisar_Step(){return mAnalisar_Step;}
    public Analisar_Condition getAnalisar_Condition(){return mAnalisar_Condition;}
    public Analisar_While getAnalisar_While(){return mAnalisar_While;}
    public Analisar_Argumentos getAnalisar_Argumentos(){return mAnalisar_Argumentos;}
    public Analisar_Apply getAnalisar_Apply(){return mAnalisar_Apply;}
    public Analisar_Execute getAnalisar_Execute(){return mAnalisar_Execute;}

    public Analisar_Outros getAnalisar_Outros(){return mAnalisar_Outros;}




    public void externarlizar() {
        mExterno = true;
    }

    public void internalizar() {
        mExterno = false;
    }

    public boolean getExterno() {
        return mExterno;
    }

    public void mensagem(String eMensagem) {

        if (mExterno) {

            System.out.println(eMensagem);

        }

    }

    public ArrayList<String> getErros() {
        return mErros;
    }

    public ArrayList<String> getActions_Nomes() {
        return mAnalisar_Outros.getActions_Nomes();
    }
    public ArrayList<String> getFunctions_Nomes() {
        return mAnalisar_Outros.getFunctions_Nomes();
    }

    public void init(ArrayList<AST> eASTs) {
        mASTS = eASTs;
        mErros.clear();

        getAnalisar_Outros().limpar();

        ArrayList<String> mAlocados = new ArrayList<String>();

        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                analisarGlobal(ASTCGlobal, mAlocados);

            } else {

                mErros.add("Escopo Desconhecido : " + ASTCGlobal.getTipo());

            }

        }


        mHeranca.init(mASTS);

        mEstagiador.init(mASTS);

    }

    public ArrayList<String> copiarAlocados(ArrayList<String> mAlocados) {

        ArrayList<String> mCopia = new ArrayList<String>();

        for (String a : mAlocados) {
            mCopia.add(a);
        }

        return mCopia;
    }

    public void analisarAlocacao(AST ASTPai, ArrayList<String> mAlocadosAntes) {

        if (!mAlocadosAntes.contains(ASTPai.getNome())) {
            mAlocadosAntes.add(ASTPai.getNome());
        } else {
            mErros.add("Definicao Duplicada : " + ASTPai.getNome());
        }
    }

    public void analisarValoracao(AST ASTPai, ArrayList<String> mAlocadosAntes) {

        mAnalisar_Apply.analisar_valorizar(ASTPai, mAlocadosAntes);

    }


    public void analisarGlobal(AST ASTPai, ArrayList<String> mAlocadosAntes) {

        ArrayList<String> mAlocados = copiarAlocados(mAlocadosAntes);


        mAnalisar_Outros.inclusao(ASTPai);

        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("DEFINE")) {

                analisarAlocacao(mAST, mAlocados);

                mAnalisar_Outros.analisandoDefines(mAST);
                analisarValoracao(ASTPai, mAlocadosAntes);


            } else if (mAST.mesmoTipo("MOCKIZ")) {

                analisarAlocacao(mAST, mAlocados);


                mAnalisar_Outros.analisandoDefines(mAST);
                analisarValoracao(ASTPai, mAlocadosAntes);


            }

        }


        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("ACTION")) {

                mAnalisar_Action.analisarAction(mAST, mAlocados);

            } else if (mAST.mesmoTipo("FUNCTION")) {

               mAnalisar_Function.analisarFunction(mAST, mAlocados);

            } else if (mAST.mesmoTipo("CALL")) {
            } else if (mAST.mesmoTipo("INVOKE")) {

            } else if (mAST.mesmoTipo("DEFINE")) {
            } else if (mAST.mesmoTipo("MOCKIZ")) {

            } else if (mAST.mesmoTipo("OPERATION")) {

                mAnalisar_Function.analisarFunction(mAST, mAlocados);


            } else if (mAST.mesmoTipo("CAST")) {
            } else if (mAST.mesmoTipo("STRUCT")) {

                Analisar_Struct mAnalisar_Struct = new Analisar_Struct(this);
                mAnalisar_Struct.init_Struct(mAST,mAlocados);

            } else if (mAST.mesmoTipo("STAGES")) {

            } else {

                mErros.add("AST x : " + mAST.getTipo());


            }


        }


        mAnalisar_Outros.exportarOperadores(ASTPai);

    }







}
