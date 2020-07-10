package Sigmaz;

import Sigmaz.Compilador.Compiler;
import Sigmaz.Lexer.Token;
import Sigmaz.Utils.Documentador;
import Sigmaz.Utils.Erro;
import Sigmaz.Utils.GrupoDeComentario;
import Sigmaz.Utils.GrupoDeErro;

import java.io.File;

public class Comentarios {

    public void init(String eArquivo) {

        boolean ret = false;

        File arq = new File(eArquivo);
        String mLocal = arq.getParent() + "/";


        System.out.println("################ SIGMAZ COMENTARIOS ################");
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
        System.out.println("\t - Instrucoes : " + CompilerC.Instrucoes());
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

            System.out.println("\n\t ERROS DE COMPILACAO : ");

            for (GrupoDeErro eGE : CompilerC.getErros_Compiler()) {
                System.out.println("\t\t" + eGE.getArquivo());
                for (Erro eErro : eGE.getErros()) {
                    System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                }
            }
        }

        if ((CompilerC.getErros_Lexer().size() == 0) && (CompilerC.getErros_Compiler().size() == 0)) {

            System.out.println("");
            System.out.println("############### COMENTARIOS ###############");
            System.out.println("");

            for (GrupoDeComentario eGE : CompilerC.getComentarios()) {
                if (eGE.getComentarios().size()>0){


                    System.out.println("\t" + eGE.getArquivo());

                    for (Token eComentario : eGE.getComentarios()) {
                        System.out.println("\t   ->> " + eComentario.getLinha() + ":" + eComentario.getPosicao() + " -> " + eComentario.getConteudo());
                    }

                }

            }




        }


    }


}
