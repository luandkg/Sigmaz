package OA.Apps;

import OA.Utils.BlocoDeTrilha;
import OA.Utils.EmTrila;
import OA.Utils.Grupo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OATrilha {

    public void gerarRotasH(String eArquivo, ArrayList<Grupo<String>> mGrupos, Color eBarra, Color eFonteCor) {

        BufferedImage IMG_ROAD = null;

        try {
            IMG_ROAD = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\road.png"));

        } catch (IOException e) {
        }


        int Alturamin = 220;

        int acima = 0;
        int abaixo = 0;
        int intercalador = 0;

        int entreLinhas = 40;

        for (OA.Utils.Grupo<String> Grupo : mGrupos) {

            int alturando = 220;

            for (String mObjeto : Grupo.getObjetos()) {
                alturando += entreLinhas;
            }

            if (intercalador == 0) {

                if (alturando > acima) {
                    acima = alturando;
                }

                intercalador = 1;
            } else {

                if (alturando > abaixo) {
                    abaixo = alturando;
                }

                intercalador = 0;
            }


        }

        Alturamin = 220 + acima + abaixo;


        int eLarguraGrupo = 600;

        int eLargura = eLarguraGrupo;

        if (mGrupos.size() > 0) {
            eLargura = (eLarguraGrupo * (mGrupos.size())) + 200;
        }

        BufferedImage img = new BufferedImage(eLargura, Alturamin, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, eLargura, Alturamin);


        g.setColor(eBarra);
        g.fillRect(0, acima, eLargura, 100);


        g.setColor(new Color(52, 73, 94));

        int c = 0;

        intercalador = 0;

        for (OA.Utils.Grupo<String> Grupo : mGrupos) {


            if (intercalador == 0) {

                int cAltura = acima - 100;

                g.drawImage(IMG_ROAD, (c * eLarguraGrupo) + (eLarguraGrupo / 2) - (IMG_ROAD.getWidth() / 2), cAltura + 120, null);


                centerString(g, new Rectangle(c * eLarguraGrupo, cAltura, eLarguraGrupo, 100), Grupo.getNome(), new Font("TimesRoman", Font.BOLD, 50), eFonteCor);

                cAltura -= 100;

                for (String mObjeto : Grupo.getObjetos()) {


                    leftString(g, new Rectangle((c * eLarguraGrupo) + 80, cAltura, eLarguraGrupo, 100), mObjeto, new Font("TimesRoman", Font.BOLD, 30),eBarra, eFonteCor);

                    cAltura -= entreLinhas;

                }


                intercalador = 1;
            } else {
                int cAltura = acima + 110;

                g.drawImage(IMG_ROAD, (c * eLarguraGrupo) + (eLarguraGrupo / 2) - (IMG_ROAD.getWidth() / 2), cAltura - 90, null);


                centerString(g, new Rectangle(c * eLarguraGrupo, cAltura, eLarguraGrupo, 100), Grupo.getNome(), new Font("TimesRoman", Font.BOLD, 50), eFonteCor);

                cAltura += 100;

                for (String mObjeto : Grupo.getObjetos()) {


                    leftString(g, new Rectangle((c * eLarguraGrupo) + 80, cAltura, eLarguraGrupo, 100), mObjeto, new Font("TimesRoman", Font.BOLD, 30),eBarra, eFonteCor);

                    cAltura += entreLinhas;

                }

                intercalador = 0;
            }


            c += 1;

        }


        try {
            ImageIO.write(img, "png",  new File(eArquivo));
        } catch (
                IOException e) {

        }

    }


    public void centerString(Graphics g, Rectangle r, String s,
                             Font font, Color mCorFonte) {
        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;

        g.setColor(mCorFonte);

        g.setFont(font);
        g.drawString(s, r.x + a, r.y + b);
    }


    public void leftString(Graphics g, Rectangle r, String s,
                           Font font,Color mCorQuad ,Color mCorFonte) {
        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;

        g.setColor(mCorQuad);
        g.fillRect(r.x, r.y + (b / 2) + (25 / 2), 25, 25);


        g.setFont(font);

        g.setColor(mCorFonte);

        g.drawString(s, r.x + 40, r.y + b);
    }


    public void gerarRotasHV(String eArquivo, ArrayList<Grupo<String>> mGrupos, Color eBarra, Color mCorFonte) {


        BlocoDeTrilha BDT = new BlocoDeTrilha(10);
        for (OA.Utils.Grupo<String> Grupo : mGrupos) {
            BDT.adicionar(Grupo);
        }

        boolean modo = true;

        int Alturamin = 0;
        int eLarguraGrupo = 600;
        int eLargura = (11 * eLarguraGrupo) + 200;

        int eTamanhoFonte_Grande = 30;
        int eTamanhoFonte_Pequena = 10;

        Font eFonte_Grande = new Font("TimesRoman", Font.BOLD, eTamanhoFonte_Grande);
        Font eFonte_Pequena = new Font("TimesRoman", Font.BOLD, eTamanhoFonte_Pequena);

        int entreLinhas = eTamanhoFonte_Grande - 10;
        int Separador = eTamanhoFonte_Grande;

        int SeparadorGrande = (Separador * 2) + 20;
        int Faixa = 2 * eTamanhoFonte_Grande;


        BufferedImage IMG_ROAD = new BufferedImage(eTamanhoFonte_Grande, eTamanhoFonte_Grande, 1);

        for (int x = 0; x < IMG_ROAD.getWidth(); x++) {
            for (int y = 0; y < IMG_ROAD.getHeight(); y++) {
                IMG_ROAD.setRGB(x, y, Color.WHITE.getRGB());
            }
        }


        if (BDT.getTrilhas().size() == 0) {
            Alturamin = 100;
        } else {
            Alturamin = 0;
        }

        boolean a = true;
        for (EmTrila mEmTrila : BDT.getTrilhas()) {
            if (a) {
                mEmTrila.calcular(true);
                a = false;
            } else {
                mEmTrila.calcular(false);
                a = true;
            }
        }

        for (EmTrila mEmTrila : BDT.getTrilhas()) {

            int altura = Faixa + (4 * Separador) + (2 * SeparadorGrande) + (mEmTrila.getAcima() * entreLinhas) + (mEmTrila.getAbaixo() * entreLinhas);

            if (modo) {
                modo = false;
                //   System.out.println("Trilha : " + mEmTrila.getGrupos().size() + " -->> [ " + mEmTrila.getAcima() + " :: " + mEmTrila.getAbaixo() + " -> " + altura + " ] - Indo ");
            } else {
                modo = true;
                //   System.out.println("Trilha : " + mEmTrila.getGrupos().size() + " -->> [ " + mEmTrila.getAcima() + " :: " + mEmTrila.getAbaixo() + " -> " + altura + " ] - Voltando ");
            }

            Alturamin += altura;

        }


        BufferedImage img = new BufferedImage(eLargura, Alturamin, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, eLargura, Alturamin);


        g.setColor(eBarra);

        int eixoY = 0;
        modo = true;

        int eixoX = 400;

        int trilhando = 0;
        int mTrilhas = BDT.getTrilhas().size();

        int mBarraYAnteior = 0;

        for (EmTrila mEmTrila : BDT.getTrilhas()) {

            int superior = (mEmTrila.getAcima() * entreLinhas);
            int inferior = (mEmTrila.getAbaixo() * entreLinhas);

            // System.out.println("Trilha : " + mEmTrila.getGrupos().size() + " -->> [ " + mEmTrila.getAcima() + " :: " + mEmTrila.getAbaixo() + " ] ");
            // System.out.println("\t - Superior : " + mEmTrila.getAcima() + " -->> " + superior);
            // System.out.println("\t - Inferior : " + mEmTrila.getAbaixo() + " -->> " + inferior);
            // System.out.println("\t - Altura : " + mAltura);
            //  if (modo) {
            //      System.out.println("\t - Modo : Indo");
            //  } else {
            //   System.out.println("\t - Modo : Voltando");
            //  }


            int BarraY = eixoY + superior + (2 * Separador) + SeparadorGrande;


            if (modo) {

                if (mEmTrila.getGrupos().size() == 10) {
                    g.setColor(eBarra);
                    g.fillRect(0, BarraY, eLargura, Faixa);
                } else {
                    g.setColor(eBarra);
                    g.fillRect(0, BarraY, ((mEmTrila.getGrupos().size() + 1) * eLarguraGrupo), Faixa);
                }


            } else {

                if (mEmTrila.getGrupos().size() == 10) {
                    g.setColor(eBarra);
                    g.fillRect(0, BarraY, eLargura, Faixa);
                } else {
                    g.setColor(eBarra);
                    g.fillRect(eLargura - ((mEmTrila.getGrupos().size() + 1) * eLarguraGrupo), BarraY, ((mEmTrila.getGrupos().size() + 1) * eLarguraGrupo), Faixa);
                }

            }

            if (trilhando == 0) {


                g.setColor(eBarra);
                g.fillRect(0, BarraY - Faixa, Faixa, Faixa);

                g.fillRect(0, BarraY + Faixa, Faixa, Faixa);


            } else if (trilhando == mTrilhas - 1) {

                if (mEmTrila.getGrupos().size() == 10) {

                    g.setColor(eBarra);
                    g.fillRect(0, BarraY - Faixa, Faixa, Faixa);
                    g.fillRect(0, BarraY + Faixa, Faixa, Faixa);

                } else {

                    if (modo) {

                        g.setColor(eBarra);
                        g.fillRect(((mEmTrila.getGrupos().size() + 1) * eLarguraGrupo) - Faixa, BarraY - Faixa, Faixa, Faixa);
                        g.fillRect(((mEmTrila.getGrupos().size() + 1) * eLarguraGrupo) - Faixa, BarraY + Faixa, Faixa, Faixa);


                    } else {
                        g.setColor(eBarra);
                        g.fillRect(eLargura - ((mEmTrila.getGrupos().size() + 1) * eLarguraGrupo) - Faixa, BarraY - Faixa, Faixa, Faixa);
                        g.fillRect(eLargura - ((mEmTrila.getGrupos().size() + 1) * eLarguraGrupo) - Faixa, BarraY + Faixa, Faixa, Faixa);

                    }


                }

            }

            if (trilhando < mTrilhas - 1) {

                int mAcimaAnterior = BDT.getTrilhas().get(trilhando + 1).getAcima() * entreLinhas;
                int mAltura = inferior + (4 * Separador) + (2 * SeparadorGrande) + Faixa + mAcimaAnterior;


                if (modo) {

                    g.setColor(eBarra);
                    g.fillRect(eLargura - Faixa, BarraY + Faixa, Faixa, mAltura);

                } else {

                    g.setColor(eBarra);
                    g.fillRect(0, BarraY + Faixa, Faixa, mAltura);
                }

            }

            mBarraYAnteior = BarraY;

            int c = 0;

            if (modo) {
                c = 0;
            } else {
                c = 9;
            }

            boolean submodo = mEmTrila.getIntercalador();
            for (OA.Utils.Grupo<String> Grupo : mEmTrila.getGrupos()) {


                g.drawImage(IMG_ROAD, (c * eLarguraGrupo) + (eLarguraGrupo / 2) - (IMG_ROAD.getWidth() / 2) + eixoX, BarraY + (eTamanhoFonte_Grande / 2), null);

                if (submodo) {

                    int alturaBloco = BarraY + SeparadorGrande;

                    centerString(g, new Rectangle((c * eLarguraGrupo) + eixoX, alturaBloco, eLarguraGrupo, 100), Grupo.getNome(), eFonte_Grande, mCorFonte);

                    alturaBloco += (2 * Separador);

                    for (String mObjeto : Grupo.getObjetos()) {

                        leftString(g, new Rectangle((c * eLarguraGrupo) + eixoX + 80, alturaBloco, eLarguraGrupo, 100), mObjeto, eFonte_Pequena,eBarra, mCorFonte);

                        alturaBloco += entreLinhas;

                    }

                } else {

                    int alturaBloco = BarraY - SeparadorGrande;

                    centerString(g, new Rectangle((c * eLarguraGrupo) + eixoX, alturaBloco, eLarguraGrupo, 100), Grupo.getNome(), eFonte_Grande, mCorFonte);

                    alturaBloco -= (2 * Separador);

                    for (String mObjeto : Grupo.getObjetos()) {

                        leftString(g, new Rectangle((c * eLarguraGrupo) + eixoX + 80, alturaBloco, eLarguraGrupo, 100), mObjeto, eFonte_Pequena,eBarra, mCorFonte);

                        alturaBloco -= entreLinhas;

                    }

                }

                if (modo) {
                    c += 1;
                } else {
                    c -= 1;
                }


                if (submodo) {
                    submodo = false;
                } else {
                    submodo = true;
                }

            }


            if (modo) {
                modo = false;
            } else {
                modo = true;
            }


            eixoY = BarraY + inferior + (2 * Separador) + SeparadorGrande + Faixa;
            trilhando += 1;

        }


        try {
            ImageIO.write(img, "png", new File(eArquivo));
        } catch (
                IOException e) {
        }

    }

}
