package Sigmaz.S06_Montador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Chronos_Intervalo;
import Sigmaz.S00_Utilitarios.Mensageiro;

public class Montador {

    private Mensageiro mMensageiro;

    private float mTempo_Processamento;
    private float mTempo_Organizacao;

    public Montador() {

        mMensageiro = new Mensageiro();

        mTempo_Processamento=0.0F;
        mTempo_Organizacao=0.0F;

    }

    public void limpar() {

        mMensageiro.limpar();
        mTempo_Processamento=0.0F;
        mTempo_Organizacao=0.0F;

    }

    public float getTempo_Processamento() {
        return mTempo_Processamento;
    }

    public float getTempo_Organizacao() {
        return mTempo_Organizacao;
    }

    public ArrayList<String> getMensagens() {
        return mMensageiro.getMensagens();
    }

    public ArrayList<String> getErros() {
        return mMensageiro.getErros();
    }

    public void compilar(ArrayList<AST> lsAST, String eArquivo) {

        limpar();

        R5 mSegurador = new R5();
        P2 mPacotador = new P2();


        mMensageiro.mensagem("");
        mMensageiro.mensagem("############ MONTAGEM #################");
        mMensageiro.mensagem("");

        Chronos_Intervalo mTempo_01 = new Chronos_Intervalo();

        mTempo_01.marqueInicio();

        String eDocumento = mPacotador.empacotar(lsAST);

        mTempo_01.marqueFim();

        mMensageiro.mensagem("\tObjetos : " + mPacotador.getObjetos());
        mMensageiro.mensagem("\tInstrucoes : " + mPacotador.getInstrucoes());
        mMensageiro.mensagem("\tProfundidade : " + mPacotador.getProfundidade());

        mMensageiro.mensagem("\tTamanho : " + mPacotador.getTamanho());
        mMensageiro.mensagem("\tTempo de Empacotamento : " + mTempo_01.getIntervalo());

        Chronos_Intervalo mTempo_02 = new Chronos_Intervalo();

        mTempo_02.marqueInicio();

        if (mSegurador.guardar(eDocumento, eArquivo).getOk()) {

            mTempo_02.marqueFim();

            mMensageiro.mensagem("\tTempo de Criptografia : " + mTempo_02.getIntervalo());

        } else {
            mMensageiro.errar("Houve um problema na montagem !");
        }



    }


    public ArrayList<AST> Decompilar(String eArquivo) {

        ArrayList<AST> ASTSaida = new ArrayList<AST>();


        R5 mSegurador = new R5();
        P2 mPacotador = new P2();


        mTempo_Processamento=0.0F;
        mTempo_Organizacao=0.0F;


        Chronos_Intervalo mTempo_01 = new Chronos_Intervalo();

        mTempo_01.marqueInicio();

        R5Resposta mResposta = mSegurador.revelar(eArquivo);

        mTempo_01.marqueFim();

        mTempo_Processamento=mTempo_01.getIntervalo();

        if (mResposta.getOk()) {


            Chronos_Intervalo mTempo_02 = new Chronos_Intervalo();

            mTempo_02.marqueInicio();

            mPacotador.desempacotar(mResposta.getConteudo());

            mTempo_02.marqueFim();

            mTempo_Organizacao=mTempo_02.getIntervalo();


            if (mPacotador.getOK()) {
                ASTSaida = mPacotador.getDesempacotado();
            } else {
                mMensageiro.errar("Objeto corrompido !");
            }

        } else {
            mMensageiro.errar("Objeto corrompido !");
        }

       // for(AST eAST : mPacotador.getDesempacotado()){
       //     System.out.println(eAST.getImpressao());
       // }

        return ASTSaida;

    }





    public String mapaObjeto(String eArquivo) {

        String saida = "";

        Path dpath = Paths.get(eArquivo);


        try {
            byte[] l = Files.readAllBytes(dpath);

            int li = 0;
            int lo = l.length;

            int d = 0;

            while (li < lo) {
                int novo = (int) l[li];

                saida += " " + novo;


                if (d >= 50) {
                    d = 0;
                    saida += "\n";
                }

                li += 1;
                d += 1;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return saida;

    }

    public String tamanhoObjeto(String eArquivo) {

        File file = new File(eArquivo);

        long t = file.length();


        return t + "";
    }


}
