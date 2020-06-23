package Sigmaz.Executor.Invokes;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Executor.Runners.Run_Invoke;
import Sigmaz.Utils.AST;

public class InvokeMath {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private Run_Invoke mRun_Invoke;

    public InvokeMath(RunTime eRunTime, Escopo eEscopo,Run_Invoke eRun_Invoke) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mRun_Invoke=eRun_Invoke;

    }

    public void init(String eAcao, String eSaida, AST ASTArgumentos) {



        int i = mRun_Invoke.argumentosContagem(ASTArgumentos);

        if (i == 2) {

            argumentos_2num(eAcao, eSaida, ASTArgumentos);


        } else {

            mRunTime.getErros().add("Invocacao : Paramentros Invalidos ->  " + i);

        }

    }


    public String getP1(AST ASTArgumentos) {
        return mRun_Invoke.getQualquer(ASTArgumentos, 1);
    }

    public String getP2(AST ASTArgumentos) {
        return mRun_Invoke.getQualquer(ASTArgumentos, 2);
    }



    public void argumentos_2num(String eAcao, String eSaida, AST ASTArgumentos) {



        if (eAcao.contentEquals("operator_sum")) {

            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);

            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);

                float f3 = f1 + f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));
            } catch (Exception e) {
                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
            }

        } else if (eAcao.contentEquals("operator_sub")) {

            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);
            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);

                float f3 = f1 - f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));
            } catch (Exception e) {
                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
            }


        } else if (eAcao.contentEquals("operator_mux")) {
            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);
            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);

                float f3 = f1 * f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));
            } catch (Exception e) {
                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
            }


        } else if (eAcao.contentEquals("operator_div")) {
            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);
            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);

                if (f2 == 0) {
                    mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
                } else {
                    float f3 = f1 / f2;
                    mEscopo.setDefinido(eSaida, String.valueOf(f3));
                }

            } catch (Exception e) {
                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
            }
        } else if (eAcao.contentEquals("operator_trash")) {
            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);
            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);

                if (f2 == 0) {
                    mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
                } else {
                    float f3 = f1 % f2;
                    mEscopo.setDefinido(eSaida, String.valueOf(f3));
                }

            } catch (Exception e) {
                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
            }

        } else if (eAcao.contentEquals("operator_less")) {
            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);
            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);


                boolean f3 = f1 < f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));


            } catch (Exception e) {
                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
            }
        } else if (eAcao.contentEquals("operator_great")) {
            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);
            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);


                boolean f3 = f1 > f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));


            } catch (Exception e) {
                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
            }
        } else if (eAcao.contentEquals("operator_equal")) {
            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);
            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);


                boolean f3 = f1 == f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));


            } catch (Exception e) {
                mRunTime.getErros().add("P1 ->  " + p1);
                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
            }
        } else if (eAcao.contentEquals("operator_not")) {
            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);
            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);


                boolean f3 = f1 != f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));


            } catch (Exception e) {
                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
            }
        } else if (eAcao.contentEquals("operator_random")) {
            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);

            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);
                float f3 = 0;

                if (f1 == f2) {

                    f3 = f1;

                } else {

                    if (f1 > f2) {
                        float t = f1;
                        f1 = f2;
                        f2 = t;
                    }


                    float delta = f2 - f1;

                    f3 = f1 + (float) (Math.random() * delta);


                }


                mEscopo.setDefinido(eSaida, String.valueOf(f3));


            } catch (Exception e) {
                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
            }
        } else if (eAcao.contentEquals("operator_match")) {

            String p1 = mRun_Invoke.getBool(ASTArgumentos, 1);
            String p2 = mRun_Invoke.getBool(ASTArgumentos, 2);

            if (p1.contentEquals("true") || p1.contentEquals("false")) {
                if (p2.contentEquals("true") || p2.contentEquals("false")) {

                    if (p1.contentEquals(p2)) {
                        mEscopo.setDefinido(eSaida, "true");
                    } else {
                        mEscopo.setDefinido(eSaida, "false");
                    }

                } else {
                    mRunTime.getErros().add("Invocacao : Ação nao encontrada  ->  " + eAcao);
                }
            } else {
                mRunTime.getErros().add("Invocacao : Ação nao encontrada  ->  " + eAcao);
            }
        } else if (eAcao.contentEquals("operator_unmatch")) {

            String p1 = mRun_Invoke.getBool(ASTArgumentos, 1);
            String p2 = mRun_Invoke.getBool(ASTArgumentos, 2);

            if (p1.contentEquals("true") || p1.contentEquals("false")) {
                if (p2.contentEquals("true") || p2.contentEquals("false")) {

                    if (p1.contentEquals(p2)) {
                        mEscopo.setDefinido(eSaida, "false");
                    } else {
                        mEscopo.setDefinido(eSaida, "true");
                    }

                } else {
                    mRunTime.getErros().add("Invocacao : Ação nao encontrada  ->  " + eAcao);
                }
            } else {
                mRunTime.getErros().add("Invocacao : Ação nao encontrada  ->  " + eAcao);
            }

        } else {

            mRunTime.getErros().add("Invocacao : Ação nao encontrada  ->  " + eAcao);


        }


    }

}
