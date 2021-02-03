package Sigmaz.S05_Executor;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Contador;

import java.io.File;
import java.util.ArrayList;

public class Run_Required {


    private RunTime mRunTime;
    private String mLocal;

    public Run_Required(RunTime eRunTime) {
        mRunTime = eRunTime;
        mLocal = "Run_Required";
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

    public void requerer(AST eSigmaz) {


        // IMPORTANDO BIBLIOTECAS EXTERNAS

        ArrayList<AST> mReq = new ArrayList<AST>();


        for (AST ASTC : eSigmaz.getASTS()) {
            if (ASTC.mesmoTipo("REQUIRED")) {
                mReq.add(ASTC);
            }
        }



        // IMPORTANDO BIBLIOTECAS EXTERNAS


        ArrayList<String> mBibliotecas = new ArrayList<String>();


        for (String mBiblioteca : getBibliotecas(eSigmaz, mRunTime.getLocal_Bibliotecas())) {

            if (!mBibliotecas.contains(mBiblioteca)) {
                mBibliotecas.add(mBiblioteca);
            }

        }


        ArrayList<String> mRequisicoes_Unicamente = new ArrayList<>();

        while (mBibliotecas.size() > 0) {

            String mBiblioteca = mBibliotecas.get(0);

            if (!mRequisicoes_Unicamente.contains(mBiblioteca)) {

                mRequisicoes_Unicamente.add(mBiblioteca);

                File arq = new File(mBiblioteca);

                if (arq.exists()) {

                    RunTime RunTimeC = new RunTime();

                    try {
                        RunTimeC.init(mBiblioteca, false);


                        AST mBibliotecaAST = RunTimeC.getBranch("SIGMAZ");


                        for (String mBibliotecaSecundaria : getBibliotecas(mBibliotecaAST, mRunTime.getLocal_Bibliotecas())) {

                            if (!mBibliotecas.contains(mBibliotecaSecundaria)) {
                                mBibliotecas.add(mBibliotecaSecundaria);
                            }

                        }


                        Contador mContador = new Contador();
                        mContador.init(mBibliotecaAST);

                        for (AST ASTR : mBibliotecaAST.getASTS()) {

                            eSigmaz.getASTS().add(ASTR);

                        }


                    } catch (Exception e) {

                        mRunTime.errar(mLocal, "\t - Status : Corrompida");


                        mRunTime.errar(mLocal, "Biblioteca " + mBiblioteca + " : Problema ao carregar !");

                        for (String mLibErro : RunTimeC.getErros()) {
                            mRunTime.errar(mLocal, "Biblioteca " + mBiblioteca + " : " + mLibErro);
                        }
                    }

                } else {

                    mRunTime.errar(mLocal,"Biblioteca " + mBiblioteca + " : Nao encontrada !");

                }

            }


            mBibliotecas.remove(mBiblioteca);

        }



    }


}
