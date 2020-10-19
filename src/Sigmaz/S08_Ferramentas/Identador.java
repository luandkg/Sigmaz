package Sigmaz.S08_Ferramentas;

import Sigmaz.S00_Utilitarios.Erro;
import Sigmaz.S00_Utilitarios.Texto;
import Sigmaz.S02_Lexer.Lexer;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;

import java.util.Calendar;

public class Identador {

    public void init(String eArquivoOrigem, String eArquivoDestino) {


        Lexer LexerC = new Lexer();

        String DI = LexerC.getData().toString();

        LexerC.init(eArquivoOrigem);

        String DF = LexerC.getData().toString();

        System.out.println("");
        System.out.println("################# LEXER ##################");
        System.out.println("");
        System.out.println("\t Iniciado : " + DI);
        System.out.println("\t - Origem : " + eArquivoOrigem);
        System.out.println("\t - Chars : " + LexerC.getChars());
        System.out.println("\t - Tokens : " + LexerC.getTokens().size());
        System.out.println("\t - Erros : " + LexerC.getErros().size());
        System.out.println("\t Finalizado : " + DF);
        System.out.println("");


        if (LexerC.getErros().size() > 0) {

            System.out.println("\n\t ERROS DE LEXICOGRAFICA : ");

            for (Erro eErro : LexerC.getErros()) {
                System.out.println("\t\t    ->> " + eErro.getPosicao() + " : " + eErro.getMensagem());
            }

        } else {

            System.out.println("\t IDENTANDO");
            System.out.println("\t - Inicio : " + LexerC.getData());
            System.out.println("\t - Destino : " + eArquivoDestino);


            identar(eArquivoOrigem, eArquivoDestino);

            System.out.println("\t - Fim : " + LexerC.getData());
            System.out.println("");

            System.out.println("################# ##### ##################");

        }

    }

    public boolean initSimples(String eArquivoOrigem, String eArquivoDestino) {
        boolean ret = false;

        Lexer LexerC = new Lexer();


        LexerC.init(eArquivoOrigem);
        if (LexerC.getErros().size() == 0) {
            identar(eArquivoOrigem, eArquivoDestino);
            ret = true;
        }


        return ret;

    }


    public void identar(String eArquivoOrigem, String eArquivoDestino) {

        Lexer LexerC = new Lexer();

        LexerC.init(eArquivoOrigem);

        String eDocumento = "";

        int mQuantidade = LexerC.getTokens().size();
        int mIndex = 0;

        int mTab = 0;


        for (Token TokenC : LexerC.getTokens()) {

            if (TokenC.getTipo() == TokenTipo.PONTOVIRGULA) {

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

                            eDocumento +=  TokenC.getConteudo()  + "\n" + getTabulacao(mTab);
                        } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("act")) {

                            eDocumento +=  TokenC.getConteudo()  + "\n" + getTabulacao(mTab);
                        } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("struct")) {

                            eDocumento +=  TokenC.getConteudo()  + "\n" + getTabulacao(mTab);
                        } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("operator")) {

                            eDocumento +=  TokenC.getConteudo()  + "\n" + getTabulacao(mTab);

                        } else {
                            eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);
                        }
                    } else {
                        eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);
                    }

                }


            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {

                mTab += 1;
                eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

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
                    } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("operation")) {

                        eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n\n" + getTabulacao(mTab);

                    } else if (Futuro.getTipo() == TokenTipo.PONTOVIRGULA) {
                        eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo();
                    } else {

                        eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

                    }

                } else {

                    eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

                }


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {
                eDocumento += "\"" + TokenC.getConteudo() + "\"";
            } else if (TokenC.getTipo() == TokenTipo.PONTO) {
                eDocumento += TokenC.getConteudo();
            } else if (TokenC.getTipo() == TokenTipo.DOISPONTOS) {


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

            } else {

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

            mIndex += 1;

        }

        Texto.Escrever(eArquivoDestino, eDocumento);

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

    public String getData() {

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
