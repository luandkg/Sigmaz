package OA;

import OA.LuanDKG.Objeto;
import OA.LuanDKG.Organizador;
import Sigmaz.Utils.Texto;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class OARoadmap {

    private String mArquivo;

    public OARoadmap(String eArquivo) {
        mArquivo = eArquivo;
    }

    public ArrayList<String> getNovos(String eArquivo) {

        ArrayList<String> novos = new ArrayList<String>();

        File arq = new File(eArquivo);

        if (arq.exists()) {
            String Documento = Texto.Ler(eArquivo);

            if (Documento != null) {
                Documento += "\n";

                int i = 0;
                int o = Documento.length();

                String linha = "";


                while (i < o) {
                    String l = Documento.charAt(i) + "";
                    if (l.contentEquals("\n")) {


                        if (linha.length() > 0) {

                            linha = linha.replace("\t", " ");
                            while (linha.contains("  ")) {
                                linha = linha.replace("  ", " ");
                            }
                            if (!linha.contentEquals("null")) {
                                novos.add(linha);
                            }

                        }

                        linha = "";
                    } else {
                        linha += l;
                    }
                    i += 1;
                }

            }


        }


        return novos;

    }

    public void receber(String eArquivo) {


        String Documento = "";

        File arq = new File(mArquivo);

        if (arq.exists()) {
            Documento = Texto.Ler(mArquivo);
        }


        for (String linha : getNovos(eArquivo)) {

            if (Documento.contentEquals("null")) {
                Documento = getDataHora() + "  -->>  " + linha + "\n";
            } else {
                Documento += "\n" + getDataHora() + "  -->>  " + linha;
            }


        }


        Texto.Escrever(eArquivo, "");
        Texto.Escrever(mArquivo, Documento);

    }


    public static String getDataHora() {

        Calendar c = Calendar.getInstance();

        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH) + 1;
        int ano = c.get(Calendar.YEAR);

        String sdia = String.valueOf(dia);
        String smes = String.valueOf(mes);

        if (sdia.length() == 1) {
            sdia = "0" + sdia;
        }
        if (smes.length() == 1) {
            smes = "0" + smes;
        }

        return ano + " " + smes + " " + sdia;

    }


    public void exportar(String eArquivo) {

        BufferedImage IMG_ITEM = null;
        BufferedImage IMG_ROAD= null;

        try {
            IMG_ITEM = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\define_all.png"));
            IMG_ROAD = ImageIO.read(new File("C:\\Users\\Luand\\OneDrive\\Imagens\\Sigmaz Res\\road.png"));

        } catch (IOException e) {
        }


        String Documento = "";

        File arq = new File(mArquivo);

        if (arq.exists()) {
            Documento = Texto.Ler(mArquivo);
        }


        ArrayList<String> mLinhas = new ArrayList<String>();

        int i = 0;
        int o = Documento.length();

        String mLinha = "";

        while (i < o) {
            String l = Documento.charAt(i) + "";

            if (l.contentEquals("\n")) {
                if (mLinha.length() > 0) {
                    if (mLinha.replace(" ", "").length() > 0) {
                        if (mLinha.replace("\t", "").length() > 0) {
                            mLinhas.add(mLinha);
                        }
                    }
                }
                mLinha = "";
            } else {
                mLinha += l;
            }
            i += 1;
        }

        if (mLinha.length() > 0) {
            if (mLinha.replace(" ", "").length() > 0) {
                if (mLinha.replace("\t", "").length() > 0) {
                    mLinhas.add(mLinha);
                }
            }
        }


        ArrayList<Agrupador<String>> mGrupos = new ArrayList<Agrupador<String>>();

        Organizador<String> mOrganizador = new Organizador<String>();


        for (String eLinha : mLinhas) {
            if (eLinha.length() > 19) {

                String ePrefixo = eLinha.substring(0, 10);
                String eResto = eLinha.substring(18);

                if (eResto.length() > 31) {
                    eResto = eResto.substring(0, 30) + "...";
                }

            //    System.out.println(ePrefixo + " :: " + eResto);

                mOrganizador.agrupar(ePrefixo, mGrupos).adicionar(eResto);
            }

        }

        int Alturamin = 220;

        for (Agrupador<String> Grupo : mGrupos) {
            int alturando = 220;
            for (String mObjeto : Grupo.getObjetos()) {
                alturando+=30;
            }

            if (alturando>Alturamin){
                Alturamin=alturando;
            }
        }


        Color eBarra = new Color(52, 73, 94);

        int separador = 30;

        int eLarguraGrupo = 600;

        int eLargura = eLarguraGrupo;

        if (mGrupos.size() > 0) {
            eLargura = eLarguraGrupo * (mGrupos.size());
        }

        BufferedImage img = new BufferedImage(eLargura, Alturamin, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, eLargura, Alturamin);


        g.setColor(eBarra);
        g.fillRect(0, 0, eLargura, 100);


        int y = 110;
        int c = 0;

        for (Agrupador<String> Grupo : mGrupos) {

            int cAltura = y;

           g.drawImage(IMG_ROAD, (c * eLarguraGrupo) + (eLarguraGrupo/2) - (IMG_ROAD.getWidth()/2), y-90, null);


            centerString(g, new Rectangle(c * eLarguraGrupo, y, eLarguraGrupo, 100), Grupo.getNome(), new Font("TimesRoman", Font.BOLD, 50));

            cAltura += 100;

            for (String mObjeto : Grupo.getObjetos()) {


                leftString(g, new Rectangle((c * eLarguraGrupo) + 80, cAltura, eLarguraGrupo, 100), mObjeto, new Font("TimesRoman", Font.BOLD, 30), IMG_ITEM);

                cAltura += 30;

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
