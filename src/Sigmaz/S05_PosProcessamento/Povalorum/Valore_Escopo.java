package Sigmaz.S05_PosProcessamento.Povalorum;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Mensageiro;
import Sigmaz.S05_PosProcessamento.Processadores.Valorador;
import Sigmaz.S05_PosProcessamento.Pronoco.*;
import Sigmaz.S07_Executor.Debuggers.Simplificador;

import java.util.ArrayList;

public class Valore_Escopo {

    Valorador mValorador;
    Valoramento mValoramento;

    public Valore_Escopo(Valorador eValorador, Valoramento eValoramento) {
        mValorador = eValorador;
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

    public void atribuicao_escopo(String ePrefixo, AST eASTPai, Pronoco mAtribuindo) {


        for (AST mAST : eASTPai.getASTS()) {


            if (mAST.mesmoTipo("DEF")) {

                mValoramento.atribuicaoItem(ePrefixo, mAST, mAtribuindo);

                mAtribuindo.adicionarDefine(new Pronoco_Def(mAST.getNome()));

            } else if (mAST.mesmoTipo("MOC")) {

                mValoramento.atribuicaoItem(ePrefixo, mAST, mAtribuindo);

                mAtribuindo.adicionarMockiz(new Pronoco_Moc(mAST.getNome()));

            } else if (mAST.mesmoTipo("LET")) {

                mValoramento.atribuicaoItem(ePrefixo, mAST, mAtribuindo);

                mAtribuindo.adicionarLet(new Pronoco_Let(mAST.getNome()));

            } else if (mAST.mesmoTipo("VOZ")) {

                mValoramento.atribuicaoItem(ePrefixo, mAST, mAtribuindo);

                mAtribuindo.adicionarVoz(new Pronoco_Voz(mAST.getNome()));

            } else if (mAST.mesmoTipo("MUT")) {

                mValoramento.atribuicaoItem(ePrefixo, mAST, mAtribuindo);

                mAtribuindo.adicionarMut(new Pronoco_Mut(mAST.getNome()));

            } else if (mAST.mesmoTipo("IF")) {

                getValore_Statements().atribuidor_If(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("WHILE")) {

                getValore_Statements().atribuidor_While(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("STEP")) {

                getValore_Statements().atribuidor_Step(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("STEPDEF")) {

                getValore_Statements().atribuidor_StepDef(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("WHEN")) {

                getValore_Statements().atribuidor_when(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("DAZ")) {

                getValore_Statements().atribuidor_daz(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("RETURN")) {

                getValore_Statements().atribuidor_return(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("EXECUTE")) {

                getValore_Statements().atribuidor_execute(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("APPLY")) {

                getValore_Statements().atribuidor_apply(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("CANCEL")) {
            } else if (mAST.mesmoTipo("CONTINUE")) {
            } else if (mAST.mesmoTipo("INVOKE")) {

            } else if (mAST.mesmoTipo("EXCEPTION")) {

                mValorador.mensagem(ePrefixo + "ESCOPO : EXCEPTION");
                mValoramento.valore(ePrefixo + "\t", mAST.getBranch("VALUE"), mAtribuindo);


            } else if (mAST.mesmoTipo("INIT")) {

                mValoramento.valore(ePrefixo + "\t", mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("THIS")) {

                if (mAST.existeBranch("INTERNAL")) {

                    Valore_StructThis mValore_StructThis = new Valore_StructThis(mValorador,mValoramento);

                    mValore_StructThis.emInternal(ePrefixo + "\t", mAST.getBranch("INTERNAL"), mAtribuindo);

                }

            } else if (mAST.mesmoTipo("ARGUMENTS")) {

                for (AST eArgumento : mAST.getASTS()) {

                    mValoramento.valore(ePrefixo, eArgumento, mAtribuindo);

                }

            } else if (mAST.mesmoTipo("INTERNAL")) {

                Valore_Struct mValore_Struct = new Valore_Struct(mValorador,mValoramento);

                mValore_Struct.emInternal(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("GENERIC")) {
            } else if (mAST.mesmoTipo("STARTED")) {
            } else if (mAST.mesmoTipo("EACH")) {

                mValorador.mensagem(ePrefixo + "Valorando EACH ");

                Pronoco mAqui = new Pronoco("EACH");
                mAqui.setSuperior(mAtribuindo);


                AST eDef = mAST.getBranch("DEF");

                if (mAqui.existeNesse(eDef.getNome())) {

                    mValorador.mensagem(" Argumento Duplicado : " + eDef.getNome());
                    mValorador.getErros().add("Argumento Duplicado : " + eDef.getNome());

                } else {
                    mAqui.adicionarDefine(new Pronoco_Def(eDef.getNome()));
                }

                for (AST od : mAST.getBranch("BODY").getASTS()) {

                    atribuicao_escopo(ePrefixo, od, mAqui);

                }

            } else if (mAST.mesmoTipo("START")) {
            } else if (mAST.mesmoTipo("MODE")) {
            } else if (mAST.mesmoTipo("LEFT")) {

                mValoramento.valore(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("RIGHT")) {

                mValoramento.valore(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("TRY")) {

                getValore_Statements().atribuidor_try(ePrefixo, mAST, mAtribuindo);

            } else if (mAST.mesmoTipo("LOCAL")) {


                mValorador.mensagem(ePrefixo + "Valorando LOCAL : " + getSimplificador().getFuction(mAST));

                Pronoco mAqui = new Pronoco("LOCAL");
                mAqui.setSuperior(mAtribuindo);


                mValoramento.obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);

                atribuicao_escopo(ePrefixo + "\t", mAST.getBranch("BODY"), mAqui);

            } else if (mAST.mesmoTipo("EXECUTE_LOCAL")) {

                mValorador.mensagem(ePrefixo + "Valorando EXECUTE_LOCAL : " + getSimplificador().getFuction(mAST));

                Pronoco mAqui = new Pronoco("EXECUTE_LOCAL");
                mAqui.setSuperior(mAtribuindo);

                mValoramento.obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);

            } else if (mAST.mesmoTipo("EXECUTE_AUTO")) {

                mValorador.mensagem(ePrefixo + "Valorando EXECUTE_AUTO : " + getSimplificador().getAction(mAST));

                Pronoco mAqui = new Pronoco("EXECUTE_AUTO");
                mAqui.setSuperior(mAtribuindo);

                mValoramento.obterVarArgumentos(mAST.getBranch("ARGUMENTS"), mAqui);


            } else if (mAST.mesmoTipo("REG")) {

                if (getRegistradores().contains(mAST.getNome())) {

                } else {
                    mValorador.errar("Registrado Desconhecido : " + mAtribuindo.getRegressivo() + " :: " + mAST.getNome());
                }


            } else if (mAST.mesmoTipo("LOOP")) {

                mValorador.mensagem(ePrefixo + "Valorando LOOP ");

                Pronoco mAqui = new Pronoco("LOOP");
                mAqui.setSuperior(mAtribuindo);

                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mAST.getBranch("BODY"), mAqui);

            } else if (mAST.mesmoTipo("SCOPE")) {

                mValorador.mensagem(ePrefixo + "Valorando SCOPE ");

                Pronoco mAqui = new Pronoco("SCOPE");
                mAqui.setSuperior(mAtribuindo);


                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mAST, mAqui);





            } else if (mAST.mesmoTipo("EXTERN_REFERED")) {

                mValorador.mensagem(ePrefixo + "EXTERN_REFERED ");

                String mExternRefered =mAST.getNome();
                String mStruct = mAST.getBranch("STRUCT").getNome();
                String mRefered = mAST.getBranch("REFERED").getNome();

                mValorador.mensagem(ePrefixo + "\t - Extern Refered : " + mAST.getNome());
                mValorador.mensagem(ePrefixo + "\t - Struct : " + mStruct);
                mValorador.mensagem(ePrefixo + "\t - Refered : "+mRefered);

                if (mAtribuindo.existeNesse(mExternRefered)) {

                    mValorador.mensagem(ePrefixo +" Argumento Duplicado : " + mExternRefered);
                    mValorador.getErros().add("Argumento Duplicado : " + mExternRefered);

                } else {
                    mAtribuindo.adicionarDefine(new Pronoco_Def(mExternRefered));
                }



                if (mAtribuindo.existeExtern(mStruct)) {

                }else{
                    mValorador.errar("Struct desconhecida : " + mAtribuindo.getRegressivo() + " :: " + mStruct);
                }


            } else if (mAST.mesmoTipo("PROC")) {

            } else if (mAST.mesmoTipo("CHANGE")) {

            } else if (mAST.mesmoTipo("USING")) {

            } else if (mAST.mesmoTipo("MOVE_DATA")) {

            } else if (mAST.mesmoTipo("DEBUG")) {

            } else if (mAST.mesmoTipo("DELETE")) {

            } else if (mAST.mesmoTipo("STRUCT_SETTER")) {

            } else {


                mValorador.errar("Escopo Desconhecido : " + mAtribuindo.getRegressivo() + " :: " + mAST.getTipo());

                mValorador.mensagem(ePrefixo + mAST.getImpressao());

            }

            if (mValorador.getErros().size() > 0) {
                break;
            }

        }


    }


}
