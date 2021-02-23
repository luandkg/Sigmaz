package Sigmaz.S07_Apps;

import Sigmaz.S00_Utilitarios.*;
import Sigmaz.S02_PosProcessamento.Processadores.Cabecalho;
import Sigmaz.Sigmaz_Compilador;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class SigmazTestes {

    private ArrayList<String> mArquivos;
    private String mSaida;

    public SigmazTestes() {

        mArquivos = new ArrayList<String>();
        mSaida = "Compiled.sigmad";

    }

    public void setSaida(String eSaida) {
        mSaida = eSaida;
    }

    public void adicionar(String eArquivo) {
        mArquivos.add(eArquivo);
    }

    public void init(String mLocalLibs, String eTitulo) {

        System.out.println("");

        System.out.println("############################ " + eTitulo + " ############################");

        Estatisticas mEstatisticas = new Estatisticas();

        Chronos mChronos = new Chronos();


        Chronos_Intervalo mIntervalo = new Chronos_Intervalo();
        mIntervalo.marqueInicio();

        System.out.println("");
        System.out.println(" - AUTOR	: LUAN FREITAS");
        System.out.println(" - VERSAO   : 1.0");
        System.out.println(" - STATUS  	: BETA");

        System.out.println("");
        System.out.println(" - INICIO  	: " + mChronos.getData());
        System.out.println("");




        int Contador = 1;

        SequenciadorDeTestes mSequenciadorDeTestes = new SequenciadorDeTestes();

        long iChars = 0;
        long iTokens = 0;
        long iTempoParser = 0;

        for (String Arquivo : mArquivos) {

            Chronos_Intervalo mTempoCorrente = new Chronos_Intervalo();
            mTempoCorrente.marqueInicio();

            ArquivoTeste mTeste = mSequenciadorDeTestes.adicionarTeste(Arquivo);


            Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();

            SigmazC.setMostrar_Fases(false);
            SigmazC.mostrarDebug(false);
            SigmazC.setMostrarArvoreRunTime(false);
            SigmazC.setMostrar_ArvoreFalhou(false);
            SigmazC.setMostrar_Erros(false);
            SigmazC.setMostrar_Execucao(false);
            SigmazC.setMostrar_Integracao(true);
            SigmazC.setMostrar_Debugador(false);

            SigmazC.init(Arquivo, mSaida, mLocalLibs, 1, false);

            adicionarProblemas(SigmazC, mTeste);

            mTempoCorrente.marqueFim();

            iChars += SigmazC.getIChars();
            iTokens += SigmazC.getITokens();
            iTempoParser += SigmazC.getLexer_Tempo() + SigmazC.getParser_Tempo();


            String sContador = String.valueOf(Contador);

            if (sContador.length() == 1) {
                sContador = "0" + sContador;
            }

            if (mTeste.tudoOk()) {

                System.out.println(getTextoOrganizado("\t -->> Arquivo : " + sContador + " -> " + mTeste.getArquivo(), 55, " : SUCESSO "));

                mEstatisticas.velocidades(mTeste,mTempoCorrente.getIntervalo());


            } else {

                System.out.println(getTextoOrganizado("\t -->> Arquivo : " + sContador + " -> " + mTeste.getArquivo(), 55, " : FALHOU -> " + mTeste.getProblema()));
                System.out.println(mTeste.getComplemento().getConteudo());

            }

            Contador += 1;
        }

        mIntervalo.marqueFim();

        System.out.println("");


        System.out.println(" - FIM  	: " + mChronos.getData());
        System.out.println("");


        System.out.println(" - TEMPO  	: " + mIntervalo.getIntervalo() + " segundos");
        System.out.println("");


        if (mSequenciadorDeTestes.getTotal() > 0) {

            if (mEstatisticas.isVelocidades()) {

                System.out.println(" - VELOCIDADES ");

                System.out.println("\t - RAPIDO  : " + mEstatisticas.getLocalRapido() + " -> " + mEstatisticas.getTempoRapido());
                System.out.println("\t - DEVAGAR  : " + mEstatisticas.getLocalDevagar() + " -> " + mEstatisticas.getTempoDevagar());

                System.out.println("");

            }

            String s = getPorcentagem(mSequenciadorDeTestes.getCorretos().size(), mSequenciadorDeTestes.getTotal());
            String f = getPorcentagem(mSequenciadorDeTestes.getProblemas().size(), mSequenciadorDeTestes.getTotal());

            System.out.println(" - TESTES  	: " + mSequenciadorDeTestes.getTotal() + " -> 100.00 % ");
            System.out.println("\t - SUCESSO  : " + mSequenciadorDeTestes.getCorretos().size() + " -> " + s);
            System.out.println("\t - FALHOU  	: " + mSequenciadorDeTestes.getProblemas().size() + " -> " + f);

            double mSegundos = (double) iTempoParser / (double) 1_000_000_000.0;

            System.out.println("");
            System.out.println(" - ESTATISTICAS  DE COMPILACAO	");
            System.out.println("\t - Chars      : " + iChars);
            System.out.println("\t - Tokens  	: " + iTokens);
            System.out.println("\t - Tempo  	: " + mSegundos);
            System.out.println("");

            if (mSequenciadorDeTestes.temProblemas()) {

                System.out.println("");
                System.out.println("\t - ARQUIVOS COM PROBLEMAS : ");
                System.out.println("");

                for (ArquivoTeste a : mSequenciadorDeTestes.getProblemas()) {

                    System.out.println("\t\t - PROBLEMA COM : " + a.getArquivo() + " -->> " + a.getProblema());
                }

            }

        }


    }

    public String getPorcentagem(int eQuantos, int eTotal) {
        float s = ((float) eQuantos / (float) eTotal) * 100.0f;

        NumberFormat formatarFloat = new DecimalFormat("0.00");

        return formatarFloat.format(s).replace(",", ".") + " % ";
    }

    public String getTextoOrganizado(String eAntes, int eTamanho, String eDepois) {
        String ret = eAntes;
        while (ret.length() < eTamanho) {
            ret += " ";
        }
        ret += eDepois;
        return ret;
    }


    private void adicionarProblemas(Sigmaz_Compilador SigmazC, ArquivoTeste mTeste) {

        if (SigmazC.temErros()) {

            if (SigmazC.getFase() == Sigmaz_Compilador.Fases.PRE_PROCESSAMENTO) {

                mTeste.setProblema("PRE-PROCESSAMENTO");

                for (GrupoDeErro eGE : SigmazC.getErros_PreProcessamento()) {
                    mTeste.getComplemento().adicionarLinha(2, eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        mTeste.getComplemento().adicionarLinha(2, "->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                    }
                }

            } else if (SigmazC.getFase() == Sigmaz_Compilador.Fases.LEXER) {

                mTeste.setProblema("PRE-LEXER");

                for (GrupoDeErro eGE : SigmazC.getErros_Lexer()) {
                    mTeste.getComplemento().adicionarLinha(2, eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        mTeste.getComplemento().adicionarLinha(2, "->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                    }
                }

            } else if (SigmazC.getFase() == Sigmaz_Compilador.Fases.PARSER) {

                mTeste.setProblema("PARSER");

                for (GrupoDeErro eGE : SigmazC.getErros_Parser()) {
                    mTeste.getComplemento().adicionarLinha(2, eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        mTeste.getComplemento().adicionarLinha(2, "->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                    }
                }

            } else if (SigmazC.getFase() == Sigmaz_Compilador.Fases.POS_PROCESSAMENTO) {

                mTeste.setProblema("POS-PROCESSAMENTO");

                for (String Erro : SigmazC.getErros_PosProcessamento()) {
                    mTeste.getComplemento().adicionarLinha(2, Erro);
                }

            } else if (SigmazC.getFase() == Sigmaz_Compilador.Fases.INTEGRALIZADOR) {

                mTeste.setProblema("INTEGRALIZADOR");

                for (String Erro : SigmazC.getErros_Integracao()) {
                    mTeste.getComplemento().adicionarLinha(2, Erro);
                }

            } else if (SigmazC.getFase() == Sigmaz_Compilador.Fases.MONTAGEM) {

                mTeste.setProblema("MONTAGEM");

            } else if (SigmazC.getFase() == Sigmaz_Compilador.Fases.PRONTO) {

                mTeste.setProblema("EXECUCAO");

                for (String Erro : SigmazC.getErros_Execucao()) {
                    mTeste.getComplemento().adicionarLinha(2, Erro);
                }

            }

        }

    }
}
