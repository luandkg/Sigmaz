package Sigmaz.S00_Utilitarios.Visualizador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S08_Executor.Debuggers.Simplificador;

public class SigmazBlocoSetter {

    private AST mAST;
    private SigmazStruct mStruct;

    public SigmazBlocoSetter(SigmazStruct eStruct,AST eAST) {

        mAST = eAST;
        mStruct=eStruct;

    }

    public String getNome() {
        return mAST.getNome();
    }

    public String getDefinicao() {
        Simplificador mSimplificador = new Simplificador();
        return mSimplificador.getBlocoSetter(mStruct.getNome(),mAST);
    }


}
