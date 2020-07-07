package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Heranca {

    private Analisador mAnalisador;

    public Heranca(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

    }


    public void init(ArrayList<AST> mTodos) {

        ArrayList<AST> mEstruturas = new ArrayList<AST>();

        for (AST mAST : mTodos) {

            if (mAST.mesmoTipo("SIGMAZ")) {

                ArrayList<AST> mPacotes = new ArrayList<AST>();

                for (AST Struct_AST : mAST.getASTS()) {
                    if (Struct_AST.mesmoTipo("PACKAGE")) {
                        mPacotes.add(Struct_AST);
                    }
                }

                analisarPacotes(mPacotes);

                for (AST Struct_AST : mPacotes) {

                    ArrayList<AST> mPackageEstruturas = new ArrayList<AST>();
                    ArrayList<AST> mPackageStructs = new ArrayList<AST>();
                    ArrayList<AST> mStruct_Referenciadas = new ArrayList<AST>();

                    for (AST PackageStruct : Struct_AST.getASTS()) {
                        if (PackageStruct.mesmoTipo("STRUCT")) {
                            mPackageStructs.add(PackageStruct);
                        }
                    }

                    mStruct_Referenciadas = referenciarStructs(mPacotes, Struct_AST);


                    for (AST PackageStruct : mPackageStructs) {
                        if (PackageStruct.mesmoTipo("STRUCT")) {
                            mPackageEstruturas.add(PackageStruct);
                        }
                    }

                    init_estruturas(mPackageEstruturas, mStruct_Referenciadas);

                }

                ArrayList<AST> mPackageStructs = new ArrayList<AST>();
                ArrayList<AST> mPackageStructs_refer = new ArrayList<AST>();

                for (AST Struct_AST : mAST.getASTS()) {

                    if (Struct_AST.mesmoTipo("STRUCT")) {
                        mPackageStructs.add(Struct_AST);
                    }

                }

                mPackageStructs_refer = referenciarStructs(mPacotes, mAST);

                for (AST Struct_AST : mPackageStructs) {

                    if (Struct_AST.mesmoTipo("STRUCT")) {
                        mEstruturas.add(Struct_AST);
                    }

                }

            }
        }

        ArrayList<AST> mStruct_Referenciadas = new ArrayList<AST>();

        init_estruturas(mEstruturas, mStruct_Referenciadas);

    }

    public class PacoteRefer {
        public int Complexidade = 0;
        public AST Pacote = null;
    }

    public void analisarPacotes(ArrayList<AST> mPacotes) {

        ArrayList<PacoteRefer> Ref_Pacotes = new ArrayList<PacoteRefer>();

        for (AST ASTC : mPacotes) {

            ArrayList<String> usados = new ArrayList<String>();

            PacoteRefer RP = new PacoteRefer();
            RP.Complexidade = pacoteContagemRefer(mPacotes, ASTC.getNome(), usados);
            RP.Pacote = ASTC;
            Ref_Pacotes.add(RP);

            if (RP.Complexidade < 0) {
                mAnalisador.getErros().add("Pacote " + RP.Pacote.getNome() + " : Possui problemas de referencias !");
                return;
            }

        }

        while (Ref_Pacotes.size() > 0) {
            PacoteRefer menor = getMenor(Ref_Pacotes);

            mAnalisador.mensagem("PACKAGE : " + menor.Pacote.getNome() + " -->> " + menor.Complexidade);

            Ref_Pacotes.remove(menor);
        }

    }

    public PacoteRefer getMenor(ArrayList<PacoteRefer> Ref_Pacotes) {
        PacoteRefer menor = null;

        if (Ref_Pacotes.size() > 0) {
            menor = Ref_Pacotes.get(0);

            for (PacoteRefer RP : Ref_Pacotes) {
                if (RP.Complexidade < menor.Complexidade) {
                    menor = RP;
                }
            }
        }


        return menor;
    }

    public ArrayList<AST> referenciarStructs(ArrayList<AST> mPacotes, AST Pacote) {

        ArrayList<AST> mStruct_Referenciadas = new ArrayList<AST>();

        for (AST PackageStruct : Pacote.getASTS()) {
            if (PackageStruct.mesmoTipo("REFER")) {
                referPacote(mPacotes, PackageStruct.getNome(), mStruct_Referenciadas);
            }
        }

        return mStruct_Referenciadas;

    }

    public boolean existeRefer(ArrayList<AST> mPacotes, String eNome) {
        boolean enc = false;

        for (AST ASTC : mPacotes) {
            if (ASTC.mesmoNome(eNome)) {
                enc = true;
                break;
            }
        }
        return enc;
    }

    public int pacoteContagemRefer(ArrayList<AST> mPacotes, String eNome, ArrayList<String> mUsados) {
        int enc = -1;

        for (AST ASTC : mPacotes) {


            if (ASTC.mesmoNome(eNome)) {
                enc = 0;
                if (!mUsados.contains(eNome)) {
                    mUsados.add(eNome);


                    for (AST PackageStruct : ASTC.getASTS()) {
                        if (PackageStruct.mesmoTipo("REFER")) {


                            int tmp = pacoteContagemRefer(mPacotes, PackageStruct.getNome(), mUsados);
                            if (tmp == -1) {
                                return -1;
                            } else {
                                enc += 1 + tmp;
                            }
                        }
                    }


                    break;
                }
            }
        }
        return enc;
    }

    public void referPacote(ArrayList<AST> mPacotes, String eNome, ArrayList<AST> mRefPacotes) {

        boolean enc = false;

        for (AST ASTC : mPacotes) {
            if (ASTC.mesmoNome(eNome)) {


                for (AST Sub : ASTC.getASTS()) {
                    if (Sub.mesmoTipo("STRUCT")) {
                        mRefPacotes.add(Sub);
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

    public void init_estruturas(ArrayList<AST> mEstruturas, ArrayList<AST> mEstruturasReferenciadas) {

        ArrayList<AST> mEstruturasComHeranca = new ArrayList<AST>();


        int c = mEstruturas.size();
        int v = 0;


        for (AST Struct_AST : mEstruturas) {

            //  System.out.println("Estruturando : " + Struct_AST.getNome());

            AST AST_With = Struct_AST.getBranch("WITH");

            if (AST_With.mesmoValor("TRUE")) {

                String Struct_Base = Struct_AST.getBranch("WITH").getNome();

                ArrayList<String> mDependencias = new ArrayList<String>();
                boolean normalizavel = getNormalizavel(Struct_Base, mEstruturas, mDependencias, mEstruturasReferenciadas);

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

            herdar(mEstruturas, mEstruturasComHeranca, mEstruturasReferenciadas);

        } else {

            mAnalisador.getErros().add("Problema na heranca !");

        }


    }


    public void herdar(ArrayList<AST> mEstruturas, ArrayList<AST> mEstruturasComHeranca, ArrayList<AST> mEstruturasReferenciadas) {


        for (AST Struct_AST : mEstruturasComHeranca) {

            String Struct_Base = Struct_AST.getBranch("WITH").getNome();

            ArrayList<String> mDependencias = new ArrayList<String>();

            boolean normalizavel = getNormalizavel(Struct_Base, mEstruturas, mDependencias, mEstruturasReferenciadas);

            Realizar_Heranca(Struct_AST.getNome(), mDependencias, mEstruturas, mEstruturasReferenciadas);

        }

    }


    public void Realizar_Heranca(String Struct_Nome, ArrayList<String> mDependencias, ArrayList<AST> mEstruturas, ArrayList<AST> mEstruturasReferenciadas) {

        AST Super = ProcurarStruct(Struct_Nome, mEstruturas, mEstruturasReferenciadas);

        String eBase = Super.getBranch("WITH").getNome();

        mAnalisador.mensagem("---------------------------------------------------");

        mAnalisador.mensagem("              " + Struct_Nome + "           ");

        mAnalisador.mensagem("---------------------------------------------------");


        mAnalisador.mensagem("Realizando Heranca : " + Struct_Nome + " -> " + eBase);
        for (String mDepende : mDependencias) {

            AST Base = ProcurarStruct(mDepende, mEstruturas, mEstruturasReferenciadas);

            if (Base.getBranch("WITH").mesmoValor("TRUE")) {
                mAnalisador.mensagem("\t\t - Precisa de : " + mDepende + " -> " + Base.getBranch("WITH").getNome());
            } else {
                mAnalisador.mensagem("\t\t - Precisa de : " + mDepende);
            }
        }


        DependencieAgora("", Struct_Nome, eBase, mEstruturas, mEstruturasReferenciadas);


        for (String mDepende : mDependencias) {
            Super.getBranch("BASES").criarBranch("BASE").setNome(mDepende);
        }


    }

    public AST ProcurarStruct(String eNome, ArrayList<AST> mEstruturas, ArrayList<AST> mEstruturasReferenciadas) {
        AST mRet = null;
        boolean enc = false;

        for (AST Procurando : mEstruturas) {
            if (Procurando.mesmoNome(eNome)) {
                mRet = Procurando;
                enc = true;
                break;
            }
        }

        if (!enc) {
            for (AST Procurando : mEstruturasReferenciadas) {
                if (Procurando.mesmoNome(eNome)) {
                    mRet = Procurando;
                    enc = true;
                    break;
                }
            }
        }
        return mRet;
    }


    public boolean estanoRefer(String eNome, ArrayList<AST> mEstruturasReferenciadas) {
        boolean enc = false;

        for (AST Procurando : mEstruturasReferenciadas) {
            if (Procurando.mesmoNome(eNome)) {
                enc = true;
                break;
            }
        }
        return enc;
    }


    public AST MontarBase(String ePref, String eBaseNome, ArrayList<AST> mEstruturas, ArrayList<AST> mEstruturasReferenciadas) {
        AST copia = ProcurarStruct(eBaseNome, mEstruturas, mEstruturasReferenciadas).copiar();


        if (copia.getBranch("WITH").mesmoValor("TRUE")) {

            DependencieAgora(ePref + "\t", eBaseNome, copia.getBranch("WITH").getNome(), mEstruturas, mEstruturasReferenciadas);

        } else {


            mAnalisador.mensagem(ePref + "--------------------------------------------------");

            mAnalisador.mensagem(ePref + "  - Simples  : " + eBaseNome);


        }

        return copia;
    }

    public void DependencieAgora(String ePref, String eStructNome, String eBaseNome, ArrayList<AST> mEstruturas, ArrayList<AST> mEstruturasReferenciadas) {


        AST Base = MontarBase(ePref, eBaseNome, mEstruturas, mEstruturasReferenciadas);


        mAnalisador.mensagem(ePref + "--------------------------------------------------");


        AST Super = ProcurarStruct(eStructNome, mEstruturas, mEstruturasReferenciadas);

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


    public boolean getNormalizavel(String eNome, ArrayList<AST> mEstruturas, ArrayList<String> mDependencias, ArrayList<AST> mAnteriores) {


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

        if (!enc) {
            for (AST Struct_AST : mAnteriores) {
                if (Struct_AST.mesmoNome(eNome)) {
                    enc = true;
                }
            }

        }

        if (enc) {

            if (mDependencias.contains(eNome)) {
                return false;
            } else {
                mDependencias.add(eNome);

                if (mais) {

                    return getNormalizavel(eProximo, mEstruturas, mDependencias, mAnteriores);
                } else {

                    return true;
                }

            }

        } else {
            mAnalisador.mensagem("Struct " + eNome + " : Nao encontrada !");
            return false;
        }

    }


}
