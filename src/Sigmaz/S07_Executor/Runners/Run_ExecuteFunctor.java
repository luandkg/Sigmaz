package Sigmaz.S07_Executor.Runners;

import Sigmaz.S00_Utilitarios.Utilitario;
import Sigmaz.S07_Executor.Alterador;
import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Indexador.Index_Function;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.RunTime;
import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class Run_ExecuteFunctor {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;


    public Run_ExecuteFunctor(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_ExecuteFunctor";

    }


    public Item init(AST ASTCorrente,String eRetorno) {

        //System.out.println(ASTCorrente.ImprimirArvoreDeInstrucoes());

        int iG = ASTCorrente.getBranch("GENERICS").getASTS().size();
        int iA = ASTCorrente.getBranch("ARGUMENTS").getASTS().size();

        boolean enc_nome = false;
        boolean enc_argumentos = false;
        boolean enc_completo = false;

        AST Guardado = null;

        for (AST eAST : mEscopo.getGuardadosCompleto()) {

            if (eAST.mesmoTipo("PROTOTYPE_FUNCTOR")) {

                int fG = eAST.getBranch("GENERICS").getASTS().size();
                int fA = eAST.getBranch("ARGUMENTS").getASTS().size();

                if (eAST.mesmoNome(ASTCorrente.getNome())) {
                    if (iG == fG) {

                        if (iA == fA) {

                            //    System.out.println(eAST.ImprimirArvoreDeInstrucoes());
                            Guardado = eAST.copiar();


                            enc_completo = true;
                            break;
                        }
                        enc_argumentos = true;


                    }
                    enc_nome = true;
                }


            }

        }




        if (enc_completo) {

            String gTipagem = getTipagemGenerica(Guardado.getBranch("GENERICS").getASTS());


            Alterador mAlterador = new Alterador();
            int ii = 0;
            for (AST eGen : ASTCorrente.getBranch("GENERICS").getASTS()) {

                mAlterador.adicionar(Guardado.getBranch("GENERICS").getASTS().get(ii).getNome(), eGen);

                // System.out.println("Trocando " + Guardado.getBranch("GENERICS").getASTS().get(ii).getNome() + " por " + eGen.ImprimirArvoreDeInstrucoes());
                ii += 1;
            }


            mAlterador.alterar(Guardado);

            // System.out.println(Guardado.ImprimirArvoreDeInstrucoes());

            //System.out.println("FUNCTOR");
            //System.out.println(Guardado.getImpressao());


            Run_Arguments mPreparadorDeArgumentos = new Run_Arguments();

            ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, mEscopo, ASTCorrente.getBranch("ARGUMENTS"));
            Utilitario mUtilitario = new Utilitario();

            String mTipagem = mUtilitario.getArgumentos(mArgumentos);

            Index_Function mIndex_Function = new Index_Function(mRunTime, mEscopo, Guardado);
            mIndex_Function.resolverTipagem(mEscopo.getRefers());

            Run_Arguments mRunArguments = new Run_Arguments();

            int contagem = mRunArguments.conferirArgumentos(mRunTime, mIndex_Function.getArgumentos(), mArgumentos);
            if (contagem == mArgumentos.size()) {
                Run_Escopo mRun_Escopo = new Run_Escopo();

             return   mRun_Escopo.executar_ComRetorno(mRunTime, mEscopo, mIndex_Function, mArgumentos,eRetorno);

            } else {

                mRunTime.errar(mLocal, gTipagem + " " + mIndex_Function.getDefinicao() + " : Argumentos incompativeis : " + mTipagem);

            }


        } else {

            if (enc_nome) {

                if (enc_argumentos) {
                    mRunTime.getErros().add("AUTO  " + ASTCorrente.getNome() + " : Nao encontrado com mesmo numero de argumentos !");
                } else {
                    mRunTime.getErros().add("AUTO  " + ASTCorrente.getNome() + " : Nao encontrado com mesmo numero de genericos !");
                }

            } else {

                mRunTime.getErros().add("AUTO  " + ASTCorrente.getNome() + " : Nao encontrado !");

            }

        }

        return null;

    }


    public String getTipagemGenerica(ArrayList<AST> mArgumentos) {


        String mTipagem = "";


        int i = 0;
        int o = mArgumentos.size();
        for (AST ie : mArgumentos) {

            i += 1;

            if (i < o) {
                mTipagem += ie.getNome() + " , ";
            } else {
                mTipagem += ie.getNome() + " ";
            }


        }

        return " < " + mTipagem + "> ";

    }

}
