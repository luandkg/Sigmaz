package Sigmaz;

import Sigmaz.Analisador.Analisador;
import Sigmaz.PosProcessamento.PosProcessador;
import Sigmaz.Utils.AST;
import Sigmaz.Executor.RunTime;
import Sigmaz.Compilador.Compiler;
import Sigmaz.Utils.Documentador;
import Sigmaz.Utils.Erro;
import Sigmaz.Utils.GrupoDeErro;

import java.io.File;
import java.util.ArrayList;

// COMPILADOR OLIMPUS

// DESENVOLVEDOR : LUAN FREITAS

import java.util.Calendar;


public class Sigmaz {

    private boolean geral(String eArquivo, String saida,boolean mostrarAST) {

        boolean ret = false;

        File arq = new File(saida);
        String mLocal = arq.getParent() + "/";


        System.out.println("################# SIGMAZ #################");
        System.out.println("");
        System.out.println(" - AUTOR	: LUAN FREITAS");
        System.out.println(" - VERSAO   : 1.0");
        System.out.println(" - STATUS  	: ALPHA");
        System.out.println(" - INICIO  	: 2020.06.12");

        System.out.println("");


        Compiler CompilerC = new Compiler();
        CompilerC.init(eArquivo);

        System.out.println("############## PROCESSAMENTO ###############");
        System.out.println("");
        System.out.println(CompilerC.getProcessamento());

        System.out.println("################# LEXER ##################");
        System.out.println("");
        System.out.println("\t Iniciado : " + CompilerC.getData().toString());
        System.out.println("\t - Arquivo : " + eArquivo);
        System.out.println("\t - Chars : " + CompilerC.getIChars());
        System.out.println("\t - Tokens : " + CompilerC.getITokens());
        System.out.println("\t - Erros : " + CompilerC.getErros_Lexer().size());
        System.out.println("\t Finalizado : " + CompilerC.getData().toString());
        System.out.println("");


        System.out.println("############### COMPILADOR ###############");
        System.out.println("");
        System.out.println("\t Iniciado : " + CompilerC.getData().toString());
        System.out.println("\t - Instrucoes : " + CompilerC.getInstrucoes());
        System.out.println("\t - Erros : " + CompilerC.getErros_Compiler().size());
        System.out.println("\t - Requisitados : ");

        for (String Req : CompilerC.getRequisitados()) {
            System.out.println("\t\t - " + Req);
        }

        System.out.println("\t Finalizado : " + CompilerC.getData().toString());


        if (CompilerC.getErros_Lexer().size() > 0) {

            System.out.println("\n\t ERROS DE LEXICOGRAFICA : ");

            for (GrupoDeErro eGE : CompilerC.getErros_Lexer()) {
                System.out.println("\t\t" + eGE.getArquivo());
                for (Erro eErro : eGE.getErros()) {
                    System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
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
                    System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                }
            }
        }


        if (CompilerC.getErros_Lexer().size() == 0 && CompilerC.getErros_Compiler().size() == 0) {


            System.out.println("");
            System.out.println("################ ANALISE ################");
            System.out.println("");


            Analisador AnaliseC = new Analisador();
            String AI = CompilerC.getData().toString();

if (mostrarAST){
     System.out.println(CompilerC.getArvoreDeInstrucoes());
}


            AnaliseC.init(CompilerC.getASTS(), mLocal);
            String AF = CompilerC.getData().toString();


            System.out.println("\t - Iniciado : " + AI);
            System.out.println("\t - Finalizado : " + AF);


            System.out.println("\t - Erros : " + AnaliseC.getErros().size());

            AnaliseC.MostrarMensagens();


            if (AnaliseC.getErros().size() > 0) {
                System.out.println("\n\t ERROS DE ANALISE : ");

                for (String Erro : AnaliseC.getErros()) {
                    System.out.println("\t\t" + Erro);
                }


                //  System.out.println("");
                // System.out.println("################ AST - COM DEFEITOS ################");
                //  System.out.println("");

                //  Documentador DC = new Documentador();
                //  System.out.println(CompilerC.getArvoreDeInstrucoes());

            } else {


                System.out.println("");
                System.out.println("################ POS-PROCESSAMENTO ################");
                System.out.println("");

                String AI_PosProcessamento = CompilerC.getData().toString();

                PosProcessador PosProcessadorC = new PosProcessador();

                PosProcessadorC.init(CompilerC.getASTS(), mLocal);

                String AF_PosProcessamento = CompilerC.getData().toString();

                System.out.println("\t - Iniciado : " + AI_PosProcessamento);
                System.out.println("\t - Finalizado : " + AF_PosProcessamento);


                PosProcessadorC.MostrarMensagens();


                if (PosProcessadorC.getErros().size() > 0) {
                    System.out.println("\n\t ERROS DE POS-PROCESSAMENTO : ");


                    for (String Erro : PosProcessadorC.getErros()) {
                        System.out.println("\t\t" + Erro);
                    }
                }else{


                    System.out.println("");
                    System.out.println("################ OBJETO ################");
                    System.out.println("");


                    // String instrucoes = CompilerC.ArvoreDeInstrucoes();


                    CompilerC.Compilar(saida);

                    Documentador DC2 = new Documentador();

                    System.out.println("\t Iniciado : " + CompilerC.getData().toString());
                    System.out.println("\t - Tamanho : " + DC2.tamanhoObjeto(saida));
                 //   System.out.println("\t - Saida : " + saida);
                    System.out.println("\t Finalizado : " + CompilerC.getData().toString());

                    System.out.println("");


                    ret = true;

                }





            }


        } else {


        }

        return ret;
    }


    public void init(String eArquivo, String saida,boolean mostrarAST) {


        if (geral(eArquivo, saida, mostrarAST)) {


            System.out.println("");
            System.out.println("################ RUNTIME ################");
            System.out.println("");
            System.out.println("\t - Executando : " + saida);
            System.out.println("");

            RunTime RunTimeC = new RunTime();
            String DI = RunTimeC.getData().toString();


            RunTimeC.init(saida);

            System.out.println("\t - Instrucoes : " + RunTimeC.getInstrucoes());
            System.out.println("");


         //   System.out.println(RunTimeC.getArvoreDeInstrucoes());

            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

            RunTimeC.run();

            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

            String DF = RunTimeC.getData().toString();

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


    public void estrutural(String eArquivo, String saida) {


        if (geral(eArquivo, saida,false)) {


            System.out.println("");
            System.out.println("################ RUNTIME ################");
            System.out.println("");
            System.out.println("\t - Executando : " + saida);
            System.out.println("");

            RunTime RunTimeC = new RunTime();
            String DI = RunTimeC.getData().toString();


            RunTimeC.init(saida);

            System.out.println("\t - Instrucoes : " + RunTimeC.getInstrucoes());
            System.out.println("");


            System.out.println(RunTimeC.getArvoreDeInstrucoes());

            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

            RunTimeC.estrutura();


            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

            String DF = RunTimeC.getData().toString();

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

    public void interno(String eArquivo, String saida, String eLocal) {


        if (geral(eArquivo, saida,false)) {


            System.out.println("");
            System.out.println("################ RUNTIME ################");
            System.out.println("");
            System.out.println("\t - Executando : " + saida);
            System.out.println("");

            RunTime RunTimeC = new RunTime();
            String DI = RunTimeC.getData().toString();


            RunTimeC.init(saida);

            System.out.println("\t - Instrucoes : " + RunTimeC.getInstrucoes());
            System.out.println("");


            System.out.println(RunTimeC.getArvoreDeInstrucoes());

            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

            RunTimeC.interno(eLocal);


            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

            String DF = RunTimeC.getData().toString();

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


    public void uml(String eArquivo, String saida, String eGrafico) {

        if (geral(eArquivo, saida,false)) {


            System.out.println("");
            System.out.println("################ UML ################");
            System.out.println("");
            System.out.println("\t - Executando : " + saida);
            System.out.println("");

            RunTime RunTimeC = new RunTime();
            String DI = RunTimeC.getData().toString();


            RunTimeC.init(saida);

            System.out.println("\t - Instrucoes : " + RunTimeC.getInstrucoes());
            System.out.println("");


            System.out.println(RunTimeC.getArvoreDeInstrucoes());

            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

            RunTimeC.uml(eGrafico);

            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

            String DF = RunTimeC.getData().toString();

            System.out.println("\t - Iniciado : " + DI);
            System.out.println("\t - Finalizado : " + DF);


            System.out.println("");
            System.out.println("----------------------------------------------");


        }

    }

    public void intellisense(String eArquivo, String saida, String eGrafico) {

        if (geral(eArquivo, saida,false)) {


            System.out.println("");
            System.out.println("################ INTELISENSE ################");
            System.out.println("");
            System.out.println("\t - Executando : " + saida);
            System.out.println("");

            RunTime RunTimeC = new RunTime();
            String DI = RunTimeC.getData().toString();


            RunTimeC.init(saida);


            System.out.println(RunTimeC.getArvoreDeInstrucoes());

            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

            RunTimeC.intellisense(eGrafico);

            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

            String DF = RunTimeC.getData().toString();

            System.out.println("\t - Iniciado : " + DI);
            System.out.println("\t - Finalizado : " + DF);


            System.out.println("");
            System.out.println("----------------------------------------------");


        }

    }

    public void initDependencia(String eArquivo) {


        Compiler CompilerC = new Compiler();
        CompilerC.init(eArquivo);


        System.out.println("");
        System.out.println("############## DEPENDENCIAS ###############");
        System.out.println("");

        if (CompilerC.getErros_Lexer().size() == 0 && CompilerC.getErros_Compiler().size() == 0) {


            ArrayList<String> mDep = new ArrayList<>();


            mDep.add(eArquivo);


            for (String Req : CompilerC.getRequisitados()) {
                Req = Req.replace("\\", "/");
                if (!mDep.contains(Req)) {
                    mDep.add(Req);
                }
            }


            for (String Req : mDep) {
                System.out.println("\t\t - " + Req);
            }

        } else {

            System.out.println("\n\t - ERROS DE PROCESSAMENTO ");

        }
    }


}
