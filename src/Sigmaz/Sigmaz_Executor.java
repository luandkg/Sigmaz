package Sigmaz;

import Sigmaz.Executor.RunTime;

public class Sigmaz_Executor {

    public void executar(String eArquivo, String saida) {

        System.out.println("");
        System.out.println("################ RUNTIME ################");
        System.out.println("");
        System.out.println("\t - Source : " + eArquivo);
        System.out.println("\t - Executando : " + saida);

        RunTime RunTimeC = new RunTime();
        String DI = RunTimeC.getData();


        RunTimeC.init(saida);

        System.out.println("\t - Instrucoes : " + RunTimeC.getInstrucoes());
        System.out.println("");


        System.out.println("");
        System.out.println("----------------------------------------------");

        RunTimeC.run();

        System.out.println("");
        System.out.println("----------------------------------------------");
        System.out.println("");

        String DF = RunTimeC.getData();

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

    public void estruturador(String eArquivo, String saida) {

        System.out.println("");
        System.out.println("################ RUNTIME ################");
        System.out.println("");
        System.out.println("\t - Source : " + eArquivo);
        System.out.println("\t - Executando : " + saida);

        RunTime RunTimeC = new RunTime();
        String DI = RunTimeC.getData();


        RunTimeC.init(saida);

        System.out.println("\t - Instrucoes : " + RunTimeC.getInstrucoes());
        System.out.println("");


        System.out.println("");
        System.out.println("----------------------------------------------");

        RunTimeC.estrutura();

        System.out.println("");
        System.out.println("----------------------------------------------");
        System.out.println("");

        String DF = RunTimeC.getData();

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
