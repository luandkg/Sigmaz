package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_Anonymous {


    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public Run_Anonymous(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }

    public void criarAuto(String eNome, Run_Value mAST, AST mValor) {

        if (mValor.mesmoValor("AUTO")) {
            //System.out.println("AUTO -->> " + eAST.getNome());

            AST eAcao = mValor.copiar();
            eAcao.setTipo("ACTION");
            eAcao.setNome(eNome);

            String anonimoID = mEscopo.getAnonimoAuto();
            mAST.setConteudo(anonimoID);
            eAcao.setValor(anonimoID);

          //  System.out.println(eNome + " -->> CRIAR AUTO : " + anonimoID);

            mEscopo.guardar(eAcao);
        } else {

            String eAntigo = mAST.getConteudo();

            if (mAST.getIsNulo()){

            }else{

                AST eAuto = mEscopo.obterAuto(mAST.getConteudo());
                String anonimoID = mEscopo.getAnonimoAuto();
                mAST.setConteudo(anonimoID);

                eAuto.setValor(anonimoID);
                eAuto.setNome(eNome);

                mEscopo.guardar(eAuto);

               // System.out.println(eNome + " -->> REF AUTO : " + anonimoID);

            }

        //    mEscopo.removerAuto(eAntigo);


        }

    }

    public void criarFunctor(String eNome, Run_Value mAST, AST mValor) {

        if (mValor.mesmoValor("FUNCTOR")) {
            //System.out.println("AUTO -->> " + eAST.getNome());


            String anonimoID = mEscopo.getAnonimoFunctor();


            AST eAcao = mValor.copiar();
            eAcao.setTipo("FUNCTION");
            eAcao.setNome(eNome);
            eAcao.setValor(anonimoID);

            mAST.setConteudo(anonimoID);

           // System.out.println(eNome + " -->> CRIAR FUNCTOR : " + anonimoID);

            mEscopo.guardar(eAcao);



        } else {

            if (mAST.getIsNulo()){


            }else{

                String anonimoID = mEscopo.getAnonimoFunctor();

                AST eAuto = mEscopo.obterFunctor(mAST.getConteudo());
                eAuto.setValor(anonimoID);
                eAuto.setNome(eNome);

                mAST.setConteudo(anonimoID);

                mEscopo.guardar(eAuto);

              //  System.out.println(eNome + " -->> REF FUNCTOR : " + anonimoID);

            }





        }

    }


}
