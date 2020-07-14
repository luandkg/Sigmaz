package Sigmaz.Intellisenses;

import Sigmaz.Executor.Utils;
import Sigmaz.Utils.AST;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IS_Struct {

    private Intellisense mIntellisense;

    public IS_Struct(Intellisense eIntellisense) {
        mIntellisense = eIntellisense;

    }

    public int getContagem(AST eTudo) {

        int contador = mIntellisense.getContagem(eTudo.getBranch("BODY"));

        if (eTudo.getBranch("EXTENDED").mesmoNome("STAGES")) {

            for (AST Sub2 : eTudo.getBranch("STAGES").getASTS()) {
                contador += 1;
            }


        } else if (eTudo.getBranch("EXTENDED").mesmoNome("STRUCT")) {

            for (AST Sub2 : eTudo.getBranch("INITS").getASTS()) {
                if (Sub2.mesmoNome(eTudo.getNome())) {
                    contador += 1;
                }

            }

            contador += mIntellisense.getContagemGenericos(eTudo.getBranch("GENERIC"));
            contador += mIntellisense.getContagemGenericos(eTudo.getBranch("BASES"));

            if (eTudo.getBranch("MODEL").mesmoValor("TRUE")) {
                contador += 1;
            }

        }

        return contador;
    }


    public int continuar(Graphics g, AST eTudo, String eTitulo,int x, int mais, int eLargura, int altura) {


        Color eBarra = Color.RED;

        if (eTudo.getBranch("EXTENDED").mesmoNome("STAGES")) {


            eBarra = new Color(255, 110, 64);


        } else if (eTudo.getBranch("EXTENDED").mesmoNome("TYPE")) {

            eBarra = new Color(64, 196, 255);


        } else if (eTudo.getBranch("EXTENDED").mesmoNome("STRUCT")) {

            eBarra = new Color(124, 179, 66);


        } else if (eTudo.getBranch("EXTENDED").mesmoNome("EXTERNAL")) {

            eBarra = new Color(22, 160, 133);


        } else {


        }


        g.setColor(Color.WHITE);
        g.fillRect(x, mais, mIntellisense.getLargura(), altura - mais);


        g.setColor(eBarra);
        g.fillRect(x, mais, mIntellisense.getLargura(), 100);


        g.setColor(Color.BLACK);
        mIntellisense.centerString(g, new Rectangle(x, mais, eLargura, 100), eTitulo, new Font("TimesRoman", Font.BOLD, 50));


        mais += 110;


        if (eTudo.getBranch("EXTENDED").mesmoNome("STAGES")) {

            for (AST Sub2 : eTudo.getBranch("STAGES").getASTS()) {
                String eConteudo = Sub2.getNome();

                g.setColor(Color.BLACK);
                mIntellisense.leftString(g, new Rectangle(x+30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_STAGE);

                mais += 30;

            }

            mais += 50;

            g.setColor(Color.BLACK);
            g.fillRect(x+ 50, mais, eLargura / 2, 15);

            g.setColor(Color.BLACK);

            mais = mIntellisense.colocarGlobal2(x,mais, g, eTudo.getBranch("BODY"));

            mais += 50;

        } else if (eTudo.getBranch("EXTENDED").mesmoNome("TYPE")) {

            Utils mUtils = new Utils();

            if (eTudo.getBranch("GENERIC").mesmoNome("TRUE")) {


                for (AST Sub2 : eTudo.getBranch("GENERIC").getASTS()) {

                    String eConteudo = Sub2.getNome();

                    g.setColor(Color.BLACK);
                    mIntellisense.leftString(g, new Rectangle(x+30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_GENERIC_TYPE);

                    mais += 30;

                }

                mais += 70;

                g.setColor(Color.BLACK);
                g.fillRect(x+ 50, mais, eLargura / 2, 15);


            }


            for (AST Sub2 : eTudo.getBranch("BODY").getASTS()) {

                if (Sub2.mesmoTipo("DEFINE")) {
                    String eConteudo = mUtils.getDefine(Sub2);
                    mais = mIntellisense. algum(Sub2, g, x,mais, "ALL", eConteudo, mIntellisense.IMG_DEFINE_TYPE);
                  //  p += 1;
                }

            }


            for (AST Sub2 : eTudo.getBranch("BODY").getASTS()) {

                if (Sub2.mesmoTipo("MOCKIZ")) {
                    String eConteudo = mUtils.getMockiz(Sub2);
                    mais = mIntellisense.algum(Sub2, g, mais, x,"ALL", eConteudo, mIntellisense.IMG_MOCKIZ_TYPE);
                    //p += 1;
                }

            }

            mais += 50;

        } else {

            Utils mUtils = new Utils();

            if (eTudo.getBranch("MODEL").mesmoValor("TRUE")) {

                String eConteudo = eTudo.getBranch("MODEL").getNome();

                g.setColor(Color.BLACK);
                mIntellisense.leftString(g, new Rectangle(x+30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_MODEL);

                mais += 30;

                mais += 70;

                g.setColor(Color.BLACK);
                g.fillRect(x+ 50, mais, eLargura / 2, 15);


            }

            if (eTudo.getBranch("WITH").mesmoValor("TRUE")) {

                for (AST Sub2 : eTudo.getBranch("BASES").getASTS()) {

                    String eConteudo = Sub2.getNome();

                    g.setColor(Color.BLACK);
                    mIntellisense.leftString(g, new Rectangle(x+30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_BASE);

                    mais += 30;

                }

                mais += 70;

                g.setColor(Color.BLACK);
                g.fillRect(x+ 50, mais, eLargura / 2, 15);


            }

            if (eTudo.getBranch("GENERIC").mesmoNome("TRUE")) {


                for (AST Sub2 : eTudo.getBranch("GENERIC").getASTS()) {

                    String eConteudo = Sub2.getNome();

                    g.setColor(Color.BLACK);
                    mIntellisense.leftString(g, new Rectangle(x+30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_GENERIC);

                    mais += 30;

                }

                mais += 70;

                g.setColor(Color.BLACK);
                g.fillRect(x+ 50, mais, eLargura / 2, 15);


            }

            int inits = 0;


            for (AST Sub2 : eTudo.getBranch("INITS").getASTS()) {

                if (Sub2.mesmoTipo("INIT")) {

                    if (Sub2.mesmoNome(eTudo.getNome())) {

                        String eConteudo = mUtils.getAction(Sub2);

                        g.setColor(Color.BLACK);
                        mIntellisense.leftString(g, new Rectangle(x+30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_INIT);

                        mais += 30;
                        inits += 1;

                    }

                }

            }

            if (inits > 0) {
                mais += 70;

                g.setColor(Color.BLACK);
                g.fillRect(x+ 50, mais, eLargura / 2, 15);

            }


            g.setColor(Color.BLACK);

            mais = mIntellisense.colocarGlobal(x,mais, g, eTudo.getBranch("BODY"));

        }

        return mais;

    }


}
