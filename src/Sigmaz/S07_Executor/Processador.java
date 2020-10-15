package Sigmaz.S07_Executor;

import Sigmaz.S07_Executor.Processor.Registrado;

import java.util.ArrayList;

public class Processador {

    private RunTime mRunTime;
    private String mLocal;

    private String mNome;

    private boolean mR0;

    private boolean mR1;
    private boolean mR2;
    private boolean mR3;
    private boolean mR4;

    private long mR5;
    private long mR6;
    private long mR7;
    private long mR8;

    private float mR9;
    private float mR10;
    private float mR11;
    private float mR12;

    private String mR13;
    private String mR14;
    private String mR15;
    private String mR16;

    private String mR17;


    private ArrayList<String> mRegistradores;

    private ArrayList<String> mNulos;
    private ArrayList<String> mLogicos;
    private ArrayList<String> mInteiros;
    private ArrayList<String> mReais;
    private ArrayList<String> mTextos;

    private ArrayList<String> mTipos;


    private String mApontando;

    public Processador(RunTime eRunTime) {

        mRunTime = eRunTime;
        mLocal = "Processador";
        mNome = "N7";

        zerar();

        mRegistradores = new ArrayList<String>();

        mNulos = new ArrayList<String>();
        mLogicos = new ArrayList<String>();
        mInteiros = new ArrayList<String>();
        mReais = new ArrayList<String>();
        mTextos = new ArrayList<String>();
        mTipos = new ArrayList<String>();


        for (int i = 0; i < 20; i++) {
            mRegistradores.add("R" + i);
        }

        mNulos.add("R0");

        for (int i = 1; i < 5; i++) {
            mLogicos.add("R" + i);
        }
        for (int i = 5; i < 9; i++) {
            mInteiros.add("R" + i);
        }
        for (int i = 9; i < 13; i++) {
            mReais.add("R" + i);
        }

        for (int i = 13; i < 17; i++) {
            mTextos.add("R" + i);
        }

        mTipos.add("R17");

    }


    public void zerar() {


        mApontando = "R0";

        mR0 = false;

        mR1 = false;
        mR2 = false;
        mR3 = false;
        mR4 = false;

        mR5 = 0;
        mR6 = 0;
        mR7 = 0;
        mR8 = 0;

        mR9 = 0.0F;
        mR10 = 0.0F;
        mR11 = 0.0F;
        mR12 = 0.0F;

        mR13 = "";
        mR14 = "";
        mR15 = "";
        mR16 = "";

        mR17 = "";


    }

    public void apontar(String eRegistrador) {

        if (mRegistradores.contains(eRegistrador)) {
            mApontando = eRegistrador;
        } else {
            mRunTime.errar(mLocal, "registrador Desconhecido : " + eRegistrador);
        }

    }

    public String getApontando() {
        return mApontando;
    }

    public Registrado getApontandoConteudo() {

        Registrado ret = new Registrado();

        if (mApontando.contentEquals("R0")) {
            ret.atribuir(String.valueOf(mR0), "Logico");
        } else if (mApontando.contentEquals("R1")) {
            ret.atribuir(String.valueOf(mR1), "Logico");
        } else if (mApontando.contentEquals("R2")) {
            ret.atribuir(String.valueOf(mR2), "Logico");
        } else if (mApontando.contentEquals("R3")) {
            ret.atribuir(String.valueOf(mR3), "Logico");
        } else if (mApontando.contentEquals("R4")) {
            ret.atribuir(String.valueOf(mR4), "Logico");

        } else if (mApontando.contentEquals("R5")) {
            ret.atribuir(String.valueOf(mR5), "Inteiro");
        } else if (mApontando.contentEquals("R6")) {
            ret.atribuir(String.valueOf(mR6), "Inteiro");
        } else if (mApontando.contentEquals("R7")) {
            ret.atribuir(String.valueOf(mR7), "Inteiro");
        } else if (mApontando.contentEquals("R8")) {
            ret.atribuir(String.valueOf(mR8), "Inteiro");


        } else if (mApontando.contentEquals("R9")) {
            ret.atribuir(String.valueOf(mR9), "Real");
        } else if (mApontando.contentEquals("R10")) {
            ret.atribuir(String.valueOf(mR10), "Real");
        } else if (mApontando.contentEquals("R11")) {
            ret.atribuir(String.valueOf(mR11), "Real");
        } else if (mApontando.contentEquals("R12")) {
            ret.atribuir(String.valueOf(mR12), "Real");

        } else if (mApontando.contentEquals("R13")) {
            ret.atribuir(String.valueOf(mR13), "Texto");
        } else if (mApontando.contentEquals("R14")) {
            ret.atribuir(String.valueOf(mR14), "Texto");
        } else if (mApontando.contentEquals("R15")) {
            ret.atribuir(String.valueOf(mR15), "Texto");
        } else if (mApontando.contentEquals("R16")) {
            ret.atribuir(String.valueOf(mR15), "Texto");

        } else if (mApontando.contentEquals("R17")) {
            ret.atribuir(String.valueOf(mR15), "Tipo");

        }


        return ret;
    }

    public ArrayList<String> getNulos() {
        return mNulos;
    }

    public ArrayList<String> getLogicos() {
        return mLogicos;
    }

    public ArrayList<String> getInteiros() {
        return mInteiros;
    }

    public ArrayList<String> getReais() {
        return mReais;
    }

    public ArrayList<String> getRegistradores() {
        return mRegistradores;
    }

    public void aplicar(String eRegistrador, Registrado eRegistro) {

        if (mRegistradores.contains(eRegistrador)) {

            if (mNulos.contains(eRegistrador)) {

                if (eRegistro.getTipo().contentEquals("Logico")) {
                    aplicar_nulo(eRegistrador, eRegistro.getConteudo());
                } else {
                    mRunTime.errar(mLocal, "O registrador " + eRegistrador + " : Nao pode receber conteudo do tipo " + eRegistro.getTipo());
                }

            } else if (mLogicos.contains(eRegistrador)) {

                if (eRegistro.getTipo().contentEquals("Logico")) {
                    aplicar_logico(eRegistrador, eRegistro.getConteudo());
                } else {
                    mRunTime.errar(mLocal, "O registrador " + eRegistrador + " : Nao pode receber conteudo do tipo " + eRegistro.getTipo());
                }
            } else if (mInteiros.contains(eRegistrador)) {

                if (eRegistro.getTipo().contentEquals("Inteiro")) {
                    aplicar_inteiro(eRegistrador, eRegistro.getConteudo());
                } else {
                    mRunTime.errar(mLocal, "O registrador " + eRegistrador + " : Nao pode receber conteudo do tipo " + eRegistro.getTipo());
                }
            } else if (mReais.contains(eRegistrador)) {

                if (eRegistro.getTipo().contentEquals("Real")) {
                    aplicar_real(eRegistrador, eRegistro.getConteudo());
                } else {
                    mRunTime.errar(mLocal, "O registrador " + eRegistrador + " : Nao pode receber conteudo do tipo " + eRegistro.getTipo());
                }
            } else if (mTextos.contains(eRegistrador)) {

                if (eRegistro.getTipo().contentEquals("Texto")) {
                    aplicar_texto(eRegistrador, eRegistro.getConteudo());
                } else {
                    mRunTime.errar(mLocal, "O registrador " + eRegistrador + " : Nao pode receber conteudo do tipo " + eRegistro.getTipo());
                }
            } else {
                mRunTime.errar(mLocal, "O registrador Desconhecido : " + eRegistrador);
            }


        } else {

            mRunTime.errar(mLocal, "O registrador Desconhecido : " + eRegistrador);
        }

    }


    public void aplicar_nulo(String eRegistrador, String eConteudo) {

        if (mNulos.contains(eRegistrador)) {

            if (eConteudo.toUpperCase().contentEquals("TRUE") || eConteudo.toUpperCase().contentEquals("FALSE")) {

                boolean mRet = false;

                if (eConteudo.toUpperCase().contentEquals("TRUE")) {
                    mRet = true;
                }

                if (mRunTime.getErros().size() > 0) {
                    return;
                }


                if (eRegistrador.contentEquals("R0")) {
                    mR0 = mRet;
                } else {
                    mRunTime.errar(mLocal, "O registrador " + eRegistrador + " nao pode receber esse conteudo : " + eConteudo);
                }

            } else {

                mRunTime.errar(mLocal, "O registrador " + eRegistrador + " nao pode receber esse conteudo : " + eConteudo);

            }

        } else {

            mRunTime.errar(mLocal, "O registrador " + eRegistrador + " nao pode receber conteudo do tipo logico : " + eConteudo);

        }

    }

    public void aplicar_logico(String eRegistrador, String eConteudo) {

        if (mLogicos.contains(eRegistrador)) {

            if (eConteudo.toUpperCase().contentEquals("TRUE") || eConteudo.toUpperCase().contentEquals("FALSE")) {

                boolean mRet = false;

                if (eConteudo.toUpperCase().contentEquals("TRUE")) {
                    mRet = true;
                }

                if (mRunTime.getErros().size() > 0) {
                    return;
                }


                if (eRegistrador.contentEquals("R1")) {
                    mR1 = mRet;
                } else if (eRegistrador.contentEquals("R2")) {
                    mR2 = mRet;
                } else if (eRegistrador.contentEquals("R3")) {
                    mR3 = mRet;
                } else if (eRegistrador.contentEquals("R4")) {
                    mR4 = mRet;
                } else {
                    mRunTime.errar(mLocal, "O registrador " + eRegistrador + " nao pode receber esse conteudo : " + eConteudo);
                }

            } else {

                mRunTime.errar(mLocal, "O registrador " + eRegistrador + " nao pode receber esse conteudo : " + eConteudo);

            }

        } else {

            mRunTime.errar(mLocal, "O registrador " + eRegistrador + " nao pode receber conteudo do tipo logico : " + eConteudo);

        }

    }

    public void aplicar_inteiro(String eRegistrador, String eConteudo) {


        if (mInteiros.contains(eRegistrador)) {

            long mRet = 0;

            try {
                mRet = Long.parseLong(eConteudo);
            } catch (Exception e) {
                mRunTime.errar(mLocal, "O registrador " + eRegistrador + " nao pode receber esse conteudo : " + eConteudo);
            }
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            if (eConteudo.contains(".")) {
                mRunTime.errar(mLocal, "O registrador " + eRegistrador + " nao pode receber esse conteudo : " + eConteudo);
            }

            if (mRunTime.getErros().size() > 0) {
                return;
            }

            if (eRegistrador.contentEquals("R5")) {
                mR5 = mRet;
            } else if (eRegistrador.contentEquals("R6")) {
                mR6 = mRet;
            } else if (eRegistrador.contentEquals("R7")) {
                mR7 = mRet;
            } else if (eRegistrador.contentEquals("R8")) {
                mR8 = mRet;
            } else {
                mRunTime.errar(mLocal, "O registrador " + eRegistrador + " nao pode receber esse conteudo : " + eConteudo);
            }

        } else {
            mRunTime.errar(mLocal, "O registrador " + eRegistrador + " nao pode receber conteudo do tipo inteiro : " + eConteudo);
        }


    }

    public void aplicar_real(String eRegistrador, String eConteudo) {

        if (mReais.contains(eRegistrador)) {

            float mRet = 0.0F;

            try {
                mRet = Float.parseFloat(eConteudo);
            } catch (Exception e) {
                mRunTime.errar(mLocal, "O registrador " + eRegistrador + " nao pode receber esse conteudo : " + eConteudo);
            }

            if (!eConteudo.contains(".")) {
                mRunTime.errar(mLocal, "O registrador " + eRegistrador + " nao pode receber esse conteudo : " + eConteudo);
            }

            if (mRunTime.getErros().size() > 0) {
                return;
            }

            if (eRegistrador.contentEquals("R9")) {
                mR9 = mRet;
            } else if (eRegistrador.contentEquals("R10")) {
                mR10 = mRet;
            } else if (eRegistrador.contentEquals("R11")) {
                mR11 = mRet;
            } else if (eRegistrador.contentEquals("R12")) {
                mR12 = mRet;
            } else {
                mRunTime.errar(mLocal, "O registrador " + eRegistrador + " nao pode receber esse conteudo : " + eConteudo);
            }

        } else {
            mRunTime.errar(mLocal, "O registrador " + eRegistrador + " nao pode receber conteudo do tipo real : " + eConteudo);
        }
    }

    public void aplicar_texto(String eRegistrador, String eConteudo) {

        if (mTextos.contains(eRegistrador)) {


            if (eRegistrador.contentEquals("R13")) {
                mR13 = eConteudo;
            }else if (eRegistrador.contentEquals("R14")) {
                mR14 = eConteudo;
            }else if (eRegistrador.contentEquals("R15")) {
                mR15 = eConteudo;
            }else if (eRegistrador.contentEquals("R16")) {
                mR16 = eConteudo;
            } else {
                mRunTime.errar(mLocal, "O registrador " + eRegistrador + " nao pode receber esse conteudo : " + eConteudo);
            }

        } else {
            mRunTime.errar(mLocal, "O registrador " + eRegistrador + " nao pode receber conteudo do tipo texto : " + eConteudo);
        }
    }

    public void aplicar_tipo(String eRegistrador, String eConteudo) {

        if (mTipos.contains(eRegistrador)) {


            if (eRegistrador.contentEquals("R17")) {
                mR17 = eConteudo;

            } else {
                mRunTime.errar(mLocal, "O registrador " + eRegistrador + " nao pode receber esse conteudo : " + eConteudo);
            }

        } else {
            mRunTime.errar(mLocal, "O registrador " + eRegistrador + " nao pode receber conteudo do tipo TYPE : " + eConteudo);
        }
    }




    public Registrado getRegistradorApontado() {
        return getRegistrador(mApontando);
    }

    public Registrado getRegistrador(String eRegistrador) {

        Registrado mRegistrado = new Registrado();

        if (mRegistradores.contains(eRegistrador)) {


            if (mNulos.contains(eRegistrador)) {

                if (eRegistrador.contentEquals("R0")) {
                    mRegistrado.atribuir(String.valueOf(mR0), "Logico");
                } else {
                    mRunTime.errar(mLocal, "registrador Nulo Desconhecido : " + eRegistrador);
                }

            } else if (mLogicos.contains(eRegistrador)) {

                if (eRegistrador.contentEquals("R1")) {
                    mRegistrado.atribuir(String.valueOf(mR1), "Logico");
                } else if (eRegistrador.contentEquals("R2")) {
                    mRegistrado.atribuir(String.valueOf(mR2), "Logico");
                } else if (eRegistrador.contentEquals("R3")) {
                    mRegistrado.atribuir(String.valueOf(mR3), "Logico");
                } else if (eRegistrador.contentEquals("R4")) {
                    mRegistrado.atribuir(String.valueOf(mR4), "Logico");
                } else {
                    mRunTime.errar(mLocal, "registrador Logico Desconhecido : " + eRegistrador);
                }

            } else if (mInteiros.contains(eRegistrador)) {

                if (eRegistrador.contentEquals("R5")) {
                    mRegistrado.atribuir(String.valueOf(mR5), "Inteiro");
                } else if (eRegistrador.contentEquals("R6")) {
                    mRegistrado.atribuir(String.valueOf(mR6), "Inteiro");
                } else if (eRegistrador.contentEquals("R7")) {
                    mRegistrado.atribuir(String.valueOf(mR7), "Inteiro");
                } else if (eRegistrador.contentEquals("R8")) {
                    mRegistrado.atribuir(String.valueOf(mR8), "Inteiro");
                } else {
                    mRunTime.errar(mLocal, "registrador Inteiro Desconhecido : " + eRegistrador);
                }
            } else if (mReais.contains(eRegistrador)) {

                if (eRegistrador.contentEquals("R9")) {
                    mRegistrado.atribuir(String.valueOf(mR9), "Real");
                } else if (eRegistrador.contentEquals("R10")) {
                    mRegistrado.atribuir(String.valueOf(mR10), "Real");
                } else if (eRegistrador.contentEquals("R11")) {
                    mRegistrado.atribuir(String.valueOf(mR11), "Real");
                } else if (eRegistrador.contentEquals("R12")) {
                    mRegistrado.atribuir(String.valueOf(mR12), "Real");
                } else {
                    mRunTime.errar(mLocal, "registrador Real Desconhecido : " + eRegistrador);
                }
            } else if (mTextos.contains(eRegistrador)) {

                if (eRegistrador.contentEquals("R13")) {
                    mRegistrado.atribuir(String.valueOf(mR13), "Texto");
                } else if (eRegistrador.contentEquals("R14")) {
                    mRegistrado.atribuir(String.valueOf(mR14), "Texto");
                } else if (eRegistrador.contentEquals("R15")) {
                    mRegistrado.atribuir(String.valueOf(mR15), "Texto");
                } else if (eRegistrador.contentEquals("R16")) {
                    mRegistrado.atribuir(String.valueOf(mR16), "Texto");
                } else {
                    mRunTime.errar(mLocal, "registrador Real Desconhecido : " + eRegistrador);
                }

            } else if (mTipos.contains(eRegistrador)) {

                if (eRegistrador.contentEquals("R17")) {
                    mRegistrado.atribuir(String.valueOf(mR17), "Tipo");
                }




            } else {
                mRunTime.errar(mLocal, "registrador Desconhecido : " + eRegistrador);
            }


        } else {
            mRunTime.errar(mLocal, "registrador  Desconhecido : " + eRegistrador);
        }


        return mRegistrado;
    }

    public void debug() {


        System.out.println("################### PROCESSADOR ####################");
        System.out.println("");
        System.out.println("\t - PROCESSADOR : " + mNome);
        System.out.println("\t - SET : " + mApontando);

        System.out.println("");

        System.out.println("\t - R0 : " + mR0);
        System.out.println("");

        System.out.println("\t - R1 : " + mR1);
        System.out.println("\t - R2 : " + mR2);
        System.out.println("\t - R3 : " + mR3);
        System.out.println("\t - R4 : " + mR4);
        System.out.println("");

        System.out.println("\t - R5 : " + mR5);
        System.out.println("\t - R6 : " + mR6);
        System.out.println("\t - R7 : " + mR7);
        System.out.println("\t - R8 : " + mR8);
        System.out.println("");

        System.out.println("\t - R9 : " + mR9);
        System.out.println("\t - R10 : " + mR10);
        System.out.println("\t - R11 : " + mR11);
        System.out.println("\t - R12 : " + mR12);

        System.out.println("");
        System.out.println("\t - R13 : " + mR13);
        System.out.println("\t - R14 : " + mR14);
        System.out.println("\t - R15 : " + mR15);
        System.out.println("\t - R16 : " + mR16);

        System.out.println("");
        System.out.println("\t - R17 : " + mR17);

        System.out.println("");
        System.out.println("################### ########### ####################");

    }
}
