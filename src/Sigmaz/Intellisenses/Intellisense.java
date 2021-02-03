package Sigmaz.Intellisenses;

import Sigmaz.S00_Utilitarios.Visualizador.SigmazPackage;
import Sigmaz.S00_Utilitarios.Visualizador.SigmazRaiz;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.AST;

public class Intellisense {

    public BufferedImage IMG_DEFINE_ALL = null;
    public BufferedImage IMG_DEFINE_RESTRICT = null;
    public BufferedImage IMG_DEFINE_TYPE = null;
    public BufferedImage IMG_DEFINE_EXPLICIT = null;
    public BufferedImage IMG_DEFINE_IMPLICIT = null;

    public BufferedImage IMG_MOCKIZ_ALL = null;
    public BufferedImage IMG_MOCKIZ_RESTRICT = null;
    public BufferedImage IMG_MOCKIZ_TYPE = null;
    public BufferedImage IMG_MOCKIZ_EXPLICIT = null;
    public BufferedImage IMG_MOCKIZ_IMPLICIT = null;


    public BufferedImage IMG_ACTION_ALL = null;
    public BufferedImage IMG_ACTION_RESTRICT = null;
    public BufferedImage IMG_ACTION_EXPLICIT = null;
    public BufferedImage IMG_ACTION_IMPLICIT = null;

    public BufferedImage IMG_FUNCTION_ALL = null;
    public BufferedImage IMG_FUNCTION_RESTRICT = null;
    public BufferedImage IMG_FUNCTION_EXPLICIT = null;
    public BufferedImage IMG_FUNCTION_IMPLICIT = null;

    public BufferedImage IMG_AUTO_ALL = null;
    public BufferedImage IMG_AUTO_RESTRICT = null;


    public BufferedImage IMG_FUNCTOR_ALL = null;
    public BufferedImage IMG_FUNCTOR_RESTRICT = null;

    public BufferedImage IMG_OPERATOR = null;
    public BufferedImage IMG_DIRECTOR = null;

    public BufferedImage IMG_INIT = null;

    public BufferedImage IMG_STAGE = null;
    public BufferedImage IMG_GETTER = null;
    public BufferedImage IMG_SETTER = null;

    public BufferedImage IMG_MODEL = null;
    public BufferedImage IMG_BASE = null;
    public BufferedImage IMG_GENERIC_TYPE = null;

    public BufferedImage IMG_MARK = null;

    public BufferedImage IMG_BLOCO_GETTER = null;
    public BufferedImage IMG_BLOCO_SETTER = null;

    private IntellisenseTheme mIntellisenseTheme;

    private int eLargura;
    private int mCaixaAltura;
    private int mCaixaGrandeAltura;

    private Font eFonte_TituloGrande;
    private Font eFonte_Titulo;
    private Font eFonte_Normal;

    private int mT1;
    private int mT2;
    private int mT3;

    private int mTamanhoTitulado;
    private int mAposTexto;
    private int mAposTituloGrande;

    private int mEspecadorAntesBox;
    private int mAlturaBox;
    private int mItem;
    private int mTamanhoCaixa;

    public void peq() {
        mT1 = 50;
        mT2 = 30;
        mT3 = 10;

        mCaixaAltura = 60;
        mCaixaGrandeAltura = 80;

        mEspecadorAntesBox = 50;
        mAlturaBox = 8;
        eLargura = 500;

        mItem = 40;
        mTamanhoCaixa=70;

    }

    public Intellisense() {

        mT1 = 80;
        mT2 = 50;
        mT3 = 20;

        mCaixaAltura = 100;
        mCaixaGrandeAltura = 120;

        mEspecadorAntesBox = 70;
        mAlturaBox = 15;
        eLargura = 800;

        mItem = 80;
        mTamanhoCaixa=100;

        peq();

        eFonte_TituloGrande = new Font("TimesRoman", Font.BOLD, mT1);
        eFonte_Titulo = new Font("TimesRoman", Font.BOLD, mT2);
        eFonte_Normal = new Font("TimesRoman", Font.BOLD, mT3);

        mTamanhoTitulado = mCaixaAltura + 10;
        mAposTexto = mT3 + 20;
        mAposTituloGrande = mT1 + 20;

        Insignia mInsignia = new Insignia();


        IMG_DEFINE_ALL = mInsignia.getDefine_All();
        IMG_MOCKIZ_ALL = mInsignia.getMockiz_All();
        IMG_ACTION_ALL = mInsignia.getAction_All();
        IMG_FUNCTION_ALL = mInsignia.getFunction_All();

        IMG_DEFINE_RESTRICT = mInsignia.getDefine_Restrict();
        IMG_MOCKIZ_RESTRICT = mInsignia.getMockiz_Restrict();
        IMG_ACTION_RESTRICT = mInsignia.getAction_Restrict();
        IMG_FUNCTION_RESTRICT = mInsignia.getFunction_Restrict();

        IMG_DEFINE_EXPLICIT = mInsignia.getDefine_Extern();
        IMG_MOCKIZ_EXPLICIT = mInsignia.getMockiz_Explicit();
        IMG_ACTION_EXPLICIT = mInsignia.getAction_Explicit();
        IMG_FUNCTION_EXPLICIT = mInsignia.getFunction_Explicit();

        IMG_DEFINE_IMPLICIT = mInsignia.getDefine_Implicit();
        IMG_MOCKIZ_IMPLICIT = mInsignia.getMockiz_Implicit();
        IMG_ACTION_IMPLICIT = mInsignia.getAction_Implicit();
        IMG_FUNCTION_IMPLICIT = mInsignia.getFunction_Implicit();

        IMG_DEFINE_TYPE = mInsignia.getDefine_Type();
        IMG_MOCKIZ_TYPE = mInsignia.getMockiz_Type();

        IMG_AUTO_ALL = mInsignia.getAuto_All();
        IMG_AUTO_RESTRICT = mInsignia.getAuto_Restrict();

        IMG_FUNCTOR_ALL = mInsignia.getFunctor_All();
        IMG_FUNCTOR_RESTRICT = mInsignia.getFunctor_Restrict();

        IMG_OPERATOR = mInsignia.getOperator();
        IMG_DIRECTOR = mInsignia.getDirector();

        IMG_GENERIC_TYPE = mInsignia.getGenericType();

        IMG_INIT = mInsignia.getInit();
        IMG_STAGE = mInsignia.getStage();
        IMG_BASE = mInsignia.getBase();
        IMG_MODEL = mInsignia.getModel();


        IMG_GETTER = mInsignia.getGetter();
        IMG_SETTER = mInsignia.getSetter();

        IMG_MARK = mInsignia.getMark();

        IMG_BLOCO_GETTER = mInsignia.getBlocoSetter();
        IMG_BLOCO_SETTER = mInsignia.getBlocoSetter();

    }

    public int getLargura() {
        return eLargura;
    }

    public void run(ArrayList<AST> eASTS, IntellisenseTheme eIntellisenseTheme, String eLocalIntellisenses) {

        mIntellisenseTheme = eIntellisenseTheme;

        for (AST ASTCGlobal : eASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {


                ArrayList<SigmazPackage> mPacotes = new ArrayList<SigmazPackage>();
                SigmazRaiz mSigmazRaiz = new SigmazRaiz(ASTCGlobal);


                for (AST Struct_AST : ASTCGlobal.getASTS()) {
                    if (Struct_AST.mesmoTipo("PACKAGE")) {
                        mPacotes.add(new SigmazPackage(Struct_AST));
                    }
                }

                IS_Sigmaz mISig = new IS_Sigmaz(this, mIntellisenseTheme);
                IS_Package mIP = new IS_Package(this, mIntellisenseTheme);


                mISig.sigmaz_raiz_horizontal(eLocalIntellisenses, "SIGMAZ_1", mSigmazRaiz, eLargura);
                mIP.sigmaz_horizontal(eLocalIntellisenses, "SIGMAZ_2", mSigmazRaiz, eLargura);

                //  mISig.sigmaz_raiz(eLocalIntellisenses, "SIGMAZ_3", mSigmazRaiz, eLargura);
                //  mIP.sigmaz_vertical(eLocalIntellisenses, "SIGMAZ_4", mSigmazRaiz, eLargura);


                for (SigmazPackage ePacote : mPacotes) {

                    mIP.sigmaz_package_horizontal(eLocalIntellisenses, ePacote.getNome(), ePacote, eLargura);
                    //    mIP.sigmaz_package_vertical(eLocalIntellisenses,ePacote.getNome() + "_v", ePacote, eLargura);

                }


            }

        }
    }

    public int algum(Graphics g, int mais, String eInfo, BufferedImage ePino) {

        Rectangle r = new Rectangle(30, mais, eLargura, 100);

        g.setColor(Color.BLACK);

        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = eFonte_Normal.getStringBounds(eInfo, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;


        g.drawImage(ePino, r.x, r.y + 33, ePino.getWidth(), ePino.getHeight(), null);
        g.setColor(mIntellisenseTheme.getTexto());

        g.setFont(eFonte_Normal);

        g.drawString(eInfo, r.x + 40, r.y + b);


        mais += 30;


        return mais;
    }

    public int titulo(Graphics g, int x, int mais, int iLargura, String eTitulo) {

        Rectangle r = new Rectangle(x, mais, iLargura, mCaixaAltura);


        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = eFonte_Titulo.getStringBounds(eTitulo, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;
        g.setColor(mIntellisenseTheme.getTexto());

        g.setFont(eFonte_Titulo);
        g.drawString(eTitulo, r.x + a, r.y + b);

        mais += getTamanhoTitulado();

        return mais;
    }

    public void tituloGrande(Graphics g, int x, int mais, int iLargura, String eTitulo) {

        Rectangle r = new Rectangle(x, mais, iLargura, mCaixaGrandeAltura);


        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = eFonte_TituloGrande.getStringBounds(eTitulo, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;
        g.setColor(mIntellisenseTheme.getTexto());

        g.setFont(eFonte_TituloGrande);
        g.drawString(eTitulo, r.x + a, r.y + b);

        mais += mAposTituloGrande;

    }

    public int algum(Graphics g, int x, int y, String eInfo, BufferedImage ePino) {


        g.setColor(Color.BLACK);

        Rectangle r = new Rectangle(x, y, eLargura, 100);
        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = eFonte_Normal.getStringBounds(eInfo, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;


        g.drawImage(ePino, r.x, r.y + 33, ePino.getWidth(), ePino.getHeight(), null);

        g.setColor(mIntellisenseTheme.getTexto());

        g.setFont(eFonte_Normal);

        g.drawString(eInfo, r.x + 40, r.y + b);


        y += mAposTexto +20;


        return y;
    }

    public int leftString_Normal(Graphics g, int x, int mais, int iLargura, String s, BufferedImage ePino) {

        Rectangle r = new Rectangle(x, mais, iLargura, mCaixaAltura);


        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = eFonte_Normal.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;


        g.drawImage(ePino, r.x, r.y + (b / 2), ePino.getWidth(), ePino.getHeight(), null);

        g.setColor(mIntellisenseTheme.getTexto());

        g.setFont(eFonte_Normal);

        g.drawString(s, r.x + 40, r.y + b);

        mais += getAposTexto();

        return mais;
    }

    public int criarBox(Graphics g, int mais, int x) {

        mais += mEspecadorAntesBox;
        g.setColor(mIntellisenseTheme.getBox());
        g.fillRect(x + 50, mais, eLargura / 2, mAlturaBox);

        return mais;
    }

    public int criarBox_vertical(Graphics g, int mais) {

        mais += mEspecadorAntesBox;

        g.setColor(mIntellisenseTheme.getBox());
        g.fillRect(eLargura / 4, mais, eLargura / 2, mAlturaBox);


        return mais;
    }

    public int criarBox_horizontal(Graphics g, int mais, int eColunador) {
        mais += mEspecadorAntesBox;
        g.setColor(mIntellisenseTheme.getBox());
        g.fillRect((eColunador * eLargura) + (eLargura / 4), mais, eLargura / 2, 15);

        return mais;
    }

    public int criarCaixaTitulo(Graphics g, int x, int mais, Color eCor) {

        g.setColor(eCor);
        g.fillRect(x, mais, getLargura(), mCaixaAltura);

        return mais;
    }


    public int criarCaixaTituloGrande(Graphics g, int x, int mais, int iLargura, Color eCor) {

        g.setColor(eCor);
        g.fillRect(x, mais, iLargura, mCaixaGrandeAltura);

        return mais;
    }

    public void criarFundo(Graphics g, Color eCor, int x, int mais, int iLargura, int iAltura) {

        g.setColor(eCor);
        g.fillRect(x, mais, iLargura, iAltura);

    }

    public int getTamanhoTitulado() {
        return mTamanhoTitulado;
    }

    public int getAposTexto() {
        return mAposTexto;
    }

    public boolean deveSeparar(boolean anterior, boolean tem) {

        boolean ret = false;

        if (anterior) {

            if (tem) {

                ret = true;

            }
        }

        return ret;
    }

    public int getTamanhoItem() {
        return mItem;
    }

    public int getTamanhoCaixa() {
        return mTamanhoCaixa;
    }

}
