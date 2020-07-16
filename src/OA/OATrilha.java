package OA;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OATrilha {

    public void gerarRotas(String eArquivo, ArrayList<Grupo<String>> mGrupos, Color eBarra) {

        BufferedImage IMG_ITEM = null;
        BufferedImage IMG_ROAD = null;

        try {
            IMG_ITEM = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\define_all.png"));
            IMG_ROAD = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\road.png"));

        } catch (IOException e) {
        }


        int Alturamin = 220;

        int acima = 0;
        int abaixo = 0;
        int intercalador = 0;

        for (OA.Grupo<String> Grupo : mGrupos) {

            int alturando = 220;

            for (String mObjeto : Grupo.getObjetos()) {
                alturando += 30;
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


        int separador = 30;

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

        for (OA.Grupo<String> Grupo : mGrupos) {


            if (intercalador == 0) {

                int cAltura = acima - 100;

                g.drawImage(IMG_ROAD, (c * eLarguraGrupo) + (eLarguraGrupo / 2) - (IMG_ROAD.getWidth() / 2), cAltura + 120, null);


                centerString(g, new Rectangle(c * eLarguraGrupo, cAltura, eLarguraGrupo, 100), Grupo.getNome(), new Font("TimesRoman", Font.BOLD, 50));

                cAltura -= 100;

                for (String mObjeto : Grupo.getObjetos()) {


                    leftString(g, new Rectangle((c * eLarguraGrupo) + 80, cAltura, eLarguraGrupo, 100), mObjeto, new Font("TimesRoman", Font.BOLD, 30), IMG_ITEM);

                    cAltura -= 30;

                }


                intercalador = 1;
            } else {
                int cAltura = acima + 110;

                g.drawImage(IMG_ROAD, (c * eLarguraGrupo) + (eLarguraGrupo / 2) - (IMG_ROAD.getWidth() / 2), cAltura - 90, null);


                centerString(g, new Rectangle(c * eLarguraGrupo, cAltura, eLarguraGrupo, 100), Grupo.getNome(), new Font("TimesRoman", Font.BOLD, 50));

                cAltura += 100;

                for (String mObjeto : Grupo.getObjetos()) {


                    leftString(g, new Rectangle((c * eLarguraGrupo) + 80, cAltura, eLarguraGrupo, 100), mObjeto, new Font("TimesRoman", Font.BOLD, 30), IMG_ITEM);

                    cAltura += 30;

                }

                intercalador = 0;
            }


            c += 1;

        }


        try {
            File outputfile = new File(eArquivo);
            ImageIO.write(img, "png", outputfile);
        } catch (
                IOException e) {

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

        g.drawString(s, r.x + 40, r.y + b);
    }

}
