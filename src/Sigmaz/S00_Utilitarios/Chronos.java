package Sigmaz.S00_Utilitarios;

import java.util.Calendar;

public class Chronos {

    public long get() {
        return System.currentTimeMillis();
    }

    public float getIntervalo(long eMenor, long eMaior) {

        return (eMaior - eMenor) / 1000F;

    }


    public static String getData() {

        Calendar c = Calendar.getInstance();

        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH) + 1;
        int ano = c.get(Calendar.YEAR);

        int hora = c.get(Calendar.HOUR);
        int minutos = c.get(Calendar.MINUTE);
        int segundos = c.get(Calendar.SECOND);

        return dia + "/" + mes + "/" + ano + " " + hora + ":" + minutos + ":" + segundos;

    }

}
