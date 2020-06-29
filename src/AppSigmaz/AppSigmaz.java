package AppSigmaz;

import java.util.ArrayList;

public class AppSigmaz {

    public static void main(String[] args) {

        ArrayList<String> mArquivos = Carregadores.Carregar_Arquivos();
        ArrayList<String> mBibliotecas = Carregadores.Carregar_Bibliotecas();

        String mBiblioteca_Fonte = "res/libs/lib.sigmaz";
        String mBiblioteca_Sigmad = "res/lib.sigmad";

        Fases mFase = Fases.EXECUTAR;

        int ARQUIVO = 49;

        switch(mFase){
            case EXECUTAR: AppUtils.EXECUTAR(ARQUIVO, mArquivos); break;
            case DEPENDENCIAS: AppUtils.DEPENDENCIA(ARQUIVO, mArquivos);break;
            case IDENTAR: AppUtils.IDENTAR(ARQUIVO, mArquivos);break;
            case ESTRUTURADOR:  AppUtils.ESTRUTURAL(ARQUIVO, mArquivos);break;
            case TESTES: AppUtils.TESTE_GERAL(mArquivos);break;
            case IDENTAR_TUDO: AppUtils.IDENTAR_LOTE("ARQUIVOS",mArquivos);break;
            case IDENTAR_BIBLIOTECAS:  AppUtils.IDENTAR_LOTE("BIBLIOTECAS",mBibliotecas);break;
            case MONTAR_BIBLIOTECAS: AppUtils.MONTAR_BIBLIOTECA(mBiblioteca_Fonte,mBiblioteca_Sigmad);break;
            default:  System.out.println("\t - Fases : Desconhecida !");break;
        }


    }

}
