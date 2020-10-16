package Sigmaz.S04_Compilador;

import java.util.ArrayList;

import Sigmaz.S01_PreProcessamento.Etapa;
import Sigmaz.S01_PreProcessamento.PreProcessamento;
import Sigmaz.S00_Utilitarios.*;
import Sigmaz.S05_PosProcessamento.Processadores.Cabecalho;

public class Compiler {

    private ArrayList<AST> mCabecalhos;
    private ArrayList<AST> mASTS;


    private ArrayList<String> mRequisitados;

    private ArrayList<GrupoDeErro> mErros_PreProcessamento;
    private ArrayList<GrupoDeErro> mErros_Lexer;
    private ArrayList<GrupoDeErro> mErros_Compiler;

    private ArrayList<GrupoDeComentario> mComentarios;

    private int mIChars;
    private int mITokens;

    private String mPreProcessamento;
    private String mLexer_Processamento;

    private boolean mPre;
    PreProcessamento mPreProcessador;
    private String mPreProcessando;

    private boolean mCorrentePreprocessando;
    private boolean mCorrenteParseando;
    private boolean mCorrenteCompilando;

    private ArrayList<Etapa> mEtapas;


    public Compiler() {

        mCabecalhos= new ArrayList<>();
        mASTS = new ArrayList<>();


        mRequisitados = new ArrayList<>();

        mErros_PreProcessamento = new ArrayList<>();
        mErros_Lexer = new ArrayList<>();
        mErros_Compiler = new ArrayList<>();

        mComentarios = new ArrayList<>();


        mIChars = 0;
        mITokens = 0;
        mPreProcessamento = "";
        mLexer_Processamento = "";

        mPre = false;

        mEtapas = new ArrayList<Etapa>();

    }


    public ArrayList<Etapa> getEtapas() {
        return mEtapas;
    }


    public boolean getPre() {
        return mPre;
    }

    public String getPreProcessando() {
        return mPreProcessando;
    }

    public boolean estaPre() {
        return mCorrentePreprocessando;
    }


    public ArrayList<AST> getCabecalhos() {
        return mCabecalhos;
    }

    public ArrayList<AST> getASTS() {
        return mASTS;
    }


    public int getIChars() {
        return mIChars;
    }

    public int getITokens() {
        return mITokens;
    }

    public int getInstrucoes() {
        int t = 0;

        for (AST a : getASTS()) {
            t += a.getInstrucoes();
        }

        return t;
    }

    public ArrayList<String> getRequisitados() {
        return mRequisitados;
    }

    public String getPreProcessamento() {
        return mPreProcessamento;
    }

    public String getLexer_Processamento() {
        return mLexer_Processamento;
    }

    public void init(String eArquivo, int mOpcao) {


        ArrayList<String> eArquivos = new ArrayList<String>();
        eArquivos.add(eArquivo);

        initvarios(eArquivos, mOpcao);

    }

    public void limpar() {

        mRequisitados.clear();

        mErros_PreProcessamento.clear();
        mErros_Lexer.clear();
        mErros_Compiler.clear();

        mComentarios.clear();

        mIChars = 0;
        mITokens = 0;
        mPreProcessamento = "";
        mLexer_Processamento = "";

    }

    public void initvarios(ArrayList<String> eArquivos, int mOpcao) {

        limpar();

        mPre = false;

        AST AST_Raiz = new AST("SIGMAZ");


        mASTS.add(AST_Raiz);

        UUID mUUIDC = new UUID();

        AST_Raiz.setValor(mUUIDC.getUUID());

        if (mOpcao == 1) {

            AST_Raiz.setNome("EXECUTABLE");

        } else if (mOpcao == 2) {

            AST_Raiz.setNome("LIBRARY");

        }


        PreProcessamento mPreProcessador = new PreProcessamento();
        mPreProcessador.init(eArquivos, AST_Raiz);

        mPreProcessamento = mPreProcessador.getPreProcessamento();
        mLexer_Processamento = mPreProcessador.getLexer_Processamento();

        mIChars = mPreProcessador.getIChars();
        mITokens = mPreProcessador.getITokens();

        mEtapas = mPreProcessador.getEtapas();

        for (String mRequisicao : mPreProcessador.getRequisitados()) {
            mRequisitados.add(mRequisicao);
        }

        for (GrupoDeComentario mGrupoDeComentario : mPreProcessador.getComentarios()) {
            mComentarios.add(mGrupoDeComentario);
        }

        for (GrupoDeErro mGrupoDeErro : mPreProcessador.getErros_Processamento()) {
            mErros_PreProcessamento.add(mGrupoDeErro);
        }

        for (GrupoDeErro mGrupoDeErro : mPreProcessador.getErros_Lexer()) {
            mErros_Lexer.add(mGrupoDeErro);
        }


        for (GrupoDeErro mGrupoDeErro : mPreProcessador.getErros_Compiler()) {
            mErros_Compiler.add(mGrupoDeErro);
        }



    }



    public void initPreProcessamento(String eArquivo, int mOpcao) {

        mPre = false;
        mPreProcessando = "";

        ArrayList<String> eArquivos = new ArrayList<String>();
        eArquivos.add(eArquivo);

        initprevarios(eArquivos, mOpcao);

    }


    public void initprevarios(ArrayList<String> eArquivos, int mOpcao) {

        limpar();

        mPre = false;

        AST AST_Raiz = new AST("SIGMAZ");


        mASTS.add(AST_Raiz);

        UUID mUUIDC = new UUID();

        AST_Raiz.setValor(mUUIDC.getUUID());

        if (mOpcao == 1) {

            AST_Raiz.setNome("EXECUTABLE");

        } else if (mOpcao == 2) {

            AST_Raiz.setNome("LIBRARY");

        }


        mPreProcessador = new PreProcessamento();
        mPreProcessador.initPassos(eArquivos, AST_Raiz);
        mPreProcessando = mPreProcessador.getProcessando();


    }


    public void continuarPreProcessamento() {

        if (mPreProcessador.getTerminou()) {

            mPre = true;

            mPreProcessamento = mPreProcessador.getPreProcessamento();
            mLexer_Processamento = mPreProcessador.getLexer_Processamento();

            mIChars = mPreProcessador.getIChars();
            mITokens = mPreProcessador.getITokens();


            for (String mRequisicao : mPreProcessador.getRequisitados()) {
                mRequisitados.add(mRequisicao);
            }

            for (GrupoDeComentario mGrupoDeComentario : mPreProcessador.getComentarios()) {
                mComentarios.add(mGrupoDeComentario);
            }

            for (GrupoDeErro mGrupoDeErro : mPreProcessador.getErros_Processamento()) {
                mErros_PreProcessamento.add(mGrupoDeErro);
            }

            for (GrupoDeErro mGrupoDeErro : mPreProcessador.getErros_Lexer()) {
                mErros_Lexer.add(mGrupoDeErro);
            }


            for (GrupoDeErro mGrupoDeErro : mPreProcessador.getErros_Compiler()) {
                mErros_Compiler.add(mGrupoDeErro);
            }

        } else {

            mPreProcessador.continuar();
            mPreProcessando = mPreProcessador.getProcessando();

            mCorrentePreprocessando = mPreProcessador.estaPreProcessando();
            mCorrenteParseando = mPreProcessador.estaParseando();
            mCorrenteCompilando = mPreProcessador.estaCompilando();

        }


    }


    public ArrayList<GrupoDeErro> getErros_PreProcessamento() {
        return mErros_PreProcessamento;
    }

    public ArrayList<GrupoDeErro> getErros_Lexer() {
        return mErros_Lexer;
    }

    public ArrayList<GrupoDeErro> getErros_Compiler() {
        return mErros_Compiler;
    }

    public ArrayList<GrupoDeComentario> getComentarios() {
        return mComentarios;
    }


    public String getArvoreDeInstrucoes() {

        Documento DocumentoC = new Documento();

        DocumentoC.adicionarLinha("");

        for (AST a : getASTS()) {

            if (a.getValor().length() > 0) {
                DocumentoC.adicionarLinha(" " + a.getTipo() + " -> " + a.getNome() + " : " + a.getValor());
            } else {
                DocumentoC.adicionarLinha(" " + a.getTipo() + " -> " + a.getNome());
            }

            SubArvoreDeInstrucoes("   ", a, DocumentoC);

        }

        DocumentoC.adicionarLinha("");

        return DocumentoC.getConteudo();
    }

    private void SubArvoreDeInstrucoes(String ePref, AST ASTC, Documento DocumentoC) {

        for (AST a : ASTC.getASTS()) {

            if (a.getValor().length() > 0) {
                DocumentoC.adicionarLinha(ePref + a.getTipo() + " -> " + a.getNome() + " : " + a.getValor());

            } else {
                DocumentoC.adicionarLinha(ePref + a.getTipo() + " -> " + a.getNome());

            }

            SubArvoreDeInstrucoes(ePref + "   ", a, DocumentoC);

        }

    }


    public String getData() {

        return Tempo.getData();


    }

    public String imprimirArvore() {
        String e = "";

        for (AST eAST : mASTS) {
            e += eAST.ImprimirArvoreDeInstrucoes() + "\n";
        }

        return e;
    }

    public boolean estaPreProcessando() {
        return mCorrentePreprocessando;
    }

    public boolean estaParseando() {
        return mCorrenteParseando;
    }

    public boolean estaCompilando() {
        return mCorrenteCompilando;
    }

}
