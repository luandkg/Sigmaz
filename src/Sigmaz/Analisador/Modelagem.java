package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Modelagem {

    private Analisador mAnalisador;

    private boolean mExterno;

    public Modelagem(Analisador eAnalisador) {

        mAnalisador = eAnalisador;
        mExterno = true;

    }

    public void init(ArrayList<AST> mTodos) {

        ArrayList<AST> mModelos = new ArrayList<AST>();

        ArrayList<AST> mEstruturasComModelos = new ArrayList<AST>();

        for (AST mAST : mTodos) {

            if (mAST.mesmoTipo("SIGMAZ")) {

                for (AST Struct_AST : mAST.getASTS()) {

                    if (Struct_AST.mesmoTipo("STRUCT")) {

                        AST AST_Model = Struct_AST.getBranch("MODEL");
                        if (AST_Model.mesmoValor("TRUE")) {
                            mEstruturasComModelos.add(Struct_AST);
                        }


                    } else if (Struct_AST.mesmoTipo("MODEL")) {
                        mModelos.add(Struct_AST);
                    }

                }

            }
        }

        mAnalisador.mensagem("------------------------- MODEL --------------------------");

        mAnalisador.mensagem(" MODELS : ");

        ArrayList<String> mNomes = new ArrayList<String>();

        for (AST Struct_AST : mModelos) {
            mAnalisador.mensagem("\t - " + Struct_AST.getNome());
            mNomes.add(Struct_AST.getNome());
        }
        mAnalisador.mensagem(" STRUCTS : ");
        for (AST Struct_AST : mEstruturasComModelos) {
            mAnalisador.mensagem("\t - " + Struct_AST.getNome() + " -> " + Struct_AST.getBranch("MODEL").getNome());
        }

        mAnalisador.mensagem("");

        for (AST Struct_AST : mEstruturasComModelos) {

            String eModel_Nome = Struct_AST.getBranch("MODEL").getNome();

            mAnalisador.mensagem(" MODELANDO : " + Struct_AST.getNome());
            mAnalisador.mensagem("\t - " + Struct_AST.getNome());

            if (mNomes.contains(eModel_Nome)) {

                modelar(Struct_AST, eModel_Nome, mModelos);

            } else {
                mAnalisador.getErros().add("  - Model " + eModel_Nome + " : Nao encontrado ");
            }


        }

        mAnalisador.mensagem("---------------------------------------------------");


    }


    public void modelar(AST Struct, String eModelo, ArrayList<AST> mModelos) {

        AST Modelo = ProcurarModelo(eModelo, mModelos);


        for (AST Parte : Modelo.getASTS()) {


            if (Parte.mesmoTipo("DEFINE")) {

                Verificar_Define(Struct, Parte);
            } else if (Parte.mesmoTipo("MOCKIZ")) {

                Verificar_Mockiz(Struct, Parte);
            } else if (Parte.mesmoTipo("ACTION")) {

                Verificar_Action(Struct, Parte);

            } else if (Parte.mesmoTipo("FUNCTION")) {

                Verificar_Function(Struct, Parte);

            } else {

                mAnalisador.mensagem("\t - Precisa : " + Parte.getTipo() + " " + Parte.getNome());


            }

        }

    }

    public void Verificar_Define(AST Struct, AST Modelo) {

        boolean enc = false;

        for (AST DentroStruct : Struct.getBranch("BODY").getASTS()) {

            if (DentroStruct.mesmoTipo("DEFINE")) {
                if (DentroStruct.mesmoNome(Modelo.getNome())) {

                    String eModelo_Tipo = getTipagem(Modelo);
                    String eStruct_Tipo = getTipagem(DentroStruct);

                    if (eModelo_Tipo.contentEquals(eStruct_Tipo)) {

                    } else {
                        mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa ter um DEFINE : " + DentroStruct.getNome() + " : " + eModelo_Tipo);
                    }


                    enc = true;
                    break;
                }
            }

        }

        if (!enc) {
            mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa ter um DEFINE : " + Modelo.getNome() + " : " + getTipagem(Modelo));
        }


    }

    public void Verificar_Mockiz(AST Struct, AST Modelo) {

        boolean enc = false;

        for (AST DentroStruct : Struct.getBranch("BODY").getASTS()) {

            if (DentroStruct.mesmoTipo("MOCKIZ")) {
                if (DentroStruct.mesmoNome(Modelo.getNome())) {

                    String eModelo_Tipo = getTipagem(Modelo);
                    String eStruct_Tipo = getTipagem(DentroStruct);

                    if (eModelo_Tipo.contentEquals(eStruct_Tipo)) {

                    } else {
                        mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa ter um MOCKIZ : " + DentroStruct.getNome() + " : " + eModelo_Tipo);
                    }


                    enc = true;
                    break;
                }
            }

        }

        if (!enc) {
            mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa ter um MOCKIZ : " + Modelo.getNome() + " : " + getTipagem(Modelo));
        }


    }


    public void Verificar_Action(AST Struct, AST Modelo) {

        boolean enc = false;
        boolean alguma = false;

        for (AST DentroStruct : Struct.getBranch("BODY").getASTS()) {

            if (DentroStruct.mesmoTipo("ACTION")) {
                if (DentroStruct.mesmoNome(Modelo.getNome())) {
                    enc = true;

                    String eModelo_Parametragem = getParametragem(Modelo);
                    String eStruct_Parametragem = getParametragem(DentroStruct);

                    if (eModelo_Parametragem.contentEquals(eStruct_Parametragem)) {
                        alguma = true;
                        break;

                    }

                }
            }

        }

        if (!alguma) {
            mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa ter um ACTION : " + Modelo.getNome() + "(" + getParametragem(Modelo) + ")");
        }


        if (!enc) {
            mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa ter um ACTION : " + Modelo.getNome() + " : " + getParametragem(Modelo));
        }


    }

    public void Verificar_Function(AST Struct, AST Modelo) {

        boolean enc = false;
        boolean alguma = false;

        for (AST DentroStruct : Struct.getBranch("BODY").getASTS()) {

            if (DentroStruct.mesmoTipo("FUNCTION")) {
                if (DentroStruct.mesmoNome(Modelo.getNome())) {
                    enc = true;

                    String eModelo_Tipo = getTipagem(Modelo);
                    String eStruct_Tipo = getTipagem(DentroStruct);

                    if (eModelo_Tipo.contentEquals(eStruct_Tipo)) {


                        String eModelo_Parametragem = getParametragem(Modelo);
                        String eStruct_Parametragem = getParametragem(DentroStruct);

                        if (eModelo_Parametragem.contentEquals(eStruct_Parametragem)) {
                            alguma = true;
                            break;
                        }

                    }


                }
            }

        }


        if (!alguma) {
            mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa ter um FUNCTION : " + Modelo.getNome() + " ( " + getParametragem(Modelo) + ") : " +getTipagem(Modelo) );
        }

        if (!enc) {
            mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa ter um FUNCTION : " + Modelo.getNome() + " ( " + getParametragem(Modelo) + ") : " + getTipagem(Modelo));
        }


    }


    public String getTipagem(AST ASTpai) {

        String mTipagem = "";


        if (ASTpai.existeBranch("GENERIC")){

            AST mGENERIC = ASTpai.getBranch("GENERIC");


            if (mGENERIC.mesmoNome("TRUE")) {
                mTipagem = ASTpai.getValor();


                for (AST eTipando : mGENERIC.getASTS()) {
                    mTipagem += "<" + eTipando.getNome() + ">";
                }

            } else {
                mTipagem = ASTpai.getValor();
            }

        } else{

            mTipagem = ASTpai.getValor();

        }




        return mTipagem;
    }

    public String getParametragem(AST ASTpai) {
        AST mArguments = ASTpai.getBranch("ARGUMENTS");

        String eParam = "";

        for (AST eTipando : mArguments.getASTS()) {
            eParam += "<" + eTipando.getValor() + ">";
        }

        return eParam;
    }

    public AST ProcurarModelo(String eNome, ArrayList<AST> mModelos) {
        AST mRet = null;
        for (AST Procurando : mModelos) {
            if (Procurando.mesmoNome(eNome)) {
                mRet = Procurando;
            }
        }
        return mRet;
    }


}
