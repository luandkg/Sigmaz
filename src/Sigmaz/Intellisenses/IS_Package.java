package Sigmaz.Intellisenses;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import Sigmaz.S00_Utilitarios.AST;

public class IS_Package {

    private Intellisense mIntellisense;
    private IntellisenseTheme mIntellisenseTheme;

    public IS_Package(Intellisense eIntellisense,IntellisenseTheme eIntellisenseTheme) {

        mIntellisense = eIntellisense;
        mIntellisenseTheme=eIntellisenseTheme;

    }

    public void sigmaz_package(String eLocal, String eTitulo, AST eTudo, int eLargura) {


        Color eBarraPackage = mIntellisenseTheme.getPackage();
        Color eTexto=mIntellisenseTheme.getTexto();
        Color eBox=mIntellisenseTheme.getBox();


        int h = 0;

        IS_Struct mIS = new IS_Struct(mIntellisense,mIntellisenseTheme);

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

        g.setColor(mIntellisenseTheme.getFundo());
        g.fillRect(0, 0, eLargura, h);


        g.setColor(eBarraPackage);
        g.fillRect(0, 0, eLargura, 100);


        g.setColor(eTexto);
        mIntellisense.centerString(g, new Rectangle(0, 0, eLargura, 100), eTitulo, new Font("TimesRoman", Font.BOLD, 50));


        int mais = 110;


        g.setColor(Color.BLACK);



        for (AST Sub3 : eTudo.getASTS()) {

            if (Sub3.mesmoTipo("TYPE")) {

                //  IS_Type mIT = new IS_Type(mIntellisense);

                mais = mIS.continuar(g, Sub3, Sub3.getNome(), 0, mais, eLargura, h);

            } else if (Sub3.mesmoTipo("STRUCT")) {


                mais = mIS.continuar(g, Sub3, Sub3.getNome(), 0, mais, eLargura, h);


            } else if (Sub3.mesmoTipo("CAST")) {

                IS_Cast mIC = new IS_Cast(mIntellisense,mIntellisenseTheme);


                mais = mIC.continuar(g, Sub3, Sub3.getNome(), 0, mais, eLargura, h);

            } else if (Sub3.mesmoTipo("MODEL")) {

                IS_Model mIC = new IS_Model(mIntellisense,mIntellisenseTheme);

                mais = mIC.continuar(g, Sub3, Sub3.getNome(), 50, mais, eLargura, h);


            } else {


            }


        }


        try {
            File outputfile = new File(eLocal + eTitulo + ".png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {

        }

    }

    public void sigmaz_package_horizontal(String eLocal, String eTitulo, AST eTudo, int eLargura) {


        eLargura += 30;


        Color eBarraPackage = mIntellisenseTheme.getPackage();
        Color eTexto=mIntellisenseTheme.getTexto();
        Color eBox=mIntellisenseTheme.getBox();
        Color eFundo=mIntellisenseTheme.getFundo();


        IS_Struct mIS = new IS_Struct(mIntellisense,mIntellisenseTheme);

        int colunas = 0;
        int altura = 200;

        int alturaMinima = 200;

        for (AST Sub3 : eTudo.getASTS()) {


            if (Sub3.mesmoTipo("STRUCT")) {
                int h = alturaMinima + ((mIS.getContagem(Sub3)) * 80);

                if (Sub3.getBranch("EXTENDED").mesmoNome("STAGES")) {

                    h += 100;

                }

                colunas += 1;

                if (h > altura) {
                    altura = h;
                }

            } else if (Sub3.mesmoTipo("CAST")) {
                int h = alturaMinima + ((mIntellisense.getContagem(Sub3)) * 80);

                colunas += 1;
                if (h > altura) {
                    altura = h;
                }
            } else if (Sub3.mesmoTipo("MODEL")) {
                int h = alturaMinima + ((mIntellisense.getContagem(Sub3)) * 80);

                colunas += 1;
                if (h > altura) {
                    altura = h;
                }

            } else {
            }

        }

        if (colunas == 0) {
            colunas += 1;
        }

        int IMAGEM_LARGURA = (eLargura * colunas) - 30;
        int IMAGEM_ALTURA = altura;

        // System.out.println("Alocar Imagem Largura : " + IMAGEM_LARGURA + " Altura : " + IMAGEM_ALTURA);

        BufferedImage img = new BufferedImage(IMAGEM_LARGURA, IMAGEM_ALTURA, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(eFundo);
        g.fillRect(0, 0, eLargura * colunas, altura);


        g.setColor(eBarraPackage);
        g.fillRect(0, 0, eLargura * colunas, 100);


        g.setColor(eTexto);
        mIntellisense.centerString(g, new Rectangle(0, 0, eLargura * colunas, 100), eTitulo, new Font("TimesRoman", Font.BOLD, 50));








        int colunando = 0;

        for (AST Sub3 : eTudo.getASTS()) {

            //   System.out.println("\t INTELLISENSE " + eTitulo + " -> " + Sub3.getNome() + " :: " + colunando);

            if (Sub3.mesmoTipo("STRUCT")) {


                 mIS.continuar(g, Sub3, Sub3.getNome(), colunando * eLargura, 110, eLargura, altura);

                colunando += 1;
            } else if (Sub3.mesmoTipo("CAST")) {

                IS_Cast mIC = new IS_Cast(mIntellisense,mIntellisenseTheme);


                mIC.continuar(g, Sub3, Sub3.getNome(), colunando * eLargura, 110, eLargura, altura);
                colunando += 1;
            } else if (Sub3.mesmoTipo("MODEL")) {

                IS_Model mIC = new IS_Model(mIntellisense,mIntellisenseTheme);

               mIC.continuar(g, Sub3, Sub3.getNome(), (colunando * eLargura) + 20, 110, eLargura, altura);

                colunando += 1;

            } else {


            }


        }


        try {
            ImageIO.write(img, "png", new File(eLocal + eTitulo + ".png"));
        } catch (IOException e) {

        }

    }
}
