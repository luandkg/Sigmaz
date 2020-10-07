package Make.Run;

import Sigmaz.S00_Utilitarios.AST;

public class Runner_Status {


    private RunMake mRunMake;

    public Runner_Status(RunMake eRunMake) {
        mRunMake = eRunMake;
    }

    public void init(AST ASTCGlobal) {

        String eConteudo = ASTCGlobal.getValor();

        if (eConteudo.contains("${SOURCE}")) {
            eConteudo=  eConteudo.replace("${SOURCE}",mRunMake.getSource());
        }

        if (eConteudo.contains("${BUILD}")) {
            eConteudo=   eConteudo.replace("${BUILD}",mRunMake.getBuild());
        }

        if (eConteudo.contains("${INTELLISENSE}")) {
            eConteudo=   eConteudo.replace("${INTELLISENSE}",mRunMake.getIntellisenses());
        }

        if (eConteudo.contains("${HIGH_LIGHT}")) {
            eConteudo=   eConteudo.replace("${HIGH_LIGHT}",mRunMake.getHighLights());
        }

        System.out.println(eConteudo);

    }
}
