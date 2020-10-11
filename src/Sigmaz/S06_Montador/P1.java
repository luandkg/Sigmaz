package Sigmaz.S06_Montador;

import LuanDKG.LuanDKG;
import LuanDKG.Pacote;

import Sigmaz.S00_Utilitarios.AST;
import java.util.ArrayList;

public class P1 {

    private int mInstrucoes;
    private int mObjetos;
    private int mTamanho;
    private boolean mOK;
    private ArrayList<AST> mDesempacotado;

    public P1() {

        mInstrucoes = 0;
        mObjetos = 0;
        mTamanho = 0;
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
        mOK = false;

        LuanDKG DocumentoC = new LuanDKG();


        for (AST a : lsAST) {

            Pacote ePacote = new Pacote(a.getTipo());

            DocumentoC.getPacotes().add(ePacote);
            compilar_ASTPackage(ePacote, a);

            mInstrucoes += a.getInstrucoes();

        }

        mObjetos = DocumentoC.contagem();

      //  DocumentoC.Salvar("res/build/ASTPackage.txt");

        String eDocumento = DocumentoC.gerarReduzido();
        mTamanho = eDocumento.length();


        mOK = true;

        return eDocumento;

    }

    public void desempacotar(String eConteudo) {

        mInstrucoes = 0;
        mObjetos = 0;
        mTamanho = 0;
        mOK = false;

        mDesempacotado.clear();


        LuanDKG DocumentoC = new LuanDKG();
        DocumentoC.Parser(eConteudo);

        if (DocumentoC.getErros().size() == 0) {

            for (Pacote mPacote : DocumentoC.getPacotes()) {

                AST mAST = new AST(mPacote.getNome());
                mDesempacotado.add(mAST);

                decompilar_ASTPackage(mAST, mPacote);
            }

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

    public boolean getOK() {
        return mOK;
    }

    private void compilar_ASTPackage(Pacote ePacotePai, AST eAST) {

        for (AST a : eAST.getASTS()) {

            Pacote ePacote = new Pacote(a.getTipo());
            ePacote.Identifique("Nome", a.getNome());
            ePacote.Identifique("Valor", a.getValor());
            ePacotePai.getPacotes().add(ePacote);

            compilar_ASTPackage(ePacote, a);

        }

    }


    private void decompilar_ASTPackage(AST eAST, Pacote ePacotePai) {

        for (Pacote mPacote : ePacotePai.getPacotes()) {

            String aNome = mPacote.Identifique("Nome").getValor();
            String aValor = mPacote.Identifique("Valor").getValor();

            AST mAST = new AST(mPacote.getNome());
            mAST.setNome(aNome);
            mAST.setValor(aValor);
            eAST.getASTS().add(mAST);


            decompilar_ASTPackage(mAST, mPacote);

        }

    }


}
