package AppSigmaz;

import AppUI.App.SigmazCompilador;
import AppUI.Mottum.Windows;
import OA.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class AppSigmaz {

    public static void main(String[] args) {


        //  Gerador G = new Gerador();
        // G.gerarPrint("res/libs/terminal.sigmaz");

        AUTO();


        ArrayList<String> mArquivos = Carregadores.Carregar_Arquivos();
        ArrayList<String> mBibliotecas = Carregadores.Carregar_Bibliotecas();
        ArrayList<String> mMakes = Carregadores.Carregar_Makes();

        String mCompilado = "res/build/Sigmaz.sigmad";

        String mBiblioteca_Fonte = "res/libs/lib.sigmaz";
        String mBiblioteca_Sigmad = "res/build/lib.sigmad";


        String mUML = "res/uml/uml.txt";
        String mIntellisense = "res/intellisenses/";
        String mSyntax_HighLights = "res/highlights/";

        String mInternos = "res/internos/";
        String mLocal = "res/";


        int ARQUIVO = 1;

        switch (Fases.MAKE_EXECUTAR) {

            case DEPENDENCIAS -> AppUtils.DEPENDENCIA(ARQUIVO, mArquivos);
            case ESTRUTURADOR -> AppUtils.ESTRUTURAL(ARQUIVO, mArquivos, mCompilado);
            case INTERNO -> AppUtils.INTERNO(ARQUIVO, mArquivos, mCompilado, mInternos);


            case COMPILAR_BIBLIOTECA -> AppUtils.COMPILAR_BIBLIOTECA(ARQUIVO, mArquivos, mCompilado);
            case COMPILAR_EXECUTAVEL -> AppUtils.COMPILAR_EXECUTAVEL(ARQUIVO, mArquivos, mCompilado);

            case EXECUTAR -> AppUtils.EXECUTAR(ARQUIVO, mArquivos, mCompilado);
            case EXECUTAR_SIMPLES -> AppUtils.EXECUTAR_SIMPLES(ARQUIVO, mArquivos, mCompilado);
            case EXECUTAR_FASES -> AppUtils.EXECUTAR_FASES(ARQUIVO, mArquivos, mCompilado);


            case TESTES -> AppUtils.TESTE_GERAL(mArquivos, mCompilado, mLocal);

            case IDENTAR -> AppUtils.IDENTAR(ARQUIVO, mArquivos);
            case SYNTAX_HIGHLIGHT -> AppUtils.SYNTAX_HIGHLIGHT(ARQUIVO, mArquivos,mSyntax_HighLights);

            case IDENTAR_TUDO -> AppUtils.IDENTAR_LOTE("ARQUIVOS", mArquivos);
            case IDENTAR_BIBLIOTECAS -> AppUtils.IDENTAR_LOTE("BIBLIOTECAS", mBibliotecas);

            case MONTAR_BIBLIOTECAS -> AppUtils.MONTAR_BIBLIOTECA(mBiblioteca_Fonte, mBiblioteca_Sigmad);

            case INTELLISENSE -> AppUtils.INTELISENSE(ARQUIVO, mArquivos, mCompilado, mIntellisense);
            case INTELLISENSE_BIBLIOTECA -> AppUtils.INTELISENSE_BIBLIOTECA(mBiblioteca_Fonte, mBiblioteca_Sigmad, mIntellisense);

            case LEXER -> AppUtils.LEXER(ARQUIVO, mArquivos);
            case TODO -> AppUtils.TODO(ARQUIVO, mArquivos);
            case COMENTARIOS -> AppUtils.COMENTARIOS(ARQUIVO, mArquivos);

            case MAKE_LEXER -> AppMake.MAKE_LEXER(ARQUIVO, mMakes);
            case MAKE_EXECUTAR -> AppMake.MAKE_EXECUTAR(ARQUIVO, mMakes);

            case APP ->APP();

            default -> System.out.println("\t - Fases : Desconhecida !");
        }


    }

    public static void APP() {

        Windows mWindows = new Windows("Sigmaz - Compilador", 720, 600);

        //  mWindows.setIconImage(Imaginador.CarregarStream(Local.Carregar("editor.png")));

        SigmazCompilador eSigmazCompilador = new SigmazCompilador(mWindows);

        mWindows.CriarCenarioAplicavel(eSigmazCompilador);

        Thread mThread = new Thread(mWindows);
        mThread.start();

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
