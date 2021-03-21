package Sigmaz;

import Sigmaz.S08_Utilitarios.*;
import Sigmaz.S01_Compilador.Compilador;
import Sigmaz.S03_Integrador.Integrador;
import Sigmaz.S04_Montador.Montador;
import Sigmaz.S02_PosProcessamento.Processadores.Cabecalho;
import Sigmaz.S02_PosProcessamento.PosProcessador;

import java.io.File;
import java.util.ArrayList;

public class Sigmaz_Etapas {

    private Cabecalho mCabecalho;
    private ArrayList<String> mErros_Mensagens;
    private Fases mFase;

    private String mETAPA_PRE_PROCESSAMENTO;
    private String mETAPA_LEXER;
    private String mETAPA_PARSER;
    private String mETAPA_POS_PROCESSAMENTO;
    private String mETAPA_MONTAGEM;
    private String mETAPA_INTEGRALIZACAO;

    private String mSTATUS_NAO;
    private String mSTATUS_SUCESSO;
    private String mSTATUS_FALHOU;

    private boolean mDebug;
    private ArrayList<String> mDebugMensagens;
    private ArrayList<String> mRequisitados;

    private String mLocal;
    private String mSaida;
    private String mArquivo;
    private int mOpcao;


    private boolean mEstaPreProcessando;
    private String mPreProcessando;
    private String mArquivoLexando;
    private String mArquivoParseando;

    private boolean mCorrentePreprocessando;
    private boolean mCorrenteParseando;
    private boolean mCorrenteCompilando;


    private String mLexer_Processamento;

    private Compilador mSigmazCompilador;

    private ArrayList<GrupoDeComentario> mComentarios;


    private ArrayList<GrupoDeErro> mErros_PreProcessamento;
    private ArrayList<GrupoDeErro> mErros_Lexer;
    private ArrayList<GrupoDeErro> mErros_Parser;
    private ArrayList<String> mErros_PosProcessamento;
    private ArrayList<String> mErros_Execucao;

    private ArrayList<AST> mASTS;

    private boolean mTemErros;

    public ArrayList<AST> getASTS() {
        return mASTS;
    }

    private PosProcessador PosProcessadorC;
    private String mEtapa;
    private String mSubEtapa;

    private Montador MontadorC;
    private int mPassos;

    private boolean mDebugar;

    private OrganizadorDeEtapas mOrganizadorDeEtapas;

    public enum Fases {
        PRE_PROCESSAMENTO, LEXER, PARSER, POS_PROCESSAMENTO, INTEGRALIZACAO, MONTAGEM, PRONTO;
    }

    public boolean estaPre() {
        return mCorrentePreprocessando;
    }

    public boolean estaParseando() {
        return mCorrenteParseando;
    }

    public boolean estaCompilando() {
        return mCorrenteCompilando;
    }


    public Sigmaz_Etapas() {


        mCabecalho = new Cabecalho();

        mSTATUS_NAO = "NAO REALIZADO";
        mSTATUS_SUCESSO = "SUCESSO";
        mSTATUS_FALHOU = "FALHOU";


        mETAPA_PRE_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_LEXER = mSTATUS_NAO;
        mETAPA_PARSER = mSTATUS_NAO;
        mETAPA_POS_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_INTEGRALIZACAO = mSTATUS_NAO;
        mETAPA_MONTAGEM = mSTATUS_NAO;

        mASTS = new ArrayList<AST>();

        mDebug = false;
        mDebugMensagens = new ArrayList<String>();
        mErros_Mensagens = new ArrayList<String>();
        mSaida = "";
        mLocal = "";
        mArquivo = "";
        mOpcao = 0;
        mEstaPreProcessando = false;
        mPreProcessando = "";
        mPassos = 0;

        mCorrentePreprocessando = false;
        mCorrenteParseando = false;
        mCorrenteCompilando = false;

        mComentarios = new ArrayList<GrupoDeComentario>();
        mRequisitados = new ArrayList<String>();


        mLexer_Processamento = "";


        mTemErros = false;


        mErros_PreProcessamento = new ArrayList<GrupoDeErro>();
        mErros_Lexer = new ArrayList<GrupoDeErro>();
        mErros_Parser = new ArrayList<GrupoDeErro>();
        mErros_PosProcessamento = new ArrayList<String>();
        mErros_Execucao = new ArrayList<String>();

        mEtapa = "";
        mDebugar = false;

        mOrganizadorDeEtapas = new OrganizadorDeEtapas();

    }


    public void init(String eArquivo, String eSaida, int eOpcao, boolean eDebugar) {

        limpar();

        mDebugar = eDebugar;

        mSaida = eSaida;
        mArquivo = eArquivo;
        mOpcao = eOpcao;

        mOrganizadorDeEtapas.limpar();

        mOrganizadorDeEtapas.implemente(new EtapaCallBack(0) {
            @Override
            public int processar() {

                File arq = new File(mSaida);
                mLocal = arq.getParent() + "/";

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


                System.out.println("");
                System.out.println("################ SIGMAZ ETAPAS ################");
                System.out.println("");

                System.out.println("\t - Source : " + eArquivo);
                System.out.println("\t - Local  : " + mLocal);
                System.out.println("\t - Build  : " + mSaida);
                System.out.println("\t - Modo   : " + eModo);

                System.out.println("");


                mCorrentePreprocessando = false;
                mCorrenteParseando = false;
                mCorrenteCompilando = false;

                mEtapa = "Pre-Processamento";

                return 1;
            }
        });


        mOrganizadorDeEtapas.implemente(new EtapaCallBack(1) {
            @Override
            public int processar() {
                return executar_init_01();
            }
        });


        mOrganizadorDeEtapas.implemente(new EtapaCallBack(2) {
            @Override
            public int processar() {
                return executar_init_02();
            }
        });


        mOrganizadorDeEtapas.implemente(new EtapaCallBack(3) {
            @Override
            public int processar() {
                return executar_init_03();
            }
        });

        mOrganizadorDeEtapas.implemente(new EtapaCallBack(4) {
            @Override
            public int processar() {
                return executar_Lexer();
            }
        });

        mOrganizadorDeEtapas.implemente(new EtapaCallBack(5) {
            @Override
            public int processar() {
                return executar_Parser();
            }
        });

        mOrganizadorDeEtapas.implemente(new EtapaCallBack(6) {
            @Override
            public int processar() {
                return iniciar_PosProcessamento(mLocal);
            }
        });

        mOrganizadorDeEtapas.implemente(new EtapaCallBack(7) {
            @Override
            public int processar() {
                return continuar_PosProcessamento();
            }
        });

        mOrganizadorDeEtapas.implemente(new EtapaCallBack(8) {
            @Override
            public int processar() {
                return terminar_PosProcessamento();
            }
        });

        mOrganizadorDeEtapas.implemente(new EtapaCallBack(9) {
            @Override
            public int processar() {
                return organizar();
            }
        });

        mOrganizadorDeEtapas.implemente(new EtapaCallBack(10) {
            @Override
            public int processar() {
                return integralizar_iniciar();
            }
        });


        mOrganizadorDeEtapas.implemente(new EtapaCallBack(11) {
            @Override
            public int processar() {
                return integralizar_realizar();
            }
        });


        mOrganizadorDeEtapas.implemente(new EtapaCallBack(12) {
            @Override
            public int processar() {
                return montagem_iniciar();
            }
        });

        mOrganizadorDeEtapas.implemente(new EtapaCallBack(13) {
            @Override
            public int processar() {
                return montagem_continuar();
            }
        });

        mOrganizadorDeEtapas.implemente(new EtapaCallBack(14) {
            @Override
            public int processar() {
                return montagem_terminar();
            }
        });

        mOrganizadorDeEtapas.implemente(new EtapaCallBack(15) {
            @Override
            public int processar() {
                return terminar();
            }
        });

        mOrganizadorDeEtapas.implemente(new EtapaCallBack(16) {
            @Override
            public int processar() {

                mEtapa = "";
                mSubEtapa = "";

                return 17;
            }
        });

        mOrganizadorDeEtapas.iniciar(0);


    }


    public int getPassos() {
        return mPassos;
    }

    public void continuar() {

        verificarSeTemErros();

        if (!mTemErros) {
            mPassos += 1;
            mOrganizadorDeEtapas.processe();
        }


    }

    public void verificarSeTemErros() {

        if (mErros_Mensagens.size() > 0) {
            mTemErros = true;
        }

        if (mTemErros) {
            mOrganizadorDeEtapas.setEtapaID(8);
        }
    }

    public int executar_init_01() {

        int mRet = 1;

        mETAPA_PRE_PROCESSAMENTO = "EXECUTANDO";

        mEstaPreProcessando = true;


        if (mFase == Fases.PRE_PROCESSAMENTO) {

            mPreProcessando = "";
            mEtapa = "Pre-Processamento";
            mSubEtapa = "Init";

            ArrayList<String> eArquivos = new ArrayList<String>();
            eArquivos.add(mArquivo);

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


            mSigmazCompilador = new Compilador();
            mSigmazCompilador.initPassos(eArquivos, AST_Raiz, mDebugar, new AST(""));
            mPreProcessando = mSigmazCompilador.getProcessando();

            mRet = 2;


        }

        return mRet;
    }

    public int executar_init_02() {


        mEstaPreProcessando = true;

        int mRet = 2;

        if (mSigmazCompilador.getTerminou()) {

            mRet = 3;

            mLexer_Processamento = mSigmazCompilador.getLexer_Processamento();


            for (String mRequisicao : mSigmazCompilador.getRequisitados()) {
                mRequisitados.add(mRequisicao);
            }

            for (GrupoDeComentario mGrupoDeComentario : mSigmazCompilador.getComentarios()) {
                mComentarios.add(mGrupoDeComentario);
            }

            for (GrupoDeErro mGrupoDeErro : mSigmazCompilador.getErros_Processamento()) {
                mErros_PreProcessamento.add(mGrupoDeErro);
            }

            for (GrupoDeErro mGrupoDeErro : mSigmazCompilador.getErros_Lexer()) {
                mErros_Lexer.add(mGrupoDeErro);
            }


            for (GrupoDeErro mGrupoDeErro : mSigmazCompilador.getErros_Parser()) {
                mErros_Parser.add(mGrupoDeErro);
            }

        } else {

            mSigmazCompilador.continuar();
            mPreProcessando = mSigmazCompilador.getProcessando();

            mCorrentePreprocessando = mSigmazCompilador.estaPreProcessando();
            mCorrenteParseando = mSigmazCompilador.estaParseando();
            mCorrenteCompilando = mSigmazCompilador.estaCompilando();

        }

        mPreProcessando = mSigmazCompilador.getProcessando();
        mArquivoLexando = mSigmazCompilador.getArquivoLexando();
        mArquivoParseando = mSigmazCompilador.getArquivoParseando();

        mCorrentePreprocessando = estaPreProcessando();
        mCorrenteParseando = estaParseando();

        mCorrenteCompilando = estaCompilando();


        if (getEstaPreProcessando()) {

            mETAPA_PRE_PROCESSAMENTO = "EXECUTANDO -->> " + getPreprocessando();

            if (estaParseando()) {

                mEtapa = "Lexer";
                mSubEtapa = getArquivoLexando();

                mETAPA_PARSER = "PRONTO";
                mETAPA_LEXER = "EXECUTANDO -->> " + getArquivoLexando();
            }

            if (estaCompilando()) {

                mEtapa = "Parser";
                mSubEtapa = getArquivoParseando();

                mETAPA_LEXER = "PRONTO";
                mETAPA_PARSER = "EXECUTANDO -->> " + getArquivoParseando();
            }

        }

        return mRet;
    }

    public int executar_init_03() {
        
        mEstaPreProcessando = true;
        mETAPA_PRE_PROCESSAMENTO = "CONCLUIDO";
        mSubEtapa = "Parser";

        mEtapa = "Pre-Processamento";
        mSubEtapa = "Finalize";

        int mRet = 3;

        if (mSigmazCompilador.getTerminou()) {

            mDebugMensagens.add(getPreProcessamento());


            if (getErros_PreProcessamento().size() == 0) {

                mFase = Fases.LEXER;
                mETAPA_PRE_PROCESSAMENTO = mSTATUS_SUCESSO;

                mETAPA_LEXER = mSTATUS_SUCESSO;
                mETAPA_PARSER = mSTATUS_SUCESSO;

            } else {

                mETAPA_PRE_PROCESSAMENTO = mSTATUS_FALHOU;

                mErros_Mensagens.add("\n\t ERROS DE PRE PROCESSAMENTO : ");

                for (GrupoDeErro eGE : getErros_PreProcessamento()) {
                    mErros_Mensagens.add("\t\t" + eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        mErros_Mensagens.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                    }
                }

            }

            mRet = 4;
            System.out.println("\t - 1 : Pre Processamento       : " + mETAPA_PRE_PROCESSAMENTO);

        }


        mEstaPreProcessando = false;

        return mRet;
    }


    private int continuar_PosProcessamento() {


        int mRet = 7;

        PosProcessadorC.continuar();

        mSubEtapa = PosProcessadorC.getFase();

        mETAPA_POS_PROCESSAMENTO = "EXECUTANDO -->> " + PosProcessadorC.getFase();


        if (PosProcessadorC.getTerminou()) {

            mRet = 8;

            if (PosProcessadorC.getErros().size() == 0) {
                mETAPA_POS_PROCESSAMENTO = mSTATUS_SUCESSO;
            } else {
                mETAPA_POS_PROCESSAMENTO = mSTATUS_FALHOU;
            }

        }


        return mRet;

    }

    public int terminar_PosProcessamento() {

        int mRet = 8;

        if (mRet == 8) {

            for (String eMensagem : PosProcessadorC.getMensagens()) {
                mDebugMensagens.add(eMensagem);
            }


            if (PosProcessadorC.getErros().size() == 0) {

                mRet = 9;
                mSubEtapa = "";
                mEtapa = "";

            } else {

                mETAPA_POS_PROCESSAMENTO = mSTATUS_FALHOU;

                mErros_Mensagens.add("\n\t ERROS DE POS-PROCESSAMENTO : ");

                for (String Erro : PosProcessadorC.getErros()) {
                    mErros_Mensagens.add("\t\t" + Erro);
                }

            }

            System.out.println("\t - 4 : Pos Processamento       : " + mETAPA_POS_PROCESSAMENTO);


            if (mSigmazCompilador.getErros_Processamento().size() > 0) {
                mRet = 14;
            }


        }

        return mRet;
    }


    public int organizar() {

        mEtapa = "Organizando";
        mSubEtapa = "";
        mFase = Fases.INTEGRALIZACAO;

        try {

            Thread.sleep(500);

        } catch (Exception e) {

        }

        return 10;

    }

    public int terminar() {


        if (mDebug) {

            System.out.println("");
            System.out.println("################ DEBUG ################");
            System.out.println("");

            for (String eMensagem : mDebugMensagens) {

                System.out.println(eMensagem);
            }

        }

        if (mFase == Fases.PRONTO) {


            System.out.println("----------------------------------------------");
            System.out.println("");
            System.out.println("\t - COMPILADO COM SUCESSO");
            System.out.println("");
            System.out.println("----------------------------------------------");


        } else {


            System.out.println("----------------------------------------------");
            System.out.println("");

            for (String eMensagem : mErros_Mensagens) {
                System.out.println(eMensagem);
            }

            System.out.println("");
            System.out.println("----------------------------------------------");


        }

        return 16;
    }

    public boolean getTerminou() {
        return mOrganizadorDeEtapas.getEtapaID() >= 16;
    }

    public int getEtapaID() {
        return mOrganizadorDeEtapas.getEtapaID();
    }


    public boolean estaPreProcessando() {
        return mCorrentePreprocessando;
    }


    private int executar_Lexer() {

        int mRet = 4;

        if (mFase == Fases.LEXER) {

            mDebugMensagens.add(getLexer_Processamento());

            mEtapa = "Lexer";
            mSubEtapa = "";

            if (getErros_Lexer().size() == 0) {
                mFase = Fases.PARSER;
                mETAPA_LEXER = mSTATUS_SUCESSO;
                mRet = 5;
            } else {
                mETAPA_LEXER = mSTATUS_FALHOU;

                mErros_Mensagens.add("\n\t ERROS DE LEXER : ");

                for (GrupoDeErro eGE : mSigmazCompilador.getErros_Lexer()) {
                    mErros_Lexer.add(eGE);
                    mErros_Mensagens.add("\t\t" + eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        mErros_Mensagens.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                    }
                }

            }

            System.out.println("\t - 2 : Lexer                   : " + mETAPA_LEXER);


            if (mSigmazCompilador.getErros_Lexer().size() > 0) {
                mRet = 14;
            }

        }

        return mRet;

    }

    private int executar_Parser() {

        int mRet = 5;

        if (mFase == Fases.PARSER) {

            mEtapa = "Parser";
            mSubEtapa = "";

            if (getErros_Parser().size() == 0) {
                mFase = Fases.POS_PROCESSAMENTO;
                mETAPA_PARSER = mSTATUS_SUCESSO;
                mRet = 6;

            } else {
                mETAPA_PARSER = mSTATUS_FALHOU;

                mErros_Mensagens.add("\n\t ERROS DE COMPILACAO : ");

                for (GrupoDeErro eGE : mSigmazCompilador.getErros_Parser()) {
                    mErros_Parser.add(eGE);
                    mErros_Mensagens.add("\t\t" + eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        mErros_Mensagens.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());

                    }
                }

            }

            System.out.println("\t - 3 : Parser                  : " + mETAPA_PARSER);

            if (mSigmazCompilador.getErros_Parser().size() > 0) {
                mRet = 14;
            }


        }

        return mRet;

    }


    private int iniciar_PosProcessamento(String mLocal) {

        mEtapa = "Pos-Processamento";
        mFase = Fases.POS_PROCESSAMENTO;

        if (mFase == Fases.POS_PROCESSAMENTO) {

            PosProcessadorC = new PosProcessador();
            PosProcessadorC.initPassos(getASTS(), mLocal);

            mSubEtapa = PosProcessadorC.getFase();
        }

        return 7;
    }

    public int integralizar_iniciar() {

        if (mFase == Fases.INTEGRALIZACAO) {

            mEtapa = "Integralizacao";
            mSubEtapa = "";
            mETAPA_INTEGRALIZACAO = "EXECUTANDO";

        }

        return 11;
    }

    public int integralizar_realizar() {


        if (mFase == Fases.INTEGRALIZACAO) {

            mEtapa = "Integralizacao";
            mSubEtapa = "";


            Integrador mIntegrador = new Integrador();

            mIntegrador.setDebug(false);

            mIntegrador.init(getASTS(), "");

            mETAPA_INTEGRALIZACAO = "CONCLUIDO";

            for (String eMensagem : mIntegrador.getMensagens()) {
                mDebugMensagens.add(eMensagem);
            }


            if (mIntegrador.getErros().size() == 0) {

                mETAPA_INTEGRALIZACAO = mSTATUS_SUCESSO;
                mFase = Fases.MONTAGEM;

            } else {

                mETAPA_INTEGRALIZACAO = mSTATUS_FALHOU;


            }

            System.out.println("\t - 5 : Integralizacao          : " + mETAPA_INTEGRALIZACAO);


        }

        return 12;
    }

    public int montagem_iniciar() {


        mEtapa = "Montagem";
        mSubEtapa = "Objeto";
        mETAPA_MONTAGEM = "MONTANDO -->> " + mSaida;
        mFase = Fases.MONTAGEM;

        ArrayList<AST> mASTCabecalhos = new ArrayList<AST>();

        SigmazCab mSigmazCab = new SigmazCab();

        mSigmazCab.gravarCabecalho(mCabecalho, mASTCabecalhos);

        mASTS = getASTS();


        MontadorC = new Montador();

        MontadorC.compilar_iniciar(mASTCabecalhos, getASTS(), new ArrayList<AST>(), mSaida);

        mETAPA_MONTAGEM = "EXECUTANDO -->> " + MontadorC.getFase();


        return 13;

    }

    public int montagem_continuar() {

        int mRet = 13;

        if (mFase == Fases.MONTAGEM) {

            MontadorC.continuar();

            mETAPA_MONTAGEM = "EXECUTANDO -->> " + MontadorC.getFase();

            if (MontadorC.getTerminou()) {
                mRet = 14;
            }

        }

        return mRet;
    }

    public int montagem_terminar() {

        if (mFase == Fases.MONTAGEM) {

            mFase = Sigmaz_Etapas.Fases.PRONTO;

            mETAPA_MONTAGEM = mSTATUS_SUCESSO;

            for (String eMensagem : MontadorC.getMensagens()) {
                mDebugMensagens.add(eMensagem);
            }

            System.out.println("\t - 6 : Montagem                : " + mETAPA_MONTAGEM);

        }
        return 15;
    }


    public void limpar() {

        mFase = Sigmaz_Etapas.Fases.PRE_PROCESSAMENTO;

        mETAPA_PRE_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_LEXER = mSTATUS_NAO;
        mETAPA_PARSER = mSTATUS_NAO;
        mETAPA_POS_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_MONTAGEM = mSTATUS_NAO;

        mDebugMensagens.clear();

        mTemErros = false;

        mErros_Lexer.clear();
        mErros_Parser.clear();
        mErros_PosProcessamento.clear();
        mErros_Execucao.clear();
        mErros_PreProcessamento.clear();

        mASTS.clear();

        mEstaPreProcessando = false;
        mPreProcessando = "";

        mRequisitados.clear();

        mErros_PreProcessamento.clear();
        mErros_Lexer.clear();
        mErros_Parser.clear();

        mComentarios.clear();


        mLexer_Processamento = "";

        mPassos = 0;

    }


    public String getEtapa() {
        return mEtapa;
    }

    public String getSubEtapa() {
        return mSubEtapa;
    }

    public boolean getEstaPreProcessando() {
        return mEstaPreProcessando;
    }


    public String getPreprocessando() {
        return mPreProcessando;
    }

    public String getArquivoLexando() {
        return mArquivoLexando;
    }

    public String getArquivoParseando() {
        return mArquivoParseando;
    }

    public String getPreProcessamento() {
        return mETAPA_PRE_PROCESSAMENTO;
    }

    public String getLexer() {
        return mETAPA_LEXER;
    }

    public String getCompiler() {
        return mETAPA_PARSER;
    }


    public String getPosProcessamento() {
        return mETAPA_POS_PROCESSAMENTO;
    }

    public String getMontagem() {
        return mETAPA_MONTAGEM;
    }

    public String getIntegralizacao() {
        return mETAPA_INTEGRALIZACAO;
    }


    public void mostrarDebug(boolean eDebug) {
        mDebug = eDebug;
    }

    public void setCabecalho(Cabecalho eCabecalho) {
        mCabecalho = eCabecalho;
    }


    public ArrayList<String> getRequisitados() {
        return mRequisitados;
    }


    public String getLexer_Processamento() {
        return mLexer_Processamento;
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

    public ArrayList<GrupoDeErro> getErros_Parser() {
        return mErros_Parser;
    }

    public ArrayList<String> getErros_PosProcessamento() {
        return mErros_PosProcessamento;
    }

    public ArrayList<String> getErros_Execucao() {
        return mErros_Execucao;
    }


    public void mostrarErros() {

        System.out.println("\n\t ERROS DE LEXER : ");

        for (GrupoDeErro eGE : mErros_Lexer) {
            System.out.println("\t\t" + eGE.getArquivo());
            for (Erro eErro : eGE.getErros()) {
                System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
            }
        }

        System.out.println("\n\t ERROS DE PARSER : ");

        for (GrupoDeErro eGE : mErros_Parser) {
            System.out.println("\t\t" + eGE.getArquivo());
            for (Erro eErro : eGE.getErros()) {
                System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
            }
        }


    }

}
