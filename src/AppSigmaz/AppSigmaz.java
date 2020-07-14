package AppSigmaz;

import OA.OATodo;
import OA.OAVersion;
import OA.OARoadmap;
import Sigmaz.Gerador;

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

        int ARQUIVO = 49;

        switch (Fases.INTELLISENSE_BIBLIOTECA) {

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

        OARoadmap oar = new OARoadmap("res/oa/Roadmap.oa");
        oar.receber("Roadmap.txt");

        OATodo mTodo = new OATodo("res/oa/Todo.oa");
        mTodo.sincronizar("Todo.txt");

         mTodo.Listar();

        mTodo.ListarStatus("i", "Iniciar");
        mTodo.ListarStatus_Recente("f", "Fazendo");
        mTodo.ListarStatus_Modificado("c", "Concluido");

        mTodo.ListarTag("oa");

        mTodo.ListarComTarefas();

    }
}
