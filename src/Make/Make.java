package Make;

import Make.Run.Run;
import Sigmaz.Executor.RunTime;
import Sigmaz.Lexer.Token;
import Sigmaz.Utils.Erro;
import Sigmaz.Utils.GrupoDeErro;

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


                System.out.println(CompilerC.getAST().ImprimirArvoreDeInstrucoes());


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
            System.out.println("################ RUNTIME ################");
            System.out.println("");
            System.out.println("\t - Executando : " + eArquivo);
            System.out.println("");

            Compiler CompilerC = new Compiler();
            CompilerC.init(eArquivo);

            File arq = new File(eArquivo);
            String mLocal = arq.getParent() + "/";


            Run RunC = new Run();
            String DI = RunC.getData().toString();


            RunC.init(CompilerC.getAST());

            System.out.println("\t - Instrucoes : " + RunC.getInstrucoes());
            System.out.println("");


            //   System.out.println(RunTimeC.getArvoreDeInstrucoes());

            System.out.println("");
            System.out.println("----------------------------------------------");

            RunC.run(mLocal);

            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

            String DF = RunC.getData();

            System.out.println("\t - Iniciado : " + DI);
            System.out.println("\t - Finalizado : " + DF);

            System.out.println("\t - Erros : " + RunC.getErros().size());

            if (RunC.getErros().size() > 0) {
                System.out.println("\n\t ERROS DE EXECUCAO : ");

                for (String Erro : RunC.getErros()) {
                    System.out.println("\t\t" + Erro);
                }
            }

            System.out.println("");
            System.out.println("----------------------------------------------");


        }

    }

}
