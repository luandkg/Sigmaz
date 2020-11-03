package Sigmaz.S05_Executor.Indexador;

import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.RunTime;
import Sigmaz.S05_Executor.Runners.Run_GetType;

import java.util.ArrayList;
import Sigmaz.S00_Utilitarios.AST;

public class Tipificador {



    public void argumentar(RunTime eRunTime, Escopo mEscopo,AST eArg, ArrayList<String> dRefers,ArrayList<String> mNomeArgumentos, ArrayList<String> mTipoArgumentos,ArrayList<String> mModoArgumentos) {

        mNomeArgumentos.add(eArg.getNome());
        mModoArgumentos.add(eArg.getValor());

        Run_GetType mRun_GetType = new Run_GetType(eRunTime,mEscopo,dRefers);

       // String antes =mRun_GetType.getTipagemBruta(eArg.getBranch("TYPE"));
        String mTipagem = mRun_GetType.getTipagem(eArg.getBranch("TYPE"));

     //   System.out.println("Tipando : " + antes + " -->> " + mTipagem);

        mTipoArgumentos.add(mTipagem);

    }


    public String getTipagem(AST eAST){

        String mTipagem = eAST.getNome();

        if (eAST.mesmoValor("GENERIC")){

            for (AST eTipando : eAST.getASTS()) {
                mTipagem += "<" +getTipagem(eTipando) + ">";
            }

        }


        return mTipagem;

    }


}
