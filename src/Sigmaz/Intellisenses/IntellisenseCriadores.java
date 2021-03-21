package Sigmaz.Intellisenses;

import Sigmaz.S08_Utilitarios.Visualizador.SigmazCast;
import Sigmaz.S08_Utilitarios.Visualizador.SigmazModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IntellisenseCriadores {


    private IntellisensePartes mIntellisensePartes;

    public IntellisenseCriadores(Intellisense eIntellisense, IntellisenseTheme eIntellisenseTheme) {

        mIntellisensePartes = new IntellisensePartes(eIntellisense, eIntellisenseTheme);
    }

    public void cast(String eLocal, SigmazCast eSigmazCast, int eLargura) {

        int c = 0;

        int h = 100 + ((eSigmazCast.getContagem() + c) * 40);

        BufferedImage img = new BufferedImage(eLargura, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        mIntellisensePartes.cast_continuar(g, eSigmazCast, 0, 0, eLargura, h);


        try {
            File outputfile = new File(eLocal + eSigmazCast.getNome() + ".png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {

        }

    }

    public void model(String eLocal, SigmazModel eSigmazModel, int x, int eLargura) {

        int c = 0;

        int h = 100 + ((eSigmazModel.getContagem() + c) * 40);

        BufferedImage img = new BufferedImage(eLargura, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        mIntellisensePartes.model_continuar(g, eSigmazModel, x, 0, h, eLargura);


        try {
            ImageIO.write(img, "png", new File(eLocal + eSigmazModel.getNome() + ".png"));
        } catch (IOException e) {

        }

    }

}
