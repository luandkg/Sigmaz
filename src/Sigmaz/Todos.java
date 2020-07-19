package Sigmaz;

import Sigmaz.Compilador.Compiler;
import Sigmaz.Lexer.Token;
import Sigmaz.Utils.Erro;
import Sigmaz.Utils.GrupoDeComentario;
import Sigmaz.Utils.GrupoDeErro;

import java.io.File;
import java.util.ArrayList;

public class Todos {

    public void init(String eArquivo) {

        boolean ret = false;

        File arq = new File(eArquivo);
        String mLocal = arq.getParent() + "/";


        System.out.println("################ SIGMAZ TODOS ################");
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
            System.out.println("############### TODOS ###############");
            System.out.println("");

            for (GrupoDeComentario eGE : CompilerC.getComentarios()) {
                if (eGE.getComentarios().size() > 0) {


                    for (Token eComentario : eGE.getComentarios()) {

                        ArrayList<String> mTodos = getTodos(eComentario.getConteudo());
                        if (mTodos.size() > 0) {
                            System.out.println("\t" + eGE.getArquivo());
                            for (String eTodo : mTodos) {
                                System.out.println(eTodo);

                            }
                        }
                    }

                }

            }

        }


    }


    public ArrayList<String> getTodos(String eComentario) {


        ArrayList<String> mLinhas = new ArrayList<String>();

        ArrayList<String> mTodos = new ArrayList<String>();

        ArrayList<String> mPrefixos = new ArrayList<String>();
        mPrefixos.add("todo");
        mPrefixos.add("TODO");
        mPrefixos.add("fixme");
        mPrefixos.add("FIXME");
        mPrefixos.add("warning");
        mPrefixos.add("WARNING");
        mPrefixos.add("warn");
        mPrefixos.add("WARN");
        mPrefixos.add("error");
        mPrefixos.add("ERROR");
        mPrefixos.add("implement");
        mPrefixos.add("IMPLEMENT");


        int i = 0;
        int o = eComentario.length();

        String mLinha = "";

        while (i < o) {
            String l = eComentario.charAt(i) + "";

            if (l.contentEquals("\n")) {

                if (mLinha.length() > 0) {
                    mLinhas.add(mLinha);
                }

                mLinha = "";

            } else {
                mLinha += l;
            }

            i += 1;
        }

        if (mLinha.length() > 0) {
            mLinhas.add(mLinha);
        }

        for (String eLinha : mLinhas) {

            String eLinhaArrumada = "";
            String ePrefixo = "";

            int a = 0;
            int e = eLinha.length();
            while (a < e) {
                String l = eLinha.charAt(a) + "";
                if (l.contentEquals(" ")) {
                } else if (l.contentEquals("\t")) {
                } else if (l.contentEquals("#")) {

                } else {
                    break;
                }
                a += 1;
            }

            while (a < e) {
                String l = eLinha.charAt(a) + "";
                if (l.contentEquals(" ")) {
                    a += 1;
                    break;
                } else if (l.contentEquals("\t")) {
                    a += 1;
                    break;
                } else {
                    ePrefixo += l;
                }
                a += 1;
            }

            while (a < e) {
                String l = eLinha.charAt(a) + "";
                eLinhaArrumada += l;
                a += 1;
            }

            if (mPrefixos.contains(ePrefixo)) {
                mTodos.add("\t   ->> " + ePrefixo + " " + eLinhaArrumada);

            }


        }

        return mTodos;
    }

}
