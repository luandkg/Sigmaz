package Sigmaz.Utils;

import java.util.ArrayList;

public class AST {

    private String mTipo = "";
    private String mNome = "";
    private String mValor = "";

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

    public void setTipo(String eTipo) {
        mTipo = eTipo;
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


    public boolean existeBranch(String eTipo) {
        boolean enc = false;

        for (AST mAST : mASTS) {
            if (mAST.mesmoTipo(eTipo)) {
                enc = true;
                break;
            }
        }

        return enc;
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

        for (AST mSubCopia : getASTS()) {

            AST mSub = mSubCopia.copiar();

            copia.getASTS().add(mSub);

        }

        return copia;


    }

    public void espelhar(AST espelho) {
        //try {
        this.setNome(espelho.getNome());
        this.setValor(espelho.getValor());
        this.getASTS().clear();
        this.getASTS().addAll(espelho.getASTS());
        // } catch (Exception e) {

        // }

    }


    public String ImprimirArvoreDeInstrucoes() {

        String eRet = "";

        eRet += "\n" + this.getTipo() + " -> " + this.getNome() + " : " + this.getValor();

        String eTab = "   ";

        for (AST a : getASTS()) {

            eRet += "\n" + eTab + a.getTipo() + " -> " + a.getNome() + " : " + a.getValor();

            eRet += ImprimirSubArvoreDeInstrucoes(eTab + "   ", a);

        }

        return eRet;
    }

    private String ImprimirSubArvoreDeInstrucoes(String ePref, AST ASTC) {

        String eRet = "";

        for (AST a : ASTC.getASTS()) {

            eRet += "\n" + ePref + a.getTipo() + " -> " + a.getNome() + " : " + a.getValor();

            eRet += ImprimirSubArvoreDeInstrucoes(ePref + "   ", a);

        }

        return eRet;
    }


    public boolean mesmoTipo(String eTipo) {
        return this.mTipo.contentEquals(eTipo);
    }

    public boolean mesmoNome(String eNome) {
        return this.mNome.contentEquals(eNome);
    }

    public boolean mesmoValor(String eValor) {
        return this.mValor.contentEquals(eValor);
    }


    public int getInstrucoes() {
        int t = 1;

        for (AST a : getASTS()) {
            if (a.getASTS().size() > 0) {
                t += subInstrucoes(a);
            }
            t += 1;
        }

        return t;
    }

    private int subInstrucoes(AST ASTC) {
        int t = 0;

        for (AST a : ASTC.getASTS()) {
            if (a.getASTS().size() > 0) {
                t += subInstrucoes(a);
            }
            t += 1;
        }

        return t;
    }

    public void limpar() {

        this.getASTS().clear();
        this.setNome("");
        this.setValor("");
        this.setTipo("");

    }

    public String toJava(int i) {


        Documento DocumentoC = new Documento();

        DocumentoC.adicionarLinha("AST AST_" + i + " = new AST(" + "\"" + this.getTipo() + "\"" + ");");
        DocumentoC.adicionarLinha("AST_" + i + ".setNome(" + "\"" + this.getNome() + "\"" + ");");
        DocumentoC.adicionarLinha("AST_" + i + ".setValor(" + "\"" + this.getValor() + "\"" + ");");

        dentroJava(DocumentoC, "AST_" + i, this);

        return DocumentoC.getConteudo();

    }

    private void dentroJava(Documento DocumentoC, String eDono, AST ASTPai) {

        int i = 0;

        for (AST ASTCGlobal : ASTPai.getASTS()) {


            DocumentoC.adicionarLinha("AST " + eDono + "_" + i + " = " + eDono + ".criarBranch(" + "\"" + ASTCGlobal.getTipo() + "\"" + ");");
            DocumentoC.adicionarLinha(eDono + "_" + i + ".setNome(" + "\"" + ASTCGlobal.getNome() + "\"" + ");");
            DocumentoC.adicionarLinha(eDono + "_" + i + ".setValor(" + "\"" + ASTCGlobal.getValor() + "\"" + ");");

            dentroJava(DocumentoC, eDono + "_" + i, ASTCGlobal);

            i += 1;

        }

    }

    public int getAltura() {

        int a = 1;

        int b = 0;

        for (AST Filho : getASTS()) {
            int c = Filho.getAltura();
            if (c > b) {
                b = c;
            }
        }


        return a + b;


    }

    public int getNiveis() {

        int tmp = 0;

        if (mASTS.size() > 0) {

            for (AST ea : mASTS) {
                if (ea.getNiveis() > tmp) {
                    tmp = ea.getNiveis();
                }
            }
            tmp = 1 + tmp;
        }


        return tmp;

    }

    public int getFilhosCompleto() {

        int tmp = 1;


        for (AST ea : mASTS) {

            tmp += ea.getFilhosCompleto();

        }


        return tmp;

    }


}
