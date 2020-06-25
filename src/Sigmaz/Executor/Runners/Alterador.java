package Sigmaz.Executor.Runners;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Alterador {

    private ArrayList<AItem> mAlterar;
    private ArrayList<String> mAbstratos;

    public class AItem {
        private String mAbstrato;
        private String mTipo;

        public AItem(String eAbstrato, String eTipo) {
            mAbstrato = eAbstrato;
            mTipo = eTipo;
        }

        public String getAbstrato() {
            return mAbstrato;
        }

        public String getTipo() {
            return mTipo;
        }

        public boolean mesmoAbstrato(String eAbstrato) {
            return mAbstrato.contentEquals(eAbstrato);
        }

    }

    public Alterador() {

        mAlterar = new ArrayList<AItem>();
        mAbstratos = new ArrayList<String>();
    }

    public ArrayList<AItem> getAlteracoes() {
        return mAlterar;
    }

    public void adicionar(String eAbstrato, String eTipo) {

        mAlterar.add(new AItem(eAbstrato, eTipo));
        mAbstratos.add(eTipo);

    }

    public void alterarTipo(AST ASTPai) {

        for (AItem eTipo : mAlterar) {

            if (ASTPai.mesmoValor(eTipo.getAbstrato())) {
                ASTPai.setValor(eTipo.getTipo());


                break;
            }

        }


    }

    public void alterarTipoDeType(AST ASTPai) {

        for (AItem eTipo : mAlterar) {

            if (ASTPai.mesmoNome(eTipo.getAbstrato())) {
                ASTPai.setNome(eTipo.getTipo());


                break;
            }

        }


    }

    public void alterar(AST ASTPai) {

        for (AST eAST : ASTPai.getASTS()) {

            if (eAST.mesmoTipo("DEFINE")) {

                alterarTipo(eAST);

                alterar(eAST.getBranch("GENERIC"));


            } else if (eAST.mesmoTipo("MOCKIZ")) {

                alterarTipo(eAST);

            } else if (eAST.mesmoTipo("DEF")) {

                alterarTipo(eAST);

            } else if (eAST.mesmoTipo("MOC")) {

                alterarTipo(eAST);

            } else if (eAST.mesmoTipo("TYPE")) {

                alterarTipoDeType(eAST);

            } else if (eAST.mesmoTipo("IF")) {

                alterar(eAST.getBranch("BODY"));

            } else if (eAST.mesmoTipo("CONDITION")) {

                alterar(eAST);

            } else if (eAST.mesmoTipo("APPLY")) {

                alterar(eAST);

            } else if (eAST.mesmoTipo("VALUE")) {

                alterar(eAST);
            } else if (eAST.mesmoTipo("GENERIC")) {

                alterar(eAST);

            } else if (eAST.mesmoTipo("ARGUMENT")) {

                alterarTipo(eAST);


            } else if (eAST.mesmoTipo("FUNCTION")) {

                alterarTipo(eAST);

                alterar(eAST.getBranch("BODY"));

            } else if (eAST.mesmoTipo("ACTION")) {

                alterarTipo(eAST);

                alterar(eAST.getBranch("BODY"));
                alterar(eAST.getBranch("ARGUMENTS"));

            }

        }

    }


}
