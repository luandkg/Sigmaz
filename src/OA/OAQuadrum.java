package OA;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import Sigmaz.Utils.Tempo;

public class OAQuadrum {


    public OAQuadrum() {

    }

    public class MarcadorCor {
        private String mMarcador;
        private Color mCor;

        public MarcadorCor(String eMarcador, Color eCor) {
            mMarcador = eMarcador;
            mCor = eCor;
        }

        public String getMarcador() {
            return mMarcador;
        }

        public Color getCor() {
            return mCor;
        }

    }

    public String getData() {
        return Tempo.getData();
    }

    public void exportar(String eArquivo, ArrayList<AOAnotacao> mAOAnotacaos, ArrayList<AOAnotacao> mConfig) {


        Agrupador<AOAnotacao> mAgrupador = new Agrupador<AOAnotacao>();

        ArrayList<String> mFinalizados = new ArrayList<String>();
        ArrayList<String> mCancelados = new ArrayList<String>();

         ArrayList<MarcadorCor> mMarcadores= new ArrayList<MarcadorCor>();

        String mTitulo = "";

        for (AOAnotacao eConfig : mConfig) {

            mTitulo = eConfig.getNome();

            for (AOPagina mAOPagina : eConfig.getPaginas()) {
                if (mAOPagina.getNome().contentEquals("Colors")) {
                    for (AOTarefa mAOTarefa : mAOPagina.getTarefas()) {

                        mAgrupador.agrupar(mAOTarefa.getMarcador()).setValor(mAOTarefa.getNome());

                        if (mAOTarefa.getTags().size() >= 3) {

                            String sr = mAOTarefa.getTags().get(0);
                            String sg = mAOTarefa.getTags().get(1);
                            String sb = mAOTarefa.getTags().get(2);

                            //    System.out.println("Cor :: " + sr + " " + sg + " " + sb);

                            try {
                                int r = Integer.parseInt(sr);
                                int g = Integer.parseInt(sg);
                                int b = Integer.parseInt(sb);

                                if (r >= 0 && r <= 255) {
                                    if (g >= 0 && g <= 255) {
                                        if (b >= 0 && b <= 255) {
                                            //  ret = new Color(r, g, b);

                                            mMarcadores.add(new MarcadorCor(mAOTarefa.getMarcador(), new Color(r, g, b)));
                                        }
                                    }
                                }

                            } catch (Exception e) {

                            }

                        }


                    }
                } else if (mAOPagina.getNome().contentEquals("Markers")) {
                    for (AOTarefa mAOTarefa : mAOPagina.getTarefas()) {

                        if (mAOTarefa.getNome().contentEquals("End")) {
                            for (String mTag : mAOTarefa.getTags()) {
                                mFinalizados.add(mTag);
                            }
                        } else if (mAOTarefa.getNome().contentEquals("Cancel")) {
                            for (String mTag : mAOTarefa.getTags()) {
                                mCancelados.add(mTag);
                            }
                        }

                    }

                }


            }
        }


        for (AOAnotacao eAOAnotacao : mAOAnotacaos) {

            mAgrupador.agrupar(eAOAnotacao.getMarcador()).adicionar(eAOAnotacao);


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

        for (OA.Grupo<AOAnotacao> Grupo : mAgrupador.getGrupos()) {
            int cAltura = eAltura;
            for (AOAnotacao mObjeto : Grupo.getObjetos()) {

                if (mObjeto.getTarefas().size() > 0) {
                    cAltura += 50;
                    for (AOTarefa mAOTarefa : mObjeto.getTarefas()) {
                        cAltura += 50;
                    }
                }

                if (mObjeto.getPaginas().size() > 0) {
                    cAltura += 50;
                    for (AOPagina mAOPagina : mObjeto.getPaginas()) {
                        cAltura += 50;

                        if (mAOPagina.getTarefas().size() > 0) {
                            cAltura += 50;
                            for (AOTarefa mAOTarefa : mAOPagina.getTarefas()) {
                                cAltura += 50;
                            }
                        }


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

        centerString(g, new Rectangle(0, 0, eLargura, 100), mTitulo, new Font("TimesRoman", Font.BOLD, 50));
        leftString_sem(g, new Rectangle(30, 0, eLarguraGrupo, 100), getDia().replace("_", " "), new Font("TimesRoman", Font.BOLD, 30));

        int x = 0;
        int y = 110;

        x += separador;


        for (OA.Grupo<AOAnotacao> Grupo : mAgrupador.getGrupos()) {


            Color eCor = new Color(189, 195, 199);
            String eInfo = Grupo.getNome();

            String eNome = Grupo.getValor();
            eCor = getCor(eInfo, mMarcadores);


            g.setColor(eCor);
            g.fillRect(x, y, eLarguraGrupo, 100);

            g.setColor(Color.BLACK);

            centerString(g, new Rectangle(x, y, eLarguraGrupo, 100), eNome, new Font("TimesRoman", Font.BOLD, 50));

            int cy = y + 110;


            for (AOAnotacao mObjeto : Grupo.getObjetos()) {


                String eNota = mObjeto.getNome();


                if (mObjeto.getTarefas().size() > 0) {

                    int todas = 0;
                    int completa = 0;

                    for (AOTarefa mAOTarefa : mObjeto.getTarefas()) {

                        if (mFinalizados.contains(mAOTarefa.getMarcador())) {
                            completa += 1;
                        }
                        todas += 1;

                    }
                    if (todas > 0) {
                        eNota += " [ " + completa + " de " + todas + " ]";

                    }
                }


                leftString_ComBar(g, new Rectangle(x + 30, cy, eLarguraGrupo, 100), eNota, new Font("TimesRoman", Font.BOLD, 30), eCor);


                if (mObjeto.getTarefas().size() > 0) {

                    cy += 50;

                    int ti = 0;
                    int to = mObjeto.getTarefas().size();

                    for (AOTarefa mAOTarefa : mObjeto.getTarefas()) {

                        Color tarefaCor = getCor(mAOTarefa.getMarcador(), mMarcadores);

                        if (mCancelados.contains(mAOTarefa.getMarcador())) {
                            leftString_ComQuad_Cortada(g, new Rectangle(x + 100, cy, eLarguraGrupo, 100), mAOTarefa.getNome(), new Font("TimesRoman", Font.BOLD, 30), tarefaCor);
                        } else {
                            leftString_ComQuad(g, new Rectangle(x + 100, cy, eLarguraGrupo, 100), mAOTarefa.getNome(), new Font("TimesRoman", Font.BOLD, 30), tarefaCor);
                        }


                        ti += 1;
                        if (ti < to) {
                            cy += 50;
                        }


                    }
                }


                if (mObjeto.getPaginas().size() > 0) {

                    cy += 50;

                    for (AOPagina mAOPagina : mObjeto.getPaginas()) {


                        String ePaginaNome = mAOPagina.getNome();


                        if (mAOPagina.getTarefas().size() > 0) {

                            int todas = 0;
                            int completa = 0;

                            for (AOTarefa mAOTarefa : mAOPagina.getTarefas()) {

                                if (mFinalizados.contains(mAOTarefa.getMarcador())) {
                                    completa += 1;
                                }
                                todas += 1;

                            }
                            if (todas > 0) {
                                ePaginaNome += " [ " + completa + " de " + todas + " ]";

                            }
                        }

                        int xFase = x + 80;

                        leftString_ComPagina(g, new Rectangle(xFase, cy, eLarguraGrupo, 100), ePaginaNome, new Font("TimesRoman", Font.BOLD, 30), eCor);


                        if (mAOPagina.getTarefas().size() > 0) {

                            cy += 50;

                            int ti = 0;
                            int to = mAOPagina.getTarefas().size();

                            for (AOTarefa mAOTarefa : mAOPagina.getTarefas()) {

                                Color tarefaCor = getCor(mAOTarefa.getMarcador(), mMarcadores);

                                if (mCancelados.contains(mAOTarefa.getMarcador())) {
                                    leftString_ComQuad_Cortada(g, new Rectangle(xFase + 100, cy, eLarguraGrupo, 100), mAOTarefa.getNome(), new Font("TimesRoman", Font.PLAIN, 30), tarefaCor);

                                } else {
                                    leftString_ComQuad(g, new Rectangle(xFase + 100, cy, eLarguraGrupo, 100), mAOTarefa.getNome(), new Font("TimesRoman", Font.PLAIN, 30), tarefaCor);

                                }


                                ti += 1;
                                if (ti < to) {
                                    cy += 50;
                                }


                            }
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



    public Color getCor(String eMarca, ArrayList<MarcadorCor> mMarcadores) {

        Color ret = Color.WHITE;

        for (MarcadorCor eMarcadorCor : mMarcadores) {

            if (eMarcadorCor.getMarcador().contentEquals(eMarca)) {
                ret = eMarcadorCor.getCor();
                break;
            }

        }


        return ret;
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

        g.fillRect(r.x, r.y + b - 20, 20, 20);

        g.setColor(rCor);

        g.setFont(font);

        g.drawString(s, r.x + 40, r.y + b);
    }

    public void leftString_ComQuad_Cortada(Graphics g, Rectangle r, String s,
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

        g.fillRect(r.x, r.y + b - 20, 20, 20);

        g.setColor(rCor);

        g.setFont(font);

        g.drawString(s, r.x + 40, r.y + b);

        g.fillRect(r.x + 40, r.y + b - (b / 4), rWidth, 5);

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

        g.fillRect(r.x, r.y + b - 20, 40, 20);

        g.setColor(rCor);

        g.setFont(font);

        g.drawString(s, r.x + 50, r.y + b);
    }

    public void leftString_ComBar_Cortada(Graphics g, Rectangle r, String s,
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

        g.fillRect(r.x, r.y + b - 20, 40, 20);

        g.setColor(rCor);

        g.setFont(font);

        g.drawString(s, r.x + 50, r.y + b);

        g.fillRect(r.x, r.y + b, rWidth, 5);


    }

    public void leftString_ComPagina(Graphics g, Rectangle r, String s,
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


        g.fillRect(r.x, r.y + b - 20, 40, 20);

        g.fillRect(r.x + 10, r.y + b - 30, 20, 40);

        g.setColor(rCor);

        g.setFont(font);

        g.drawString(s, r.x + 50, r.y + b);
    }

    public void leftString_sem(Graphics g, Rectangle r, String s,
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
        g.drawString(s, r.x + 40, r.y + b);

    }


    public static String getDia() {

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

        return ano + "_" + smes + "_" + sdia;

    }

}
