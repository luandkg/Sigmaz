package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Stage {

    private Analisador mAnalisador;

    public Analisar_Stage(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

    }

    public void analisar(AST ASTPai) {

        ArrayList<String> mOpcoes = new ArrayList<>();

        for (AST sAST : ASTPai.getBranch("OPTIONS").getASTS()) {


            if (mOpcoes.contains(sAST.getNome())) {
                mAnalisador.getErros().add("Stage Duplicada " + ASTPai.getNome() + "::" + sAST.getNome());
            }else{
                mOpcoes.add(sAST.getNome());
            }

        }

    }

}
