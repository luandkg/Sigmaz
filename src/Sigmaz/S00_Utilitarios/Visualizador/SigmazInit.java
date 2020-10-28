package Sigmaz.S00_Utilitarios.Visualizador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S07_Executor.Debuggers.Simplificador;

public class SigmazInit {

    private AST mAST;

    public SigmazInit(AST eAST) {

        mAST = eAST;

    }

    public String getNome() {
        return mAST.getNome();
    }

    public String getDefinicao() {
        Simplificador mSimplificador = new Simplificador();
        return mSimplificador.getInit(mAST);
    }


}
