package Sigmaz.Executor.Invokes;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Executor.Runners.Run_Invoke;
import Sigmaz.Utils.AST;

public class InvokeMath {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private Run_Invoke mRun_Invoke;

    public InvokeMath(RunTime eRunTime, Escopo eEscopo, Run_Invoke eRun_Invoke) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mRun_Invoke = eRun_Invoke;

    }

    public void init(String eAcao, String eSaida, AST ASTArgumentos) {


        int i = mRun_Invoke.argumentosContagem(ASTArgumentos);

        if (i == 1) {

            argumentos_1num(eAcao, eSaida, ASTArgumentos);

        } else if (i == 2) {
            argumentos_2num(eAcao, eSaida, ASTArgumentos);

        } else {

            mRunTime.getErros().add("Invocacao : Paramentros Invalidos ->  " + i);

        }

    }


    public void argumentos_1num(String eAcao, String eSaida, AST ASTArgumentos) {
        String mErro = "Invocacao : Ação inconsistente ->  " + eAcao;

        if (eAcao.contentEquals("operator_int_to_num")) {

            String p1 = mRun_Invoke.getInt(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                long f1 = Long.parseLong(p1);
                float f2 = (float) f1;

                mEscopo.setDefinido(eSaida, String.valueOf(f2));
            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }
        } else if (eAcao.contentEquals("operator_num_to_int")) {

            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                float f1 = Float.parseFloat(p1);
                long f2 = (long) f1;

                mEscopo.setDefinido(eSaida, String.valueOf(f2));
            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }

        } else if (eAcao.contentEquals("director_inverse_int")) {

            String p1 = mRun_Invoke.getInt(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                long f1 = Long.parseLong(p1) * (-1);

                mEscopo.setDefinido(eSaida, String.valueOf(f1));
            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }

        } else if (eAcao.contentEquals("director_inverse_num")) {

            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                double f1 = Float.parseFloat(p1) * (-1.0);

                mEscopo.setDefinido(eSaida, String.valueOf(f1));
            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }

        } else {

            mRunTime.getErros().add("Invocacao : Ação nao encontrada  ->  " + eAcao);

        }
    }


    public void argumentos_2num(String eAcao, String eSaida, AST ASTArgumentos) {

        String mErro = "Invocacao : Ação inconsistente ->  " + eAcao;

        if (eAcao.contentEquals("operator_sum_num")) {

            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);

                float f3 = f1 + f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));
            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }
        } else if (eAcao.contentEquals("operator_sum_int")) {

            String p1 = mRun_Invoke.getInt(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getInt(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                long f1 = Long.parseLong(p1);
                long f2 = Long.parseLong(p2);

                long f3 = f1 + f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));
            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }

        } else if (eAcao.contentEquals("operator_sub_num")) {

            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);

                float f3 = f1 - f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));
            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }
        } else if (eAcao.contentEquals("operator_sub_int")) {

            String p1 = mRun_Invoke.getInt(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getInt(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                long f1 = Long.parseLong(p1);
                long f2 = Long.parseLong(p2);

                long f3 = f1 - f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));
            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }

        } else if (eAcao.contentEquals("operator_mux_num")) {
            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);

                float f3 = f1 * f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));
            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }
        } else if (eAcao.contentEquals("operator_mux_int")) {
            String p1 = mRun_Invoke.getInt(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getInt(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                long f1 = Long.parseLong(p1);
                long f2 = Long.parseLong(p2);

                long f3 = f1 * f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));
            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }


        } else if (eAcao.contentEquals("operator_div_num")) {
            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);

                if (f2 == 0) {
                    mRunTime.getErros().add(mErro);
                } else {
                    float f3 = f1 / f2;
                    mEscopo.setDefinido(eSaida, String.valueOf(f3));
                }

            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }
        } else if (eAcao.contentEquals("operator_div_int")) {
            String p1 = mRun_Invoke.getInt(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getInt(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                long f1 = Long.parseLong(p1);
                long f2 = Long.parseLong(p2);

                if (f2 == 0) {
                    mRunTime.getErros().add(mErro);
                } else {
                    long f3 = f1 / f2;
                    mEscopo.setDefinido(eSaida, String.valueOf(f3));
                }

            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }
        } else if (eAcao.contentEquals("operator_mod_num")) {
            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);

                if (f2 == 0) {
                    mRunTime.getErros().add(mErro);
                } else {
                    float f3 = f1 % f2;
                    mEscopo.setDefinido(eSaida, String.valueOf(f3));
                }

            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }
        } else if (eAcao.contentEquals("operator_mod_int")) {
            String p1 = mRun_Invoke.getInt(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getInt(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                long f1 = Long.parseLong(p1);
                long f2 = Long.parseLong(p2);

                if (f2 == 0) {
                    mRunTime.getErros().add(mErro);
                } else {
                    long f3 = f1 % f2;
                    mEscopo.setDefinido(eSaida, String.valueOf(f3));
                }

            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }
        } else if (eAcao.contentEquals("operator_less_num")) {
            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);


                boolean f3 = f1 < f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));


            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }
        } else if (eAcao.contentEquals("operator_less_int")) {
            String p1 = mRun_Invoke.getInt(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getInt(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                long f1 = Long.parseLong(p1);
                long f2 = Long.parseLong(p2);


                boolean f3 = f1 < f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));


            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }
        } else if (eAcao.contentEquals("operator_great_num")) {
            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);


                boolean f3 = f1 > f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));


            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }
        } else if (eAcao.contentEquals("operator_great_int")) {
            String p1 = mRun_Invoke.getInt(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getInt(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                long f1 = Long.parseLong(p1);
                long f2 = Long.parseLong(p2);


                boolean f3 = f1 > f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));


            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }
        } else if (eAcao.contentEquals("operator_equal_num")) {
            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);


                boolean f3 = f1 == f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));


            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }
        } else if (eAcao.contentEquals("operator_equal_int")) {
            String p1 = mRun_Invoke.getInt(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getInt(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                long f1 = Long.parseLong(p1);
                long f2 = Long.parseLong(p2);


                boolean f3 = f1 == f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));



            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }
        } else if (eAcao.contentEquals("operator_not_num")) {
            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                float f1 = Float.parseFloat(p1);
                float f2 = Float.parseFloat(p2);


                boolean f3 = f1 != f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));


            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }
        } else if (eAcao.contentEquals("operator_not_int")) {
            String p1 = mRun_Invoke.getInt(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getInt(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            try {
                long f1 = Long.parseLong(p1);
                long f2 = Long.parseLong(p2);


                boolean f3 = f1 != f2;
                mEscopo.setDefinido(eSaida, String.valueOf(f3));


            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }

        } else if (eAcao.contentEquals("operator_random_num")) {
            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getNum(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }

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
                mRunTime.getErros().add(mErro);
            }
        } else if (eAcao.contentEquals("operator_random_int")) {
            String p1 = mRun_Invoke.getInt(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getInt(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }

            try {
                long f1 = Long.parseLong(p1);
                long f2 = Long.parseLong(p2);
                long f3 = 0;

                if (f1 == f2) {

                    f3 = f1;

                } else {

                    if (f1 > f2) {
                        long t = f1;
                        f1 = f2;
                        f2 = t;
                    }


                    long delta = f2 - f1;

                    f3 = f1 + (long) (Math.random() * delta);


                }


                mEscopo.setDefinido(eSaida, String.valueOf(f3));


            } catch (Exception e) {
                mRunTime.getErros().add(mErro);
            }
        } else if (eAcao.contentEquals("operator_match")) {

            String p1 = mRun_Invoke.getBool(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getBool(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }

            mEscopo.setDefinido(eSaida, "false");

            if (p1.contentEquals("true") && p2.contentEquals("true")) {
                mEscopo.setDefinido(eSaida, "true");
            } else if (p1.contentEquals("false") && p2.contentEquals("false")) {
                mEscopo.setDefinido(eSaida, "true");
            }



        } else if (eAcao.contentEquals("operator_unmatch")) {

            String p1 = mRun_Invoke.getBool(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String p2 = mRun_Invoke.getBool(ASTArgumentos, 2);
            if (mRunTime.getErros().size() > 0) {
                return;
            }

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
