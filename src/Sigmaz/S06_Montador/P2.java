package Sigmaz.S06_Montador;


import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Texto;

import java.util.ArrayList;

public class P2 {

    private int mInstrucoes;
    private int mObjetos;
    private int mTamanho;
    private int mProfundidade;

    private boolean mOK;
    private ArrayList<AST> mDesempacotado;

    public P2() {

        mInstrucoes = 0;
        mObjetos = 0;
        mTamanho = 0;
        mProfundidade = 0;

        mOK = false;
        mDesempacotado = new ArrayList<>();

    }

    public ArrayList<AST> getDesempacotado() {
        return mDesempacotado;
    }

    public String empacotar(ArrayList<AST> lsAST) {

        mInstrucoes = 0;
        mObjetos = 0;
        mTamanho = 0;
        mProfundidade = 0;

        mOK = false;
        mDesempacotado=lsAST;

        analisar();

        OLM mOLM = new OLM();

         String mDocumentoNovo = mOLM.toDocumento(lsAST);
        String mDocumentoReduzido = mOLM.toDocumentoReduzido(lsAST);

        mTamanho = mDocumentoReduzido.length();

          // Texto.Escrever("res/build/ASTS.txt", mDocumentoNovo);
        //  Texto.Escrever("res/build/ASTR.txt", mDocumentoReduzido);


        mOK = true;

        return mDocumentoReduzido;

    }

    public void analisar(){

        for (AST a : mDesempacotado) {
            mInstrucoes += a.getInstrucoes();
            mObjetos += a.getObjetos();
            int prof = a.getProfundidade(0);
            if (prof > mProfundidade) {
                mProfundidade = prof;
            }

         //   System.out.println(a.getImpressao());

        }


    }


    public void desempacotar(String eConteudo) {

        mInstrucoes = 0;
        mObjetos = 0;
        mTamanho = 0;
        mOK = false;

        mDesempacotado.clear();

        OLM mOLM = new OLM();

        mOLM.parserDocumento(eConteudo);

        if (mOLM.getOk()) {

            mDesempacotado = mOLM.getASTS();

            analisar();

            mOK = true;

        } else {
            mOK = false;
        }


    }


    public int getInstrucoes() {
        return mInstrucoes;
    }

    public int getObjetos() {
        return mObjetos;
    }

    public int getTamanho() {
        return mTamanho;
    }

    public int getProfundidade() {
        return mProfundidade;
    }

    public boolean getOK() {
        return mOK;
    }

}
