package Sigmaz.Executor;

import Sigmaz.Utils.AST;
import Sigmaz.Utils.Documento;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Intellisense {

    BufferedImage IMG_DEFINE_ALL = null;
    BufferedImage IMG_DEFINE_RESTRICT = null;
    BufferedImage IMG_DEFINE_EXTERN = null;

    BufferedImage IMG_MOCKIZ_ALL = null;
    BufferedImage IMG_MOCKIZ_RESTRICT = null;
    BufferedImage IMG_MOCKIZ_EXTERN = null;


    BufferedImage IMG_ACTION_ALL = null;
    BufferedImage IMG_ACTION_RESTRICT = null;
    BufferedImage IMG_ACTION_EXTERN = null;

    BufferedImage IMG_FUNCTION_ALL = null;
    BufferedImage IMG_FUNCTION_RESTRICT = null;
    BufferedImage IMG_FUNCTION_EXTERN = null;

    BufferedImage IMG_OPERATOR_ALL = null;
    BufferedImage IMG_DIRECTOR_ALL = null;

    BufferedImage IMG_STAGE = null;

    int eLargura = 800;

    public Intellisense() {

        try {

            IMG_DEFINE_ALL = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\define_all.png"));
            IMG_DEFINE_RESTRICT = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\define_restrict.png"));
            IMG_DEFINE_EXTERN = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\define_extern.png"));

            IMG_MOCKIZ_ALL = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\mockiz_all.png"));
            IMG_MOCKIZ_RESTRICT = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\mockiz_restrict.png"));
            IMG_MOCKIZ_EXTERN = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\mockiz_extern.png"));


            IMG_ACTION_ALL = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\action_all.png"));
            IMG_ACTION_RESTRICT = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\action_restrict.png"));
            IMG_ACTION_EXTERN = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\action_extern.png"));

            IMG_FUNCTION_ALL = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\function_all.png"));
            IMG_FUNCTION_RESTRICT = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\function_restrict.png"));
            IMG_FUNCTION_EXTERN = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\function_extern.png"));

            IMG_OPERATOR_ALL = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\operator.png"));
            IMG_DIRECTOR_ALL = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\director.png"));

            IMG_STAGE = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\stage.png"));

        } catch (IOException e) {
        }


    }

    public void run(ArrayList<AST> eASTS, String eLocal) {


        for (AST ASTCGlobal : eASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                sigmaz_raiz(eLocal, "SIGMAZ", ASTCGlobal);

                for (AST Pacote : ASTCGlobal.getASTS()) {
                    if (Pacote.mesmoTipo("PACKAGE")) {

                        for (AST Sub3 : Pacote.getASTS()) {

                            if (Sub3.mesmoTipo("TYPE")) {
                                sigmaz_type(eLocal, Pacote.getNome() + "." + Sub3.getNome(), Sub3);

                            } else if (Sub3.mesmoTipo("STRUCT")) {
                                sigmaz_estrutura(eLocal, Pacote.getNome() + "." + Sub3.getNome(), Sub3);

                            } else {

                            }

                        }
                    } else if (Pacote.mesmoTipo("STRUCT")) {
                        sigmaz_estrutura(eLocal, "GLOBAL SIGMAZ - " + Pacote.getNome(), Pacote);
                    } else if (Pacote.mesmoTipo("TYPE")) {
                        sigmaz_type(eLocal, "GLOBAL SIGMAZ - " + Pacote.getNome(), Pacote);
                    }
                }
            }

        }
    }


    public void sigmaz_raiz(String eLocal, String eTitulo, AST eTudo) {


        int w = eLargura;
        int h = 200 + (getContagem(eTudo) * 30);


        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);


        g.setColor(Color.RED);
        g.fillRect(0, 0, w, 100);


        g.setColor(Color.BLACK);
        centerString(g, new Rectangle(0, 0, w, 100), eTitulo, new Font("TimesRoman", Font.BOLD, 50));

        int h1 = 110;
        int mais = 0;
        colocarGlobal(h1, mais, g, eTudo);

        try {
            File outputfile = new File(eLocal + eTitulo + ".png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {

        }

    }

    public void sigmaz_estrutura(String eLocal, String eTitulo, AST eTudo) {


        int w = eLargura;

        int c = 0;

        Color eBarra = Color.RED;

        if (eTudo.getBranch("EXTENDED").mesmoNome("STAGES")) {

            for (AST Sub2 : eTudo.getBranch("STAGES").getASTS()) {
                c += 1;
            }

            eBarra = new Color(255, 110, 64);


        } else if (eTudo.getBranch("EXTENDED").mesmoNome("TYPE")) {

            eBarra = new Color(64, 196, 255);


        } else if (eTudo.getBranch("EXTENDED").mesmoNome("STRUCT")) {

            eBarra = new Color(124, 179, 66);

            for (AST Sub2 : eTudo.getBranch("INITS").getASTS()) {
                c += 1;
            }
        } else if (eTudo.getBranch("EXTENDED").mesmoNome("EXTERNAL")) {

            eBarra = new Color(255, 235, 59);


        } else {


        }


        int h = 200 + ((getContagem(eTudo.getBranch("BODY")) + c) * 30) + 200;


        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);


        g.setColor(eBarra);
        g.fillRect(0, 0, w, 100);


        g.setColor(Color.BLACK);
        centerString(g, new Rectangle(0, 0, w, 100), eTitulo, new Font("TimesRoman", Font.BOLD, 50));

        int h1 = 110;
        int mais = 0;

        if (eTudo.getBranch("EXTENDED").mesmoNome("STAGES")) {

            for (AST Sub2 : eTudo.getBranch("STAGES").getASTS()) {
                String eConteudo = Sub2.getNome();

                g.setColor(Color.BLACK);
                leftString(g, new Rectangle(30, h1 + mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), IMG_STAGE);

                mais += 30;

            }

            g.setColor(Color.BLACK);
            g.fillRect(0, h1 + mais + 50, w, 15);

            mais += 50;

            g.setColor(Color.BLACK);

            colocarGlobalStages(h1, mais, g, eTudo.getBranch("BODY"));

        } else {


            g.setColor(Color.BLACK);

            colocarGlobal(h1, mais, g, eTudo.getBranch("BODY"));

        }


        try {
            File outputfile = new File(eLocal + eTitulo + ".png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {

        }

    }

    public void sigmaz_type(String eLocal, String eTitulo, AST eTudo) {


        int w = eLargura;

        int c = 0;

        Color eBarra = Color.BLUE;



        int h = 200 + ((getContagem(eTudo) + c) * 30) + 200;


        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);


        g.setColor(eBarra);
        g.fillRect(0, 0, w, 100);


        g.setColor(Color.BLACK);
        centerString(g, new Rectangle(0, 0, w, 100), eTitulo, new Font("TimesRoman", Font.BOLD, 50));

        int h1 = 110;
        int mais = 0;


        g.setColor(Color.BLACK);


        Utils mUtils = new Utils();


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                String eConteudo = mUtils.getDefine(Sub2);
                mais = algum(Sub2, g, h1, mais, "ALL", eConteudo, IMG_DEFINE_ALL);
            }

        }


        for (AST Sub2 : eTudo.getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {
                String eConteudo = mUtils.getMockiz(Sub2);
                mais = algum(Sub2, g, h1, mais, "ALL", eConteudo, IMG_MOCKIZ_ALL);
            }

        }


        try {
            File outputfile = new File(eLocal + eTitulo + ".png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {

        }

    }


    public int getContagem(AST Sub) {
        int r = 1;

        for (AST Sub2 : Sub.getASTS()) {
            if (Sub2.mesmoTipo("ACTION")) {
                r += 1;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                r += 1;
            } else if (Sub2.mesmoTipo("OPERATOR")) {
                r += 1;
            } else if (Sub2.mesmoTipo("DIRECTOR")) {
                r += 1;
            } else if (Sub2.mesmoTipo("DEFINE")) {
                r += 1;
            } else if (Sub2.mesmoTipo("MOCKIZ")) {
                r += 1;
            }
        }
        return r;
    }


    public int algum(AST Sub2, Graphics g, int h1, int mais, String eModo, String eInfo, BufferedImage ePino) {


        if (Sub2.getBranch("VISIBILITY").mesmoNome(eModo)) {


            g.setColor(Color.BLACK);
            leftString(g, new Rectangle(30, h1 + mais, eLargura, 100), eInfo, new Font("TimesRoman", Font.BOLD, 20), ePino);

            mais += 30;

        }

        return mais;
    }


    public void colocarGlobal(int h1, int mais, Graphics g, AST Sub) {



        Utils mUtils = new Utils();


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                String eConteudo = mUtils.getDefine(Sub2);
                mais = algum(Sub2, g, h1, mais, "ALL", eConteudo, IMG_DEFINE_ALL);
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                String eConteudo = mUtils.getDefine(Sub2);
                mais = algum(Sub2, g, h1, mais, "RESTRICT", eConteudo, IMG_DEFINE_RESTRICT);
            }

        }


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {
                String eConteudo = mUtils.getMockiz(Sub2);
                mais = algum(Sub2, g, h1, mais, "ALL", eConteudo, IMG_MOCKIZ_ALL);
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {
                String eConteudo = mUtils.getMockiz(Sub2);
                mais = algum(Sub2, g, h1, mais, "RESTRICT", eConteudo, IMG_MOCKIZ_RESTRICT);
            }

        }


        g.setColor(Color.BLACK);
        g.fillRect(0, h1 + mais + 50, eLargura, 15);

        mais += 50;


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {
                String eConteudo = mUtils.getAction(Sub2);
                mais = algum(Sub2, g, h1, mais, "ALL", eConteudo, IMG_ACTION_ALL);
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {
                String eConteudo = mUtils.getAction(Sub2);
                mais = algum(Sub2, g, h1, mais, "RESTRICT", eConteudo, IMG_ACTION_RESTRICT);
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("FUNCTION")) {
                String eConteudo = mUtils.getFuction(Sub2);
                mais = algum(Sub2, g, h1, mais, "ALL", eConteudo, IMG_FUNCTION_ALL);
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("FUNCTION")) {
                String eConteudo = mUtils.getFuction(Sub2);
                mais = algum(Sub2, g, h1, mais, "RESTRICT", eConteudo, IMG_FUNCTION_RESTRICT);
            }

        }

        g.setColor(Color.BLACK);
        g.fillRect(0, h1 + mais + 50, eLargura, 15);

        mais += 50;

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                String eConteudo = mUtils.getDefine(Sub2);
                mais = algum(Sub2, g, h1, mais, "EXTERN", eConteudo, IMG_DEFINE_EXTERN);
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {
                String eConteudo = mUtils.getMockiz(Sub2);
                mais = algum(Sub2, g, h1, mais, "EXTERN", eConteudo, IMG_MOCKIZ_EXTERN);
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {
                String eConteudo = mUtils.getAction(Sub2);
                mais = algum(Sub2, g, h1, mais, "EXTERN", eConteudo, IMG_ACTION_EXTERN);
            }

        }
        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("FUNCTION")) {
                String eConteudo = mUtils.getFuction(Sub2);
                mais = algum(Sub2, g, h1, mais, "EXTERN", eConteudo, IMG_FUNCTION_EXTERN);
            }

        }

        g.setColor(Color.BLACK);
        g.fillRect(0, h1 + mais + 50, eLargura, 15);

        mais += 50;

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("DIRECTOR")) {

                String eConteudo = mUtils.getFuction(Sub2);

                g.setColor(Color.BLACK);
                leftString(g, new Rectangle(30, h1 + mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), IMG_DIRECTOR_ALL);

                mais += 30;

            }
        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("OPERATOR")) {

                String eConteudo = mUtils.getFuction(Sub2);

                g.setColor(Color.BLACK);
                leftString(g, new Rectangle(30, h1 + mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), IMG_OPERATOR_ALL);

                mais += 30;

            }
        }


    }

    public void colocarGlobalStages(int h1, int mais, Graphics g, AST Sub) {


        Utils mUtils = new Utils();


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                String eConteudo = mUtils.getDefine(Sub2);
                mais = algum(Sub2, g, h1, mais, "EXTERN", eConteudo, IMG_DEFINE_EXTERN);
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {
                String eConteudo = mUtils.getMockiz(Sub2);
                mais = algum(Sub2, g, h1, mais, "EXTERN", eConteudo, IMG_MOCKIZ_EXTERN);
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {
                String eConteudo = mUtils.getAction(Sub2);
                mais = algum(Sub2, g, h1, mais, "EXTERN", eConteudo, IMG_ACTION_EXTERN);
            }

        }
        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("FUNCTION")) {
                String eConteudo = mUtils.getFuction(Sub2);
                mais = algum(Sub2, g, h1, mais, "EXTERN", eConteudo, IMG_FUNCTION_EXTERN);
            }

        }

        g.setColor(Color.BLACK);
        g.fillRect(0, h1 + mais + 50, eLargura, 15);

        mais += 50;

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("DIRECTOR")) {

                String eConteudo = mUtils.getFuction(Sub2);

                g.setColor(Color.BLACK);
                leftString(g, new Rectangle(30, h1 + mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), IMG_DIRECTOR_ALL);

                mais += 30;

            }
        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("OPERATOR")) {

                String eConteudo = mUtils.getFuction(Sub2);

                g.setColor(Color.BLACK);
                leftString(g, new Rectangle(30, h1 + mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), IMG_OPERATOR_ALL);

                mais += 30;

            }
        }


    }

    public void centerString(Graphics g, Rectangle r, String s,
                             Font font) {
        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;

        g.setFont(font);
        g.drawString(s, r.x + a, r.y + b);
    }

    public void leftString(Graphics g, Rectangle r, String s,
                           Font font, BufferedImage ePino) {
        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;

        g.drawImage(ePino, r.x, r.y + b - (ePino.getHeight()), null);


        g.setFont(font);
        g.drawString(s, r.x + 20, r.y + b);
    }
}
