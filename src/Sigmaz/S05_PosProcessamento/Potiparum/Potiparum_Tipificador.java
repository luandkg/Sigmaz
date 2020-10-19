package Sigmaz.S05_PosProcessamento.Potiparum;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Mensageiro;
import Sigmaz.S05_PosProcessamento.Processadores.Tipador;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco;
import Sigmaz.S07_Executor.Debuggers.Simplificador;

import java.util.ArrayList;

public class Potiparum_Tipificador {

    private Simplificador mSimplificador;
    private Potiparum mPotiparum;

    public Potiparum_Tipificador(Potiparum ePotiparum) {

        mPotiparum = ePotiparum;
        mSimplificador = mPotiparum.getSimplificador();
    }

    public void index() {

    }

    public Simplificador getSimplificador() {
        return mSimplificador;
    }

    public Tipador getTipador() {
        return mPotiparum.getTipador();
    }


    public void status_GenericoArgumentoRetorno(String ePrefixo, String eNome, ArrayList<String> genericos_ok, ArrayList<String> argumentos_ok, boolean retorno_ok) {

        if (genericos_ok.size() > 0) {
            mPotiparum.mensagem(ePrefixo + eNome + " -->> TIPAGEM DE GENERICOS INVALIDA ");
            mPotiparum.errar(eNome + " -->> TIPAGEM DE GENERICOS INVALIDA ");
            for (String e : genericos_ok) {
                mPotiparum.mensagem(ePrefixo + "\t" + e);
                mPotiparum.errar(eNome + " -->> " + e);
            }
        }

        if (argumentos_ok.size() > 0) {
            mPotiparum.mensagem(ePrefixo + eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            mPotiparum.errar(eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            for (String e : argumentos_ok) {
                mPotiparum.mensagem(ePrefixo + "\t" + e);
                mPotiparum.errar(eNome + " -->> " + e);
            }
        }


        if (!retorno_ok) {
            mPotiparum.mensagem(ePrefixo + eNome + " -->> TIPAGEM DE RETORNO INVALIDA ");
            mPotiparum.errar(eNome + " -->> TIPAGEM DE RETORNO INVALIDA ");
        }

    }

    public void status_GenericoArgumento(String ePrefixo, String eNome, ArrayList<String> genericos_ok, ArrayList<String> argumentos_ok) {

        if (genericos_ok.size() > 0) {
            mPotiparum.mensagem(ePrefixo + eNome + " -->> TIPAGEM DE GENERICOS INVALIDA ");
            mPotiparum.errar(eNome + " -->> TIPAGEM DE GENERICOS INVALIDA ");
            for (String e : genericos_ok) {
                mPotiparum.mensagem(ePrefixo + "\t" + e);
                mPotiparum.errar(eNome + " -->> " + e);
            }
        }

        if (argumentos_ok.size() > 0) {
            mPotiparum.mensagem(ePrefixo + eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            mPotiparum.errar(eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            for (String e : argumentos_ok) {
                mPotiparum.mensagem(ePrefixo + "\t" + e);
                mPotiparum.errar(eNome + " -->> " + e);
            }
        }


    }

    public void status_Argumento(String ePrefixo, String eNome, ArrayList<String> argumentos_ok) {

        if (argumentos_ok.size() > 0) {
            mPotiparum.mensagem(ePrefixo + eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            mPotiparum.errar(eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            for (String e : argumentos_ok) {
                mPotiparum.mensagem(ePrefixo + "\t" + e);
                mPotiparum.errar(eNome + " -->> " + e);
            }
        }

    }

    public void status_ArgumentoRetorno(String ePrefixo, String eNome, ArrayList<String> argumentos_ok, boolean retorno_ok) {


        if (argumentos_ok.size() > 0) {
            mPotiparum.mensagem(ePrefixo + eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            mPotiparum.errar(eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            for (String e : argumentos_ok) {
                mPotiparum.mensagem(ePrefixo + "\t" + e);
                mPotiparum.errar(eNome + " -->> " + e);
            }
        }


        if (!retorno_ok) {
            mPotiparum.mensagem(ePrefixo + eNome + " -->> TIPAGEM DE RETORNO INVALIDA ");
            mPotiparum.errar(eNome + " -->> TIPAGEM DE RETORNO INVALIDA ");
        }

    }


    public ArrayList<String> conferirGenericos(AST mGenericos, Pronoco mEscopo) {

        ArrayList<String> gErros = new ArrayList<String>();

        int i = 0;
        int o = mGenericos.getASTS().size();

        ArrayList<String> mIncluir = new ArrayList<String>();

        for (AST oAST : mGenericos.getASTS()) {
            if (conferirTipo(oAST, mEscopo)) {
                gErros.add("Tipo generico " + oAST.getNome() + " : Nome invalido !");
            } else {
                i += 1;
                mIncluir.add(oAST.getNome());
            }
        }

        for (String oAST : mIncluir) {
            mEscopo.adicionarGenerico(oAST);
        }

        return gErros;
    }

    public ArrayList<String> conferirArgumentos_novo(AST mArgumetos, Pronoco mEscopo) {

        ArrayList<String> gErros = new ArrayList<String>();

        for (AST eArgumento : mArgumetos.getASTS()) {

            if (conferirTipo(eArgumento.getBranch("TYPE"), mEscopo)) {

            } else {
                gErros.add("Argumento Tipo " + eArgumento.getBranch("TYPE").getNome() + " : Deconhecido !");
                break;
            }

        }
        return gErros;
    }


    public boolean conferirTipo(AST mTipo, Pronoco mEscopo) {

        boolean ret = false;

        if (mTipo.mesmoValor("CONCRETE")) {

            if (mEscopo.getTipos_Primitivo().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Cast().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Stage().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Struct().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Type().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Model().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Genericos().contains(mTipo.getNome())) {
                ret = true;
            } else {
                ret = false;
            }
        } else if (mTipo.mesmoValor("GENERIC")) {


            if (mEscopo.getTipos_Primitivo().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Cast().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Stage().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Struct().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Type().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Model().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Genericos().contains(mTipo.getNome())) {
                ret = true;
            } else {
                ret = false;
            }

            for (AST eAST : mTipo.getASTS()) {

                if (eAST.mesmoTipo("TYPE")) {
                    ret = conferirTipo(eAST, mEscopo);
                    if (!ret) {
                        break;
                    }
                }
            }

        } else {
            ret = false;
        }

        return ret;
    }

}
