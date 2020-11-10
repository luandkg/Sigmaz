package Sigmaz.S01_Compilador;

import Sigmaz.S00_Utilitarios.*;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C01_Enfileirador.Enfileirador;
import Sigmaz.S01_Compilador.C01_Enfileirador.Etapa;
import Sigmaz.S01_Compilador.C02_Lexer.Lexer;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;

import java.io.File;
import java.util.ArrayList;

public class Compilador {


    private ArrayList<String> mRequisitados;

    private ArrayList<GrupoDeErro> mErros_Processamento;
    private ArrayList<GrupoDeErro> mErros_Lexer;
    private ArrayList<GrupoDeErro> mErros_Parser;

    private ArrayList<InfoLexer> mInfoLexers;
    private ArrayList<InfoParser> mInfoParser;
    private ArrayList<InfoComment> mInfoComment;

    private ArrayList<GrupoDeComentario> mComentarios;
    private ArrayList<Token> mTodosTokens;


    private String mPreProcessando;

    private boolean mCorrentePreprocessando;
    private boolean mCorrenteParseando;
    private boolean mCorrenteCompilando;

    private ArrayList<Etapa> mEtapas;

    private int mEtapaID;

    private Enfileirador mFila;
    private AST AST_Raiz;
    private AST mAST_DEBUGGER;

    private int mContinuarInterno;

    private Etapa mEtapa;

    private String mArquivoCorrente;
    private boolean mIsDebug;

    private ArrayList<Importacao> eImportacoes;

    private boolean mExecutando;

    public Compilador() {

        mPreProcessando = "";


        mRequisitados = new ArrayList<>();

        mErros_Processamento = new ArrayList<>();
        mErros_Lexer = new ArrayList<>();
        mErros_Parser = new ArrayList<>();

        mInfoLexers = new ArrayList<>();
        mInfoParser = new ArrayList<>();
        mInfoComment = new ArrayList<>();

        mComentarios = new ArrayList<>();

        mEtapas = new ArrayList<Etapa>();
        mEtapaID = 0;
        mIsDebug = false;
        mExecutando = false;
        mContinuarInterno=0;

        mTodosTokens = new ArrayList<>();

    }

    public ArrayList<Etapa> getEtapas() {
        return mEtapas;
    }

    public void init(ArrayList<String> eArquivos, AST AST_Raiz, boolean mDebugar, AST eAST_DEBUGGER) {


        initPassos(eArquivos, AST_Raiz, mDebugar, eAST_DEBUGGER);

        while (!getTerminou()) {
            continuar();
        }


    }


    public int getIChars() {
        int somando = 0;
        for (InfoLexer info : mInfoLexers) {
            somando += info.getChars();
        }
        return somando;
    }

    public int getITokens() {
        int somando = 0;
        for (InfoLexer info : mInfoLexers) {
            somando += info.getTokens();
        }
        return somando;
    }

    public int getPTokens() {
        int somando = 0;
        for (InfoParser info : mInfoParser) {
            somando += info.getTokens();
        }
        return somando;
    }

    public int getASTS() {
        int somando = 0;
        for (InfoParser info : mInfoParser) {
            somando += info.getASTS();
        }
        return somando;
    }


    public int getCTokens() {
        int somando = 0;
        for (InfoComment info : mInfoComment) {
            somando += info.getTokens();
        }
        return somando;
    }

    public int getIComentarios() {
        int somando = 0;
        for (InfoComment info : mInfoComment) {
            somando += info.getComentarios();
        }
        return somando;
    }

    public String getPreProcessamento() {
        return  mFila.getProcessamento();
    }

    public String getLexer_Processamento() {

        Documento mLexer_Processamento = new Documento();

        int casas = 1;
        if (mInfoLexers.size() >= 10 && mInfoLexers.size() < 99) {
            casas = 2;
        } else if (mInfoLexers.size() >= 100 && mInfoLexers.size() < 999) {
            casas = 3;
        } else if (mInfoLexers.size() >= 1000 && mInfoLexers.size() < 9999) {
            casas = 4;
        }

        mLexer_Processamento.adicionarLinha(" ################################# LEXER  #################################  ");
        mLexer_Processamento.pularLinha();


        int i = 1;
        for (InfoLexer info : mInfoLexers) {

            String P1 = organizarNumero(i, casas) + " :: " + info.getArquivo() + " ";
            mLexer_Processamento.adicionarLinha(1, organizarString(P1, 45) + " [ Chars = " + organizarNumero(info.getChars(), 6) + "    Tokens = " + organizarNumero(info.getTokens(), 6) + " ] ");

            i += 1;
        }


        String P2 = "Lexer Total -->> " + mInfoLexers.size() + " ";
        mLexer_Processamento.pularLinha();
        mLexer_Processamento.adicionarLinha(1, organizarString(P2, 45) + " [ Chars : " + organizarNumero(getIChars(), 6) + "    Tokens : " + organizarNumero(getITokens(), 6) + " ]");

        return mLexer_Processamento.getConteudo();

    }

    public String organizarString(String e, int t) {
        while (e.length() < t) {
            e += " ";
        }
        return e;
    }

    public String organizarNumero(int e, int casas) {
        String v = String.valueOf(e);
        while (v.length() < casas) {
            v = "0" + v;
        }
        return v;
    }


    public String getParser_Processamento() {

        Documento mLexer_Processamento = new Documento();

        int casas = 1;
        if (mInfoParser.size() >= 10 && mInfoParser.size() < 99) {
            casas = 2;
        } else if (mInfoParser.size() >= 100 && mInfoParser.size() < 999) {
            casas = 3;
        } else if (mInfoParser.size() >= 1000 && mInfoParser.size() < 9999) {
            casas = 4;
        }

        mLexer_Processamento.adicionarLinha(" ################################# PARSER  #################################  ");
        mLexer_Processamento.pularLinha();


        int i = 1;
        for (InfoParser info : mInfoParser) {

            String P1 = organizarNumero(i, casas) + " :: " + info.getArquivo() + " ";
            mLexer_Processamento.adicionarLinha(1, organizarString(P1, 45) + " [ Tokens = " + organizarNumero(info.getTokens(), 6) + "    ASTS = " + organizarNumero(info.getASTS(), 6) + " ] ");

            i += 1;
        }


        String P2 = "Parser Total -->> " + mInfoParser.size() + " ";
        mLexer_Processamento.pularLinha();
        mLexer_Processamento.adicionarLinha(1, organizarString(P2, 45) + " [ Tokens : " + organizarNumero(getPTokens(), 6) + "    ASTS : " + organizarNumero(getASTS(), 6) + " ]");

        return mLexer_Processamento.getConteudo();

    }

    public String getComentario_Processamento() {

        Documento mLexer_Processamento = new Documento();

        int casas = 1;
        if (mInfoComment.size() >= 10 && mInfoComment.size() < 99) {
            casas = 2;
        } else if (mInfoComment.size() >= 100 && mInfoComment.size() < 999) {
            casas = 3;
        } else if (mInfoComment.size() >= 1000 && mInfoComment.size() < 9999) {
            casas = 4;
        }

        mLexer_Processamento.adicionarLinha(" ################################# COMENTARIOS  #################################  ");
        mLexer_Processamento.pularLinha();


        int i = 1;
        for (InfoComment info : mInfoComment) {

            String P1 = organizarNumero(i, casas) + " :: " + info.getArquivo() + " ";
            mLexer_Processamento.adicionarLinha(1, organizarString(P1, 45) + " [ Tokens = " + organizarNumero(info.getTokens(), 6) + "    Comentarios = " + organizarNumero(info.getComentarios(), 6) + " ] ");

            i += 1;
        }


        String P2 = "Comentarios Total -->> " + mInfoComment.size() + " ";
        mLexer_Processamento.pularLinha();
        mLexer_Processamento.adicionarLinha(1, organizarString(P2, 45) + " [ Tokens : " + organizarNumero(getCTokens(), 6) + "    Comentarios : " + organizarNumero(getIComentarios(), 6) + " ]");

        return mLexer_Processamento.getConteudo();


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

    public ArrayList<GrupoDeErro> getErros_Parser() {
        return mErros_Parser;
    }

    public ArrayList<GrupoDeComentario> getComentarios() {
        return mComentarios;
    }


    public void initPassos(ArrayList<String> eArquivos, AST eAST_Raiz, boolean mDebugar, AST eAST_DEBUGGER) {

        mIsDebug = mDebugar;

        AST_Raiz = eAST_Raiz;
        mAST_DEBUGGER = eAST_DEBUGGER;

        mRequisitados.clear();

        mErros_Processamento.clear();
        mErros_Lexer.clear();
        mErros_Parser.clear();

        mInfoLexers.clear();

        mComentarios.clear();

        mEtapas.clear();

        mEtapaID = 0;


        mFila = new Enfileirador();

        for (String e : eArquivos) {
            mFila.adicionar(e);
        }

        mFila.iniciar();

        mContinuarInterno = 0;

        mExecutando = true;

    }

    public void continuar_01() {


        // INICIAR

        mArquivoCorrente = mFila.processar();

        if (mFila.isTerminou()) {
            mFila.terminar();
            mExecutando = false;
        } else {

            mEtapa = new Etapa(mEtapaID, mArquivoCorrente);
            mEtapaID += 1;
            mEtapas.add(mEtapa);

            mRequisitados.add(mArquivoCorrente);

            File arq = new File(mArquivoCorrente);

            mPreProcessando = mArquivoCorrente;

            mCorrentePreprocessando = true;

            if (!arq.exists()) {
                GrupoDeErro nG = new GrupoDeErro("SIGMAZ");
                nG.adicionarErro("Arquivo nao encontrado : " + mArquivoCorrente, 0, 0);
                mErros_Processamento.add(nG);
                mFila.terminar();

            }


        }


        mContinuarInterno = 1;
    }

    public void continuar_02() {

        // LEXER

        if (mExecutando) {

            File arq = new File(mArquivoCorrente);
            if (arq.exists()) {

                mCorrenteParseando = true;

                Lexer LexerC = new Lexer();
                LexerC.init(mArquivoCorrente);


                for (Erro mErro : LexerC.getErros()) {
                    errarLexer(mArquivoCorrente, mErro.getMensagem(), mErro.getLinha(), mErro.getPosicao());
                }

                if (LexerC.getTokens().size() == 0) {
                    errarLexer(mArquivoCorrente, "Arquivo vazio !", 0, 0);
                }

                mTodosTokens = LexerC.getTokens();

                mInfoLexers.add(new InfoLexer(mArquivoCorrente, LexerC.getChars(), LexerC.getTokens().size()));


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

    }

    public void continuar_03(){

        int ic = 0;

        GrupoDeComentario GrupoDeComentarioC = new GrupoDeComentario(mArquivoCorrente);

        ArrayList<Token> mTokens_Saida = new ArrayList<Token>();

        for (Token TokenC : mTodosTokens) {

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {
                GrupoDeComentarioC.adicionarComentario(TokenC);
                ic += 1;
            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {
                GrupoDeComentarioC.adicionarComentario(TokenC);
                ic += 1;
            } else {
                mTokens_Saida.add(TokenC);

            }
        }

        mInfoComment.add(new InfoComment(mArquivoCorrente, mTodosTokens.size(),  ic));

        mTodosTokens = mTokens_Saida;

        mComentarios.add(GrupoDeComentarioC);

        mContinuarInterno = 3;


    }

    public void continuar_04() {

        // PARSER

        if (mExecutando) {

            mCorrenteCompilando = true;

            File arq = new File(mArquivoCorrente);
            String mLocal = arq.getParent() + "/";


            Parser mParser = new Parser();
            mParser.init(mArquivoCorrente, mLocal, mIsDebug, mTodosTokens, AST_Raiz, mAST_DEBUGGER, mRequisitados);

            mInfoParser.add(new InfoParser(mArquivoCorrente, mParser.getTokens().size(), mParser.getObjetos()));

            mErros_Parser.addAll(mParser.getErros_Parser());

            mContinuarInterno = 4;

            eImportacoes = mParser.getFila();


        }
    }

    public void continuar_05() {

        for (Importacao a : eImportacoes) {

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


        mFila.organizar();

        if (mErros_Processamento.size() > 0) {
            mFila.terminar();
        }


        if (mFila.isTerminou()) {

            mContinuarInterno += 1;
            mExecutando = false;


            mContinuarInterno = 6;

        } else {
            mContinuarInterno = 0;
        }


    }

    public void continuar() {

        mCorrentePreprocessando = false;
        mCorrenteParseando = false;
        mCorrenteCompilando = false;

        if (mContinuarInterno == 0) {
            continuar_01();
        } else if (mContinuarInterno == 1) {
            continuar_02();
        } else if (mContinuarInterno == 2) {
            continuar_03();
        } else if (mContinuarInterno == 3) {
            continuar_04();
        } else {
            continuar_05();
        }


    }


    public boolean getTerminou() {

        return mExecutando == false;


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




}
