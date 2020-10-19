package Sigmaz.S05_PosProcessamento.Potiparum;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Mensageiro;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco;
import Sigmaz.S07_Executor.Debuggers.Simplificador;

import java.util.ArrayList;

public class Potiparum_Sigmaz {

    private Simplificador mSimplificador;

    private  Potiparum mPotiparum;
    private  Potiparum_Tipificador mPotiparum_Tipificador;
    private Potiparum_Escopos mPotiparum_Escopos;
    private Potiparum_Sigmaz mPotiparum_Sigmaz;

    public Potiparum_Sigmaz(Potiparum ePotiparum ) {

        mPotiparum = ePotiparum;
        mSimplificador = mPotiparum.getSimplificador();


    }

    public void index(){

        mPotiparum_Tipificador=mPotiparum.getPotiparum_Tipificador();
        mPotiparum_Escopos = mPotiparum.getPotiparum_Escopos();
        mPotiparum_Sigmaz = mPotiparum.getPotiparum_Sigmaz();

    }

    public Simplificador getSimplificador() {
        return mSimplificador;
    }



    public Potiparum_Tipificador getPotiparum_Tipificador() {
        return mPotiparum_Tipificador;
    }

    public Potiparum_Sigmaz getPotiparum_Sigmaz() {
        return mPotiparum_Sigmaz;
    }

    public Potiparum_Escopos getPotiparum_Escopos() {
        return mPotiparum_Escopos;
    }


    public void alocacao_Define(String ePrefixo, AST mAST, Pronoco mEscopo) {


        String eNome = mSimplificador.getDefine(mAST);

        Pronoco mNovoEscopo = new Pronoco(eNome);
        mNovoEscopo.setSuperior(mEscopo);

        boolean retorno_ok = getPotiparum_Tipificador().conferirTipo(mAST.getBranch("TYPE"), mEscopo);

        if (retorno_ok) {

           mPotiparum.  mensagem(ePrefixo + "DEFINE " + eNome + " -->> OK ");

        } else {

            String mErro = "DEFINE " + eNome + " -->> TIPAGEM INVALIDA ";

           mPotiparum. mensagem(ePrefixo + mErro);
           mPotiparum. errar(mErro);
        }


    }

    public void alocacao_Mockiz(String ePrefixo, AST mAST, Pronoco mEscopo) {


        String eNome = mSimplificador.getDefine(mAST);

        Pronoco mNovoEscopo = new Pronoco(eNome);
        mNovoEscopo.setSuperior(mEscopo);

        boolean retorno_ok = getPotiparum_Tipificador().conferirTipo(mAST.getBranch("TYPE"), mEscopo);

        if (retorno_ok) {

           mPotiparum.  mensagem(ePrefixo + "MOCKIZ " + eNome + " -->> OK ");

        } else {

            String mErro = "MOCKIZ " + eNome + " -->> TIPAGEM INVALIDA ";

           mPotiparum.  mensagem(ePrefixo + mErro);
           mPotiparum. errar(mErro);
        }


    }


    public void alocacao_Call(String ePrefixo, AST mAST, Pronoco mEscopo) {

        String eNome = "CALL " + mSimplificador.getCall(mAST);

        Pronoco mNovoEscopo = new Pronoco(eNome);
        mNovoEscopo.setSuperior(mEscopo);

        mPotiparum.mensagem(ePrefixo + eNome + " -->> OK ");
        if (mAST.mesmoValor("AUTO")) {
            getPotiparum_Escopos().conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), mNovoEscopo);
        }


    }

    public void alocacao_Action(String ePrefixo, AST mAST, Pronoco mEscopo) {

        String eNome = "ACTION " + mSimplificador.getAction(mAST);

        Pronoco mNovoEscopo = new Pronoco(eNome);
        mNovoEscopo.setSuperior(mEscopo);


        ArrayList<String> argumentos_ok = getPotiparum_Tipificador().conferirArgumentos_novo(mAST.getBranch("ARGUMENTS"), mNovoEscopo);


        if (argumentos_ok.size() == 0) {

            mPotiparum.mensagem(ePrefixo + eNome + " -->> OK ");

            getPotiparum_Escopos().conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), mNovoEscopo);

        } else {

            getPotiparum_Tipificador().status_Argumento(ePrefixo, eNome, argumentos_ok);

        }


    }

    public void alocacao_Auto(String ePrefixo, AST mAST, Pronoco mEscopo) {

        String eNome = "AUTO " + mSimplificador.getAction(mAST);

        Pronoco mNovoEscopo = new Pronoco(eNome);
        mNovoEscopo.setSuperior(mEscopo);


        ArrayList<String> genericos_ok = getPotiparum_Tipificador().conferirGenericos(mAST.getBranch("GENERICS"), mNovoEscopo);
        ArrayList<String> argumentos_ok = getPotiparum_Tipificador().conferirArgumentos_novo(mAST.getBranch("ARGUMENTS"), mNovoEscopo);


        if (genericos_ok.size() == 0 && argumentos_ok.size() == 0) {

            mPotiparum.mensagem(ePrefixo + eNome + " -->> OK ");

            getPotiparum_Escopos().conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), mNovoEscopo);

        } else {

            getPotiparum_Tipificador().status_GenericoArgumento(ePrefixo, eNome, genericos_ok, argumentos_ok);

        }

    }

    public void alocacao_Function(String ePrefixo, AST mAST, Pronoco mEscopo) {

        String eNome = "FUNCTION " + mSimplificador.getFuction(mAST);

        Pronoco mNovoEscopo = new Pronoco(eNome);
        mNovoEscopo.setSuperior(mEscopo);


        ArrayList<String> argumentos_ok = getPotiparum_Tipificador().conferirArgumentos_novo(mAST.getBranch("ARGUMENTS"), mNovoEscopo);
        boolean retorno_ok = getPotiparum_Tipificador().conferirTipo(mAST.getBranch("TYPE"), mNovoEscopo);

        if (argumentos_ok.size() == 0 && retorno_ok) {

            mPotiparum.mensagem(ePrefixo + eNome + " -->> OK ");

            getPotiparum_Escopos().conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), mNovoEscopo);

        } else {

            getPotiparum_Tipificador().status_ArgumentoRetorno(ePrefixo, eNome, argumentos_ok, retorno_ok);

        }


    }

    public void alocacao_Functor(String ePrefixo, AST mAST, Pronoco mEscopo) {

        String eNome = "FUNCTOR " + mSimplificador.getFunctor(mAST);

        Pronoco mNovoEscopo = new Pronoco(eNome);
        mNovoEscopo.setSuperior(mEscopo);


        ArrayList<String> genericos_ok = getPotiparum_Tipificador().conferirGenericos(mAST.getBranch("GENERICS"), mNovoEscopo);
        ArrayList<String> argumentos_ok = getPotiparum_Tipificador().conferirArgumentos_novo(mAST.getBranch("ARGUMENTS"), mNovoEscopo);

        boolean retorno_ok = getPotiparum_Tipificador().conferirTipo(mAST.getBranch("TYPE"), mNovoEscopo);

        if (genericos_ok.size() == 0 && argumentos_ok.size() == 0 && retorno_ok) {

            mPotiparum.mensagem(ePrefixo + eNome + " -->> OK ");

            getPotiparum_Escopos().conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), mNovoEscopo);

        } else {

            getPotiparum_Tipificador().status_GenericoArgumentoRetorno(ePrefixo, eNome, genericos_ok, argumentos_ok, retorno_ok);

        }


    }

    public void alocacao_Director(String ePrefixo, AST mAST, Pronoco mEscopo) {

        String eNome = "DIRECTOR " + mSimplificador.getDirector(mAST);

        Pronoco mNovoEscopo = new Pronoco(eNome);
        mNovoEscopo.setSuperior(mEscopo);


        ArrayList<String> argumentos_ok = getPotiparum_Tipificador().conferirArgumentos_novo(mAST.getBranch("ARGUMENTS"), mNovoEscopo);
        boolean retorno_ok = getPotiparum_Tipificador().conferirTipo(mAST.getBranch("TYPE"), mNovoEscopo);

        if (argumentos_ok.size() == 0 && retorno_ok) {

            mPotiparum.mensagem(ePrefixo + eNome + " -->> OK ");

            getPotiparum_Escopos().conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), mNovoEscopo);

        } else {

            getPotiparum_Tipificador().status_ArgumentoRetorno(ePrefixo, eNome, argumentos_ok, retorno_ok);

        }

    }

    public void alocacao_Operator(String ePrefixo, AST mAST, Pronoco mEscopo) {

        String eNome = "OPERATOR " + mSimplificador.getOperator(mAST);

        Pronoco mNovoEscopo = new Pronoco(eNome);
        mNovoEscopo.setSuperior(mEscopo);


        ArrayList<String> argumentos_ok = getPotiparum_Tipificador().conferirArgumentos_novo(mAST.getBranch("ARGUMENTS"), mNovoEscopo);
        boolean retorno_ok = getPotiparum_Tipificador().conferirTipo(mAST.getBranch("TYPE"), mNovoEscopo);

        if (argumentos_ok.size() == 0 && retorno_ok) {

            mPotiparum.mensagem(ePrefixo + eNome + " -->> OK ");

            getPotiparum_Escopos().conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), mNovoEscopo);

        } else {

            getPotiparum_Tipificador().status_ArgumentoRetorno(ePrefixo, eNome, argumentos_ok, retorno_ok);

        }


    }



}
