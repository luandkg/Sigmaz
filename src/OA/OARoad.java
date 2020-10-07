package OA;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import Sigmaz.S00_Utilitarios.Texto;

public class OARoad {

    private String mArquivo;

    public OARoad(String eArquivo) {
        mArquivo = eArquivo;
    }

    public ArrayList<String> getNovos(String eArquivo) {

        ArrayList<String> novos = new ArrayList<String>();

        File arq = new File(eArquivo);

        if (arq.exists()) {
            String Documento = Texto.Ler(eArquivo);

            if (Documento != null) {
                Documento += "\n";

                int i = 0;
                int o = Documento.length();

                String linha = "";


                while (i < o) {
                    String l = Documento.charAt(i) + "";
                    if (l.contentEquals("\n")) {


                        if (linha.length() > 0) {

                            linha = linha.replace("\t", " ");
                            while (linha.contains("  ")) {
                                linha = linha.replace("  ", " ");
                            }
                            if (!linha.contentEquals("null")) {
                                novos.add(linha);
                            }

                        }

                        linha = "";
                    } else {
                        linha += l;
                    }
                    i += 1;
                }

            }


        }


        return novos;

    }

    public void receber(String eArquivo) {


        String Documento = "";

        File arq = new File(mArquivo);

        if (arq.exists()) {
            Documento = Texto.Ler(mArquivo);
        }


        for (String linha : getNovos(eArquivo)) {

            if (Documento.contentEquals("null")) {
                Documento = getDataHora() + "  -->>  " + linha + "\n";
            } else {
                Documento += "\n" + getDataHora() + "  -->>  " + linha;
            }


        }


        Texto.Escrever(eArquivo, "");
        Texto.Escrever(mArquivo, Documento);

    }


    public static String getDataHora() {

        Calendar c = Calendar.getInstance();

        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH) + 1;
        int ano = c.get(Calendar.YEAR);

        String sdia = String.valueOf(dia);
        String smes = String.valueOf(mes);

        if (sdia.length() == 1) {
            sdia = "0" + sdia;
        }
        if (smes.length() == 1) {
            smes = "0" + smes;
        }

        return ano + " " + smes + " " + sdia;

    }


    public ArrayList<Grupo<String>> getRoads() {

        String Documento = "";

        File arq = new File(mArquivo);

        if (arq.exists()) {
            Documento = Texto.Ler(mArquivo);
        }


        ArrayList<String> mLinhas = new ArrayList<String>();

        int i = 0;
        int o = Documento.length();

        String mLinha = "";

        while (i < o) {
            String l = Documento.charAt(i) + "";

            if (l.contentEquals("\n")) {
                if (mLinha.length() > 0) {
                    if (mLinha.replace(" ", "").length() > 0) {
                        if (mLinha.replace("\t", "").length() > 0) {
                            mLinhas.add(mLinha);
                        }
                    }
                }
                mLinha = "";
            } else {
                mLinha += l;
            }
            i += 1;
        }

        if (mLinha.length() > 0) {
            if (mLinha.replace(" ", "").length() > 0) {
                if (mLinha.replace("\t", "").length() > 0) {
                    mLinhas.add(mLinha);
                }
            }
        }



        Agrupador<String> mAgrupador = new Agrupador<String>();


        for (String eLinha : mLinhas) {
            if (eLinha.length() > 19) {

                String ePrefixo = eLinha.substring(0, 10);
                String eResto = eLinha.substring(18);

                if (eResto.length() > 31) {
                    //    eResto = eResto.substring(0, 30) + "...";
                }

                //    System.out.println(ePrefixo + " :: " + eResto);

                mAgrupador.agrupar(ePrefixo).adicionar(eResto);
            }

        }

        return mAgrupador.getGrupos();
    }

    public void exportarImagem(String eArquivo,Color eBarra,Color eFonteCor) {


        OATrilha mRotas = new OATrilha();

        mRotas.gerarRotasH(eArquivo,this.getRoads(), eBarra,eFonteCor);



    }

    public void exportarImagemHV(String eArquivo,Color eBarra,Color eFonteCor) {


        OATrilha mRotas = new OATrilha();

        mRotas.gerarRotasHV(eArquivo,this.getRoads(), eBarra,eFonteCor);



    }



    public void exportarMarkDown(String eArquivo, String eTitulo, String eLinkImagem, String eDesImagem) {

        ArrayList<Grupo<String>> mGrupos = getRoads();


        String eConteudo = "";

        eConteudo +="![" + eDesImagem+ "](" + eLinkImagem + ")";
        eConteudo+="\n\n";

        eConteudo+="\n" + eTitulo;
        eConteudo+="\n\n";

        for (OA.Grupo<String> Grupo : mGrupos) {



            for(String eLinha : Grupo.getObjetos()){

                eConteudo+="\n\t\t" + Grupo.getNome() + " -->> " + eLinha;

            }

            eConteudo+="\n\n";
        }

        Texto.Escrever(eArquivo, eConteudo);

    }

}
