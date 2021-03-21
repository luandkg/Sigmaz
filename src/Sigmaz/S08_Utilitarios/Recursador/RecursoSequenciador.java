package Sigmaz.S08_Utilitarios.Recursador;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RecursoSequenciador {

    private int mCaixaGrandeAltura;
    private int mCaixaMediaAltura;
    private int mCaixaMediaPequena;

    private int mTamanho_Grande;
    private int mTamanho_Medio;
    private int mTamanho_Pequena;

    private int mAposGrande;
    private int mAposMedio;
    private int mAposPequena;

    private Font eFonte_TituloGrande;
    private Font eFonte_Media;
    private Font eFonte_Pequena;

    private int mIMAGEM_LARGURA;
    private int mIMAGEM_ALTURA;
    private Color eFundo;


    private BufferedImage img;
    private Graphics g;

    private int ALTURANDO;

    private int eInicioX;
    private int eInicioSubX;

    private int eCaixa;
    private int eCaixaPequena;


    public RecursoSequenciador(int eLargura, int eAltura) {


        mTamanho_Grande = 50;
        mTamanho_Medio = 30;
        mTamanho_Pequena = 20;


        eFonte_TituloGrande = new Font("TimesRoman", Font.BOLD, mTamanho_Grande);
        eFonte_Media = new Font("TimesRoman", Font.BOLD, mTamanho_Medio);
        eFonte_Pequena = new Font("TimesRoman", Font.BOLD, mTamanho_Medio);

        mCaixaGrandeAltura = (mTamanho_Grande * 2) + 20;
        mCaixaMediaAltura = (mTamanho_Medio * 2) + 20;
        mCaixaMediaPequena = (mTamanho_Pequena * 2) + 20;

        mAposGrande = mTamanho_Grande + 20;
        mAposMedio = mTamanho_Medio + 20;
        mAposPequena = mTamanho_Pequena + 20;

        eFundo = Color.WHITE;


        mIMAGEM_LARGURA = eLargura;
        mIMAGEM_ALTURA = eAltura;


        img = new BufferedImage(mIMAGEM_LARGURA, mIMAGEM_ALTURA, BufferedImage.TYPE_INT_ARGB);
        g = img.getGraphics();

        g.setColor(eFundo);
        g.fillRect(0, 0, mIMAGEM_LARGURA, mIMAGEM_ALTURA);

        eInicioX = 250;
        eInicioSubX = 450;

        eCaixa = 700;
        eCaixaPequena = 450;


        ALTURANDO = 0;

    }

    public void criarTitulo(String eNome, Color eCor) {

        criarCaixaTituloGrande(g, 0, ALTURANDO, mIMAGEM_LARGURA, eCor);
        tituloGrande(g, 0, ALTURANDO, mIMAGEM_LARGURA, "SIGMAZ");


    }

    public int criarSessao(String eNome, Color eCor) {

        int ret = ALTURANDO + (mCaixaMediaAltura / 2);

        criarCaixaMedia(g, eInicioX, ALTURANDO, eCaixa, eCor);
        ALTURANDO = tituloMedio(g, eInicioX, ALTURANDO, eCaixa, eNome);
        ALTURANDO += 35;

        return ret;

    }

    public int criarSubSessao(String eNome, Color eCor) {

        ALTURANDO += 35;

        int ret = ALTURANDO + (mCaixaMediaPequena / 2);

        criarCaixaPequena(g, eInicioSubX + 50, ALTURANDO, eCaixaPequena, eCor);
        ALTURANDO = tituloPequeno(g, eInicioSubX, ALTURANDO, eCaixa, eNome);

        return ret;
    }

    public void espacar(int e) {
        ALTURANDO += e;
    }

    public void exportar(String eArquivo) {

        try {
            ImageIO.write(img, "png", new File(eArquivo));
        } catch (IOException e) {

        }

    }


    public int criarCaixaTituloGrande(Graphics g, int x, int mais, int iLargura, Color eCor) {

        g.setColor(eCor);
        g.fillRect(x, mais, iLargura, mCaixaGrandeAltura);

        return mais;
    }

    public int criarCaixaMedia(Graphics g, int x, int mais, int iLargura, Color eCor) {

        g.setColor(eCor);
        g.fillRect(x, mais, iLargura, mCaixaMediaAltura);

        return mais;
    }

    public int criarCaixaPequena(Graphics g, int x, int mais, int iLargura, Color eCor) {

        g.setColor(eCor);
        g.fillRect(x, mais, iLargura, mCaixaMediaPequena);

        return mais;
    }

    public void criarFundo(Graphics g, Color eCor, int x, int mais, int iLargura, int iAltura) {

        g.setColor(eCor);
        g.fillRect(x, mais, iLargura, iAltura);

    }


    public void criarConexao(int x, int ey1, int ey2, Color eCor) {


        g.setColor(eCor);
        g.fillRect(x, ey1, 10, ey2 - ey1);

        g.fillRect(x + 10, ey1, 30, 10);
        g.fillRect(x + 10, ey2 - 10, 30, 10);

        // SETA DIREITA

        int seta_x = x + 10 + 30 + 10;
        int seta_y = ey1 - 5;
        int seta_largura = 25;
        int seta_altura = 20;


        Polygon triangle = new Polygon();
        triangle.addPoint(seta_x, seta_y);
        triangle.addPoint(seta_x, seta_y + seta_altura);

        triangle.addPoint(seta_x + seta_largura, seta_y + (seta_altura / 2));

        g.fillPolygon(triangle);

    }

    public void criarDescida(int x, int ey1, int ey2, Color eCor) {


        g.setColor(eCor);
        g.fillRect(x, ey1, 10, ey2 - ey1);


        // SETA ABAIXO

        int seta_x = x  - 8 ;
        int seta_y = ey2 + 5;
        int seta_largura = 25;
        int seta_altura = 20;


        Polygon triangle = new Polygon();
        triangle.addPoint(seta_x, seta_y);
        triangle.addPoint(seta_x+seta_largura, seta_y );

        triangle.addPoint(seta_x + (seta_largura/2), seta_y + (seta_altura ));

        g.fillPolygon(triangle);

    }

    public int tituloGrande(Graphics g, int x, int mais, int iLargura, String eTitulo) {

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
        g.setColor(Color.BLACK);

        g.setFont(eFonte_TituloGrande);
        g.drawString(eTitulo, r.x + a, r.y + b);

        mais += mAposGrande;
        return mais;

    }

    public int tituloMedio(Graphics g, int x, int mais, int iLargura, String eTitulo) {

        Rectangle r = new Rectangle(x, mais, iLargura, mCaixaMediaAltura);


        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = eFonte_Media.getStringBounds(eTitulo, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;
        g.setColor(Color.BLACK);

        g.setFont(eFonte_Media);
        g.drawString(eTitulo, r.x + a, r.y + b);

        mais += mAposMedio;

        return mais;
    }


    public int tituloPequeno(Graphics g, int x, int mais, int iLargura, String eTitulo) {

        Rectangle r = new Rectangle(x, mais, iLargura, mCaixaMediaPequena);


        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = eFonte_Pequena.getStringBounds(eTitulo, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;
        g.setColor(Color.BLACK);

        g.setFont(eFonte_Pequena);
        g.drawString(eTitulo, r.x + a, r.y + b);

        mais += mAposPequena;
        return mais;

    }
}
