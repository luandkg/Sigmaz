package Sigmaz.S01_Compilador;

import Sigmaz.S00_Utilitarios.*;
import Sigmaz.S01_Compilador.Avisos.Avisador;
import Sigmaz.S01_Compilador.Avisos.InfoComment;
import Sigmaz.S01_Compilador.Avisos.InfoLexer;
import Sigmaz.S01_Compilador.Avisos.InfoParser;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C01_Enfileirador.Enfileirador;
import Sigmaz.S01_Compilador.C01_Enfileirador.Etapa;
import Sigmaz.S01_Compilador.C02_Lexer.Lexer;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S02_PosProcessamento.Processadores.Caller;

import java.io.File;
import java.util.ArrayList;

public class Compilador {

    private boolean mExecutando;
    private OrganizadorDeCiclos mCiclador;


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
    private String mArquivoLexando;
    private String mArquivoParseando;

    private boolean mCorrentePreprocessando;
    private boolean mCorrenteParseando;
    private boolean mCorrenteCompilando;

    private ArrayList<Etapa> mEtapas;


    private Enfileirador mFila;
    private AST AST_Raiz;
    private AST mAST_DEBUGGER;


    private Etapa mEtapa;

    private String mArquivoCorrente;
    private boolean mIsDebug;

    private ArrayList<Importacao> eImportacoes;


    private long mLexerTempo;
    private long mParserTempo;

    public Compilador() {

        mPreProcessando = "";
        mArquivoLexando = "";
        mArquivoParseando = "";


        mRequisitados = new ArrayList<>();

        mErros_Processamento = new ArrayList<>();
        mErros_Lexer = new ArrayList<>();
        mErros_Parser = new ArrayList<>();

        mInfoLexers = new ArrayList<>();
        mInfoParser = new ArrayList<>();
        mInfoComment = new ArrayList<>();

        mComentarios = new ArrayList<>();

        mEtapas = new ArrayList<Etapa>();
        mIsDebug = false;
        mExecutando = false;

        mTodosTokens = new ArrayList<>();
        mCiclador = new OrganizadorDeCiclos();

        mLexerTempo = 0;
        mParserTempo = 0;

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

    public void initDireto(String eCodigo, AST AST_Raiz, boolean mDebugar, AST eAST_DEBUGGER) {


        initPassosDireto(eCodigo, AST_Raiz, mDebugar, eAST_DEBUGGER);

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
        return mFila.getProcessamento();
    }

    public String getLexer_Processamento() {
        Avisador eAvisador = new Avisador();
        return eAvisador.getAvisosLexer(mInfoLexers);
    }


    public String getParser_Processamento() {
        Avisador eAvisador = new Avisador();
        return eAvisador.getAvisosParser(mInfoParser);
    }

    public String getComentario_Processamento() {
        Avisador eAvisador = new Avisador();
        return eAvisador.getAvisosComentarios(mInfoComment);
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

        mLexerTempo = 0;
        mParserTempo = 0;


        mFila = new Enfileirador();

        for (String e : eArquivos) {
            mFila.adicionar(e);
        }

        mFila.iniciar();

        mExecutando = true;


        mCiclador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {


                // INICIAR

                int mIDCorrente = mFila.getProcessados().size();
                int mIDTotal = mFila.getProcessados().size() + mFila.getTamanho();

                mArquivoCorrente = mFila.processar();

                if (mFila.isTerminou()) {
                    mFila.terminar();
                    mExecutando = false;
                } else {

                    mEtapa = new Etapa(mEtapas.size() + 1, mArquivoCorrente);
                    mEtapas.add(mEtapa);

                    mRequisitados.add(mArquivoCorrente);


                    mFila.marcarProcessado();
                    mPreProcessando = mIDCorrente + " de " + mIDTotal;
                    mCorrentePreprocessando = true;

                    if (!new File(mArquivoCorrente).exists()) {
                        GrupoDeErro nG = new GrupoDeErro("SIGMAZ");
                        nG.adicionarErro("Arquivo nao encontrado : " + mArquivoCorrente, 0, 0);
                        mErros_Processamento.add(nG);
                        mFila.terminar();
                    }
                }


            }
        });

        mCiclador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {
                mArquivoLexando = mArquivoCorrente;
                mArquivoParseando = "";
            }
        });


        mCiclador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {
                // LEXER

                if (mExecutando) {

                    File arq = new File(mArquivoCorrente);
                    if (arq.exists()) {

                        mCorrenteParseando = true;


                        Lexer LexerC = new Lexer();
                        LexerC.init(mArquivoCorrente);
                        mLexerTempo = LexerC.getTempo();

                        GrupoDeErro mErrosLexer = new GrupoDeErro(mArquivoCorrente);

                        if (LexerC.getTokens().size() == 0) {
                            mErrosLexer.adicionarErro("Arquivo vazio !", 0, 0);
                        }


                        for (Erro mErro : LexerC.getErros()) {
                            mErrosLexer.adicionarErro(mErro.getMensagem(), mErro.getLinha(), mErro.getPosicao());
                        }

                        if (mErrosLexer.getErros().size() > 0) {
                            mErros_Lexer.add(mErrosLexer);
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
            }
        });

        mCiclador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {
                mArquivoLexando = "";
                mArquivoParseando = mArquivoCorrente;
            }
        });

        mCiclador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {
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

                mInfoComment.add(new InfoComment(mArquivoCorrente, mTodosTokens.size(), ic));

                mTodosTokens = mTokens_Saida;

                mComentarios.add(GrupoDeComentarioC);

            }
        });


        mCiclador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {
                mCorrenteCompilando = true;

                File arq = new File(mArquivoCorrente);
                String mLocal = arq.getParent() + "/";

                mArquivoLexando = "";
                mArquivoParseando = mArquivoCorrente;

                Parser mParser = new Parser();
                mParser.init(mArquivoCorrente, mLocal, mIsDebug, mTodosTokens, AST_Raiz, mAST_DEBUGGER, mRequisitados);
                mParserTempo = mParser.getTempo();

                mInfoParser.add(new InfoParser(mArquivoCorrente, mParser.getTokens().size(), mParser.getObjetos()));

                GrupoDeErro mErrosParser = new GrupoDeErro(mArquivoCorrente);

                if (mParser.getErros_Parser().size() > 0) {
                    for (Erro mErro : mParser.getErros_Parser()) {
                        mErrosParser.adicionarErro(mErro);
                    }
                }

                if (mErrosParser.getErros().size() > 0) {
                    mErros_Parser.add(mErrosParser);
                }

                eImportacoes = mParser.getFila();
            }
        });

        mCiclador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {
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
                    mExecutando = false;
                }

            }
        });
    }

    public void initPassosDireto(String eCodigo, AST eAST_Raiz, boolean mDebugar, AST eAST_DEBUGGER) {

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

        mLexerTempo = 0;
        mParserTempo = 0;


        mFila = new Enfileirador();

        mFila.setCodigo(eCodigo);

        mFila.iniciar();

        mExecutando = true;


        mCiclador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {

                if (mFila.estaPorCodigo()) {
                    mFila.adicionar("CODIGO");
                }


                // INICIAR

                int mIDCorrente = mFila.getProcessados().size();
                int mIDTotal = mFila.getProcessados().size() + mFila.getTamanho();


                mArquivoCorrente = mFila.processar();

                if (mFila.isTerminou()) {
                    mFila.terminar();
                    mExecutando = false;
                } else {

                    mEtapa = new Etapa(mEtapas.size() + 1, mArquivoCorrente);
                    mEtapas.add(mEtapa);

                    File arq = null;

                    if (!mFila.estaPorCodigo()) {
                        mRequisitados.add(mArquivoCorrente);
                        arq = new File(mArquivoCorrente);
                    }


                    mFila.marcarProcessado();
                    mPreProcessando = mIDCorrente + " de " + mIDTotal;
                    mCorrentePreprocessando = true;

                    if (!mFila.estaPorCodigo()) {
                        if (!arq.exists()) {
                            GrupoDeErro nG = new GrupoDeErro("SIGMAZ");
                            nG.adicionarErro("Arquivo nao encontrado : " + mArquivoCorrente, 0, 0);
                            mErros_Processamento.add(nG);
                            mFila.terminar();
                        }
                    }


                }


            }
        });

        mCiclador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {
                mArquivoLexando = mArquivoCorrente;
                mArquivoParseando = "";
            }
        });


        mCiclador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {
                // LEXER

                if (mExecutando) {

                    Lexer LexerC = new Lexer();


                    if (mFila.estaPorCodigo()) {

                        LexerC.initDireto(mFila.getCodigo());

                    } else {

                        File arq = new File(mArquivoCorrente);

                        if (arq.exists()) {

                            mCorrenteParseando = true;

                            LexerC.init(mArquivoCorrente);


                        } else {

                            GrupoDeErro nG = new GrupoDeErro("SIGMAZ");
                            nG.adicionarErro("Arquivo nao encontrado : " + mArquivoCorrente, 0, 0);
                            mErros_Processamento.add(nG);
                            mFila.terminar();

                        }


                    }

                    mLexerTempo = LexerC.getTempo();

                    GrupoDeErro mErrosLexer = new GrupoDeErro(mArquivoCorrente);

                    if (LexerC.getTokens().size() == 0) {
                        mErrosLexer.adicionarErro("Arquivo vazio !", 0, 0);
                    }


                    for (Erro mErro : LexerC.getErros()) {
                        mErrosLexer.adicionarErro(mErro.getMensagem(), mErro.getLinha(), mErro.getPosicao());
                    }

                    if (mErrosLexer.getErros().size() > 0) {
                        mErros_Lexer.add(mErrosLexer);
                    }

                    mTodosTokens = LexerC.getTokens();

                    mInfoLexers.add(new InfoLexer(mArquivoCorrente, LexerC.getChars(), LexerC.getTokens().size()));


                    if (mErros_Processamento.size() > 0) {
                        mFila.terminar();
                    }

                }
            }
        });

        mCiclador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {
                mArquivoLexando = "";
                mArquivoParseando = mArquivoCorrente;
            }
        });

        mCiclador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {
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

                mInfoComment.add(new InfoComment(mArquivoCorrente, mTodosTokens.size(), ic));

                mTodosTokens = mTokens_Saida;

                mComentarios.add(GrupoDeComentarioC);

            }
        });


        mCiclador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {
                mCorrenteCompilando = true;

                File arq = new File(mArquivoCorrente);
                String mLocal = arq.getParent() + "/";

                mArquivoLexando = "";
                mArquivoParseando = mArquivoCorrente;

                Parser mParser = new Parser();
                mParser.init(mArquivoCorrente, mLocal, mIsDebug, mTodosTokens, AST_Raiz, mAST_DEBUGGER, mRequisitados);
                mParserTempo = mParser.getTempo();

                mInfoParser.add(new InfoParser(mArquivoCorrente, mParser.getTokens().size(), mParser.getObjetos()));

                GrupoDeErro mErrosParser = new GrupoDeErro(mArquivoCorrente);

                if (mParser.getErros_Parser().size() > 0) {
                    for (Erro mErro : mParser.getErros_Parser()) {
                        mErrosParser.adicionarErro(mErro);
                    }
                }

                if (mErrosParser.getErros().size() > 0) {
                    mErros_Parser.add(mErrosParser);
                }

                eImportacoes = mParser.getFila();
            }
        });

        mCiclador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {
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
                    mExecutando = false;
                }

            }
        });
    }


    public void continuar() {

        mCorrentePreprocessando = false;
        mCorrenteParseando = false;
        mCorrenteCompilando = false;

        if (mExecutando) {
            mCiclador.processe();
        }


    }


    public boolean getTerminou() {
        return mExecutando == false;
    }

    public String getProcessando() {
        return mPreProcessando;
    }

    public String getArquivoLexando() {
        return mArquivoLexando;
    }

    public String getArquivoParseando() {
        return mArquivoParseando;
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

    public long getLexer_Tempo() {
        return mLexerTempo;
    }

    public long getParser_Tempo() {
        return mParserTempo;
    }

}
