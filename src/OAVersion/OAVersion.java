package OAVersion;

import OAVersion.LuanDKG.Identificador;
import OAVersion.LuanDKG.LuanDKG;
import OAVersion.LuanDKG.Pacote;

import java.io.File;
import java.util.Calendar;

public class OAVersion {

    public static void init(String eArquivo) {

        LuanDKG arquivo = new LuanDKG();
        File arq = new File(eArquivo);

        if (arq.exists()) {
            arquivo.Abrir(eArquivo);
        }

        Pacote OA = arquivo.UnicoPacote("OAVersion");

        Pacote Branches = OA.UnicoPacote("Branches");

        String v = OA.Identifique("Version").getValor();

        v = string_num(v, 1);

        String Hoje = getData();

        String b = OA.Identifique("Branch").getValor();
        Pacote Branch = null;

        int ci = 1;

        if (b.length() == 0) {
            Branch = Branches.PacoteComAtributoUnico("Branch", "Date", Hoje);

            Branch.Identifique("Start").setValor(getDataHora());
            Branch.Identifique("Update").setValor(".");
            Branch.Identifique("End").setValor(".");
            Branch.Identifique("Status").setValor("BLUE");

        } else {

            Branch = Branches.PacoteComAtributoUnico("Branch", "Date", b);

            if (b.contentEquals(Hoje)) {
                Branch.Identifique("Update").setValor(getDataHora());

                String C = Branch.Identifique("Count").getValor();

                ci = get_string_num(C, 1) + 1;
                Branch.Identifique("Status").setValor("GREEN");


            } else {
                Branch.Identifique("End").setValor(getDataHora());
                Branch.Identifique("Status").setValor("RED");

                Branch = Branches.PacoteComAtributoUnico("Branch", "Date", Hoje);
                Branch.Identifique("Start").setValor(getDataHora());
                Branch.Identifique("Update").setValor(".");
                Branch.Identifique("End").setValor(".");
                Branch.Identifique("Status").setValor("BLUE");

            }

        }

        Branch.Identifique("Count").setValor(String.valueOf(ci));


        int Trabalhos = Branches.getPacotes().size();

        String Full = v + "." + min3(Trabalhos) + "." + ci;

        OA.Identifique("Version").setValor(v);
        OA.Identifique("Branch").setValor(Hoje);
        OA.Identifique("Works").setValor(String.valueOf(Trabalhos));
        OA.Identifique("Full").setValor(Full);
        OA.Identifique("Mode").setValor(".");
        OA.Identifique("Release").setValor(".");

        arquivo.Salvar(eArquivo);
    }

    public static void release(String eArquivo) {

        LuanDKG arquivo = new LuanDKG();
        File arq = new File(eArquivo);

        if (arq.exists()) {
            arquivo.Abrir(eArquivo);
        }

        Pacote OA = arquivo.UnicoPacote("OAVersion");

        Pacote Releases = OA.UnicoPacote("Releases");


        String Hoje = getData();

        Pacote R = Releases.PacoteComAtributoUnico("Release", "Date", Hoje);

        String C = R.Identifique("Count").getValor();

        int ci = get_string_num(C, 0) + 1;

        R.Identifique("Count").setValor(String.valueOf(ci));


        String RString = OA.Identifique("Full").getValor() + " R" + ci;

        Pacote V = R.PacoteComAtributoUnico("Version", "Full", RString);

        V.Identifique("Release").setValor(String.valueOf(ci));

        for (Pacote PacoteC : R.getPacotes()) {
            PacoteC.salvarLinear();
        }

        OA.Identifique("Mode").setValor("Release");
        OA.Identifique("Release").setValor(RString);

        arquivo.Salvar(eArquivo);
    }


    private static String string_num(String v, int min) {

        if (v.length() == 0) {
            v = "1";
        }

        try {
            int vi = Integer.parseInt(v);
            if (vi < min) {
                v = String.valueOf(min);
            }
        } catch (Exception e) {
            v = "1";
        }

        return v;

    }

    private static int get_string_num(String v, int min) {

        int vi = 0;

        if (v.length() == 0) {
            vi = min;
        }

        try {
            vi = Integer.parseInt(v);
            if (vi < min) {
                vi = min;
            }
        } catch (Exception e) {
            vi = min;
        }

        return vi;

    }

    private static String min3(int v) {

        String sv = String.valueOf(v);

        if (sv.length() == 0) {
            //     sv = "000";
        } else if (sv.length() == 1) {
            //     sv = "00" + sv;
        } else if (sv.length() == 2) {
            //   sv = "0" + sv;
        }


        return sv;
    }

    public static String getDataHora() {

        Calendar c = Calendar.getInstance();

        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH) + 1;
        int ano = c.get(Calendar.YEAR);

        int hora = c.get(Calendar.HOUR);
        int minutos = c.get(Calendar.MINUTE);
        int segundos = c.get(Calendar.SECOND);

        return dia + "/" + mes + "/" + ano + " " + hora + ":" + minutos + ":" + segundos;

    }


    public static String getData() {

        Calendar c = Calendar.getInstance();

        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH) + 1;
        int ano = c.get(Calendar.YEAR);


        return dia + "/" + mes + "/" + ano;

    }

    public static String getHora() {

        Calendar c = Calendar.getInstance();


        int hora = c.get(Calendar.HOUR);
        int minutos = c.get(Calendar.MINUTE);
        int segundos = c.get(Calendar.SECOND);

        return hora + ":" + minutos + ":" + segundos;

    }


}
