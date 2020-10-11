package Sigmaz.S07_Executor.Runners;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.RunTime;
import Sigmaz.S00_Utilitarios.AST;

public class Run_Alocadores {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public Run_Alocadores(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;

        mLocal = "Run_Alocadores";

    }

    public void init(AST ASTCorrente) {

        for ( AST ASTC : ASTCorrente.getASTS()) {

            if (mRunTime.getErros().size() > 0) {
                return;
            }

            if (ASTC.mesmoTipo("DEFINE")) {

                Run_Def mAST = new Run_Def(mRunTime, mEscopo);
                mAST.init(ASTC);


            } else if (ASTC.mesmoTipo("MOCKIZ")) {


                Run_Moc mAST = new Run_Moc(mRunTime, mEscopo);
                mAST.init(ASTC);

            } else if (ASTC.mesmoTipo("INVOKE")) {

                Run_Invoke mAST = new Run_Invoke(mRunTime, mEscopo);
                mAST.init(ASTC);

            }

        }


    }
}
