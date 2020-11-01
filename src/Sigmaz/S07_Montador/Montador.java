package Sigmaz.S07_Montador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Chronos_Intervalo;
import Sigmaz.S00_Utilitarios.Mensageiro;
import Sigmaz.S08_Executor.OMLRun;

public class Montador {

    private Mensageiro mMensageiro;

    private float mTempo_Leitura;
    private float mTempo_Processamento;
    private float mTempo_Organizacao;

    private Chaveador mChave_Cabecalho;
    private Chaveador mChave_Cogido;

    private String mArquivo;

    private boolean mTerminou;
    private int mEtapa;
    private ArrayList<AST> CASTCabecalho;
    private ArrayList<AST> CASTCodigo;

    private String CCabecalho;
    private String CDocumento;

    private R5Resposta mCabecalho;
    private R5Resposta mCodigo;

    private String mFase;

    public Montador() {

        mMensageiro = new Mensageiro();

        mTempo_Leitura = 0.0F;
        mTempo_Processamento = 0.0F;
        mTempo_Organizacao = 0.0F;

        Chaves mChaves = new Chaves();

        mChave_Cabecalho = mChaves.getChave_Cabecalho();
        mChave_Cogido = mChaves.getChave_Codigo();


    }

    public void limpar() {

        mMensageiro.limpar();

        mTempo_Leitura = 0.0F;
        mTempo_Processamento = 0.0F;
        mTempo_Organizacao = 0.0F;

        mTerminou = false;
        mEtapa = 1;
        CCabecalho = "";
        CDocumento = "";
        mCabecalho = null;
        mCodigo = null;

    }

    public float getTempo_Leitura() {
        return mTempo_Leitura;
    }


    public float getTempo_Processamento() {
        return mTempo_Processamento;
    }

    public float getTempo_Organizacao() {
        return mTempo_Organizacao;
    }

    public ArrayList<String> getMensagens() {
        return mMensageiro.getMensagens();
    }

    public ArrayList<String> getErros() {
        return mMensageiro.getErros();
    }

    public void compilar(ArrayList<AST> ASTCabecalho, ArrayList<AST> ASTCodigo, String eArquivo) {


        compilar_iniciar(ASTCabecalho, ASTCodigo, eArquivo);

        while (!getTerminou()) {
            continuar();
        }


    }


    public void compilar_iniciar(ArrayList<AST> ASTCabecalho, ArrayList<AST> ASTCodigo, String eArquivo) {

        limpar();
        mFase = "";

        CASTCabecalho = ASTCabecalho;
        CASTCodigo = ASTCodigo;
        mArquivo = eArquivo;

        mFase = "Codificando";
    }

    public String getFase() {
        return mFase;
    }

    public void continuar() {

        if (mEtapa == 1) {


            Empacotador mCPacotador_Cabecalho = new Empacotador();
            Empacotador mCPacotador_Codigo = new Empacotador();


            mMensageiro.mensagem("");
            mMensageiro.mensagem("############ MONTAGEM #################");
            mMensageiro.mensagem("");


            Chronos_Intervalo mTempo_01 = new Chronos_Intervalo();

            mTempo_01.marqueInicio();

            CCabecalho = mCPacotador_Cabecalho.empacotar(CASTCabecalho);

            mMensageiro.mensagem("\tCabecalho : " + mCPacotador_Cabecalho.getObjetos());


            CDocumento = mCPacotador_Codigo.empacotar(CASTCodigo);

            mTempo_01.marqueFim();


            mMensageiro.mensagem("\tObjetos : " + mCPacotador_Codigo.getObjetos());
            mMensageiro.mensagem("\tInstrucoes : " + mCPacotador_Codigo.getInstrucoes());
            mMensageiro.mensagem("\tProfundidade : " + mCPacotador_Codigo.getProfundidade());

            mMensageiro.mensagem("\tTamanho : " + mCPacotador_Codigo.getTamanho());
            mMensageiro.mensagem("\tTempo de Empacotamento : " + mTempo_01.getIntervalo());

            mEtapa = 2;

            if (mMensageiro.temErros()) {
                mTerminou = true;
            }

            mFase = "Criptografando";


        } else if (mEtapa == 2) {


            Protetor mCSegurador_Cabecalho = new Protetor(mChave_Cabecalho);
            Protetor mCSegurador_Codigo = new Protetor(mChave_Cogido);


            Chronos_Intervalo mTempo_02 = new Chronos_Intervalo();

            mTempo_02.marqueInicio();

            mCabecalho = mCSegurador_Cabecalho.guardar(CCabecalho);
            mCodigo = mCSegurador_Codigo.guardar(CDocumento);

           // System.out.println("-->> CODIFICAR");
            //System.out.println(CDocumento);

            mTempo_02.marqueFim();

            mMensageiro.mensagem("\tTempo de Criptografia : " + mTempo_02.getIntervalo());

            if (mCabecalho.getOk() && mCodigo.getOk()) {

            } else {
                mMensageiro.errar("Houve um problema na montagem !");
            }

            mEtapa = 3;

            if (mMensageiro.temErros()) {
                mTerminou = true;
            }

            mFase = "Arquivando";


        } else if (mEtapa == 3) {


            Chronos_Intervalo mTempo_03 = new Chronos_Intervalo();
            mTempo_03.marqueInicio();

            Assinatura mAssinatura = new Assinatura();


            OLMCabecalho mOLMCabecalho = OLM.criar(mArquivo, mCabecalho.getData(), mCodigo.getData(), mAssinatura.getData());

            mMensageiro.mensagem("\tOLM Titulo : " + mOLMCabecalho.getTitulo());
            mMensageiro.mensagem("\tOLM Versao : " + mOLMCabecalho.getVersao());

            mMensageiro.mensagem("\tOLM Setor Sigmaz : " + mOLMCabecalho.getSetorSigmaz_Inicio());
            mMensageiro.mensagem("\tOLM Setor Codigo : " + mOLMCabecalho.getSetorCodigo_Inicio());
            mMensageiro.mensagem("\tOLM Setor Assinatura : " + mOLMCabecalho.getAssinatura_Inicio());

            mMensageiro.mensagem("\tOLM Sigmaz Tamanho : " + mOLMCabecalho.getSigmaz_Tamanho());
            mMensageiro.mensagem("\tOLM Codigo Tamanho : " + mOLMCabecalho.getCodigo_Tamanho());
            mMensageiro.mensagem("\tOLM Assinatura Tamanho : " + mOLMCabecalho.getAssinatura_Tamanho());

            for (AST eC : CASTCabecalho) {
                if (eC.mesmoTipo("PRIVATE")) {
                    mMensageiro.mensagem("\tChave Privada : " + eC.getValor());
                } else if (eC.mesmoTipo("PUBLIC")) {
                    mMensageiro.mensagem("\tChave Publica : " + eC.getValor());
                } else if (eC.mesmoTipo("SHARED")) {
                    mMensageiro.mensagem("\tChave Compartilhada : " + eC.getValor());
                }
            }

            mTempo_03.marqueFim();

            mMensageiro.mensagem("\tTempo de Escrita : " + mTempo_03.getIntervalo());


            mTerminou = true;
            mFase = "Concluido";

        }

    }


    public boolean getTerminou() {
        return mTerminou;
    }

    public OMLRun Decompilar(String eArquivo) {

        OMLRun mOMLRun = new OMLRun();

        Protetor mDesSegurador_Sigmaz = new Protetor(mChave_Cabecalho);
        Protetor mDesSegurador_Codigo = new Protetor(mChave_Cogido);

        Empacotador mDesempacotador = new Empacotador();


        Arquivador mArquivador = new Arquivador();


        mTempo_Processamento = 0.0F;
        mTempo_Organizacao = 0.0F;


        Chronos_Intervalo mTempo_00 = new Chronos_Intervalo();


        mTempo_00.marqueInicio();


        mTempo_00.marqueFim();


        mTempo_Leitura = mTempo_00.getIntervalo();

        Chronos_Intervalo mTempo_01 = new Chronos_Intervalo();

        mTempo_01.marqueInicio();


        OLM mOLM = new OLM();

        OLMCabecalho mOLMCabecalho = mOLM.lerCabecalho(eArquivo);


        mMensageiro.mensagem("OLM Titulo : " + mOLMCabecalho.getTitulo());
        mMensageiro.mensagem("OLM Versao : " + mOLMCabecalho.getVersao());

        mMensageiro.mensagem("OLM Sigmaz : " + mOLMCabecalho.getSetorSigmaz_Inicio());
        mMensageiro.mensagem("OLM Sigmaz Tamanho : " + mOLMCabecalho.getSigmaz_Tamanho());

        mMensageiro.mensagem("OLM Codigo : " + mOLMCabecalho.getSetorCodigo_Inicio());
        mMensageiro.mensagem("OLM Codigo Tamanho : " + mOLMCabecalho.getCodigo_Tamanho());

        mMensageiro.mensagem("OLM Assinatura : " + mOLMCabecalho.getAssinatura_Inicio());
        mMensageiro.mensagem("OLM Assinatura Tamanho : " + mOLMCabecalho.getAssinatura_Tamanho());


        if (mOLMCabecalho.getTitulo().contentEquals("OLM")) {


            if (String.valueOf(mOLMCabecalho.getVersao()).contentEquals("1")) {


                boolean mProblemaSigmaz = false;

                byte[] mCabecalho = mArquivador.lerBloco(mOLMCabecalho.getSetorSigmaz_Inicio(), mOLMCabecalho.getSigmaz_Tamanho(), eArquivo);


                R5Resposta mSetorSigmaz = mDesSegurador_Sigmaz.revelar(mCabecalho);

                if (mSetorSigmaz.getOk()) {


                    mDesempacotador.desempacotar(mSetorSigmaz.getConteudo());

                    if (mDesempacotador.getOK()) {


                        if (mDesempacotador.getDesempacotado().size() == 0) {
                            mProblemaSigmaz = true;
                        } else {

                            int v = 0;

                            for (AST eC : mDesempacotador.getDesempacotado()) {

                                if (eC.mesmoTipo("PRIVATE")) {
                                    mMensageiro.mensagem("Chave Privada : " + eC.getValor());
                                    v += 1;
                                } else if (eC.mesmoTipo("PUBLIC")) {
                                    mMensageiro.mensagem("Chave Publica : " + eC.getValor());
                                    v += 1;

                                } else if (eC.mesmoTipo("SHARED")) {
                                    mMensageiro.mensagem("Chave Compartilhada : " + eC.getValor());
                                    v += 1;

                                }

                                //  System.out.println(eC.getImpressao());

                            }

                            if (v < 3) {
                                mProblemaSigmaz = true;
                            }


                        }


                    } else {
                        mProblemaSigmaz = true;
                    }

                } else {
                    mProblemaSigmaz = true;
                }

                if (mProblemaSigmaz) {
                    mMensageiro.errar("Objeto corrompido - Setor Sigmaz !");
                }

                byte[] mDados = mArquivador.lerBloco(mOLMCabecalho.getSetorCodigo_Inicio(), mOLMCabecalho.getCodigo_Tamanho(), eArquivo);


                R5Resposta mResposta = mDesSegurador_Codigo.revelar(mDados);

                mTempo_01.marqueFim();

                mTempo_Processamento = mTempo_01.getIntervalo();

                boolean mProblemaCodigo = false;

                if (mResposta.getOk()) {

                   // System.out.println("-->> ABRINDO");

                 //   System.out.println(mResposta.getConteudo());

                    Chronos_Intervalo mTempo_02 = new Chronos_Intervalo();

                    mTempo_02.marqueInicio();

                    Empacotador mDesemPacotador_Codigo = new Empacotador();

                    mDesemPacotador_Codigo.desempacotar(mResposta.getConteudo());

                    mTempo_02.marqueFim();

                    mTempo_Organizacao = mTempo_02.getIntervalo();


                    if (mDesemPacotador_Codigo.getOK()) {

                        mOMLRun.carregar(mDesempacotador.getDesempacotado(), mDesemPacotador_Codigo.getDesempacotado());

                    } else {
                        mProblemaCodigo = true;
                    }

                } else {
                    mProblemaCodigo = true;
                }


                if (mOMLRun.getCodigo().size() == 0) {
                    mProblemaCodigo = true;
                } else {
                    for (AST eAST : mOMLRun.getCodigo()) {
                        if (eAST.mesmoTipo("SIGMAZ")) {

                        } else {
                            mProblemaCodigo = true;
                        }
                        // System.out.println(eAST.getImpressao());
                    }

                }


                if (mProblemaCodigo) {
                    mMensageiro.errar("Objeto corrompido - Setor Codigo !");
                }


            } else {
                mMensageiro.errar("Objeto OLM Versao desconhecida : " + mOLMCabecalho.getVersao());
            }


        } else {
            mMensageiro.errar("Objeto OLM corrompido !");
        }


        return mOMLRun;

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


}
