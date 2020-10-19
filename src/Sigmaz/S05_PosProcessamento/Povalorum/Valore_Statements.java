package Sigmaz.S05_PosProcessamento.Povalorum;

import Sigmaz.S00_Utilitarios.Mensageiro;
import Sigmaz.S05_PosProcessamento.Processadores.Valorador;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco_Def;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S07_Executor.Debuggers.Simplificador;

import java.util.ArrayList;

public class Valore_Statements {

    Valorador mValorador;
    Valoramento mValoramento;

    public Valore_Statements(Valorador eValorador, Valoramento eValoramento) {
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

    public void atribuidor_If(String ePrefixo, AST mAST, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + " ESCOPO : IF");

        Pronoco mAqui = new Pronoco("IF");
        mAqui.setSuperior(mAtribuindo);

        mValoramento.valore(ePrefixo + "\t", mAST.getBranch("CONDITION"), mAtribuindo);

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mAST.getBranch("BODY"), mAqui);

        for (AST eOutro : mAST.getASTS()) {
            if (eOutro.mesmoTipo("OTHER")) {

                Pronoco mOutro = new Pronoco("OTHER");
                mOutro.setSuperior(mAtribuindo);

                mValoramento.valore(ePrefixo + "\t", eOutro.getBranch("CONDITION"), mAtribuindo);
                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", eOutro.getBranch("BODY"), mOutro);

            } else if (eOutro.mesmoTipo("OTHERS")) {

                Pronoco mOutros = new Pronoco("OTHER");
                mOutros.setSuperior(mAtribuindo);

                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", eOutro, mOutros);

            }
        }


    }

    public void atribuidor_when(String ePrefixo, AST mAST, Pronoco mAtribuindo) {


        mValorador.mensagem(ePrefixo + " ESCOPO : WHEN");

        mValoramento.valore(ePrefixo + "\t", mAST.getBranch("CHOOSABLE"), mAtribuindo);


        for (AST eCaso : mAST.getBranch("CASES").getASTS()) {
            if (eCaso.mesmoTipo("CASE")) {

                Pronoco mOutros = new Pronoco("CASE");
                mOutros.setSuperior(mAtribuindo);

                mValoramento.valore(ePrefixo + "\t", eCaso.getBranch("CONDITION"), mAtribuindo);
                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", eCaso.getBranch("BODY"), mOutros);

            }
        }

        for (AST eOutro : mAST.getASTS()) {
            if (eOutro.mesmoTipo("OTHERS")) {

                Pronoco mOutros = new Pronoco("OTHERS");
                mOutros.setSuperior(mAtribuindo);

                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", eOutro, mOutros);

            }
        }


    }

    public void atribuidor_While(String ePrefixo, AST mAST, Pronoco mAtribuindo) {


        mValorador.mensagem(ePrefixo + " ESCOPO : WHILE");

        mValoramento.valore(ePrefixo + "\t", mAST.getBranch("CONDITION"), mAtribuindo);

        Pronoco mOutros = new Pronoco("WHILE");
        mOutros.setSuperior(mAtribuindo);

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mAST.getBranch("BODY"), mOutros);


    }

    public void atribuidor_Step(String ePrefixo, AST mAST, Pronoco mAtribuindo) {


        mValorador.mensagem(ePrefixo + " ESCOPO : STEP");

        for (AST eOutro : mAST.getBranch("ARGUMENTS").getASTS()) {

            mValoramento.valore(ePrefixo + "\t", eOutro, mAtribuindo);

        }

        Pronoco mOutros = new Pronoco("STEP");
        mOutros.setSuperior(mAtribuindo);

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mAST.getBranch("BODY"), mOutros);


    }

    public void atribuidor_StepDef(String ePrefixo, AST mAST, Pronoco mAtribuindo) {

        mValorador.mensagem(ePrefixo + " ESCOPO : STEPDEF");

        Pronoco mOutros = new Pronoco("STEPDEF");
        mOutros.setSuperior(mAtribuindo);


        if (mAtribuindo.existeNesse(mAST.getNome())) {

            mValorador.mensagem(" Variavel Duplicada : " + mAST.getNome());
            mValorador.getErros().add("Variavel Duplicada : " + mAST.getNome());

        } else {
            mAtribuindo.adicionarDefine(new Pronoco_Def(mAST.getNome()));
        }


        for (AST eOutro : mAST.getBranch("ARGUMENTS").getASTS()) {

            mValoramento.valore(ePrefixo + "\t", eOutro, mOutros);

        }


        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mAST.getBranch("BODY"), mOutros);


    }

    public void atribuidor_daz(String ePrefixo, AST mAST, Pronoco mAtribuindo) {


        mValorador.mensagem(ePrefixo + " ESCOPO : DAZ");

        mValoramento.valore(ePrefixo + "\t", mAST.getBranch("CHOOSABLE"), mAtribuindo);


        for (AST eCaso : mAST.getBranch("CASES").getASTS()) {
            //  if (eCaso.mesmoTipo("CASE")) {


            for (AST eOutro : eCaso.getBranch("ARGUMENTS").getASTS()) {

                mValoramento.valore(ePrefixo + "\t", eOutro, mAtribuindo);

            }

            Pronoco mOutros = new Pronoco("CASE");
            mOutros.setSuperior(mAtribuindo);


            getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", eCaso.getBranch("BODY"), mOutros);

            //   }
        }

        for (AST eOutro : mAST.getASTS()) {
            if (eOutro.mesmoTipo("OTHERS")) {

                Pronoco mOutros = new Pronoco("OTHERS");
                mOutros.setSuperior(mAtribuindo);


                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", eOutro, mOutros);

            }
        }


    }

    public void atribuidor_return(String ePrefixo, AST mAST, Pronoco mAtribuindo) {

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mAST.getBranch("VALUE"), mAtribuindo);


    }

    public void atribuidor_execute(String ePrefixo, AST mAST, Pronoco mAtribuindo) {

        mValoramento.valoreSemRetorno(ePrefixo + "\t", mAST, mAtribuindo);


    }


    public void atribuidor_apply(String ePrefixo, AST mAST, Pronoco mAtribuindo) {

        mValoramento.valore(ePrefixo + "\t", mAST.getBranch("SETTABLE"), mAtribuindo);
        mValoramento.valore(ePrefixo + "\t", mAST.getBranch("VALUE"), mAtribuindo);


    }

    public void atribuidor_try(String ePrefixo, AST mAST, Pronoco mAtribuindo) {


        Pronoco gAtribuindo = new Pronoco("TRY");
        gAtribuindo.setSuperior(mAtribuindo);

        mValorador.mensagem(mAST.getImpressao());

        if (mAST.getBranch("LOGIC").mesmoNome("TRUE")) {

            if (mAtribuindo.existeNesse(mAST.getBranch("LOGIC").getValor())) {
            } else {
                mValorador.mensagem(" Variavel nao encontrada : " + mAST.getBranch("LOGIC").getValor());
                mValorador.getErros().add("Variavel nao encontrada : " + mAST.getBranch("LOGIC").getValor());
            }
        }
        if (mAST.getBranch("MESSAGE").mesmoNome("TRUE")) {

            if (mAtribuindo.existeNesse(mAST.getBranch("MESSAGE").getValor())) {
            } else {
                mValorador.mensagem(" Variavel nao encontrada : " + mAST.getBranch("MESSAGE").getValor());
                mValorador.getErros().add("Variavel nao encontrada : " + mAST.getBranch("MESSAGE").getValor());
            }
        }

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mAST.getBranch("BODY"), gAtribuindo);


    }
}
