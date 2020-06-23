package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Heranca {

    private Analisador mAnalisador;

    private boolean mExterno;

    public Heranca(Analisador eAnalisador) {

        mAnalisador = eAnalisador;
        mExterno = true;

    }



    public void init(ArrayList<AST> mTodos) {

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

        } else {

            mAnalisador.getErros().add("Problema na heranca !");

        }


    }


    public void herdar(ArrayList<AST> mEstruturas) {


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

        mAnalisador.mensagem("Realizando Heranca : " + Struct_AST.getNome());


        for (String mDepende : mDependencias) {
            DependencieAgora(Struct_AST, mDepende, mEstruturas);
        }

    }

    public void DependencieAgora(AST Struct_AST, String eNome, ArrayList<AST> mEstruturas) {

        AST Struct_Inits = Struct_AST.getBranch("INITS");
        AST Struct_Corpo = Struct_AST.getBranch("BODY");

        int S_inits = 0;

        for (AST migrando : Struct_Inits.getASTS()) {

            mAnalisador.mensagem("  - Init da Struct : " + Struct_AST.getNome() + "(" + getAssinatura(migrando.getBranch("ARGUMENTS")) + ")");
            S_inits += 1;

        }


        for (AST Procurando : mEstruturas) {
            if (Procurando.mesmoNome(eNome)) {

                mAnalisador.mensagem("  - Herdando : " + Procurando.getNome());


                AST Heranca_Inits = Procurando.getBranch("INITS");

                int H_inits = 0;

                for (AST migrando : Heranca_Inits.getASTS()) {

                    mAnalisador.mensagem("  - Init da Heranca : " + Procurando.getNome() + "(" + getAssinatura(migrando.getBranch("ARGUMENTS")) + ")");
                    H_inits += 1;

                }

                if (H_inits > 0) {

                    mAnalisador.mensagem("  - Obrigatoriedade de Init em " + Struct_AST.getNome() + " -> " + Procurando.getNome());

                    if (S_inits == 0) {
                        System.out.println("  - Struct " + Struct_AST.getNome() + " Nao consegue herdar  " + Procurando.getNome());
                        mAnalisador.getErros().add("  - Struct " + Struct_AST.getNome() + " Nao consegue herdar  " + Procurando.getNome());
                    } else {

                        boolean compativel = false;

                        for (AST migrando_struct : Struct_Inits.getASTS()) {
                            mAnalisador.mensagem("  - Struct Init : " + Struct_AST.getNome() + "(" + getAssinatura(migrando_struct.getBranch("ARGUMENTS")) + ")");

                            AST mCall = migrando_struct.getBranch("CALL");

                            if (mCall.mesmoValor("TRUE")) {
                                mAnalisador.mensagem("  -  VALOR : " + mCall.getValor());

                                if (mCall.existeBranch("ARGUMENTS")) {
                                    int t = getContagemAssinatura(mCall.getBranch("ARGUMENTS"));
                                    int r = getContagemAssinatura(migrando_struct.getBranch("ARGUMENTS"));

                                    mAnalisador.mensagem("  - Args Init Call : " + (getContagemAssinatura(mCall.getBranch("ARGUMENTS"))));
                                    mAnalisador.mensagem("  - Ass Init Call : " + (getAssinatura(mCall.getBranch("ARGUMENTS"))));

                                    // if (t == r) {

                                    for (AST migrando_heranca : Heranca_Inits.getASTS()) {
                                        //  if (t==getContagemAssinatura(migrando_struct.getBranch("ARGUMENTS"))){
                                        compativel = true;
                                        mAnalisador.mensagem("  - Heranca Init : " + Procurando.getNome() + "(" + getAssinatura(migrando_heranca.getBranch("ARGUMENTS")) + ")");

                                        //  }

                                    }
                                    //  }
                                }

                            }


                            if (compativel) {
                                mAnalisador.mensagem("  - Struct " + Struct_AST.getNome() + " pode herdar  " + Procurando.getNome());


                            } else {
                                mAnalisador.mensagem("  - Struct " + Struct_AST.getNome() + " Nao consegue herdar  " + Procurando.getNome());
                                mAnalisador.getErros().add("  - Struct " + Struct_AST.getNome() + " Nao consegue herdar  " + Procurando.getNome());
                            }


                        }


                    }

                }

                AST Heranca_Init = Procurando.getBranch("INITS");

                for (AST migrando : Heranca_Init.getASTS()) {

                    Struct_Inits.getASTS().add(migrando);

                }

                AST Heranca_Corpo = Procurando.getBranch("BODY");

                for (AST migrando : Heranca_Corpo.getASTS()) {

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


    public String getAssinatura(AST ASTPai) {

        ArrayList<String> mNomes = new ArrayList<String>();

        String mParametragem = "";

        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {

                if (!mNomes.contains(mAST.getNome())) {
                    mNomes.add(mAST.getNome());
                } else {
                    mAnalisador.getErros().add("Argumento Init Duplicado : " + mAST.getNome());
                }

                mParametragem += "<" + mAST.getValor() + "> ";

                mAnalisador.getAnalisar_Outros().analisandoDefines(mAST);


            } else {
                mAnalisador.getErros().add("Tipo Desconhecido : " + mAST.getTipo());
            }
        }

        return mParametragem;
    }

    public int getContagemAssinatura(AST ASTPai) {

        int i = 0;


        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {

                i += 1;


            } else {
                mAnalisador.getErros().add("Tipo Desconhecido : " + mAST.getTipo());
            }
        }

        return i;
    }
}
