package Sigmaz.Intellisenses;

import Sigmaz.S06_Executor.Debuggers.Simplificador;
import Sigmaz.S06_Executor.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import Sigmaz.S00_Utilitarios.AST;

public class IS_Sigmaz {

    private Intellisense mIntellisense;
    private IntellisenseTheme mIntellisenseTheme;

    public IS_Sigmaz(Intellisense eIntellisense, IntellisenseTheme eIntellisenseTheme) {
        mIntellisense = eIntellisense;
        mIntellisenseTheme = eIntellisenseTheme;

    }

    public void sigmaz_raiz(String eLocal, String eTitulo, AST eTudo, int eLargura) {


        int w = eLargura;
        int h = 100 + (mIntellisense.getContagem(eTudo) * 40);


        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(mIntellisenseTheme.getFundo());
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

        Color eBarraSigmaz = mIntellisenseTheme.getSigmaz();
        Color eTexto = mIntellisenseTheme.getTexto();
        Color eBox = mIntellisenseTheme.getBox();
        Color eFundo = mIntellisenseTheme.getFundo();


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

        g.setColor(eFundo);
        g.fillRect(0, 0, w * colunas, h);


        g.setColor(eBarraSigmaz);
        g.fillRect(0, 0, w * colunas, 100);


        g.setColor(eTexto);
        mIntellisense.centerString(g, new Rectangle(0, 0, w * colunas, 100), eTitulo, new Font("TimesRoman", Font.BOLD, 50));




        int mais = 110;
        int anterior = 0;

        Simplificador mSimplificador = new Simplificador();

        boolean tem_definemockiz = false;
        boolean tem_actionfunction = false;
        boolean tem_directoroperator = false;
        boolean tem_autofunctor = false;
        boolean tem_mark = false;

        for (AST Sub2 : eTudo.getASTS()) {
            if (Sub2.mesmoTipo("DEFINE")) {
                tem_definemockiz=true;
            } else if (Sub2.mesmoTipo("MOCKIZ")) {
                tem_definemockiz=true;
            } else if (Sub2.mesmoTipo("ACTION")) {
                tem_actionfunction=true;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                tem_actionfunction=true;
            } else if (Sub2.mesmoTipo("DIRECTOR")) {
                tem_directoroperator = true;
            } else if (Sub2.mesmoTipo("OPERATOR")) {
                tem_directoroperator = true;
            } else if (Sub2.mesmoTipo("PROTOTYPE_AUTO")) {
                tem_autofunctor = true;
            } else if (Sub2.mesmoTipo("PROTOTYPE_FUNCTOR")) {
                tem_autofunctor = true;
            } else if (Sub2.mesmoTipo("MARK")) {
                tem_mark = true;
            }

        }

        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                String eConteudo = mSimplificador.getDefine(Sub2);

                if (indexador >= faixador) {
                    indexador = 0;
                    colunador += 1;
                    mais = 110;
                }

                mais = mIntellisense.algum(Sub2, g, (colunador * eLargura) + 30, mais, eConteudo, mIntellisense.IMG_DEFINE_ALL);

                indexador += 1;
                anterior+=1;

            }

        }


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {
                String eConteudo = mSimplificador.getMockiz(Sub2);

                if (indexador >= faixador) {
                    indexador = 0;
                    colunador += 1;
                    mais = 110;
                }


                mais = mIntellisense.algum(Sub2, g, (colunador * eLargura) + 30, mais, eConteudo, mIntellisense.IMG_MOCKIZ_ALL);
                indexador += 1;
                anterior+=1;

            }

        }


        if (deveSeparar(anterior,tem_actionfunction)){
            mais += 70;
            g.setColor(eBox);
            g.fillRect((colunador * eLargura) + (eLargura / 4), mais, eLargura / 2, 15);
        }



        anterior=0;


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {
                String eConteudo = mSimplificador.getAction(Sub2);

                if (indexador >= faixador) {
                    indexador = 0;
                    colunador += 1;
                    mais = 110;
                }

                g.setColor(eTexto);

                mais = mIntellisense.algum(Sub2, g, (colunador * eLargura) + 30, mais, eConteudo, mIntellisense.IMG_ACTION_ALL);
                indexador += 1;
                anterior += 1;

            }

        }


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("FUNCTION")) {
                String eConteudo = mSimplificador.getFuction(Sub2);

                if (indexador >= faixador) {
                    indexador = 0;
                    colunador += 1;
                    mais = 110;
                }
                g.setColor(eTexto);

                mais = mIntellisense.algum(Sub2, g, (colunador * eLargura) + 30, mais, eConteudo, mIntellisense.IMG_FUNCTION_ALL);
                indexador += 1;
                anterior += 1;

            }

        }



        if (deveSeparar(anterior,tem_autofunctor)){
            mais += 70;
            g.setColor(eBox);
            g.fillRect((colunador * eLargura) + (eLargura / 4), mais, eLargura / 2, 15);
        }


        anterior = 0;


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("PROTOTYPE_AUTO")) {

                String eConteudo =  mSimplificador.getAuto(Sub2) ;

                if (indexador >= faixador) {
                    indexador = 0;
                    colunador += 1;
                    mais = 110;
                }

                g.setColor(eTexto);
                mIntellisense.leftString(g, new Rectangle((colunador * eLargura) + 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_AUTO_ALL);

                mais += 30;
                indexador += 1;
                anterior+=1;

            }
        }

        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("PROTOTYPE_FUNCTOR")) {

                String eConteudo =  mSimplificador.getFunctor(Sub2) ;

                if (indexador >= faixador) {
                    indexador = 0;
                    colunador += 1;
                    mais = 110;
                }

                g.setColor(eTexto);
                mIntellisense.leftString(g, new Rectangle((colunador * eLargura) + 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_FUNCTOR_ALL);

                mais += 30;
                indexador += 1;
                anterior+=1;

            }
        }


        if (anterior > 0) {

            if (tem_directoroperator) {

                mais += 70;

                g.setColor(eBox);
                g.fillRect((colunador * eLargura) + (eLargura / 4), mais, eLargura / 2, 15);

            }


        }


        anterior = 0;

        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("DIRECTOR")) {

                String eConteudo = mSimplificador.getDirector(Sub2);

                if (indexador >= faixador) {
                    indexador = 0;
                    colunador += 1;
                    mais = 110;
                }

                g.setColor(eTexto);
                mIntellisense.leftString(g, new Rectangle((colunador * eLargura) + 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_DIRECTOR_ALL);

                mais += 30;
                indexador += 1;
                anterior += 1;

            }
        }

        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("OPERATOR")) {

                String eConteudo = mSimplificador.getOperator(Sub2);

                if (indexador >= faixador) {
                    indexador = 0;
                    colunador += 1;
                    mais = 110;
                }

                g.setColor(eTexto);
                mIntellisense.leftString(g, new Rectangle((colunador * eLargura) + 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_OPERATOR_ALL);

                mais += 30;
                indexador += 1;
                anterior += 1;

            }
        }

        if (deveSeparar(anterior,tem_mark)){
            mais += 70;
            g.setColor(eBox);
            g.fillRect((colunador * eLargura) + (eLargura / 4), mais, eLargura / 2, 15);
        }


        anterior = 0;

        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("MARK")) {

                String eConteudo =  mSimplificador.getMark(Sub2) ;

                if (indexador >= faixador) {
                    indexador = 0;
                    colunador += 1;
                    mais = 110;
                }

                g.setColor(eTexto);
                mIntellisense.leftString(g, new Rectangle((colunador * eLargura) + 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_MARK);

                mais += 30;
                indexador += 1;
                anterior+=1;

            }
        }


        try {
            ImageIO.write(img, "png", new File(eLocal + eTitulo + ".png"));
        } catch (IOException o) {

        }

    }


    public boolean deveSeparar(int anterior,boolean tem){

        boolean ret = false;

        if (anterior > 0) {

            if (tem) {

                ret=true;

            }
        }

        return ret;
    }

}
