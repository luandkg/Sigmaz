package Sigmaz.Intellisenses;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Sigmaz.S08_Utilitarios.Visualizador.*;

public class IS_Package {

    private Intellisense mIntellisense;
    private IntellisenseTheme mIntellisenseTheme;

    public IS_Package(Intellisense eIntellisense, IntellisenseTheme eIntellisenseTheme) {

        mIntellisense = eIntellisense;
        mIntellisenseTheme = eIntellisenseTheme;

    }


    public void sigmaz_vertical(String eLocal, String eNomeArquivo, SigmazRaiz eSigmazRaiz, int eLargura) {

        vertical(eLocal, eNomeArquivo,"SIGMAZ", eSigmazRaiz.getCasts(), eSigmazRaiz.getTypes(), eSigmazRaiz.getModelos(), eSigmazRaiz.getStages(), eSigmazRaiz.getStructs(), eSigmazRaiz.getExternals(), eLargura);

    }


    public void sigmaz_package_vertical(String eLocal,String eNomeArquivo, SigmazPackage eSigmazPacote, int eLargura) {

        vertical(eLocal, eNomeArquivo,eSigmazPacote.getNome(), eSigmazPacote.getCasts(), eSigmazPacote.getTypes(), eSigmazPacote.getModels(), eSigmazPacote.getStages(), eSigmazPacote.getStructs(), eSigmazPacote.getExternals(), eLargura);


    }

    public void vertical(String eLocal, String eNomeArquivo,String eNome,
                         ArrayList<SigmazCast> mCasts,
                         ArrayList<SigmazType> mTypes,
                         ArrayList<SigmazModel> mModels,
                         ArrayList<SigmazStages> mStages,
                         ArrayList<SigmazStruct> mStructs,
                         ArrayList<SigmazExternal> mExternals,
                         int eLargura) {


        Color eBarraPackage = mIntellisenseTheme.getPackage();
        Color eTexto = mIntellisenseTheme.getTexto();


        int h = 0;


        for (SigmazCast ss : mCasts) {
            h += (ss.getContagem() * mIntellisense.getTamanhoItem());
        }

        for (SigmazStages ss : mStages) {
            h += (ss.getContagem() * mIntellisense.getTamanhoItem());
        }

        for (SigmazStruct ss : mStructs) {
            h += (ss.getContagem() * mIntellisense.getTamanhoItem());
        }

        for (SigmazType ss : mTypes) {
            h += (ss.getContagem() * mIntellisense.getTamanhoItem());
        }

        for (SigmazModel ss : mModels) {
            h += (ss.getContagem() * mIntellisense.getTamanhoItem());
        }

        for (SigmazExternal ss :mExternals) {
            h += (ss.getContagem() * mIntellisense.getTamanhoItem());
        }


        BufferedImage img = new BufferedImage(eLargura, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(mIntellisenseTheme.getFundo());
        g.fillRect(0, 0, eLargura, h);


        g.setColor(eBarraPackage);
        g.fillRect(0, 0, eLargura, 100);


        g.setColor(eTexto);
        mIntellisense.titulo(g, 0, 0, eLargura,  eNome);


        int mais = 110;


        g.setColor(Color.BLACK);

        IntellisensePartes mIP = new IntellisensePartes(mIntellisense, mIntellisenseTheme);

        for (SigmazCast ss : mCasts) {
            mais = mIP.cast_continuar(g, ss, 0, mais, eLargura, h);
        }

        for (SigmazType ss : mTypes) {
            mais = mIP.type_continuar(g, ss, 0, mais, eLargura, h);
        }

        for (SigmazStages ss : mStages) {
            mais = mIP.stages_continuar(g, ss, 0, mais, eLargura, h);
        }

        for (SigmazModel ss : mModels) {
            mais = mIP.model_continuar(g, ss, 0, mais, eLargura, h);
        }

        for (SigmazStruct ss : mStructs) {
            mais = mIP.struct_continuar(g, ss, 0, mais, eLargura, h);
        }

        for (SigmazExternal ss : mExternals) {
            mais = mIP.external_continuar(g, ss, 0, mais, eLargura, h);
        }


        try {
            ImageIO.write(img, "png", new File(eLocal + eNomeArquivo + ".png"));
        } catch (IOException e) {

        }

    }







    public void sigmaz_horizontal(String eLocal, String eNome, SigmazRaiz eSigmazRaiz, int eLargura) {

        horizontal(eLocal, eNome, eSigmazRaiz.getCasts(), eSigmazRaiz.getTypes(), eSigmazRaiz.getModelos(), eSigmazRaiz.getStages(), eSigmazRaiz.getStructs(), eSigmazRaiz.getExternals(), eLargura);

    }

    public void sigmaz_package_horizontal(String eLocal,String eNome, SigmazPackage eSigmazPacote, int eLargura) {

        horizontal(eLocal, eNome, eSigmazPacote.getCasts(), eSigmazPacote.getTypes(), eSigmazPacote.getModels(), eSigmazPacote.getStages(), eSigmazPacote.getStructs(), eSigmazPacote.getExternals(), eLargura);


    }

    public void horizontal(String eLocal, String eNome,
                                            ArrayList<SigmazCast> mCasts,
                                            ArrayList<SigmazType> mTypes,
                                            ArrayList<SigmazModel> mModels,
                                            ArrayList<SigmazStages> mStages,
                                            ArrayList<SigmazStruct> mStructs,
                                            ArrayList<SigmazExternal> mExternals,
                                            int eLargura) {


        eLargura += 30;


        Color eBarraPackage = mIntellisenseTheme.getPackage();
        Color eFundo = mIntellisenseTheme.getFundo();


        int colunas = 0;
        int altura = 200;

        int alturaMinima = 200;

        for (SigmazStages ss : mStages) {

            int h = alturaMinima + (ss.getContagem() * mIntellisense.getTamanhoItem())  + (ss.getSeparadores() * mIntellisense.getTamanhoCaixa());
            colunas += 1;

            if (h > altura) {
                altura = h;
            }
        }

        for (SigmazCast ss : mCasts) {

            int h = alturaMinima + (ss.getContagem() * mIntellisense.getTamanhoItem());
            colunas += 1;

            if (h > altura) {
                altura = h;
            }
        }

        for (SigmazModel ss : mModels) {

            int h = alturaMinima + (ss.getContagem() * mIntellisense.getTamanhoItem());
            colunas += 1;

            if (h > altura) {
                altura = h;
            }
        }

        for (SigmazStruct ss : mStructs) {

            int h = alturaMinima + (ss.getContagem() * mIntellisense.getTamanhoItem())  + (ss.getSeparadores() * mIntellisense.getTamanhoCaixa());
            colunas += 1;

            if (h > altura) {
                altura = h;
            }
        }

        for (SigmazType ss : mTypes) {

            int h = alturaMinima + (ss.getContagem() * mIntellisense.getTamanhoItem());
            colunas += 1;

            if (h > altura) {
                altura = h;
            }
        }

        for (SigmazExternal ss : mExternals) {

            int h = alturaMinima + (ss.getContagem() * mIntellisense.getTamanhoItem());
            colunas += 1;

            if (h > altura) {
                altura = h;
            }
        }


        if (colunas == 0) {
            colunas += 1;
        }

        int IMAGEM_LARGURA = (eLargura * colunas) - 30;
        int IMAGEM_ALTURA = altura;

        // System.out.println("Alocar Imagem Largura : " + IMAGEM_LARGURA + " Altura : " + IMAGEM_ALTURA);

        BufferedImage img = new BufferedImage(IMAGEM_LARGURA, IMAGEM_ALTURA, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(eFundo);
        g.fillRect(0, 0, eLargura * colunas, altura);


        mIntellisense.criarCaixaTituloGrande(g,0,0,eLargura * colunas,eBarraPackage);
        mIntellisense.tituloGrande(g,0, 0, eLargura * colunas,  eNome);

        IntellisensePartes mIP = new IntellisensePartes(mIntellisense, mIntellisenseTheme);

        int colunando = 0;

        for (SigmazCast eSigmazCast : mCasts) {

            mIP.cast_continuar(g, eSigmazCast, colunando * eLargura, 110, eLargura, altura);
            colunando += 1;

        }

        for (SigmazModel ss : mModels) {

            mIP.model_continuar(g, ss, (colunando * eLargura) , 110, eLargura, altura);
            colunando += 1;

        }

        for (SigmazStages ss : mStages) {

            mIP.stages_continuar(g, ss, colunando * eLargura, 110, eLargura, altura);
            colunando += 1;

        }

        for (SigmazType ss : mTypes) {

            mIP.type_continuar(g, ss, colunando * eLargura, 110, eLargura, altura);
            colunando += 1;

        }

        for (SigmazStruct ss : mStructs) {

            mIP.struct_continuar(g, ss, colunando * eLargura, 110, eLargura, altura);

            colunando += 1;

        }

        for (SigmazExternal ss : mExternals) {

            mIP.external_continuar(g, ss, colunando * eLargura, 110, eLargura, altura);
            colunando += 1;

        }


        try {
            ImageIO.write(img, "png", new File(eLocal + eNome + ".png"));
        } catch (IOException e) {

        }


    }

}
