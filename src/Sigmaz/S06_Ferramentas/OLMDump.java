package Sigmaz.S06_Ferramentas;

import Sigmaz.S04_Montador.ArquivadorUtils;
import Sigmaz.S04_Montador.OLMCabecalho;

import java.io.File;

public class OLMDump {

    private int mMAXIMO_LINHA;

    public OLMDump() {
        mMAXIMO_LINHA = 30;
    }

    public void dump(String eArquivo) {

        System.out.println("");
        System.out.println("################ DUMP ####################################################################################");
        System.out.println("");
        System.out.println("\t - Analisando        : " + eArquivo);


        File mArquivo = new File(eArquivo);

        boolean mEnc = false;

        if (mArquivo.exists()) {
            System.out.println("\t - Encontrado        : Sim");
            mEnc = true;
        }else{
            System.out.println("\t - Encontrado        : Não");
        }



        if (mEnc) {

            OLMCabecalho mOLM = new OLMCabecalho();

            boolean mOk = false;

            try {
                mOLM.ler(eArquivo);

                mOk = true;

                System.out.println("\t - OLM               : Integro !");

                System.out.println("\t - OLM Titulo        : " + mOLM.getTitulo());
                System.out.println("\t - OLM Versao        : " + mOLM.getVersao());
                System.out.println("\t - OLM Tamanho       : " + mOLM.getTamanho());

                System.out.println("\t - OLM Sigmaz        : " + getNum(mOLM.getSetorSigmaz_Inicio(), 8) + " :: " + getNum(mOLM.getSigmaz_Fim(),8)  + " -->> " + getNum(mOLM.getSigmaz_Tamanho(),8) );
                System.out.println("\t - OLM Codigo        : " + getNum(mOLM.getSetorCodigo_Inicio(), 8) + " :: " + getNum(mOLM.getCodigo_Fim(),8)  + " -->> " +getNum( mOLM.getCodigo_Tamanho(),8) );
                System.out.println("\t - OLM Assinatura    : " + getNum(mOLM.getAssinatura_Inicio(), 8) + " :: " + getNum(mOLM.getAssinatura_Fim(),8)  + " -->> " +getNum( mOLM.getAssinatura_Tamanho(),8) );
                System.out.println("\t - OLM Debug         : " + getNum(mOLM.getSetorDebug_Inicio(), 8) + " :: " + getNum(mOLM.getDebug_Tamanho(),8)  + " -->> " + getNum(mOLM.getDebug_Fim(),8) );


            } catch (Exception e) {
                System.out.println("\t - OLM : Corrompido !");
            }

            if (mOk) {

                ArquivadorUtils ma = new ArquivadorUtils(eArquivo);

                // dumpTudo(ma);

                dumpSetor(ma, "OLM", 0, mOLM.getSetorSigmaz_Inicio());
                dumpSetor(ma, "SIGMAZ", mOLM.getSetorSigmaz_Inicio(), mOLM.getSigmaz_Fim());
                dumpSetor(ma, "CODIGO", mOLM.getSetorCodigo_Inicio(), mOLM.getCodigo_Fim());
                dumpSetor(ma, "SIGN", mOLM.getAssinatura_Inicio(), mOLM.getAssinatura_Fim());
                dumpSetor(ma, "DEBUG", mOLM.getSetorDebug_Inicio(), mOLM.getDebug_Fim());

                ma.close();

                System.out.println("");
                System.out.println("");
                System.out.println("");

            }


        }

    }

    public String getNum(long eInicio, int n) {
        String str = String.valueOf(eInicio);
        while (str.length() < 8) {
            str = "0" + str;
        }
        return str;
    }

    public void dumpTudo(ArquivadorUtils ma) {

        ma.setPonteiro(0);


        long mTam = ma.getLength();
        long mIndex = 0;

        long mLinha = mMAXIMO_LINHA;
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
                eLinha_Fim = mIndex + mLinha;
                if (eLinha_Fim > mTam) {
                    eLinha_Fim = mTam;
                }

                i = 0;
                System.out.print("\n " + textoLong(mIndex, 5) + " :: " + textoLong(eLinha_Fim, 5) + " - ");
            } else {
                i += 1;
            }

            mIndex += 1;
        }

    }

    public void dumpSetor(ArquivadorUtils ma, String eTitulo, long eInicio, long eFim) {

        System.out.println("");
        System.out.println("");
        System.out.println("##############################################################################################################################################");
        System.out.println("");
        System.out.println("SETOR : " + eTitulo);
        System.out.println("\t - Inicio : " + eInicio);
        System.out.println("\t - Fim : " + eFim);
        System.out.println("\t - Tamanho : " + (eFim - eInicio));

        System.out.println("");

        ma.setPonteiro(eInicio);


        long mIndex = 0;
        long mTam = eFim;

        long mLinha = mMAXIMO_LINHA;
        long i = 0;

        long eLinha_Inicio = 0;

        long eLinha_Fim = eLinha_Inicio + mLinha;
        if (eLinha_Fim > mTam) {
            eLinha_Fim = mTam;
        }

        String eCab = textoLong(eLinha_Inicio, 5) + " :: " + textoLong(eLinha_Fim, 5) + " - ";
        int a = 0;

        while (mIndex < eInicio) {

            a += 1;

            if (i == mLinha) {
                eLinha_Fim = mIndex + mLinha;
                if (eLinha_Fim > mTam) {
                    //eLinha_Fim = mTam;
                }
                eCab = textoLong(mIndex, 5) + " :: " + textoLong(eLinha_Fim, 5) + " - ";
                i = 0;
                a = 0;
            } else {
                i += 1;
            }

            mIndex += 1;
        }


        System.out.print("\n " + eCab);

        i = 0;
        while (i < a) {
            System.out.print("    ");
            i += 1;
        }

        i = a;
        while (mIndex < mTam) {


            System.out.print(textoInt(organizarByteInt(ma.readByte()), 3) + " ");


            if (i == mLinha) {
                eLinha_Fim = mIndex + mLinha;
                if (eLinha_Fim > mTam) {
                    eLinha_Fim = mTam;
                }

                i = 0;
                System.out.print("\n " + textoLong(mIndex, 5) + " :: " + textoLong(eLinha_Fim, 5) + " - ");
            } else {
                i += 1;
            }

            mIndex += 1;
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
