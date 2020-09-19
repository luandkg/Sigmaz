package Make.Run;

import Sigmaz.Sigmaz;
import Sigmaz.Utils.AST;
import Sigmaz.Utils.Tempo;

import java.util.ArrayList;

public class Run {

    private AST mAST;
    private ArrayList<String> mErros;
    private String mLocal;

    public Run() {
        mAST = null;
        mErros = new ArrayList<>();
        mLocal = "";

    }

    public void init(AST eAST) {
        mAST = eAST;

        limpar();

    }


    public void run(String eLocal) {
        mLocal = eLocal;

        String eBuild = "";
        String eSource = "";

        for (AST ASTCGlobal : mAST.getASTS()) {


            if (ASTCGlobal.mesmoTipo("STATUS")) {

                System.out.println(ASTCGlobal.getValor());
            } else if (ASTCGlobal.mesmoTipo("BUILD")) {

                eBuild = ASTCGlobal.getValor();

                System.out.println(" -->> BUILD :: " + eBuild);

            } else if (ASTCGlobal.mesmoTipo("SOURCE")) {

                eSource = ASTCGlobal.getValor();

                System.out.println(" -->> SOURCE :: " + eSource);

            } else if (ASTCGlobal.mesmoTipo("MAKE")) {

                String TempSource = eSource;
                String TempBuild = eBuild;

                if (ASTCGlobal.mesmoNome("EXECUTABLE")) {


                    AST AST_MODE = ASTCGlobal.getBranch("MODE");

                    // System.out.println(" :: " + AST_MODE.getValor());

                    if (AST_MODE.mesmoValor("UNIQUE")) {

                        TempSource = TempSource + ASTCGlobal.getBranch("UNIQUE").getNome();
                        TempBuild = TempBuild + ASTCGlobal.getValor();

                        System.out.println(" SOURCE :: " + AST_MODE.getValor() + " -->> " + TempSource);
                        System.out.println(" BUILD :: " + AST_MODE.getValor() + " -->> " + TempBuild);

                        Sigmaz SigmazC = new Sigmaz();

                        SigmazC.geral(TempSource, TempBuild, false, 1);


                    } else {

                        errar(eLocal, "Implementacao Futura : " + ASTCGlobal.getTipo() + " com " + AST_MODE.getValor());

                    }
                } else      if (ASTCGlobal.mesmoNome("LIBRARY")) {


                    AST AST_MODE = ASTCGlobal.getBranch("MODE");

                    // System.out.println(" :: " + AST_MODE.getValor());

                    if (AST_MODE.mesmoValor("UNIQUE")) {

                        TempSource = TempSource + ASTCGlobal.getBranch("UNIQUE").getNome();
                        TempBuild=TempBuild + ASTCGlobal.getValor();

                        System.out.println(" SOURCE :: " + AST_MODE.getValor() + " -->> " + TempSource);
                        System.out.println(" BUILD :: " + AST_MODE.getValor() + " -->> " + TempBuild);

                        Sigmaz SigmazC = new Sigmaz();

                        SigmazC.geral(TempSource, TempBuild, true,2);


                    } else {

                        errar(eLocal, "Implementacao Futura : " + ASTCGlobal.getTipo() + " com " + AST_MODE.getValor());

                    }


                } else {
                    errar(eLocal, "Comando Make Desconhecido : " + ASTCGlobal.getNome());
                }

            } else {
                errar(eLocal, "Comando Desconhecido : " + ASTCGlobal.getTipo());
            }

        }


    }


    public void limpar() {
        mErros.clear();
    }

    public int getInstrucoes() {

        int ret = 0;

        ret += mAST.getInstrucoes();


        return ret;
    }

    public String getData() {

        return Tempo.getData();


    }

    public void errar(String eLocal, String eMensagem) {
        getErros().add(eLocal + " -->> " + eMensagem);
    }

    public ArrayList<String> getErros() {
        return mErros;
    }

}
