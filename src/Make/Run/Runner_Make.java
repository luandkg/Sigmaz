package Make.Run;

import Sigmaz.S05_PosProcessamento.Processadores.Cabecalho;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.Sigmaz_Compilador;

import java.util.ArrayList;

public class Runner_Make {

    private RunMake mRunMake;

    public Runner_Make(RunMake eRunMake) {
        mRunMake = eRunMake;
    }

    public void init(AST ASTCGlobal) {

        Cabecalho eCabecalho = new Cabecalho();


        for (String eAutor : mRunMake.getAutores()) {
            eCabecalho.autor_adicionar(eAutor);
        }

        eCabecalho.setVersao(mRunMake.getVersao());
        eCabecalho.setCompanhia(mRunMake.getCompanhia());

        Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();

      //  SigmazC.setObject(mRunMake.getObject());
       // SigmazC.setPosProcess(mRunMake.getPosProcess());
      //  SigmazC.setStackProcess(mRunMake.getStackProcess());
      //  SigmazC.setAnalysisProcess(mRunMake.getAnalysisProcess());
        SigmazC.setMostrar_ArvoreFalhou(false);
        SigmazC.setCabecalho(eCabecalho);


        if (ASTCGlobal.mesmoNome("EXECUTABLE")) {


            AST AST_MODE = ASTCGlobal.getBranch("MODE");

            // System.out.println(" :: " + AST_MODE.getValor());

            if (AST_MODE.mesmoValor("UNIQUE")) {

                String TempSource = mRunMake.getSource() + ASTCGlobal.getBranch("UNIQUE").getNome();
                String TempBuild = mRunMake.getBuild() + ASTCGlobal.getValor();

                //  System.out.println(" SOURCE :: " + AST_MODE.getValor() + " -->> " + TempSource);
                //   System.out.println(" BUILD :: " + AST_MODE.getValor() + " -->> " + TempBuild);


                SigmazC.initSemExecucao(TempSource, TempBuild,mRunMake.getBuild(), 1,false);

            } else if (AST_MODE.mesmoValor("LIST")) {

                AST eLista = ASTCGlobal.getBranch("LIST");

                //   System.out.println(" SOURCE :: ");
                ArrayList<String> eArquivos = new ArrayList<String>();

                String TempBuild = mRunMake.getBuild() + ASTCGlobal.getValor();

                int eQuantidade = 0;

                for (AST eItem : eLista.getASTS()) {

                    String TempSource_Item = mRunMake.getSource() + eItem.getValor();

                    //   System.out.println("\t - " + AST_MODE.getValor() + " -->> " + TempSource_Item);
                    eArquivos.add(TempSource_Item);
                    eQuantidade += 1;

                }

                //System.out.println(" BUILD :: " + AST_MODE.getValor() + " -->> " + TempBuild);

                if (eQuantidade > 0) {
                    SigmazC.initSemExecucao(eArquivos, TempBuild,mRunMake.getBuild(), 1,false);
                } else {
                    mRunMake.errar(mRunMake.getLocal(), "Make executable source vazio ! ");
                }


            } else {

                mRunMake.errar(mRunMake.getLocal(), "Make comando desconhecido : " + AST_MODE.getValor());

            }
        } else if (ASTCGlobal.mesmoNome("LIBRARY")) {


            AST AST_MODE = ASTCGlobal.getBranch("MODE");

            // System.out.println(" :: " + AST_MODE.getValor());

            if (AST_MODE.mesmoValor("UNIQUE")) {

                String TempSource = mRunMake.getSource() + ASTCGlobal.getBranch("UNIQUE").getNome();
                String TempBuild = mRunMake.getBuild() + ASTCGlobal.getValor();

                //   System.out.println(" SOURCE :: " + AST_MODE.getValor() + " -->> " + TempSource);
                //   System.out.println(" BUILD :: " + AST_MODE.getValor() + " -->> " + TempBuild);


                SigmazC.initSemExecucao(TempSource, TempBuild, mRunMake.getBuild(),2,false);


            } else if (AST_MODE.mesmoValor("LIST")) {

                AST eLista = ASTCGlobal.getBranch("LIST");

                //  System.out.println(" SOURCE :: ");
                ArrayList<String> eArquivos = new ArrayList<String>();

                String TempBuild = mRunMake.getBuild() + ASTCGlobal.getValor();

                int eQuantidade = 0;

                for (AST eItem : eLista.getASTS()) {

                    String TempSource_Item = mRunMake.getSource() + eItem.getValor();

                    //  System.out.println("\t - " + AST_MODE.getValor() + " -->> " + TempSource_Item);
                    eArquivos.add(TempSource_Item);
                    eQuantidade += 1;

                }

                //  System.out.println(" BUILD :: " + AST_MODE.getValor() + " -->> " + TempBuild);

                if (eQuantidade > 0) {
                    SigmazC.initSemExecucao(eArquivos, TempBuild, mRunMake.getBuild(),2,false);
                } else {
                    mRunMake.errar(mRunMake.getLocal(), "Make executable source vazio ! ");
                }


            } else {

                mRunMake.errar(mRunMake.getLocal(), "Make comando desconhecido : " + AST_MODE.getValor());

            }


        } else {
            mRunMake.errar(mRunMake.getLocal(), "Comando Make Desconhecido : " + ASTCGlobal.getNome());
        }

    }
}
