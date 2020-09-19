package Sigmaz.Intellisenses;

import Sigmaz.Executor.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import Sigmaz.Utils.AST;

public class IS_Sigmaz {

    private Intellisense mIntellisense;

    public IS_Sigmaz(Intellisense eIntellisense) {
        mIntellisense = eIntellisense;

    }

    public void sigmaz_raiz(String eLocal, String eTitulo, AST eTudo, int eLargura) {


        int w = eLargura;
        int h = 100 + (mIntellisense.getContagem(eTudo) * 40);


        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);


        g.setColor(Color.RED);
        g.fillRect(0, 0, w, 100);


        g.setColor(Color.BLACK);
        mIntellisense.centerString(g, new Rectangle(0, 0, w, 100), eTitulo, new Font("TimesRoman", Font.BOLD, 50));


        Utils mUtils = new Utils();

        int p = 0;

        int mais = 110;


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                String eConteudo = mUtils.getDefine(Sub2);
                mais = mIntellisense.algum(Sub2, g, mais, eConteudo, mIntellisense.IMG_DEFINE_ALL);
                p += 1;
            }

        }


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {
                String eConteudo = mUtils.getMockiz(Sub2);
                mais = mIntellisense.algum(Sub2, g, mais, eConteudo, mIntellisense.IMG_MOCKIZ_ALL);
                p += 1;
            }

        }


        int ne = 0;

        for (AST Sub2 : eTudo.getASTS()) {
            if (Sub2.mesmoTipo("ACTION")) {
                ne += 1;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                ne += 1;
            }

        }


        if (p > 0 && ne > 0) {
            mais += 70;

            g.setColor(Color.BLACK);
            g.fillRect(eLargura / 4, mais, eLargura / 2, 15);


        }

        int p2 = 0;


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {
                String eConteudo = mUtils.getAction(Sub2);
                mais = mIntellisense.algum(Sub2, g, mais, eConteudo, mIntellisense.IMG_ACTION_ALL);
                p2 += 1;
            }

        }


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("FUNCTION")) {
                String eConteudo = mUtils.getFuction(Sub2);
                mais = mIntellisense.algum(Sub2, g, mais, eConteudo, mIntellisense.IMG_FUNCTION_ALL);
                p2 += 1;
            }

        }


        int e = 0;

        for (AST Sub2 : eTudo.getASTS()) {
            if (Sub2.mesmoTipo("DEFINE")) {
                e += 1;
            } else if (Sub2.mesmoTipo("MOCKIZ")) {
                e += 1;
            } else if (Sub2.mesmoTipo("ACTION")) {
                e += 1;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                e += 1;
            }

        }

        if (p2 > 0 && e > 0) {

            mais += 70;

            g.setColor(Color.BLACK);
            g.fillRect(eLargura / 4, mais, eLargura / 2, 15);

        }


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("DIRECTOR")) {

                String eConteudo = mUtils.getFuction(Sub2);

                g.setColor(Color.BLACK);
                mIntellisense.leftString(g, new Rectangle(30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_DIRECTOR_ALL);

                mais += 30;

            }
        }

        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("OPERATOR")) {

                String eConteudo = mUtils.getFuction(Sub2);

                g.setColor(Color.BLACK);
                mIntellisense.leftString(g, new Rectangle(30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_OPERATOR_ALL);

                mais += 30;

            }
        }


        try {
            File outputfile = new File(eLocal + eTitulo + ".png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException o) {

        }

    }

    public void sigmaz_raiz_horizontal(String eLocal, String eTitulo, AST eTudo, int eLargura) {


        int w = eLargura;
        int c = mIntellisense.getContagem(eTudo);

        int faixador = 50;

        int ci = c;
        int colunas = 1;
        while (ci >= faixador) {
            ci -= faixador;
            colunas += 1;
        }

        //System.out.println("Colunas : " + colunas);

        // int h = 100 + (c* 40);

        int h = 100 + (faixador * 40);

        int indexador = 0;
        int colunador = 0;


        BufferedImage img = new BufferedImage(w * colunas, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w* colunas, h);


        g.setColor(Color.RED);
        g.fillRect(0, 0, w* colunas, 100);


        g.setColor(Color.BLACK);
        mIntellisense.centerString(g, new Rectangle(0, 0, w * colunas, 100), eTitulo, new Font("TimesRoman", Font.BOLD, 50));


        Utils mUtils = new Utils();

        int p = 0;

        int mais = 110;


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                String eConteudo = mUtils.getDefine(Sub2);

                if (indexador >= faixador) {
                    indexador = 0;
                    colunador += 1;
                    mais=110;
                }

                mais = mIntellisense.algum(Sub2,g, (colunador*eLargura)+30, mais, eConteudo, mIntellisense.IMG_DEFINE_ALL);

                p += 1;
                indexador += 1;
            }

        }


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {
                String eConteudo = mUtils.getMockiz(Sub2);

                if (indexador >= faixador) {
                    indexador = 0;
                    colunador += 1;
                    mais=110;
                }


                mais = mIntellisense.algum(Sub2,g, (colunador*eLargura)+30, mais, eConteudo, mIntellisense.IMG_MOCKIZ_ALL);
                p += 1;
                indexador += 1;
            }

        }


        int ne = 0;

        for (AST Sub2 : eTudo.getASTS()) {
            if (Sub2.mesmoTipo("ACTION")) {
                ne += 1;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                ne += 1;
            }

        }


        if (p > 0 && ne > 0) {
            mais += 70;

            g.setColor(Color.BLACK);
            g.fillRect((colunador*eLargura) + (eLargura / 4), mais, eLargura / 2, 15);


        }

        int p2 = 0;


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {
                String eConteudo = mUtils.getAction(Sub2);

                if (indexador >= faixador) {
                    indexador = 0;
                    colunador += 1;
                    mais=110;
                }

                mais = mIntellisense.algum(Sub2,g, (colunador*eLargura)+30, mais, eConteudo, mIntellisense.IMG_ACTION_ALL);
                p2 += 1;
                indexador += 1;
            }

        }


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("FUNCTION")) {
                String eConteudo = mUtils.getFuction(Sub2);

                if (indexador >= faixador) {
                    indexador = 0;
                    colunador += 1;
                    mais=110;
                }

                mais = mIntellisense.algum(Sub2,g, (colunador*eLargura)+30, mais, eConteudo, mIntellisense.IMG_FUNCTION_ALL);
                p2 += 1;
                indexador += 1;
            }

        }


        int e = 0;

        for (AST Sub2 : eTudo.getASTS()) {
            if (Sub2.mesmoTipo("DEFINE")) {
                e += 1;
            } else if (Sub2.mesmoTipo("MOCKIZ")) {
                e += 1;
            } else if (Sub2.mesmoTipo("ACTION")) {
                e += 1;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                e += 1;
            }

        }

        if (p2 > 0 && e > 0) {

            mais += 70;

            g.setColor(Color.BLACK);
            g.fillRect((colunador*eLargura) + (eLargura / 4), mais, eLargura / 2, 15);

        }


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("DIRECTOR")) {

                String eConteudo = mUtils.getFuction(Sub2);

                if (indexador >= faixador) {
                    indexador = 0;
                    colunador += 1;
                    mais=110;
                }

                g.setColor(Color.BLACK);
                mIntellisense.leftString(g, new Rectangle((colunador*eLargura)+30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_DIRECTOR_ALL);

                mais += 30;
                indexador += 1;

            }
        }

        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("OPERATOR")) {

                String eConteudo = mUtils.getFuction(Sub2);

                if (indexador >= faixador) {
                    indexador = 0;
                    colunador += 1;
                    mais=110;
                }

                g.setColor(Color.BLACK);
                mIntellisense.leftString(g, new Rectangle((colunador*eLargura)+30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_OPERATOR_ALL);

                mais += 30;
                indexador += 1;

            }
        }


        try {
            File outputfile = new File(eLocal + eTitulo + ".png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException o) {

        }

    }

}
