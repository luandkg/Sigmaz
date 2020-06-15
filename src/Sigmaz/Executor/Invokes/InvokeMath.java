package Sigmaz.Executor.Invokes;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class InvokeMath {

    private RunTime mRunTime;
    private Escopo mEscopo;


    public InvokeMath(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }

    public void init(String eAcao, String eSaida, AST ASTArgumentos) {



        if (eAcao.contentEquals("operator_sum")) {

            argumentos_2num(eAcao, eSaida, ASTArgumentos);
        } else   if (eAcao.contentEquals("operator_sub")) {

            argumentos_2num(eAcao,eSaida, ASTArgumentos);
        } else   if (eAcao.contentEquals("operator_mux")) {

            argumentos_2num(eAcao,eSaida, ASTArgumentos);

        } else   if (eAcao.contentEquals("operator_div")) {

            argumentos_2num(eAcao,eSaida, ASTArgumentos);

        } else {

            mRunTime.getErros().add("Invocacao : Acao nao encontrada ->  " + eAcao);

        }

    }

    public void argumentos_2num(String eAcao,String eSaida, AST ASTArgumentos) {


        int i = 0;

        String p1 = "";
        String p2 = "";

        for (AST eAST : ASTArgumentos.getASTS()) {

            if (eAST.mesmoTipo("ARGUMENT")) {

                //System.out.println(" \t - Argumento : " + eAST.getNome() + " : " + eAST.getValor());


                if (eAST.mesmoValor("ID")) {

                    String eRet = mEscopo.getDefinidoNum(eAST.getNome());

                    if (i == 0) {
                        p1 =eRet;
                    } else {
                        p2 = eRet;
                    }


                } else if (eAST.mesmoValor("Num")) {
                    if (i == 0) {
                        p1 = eAST.getNome();
                    } else {
                        p2 = eAST.getNome();
                    }
                } else if (eAST.mesmoValor("Text")) {

                    mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);

                    break;
                }

                i += 1;

            }

        }

        if (i == 2) {

            if(eAcao.contentEquals("operator_sum")){

                try {
                    float f1 = Float.parseFloat(p1);
                    float f2 = Float.parseFloat(p2);

                    float f3 = f1+f2;
                    mEscopo.setDefinido(eSaida,String.valueOf(f3));
                }
                catch (Exception e) {
                    mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
                }

            } else  if(eAcao.contentEquals("operator_sub")){


                try {
                    float f1 = Float.parseFloat(p1);
                    float f2 = Float.parseFloat(p2);

                    float f3 = f1-f2;
                    mEscopo.setDefinido(eSaida,String.valueOf(f3));
                }
                catch (Exception e) {
                    mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
                }


            } else  if(eAcao.contentEquals("operator_mux")){

                try {
                    float f1 = Float.parseFloat(p1);
                    float f2 = Float.parseFloat(p2);

                    float f3 = f1*f2;
                    mEscopo.setDefinido(eSaida,String.valueOf(f3));
                }
                catch (Exception e) {
                    mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
                }


            } else  if(eAcao.contentEquals("operator_div")){

                try {
                    float f1 = Float.parseFloat(p1);
                    float f2 = Float.parseFloat(p2);

                    if(f2==0){
                        mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
                    }else{
                        float f3 = f1/f2;
                        mEscopo.setDefinido(eSaida,String.valueOf(f3));
                    }

                }
                catch (Exception e) {
                    mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
                }


            }


        } else {

            mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);

        }

    }

}
