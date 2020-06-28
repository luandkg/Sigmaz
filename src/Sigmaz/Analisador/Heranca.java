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
        ArrayList<AST> mEstruturasComHeranca = new ArrayList<AST>();

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

                String Struct_Base = Struct_AST.getBranch("WITH").getNome();

                ArrayList<String> mDependencias = new ArrayList<String>();
                boolean normalizavel = getNormalizavel(Struct_Base, mEstruturas, mDependencias);

                if (normalizavel) {

                    v += 1;

                    Struct_AST.getBranch("EXTENDED").setValor("" + (1 + mDependencias.size()));

                    mEstruturasComHeranca.add(Struct_AST);

                } else {
                    mAnalisador.getErros().add("Estrutural : " + Struct_AST.getNome() + " -->> " + AST_With.getNome() + "  :: PROBLEMA DE DEPENDENCIA");
                }


            } else {
                //   System.out.println("Estrutural : " + Struct_AST.getNome());

                Struct_AST.getBranch("EXTENDED").setValor("1");

                v += 1;
            }
        }

        if (c == v) {

            herdar(mEstruturas, mEstruturasComHeranca);

        } else {

            mAnalisador.getErros().add("Problema na heranca !");

        }


    }


    public void herdar(ArrayList<AST> mEstruturas, ArrayList<AST> mEstruturasComHeranca) {


        for (AST Struct_AST : mEstruturasComHeranca) {

            String Struct_Base = Struct_AST.getBranch("WITH").getNome();

            ArrayList<String> mDependencias = new ArrayList<String>();

            boolean normalizavel = getNormalizavel(Struct_Base, mEstruturas, mDependencias);

            Realizar_Heranca(Struct_AST.getNome(), mDependencias, mEstruturas);

        }

    }


    public void Realizar_Heranca(String Struct_Nome, ArrayList<String> mDependencias, ArrayList<AST> mEstruturas) {

        AST Super = ProcurarStruct(Struct_Nome, mEstruturas);

        String eBase = Super.getBranch("WITH").getNome();

        mAnalisador.mensagem("---------------------------------------------------");

        mAnalisador.mensagem("              " + Struct_Nome + "           ");

        mAnalisador.mensagem("---------------------------------------------------");


        mAnalisador.mensagem("Realizando Heranca : " + Struct_Nome + " -> " + eBase);
        for (String mDepende : mDependencias) {

            AST Base = ProcurarStruct(mDepende, mEstruturas);

            if (Base.getBranch("WITH").mesmoValor("TRUE")) {
                mAnalisador.mensagem("\t\t - Precisa de : " + mDepende + " -> " + Base.getBranch("WITH").getNome());
            } else {
                mAnalisador.mensagem("\t\t - Precisa de : " + mDepende);
            }
        }


        DependencieAgora("", Struct_Nome, eBase, mEstruturas);


        for (String mDepende : mDependencias) {
            Super.getBranch("BASES").criarBranch("BASE").setNome(mDepende);
        }



    }

    public AST ProcurarStruct(String eNome, ArrayList<AST> mEstruturas) {
        AST mRet = null;
        for (AST Procurando : mEstruturas) {
            if (Procurando.mesmoNome(eNome)) {
                mRet = Procurando;
            }
        }
        return mRet;
    }

    public AST MontarBase(String ePref, String eBaseNome, ArrayList<AST> mEstruturas) {
        AST copia = ProcurarStruct(eBaseNome, mEstruturas).copiar();


        if (copia.getBranch("WITH").mesmoValor("TRUE")) {

            DependencieAgora(ePref + "\t", eBaseNome, copia.getBranch("WITH").getNome(), mEstruturas);

        } else {


            mAnalisador.mensagem(ePref + "--------------------------------------------------");

            mAnalisador.mensagem(ePref + "  - Simples  : " + eBaseNome);


        }

        return copia;
    }

    public void DependencieAgora(String ePref, String eStructNome, String eBaseNome, ArrayList<AST> mEstruturas) {


        AST Base = MontarBase(ePref, eBaseNome, mEstruturas);


        mAnalisador.mensagem(ePref + "--------------------------------------------------");


        AST Super = ProcurarStruct(eStructNome, mEstruturas);

        AST Base_Inits = Base.getBranch("INITS");
        AST Super_Inits = Super.getBranch("INITS");

        mAnalisador.mensagem(ePref + "  - Super  : " + eStructNome + " (" + Super_Inits.getASTS().size() + ")" + "   Base : " + eBaseNome + " (" + Base_Inits.getASTS().size() + ")");


        ArrayList<AST> mSuper_Inits_Filtrados = new ArrayList<AST>();

        for (AST mStruct_Init : Super_Inits.getASTS()) {
            if (mStruct_Init.mesmoNome((eStructNome))) {
                mSuper_Inits_Filtrados.add(mStruct_Init);
            }
        }


        if (Base_Inits.getASTS().size() > 0) {

            if (mSuper_Inits_Filtrados.size() > 0) {


                for (AST mStruct_Init : mSuper_Inits_Filtrados) {
                    AST mCall = mStruct_Init.getBranch("CALL");


                    if (mCall.mesmoValor("TRUE")) {

                        if (mCall.mesmoNome(eBaseNome)) {


                            if (checarArgumentos(mCall, eBaseNome, Base_Inits.getASTS())) {


                            } else {
                                mAnalisador.mensagem("  - Struct " + eStructNome + " com Chamador  " + eBaseNome + " : Quantidade de argumentos invalido !");
                                mAnalisador.getErros().add("  - Struct " + eStructNome + " com Chamador  " + eBaseNome + " : Quantidade de argumentos invalido !");
                                return;
                            }


                        } else {
                            mAnalisador.mensagem("  - Struct " + eStructNome + " deve ter um chamador com o mesmo nome da Struct Base : " + eBaseNome);
                            mAnalisador.getErros().add("  - Struct " + eStructNome + " deve ter um chamador com o mesmo nome da Struct Base : " + eBaseNome);
                            return;
                        }


                    } else {
                        mAnalisador.mensagem("  - Struct " + eStructNome + " deve ter um chamador ! ");
                        mAnalisador.getErros().add("  - Struct " + eStructNome + " deve ter um chamador ! ");
                        return;
                    }
                }


            } else {
                mAnalisador.mensagem("  - Struct " + eStructNome + " deve possuir um inicializador  ");
                mAnalisador.getErros().add("  - Struct " + eStructNome + " deve possuir um inicializador  ");
                return;
            }

        } else {
            for (AST mStruct_Init : Super_Inits.getASTS()) {
                AST mCall = mStruct_Init.getBranch("CALL");
                if (mCall.mesmoValor("TRUE")) {
                    mAnalisador.mensagem("  - Struct " + eStructNome + " nao pode ter chamadores ! ");

                    mAnalisador.getErros().add("  - Struct " + eStructNome + " nao pode ter chamadores ! ");
                    return;
                }
            }
        }


        // REALIZAR HERANCA INTERNA

        AST Base_Corpo = Base.getBranch("BODY");
        AST Super_Corpo = Super.getBranch("BODY");


        for (AST migrando : Base_Inits.getASTS()) {
            Super_Inits.getASTS().add(migrando);
        }


        for (AST migrando : Base_Corpo.getASTS()) {
            Super_Corpo.getASTS().add(migrando);
        }

    }


    public boolean checarArgumentos(AST mCall, String eBaseNome, ArrayList<AST> mInits) {

        boolean ret = false;

        int quantidade = mCall.getBranch("ARGUMENTS").getASTS().size();


        for (AST mBaseInit : mInits) {

            if (mBaseInit.mesmoNome(eBaseNome)) {
                int init_quantidade = mBaseInit.getBranch("ARGUMENTS").getASTS().size();

                if (init_quantidade == quantidade) {
                    ret = true;
                    break;
                }
            }


        }

        return ret;
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

                mAnalisador.getAnalisar_Outros().analisarTipagem(mAST);


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
