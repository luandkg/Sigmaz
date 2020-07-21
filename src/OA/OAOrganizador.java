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


    public void renderizar(String eSincronizar,String eArquivoQuadro) {


        System.out.println("Sincronizador de Organizacao");

        ArrayList<Note> mNotes = new ArrayList<Note>();
        ArrayList<Note> mConfig = new ArrayList<Note>();

        mErros = new ArrayList<String>();


        parser(eSincronizar,mNotes,mConfig,mErros);

        mIndex = 0;
        mFim = mLexemas.size();

        while (mIndex < mFim) {
            Lexema eLexema = mLexemas.get(mIndex);


            if (eLexema.getTipoLexema() == TipoLexema.ID && eLexema.mesmoConteudo("note")) {

                Note eNote = new Note("");
                mNotes.add(eNote);

                parser_note(eNote);

            } else if (eLexema.getTipoLexema() == TipoLexema.ID && eLexema.mesmoConteudo("config")) {

                Note eNote = new Note("");
                mConfig.add(eNote);

                parser_note(eNote);
            }


            mIndex += 1;
        }

        if (mErros.size() == 0) {

/*            for (Note eNote : mNotes) {

                imprimir("NOTE", eNote);

            }

            for (Note eNote : mConfig) {

                imprimir("CONFIGURATION", eNote);

            }*/

            OAQuadrum Q = new OAQuadrum();
            Q.exportar(eArquivoQuadro,mNotes,mConfig);
        } else {

            for (String erro : mErros) {
                System.out.println("ERRO :: " + erro);
            }

        }


    }

    public void imprimir(String eTitulo, Note eNote) {

        System.out.println(eTitulo + " : " + eNote.getNome());
        System.out.println("\t - Marcador :  " + eNote.getMarcador());

        System.out.println("\t - Tags :  ");

        for (String eTag : eNote.getTags()) {
            System.out.println("\t\t - " + eTag);
        }

        System.out.println("\t - Tarefas :  ");

        for (Tarefa eTarefa : eNote.getTarefas()) {
            System.out.println("\t\t - " + eTarefa.getNome() + " : " + eTarefa.getMarcador());

            System.out.println("\t\t\t - Tags :  ");

            for (String eTag : eTarefa.getTags()) {
                System.out.println("\t\t\t\t - " + eTag);
            }


        }

        System.out.println("\t - Paginas :  ");

        for (Pagina ePagina : eNote.getPaginas()) {
            System.out.println("\t\t - " + ePagina.getNome());


            System.out.println("\t\t\t - Tarefas :  ");

            for (Tarefa eTarefa : ePagina.getTarefas()) {
                System.out.println("\t\t\t\t - " + eTarefa.getNome() + " : " + eTarefa.getMarcador());

                System.out.println("\t\t\t\t\t\t - Tags :  ");

                for (String eTag : eTarefa.getTags()) {
                    System.out.println("\t\t\t\t\t\t\t - " + eTag);
                }


            }

        }


    }


    public void parser(String eSincronizar,  ArrayList<Note> mNotes, ArrayList<Note> mConfig,ArrayList<String> mErros) {



        mLexemas = new ArrayList<Lexema>();


        File arq = new File(eSincronizar);

        if (arq.exists()) {

            mDocumento = Texto.Ler(eSincronizar);


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

    public void parser_note(Note eNote) {


        mIndex += 1;
        if (mIndex < mFim) {

            Lexema sLexema = mLexemas.get(mIndex);

            if (sLexema.getTipoLexema() == TipoLexema.TEXTO) {
                eNote.setNome(sLexema.getConteudo());

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
                eNote.setMarcador(sLexema.getConteudo());

            } else {
                mErros.add("Era esperado o tipo da anotacao !");
            }


        }

        parser_tags(eNote.getTags());

        parser_tarefas(eNote);


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

    public void parser_tarefas(Note eNote) {


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

                        Tarefa eTarefa = new Tarefa("");
                        eNote.getTarefas().add(eTarefa);

                        parser_tarefa(eTarefa);
                    } else if (tLexema.mesmoConteudo("page")) {

                        Pagina ePagina = new Pagina("");
                        eNote.getPaginas().add(ePagina);

                        parser_pagina(ePagina);
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

    public void parser_tarefa(Tarefa eTarefa) {


        mIndex += 1;
        if (mIndex < mFim) {

            Lexema sLexema = mLexemas.get(mIndex);

            if (sLexema.getTipoLexema() == TipoLexema.TEXTO) {
                eTarefa.setNome(sLexema.getConteudo());

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
                eTarefa.setMarcador(sLexema.getConteudo());

            } else {
                mErros.add("Era esperado o tipo da tarefa !");
            }


        }

        mIndex += 1;
        if (mIndex < mFim) {

            Lexema sLexema = mLexemas.get(mIndex);

            if (sLexema.getTipoLexema() == TipoLexema.PARENTESESES_ABRE) {

                mIndex -= 1;

                parser_tags(eTarefa.getTags());

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

    public void parser_pagina(Pagina ePagina) {


        mIndex += 1;
        if (mIndex < mFim) {

            Lexema sLexema = mLexemas.get(mIndex);

            if (sLexema.getTipoLexema() == TipoLexema.TEXTO) {
                ePagina.setNome(sLexema.getConteudo());

            } else {
                mErros.add("Era esperado o texto da pagina !");
            }

        }


        parser_tarefas_pagina(ePagina);


    }


    public void parser_tarefas_pagina(Pagina ePagina) {


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

                        Tarefa eTarefa = new Tarefa("");
                        ePagina.getTarefas().add(eTarefa);

                        parser_tarefa(eTarefa);

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
