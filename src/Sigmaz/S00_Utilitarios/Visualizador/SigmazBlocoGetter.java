package Sigmaz.S00_Utilitarios.Visualizador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S07_Executor.Debuggers.Simplificador;

public class SigmazBlocoGetter {

    private AST mAST;
    private SigmazStruct mStruct;

    public SigmazBlocoGetter(SigmazStruct eStruct,AST eAST) {

        mAST = eAST;
        mStruct=eStruct;

    }

    public String getNome() {
        return mAST.getNome();
    }

    public String getDefinicao() {
        Simplificador mSimplificador = new Simplificador();
        return mSimplificador.getBlocoGetter(mStruct.getNome(),mAST);
    }


}
