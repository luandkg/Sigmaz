package Sigmaz.Compilador;

import Sigmaz.Lexer.Lexer;
import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.Documentador;
import Sigmaz.Utils.Requerimento;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;



public class Compiler {

    private int mIndex;
    private int mTamanho;

    private ArrayList<Token> mTokens;
    private ArrayList<AST> mASTS;

    private ArrayList<String> mErros_Lexer;
    private ArrayList<String> mErros_Compiler;

    private ArrayList<String> mRequisitados;

    private String mLocal;

    private int mIChars;
    private int mITokens;

    private ArrayList<Requerimento> mRequerimentos;

    public Compiler() {

        mIndex = 0;
        mTamanho = 0;

        mIChars=0;
        mITokens=0;

        mTokens = new ArrayList<>();
        mASTS = new ArrayList<>();
        mErros_Lexer = new ArrayList<>();
        mErros_Compiler = new ArrayList<>();
        mLocal = "";
        mRequerimentos = new ArrayList<>();
        mRequisitados = new ArrayList<>();

        RequirimentosCarregar();

    }

    public int getIChars() {
        return mIChars;
    }

    public int getITokens() {
        return mITokens;
    }

    public boolean Continuar() {
        return mIndex < mTamanho;
    }

    public void Proximo() {
        mIndex += 1;
    }

    public Token getTokenCorrente() {
        return mTokens.get(mIndex);
    }

    public ArrayList<AST> getASTS() {
        return mASTS;
    }

    public AST getAST(String eAST) {
        AST mRet = null;

        for (AST mAST : mASTS) {
            if (mAST.mesmoTipo(eAST)) {
                mRet = mAST;
                break;
            }
        }

        return mRet;
    }

    public ArrayList<String> getErros_Lexer() {
        return mErros_Lexer;
    }
    public ArrayList<String> getErros_Compiler() {
        return mErros_Compiler;
    }

    public ArrayList<String> getRequisitados() {
        return mRequisitados;
    }

    public int Instrucoes() {
        int t = 0;

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

    public void init(String eArquivo, String eLocal) {
        mIndex = 0;

        mTokens.clear();
        mASTS.clear();
        mErros_Lexer.clear();
        mErros_Compiler.clear();
        mRequisitados.clear();
        mLocal = eLocal;
        mIChars=0;
        mITokens=0;

        Lexer LexerC = new Lexer();

        LexerC.init(eArquivo);
        mIChars=LexerC.getChars();
        mITokens=LexerC.getTokens().size();

        System.out.println(" -->> " + mITokens);


        for (String mErro : LexerC.getErros()) {
            mErros_Lexer.add(mErro);
        }


        for (Token TokenC : LexerC.getTokens()) {
            if (TokenC.Tipo() != TokenTipo.COMENTARIO) {
                mTokens.add(TokenC);
            }
        }

        mTamanho = mTokens.size();

        AST AST_Raiz = new AST("SIGMAZ");
        mASTS.add(AST_Raiz);


        AST AST_INIT= AST_Raiz.criarBranch("INIT");
        AST AST_LIBS= AST_Raiz.criarBranch("LIBS");



    }



    public String ArvoreDeInstrucoes() {

        Documentador documentadorC = new Documentador();

        return documentadorC.ImprimirArvoreDeInstrucoes_De(getASTS());
    }

    public String ArvoreDeInstrucoes_De(ArrayList<AST> lsAST) {

        Documentador documentadorC = new Documentador();

        return documentadorC.ImprimirArvoreDeInstrucoes_De(lsAST);

    }


    public void Compilar(String eArquivo) {

        Documentador documentadorC = new Documentador();

        String documento = documentadorC.MontandoArvoreDeInstrucoes(this.getASTS());

        byte[] bytes = documento.getBytes(StandardCharsets.UTF_8);

        int t = bytes.length;
        //System.out.println("Tam : " + t);

        int i = 0;
        int o = documento.length();

        int auxilador = 53;

        while (i < o) {
            int novo = (int) bytes[i];

            novo += auxilador;
            if (novo >= 255) {
                novo -= 255;
            }


            bytes[i] = (byte) novo;
            i += 1;
        }

        Path path = Paths.get(eArquivo);
        try {
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public String tamanhoObjeto(String eArquivo) {

        File file = new File(eArquivo);

        long t = file.length();


        return t + "";
    }


    public String mapaObjeto(String eArquivo) {

        String saida = "";

        Path dpath = Paths.get(eArquivo);


        try {
            byte[] l = Files.readAllBytes(dpath);

            int li = 0;
            int lo = l.length;

            int d = 0;

            while (li < lo) {
                int novo = (int) l[li];

                saida += " " + novo;


                if (d >= 50) {
                    d = 0;
                    saida += "\n";
                }

                li += 1;
                d += 1;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return saida;

    }


    public ArrayList<AST> Decompilar(String eArquivo) {

        ArrayList<AST> ASTSaida = new ArrayList<AST>();


        Path dpath = Paths.get(eArquivo);
        int auxilador = 53;

        String saida = "";

        try {
            byte[] l = Files.readAllBytes(dpath);

            int li = 0;
            int lo = l.length;

            while (li < lo) {
                int novo = (int) l[li];

                novo -= auxilador;
                if (novo < 0) {
                    novo += 256;
                }


                l[li] = (byte) novo;
                li += 1;
            }

            //try {
            //	Files.write(dpath, l);
            //} catch (IOException e) {
            //	e.printStackTrace();
            //}

            saida = new String(l, "UTF-8");

            //	System.out.println("Decompilado : " + saida);


        } catch (IOException e) {
            e.printStackTrace();
        }

        Documentador DC = new Documentador();

        ASTSaida = DC.decompilar(saida);

        return ASTSaida;

    }


    public void RequirimentosCarregar() {

        Bibliotecas BibliotecaC = new Bibliotecas();

        mRequerimentos.add(new Requerimento("operador", BibliotecaC.operador()));
        mRequerimentos.add(new Requerimento("area", BibliotecaC.area()));

    }

}
