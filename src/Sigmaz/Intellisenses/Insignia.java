package Sigmaz.Intellisenses;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Insignia {

    private Color mVerde = getColorHex2Rgb("#00a40f");
    private Color mVermelho = getColorHex2Rgb("#FF0000");
    private Color mAzul = getColorHex2Rgb("#1290C3");
    private Color mAzul2 = getColorHex2Rgb("#79ABFF");
    private Color mAmarelo = getColorHex2Rgb("#FFC600");
    private Color mLaranja = getColorHex2Rgb("#DE6546");
    private Color mPreto = getColorHex2Rgb("#2A2A2A");
    private Color mRoxo = getColorHex2Rgb("#FF00FF");

    public Insignia() {


    }

    public BufferedImage getDefine_All() {
        return getDefine(mVerde);
    }

    public BufferedImage getDefine_Restrict() {
        return getDefine(mVermelho);
    }

    public BufferedImage getDefine_Extern() {
        return getDefine(mAmarelo);
    }

    public BufferedImage getDefine_Type() {
        return getDefine(mAzul2);
    }

    public BufferedImage getMockiz_All() {
        return getMockiz(mVerde);
    }

    public BufferedImage getMockiz_Restrict() {
        return getMockiz(mVermelho);
    }

    public BufferedImage getMockiz_Extern() {
        return getMockiz(mAmarelo);
    }

    public BufferedImage getMockiz_Type() {
        return getMockiz(mAzul2);
    }

    public BufferedImage getAction_All() {
        return getAction(mVerde);
    }

    public BufferedImage getAction_Restrict() {
        return getAction(mVermelho);
    }

    public BufferedImage getAction_Extern() {
        return getAction(mAmarelo);
    }

    public BufferedImage getFunction_All() {
        return getFunction(mVerde);
    }

    public BufferedImage getFunction_Restrict() {
        return getFunction(mVermelho);
    }

    public BufferedImage getFunction_Extern() {
        return getFunction(mAmarelo);
    }

    public BufferedImage getAuto_All() {
        return getAuto(mVerde);
    }

    public BufferedImage getAuto_Restrict( ) {
        return getAuto(mVermelho);
    }

    public BufferedImage getFunctor_All() {
        return getFunctor(mVerde);
    }

    public BufferedImage getFunctor_Restrict() {
        return getFunctor(mVermelho);
    }




    public BufferedImage getDefine(Color eCor) {

        BufferedImage img = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 30, 30);

        int margem = 2;
        int completo = 30 - (2 * margem);

        int dentro = margem + 5;
        int tamanho = 30 - (2 * dentro);

        g.setColor(eCor);
        g.fillRect(margem, margem, completo, completo);

        g.setColor(Color.WHITE);
        g.fillRect(dentro, dentro, tamanho, tamanho);

        return img;
    }

    public BufferedImage getMockiz(Color eCor) {

        BufferedImage img = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 30, 30);

        int margem = 2;
        int completo = 30 - (2 * margem);

        int dentro = margem + 5;
        int tamanho = 30 - (2 * dentro);

        int interno = dentro + 5;
        int interno_largura = 30 - (2 * interno);
        int interno_altura = 5;

        g.setColor(eCor);
        g.fillRect(margem, margem, completo, completo);

        g.setColor(Color.WHITE);
        g.fillRect(dentro, dentro, tamanho, tamanho);

        g.setColor(eCor);
        g.fillRect(interno, interno, interno_largura, interno_altura);


        return img;
    }


    public BufferedImage getAction(Color eCor) {

        BufferedImage img = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 30, 30);

        int margem_x = 2;
        int margem_y = 10;

        int largura = 30 - (2 * margem_x);
        int altura = 30 - (2 * margem_y);

        // int dentro = margem + 5;
        //  int tamanho = 30 - (2 * dentro);

        g.setColor(eCor);
        g.fillRect(margem_x, margem_y + 5, largura, altura);

        //    g.setColor(Color.WHITE);
        //   g.fillRect(dentro, dentro, tamanho, tamanho);

        return img;
    }

    public BufferedImage getFunction(Color eCor) {

        BufferedImage img = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 30, 30);

        int margem_x = 2;
        int margem_y = 10;

        int largura = 20 - (2 * margem_x);
        int altura = 30 - (2 * margem_y);

        int largura_real = largura - 5;

        g.setColor(eCor);
        g.fillRect(margem_x, margem_y + 5, largura_real, altura);

        g.fillRect(margem_x + largura + 2, margem_y + 5, 3, altura);
        g.fillRect(margem_x + largura + 7, margem_y + 5, 3, altura);

        return img;
    }

    public BufferedImage getAuto(Color eCor) {

        BufferedImage img = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 30, 30);

        int margem_x = 2;
        int margem_y = 8;

        int largura = 30 - (2 * margem_x);

        g.setColor(eCor);
        g.fillRect(margem_x, margem_y, largura, 5);

        g.fillRect(margem_x, margem_y + 10, largura, 5);


        return img;
    }


    public BufferedImage getFunctor(Color eCor) {

        BufferedImage img = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 30, 30);

        int margem_x = 2;
        int margem_y = 10;

        int largura = 20 - (2 * margem_x);
        int largura_real = largura - 5;
        int altura = 30 - (2 * margem_y);

        g.setColor(eCor);
        g.fillRect(margem_x, margem_y, largura_real, 5);

        g.fillRect(margem_x, margem_y + 10, largura_real, 5);

        g.fillRect(margem_x + largura + 2, margem_y , 3, 5);
        g.fillRect(margem_x + largura + 7, margem_y , 3, 5);

        g.fillRect(margem_x + largura + 2, margem_y+ 10 , 3, 5);
        g.fillRect(margem_x + largura + 7, margem_y + 10, 3, 5);



        return img;
    }


    public BufferedImage getOperator() {

        BufferedImage img = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 30, 30);

        int margem_x = 2;
        int margem_y = 8;

        int largura = 30 - (2 * margem_x);

        g.setColor(mAzul);
        g.fillRect(margem_x, margem_y, largura, 5);

        g.fillRect(margem_x, margem_y + 10, largura, 5);


        return img;
    }

    public BufferedImage getDirector() {

        BufferedImage img = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 30, 30);

        int margem_x = 2;
        int margem_y = 8;

        int largura = 30 - (2 * margem_x);

        g.setColor(mAzul);

        int a = 6;
        int px = (largura / 2) - (a / 2);

        g.fillRect(margem_x + px, margem_y, a, 10);


        g.fillRect(margem_x, margem_y + 10, largura, 5);


        return img;
    }

    public BufferedImage getMark() {

        BufferedImage img = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 30, 30);

        int margem_x = 2;
        int margem_y = 8;

        int largura = 30 - (2 * margem_x);
        int altura = 30 - (2 * margem_y);

        g.setColor(mAzul);

        g.fillRect(margem_x, margem_y + 5, largura, 5);

        g.fillRect(30-margem_x-5, margem_y , 5, altura);


        return img;
    }


    public BufferedImage getGenericType() {

        BufferedImage img = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 30, 30);

        int margem = 2;
        int completo = 30 - (2 * margem);

        int dentro = margem + 5;
        int tamanho = 30 - (2 * dentro);

        g.setColor(mAzul2);
        g.fillRect(margem, margem, completo, completo);

        g.setColor(Color.WHITE);
        g.fillRect(dentro, dentro, tamanho, tamanho);

        g.setColor(mAzul2);
        g.fillRect(dentro, dentro + (tamanho / 2), tamanho, tamanho / 2);


        return img;
    }

    public BufferedImage getStage() {

        BufferedImage img = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 30, 30);

        int margem_x = 2;
        int margem_y = 8;

        int largura = 30 - (2 * margem_x);

        g.setColor(mLaranja);

        int a = 6;
        int px = (largura / 2) - (a / 2);

        //   g.fillRect(margem_x + px, margem_y, a, 10);


        g.fillRect(margem_x, margem_y + 5, largura, 5);


        return img;
    }


    public BufferedImage getBase() {

        BufferedImage img = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 30, 30);

        int margem_x = 2;
        int margem_y = 2;

        int largura = 30 - (2 * margem_x);
        int altura = 30 - (2 * margem_y);

        g.setColor(mPreto);

        int a = 6;
        int px = (largura / 2) - (a / 2);

        //   g.fillRect(margem_x + px, margem_y, a, 10);


        g.fillRect(margem_x, margem_y, 5, altura);
        g.fillRect(margem_x + 10, margem_y, 5, altura);
        g.fillRect(margem_x + 20, margem_y, 5, altura);


        return img;
    }

    public BufferedImage getModel() {

        BufferedImage img = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 30, 30);

        int margem_x = 2;
        int margem_y = 2;

        int largura = 30 - (2 * margem_x);
        int altura = 30 - (2 * margem_y);

        g.setColor(mRoxo);

        g.fillRect(margem_x, margem_y, 5, altura);
        g.fillRect(margem_x + 10, margem_y, 5, altura);
        g.fillRect(margem_x + 20, margem_y, 5, altura);

        g.fillRect(margem_x,margem_y,largura-margem_x,5);
        g.fillRect(margem_x,30-5-2,largura-margem_x,5);


        return img;
    }

    public BufferedImage getSetter() {

        BufferedImage img = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 30, 30);

        int margem_x = 2;
        int margem_y = 2;

        int largura = 30 - (2 * margem_x);
        int altura = 30 - (2 * margem_y);

        g.setColor(mVerde);


        g.fillRect(margem_x, margem_y, 5, altura);

        g.fillRect(margem_x + 10, margem_y+5, 5, altura-10);

        g.fillRect(margem_x,margem_y + (altura/2) -3,largura-2,6);


        return img;
    }

    public BufferedImage getGetter() {

        BufferedImage img = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 30, 30);

        int margem_x = 2;
        int margem_y = 2;

        int largura = 30 - (2 * margem_x);
        int altura = 30 - (2 * margem_y);

        g.setColor(mVermelho);


        g.fillRect(30-margem_x-5, margem_y, 5, altura);

        g.fillRect(margem_x + 8, margem_y+5, 5, altura-10);

        g.fillRect(margem_x,margem_y + (altura/2) -3,largura-5,6);


        return img;
    }

    public BufferedImage getInit() {

        BufferedImage img = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 30, 30);

        int margem_x = 2;
        int margem_y = 8;

        int largura = 30 - (2 * margem_x);

        g.setColor(mVerde);

        int a = 6;
        int px = (largura / 2) - (a / 2);

        g.fillRect(margem_x + px, margem_y, a, 10);


        g.fillRect(margem_x, margem_y + 15, largura, 5);


        return img;
    }


    public static Color getColorHex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }


}

