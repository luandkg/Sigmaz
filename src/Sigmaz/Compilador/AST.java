package Sigmaz.Compilador;

import java.util.ArrayList;

public class AST {

    private String mTipo;
    private String mNome;
    private String mValor;

    private ArrayList<AST> mASTS;

    public AST(String eTipo) {

        mASTS = new ArrayList<>();

        mTipo = eTipo;
        mNome = "";
        mValor = "";

    }

    public ArrayList<AST> getASTS() {
        return mASTS;
    }

    public String getTipo() {
        return mTipo;
    }


    public String getNome() {
        return mNome;
    }

    public void setNome(String eNome) {
        mNome = eNome;
    }

    public String getValor() {
        return mValor;
    }

    public void setValor(String eValor) {
        mValor = eValor;
    }



    public AST getBranch(String eTipo) {
        AST mRet = null;

        for (AST mAST : mASTS) {
            if (mAST.mesmoTipo(eTipo)) {
                mRet = mAST;
                break;
            }
        }

        return mRet;
    }

    public AST criarBranch(String eTipo) {

        AST mRet = new AST(eTipo);
        mASTS.add(mRet);


        return mRet;
    }


    public AST copiar() {


        AST copia = new AST(this.getTipo());
        copia.setNome(this.getNome());
        copia.setValor(this.getValor());

        for (AST a : getASTS()) {

            copia.getASTS().add(a.copiar());

        }

        return copia;


    }


    public void ImprimirArvoreDeInstrucoes() {

        System.out.println(this.getTipo() + " -> " + this.getNome());

        for (AST a : getASTS()) {

            System.out.println("        " + a.getTipo() + " -> " + a.getNome());

            ImprimirSubArvoreDeInstrucoes("   ", a);

        }

    }

    private void ImprimirSubArvoreDeInstrucoes(String ePref, AST ASTC) {

        for (AST a : ASTC.getASTS()) {

            System.out.println(ePref + a.getTipo() + " -> " + a.getNome());

            ImprimirSubArvoreDeInstrucoes(ePref + "   ", a);

        }

    }


    public boolean mesmoTipo(String eTipo) {
        return this.mTipo.contentEquals(eTipo);
    }





}
