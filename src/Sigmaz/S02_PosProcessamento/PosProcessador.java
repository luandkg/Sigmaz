package Sigmaz.S02_PosProcessamento;

import java.util.ArrayList;

import Sigmaz.S08_Utilitarios.Mensageiro;
import Sigmaz.S08_Utilitarios.OrganizadorDeProcessos;
import Sigmaz.S08_Utilitarios.ProcessoCallback;
import Sigmaz.S02_PosProcessamento.Processadores.*;
import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S05_Executor.Debuggers.Simplificador;

public class PosProcessador {

    private ArrayList<AST> mASTS;
    private ArrayList<AST> mRequisicoes;

    private ArrayList<AST> mPacotes;

    private Mensageiro mMensageiro;

    private boolean mDebug_Requisidor;
    private boolean mDebug_Alocador;
    private boolean mDebug_Modelador;
    private boolean mDebug_Estagiador;
    private boolean mDebug_Tipador;
    private boolean mDebug_Valorador;
    private boolean mDebug_Cast;
    private boolean mDebug_Unificador;
    private boolean mDebug_Heranca;
    private boolean mDebug_Referenciador;
    private boolean mDebug_Argumentador;
    private boolean mDebug_Opcionador;
    private boolean mDebug_Estruturador;
    private boolean mDebug_Unicidade;

    private String mLocalLibs;

    private boolean mTerminou;

    private Simplificador mSimplificador;
    private OrganizadorDeProcessos mProcessador;

    public PosProcessador() {

        mASTS = new ArrayList<>();

        mMensageiro = new Mensageiro();

        mPacotes = new ArrayList<AST>();
        mRequisicoes = new ArrayList<AST>();

        mSimplificador = new Simplificador();

        mDebug_Requisidor = true;
        mDebug_Alocador = true;
        mDebug_Modelador = true;
        mDebug_Estagiador = true;
        mDebug_Tipador = true;
        mDebug_Valorador = true;
        mDebug_Cast = true;
        mDebug_Unificador = true;
        mDebug_Heranca = true;
        mDebug_Referenciador = true;
        mDebug_Argumentador = true;
        mDebug_Opcionador = true;
        mDebug_Estruturador = true;
        mDebug_Unicidade = true;

        mProcessador = new OrganizadorDeProcessos();

    }

    public Simplificador getSimplificador() {
        return mSimplificador;
    }

    public void setDebug_Requisidor(boolean e) {
        mDebug_Requisidor = e;
    }

    public void setDebug_Alocador(boolean e) {
        mDebug_Alocador = e;
    }


    public void setDebug_Modelador(boolean e) {
        mDebug_Modelador = e;
    }


    public void setDebug_Estagiador(boolean e) {
        mDebug_Estagiador = e;
    }

    public void setDebug_Tipador(boolean e) {
        mDebug_Tipador = e;
    }

    public void setDebug_Valorador(boolean e) {
        mDebug_Valorador = e;
    }

    public void setDebug_Cast(boolean e) {
        mDebug_Cast = e;
    }

    public void setDebug_Unificador(boolean e) {
        mDebug_Unificador = e;
    }

    public void setDebug_Heranca(boolean e) {
        mDebug_Heranca = e;
    }

    public void setDebug_Referenciador(boolean e) {
        mDebug_Referenciador = e;
    }

    public void setDebug_Argumentador(boolean e) {
        mDebug_Argumentador = e;
    }

    public void setDebug_Opcionador(boolean e) {
        mDebug_Opcionador = e;
    }

    public void setDebug_Estruturador(boolean e) {
        mDebug_Estruturador = e;
    }

    public void setDebug_Unicidade(boolean e) {
        mDebug_Unicidade = e;
    }


    public boolean getDebug_Requeridor() {
        return mDebug_Requisidor;
    }

    public boolean getDebug_Alocador() {
        return mDebug_Alocador;
    }

    public boolean getDebug_Modelador() {
        return mDebug_Modelador;
    }

    public boolean getDebug_Estagiador() {
        return mDebug_Estagiador;
    }

    public boolean getDebug_Tipador() {
        return mDebug_Tipador;
    }

    public boolean getDebug_Valorador() {
        return mDebug_Valorador;
    }

    public boolean getDebug_Cast() {
        return mDebug_Cast;
    }

    public boolean getDebug_Unificador() {
        return mDebug_Unificador;
    }

    public boolean getDebug_Heranca() {
        return mDebug_Heranca;
    }

    public boolean getDebug_Referenciador() {
        return mDebug_Referenciador;
    }

    public boolean getDebug_Argumentador() {
        return mDebug_Argumentador;
    }

    public boolean getDebug_Opcionador() {
        return mDebug_Opcionador;
    }


    public boolean getDebug_Estruturador() {
        return mDebug_Estruturador;
    }

    public boolean getDebug_Unicidade() {
        return mDebug_Unicidade;
    }


    public ArrayList<AST> getRequisicoes() {
        return mRequisicoes;
    }


    public void init(ArrayList<AST> eASTs, String eLocalLibs) {

        initPassos(eASTs, eLocalLibs);


        while (!getTerminou()) {
            continuar();
        }


    }


    public void initPassos(ArrayList<AST> eASTs, String eLocalLibs) {

        mTerminou = false;


        mASTS = eASTs;
        mLocalLibs = eLocalLibs;
        mPacotes.clear();
        mMensageiro.limpar();
        mRequisicoes.clear();


        PosProcessador mPosProcessador = this;

        mProcessador.implementeStatus("Caller");

        mProcessador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {
                Caller mCaller = new Caller(mPosProcessador);
                mCaller.init(mASTS);
            }
        });

        mProcessador.implementeStatus("Requeridor");

        mProcessador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {
                if (tudoOK()) {
                    Requeridor mRequeridor = new Requeridor(mPosProcessador);
                    mRequeridor.init(mASTS, mLocalLibs);
                    mRequisicoes = mRequeridor.getRequisicoes();
                }
            }
        });

        mProcessador.implementeStatus("Alocador");

        mProcessador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {

                if (tudoOK()) {
                    Alocador mAlocador = new Alocador(mPosProcessador);
                    mAlocador.init(mASTS);
                }
            }
        });

        mProcessador.implementeStatus("Castificador");

        mProcessador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {

                if (tudoOK()) {
                    Castificador mCastificador = new Castificador(mPosProcessador);
                    mCastificador.init(mASTS);
                }
            }
        });


        mProcessador.implementeStatus("Unificador");

        mProcessador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {

                if (tudoOK()) {

                    Unificador mUnificador = new Unificador(mPosProcessador);
                    mUnificador.init(mASTS);
                }
            }
        });

        mProcessador.implementeStatus("Heranca");

        mProcessador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {

                if (tudoOK()) {
                    Heranca mHeranca = new Heranca(mPosProcessador);
                    mHeranca.init(mASTS);
                }

            }
        });

        mProcessador.implementeStatus("Modelador");


        mProcessador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {

                if (tudoOK()) {

                    Modelador mModelador = new Modelador(mPosProcessador);
                    mModelador.init(mASTS);
                }
            }
        });

        mProcessador.implementeStatus("Estagiador");

        mProcessador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {

                if (tudoOK()) {
                    Estagiador mEstagiador = new Estagiador(mPosProcessador);
                    mEstagiador.init(mASTS);
                }
            }
        });

        mProcessador.implementeStatus("Tipador");

        mProcessador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {

                if (tudoOK()) {
                    Tipador mTipador = new Tipador(mPosProcessador);
                    mTipador.init(mASTS);
                }
            }
        });

        mProcessador.implementeStatus("Referenciador");

        mProcessador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {

                if (tudoOK()) {
                    Referenciador mReferenciador = new Referenciador(mPosProcessador);
                    mReferenciador.init(mASTS);
                }
            }
        });

        mProcessador.implementeStatus("Argumentador");

        mProcessador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {
                if (tudoOK()) {
                    Argumentador mArgumentador = new Argumentador(mPosProcessador);
                    mArgumentador.init(mASTS);
                }
            }
        });

        mProcessador.implementeStatus("Opcionador");

        mProcessador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {
                if (tudoOK()) {
                    Opcionador mOpcionador = new Opcionador(mPosProcessador);
                    mOpcionador.init(mASTS);

                }

            }
        });

        mProcessador.implementeStatus("Estruturador");

        mProcessador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {

                if (tudoOK()) {
                    Estruturador mEstruturador = new Estruturador(mPosProcessador);
                    mEstruturador.init(mASTS);

                }
            }
        });

        mProcessador.implementeStatus("Uniciade");


        mProcessador.implemente(new ProcessoCallback() {
            @Override
            public void processar() {

                if (tudoOK()) {
                    mMensageiro.mensagem("Quantos Erros 1 :: " + mPosProcessador.getErros().size());

                    Unicidade mUnicidade = new Unicidade(mPosProcessador);
                    mUnicidade.init(mASTS);
                    mMensageiro.mensagem("Quantos Erros 2 :: " + mPosProcessador.getErros().size());

                }
            }
        });



    }

    public void continuar() {

        if (mMensageiro.temErros()) {
            mTerminou = true;
        } else {
            if (mProcessador.getTerminou()) {
                mTerminou = true;
            } else {
                mProcessador.processe();
            }
        }

    }

    public String getFase() {
        return mProcessador.getStatus();
    }

    public boolean getTerminou() {
        return mTerminou;
    }


    public boolean temErros() {
        return mMensageiro.temErros();
    }

    public boolean tudoOK() {
        return mMensageiro.tudoOK();
    }

    public Mensageiro getMensageiro() {
        return mMensageiro;
    }


    public ArrayList<String> getErros() {
        return mMensageiro.getErros();
    }

    public void mensagem(String eMensagem) {
        mMensageiro.mensagem(eMensagem);
    }

    public void errar(String eErro) {
        mMensageiro.errar(eErro);
    }

    public ArrayList<String> getMensagens() {
        return mMensageiro.getMensagens();
    }




}
