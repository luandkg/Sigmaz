package OA;

import Sigmaz.Utils.Texto;

import java.io.File;
import java.util.ArrayList;

public class OAOrganizador {

    private String mArquivo;

    private String alfabeto = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789çÇáéíóúóàèìòùâêîôûãõÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕ";
    private String mDocumento;

    private int mIndex;
    private int mFim;
    private ArrayList<Lexema> mLexemas;
    private ArrayList<String> mErros;

    public OAOrganizador(String eArquivo) {
        mArquivo = eArquivo;
        mDocumento = "";
        mIndex = 0;
        mFim = 0;
        mLexemas = new ArrayList<Lexema>();
        mErros = new ArrayList<String>();

    }

    public enum TipoLexema {
        ID, PARENTESESES_ABRE, PARESENTESES_FECHA, CHAVES_ABRE, CHAVES_FECHA, DOIS_PONTOS, TEXTO, PONTO_VIRGULA, DESCONHECIDO;
    }

    private class Lexema {

        private TipoLexema mTipoLexema;
        private String mConteudo;

        public Lexema(TipoLexema eTipoLexema, String eConteudo) {
            mTipoLexema = eTipoLexema;
            mConteudo = eConteudo;
        }

        public TipoLexema getTipoLexema() {
            return mTipoLexema;
        }

        public void setTipoLexema(TipoLexema eTipoLexema) {
            mTipoLexema = eTipoLexema;
        }


        public String getConteudo() {
            return mConteudo;
        }

        public void setConteudo(String eConteudo) {
            mConteudo = eConteudo;
        }

        public boolean mesmoConteudo(String eConteudo) {
            return mConteudo.contentEquals(eConteudo);
        }
    }


    public void renderizar(String eArquivoQuadro) {

        ArrayList<AOAnotacao> mAOAnotacaos = new ArrayList<AOAnotacao>();
        ArrayList<AOAnotacao> mConfig = new ArrayList<AOAnotacao>();

        mErros = new ArrayList<String>();

        parser(mAOAnotacaos, mConfig, mErros);

        mIndex = 0;
        mFim = mLexemas.size();

        while (mIndex < mFim) {
            Lexema eLexema = mLexemas.get(mIndex);


            if (eLexema.getTipoLexema() == TipoLexema.ID && eLexema.mesmoConteudo("note")) {

                AOAnotacao eAOAnotacao = new AOAnotacao("");
                mAOAnotacaos.add(eAOAnotacao);

                parser_note(eAOAnotacao);

            } else if (eLexema.getTipoLexema() == TipoLexema.ID && eLexema.mesmoConteudo("config")) {

                AOAnotacao eAOAnotacao = new AOAnotacao("");
                mConfig.add(eAOAnotacao);

                parser_note(eAOAnotacao);
            }


            mIndex += 1;
        }

        if (mErros.size() == 0) {

            OAQuadrum Q = new OAQuadrum();
            Q.exportar(eArquivoQuadro, mAOAnotacaos, mConfig);

        } else {

            for (String erro : mErros) {
                System.out.println("ERRO :: " + erro);
            }

        }


    }

    public void identar() {

        ArrayList<AOAnotacao> mAOAnotacaos = new ArrayList<AOAnotacao>();
        ArrayList<AOAnotacao> mConfig = new ArrayList<AOAnotacao>();

        mErros = new ArrayList<String>();

        parser(mAOAnotacaos, mConfig, mErros);

        mIndex = 0;
        mFim = mLexemas.size();

        while (mIndex < mFim) {
            Lexema eLexema = mLexemas.get(mIndex);


            if (eLexema.getTipoLexema() == TipoLexema.ID && eLexema.mesmoConteudo("note")) {

                AOAnotacao eAOAnotacao = new AOAnotacao("");
                mAOAnotacaos.add(eAOAnotacao);

                parser_note(eAOAnotacao);

            } else if (eLexema.getTipoLexema() == TipoLexema.ID && eLexema.mesmoConteudo("config")) {

                AOAnotacao eAOAnotacao = new AOAnotacao("");
                mConfig.add(eAOAnotacao);

                parser_note(eAOAnotacao);
            }


            mIndex += 1;
        }

        if (mErros.size() == 0) {

            String eDocumento = "";

            mIndex = 0;
            mFim = mLexemas.size();

            int mTab = 0;

            while (mIndex < mFim) {
                Lexema eLexema = mLexemas.get(mIndex);

                if (mTab < 0) {
                    mTab = 0;
                }
                if (eLexema.getTipoLexema() == TipoLexema.TEXTO) {

                    if (eLexema.getConteudo().contains("\"")) {
                        eDocumento += "'" + eLexema.getConteudo() + "' ";
                    } else {
                        eDocumento += "\"" + eLexema.getConteudo() + "\" ";
                    }


                } else if (eLexema.getTipoLexema() == TipoLexema.CHAVES_ABRE) {


                    boolean mudar = true;

                    int pIndex = mIndex + 1;
                    if (pIndex < mFim) {
                        if (mLexemas.get(pIndex).getTipoLexema() == TipoLexema.CHAVES_FECHA) {
                            mTab -= 1;
                            mudar = false;
                        }
                    }

                    if (mudar) {
                        eDocumento += eLexema.getConteudo() + "\n";
                        mTab += 1;
                        if (mTab > 0) {
                            for (int i = 0; i <= mTab; i++) {
                                eDocumento += "\t";
                            }
                        }
                    } else {
                        eDocumento += eLexema.getConteudo() + " ";
                    }


                } else if (eLexema.getTipoLexema() == TipoLexema.CHAVES_FECHA) {

                    //   eDocumento +=  "\n" ;


                    mTab -= 1;
                    if (mTab > 0) {
                        for (int i = 0; i <= mTab; i++) {
                            eDocumento += "\t";
                        }
                    }
                    eDocumento += eLexema.getConteudo() + "\n";

                    boolean espacar = true;

                    int pIndex = mIndex + 1;
                    if (pIndex < mFim) {
                        if (mLexemas.get(pIndex).getTipoLexema() == TipoLexema.CHAVES_FECHA) {
                            mTab -= 1;
                        }
                    }


                    if (mTab > 0) {
                        for (int i = 0; i <= mTab; i++) {
                            eDocumento += "\t";
                        }
                    }

                } else if (eLexema.getTipoLexema() == TipoLexema.PONTO_VIRGULA) {
                    eDocumento += eLexema.getConteudo() + "\n";

                    boolean espacar = true;

                    int pIndex = mIndex + 1;
                    if (pIndex < mFim) {
                        if (mLexemas.get(pIndex).getTipoLexema() == TipoLexema.CHAVES_FECHA) {
                            espacar = false;
                        }
                    }
                    if (espacar) {
                        if (mTab > 0) {
                            for (int i = 0; i <= mTab; i++) {
                                eDocumento += "\t";
                            }
                        }
                    }


                } else {
                    eDocumento += eLexema.getConteudo() + " ";

                }
                mIndex += 1;
            }

            //System.out.println(eDocumento);

            Texto.Escrever(mArquivo, eDocumento);
        }

    }


    public void imprimir(String eTitulo, AOAnotacao eAOAnotacao) {

        System.out.println(eTitulo + " : " + eAOAnotacao.getNome());
        System.out.println("\t - Marcador :  " + eAOAnotacao.getMarcador());

        System.out.println("\t - Tags :  ");

        for (String eTag : eAOAnotacao.getTags()) {
            System.out.println("\t\t - " + eTag);
        }

        System.out.println("\t - Tarefas :  ");

        for (AOTarefa eAOTarefa : eAOAnotacao.getTarefas()) {
            System.out.println("\t\t - " + eAOTarefa.getNome() + " : " + eAOTarefa.getMarcador());

            System.out.println("\t\t\t - Tags :  ");

            for (String eTag : eAOTarefa.getTags()) {
                System.out.println("\t\t\t\t - " + eTag);
            }


        }

        System.out.println("\t - Paginas :  ");

        for (AOPagina eAOPagina : eAOAnotacao.getPaginas()) {
            System.out.println("\t\t - " + eAOPagina.getNome());


            System.out.println("\t\t\t - Tarefas :  ");

            for (AOTarefa eAOTarefa : eAOPagina.getTarefas()) {
                System.out.println("\t\t\t\t - " + eAOTarefa.getNome() + " : " + eAOTarefa.getMarcador());

                System.out.println("\t\t\t\t\t\t - Tags :  ");

                for (String eTag : eAOTarefa.getTags()) {
                    System.out.println("\t\t\t\t\t\t\t - " + eTag);
                }


            }

        }


    }


    public void parser(ArrayList<AOAnotacao> mAOAnotacaos, ArrayList<AOAnotacao> mConfig, ArrayList<String> mErros) {


        mLexemas = new ArrayList<Lexema>();


        File arq = new File(mArquivo);

        if (arq.exists()) {

            mDocumento = Texto.Ler(mArquivo);


            mIndex = 0;
            mFim = mDocumento.length();

            while (mIndex < mFim) {

                String l = String.valueOf(mDocumento.charAt(mIndex));

                if (l.contentEquals(" ")) {

                } else if (l.contentEquals("\t")) {

                } else if (l.contentEquals("\n")) {

                } else if (l.contentEquals(":")) {

                    mLexemas.add(new Lexema(TipoLexema.DOIS_PONTOS, ":"));
                } else if (l.contentEquals("(")) {

                    mLexemas.add(new Lexema(TipoLexema.PARENTESESES_ABRE, "("));
                } else if (l.contentEquals(")")) {

                    mLexemas.add(new Lexema(TipoLexema.PARESENTESES_FECHA, ")"));
                } else if (l.contentEquals("{")) {

                    mLexemas.add(new Lexema(TipoLexema.CHAVES_ABRE, "{"));
                } else if (l.contentEquals("}")) {

                    mLexemas.add(new Lexema(TipoLexema.CHAVES_FECHA, "}"));

                } else if (l.contentEquals(";")) {

                    mLexemas.add(new Lexema(TipoLexema.PONTO_VIRGULA, ";"));

                } else if (alfabeto.contains(l)) {

                    String ret = "";

                    while (mIndex < mFim) {

                        String l2 = String.valueOf(mDocumento.charAt(mIndex));

                        if (alfabeto.contains(l2)) {
                            ret += l2;
                        } else {
                            mIndex -= 1;
                            break;
                        }

                        mIndex += 1;
                    }

                    mLexemas.add(new Lexema(TipoLexema.ID, ret));

                } else if (l.contentEquals("\"")) {

                    String ret = "";
                    mIndex += 1;
                    boolean fechado = false;

                    while (mIndex < mFim) {

                        String l2 = String.valueOf(mDocumento.charAt(mIndex));

                        if (l2.contentEquals("\"")) {
                            fechado = true;
                            break;
                        } else {
                            ret += l2;
                        }

                        mIndex += 1;
                    }
                    if (!fechado) {
                        mErros.add("Texto nao fechado !");
                    }

                    mLexemas.add(new Lexema(TipoLexema.TEXTO, ret));
                } else if (l.contentEquals("'")) {

                    String ret = "";
                    mIndex += 1;
                    boolean fechado = false;

                    while (mIndex < mFim) {

                        String l2 = String.valueOf(mDocumento.charAt(mIndex));

                        if (l2.contentEquals("'")) {
                            fechado = true;
                            break;
                        } else {
                            ret += l2;
                        }

                        mIndex += 1;
                    }

                    if (!fechado) {
                        mErros.add("Texto nao fechado !");
                    }

                    mLexemas.add(new Lexema(TipoLexema.TEXTO, ret));
                }

                mIndex += 1;
            }

        }

        //  for (Lexema eLexema : mLexemas) {
        //      System.out.println(eLexema.getTipoLexema().toString() + " -->> " + eLexema.getConteudo());
        //   }
    }

    public void parser_note(AOAnotacao eAOAnotacao) {


        mIndex += 1;
        if (mIndex < mFim) {

            Lexema sLexema = mLexemas.get(mIndex);

            if (sLexema.getTipoLexema() == TipoLexema.TEXTO) {
                eAOAnotacao.setNome(sLexema.getConteudo());

            } else {
                mErros.add("Era esperado o texto da anotacao !");
            }

        }

        mIndex += 1;
        if (mIndex < mFim) {

            Lexema sLexema = mLexemas.get(mIndex);

            if (sLexema.getTipoLexema() == TipoLexema.DOIS_PONTOS) {

            } else {
                mErros.add("Era esperado dois pontos !");
            }

        }

        mIndex += 1;
        if (mIndex < mFim) {

            Lexema sLexema = mLexemas.get(mIndex);

            if (sLexema.getTipoLexema() == TipoLexema.ID) {
                eAOAnotacao.setMarcador(sLexema.getConteudo());

            } else {
                mErros.add("Era esperado o tipo da anotacao !");
            }


        }

        parser_tags(eAOAnotacao.getTags());

        parser_tarefas(eAOAnotacao);


    }

    public void parser_tags(ArrayList<String> mTags) {


        mIndex += 1;
        if (mIndex < mFim) {

            Lexema sLexema = mLexemas.get(mIndex);

            if (sLexema.getTipoLexema() == TipoLexema.PARENTESESES_ABRE) {


            } else {
                mErros.add("Era esperado abrir paresenteses !");
            }

            mIndex += 1;

            boolean fechado = false;

            while (mIndex < mFim) {

                Lexema tLexema = mLexemas.get(mIndex);

                if (tLexema.getTipoLexema() == TipoLexema.ID) {
                    mTags.add(tLexema.getConteudo());
                } else if (tLexema.getTipoLexema() == TipoLexema.PARESENTESES_FECHA) {
                    fechado = true;
                    break;
                } else {
                    mErros.add("Lexema nao esperado : " + tLexema.getTipoLexema());
                    break;
                }
                mIndex += 1;
            }

            if (!fechado) {
                mErros.add("Era esperado fechar paresenteses !");
            }


        }

    }

    public void parser_tarefas(AOAnotacao eAOAnotacao) {


        mIndex += 1;
        if (mIndex < mFim) {

            Lexema sLexema = mLexemas.get(mIndex);

            if (sLexema.getTipoLexema() == TipoLexema.CHAVES_ABRE) {


            } else {
                mErros.add("Era esperado abrir chaves !");
            }

            mIndex += 1;

            boolean fechado = false;

            while (mIndex < mFim) {

                Lexema tLexema = mLexemas.get(mIndex);

                if (tLexema.getTipoLexema() == TipoLexema.ID) {

                    if (tLexema.mesmoConteudo("task")) {

                        AOTarefa eAOTarefa = new AOTarefa("");
                        eAOAnotacao.getTarefas().add(eAOTarefa);

                        parser_tarefa(eAOTarefa);
                    } else if (tLexema.mesmoConteudo("page")) {

                        AOPagina eAOPagina = new AOPagina("");
                        eAOAnotacao.getPaginas().add(eAOPagina);

                        parser_pagina(eAOPagina);
                    } else {
                        mErros.add("ID nao desconhecido : " + tLexema.getConteudo());
                    }


                } else if (tLexema.getTipoLexema() == TipoLexema.CHAVES_FECHA) {
                    fechado = true;
                    break;
                } else {
                    mErros.add("Lexema nao esperado : " + tLexema.getTipoLexema());
                    break;
                }
                mIndex += 1;
            }

            if (!fechado) {
                mErros.add("Era esperado fechar chaves !");
            }


        }

    }

    public void parser_tarefa(AOTarefa eAOTarefa) {


        mIndex += 1;
        if (mIndex < mFim) {

            Lexema sLexema = mLexemas.get(mIndex);

            if (sLexema.getTipoLexema() == TipoLexema.TEXTO) {
                eAOTarefa.setNome(sLexema.getConteudo());

            } else {
                mErros.add("Era esperado o texto da tarefa !");
            }

        }

        mIndex += 1;
        if (mIndex < mFim) {

            Lexema sLexema = mLexemas.get(mIndex);

            if (sLexema.getTipoLexema() == TipoLexema.DOIS_PONTOS) {

            } else {
                mErros.add("Era esperado dois pontos !");
            }

        }

        mIndex += 1;
        if (mIndex < mFim) {

            Lexema sLexema = mLexemas.get(mIndex);

            if (sLexema.getTipoLexema() == TipoLexema.ID) {
                eAOTarefa.setMarcador(sLexema.getConteudo());

            } else {
                mErros.add("Era esperado o tipo da tarefa !");
            }


        }

        mIndex += 1;
        if (mIndex < mFim) {

            Lexema sLexema = mLexemas.get(mIndex);

            if (sLexema.getTipoLexema() == TipoLexema.PARENTESESES_ABRE) {

                mIndex -= 1;

                parser_tags(eAOTarefa.getTags());

                mIndex += 1;
                if (mIndex < mFim) {
                    Lexema tLexema = mLexemas.get(mIndex);
                    if (tLexema.getTipoLexema() == TipoLexema.PONTO_VIRGULA) {

                    }
                }

            } else if (sLexema.getTipoLexema() == TipoLexema.PONTO_VIRGULA) {

            } else {
                mErros.add("Era esperado o tipo da tarefa !");
            }


        }


    }

    public void parser_pagina(AOPagina eAOPagina) {


        mIndex += 1;
        if (mIndex < mFim) {

            Lexema sLexema = mLexemas.get(mIndex);

            if (sLexema.getTipoLexema() == TipoLexema.TEXTO) {
                eAOPagina.setNome(sLexema.getConteudo());

            } else {
                mErros.add("Era esperado o texto da pagina !");
            }

        }


        parser_tarefas_pagina(eAOPagina);


    }


    public void parser_tarefas_pagina(AOPagina eAOPagina) {


        mIndex += 1;
        if (mIndex < mFim) {

            Lexema sLexema = mLexemas.get(mIndex);

            if (sLexema.getTipoLexema() == TipoLexema.CHAVES_ABRE) {


            } else {
                mErros.add("Era esperado abrir chaves !");
            }

            mIndex += 1;

            boolean fechado = false;

            while (mIndex < mFim) {

                Lexema tLexema = mLexemas.get(mIndex);

                if (tLexema.getTipoLexema() == TipoLexema.ID) {

                    if (tLexema.mesmoConteudo("task")) {

                        AOTarefa eAOTarefa = new AOTarefa("");
                        eAOPagina.getTarefas().add(eAOTarefa);

                        parser_tarefa(eAOTarefa);

                    } else {
                        mErros.add("ID nao desconhecido : " + tLexema.getConteudo());
                    }


                } else if (tLexema.getTipoLexema() == TipoLexema.CHAVES_FECHA) {
                    fechado = true;
                    break;
                } else {
                    mErros.add("Lexema nao esperado : " + tLexema.getTipoLexema());
                    break;
                }
                mIndex += 1;
            }

            if (!fechado) {
                mErros.add("Era esperado fechar chaves !");
            }


        }


    }

}
