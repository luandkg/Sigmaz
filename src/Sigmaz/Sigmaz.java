package Sigmaz;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Utils.AST;
import Sigmaz.Executor.RunTime;
import Sigmaz.Compilador.Compiler;
import Sigmaz.Utils.Documentador;
import Sigmaz.Utils.Erro;
import Sigmaz.Utils.GrupoDeErro;

import java.util.ArrayList;

// COMPILADOR OLIMPUS

// DESENVOLVEDOR : LUAN FREITAS

import java.util.Calendar;


public class Sigmaz {

    public void init(String eArquivo, String saida) {




        System.out.println("################ SIGMAZ ################");
        System.out.println("");
        System.out.println(" - AUTOR	: LUAN FREITAS");
        System.out.println(" - VERSAO   : 1.0");
        System.out.println(" - STATUS  	: ALPHA");
		System.out.println(" - INICIO  	: 2020.06.12");

        System.out.println("");


        Compiler CompilerC = new Compiler();
        CompilerC.init(eArquivo);

        System.out.println("################# LEXER ##################");
        System.out.println("");
        System.out.println("\t Iniciado : " + getDate().toString());
        System.out.println("\t - Chars : " + CompilerC.getIChars());
        System.out.println("\t - Tokens : " + CompilerC.getITokens());
        System.out.println("\t - Erros : " + CompilerC.getErros_Lexer().size());
        System.out.println("\t Finalizado : " + getDate().toString());
        System.out.println("");


        System.out.println("############### COMPILADOR ###############");
        System.out.println("");
        System.out.println("\t Iniciado : " + getDate().toString());
        System.out.println("\t - Instrucoes : " + CompilerC.Instrucoes());
        System.out.println("\t - Erros : " + CompilerC.getErros_Compiler().size());
        System.out.println("\t - Requisitados : ");

        for (String Req : CompilerC.getRequisitados()) {
            System.out.println("\t\t - " + Req);
        }

        System.out.println("\t Finalizado : " + getDate().toString());


        if (CompilerC.getErros_Lexer().size() > 0) {

            System.out.println("\n\t ERROS DE LEXICOGRAFICA : ");

            for (GrupoDeErro eGE : CompilerC.getErros_Lexer()) {
                System.out.println("\t\t" + eGE.getArquivo());
                for (Erro eErro : eGE.getErros()) {
                    System.out.println("\t\t    ->> " + eErro.getPosicao() + " : " + eErro.getMensagem());
                }
            }

        }

        if (CompilerC.getErros_Compiler().size() > 0) {

            System.out.println("");
            System.out.println("################ AST - COM DEFEITOS ################");
            System.out.println("");

            Documentador DC = new Documentador();
            System.out.println(CompilerC.getArvoreDeInstrucoes());


            System.out.println("\n\t ERROS DE COMPILACAO : ");

            for (GrupoDeErro eGE : CompilerC.getErros_Compiler()) {
                System.out.println("\t\t" + eGE.getArquivo());
                for (Erro eErro : eGE.getErros()) {
                    System.out.println("\t\t    ->> " + eErro.getMensagem());
                }
            }
        }


        if (CompilerC.getErros_Lexer().size() == 0 && CompilerC.getErros_Compiler().size() == 0) {


            System.out.println("");
            System.out.println("################ ANALISE ################");
            System.out.println("");


            Analisador AnaliseC = new Analisador();
            String AI = getDate().toString();
            AnaliseC.init(CompilerC.getASTS());
            String AF = getDate().toString();


            System.out.println("\t - Iniciado : " + AI);
            System.out.println("\t - Finalizado : " + AF);

            System.out.println("\t - Erros : " + AnaliseC.getErros().size());



            if (AnaliseC.getErros().size() > 0) {
                System.out.println("\n\t ERROS DE ANALISE : ");

                for (String Erro : AnaliseC.getErros()) {
                    System.out.println("\t\t" + Erro);
                }

                System.out.println("");
                System.out.println("################ AST - COM DEFEITOS ################");
                System.out.println("");

                Documentador DC = new Documentador();
             //   System.out.println(CompilerC.getArvoreDeInstrucoes());

            } else {


                System.out.println("");
                System.out.println("################ OBJETO ################");
                System.out.println("");


               // String instrucoes = CompilerC.ArvoreDeInstrucoes();


                CompilerC.Compilar(saida);

                Documentador DC2 = new Documentador();

                System.out.println("\t Iniciado : " + getDate().toString());
                System.out.println("\t - Tamanho : " + DC2.tamanhoObjeto(saida));
                System.out.println("\t Finalizado : " + getDate().toString());

                System.out.println("");



                System.out.println("");
                System.out.println("################ RUNTIME ################");
                System.out.println("");

                RunTime RunTimeC = new RunTime();
                String DI = getDate().toString();


                RunTimeC.init(saida);


                System.out.println("");


                System.out.println( RunTimeC.getArvoreDeInstrucoes());

                System.out.println("");
                System.out.println("----------------------------------------------");
                System.out.println("");


                RunTimeC.run();




                System.out.println("");
                System.out.println("----------------------------------------------");
                System.out.println("");

                String DF = getDate().toString();

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


        }else{




        }

    }

    public String getDate() {

        Calendar c = Calendar.getInstance();

        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH) + 1;
        int ano = c.get(Calendar.YEAR);

        int hora = c.get(Calendar.HOUR);
        int minutos = c.get(Calendar.MINUTE);
        int segundos = c.get(Calendar.SECOND);

        return dia + "/" + mes + "/" + ano + " " + hora + ":" + minutos + ":" + segundos;

    }

}
