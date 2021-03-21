package Sigmaz.S03_Integrador;

import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S08_Utilitarios.AgrupadorAST;
import Sigmaz.S05_Executor.Debuggers.Simplificador;

import java.util.ArrayList;

public class Simbolismo {

    private Integrador mIntegrador;
    private Simplificador mSimplificador;
    private AgrupadorAST mAgrupadorAST;

    public Simbolismo(Integrador eIntegrador) {

        mIntegrador = eIntegrador;
        mSimplificador = new Simplificador();
        mAgrupadorAST = new AgrupadorAST();

    }

    public void realizarSimbolismo(String ePrefixo, AST eASTPai, Integralizante mIntegralizante) {

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


        realizarSimbolismo_Calls(eNomeGeral, ePrefixo, mIntegralizante, mCalls);

        realizarSimbolismo_ActionsFunctions(eNomeGeral, ePrefixo, mIntegralizante, mActions, mFunctions);

        realizarSimbolismo_AutosFunctores(eNomeGeral, ePrefixo, mIntegralizante, mAutos, mFunctores);

        realizarSimbolismo_Types(eNomeGeral, ePrefixo, mIntegralizante, mTypes);
        realizarSimbolismo_Models(eNomeGeral, ePrefixo, mIntegralizante, mModels);

        realizarSimbolismo_StagesExternals(eNomeGeral, ePrefixo, mIntegralizante, mStages);

        realizarSimbolismo_StructsExternals(eNomeGeral, ePrefixo, mIntegralizante, mStructs);

        realizarSimbolismo_Externals(eNomeGeral, ePrefixo, mIntegralizante, mExternals);


    }

    public void realizarSimbolismoExterno(String ePrefixo, AST eASTPai, Integralizante mIntegralizante) {


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


        realizarSimbolismo_Calls(eNomeGeral, ePrefixo, mIntegralizante, mCalls);

        realizarSimbolismo_ActionsFunctions(eNomeGeral, ePrefixo, mIntegralizante, mActions, mFunctions);

        realizarSimbolismo_AutosFunctores(eNomeGeral, ePrefixo, mIntegralizante, mAutos, mFunctores);

        realizarSimbolismo_Types(eNomeGeral, ePrefixo, mIntegralizante, mTypes);
        realizarSimbolismo_Models(eNomeGeral, ePrefixo, mIntegralizante, mModels);

        realizarSimbolismo_StagesExternals(eNomeGeral, ePrefixo, mIntegralizante, mStages);
        realizarSimbolismo_StructsExternals(eNomeGeral, ePrefixo, mIntegralizante, mStructs);

        realizarSimbolismo_Externals(eNomeGeral, ePrefixo, mIntegralizante, mExternals);


    }

    public void realizarSimbolismo_Calls(String eNomeGeral, String ePrefixo, Integralizante mIntegralizante, ArrayList<AST> mCalls) {


        for (AST mAST : mCalls) {

            if (mIntegrador.getErros().size() > 0) {
                break;
            }

            mIntegrador.mensagem(ePrefixo + eNomeGeral + " Call : " + mSimplificador.getCall(mAST));

        }

    }

    public void realizarSimbolismo_ActionsFunctions(String eNomeGeral, String ePrefixo, Integralizante mIntegralizante, ArrayList<AST> mActions, ArrayList<AST> mFunctions) {


        for (AST mAST : mActions) {

            if (mIntegrador.getErros().size() > 0) {
                break;
            }

            mIntegralizante.adicionar_Action(mAST);

            mIntegrador.mensagem(ePrefixo + eNomeGeral + " Action " + mSimplificador.getAction(mAST));

        }

        for (AST mAST : mFunctions) {

            if (mIntegrador.getErros().size() > 0) {
                break;
            }

            mIntegralizante.adicionar_Function(mAST);

            mIntegrador.mensagem(ePrefixo + eNomeGeral + " Function " + mSimplificador.getFuction(mAST));

        }

    }

    public void realizarSimbolismo_AutosFunctores(String eNomeGeral, String ePrefixo, Integralizante mIntegralizante, ArrayList<AST> mAutos, ArrayList<AST> mFunctores) {

        for (AST mAST : mAutos) {

            if (mIntegrador.getErros().size() > 0) {
                break;
            }


            mIntegrador.mensagem(ePrefixo + eNomeGeral + " Auto " + mSimplificador.getAuto(mAST));

        }


        for (AST mAST : mFunctores) {

            if (mIntegrador.getErros().size() > 0) {
                break;
            }


            mIntegrador.mensagem(ePrefixo + eNomeGeral + " Functor " + mSimplificador.getFunctor(mAST));

        }

    }

    public void realizarSimbolismo_StructsExternals(String eNomeGeral, String ePrefixo, Integralizante mIntegralizante, ArrayList<AST> mStructs) {

        for (AST mAST : mStructs) {

            if (mIntegrador.getErros().size() > 0) {
                break;
            }

            mIntegralizante.adicionar_Struct(mAST);
            mIntegralizante.adicionar_External(mAST);


            mIntegrador.mensagem(ePrefixo + eNomeGeral + " Struct " + mSimplificador.getStruct(mAST));
            mIntegrador.mensagem(ePrefixo + eNomeGeral + " External " + mSimplificador.getExternal(mAST));


        }


    }

    public void realizarSimbolismo_Externals(String eNomeGeral, String ePrefixo, Integralizante mIntegralizante, ArrayList<AST> mExternals) {


        for (AST mAST : mExternals) {

            if (mIntegrador.getErros().size() > 0) {
                break;
            }

            mIntegralizante.adicionar_External(mAST);

            mIntegrador.mensagem(ePrefixo + eNomeGeral + " External " + mAST.getNome());

        }

    }


    public void realizarSimbolismo_Types(String eNomeGeral, String ePrefixo, Integralizante mIntegralizante, ArrayList<AST> mTypes) {


        for (AST mAST : mTypes) {

            if (mIntegrador.getErros().size() > 0) {
                break;
            }

            mIntegralizante.adicionar_Type(mAST);

            mIntegrador.mensagem(ePrefixo + eNomeGeral + " Type " + mAST.getNome());

        }

    }

    public void realizarSimbolismo_StagesExternals(String eNomeGeral, String ePrefixo, Integralizante mIntegralizante, ArrayList<AST> mStages) {


        for (AST mAST : mStages) {

            if (mIntegrador.getErros().size() > 0) {
                break;
            }



            mIntegrador.mensagem(ePrefixo + eNomeGeral + " Stages " + mAST.getNome());

            mIntegralizante.adicionar_Stages(mAST);
            mIntegralizante.adicionar_External(mAST);


            mIntegrador.mensagem(ePrefixo + eNomeGeral + " Extern " + mAST.getNome());


        }

    }

    public void realizarSimbolismo_Models(String eNomeGeral, String ePrefixo, Integralizante mIntegralizante, ArrayList<AST> mModels) {


        for (AST mAST : mModels) {

            if (mIntegrador.getErros().size() > 0) {
                break;
            }


            mIntegrador.mensagem(ePrefixo + eNomeGeral + " Model " + mAST.getNome() + " ->> IMPLEMENTAR");

        }

    }


}
