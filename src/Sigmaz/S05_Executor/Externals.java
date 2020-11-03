package Sigmaz.S05_Executor;

import Sigmaz.S05_Executor.Escopos.Run_External;
import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class Externals {

    private RunTime mRunTime;
    private String mLocal;

    private ArrayList<Run_External> mExtern;

    private long mExternID;

    public Externals(RunTime eRunTime) {

        mLocal = "Externals";
        mRunTime = eRunTime;

        mExtern = new ArrayList<Run_External>();
        mExternID = 0;

    }

    public ArrayList<Run_External> getExterns() {
        return mExtern;
    }

    public Run_External adicionar(String ePacote, AST eExtern, AST ASTCGlobal) {

        Run_External mRE = new Run_External(mRunTime);
        mRE.init(ePacote, eExtern.getNome(), eExtern, ASTCGlobal);


      //  mRE.setID(mExternID);

        mExtern.add(mRE);

        mExternID += 1;

        return mRE;
    }

    public void limpar() {

        mExtern.clear();
        mExternID = 0;

    }
}
