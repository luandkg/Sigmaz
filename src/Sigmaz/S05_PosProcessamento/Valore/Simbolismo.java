package Sigmaz.S05_PosProcessamento.Valore;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.AgrupadorAST;
import Sigmaz.S05_PosProcessamento.Mensageiro;
import Sigmaz.S05_PosProcessamento.Pronoco.*;
import Sigmaz.S06_Executor.Debuggers.Simplificador;

import java.util.ArrayList;

public class Simbolismo {

    private Mensageiro mMensageiro;
    private Simplificador mSimplificador;
    private AgrupadorAST mAgrupadorAST;

    public Simbolismo(Mensageiro eMensageiro) {

        mMensageiro = eMensageiro;
        mSimplificador = new Simplificador();
        mAgrupadorAST = new AgrupadorAST();

    }

    public void realizarSimbolismo(String ePrefixo, AST eASTPai, Pronoco mAtribuindos) {

        String eNomeGeral = "Simbolo";

        ArrayList<AST> mCalls = new ArrayList<AST>();

        ArrayList<AST> mActions = new ArrayList<AST>();
        ArrayList<AST> mFunctions = new ArrayList<AST>();
        ArrayList<AST> mAutos = new ArrayList<AST>();
        ArrayList<AST> mFunctores = new ArrayList<AST>();

        ArrayList<AST> mStructs = new ArrayList<AST>();
        ArrayList<AST> mExternals = new ArrayList<AST>();
        ArrayList<AST> mStages = new ArrayList<AST>();
        ArrayList<AST> mTypes = new ArrayList<AST>();
        ArrayList<AST> mModels = new ArrayList<AST>();


        mAgrupadorAST.agruparCalls(eASTPai, mCalls);

        mAgrupadorAST.agruparActionsFunctions(eASTPai, mActions, mFunctions);
        mAgrupadorAST.agruparAutosFunctores(eASTPai, mAutos, mFunctores);

        mAgrupadorAST.agruparTipos(eASTPai, mStages, mTypes, mStructs, mExternals, mModels);




        realizarSimbolismo_Calls(eNomeGeral,ePrefixo, mAtribuindos, mCalls);

        realizarSimbolismo_ActionsFunctions(eNomeGeral,ePrefixo, mAtribuindos, mActions, mFunctions);

        realizarSimbolismo_AutosFunctores(eNomeGeral,ePrefixo, mAtribuindos, mAutos, mFunctores);

        realizarSimbolismo_Types(eNomeGeral,ePrefixo, mAtribuindos, mTypes);
        realizarSimbolismo_Models(eNomeGeral,ePrefixo, mAtribuindos, mModels);

        realizarSimbolismo_StagesExternals(eNomeGeral,ePrefixo, mAtribuindos, mStages);
        realizarSimbolismo_StructsExternals(eNomeGeral,ePrefixo, mAtribuindos, mStructs);




    }

    public void realizarSimbolismoExterno(String ePrefixo, AST eASTPai, Pronoco mAtribuindos) {


        String eNomeGeral = "Simbolo Externo";

        ArrayList<AST> mCalls = new ArrayList<AST>();

        ArrayList<AST> mActions = new ArrayList<AST>();
        ArrayList<AST> mFunctions = new ArrayList<AST>();
        ArrayList<AST> mAutos = new ArrayList<AST>();
        ArrayList<AST> mFunctores = new ArrayList<AST>();

        ArrayList<AST> mStructs = new ArrayList<AST>();
        ArrayList<AST> mExternals = new ArrayList<AST>();
        ArrayList<AST> mStages = new ArrayList<AST>();
        ArrayList<AST> mTypes = new ArrayList<AST>();
        ArrayList<AST> mModels = new ArrayList<AST>();


        mAgrupadorAST.agruparCalls(eASTPai, mCalls);

        mAgrupadorAST.agruparActionsFunctions(eASTPai, mActions, mFunctions);
        mAgrupadorAST.agruparAutosFunctores(eASTPai, mAutos, mFunctores);

        mAgrupadorAST.agruparTipos(eASTPai, mStages, mTypes, mStructs, mExternals, mModels);




        realizarSimbolismo_Calls(eNomeGeral,ePrefixo, mAtribuindos, mCalls);

        realizarSimbolismo_ActionsFunctions(eNomeGeral,ePrefixo, mAtribuindos, mActions, mFunctions);

        realizarSimbolismo_AutosFunctores(eNomeGeral,ePrefixo, mAtribuindos, mAutos, mFunctores);

        realizarSimbolismo_Types(eNomeGeral,ePrefixo, mAtribuindos, mTypes);
        realizarSimbolismo_Models(eNomeGeral,ePrefixo, mAtribuindos, mModels);

        realizarSimbolismo_StagesExternals(eNomeGeral,ePrefixo, mAtribuindos, mStages);
        realizarSimbolismo_StructsExternals(eNomeGeral,ePrefixo, mAtribuindos, mStructs);

        realizarSimbolismo_Externals(eNomeGeral,ePrefixo, mAtribuindos, mExternals);




    }

    public void realizarSimbolismo_Calls(String eNomeGeral,String ePrefixo, Pronoco mAtribuindos, ArrayList<AST> mCalls) {


        for (AST mAST : mCalls) {

            if (mMensageiro.getErros().size() > 0) {
                break;
            }

            mMensageiro.mensagem(ePrefixo + eNomeGeral+ " Call : " + mSimplificador.getCall(mAST));

        }

    }

    public void realizarSimbolismo_ActionsFunctions(String eNomeGeral,String ePrefixo, Pronoco mAtribuindos, ArrayList<AST> mActions, ArrayList<AST> mFunctions) {


        for (AST mAST : mActions) {

            if (mMensageiro.getErros().size() > 0) {
                break;
            }

            Pronoco_Action mS = new Pronoco_Action(mAST);
            mAtribuindos.adicionarAction(mS);
            mMensageiro.mensagem(ePrefixo + eNomeGeral + " Action " + mS.getDefinicao());

        }

        for (AST mAST : mFunctions) {

            if (mMensageiro.getErros().size() > 0) {
                break;
            }

            Pronoco_Function mS = new Pronoco_Function(mAST);
            mAtribuindos.adicionarFunction(mS);

            mMensageiro.mensagem(ePrefixo + eNomeGeral+ " Function " + mS.getDefinicao());

        }

    }

    public void realizarSimbolismo_AutosFunctores(String eNomeGeral,String ePrefixo, Pronoco mAtribuindos, ArrayList<AST> mAutos, ArrayList<AST> mFunctores) {

        for (AST mAST : mAutos) {

            if (mMensageiro.getErros().size() > 0) {
                break;
            }

            Pronoco_Auto mS = new Pronoco_Auto(mAST);
            mAtribuindos.adicionarAuto(mS);

            mMensageiro.mensagem(ePrefixo + eNomeGeral+ " Auto " + mS.getDefinicao());

        }


        for (AST mAST : mFunctores) {

            if (mMensageiro.getErros().size() > 0) {
                break;
            }

            Pronoco_Functor mS = new Pronoco_Functor(mAST);
            mAtribuindos.adicionarFunctor(mS);

            mMensageiro.mensagem(ePrefixo + eNomeGeral+ " Functor " + mS.getDefinicao());

        }

    }

    public void realizarSimbolismo_StructsExternals(String eNomeGeral,String ePrefixo, Pronoco mAtribuindos, ArrayList<AST> mStructs) {

        for (AST mAST : mStructs) {

            if (mMensageiro.getErros().size() > 0) {
                break;
            }

            Pronoco_Struct mS = new Pronoco_Struct(mAST);
            mAtribuindos.adicionarStruct(mS);


            Pronoco_Extern mE = new Pronoco_Extern(mAST);
            mAtribuindos.adicionarExtern(mE);

            mMensageiro.mensagem(ePrefixo + eNomeGeral+" Struct " + mAST.getNome());
            mMensageiro.mensagem(ePrefixo + eNomeGeral+" External " + mAST.getNome());

        }


    }

    public void realizarSimbolismo_Externals(String eNomeGeral,String ePrefixo, Pronoco mAtribuindos,  ArrayList<AST> mExternals) {


        for (AST mAST : mExternals) {

            if (mMensageiro.getErros().size() > 0) {
                break;
            }

            Pronoco_Extern mE = new Pronoco_Extern(mAST);
            mAtribuindos.adicionarExtern(mE);

            mMensageiro.mensagem(ePrefixo + eNomeGeral+" External " + mAST.getNome());

        }

    }


    public void realizarSimbolismo_Types(String eNomeGeral,String ePrefixo, Pronoco mAtribuindos, ArrayList<AST> mTypes) {


        for (AST mAST : mTypes) {

            if (mMensageiro.getErros().size() > 0) {
                break;
            }

            Pronoco_Type mE = new Pronoco_Type(mAST);
            mAtribuindos.adicionarTypes(mE);

            mMensageiro.mensagem(ePrefixo + eNomeGeral+" Type " + mAST.getNome());

        }

    }

    public void realizarSimbolismo_StagesExternals(String eNomeGeral,String ePrefixo, Pronoco mAtribuindos, ArrayList<AST> mStages) {


        for (AST mAST : mStages) {

            if (mMensageiro.getErros().size() > 0) {
                break;
            }

            Pronoco_Stages ms = new Pronoco_Stages(mAST);


            AST eStages = mAST.getBranch("STAGES");

            for (AST sAST : eStages.getASTS()) {
                ms.adicionarStage(sAST.getNome());
            }


            mAtribuindos.adicionarStages(ms);

            mMensageiro.mensagem(ePrefixo +eNomeGeral+ " Stages " + mAST.getNome());

            Pronoco_Extern me = new Pronoco_Extern(mAST);

            mAtribuindos.adicionarExtern(me);

            mMensageiro.mensagem(ePrefixo +eNomeGeral+ " Extern " + mAST.getNome());


        }

    }

    public void realizarSimbolismo_Models(String eNomeGeral,String ePrefixo, Pronoco mAtribuindos, ArrayList<AST> mModels) {


        for (AST mAST : mModels) {

            if (mMensageiro.getErros().size() > 0) {
                break;
            }



            mMensageiro.mensagem(ePrefixo + eNomeGeral+" Model " + mAST.getNome()+ " ->> IMPLEMENTAR");

        }

    }


}
