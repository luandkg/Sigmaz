package OAVersion;

import OAVersion.LuanDKG.LuanDKG;
import OAVersion.LuanDKG.Pacote;
import Sigmaz.Utils.Texto;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class OARoadmap {

    private String mArquivo;

    public OARoadmap(String eArquivo) {
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
                Documento =getDataHora() + "  -->>  " + linha + "\n";
            }else{
                Documento +="\n" + getDataHora() + "  -->>  " + linha ;
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

        if (sdia.length()==1){sdia = "0" + sdia;}
        if (smes.length()==1){smes = "0" + smes;}

        return ano + " " + smes + " " + sdia;

    }

}
