package Sigmaz.S03_Integrador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S05_Executor.Debuggers.Simplificador;

import java.util.ArrayList;

public class Valore_Escopo {

    Integrador mIntegrador;
    Valoramento mValoramento;

    public Valore_Escopo(Integrador eIntegrador, Valoramento eValoramento) {
        mIntegrador = eIntegrador;
        mValoramento = eValoramento;
    }

    public Valore_Statements getValore_Statements() {
        return mValoramento.getValore_Statements();
    }

    public Valore_ValueComplex getValore_Hiper() {
        return mValoramento.getValore_Hiper();
    }

    public Valore_Value getValore_Tipos() {
        return mValoramento.getValore_Tipos();
    }

    public Valoramento getValoramento() {
        return mValoramento;
    }

    public Simplificador getSimplificador() {
        return mValoramento.getSimplificador();
    }

    public ArrayList<String> getRegistradores() {
        return mValoramento.getRegistradores();
    }

    public Valore_Escopo getValore_Escopo() {
        return mValoramento.getValore_Escopo();
    }

    public void atribuicao_escopo(String ePrefixo, AST eASTPai, Integralizante mIntegralizante, Escopante mEscopo) {


        for (AST mAST : eASTPai.getASTS()) {


            if (mAST.mesmoTipo("DEF")) {

                mValoramento.atribuicaoItem(ePrefixo, mAST,mIntegralizante, mEscopo);

                mEscopo.alocar((mAST.getNome()));

            } else if (mAST.mesmoTipo("MOC")) {

                mValoramento.atribuicaoItem(ePrefixo, mAST,mIntegralizante,  mEscopo);

                mEscopo.alocar((mAST.getNome()));

            } else if (mAST.mesmoTipo("LET")) {

                mValoramento.atribuicaoItem(ePrefixo, mAST,mIntegralizante,  mEscopo);

                mEscopo.alocar((mAST.getNome()));

            } else if (mAST.mesmoTipo("VOZ")) {

                mValoramento.atribuicaoItem(ePrefixo, mAST,mIntegralizante,  mEscopo);

                mEscopo.alocar((mAST.getNome()));

            } else if (mAST.mesmoTipo("MUT")) {

                mValoramento.atribuicaoItem(ePrefixo, mAST, mIntegralizante, mEscopo);

                mEscopo.alocar((mAST.getNome()));

            } else if (mAST.mesmoTipo("IF")) {

                getValore_Statements().atribuidor_If(ePrefixo, mAST, mIntegralizante, mEscopo);

            } else if (mAST.mesmoTipo("WHILE")) {

                getValore_Statements().atribuidor_While(ePrefixo, mAST, mIntegralizante, mEscopo);

            } else if (mAST.mesmoTipo("STEP")) {

                getValore_Statements().atribuidor_Step(ePrefixo, mAST, mIntegralizante, mEscopo);

            } else if (mAST.mesmoTipo("STEPDEF")) {

                getValore_Statements().atribuidor_StepDef(ePrefixo, mAST, mIntegralizante, mEscopo);

            } else if (mAST.mesmoTipo("WHEN")) {

                getValore_Statements().atribuidor_when(ePrefixo, mAST, mIntegralizante, mEscopo);

            } else if (mAST.mesmoTipo("DAZ")) {

                getValore_Statements().atribuidor_daz(ePrefixo, mAST, mIntegralizante, mEscopo);

            } else if (mAST.mesmoTipo("RETURN")) {

                getValore_Statements().atribuidor_return(ePrefixo, mAST, mIntegralizante, mEscopo);

            } else if (mAST.mesmoTipo("EXECUTE")) {

                getValore_Statements().atribuidor_execute(ePrefixo, mAST, mIntegralizante, mEscopo);

            } else if (mAST.mesmoTipo("APPLY")) {

                getValore_Statements().atribuidor_apply(ePrefixo, mAST, mIntegralizante, mEscopo);

            } else if (mAST.mesmoTipo("CANCEL")) {
            } else if (mAST.mesmoTipo("CONTINUE")) {
            } else if (mAST.mesmoTipo("INVOKE")) {

            } else if (mAST.mesmoTipo("EXCEPTION")) {

                mIntegrador.mensagem(ePrefixo + "ESCOPO : EXCEPTION");
                mValoramento.valore(ePrefixo + "\t", mAST.getBranch("VALUE"), mIntegralizante, mEscopo);


            } else if (mAST.mesmoTipo("INIT")) {

                mValoramento.valore(ePrefixo + "\t", mAST, mIntegralizante, mEscopo);

            } else if (mAST.mesmoTipo("THIS")) {

                if (mAST.existeBranch("INTERNAL")) {

                    Valore_StructThis mValore_StructThis = new Valore_StructThis(mIntegrador,mValoramento);

                    mValore_StructThis.emInternal(ePrefixo + "\t", mAST.getBranch("INTERNAL"), mIntegralizante, mEscopo);

                }

            } else if (mAST.mesmoTipo("ARGUMENTS")) {

                for (AST eArgumento : mAST.getASTS()) {

                    mValoramento.valore(ePrefixo, eArgumento, mIntegralizante, mEscopo);

                }

            } else if (mAST.mesmoTipo("INTERNAL")) {

                Valore_Struct mValore_Struct = new Valore_Struct(mIntegrador,mValoramento);

                mValore_Struct.emInternal(ePrefixo, mAST, mIntegralizante, mEscopo);

            } else if (mAST.mesmoTipo("GENERIC")) {
            } else if (mAST.mesmoTipo("STARTED")) {
            } else if (mAST.mesmoTipo("EACH")) {

                mIntegrador.mensagem(ePrefixo + "Valorando EACH ");

                Escopante mAqui = new Escopante("EACH");
                mAqui.setAnterior(mEscopo);


                AST eDef = mAST.getBranch("DEF");

                if (mAqui.existeNesse(eDef.getNome())) {

                    mIntegrador.mensagem(" Argumento Duplicado : " + eDef.getNome());
                    mIntegrador.getErros().add("Argumento Duplicado : " + eDef.getNome());

                } else {
                    mAqui.alocar((eDef.getNome()));
                }

                for (AST od : mAST.getBranch("BODY").getASTS()) {

                    atribuicao_escopo(ePrefixo, od, mIntegralizante,mAqui);

                }

            } else if (mAST.mesmoTipo("START")) {
            } else if (mAST.mesmoTipo("MODE")) {
            } else if (mAST.mesmoTipo("LEFT")) {

                mValoramento.valore(ePrefixo, mAST, mIntegralizante, mEscopo);

            } else if (mAST.mesmoTipo("RIGHT")) {

                mValoramento.valore(ePrefixo, mAST, mIntegralizante, mEscopo);

            } else if (mAST.mesmoTipo("TRY")) {

                getValore_Statements().atribuidor_try(ePrefixo, mAST, mIntegralizante, mEscopo);

            } else if (mAST.mesmoTipo("LOCAL")) {


                mIntegrador.mensagem(ePrefixo + "Valorando LOCAL : " + getSimplificador().getFuction(mAST));

                Escopante mAqui = new Escopante("LOCAL");
                mAqui.setAnterior(mEscopo);


                mValoramento.obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);

                atribuicao_escopo(ePrefixo + "\t", mAST.getBranch("BODY"), mIntegralizante,mAqui);

            } else if (mAST.mesmoTipo("EXECUTE_LOCAL")) {

                mIntegrador.mensagem(ePrefixo + "Valorando EXECUTE_LOCAL : " + getSimplificador().getFuction(mAST));

                Escopante mAqui = new Escopante("EXECUTE_LOCAL");
                mAqui.setAnterior(mEscopo);

                mValoramento.obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);

            } else if (mAST.mesmoTipo("EXECUTE_AUTO")) {

                mIntegrador.mensagem(ePrefixo + "Valorando EXECUTE_AUTO : " + getSimplificador().getAction(mAST));

                Escopante mAqui = new Escopante("EXECUTE_AUTO");
                mAqui.setAnterior(mEscopo);

                mValoramento.obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);


            } else if (mAST.mesmoTipo("REG")) {

                if (getRegistradores().contains(mAST.getNome())) {

                } else {
                    mIntegrador.errar("Registrado Desconhecido : " + mEscopo.getRegressivo() + " :: " + mAST.getNome());
                }


            } else if (mAST.mesmoTipo("LOOP")) {

                mIntegrador.mensagem(ePrefixo + "Valorando LOOP ");

                Escopante mAqui = new Escopante("LOOP");
                mAqui.setAnterior(mEscopo);

                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mAST.getBranch("BODY"),mIntegralizante, mAqui);

            } else if (mAST.mesmoTipo("SCOPE")) {

                mIntegrador.mensagem(ePrefixo + "Valorando SCOPE ");

                Escopante mAqui = new Escopante("SCOPE");
                mAqui.setAnterior(mEscopo);


                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mAST,mIntegralizante, mAqui);





            } else if (mAST.mesmoTipo("EXTERN_REFERED")) {

                mIntegrador.mensagem(ePrefixo + "EXTERN_REFERED ");

                String mExternRefered =mAST.getNome();
                String mStruct = mAST.getBranch("STRUCT").getNome();
                String mRefered = mAST.getBranch("REFERED").getNome();

                mIntegrador.mensagem(ePrefixo + "\t - Extern Refered : " + mAST.getNome());
                mIntegrador.mensagem(ePrefixo + "\t - Struct : " + mStruct);
                mIntegrador.mensagem(ePrefixo + "\t - Refered : "+mRefered);

                if (mEscopo.existeNesse(mExternRefered)) {

                    mIntegrador.mensagem(ePrefixo +" Argumento Duplicado : " + mExternRefered);
                    mIntegrador.getErros().add("Argumento Duplicado : " + mExternRefered);

                } else {
                    mEscopo.alocar((mExternRefered));
                }



                if (mIntegralizante.existeExtern(mStruct)) {

                }else{
                    mIntegrador.errar("Struct desconhecida : " + mEscopo.getRegressivo() + " :: " + mStruct);
                }


            } else if (mAST.mesmoTipo("PROC")) {

            } else if (mAST.mesmoTipo("CHANGE")) {

            } else if (mAST.mesmoTipo("USING")) {

            } else if (mAST.mesmoTipo("MOVE_DATA")) {

            } else if (mAST.mesmoTipo("DEBUG")) {

            } else if (mAST.mesmoTipo("DELETE")) {

            } else if (mAST.mesmoTipo("STRUCT_SETTER")) {

            } else if (mAST.mesmoTipo("IN_DEBUG")) {

            } else {


                mIntegrador.errar("Escopo Desconhecido : " + mEscopo.getRegressivo() + " :: " + mAST.getTipo());

                mIntegrador.mensagem(ePrefixo + mAST.getImpressao());

            }

            if (mIntegrador.getErros().size() > 0) {
                break;
            }

        }


    }


}
