package Sigmaz.Intellisenses;

import Sigmaz.Executor.Utils;
import Sigmaz.Utils.AST;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IS_Model {

    private Intellisense mIntellisense;

    public IS_Model(Intellisense eIntellisense){
        mIntellisense=eIntellisense;

    }

    public void model(String eLocal, String eTitulo, AST eTudo, int x,int eLargura) {

        int c = 0;

        int h = 100 + ((mIntellisense.getContagem(eTudo) + c) * 40) ;

        BufferedImage img = new BufferedImage(eLargura, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        continuar(g, eTudo,eTitulo,x,0,h,eLargura);


        try {
            File outputfile = new File(eLocal + eTitulo + ".png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {

        }

    }

    public int continuar( Graphics g , AST eTudo, String eTitulo,int x, int mais,int eLargura,int altura){


        g.setColor(Color.WHITE);
        g.fillRect(x, mais, eLargura, altura);


        g.setColor(new Color(155, 89, 182));
        g.fillRect(x, mais, mIntellisense.getLargura(), 100);


        g.setColor(Color.BLACK);
        mIntellisense.centerString(g, new Rectangle(x, mais, eLargura, 100), eTitulo, new Font("TimesRoman", Font.BOLD, 50));

        mais+=110;


        g.setColor(Color.BLACK);


        Utils mUtils = new Utils();


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                String eConteudo = mUtils.getDefine(Sub2);
                mais = mIntellisense.algum(Sub2, g,x,  mais, eConteudo, mIntellisense.IMG_DEFINE_ALL);
            }

        }


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {
                String eConteudo = mUtils.getMockiz(Sub2);
                mais = mIntellisense.algum(Sub2, g,x,  mais, eConteudo, mIntellisense.IMG_MOCKIZ_ALL);
            }

        }


        mais+=70;

        g.setColor(Color.BLACK);
        g.fillRect(x+ 50, mais , eLargura/2, 15);


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {
                String eConteudo = mUtils.getModelAction(Sub2);
                mais = mIntellisense.algum(Sub2, g, x, mais, eConteudo, mIntellisense.IMG_ACTION_ALL);
            }

        }


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("FUNCTION")) {
                String eConteudo = mUtils.getModelFuction(Sub2);
                mais = mIntellisense.algum(Sub2, g,x,  mais, eConteudo, mIntellisense.IMG_FUNCTION_ALL);
            }

        }


        mais+=50;

        return mais;
    }
}
