package Sigmaz.S06_Montador;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class R5 {


    public R5Resposta guardar(String eDocumento, String eArquivo) {

        R5Resposta mR5Resposta = new R5Resposta();

        byte[] bytes = eDocumento.getBytes(StandardCharsets.UTF_8);


        int i = 0;
        int o = eDocumento.length();


        Chaveador mChaveador = new Chaveador();

        int ic = 0;
        int oc = mChaveador.getChaveTamanho();


        while (i < o) {
            int novo = (int) bytes[i];


            novo += mChaveador.getChave()[ic];
            ic += 1;
            if (ic == oc) {
                ic = 0;
            }

            if (novo > 255) {
                novo -= 255;
            }


            bytes[i] = (byte) novo;
            i += 1;
        }

        try {

            File arq = new File(eArquivo);
            if (arq.exists()) {
                arq.delete();
            }


            Files.write(Paths.get(eArquivo), bytes);

            mR5Resposta.validar("");

        } catch (IOException e) {
            mR5Resposta.anular();
        }

        return mR5Resposta;
    }

    public R5Resposta revelar(String eArquivo) {

        String mDecompilado = "";

        R5Resposta mR5Resposta = new R5Resposta();


        Chaveador mChaveador = new Chaveador();


        int ic = 0;
        int oc = mChaveador.getChaveTamanho();

        try {
            byte[] l = Files.readAllBytes(Paths.get(eArquivo));

            int li = 0;
            int lo = l.length;

            while (li < lo) {
                int novo = (int) l[li];

                //	novo -= auxilador;

                novo -= mChaveador.getChave()[ic];
                ic += 1;
                if (ic == oc) {
                    ic = 0;
                }


                if (novo < 0) {
                    novo += 256;
                }


                l[li] = (byte) novo;
                li += 1;
            }


            mDecompilado = new String(l, StandardCharsets.UTF_8);


            // System.out.println("Decompilado : " + saida);

            mR5Resposta.validar(mDecompilado);

        } catch (IOException e) {
            e.printStackTrace();
            mR5Resposta.anular();
        }

        return mR5Resposta;
    }

}
