package Make;

import Make.Run.RunMake;
import Sigmaz.S08_Utilitarios.Erro;
import Sigmaz.S08_Utilitarios.GrupoDeErro;

import Make.Compiler.Compiler;

import java.io.File;

public class Make {

    private boolean geral(String eArquivo, boolean mostrarAST) {

        boolean ret = false;

        File arq = new File(eArquivo);
        String mLocal = arq.getParent() + "/";


        System.out.println("################# SIGMAZ MAKE #################");
        System.out.println("");
        System.out.println(" - AUTOR	: LUAN FREITAS");
        System.out.println(" - VERSAO   : 1.0");
        System.out.println(" - STATUS  	: ALPHA");
        System.out.println(" - INICIO  	: 2020.09.19");

        System.out.println("");


        Compiler CompilerC = new Compiler();
        CompilerC.init(eArquivo);

        System.out.println("");

        System.out.println("################# LEXER MAKE ##################");
        System.out.println("");
        System.out.println("\t Iniciado : " + CompilerC.getData().toString());
        System.out.println("\t - Arquivo : " + eArquivo);
        System.out.println("\t - Chars : " + CompilerC.getIChars());
        System.out.println("\t - Tokens : " + CompilerC.getITokens());
        System.out.println("\t - Erros : " + CompilerC.getErros_Lexer().size());
        System.out.println("\t Finalizado : " + CompilerC.getData().toString());
        System.out.println("");


        System.out.println("############### COMPILADOR MAKE ###############");
        System.out.println("");
        System.out.println("\t Iniciado : " + CompilerC.getData().toString());
        System.out.println("\t - Instrucoes : " + CompilerC.getInstrucoes());
        System.out.println("\t - Erros : " + CompilerC.getErros_Compiler().size());
        System.out.println("\t Finalizado : " + CompilerC.getData().toString());


        if (CompilerC.getErros_Lexer().size() == 0 && CompilerC.getErros_Compiler().size() == 0) {


            if (mostrarAST){


               // System.out.println(CompilerC.getAST().ImprimirArvoreDeInstrucoes());


            }

            ret=true;

        } else {


            if (CompilerC.getErros_Lexer().size() > 0) {
                System.out.println("\n\t ERROS DE LEXICOGRAFICA : ");

                for (GrupoDeErro eGE : CompilerC.getErros_Lexer()) {
                    System.out.println("\t\t" + eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                    }
                }
            }else{


                System.out.println("\n\t ERROS DE COMPILACAO : ");

                for (GrupoDeErro eGE : CompilerC.getErros_Compiler()) {
                    System.out.println("\t\t" + eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                    }
                }

            }


        }


        return ret;
    }


    public void init(String eArquivo, boolean mostrarAST) {


        if (geral(eArquivo, mostrarAST)) {


            System.out.println("");
            System.out.println("################ RUNTIME MAKE ################");
            System.out.println("");
            System.out.println("\t - Executando : " + eArquivo);
            System.out.println("");

            Compiler CompilerC = new Compiler();
            CompilerC.init(eArquivo);

            File arq = new File(eArquivo);
            String mLocal = arq.getParent() + "/";


            RunMake runMakeC = new RunMake();
            String DI = runMakeC.getData().toString();


            runMakeC.init(CompilerC.getAST());

            System.out.println("\t - Instrucoes : " + runMakeC.getInstrucoes());
            System.out.println("");


            //   System.out.println(RunTimeC.getArvoreDeInstrucoes());

            System.out.println("");
            System.out.println("----------------------------------------------");

            runMakeC.run(mLocal);

            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

            String DF = runMakeC.getData();

            System.out.println("\t - Iniciado : " + DI);
            System.out.println("\t - Finalizado : " + DF);

            System.out.println("\t - Erros : " + runMakeC.getErros().size());

            if (runMakeC.getErros().size() > 0) {
                System.out.println("\n\t ERROS DE EXECUCAO : ");

                for (String Erro : runMakeC.getErros()) {
                    System.out.println("\t\t" + Erro);
                }
            }

            System.out.println("");
            System.out.println("----------------------------------------------");


        }

    }

}
