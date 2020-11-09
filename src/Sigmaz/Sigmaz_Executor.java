package Sigmaz;

import Sigmaz.S00_Utilitarios.Chronos;
import Sigmaz.S05_Executor.RunTime;

import java.util.ArrayList;

public class Sigmaz_Executor {

    private Chronos mChronos;

    private boolean mMostrar_Execucao;
    private boolean mMostrar_ArvoreRunTime;
    private boolean mMostrar_ArvoreDebug;

    private boolean mTemErros;
    private ArrayList<String> mErros_Execucao;

    public Sigmaz_Executor() {

        mChronos = new Chronos();

        mMostrar_Execucao = false;
        mMostrar_ArvoreRunTime = false;
        mMostrar_ArvoreDebug = false;

        mTemErros = false;
        mErros_Execucao = new ArrayList<String>();

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

    public void executar(String eExecutor, boolean mDebugar) {

        mTemErros = false;
        mErros_Execucao.clear();

        if (mMostrar_Execucao) {

            System.out.println("");
            System.out.println("################ RUNTIME ################");
            System.out.println("");
            System.out.println("\t - Executando : " + eExecutor);

        }


        RunTime RunTimeC = new RunTime();
        String DI = mChronos.getData();


        RunTimeC.ocultar();

        if (mMostrar_Execucao) {
            RunTimeC.mostrar();
        }


        RunTimeC.init(eExecutor, mDebugar);

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

        if (RunTimeC.getErros().size() == 0) {


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

            if (mMostrar_Execucao ) {
                System.out.println("");
                System.out.println("----------------------------------------------");
            }

            RunTimeC.run();

            if (mMostrar_Execucao) {

                System.out.println("");
                System.out.println("----------------------------------------------");
                System.out.println("");

            }


        }


        if (RunTimeC.getErros().size() > 0) {

            mTemErros = true;

            for (String Erro : RunTimeC.getErros()) {
                mErros_Execucao.add(Erro);
            }
        }

        if (mMostrar_Execucao) {

            String DF = mChronos.getData();


            System.out.println("\t - Iniciado : " + DI);
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


    public void estruturador(String eArquivo, String saida) {

        System.out.println("");
        System.out.println("################ RUNTIME ################");
        System.out.println("");
        System.out.println("\t - Source : " + eArquivo);
        System.out.println("\t - Executando : " + saida);

        RunTime RunTimeC = new RunTime();
        String DI = mChronos.getData();


        RunTimeC.init(saida, false);

        System.out.println("\t - Instrucoes : " + RunTimeC.getInstrucoes());
        System.out.println("");


        System.out.println("");
        System.out.println("----------------------------------------------");

        RunTimeC.estrutura();

        System.out.println("");
        System.out.println("----------------------------------------------");
        System.out.println("");

        String DF = mChronos.getData();

        System.out.println("\t - Iniciado : " + DI);
        System.out.println("\t - Finalizado : " + DF);

        System.out.println("\t - Erros : " + RunTimeC.getErros().size());

        if (RunTimeC.getErros().size() > 0) {
            System.out.println("\n\t ERROS DE EXECUCAO : ");

            for (String Erro : RunTimeC.getErros()) {
                System.out.println("\t\t" + Erro);
            }
        }

        System.out.println("");
        System.out.println("----------------------------------------------");


    }

}
