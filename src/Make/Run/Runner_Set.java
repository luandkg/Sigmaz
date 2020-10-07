package Make.Run;

import Sigmaz.S00_Utilitarios.AST;

public class Runner_Set {


    private RunMake mRunMake;

    public Runner_Set(RunMake eRunMake) {
        mRunMake = eRunMake;
    }

    public void init(AST ASTCGlobal) {

        if (ASTCGlobal.mesmoNome("author")) {

            mRunMake.autor_adicionar(ASTCGlobal.getValor());

        } else         if (ASTCGlobal.mesmoNome("version")) {

            mRunMake.setVersao(ASTCGlobal.getValor());
        } else         if (ASTCGlobal.mesmoNome("company")) {

            mRunMake.setCompanhia(ASTCGlobal.getValor());


        }else{
            mRunMake.errar(mRunMake.getLocal(),"SET deconhecido : " + ASTCGlobal.getNome());
        }

    }
}
