package Sigmaz.S07_Executor;

import Sigmaz.S00_Utilitarios.AST;

import java.io.File;
import java.util.ArrayList;

public class Run_Required {


    private RunTime mRunTime;
    private String mLocal;

    public Run_Required(RunTime eRunTime) {
        mRunTime = eRunTime;
        mLocal = "Run_Required";
    }

    public void requerer(AST eSigmaz) {


        // IMPORTANDO BIBLIOTECAS EXTERNAS

        ArrayList<AST> mReq = new ArrayList<AST>();


        for (AST ASTC : eSigmaz.getASTS()) {
            if (ASTC.mesmoTipo("REQUIRED")) {
                mReq.add(ASTC);
            }
        }


        ArrayList<String> mRequiscoes = new ArrayList<>();

        int i = 0;
        int o = mReq.size();

        while (i < o) {

            AST ASTReq = mReq.get(i);


            String mLocalBiblioteca = mRunTime.getLocal_Bibliotecas() + ASTReq.getNome() + ".sigmad";

            if (!mRequiscoes.contains(ASTReq.getNome())) {

              //  System.out.println("Importando :: " + mLocalBiblioteca);


                mRequiscoes.add(mLocalBiblioteca);

                File arq = new File(mLocalBiblioteca);

                if (arq.exists()) {


                    RunTime RunTimeC = new RunTime();

                    try {
                        RunTimeC.init(mLocalBiblioteca);

                        AST mBiblioteca = RunTimeC.getBranch("SIGMAZ");

                        if (ASTReq.getValor().contentEquals(RunTimeC.getShared())) {

                            for (AST ASTR : mBiblioteca.getASTS()) {

                                eSigmaz.getASTS().add(ASTR);

                            }

                        } else {
                            mRunTime.errar(mLocal, "Biblioteca " + ASTReq.getNome() + " : Problema com Chave Compartilhada !");
                        }


                    } catch (Exception e) {
                        mRunTime.errar(mLocal, "Biblioteca " + ASTReq.getNome() + " : Problema ao carregar !");
                    }

                } else {
                    mRunTime.errar(mLocal, "Biblioteca " + ASTReq.getNome() + " : Nao Encontrado !");
                }


            }


            i += 1;
        }

    }


}
