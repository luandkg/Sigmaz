package AppSigmaz;

import OA.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import Sigmaz.Gerador;

public class AppSigmaz {

    public static void main(String[] args) {


       //  Gerador G = new Gerador();
        // G.gerarPrint("res/libs/terminal.sigmaz");

        AUTO();


        ArrayList<String> mArquivos = Carregadores.Carregar_Arquivos();
        ArrayList<String> mBibliotecas = Carregadores.Carregar_Bibliotecas();
        ArrayList<String> mMakes = Carregadores.Carregar_Makes();

        String mCompilado = "res/Sigmaz.sigmad";
        String mBiblioteca_Fonte = "res/libs/lib.sigmaz";
        String mBiblioteca_Sigmad = "res/lib.sigmad";

        String mUML = "res/uml/uml.txt";
        String mIntellisense = "res/intellisenses/";
        String mInternos = "res/internos/";
        String mLocal = "res/";

        boolean mostrarAST = true;

        int ARQUIVO = 1;

        switch (Fases.MAKE_EXECUTAR) {

            case DEPENDENCIAS -> AppUtils.DEPENDENCIA(ARQUIVO, mArquivos);
            case ESTRUTURADOR -> AppUtils.ESTRUTURAL(ARQUIVO, mArquivos, mCompilado,mostrarAST);
            case INTERNO -> AppUtils.INTERNO(ARQUIVO, mArquivos, mCompilado, mInternos);


            case COMPILAR_BIBLIOTECA -> AppUtils.COMPILAR_BIBLIOTECA(ARQUIVO, mArquivos, mCompilado,mostrarAST);
            case COMPILAR_EXECUTAVEL -> AppUtils.COMPILAR_EXECUTAVEL(ARQUIVO, mArquivos, mCompilado,mostrarAST);

            case EXECUTAR -> AppUtils.EXECUTAR(ARQUIVO, mArquivos, mCompilado,mostrarAST);


            case TESTES -> AppUtils.TESTE_GERAL(mArquivos, mCompilado, mLocal);

            case IDENTAR -> AppUtils.IDENTAR(ARQUIVO, mArquivos);
            case IDENTAR_TUDO -> AppUtils.IDENTAR_LOTE("ARQUIVOS", mArquivos);
            case IDENTAR_BIBLIOTECAS -> AppUtils.IDENTAR_LOTE("BIBLIOTECAS", mBibliotecas);

            case MONTAR_BIBLIOTECAS -> AppUtils.MONTAR_BIBLIOTECA(mBiblioteca_Fonte, mBiblioteca_Sigmad,mostrarAST);

            case INTELLISENSE -> AppUtils.INTELISENSE(ARQUIVO, mArquivos, mCompilado, mIntellisense);
            case INTELLISENSE_BIBLIOTECA -> AppUtils.INTELISENSE_BIBLIOTECA(mBiblioteca_Fonte, mBiblioteca_Sigmad, mIntellisense);

            case LEXER -> AppUtils.LEXER(ARQUIVO, mArquivos);
            case TODO -> AppUtils.TODO(ARQUIVO, mArquivos);
            case COMENTARIOS -> AppUtils.COMENTARIOS(ARQUIVO, mArquivos);

            case MAKE_LEXER -> AppMake.MAKE_LEXER(ARQUIVO, mMakes);
            case MAKE_EXECUTAR -> AppMake.MAKE_EXECUTAR(ARQUIVO, mMakes, mCompilado,mostrarAST);

            default -> System.out.println("\t - Fases : Desconhecida !");
        }


    }

    public static void AUTO() {

        OAVersion OA = new OAVersion("Sigmaz.oa");
        OA.init();


        OAOrganizador mOrganizador = new OAOrganizador("todo.todo");
        mOrganizador.renderizar("todo.png");

        mOrganizador.identar();

        String IMG_ROAD = "https://raw.githubusercontent.com/luandkg/Sigmaz/master/res/imagens/road.png";
        String IMG_CHANGE = "https://raw.githubusercontent.com/luandkg/Sigmaz/master/res/imagens/change.png";

        Color eCorFonte = new Color(52, 73, 94);

        OARoad mOARoadmap = new OARoad("res/oa/Roadmap.trilha");
        mOARoadmap.receber("Roadmap.txt");
        mOARoadmap.exportarImagemHV("roadmap.png", new Color(52, 73, 94), eCorFonte);
        mOARoadmap.exportarMarkDown("ROADMAP.md", "Linguagem de Programação Estruturada - Implementações", IMG_ROAD, "RoadMap - Sigmaz");


        OARoad mChangeList = new OARoad("res/oa/ChangeList.trilha");
        mChangeList.receber("ChangeList.txt");
        mChangeList.exportarImagemHV("changelist.png", new Color(230, 126, 34), eCorFonte);
        mChangeList.exportarMarkDown("CHANGELIST.md", "Linguagem de Programação Estruturada - Alterações", IMG_CHANGE, "ChangeList - Sigmaz");


        OA.exportarReleasesHV("VersionMap.png", "res/oa/Roadmap.trilha", eCorFonte);


        //   mOARoadmap.exportarImagemHV("res/imagens/roadmap.png", new Color(52, 73, 94),eCorFonte);
        //   mChangeList.exportarImagemHV("res/imagens/changelist.png", new Color(230, 126, 34),eCorFonte);

        // OA.exportarReleasesHV("res/imagens/VersionMap.png", "res/oa/Roadmap.trilha",eCorFonte);
        //  OA.exportarBranchesHV("res/imagens/VersionBranches.png", "res/oa/Roadmap.trilha",eCorFonte);


    }

    public void ale() {

        for (int i = 1; i <= 4; i++) {

            Random rd = new Random();
            int v = rd.nextInt(100) + 10;

            int sh = rd.nextInt(10) + 7;
            int sm = rd.nextInt(48) + 1;
            int ss = rd.nextInt(48) + 1;

            int fh = sh + rd.nextInt(5) + 1;
            int fm = rd.nextInt(48) + 1;
            int fs = rd.nextInt(48) + 1;

            int uh = fh + rd.nextInt(2) + 1;
            int um = rd.nextInt(48) + 1;
            int us = rd.nextInt(48) + 1;

            System.out.println(" PACOTE Branch");
            System.out.println("   {");
            System.out.println("      ID Date = \"" + i + "/7/2020\"");
            System.out.println("      ID Start = \"" + i + "/7/2020 " + sh + ":" + sm + ":" + ss + "\"");
            System.out.println("    ID Update = \"" + i + "/7/2020 " + fh + ":" + fm + ":" + fs + "\"");
            System.out.println("    ID End = \"" + i + "/7/2020 " + uh + ":" + um + ":" + us + "\"");
            System.out.println("     ID Status = \"RED\"");
            System.out.println("     ID Count = \"" + v + "\"");
            System.out.println(" }");


        }


    }
}
