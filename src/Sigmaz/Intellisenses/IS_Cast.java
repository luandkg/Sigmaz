package Sigmaz.Intellisenses;

import Sigmaz.Utils.AST;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IS_Cast {

    private Intellisense mIntellisense;

    public IS_Cast(Intellisense eIntellisense) {
        mIntellisense = eIntellisense;

    }

    public void sigmaz_cast(String eLocal, String eTitulo, AST eTudo, int eLargura) {

        int c = 0;

        int h = 100 + ((mIntellisense.getContagem(eTudo) + c) * 40);

        BufferedImage img = new BufferedImage(eLargura, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        continuar(g, eTudo, eTitulo, 0,0, eLargura, h);


        try {
            File outputfile = new File(eLocal + eTitulo + ".png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {

        }

    }

    public int continuar(Graphics g, AST eTudo, String eTitulo, int x, int mais, int eLargura, int altura) {


        Color eBarra = new Color(241, 196, 15);


        g.setColor(Color.WHITE);
        g.fillRect(x, mais,  mIntellisense.getLargura(), altura);


        g.setColor(eBarra);
        g.fillRect(x, mais,  mIntellisense.getLargura(), 100);


        g.setColor(Color.BLACK);

        mIntellisense.centerString(g, new Rectangle(x, mais, eLargura, 100), eTitulo, new Font("TimesRoman", Font.BOLD, 50));


        mais += 110;


        g.setColor(Color.BLACK);


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("GETTER")) {
                String eConteudo =  eTudo.getNome() + " <<-- " + Sub2.getValor() ;
                mIntellisense.leftString(g, new Rectangle(x+30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_GETTER);
                mais += 30;
            }

        }


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("SETTER")) {
                String eConteudo =eTudo.getNome()+ " -->> " + Sub2.getValor();
                mIntellisense.leftString(g, new Rectangle(x+30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_SETTER);
                mais += 30;
            }

        }

        mais += 50;

        return mais;
    }
}
