package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_ExternRefered {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;


    public Run_ExternRefered(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_ExternRefered";

    }


    public void init(AST ASTCorrente) {

        String eNome = ASTCorrente.getNome();

        String eStruct = ASTCorrente.getBranch("STRUCT").getNome();
        String eExtern = ASTCorrente.getBranch("EXTERN").getNome();


        Run_Context mRun_Context = new Run_Context(mRunTime);

        boolean enc = false;

        for (AST ASTC : mRun_Context.getStructsContexto(mEscopo.getRefers())) {

            if (ASTC.mesmoTipo("STRUCT")) {
                if (ASTC.mesmoNome(eStruct)) {

                    //   System.out.println(ASTC.ImprimirArvoreDeInstrucoes());

                    boolean campo = false;
                    for (AST eCampo : ASTC.getBranch("BODY").getASTS()) {
                        if (eCampo.mesmoTipo("DEFINE") || eCampo.mesmoTipo("MOCKIZ")) {

                            if (eCampo.getBranch("VISIBILITY").mesmoNome("EXTERN")) {
                                if (eCampo.mesmoNome(eExtern)) {

                                    Run_GetType mRun_GetType = new Run_GetType(mRunTime, mEscopo);
                                    mRun_GetType.adicionarRefers(mEscopo.getRefersOcultas());

                                    String mTipagem = mRun_GetType.getTipagem(eCampo.getBranch("TYPE"));
                                    if (eCampo.mesmoTipo("MOCKIZ")) {
                                        mEscopo.criarExternRefered_Const(eNome, mTipagem, eStruct, eExtern);
                                    }else{
                                        mEscopo.criarExternRefered(eNome, mTipagem, eStruct, eExtern);
                                    }

                                    campo = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (!campo) {
                        mRunTime.getErros().add(mLocal + " - Campo Externo nao encontrado na STRUCT " + eStruct + " -> " + eExtern);
                    }

                    enc = true;
                    break;
                }
            }
        }

        if (!enc) {
            mRunTime.getErros().add(mLocal + " - STRUCT nao encontrada : " + eStruct);
        }


    }


}
