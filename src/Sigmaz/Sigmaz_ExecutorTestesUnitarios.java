package Sigmaz;

import Sigmaz.S08_Utilitarios.Chronos;
import Sigmaz.S05_Executor.RunTime;

import java.util.ArrayList;

public class Sigmaz_ExecutorTestesUnitarios {

    private String mExecutor;
    private boolean mDebugar;

    private Chronos mChronos;
    private String mTempoInicio;

    private boolean mMostrar_Execucao;
    private boolean mMostrar_ArvoreRunTime;
    private boolean mMostrar_ArvoreDebug;

    private boolean mTemErros;
    private ArrayList<String> mErros_Execucao;

    public Sigmaz_ExecutorTestesUnitarios() {

        mChronos = new Chronos();

        mMostrar_Execucao = false;
        mMostrar_ArvoreRunTime = false;
        mMostrar_ArvoreDebug = false;

        mTemErros = false;
        mErros_Execucao = new ArrayList<String>();

        mExecutor = "";
        mTempoInicio = "";
        mDebugar = false;

    }

    public void setMostrar_Execucao(boolean e) {
        mMostrar_Execucao = e;
    }

    public void setMostrar_ArvoreRunTime(boolean e) {
        mMostrar_ArvoreRunTime = e;
    }

    public void setMostrar_ArvoreDebug(boolean e) {
        mMostrar_ArvoreDebug = e;
    }

    public boolean temErros() {
        return mTemErros;
    }

    public ArrayList<String> getErros() {
        return mErros_Execucao;
    }

    private void mostrar_Parte01() {

        if (mMostrar_Execucao) {

            System.out.println("");
            System.out.println("################ TESTES UNITARIOS ################");
            System.out.println("");
            System.out.println("\t - Executando : " + mExecutor);

        }


    }

    private void mostrar_Parte02(RunTime RunTimeC) {
        if (mMostrar_Execucao) {

            System.out.println("\t - Tempo de Leitura : " + RunTimeC.getTempo_Leitura());
            System.out.println("\t - Tempo de Processamento : " + RunTimeC.getTempo_Processamento());
            System.out.println("\t - Tempo de Organizacao : " + RunTimeC.getTempo_Organizacao());
            System.out.println("\t - Tempo de Inicializacao : " + RunTimeC.getTempo_Inicializacao());


            System.out.println("\t - Instrucoes : " + RunTimeC.getInstrucoes());

            for (String e : RunTimeC.getMensagens()) {
                System.out.println("\t - " + e);
            }

        }
    }

    private void mostrar_Parte03(RunTime RunTimeC) {
        if (mMostrar_ArvoreRunTime) {
            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println(RunTimeC.getArvoreDeInstrucoes());
        }

        if (mMostrar_ArvoreDebug) {
            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println(RunTimeC.getArvoreDeDebug());
        }

        if (mMostrar_Execucao) {
            System.out.println("");
            System.out.println("----------------------------------------------");
        }

    }

    private void mostrar_Parte04() {
        if (mMostrar_Execucao) {

            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

        }
    }

    private void mostrar_Parte05(RunTime RunTimeC) {

        if (RunTimeC.getErros().size() > 0) {

            mTemErros = true;

            for (String Erro : RunTimeC.getErros()) {
                mErros_Execucao.add(Erro);
            }
        }

        if (mMostrar_Execucao) {

            String DF = mChronos.getData();


            System.out.println("\t - Iniciado : " + mTempoInicio);
            System.out.println("\t - Finalizado : " + DF);
            System.out.println("\t - Tempo de Execucao : " + RunTimeC.getTempo_Execucao());

            System.out.println("\t - Erros : " + RunTimeC.getErros().size());

            if (RunTimeC.getErros().size() > 0) {
                System.out.println("\n\t ERROS DE EXECUCAO : ");


                for (String Erro : RunTimeC.getErros()) {
                    System.out.println("\t\t" + Erro);
                }

                if (mDebugar) {

                    System.out.println("\n\t LOCALIZACAO DEBUG : ");

                    for (String eDebug : RunTimeC.getDebugs()) {
                        System.out.println("\t\t" + eDebug);
                    }

                }

            }


            System.out.println("");
            System.out.println("----------------------------------------------");


        }
    }

    public void executar(String eExecutor, boolean eDebugar) {

        mExecutor = eExecutor;
        mDebugar = eDebugar;

        mTemErros = false;
        mErros_Execucao.clear();

        mostrar_Parte01();

        RunTime RunTimeC = new RunTime();
        mTempoInicio = mChronos.getData();


        RunTimeC.ocultar();

        if (mMostrar_Execucao) {
            RunTimeC.mostrar();
        }


        RunTimeC.init(eExecutor, mDebugar);

        mostrar_Parte02(RunTimeC);

        if (RunTimeC.getErros().size() == 0) {

            mostrar_Parte03(RunTimeC);

            RunTimeC.runTestes();

            mostrar_Parte04();

        }


        mostrar_Parte05(RunTimeC);

    }


}
