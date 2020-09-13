package Sigmaz.PosProcessamento;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Heranca {

    private PosProcessador mPosProcessador;

    public Heranca(PosProcessador ePosProcessador) {

        mPosProcessador = ePosProcessador;

    }


    public void init(ArrayList<AST> mTodos) {

        mPosProcessador.mensagem("");
        mPosProcessador.mensagem(" ------------------ FASE HERANCA ----------------------- ");


        ArrayList<AST> mPacotes = new ArrayList<AST>();

        for (AST mAST : mTodos) {

            if (mAST.mesmoTipo("SIGMAZ")) {

                for (AST Struct_AST : mAST.getASTS()) {
                    if (Struct_AST.mesmoTipo("PACKAGE")) {
                        mPacotes.add(Struct_AST.copiar());
                    }
                }


                mPosProcessador.mensagem("");

                for (AST ePacote : mAST.getASTS()) {
                    if (ePacote.mesmoTipo("PACKAGE")) {
                        iniciarProcessamento(ePacote.getNome(), mPacotes, ePacote);
                    }
                }

                iniciarProcessamento("", mPacotes, mAST);


            }
        }


    }


    public ArrayList<String> getRefers(AST eAST) {

        ArrayList<String> mRefers = new ArrayList<String>();

        for (AST PackageStruct : eAST.getASTS()) {
            if (PackageStruct.mesmoTipo("REFER")) {
                mRefers.add(PackageStruct.getNome());
            }
        }
        return mRefers;
    }


    public void iniciarProcessamento(String ePacote, ArrayList<AST> mPacotes, AST mRaiz) {


        ArrayList<String> mRefers = getRefers(mRaiz);

        if (mRaiz.mesmoTipo("PACKAGE")) {
            mPosProcessador.mensagem("PACOTE : " + mRaiz.getNome());
        } else {
            mPosProcessador.mensagem("SIGMAZ : " + mRaiz.getTipo());
        }

        if (mRefers.size() > 0) {
            mPosProcessador.mensagem("");
            for (String eRefer : mRefers) {
                mPosProcessador.mensagem("\t - REFER : " + eRefer);
            }
        }


        ArrayList<AST> mStructs = new ArrayList<AST>();


        for (AST eAST : mRaiz.getASTS()) {
            if (eAST.mesmoTipo("STRUCT")) {
                if (eAST.getBranch("EXTENDED").mesmoNome("STRUCT")) {
                    mStructs.add(eAST.copiar());
                }
            }

        }

        mPosProcessador.mensagem("");
        for (AST eAST : mRaiz.getASTS()) {
            if (eAST.mesmoTipo("STRUCT")) {
                if (eAST.getBranch("EXTENDED").mesmoNome("STRUCT")) {

                    if (eAST.getBranch("WITH").mesmoValor("TRUE")) {

                        mPosProcessador.mensagem("\t - STRUCT : " + eAST.getNome() + " :: HERANCA");

                        String eBase = eAST.getBranch("WITH").getNome();


                        montarStruct(ePacote, eAST, mStructs, mRefers, mPacotes);

                    } else {

                        mPosProcessador.mensagem("\t - STRUCT : " + eAST.getNome() + " :: CONCRETA");


                    }

                }
            }

        }


        mPosProcessador.mensagem("");

    }

    public void montarStruct(String eNomePacote, AST eStruct, ArrayList<AST> mStructs, ArrayList<String> mRefers, ArrayList<AST> mPacotes) {


        String eBase_Nome = eStruct.getBranch("WITH").getNome();
        AST eBase = null;
        boolean BaseEnc = false;
        String eLocal = ".";
        AST ePacoteBase = null;
        boolean BaseEmPacote = false;


        mPosProcessador.mensagem("\t\t - MONTAGEM : " + eStruct.getNome() + " -->> " + eBase_Nome);
        mPosProcessador.mensagem("\t\t\t  - ESTRUTURA : " + eStruct.getNome());


        for (AST eAST : mStructs) {
            if (eAST.mesmoNome(eBase_Nome)) {

                eLocal = "LOCAL";
                eBase = eAST.copiar();
                BaseEnc = true;
                break;

            }
        }

        if (!BaseEnc) {

            for (String eRefer : mRefers) {

                AST mPacote = null;
                boolean PacoteEnc = false;

                for (AST ePacote : mPacotes) {
                    if (ePacote.mesmoNome(eRefer)) {
                        mPacote = ePacote;
                        PacoteEnc = true;
                        break;
                    }
                }

                if (PacoteEnc) {

                    for (AST eAST : mPacote.getASTS()) {
                        if (eAST.mesmoNome(eBase_Nome)) {
                            eLocal = "REFER : " + eRefer;

                            eBase = eAST.copiar();

                            BaseEmPacote = true;
                            ePacoteBase = mPacote;
                            BaseEnc = true;
                            break;
                        }
                    }

                    if (BaseEnc) {
                        break;
                    }

                } else {
                    mPosProcessador.getErros().add("Pacote " + eRefer + " : Nao encontrado !");
                    break;
                }

            }

        }

        if (BaseEnc) {

            mPosProcessador.mensagem("\t\t\t  - BASE : " + eBase_Nome + " -->> " + eLocal);


            if (eBase.getBranch("WITH").mesmoValor("TRUE")) {

                // ArrayList<String> pRefers = getRefers(mPacote);

                mPosProcessador.mensagem("\t\t\t  - CONCRETA : NAO -->> " + eBase.getBranch("WITH").getNome());


                if (BaseEmPacote) {

                    AST nBase = montarBase(ePacoteBase.getNome(), eBase.copiar(), ePacoteBase.getASTS(), getRefers(ePacoteBase), mPacotes);

                    herdarAgora(ePacoteBase.getNome(), eStruct, nBase);

                } else {

                    AST nBase = montarBase(eNomePacote, eBase.copiar(), mStructs, mRefers, mPacotes);

                    herdarAgora(eNomePacote, eStruct, nBase);

                }


            } else {

                mPosProcessador.mensagem("\t\t\t  - CONCRETA : SIM ");

                herdarAgora(eNomePacote, eStruct, eBase);

            }


        } else {
            eLocal = "DESCONHECIDO";
            mPosProcessador.getErros().add("Struct " + eBase_Nome + " : Nao encontrado !");
        }

        mPosProcessador.mensagem("");

    }

    public AST montarBase(String ePacoteNome, AST eStruct, ArrayList<AST> mStructs, ArrayList<String> mRefers, ArrayList<AST> mPacotes) {


        AST eBase = null;
        AST ePacoteBase = null;

        boolean BaseEnc = false;
        String onde = "";


        if (eStruct.getBranch("WITH").mesmoValor("TRUE")) {

            String eBase_Nome = eStruct.getBranch("WITH").getNome();
            String eLocal = "";

            for (AST eAST : mStructs) {
                if (eAST.mesmoNome(eBase_Nome)) {

                    eBase = eAST;
                    BaseEnc = true;
                    eLocal = "LOCAL";
                    onde = "LOCAL";

                    break;
                }
            }

            if (!BaseEnc) {

                for (String eRefer : mRefers) {

                    AST mPacote = null;
                    boolean PacoteEnc = false;

                    for (AST ePacote : mPacotes) {
                        if (ePacote.mesmoNome(eRefer)) {
                            mPacote = ePacote;
                            PacoteEnc = true;
                            break;
                        }
                    }

                    if (PacoteEnc) {

                        for (AST eAST : mPacote.getASTS()) {
                            if (eAST.mesmoNome(eBase_Nome)) {
                                eLocal = "REFER : " + eRefer;
                                onde = "REFER";

                                eBase = eAST.copiar();
                                ePacoteBase = mPacote;

                                BaseEnc = true;
                                break;
                            }
                        }

                        if (BaseEnc) {
                            break;
                        }

                    } else {
                        mPosProcessador.getErros().add("Pacote " + eRefer + " : Nao encontrado !");
                        break;
                    }

                }

            }


            if (BaseEnc) {

                if (onde.contentEquals("LOCAL")) {

                    AST nBase = montarBase(ePacoteNome, eBase.copiar(), mStructs, mRefers, mPacotes);

                    herdarAgora(ePacoteNome, eStruct, nBase);

                } else if (onde.contentEquals("REFER")) {

                    AST nBase = montarBase(ePacoteBase.getNome(), eBase.copiar(), ePacoteBase.getASTS(), getRefers(ePacoteBase), mPacotes);

                    herdarAgora(ePacoteBase.getNome(), eStruct, nBase);


                } else {
                    mPosProcessador.getErros().add("Struct : " + eBase_Nome + " : Nao encontrada !");
                }

            } else {
                mPosProcessador.getErros().add("Struct : " + eBase_Nome + " : Nao encontrada !");

            }


            mPosProcessador.mensagem("\t\t\t--------------------- MONTAR BASE -----------------------------");

            mPosProcessador.mensagem("\t\t\t  - BASE : " + eStruct.getNome());


            mPosProcessador.mensagem("\t\t\t  - HERDAR :  " + eBase_Nome + " -->> " + eLocal);

            if (eStruct.getBranch("WITH").mesmoValor("TRUE")) {

                mPosProcessador.mensagem("\t\t\t  - CONCRETA : NAO -->> " + eStruct.getBranch("WITH").getNome());

            } else {
                mPosProcessador.mensagem("\t\t\t  - CONCRETA : SIM ");
            }

        } else {

            mPosProcessador.mensagem("\t\t\t--------------------- MONTAR BASE -----------------------------");

            mPosProcessador.mensagem("\t\t\t  - BASE : " + eStruct.getNome());

            mPosProcessador.mensagem("\t\t\t  - CONCRETA : SIM ");
        }


        return eStruct;
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


    public void herdarAgora(String ePacote, AST Super, AST Base) {


        String eStructNome = Super.getNome();
        String eBaseNome = Base.getNome();


        mPosProcessador.mensagem("\t\t\t--------------------- HERDAR -----------------------------");


        AST Base_Inits = Base.getBranch("INITS");
        AST Super_Inits = Super.getBranch("INITS");

        AST Base_Generics = Base.getBranch("GENERIC");
        AST Super_Generics = Super.getBranch("GENERIC");

        String mBaseGenerica = "Nao";
        String mSuperGenerica = "Nao";

        if (Base_Generics.mesmoNome("TRUE")) {
            mBaseGenerica = "Sim";
        }

        if (Super_Generics.mesmoNome("TRUE")) {
            mSuperGenerica = "Sim";
        }

        mPosProcessador.mensagem("\t\t\t  - Super  : " + eStructNome + " - Generics " + mSuperGenerica + " (" + Super_Generics.getASTS().size() + ")" + "   Base : " + eBaseNome + " - Generics " + mBaseGenerica + " (" + Base_Generics.getASTS().size() + ")");

        mPosProcessador.mensagem("\t\t\t  - Super  : " + eStructNome + " - Inits (" + Super_Inits.getASTS().size() + ")" + "   Base : " + eBaseNome + " - Inits (" + Base_Inits.getASTS().size() + ")");

        if (mBaseGenerica.contentEquals("Sim")) {

            if (mSuperGenerica.contentEquals("Sim")) {


                ArrayList<String> mBaseGenericos = new ArrayList<String>();
                ArrayList<String> mSuperGenericos = new ArrayList<String>();

                for (AST mBase_Generica : Base_Generics.getASTS()) {
                    mBaseGenericos.add(mBase_Generica.getNome());
                }
                for (AST mSuper_Generica : Super_Generics.getASTS()) {
                    mSuperGenericos.add(mSuper_Generica.getNome());
                }

                int v = 0;
                int vo = mBaseGenericos.size();
                for (String eg : mBaseGenericos) {

                    if (mSuperGenericos.contains(eg)) {
                        v += 1;
                    } else {

                        mPosProcessador.mensagem("A Struct " + eStructNome + " precisa ter o tipo generico : " + eg);
                        mPosProcessador.getErros().add("A Struct " + eStructNome + " precisa ter o tipo generico : " + eg);

                    }

                }


            } else {
                mPosProcessador.mensagem("A Struct " + eStructNome + " precisa ser Generica !");
                mPosProcessador.getErros().add("A Struct " + eStructNome + " precisa ser Generica !");
            }

        }


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


                           // if (checarArgumentos(mCall, eBaseNome, Base_Inits.getASTS())) {


                           // } else {
                           //     mPosProcessador.mensagem("\t\t\t  - Struct " + eStructNome + " com Chamador  " + eBaseNome + " : Quantidade de argumentos invalido !");
                           //     mPosProcessador.getErros().add("  - Struct " + eStructNome + " com Chamador  " + eBaseNome + " : Quantidade de argumentos invalido !");
                           //     return;
                          //  }


                        } else {
                            mPosProcessador.mensagem("\t\t\t  - Struct " + eStructNome + " deve ter um chamador com o mesmo nome da Struct Base : " + eBaseNome);
                            mPosProcessador.getErros().add("  - Struct " + eStructNome + " deve ter um chamador com o mesmo nome da Struct Base : " + eBaseNome);
                            return;
                        }


                    } else {
                        mPosProcessador.mensagem("\t\t\t  - Struct " + eStructNome + " deve ter um chamador ! ");
                        mPosProcessador.getErros().add("  - Struct " + eStructNome + " deve ter um chamador ! ");
                        return;
                    }
                }


            } else {
                mPosProcessador.mensagem("\t\t\t  - Struct " + eStructNome + " deve possuir um inicializador  ");
                mPosProcessador.getErros().add("  - Struct " + eStructNome + " deve possuir um inicializador  ");
                return;
            }

        } else {
            for (AST mStruct_Init : Super_Inits.getASTS()) {
                AST mCall = mStruct_Init.getBranch("CALL");
                if (mCall.mesmoValor("TRUE")) {
                    mPosProcessador.mensagem("\t\t\t  - Struct " + eStructNome + " nao pode ter chamadores ! ");

                    mPosProcessador.getErros().add("  - Struct " + eStructNome + " nao pode ter chamadores ! ");
                    return;
                }
            }
        }


        // REALIZAR HERANCA INTERNA

      //  System.out.println("Iniciar Heranca : " + Super.getNome());

       // for (AST mBase_Init : Base_Inits.getASTS()) {
       //     Super_Inits.getASTS().add(mBase_Init);
      //      System.out.println("Herando init : " + mBase_Init.getNome());
       // }

            AST Base_Corpo = Base.getBranch("BODY");
        AST Super_Corpo = Super.getBranch("BODY");

        for (AST migrando : Base.getBranch("BASES").getASTS()) {
            Super.getBranch("BASES").getASTS().add(migrando);
        }

        if (ePacote.length() == 0) {
            Super.getBranch("BASES").criarBranch("BASE").setNome(eBaseNome);
        } else {
            Super.getBranch("BASES").criarBranch("BASE").setNome(ePacote + "<>" + eBaseNome);
        }

        for (AST migrando : Base_Inits.getASTS()) {
            Super_Inits.getASTS().add(migrando);
        }

        ArrayList<AST> mHerdados = new ArrayList<AST>();

        int n = 0;
        for (AST migrando : Base_Corpo.getASTS()) {
            Super_Corpo.getASTS().add(n, migrando);
            n += 1;
            mHerdados.add(migrando);
        }




        ArrayList<AST> mAllows = new ArrayList<AST>();

        for (AST eDentro : Super_Corpo.getASTS()) {

            AST AST_Visibilidade = eDentro.getBranch("VISIBILITY");

            if (AST_Visibilidade.mesmoNome("ALLOW")) {
                mAllows.add(eDentro);
            }


        }

        if (mAllows.size()>0){
            mPosProcessador.mensagem("\t\t\t  -->> ALLOW ");
        }

        for (AST eAllow : mAllows) {

            AST AST_Visibilidade = eAllow.getBranch("VISIBILITY");
            boolean enc = false;

            for (AST eProcurando : mHerdados) {
                if (eProcurando.mesmoTipo(eAllow.getTipo())) {
                    if (eProcurando.mesmoNome(eAllow.getNome())) {

                        AST Proc_Visibilidade = eProcurando.getBranch("VISIBILITY");

                        AST_Visibilidade.setNome(Proc_Visibilidade.getNome());
                        Super_Corpo.getASTS().remove(eProcurando);

                        enc=true;
                        break;
                    }
                }
            }

            if (enc){

                mPosProcessador.mensagem("\t\t\t  - Struct " + eStructNome + " :: " + AST_Visibilidade.getNome() + " " + eAllow.getTipo() + " " + eAllow.getNome() + " !");

           }else{
                mPosProcessador.getErros().add(" - Struct " + eStructNome + " :: " + AST_Visibilidade.getNome() + " " + eAllow.getTipo() + " " + eAllow.getNome() + " Nao encontrado !");
                mPosProcessador.mensagem("\t\t\t  - Struct " + eStructNome + " :: " + AST_Visibilidade.getNome() + " " + eAllow.getTipo() + " " + eAllow.getNome() + " Nao encontrado !");


            }

        }

    }
}
