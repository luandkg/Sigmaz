package Sigmaz.S00_Utilitarios;

import Sigmaz.S01_Compilador.C02_Lexer.Lexer;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;

public class Formatador {


    private String eDocumento;

    private int mQuantidade;
    private int mIndex;
    private int mTab;
    private Lexer LexerC;

    public Formatador() {

        mQuantidade = 0;
        mIndex = 0;
        mTab = 0;
        eDocumento = "";
        LexerC = new Lexer();

    }


    public void identar(String eArquivoOrigem, String eArquivoDestino) {


        LexerC.init(eArquivoOrigem);

        mQuantidade = LexerC.getTokens().size();
        mIndex = 0;
        mTab = 0;
        eDocumento = "";

        for (Token TokenC : LexerC.getTokens()) {

            if (TokenC.getTipo() == TokenTipo.PONTOVIRGULA) {

                idente_PontoEVirgula(TokenC);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA){

                eDocumento += " " + TokenC.getConteudo() ;

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {

                mTab += 1;
                eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                idente_ChaveFecha(TokenC);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                if (TokenC.getConteudo().contains("\"")){
                    eDocumento += "'" + TokenC.getConteudo() + "'";
                }else{
                    eDocumento += "\"" + TokenC.getConteudo() + "\"";
                }

            } else if (TokenC.getTipo() == TokenTipo.PONTO) {
                eDocumento += TokenC.getConteudo();
            } else if (TokenC.getTipo() == TokenTipo.DOISPONTOS) {

                idente_DoisPontos(TokenC);

            } else {

                idente_Outro(TokenC);


            }

            mIndex += 1;

        }

        Texto.Escrever(eArquivoDestino, eDocumento);

    }

    private void idente_Outro(Token TokenC) {

        if (mIndex + 1 < mQuantidade) {
            Token Futuro = LexerC.getTokens().get(mIndex + 1);

            if (Futuro.getTipo() == TokenTipo.PONTOVIRGULA) {
                eDocumento += TokenC.getConteudo();
            } else if (Futuro.getTipo() == TokenTipo.PONTO) {
                eDocumento += TokenC.getConteudo();
            } else {
                eDocumento += TokenC.getConteudo() + " ";
            }

        } else {
            eDocumento += TokenC.getConteudo();
        }

    }

    private void idente_DoisPontos(Token TokenC) {

        if (mIndex - 1 >= 0) {

            Token Passado = LexerC.getTokens().get(mIndex - 1);

            if (Passado.getTipo() == TokenTipo.ID && Passado.mesmoConteudo("all")) {


                eDocumento += TokenC.getConteudo() + " \n" + getTabulacao(mTab);

            } else if (Passado.getTipo() == TokenTipo.ID && Passado.mesmoConteudo("restrict")) {

                eDocumento += TokenC.getConteudo() + " \n" + getTabulacao(mTab);

            } else if (Passado.getTipo() == TokenTipo.ID && Passado.mesmoConteudo("extern")) {

                eDocumento += TokenC.getConteudo() + " \n" + getTabulacao(mTab);

            } else {
                eDocumento += TokenC.getConteudo() + " ";
            }

        } else {
            eDocumento += TokenC.getConteudo() + " ";
        }

    }

    private void idente_ChaveFecha(Token TokenC) {

        mTab -= 1;


        if (mIndex + 1 < mQuantidade) {
            Token Futuro = LexerC.getTokens().get(mIndex + 1);

            if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("others")) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo();
            } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("other")) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo();
            } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("act")) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n\n" + getTabulacao(mTab);
            } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("struct")) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n\n" + getTabulacao(mTab);
            } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("operator")) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n\n" + getTabulacao(mTab);

            } else if (Futuro.getTipo() == TokenTipo.PONTOVIRGULA) {
                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo();
            } else {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            }

        } else {

            eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

        }


    }

    private void idente_PontoEVirgula(Token TokenC) {


        boolean ja = false;
        if (mIndex - 1 > 0) {
            Token Passado = LexerC.getTokens().get(mIndex - 1);
            if (Passado.getTipo() == TokenTipo.CHAVE_FECHA) {
                eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);
                ja = true;
            }
        }

        if (!ja) {

            if (mIndex + 1 < mQuantidade) {

                Token Futuro = LexerC.getTokens().get(mIndex + 1);

                if (Futuro.getTipo() == TokenTipo.CHAVE_FECHA) {
                    eDocumento += TokenC.getConteudo();
                } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("call")) {

                    eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);
                } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("act")) {

                    eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);
                } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("struct")) {

                    eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);
                } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("operator")) {

                    eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);

                } else {
                    eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);
                }
            } else {
                eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);
            }

        }


    }

    public String getTabulacao(int t) {
        String ret = "";

        if (t > 0) {
            for (int i = 0; i < t; i++) {
                ret += "\t";
            }
        }

        return ret;
    }


}
