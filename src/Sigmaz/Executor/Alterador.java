package Sigmaz.Executor;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Alterador {

    private ArrayList<AItem> mAlterar;
    private ArrayList<AST> mAbstratos;

    public class AItem {
        private String mAbstrato;
        private AST mTipo;

        public AItem(String eAbstrato, AST eTipo) {
            mAbstrato = eAbstrato;
            mTipo = eTipo;
        }

        public String getAbstrato() {
            return mAbstrato;
        }

        public AST getTipo() {
            return mTipo;
        }

        public boolean mesmoAbstrato(String eAbstrato) {
            return mAbstrato.contentEquals(eAbstrato);
        }

    }

    public Alterador() {

        mAlterar = new ArrayList<AItem>();
        mAbstratos = new ArrayList<AST>();
    }

    public ArrayList<AItem> getAlteracoes() {
        return mAlterar;
    }

    public void adicionar(String eAbstrato, AST eTipo) {

        mAlterar.add(new AItem(eAbstrato, eTipo));
        mAbstratos.add(eTipo);

    }



    public void alterarTipo(AST ASTPai) {

        AST AST_Type = ASTPai.getBranch("TYPE");

        alterarTipoDeType(AST_Type);

    }



    public void alterarTipoDeType(AST ASTPai) {

        for (AItem eTipo : mAlterar) {

            if (ASTPai.mesmoValor("CONCRETE")) {
                if (ASTPai.mesmoNome(eTipo.getAbstrato())) {

                   // if (eTipo.getTipo().existeBranch("TYPE")){
                     //   ASTPai.espelhar(eTipo.getTipo().getBranch("TYPE"));
                  //  }else{
                        ASTPai.espelhar(eTipo.getTipo());
                    //}

                    break;
                }
            }else if(ASTPai.mesmoValor("GENERIC")){
                for (AST eSub : ASTPai.getASTS()) {
                    alterarTipoDeType(eSub);
                }
            }

        }


    }

    public void alterar(AST ASTPai) {

        for (AST eAST : ASTPai.getASTS()) {

            if (eAST.mesmoTipo("DEFINE")) {

                alterarTipo(eAST);

                if (eAST.existeBranch("VALUE")){
                    if (eAST.getBranch("VALUE").mesmoValor("INIT")){
                        alterar(eAST.getBranch("VALUE"));
                    }
                }

            } else if (eAST.mesmoTipo("MOCKIZ")) {

                alterarTipo(eAST);

                if (eAST.existeBranch("VALUE")){
                    if (eAST.getBranch("VALUE").mesmoValor("INIT")){
                        alterar(eAST.getBranch("VALUE"));
                    }
                }

            } else if (eAST.mesmoTipo("DEF")) {

                alterarTipo(eAST);

                if (eAST.existeBranch("VALUE")){
                    if (eAST.getBranch("VALUE").mesmoValor("INIT")){
                        alterar(eAST.getBranch("VALUE"));
                    }
                }

            } else if (eAST.mesmoTipo("MOC")) {

                alterarTipo(eAST);

                if (eAST.existeBranch("VALUE")){
                    if (eAST.getBranch("VALUE").mesmoValor("INIT")){
                        alterar(eAST.getBranch("VALUE"));
                    }
                }

            } else if (eAST.mesmoTipo("TYPE")) {

                alterarTipoDeType(eAST);

            } else if (eAST.mesmoTipo("BODY")) {

                alterar(eAST);
            } else if (eAST.mesmoTipo("OTHER")) {

                alterar(eAST);
            } else if (eAST.mesmoTipo("OTHERS")) {

                alterar(eAST);
            } else if (eAST.mesmoTipo("IF")) {

                alterar(eAST);


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

            } else if (eAST.mesmoTipo("ARGUMENTS")) {

               // alterar(eAST);


            } else if (eAST.mesmoTipo("FUNCTION")) {

                alterarTipo(eAST);

                alterar(eAST.getBranch("ARGUMENTS"));

                alterar(eAST.getBranch("BODY"));

            } else if (eAST.mesmoTipo("OPERATOR")) {

                alterarTipo(eAST);

                alterar(eAST.getBranch("ARGUMENTS"));

                alterar(eAST.getBranch("BODY"));
            } else if (eAST.mesmoTipo("DIRECTOR")) {

                alterarTipo(eAST);

                alterar(eAST.getBranch("ARGUMENTS"));

                alterar(eAST.getBranch("BODY"));

            } else if (eAST.mesmoTipo("ACTION")) {


                alterar(eAST.getBranch("BODY"));
                alterar(eAST.getBranch("ARGUMENTS"));

            } else if (eAST.mesmoTipo("RETURN")) {

                if (eAST.existeBranch("GENERIC")){

                    alterar(eAST.getBranch("GENERIC"));

                }

            } else if (eAST.mesmoTipo("INIT")) {

               if (eAST.existeBranch("ARGUMENTS")){
                   for(AST sAST : eAST.getBranch("ARGUMENTS").getASTS()){
                       if (sAST.mesmoTipo("ARGUMENT")) {

                         //  System.out.println("Alt Argument: " + sAST.getTipo());

                           alterarTipo(sAST);
                       }
                   }
               }

            } else {

                //System.out.println("Alterar Desconhecido : " + eAST.getTipo());

            }

        }

    }


}
