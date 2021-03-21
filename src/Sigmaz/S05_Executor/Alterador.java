package Sigmaz.S05_Executor;

import java.util.ArrayList;

import Sigmaz.S08_Utilitarios.AST;

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

        if (ASTPai != null) {
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
                } else if (ASTPai.mesmoValor("GENERIC")) {
                    for (AST eSub : ASTPai.getASTS()) {
                        alterarTipoDeType(eSub);
                    }
                }

            }
        }



    }

    public void alterarTipoValue(AST eAST){

        if (eAST.existeBranch("VALUE")) {
            if (eAST.getBranch("VALUE").mesmoValor("INIT")) {
                alterar(eAST.getBranch("VALUE"));
            }

            if (eAST.getBranch("VALUE").existeBranch("TYPE")){
                alterarTipoDeType(eAST.getBranch("VALUE").getBranch("TYPE"));
            }

        }

    }
    public void alterar(AST ASTPai) {

        for (AST eAST : ASTPai.getASTS()) {

            if (eAST.mesmoTipo("DEFINE")) {

                alterarTipo(eAST);

                alterarTipoValue(eAST);


            } else if (eAST.mesmoTipo("TYPE")) {

                alterarTipoDeType(eAST);

            } else if (eAST.mesmoTipo("GENERICS")) {

                for(AST oAST : eAST.getASTS()){

                    if (oAST.mesmoTipo("TYPE")){
                        alterarTipoDeType(oAST);
                    }

                }

            } else if (eAST.mesmoTipo("MOCKIZ")) {

                alterarTipo(eAST);

                alterarTipoValue(eAST);


            } else if (eAST.mesmoTipo("DEF")) {

                alterarTipo(eAST);

                alterarTipoValue(eAST);

            } else if (eAST.mesmoTipo("MOC")) {

                alterarTipo(eAST);

                alterarTipoValue(eAST);

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

            } else if (eAST.mesmoTipo("WHILE")) {

                alterar(eAST);

            } else if (eAST.mesmoTipo("APPLY")) {

                alterar(eAST);

                if (eAST.existeBranch("VALUE")) {
                    alterar(eAST.getBranch("VALUE"));
                }


            } else if (eAST.mesmoTipo("VALUE")) {

                alterar(eAST);
            } else if (eAST.mesmoTipo("GENERIC")) {

                alterar(eAST);
            } else if (eAST.mesmoTipo("GENERICS")) {

                alterar(eAST);

            } else if (eAST.mesmoTipo("ARGUMENT")) {

                alterarTipo(eAST);
            } else if (eAST.mesmoTipo("ARGUMENTS")) {

                alterar(eAST);

            } else if (eAST.mesmoTipo("GENERIC")) {

                alterar(eAST);


            } else if (eAST.mesmoTipo("FUNCTION")) {

                alterarTipo(eAST);

                alterar(eAST.getBranch("ARGUMENTS"));


                if (eAST.existeBranch("BODY")) {
                    alterar(eAST.getBranch("BODY"));
                }

            } else if (eAST.mesmoTipo("OPERATOR")) {

                alterarTipo(eAST);

                alterar(eAST.getBranch("ARGUMENTS"));

                alterar(eAST.getBranch("BODY"));
            } else if (eAST.mesmoTipo("DIRECTOR")) {

                alterarTipo(eAST);

                alterar(eAST.getBranch("ARGUMENTS"));

                if (eAST.existeBranch("BODY")) {
                    alterar(eAST.getBranch("BODY"));
                }


            } else if (eAST.mesmoTipo("ACTION")) {

                if (eAST.existeBranch("BODY")) {
                    alterar(eAST.getBranch("BODY"));
                }
                alterar(eAST.getBranch("ARGUMENTS"));

            } else if (eAST.mesmoTipo("GETTER")) {

                alterarTipo(eAST);

                alterar(eAST.getBranch("ARGUMENTS"));


                if (eAST.existeBranch("BODY")) {
                    alterar(eAST.getBranch("BODY"));
                }
            } else if (eAST.mesmoTipo("SETTER")) {

                if (eAST.existeBranch("BODY")) {
                    alterar(eAST.getBranch("BODY"));
                }

                alterar(eAST.getBranch("ARGUMENTS"));

                alterar(eAST.getBranch("VALUES"));

            } else if (eAST.mesmoTipo("RETURN")) {

                if (eAST.existeBranch("GENERIC")) {

                    alterar(eAST.getBranch("GENERIC"));

                } else if (eAST.existeBranch("VALUE")) {

                    alterar(eAST.getBranch("VALUE"));


                }

            } else if (eAST.mesmoTipo("VALUE")) {

                alterar(eAST);

            } else if (eAST.mesmoTipo("EXECUTE_AUTO")) {

                alterar(eAST);

            } else if (eAST.mesmoTipo("INIT")) {


                for (AST oAST : eAST.getASTS()) {

                    if (oAST.mesmoTipo("ARGUMENTS")) {

                        for (AST sAST : eAST.getBranch("ARGUMENTS").getASTS()) {
                            if (sAST.mesmoTipo("ARGUMENT")) {

                                //  System.out.println("Alt Argument: " + sAST.getTipo());

                                alterarTipo(sAST);
                            }
                        }

                    } else if (oAST.mesmoTipo("BODY")) {

                        alterar(oAST);

                    }

                }

            } else {

                //System.out.println("Alterar Desconhecido : " + eAST.getTipo());

            }

        }

    }


}
