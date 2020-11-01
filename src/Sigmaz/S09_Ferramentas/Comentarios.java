package Sigmaz.S09_Ferramentas;

import Sigmaz.S00_Utilitarios.Tempo;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S00_Utilitarios.Erro;
import Sigmaz.S00_Utilitarios.GrupoDeErro;
import Sigmaz.S00_Utilitarios.GrupoDeComentario;
import Sigmaz.Sigmaz_Compilador;

import java.io.File;

public class Comentarios {

    public void init(String eArquivo,String mLocalLibs) {


        File arq = new File(eArquivo);
        String mLocal = arq.getParent() + "/";


        System.out.println("################ SIGMAZ COMENTARIOS ################");
        System.out.println("");
        System.out.println(" - AUTOR	: LUAN FREITAS");
        System.out.println(" - VERSAO   : 1.0");
        System.out.println(" - STATUS  	: ALPHA");
        System.out.println(" - INICIO  	: 2020.06.12");

        System.out.println("");


        Sigmaz_Compilador CompilerC = new Sigmaz_Compilador();
        CompilerC.initSemObjeto(eArquivo,mLocalLibs,0);

        System.out.println("################# LEXER ##################");
        System.out.println("");
        System.out.println("\t Iniciado : " + Tempo.getData().toString());
        System.out.println("\t - Arquivo : " + eArquivo);
        System.out.println("\t - Chars : " + CompilerC.getIChars());
        System.out.println("\t - Tokens : " + CompilerC.getITokens());
        System.out.println("\t - Erros : " + CompilerC.getErros_Lexer().size());
        System.out.println("\t Finalizado : " + Tempo.getData().toString());
        System.out.println("");


        System.out.println("############### COMPILADOR ###############");
        System.out.println("");
        System.out.println("\t Iniciado : " + Tempo.getData().toString());
        System.out.println("\t - Instrucoes : " + CompilerC.getInstrucoes());
        System.out.println("\t - Erros : " + CompilerC.getErros_Parser().size());
        System.out.println("\t - Requisitados : ");

        for (String Req : CompilerC.getRequisitados()) {
            System.out.println("\t\t - " + Req);
        }

        System.out.println("\t Finalizado : " + Tempo.getData().toString());


        if (CompilerC.getErros_Lexer().size() > 0) {

            System.out.println("\n\t ERROS DE LEXICOGRAFICA : ");

            for (GrupoDeErro eGE : CompilerC.getErros_Lexer()) {
                System.out.println("\t\t" + eGE.getArquivo());
                for (Erro eErro : eGE.getErros()) {
                    System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                }
            }

        }

        if (CompilerC.getErros_Parser().size() > 0) {

            System.out.println("\n\t ERROS DE PARSER : ");

            for (GrupoDeErro eGE : CompilerC.getErros_Parser()) {
                System.out.println("\t\t" + eGE.getArquivo());
                for (Erro eErro : eGE.getErros()) {
                    System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                }
            }
        }

        if ((CompilerC.getErros_Lexer().size() == 0) && (CompilerC.getErros_Parser().size() == 0)) {

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
