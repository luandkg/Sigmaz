package Sigmaz.Intellisenses;

import Sigmaz.S07_Executor.Utils;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.AST;

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

    public BufferedImage IMG_AUTO_ALL= null;
    public BufferedImage IMG_AUTO_RESTRICT= null;


    public BufferedImage IMG_FUNCTOR_ALL= null;
    public BufferedImage IMG_FUNCTOR_RESTRICT= null;

    public BufferedImage IMG_OPERATOR_ALL = null;
    public BufferedImage IMG_DIRECTOR_ALL = null;

    public BufferedImage IMG_INIT = null;

    public BufferedImage IMG_STAGE = null;
    public BufferedImage IMG_GETTER = null;
    public BufferedImage IMG_SETTER = null;

    public BufferedImage IMG_MODEL = null;
    public BufferedImage IMG_BASE = null;
    public BufferedImage IMG_GENERIC_TYPE = null;

    public BufferedImage IMG_MARK = null;

    private int eLargura = 800;


    public Intellisense() {

        Insignia mInsignia = new Insignia();


        IMG_DEFINE_ALL = mInsignia.getDefine_All();
        IMG_MOCKIZ_ALL = mInsignia.getMockiz_All();
        IMG_ACTION_ALL = mInsignia.getAction_All();
        IMG_FUNCTION_ALL = mInsignia.getFunction_All();

        IMG_DEFINE_RESTRICT = mInsignia.getDefine_Restrict();
        IMG_MOCKIZ_RESTRICT = mInsignia.getMockiz_Restrict();
        IMG_ACTION_RESTRICT = mInsignia.getAction_Restrict();
        IMG_FUNCTION_RESTRICT = mInsignia.getFunction_Restrict();

        IMG_DEFINE_EXTERN = mInsignia.getDefine_Extern();
        IMG_MOCKIZ_EXTERN = mInsignia.getMockiz_Extern();
        IMG_ACTION_EXTERN = mInsignia.getAction_Extern();
        IMG_FUNCTION_EXTERN = mInsignia.getFunction_Extern();

        IMG_DEFINE_TYPE = mInsignia.getDefine_Type();
        IMG_MOCKIZ_TYPE = mInsignia.getMockiz_Type();

        IMG_AUTO_ALL=mInsignia.getAuto_All();
        IMG_AUTO_RESTRICT=mInsignia.getAuto_Restrict();

        IMG_FUNCTOR_ALL=mInsignia.getFunctor_All();
        IMG_FUNCTOR_RESTRICT=mInsignia.getFunctor_Restrict();

        IMG_OPERATOR_ALL = mInsignia.getOperator();
        IMG_DIRECTOR_ALL = mInsignia.getDirector();

        IMG_GENERIC_TYPE = mInsignia.getGenericType();

        IMG_INIT = mInsignia.getInit();
        IMG_STAGE = mInsignia.getStage();
        IMG_BASE = mInsignia.getBase();
        IMG_MODEL = mInsignia.getModel();


        IMG_GETTER = mInsignia.getGetter();
        IMG_SETTER = mInsignia.getSetter();

        IMG_MARK = mInsignia.getMark();

    }

    public int getLargura() {
        return eLargura;
    }

    public void run(ArrayList<AST> eASTS, IntellisenseTheme mIntellisenseTheme, String eLocal) {


        for (AST ASTCGlobal : eASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                IS_Sigmaz mISig = new IS_Sigmaz(this, mIntellisenseTheme);
                IS_Package mIP = new IS_Package(this, mIntellisenseTheme);

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

                                IS_Struct mIS = new IS_Struct(this, mIntellisenseTheme);

                                //    mIT.sigmaz_type(eLocal, Pacote.getNome() + "." + Sub3.getNome(), Sub3,eLargura);

                            } else if (Sub3.mesmoTipo("STRUCT")) {

                                IS_Struct mIS = new IS_Struct(this, mIntellisenseTheme);

                                //  mIS.sigmaz_estrutura(eLocal, Pacote.getNome() + "." + Sub3.getNome(), Sub3,eLargura);
                            } else if (Sub3.mesmoTipo("CAST")) {

                                IS_Cast mIS = new IS_Cast(this, mIntellisenseTheme);

                                //   mIS.sigmaz_cast(eLocal, Pacote.getNome() + "." + Sub3.getNome(), Sub3,eLargura);

                            } else {

                            }

                        }
                    } else if (Pacote.mesmoTipo("STRUCT")) {

                        IS_Struct mIS = new IS_Struct(this, mIntellisenseTheme);

                        //     mIS.sigmaz_estrutura(eLocal, "GLOBAL SIGMAZ - " + Pacote.getNome(), Pacote, eLargura);

                    } else if (Pacote.mesmoTipo("TYPE")) {

                        IS_Struct mIS = new IS_Struct(this, mIntellisenseTheme);

                        //   mIT.sigmaz_type(eLocal, "GLOBAL SIGMAZ - " + Pacote.getNome(), Pacote, eLargura);

                    } else if (Pacote.mesmoTipo("CAST")) {

                        IS_Cast mIC = new IS_Cast(this, mIntellisenseTheme);

                        //  mIC.sigmaz_cast(eLocal, "GLOBAL SIGMAZ - " + Pacote.getNome(), Pacote, eLargura);

                    } else if (Pacote.mesmoTipo("MODEL")) {

                        IS_Model mIC = new IS_Model(this, mIntellisenseTheme);

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
            } else if (Sub2.mesmoTipo("PROTOTYPE_FUNCTOR")) {
                r += 1;
            } else if (Sub2.mesmoTipo("PROTOTYPE_AUTO")) {
                r += 1;
            } else if (Sub2.mesmoTipo("MARK")) {
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
            leftString(g, new Rectangle(x + 30, mais, eLargura, 100), eInfo, new Font("TimesRoman", Font.BOLD, 20), ePino);

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

    public int algum(AST Sub2, Graphics g, int x, int y, String eInfo, BufferedImage ePino) {


        g.setColor(Color.BLACK);
        leftString(g, new Rectangle(x, y, eLargura, 100), eInfo, new Font("TimesRoman", Font.BOLD, 20), ePino);

        y += 30;


        return y;
    }

    public int colocarGlobal(int x, int mais, Graphics g, AST Sub) {


        Utils mUtils = new Utils();

        int p = 0;


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                String eConteudo = mUtils.getDefine(Sub2);
                mais = algum(Sub2, g, x, mais, "ALL", eConteudo, IMG_DEFINE_ALL);
                p += 1;
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                String eConteudo = mUtils.getDefine(Sub2);
                mais = algum(Sub2, g, x, mais, "RESTRICT", eConteudo, IMG_DEFINE_RESTRICT);
                p += 1;
            }

        }


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {
                String eConteudo = mUtils.getMockiz(Sub2);
                mais = algum(Sub2, g, x, mais, "ALL", eConteudo, IMG_MOCKIZ_ALL);
                p += 1;
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {
                String eConteudo = mUtils.getMockiz(Sub2);
                mais = algum(Sub2, g, x, mais, "RESTRICT", eConteudo, IMG_MOCKIZ_RESTRICT);
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


        if (p > 0 && ne > 0) {
            mais += 70;

            g.setColor(Color.BLACK);
            g.fillRect(x + 50, mais, eLargura / 2, 15);


        }

        int p2 = 0;


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {
                String eConteudo = mUtils.getAction(Sub2);
                mais = algum(Sub2, g, x, mais, "ALL", eConteudo, IMG_ACTION_ALL);
                p2 += 1;
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {
                String eConteudo = mUtils.getAction(Sub2);
                mais = algum(Sub2, g, x, mais, "RESTRICT", eConteudo, IMG_ACTION_RESTRICT);
                p2 += 1;
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("FUNCTION")) {
                String eConteudo = mUtils.getFuction(Sub2);
                mais = algum(Sub2, g, x, mais, "ALL", eConteudo, IMG_FUNCTION_ALL);
                p2 += 1;
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("FUNCTION")) {
                String eConteudo = mUtils.getFuction(Sub2);
                mais = algum(Sub2, g, x, mais, "RESTRICT", eConteudo, IMG_FUNCTION_RESTRICT);
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
            g.fillRect(x + 50, mais, eLargura / 2, 15);

        }


        mais = colocarGlobal2(x, mais, g, Sub);

        return mais;

    }

    public int colocarGlobal2(int x, int mais, Graphics g, AST Sub) {

        Utils mUtils = new Utils();


        int e = 0;


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                String eConteudo = mUtils.getDefine(Sub2);
                mais = algum(Sub2, g, x, mais, "EXTERN", eConteudo, IMG_DEFINE_EXTERN);
                e += 1;
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {
                String eConteudo = mUtils.getMockiz(Sub2);
                mais = algum(Sub2, g, x, mais, "EXTERN", eConteudo, IMG_MOCKIZ_EXTERN);
                e += 1;
            }

        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {
                String eConteudo = mUtils.getAction(Sub2);
                mais = algum(Sub2, g, x, mais, "EXTERN", eConteudo, IMG_ACTION_EXTERN);
                e += 1;
            }

        }
        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("FUNCTION")) {
                String eConteudo = mUtils.getFuction(Sub2);
                mais = algum(Sub2, g, x, mais, "EXTERN", eConteudo, IMG_FUNCTION_EXTERN);
                e += 1;
            }

        }


        if (e > 0) {

            mais += 70;

            g.setColor(Color.BLACK);
            g.fillRect(x + 50, mais, eLargura / 2, 15);

        }


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("DIRECTOR")) {

                String eConteudo = mUtils.getFuction(Sub2);

                g.setColor(Color.BLACK);
                leftString(g, new Rectangle(x + 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), IMG_DIRECTOR_ALL);

                mais += 30;

            }
        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("OPERATOR")) {

                String eConteudo = mUtils.getFuction(Sub2);

                g.setColor(Color.BLACK);
                leftString(g, new Rectangle(x + 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), IMG_OPERATOR_ALL);

                mais += 30;

            }
        }


        mais += 70;

        g.setColor(Color.BLACK);
        g.fillRect(x + 50, mais, eLargura / 2, 15);

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("COL_GET")) {

                String eConteudo = mUtils.getColGet(Sub2);

                g.setColor(Color.BLACK);
                leftString(g, new Rectangle(x + 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), IMG_OPERATOR_ALL);

                mais += 30;

            }
        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("COL_SET")) {

                String eConteudo = mUtils.getColSet(Sub2);

                g.setColor(Color.BLACK);
                leftString(g, new Rectangle(x + 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), IMG_OPERATOR_ALL);

                mais += 30;

            }
        }


        return mais;

    }


    public void centerString(Graphics g, Rectangle r, String s, Font font) {
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

    public void leftString(Graphics g, Rectangle r, String s, Font font, BufferedImage ePino) {
        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;


        g.drawImage(ePino, r.x, r.y + 33, ePino.getWidth(), ePino.getHeight(), null);

        g.setFont(font);

        g.drawString(s, r.x + 40, r.y + b);
    }


}
