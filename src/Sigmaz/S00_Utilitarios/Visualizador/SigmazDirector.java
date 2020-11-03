package Sigmaz.S00_Utilitarios.Visualizador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S05_Executor.Debuggers.Simplificador;

public class SigmazDirector {
    private AST mAST;

    public SigmazDirector(AST eAST) {

        mAST = eAST;

    }

    public String getNome() {
        return mAST.getNome();
    }

    public boolean mesmoNome(String eNome){
        return this.getNome().contentEquals(eNome);
    }

    public String getDefinicao() {
        Simplificador mSimplificador = new Simplificador();
        return mSimplificador.getDirector(mAST);
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
