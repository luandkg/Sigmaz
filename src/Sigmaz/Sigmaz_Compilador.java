package Sigmaz;

import Sigmaz.S00_Utilitarios.*;
import Sigmaz.S01_PreProcessamento.Etapa;
import Sigmaz.S01_PreProcessamento.Planejador;
import Sigmaz.S04_Compilador.Compilador;
import Sigmaz.S06_Montador.Montador;
import Sigmaz.S05_PosProcessamento.Processadores.Cabecalho;
import Sigmaz.S05_PosProcessamento.PosProcessador;

import java.util.ArrayList;

public class Sigmaz_Compilador {

    private Cabecalho mCabecalho;
    private Fases mFase;
    private OrganizadorDeErros mOrganizadorDeErros;

    private String mETAPA_PRE_PROCESSAMENTO;
    private String mETAPA_LEXER;
    private String mETAPA_PARSER;
    private String mETAPA_POS_PROCESSAMENTO;
    private String mETAPA_MONTAGEM;

    private String mSTATUS_NAO;
    private String mSTATUS_SUCESSO;
    private String mSTATUS_FALHOU;

    private ArrayList<String> mDebugMensagens;

    private boolean mDebug;
    private boolean mDebug_PreProcessamento;
    private boolean mDebug_Lexer;
    private boolean mDebug_Parser;
    private boolean mDebug_Montagem;
    private boolean mDebug_Comentario;

    private boolean mDebug_Requisidor;
    private boolean mDebug_Alocador;
    private boolean mDebug_Modelador;
    private boolean mDebug_Estagiador;
    private boolean mDebug_Tipador;
    private boolean mDebug_Valorador;
    private boolean mDebug_Cast;
    private boolean mDebug_UnionType;
    private boolean mDebug_Heranca;
    private boolean mDebug_Opcionador;

    private boolean mDebug_Referenciador;
    private boolean mDebug_Argumentador;

    private ArrayList<GrupoDeErro> mErros_PreProcessamento;
    private ArrayList<GrupoDeErro> mErros_Lexer;
    private ArrayList<GrupoDeErro> mErros_Compiler;
    private ArrayList<String> mErros_PosProcessamento;
    private ArrayList<String> mErros_Execucao;

    private boolean mMostrar_Fases;
    private boolean mMostrar_ArvoreRunTime;
    private boolean mMostrar_ArvoreFalhou;
    private boolean mMostrar_Erros;
    private boolean mMostrar_Execucao;
    private boolean mMostrar_Mensagens;

    private ArrayList<Etapa> mEtapas;

    private boolean mTemErros;

    private ArrayList<AST> mASTS;

    private ArrayList<AST> mCabecalhos;


    private ArrayList<String> mRequisitados;

    private ArrayList<GrupoDeComentario> mComentarios;

    private int mIChars;
    private int mITokens;

    private String mPreProcessamento;
    private String mLexer_Processamento;
    private String mParser_Processamento;
    private String mComentario_Processamento;

    public enum Fases {
        PRE_PROCESSAMENTO, LEXER, PARSER, POS_PROCESSAMENTO, MONTAGEM, PRONTO;
    }

    public Sigmaz_Compilador() {


        mCabecalho = new Cabecalho();

        mSTATUS_NAO = "NAO REALIZADO";
        mSTATUS_SUCESSO = "SUCESSO";
        mSTATUS_FALHOU = "FALHOU";


        mETAPA_PRE_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_LEXER = mSTATUS_NAO;
        mETAPA_PARSER = mSTATUS_NAO;
        mETAPA_POS_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_MONTAGEM = mSTATUS_NAO;

        mDebug = false;
        mDebugMensagens = new ArrayList<String>();
        mDebug_PreProcessamento = true;
        mDebug_Lexer = true;
        mDebug_Parser = true;
        mDebug_Comentario = true;
        mDebug_Montagem = true;

        mDebug_Requisidor = true;
        mDebug_Alocador = true;
        mDebug_Modelador = true;
        mDebug_Estagiador = true;
        mDebug_Tipador = true;
        mDebug_Valorador = true;
        mDebug_Cast = true;
        mDebug_UnionType = true;
        mDebug_Heranca = true;
        mDebug_Referenciador = true;
        mDebug_Argumentador = true;
        mDebug_Opcionador = true;

        mOrganizadorDeErros = new OrganizadorDeErros();

        mMostrar_Fases = true;
        mMostrar_Erros = true;
        mMostrar_ArvoreRunTime = true;
        mMostrar_ArvoreFalhou = true;
        mMostrar_Execucao = true;
        mMostrar_Mensagens = true;

        mEtapas = new ArrayList<Etapa>();

        mTemErros = false;

        mErros_PreProcessamento = new ArrayList<>();
        mErros_Lexer = new ArrayList<>();
        mErros_Compiler = new ArrayList<>();
        mErros_PosProcessamento = new ArrayList<>();
        mErros_Execucao = new ArrayList<>();
        mASTS = new ArrayList<AST>();


        mCabecalhos = new ArrayList<>();
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
        mParser_Processamento = "";
        mComentario_Processamento="";

        mEtapas = new ArrayList<Etapa>();

    }


    public void setMostrar_Fases(boolean e) {
        mMostrar_Fases = e;
    }

    public void setMostrar_Erros(boolean e) {
        mMostrar_Erros = e;
    }

    public void setMostrarArvoreRunTime(boolean e) {
        mMostrar_ArvoreRunTime = e;
    }

    public void setMostrar_ArvoreFalhou(boolean e) {
        mMostrar_ArvoreFalhou = e;
    }

    public void mostrarDebug(boolean eDebug) {
        mDebug = eDebug;
    }

    public void setMostrar_Execucao(boolean e) {
        mMostrar_Execucao = e;
    }

    public void setMostrar_Mensagens(boolean e) {
        mMostrar_Mensagens = e;
    }


    public void setDebug_PreProcessamento(boolean e) {
        mDebug_PreProcessamento = e;
    }

    public void setDebug_Lexer(boolean e) {
        mDebug_Lexer = e;
    }

    public void setDebug_Parser(boolean e) {
        mDebug_Parser = e;
    }

    public void setDebug_Comentario(boolean e) {
        mDebug_Comentario = e;
    }


    public void setDebug_Montagem(boolean e) {
        mDebug_Montagem = e;
    }


    public void setDebug_PosProcessador_Requisidor(boolean e) {
        mDebug_Requisidor = e;
    }

    public void setDebug_PosProcessador_Alocador(boolean e) {
        mDebug_Alocador = e;
    }


    public void setDebug_PosProcessador_Modelador(boolean e) {
        mDebug_Modelador = e;
    }


    public void setDebug_PosProcessador_Estagiador(boolean e) {
        mDebug_Estagiador = e;
    }

    public void setDebug_PosProcessador_Tipador(boolean e) {
        mDebug_Tipador = e;
    }

    public void setDebug_PosProcessador_Valorador(boolean e) {
        mDebug_Valorador = e;
    }

    public void setDebug_PosProcessador_Cast(boolean e) {
        mDebug_Cast = e;
    }

    public void setDebug_PosProcessador_UnionType(boolean e) {
        mDebug_UnionType = e;
    }

    public void setDebug_PosProcessador_Heranca(boolean e) {
        mDebug_Heranca = e;
    }

    public void setDebug_PosProcessador_Referenciador(boolean e) {
        mDebug_Referenciador = e;
    }

    public void setDebug_PosProcessador_Argumentador(boolean e) {
        mDebug_Argumentador = e;
    }

    public void setDebug_PosProcessador_Opcionador(boolean e) {
        mDebug_Opcionador = e;
    }


    public void setCabecalho(Cabecalho eCabecalho) {
        mCabecalho = eCabecalho;
    }

    public ArrayList<Etapa> getEtapas() {
        return mEtapas;
    }

    public boolean temErros() {
        return mTemErros;
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

    public ArrayList<String> getErros_PosProcessamento() {
        return mErros_PosProcessamento;
    }

    public ArrayList<String> getErros_Execucao() {
        return mErros_Execucao;
    }

    public Fases getFase() {
        return mFase;
    }


    public String getModo(int mOpcao) {
        String eModo = "DESCONHECIDO";

        if (mOpcao == 1) {
            eModo = "EXECUTAVEL";
        }

        if (mOpcao == 2) {
            eModo = "BIBLIOTECA";
        }

        return eModo;
    }


    public void limpar() {

        mFase = Fases.PRE_PROCESSAMENTO;

        mETAPA_PRE_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_LEXER = mSTATUS_NAO;
        mETAPA_PARSER = mSTATUS_NAO;
        mETAPA_POS_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_MONTAGEM = mSTATUS_NAO;

        mDebugMensagens.clear();
        mDebugMensagens.clear();

        mTemErros = false;

        mErros_Lexer.clear();
        mErros_Compiler.clear();
        mErros_PosProcessamento.clear();
        mErros_Execucao.clear();
        mErros_PreProcessamento.clear();

        mASTS.clear();


        mRequisitados.clear();

        mErros_PreProcessamento.clear();
        mErros_Lexer.clear();
        mErros_Compiler.clear();

        mComentarios.clear();

        mIChars = 0;
        mITokens = 0;
        mPreProcessamento = "";
        mLexer_Processamento = "";
        mOrganizadorDeErros.limpar();

    }

    public ArrayList<AST> getASTS() {
        return mASTS;
    }

    public void init(String eArquivo, String mSaida, String eLocalLibs, int mOpcao) {
        ArrayList<String> aa = new ArrayList<String>();
        aa.add(eArquivo);
        init(aa, mSaida, eLocalLibs, mOpcao);
    }

    public void init(ArrayList<String> eArquivos, String mSaida, String eLocalLibs, int mOpcao) {

        limpar();


        mFase = Fases.PRE_PROCESSAMENTO;


        if (mMostrar_Fases) {

            System.out.println("");
            System.out.println("################ SIGMAZ FASES ################");
            System.out.println("");

            for (String a : eArquivos) {
                System.out.println("\t - Source : " + a);
            }
            System.out.println("\t - Local  : " + eLocalLibs);
            System.out.println("\t - Build  : " + mSaida);
            System.out.println("\t - Modo   : " + getModo(mOpcao));

            System.out.println("");

        }

        Chronos_Intervalo mIntervalo = new Chronos_Intervalo();

        mIntervalo.marqueInicio();


        executar_Compilador(eArquivos, mOpcao);


        fase_Lexer();

        fase_Comentario();

        fase_Parser();


        executar_PosProcessamento(eLocalLibs);


        executar_Montagem(mSaida);

        mIntervalo.marqueFim();

        if (mMostrar_Fases) {
            System.out.println("");
            System.out.println("\t - Tempo   : " + mIntervalo.getIntervalo() + " segundos");
        }

        listar_mensagens();

        if (mFase == Fases.PRONTO) {

            executar(mSaida);

        } else {

            falhou();

        }

    }

    public void executar(String eSaida) {

        Sigmaz_Executor mSigmaz_Executor = new Sigmaz_Executor();

        mSigmaz_Executor.setMostrar_Execucao(mMostrar_Execucao);
        mSigmaz_Executor.setMostrar_ArvoreRunTime(mMostrar_ArvoreRunTime);

        mSigmaz_Executor.executar(eSaida);

        if (mSigmaz_Executor.temErros()) {
            for (String mErro : mSigmaz_Executor.getErros()) {
                mErros_Execucao.add(mErro);
                mTemErros = true;
            }
        }

    }


    public void initSemExecucao(String eArquivo, String mSaida, String eLocalLibs, int mOpcao) {
        ArrayList<String> aa = new ArrayList<String>();
        aa.add(eArquivo);
        initSemExecucao(aa, mSaida, eLocalLibs, mOpcao);
    }

    public void initSemExecucao(ArrayList<String> eArquivos, String mSaida, String eLocalLibs, int mOpcao) {

        limpar();


        mFase = Fases.PRE_PROCESSAMENTO;


        if (mMostrar_Fases) {

            System.out.println("");
            System.out.println("################ SIGMAZ FASES ################");
            System.out.println("");

            for (String a : eArquivos) {
                System.out.println("\t - Source : " + a);
            }
            System.out.println("\t - Local  : " + eLocalLibs);
            System.out.println("\t - Build  : " + mSaida);
            System.out.println("\t - Modo   : " + getModo(mOpcao));

            System.out.println("");

        }

        Chronos_Intervalo mIntervalo = new Chronos_Intervalo();

        mIntervalo.marqueInicio();

        executar_Compilador(eArquivos, mOpcao);

        fase_Lexer();

        fase_Parser();

        executar_PosProcessamento(eLocalLibs);

        executar_Montagem(mSaida);

        mIntervalo.marqueFim();

        if (mMostrar_Fases) {
            System.out.println("");
            System.out.println("\t - Tempo   : " + mIntervalo.getIntervalo() + " segundos");
        }

        listar_mensagens();

        if (mFase == Fases.PRONTO) {

        } else {

            falhou();

        }

    }

    public void initSemObjeto(String eArquivo, String eLocalLibs, int mOpcao) {
        ArrayList<String> aa = new ArrayList<String>();
        aa.add(eArquivo);
        initSemObjeto(aa, eLocalLibs, mOpcao);
    }

    public void initSemObjeto(ArrayList<String> eArquivos, String mLocalLibs, int mOpcao) {

        limpar();


        mFase = Fases.PRE_PROCESSAMENTO;


        if (mMostrar_Fases) {

            System.out.println("");
            System.out.println("################ SIGMAZ FASES ################");
            System.out.println("");

            for (String a : eArquivos) {
                System.out.println("\t - Source : " + a);
            }
            System.out.println("\t - Local  : " + mLocalLibs);
            System.out.println("\t - Modo   : " + getModo(mOpcao));

            System.out.println("");

        }

        Chronos_Intervalo mIntervalo = new Chronos_Intervalo();

        mIntervalo.marqueInicio();

        executar_Compilador(eArquivos, mOpcao);

        fase_Lexer();

        fase_Parser();

        executar_PosProcessamento(mLocalLibs);


        mIntervalo.marqueFim();

        listar_mensagens();

        if (mMostrar_Fases) {
            System.out.println("");
            System.out.println("\t - Tempo   : " + mIntervalo.getIntervalo() + " segundos");
        }

        if (mFase == Fases.MONTAGEM) {


        } else {

            falhou();

        }

    }


    public void listar_mensagens() {

        if (mMostrar_Mensagens) {

            if (mDebug) {

                System.out.println("");

                for (String eMensagem : mDebugMensagens) {
                    System.out.println(eMensagem);
                }

            }

        }


    }

    public void falhou() {

        mTemErros = true;


        if (mMostrar_ArvoreFalhou) {

            System.out.println("----------------------------------------------");
            System.out.println("");

            ASTImpressor mASTImpressor = new ASTImpressor();
            mASTImpressor.init(mASTS);

            System.out.println(mASTImpressor.getArvoreDeInstrucoes());
        }


        if (mMostrar_Erros) {

            System.out.println("");
            System.out.println("----------------------------------------------");


            for (String eMensagem : mOrganizadorDeErros.getErros()) {
                System.out.println(eMensagem);
            }

            System.out.println("");
            System.out.println("----------------------------------------------");


        }

    }

    public void montarPlano(String eArquivo, String mSaida, String eLocalLibs, int mOpcao) {
        ArrayList<String> aa = new ArrayList<String>();
        aa.add(eArquivo);
        montarPlano(aa, mSaida, eLocalLibs, mOpcao);
    }


    public void montarPlano(ArrayList<String> eArquivos, String eLocalLibs, String mArquivoPlano, int mOpcao) {


        String eModo = "DESCONHECIDO";

        if (mOpcao == 1) {
            eModo = "EXECUTAVEL";
        }

        if (mOpcao == 2) {
            eModo = "BIBLIOTECA";
        }

        mFase = Fases.PRE_PROCESSAMENTO;

        mETAPA_PRE_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_LEXER = mSTATUS_NAO;
        mETAPA_PARSER = mSTATUS_NAO;
        mETAPA_POS_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_MONTAGEM = mSTATUS_NAO;

        mDebugMensagens.clear();
        mDebugMensagens.clear();

        mTemErros = false;

        mErros_Lexer.clear();
        mErros_Compiler.clear();
        mErros_PosProcessamento.clear();
        mErros_Execucao.clear();
        mErros_PreProcessamento.clear();

        System.out.println("");
        System.out.println("################ SIGMAZ FASES ################");
        System.out.println("");

        for (String a : eArquivos) {
            System.out.println("\t - Source : " + a);
        }

        System.out.println("\t - Local  : " + eLocalLibs);
        System.out.println("\t - Build  : " + eLocalLibs);
        System.out.println("\t - Modo   : " + eModo);
        System.out.println("\t - Plano   : " + mArquivoPlano);

        System.out.println("");


        executar_Compilador(eArquivos, mOpcao);

        fase_Lexer();

        fase_Parser();


        executar_PosProcessamento(eLocalLibs);

        //executar_Montagem(CompilerC, eLocalLibs);


        if (mDebug) {

            System.out.println("");
            System.out.println("################ DEBUG ################");
            System.out.println("");

            for (String eMensagem : mDebugMensagens) {

                System.out.println(eMensagem);
            }

        }

        if (mFase == Fases.MONTAGEM) {

            System.out.println("");
            System.out.println("################ PLANO ################");
            System.out.println("");

            System.out.println("ETAPAS : " + mEtapas.size());


            for (Etapa mEtapa : mEtapas) {

                System.out.println("");
                System.out.println("ETAPA: " + mEtapa.getEtapaID() + " :: " + mEtapa.getCorrente());

                System.out.println("\t - Adicionar : " + mEtapa.getAdicionar().size());
                for (String a : mEtapa.getAdicionar()) {
                    System.out.println("\t\t - " + a);
                }

                System.out.println("\t - Ja Esperando : " + mEtapa.getJaEsperando().size());
                for (String a : mEtapa.getJaEsperando()) {
                    System.out.println("\t\t - " + a);
                }

                System.out.println("\t - Ja Processados : " + mEtapa.getJaProcessado().size());
                for (String a : mEtapa.getJaProcessado()) {
                    System.out.println("\t\t - " + a);
                }

            }

            Planejador mPlanejador = new Planejador();
            mPlanejador.init(mArquivoPlano, mEtapas);


        } else {


            System.out.println("----------------------------------------------");
            System.out.println("");


            if (mMostrar_ArvoreFalhou) {
                ASTImpressor mASTImpressor = new ASTImpressor();
                mASTImpressor.init(mASTS);

                System.out.println(mASTImpressor.getArvoreDeInstrucoes());
            }


            System.out.println("");
            System.out.println("----------------------------------------------");


            for (String eMensagem : mOrganizadorDeErros.getErros()) {
                System.out.println(eMensagem);
            }

            System.out.println("");
            System.out.println("----------------------------------------------");

        }

    }


    private void executar_Compilador(ArrayList<String> eArquivos, int mOpcao) {

        if (mFase == Fases.PRE_PROCESSAMENTO) {


            limpar();


            AST AST_Raiz = new AST("SIGMAZ");


            mASTS.add(AST_Raiz);

            UUID mUUIDC = new UUID();

            AST_Raiz.setValor(mUUIDC.getUUID());

            if (mOpcao == 1) {

                AST_Raiz.setNome("EXECUTABLE");

            } else if (mOpcao == 2) {

                AST_Raiz.setNome("LIBRARY");

            }


            Compilador mCompilador = new Compilador();
            mCompilador.init(eArquivos, AST_Raiz);

            mPreProcessamento = mCompilador.getPreProcessamento();
            mLexer_Processamento = mCompilador.getLexer_Processamento();
            mParser_Processamento = mCompilador.getParser_Processamento();
            mComentario_Processamento = mCompilador.getComentario_Processamento();

            mIChars = mCompilador.getIChars();
            mITokens = mCompilador.getITokens();

            mEtapas = mCompilador.getEtapas();

            for (String mRequisicao : mCompilador.getRequisitados()) {
                mRequisitados.add(mRequisicao);
            }

            for (GrupoDeComentario mGrupoDeComentario : mCompilador.getComentarios()) {
                mComentarios.add(mGrupoDeComentario);
            }

            for (GrupoDeErro mGrupoDeErro : mCompilador.getErros_Processamento()) {
                mErros_PreProcessamento.add(mGrupoDeErro);
            }

            for (GrupoDeErro mGrupoDeErro : mCompilador.getErros_Lexer()) {
                mErros_Lexer.add(mGrupoDeErro);
            }


            for (GrupoDeErro mGrupoDeErro : mCompilador.getErros_Compiler()) {
                mErros_Compiler.add(mGrupoDeErro);
            }

            if (mDebug_PreProcessamento) {
                mDebugMensagens.add(getPreProcessamento());
            }


            mEtapas = getEtapas();

            if (getErros_PreProcessamento().size() == 0) {

                mFase = Fases.LEXER;
                mETAPA_PRE_PROCESSAMENTO = mSTATUS_SUCESSO;

            } else {

                mETAPA_PRE_PROCESSAMENTO = mSTATUS_FALHOU;

                mOrganizadorDeErros.getPreProcessador(getErros_PreProcessamento());
            }


        }

        if (mMostrar_Fases) {
            System.out.println("\t - 1 : Pre Processamento       : " + mETAPA_PRE_PROCESSAMENTO);

        }


    }

    private void fase_Comentario() {


        if (mDebug_Comentario) {

            mDebugMensagens.add(getComentario_Processamento());

        }

    }

    private void fase_Lexer() {


        if (mFase == Fases.LEXER) {

            if (mDebug_Lexer) {

                mDebugMensagens.add(getLexer_Processamento());

            }


            if (getErros_Lexer().size() == 0) {
                mFase = Fases.PARSER;
                mETAPA_LEXER = mSTATUS_SUCESSO;
            } else {
                mETAPA_LEXER = mSTATUS_FALHOU;

                mOrganizadorDeErros.getLexer(getErros_Lexer());


            }

        }
        if (mMostrar_Fases) {
            System.out.println("\t - 2 : Lexer                   : " + mETAPA_LEXER);

        }


    }

    private void fase_Parser() {

        if (mFase == Fases.PARSER) {

            if (mDebug_Parser) {

                mDebugMensagens.add(getParser_Processamento());

            }

            if (getErros_Compiler().size() == 0) {
                mFase = Fases.POS_PROCESSAMENTO;
                mETAPA_PARSER = mSTATUS_SUCESSO;
            } else {
                mETAPA_PARSER = mSTATUS_FALHOU;

                mOrganizadorDeErros.getParser(getErros_Compiler());

            }

        }
        if (mMostrar_Fases) {

            System.out.println("\t - 3 : Parser                  : " + mETAPA_PARSER);

        }


    }


    private void executar_PosProcessamento(String mLocalLibs) {

        if (mFase == Fases.POS_PROCESSAMENTO) {

            PosProcessador PosProcessadorC = new PosProcessador();

            PosProcessadorC.setDebug_Requisidor(mDebug_Requisidor);
            PosProcessadorC.setDebug_Alocador(mDebug_Alocador);
            PosProcessadorC.setDebug_Modelador(mDebug_Modelador);
            PosProcessadorC.setDebug_Estagiador(mDebug_Estagiador);
            PosProcessadorC.setDebug_Tipador(mDebug_Tipador);
            PosProcessadorC.setDebug_Valorador(mDebug_Valorador);

            PosProcessadorC.setDebug_Cast(mDebug_Cast);
            PosProcessadorC.setDebug_UnionType(mDebug_UnionType);
            PosProcessadorC.setDebug_Heranca(mDebug_Heranca);
            PosProcessadorC.setDebug_Referenciador(mDebug_Referenciador);
            PosProcessadorC.setDebug_Argumentador(mDebug_Argumentador);
            PosProcessadorC.setDebug_Opcionador(mDebug_Opcionador);


            PosProcessadorC.init(getASTS(), mLocalLibs);

            for (String eMensagem : PosProcessadorC.getMensagens()) {
                mDebugMensagens.add(eMensagem);
            }


            if (PosProcessadorC.getErros().size() == 0) {


                mFase = Fases.MONTAGEM;
                mETAPA_POS_PROCESSAMENTO = mSTATUS_SUCESSO;


            } else {

                mETAPA_POS_PROCESSAMENTO = mSTATUS_FALHOU;

                mOrganizadorDeErros.getPosProcessador(getErros_PosProcessamento());


            }


        }
        if (mMostrar_Fases) {

            System.out.println("\t - 5 : Pos Processamento       : " + mETAPA_POS_PROCESSAMENTO);

        }


    }

    public void executar_Montagem(String mSaida) {


        if (mFase == Fases.MONTAGEM) {

            ArrayList<AST> mASTCabecalhos = new ArrayList<AST>();

            SigmazCab mSigmazCab = new SigmazCab();

            mSigmazCab.gravarCabecalho(mCabecalho, mASTCabecalhos);


            Montador MontadorC = new Montador();

            MontadorC.compilar(mASTCabecalhos, getASTS(), mSaida);

            mASTS = getASTS();


            mFase = Fases.PRONTO;

            mETAPA_MONTAGEM = mSTATUS_SUCESSO;

            if (mDebug_Montagem) {
                for (String eMensagem : MontadorC.getMensagens()) {
                    mDebugMensagens.add(eMensagem);
                }
            }


        }
        if (mMostrar_Fases) {

            System.out.println("\t - 6 : Montagem                : " + mETAPA_MONTAGEM);

        }


    }


    public ArrayList<AST> getCabecalhos() {
        return mCabecalhos;
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

    public String getComentario_Processamento() {
        return mComentario_Processamento;
    }

    public String getParser_Processamento() {
        return mParser_Processamento;
    }


    public ArrayList<GrupoDeComentario> getComentarios() {
        return mComentarios;
    }


}
