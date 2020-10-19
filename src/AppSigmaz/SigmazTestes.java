package AppSigmaz;

import Sigmaz.S00_Utilitarios.*;
import Sigmaz.S05_PosProcessamento.Processadores.Cabecalho;
import Sigmaz.Sigmaz_Compilador;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class SigmazTestes {

    private ArrayList<String> mArquivos;
    private String mSaida;
    private Cabecalho mCabecalho;

    public SigmazTestes() {

        mArquivos = new ArrayList<String>();
        mSaida = "Compiled.sigmad";
        mCabecalho = new Cabecalho();

    }

    public void setSaida(String eSaida) {
        mSaida = eSaida;
    }

    public void adicionar(String eArquivo) {
        mArquivos.add(eArquivo);
    }

    public void init(String mLocal, String mLocalLibs, String eTitulo) {

        System.out.println("");

        System.out.println("################ " + eTitulo + " ################");

        Chronos mChronos = new Chronos();

        String DDI = mChronos.getData();
        long mInicio = mChronos.get();

        System.out.println("");
        System.out.println(" - AUTOR	: LUAN FREITAS");
        System.out.println(" - VERSAO   : 1.0");
        System.out.println(" - STATUS  	: ALPHA");

        System.out.println("");
        System.out.println(" - INICIO  	: " + DDI);
        System.out.println("");

        int Contador = 1;

        int mQuantidade = mArquivos.size();
        int mSucesso = 0;
        int mProblema = 0;

        SequenciadorDeTestes mSequenciadorDeTestes = new SequenciadorDeTestes();


        for (String Arquivo : mArquivos) {


            Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();

            SigmazC.setMostrar_Fases(false);
            SigmazC.mostrarDebug(false);
            SigmazC.setMostrarArvoreRunTime(false);
            SigmazC.setMostrar_ArvoreFalhou(false);
            SigmazC.setMostrar_Erros(false);
            SigmazC.setMostrar_Execucao(false);

            SigmazC.init(Arquivo, mSaida, mLocalLibs, 1);

            boolean passou = false;
            String parou = "";
            Documento mDocumento = new Documento();

            if (SigmazC.temErros()) {

                if (SigmazC.getFase() == Sigmaz_Compilador.Fases.PRE_PROCESSAMENTO) {

                    parou = "PRE-PROCESSAMENTO";

                    for (GrupoDeErro eGE : SigmazC.getErros_PreProcessamento()) {
                        mDocumento.adicionarLinha(2, eGE.getArquivo());
                        for (Erro eErro : eGE.getErros()) {
                            mDocumento.adicionarLinha(2, "->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                        }
                    }

                } else if (SigmazC.getFase() == Sigmaz_Compilador.Fases.LEXER) {

                    parou = "LEXER";

                    for (GrupoDeErro eGE : SigmazC.getErros_Lexer()) {
                        mDocumento.adicionarLinha(2, eGE.getArquivo());
                        for (Erro eErro : eGE.getErros()) {
                            mDocumento.adicionarLinha(2, "->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                        }
                    }

                } else if (SigmazC.getFase() == Sigmaz_Compilador.Fases.PARSER) {

                    parou = "PARSER";

                    for (GrupoDeErro eGE : SigmazC.getErros_Compiler()) {
                        mDocumento.adicionarLinha(2, eGE.getArquivo());
                        for (Erro eErro : eGE.getErros()) {
                            mDocumento.adicionarLinha(2, "->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                        }
                    }

                } else if (SigmazC.getFase() == Sigmaz_Compilador.Fases.POS_PROCESSAMENTO) {

                    parou = "POS-PROCESSAMENTO";

                    for (String Erro : SigmazC.getErros_PosProcessamento()) {
                        mDocumento.adicionarLinha(2, Erro);
                    }

                } else if (SigmazC.getFase() == Sigmaz_Compilador.Fases.MONTAGEM) {

                    parou = "MONTAGEM";

                } else if (SigmazC.getFase() == Sigmaz_Compilador.Fases.PRONTO) {

                    parou = "EXECUCAO";

                    for (String Erro : SigmazC.getErros_Execucao()) {
                        mDocumento.adicionarLinha(2, Erro);
                    }

                }


            } else {
                passou = true;
            }


            String sContador = String.valueOf(Contador);

            if (sContador.length() == 1) {
                sContador = "0" + sContador;
            }

            if (passou) {
                System.out.println(" Arquivo : " + sContador + " -> " + Arquivo + " : SUCESSO ");
                mSucesso += 1;
            } else {

                System.out.println(" Arquivo : " + sContador + " -> " + Arquivo + " : FALHOU -> " + parou);

                System.out.println(mDocumento.getConteudo());
                mProblema += 1;

                mSequenciadorDeTestes.adicionarProblema(Arquivo, parou);
            }

            Contador += 1;
        }

        String DDF = mChronos.getData();
        long mFim = mChronos.get();

        System.out.println("");


        System.out.println(" - FIM  	: " + DDF);
        System.out.println("");

        float sec = mChronos.getIntervalo(mInicio, mFim);

        System.out.println(" - TEMPO  	: " + sec + " segundos");
        System.out.println("");



        if (mQuantidade > 0) {

            String s = getPorcentagem(mSucesso,mQuantidade);
            String f = getPorcentagem(mProblema,mQuantidade);

            System.out.println(" - TESTES  	: " + mQuantidade + " -> 100.00 % ");
            System.out.println("\t - SUCESSO  : " + mSucesso + " -> " +  s);
            System.out.println("\t - FALHOU  	: " + mProblema + " -> " + f);


            if (mSequenciadorDeTestes.temProblemas()) {

                System.out.println("");
                System.out.println("\t - ARQUIVOS COM PROBLEMAS : ");
                System.out.println("");

                for (ArquivoProblema a : mSequenciadorDeTestes.getProblemas()) {

                    System.out.println("\t\t - PROBLEMA COM : " + a.getArquivo() + " -->> " + a.getProblema());
                }

            }

        }


    }

    public String getPorcentagem(int eQuantos,int eTotal){
        float s = ((float) eQuantos / (float) eTotal) * 100.0f;

        NumberFormat formatarFloat = new DecimalFormat("0.00");

        return formatarFloat.format(s).replace(",", ".") + " % ";
    }

}
