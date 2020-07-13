package Sigmaz.Executor.Indexador;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Executor.Runners.Run_GetType;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Tipificador {



    public void argumentar(RunTime eRunTime, Escopo mEscopo,AST eArg, ArrayList<String> dRefers,ArrayList<String> mNomeArgumentos, ArrayList<String> mTipoArgumentos,ArrayList<String> mModoArgumentos) {

        mNomeArgumentos.add(eArg.getNome());
        mModoArgumentos.add(eArg.getValor());

        Run_GetType mRun_GetType = new Run_GetType(eRunTime,mEscopo,dRefers);

        String antes =mRun_GetType.getTipagemBruta(eArg.getBranch("TYPE"));
        String mTipagem = mRun_GetType.getTipagem(eArg.getBranch("TYPE"));

     //   System.out.println("Tipando : " + antes + " -->> " + mTipagem);

        mTipoArgumentos.add(mTipagem);

    }

}
