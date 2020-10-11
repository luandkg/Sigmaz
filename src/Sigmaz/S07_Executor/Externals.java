package Sigmaz.S07_Executor;

import Sigmaz.S07_Executor.Escopos.Run_Explicit;
import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class Externals {

    private RunTime mRunTime;
    private String mLocal;

    private ArrayList<Run_Explicit> mExtern;

    private long mExternID;

    public Externals(RunTime eRunTime) {

        mLocal = "Externals";
        mRunTime = eRunTime;

        mExtern = new ArrayList<Run_Explicit>();
        mExternID = 0;

    }

    public ArrayList<Run_Explicit> getExterns() {
        return mExtern;
    }

    public Run_Explicit adicionar(String ePacote, AST eExtern, AST ASTCGlobal) {

        Run_Explicit mRE = new Run_Explicit(mRunTime);
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
