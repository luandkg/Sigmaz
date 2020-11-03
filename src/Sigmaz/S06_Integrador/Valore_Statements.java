package Sigmaz.S06_Integrador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S08_Executor.Debuggers.Simplificador;

import java.util.ArrayList;

public class Valore_Statements {

    Integrador mIntegrador;
    Valoramento mValoramento;

    public Valore_Statements(Integrador eIntegrador, Valoramento eValoramento) {
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

    public void atribuidor_If(String ePrefixo, AST mAST, Integralizante mIntegralizante,Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + " ESCOPO : IF");

        Escopante mAqui = new Escopante("IF");
        mAqui.setAnterior(mEscopo);

        mValoramento.valore(ePrefixo + "\t", mAST.getBranch("CONDITION"),mIntegralizante, mEscopo);

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mAST.getBranch("BODY"), mIntegralizante,mAqui);

        for (AST eOutro : mAST.getASTS()) {
            if (eOutro.mesmoTipo("OTHER")) {

                Escopante mOutro = new Escopante("OTHER");
                mOutro.setAnterior(mEscopo);

                mValoramento.valore(ePrefixo + "\t", eOutro.getBranch("CONDITION"),mIntegralizante, mEscopo);
                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", eOutro.getBranch("BODY"), mIntegralizante, mOutro);

            } else if (eOutro.mesmoTipo("OTHERS")) {

                Escopante mOutros = new Escopante("OTHER");
                mOutros.setAnterior(mEscopo);

                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", eOutro, mIntegralizante, mOutros);

            }
        }


    }

    public void atribuidor_when(String ePrefixo, AST mAST, Integralizante mIntegralizante,Escopante mEscopo) {


        mIntegrador.mensagem(ePrefixo + " ESCOPO : WHEN");

        mValoramento.valore(ePrefixo + "\t", mAST.getBranch("CHOOSABLE"),mIntegralizante, mEscopo);


        for (AST eCaso : mAST.getBranch("CASES").getASTS()) {
            if (eCaso.mesmoTipo("CASE")) {

                Escopante mOutros = new Escopante("CASE");
                mOutros.setAnterior(mEscopo);

                mValoramento.valore(ePrefixo + "\t", eCaso.getBranch("CONDITION"),mIntegralizante, mEscopo);
                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", eCaso.getBranch("BODY"),mIntegralizante, mOutros);

            }
        }

        for (AST eOutro : mAST.getASTS()) {
            if (eOutro.mesmoTipo("OTHERS")) {

                Escopante mOutros = new Escopante("OTHERS");
                mOutros.setAnterior(mEscopo);

                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", eOutro, mIntegralizante,mOutros);

            }
        }


    }

    public void atribuidor_While(String ePrefixo, AST mAST, Integralizante mIntegralizante,Escopante mEscopo) {


        mIntegrador.mensagem(ePrefixo + " ESCOPO : WHILE");

        mValoramento.valore(ePrefixo + "\t", mAST.getBranch("CONDITION"),mIntegralizante, mEscopo);

        Escopante mOutros = new Escopante("WHILE");
        mOutros.setAnterior(mEscopo);

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mAST.getBranch("BODY"), mIntegralizante,mOutros);


    }

    public void atribuidor_Step(String ePrefixo, AST mAST, Integralizante mIntegralizante,Escopante mEscopo) {


        mIntegrador.mensagem(ePrefixo + " ESCOPO : STEP");

        for (AST eOutro : mAST.getBranch("ARGUMENTS").getASTS()) {

            mValoramento.valore(ePrefixo + "\t", eOutro,mIntegralizante, mEscopo);

        }

        Escopante mOutros = new Escopante("STEP");
        mOutros.setAnterior(mEscopo);

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mAST.getBranch("BODY"), mIntegralizante,mOutros);


    }

    public void atribuidor_StepDef(String ePrefixo, AST mAST, Integralizante mIntegralizante,Escopante mEscopo) {

        mIntegrador.mensagem(ePrefixo + " ESCOPO : STEPDEF");

        Escopante mOutros = new Escopante("STEPDEF");
        mOutros.setAnterior(mEscopo);


        if (mEscopo.existeNesse(mAST.getNome())) {

            mIntegrador.mensagem(" Variavel Duplicada : " + mAST.getNome());
            mIntegrador.getErros().add("Variavel Duplicada : " + mAST.getNome());

        } else {
            mEscopo.alocar((mAST.getNome()));
        }


        for (AST eOutro : mAST.getBranch("ARGUMENTS").getASTS()) {

            mValoramento.valore(ePrefixo + "\t", eOutro,mIntegralizante, mOutros);

        }


        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mAST.getBranch("BODY"), mIntegralizante,mOutros);


    }

    public void atribuidor_daz(String ePrefixo, AST mAST, Integralizante mIntegralizante,Escopante mEscopo) {


        mIntegrador.mensagem(ePrefixo + " ESCOPO : DAZ");

        mValoramento.valore(ePrefixo + "\t", mAST.getBranch("CHOOSABLE"),mIntegralizante, mEscopo);


        for (AST eCaso : mAST.getBranch("CASES").getASTS()) {
            //  if (eCaso.mesmoTipo("CASE")) {


            for (AST eOutro : eCaso.getBranch("ARGUMENTS").getASTS()) {

                mValoramento.valore(ePrefixo + "\t", eOutro,mIntegralizante, mEscopo);

            }

            Escopante mOutros = new Escopante("CASE");
            mOutros.setAnterior(mEscopo);


            getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", eCaso.getBranch("BODY"), mIntegralizante,mOutros);

            //   }
        }

        for (AST eOutro : mAST.getASTS()) {
            if (eOutro.mesmoTipo("OTHERS")) {

                Escopante mOutros = new Escopante("OTHERS");
                mOutros.setAnterior(mEscopo);


                getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", eOutro, mIntegralizante,mOutros);

            }
        }


    }

    public void atribuidor_return(String ePrefixo, AST mAST, Integralizante mIntegralizante,Escopante mEscopo) {

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mAST.getBranch("VALUE"), mIntegralizante,mEscopo);


    }

    public void atribuidor_execute(String ePrefixo, AST mAST, Integralizante mIntegralizante,Escopante mEscopo) {

        mValoramento.valoreSemRetorno(ePrefixo + "\t", mAST, mIntegralizante,mEscopo);


    }


    public void atribuidor_apply(String ePrefixo, AST mAST, Integralizante mIntegralizante,Escopante mEscopo) {

        mValoramento.valore(ePrefixo + "\t", mAST.getBranch("SETTABLE"), mIntegralizante,mEscopo);
        mValoramento.valore(ePrefixo + "\t", mAST.getBranch("VALUE"),mIntegralizante, mEscopo);


    }

    public void atribuidor_try(String ePrefixo, AST mAST, Integralizante mIntegralizante,Escopante mEscopo) {


        Escopante gAtribuindo = new Escopante("TRY");
        gAtribuindo.setAnterior(mEscopo);

        mIntegrador.mensagem(mAST.getImpressao());

        if (mAST.getBranch("LOGIC").mesmoNome("TRUE")) {

            if (mEscopo.existeNesse(mAST.getBranch("LOGIC").getValor())) {
            } else {
                mIntegrador.mensagem(" Variavel nao encontrada : " + mAST.getBranch("LOGIC").getValor());
                mIntegrador.getErros().add("Variavel nao encontrada : " + mAST.getBranch("LOGIC").getValor());
            }
        }
        if (mAST.getBranch("MESSAGE").mesmoNome("TRUE")) {

            if (mEscopo.existeNesse(mAST.getBranch("MESSAGE").getValor())) {
            } else {
                mIntegrador.mensagem(" Variavel nao encontrada : " + mAST.getBranch("MESSAGE").getValor());
                mIntegrador.getErros().add("Variavel nao encontrada : " + mAST.getBranch("MESSAGE").getValor());
            }
        }

        getValore_Escopo().atribuicao_escopo(ePrefixo + "\t", mAST.getBranch("BODY"), mIntegralizante,gAtribuindo);


    }
}
