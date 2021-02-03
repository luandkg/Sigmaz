package Sigmaz.S02_PosProcessamento.Processadores;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Contador;
import Sigmaz.S02_PosProcessamento.PosProcessador;
import Sigmaz.S05_Executor.RunTime;

import java.io.File;
import java.util.ArrayList;

public class Requeridor {


    private PosProcessador mPosProcessador;
    private ArrayList<AST> mRequisicoes;

    public Requeridor(PosProcessador ePosProcessador) {

        mPosProcessador = ePosProcessador;
        mRequisicoes = new ArrayList<AST>();

    }

    public ArrayList<AST> getRequisicoes() {
        return mRequisicoes;
    }


    public ArrayList<String> getBibliotecas(AST eAST, String mLocalLibs) {

        ArrayList<String> mBibliotecas = new ArrayList<String>();

        for (AST ASTC : eAST.getASTS()) {
            if (ASTC.mesmoTipo("REQUIRED")) {

                String mReq = mLocalLibs + ASTC.getNome() + ".sigmad";

                if (!mBibliotecas.contains(mReq)) {
                    mBibliotecas.add(mReq);
                }
            }

        }

        return mBibliotecas;
    }


    public void init(ArrayList<AST> mTodos, String mLocalLibs) {

        if (mPosProcessador.getDebug_Requeridor()) {
            mPosProcessador.mensagem("");
            mPosProcessador.mensagem(" ------------------ FASE REQUERIDOR ----------------------- ");
            mPosProcessador.mensagem("");
        }


        mRequisicoes.clear();

        // IMPORTANDO BIBLIOTECAS EXTERNAS


        ArrayList<String> mBibliotecas = new ArrayList<String>();


        for (AST ASTCGlobal : mTodos) {
            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                for (String mBiblioteca : getBibliotecas(ASTCGlobal, mLocalLibs)) {

                    if (!mBibliotecas.contains(mBiblioteca)) {
                        mBibliotecas.add(mBiblioteca);
                    }

                }

            }
        }

        ArrayList<String> mRequisicoes_Unicamente = new ArrayList<>();

        while (mBibliotecas.size() > 0) {

            String mBiblioteca = mBibliotecas.get(0);

            if (!mRequisicoes_Unicamente.contains(mBiblioteca)) {
                mPosProcessador.mensagem("Carregar Biblioteca : " + mBiblioteca);

                mRequisicoes_Unicamente.add(mBiblioteca);

                if (mPosProcessador.getDebug_Requeridor()) {
                    mPosProcessador.mensagem("Biblioteca Externa  -> " + mBiblioteca);

                }


                File arq = new File(mBiblioteca);

                if (arq.exists()) {

                    RunTime RunTimeC = new RunTime();

                    try {
                        RunTimeC.init(mBiblioteca, false);


                        mRequisicoes.add(RunTimeC.getBranch("SIGMAZ"));

                        if (mPosProcessador.getDebug_Requeridor()) {

                            mPosProcessador.mensagem("\t - Encontrada : Sim");
                            mPosProcessador.mensagem("\t - Status : OK");
                            mPosProcessador.mensagem("\t - Chave : " + RunTimeC.getShared());

                        }


                        AST mBibliotecaAST = RunTimeC.getBranch("SIGMAZ");


                        for (String mBibliotecaSecundaria : getBibliotecas(mBibliotecaAST, mLocalLibs)) {

                            if (!mBibliotecas.contains(mBibliotecaSecundaria)) {
                                mBibliotecas.add(mBibliotecaSecundaria);
                            }

                        }


                        Contador mContador = new Contador();
                        mContador.init(mBibliotecaAST);


                        mBibliotecaAST.setValor(RunTimeC.getShared());

                        if (mPosProcessador.getDebug_Requeridor()) {

                            mPosProcessador.mensagem("");

                            mPosProcessador.mensagem("\t - Actions : " + mContador.getActions());
                            mPosProcessador.mensagem("\t - Functions : " + mContador.getFunctions());
                            mPosProcessador.mensagem("\t - Autos : " + mContador.getAutos());
                            mPosProcessador.mensagem("\t - Functors : " + mContador.getFunctors());
                            mPosProcessador.mensagem("\t - Casts : " + mContador.getCasts());
                            mPosProcessador.mensagem("\t - Stages : " + mContador.getStages());
                            mPosProcessador.mensagem("\t - Types : " + mContador.getTypes());
                            mPosProcessador.mensagem("\t - Structs : " + mContador.getStructs());
                            mPosProcessador.mensagem("\t - Models : " + mContador.getModels());
                            mPosProcessador.mensagem("\t - Externals : " + mContador.getExternals());
                            mPosProcessador.mensagem("\t - Packages : " + mContador.getPackages());

                        }


                    } catch (Exception e) {

                        if (mPosProcessador.getDebug_Requeridor()) {
                            mPosProcessador.mensagem("\t - Status : Corrompida");
                        }

                        mPosProcessador.errar("Biblioteca " + mBiblioteca + " : Problema ao carregar !");

                        for (String mLibErro : RunTimeC.getErros()) {
                            mPosProcessador.errar("Biblioteca " + mBiblioteca + " : " + mLibErro);
                        }
                    }

                } else {
                    if (mPosProcessador.getDebug_Requeridor()) {
                        mPosProcessador.mensagem("\t - Encontrada : Nao");
                    }
                    mPosProcessador.errar("Biblioteca " + mBiblioteca + " : Nao encontrada !");

                }

            }


            mBibliotecas.remove(mBiblioteca);

        }


    }
}
