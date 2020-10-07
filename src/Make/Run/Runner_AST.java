package Make.Run;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Documentador;

import java.util.ArrayList;

public class Runner_AST {


    private RunMake mRunMake;

    public Runner_AST(RunMake eRunMake) {
        mRunMake = eRunMake;
    }

    public void init(AST ASTCGlobal) {


        String TempBuild = mRunMake.getBuild() + ASTCGlobal.getValor();

        Documentador mDoc = new Documentador();
        ArrayList<AST> mASTS = mDoc.Decompilar(TempBuild);

        for (AST eAST : mASTS) {
            System.out.println(eAST.ImprimirArvoreDeInstrucoes());
        }

    }
}
