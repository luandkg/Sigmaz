package Make.Run;

import Sigmaz.Utils.AST;

import java.io.File;

public class Runner_Remove {


    private RunMake mRunMake;

    public Runner_Remove(RunMake eRunMake) {
        mRunMake = eRunMake;
    }

    public void init(AST ASTCGlobal) {

        if (ASTCGlobal.mesmoNome("build")) {


            remover(mRunMake.getBuild(), ASTCGlobal.getValor());

        } else if (ASTCGlobal.mesmoNome("intellisense")) {

            remover(mRunMake.getIntellisenses(), ASTCGlobal.getValor());

        } else {
            mRunMake.errar(mRunMake.getLocal(), "LOCAL REMOVE desconhecido : " + ASTCGlobal.getNome());
        }

    }

    public void remover(String eLocal, String eTipo) {

       // System.out.println("REMOVER :: " + eTipo + " de " + eLocal);

       // System.out.println("################ REMOVEDOR ################");


        File ePasta = new File(eLocal);
        if (ePasta.exists()) {


            File afile[] = ePasta.listFiles();
            int i = 0;

            for (int j = afile.length; i < j; i++) {
                File arquivo = afile[i];

                if (arquivo.getName().endsWith(eTipo)){

                  //  System.out.println("\t - REMOVENDO :: " + arquivo.getName());

                    arquivo.delete();

                }
            }


        }else{


        }


    }
}
