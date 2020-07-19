package AppSigmaz;

import OA.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class AppSigmaz {

    public static void main(String[] args) {


        //  Gerador G = new Gerador();
        //  G.gerarPrint();


        AUTO();


        ArrayList<String> mArquivos = Carregadores.Carregar_Arquivos();
        ArrayList<String> mBibliotecas = Carregadores.Carregar_Bibliotecas();

        String mCompilado = "res/Sigmaz.sigmad";
        String mBiblioteca_Fonte = "res/libs/lib.sigmaz";
        String mBiblioteca_Sigmad = "res/lib.sigmad";

        String mUML = "res/uml/uml.txt";
        String mIntellisense = "res/intellisenses/";
        String mInternos = "res/internos/";

        int ARQUIVO = 69;

        switch (Fases.TESTES) {

            case EXECUTAR -> AppUtils.EXECUTAR(ARQUIVO, mArquivos, mCompilado);
            case DEPENDENCIAS -> AppUtils.DEPENDENCIA(ARQUIVO, mArquivos);
            case ESTRUTURADOR -> AppUtils.ESTRUTURAL(ARQUIVO, mArquivos, mCompilado);
            case INTERNO -> AppUtils.INTERNO(ARQUIVO, mArquivos, mCompilado, mInternos);

            case TESTES -> AppUtils.TESTE_GERAL(mArquivos, mCompilado);

            case IDENTAR -> AppUtils.IDENTAR(ARQUIVO, mArquivos);
            case IDENTAR_TUDO -> AppUtils.IDENTAR_LOTE("ARQUIVOS", mArquivos);
            case IDENTAR_BIBLIOTECAS -> AppUtils.IDENTAR_LOTE("BIBLIOTECAS", mBibliotecas);

            case MONTAR_BIBLIOTECAS -> AppUtils.MONTAR_BIBLIOTECA(mBiblioteca_Fonte, mBiblioteca_Sigmad);

            case INTELLISENSE -> AppUtils.INTELISENSE(ARQUIVO, mArquivos, mCompilado, mIntellisense);
            case INTELLISENSE_BIBLIOTECA -> AppUtils.INTELISENSE_BIBLIOTECA(mBiblioteca_Fonte, mBiblioteca_Sigmad, mIntellisense);

            case LEXER -> AppUtils.LEXER(ARQUIVO, mArquivos);
            case TODO -> AppUtils.TODO(ARQUIVO, mArquivos);
            case COMENTARIOS -> AppUtils.COMENTARIOS(ARQUIVO, mArquivos);

            default -> System.out.println("\t - Fases : Desconhecida !");
        }



    }

    public static void AUTO() {

        OAVersion OA = new OAVersion("Sigmaz.oa");
        OA.init();


        OATodo mTodo = new OATodo("res/oa/Todo.oa");
        mTodo.sincronizar("Todo.txt");

        mTodo.marcarSem("i");
        mTodo.removerCom("r");

        ArrayList<Anotacao> eInfos = new ArrayList<Anotacao>();

        eInfos.add(new Anotacao("i", "Ideias", new Color(39, 174, 96)));
        eInfos.add(new Anotacao("f", "Fazendo", new Color(230, 126, 34)));
        eInfos.add(new Anotacao("c", "Cancelado", new Color(192, 57, 43)));
        eInfos.add(new Anotacao("t", "Terminado", new Color(41, 128, 185)));

        mTodo.exportar("todo.png", eInfos);


        mTodo.exportarDiario("C:\\Users\\Luand\\OneDrive\\Imagens\\todos\\sigmaz_", ".png", eInfos);


        String IMG_ROAD = "https://raw.githubusercontent.com/luandkg/Sigmaz/master/res/imagens/road.png";
        String IMG_CHANGE = "https://raw.githubusercontent.com/luandkg/Sigmaz/master/res/imagens/change.png";


        OARoad mOARoadmap = new OARoad("res/oa/Roadmap.oa");
        mOARoadmap.receber("Roadmap.txt");
        mOARoadmap.exportarImagem("roadmap.png", new Color(52, 73, 94));
        mOARoadmap.exportarMarkDown("ROADMAP.md", "Linguagem de Programação Estruturada - Implementações", IMG_ROAD, "RoadMap - Sigmaz");


        OARoad mChangeList = new OARoad("res/oa/ChangeList.oa");
        mChangeList.receber("ChangeList.txt");
        mChangeList.exportarImagem("changelist.png", new Color(230, 126, 34));
        mChangeList.exportarMarkDown("CHANGELIST.md", "Linguagem de Programação Estruturada - Alterações", IMG_CHANGE, "ChangeList - Sigmaz");


        OA.exportarReleases("VersionMap.png", "res/oa/Roadmap.oa");

        //mTrilha.exportarBranches("res/imagens/changebranches.png", "Sigmaz.oa", "res/oa/ChangeList.oa",new Color(243, 156, 18));
        //mTrilha.exportarBranches("res/imagens/roadbranches.png", "Sigmaz.oa", "res/oa/Roadmap.oa",new Color(41, 128, 185));


        mOARoadmap.exportarImagem("res/imagens/roadmap.png", new Color(52, 73, 94));
        mChangeList.exportarImagem("res/imagens/changelist.png", new Color(230, 126, 34));




    }

    public void ale(){

        for (int i = 1; i <= 4; i++) {

            Random rd = new Random();
            int v = rd.nextInt( 100)+10;

            int sh = rd.nextInt(10)+7;
            int sm = rd.nextInt(48) + 1;
            int ss = rd.nextInt(48) + 1;

            int fh = sh + rd.nextInt(5)+1;
            int fm = rd.nextInt(48) + 1;
            int fs = rd.nextInt(48) + 1;

            int uh = fh + rd.nextInt(2)+1;
            int um = rd.nextInt(48) + 1;
            int us = rd.nextInt(48) + 1;

            System.out.println(" PACOTE Branch");
            System.out.println("   {");
            System.out.println("      ID Date = \"" + i + "/7/2020\"");
            System.out.println("      ID Start = \"" + i+ "/7/2020 " + sh + ":" + sm + ":" + ss +"\"");
            System.out.println("    ID Update = \"" + i + "/7/2020 " + fh + ":" + fm + ":" + fs +"\"");
            System.out.println("    ID End = \"" + i + "/7/2020 " + uh + ":" + um + ":" + us +"\"");
            System.out.println("     ID Status = \"RED\"");
            System.out.println("     ID Count = \"" + v + "\"");
            System.out.println(" }");


        }



    }
}
