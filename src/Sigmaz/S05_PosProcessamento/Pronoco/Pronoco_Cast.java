package Sigmaz.S05_PosProcessamento.Pronoco;


import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S07_Executor.Debuggers.Simplificador;

public class Pronoco_Cast {

    private AST mAST;

    private Simplificador mSimplificador;

    public Pronoco_Cast(AST eAST) {

        mAST = eAST;
        mSimplificador = new Simplificador();

    }


    public String getNome() {
        return mAST.getNome();
    }

    public String getDefinicao() {
        return mSimplificador.getCast(mAST);
    }
}
