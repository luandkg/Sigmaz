package Sigmaz.S06_Montador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Chronos_Intervalo;
import Sigmaz.S00_Utilitarios.Mensageiro;
import Sigmaz.S07_Executor.OMLRun;

public class Montador {

    private Mensageiro mMensageiro;

    private float mTempo_Leitura;
    private float mTempo_Processamento;
    private float mTempo_Organizacao;

    private Chaveador mChave_Cabecalho;
    private Chaveador mChave_Cogido;

    public Montador() {

        mMensageiro = new Mensageiro();

        mTempo_Leitura = 0.0F;
        mTempo_Processamento = 0.0F;
        mTempo_Organizacao = 0.0F;

        mChave_Cabecalho = new Chaveador(6);
        mChave_Cogido = new Chaveador(6);


        mChave_Cabecalho.set(0, 10);
        mChave_Cabecalho.set(1, 56);
        mChave_Cabecalho.set(2, 130);
        mChave_Cabecalho.set(3, 22);
        mChave_Cabecalho.set(4, 12);
        mChave_Cabecalho.set(5, 80);


        mChave_Cogido.set(0, 40);
        mChave_Cogido.set(1, 12);
        mChave_Cogido.set(2, 5);
        mChave_Cogido.set(3, 130);
        mChave_Cogido.set(4, 54);
        mChave_Cogido.set(5, 38);

    }

    public void limpar() {

        mMensageiro.limpar();

        mTempo_Leitura = 0.0F;
        mTempo_Processamento = 0.0F;
        mTempo_Organizacao = 0.0F;

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

        limpar();


        R5 mSegurador_Cabecalho = new R5(mChave_Cabecalho);
        R5 mSegurador_Codigo = new R5(mChave_Cogido);

        P2 mPacotador_Cabecalho = new P2();
        P2 mPacotador_Codigo = new P2();


        mMensageiro.mensagem("");
        mMensageiro.mensagem("############ MONTAGEM #################");
        mMensageiro.mensagem("");


        Chronos_Intervalo mTempo_01 = new Chronos_Intervalo();

        mTempo_01.marqueInicio();

        String eCabecalho = mPacotador_Cabecalho.empacotar(ASTCabecalho);

        mMensageiro.mensagem("\tCabecalho : " + mPacotador_Cabecalho.getObjetos());


        String eDocumento = mPacotador_Codigo.empacotar(ASTCodigo);

        mTempo_01.marqueFim();


        mMensageiro.mensagem("\tObjetos : " + mPacotador_Codigo.getObjetos());
        mMensageiro.mensagem("\tInstrucoes : " + mPacotador_Codigo.getInstrucoes());
        mMensageiro.mensagem("\tProfundidade : " + mPacotador_Codigo.getProfundidade());

        mMensageiro.mensagem("\tTamanho : " + mPacotador_Codigo.getTamanho());
        mMensageiro.mensagem("\tTempo de Empacotamento : " + mTempo_01.getIntervalo());

        Chronos_Intervalo mTempo_02 = new Chronos_Intervalo();

        mTempo_02.marqueInicio();

        R5Resposta mCabecalho = mSegurador_Cabecalho.guardar(eCabecalho);
        R5Resposta mCodigo = mSegurador_Codigo.guardar(eDocumento);

        mTempo_02.marqueFim();

        mMensageiro.mensagem("\tTempo de Criptografia : " + mTempo_02.getIntervalo());

        if (mCabecalho.getOk() && mCodigo.getOk()) {

            Chronos_Intervalo mTempo_03 = new Chronos_Intervalo();
            mTempo_03.marqueInicio();

            Assinatura mAssinatura = new Assinatura();


            OLMCabecalho mOLMCabecalho = OLM.criar(eArquivo, mCabecalho.getData(), mCodigo.getData(),mAssinatura.getData());

            mMensageiro.mensagem("\tOLM Titulo : " + mOLMCabecalho.getTitulo());
            mMensageiro.mensagem("\tOLM Versao : " + mOLMCabecalho.getVersao());

            mMensageiro.mensagem("\tOLM Setor Sigmaz : " + mOLMCabecalho.getSetorSigmaz());
            mMensageiro.mensagem("\tOLM Setor Codigo : " + mOLMCabecalho.getSetorCodigo());
            mMensageiro.mensagem("\tOLM Setor Assinatura : " + mOLMCabecalho.getAssinatura_Inicio());

            mMensageiro.mensagem("\tOLM Sigmaz Tamanho : " + mOLMCabecalho.getSigmaz_Tamanho());
            mMensageiro.mensagem("\tOLM Codigo Tamanho : " + mOLMCabecalho.getCodigo_Tamanho());
            mMensageiro.mensagem("\tOLM Assinatura Tamanho : " + mOLMCabecalho.getAssinatura_Tamanho());

            for (AST eC : ASTCabecalho) {
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

        } else {
            mMensageiro.errar("Houve um problema na montagem !");
        }


    }


    public OMLRun Decompilar(String eArquivo) {

        OMLRun mOMLRun = new OMLRun();

        R5 mDesSegurador_Cabecalho = new R5(mChave_Cabecalho);
        R5 mDesSegurador_Codigo = new R5(mChave_Cogido);

        P2 mDesemPacotador_Cabecalho = new P2();


        ArrayList<AST> ASTSaida = new ArrayList<AST>();

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

        mMensageiro.mensagem("OLM Sigmaz : " + mOLMCabecalho.getSetorSigmaz());
        mMensageiro.mensagem("OLM Sigmaz Tamanho : " + mOLMCabecalho.getSigmaz_Tamanho());

        mMensageiro.mensagem("OLM Codigo : " + mOLMCabecalho.getSetorCodigo());
        mMensageiro.mensagem("OLM Codigo Tamanho : " + mOLMCabecalho.getCodigo_Tamanho());

        mMensageiro.mensagem("OLM Assinatura : " + mOLMCabecalho.getAssinatura_Inicio());
        mMensageiro.mensagem("OLM Assinatura Tamanho : " + mOLMCabecalho.getAssinatura_Tamanho());


        if (mOLMCabecalho.getTitulo().contentEquals("OLM")) {


            if (String.valueOf(mOLMCabecalho.getVersao()).contentEquals("1")) {


                boolean mProblemaCabecalho = false;

                byte[] mCabecalho = mArquivador.lerBloco(mOLMCabecalho.getSetorSigmaz(), mOLMCabecalho.getSigmaz_Tamanho(), eArquivo);


                R5Resposta mASTCabecalho = mDesSegurador_Cabecalho.revelar(mCabecalho);

                if (mASTCabecalho.getOk()) {


                    mDesemPacotador_Cabecalho.desempacotar(mASTCabecalho.getConteudo());

                    if (mDesemPacotador_Cabecalho.getOK()) {



                        if (mDesemPacotador_Cabecalho.getDesempacotado().size() == 0) {
                            mProblemaCabecalho = true;
                        }else{

                            int v = 0;

                            for (AST eC : mDesemPacotador_Cabecalho.getDesempacotado()) {

                                if (eC.mesmoTipo("PRIVATE")) {
                                    mMensageiro.mensagem("Chave Privada : " + eC.getValor());
                                    v+=1;
                                } else if (eC.mesmoTipo("PUBLIC")) {
                                    mMensageiro.mensagem("Chave Publica : " + eC.getValor());
                                    v+=1;

                                } else if (eC.mesmoTipo("SHARED")) {
                                    mMensageiro.mensagem("Chave Compartilhada : " + eC.getValor());
                                    v+=1;

                                }

                                //  System.out.println(eC.getImpressao());

                            }

                            if (v<3){
                                mProblemaCabecalho = true;
                            }


                        }


                    } else {
                        mProblemaCabecalho = true;
                    }

                } else {
                    mProblemaCabecalho = true;
                }

                if (mProblemaCabecalho) {
                    mMensageiro.errar("Objeto corrompido - Setor Cabecalho !");
                }

                byte[] mDados = mArquivador.lerBloco(mOLMCabecalho.getSetorCodigo(), mOLMCabecalho.getCodigo_Tamanho(), eArquivo);


                R5Resposta mResposta = mDesSegurador_Codigo.revelar(mDados);

                mTempo_01.marqueFim();

                mTempo_Processamento = mTempo_01.getIntervalo();

                boolean mProblemaCodigo = false;

                if (mResposta.getOk()) {


                    Chronos_Intervalo mTempo_02 = new Chronos_Intervalo();

                    mTempo_02.marqueInicio();

                    P2 mDesemPacotador_Codigo = new P2();

                    mDesemPacotador_Codigo.desempacotar(mResposta.getConteudo());

                    mTempo_02.marqueFim();

                    mTempo_Organizacao = mTempo_02.getIntervalo();


                    if (mDesemPacotador_Codigo.getOK()) {
                        ASTSaida = mDesemPacotador_Codigo.getDesempacotado();

                        mOMLRun.carregar(mDesemPacotador_Cabecalho.getDesempacotado(),mDesemPacotador_Codigo.getDesempacotado());

                    } else {
                        mProblemaCodigo = true;
                    }

                } else {
                    mProblemaCodigo = true;
                }


                if (ASTSaida.size() == 0) {
                    mProblemaCodigo = true;
                } else {
                    for (AST eAST : ASTSaida) {
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
