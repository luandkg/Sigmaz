package AppUI.App;

import AppUI.Mottum.Cenarios.Cena;
import AppUI.Mottum.UI.BotaoCor;
import AppUI.Mottum.UI.Clicavel;
import AppUI.Mottum.Utils.Escritor;
import AppUI.Mottum.Windows;
import Sigmaz.Sigmaz_Etapas;

import java.awt.Color;
import java.awt.Graphics;


public class SigmazCompilador extends Cena {

    private Windows mWindows;

    private Escritor TextoGrande;
    private Escritor TextoPequeno;

    private BotaoCor BTN_CRIAR;

    private Clicavel mClicavel;

    boolean mPode;

    boolean mContinuar;
    int mPassos;
    private Sigmaz_Etapas SigmazC;

    private String mETAPA_PRE_PROCESSAMENTO;
    private String mETAPA_LEXER;
    private String mETAPA_COMPILER;
    private String mETAPA_POS_PROCESSAMENTO;
    private String mETAPA_MONTAGEM;


    public SigmazCompilador(Windows eWindows) {
        mWindows = eWindows;

        TextoGrande = new Escritor(30, Color.BLACK);
        TextoPequeno = new Escritor(15, Color.BLACK);

        mClicavel = new Clicavel();

        BTN_CRIAR = new BotaoCor(300, 50, 100, 100, new Color(120, 50, 156));

        mPode = true;
        mContinuar = false;
        mPassos = 0;
        SigmazC = new Sigmaz_Etapas();

        String mSTATUS_NAO = "NAO REALIZADO";

        mETAPA_PRE_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_LEXER = mSTATUS_NAO;
        mETAPA_COMPILER = mSTATUS_NAO;
        mETAPA_POS_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_MONTAGEM = mSTATUS_NAO;


    }


    @Override
    public void iniciar() {
        mWindows.setTitle("Sigmaz Compilador - V1");
    }

    @Override
    public void update(double dt) {

        mClicavel.update(dt, mWindows.getMouse().Pressed());


        if (mContinuar) {

            try {

                Thread.sleep(200);

            } catch (Exception e) {

            }


            SigmazC.continuar();

            System.out.println(SigmazC.getContinuar() + " :: " + "Passo : " + mPassos + " -->> " + SigmazC.getEtapa() + " :: " + SigmazC.getSubEtapa());

            mPassos+=1;

            mETAPA_PRE_PROCESSAMENTO = SigmazC.getPreProcessamento();
            mETAPA_LEXER = SigmazC.getLexer();
            mETAPA_COMPILER = SigmazC.getCompiler();
            mETAPA_POS_PROCESSAMENTO = SigmazC.getPosProcessamento();
            mETAPA_MONTAGEM = SigmazC.getMontagem();


            if (SigmazC.getTerminou()) {
                mContinuar = false;
            }

        } else {
            mPode = true;
        }


        if (mClicavel.getClicado()) {

            int px = (int) mWindows.getMouse().x;
            int py = (int) mWindows.getMouse().y;


            if (BTN_CRIAR.getClicado(px, py)) {

                if (mPode) {
                    mPode = false;

                    System.out.println("Compilar");


                    SigmazC.mostrarDebug(false);

                    String mCompilado = "res/build/Sigmaz.sigmad";
                    String mCompilar = "res/73 - vetores.sigmaz";

                    SigmazC.init(mCompilar, mCompilado, 1);

                    mETAPA_PRE_PROCESSAMENTO = SigmazC.getPreProcessamento();
                    mETAPA_LEXER = SigmazC.getLexer();
                    mETAPA_COMPILER = SigmazC.getCompiler();
                    mETAPA_POS_PROCESSAMENTO = SigmazC.getPosProcessamento();
                    mETAPA_MONTAGEM = SigmazC.getMontagem();

                    mContinuar = true;
                    mPassos = 0;

                } else {

                    System.out.println("Ja existe uma compilacao em andamento -->> AGUARDE !");

                }


            }


        }

    }

    @Override
    public void draw(Graphics g) {

        mWindows.Limpar(g);
        BTN_CRIAR.draw(g);

        g.setColor(Color.RED);

        TextoPequeno.EscreveNegrito(g, "PRE PROCESAMENTO : " + mETAPA_PRE_PROCESSAMENTO, 20, 200);
        TextoPequeno.EscreveNegrito(g, "LEXER : " + mETAPA_LEXER, 20, 250);
        TextoPequeno.EscreveNegrito(g, "COMPILER : " + mETAPA_COMPILER, 20, 300);
        TextoPequeno.EscreveNegrito(g, "POS PROCESSAMENTO : " + mETAPA_POS_PROCESSAMENTO, 20, 350);
        TextoPequeno.EscreveNegrito(g, "MONTAGEM : " + mETAPA_MONTAGEM, 20, 400);


        barra(g, 0, mETAPA_PRE_PROCESSAMENTO);

        barra(g, 1, mETAPA_LEXER);

        barra(g, 2, mETAPA_COMPILER);

        barra(g, 3, mETAPA_POS_PROCESSAMENTO);

        barra(g, 4, mETAPA_MONTAGEM);


    }

    public void barra(Graphics g, int n, String status) {

        int sep = 30;
        int ini = 100;
        int tam = 80;

        if (status.contains("SUCESSO")) {
            g.setColor(new Color(0,128,0));
        } else if (status.contains("EXECUTANDO")) {
            g.setColor(new Color(210,105,30));
        } else if (status.contains("FALHOU")) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLACK);
        }

        g.fillRect(ini + (n * tam) + (n * sep), 500, tam, 50);


    }

}


