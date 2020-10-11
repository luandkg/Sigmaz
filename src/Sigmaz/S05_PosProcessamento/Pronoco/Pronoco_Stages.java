package Sigmaz.S05_PosProcessamento.Pronoco;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S07_Executor.Debuggers.Simplificador;

import java.util.ArrayList;


public class Pronoco_Stages {

    private AST mAST;

    private Simplificador mSimplificador;
    ArrayList<String> mStages;

    public Pronoco_Stages(AST eAST) {

        mAST = eAST;
        mSimplificador = new Simplificador();
        mStages = new ArrayList<String>();

        AST eStages = mAST.getBranch("STAGES");

        for (AST sAST : eStages.getASTS()) {
            adicionarStage(sAST.getNome());
        }


    }


    public String getNome() {
        return mAST.getNome();
    }

    public String getDefinicao() {
        return mAST.getNome();
    }

    public void adicionarStage(String eStage) {
        mStages.add(eStage);
    }

    public boolean existeStage(String eStage) {
        return mStages.contains(eStage);
    }

}
