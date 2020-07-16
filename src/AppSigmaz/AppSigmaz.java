package AppSigmaz;

import OA.Anotacao;
import OA.OATodo;
import OA.OAVersion;
import OA.OARoadmap;
import Sigmaz.Gerador;
import Sigmaz.Utils.Tempo;

import java.awt.*;
import java.util.ArrayList;

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

        int ARQUIVO = 1;

        switch (Fases.EXECUTAR) {

            case EXECUTAR -> AppUtils.EXECUTAR(ARQUIVO, mArquivos, mCompilado);
            case DEPENDENCIAS -> AppUtils.DEPENDENCIA(ARQUIVO, mArquivos);
            case ESTRUTURADOR -> AppUtils.ESTRUTURAL(ARQUIVO, mArquivos, mCompilado);

            case TESTES -> AppUtils.TESTE_GERAL(mArquivos, mCompilado);

            case IDENTAR -> AppUtils.IDENTAR(ARQUIVO, mArquivos);
            case IDENTAR_TUDO -> AppUtils.IDENTAR_LOTE("ARQUIVOS", mArquivos);
            case IDENTAR_BIBLIOTECAS -> AppUtils.IDENTAR_LOTE("BIBLIOTECAS", mBibliotecas);

            case MONTAR_BIBLIOTECAS -> AppUtils.MONTAR_BIBLIOTECA(mBiblioteca_Fonte, mBiblioteca_Sigmad);

            case INTELLISENSE -> AppUtils.INTELISENSE(ARQUIVO, mArquivos, mCompilado, mIntellisense);
            case INTELLISENSE_BIBLIOTECA -> AppUtils.INTELISENSE_BIBLIOTECA(mBiblioteca_Fonte, mBiblioteca_Sigmad, mIntellisense);

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


        OARoadmap mOARoadmap = new OARoadmap("res/oa/Roadmap.oa");
        mOARoadmap.receber("Roadmap.txt");
        mOARoadmap.exportarImagem("roadmap.png",new Color(52, 73, 94));
        mOARoadmap.exportarMarkDown("ROADMAP.md", "Linguagem de Programação Estruturada - Implementações", IMG_ROAD, "RoadMap - Sigmaz");

        OARoadmap mChangeList = new OARoadmap("res/oa/ChangeList.oa");
        mChangeList.receber("ChangeList.txt");
        mChangeList.exportarImagem("changelist.png",new Color(230, 126, 34));
        mChangeList.exportarMarkDown("CHANGELIST.md", "Linguagem de Programação Estruturada - Alterações", IMG_CHANGE, "ChangeList - Sigmaz");


    }
}
