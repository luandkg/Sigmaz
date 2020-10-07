package Container;

import Sigmaz.S00_Utilitarios.ArquivoAssociativo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;

public class Creator {

    public static final String ASSET_CONTAINER = "ASSET_CONTAINER";

    public void criar(String eArquivo, ArrayList<ArquivoAssociativo> mArquivos) {

        criarGeral(eArquivo, mArquivos, ASSET_CONTAINER);

    }


    private void criarGeral(String eArquivo,ArrayList<ArquivoAssociativo> mArquivos, String eCabecalho) {

        String eVersao = "1.0";

        try {

            RandomAccessFile raf = new RandomAccessFile(new File(eArquivo), "rw");

            FileBinary fu = new FileBinary(raf);

            fu.limpar();

            fu.inicio();

            fu.writeString(eCabecalho);

            fu.writeString(eVersao);

            long eCriado = fu.getPonteiro();
            fu.writeString(getTempo());

            long eFinalizado = fu.getPonteiro();
            fu.writeString(getTempo());

            long eApendiceTem = fu.getPonteiro();

            fu.writeByte((byte) 0);

            long eApendicePonteiro = fu.getPonteiro();

            fu.writeLong(0);

            if (eCabecalho.contentEquals(ASSET_CONTAINER)) {

                criarPasta(fu, mArquivos);


            }


            //fu.dump();

            long eApendiceInicio = fu.getPonteiro();

            fu.writeByte((byte) 50);

            fu.writeByte((byte) 55);


            raf.seek(eApendiceTem);
            fu.writeByte((byte) 1);

            raf.seek(eApendicePonteiro);
            raf.writeLong(eApendiceInicio);

            raf.seek(eFinalizado);
            fu.writeString(getTempo());


            raf.close();

        } catch (IOException e) {

            e.printStackTrace();
        }

    }


    public void criarPasta(FileBinary fu, ArrayList<ArquivoAssociativo> mArquivos) {


       // File dir = new File(eLocal);

        ArrayList<Ponto> mPontos = new ArrayList<Ponto>();

       // if (dir.exists()) {
            for (ArquivoAssociativo eDir : mArquivos) {

             //   if (eDir.isDirectory()) {

                //    fu.writeLong(11);
                //    fu.writeString(eDir.getName());

                 //   long eInicio = fu.getPonteiro();

                  //  fu.writeLong(0);

                 //   long eFim = fu.getPonteiro();

                  //  fu.writeLong(0);

                //    mPontos.add(new Ponto(eDir.getAbsolutePath(), 11, eInicio, eFim));

              //  } else if (eDir.isFile()) {

                    fu.writeLong(12);
                    fu.writeString(eDir.getNome());

                    long eInicio = fu.getPonteiro();

                    fu.writeLong(0);

                    long eFim = fu.getPonteiro();

                    fu.writeLong(0);

                    mPontos.add(new Ponto(eDir.getArquivo(), 12, eInicio, eFim));

              //  }

                // System.out.println(" ->> " + eDir.getName() + " :: "+ eDir.isDirectory());
            }
      //  }

        fu.writeLong(13);

        //System.out.println(" ALOCANDO : " + eLocal);

        long ePonteiroLocal = fu.getPonteiro();

        for (Ponto PontoC : mPontos) {

          //  System.out.println("\t -->> " + PontoC.getTipo() + " : " + PontoC.getNome().replace(eLocal + "\\", ""));

            System.out.println("\t -->> " + PontoC.getTipo() + " : " + PontoC.getNome());

            fu.setPonteiro(ePonteiroLocal);

            if (PontoC.getTipo() == 11) {

          //      long ePastaPonteiro_Inicio = fu.getPonteiro();

             //   criarPasta(fu, PontoC.getNome());

           //     long ePastaPonteiro_Fim = fu.getPonteiro();

            //    ePonteiroLocal = fu.getPonteiro();

           //     fu.setPonteiro(PontoC.getInicio());
            //    fu.writeLong(ePastaPonteiro_Inicio);

           //     fu.setPonteiro(PontoC.getFim());
           //     fu.writeLong(ePastaPonteiro_Fim);

            } else if (PontoC.getTipo() == 12) {

                long eAquivoPonteiro_Inicio = fu.getPonteiro();

                File mArquivoReal = new File(PontoC.getNome());

                if (mArquivoReal.exists()){


                    try {

                        RandomAccessFile mArquivando = new RandomAccessFile(new File(PontoC.getNome()), "r");

                        long mAquivandoIndex = 0;
                        long mAquivandoTamanho = mArquivando.length();

                        mArquivando.seek(0);

                        while (mAquivandoIndex < mAquivandoTamanho) {

                            fu.writeByte(mArquivando.readByte());

                            mAquivandoIndex += 1;
                        }

                        mArquivando.close();

                    } catch (IOException e) {

                        e.printStackTrace();
                    }


                    long eAquivoPonteiro_Fim = fu.getPonteiro();

                    ePonteiroLocal = fu.getPonteiro();

                    fu.setPonteiro(PontoC.getInicio());
                    fu.writeLong(eAquivoPonteiro_Inicio);

                    fu.setPonteiro(PontoC.getFim());
                    fu.writeLong(eAquivoPonteiro_Fim);


                }




            }

            fu.setPonteiro(ePonteiroLocal);

        }

    }



    private String getTempo() {

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
