package Sigmaz.S05_PosProcessamento.Processadores;

import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.Alterador.SigmazPackage;
import Sigmaz.S00_Utilitarios.Alterador.SigmazRaiz;
import Sigmaz.S00_Utilitarios.Alterador.SigmazStruct;
import Sigmaz.S05_PosProcessamento.PosProcessador;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S05_PosProcessamento.ProcurandoStruct;
import Sigmaz.S08_Executor.Alterador;

public class Heranca {

    private PosProcessador mPosProcessador;

    private String CRIADO = "2020 09 10";
    private String ATUALIZADO = "2020 10 21";
    private String VERSAO = "2.0";

    public Heranca(PosProcessador ePosProcessador) {

        mPosProcessador = ePosProcessador;

    }


    public void mensagem(String e) {
        if (mPosProcessador.getDebug_Heranca()) {
            mPosProcessador.mensagem(e);
        }
    }

    public void errar(String eErro) {
        mPosProcessador.errar(eErro);
    }

    public boolean temErros() {
        return mPosProcessador.temErros();
    }

    public void init(ArrayList<AST> mTodos) {

        mensagem("");
        mensagem(" ------------------ FASE HERANCA ----------------------- ");


        for (AST mAST : mTodos) {

            if (mAST.mesmoTipo("SIGMAZ")) {

                ArrayList<SigmazPackage> mPacotes = new ArrayList<SigmazPackage>();
                SigmazRaiz mSigmazRaiz = new SigmazRaiz(mAST);


                for (AST Struct_AST : mAST.getASTS()) {
                    if (Struct_AST.mesmoTipo("PACKAGE")) {
                        mPacotes.add(new SigmazPackage(Struct_AST));
                    }
                }


                processarSigmaz(mSigmazRaiz, mPacotes);

                for (SigmazPackage ePacote : mPacotes) {
                    processarPacote(mSigmazRaiz, ePacote, mPacotes);
                }

            }

        }


    }

    public void processarSigmaz(SigmazRaiz mSigmazRaiz, ArrayList<SigmazPackage> mPacotes) {


        mensagem("-->> SIGMAZ");
        mensagem("");

        mensagem("");


        for (SigmazStruct eStruct : mSigmazRaiz.getStructs()) {

            mensagem("\t - STRUCT : " + eStruct.getNome() + " ->> " + eStruct.getCompletude());

            ArrayList<String> mHerdando = new ArrayList<String>();

            if (eStruct.isIncompleta()) {


                ProcurandoStruct mProcurandoStruct = new ProcurandoStruct(this);
                mProcurandoStruct.procurarSigmaz(eStruct.getBase(), mSigmazRaiz, mPacotes);

                realizarMontagem(mHerdando, mSigmazRaiz, eStruct, mPacotes, mProcurandoStruct);

            }

        }


    }


    public void processarPacote(SigmazRaiz mSigmazRaiz, SigmazPackage mSigmazPackage, ArrayList<SigmazPackage> mPacotes) {

        mensagem("-->> PACKAGE : " + mSigmazPackage.getNome());

        mensagem("");


        for (SigmazStruct eStruct : mSigmazPackage.getStructs()) {

            mensagem("\t - STRUCT : " + eStruct.getNome() + " ->> " + eStruct.getCompletude());

            ArrayList<String> mHerdando = new ArrayList<String>();

            if (eStruct.isIncompleta()) {

                ProcurandoStruct mProcurandoStruct = new ProcurandoStruct(this);
                mProcurandoStruct.procurar(eStruct.getBase(), mSigmazRaiz, mSigmazPackage, mPacotes);

                realizarMontagem(mHerdando, mSigmazRaiz, eStruct, mPacotes, mProcurandoStruct);

            }

        }


    }


    public void realizarMontagem(ArrayList<String> mHerdando, SigmazRaiz mSigmazRaiz, SigmazStruct eStruct, ArrayList<SigmazPackage> mPacotes, ProcurandoStruct mProcurandoStruct) {

        String eBase = eStruct.getBase();

        mensagem("\t          ------------------- MONTAGEM ----------------------");

        mensagem("\t\t           - STRUCT : " + eStruct.getNome());
        mensagem("\t\t           - FORMA : " + eStruct.getCompletude());

        mensagem("\t\t           - BASE : " + eBase);


        if (mProcurandoStruct.getOk()) {


            String eEncontrado = "NAO";
            if (mProcurandoStruct.getEncontrado()) {
                eEncontrado = "SIM";
            }

            mensagem("\t\t           - ORIGEM : " + mProcurandoStruct.getOrigem());
            mensagem("\t\t           - ENCONTRADO : " + eEncontrado);


            if (mProcurandoStruct.getEncontrado()) {

                mensagem("\t\t           - BASE FORMA : " + mProcurandoStruct.getStruct().getCompletude());

                String incluir = mProcurandoStruct.getPacote() + "<>" + mProcurandoStruct.getStruct().getNome();
                if (mHerdando.contains(incluir)) {
                    errar("Heranca Ciclica  : " + eStruct.getNome() + " com " + mProcurandoStruct.getStruct().getNome());
                } else {
                    mHerdando.add(incluir);

                    if (mProcurandoStruct.getStruct().isCompleta()) {

                        herdarAgora(mProcurandoStruct.getPacote(), eStruct.getAlteravel(), mProcurandoStruct.getStruct().getLeitura().copiar());

                    } else {

                        AST mBaseMontada = montarBase(mHerdando, mProcurandoStruct.getStruct().getLeitura().copiar(), mProcurandoStruct, mSigmazRaiz, mPacotes);

                        herdarAgora(mProcurandoStruct.getPacote(), eStruct.getAlteravel(), mBaseMontada.copiar());

                    }

                }


            } else {

                errar("Struct Base  : " + eBase + " -->> NAO ENCONTRADO !");

            }


        } else {

            errar(mProcurandoStruct.getErro());
            mensagem(mProcurandoStruct.getErro());

        }


        mensagem("\t          --------------------------------------------------");

    }


    public AST montarBase(ArrayList<String> mHerdando, AST eSuperAST, ProcurandoStruct vindoProcurandoStruct, SigmazRaiz mSigmazRaiz, ArrayList<SigmazPackage> mPacotes) {


        SigmazStruct eSuper = new SigmazStruct(eSuperAST);

        mensagem("\t          ------------------- MONTAGEM ----------------------");

        mensagem("\t\t           - STRUCT : " + eSuper.getNome());
        mensagem("\t\t           - FORMA : " + eSuper.getCompletude());

        ProcurandoStruct mProcurandoStruct = new ProcurandoStruct(this);

        String eBase = eSuper.getBase();

        if (vindoProcurandoStruct.getEncontrado()) {

            if (vindoProcurandoStruct.getEstaSigmaz()) {

                mProcurandoStruct.procurarSigmaz(eBase, mSigmazRaiz, mPacotes);

            } else if (vindoProcurandoStruct.getEstaLocal()) {


                SigmazPackage mPacote = null;
                for (SigmazPackage ePacote : mPacotes) {
                    if (ePacote.mesmoNome(vindoProcurandoStruct.getPacote())) {
                        mPacote = ePacote;
                        break;
                    }
                }

                mProcurandoStruct.procurar(eBase, mSigmazRaiz, mPacote, mPacotes);

            } else if (vindoProcurandoStruct.getEstaPacote()) {

                SigmazPackage mPacote = null;
                for (SigmazPackage ePacote : mPacotes) {
                    if (ePacote.mesmoNome(vindoProcurandoStruct.getPacote())) {
                        mPacote = ePacote;
                        break;
                    }
                }

                mProcurandoStruct.procurar(eBase, mSigmazRaiz, mPacote, mPacotes);


            }


        }


        if (eSuper.isIncompleta()) {

            mensagem("\t\t           - BASE : " + eSuper.getBase());


            if (mProcurandoStruct.getOk()) {


                String eEncontrado = "NAO";
                if (mProcurandoStruct.getEncontrado()) {
                    eEncontrado = "SIM";
                }

                mensagem("\t\t           - ORIGEM : " + mProcurandoStruct.getOrigem());
                mensagem("\t\t           - ENCONTRADO : " + eEncontrado);


                if (mProcurandoStruct.getEncontrado()) {

                    mensagem("\t\t           - BASE FORMA : " + mProcurandoStruct.getStruct().getCompletude());

                    String incluir = mProcurandoStruct.getPacote() + "<>" + mProcurandoStruct.getStruct().getNome();
                    if (mHerdando.contains(incluir)) {
                        errar("Heranca Ciclica  : " + eSuper.getNome() + " com " + mProcurandoStruct.getStruct().getNome());
                    } else {
                        mHerdando.add(incluir);

                        if (mProcurandoStruct.getStruct().isCompleta()) {

                            herdarAgora(mProcurandoStruct.getPacote(), eSuper.getAlteravel(), mProcurandoStruct.getStruct().getLeitura().copiar());

                        } else {

                            AST mBaseMontada = montarBase(mHerdando, mProcurandoStruct.getStruct().getLeitura().copiar(), mProcurandoStruct, mSigmazRaiz, mPacotes);

                            if (temErros()) {
                                return eSuperAST;
                            }

                            herdarAgora(mProcurandoStruct.getPacote(), eSuper.getAlteravel(), mBaseMontada.copiar());

                        }

                    }


                } else {

                    errar("Struct Base  : " + eBase + " -->> NAO ENCONTRADO !");

                }


            } else {

                errar(mProcurandoStruct.getErro());
                mensagem(mProcurandoStruct.getErro());

            }


        }


        mensagem("\t          --------------------------------------------------");


        return eSuperAST;
    }


    public void herdarAgora(String ePacote, AST Super, AST Base) {


        String eStructNome = Super.getNome();
        String eBaseNome = Base.getNome();


        mensagem("\t\t\t--------------------- HERDAR -----------------------------");


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

        mensagem("\t\t\t  - Super : " + eStructNome + " - Generics " + mSuperGenerica + " ( " + Super_Generics.getASTS().size() + " ) ");


        AST SuperBase_With = Super.getBranch("WITH").getBranch("GENERIC");
        String eSuperBaseGeneric = "Nao";

        if (SuperBase_With.mesmoValor("TRUE")) {
            eSuperBaseGeneric = "Sim";
        }

        mensagem("\t\t\t  - Super Base Generics : " + eBaseNome + " - Generics " + eSuperBaseGeneric + " ( " + SuperBase_With.getASTS().size() + " ) ");
        mensagem("\t\t\t  - Base Generics  : " + eBaseNome + " - Generics " + mBaseGenerica + " ( " + Base_Generics.getASTS().size() + " ) ");


        if (SuperBase_With.mesmoValor("FALSE") && Base_Generics.mesmoNome("TRUE")) {

            mensagem("A Struct Base " + eBaseNome + " precisa implementar tipos genericos !");
            errar("A Struct Base " + eBaseNome + " precisa implementar tipos genericos !");


        } else if (SuperBase_With.mesmoValor("TRUE") && Base_Generics.mesmoNome("FALSE")) {

            mensagem("A Struct " + eBaseNome + " nao e generica !");
            errar("A Struct " + eBaseNome + " nao e generica !");

        } else if (SuperBase_With.mesmoValor("TRUE") && Base_Generics.mesmoNome("TRUE")) {

            ArrayList<String> mBaseGenericos = new ArrayList<String>();
            ArrayList<AST> mSuperGenericos = new ArrayList<AST>();

            for (AST mBase_Generica : Base_Generics.getASTS()) {
                mBaseGenericos.add(mBase_Generica.getNome());
            }

            for (AST mSuper_Generica : SuperBase_With.getASTS()) {
                mSuperGenericos.add(mSuper_Generica);
            }

            if (mBaseGenericos.size() == mSuperGenericos.size()) {

                Alterador mAlterador = new Alterador();
                int i = 0;

                for (AST eSub : Base_Generics.getASTS()) {

                    AST sInit = mSuperGenericos.get(i);

                    mAlterador.adicionar(eSub.getNome(), sInit);
                    mensagem("\t\t\t Alterando Tipo Abstrato : " + eSub.getNome() + " para " + sInit.getNome());
                    i += 1;
                }

                mAlterador.alterar(Base.getBranch("INITS"));
                mAlterador.alterar(Base.getBranch("BODY"));
                mAlterador.alterar(Base.getBranch("DESTRUCT"));

            } else {

                mensagem("A Struct Base " + eBaseNome + " precisa de " + mBaseGenericos.size() + " tipos genericos !");
                errar("A Struct Base " + eBaseNome + " precisa de " + mBaseGenericos.size() + " tipos genericos !");

            }

        }


        mensagem("\t\t\t  - Super  : " + eStructNome + " - Generics " + mSuperGenerica + " (" + Super_Generics.getASTS().size() + ")" + "   Base : " + eBaseNome + " - Generics " + mBaseGenerica + " (" + Base_Generics.getASTS().size() + ")");

        mensagem("\t\t\t  - Super  : " + eStructNome + " - Inits (" + Super_Inits.getASTS().size() + ")" + "   Base : " + eBaseNome + " - Inits (" + Base_Inits.getASTS().size() + ")");


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


                        } else {
                            mensagem("\t\t\t  - Struct " + eStructNome + " deve ter um chamador com o mesmo nome da Struct Base : " + eBaseNome);
                            mPosProcessador.getErros().add("  - Struct " + eStructNome + " deve ter um chamador com o mesmo nome da Struct Base : " + eBaseNome);
                            return;
                        }


                    } else {
                        mensagem("\t\t\t  - Struct " + eStructNome + " deve ter um chamador ! ");
                        mPosProcessador.getErros().add("  - Struct " + eStructNome + " deve ter um chamador ! ");
                        return;
                    }
                }


            } else {
                mensagem("\t\t\t  - Struct " + eStructNome + " deve possuir um inicializador  ");
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


        AST Base_Corpo = Base.getBranch("BODY");
        AST Super_Corpo = Super.getBranch("BODY");

        for (AST migrando : Base.getBranch("BASES").getASTS()) {
            Super.getBranch("BASES").getASTS().add(migrando);
        }

        for (AST migrando : Base.getBranch("MODELS").getASTS()) {
            Super.getBranch("MODELS").getASTS().add(migrando);
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

        if (mAllows.size() > 0) {
            mensagem("\t\t\t  -->> ALLOW ");
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

                        enc = true;
                        break;
                    }
                }
            }

            if (enc) {

                mensagem("\t\t\t  - Struct " + eStructNome + " :: " + AST_Visibilidade.getNome() + " " + eAllow.getTipo() + " " + eAllow.getNome() + " !");

            } else {

                mensagem("\t\t\t  - Struct " + eStructNome + " :: " + AST_Visibilidade.getNome() + " " + eAllow.getTipo() + " " + eAllow.getNome() + " Nao encontrado !");

                mPosProcessador.getErros().add(" - Struct " + eStructNome + " :: " + AST_Visibilidade.getNome() + " " + eAllow.getTipo() + " " + eAllow.getNome() + " Nao encontrado !");


            }


        }


        AST Base_Destruct = Base.getBranch("DESTRUCT");
        AST Super_Destruct = Super.getBranch("DESTRUCT");

        for (AST eDentro : Base_Destruct.getASTS()) {
            Super_Destruct.getASTS().add(eDentro);
        }


    }


}
