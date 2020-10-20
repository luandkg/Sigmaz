package Sigmaz.S00_Utilitarios;

import java.util.ArrayList;

public class OrganizadorDeErros {

    private ArrayList<String> mErros;

    public OrganizadorDeErros() {

        mErros = new ArrayList<String>();

    }

    public ArrayList<String> getErros() {
        return mErros;
    }

    public void limpar() {
        mErros.clear();
    }

    public void getLexer(ArrayList<GrupoDeErro> mGrupos) {

        mErros.add("\n\t ERROS DE LEXER : ");

        for (GrupoDeErro eGE : mGrupos) {
            mErros.add("\t\t" + eGE.getArquivo());
            for (Erro eErro : eGE.getErros()) {
                mErros.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
            }
        }
    }

    public void getParser(ArrayList<GrupoDeErro> mGrupos) {

        mErros.add("\n\t ERROS DE PARSER : ");

        for (GrupoDeErro eGE : mGrupos) {
            mErros.add("\t\t" + eGE.getArquivo());
            for (Erro eErro : eGE.getErros()) {
                mErros.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
            }
        }


    }

    public void getPreProcessador(ArrayList<GrupoDeErro> mGrupos) {

        mErros.add("\n\t ERROS DE PRE PROCESSAMENTO : ");

        for (GrupoDeErro eGE : mGrupos) {
            mErros.add("\t\t" + eGE.getArquivo());
            for (Erro eErro : eGE.getErros()) {
                mErros.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
            }

        }


    }

    public void getPosProcessador(ArrayList<String> eErros) {

        mErros.add("\n\t ERROS DE POS PROCESSAMENTO : ");

        for (String eErro : eErros) {
            mErros.add("\t\t    ->> "  + eErro);
        }


    }

}
