package Sigmaz.S05_Executor;

import Sigmaz.S00_Utilitarios.AST;

public class Run_Refer {

    private RunTime mRunTime;
    private String mLocal;
    private Escopo mEscopo;

    public Run_Refer(RunTime eRunTime, Escopo eEscopo) {
        mRunTime = eRunTime;
        mLocal = "Run_Refer";
        mEscopo = eEscopo;
    }


    public void init(AST mAST) {


        for (AST ASTC : mAST.getASTS()) {

            String eRefer = ASTC.getNome();

            mEscopo.adicionarRefer(eRefer);

            boolean mEnc = false;

            for (AST ePacote : mRunTime.getGlobalPackages()) {
                if (ASTC.mesmoNome(eRefer)) {

                    for (AST mDentro : ePacote.getASTS()) {
                        //    System.out.println("\t\t\t -> Recebendo " + mDentro.getNome());
                        mEscopo.guardar(mDentro);
                    }

                    mEnc = true;
                    break;
                }
            }

            if (!mEnc) {
                mRunTime.errar(mLocal, "Pacote nao encontrado : " + eRefer);
            }

        }


    }


}
