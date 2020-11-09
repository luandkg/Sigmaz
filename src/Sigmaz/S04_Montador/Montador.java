package Sigmaz.S04_Montador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


import Sigmaz.S00_Utilitarios.*;
import Sigmaz.S05_Executor.OMLRun;

public class Montador {

    private Mensageiro mMensageiro;

    private float mTempo_Leitura;
    private float mTempo_Processamento;
    private float mTempo_Organizacao;

    private float mTempo_Escrita;

    private Chaveador mChave_Cabecalho;
    private Chaveador mChave_Cogido;
    private Chaveador mChave_Assinatura;

    private String mArquivo;

    private boolean mTerminou;
    private ArrayList<AST> CASTCabecalho;
    private ArrayList<AST> CASTCodigo;
    private ArrayList<AST> ASTDebug;

    private OLMCabecalho mOLM;

    private OrganizadorDeProcessos mProcessador;

    public Montador() {

        mMensageiro = new Mensageiro();

        mTempo_Leitura = 0.0F;
        mTempo_Processamento = 0.0F;
        mTempo_Organizacao = 0.0F;
        mTempo_Escrita = 0.0F;

        Chaves mChaves = new Chaves();

        mChave_Cabecalho = mChaves.getChave_Cabecalho();
        mChave_Cogido = mChaves.getChave_Codigo();
        mChave_Assinatura = mChaves.getChave_Assinatura();

        mProcessador = new OrganizadorDeProcessos();

    }

    public void limpar() {

        mMensageiro.limpar();

        mTempo_Leitura = 0.0F;
        mTempo_Processamento = 0.0F;
        mTempo_Organizacao = 0.0F;

        mTerminou = false;

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

    public void compilar(ArrayList<AST> ASTCabecalho, ArrayList<AST> ASTCodigo, ArrayList<AST> ASTDebug, String eArquivo) {


        compilar_iniciar(ASTCabecalho, ASTCodigo,ASTDebug, eArquivo);

        while (!getTerminou()) {
            continuar();
        }


    }


    public void compilar_iniciar(ArrayList<AST> ASTCabecalho, ArrayList<AST> ASTCodigo,ArrayList<AST> eASTDebug,  String eArquivo) {

        limpar();

        CASTCabecalho = ASTCabecalho;
        CASTCodigo = ASTCodigo;
        ASTDebug=eASTDebug;

        mArquivo = eArquivo;


        mProcessador.implementeComStatus("Codificando",new ProcessoCallback() {
            @Override
            public void processar() {
                iniciar_Montagem();
            }
        });

        mProcessador.implementeComStatus("Criando Objeto OLM",new ProcessoCallback() {
            @Override
            public void processar() { mOLM = OLM.criarVazio(mArquivo);
            }
        });

        mProcessador.implementeComStatus("Setor Sigmaz",new ProcessoCallback() {
            @Override
            public void processar() {
                guardar_sigmaz();
            }
        });

        mProcessador.implementeComStatus("Setor Codigo",new ProcessoCallback() {
            @Override
            public void processar() {
                guardar_codigo();
            }
        });

        mProcessador.implementeComStatus("Setor Assinatura",new ProcessoCallback() {
            @Override
            public void processar() {
                guardar_assinatura();
            }
        });

        mProcessador.implementeComStatus("Setor Debug",new ProcessoCallback() {
            @Override
            public void processar() {
                guardar_debug();
            }
        });

        mProcessador.implementeComStatus("Finalizando Montagem",new ProcessoCallback() {
            @Override
            public void processar() {
                finalizar_Montagem();
            }
        });

        mProcessador.implementeStatus("Concluido");

        mProcessador.iniciar();

    }

    public String getFase() {
        return mProcessador.getStatus();
    }


    public void continuar() {

        if (mMensageiro.temErros()) {
            mTerminou = true;
        } else {
            if (mProcessador.getTerminou()){
                mTerminou = true;
            }else{
                mProcessador.processe();
            }
        }

    }

    private void iniciar_Montagem() {

        mTempo_Escrita = 0;

        mMensageiro.mensagem("");
        mMensageiro.mensagem("############ MONTAGEM #################");
        mMensageiro.mensagem("");


        Chronos_Intervalo mTempo_01 = new Chronos_Intervalo();

        mTempo_01.marqueInicio();


        mTempo_01.marqueFim();


        if (mMensageiro.temErros()) {
            mTerminou = true;
        }


    }

    private void guardar_sigmaz() {

        Chronos_Intervalo mTempoTodo = new Chronos_Intervalo();
        Chronos_Intervalo mTempoDocumentando = new Chronos_Intervalo();
        Chronos_Intervalo mTempoProtegendo = new Chronos_Intervalo();
        Chronos_Intervalo mTempoArquivando = new Chronos_Intervalo();


        mTempoTodo.marqueInicio();
        mTempoDocumentando.marqueInicio();

        Empacotador mCPacotador_Cabecalho = new Empacotador();
        Protetor mCSegurador_Cabecalho = new Protetor(mChave_Cabecalho);

        // DOCUMENTAR
        String CCabecalho = mCPacotador_Cabecalho.empacotar(CASTCabecalho);
        mMensageiro.mensagem("\tCabecalho : " + mCPacotador_Cabecalho.getObjetos());

        mTempoDocumentando.marqueFim();

        // PROTEGER
        mTempoProtegendo.marqueInicio();
        R5Resposta mCabecalho = mCSegurador_Cabecalho.guardar(CCabecalho);
        mTempoProtegendo.marqueFim();


        if (mCabecalho.getOk()) {

            mTempoArquivando.marqueInicio();
            Arquivador mArquivador = new Arquivador();

            long eSetor = mArquivador.guardarSetor(mCabecalho.getData(), mArquivo);
            long eTamanho = mCabecalho.getData().length;

            mTempoArquivando.marqueFim();

            mTempo_Escrita += mTempoArquivando.getIntervalo();

            mArquivador.marcarLocal(mOLM.getL0(), eSetor, mArquivo);
            mArquivador.marcarLocal(mOLM.getL1(), mCabecalho.getData().length, mArquivo);

            mOLM.definirSigmaz(eSetor, eTamanho);

        } else {
            mMensageiro.errar("Houve um problema na montagem do Setor Sigmaz !");
        }

        mTempoTodo.marqueFim();
        mMensageiro.mensagem("\tTempo Setor Sigmaz : " + mTempoTodo.getIntervalo());
        mMensageiro.mensagem("\t\t - Documentando : " + mTempoDocumentando.getIntervalo());
        mMensageiro.mensagem("\t\t - Protegendo : " + mTempoProtegendo.getIntervalo());
        mMensageiro.mensagem("\t\t - Arquivando : " + mTempoArquivando.getIntervalo());

        if (mMensageiro.temErros()) {
            mTerminou = true;
        }



    }

    private void guardar_codigo() {

        Chronos_Intervalo mTempoTodo = new Chronos_Intervalo();
        Chronos_Intervalo mTempoDocumentando = new Chronos_Intervalo();
        Chronos_Intervalo mTempoProtegendo = new Chronos_Intervalo();
        Chronos_Intervalo mTempoArquivando = new Chronos_Intervalo();


        mTempoTodo.marqueInicio();
        Empacotador mCPacotador_Codigo = new Empacotador();

        // DOCUMENTAR

        mTempoDocumentando.marqueInicio();
        String CDocumento = mCPacotador_Codigo.empacotar(CASTCodigo);
        mTempoDocumentando.marqueFim();

        mMensageiro.mensagem("\tObjetos : " + mCPacotador_Codigo.getObjetos());
        mMensageiro.mensagem("\tInstrucoes : " + mCPacotador_Codigo.getInstrucoes());
        mMensageiro.mensagem("\tProfundidade : " + mCPacotador_Codigo.getProfundidade());
        mMensageiro.mensagem("\tTamanho : " + mCPacotador_Codigo.getTamanho());

        // PROTEGER

        Protetor mCSegurador_Codigo = new Protetor(mChave_Cogido);

        mTempoProtegendo.marqueInicio();
        R5Resposta mCodigo = mCSegurador_Codigo.guardar(CDocumento);
        mTempoProtegendo.marqueFim();

        if (mCodigo.getOk()) {

            Arquivador mArquivador = new Arquivador();

            mTempoArquivando.marqueInicio();
            long eSetor = mArquivador.guardarSetor(mCodigo.getData(), mArquivo);
            mTempoArquivando.marqueFim();

            int eTamanho = mCodigo.getData().length;

            mArquivador.marcarLocal(mOLM.getL2(), eSetor, mArquivo);
            mArquivador.marcarLocal(mOLM.getL3(), eTamanho, mArquivo);

            mOLM.definirCodigo(eSetor, eTamanho);

            mTempo_Escrita += mTempoArquivando.getIntervalo();

        } else {
            mMensageiro.errar("Houve um problema na montagem do Setor Codigo !");
        }


        mTempoTodo.marqueFim();
        mMensageiro.mensagem("\tTempo Setor Codigo : " + mTempoTodo.getIntervalo());
        mMensageiro.mensagem("\t\t - Documentando : " + mTempoDocumentando.getIntervalo());
        mMensageiro.mensagem("\t\t - Protegendo : " + mTempoProtegendo.getIntervalo());
        mMensageiro.mensagem("\t\t - Arquivando : " + mTempoArquivando.getIntervalo());


        if (mMensageiro.temErros()) {
            mTerminou = true;
        }


    }

    private void guardar_assinatura() {

        Chronos_Intervalo mTempoTodo = new Chronos_Intervalo();
        Chronos_Intervalo mTempoDocumentando = new Chronos_Intervalo();
        Chronos_Intervalo mTempoProtegendo = new Chronos_Intervalo();
        Chronos_Intervalo mTempoArquivando = new Chronos_Intervalo();

        mTempoTodo.marqueInicio();

        Chronos_Intervalo mTempo_02 = new Chronos_Intervalo();
        mTempo_02.marqueInicio();

        Assinatura mAssinatura = new Assinatura();


        mTempo_02.marqueFim();
        mMensageiro.mensagem("\tTempo de Criptografia Assinatura: " + mTempo_02.getIntervalo());

        Protetor mProtetor = new Protetor(mChave_Assinatura);


        mTempoProtegendo.marqueInicio();
        R5Resposta mAssinaturaCodificada = mProtetor.guardar(mAssinatura.getData());
        mTempoProtegendo.marqueFim();

        if (mAssinaturaCodificada.getOk()) {

            Arquivador mArquivador = new Arquivador();

            mTempoArquivando.marqueInicio();
            long eSetor = mArquivador.guardarSetor(mAssinaturaCodificada.getData(), mArquivo);
            mTempoArquivando.marqueFim();

            long eTamanho = mAssinatura.getData().length;

            mArquivador.marcarLocal(mOLM.getL4(), eSetor, mArquivo);
            mArquivador.marcarLocal(mOLM.getL5(), eTamanho, mArquivo);

            mOLM.definirAssinatura(eSetor, eTamanho);

            mTempo_Escrita += mTempoArquivando.getIntervalo();

        } else {
            mMensageiro.errar("Houve um problema na montagem do Setor Assinatura !");
        }


        mTempoTodo.marqueFim();
        mMensageiro.mensagem("\tTempo Setor Assinatura : " + mTempoTodo.getIntervalo());
        mMensageiro.mensagem("\t\t - Documentando : " + mTempoDocumentando.getIntervalo());
        mMensageiro.mensagem("\t\t - Protegendo : " + mTempoProtegendo.getIntervalo());
        mMensageiro.mensagem("\t\t - Arquivando : " + mTempoArquivando.getIntervalo());


        if (mMensageiro.temErros()) {
            mTerminou = true;
        }


    }

    private void guardar_debug() {

        Chronos_Intervalo mTempoTodo = new Chronos_Intervalo();
        Chronos_Intervalo mTempoDocumentando = new Chronos_Intervalo();
        Chronos_Intervalo mTempoProtegendo = new Chronos_Intervalo();
        Chronos_Intervalo mTempoArquivando = new Chronos_Intervalo();


        mTempoTodo.marqueInicio();
        Empacotador mCPacotador_Codigo = new Empacotador();

        // DOCUMENTAR

        mTempoDocumentando.marqueInicio();
        String CDocumento = mCPacotador_Codigo.empacotar(ASTDebug);
        mTempoDocumentando.marqueFim();

        mMensageiro.mensagem("\tDEBUG : " + mCPacotador_Codigo.getObjetos());

        // PROTEGER

        Protetor mCSegurador_Codigo = new Protetor(mChave_Cogido);

        mTempoProtegendo.marqueInicio();
        R5Resposta mDebug = mCSegurador_Codigo.guardar(CDocumento);
        mTempoProtegendo.marqueFim();

        if (mDebug.getOk()) {

            Arquivador mArquivador = new Arquivador();

            mTempoArquivando.marqueInicio();
            long eSetor = mArquivador.guardarSetor(mDebug.getData(), mArquivo);
            mTempoArquivando.marqueFim();

            int eTamanho = mDebug.getData().length;

            mArquivador.marcarLocal(mOLM.getL6(), eSetor, mArquivo);
            mArquivador.marcarLocal(mOLM.getL7(), eTamanho, mArquivo);

            mOLM.definirCodigo(eSetor, eTamanho);

            mTempo_Escrita += mTempoArquivando.getIntervalo();

        } else {
            mMensageiro.errar("Houve um problema na montagem do Setor Debug !");
        }


        mTempoTodo.marqueFim();
        mMensageiro.mensagem("\tTempo Setor Debug : " + mTempoTodo.getIntervalo());
        mMensageiro.mensagem("\t\t - Documentando : " + mTempoDocumentando.getIntervalo());
        mMensageiro.mensagem("\t\t - Protegendo : " + mTempoProtegendo.getIntervalo());
        mMensageiro.mensagem("\t\t - Arquivando : " + mTempoArquivando.getIntervalo());


        if (mMensageiro.temErros()) {
            mTerminou = true;
        }


    }


    private void finalizar_Montagem() {

        mMensageiro.mensagem("\tOLM Titulo : " + mOLM.getTitulo());
        mMensageiro.mensagem("\tOLM Versao : " + mOLM.getVersao());

        mMensageiro.mensagem("\tOLM Setor Sigmaz : " + mOLM.getSetorSigmaz_Inicio());
        mMensageiro.mensagem("\tOLM Setor Codigo : " + mOLM.getSetorCodigo_Inicio());
        mMensageiro.mensagem("\tOLM Setor Assinatura : " + mOLM.getAssinatura_Inicio());

        mMensageiro.mensagem("\tOLM Sigmaz Tamanho : " + mOLM.getSigmaz_Tamanho());
        mMensageiro.mensagem("\tOLM Codigo Tamanho : " + mOLM.getCodigo_Tamanho());
        mMensageiro.mensagem("\tOLM Assinatura Tamanho : " + mOLM.getAssinatura_Tamanho());

        for (AST eC : CASTCabecalho) {
            if (eC.mesmoTipo("PRIVATE")) {
                mMensageiro.mensagem("\tChave Privada : " + eC.getValor());
            } else if (eC.mesmoTipo("PUBLIC")) {
                mMensageiro.mensagem("\tChave Publica : " + eC.getValor());
            } else if (eC.mesmoTipo("SHARED")) {
                mMensageiro.mensagem("\tChave Compartilhada : " + eC.getValor());
            }
        }


        mMensageiro.mensagem("\tTempo de Escrita : " + mTempo_Escrita);


        mTerminou = true;


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



                byte[] mDados_Debug = mArquivador.lerBloco(mOLMCabecalho.getSetorDebug_Inicio(), mOLMCabecalho.getDebug_Tamanho(), eArquivo);

                R5Resposta mDebug_Decifrar = mDesSegurador_Codigo.revelar(mDados_Debug);

                Empacotador mDesemPacotador_Debug = new Empacotador();

                if (mDebug_Decifrar.getOk()){
                    mDesemPacotador_Debug.desempacotar(mDebug_Decifrar.getConteudo());
                }

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

                        mOMLRun.carregar(mDesempacotador.getDesempacotado(), mDesemPacotador_Codigo.getDesempacotado(),mDesemPacotador_Debug.getDesempacotado());

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
