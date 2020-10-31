package Sigmaz.S04_Compilador;

import Sigmaz.S00_Utilitarios.*;
import Sigmaz.S01_PreProcessamento.PreProcessador;
import Sigmaz.S01_PreProcessamento.Etapa;
import Sigmaz.S02_Lexer.Lexer;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;
import Sigmaz.S03_Parser.Parser;

import java.io.File;
import java.util.ArrayList;

public class Compilador {

    private int mLexer_Tokens;
    private int mLexer_Chars;
    private int mParser_Tokens;
    private int mParser_Objetos;
    private int mComentario_Tokens;
    private int mComentario_Contagem;

    private ArrayList<String> mRequisitados;

    private ArrayList<GrupoDeErro> mErros_Processamento;
    private ArrayList<GrupoDeErro> mErros_Lexer;
    private ArrayList<GrupoDeErro> mErros_Compiler;

    private ArrayList<GrupoDeComentario> mComentarios;

    private String mPreProcessamento;
    private String mLexer_Processamento;
    private String mParser_Processamento;
    private String mComentario_Processamento;

    private String mPreProcessando;

    private boolean mCorrentePreprocessando;
    private boolean mCorrenteParseando;
    private boolean mCorrenteCompilando;

    private ArrayList<Etapa> mEtapas;

    private int mEtapaID;
    private int mQuantidade;

    private PreProcessador mFila;
    private AST AST_Raiz;

    private int mContinuarInterno = 0;

    private Lexer mLexer;
    private Parser mParser;
    private Etapa mEtapa;

    private String mArquivoCorrente;

    public Compilador() {

        mPreProcessando = "";

        mPreProcessamento = "";
        mLexer_Processamento = "";
        mParser_Processamento = "";
        mComentario_Processamento = "";

        mLexer_Chars = 0;
        mLexer_Tokens = 0;

        mParser_Tokens = 0;
        mParser_Objetos = 0;

        mComentario_Contagem=0;
        mComentario_Tokens=0;

        mRequisitados = new ArrayList<>();

        mErros_Processamento = new ArrayList<>();
        mErros_Lexer = new ArrayList<>();
        mErros_Compiler = new ArrayList<>();

        mComentarios = new ArrayList<>();

        mEtapas = new ArrayList<Etapa>();
        mEtapaID = 0;
        mQuantidade = 0;

    }

    public ArrayList<Etapa> getEtapas() {
        return mEtapas;
    }

    public void init(ArrayList<String> eArquivos, AST AST_Raiz) {


        initPassos(eArquivos,AST_Raiz);

        while(!getTerminou()){
            continuar();
        }


    }

    public void fila_modificar(PreProcessador mFila, Parser mParser, Etapa mEtapa) {

        for (Importacao a : mParser.getFila()) {

            mFila.momentoAdicionar(a.getImportando());

            if (mFila.getProcessados().contains(a.getImportando())) {
                mEtapa.adicionarJaProcessado(a.getImportando());
            } else if (mFila.contem(a.getImportando())) {
                mEtapa.adicionarJaEsperando(a.getImportando());
            } else {
                mEtapa.adicionar(a.getImportando());
            }


            File arqIncluir = new File(a.getImportando());

            if (!arqIncluir.exists()) {
                GrupoDeErro nG = new GrupoDeErro(a.getOrigem());
                nG.adicionarErro("Importacao nao encontrado : " + a.getImportando(), a.getLinha(), a.getColuna());
                mErros_Processamento.add(nG);
                break;
            }

        }

    }

    public int getIChars() {
        return mLexer_Chars;
    }

    public int getITokens() {
        return mLexer_Tokens;
    }


    public String getPreProcessamento() {
        return mPreProcessamento;
    }

    public String getLexer_Processamento() {
        return mLexer_Processamento;
    }

    public String getParser_Processamento() {
        return mParser_Processamento;
    }

    public String getComentario_Processamento() {
        return mComentario_Processamento;
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




    public void initPassos(ArrayList<String> eArquivos, AST eAST_Raiz) {


        AST_Raiz=eAST_Raiz;

        mLexer_Tokens = 0;
        mLexer_Chars = 0;

        mParser_Tokens = 0;
        mParser_Objetos = 0;


        mComentario_Contagem=0;
        mComentario_Tokens=0;


        mRequisitados.clear();

        mErros_Processamento.clear();
        mErros_Lexer.clear();
        mErros_Compiler.clear();

        mComentarios.clear();

        mEtapas.clear();

        mPreProcessamento = "";

        mLexer_Processamento = " ###################### LEXER  ######################  " + "\n\n";
        mComentario_Processamento = " ###################### COMENTARIO  ######################  " + "\n\n";
        mParser_Processamento = " ###################### PARSER  ######################  " + "\n\n";


        mEtapaID = 0;
        mQuantidade = 0;


        mFila = new PreProcessador();

        for (String e : eArquivos) {
            mFila.adicionar(e);
        }

        mFila.iniciar();

        mContinuarInterno = 0;

        continuar();


    }



    public void continuar() {

        mCorrentePreprocessando = false;
        mCorrenteParseando = false;
        mCorrenteCompilando = false;


        if (mContinuarInterno == 0) {

            // PRE PROCESSAR


            mArquivoCorrente = mFila.processar();

            if (mFila.isTerminou()) {
                mFila.terminar();


            } else {


                if (mArquivoCorrente != null) {

                    mEtapa = new Etapa(mEtapaID, mArquivoCorrente);
                    mEtapaID += 1;
                    mEtapas.add(mEtapa);


                    mRequisitados.add(mArquivoCorrente);

                    File arq = new File(mArquivoCorrente);

                    mPreProcessando = mArquivoCorrente;

                    mCorrentePreprocessando = true;
                    if (arq.exists()) {
                    } else {

                        GrupoDeErro nG = new GrupoDeErro("SIGMAZ");
                        nG.adicionarErro("Arquivo nao encontrado : " + mArquivoCorrente, 0, 0);
                        mErros_Processamento.add(nG);
                        mFila.terminar();

                    }


                }


            }


            mContinuarInterno = 1;

        } else if (mContinuarInterno == 1) {

            // PARSER

            if (mFila.isTerminou()) {
                mFila.terminar();
            } else {

                File arq = new File(mArquivoCorrente);
                if (arq.exists()) {

                    mCorrenteParseando = true;

                    mLexer = realizar_Lexer(mArquivoCorrente);


                    mQuantidade += 1;


                    if (mErros_Processamento.size() > 0) {
                        mFila.terminar();
                    }

                } else {

                    GrupoDeErro nG = new GrupoDeErro("SIGMAZ");
                    nG.adicionarErro("Arquivo nao encontrado : " + mArquivoCorrente, 0, 0);
                    mErros_Processamento.add(nG);
                    mFila.terminar();

                }


            }

            mContinuarInterno = 2;

        } else {

            // COMPILAR

            if (mFila.isTerminou()) {

                mFila.terminar();

            } else {

                mCorrenteCompilando = true;

                File arq = new File(mArquivoCorrente);
                String mLocal = arq.getParent() + "/";

                ArrayList<Token> mTokens = comentarios_remover(mArquivoCorrente, mLexer.getTokens());


                 mParser = realizar_Parser(mArquivoCorrente, mLocal, mTokens, AST_Raiz, mRequisitados);


                mQuantidade += 1;


                fila_modificar(mFila, mParser, mEtapa);


                mFila.organizar();
                if (mErros_Processamento.size() > 0) {
                    mFila.terminar();
                }

                if (mFila.isTerminou()) {
                    mContinuarInterno += 1;
                }

                if (getTerminou()) {


                    mLexer_Processamento += "\n\tLexer Total -->> " + mQuantidade + " [ Chars : " + mLexer_Chars + " Tokens : " + mLexer_Tokens + " ]\n";
                    mParser_Processamento += "\n\tParser Total -->> " + mQuantidade + " [ Tokens : " + mParser_Tokens + " ASTS : " + mParser_Objetos + " ]\n";
                    mComentario_Processamento += "\n\tComentarios Total -->> " + mQuantidade + " [ Tokens : " + mComentario_Tokens + " Comentarios : " + mComentario_Contagem + " ]\n";


                    mPreProcessamento = mFila.getProcessamento();

                }

                mContinuarInterno = 0;

            }

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

    public Lexer realizar_Lexer(String eArquivo) {


        Lexer LexerC = new Lexer();
        LexerC.init(eArquivo);


        for (Erro mErro : LexerC.getErros()) {
            errarLexer(eArquivo, mErro.getMensagem(), mErro.getLinha(), mErro.getPosicao());
        }

        if (LexerC.getTokens().size() == 0) {
            errarLexer(eArquivo, "Arquivo vazio !", 0, 0);
        }


        mLexer_Chars += LexerC.getChars();
        mLexer_Tokens += LexerC.getTokens().size();

        mLexer_Processamento += "\t" + mQuantidade + "  -->> " + eArquivo + " [ Chars : " + LexerC.getChars() + " Tokens : " + LexerC.getTokens().size() + "]\n";

        return LexerC;


    }

    public void errarLexer(String mArquivo, String eMensagem, int eLinha, int ePosicao) {

        boolean enc = false;

        for (GrupoDeErro G : mErros_Lexer) {
            if (G.mesmmoArquivo(mArquivo)) {
                G.adicionarErro(eMensagem, eLinha, ePosicao);
                enc = true;
                break;
            }
        }

        if (!enc) {
            GrupoDeErro nG = new GrupoDeErro(mArquivo);
            nG.adicionarErro(eMensagem, eLinha, ePosicao);
            mErros_Lexer.add(nG);
        }
    }


    public Parser realizar_Parser(String eArquivo, String eLocal, ArrayList<Token> eTokens, AST AST_Raiz, ArrayList<String> eRequisitados) {

        Parser mParser = new Parser();
        mParser.init(eArquivo, eLocal, eTokens, AST_Raiz, eRequisitados);

        mParser_Processamento += "\t" + mQuantidade + "  -->> " + eArquivo + " [ Tokens : " + mParser.getTokens().size() + " ASTS : " + mParser.getObjetos() + " ]\n";

        mParser_Tokens += mParser.getTokens().size();
        mParser_Objetos += mParser.getObjetos();

        getErros_Compiler().addAll(mParser.getErros_Compiler());

        return mParser;
    }

    public ArrayList<Token> comentarios_remover(String eArquivo, ArrayList<Token> mTokens_Entrada) {

        int ic = 0;

        GrupoDeComentario GrupoDeComentarioC = new GrupoDeComentario(mArquivoCorrente);

        ArrayList<Token> mTokens_Saida = new ArrayList<Token>();

        for (Token TokenC : mTokens_Entrada) {
            if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                GrupoDeComentarioC.adicionarComentario(TokenC);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                GrupoDeComentarioC.adicionarComentario(TokenC);

            } else {

                mTokens_Saida.add(TokenC);

                ic += 1;
            }
        }

        mComentarios.add(GrupoDeComentarioC);

        mComentario_Processamento += "\t" + mQuantidade + "  -->> " + eArquivo + " [ Tokens : " + mTokens_Entrada.size() + " Comentarios : " + ic + " ]\n";


        mComentario_Contagem+=ic;
        mComentario_Tokens+=mTokens_Entrada.size();

        return mTokens_Saida;
    }
}
