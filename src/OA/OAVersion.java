package OA;

import OA.LuanDKG.LuanDKG;
import OA.LuanDKG.Pacote;

import java.io.File;
import java.util.Calendar;

public class OAVersion {

    private String mArquivo;

    public OAVersion(String eArquivo) {
        mArquivo = eArquivo;
    }


    public void init() {


        LuanDKG arquivo = new LuanDKG();
        File arq = new File(mArquivo);

        if (arq.exists()) {
            arquivo.Abrir(mArquivo);
        }

        Pacote OA = arquivo.UnicoPacote("OA");

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

        Pacote Releases = OA.UnicoPacote("Releases");
        for (Pacote P : Releases.getPacotes()) {
            P.salvarLinear();
        }

        //  int Trabalhos = Branches.getPacotes().size() * 24;

        int Trabalhos = 0;
        for (Pacote CBranch : Branches.getPacotes()) {

            String CBT = CBranch.Identifique("Count").getValor();

            int CBTI = get_string_num(CBT, 1);
            Trabalhos += CBTI;
        }


        int Trabalhos_Versao = 0;

        int Trabalhos_Antes = Trabalhos;

        int ri = 50;

        while (Trabalhos >= ri) {
            Trabalhos -= ri;
            Trabalhos_Versao += 1;
        }

        //System.out.println("TRABALHADOR :: " + Trabalhos_Antes + " -->> " + Trabalhos + " com " + ri);

        int ti = Trabalhos;

        String Full = v + "." + min3(Trabalhos_Versao) + "." + ti;

        int mi = ci;
        int rr = 1;

        while (mi >= 50) {

            Pacote Release = Releases.PacoteComAtributoUnico("Release", "Date", Hoje);
            String FullRelease = Full + " R" + rr;

            Release.Identifique("Releaser").setValor(FullRelease);

            OA.Identifique("Releaser").setValor(FullRelease);

            mi -= 50;
            rr += 1;
        }


        // reorganizar(v,Branches,Releases);

        OA.Identifique("Version").setValor(v);
        OA.Identifique("Branch").setValor(Hoje);
        OA.Identifique("Works").setValor(String.valueOf(Trabalhos));
        OA.Identifique("Full").setValor(Full);

        arquivo.Salvar(mArquivo);
    }


    private void reorganizar(String v, Pacote Branches, Pacote Releases) {

        int Trabalhos = 0;

        for (Pacote CBranch : Branches.getPacotes()) {

            String CBT = CBranch.Identifique("Count").getValor();

            String C = CBranch.Identifique("Count").getValor();

            String eData = CBranch.Identifique("Date").getValor();

            int ci = get_string_num(C, 1);

            Trabalhos += ci;

            int Trabalhos_Versao = 0;

            int Trabalhos_Antes = Trabalhos;

            int ri = 50;

            while (Trabalhos_Antes >= ri) {
                Trabalhos_Antes -= ri;
                Trabalhos_Versao += 1;
            }

            //System.out.println("TRABALHADOR :: " + Trabalhos_Antes + " -->> " + Trabalhos + " com " + ri);

            int ti = Trabalhos_Antes;

            String Full = v + "." + min3(Trabalhos_Versao) + "." + ti;

            System.out.println(eData + " >> " + Full + " :: " + ci);

            int rr = 1;

            int mi = ci;
            String FullRelease = Full + " R1";
            int si = 0;
            int gi = ci;

            while (mi >= 50) {
                si += 50;

                FullRelease = v + "." + min3(Trabalhos_Versao) + "." + (gi - si) + " R" + rr;

                Pacote Release = Releases.PacoteComAtributoUnico("Release", "Date", eData);

                Release.Identifique("Releaser").setValor(FullRelease);

                //  OA.Identifique("Releaser").setValor(FullRelease);

                mi -= 50;
                rr += 1;
            }

            System.out.println("\t - " + FullRelease);

        }

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
