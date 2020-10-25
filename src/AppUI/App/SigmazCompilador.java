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

    private boolean mPode;

    private  boolean mContinuar;
    private Sigmaz_Etapas SigmazC;


    public SigmazCompilador(Windows eWindows) {
        mWindows = eWindows;

        TextoGrande = new Escritor(30, Color.BLACK);
        TextoPequeno = new Escritor(15, Color.BLACK);

        mClicavel = new Clicavel();

        BTN_CRIAR = new BotaoCor(300, 50, 100, 100, new Color(120, 50, 156));

        mPode = true;
        mContinuar = false;
        SigmazC = new Sigmaz_Etapas();

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

        //   System.out.println(SigmazC.getContinuar() + " :: " + "Passo : " + SigmazC.getPassos() + " -->> " + SigmazC.getEtapa() + " :: " + SigmazC.getSubEtapa());


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


                    SigmazC.mostrarDebug(false);

                    String mCompilado = "res/build/Sigmaz.sigmad";
                    String mCompilar = "res/73 - vetores.sigmaz";

                    SigmazC.init(mCompilar, mCompilado, 1);


                    mContinuar = true;

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

        TextoPequeno.EscreveNegrito(g, "PRE PROCESAMENTO : " + SigmazC.getPreProcessamento(), 20, 200);
        TextoPequeno.EscreveNegrito(g, "LEXER : " + SigmazC.getLexer(), 20, 250);
        TextoPequeno.EscreveNegrito(g, "PARSER : " + SigmazC.getCompiler(), 20, 300);
        TextoPequeno.EscreveNegrito(g, "POS PROCESSAMENTO : " +  SigmazC.getPosProcessamento(), 20, 350);
        TextoPequeno.EscreveNegrito(g, "MONTAGEM : " + SigmazC.getMontagem(), 20, 400);


        barra(g, 0, SigmazC.getPreProcessamento());

        barra(g, 1, SigmazC.getLexer());

        barra(g, 2, SigmazC.getCompiler());

        barra(g, 3, SigmazC.getPosProcessamento());

        barra(g, 4, SigmazC.getMontagem());


    }

    public void barra(Graphics g, int n, String status) {

        int sep = 30;
        int ini = 100;
        int tam = 80;

        if (status.contains("SUCESSO")) {
            g.setColor(new Color(0,128,0));
        } else if (status.contains("EXECUTANDO")) {
            g.setColor(new Color(255,225,53));
        } else if (status.contains("FALHOU")) {
            g.setColor(new Color(255,105,97));
        } else {
            g.setColor(new Color(105,105,105));
        }

        g.fillRect(ini + (n * tam) + (n * sep), 500, tam, 50);


    }

}


