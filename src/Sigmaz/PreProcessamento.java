package Sigmaz;

import Sigmaz.Compilador.CompilerUnit;
import Sigmaz.Utils.*;

import java.io.File;
import java.util.ArrayList;

public class PreProcessamento {

    private int mITokens;
    private int mIChars;

    private ArrayList<String> mRequisitados;

    private ArrayList<GrupoDeErro> mErros_Processamento;
    private ArrayList<GrupoDeErro> mErros_Lexer;
    private ArrayList<GrupoDeErro> mErros_Compiler;

    private ArrayList<GrupoDeComentario> mComentarios;

    private String mPreProcessamento;
    private String mLexer_Processamento;

    private String mPreProcessando;

    private boolean mCorrentePreprocessando;
    private boolean mCorrenteParseando;
    private boolean mCorrenteCompilando;


    public PreProcessamento() {

        mPreProcessando = "";

        mPreProcessamento = "";
        mLexer_Processamento = "";

        mITokens = 0;
        mIChars = 0;

        mRequisitados = new ArrayList<>();

        mErros_Processamento = new ArrayList<>();
        mErros_Lexer = new ArrayList<>();
        mErros_Compiler = new ArrayList<>();

        mComentarios = new ArrayList<>();


    }

    public void init(ArrayList<String> eArquivos, AST AST_Raiz) {

        mITokens = 0;
        mIChars = 0;

        mRequisitados.clear();

        mErros_Processamento.clear();
        mErros_Lexer.clear();
        mErros_Compiler.clear();

        mComentarios.clear();

        mPreProcessamento = "";
        mLexer_Processamento = "";


        Enfileirador mFila = new Enfileirador();

        for (String e : eArquivos) {
            mFila.adicionar(e);
        }

        mFila.iniciar();

        int mQuantidade = 0;

        while (mFila.estaExecutando()) {


            String mLocalRequisicao = mFila.processar();

            if (mLocalRequisicao != null) {


                mRequisitados.add(mLocalRequisicao);

                File arq = new File(mLocalRequisicao);


                if (arq.exists()) {

                    CompilerUnit mCompilerUnit = new CompilerUnit();
                    mCompilerUnit.init(mLocalRequisicao, AST_Raiz, mRequisitados);

                    mITokens += mCompilerUnit.getITokens();
                    mIChars += mCompilerUnit.getIChars();

                    mLexer_Processamento += mQuantidade + "  -->> " + mLocalRequisicao + " [ Chars : " + mCompilerUnit.getIChars() + " Tokens : " + mCompilerUnit.getITokens() + "]\n";

                    mQuantidade += 1;

                    for (Importacao a : mCompilerUnit.getFila()) {
                        mFila.momentoAdicionar(a.getImportando());

                        File arqIncluir = new File(a.getImportando());
                        if (!arqIncluir.exists()) {
                            GrupoDeErro nG = new GrupoDeErro(a.getOrigem());
                            nG.adicionarErro("Importacao nao encontrado : " + a.getImportando(), a.getLinha(), a.getColuna());
                            mErros_Processamento.add(nG);
                            break;
                        }

                    }

                    getErros_Lexer().addAll(mCompilerUnit.getErros_Lexer());
                    getErros_Compiler().addAll(mCompilerUnit.getErros_Compiler());
                    getComentarios().addAll(mCompilerUnit.getComentarios());

                    if (mErros_Processamento.size() > 0) {
                        break;
                    }

                } else {

                    GrupoDeErro nG = new GrupoDeErro("SIGMAZ");
                    nG.adicionarErro("Arquivo nao encontrado : " + mLocalRequisicao, 0, 0);
                    mErros_Processamento.add(nG);
                    break;
                }

            }


            mFila.organizar();
            if (mErros_Processamento.size() > 0) {
                break;
            }

        }

        mLexer_Processamento += "\n";

        mLexer_Processamento += "Parser Total -->> " + mQuantidade + " [ Chars : " + mIChars + " Tokens : " + mITokens + "]\n";


        mPreProcessamento = mFila.getProcessamento();

    }

    public int getIChars() {
        return mIChars;
    }

    public int getITokens() {
        return mITokens;
    }


    public String getPreProcessamento() {
        return mPreProcessamento;
    }

    public String getLexer_Processamento() {
        return mLexer_Processamento;
    }

    public ArrayList<String> getRequisitados() {
        return mRequisitados;
    }


    public ArrayList<GrupoDeErro> getErros_Processamento() {
        return mErros_Processamento;
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


    private Enfileirador mFila;
    private AST AST_Raiz;

    public void initPassos(ArrayList<String> eArquivos, AST eAST_Raiz) {

        mPreProcessando = "";

        AST_Raiz = eAST_Raiz;

        mITokens = 0;
        mIChars = 0;

        mRequisitados.clear();

        mErros_Processamento.clear();
        mErros_Lexer.clear();
        mErros_Compiler.clear();

        mComentarios.clear();

        mPreProcessamento = "";
        mLexer_Processamento = "";


        mFila = new Enfileirador();

        for (String e : eArquivos) {
            mFila.adicionar(e);
        }

        mFila.iniciar();

        mContinuarInterno = 0;

        continuar();


    }

    private int mContinuarInterno = 0;
    private int mQuantidade = 0;
    private CompilerUnit mCompilerUnit;
    private String mLocalRequisicao;

    public void continuar() {

        mCorrentePreprocessando = false;
        mCorrenteParseando = false;
        mCorrenteCompilando = false;


        if (mContinuarInterno == 0) {

            // PRE PROCESSAR




            mLocalRequisicao = mFila.processar();

            mQuantidade = 0;

            if (mLocalRequisicao != null) {

                mRequisitados.add(mLocalRequisicao);

                File arq = new File(mLocalRequisicao);

                mPreProcessando = mLocalRequisicao;

                mCorrentePreprocessando = true;
                if (arq.exists()) {
                } else {

                    GrupoDeErro nG = new GrupoDeErro("SIGMAZ");
                    nG.adicionarErro("Arquivo nao encontrado : " + mLocalRequisicao, 0, 0);
                    mErros_Processamento.add(nG);
                    mFila.terminar();

                }


            }


            mContinuarInterno = 1;

        } else if (mContinuarInterno == 1) {

            // PARSER

            File arq = new File(mLocalRequisicao);
            if (arq.exists()) {

                mCorrenteParseando = true;


                mCompilerUnit = new CompilerUnit();
                mCompilerUnit.primeitaParte(mLocalRequisicao, mRequisitados);

                mIChars += mCompilerUnit.getIChars();

                mLexer_Processamento += mQuantidade + "  -->> " + mLocalRequisicao + " [ Chars : " + mCompilerUnit.getIChars() + " Tokens : " + mCompilerUnit.getITokens() + "]\n";

                mQuantidade += 1;



                getErros_Lexer().addAll(mCompilerUnit.getErros_Lexer());
                getComentarios().addAll(mCompilerUnit.getComentarios());

                if (mErros_Processamento.size() > 0) {
                    mFila.terminar();
                }

            } else {

                GrupoDeErro nG = new GrupoDeErro("SIGMAZ");
                nG.adicionarErro("Arquivo nao encontrado : " + mLocalRequisicao, 0, 0);
                mErros_Processamento.add(nG);
                mFila.terminar();

            }

            mContinuarInterno = 2;

        } else {

            // COMPILAR

            mCorrenteCompilando = true;

            mCompilerUnit.segundaParte( AST_Raiz);

            mITokens += mCompilerUnit.getITokens();


            getErros_Compiler().addAll(mCompilerUnit.getErros_Compiler());


            for (Importacao a : mCompilerUnit.getFila()) {
                mFila.momentoAdicionar(a.getImportando());

                File arqIncluir = new File(a.getImportando());
                if (!arqIncluir.exists()) {
                    GrupoDeErro nG = new GrupoDeErro(a.getOrigem());
                    nG.adicionarErro("Importacao nao encontrado : " + a.getImportando(), a.getLinha(), a.getColuna());
                    mErros_Processamento.add(nG);
                    break;
                }

            }

            mFila.organizar();
            if (mErros_Processamento.size() > 0) {
                mFila.terminar();
            }

            if (getTerminou()) {

                mLexer_Processamento += "\n";

                mLexer_Processamento += "Parser Total -->> " + mQuantidade + " [ Chars : " + mIChars + " Tokens : " + mITokens + "]\n";


                mPreProcessamento = mFila.getProcessamento();

            }

            mContinuarInterno = 0;
        }


    }


    public boolean getTerminou() {

        if (mFila.estaExecutando()) {
            return false;
        } else {
            return true;
        }


    }

    public String getProcessando() {
        return mPreProcessando;
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
