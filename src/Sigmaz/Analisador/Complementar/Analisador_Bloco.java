package Sigmaz.Analisador.Complementar;

import Sigmaz.Analisador.ASTS.*;
import Sigmaz.Analisador.Analisador;

import java.util.ArrayList;
import Sigmaz.Utils.AST;

public class Analisador_Bloco {

    private Analisador mAnalisador;


    private Analisar_Execute mAnalisar_Execute;
    private Analisar_Cast mAnalisar_Cast;
    private Analisar_Daz mAnalisar_Daz;
    private Analisar_When mAnalisar_When;
    private Analisar_Step mAnalisar_Step;
    private Analisar_Condition mAnalisar_Condition;
    private Analisar_While mAnalisar_While;
    private Analisar_Loop mAnalisar_Loop;

    private Analisar_Argumentos mAnalisar_Argumentos;
    private Analisar_Apply mAnalisar_Apply;
    private Analisar_Stage mAnalisar_Stage;
    private Analisar_Try mAnalisar_Try;
    private Analisar_Struct mAnalisar_Struct;
    private Analisar_Outros mAnalisar_Outros;

    private ArrayList<String> mTipados;
    private ArrayList<String> mPrimitivos;

    public ArrayList<String> getTipados() {
        return mTipados;
    }
    public ArrayList<String> getPrimitivos() {
        return mPrimitivos;
    }

    public Analisador_Bloco(Analisador eAnalisador) {

        mAnalisador=eAnalisador;

        mAnalisar_Execute = new Analisar_Execute(mAnalisador,this);
        mAnalisar_Cast = new Analisar_Cast(mAnalisador,this);

        mAnalisar_Daz = new Analisar_Daz(mAnalisador,this);
        mAnalisar_When = new Analisar_When(mAnalisador,this);
        mAnalisar_Step = new Analisar_Step(mAnalisador,this);
        mAnalisar_Condition = new Analisar_Condition(mAnalisador,this);
        mAnalisar_While = new Analisar_While(mAnalisador,this);
        mAnalisar_Loop= new Analisar_Loop(mAnalisador,this);
        mAnalisar_Argumentos = new Analisar_Argumentos(mAnalisador,this);
        mAnalisar_Apply = new Analisar_Apply(mAnalisador,this);
        mAnalisar_Stage = new Analisar_Stage(mAnalisador,this);
        mAnalisar_Try = new Analisar_Try(mAnalisador,this);
        mAnalisar_Struct = new Analisar_Struct(mAnalisador,this);


        mAnalisar_Outros = new Analisar_Outros(mAnalisador,this);

        mTipados = new ArrayList<>();
        mPrimitivos = new ArrayList<>();

        mTipados.add("num");
        mTipados.add("string");
        mTipados.add("bool");
        mTipados.add("auto");
        mTipados.add("functor");
        mTipados.add("int");

        mPrimitivos=eAnalisador.getPrimitivos();



    }

    public void analisarValoracao(AST ASTPai, ArrayList<String> mAlocadosAntes) {

        mAnalisar_Apply.analisar_valorizar(ASTPai, mAlocadosAntes);

    }

    public Analisar_Function getAnalisar_Function() {

        return  new Analisar_Function(mAnalisador,this);


    }

    public Analisar_Action getAnalisar_Action() {

        return  new Analisar_Action(mAnalisador,this);

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

    public Analisar_Loop getAnalisar_Loop() {
        return mAnalisar_Loop;
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
