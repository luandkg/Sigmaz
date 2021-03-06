package Make.Run;

import Sigmaz.S07_Apps.SigmazTestes;
import Sigmaz.S08_Utilitarios.AST;

public class Runner_Tests {

    private RunMake mRunMake;

    public Runner_Tests(RunMake eRunMake) {
        mRunMake = eRunMake;
    }

    public void init(AST ASTCGlobal) {

        SigmazTestes mSigmazTestes = new SigmazTestes();

        String TempBuild = mRunMake.getBuild() + ASTCGlobal.getValor();
        mSigmazTestes.setSaida(TempBuild);

        for (AST eAST : ASTCGlobal.getBranch("LIST").getASTS()) {

            String TempSource = mRunMake.getSource() + eAST.getValor();

            mSigmazTestes.adicionar(TempSource);

        }


        System.out.println("");

        mSigmazTestes.init(mRunMake.getLocal(),ASTCGlobal.getNome());


    }
}
