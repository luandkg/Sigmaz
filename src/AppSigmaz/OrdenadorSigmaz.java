package AppSigmaz;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OrdenadorSigmaz {

    public void init(String eLocal) {

        System.out.println("###################### ORDENADOR ############################");
        System.out.println("");

        File ePasta = new File(eLocal);

        String existe = "NAO";

        if (ePasta.exists()) {
            existe = "SIM";
        }

        System.out.println("\t - LOCAL : " + eLocal);
        System.out.println("\t - EXISTE : " + existe);
        System.out.println("");

        if (ePasta.exists()) {

            File eArquivos[] = ePasta.listFiles();
            ArrayList<String> eNomes = new ArrayList<String>();
            int i = 0;
            for (int j = eArquivos.length; i < j; i++) {

                File arquivo = eArquivos[i];
                if (arquivo.isFile()) {
                    if (arquivo.getName().endsWith(".sigmaz")) {
                        eNomes.add(arquivo.getName());
                    }
                }

            }

            // Sorting
            Collections.sort(eNomes, new Comparator<String>() {
                @Override
                public int compare(String a, String b) {
                    return a.compareTo(b);
                }
            });

            int contador = 1;
            int casas = 1;

            if (eNomes.size() >= 10 && eNomes.size() < 100) {
                casas = 2;
            } else if (eNomes.size() >= 100 && eNomes.size() < 999) {
                casas = 3;
            }

            for (String eArq : eNomes) {

                String a = eArq;
                String b = getNome(eArq);
                String c = valorOrdenado(contador, casas) + " - " + b;

                String renomear = "SIM";
                if (a.contentEquals(c)) {
                    renomear = "NAO";
                }

                System.out.println("\t - " + tabular(eArq, 30, " -->> ", 45, c,78," :: ",88,renomear));

                contador += 1;
            }

        }


        System.out.println("");

    }

    public String valorOrdenado(int contador, int casas) {
        String ret = String.valueOf(contador);
        while (ret.length() < casas) {
            ret = "0" + ret;
        }
        return ret;
    }

    public String tabular(String inicio, int eAntes, String meio, int eDepois, String fim) {
        String ret = inicio;

        while (ret.length() < eAntes) {
            ret += " ";
        }

        ret += meio;

        while (ret.length() < eDepois) {
            ret += " ";
        }

        ret += fim;

        return ret;
    }

    public String tabular(String inicio, int eAntes, String meio, int eDepois, String fim, int eAntes2, String meio2, int eDepois2, String fim2) {
        String ret = inicio;

        while (ret.length() < eAntes) {
            ret += " ";
        }

        ret += meio;

        while (ret.length() < eDepois) {
            ret += " ";
        }

        ret += fim;


        while (ret.length() < eAntes2) {
            ret += " ";
        }

        ret += meio2;

        while (ret.length() < eDepois2) {
            ret += " ";
        }

        ret += fim2;

        return ret;
    }


    public String getNome(String eNome) {

        String ret = "";


        int i = 0;
        int o = eNome.length();

        String remover = "0123456789";

        while (i < o) {
            String c = String.valueOf(eNome.charAt(i));

            if (remover.contains(c)) {

            } else {
                i += 1;
                break;
            }

            i += 1;
        }


        if (i < o) {
            if (String.valueOf(eNome.charAt(i)).contentEquals(" ")) {
                i += 1;
            }
        }
        if (i < o) {
            if (String.valueOf(eNome.charAt(i)).contentEquals("-")) {
                i += 1;
            }
        }
        if (i < o) {
            if (String.valueOf(eNome.charAt(i)).contentEquals(" ")) {
                i += 1;
            }
        }

        while (i < o) {
            String c = String.valueOf(eNome.charAt(i));

            ret += c;

            i += 1;
        }

        return ret;
    }
}
