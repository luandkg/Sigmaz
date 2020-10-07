package Sigmaz.S05_PosProcessamento.Valore;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.AgrupadorAST;
import Sigmaz.S05_PosProcessamento.Mensageiro;
import Sigmaz.S05_PosProcessamento.Pronoco.*;
import Sigmaz.S06_Executor.Debuggers.Simplificador;

import java.util.ArrayList;

public class Valoramento {

    private Simplificador mSimplificador;
    private AgrupadorAST mAgrupadorAST;

    private Mensageiro mMensageiro;

    private Valore_Sigmaz mValore_Sigmaz;
    private Valore_Escopo mValore_Escopo;
    private Valore_Statements mValore_Statements;
    private Valore_ValueComplex mValore_ValueComplex;
    private Valore_Value mValore_Value;

    private ArrayList<String> mRegistradores;

    public Valoramento(Mensageiro eMensageiro) {

        mMensageiro = eMensageiro;
        mSimplificador = new Simplificador();
        mAgrupadorAST = new AgrupadorAST();

        mValore_Sigmaz = new Valore_Sigmaz(eMensageiro, this);
        mValore_Escopo = new Valore_Escopo(eMensageiro, this);
        mValore_Statements = new Valore_Statements(eMensageiro, this);
        mValore_ValueComplex = new Valore_ValueComplex(eMensageiro, this);
        mValore_Value = new Valore_Value(eMensageiro, this);

        mRegistradores = new ArrayList<String>();

        for (int i = 0; i < 20; i++) {
            mRegistradores.add("R" + i);
        }

    }

    public Simplificador getSimplificador() {
        return mSimplificador;
    }

    public Valore_Escopo getValore_Escopo() {
        return mValore_Escopo;
    }

    public Valore_Statements getValore_Statements() {
        return mValore_Statements;
    }

    public Valore_ValueComplex getValore_Hiper() {
        return mValore_ValueComplex;
    }

    public Valore_Value getValore_Tipos() {
        return mValore_Value;
    }

    public ArrayList<String> getRegistradores() {
        return mRegistradores;
    }

    public void realizarValoramento(String ePrefixo, AST eASTPai, Pronoco mAtribuindo) {


        for (AST mAST : eASTPai.getASTS()) {

            if (mMensageiro.getErros().size() > 0) {
                break;
            }

            if (mAST.mesmoTipo("CALL")) {

                mValore_Sigmaz.valore_Call(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("ACTION")) {

                mValore_Sigmaz.valore_Action(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("FUNCTION")) {

                mValore_Sigmaz.valore_Function(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("OPERATOR")) {

                mValore_Sigmaz.valore_Operator(ePrefixo, mAST, mAtribuindo);


            } else if (mAST.mesmoTipo("DIRECTOR")) {

                mValore_Sigmaz.valore_Director(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("AUTO")) {

                mValore_Sigmaz.valore_Auto(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("FUNCTOR")) {

                mValore_Sigmaz.valore_Functor(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("DEFINE")) {

                mValore_Sigmaz.valore_Define(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("MOCKIZ")) {

                mValore_Sigmaz.valore_Mockiz(ePrefixo, mAST, mAtribuindo);

            }

            if (mMensageiro.getErros().size() > 0) {
                break;
            }
        }


        for (AST mAST : eASTPai.getASTS()) {

            if (mAST.mesmoTipo("STRUCT")) {

                Pronoco mAqui = new Pronoco(mAST.getNome());
                mAqui.setSuperior(mAtribuindo);


                if (mMensageiro.getErros().size() > 0) {
                    break;
                }

                if (mAST.getBranch("EXTENDED").mesmoNome("STRUCT")) {

                    mMensageiro.mensagem(ePrefixo + "Valorando STRUCT : " + mAST.getNome());

                    getValore_Hiper().emStruct_Corpo(ePrefixo + "\t", mAST.getNome(), mAST.getBranch("BODY"), mAqui);

                    getValore_Hiper().emStruct_Inits(ePrefixo + "\t", mAST.getNome(), mAST.getBranch("INITS"), mAqui);


                }

            }


        }


    }

    public void obterVarArgumentos(AST eASTPai, Pronoco mAtribuindo) {

        for (AST mAST : eASTPai.getASTS()) {

            if (mAtribuindo.existeNesse(mAST.getNome())) {

                mMensageiro.mensagem(" Argumento Duplicado : " + mAST.getNome());
                mMensageiro.getErros().add("Argumento Duplicado : " + mAST.getNome());

            } else {
                mAtribuindo.adicionarDefine(new Pronoco_Def(mAST.getNome()));
            }

        }

    }


    public void valore(String ePrefixo, AST mValue, Pronoco mAtribuindo) {


        if (mValue.mesmoValor("NULL")) {

            mMensageiro.mensagem(ePrefixo + "Valore NULL");

        } else if (mValue.mesmoValor("Text")) {

            mMensageiro.mensagem(ePrefixo + "Valore Text : " + mValue.getNome());

        } else if (mValue.mesmoValor("Num")) {

            mMensageiro.mensagem(ePrefixo + "Valore Num : " + mValue.getNome());

        } else if (mValue.mesmoValor("Float")) {

            mMensageiro.mensagem(ePrefixo + "Valore Float : " + mValue.getNome());

        } else if (mValue.mesmoValor("ID")) {

            mMensageiro.mensagem(ePrefixo + "Valore ID : " + mValue.getNome());

            mValore_Value.valore_ID(ePrefixo, mValue, mAtribuindo);

        } else if (mValue.mesmoValor("FUNCT")) {

            mValore_Value.valore_Funct(ePrefixo, mValue, mAtribuindo);

        } else if (mValue.mesmoValor("TERNAL")) {

            mValore_Value.valore_Ternal(ePrefixo, mValue, mAtribuindo);

        } else if (mValue.mesmoValor("OPERATOR")) {

            mValore_Value.valore_Operator(ePrefixo, mValue, mAtribuindo);

        } else if (mValue.mesmoValor("DIRECTOR")) {

            mValore_Value.valore_Director(ePrefixo, mValue, mAtribuindo);

        } else if (mValue.mesmoValor("BLOCK")) {

            valore(ePrefixo, mValue.getBranch("VALUE"), mAtribuindo);

        } else if (mValue.mesmoValor("INIT")) {

            mValore_ValueComplex.emInit(ePrefixo, mValue, mAtribuindo);


        } else if (mValue.mesmoValor("STAGE")) {


            mValore_ValueComplex.emStages(ePrefixo, mValue, mAtribuindo);


        } else if (mValue.mesmoValor("STRUCT")) {

            mValore_ValueComplex.emStruct(ePrefixo, mValue, mAtribuindo);

        } else if (mValue.mesmoValor("START")) {

            mValore_ValueComplex.emStart(ePrefixo, mValue, mAtribuindo);


        } else if (mValue.mesmoValor("THIS")) {

            mValore_Value.valore_This(ePrefixo, mValue, mAtribuindo);


        } else if (mValue.mesmoValor("STRUCT_EXTERN")) {

            mValore_ValueComplex.emExtern(ePrefixo, mValue, mAtribuindo);

        } else if (mValue.mesmoValor("REG")) {


            mValore_Value.valore_Reg(ePrefixo, mValue, mAtribuindo);

        } else if (mValue.mesmoValor("EXECUTE_LOCAL")) {

            mValore_Value.valore_ExecuteLocal(ePrefixo, mValue, mAtribuindo);

        } else if (mValue.mesmoValor("MARKER")) {

            mValore_Value.valore_Marker(ePrefixo, mValue, mAtribuindo);

        } else if (mValue.mesmoValor("DEFAULT")) {

            mValore_Value.valore_Default(ePrefixo, mValue, mAtribuindo);

        } else if (mValue.mesmoValor("VECTOR")) {

            mValore_Value.valore_Vector(ePrefixo, mValue, mAtribuindo);


        } else {


            mMensageiro.errar("Atribuidor Desconhecido  : " + mAtribuindo.getRegressivo() + " :: " + mValue.getValor());
            mMensageiro.mensagem(ePrefixo + "Atribuidor Desconhecido  : " + mAtribuindo.getRegressivo() + " :: " + mValue.getValor());

            mMensageiro.mensagem(mValue.getImpressao());

        }


    }


    public void valoreSemRetorno(String ePrefixo, AST mValue, Pronoco mAtribuindo) {

        if (mValue.mesmoValor("NULL")) {


        } else if (mValue.mesmoValor("ID")) {

            mValore_Value.valore_ID(ePrefixo, mValue, mAtribuindo);

        } else if (mValue.mesmoValor("FUNCT")) {

            mValore_Value.valore_ActionFunct(ePrefixo, mValue, mAtribuindo);

        } else if (mValue.mesmoValor("TERNAL")) {


            valore(ePrefixo, mValue.getBranch("CONDITION"), mAtribuindo);

            if (mValue.existeBranch("TRUE")) {
                valore(ePrefixo, mValue.getBranch("TRUE"), mAtribuindo);
            }

            if (mValue.existeBranch("FALSE")) {
                valore(ePrefixo, mValue.getBranch("FALSE"), mAtribuindo);
            }


        } else if (mValue.mesmoValor("Text")) {
        } else if (mValue.mesmoValor("Num")) {
        } else if (mValue.mesmoValor("Float")) {

        } else if (mValue.mesmoValor("OPERATOR")) {

            valore(ePrefixo, mValue.getBranch("LEFT"), mAtribuindo);
            valore(ePrefixo, mValue.getBranch("RIGHT"), mAtribuindo);


        } else if (mValue.mesmoValor("STRUCT")) {

            mValore_ValueComplex.emStruct(ePrefixo, mValue, mAtribuindo);


        } else if (mValue.mesmoValor("THIS")) {

            mValore_Value.valore_This(ePrefixo, mValue, mAtribuindo);

        } else if (mValue.mesmoValor("STRUCT_EXTERN")) {

            mValore_ValueComplex.emExternSemRetorno(ePrefixo, mValue, mAtribuindo);


        } else {


            mMensageiro.errar("Atribuidor Sem Retorno Desconhecido  : " + mAtribuindo.getRegressivo() + " :: " + mValue.getValor());
            mMensageiro.mensagem("Atribuidor Sem Retorno Desconhecido  : " + mAtribuindo.getRegressivo() + " :: " + mValue.getValor());


        }


    }

    public void atribuicaoItem(String ePrefixo, AST mAST, Pronoco mAtribuindo) {


        String status = "Ok";


        if (mAtribuindo.existeNesse(mAST.getNome())) {

            mMensageiro.mensagem(ePrefixo + " Alocação Duplicada : " + mAST.getNome());
            mMensageiro.getErros().add("Alocação Duplicada : " + mAST.getNome());


        }

        if (mAST.existeBranch("VALUE")) {
            AST mValue = mAST.getBranch("VALUE");
            valore(ePrefixo, mValue, mAtribuindo);
        }

        if (mMensageiro.getErros().size() > 0) {
            status = "PROBLEMA";
        }

        mMensageiro.mensagem(ePrefixo + " " + mAST.getTipo() + " " + mAST.getNome() + " ->  " + status);


    }


}
