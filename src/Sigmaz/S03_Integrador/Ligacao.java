package Sigmaz.S03_Integrador;

import Sigmaz.S08_Utilitarios.AST;

public class Ligacao {
    private String mBiblioteca;
    private AST mAST;

    public Ligacao(String eBiblioteca, AST eAST) {
        mBiblioteca = eBiblioteca;
        mAST = eAST;
    }


    public String getBiblioteca() {
        return mBiblioteca;
    }

    public AST getAST() {
        return mAST;
    }


    public void setAST(AST eAST){
        mAST =eAST;
    }

}
