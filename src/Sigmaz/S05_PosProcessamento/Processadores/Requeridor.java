package Sigmaz.S05_PosProcessamento.Processadores;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Contador;
import Sigmaz.S05_PosProcessamento.PosProcessador;
import Sigmaz.S07_Executor.RunTime;

import java.io.File;
import java.util.ArrayList;

public class Requeridor {


    private PosProcessador mPosProcessador;
    private ArrayList<AST> mRequisicoes;

    public Requeridor(PosProcessador ePosProcessador) {

        mPosProcessador = ePosProcessador;
        mRequisicoes = new ArrayList<AST>();

    }

    public ArrayList<AST> getRequisicoes(){ return mRequisicoes;}


    public void init(ArrayList<AST> mTodos, String mLocalLibs) {

        mPosProcessador.mensagem("");
        mPosProcessador.mensagem(" ------------------ FASE REQUERIDOR ----------------------- ");
        mPosProcessador.mensagem("");

        mRequisicoes.clear();

        // IMPORTANDO BIBLIOTECAS EXTERNAS

        ArrayList<String> mRequisicoes_Unicamente = new ArrayList<>();


        for (AST ASTCGlobal : mTodos) {
            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {
                for (AST ASTC : ASTCGlobal.getASTS()) {
                    if (ASTC.mesmoTipo("REQUIRED")) {

                        String mReq = mLocalLibs + ASTC.getNome() + ".sigmad";

                        if (!mRequisicoes_Unicamente.contains(mReq)) {

                            mRequisicoes_Unicamente.add(mReq);
                            mPosProcessador.mensagem("Biblioteca Externa " + ASTC.getNome() + " -> " + mReq);

                            File arq = new File(mReq);

                            if (arq.exists()) {

                                RunTime RunTimeC = new RunTime();

                                try {
                                    RunTimeC.init(mReq);


                                    mRequisicoes.add(RunTimeC.getBranch("SIGMAZ"));

                                    mPosProcessador.mensagem("\t - Encontrada : Sim");
                                    mPosProcessador.mensagem("\t - Status : OK");


                                    Contador mContador = new Contador();
                                    mContador.init(RunTimeC.getBranch("SIGMAZ"));

                                    mPosProcessador.mensagem("");

                                    mPosProcessador.mensagem("\t - Actions : " +mContador.getActions());
                                    mPosProcessador.mensagem("\t - Functions : " +mContador.getFunctions());
                                    mPosProcessador.mensagem("\t - Autos : " +mContador.getAutos());
                                    mPosProcessador.mensagem("\t - Functors : " +mContador.getFunctors());
                                    mPosProcessador.mensagem("\t - Casts : " +mContador.getCasts());
                                    mPosProcessador.mensagem("\t - Stages : " +mContador.getStages());
                                    mPosProcessador.mensagem("\t - Types : " +mContador.getTypes());
                                    mPosProcessador.mensagem("\t - Structs : " +mContador.getStructs());
                                    mPosProcessador.mensagem("\t - Models : " +mContador.getModels());
                                    mPosProcessador.mensagem("\t - Externals : " +mContador.getExternals());
                                    mPosProcessador.mensagem("\t - Packages : " +mContador.getPackages());



                                } catch (Exception e) {

                                    mPosProcessador.mensagem("\t - Status : Corrompida");
                                    mPosProcessador.errar("Biblioteca " + mReq + " : Problema ao carregar !");
                                }

                            } else {
                                mPosProcessador.mensagem("\t - Encontrada : Nao");
                                mPosProcessador.errar("Biblioteca " + mReq + " : Nao encontrada !");

                            }

                        }


                    }

                }

            }
        }


    }
}
