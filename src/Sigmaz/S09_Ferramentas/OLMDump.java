package Sigmaz.S09_Ferramentas;

import Sigmaz.S07_Montador.ArquivadorUtils;
import Sigmaz.S07_Montador.OLMCabecalho;

import java.io.File;

public class OLMDump {


    public void dump(String eArquivo) {

        System.out.println("");
        System.out.println("################ DUMP ################");
        System.out.println("");
        System.out.println("\t - Analisando : " + eArquivo);


        File mArquivo = new File(eArquivo);

        String eEnc = "Nao";
        boolean mEnc = false;

        if (mArquivo.exists()) {
            eEnc = "Sim";
            mEnc = true;
        }

        System.out.println("\t - Encontrado : " + eEnc);

        boolean mOk = false;

        if (mEnc) {


            try {
                OLMCabecalho mOLM = new OLMCabecalho();
                mOLM.ler(eArquivo);

                mOk=true;

                System.out.println("\t - OLM : Integro !");

                System.out.println("\t - OLM Titulo : " + mOLM.getTitulo());
                System.out.println("\t - OLM Versao : " + mOLM.getVersao());
                System.out.println("\t - OLM Tamanho : " + mOLM.getTamanho());

                System.out.println("\t - OLM Sigmaz : " + mOLM.getSetorSigmaz_Inicio() + " :: " + mOLM.getSigmaz_Fim() + " -->> " + mOLM.getSigmaz_Tamanho());
                System.out.println("\t - OLM Codigo : " + mOLM.getSetorCodigo_Inicio() + " :: " + mOLM.getCodigo_Fim() + " -->> " + mOLM.getCodigo_Tamanho());
                System.out.println("\t - OLM Assinatura : " + mOLM.getAssinatura_Inicio() + " :: " + mOLM.getAssinatura_Fim() + " -->> " + mOLM.getAssinatura_Tamanho());


            } catch (Exception e) {

                System.out.println("\t - OLM : Corrompido !");

            }

            if (mOk){

                ArquivadorUtils ma = new ArquivadorUtils(eArquivo);
                ma.setPonteiro(0);


                long mTam = ma.getLength();
                long mIndex = 0;

                long mLinha = 30;
                long i = 0;

                long eLinha_Inicio = 0;

                System.out.println("\t - Tamanho : " + mTam);



                System.out.println("");

                long eLinha_Fim = eLinha_Inicio + mLinha;
                if (eLinha_Fim > mTam) {
                    eLinha_Fim = mTam;
                }

                System.out.print("\n " + textoLong(eLinha_Inicio, 5) + " :: " + textoLong(eLinha_Fim, 5) + " - ");

                while (mIndex < mTam) {


                    System.out.print(textoInt(organizarByteInt(ma.readByte()), 3) + " ");


                    if (i == mLinha) {
                        eLinha_Fim= mIndex+mLinha;
                        if (eLinha_Fim > mTam) {
                            eLinha_Fim = mTam;
                        }

                        i = 0;
                        System.out.print("\n " + textoLong(mIndex , 5) + " :: " + textoLong(eLinha_Fim, 5) + " - ");
                    } else {
                        i += 1;
                    }

                    mIndex += 1;
                }


            }


        }

    }

    public int organizarByteInt(byte b) {

        if (b >= 0) {
            return (int) b;
        } else {
            return 256 + (int) b;
        }

    }

    public String textoInt(int b, int casas) {

        String m = String.valueOf(b);

        while (m.length() < casas) {
            m = "0" + m;
        }


        return m;
    }

    public String textoLong(long b, int casas) {

        String m = String.valueOf(b);

        while (m.length() < casas) {
            m = "0" + m;
        }


        return m;
    }
}
