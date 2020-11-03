package Sigmaz.S01_Compilador.C01_Enfileirador;

import Sigmaz.S00_Utilitarios.ImagemEscritor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Planejador {

    public Color getColorHexa(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    public void init(String eArquivo, ArrayList<Etapa> mEtapas) {

        Color mFundo = Color.WHITE;
        Color mTexto = Color.BLACK;
        Color eBarraSigmaz = new Color(96, 125, 139);
        Color mCorCaixa = getColorHexa("#D9E577");

        ImagemEscritor me = new ImagemEscritor();


        BufferedImage tmp = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Graphics gtmp = tmp.getGraphics();

        Font fonte_dentro = new Font("TimesRoman", Font.BOLD, 30);
        gtmp.setFont(fonte_dentro);

        Font fonte_dentroMenor = new Font("TimesRoman", Font.BOLD, 20);


        int mCaixa_Largura = 100;
        int mCaixa_Altura = 100;

        for (Etapa mEtapa : mEtapas) {

            int tam = gtmp.getFontMetrics().stringWidth(mEtapa.getCorrente());
            if (tam > mCaixa_Largura) {
                mCaixa_Largura = tam;
            }

            int ay = mEtapa.getAdicionar().size() * 50;
            if (ay > mCaixa_Altura) {
                mCaixa_Altura = ay;
            }

        }


        int mLargura = 100 + ((mEtapas.size() + 1) * mCaixa_Largura) + 100;
        int mAltura = 500 + mCaixa_Altura;

        BufferedImage img = new BufferedImage(mLargura, mAltura, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(mFundo);
        g.fillRect(0, 0, mLargura, mAltura);

        g.setColor(eBarraSigmaz);
        g.fillRect(0, 0, mLargura, 100);


        g.setColor(mTexto);
        me.centralizado(g, new Rectangle(0, 0, mLargura, 100), "SIGMAZ", new Font("TimesRoman", Font.BOLD, 50));


        for (Etapa mEtapa : mEtapas) {

            String eTitulo = String.valueOf(mEtapa.getEtapaID());
            String eCorrente = mEtapa.getCorrente();

            g.setFont(fonte_dentro);

            int tam = g.getFontMetrics().stringWidth(mEtapa.getCorrente());

            int px = 50 + ((mEtapa.getEtapaID() - 1) * mCaixa_Largura);

            g.setColor(mTexto);

            int tam_superior = g.getFontMetrics().stringWidth(eTitulo);

            me.centralizado(g, new Rectangle(px + (tam / 2), 150, tam_superior, 100), eTitulo, fonte_dentro);
            me.esquerda(g, new Rectangle(px, 250, tam, 100), eCorrente, fonte_dentro);

            int aY = 350;


            for (String a : mEtapa.getAdicionar()) {

                int tama = g.getFontMetrics().stringWidth(a);

                me.esquerda(g, new Rectangle(px, aY, tama, 100), " - " + a, fonte_dentroMenor);
                aY += 50;

            }


        }


        g.setColor(mCorCaixa);
        g.fillRect(50, 350, ((mEtapas.size()) * mCaixa_Largura), 10);

        g.fillRect(50, 320, 10, 60);

        g.fillRect(30, 320, 10, 60);


        try {
            ImageIO.write(img, "png", new File(eArquivo));
        } catch (IOException o) {

        }

    }

}
