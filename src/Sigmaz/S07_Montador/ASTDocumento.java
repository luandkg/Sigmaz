package Sigmaz.S07_Montador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Documento;

import java.util.ArrayList;

public class ASTDocumento {

    public final String CRIADO = "2020 10 10";

    private int mIndex;
    private int mTamanho;
    private String mConteudo;

    private final String ALFABETO = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_@.0123456789";

    private boolean mOk;
    private ArrayList<AST> mASTS;


    public ASTDocumento() {

        mOk = false;
        mASTS = new ArrayList<AST>();

    }

    public void anular() {
        mOk = false;
    }

    public void validar() {
        mOk = true;
    }

    public boolean getOk() {
        return mOk;
    }

    public ArrayList<AST> getASTS() {
        return mASTS;
    }


    public String toDocumento(ArrayList<AST> lsAST) {

        mASTS = lsAST;
        mOk = true;

        Documento mDocumento = new Documento();

        int mTab = 0;

        for (AST mASTCorrente : lsAST) {

            objeto_AST(mDocumento, mTab, mASTCorrente);

        }


        return mDocumento.getConteudo();

    }

    private void objeto_AST(Documento mDocumento, int mTab, AST mASTCorrente) {

        String eNome = Codifica(mASTCorrente.getNome());
        String eValor = Codifica(mASTCorrente.getValor());

        String eLinha = "";

        if (mASTCorrente.getASTS().size() == 0) {


            if (eNome.length() == 0) {

                if (eValor.length() == 0) {
                    eLinha = Codifica(mASTCorrente.getTipo()) + " :: { }";
                } else {
                    eLinha = Codifica(mASTCorrente.getTipo()) + " :: " + "\"" + eNome + "\"" + " = " + "\"" + eValor + "\" { }";
                }

            } else {
                eLinha = Codifica(mASTCorrente.getTipo()) + " :: " + "\"" + eNome + "\"" + " = " + "\"" + eValor + "\" { }";
            }


            mDocumento.adicionarLinha(mTab, eLinha);

        } else {


            if (eNome.length() == 0) {

                if (eValor.length() == 0) {
                    eLinha = Codifica(mASTCorrente.getTipo()) + " :: {";
                } else {
                    eLinha = Codifica(mASTCorrente.getTipo()) + " :: " + "\"" + eNome + "\"" + " = " + "\"" + eValor + "\" {";
                }

            } else {
                eLinha = Codifica(mASTCorrente.getTipo()) + " :: " + "\"" + eNome + "\"" + " = " + "\"" + eValor + "\" {";
            }

            mDocumento.adicionarLinha(mTab, eLinha);


            for (AST mASTCorrente_Sub : mASTCorrente.getASTS()) {

                objeto_AST(mDocumento, mTab + 1, mASTCorrente_Sub);

            }

            mDocumento.adicionarLinha(mTab, " }");


        }


    }

    public String toDocumentoReduzido(ArrayList<AST> lsAST) {

        mASTS = lsAST;
        mOk = true;

        Documento mDocumento = new Documento();

        int mTab = 0;

        for (AST mASTCorrente : lsAST) {

            objetoReduzido_AST(mDocumento, mTab, mASTCorrente);

        }


        return mDocumento.getConteudo();

    }

    private void objetoReduzido_AST(Documento mDocumento, int mTab, AST mASTCorrente) {

        String eNome = Codifica(mASTCorrente.getNome());
        String eValor = Codifica(mASTCorrente.getValor());

        String eLinha = "";

        if (mASTCorrente.getASTS().size() == 0) {


            if (eNome.length() == 0) {

                if (eValor.length() == 0) {
                    eLinha = Codifica(mASTCorrente.getTipo()) + " :: { }";
                } else {
                    eLinha = Codifica(mASTCorrente.getTipo()) + " :: " + "\"" + eNome + "\"" + " = " + " \"" + eValor + "\" { }";
                }

            } else {
                eLinha = Codifica(mASTCorrente.getTipo()) + " :: " + "\"" + eNome + "\"" + " = " + " \"" + eValor + "\" { }";
            }


            mDocumento.adicionarLinha(mTab, eLinha);

        } else {


            if (eNome.length() == 0) {

                if (eValor.length() == 0) {
                    eLinha = Codifica(mASTCorrente.getTipo()) + " :: { ";
                } else {
                    eLinha = Codifica(mASTCorrente.getTipo()) + " :: " + "\"" + eNome + "\"" + " = " + " \"" + eValor + "\" { ";
                }

            } else {
                eLinha = Codifica(mASTCorrente.getTipo()) + " :: " + "\"" + eNome + "\"" + " = " + " \"" + eValor + "\" { ";
            }

            mDocumento.adicionarLinha(mTab, eLinha);


            for (AST mASTCorrente_Sub : mASTCorrente.getASTS()) {

                objetoReduzido_AST(mDocumento, mTab, mASTCorrente_Sub);

            }

            mDocumento.adicionarLinha(mTab, " } ");


        }


    }


    private String Codifica(String e) {
        e = e.replace("@", "@A");
        e = e.replace("'", "@S");
        e = e.replace("\"", "@D");
        e = e.replace("-", "@H");

        return e;
    }

    public void parserDocumento(String eConteudo) {


        mConteudo = eConteudo;

        mIndex = 0;
        mTamanho = mConteudo.length();

        mOk = true;
        getASTS().clear();

        while (mIndex < mTamanho) {
            String l = String.valueOf(mConteudo.charAt(mIndex));

            if (l.contentEquals(" ")) {
            } else if (l.contentEquals("\n")) {
            } else if (l.contentEquals("\t")) {
            } else if (l.contentEquals("\r")) {

            } else if (ALFABETO.contains(l)) {
                String Palavra = Decodifica(ParsePalavra());

                AST ASTCorrente = new AST(Palavra);

                mIndex += 1;
                Parser_AST(ASTCorrente);


                getASTS().add(ASTCorrente);

            } else {


            }

            mIndex += 1;
        }

    }


    private String ParsePalavra() {

        String p = String.valueOf(mConteudo.charAt(mIndex));
        String ret = p;

        if (ALFABETO.contains(p)) {

            mIndex += 1;

            while (mIndex < mTamanho) {

                String d = String.valueOf(mConteudo.charAt(mIndex));

                if (ALFABETO.contains(d)) {
                    ret += d;
                } else {
                    mIndex -= 1;
                    break;
                }

                mIndex += 1;

            }

        }

        return ret;
    }

    private void Parser_AST(AST ASTCorrente) {

        boolean mEsperarQUAD = esperarQuad();

        if (mEsperarQUAD) {

            boolean mBateu = esperarBater();

            if (mIndex >= mTamanho) {
                mOk = false;
                return;
            }

            if (!String.valueOf(mConteudo.charAt(mIndex)).contentEquals("{")) {
                String mNome = Decodifica(Esperar_Texto());

                boolean mEsperarIgual = esperarIgual();

                String mValor = Decodifica(Esperar_Texto());


                ASTCorrente.setNome(mNome);

                ASTCorrente.setValor(mValor);

            }


            boolean mEsperarEscopo = esperarAbrirChaves();

            if (mEsperarEscopo) {
                dentroEscopo(ASTCorrente);
            }

        } else {
            // mResp.anular();
        }


    }

    public void dentroEscopo(AST ASTPai) {


        while (mIndex < mTamanho) {

            if (mIndex >= mTamanho) {
                mOk = false;
                return;
            }
            
            String l = String.valueOf(mConteudo.charAt(mIndex));

            if (l.contentEquals(" ")) {
            } else if (l.contentEquals("\n")) {
            } else if (l.contentEquals("\t")) {
            } else if (l.contentEquals("\r")) {
            } else if (l.contentEquals("}")) {
                break;
            } else if (ALFABETO.contains(l)) {
                String Palavra = Decodifica(ParsePalavra());

                AST ASTCorrente = new AST(Palavra);

                mIndex += 1;
                Parser_AST(ASTCorrente);

                ASTPai.getASTS().add(ASTCorrente);

            } else {


            }

            mIndex += 1;
        }


    }

    public boolean esperarQuad() {
        boolean e = false;

        int c = 0;

        while (mIndex < mTamanho) {
            String l = String.valueOf(mConteudo.charAt(mIndex));

            if (l.contentEquals(" ")) {
            } else if (l.contentEquals("\n")) {
            } else if (l.contentEquals("\t")) {
            } else if (l.contentEquals("\r")) {
            } else if (l.contentEquals(":")) {
                c += 1;
                mIndex += 1;
                break;
            } else {


            }

            mIndex += 1;
        }

        while (mIndex < mTamanho) {
            String l = String.valueOf(mConteudo.charAt(mIndex));

            if (l.contentEquals(" ")) {
            } else if (l.contentEquals("\n")) {
            } else if (l.contentEquals("\t")) {
            } else if (l.contentEquals("\r")) {
            } else if (l.contentEquals(":")) {
                c += 1;
                mIndex += 1;
                break;
            } else {


            }

            mIndex += 1;
        }

        if (c == 2) {
            e = true;
        }

        return e;
    }

    public boolean esperarIgual() {
        boolean e = false;


        while (mIndex < mTamanho) {
            String l = String.valueOf(mConteudo.charAt(mIndex));

            if (l.contentEquals(" ")) {
            } else if (l.contentEquals("\n")) {
            } else if (l.contentEquals("\t")) {
            } else if (l.contentEquals("\r")) {
            } else if (l.contentEquals("=")) {
                e = true;
                mIndex += 1;
                break;
            } else {


            }

            mIndex += 1;
        }


        return e;
    }


    public boolean esperarBater() {
        boolean e = false;

        while (mIndex < mTamanho) {
            String l = String.valueOf(mConteudo.charAt(mIndex));

            if (l.contentEquals(" ")) {
            } else if (l.contentEquals("\n")) {
            } else if (l.contentEquals("\t")) {
            } else if (l.contentEquals("\r")) {
            } else if (l.contentEquals("{")) {
                e = true;
                break;
            } else if (l.contentEquals("\'")) {
                e = true;
                break;
            } else if (l.contentEquals("\"")) {
                e = true;
                break;
            } else {


            }

            mIndex += 1;
        }


        return e;
    }

    public boolean esperarAbrirChaves() {
        boolean e = false;


        while (mIndex < mTamanho) {
            String l = String.valueOf(mConteudo.charAt(mIndex));

            if (l.contentEquals(" ")) {
            } else if (l.contentEquals("\n")) {
            } else if (l.contentEquals("\t")) {
            } else if (l.contentEquals("\r")) {
            } else if (l.contentEquals("{")) {
                e = true;
                mIndex += 1;
                break;
            } else {


            }

            mIndex += 1;
        }


        return e;
    }


    private String Esperar_Texto() {

        // System.out.println("Aqui " + String.valueOf(mDocumento.charAt(mIndex)));

        String ret = "";

        while (mIndex < mTamanho) {

            String l = String.valueOf(mConteudo.charAt(mIndex));

            if (l.contentEquals("'")) {
                mIndex += 1;
                ret = BuscandoTexto("'");
                break;
            } else if (l.contentEquals("\"")) {
                mIndex += 1;
                ret = BuscandoTexto("\"");
                break;
            } else if (l.contentEquals(" ")) {
            } else if (l.contentEquals("\n")) {
            } else if (l.contentEquals("\t")) {
            } else {
                break;
            }

            mIndex += 1;

        }

        return ret;
    }

    private String BuscandoTexto(String Finalizador) {

        // System.out.println("Aqui " + String.valueOf(mDocumento.charAt(mIndex)));

        String ret = "";

        while (mIndex < mTamanho) {

            String l = String.valueOf(mConteudo.charAt(mIndex));

            if (l.contentEquals(Finalizador)) {
                mIndex += 1;
                break;

            } else {
                ret += l;
            }

            mIndex += 1;

        }

        return ret;
    }


    public String esperarNome() {

        String ret = "";

        while (mIndex < mTamanho) {
            String l = String.valueOf(mConteudo.charAt(mIndex));

            if (l.contentEquals(" ")) {
            } else if (l.contentEquals("\n")) {
            } else if (l.contentEquals("\t")) {
            } else if (l.contentEquals("\r")) {
            } else if (l.contentEquals("=")) {
                break;
            } else if (l.contentEquals("{")) {
                break;
            } else if (ALFABETO.contains(l)) {
                ret = ParsePalavra();
                break;
            } else {
                break;
            }

            mIndex += 1;
        }


        return ret;
    }

    public String Decodifica(String e) {
        e = e.replace("@H", "-");
        e = e.replace("@D", "\"");
        e = e.replace("@S", "'");
        e = e.replace("@A", "@");

        return e;
    }

}
