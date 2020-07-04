package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Package {

    private Analisador mAnalisador;
    private ArrayList<AST> mPackages;

    public Analisar_Package(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

        mPackages = new ArrayList<AST>();

    }

    public ArrayList<AST> getPackages() {
        return mPackages;
    }


    public void Usar(String eNome, ArrayList<String> mAlocados) {

        boolean enc = false;

        for (AST ASTPackage : mPackages) {

            if (ASTPackage.mesmoNome(eNome)) {

                ArrayList<AST> mPackageStructs = new ArrayList<>();

                mAnalisador.getAnalisar_Outros().inclusao(ASTPackage);


                // USAR PACKAGES
                for (AST ASTC : ASTPackage.getASTS()) {
                    if (ASTC.mesmoTipo("USING")) {

                        //mAnalisador.getErros().add("Sub Using : " + ASTC.getNome());
                        mAnalisador.getAnalisar_Package().Usar(ASTC.getNome(), mAlocados);

                    }
                }


                for (AST mAST : ASTPackage.getASTS()) {
                    if (mAST.mesmoTipo("CAST")) {

                        mAnalisador.getTipados().add(mAST.getNome());

                    } else if (mAST.mesmoTipo("ACTION")) {

                        mAnalisador.getAnalisar_Action().incluirNome(mAST);


                    } else if (mAST.mesmoTipo("FUNCTION")) {

                        mAnalisador.getAnalisar_Function().incluirNome(mAST);

                    } else if (mAST.mesmoTipo("STAGES")) {

                        mAnalisador.getTipados().add(mAST.getNome());




                    } else if (mAST.mesmoTipo("STRUCT")) {

                        mAnalisador.getTipados().add(mAST.getNome());

                        if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                            mAnalisador.getErros().add("Struct : " + mAST.getNome() + " : Nome Proibido !");
                        }




                    } else if (mAST.mesmoTipo("TYPE")) {

                        mAnalisador.getTipados().add(mAST.getNome());

                    }

                }

                for (AST mAST : ASTPackage.getASTS()) {


                    if (mAST.mesmoTipo("FUNCTION")) {

                        if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                            mAnalisador.getErros().add("Function : " + mAST.getNome() + " : Nome Proibido !");
                        }

                        mAnalisador.getAnalisar_Function().analisarFunction(mAST, mAlocados);



                    } else if (mAST.mesmoTipo("ACTION")) {

                        if (mAnalisador.getProibidos().contains(mAST.getNome())) {
                            mAnalisador.getErros().add("Action : " + mAST.getNome() + " : Nome Proibido !");
                        }

                        mAnalisador.getAnalisar_Action().analisarAction(mAST, mAlocados);


                    } else if (mAST.mesmoTipo("OPERATOR")) {


                    } else if (mAST.mesmoTipo("DIRECTOR")) {

                    } else if (mAST.mesmoTipo("STRUCT")) {

                        mAnalisador.getAnalisar_Struct().init_Struct(mAST, mAlocados);

                    }

                }


                enc = true;
                break;
            }

        }

        if (!enc) {
            mAnalisador.getErros().add("Package " + eNome + " : Nao encontrado !");
        }


    }

}
