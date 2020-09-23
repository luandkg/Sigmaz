package Sigmaz.Intellisenses;

import Sigmaz.Executor.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import Sigmaz.Utils.AST;

public class IS_Model {

    private Intellisense mIntellisense;
    private IntellisenseTheme mIntellisenseTheme;

    public IS_Model(Intellisense eIntellisense,IntellisenseTheme eIntellisenseTheme) {
        mIntellisense = eIntellisense;
        mIntellisenseTheme=eIntellisenseTheme;

    }

    public void model(String eLocal, String eTitulo, AST eTudo, int x, int eLargura) {

        int c = 0;

        int h = 100 + ((mIntellisense.getContagem(eTudo) + c) * 40);

        BufferedImage img = new BufferedImage(eLargura, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        continuar(g, eTudo, eTitulo, x, 0, h, eLargura);


        try {
            File outputfile = new File(eLocal + eTitulo + ".png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {

        }

    }

    public int continuar(Graphics g, AST eTudo, String eTitulo, int x, int mais, int eLargura, int altura) {


        g.setColor(mIntellisenseTheme.getFundo());
        g.fillRect(x, mais, eLargura, altura);


//new Color(155, 89, 182)
        Color eBarra = mIntellisenseTheme.getModel();
        Color eTexto=mIntellisenseTheme.getTexto();
        Color eBox=mIntellisenseTheme.getBox();


        g.setColor(eBarra);



        g.fillRect(x, mais, mIntellisense.getLargura(), 100);


        g.setColor(eTexto);
        mIntellisense.centerString(g, new Rectangle(x, mais, eLargura, 100), eTitulo, new Font("TimesRoman", Font.BOLD, 50));

        mais += 110;


        g.setColor(eTexto);


        Utils mUtils = new Utils();


        if (eTudo.getBranch("GENERIC").mesmoNome("TRUE")) {


            for (AST Sub2 : eTudo.getBranch("GENERIC").getASTS()) {

                String eConteudo = Sub2.getNome();

                g.setColor(eTexto);
                mIntellisense.leftString(g, new Rectangle(x + 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_GENERIC_TYPE);

                mais += 30;

            }

            mais += 70;

            g.setColor(eBox);
            g.fillRect(x + 50, mais, eLargura / 2, 15);


        }


        int temSegundo = 0;

        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {

                g.setColor(eTexto);

                String eConteudo = mUtils.getDefine(Sub2);
                mais = mIntellisense.algum(Sub2, g, x, mais, eConteudo, mIntellisense.IMG_DEFINE_ALL);
                temSegundo += 1;
            }

        }


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {

                g.setColor(eTexto);

                String eConteudo = mUtils.getMockiz(Sub2);
                mais = mIntellisense.algum(Sub2, g, x, mais, eConteudo, mIntellisense.IMG_MOCKIZ_ALL);
                temSegundo += 1;
            }

        }

        if (temSegundo > 0) {
            mais += 70;

            g.setColor(eBox);
            g.fillRect(x + 50, mais, eLargura / 2, 15);

        }


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {

                g.setColor(eTexto);

                String eConteudo = mUtils.getModelAction(Sub2);
                mais = mIntellisense.algum(Sub2, g, x, mais, eConteudo, mIntellisense.IMG_ACTION_ALL);
            }

        }


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("FUNCTION")) {

                g.setColor(eTexto);

                String eConteudo = mUtils.getModelFuction(Sub2);
                mais = mIntellisense.algum(Sub2, g, x, mais, eConteudo, mIntellisense.IMG_FUNCTION_ALL);
            }

        }


        mais += 50;

        return mais;
    }
}
