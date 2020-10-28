package Sigmaz.S00_Utilitarios.Visualizador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S07_Executor.Debuggers.Simplificador;

public class SigmazMockiz {

    private AST mAST;

    public SigmazMockiz(AST eAST) {

        mAST = eAST;

    }

    public String getNome() {
        return mAST.getNome();
    }

    public String getDefinicao() {
        Simplificador mSimplificador = new Simplificador();
        return mSimplificador.getMockiz(mAST);
    }

    public String getVisibilidade() {
        return mAST.getBranch("VISIBILITY").getNome();
    }

    public boolean isExplicit(){
        return getVisibilidade().contentEquals("EXPLICIT");
    }

    public boolean isImplicit(){
        return getVisibilidade().contentEquals("IMPLICIT");
    }

    public boolean isAll(){
        return getVisibilidade().contentEquals("ALL");
    }

    public boolean isRestrict(){
        return getVisibilidade().contentEquals("RESTRICT");
    }

    public boolean isAllow(){
        return getVisibilidade().contentEquals("ALLOW");
    }

}
