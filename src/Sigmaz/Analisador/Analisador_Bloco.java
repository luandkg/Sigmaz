package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisador_Bloco {

    private Analisador mAnalisador;

    private Analisar_Function mAnalisar_Function;
    private Analisar_Action mAnalisar_Action;
    private Analisar_Execute mAnalisar_Execute;
    private Analisar_Cast mAnalisar_Cast;
    private Analisar_Daz mAnalisar_Daz;
    private Analisar_When mAnalisar_When;
    private Analisar_Step mAnalisar_Step;
    private Analisar_Condition mAnalisar_Condition;
    private Analisar_While mAnalisar_While;
    private Analisar_Argumentos mAnalisar_Argumentos;
    private Analisar_Apply mAnalisar_Apply;
    private Analisar_Stage mAnalisar_Stage;
    private Analisar_Try mAnalisar_Try;
    private Analisar_Struct mAnalisar_Struct;
    private Analisar_Outros mAnalisar_Outros;

    private ArrayList<String> mTipados;

    public ArrayList<String> getTipados() {
        return mTipados;
    }

    public Analisador_Bloco(Analisador eAnalisador) {

        mAnalisador=eAnalisador;

        mAnalisar_Function = new Analisar_Function(mAnalisador,this);
        mAnalisar_Action = new Analisar_Action(mAnalisador,this);

        mAnalisar_Execute = new Analisar_Execute(mAnalisador,this);
        mAnalisar_Cast = new Analisar_Cast(mAnalisador,this);

        mAnalisar_Daz = new Analisar_Daz(mAnalisador,this);
        mAnalisar_When = new Analisar_When(mAnalisador,this);
        mAnalisar_Step = new Analisar_Step(mAnalisador,this);
        mAnalisar_Condition = new Analisar_Condition(mAnalisador,this);
        mAnalisar_While = new Analisar_While(mAnalisador,this);
        mAnalisar_Argumentos = new Analisar_Argumentos(mAnalisador,this);
        mAnalisar_Apply = new Analisar_Apply(mAnalisador,this);
        mAnalisar_Stage = new Analisar_Stage(mAnalisador,this);
        mAnalisar_Try = new Analisar_Try(mAnalisador,this);
        mAnalisar_Struct = new Analisar_Struct(mAnalisador,this);


        mAnalisar_Outros = new Analisar_Outros(mAnalisador,this);

        mTipados = new ArrayList<>();

        mTipados.add("num");
        mTipados.add("string");
        mTipados.add("bool");
    }

    public void analisarValoracao(AST ASTPai, ArrayList<String> mAlocadosAntes) {

        mAnalisar_Apply.analisar_valorizar(ASTPai, mAlocadosAntes);

    }

    public Analisar_Function getAnalisar_Function() {
        return mAnalisar_Function;
    }

    public Analisar_Action getAnalisar_Action() {
        return mAnalisar_Action;
    }

    public Analisar_Execute getAnalisar_Execute() {
        return mAnalisar_Execute;
    }



    public Analisar_Daz getAnalisar_All() {
        return mAnalisar_Daz;
    }

    public Analisar_When getAnalisar_When() {
        return mAnalisar_When;
    }

    public Analisar_Step getAnalisar_Step() {
        return mAnalisar_Step;
    }

    public Analisar_Condition getAnalisar_Condition() {
        return mAnalisar_Condition;
    }

    public Analisar_While getAnalisar_While() {
        return mAnalisar_While;
    }

    public Analisar_Argumentos getAnalisar_Argumentos() {
        return mAnalisar_Argumentos;
    }

    public Analisar_Apply getAnalisar_Apply() {
        return mAnalisar_Apply;
    }

    public Analisar_Try getAnalisar_Try() {
        return mAnalisar_Try;
    }

    public Analisar_Cast getAnalisar_Cast() {
        return mAnalisar_Cast;
    }

    public Analisar_Stage getAnalisar_Stage() {
        return mAnalisar_Stage;
    }

    public Analisar_Struct getAnalisar_Struct() {
        return mAnalisar_Struct;
    }

    public Analisar_Outros getAnalisar_Outros() {
        return mAnalisar_Outros;
    }




}
