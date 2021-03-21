package Sigmaz.S01_Compilador.Avisos;

import Sigmaz.S08_Utilitarios.Documento;

import java.util.ArrayList;

public class Avisador {

    public String organizarString(String e, int t) {
        while (e.length() < t) {
            e += " ";
        }
        return e;
    }

    public String organizarNumero(int e, int casas) {
        String v = String.valueOf(e);
        while (v.length() < casas) {
            v = "0" + v;
        }
        return v;
    }

    public String getAvisosParser(ArrayList<InfoParser> eAvisos) {

        Documento mLexer_Processamento = new Documento();

        int casas = 1;
        if (eAvisos.size() >= 10 && eAvisos.size() < 99) {
            casas = 2;
        } else if (eAvisos.size() >= 100 && eAvisos.size() < 999) {
            casas = 3;
        } else if (eAvisos.size() >= 1000 && eAvisos.size() < 9999) {
            casas = 4;
        }

        mLexer_Processamento.adicionarLinha(" ################################# PARSER  #################################  ");
        mLexer_Processamento.pularLinha();

        int acc_Tokens = 0;
        int acc_ASTS = 0;

        int i = 1;
        for (InfoParser info : eAvisos) {

            String P1 = organizarNumero(i, casas) + " :: " + info.getArquivo() + " ";
            mLexer_Processamento.adicionarLinha(1, organizarString(P1, 45) + " [ Tokens = " + organizarNumero(info.getTokens(), 6) + "    ASTS = " + organizarNumero(info.getASTS(), 6) + " ] ");

            acc_Tokens += info.getTokens();
            acc_ASTS += info.getASTS();

            i += 1;
        }


        String P2 = "Parser Total -->> " + eAvisos.size() + " ";
        mLexer_Processamento.pularLinha();
        mLexer_Processamento.adicionarLinha(1, organizarString(P2, 45) + " [ Tokens : " + organizarNumero(acc_Tokens, 6) + "    ASTS : " + organizarNumero(acc_ASTS, 6) + " ]");

        return mLexer_Processamento.getConteudo();

    }

    public String getAvisosLexer(ArrayList<InfoLexer> eAvisos) {

        Documento mLexer_Processamento = new Documento();

        int casas = 1;
        if (eAvisos.size() >= 10 && eAvisos.size() < 99) {
            casas = 2;
        } else if (eAvisos.size() >= 100 && eAvisos.size() < 999) {
            casas = 3;
        } else if (eAvisos.size() >= 1000 && eAvisos.size() < 9999) {
            casas = 4;
        }

        mLexer_Processamento.adicionarLinha(" ################################# LEXER  #################################  ");
        mLexer_Processamento.pularLinha();

        int acc_Chars = 1;
        int acc_Tokens = 1;

        int i = 1;
        for (InfoLexer info : eAvisos) {

            String P1 = organizarNumero(i, casas) + " :: " + info.getArquivo() + " ";
            mLexer_Processamento.adicionarLinha(1, organizarString(P1, 45) + " [ Chars = " + organizarNumero(info.getChars(), 6) + "    Tokens = " + organizarNumero(info.getTokens(), 6) + " ] ");

            acc_Chars += info.getChars();
            acc_Tokens += info.getTokens();

            i += 1;
        }


        String P2 = "Lexer Total -->> " + eAvisos.size() + " ";
        mLexer_Processamento.pularLinha();
        mLexer_Processamento.adicionarLinha(1, organizarString(P2, 45) + " [ Chars : " + organizarNumero(acc_Chars, 6) + "    Tokens : " + organizarNumero(acc_Tokens, 6) + " ]");

        return mLexer_Processamento.getConteudo();

    }

    public String getAvisosComentarios(ArrayList<InfoComment> eAvisos) {

        Documento mLexer_Processamento = new Documento();

        int casas = 1;
        if (eAvisos.size() >= 10 && eAvisos.size() < 99) {
            casas = 2;
        } else if (eAvisos.size() >= 100 && eAvisos.size() < 999) {
            casas = 3;
        } else if (eAvisos.size() >= 1000 && eAvisos.size() < 9999) {
            casas = 4;
        }

        mLexer_Processamento.adicionarLinha(" ################################# COMENTARIOS  #################################  ");
        mLexer_Processamento.pularLinha();

        int acc_Tokens = 0;
        int acc_Comentarios = 0;

        int i = 1;
        for (InfoComment info : eAvisos) {

            String P1 = organizarNumero(i, casas) + " :: " + info.getArquivo() + " ";
            mLexer_Processamento.adicionarLinha(1, organizarString(P1, 45) + " [ Tokens = " + organizarNumero(info.getTokens(), 6) + "    Comentarios = " + organizarNumero(info.getComentarios(), 6) + " ] ");

            acc_Tokens+=info.getTokens();
            acc_Comentarios+=info.getComentarios();

            i += 1;
        }


        String P2 = "Comentarios Total -->> " + eAvisos.size() + " ";
        mLexer_Processamento.pularLinha();
        mLexer_Processamento.adicionarLinha(1, organizarString(P2, 45) + " [ Tokens : " + organizarNumero(acc_Tokens, 6) + "    Comentarios : " + organizarNumero(acc_Comentarios, 6) + " ]");

        return mLexer_Processamento.getConteudo();


    }


}
