package Sigmaz.S08_Executor;

import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class OMLRun {

    private ArrayList<AST> mCabecalho;
    private ArrayList<AST> mCodigo;

    public OMLRun() {

        mCabecalho = new ArrayList<AST>();
        mCodigo = new ArrayList<AST>();

    }

    public void carregar(ArrayList<AST> eCabecalho, ArrayList<AST> eCodigo) {

        mCabecalho = eCabecalho;
        mCodigo = eCodigo;

    }


    public ArrayList<AST> getCabecalho() {
        return mCabecalho;
    }

    public ArrayList<AST> getCodigo() {
        return mCodigo;
    }

}
