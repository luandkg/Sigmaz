package Sigmaz.Intellisenses;

import Sigmaz.Executor.Utils;
import Sigmaz.Utils.AST;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Intellisense {

    public BufferedImage IMG_DEFINE_ALL = null;
    public BufferedImage IMG_DEFINE_RESTRICT = null;
    public BufferedImage IMG_DEFINE_EXTERN = null;
    public BufferedImage IMG_DEFINE_TYPE = null;

    public BufferedImage IMG_MOCKIZ_ALL = null;
    public BufferedImage IMG_MOCKIZ_RESTRICT = null;
    public BufferedImage IMG_MOCKIZ_EXTERN = null;
    public BufferedImage IMG_MOCKIZ_TYPE = null;


    public BufferedImage IMG_ACTION_ALL = null;
    public BufferedImage IMG_ACTION_RESTRICT = null;
    public BufferedImage IMG_ACTION_EXTERN = null;

    public BufferedImage IMG_FUNCTION_ALL = null;
    public BufferedImage IMG_FUNCTION_RESTRICT = null;
    public BufferedImage IMG_FUNCTION_EXTERN = null;

    public BufferedImage IMG_OPERATOR_ALL = null;
    public BufferedImage IMG_DIRECTOR_ALL = null;

    public BufferedImage IMG_INIT = null;

    public BufferedImage IMG_STAGE = null;
    public BufferedImage IMG_GETTER = null;
    public BufferedImage IMG_SETTER = null;

    public BufferedImage IMG_MODEL = null;
    public BufferedImage IMG_BASE = null;
    public BufferedImage IMG_GENERIC = null;
    public BufferedImage IMG_GENERIC_TYPE = null;

    private int eLargura = 800;


    public Intellisense() {

        try {

            IMG_DEFINE_ALL = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\define_all.png"));
            IMG_DEFINE_RESTRICT = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\define_restrict.png"));
            IMG_DEFINE_EXTERN = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\define_extern.png"));
            IMG_DEFINE_TYPE = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\define_type.png"));

            IMG_MOCKIZ_ALL = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\mockiz_all.png"));
            IMG_MOCKIZ_RESTRICT = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\mockiz_restrict.png"));
            IMG_MOCKIZ_EXTERN = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\mockiz_extern.png"));
            IMG_MOCKIZ_TYPE = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\mockiz_type.png"));


            IMG_ACTION_ALL = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\action_all.png"));
            IMG_ACTION_RESTRICT = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\action_restrict.png"));
            IMG_ACTION_EXTERN = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\action_extern.png"));

            IMG_FUNCTION_ALL = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\function_all.png"));
            IMG_FUNCTION_RESTRICT = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\function_restrict.png"));
            IMG_FUNCTION_EXTERN = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\function_extern.png"));

            IMG_OPERATOR_ALL = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\operator.png"));
            IMG_DIRECTOR_ALL = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\director.png"));

            IMG_INIT = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\init.png"));

            IMG_STAGE = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\stage.png"));
            IMG_GETTER = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\getter.png"));
            IMG_SETTER = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\setter.png"));
            IMG_MODEL = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\model.png"));
            IMG_BASE = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\base.png"));
            IMG_GENERIC = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\generic.png"));
            IMG_GENERIC_TYPE = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\generic_type.png"));

        } catch (IOException e) {
        }


    }

    public int getLargura(){
        return eLargura;
    }

    public void run(ArrayList<AST> eASTS, String eLocal) {


        for (AST ASTCGlobal : eASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                IS_Sigmaz mISig = new IS_Sigmaz(this);
                IS_Package mIP = new IS_Package(this);

              //  mISig.sigmaz_raiz(eLocal, "SIGMAZ_1", ASTCGlobal, eLargura);

                mISig.sigmaz_raiz_horizontal(eLocal, "SIGMAZ_1", ASTCGlobal, eLargura);

               // mIP.sigmaz_package(eLocal, "SIGMAZ_2", ASTCGlobal, eLargura);

                mIP.sigmaz_package_horizontal(eLocal, "SIGMAZ_2", ASTCGlobal, eLargura);

           //     mIP.sigmaz_package_horizontal(eLocal, "SIGMAZ", ASTCGlobal, eLargura);

                for (AST Pacote : ASTCGlobal.getASTS()) {
                    if (Pacote.mesmoTipo("PACKAGE")) {


                        mIP.sigmaz_package(eLocal, Pacote.getNome(), Pacote, eLargura);

                        mIP.sigmaz_package_horizontal(eLocal, Pacote.getNome(), Pacote, eLargura);


                        for (AST Sub3 : Pacote.getASTS()) {


                            if (Sub3.mesmoTipo("TYPE")) {

                                IS_Struct mIS = new IS_Struct(this);

                                //    mIT.sigmaz_type(eLocal, Pacote.getNome() + "." + Sub3.getNome(), Sub3,eLargura);

                            } else if (Sub3.mesmoTipo("STRUCT")) {

                                IS_Struct mIS = new IS_Struct(this);

                                //  mIS.sigmaz_estrutura(eLocal, Pacote.getNome() + "." + Sub3.getNome(), Sub3,eLargura);
                            } else if (Sub3.mesmoTipo("CAST")) {

                                IS_Cast mIS = new IS_Cast(this);

                                //   mIS.sigmaz_cast(eLocal, Pacote.getNome() + "." + Sub3.getNome(), Sub3,eLargura);

                            } else {

                            }

                        }
                    } else if (Pacote.mesmoTipo("STRUCT")) {

                        IS_Struct mIS = new IS_Struct(this);

                   //     mIS.sigmaz_estrutura(eLocal, "GLOBAL SIGMAZ - " + Pacote.getNome(), Pacote, eLargura);

                    } else if (Pacote.mesmoTipo("TYPE")) {

                        IS_Struct mIS = new IS_Struct(this);

                     //   mIT.sigmaz_type(eLocal, "GLOBAL SIGMAZ - " + Pacote.getNome(), Pacote, eLargura);

                    } else if (Pacote.mesmoTipo("CAST")) {

                        IS_Cast mIC = new IS_Cast(this);

                      //  mIC.sigmaz_cast(eLocal, "GLOBAL SIGMAZ - " + Pacote.getNome(), Pacote, eLargura);

                    } else if (Pacote.mesmoTipo("MODEL")) {

                        IS_Model mIC = new IS_Model(this);

                     //   mIC.model(eLocal, "GLOBAL SIGMAZ - " + Pacote.getNome(), Pacote, eLargura);

                    }
                }
            }

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
            } else if (Sub2.mesmoTipo("GETTER")) {
                r += 1;
            } else if (Sub2.mesmoTipo("SETTER")) {
                r += 1;
            }
        }
        return r;
    }

    public int getContagemPacote(AST Sub) {
        int r = 1;

        for (AST Sub2 : Sub.getASTS()) {
            if (Sub2.mesmoTipo("STRUCT")) {
                r += 1;
            } else if (Sub2.mesmoTipo("TYPE")) {
                r += 1;
            } else if (Sub2.mesmoTipo("CAST")) {
                r += 1;

            }
        }
        return r;
    }

    public int getContagemGenericos(AST Sub) {
        int r = 1;

        for (AST Sub2 : Sub.getASTS()) {
            r += 1;
        }
        return r;
    }


    public int algum(AST Sub2, Graphics g, int x, int mais, String eModo, String eInfo, BufferedImage ePino) {


        if (Sub2.getBranch("VISIBILITY").mesmoNome(eModo)) {


            g.setColor(Color.BLACK);
            leftString(g, new Rectangle(x+30, mais, eLargura, 100), eInfo, new Font("TimesRoman", Font.BOLD, 20), ePino);

            mais += 30;

        }

        return mais;
    }

    public int algum(AST Sub2, Graphics g, int mais, String eInfo, BufferedImage ePino) {



        g.setColor(Color.BLACK);
        leftString(g, new Rectangle(30, mais, eLargura, 100), eInfo, new Font("TimesRoman", Font.BOLD, 20), ePino);

        mais += 30;


        return mais;
    }

    public int algum(AST Sub2, Graphics g, int x,int y, String eInfo, BufferedImage ePino) {



        g.setColor(Color.BLACK);
        leftString(g, new Rectangle(x, y, eLargura, 100), eInfo, new Font("TimesRoman", Font.BOLD, 20), ePino);

        y += 30;


        return y;
    }

    public int colocarGlobal(int x,int mais, Graphics g, AST Sub) {


        Utils mUtils = new Utils();

        int p = 0;


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                String eConteudo = mUtils.getDefine(Sub2);
                mais = algum(Sub2, g,x, mais, "ALL", eConteudo, IMG_DEFINE_ALL);
                p += 1;
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                String eConteudo = mUtils.getDefine(Sub2);
                mais = algum(Sub2, g,x, mais, "RESTRICT", eConteudo, IMG_DEFINE_RESTRICT);
                p += 1;
            }

        }


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {
                String eConteudo = mUtils.getMockiz(Sub2);
                mais = algum(Sub2, g,x, mais, "ALL", eConteudo, IMG_MOCKIZ_ALL);
                p += 1;
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {
                String eConteudo = mUtils.getMockiz(Sub2);
                mais = algum(Sub2, g,x, mais, "RESTRICT", eConteudo, IMG_MOCKIZ_RESTRICT);
                p += 1;
            }

        }


        int ne = 0;

        for (AST Sub2 : Sub.getASTS()) {
            if (Sub2.mesmoTipo("ACTION") && (!Sub2.getBranch("VISIBILITY").mesmoNome("EXTERN"))) {
                ne += 1;
            } else if (Sub2.mesmoTipo("FUNCTION") && (!Sub2.getBranch("VISIBILITY").mesmoNome("EXTERN"))) {
                ne += 1;
            }

        }


        if (p > 0 && ne>0) {
            mais += 70;

            g.setColor(Color.BLACK);
            g.fillRect(x+ 50, mais, eLargura / 2, 15);


        }

        int p2 = 0;


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {
                String eConteudo = mUtils.getAction(Sub2);
                mais = algum(Sub2, g, x,mais, "ALL", eConteudo, IMG_ACTION_ALL);
                p2 += 1;
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {
                String eConteudo = mUtils.getAction(Sub2);
                mais = algum(Sub2, g,x, mais, "RESTRICT", eConteudo, IMG_ACTION_RESTRICT);
                p2 += 1;
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("FUNCTION")) {
                String eConteudo = mUtils.getFuction(Sub2);
                mais = algum(Sub2, g,x, mais, "ALL", eConteudo, IMG_FUNCTION_ALL);
                p2 += 1;
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("FUNCTION")) {
                String eConteudo = mUtils.getFuction(Sub2);
                mais = algum(Sub2, g,x, mais, "RESTRICT", eConteudo, IMG_FUNCTION_RESTRICT);
                p2 += 1;
            }

        }

        int e = 0;

        for (AST Sub2 : Sub.getASTS()) {
            if (Sub2.mesmoTipo("DEFINE") && (Sub2.getBranch("VISIBILITY").mesmoNome("EXTERN"))) {
                e += 1;
            } else if (Sub2.mesmoTipo("MOCKIZ") && (Sub2.getBranch("VISIBILITY").mesmoNome("EXTERN"))) {
                e += 1;
            } else if (Sub2.mesmoTipo("ACTION") && (Sub2.getBranch("VISIBILITY").mesmoNome("EXTERN"))) {
                e += 1;
            } else if (Sub2.mesmoTipo("FUNCTION") && (Sub2.getBranch("VISIBILITY").mesmoNome("EXTERN"))) {
                e += 1;
            }

        }

        if (p2 > 0 && e > 0) {

            mais += 70;

            g.setColor(Color.BLACK);
            g.fillRect(x+ 50, mais, eLargura / 2, 15);

        }


        mais = colocarGlobal2(x,mais, g, Sub);

        return mais;

    }

    public int colocarGlobal2(int x,int mais, Graphics g, AST Sub) {

        Utils mUtils = new Utils();


        int e = 0;


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                String eConteudo = mUtils.getDefine(Sub2);
                mais = algum(Sub2, g, x,mais, "EXTERN", eConteudo, IMG_DEFINE_EXTERN);
                e += 1;
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {
                String eConteudo = mUtils.getMockiz(Sub2);
                mais = algum(Sub2, g,x, mais, "EXTERN", eConteudo, IMG_MOCKIZ_EXTERN);
                e += 1;
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {
                String eConteudo = mUtils.getAction(Sub2);
                mais = algum(Sub2, g,x, mais, "EXTERN", eConteudo, IMG_ACTION_EXTERN);
                e += 1;
            }

        }
        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("FUNCTION")) {
                String eConteudo = mUtils.getFuction(Sub2);
                mais = algum(Sub2, g,x, mais, "EXTERN", eConteudo, IMG_FUNCTION_EXTERN);
                e += 1;
            }

        }


        if (e > 0) {

            mais += 70;

            g.setColor(Color.BLACK);
            g.fillRect(x+ 50, mais, eLargura / 2, 15);

        }


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("DIRECTOR")) {

                String eConteudo = mUtils.getFuction(Sub2);

                g.setColor(Color.BLACK);
                leftString(g, new Rectangle(x+ 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), IMG_DIRECTOR_ALL);

                mais += 30;

            }
        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("OPERATOR")) {

                String eConteudo = mUtils.getFuction(Sub2);

                g.setColor(Color.BLACK);
                leftString(g, new Rectangle(x+ 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), IMG_OPERATOR_ALL);

                mais += 30;

            }
        }

        return mais;

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

        g.drawString(s, r.x + 40, r.y + b);
    }

    public void leftString(Graphics g, Rectangle r, String s,
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
        g.drawString(s, r.x + 20, r.y + b);
    }
}
