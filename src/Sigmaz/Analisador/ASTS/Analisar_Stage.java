package Sigmaz.Analisador.ASTS;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Analisador.Complementar.Analisador_Bloco;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Stage {

    private Analisador mAnalisador;
    private Analisador_Bloco mAnalisador_Bloco;

    public Analisar_Stage(Analisador eAnalisador,Analisador_Bloco eAnalisador_Bloco) {

        mAnalisador = eAnalisador;
        mAnalisador_Bloco=eAnalisador_Bloco;

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
