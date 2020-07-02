package AppSigmaz;

import java.util.ArrayList;

public class AppSigmaz {

    public static void main(String[] args) {

        ArrayList<String> mArquivos = Carregadores.Carregar_Arquivos();
        ArrayList<String> mBibliotecas = Carregadores.Carregar_Bibliotecas();

        String mCompilado = "res/Sigmaz.sigmad";
        String mBiblioteca_Fonte = "res/libs/lib.sigmaz";
        String mBiblioteca_Sigmad = "res/lib.sigmad";

        Fases mFase = Fases.TESTES;

        int ARQUIVO = 42;

        switch (mFase) {
            case EXECUTAR -> AppUtils.EXECUTAR(ARQUIVO, mArquivos,mCompilado);
            case DEPENDENCIAS -> AppUtils.DEPENDENCIA(ARQUIVO, mArquivos);
            case IDENTAR -> AppUtils.IDENTAR(ARQUIVO, mArquivos);
            case ESTRUTURADOR -> AppUtils.ESTRUTURAL(ARQUIVO, mArquivos);
            case TESTES -> AppUtils.TESTE_GERAL(mArquivos,mCompilado);
            case IDENTAR_TUDO -> AppUtils.IDENTAR_LOTE("ARQUIVOS", mArquivos);
            case IDENTAR_BIBLIOTECAS -> AppUtils.IDENTAR_LOTE("BIBLIOTECAS", mBibliotecas);
            case MONTAR_BIBLIOTECAS -> AppUtils.MONTAR_BIBLIOTECA(mBiblioteca_Fonte, mBiblioteca_Sigmad);
            default -> System.out.println("\t - Fases : Desconhecida !");
        }


    }

}
