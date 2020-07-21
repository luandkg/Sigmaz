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

public class OAQuadrum {


    public OAQuadrum() {


    }

    public String getData() {
        return Tempo.getData();
    }

    public void exportar(String eArquivo, ArrayList<Note> mNotes, ArrayList<Note> mConfig) {


        Agrupador<Note> mAgrupador = new Agrupador<Note>();

        ArrayList<String> mFinalizados = new ArrayList<String>();
        ArrayList<String> mCancelados = new ArrayList<String>();

        String mTitulo = "";

        for (Note eConfig : mConfig) {

            mTitulo = eConfig.getNome();

            for (Pagina mPagina : eConfig.getPaginas()) {
                if (mPagina.getNome().contentEquals("Colors")) {
                    for (Tarefa mTarefa : mPagina.getTarefas()) {

                        mAgrupador.agrupar(mTarefa.getMarcador()).setValor(mTarefa.getNome());

                    }
                } else if (mPagina.getNome().contentEquals("End")) {
                    for (Tarefa mTarefa : mPagina.getTarefas()) {
                        mFinalizados.add(mTarefa.getMarcador());

                    }
                } else if (mPagina.getNome().contentEquals("Cancel")) {
                    for (Tarefa mTarefa : mPagina.getTarefas()) {
                        mCancelados.add(mTarefa.getMarcador());
                    }
                }
            }


        }

        for (Note eNote : mNotes) {

            mAgrupador.agrupar(eNote.getMarcador()).adicionar(eNote);


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

        for (OA.Grupo<Note> Grupo : mAgrupador.getGrupos()) {
            int cAltura = eAltura;
            for (Note mObjeto : Grupo.getObjetos()) {

                if (mObjeto.getTarefas().size() > 0) {
                    cAltura += 50;
                    for (Tarefa mTarefa : mObjeto.getTarefas()) {
                        cAltura += 50;
                    }
                }

                if (mObjeto.getPaginas().size() > 0) {
                    cAltura += 50;
                    for (Pagina mPagina : mObjeto.getPaginas()) {
                        cAltura += 50;

                        if (mPagina.getTarefas().size() > 0) {
                            cAltura += 50;
                            for (Tarefa mTarefa : mPagina.getTarefas()) {
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


        for (OA.Grupo<Note> Grupo : mAgrupador.getGrupos()) {


            Color eCor = new Color(189, 195, 199);
            String eInfo = Grupo.getNome();

            String eNome = Grupo.getValor();
            eCor = getCor(eInfo, mConfig);


            g.setColor(eCor);
            g.fillRect(x, y, eLarguraGrupo, 100);

            g.setColor(Color.BLACK);

            centerString(g, new Rectangle(x, y, eLarguraGrupo, 100), eNome, new Font("TimesRoman", Font.BOLD, 50));

            int cy = y + 110;


            for (Note mObjeto : Grupo.getObjetos()) {


                String eNota = mObjeto.getNome();


                if (mObjeto.getTarefas().size() > 0) {

                    int todas = 0;
                    int completa = 0;

                    for (Tarefa mTarefa : mObjeto.getTarefas()) {

                        if (mFinalizados.contains(mTarefa.getMarcador())){
                            completa+=1;
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

                    for (Tarefa mTarefa : mObjeto.getTarefas()) {

                      Color  tarefaCor = getCor(mTarefa.getMarcador(), mConfig);

                        if (mCancelados.contains(mTarefa.getMarcador())) {
                            leftString_ComQuad_Cortada(g, new Rectangle(x + 100, cy, eLarguraGrupo, 100), mTarefa.getNome(), new Font("TimesRoman", Font.BOLD, 30), tarefaCor);
                        }else{
                            leftString_ComQuad(g, new Rectangle(x + 100, cy, eLarguraGrupo, 100), mTarefa.getNome(), new Font("TimesRoman", Font.BOLD, 30), tarefaCor);
                        }




                        ti += 1;
                        if (ti < to) {
                            cy += 50;
                        }


                    }
                }


                if (mObjeto.getPaginas().size() > 0) {

                    cy += 50;

                    for (Pagina mPagina : mObjeto.getPaginas()) {



                        String ePaginaNome = mPagina.getNome();


                        if (mPagina.getTarefas().size() > 0) {

                            int todas = 0;
                            int completa = 0;

                            for (Tarefa mTarefa : mPagina.getTarefas()) {

                                if (mFinalizados.contains(mTarefa.getMarcador())){
                                    completa+=1;
                                }
                                todas += 1;

                            }
                            if (todas > 0) {
                                ePaginaNome += " [ " + completa + " de " + todas + " ]";

                            }
                        }

                        int xFase = x +80;

                        leftString_ComPagina    (g, new Rectangle(xFase, cy, eLarguraGrupo, 100), ePaginaNome, new Font("TimesRoman", Font.BOLD, 30), eCor);



                        if (mPagina.getTarefas().size() > 0) {

                            cy += 50;

                            int ti = 0;
                            int to = mPagina.getTarefas().size();

                            for (Tarefa mTarefa : mPagina.getTarefas()) {

                                Color  tarefaCor = getCor(mTarefa.getMarcador(), mConfig);

                                if (mCancelados.contains(mTarefa.getMarcador())){
                                    leftString_ComQuad_Cortada(g, new Rectangle(xFase + 100, cy, eLarguraGrupo, 100), mTarefa.getNome(), new Font("TimesRoman", Font.PLAIN, 30), tarefaCor);

                                }else{
                                    leftString_ComQuad(g, new Rectangle(xFase + 100, cy, eLarguraGrupo, 100), mTarefa.getNome(), new Font("TimesRoman", Font.PLAIN, 30), tarefaCor);

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


    public String getNome(String eMarca, ArrayList<Note> mConfig) {

        String ret = "";
        boolean enc = false;

        for (Note eConfig : mConfig) {

            for (Pagina mPagina : eConfig.getPaginas()) {
                if (mPagina.getNome().contentEquals("Colors")) {
                    for (Tarefa mTarefa : mPagina.getTarefas()) {

                       // System.out.println("Passando por " + mTarefa.getMarcador() + " atras de " + eMarca);

                        if (mTarefa.getMarcador().contentEquals(eMarca)) {
                            ret = mTarefa.getNome();
                            enc = true;
                            break;
                        }

                        if (enc) {
                            break;
                        }

                    }
                }

                if (enc) {
                    break;
                }
            }

            if (enc) {
                break;
            }
        }

        return ret;
    }

    public Color getCor(String eMarca, ArrayList<Note> mConfig) {

        Color ret = Color.WHITE;

        boolean enc = false;

        for (Note eConfig : mConfig) {

            for (Pagina mPagina : eConfig.getPaginas()) {
                if (mPagina.getNome().contentEquals("Colors")) {
                    for (Tarefa mTarefa : mPagina.getTarefas()) {

                    //    System.out.println("Passando por " + mTarefa.getMarcador() + " atras de " + eMarca);

                        if (mTarefa.getMarcador().contentEquals(eMarca)) {

                            if (mTarefa.getTags().size() >= 3) {

                                String sr = mTarefa.getTags().get(0);
                                String sg = mTarefa.getTags().get(1);
                                String sb = mTarefa.getTags().get(2);

                            //    System.out.println("Cor :: " + sr + " " + sg + " " + sb);

                                try {
                                    int r = Integer.parseInt(sr);
                                    int g = Integer.parseInt(sg);
                                    int b = Integer.parseInt(sb);

                                    if (r >= 0 && r <= 255) {
                                        if (g >= 0 && g <= 255) {
                                            if (b >= 0 && b <= 255) {
                                                ret = new Color(r, g, b);
                                            }
                                        }
                                    }

                                } catch (Exception e) {

                                }

                            }


                            enc = true;
                            break;
                        }

                        if (enc) {
                            break;
                        }

                    }
                }

                if (enc) {
                    break;
                }
            }

            if (enc) {
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

        g.fillRect(r.x+40, r.y + b - (b/4) , rWidth, 5);

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

        g.fillRect(r.x, r.y + b , rWidth, 5);


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

        g.fillRect(r.x+10, r.y + b - 30, 20, 40);

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

        if (sdia.length() == 1) {
            sdia = "0" + sdia;
        }
        if (smes.length() == 1) {
            smes = "0" + smes;
        }

        return ano + "_" + smes + "_" + sdia;

    }

}
