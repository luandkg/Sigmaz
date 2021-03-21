package Make.Run;

import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S05_Executor.RunTime;

import java.util.ArrayList;

public class Runner_AST {


    private RunMake mRunMake;

    public Runner_AST(RunMake eRunMake) {
        mRunMake = eRunMake;
    }

    public void init(AST ASTCGlobal) {


        String TempBuild = mRunMake.getBuild() + ASTCGlobal.getValor();

        RunTime mDoc = new RunTime();
        mDoc.init(TempBuild,false);

        ArrayList<AST> mASTS = mDoc.getASTS();

        for (AST eAST : mASTS) {
            System.out.println(eAST.ImprimirArvoreDeInstrucoes());
        }

    }
}
