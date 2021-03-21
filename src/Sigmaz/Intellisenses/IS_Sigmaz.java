package Sigmaz.Intellisenses;

import Sigmaz.S08_Utilitarios.Visualizador.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IS_Sigmaz {

    private Intellisense mIntellisense;
    private IntellisenseTheme mIntellisenseTheme;

    public IS_Sigmaz(Intellisense eIntellisense, IntellisenseTheme eIntellisenseTheme) {
        mIntellisense = eIntellisense;
        mIntellisenseTheme = eIntellisenseTheme;

    }

    public void sigmaz_raiz(String eLocal, String eTitulo, SigmazRaiz mSigmazRaiz, int eLargura) {


        int w = eLargura;
        int h = 100 + (mSigmazRaiz.getContagem() * 40);


        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(mIntellisenseTheme.getFundo());
        g.fillRect(0, 0, w, h);


        g.setColor(Color.RED);
        g.fillRect(0, 0, w, 100);


        g.setColor(Color.BLACK);
        mIntellisense.titulo(g, 0, 0, w, eTitulo);

        int mais = 110;


        boolean tem_definemockiz = mSigmazRaiz.tem_DefineMockiz();
        boolean tem_actionfunction = mSigmazRaiz.tem_ActionFunction();
        boolean tem_directoroperator = mSigmazRaiz.tem_DirectorOperator();
        boolean tem_autofunctor = mSigmazRaiz.tem_AutoFunctor();
        boolean tem_mark = mSigmazRaiz.tem_Marker();

        for (SigmazDefine Sub2 : mSigmazRaiz.getDefines()) {
            mais = mIntellisense.algum(g, mais, Sub2.getDefinicao(), mIntellisense.IMG_DEFINE_ALL);
        }


        for (SigmazMockiz Sub2 : mSigmazRaiz.getMockizes()) {
            mais = mIntellisense.algum(g, mais, Sub2.getDefinicao(), mIntellisense.IMG_MOCKIZ_ALL);
        }


        if (mIntellisense.deveSeparar(tem_definemockiz, tem_actionfunction)) {
            mais = mIntellisense.criarBox_vertical(g, mais);
        }


        for (SigmazAction Sub2 : mSigmazRaiz.getActions()) {
            mais = mIntellisense.algum(g, mais, Sub2.getDefinicao(), mIntellisense.IMG_ACTION_ALL);
        }


        for (SigmazFunction Sub2 : mSigmazRaiz.getFunctions()) {
            mais = mIntellisense.algum(g, mais, Sub2.getDefinicao(), mIntellisense.IMG_FUNCTION_ALL);
        }


        if (mIntellisense.deveSeparar(tem_actionfunction, tem_actionfunction)) {
            mais = mIntellisense.criarBox_vertical(g, mais);

        }


        for (SigmazAuto Sub2 : mSigmazRaiz.getAutos()) {
            mais = mIntellisense.algum(g, mais, Sub2.getDefinicao(), mIntellisense.IMG_ACTION_ALL);
        }


        for (SigmazFunctor Sub2 : mSigmazRaiz.getFunctores()) {
            mais = mIntellisense.algum(g, mais, Sub2.getDefinicao(), mIntellisense.IMG_FUNCTION_ALL);
        }


        if (mIntellisense.deveSeparar(tem_autofunctor, tem_directoroperator)) {
            mais = mIntellisense.criarBox_vertical(g, mais);
        }


        for (SigmazDirector Sub2 : mSigmazRaiz.getDirectors()) {
            mais = mIntellisense.leftString_Normal(g, 30, mais, eLargura, Sub2.getDefinicao(), mIntellisense.IMG_DIRECTOR);

        }

        for (SigmazOperator Sub2 : mSigmazRaiz.getOperators()) {
            mais = mIntellisense.leftString_Normal(g, 30, mais, eLargura, Sub2.getDefinicao(), mIntellisense.IMG_OPERATOR);
        }


        if (mIntellisense.deveSeparar(tem_directoroperator, tem_mark)) {
            mais = mIntellisense.criarBox_vertical(g, mais);
        }

        for (SigmazMark Sub2 : mSigmazRaiz.getMarks()) {
            mais = mIntellisense.leftString_Normal(g, 30, mais, eLargura, Sub2.getDefinicao(), mIntellisense.IMG_MARK);
        }


        try {
            ImageIO.write(img, "png", new File(eLocal + eTitulo + ".png"));
        } catch (IOException o) {

        }

    }

    public void sigmaz_raiz_horizontal(String eLocal, String eTitulo, SigmazRaiz mSigmazRaiz, int eLargura) {

        Color eBarraSigmaz = mIntellisenseTheme.getSigmaz();
        Color eTexto = mIntellisenseTheme.getTexto();
        Color eBox = mIntellisenseTheme.getBox();
        Color eFundo = mIntellisenseTheme.getFundo();


        int w = eLargura;
        int c = mSigmazRaiz.getContagem();

        int faixador = 50;


        int ci = c;
        int colunas = 1;
        while (ci >= faixador) {
            ci -= faixador;
            colunas += 1;
        }

        //System.out.println("Colunas : " + colunas);

        // int h = 100 + (c* 40);

        int h = 100 + (faixador * 40);

        int indexador = 0;
        int colunador = 0;

        int mais = 0;

        BufferedImage img = new BufferedImage(w * colunas, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(eFundo);
        g.fillRect(0, 0, w * colunas, h);


        g.setColor(eBarraSigmaz);
        g.fillRect(0, 0, w * colunas, 100);


        g.setColor(eTexto);
        mais = mIntellisense.titulo(g, 0, 0, w * colunas, eTitulo);


        mais = 110;


        boolean tem_definemockiz = mSigmazRaiz.tem_DefineMockiz();
        boolean tem_actionfunction = mSigmazRaiz.tem_ActionFunction();
        boolean tem_directoroperator = mSigmazRaiz.tem_DirectorOperator();
        boolean tem_autofunctor = mSigmazRaiz.tem_AutoFunctor();
        boolean tem_mark = mSigmazRaiz.tem_Marker();


        for (SigmazDefine Sub2 : mSigmazRaiz.getDefines()) {

            if (indexador >= faixador) {
                indexador = 0;
                colunador += 1;
                mais = 110;
            }

            mais = mIntellisense.algum(g, (colunador * eLargura) + 30, mais, Sub2.getDefinicao(), mIntellisense.IMG_DEFINE_ALL);

            indexador += 1;


        }


        for (SigmazMockiz Sub2 : mSigmazRaiz.getMockizes()) {

            if (indexador >= faixador) {
                indexador = 0;
                colunador += 1;
                mais = 110;
            }


            mais = mIntellisense.algum(g, (colunador * eLargura) + 30, mais, Sub2.getDefinicao(), mIntellisense.IMG_MOCKIZ_ALL);
            indexador += 1;


        }


        if (mIntellisense.deveSeparar(tem_definemockiz, tem_actionfunction)) {
            mais = mIntellisense.criarBox_horizontal(g, mais, colunador);
        }


        for (SigmazAction Sub2 : mSigmazRaiz.getActions()) {

            if (indexador >= faixador) {
                indexador = 0;
                colunador += 1;
                mais = 110;
            }

            g.setColor(eTexto);

            mais = mIntellisense.algum(g, (colunador * eLargura) + 30, mais, Sub2.getDefinicao(), mIntellisense.IMG_ACTION_ALL);
            indexador += 1;


        }


        for (SigmazFunction Sub2 : mSigmazRaiz.getFunctions()) {

            if (indexador >= faixador) {
                indexador = 0;
                colunador += 1;
                mais = 110;
            }
            g.setColor(eTexto);

            mais = mIntellisense.algum(g, (colunador * eLargura) + 30, mais, Sub2.getDefinicao(), mIntellisense.IMG_FUNCTION_ALL);
            indexador += 1;


        }


        if (mIntellisense.deveSeparar(tem_autofunctor, tem_autofunctor)) {
            mais = mIntellisense.criarBox_horizontal(g, mais, colunador);
        }


        for (SigmazAuto Sub2 : mSigmazRaiz.getAutos()) {

            if (indexador >= faixador) {
                indexador = 0;
                colunador += 1;
                mais = 110;
            }

            g.setColor(eTexto);
            mIntellisense.leftString_Normal(g, (colunador * eLargura) + 30, mais, eLargura, Sub2.getDefinicao(), mIntellisense.IMG_AUTO_ALL);

            mais += 30;
            indexador += 1;


        }

        for (SigmazFunctor Sub2 : mSigmazRaiz.getFunctores()) {

            if (indexador >= faixador) {
                indexador = 0;
                colunador += 1;
                mais = 110;
            }

            g.setColor(eTexto);
            mIntellisense.leftString_Normal(g, (colunador * eLargura) + 30, mais, eLargura, Sub2.getDefinicao(), mIntellisense.IMG_FUNCTOR_ALL);

            mais += 30;
            indexador += 1;


        }


        if (mIntellisense.deveSeparar(tem_autofunctor, tem_directoroperator)) {
            mais = mIntellisense.criarBox_horizontal(g, mais, colunador);
        }


        for (SigmazDirector Sub2 : mSigmazRaiz.getDirectors()) {


            if (indexador >= faixador) {
                indexador = 0;
                colunador += 1;
                mais = 110;
            }

            g.setColor(eTexto);
            mIntellisense.leftString_Normal(g, (colunador * eLargura) + 30, mais, eLargura, Sub2.getDefinicao(), mIntellisense.IMG_DIRECTOR);

            mais += 30;
            indexador += 1;


        }

        for (SigmazOperator Sub2 : mSigmazRaiz.getOperators()) {


            if (indexador >= faixador) {
                indexador = 0;
                colunador += 1;
                mais = 110;
            }

            g.setColor(eTexto);
            mIntellisense.leftString_Normal(g, (colunador * eLargura) + 30, mais, eLargura, Sub2.getDefinicao(), mIntellisense.IMG_OPERATOR);

            mais += 30;
            indexador += 1;


        }

        if (mIntellisense.deveSeparar(tem_directoroperator, tem_mark)) {
            mais = mIntellisense.criarBox_horizontal(g, mais, colunador);
        }


        for (SigmazMark Sub2 : mSigmazRaiz.getMarks()) {

            if (indexador >= faixador) {
                indexador = 0;
                colunador += 1;
                mais = 110;
            }

            g.setColor(eTexto);
            mIntellisense.leftString_Normal(g, (colunador * eLargura) + 30, mais, eLargura, Sub2.getDefinicao(), mIntellisense.IMG_MARK);

            mais += 30;
            indexador += 1;


        }


        try {
            ImageIO.write(img, "png", new File(eLocal + eTitulo + ".png"));
        } catch (IOException o) {

        }

    }


}
