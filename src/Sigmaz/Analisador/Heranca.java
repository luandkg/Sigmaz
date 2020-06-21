package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Heranca {

    private Analisador mAnalisador;

    public Heranca(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

    }


    public void init(ArrayList<AST> mTodos){

        ArrayList<AST> mEstruturas = new ArrayList<AST>();

        for (AST mAST : mTodos) {

            if (mAST.mesmoTipo("SIGMAZ")) {

                for (AST Struct_AST : mAST.getASTS()) {

                    if (Struct_AST.mesmoTipo("STRUCT")) {
                        mEstruturas.add(Struct_AST);
                    }

                }

            }
        }


        int c = mEstruturas.size();
        int v = 0;


        for (AST Struct_AST : mEstruturas) {
            AST AST_With = Struct_AST.getBranch("WITH");
            if (AST_With.mesmoValor("TRUE")) {

                ArrayList<String> mDependencias = new ArrayList<String>();
                boolean normalizavel = getNormalizavel(AST_With.getNome(), mEstruturas, mDependencias);

                if (normalizavel) {
                    v += 1;
                 //   System.out.println("Estrutural : " + Struct_AST.getNome() + " -->> " + AST_With.getNome());

                    for (String eDepende : mDependencias) {

                    //    System.out.println("   - " + eDepende);
                    }
                } else {

                    mAnalisador.getErros().add("Estrutural : " + Struct_AST.getNome() + " -->> " + AST_With.getNome() + "  :: PROBLEMA DE DEPENDENCIA");

                }


            } else {
             //   System.out.println("Estrutural : " + Struct_AST.getNome());
                v += 1;
            }
        }

        if (c == v) {

            herdar(mEstruturas);

        }else{

            mAnalisador.getErros().add("Problema na heranca !");

        }


    }



    public void herdar(ArrayList<AST> mEstruturas){


        ArrayList<AST> Realizar = new ArrayList<AST>();

        for (AST Struct_AST : mEstruturas) {
            AST AST_With = Struct_AST.getBranch("WITH");

            if (AST_With.mesmoValor("TRUE")) {
                ArrayList<String> mDependencias = new ArrayList<String>();
                boolean normalizavel = getNormalizavel(AST_With.getNome(), mEstruturas, mDependencias);

              //  System.out.println("Estrutural : " + Struct_AST.getNome() + " -->> Complexidade : " + (1 + mDependencias.size()));

                Realizar.add(Struct_AST);



            } else {

           //     System.out.println("Estrutural : " + Struct_AST.getNome() + " -->> Complexidade : 1");

            }

        }

        for (AST Struct_AST : Realizar) {

            AST AST_With = Struct_AST.getBranch("WITH");
            ArrayList<String> mDependencias = new ArrayList<String>();
            boolean normalizavel = getNormalizavel(AST_With.getNome(), mEstruturas, mDependencias);

            Realizar_Heranca(Struct_AST, mDependencias, mEstruturas);

        }

    }




    public void Realizar_Heranca(AST Struct_AST, ArrayList<String> mDependencias, ArrayList<AST> mEstruturas) {

   //     System.out.println("Realizando Heranca : " + Struct_AST.getNome() );

        AST Corpo = Struct_AST.getBranch("BODY");

        for (String mDepende : mDependencias) {
            DependencieAgora(Corpo, mDepende, mEstruturas);
        }

    }

    public void DependencieAgora(AST Struct_Corpo, String eNome, ArrayList<AST> mEstruturas) {

        for (AST Procurando : mEstruturas) {
            if (Procurando.mesmoNome(eNome)) {

             //   System.out.println("  - Herdando : " + Procurando.getNome() );

                AST Corpo = Procurando.getBranch("BODY");

                for (AST migrando : Corpo.getASTS()) {

                    Struct_Corpo.getASTS().add(migrando);

                }


                break;
            }
        }

    }

    public boolean getNormalizavel(String eNome, ArrayList<AST> mEstruturas, ArrayList<String> mDependencias) {


        boolean enc = false;
        boolean mais = false;
        String eProximo = "";

        for (AST Struct_AST : mEstruturas) {
            if (Struct_AST.mesmoNome(eNome)) {
                enc = true;

                AST AST_With = Struct_AST.getBranch("WITH");
                if (AST_With.mesmoValor("TRUE")) {
                    mais = true;
                    eProximo = AST_With.getNome();
                }

                break;
            }
        }

        if (enc) {

            if (mDependencias.contains(eNome)) {
                return false;
            } else {
                mDependencias.add(eNome);

                if (mais) {

                    return getNormalizavel(eProximo, mEstruturas, mDependencias);
                } else {

                    return true;
                }

            }

        } else {
            return false;
        }

    }

}
