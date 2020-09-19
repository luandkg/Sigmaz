package Sigmaz.Executor.Invokes;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Executor.Runners.Run_Invoke;

import java.util.Calendar;
import Sigmaz.Utils.AST;

public class InvokeUtils {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private Run_Invoke mRun_Invoke;


    public InvokeUtils(RunTime eRunTime, Escopo eEscopo, Run_Invoke eRun_Invoke) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mRun_Invoke = eRun_Invoke;


    }

    public void init(String eAcao, String eSaida, AST ASTArgumentos) {

        int i = mRun_Invoke.argumentosContagem(ASTArgumentos);


        if (i == 0) {

            argumentos_vazio(eAcao, eSaida, ASTArgumentos);

        } else if (i == 1) {

            argumentos_1(eAcao, eSaida, ASTArgumentos);


        } else if (i == 2) {

            argumentos_2(eAcao, eSaida, ASTArgumentos);

        } else {

            mRunTime.getErros().add("Invocacao : Acao nao encontrada ->  " + eAcao);

        }

    }


    public void argumentos_vazio(String eAcao, String eSaida, AST ASTArgumentos) {

        if (eAcao.contentEquals("Time_getHour")) {

            Calendar c = Calendar.getInstance();

            int dia = c.get(Calendar.DAY_OF_MONTH);
            int mes = c.get(Calendar.MONTH) + 1;
            int ano = c.get(Calendar.YEAR);

            int hora = c.get(Calendar.HOUR);
            int minutos = c.get(Calendar.MINUTE);
            int segundos = c.get(Calendar.SECOND);

            try {

                mEscopo.setDefinido(eSaida, String.valueOf(hora));
            } catch (Exception e) {
                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
            }

        } else if (eAcao.contentEquals("Time_getMinute")) {

            Calendar c = Calendar.getInstance();

            int dia = c.get(Calendar.DAY_OF_MONTH);
            int mes = c.get(Calendar.MONTH) + 1;
            int ano = c.get(Calendar.YEAR);

            int hora = c.get(Calendar.HOUR);
            int minutos = c.get(Calendar.MINUTE);
            int segundos = c.get(Calendar.SECOND);

            try {

                mEscopo.setDefinido(eSaida, String.valueOf(minutos));
            } catch (Exception e) {
                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
            }

        } else if (eAcao.contentEquals("Time_getSecond")) {

            Calendar c = Calendar.getInstance();

            int dia = c.get(Calendar.DAY_OF_MONTH);
            int mes = c.get(Calendar.MONTH) + 1;
            int ano = c.get(Calendar.YEAR);

            int hora = c.get(Calendar.HOUR);
            int minutos = c.get(Calendar.MINUTE);
            int segundos = c.get(Calendar.SECOND);

            try {

                mEscopo.setDefinido(eSaida, String.valueOf(segundos));
            } catch (Exception e) {
                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
            }

        } else if (eAcao.contentEquals("Date_getDay")) {

            Calendar c = Calendar.getInstance();

            int dia = c.get(Calendar.DAY_OF_MONTH);
            int mes = c.get(Calendar.MONTH) + 1;
            int ano = c.get(Calendar.YEAR);

            int hora = c.get(Calendar.HOUR);
            int minutos = c.get(Calendar.MINUTE);
            int segundos = c.get(Calendar.SECOND);

            try {

                mEscopo.setDefinido(eSaida, String.valueOf(dia));
            } catch (Exception e) {
                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
            }
        } else if (eAcao.contentEquals("Date_getMonth")) {

            Calendar c = Calendar.getInstance();

            int dia = c.get(Calendar.DAY_OF_MONTH);
            int mes = c.get(Calendar.MONTH) + 1;
            int ano = c.get(Calendar.YEAR);

            int hora = c.get(Calendar.HOUR);
            int minutos = c.get(Calendar.MINUTE);
            int segundos = c.get(Calendar.SECOND);

            try {

                mEscopo.setDefinido(eSaida, String.valueOf(mes));
            } catch (Exception e) {
                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
            }
        } else if (eAcao.contentEquals("Date_getYear")) {

            Calendar c = Calendar.getInstance();

            int dia = c.get(Calendar.DAY_OF_MONTH);
            int mes = c.get(Calendar.MONTH) + 1;
            int ano = c.get(Calendar.YEAR);

            int hora = c.get(Calendar.HOUR);
            int minutos = c.get(Calendar.MINUTE);
            int segundos = c.get(Calendar.SECOND);

            try {

                mEscopo.setDefinido(eSaida, String.valueOf(ano));
            } catch (Exception e) {
                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
            }
        } else {

            mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);

        }

    }


    public void argumentos_2(String eAcao, String eSaida, AST ASTArgumentos) {

        if (eAcao.contentEquals("string_append")) {

            String p1 = mRun_Invoke.getString(ASTArgumentos, 1);
            String p2 = mRun_Invoke.getString(ASTArgumentos, 2);

            String p3 = p1 + p2;

            try {
                mEscopo.setDefinido(eSaida, String.valueOf(p3));
            } catch (Exception e) {
                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
            }


        } else {

            mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);

        }

    }

    public void argumentos_1(String eAcao, String eSaida, AST ASTArgumentos) {

        if (eAcao.contentEquals("string_length")) {

            String p1 = mRun_Invoke.getString(ASTArgumentos, 1);

            int p3 = p1.length();

            try {
                mEscopo.setDefinido(eSaida, String.valueOf(p3));
            } catch (Exception e) {
                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
            }


        } else {

            mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);

        }

    }



}


