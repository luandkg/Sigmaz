package AppSigmaz;

import Mottum.Windows;
import OA.Apps.OAOrganizador;
import OA.Apps.OARoad;
import OA.Apps.OAVersion;
import Sigmaz.S07_Apps.AppMake;
import Sigmaz.S07_Apps.AppSigmazUtils;
import Sigmaz.S07_Apps.SigmazCompilador;

import java.awt.*;
import java.util.ArrayList;

public class AppSigmaz {



    public static void main(String[] args) {


        //Gerador G = new Gerador();
        // G.gerarPrint("res/libs/terminal.sigmaz");

        AUTO();


        ArrayList<String> mArquivos = Carregadores.Carregar_Arquivos();
        ArrayList<String> mBibliotecas = Carregadores.Carregar_Bibliotecas();
        ArrayList<String> mMakes = Carregadores.Carregar_Makes();

        String mCompilado = "res/build/Sigmaz.sigmad";
        String mLocalLibs = "res/build/";

        String mBiblioteca_Fonte = "res/libs/lib.sigmaz";
        String mBiblioteca_Sigmad = "res/build/lib.sigmad";


        String mUML = "res/uml/uml.txt";
        String mIntellisense = "res/intellisenses/";
        String mSyntax_HighLights = "res/highlights/";
        String mLOCAL_PLANOS = "res/plan/";

        String mInternos = "res/internos/";
        String mLocal = "res/";

        String mLocalGerador = "res/gerador/";

        String mLocalImagens = "res/imagens/";

        boolean mDebugar = true;

        int ARQUIVO = 23;


        switch (SigmazApp.TESTES) {

            case MONTAR_PLANO_COMPILACAO -> AppSigmazUtils.PLANO_COMPILACAO(ARQUIVO, mArquivos, mLOCAL_PLANOS, mLocalLibs);

            case DEPENDENCIAS -> AppSigmazUtils.DEPENDENCIA(ARQUIVO, mArquivos,mLocalLibs);
            case ESTRUTURADOR -> AppSigmazUtils.ESTRUTURAL(ARQUIVO, mArquivos, mCompilado, mLocalLibs);
            case INTERNO -> AppSigmazUtils.INTERNO(ARQUIVO, mArquivos, mCompilado, mLocalLibs, mInternos);


            case COMPILAR_BIBLIOTECA -> AppSigmazUtils.COMPILAR_BIBLIOTECA(ARQUIVO, mArquivos, mCompilado, mLocalLibs,mDebugar);
            case COMPILAR_EXECUTAVEL -> AppSigmazUtils.COMPILAR_EXECUTAVEL(ARQUIVO, mArquivos, mCompilado, mLocalLibs,mDebugar);

            case COMPILAR_E_EXECUTAR_SIMPLES -> AppSigmazUtils.COMPILAR_SIMPLES(ARQUIVO, mArquivos, mCompilado, mLocalLibs,mDebugar);
            case COMPILAR_E_EXECUTAR_DETALHADO -> AppSigmazUtils.COMPILAR_DETALHADO(ARQUIVO, mArquivos, mCompilado, mLocalLibs,mDebugar);

            case EXECUTAR -> AppSigmazUtils.EXECUTAR(mCompilado, mLocalLibs,false);
            case EXECUTAR_DEBUG -> AppSigmazUtils.EXECUTAR(mCompilado, mLocalLibs,true);

            case TESTES -> AppSigmazUtils.TESTE_GERAL(mArquivos, mCompilado, mLocal, mLocalLibs);

            case SYNTAX_HIGHLIGHT -> AppSigmazUtils.SYNTAX_HIGHLIGHT(ARQUIVO, mArquivos, mSyntax_HighLights);

            case IDENTAR -> AppSigmazUtils.IDENTAR(ARQUIVO, mArquivos);
            case IDENTAR_TUDO -> AppSigmazUtils.IDENTAR_LOTE("ARQUIVOS", mArquivos);
            case IDENTAR_BIBLIOTECAS -> AppSigmazUtils.IDENTAR_LOTE("BIBLIOTECAS", mBibliotecas);

            case MONTAR_BIBLIOTECAS -> AppSigmazUtils.MONTAR_BIBLIOTECA(mBiblioteca_Fonte, mBiblioteca_Sigmad, mLocalLibs,mDebugar);

            case INTELLISENSE -> AppSigmazUtils.INTELISENSE(ARQUIVO, mArquivos, mCompilado, mLocalLibs, mIntellisense);
            case INTELLISENSE_BIBLIOTECA -> AppSigmazUtils.INTELISENSE_BIBLIOTECA(mBiblioteca_Fonte, mBiblioteca_Sigmad, mLocalLibs, mIntellisense);

            case LEXER -> AppSigmazUtils.LEXER(ARQUIVO, mArquivos);
            case TODO -> AppSigmazUtils.TODO(ARQUIVO, mArquivos,mLocalLibs);
            case COMENTARIOS -> AppSigmazUtils.COMENTARIOS(ARQUIVO, mArquivos,mLocalLibs);

            case MAKE_LEXER -> AppMake.MAKE_LEXER(ARQUIVO, mMakes);
            case MAKE_EXECUTAR -> AppMake.MAKE_EXECUTAR(ARQUIVO, mMakes);

            case GERADOR_DETALHADO -> AppSigmazUtils.GERADOR_DETALHADO(mLocalGerador, mCompilado, mLocalLibs,mDebugar);
            case GERADOR_INTELLISENSE -> AppSigmazUtils.GERADOR_INTELISENSE(mLocalGerador, mCompilado, mLocalLibs, mIntellisense);

            case APP -> APP();

            case DUMP -> AppSigmazUtils.DUMP(mCompilado);

            case RECURSOS -> AppSigmazUtils.RECURSOS(mLocalImagens);

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
        mOARoadmap.exportarSetoresMarkDown("ROADMAP_V2.md", 12,"Linguagem de Programação Estruturada - Implementações", IMG_ROAD, "RoadMap - Sigmaz");

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

}
