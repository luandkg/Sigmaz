package Sigmaz.S05_PosProcessamento.Potiparum;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Mensageiro;
import Sigmaz.S05_PosProcessamento.Processadores.Tipador;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco;
import Sigmaz.S07_Executor.Debuggers.Simplificador;

import java.util.ArrayList;

public class Potiparum {

    private Simplificador mSimplificador;

    private Tipador mTipador;
    private Potiparum_Tipificador mPotiparum_Tipificador;
    private Potiparum_Escopos mPotiparum_Escopos;
    private Potiparum_Sigmaz mPotiparum_Sigmaz;

    public Potiparum(Tipador eTipador) {

        mTipador = eTipador;
        mSimplificador = mTipador.getSimplificador();

        mPotiparum_Sigmaz = new Potiparum_Sigmaz(this);
        mPotiparum_Tipificador = new Potiparum_Tipificador(this);
        mPotiparum_Escopos = new Potiparum_Escopos(this);


        index();

    }


    public void index() {

        mPotiparum_Sigmaz.index();
        mPotiparum_Tipificador.index();
        mPotiparum_Escopos.index();

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

    public Tipador getTipador() {
        return mTipador;
    }


    public Simplificador getSimplificador() {
        return mSimplificador;
    }

    public void mensagem(String e) {
        if (mTipador.getDebug()) {
            mTipador.mensagem(e);
        }
    }

    public void errar(String e) {
        mTipador.errar(e);
    }


    public void chegarTipagens_novo(String ePrefixo, ArrayList<AST> mPacotes, Pronoco ePronoco, AST ASTPai) {


        // USAR PACKAGES
        for (AST ASTC : ASTPai.getASTS()) {
            if (ASTC.mesmoTipo("REFER")) {
                mensagem(ePrefixo + "REFERENCIANDO PACOTE " + ASTC.getNome());
                getTipador().Usar(mPacotes, ePrefixo + "\t", ASTC.getNome(), ePronoco);
            }
        }

        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("CAST")) {


                ePronoco.adicionarCast(mAST.getNome());

                mensagem(ePrefixo + "- Tipo CAST : " + mAST.getNome());

            } else if (mAST.mesmoTipo("STRUCT")) {


                AST mExtended = mAST.getBranch("EXTENDED");

                if (mExtended.mesmoNome("STRUCT")) {

                    mensagem(ePrefixo + "- Tipo STRUCT : " + mAST.getNome());

                    ePronoco.adicionarStruct(mAST.getNome());

                } else if (mExtended.mesmoNome("STAGES")) {
                    mensagem(ePrefixo + "- Tipo STAGE : " + mAST.getNome());

                    ePronoco.adicionarStage(mAST.getNome());

                } else if (mExtended.mesmoNome("TYPE")) {

                    mensagem(ePrefixo + "- Tipo TYPE : " + mAST.getNome());

                    ePronoco.adicionarType(mAST.getNome());
                } else if (mExtended.mesmoNome("MODEL")) {

                    mensagem(ePrefixo + "- Tipo MODEL : " + mAST.getNome());

                    ePronoco.adicionarModel(mAST.getNome());


                }

            }
        }


        for (AST mAST : ASTPai.getASTS()) {


            if (mAST.mesmoTipo("DEFINE")) {

                getPotiparum_Sigmaz().alocacao_Define(ePrefixo, mAST, ePronoco);

            } else if (mAST.mesmoTipo("MOCKIZ")) {

                getPotiparum_Sigmaz().alocacao_Mockiz(ePrefixo, mAST, ePronoco);

            } else if (mAST.mesmoTipo("ACTION")) {

                getPotiparum_Sigmaz().alocacao_Action(ePrefixo, mAST, ePronoco);
            } else if (mAST.mesmoTipo("CALL")) {

                getPotiparum_Sigmaz().alocacao_Call(ePrefixo, mAST, ePronoco);
            } else if (mAST.mesmoTipo("PROTOTYPE_AUTO")) {

                getPotiparum_Sigmaz().alocacao_Auto(ePrefixo, mAST, ePronoco);

            } else if (mAST.mesmoTipo("FUNCTION")) {

                getPotiparum_Sigmaz().alocacao_Function(ePrefixo, mAST, ePronoco);

            } else if (mAST.mesmoTipo("PROTOTYPE_FUNCTOR")) {

                getPotiparum_Sigmaz().alocacao_Functor(ePrefixo, mAST, ePronoco);

            } else if (mAST.mesmoTipo("OPERATOR")) {

                getPotiparum_Sigmaz().alocacao_Operator(ePrefixo, mAST, ePronoco);

            } else if (mAST.mesmoTipo("DIRECTOR")) {

                getPotiparum_Sigmaz().alocacao_Director(ePrefixo, mAST, ePronoco);


            } else if (mAST.mesmoTipo("CAST")) {

            } else if (mAST.mesmoTipo("STRUCT")) {

                mensagem(ePrefixo + "STRUCT " + mAST.getNome());

                AST mExtended = mAST.getBranch("EXTENDED");

                if (mExtended.mesmoNome("STRUCT")) {

                    Pronoco mStructPronoco = new Pronoco("STRUCT " + mAST.getNome());
                    mStructPronoco.setSuperior(ePronoco);


                    if (mAST.getBranch("GENERIC").mesmoNome("TRUE")) {

                        ArrayList<String> genericos_ok = getPotiparum_Tipificador().conferirGenericos(mAST.getBranch("GENERIC"), mStructPronoco);

                        if (genericos_ok.size() > 0) {
                            mensagem(ePrefixo + mAST.getNome() + " -->> TIPAGEM DE GENERICOS INVALIDA ");
                            errar(mAST.getNome() + " -->> TIPAGEM DE GENERICOS INVALIDA ");
                            for (String e : genericos_ok) {
                                mensagem(ePrefixo + "\t" + e);
                                errar(mAST.getNome() + " -->> " + e);
                            }
                        }

                    }

                    mPotiparum_Escopos.conferirCorpoStruct(ePrefixo + "\t", mAST.getBranch("BODY"), mStructPronoco);

                }


            }

        }


    }


}
