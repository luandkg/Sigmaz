package Sigmaz.Intellisenses;

import Sigmaz.Executor.Utils;
import Sigmaz.Utils.AST;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IS_Package {

    private Intellisense mIntellisense;

    public IS_Package(Intellisense eIntellisense) {
        mIntellisense = eIntellisense;

    }

    public void sigmaz_package(String eLocal, String eTitulo, AST eTudo, int eLargura) {


        Color eBarra = new Color(52, 73, 94);

        int h = 0;

        IS_Struct mIS = new IS_Struct(mIntellisense);

        for (AST Sub3 : eTudo.getASTS()) {


            if (Sub3.mesmoTipo("STRUCT")) {
                h += ((mIS.getContagem(Sub3)) * 80);

                if (Sub3.getBranch("EXTENDED").mesmoNome("STAGES")) {

                    h += 100;

                }
            } else if (Sub3.mesmoTipo("TYPE")) {
                h += ((mIntellisense.getContagem(Sub3)) * 80);
            } else if (Sub3.mesmoTipo("CAST")) {
                h += ((mIntellisense.getContagem(Sub3)) * 80);
            } else if (Sub3.mesmoTipo("TYPE")) {
                h += ((mIntellisense.getContagem(Sub3)) * 80);

            } else {
            }

        }


        BufferedImage img = new BufferedImage(eLargura, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, eLargura, h);


        g.setColor(eBarra);
        g.fillRect(0, 0, eLargura, 100);


        g.setColor(Color.BLACK);
        mIntellisense.centerString(g, new Rectangle(0, 0, eLargura, 100), eTitulo, new Font("TimesRoman", Font.BOLD, 50));


        int mais = 110;


        g.setColor(Color.BLACK);


        Utils mUtils = new Utils();

        int fase = 0;

        for (AST Sub3 : eTudo.getASTS()) {

            if (Sub3.mesmoTipo("TYPE")) {

                //  IS_Type mIT = new IS_Type(mIntellisense);

                mais = mIS.continuar(g, Sub3, Sub3.getNome(), 0, mais, eLargura, h);

            } else if (Sub3.mesmoTipo("STRUCT")) {


                mais = mIS.continuar(g, Sub3, Sub3.getNome(), 0, mais, eLargura, h);


            } else if (Sub3.mesmoTipo("CAST")) {

                IS_Cast mIC = new IS_Cast(mIntellisense);


                mais = mIC.continuar(g, Sub3, Sub3.getNome(), 0, mais, eLargura, h);

            } else if (Sub3.mesmoTipo("MODEL")) {

                IS_Model mIC = new IS_Model(mIntellisense);

                mais = mIC.continuar(g, Sub3, Sub3.getNome(), 0, mais, eLargura, h);


            } else {


            }


            fase += 1;
        }


        try {
            File outputfile = new File(eLocal + eTitulo + ".png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {

        }

    }

    public void sigmaz_package_horizontal(String eLocal, String eTitulo, AST eTudo, int eLargura) {


        eLargura += 30;

        Color eBarra = new Color(52, 73, 94);


        IS_Struct mIS = new IS_Struct(mIntellisense);

        int colunas = 0;
        int altura = 100;

        for (AST Sub3 : eTudo.getASTS()) {


            if (Sub3.mesmoTipo("STRUCT")) {
                int h = ((mIS.getContagem(Sub3)) * 80);

                if (Sub3.getBranch("EXTENDED").mesmoNome("STAGES")) {

                    h += 100;

                }

                colunas += 1;

                if (h>altura){
                    altura=h;
                }

            } else if (Sub3.mesmoTipo("CAST")) {
                int h = ((mIntellisense.getContagem(Sub3)) * 80);

                colunas += 1;
                if (h>altura){
                    altura=h;
                }

            } else {
            }

        }

        if (colunas == 0) {
            colunas += 1;
        }

        BufferedImage img = new BufferedImage((eLargura * colunas)-30, altura, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, eLargura * colunas, altura);


        g.setColor(eBarra);
        g.fillRect(0, 0, eLargura * colunas, 100);


        g.setColor(Color.BLACK);
        mIntellisense.centerString(g, new Rectangle(0, 0, eLargura * colunas, 100), eTitulo, new Font("TimesRoman", Font.BOLD, 50));


        int mais = 110;


        g.setColor(Color.BLACK);


        Utils mUtils = new Utils();

        int fase = 0;

        int colunando = 0;

        for (AST Sub3 : eTudo.getASTS()) {


            if (Sub3.mesmoTipo("STRUCT")) {


                mais = mIS.continuar(g, Sub3, Sub3.getNome(), colunando * eLargura, 110, eLargura, altura);

                colunando += 1;
            } else if (Sub3.mesmoTipo("CAST")) {

                IS_Cast mIC = new IS_Cast(mIntellisense);


                mais = mIC.continuar(g, Sub3, Sub3.getNome(), colunando * eLargura, 110, eLargura, altura);
                colunando += 1;
            } else if (Sub3.mesmoTipo("MODEL")) {

                IS_Model mIC = new IS_Model(mIntellisense);

                mais = mIC.continuar(g, Sub3, Sub3.getNome(), colunando * eLargura, 110, eLargura, altura);

                colunando += 1;
            } else {


            }


            fase += 1;
        }


        try {
            File outputfile = new File(eLocal + eTitulo + ".png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {

        }

    }
}
