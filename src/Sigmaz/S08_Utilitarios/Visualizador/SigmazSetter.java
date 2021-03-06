package Sigmaz.S08_Utilitarios.Visualizador;

import Sigmaz.S08_Utilitarios.AST;

public class SigmazSetter {

    private AST mAST;
    private SigmazCast mSigmazCast;


    public SigmazSetter(SigmazCast eSigmazCast,AST eAST) {
        mAST = eAST;
        mSigmazCast=eSigmazCast;
    }

    public String getNome(){ return mAST.getValor();}
    public String getDefinicao(){ return mSigmazCast.getNome() + " -->> " + mAST.getValor();}


}
