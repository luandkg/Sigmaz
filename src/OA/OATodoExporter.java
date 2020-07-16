package OA;

import OA.LuanDKG.LuanDKG;
import OA.LuanDKG.Objeto;
import OA.LuanDKG.Pacote;
import Sigmaz.Utils.Tempo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class OATodoExporter {


    private ArrayList<Anotacao> mAnotacoes;


    public OATodoExporter(ArrayList<Anotacao> eAnotacoes) {
        mAnotacoes = eAnotacoes;

    }

    public String getData() {
        return Tempo.getData();
    }

    public void exportar(String mArquivo, String eArquivo) {


        File arq = new File(mArquivo);
        LuanDKG mTodo = new LuanDKG();

        if (arq.exists()) {
            mTodo.Abrir(mArquivo);
        }


        Pacote OATodo = mTodo.UnicoPacote("OATodo");
        Pacote OATodos = OATodo.UnicoPacote("Todos");


        Agrupador<Objeto> mAgrupador = new Agrupador<Objeto>();

        for (Anotacao mAnotacao : mAnotacoes) {

            mAgrupador.agrupar(mAnotacao.getReal());

        }

        for (Objeto mObjeto : OATodos.getObjetos()) {

            String eNota = mObjeto.Identifique("Texto").getValor();
            if (eNota.contentEquals("")) {

            } else {
                String eStatus = mObjeto.Identifique("Status").getValor();

                mAgrupador.agrupar(eStatus).adicionar(mObjeto);
            }

        }


        Color eBarra = new Color(52, 73, 94);

        int separador = 30;

        int eLarguraGrupo = 600;
        int eAltura = 500;

        int eColunas = mAgrupador.getGrupos().size();
        if (eColunas == 0) {
            eColunas = 1;
        }

        int eLargura = separador + ((eLarguraGrupo + separador) * eColunas);


        int maxAltura = eAltura;

        for (OA.Grupo<Objeto> Grupo : mAgrupador.getGrupos()) {
            int cAltura = eAltura;
            for (Objeto mObjeto : Grupo.getObjetos()) {


                String eNota = mObjeto.Identifique("Texto").getValor();


                ArrayList<String> mTags = obterTags(mObjeto.Identifique("Tarefas").getValor());

                if (mTags.size() > 0) {

                    cAltura += 50;

                    for (String t : mTags) {

                        cAltura += 50;

                    }
                }

                cAltura += 50;

            }
            if (cAltura > maxAltura) {
                maxAltura = cAltura;
            }
        }

        eAltura = maxAltura;


        BufferedImage img = new BufferedImage(eLargura, eAltura, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, eLargura, eAltura);


        g.setColor(eBarra);
        g.fillRect(0, 0, eLargura, 100);

        eBarra = Color.GREEN;

        g.setColor(Color.WHITE);

        centerString(g, new Rectangle(0, 0, eLargura, 100), "TODO", new Font("TimesRoman", Font.BOLD, 50));
        leftString_sem(g, new Rectangle( 30, 0, eLarguraGrupo, 100), getDia().replace("_"," "), new Font("TimesRoman", Font.BOLD, 30));

        int x = 0;
        int y = 110;

        x += separador;


        for (OA.Grupo<Objeto> Grupo : mAgrupador.getGrupos()) {


            Color eCor = new Color(189, 195, 199);
            String eNome = Grupo.getNome();

            for (Anotacao mAnotacao : mAnotacoes) {
                if (mAnotacao.getReal().contentEquals(Grupo.getNome())) {
                    eCor = mAnotacao.getCor();
                    eNome = mAnotacao.getNome();
                    break;
                }
            }
            g.setColor(eCor);
            g.fillRect(x, y, eLarguraGrupo, 100);

            g.setColor(Color.BLACK);

            centerString(g, new Rectangle(x, y, eLarguraGrupo, 100), eNome, new Font("TimesRoman", Font.BOLD, 50));

            int cy = y + 110;


            for (Objeto mObjeto : Grupo.getObjetos()) {


                String eNota = mObjeto.Identifique("Texto").getValor();


                ArrayList<String> mTags = obterTags(mObjeto.Identifique("Tarefas").getValor());
                if (mTags.size() > 0) {

                    int todas = 0;
                    int completa = 0;

                    for (String t : mTags) {
                        todas += 1;
                        if (t.contains("@")) {
                            completa += 1;
                        }


                    }
                    if (todas > 0) {
                        eNota += " [ " + completa + " de " + todas + " ]";

                    }
                }


                leftString_ComBar(g, new Rectangle(x + 30, cy, eLarguraGrupo, 100), eNota, new Font("TimesRoman", Font.BOLD, 30), eCor);


                if (mTags.size() > 0) {

                    cy += 50;

                    int ti=0;
                    int to = mTags.size();

                    for (String t : mTags) {

                        if (t.contains("@")) {
                            t = t.replace("@", "");
                            leftString_ComQuad(g, new Rectangle(x + 100, cy, eLarguraGrupo, 100), t, new Font("TimesRoman", Font.BOLD, 30), new Color(32, 191, 107));
                        } else {
                            leftString_ComQuad(g, new Rectangle(x + 100, cy, eLarguraGrupo, 100), t, new Font("TimesRoman", Font.BOLD, 30),new Color(231, 76, 60));
                        }

                        ti+=1;
                        if (ti<to){
                            cy += 50;
                        }


                    }
                }

                cy += 50;

            }

            x += eLarguraGrupo + separador;
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

    public void leftString_ComQuad(Graphics g, Rectangle r, String s,
                           Font font, Color eCor) {
        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;


        Color rCor = g.getColor();

        g.setColor(eCor);

        g.fillRect(r.x,r.y + b - 20,20,20);

        g.setColor(rCor);

        g.setFont(font);

        g.drawString(s, r.x + 40, r.y + b);
    }

    public void leftString_ComBar(Graphics g, Rectangle r, String s,
                                   Font font, Color eCor) {
        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;


        Color rCor = g.getColor();

        g.setColor(eCor);

        g.fillRect(r.x,r.y + b - 20,40,20);

        g.setColor(rCor);

        g.setFont(font);

        g.drawString(s, r.x + 50, r.y + b);
    }

    public void leftString_sem(Graphics g, Rectangle r, String s,
                           Font font ) {
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
        g.drawString(s, r.x + 40, r.y + b);

    }

    public ArrayList<String> obterTags(String eTexto) {

        ArrayList<String> Tags = new ArrayList<String>();
        int i = 0;
        int o = eTexto.length();

        String mTag = "";

        while (i < o) {
            String l = eTexto.charAt(i) + "";
            if (l.contentEquals("}")) {
                if (mTag.length() > 0) {
                    Tags.add(mTag);
                }
                mTag = "";
            } else if (l.contentEquals(" ")) {
                if (mTag.length() > 0) {
                    Tags.add(mTag);
                }
                mTag = "";
            } else if (l.contentEquals("\t")) {
                if (mTag.length() > 0) {
                    Tags.add(mTag);
                }
                mTag = "";
            } else {
                mTag += l;
            }
            i += 1;
        }

        return Tags;
    }

    public static String getDia() {

        Calendar c = Calendar.getInstance();

        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH) + 1;
        int ano = c.get(Calendar.YEAR);

        String sdia = String.valueOf(dia);
        String smes = String.valueOf(mes);

        if (sdia.length()==1){sdia="0" + sdia;}
        if (smes.length()==1){smes="0" + smes;}

        return ano + "_" + smes + "_" + sdia;

    }

}
