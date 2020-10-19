package Sigmaz.S05_PosProcessamento.Potiparum;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Utilitario;
import Sigmaz.S00_Utilitarios.Mensageiro;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco;
import Sigmaz.S07_Executor.Debuggers.Simplificador;

public class Potiparum_Escopos {

    private Simplificador mSimplificador;

    private Potiparum mPotiparum;
    private Potiparum_Tipificador mPotiparum_Tipificador;
    private Potiparum_Sigmaz mPotiparum_Sigmaz;

    public Potiparum_Escopos(Potiparum ePotiparum ) {

        mPotiparum = ePotiparum;
        mSimplificador = mPotiparum.getSimplificador();


    }

    public void index(){

        mPotiparum_Tipificador = mPotiparum.getPotiparum_Tipificador();
        mPotiparum_Sigmaz=mPotiparum.getPotiparum_Sigmaz();

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

    public void conferirCorpo(String ePrefixo, AST mCorpo, Pronoco mEscopo) {

        Utilitario mUtilitario = new Utilitario();

        for (AST mAST : mCorpo.getASTS()) {

            if (mAST.mesmoTipo("MOC")) {

                boolean retorno_ok = getPotiparum_Tipificador().conferirTipo(mAST.getBranch("TYPE"), mEscopo);

                if (retorno_ok) {
                    mPotiparum.mensagem(ePrefixo + "MOC " + mUtilitario.getDefine_Definicao(mAST) + " -->> OK ");
                } else {
                    mPotiparum.mensagem(ePrefixo + "MOC " + mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                    mPotiparum.errar("MOC " + mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                }

            } else if (mAST.mesmoTipo("DEF")) {

                boolean retorno_ok = getPotiparum_Tipificador().conferirTipo(mAST.getBranch("TYPE"), mEscopo);

                if (retorno_ok) {
                    mPotiparum.mensagem(ePrefixo + "DEF " + mUtilitario.getDefine_Definicao(mAST) + " -->> OK ");
                } else {
                    mPotiparum.mensagem(ePrefixo + "DEF " + mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                    mPotiparum.errar("DEF " + mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                }

            } else if (mAST.mesmoTipo("IF")) {

                conferirCorpo(ePrefixo, mAST.getBranch("BODY"), mEscopo);

                for (AST mSubIF : mAST.getASTS()) {
                    if (mSubIF.mesmoTipo("OTHER")) {

                        conferirCorpo(ePrefixo, mSubIF.getBranch("BODY"), mEscopo);

                    } else if (mSubIF.mesmoTipo("OTHERS")) {

                        conferirCorpo(ePrefixo, mSubIF, mEscopo);

                    }

                }


            } else if (mAST.mesmoTipo("WHILE")) {

                conferirCorpo(ePrefixo, mAST.getBranch("BODY"), mEscopo);

            } else if (mAST.mesmoTipo("STEP")) {

                conferirCorpo(ePrefixo, mAST.getBranch("BODY"), mEscopo);

            }

        }

    }


    public void conferirCorpoStruct(String ePrefixo, AST ASTPai, Pronoco mEscopo) {


        for (AST mAST : ASTPai.getASTS()) {


            if (mAST.mesmoTipo("DEFINE")) {

                getPotiparum_Sigmaz().alocacao_Define(ePrefixo, mAST, mEscopo);

            } else if (mAST.mesmoTipo("MOCKIZ")) {

                getPotiparum_Sigmaz(). alocacao_Mockiz(ePrefixo, mAST, mEscopo);

            } else if (mAST.mesmoTipo("ACTION")) {

                getPotiparum_Sigmaz().alocacao_Action(ePrefixo, mAST, mEscopo);

            } else if (mAST.mesmoTipo("FUNCTION")) {

                getPotiparum_Sigmaz().alocacao_Function(ePrefixo, mAST, mEscopo);

            } else if (mAST.mesmoTipo("OPERATOR")) {

                getPotiparum_Sigmaz().alocacao_Operator(ePrefixo, mAST, mEscopo);

            } else if (mAST.mesmoTipo("DIRECTOR")) {

                getPotiparum_Sigmaz().alocacao_Director(ePrefixo, mAST, mEscopo);

            }

        }


    }


}
