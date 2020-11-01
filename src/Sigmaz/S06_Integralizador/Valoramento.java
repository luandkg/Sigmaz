package Sigmaz.S06_Integralizador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.AgrupadorAST;
import Sigmaz.S08_Executor.Debuggers.Simplificador;

import java.util.ArrayList;

public class Valoramento {

    private Simplificador mSimplificador;
    private AgrupadorAST mAgrupadorAST;

    private Integrador mIntegrador;

    private Valore_Sigmaz mValore_Sigmaz;
    private Valore_Escopo mValore_Escopo;
    private Valore_Statements mValore_Statements;
    private Valore_ValueComplex mValore_ValueComplex;
    private Valore_Value mValore_Value;

    private ArrayList<String> mRegistradores;

    public Valoramento(Integrador eIntegrador) {

        mIntegrador = eIntegrador;

        mSimplificador = new Simplificador();
        mAgrupadorAST = new AgrupadorAST();

        mValore_Sigmaz = new Valore_Sigmaz(mIntegrador, this);
        mValore_Escopo = new Valore_Escopo(mIntegrador, this);
        mValore_Statements = new Valore_Statements(mIntegrador, this);
        mValore_ValueComplex = new Valore_ValueComplex(mIntegrador, this);
        mValore_Value = new Valore_Value(mIntegrador, this);

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

    public void realizarValoramento(String ePrefixo, AST eASTPai, Integralizante mIntegralizante,Escopante mEscopo) {


        for (AST mAST : eASTPai.getASTS()) {

            if (mIntegrador.getErros().size() > 0) {
                break;
            }

            if (mAST.mesmoTipo("CALL")) {

                mValore_Sigmaz.valore_Call(ePrefixo, mAST, mIntegralizante,mEscopo);

            } else if (mAST.mesmoTipo("ACTION")) {

                mValore_Sigmaz.valore_Action(ePrefixo, mAST, mIntegralizante,mEscopo);

            } else if (mAST.mesmoTipo("FUNCTION")) {

                mValore_Sigmaz.valore_Function(ePrefixo, mAST, mIntegralizante,mEscopo);

            } else if (mAST.mesmoTipo("OPERATOR")) {

                mValore_Sigmaz.valore_Operator(ePrefixo, mAST, mIntegralizante,mEscopo);

            } else if (mAST.mesmoTipo("MARK")) {

                mValore_Sigmaz.valore_Marker(ePrefixo, mAST, mIntegralizante,mEscopo);


            } else if (mAST.mesmoTipo("DIRECTOR")) {

                mValore_Sigmaz.valore_Director(ePrefixo, mAST, mIntegralizante,mEscopo);

            } else if (mAST.mesmoTipo("AUTO")) {

                mValore_Sigmaz.valore_Auto(ePrefixo, mAST, mIntegralizante,mEscopo);

            } else if (mAST.mesmoTipo("FUNCTOR")) {

                mValore_Sigmaz.valore_Functor(ePrefixo, mAST, mIntegralizante,mEscopo);

            } else if (mAST.mesmoTipo("DEFINE")) {

                mValore_Sigmaz.valore_Define(ePrefixo, mAST, mIntegralizante,mEscopo);

            } else if (mAST.mesmoTipo("MOCKIZ")) {

                mValore_Sigmaz.valore_Mockiz(ePrefixo, mAST, mIntegralizante,mEscopo);

            }

            if (mIntegrador.getErros().size() > 0) {
                break;
            }
        }


        for (AST mAST : eASTPai.getASTS()) {

            if (mAST.mesmoTipo("STRUCT")) {

                Escopante mAqui = new Escopante(mAST.getNome());


                if (mIntegrador.getErros().size() > 0) {
                    break;
                }

                if (mAST.getBranch("EXTENDED").mesmoNome("STRUCT")) {

                    mIntegrador.mensagem(ePrefixo + "Valorando STRUCT : " + mAST.getNome());

                    getValore_Hiper().emStruct_Corpo(ePrefixo + "\t", mAST.getNome(), mAST.getBranch("BODY"),mIntegralizante, mAqui);

                    getValore_Hiper().emStruct_Inits(ePrefixo + "\t", mAST.getNome(), mAST.getBranch("INITS"),mIntegralizante, mAqui);


                }

            }


        }


    }

    public void obterVarArgumentos(AST eASTPai, Escopante mEscopante) {

        for (AST mAST : eASTPai.getASTS()) {

            if (mEscopante.existeNesse(mAST.getNome())) {

                mIntegrador.mensagem(" Argumento Duplicado : " + mAST.getNome());
                mIntegrador.getErros().add("Argumento Duplicado : " + mAST.getNome());

            } else {
                mEscopante.alocar(mAST.getNome());
            }

        }

    }


    public void valore(String ePrefixo, AST mValue,Integralizante mIntegralizante, Escopante mEscopo) {


        if (mValue.mesmoValor("NULL")) {

            mIntegrador.mensagem(ePrefixo + "Valore NULL");

        } else if (mValue.mesmoValor("Text")) {

            mIntegrador.mensagem(ePrefixo + "Valore Text : " + mValue.getNome());

        } else if (mValue.mesmoValor("Num")) {

            mIntegrador.mensagem(ePrefixo + "Valore Num : " + mValue.getNome());

        } else if (mValue.mesmoValor("Float")) {

            mIntegrador.mensagem(ePrefixo + "Valore Float : " + mValue.getNome());

        } else if (mValue.mesmoValor("ID")) {

            mIntegrador.mensagem(ePrefixo + "Valore ID : " + mValue.getNome());

            mValore_Value.valore_ID(ePrefixo, mValue, mEscopo);

        } else if (mValue.mesmoValor("FUNCT")) {

            mValore_Value.valore_Funct(ePrefixo, mValue, mIntegralizante,mEscopo);

        } else if (mValue.mesmoValor("GETTER")) {


            for (AST mAST : mValue.getASTS()) {
                if (mAST.mesmoTipo("ARGUMENT") && mAST.mesmoValor("FUNCT")) {

                    valore(ePrefixo, mAST, mIntegralizante,mEscopo);


                }
            }
        } else if (mValue.mesmoValor("STRUCT_SETTER")) {


            for (AST mAST : mValue.getASTS()) {
                if (mAST.mesmoTipo("ARGUMENT") && mAST.mesmoValor("FUNCT")) {
                    valore(ePrefixo, mAST,  mIntegralizante,mEscopo);
                }
            }


        } else if (mValue.mesmoValor("TERNAL")) {

            mValore_Value.valore_Ternal(ePrefixo, mValue,  mIntegralizante,mEscopo);

        } else if (mValue.mesmoValor("OPERATOR")) {

            mValore_Value.valore_Operator(ePrefixo, mValue,  mIntegralizante,mEscopo);

        } else if (mValue.mesmoValor("DIRECTOR")) {

            mValore_Value.valore_Director(ePrefixo, mValue,  mIntegralizante,mEscopo);

        } else if (mValue.mesmoValor("BLOCK")) {

            valore(ePrefixo, mValue.getBranch("VALUE"),  mIntegralizante,mEscopo);

        } else if (mValue.mesmoValor("INIT")) {

            mValore_ValueComplex.emInit(ePrefixo, mValue,  mIntegralizante,mEscopo);


        } else if (mValue.mesmoValor("STAGE")) {


            mValore_ValueComplex.emStages(ePrefixo, mValue,  mIntegralizante,mEscopo);


        } else if (mValue.mesmoValor("STRUCT")) {

            Valore_Struct mValore_Struct = new Valore_Struct(mIntegrador, this);
            mValore_Struct.emStruct(ePrefixo, mValue,  mIntegralizante,mEscopo);

        } else if (mValue.mesmoValor("START")) {

            mValore_ValueComplex.emStart(ePrefixo, mValue,  mIntegralizante,mEscopo);


        } else if (mValue.mesmoValor("THIS")) {

            mValore_Value.valore_This(ePrefixo, mValue,  mIntegralizante,mEscopo);


        } else if (mValue.mesmoValor("STRUCT_EXTERN")) {

            Valore_External mValore_External = new Valore_External(mIntegrador, this);
            mValore_External.emExtern(ePrefixo, mValue,  mIntegralizante,mEscopo);

        } else if (mValue.mesmoValor("REG")) {


            mValore_Value.valore_Reg(ePrefixo, mValue,  mIntegralizante,mEscopo);

        } else if (mValue.mesmoValor("EXECUTE_LOCAL")) {

            mValore_Value.valore_ExecuteLocal(ePrefixo, mValue,  mIntegralizante,mEscopo);

        } else if (mValue.mesmoValor("MARKER")) {

            mValore_Value.valore_Marker(ePrefixo, mValue,  mIntegralizante,mEscopo);

        } else if (mValue.mesmoValor("DEFAULT")) {

            mValore_Value.valore_Default(ePrefixo, mValue,  mIntegralizante,mEscopo);

        } else if (mValue.mesmoValor("VECTOR")) {

            mValore_Value.valore_Vector(ePrefixo, mValue,  mIntegralizante,mEscopo);

        } else if (mValue.mesmoValor("EXECUTE_FUNCTOR")) {

            mIntegrador.mensagem(ePrefixo + "Valorando EXECUTE_FUNCTOR : " + mValue.getNome());


            for (AST eArg : mValue.getBranch("ARGUMENTS").getASTS()) {
                valore(ePrefixo + "\t", eArg,  mIntegralizante,mEscopo);
            }

        } else {


            mIntegrador.errar("Atribuidor Desconhecido  : " + mEscopo.getRegressivo() + " :: " + mValue.getValor());
            mIntegrador.mensagem(ePrefixo + "Atribuidor Desconhecido  : " + mEscopo.getRegressivo() + " :: " + mValue.getValor());

            mIntegrador.mensagem(mValue.getImpressao());

        }


    }


    public void valoreSemRetorno(String ePrefixo, AST mValue,  Integralizante mIntegralizante,Escopante mEscopo) {

        if (mValue.mesmoValor("NULL")) {


        } else if (mValue.mesmoValor("ID")) {

            mValore_Value.valore_ID(ePrefixo, mValue,mEscopo);

        } else if (mValue.mesmoValor("FUNCT")) {

            mValore_Value.valore_ActionFunct(ePrefixo, mValue,  mIntegralizante,mEscopo);

        } else if (mValue.mesmoValor("TERNAL")) {


            valore(ePrefixo, mValue.getBranch("CONDITION"),  mIntegralizante,mEscopo);

            if (mValue.existeBranch("TRUE")) {
                valore(ePrefixo, mValue.getBranch("TRUE"),  mIntegralizante,mEscopo);
            }

            if (mValue.existeBranch("FALSE")) {
                valore(ePrefixo, mValue.getBranch("FALSE"),  mIntegralizante,mEscopo);
            }


        } else if (mValue.mesmoValor("Text")) {
        } else if (mValue.mesmoValor("Num")) {
        } else if (mValue.mesmoValor("Float")) {

        } else if (mValue.mesmoValor("OPERATOR")) {

            valore(ePrefixo, mValue.getBranch("LEFT"), mIntegralizante,mEscopo);
            valore(ePrefixo, mValue.getBranch("RIGHT"), mIntegralizante,mEscopo);


        } else if (mValue.mesmoValor("STRUCT")) {

            Valore_Struct mValore_Struct = new Valore_Struct(mIntegrador, this);
            mValore_Struct.emStruct(ePrefixo, mValue, mIntegralizante,mEscopo);


        } else if (mValue.mesmoValor("THIS")) {

            mValore_Value.valore_This(ePrefixo, mValue, mIntegralizante,mEscopo);

        } else if (mValue.mesmoValor("STRUCT_EXTERN")) {

            Valore_External mValore_External = new Valore_External(mIntegrador, this);
            mValore_External.emExternSemRetorno(ePrefixo, mValue, mIntegralizante,mEscopo);


        } else {


            mIntegrador.errar("Atribuidor Sem Retorno Desconhecido  : " + mEscopo.getRegressivo() + " :: " + mValue.getValor());
            mIntegrador.mensagem("Atribuidor Sem Retorno Desconhecido  : " + mEscopo.getRegressivo() + " :: " + mValue.getValor());


        }


    }

    public void atribuicaoItem(String ePrefixo, AST mAST, Integralizante mIntegralizante, Escopante mEscopo) {


        String status = "Ok";


        if (mEscopo.existeNesse(mAST.getNome())) {

            mIntegrador.mensagem(ePrefixo + " Alocação Duplicada : " + mAST.getNome());
            mIntegrador.getErros().add("Alocação Duplicada : " + mAST.getNome());


        }

        if (mAST.existeBranch("VALUE")) {
            AST mValue = mAST.getBranch("VALUE");
            valore(ePrefixo, mValue,mIntegralizante, mEscopo);
        }

        if (mIntegrador.getErros().size() > 0) {
            status = "PROBLEMA";
        }

        mIntegrador.mensagem(ePrefixo + " " + mAST.getTipo() + " " + mAST.getNome() + " ->  " + status);


    }


}
