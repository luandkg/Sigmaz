package Sigmaz.S05_PosProcessamento.Processadores;

import Sigmaz.S00_Utilitarios.*;
import Sigmaz.S00_Utilitarios.Alterador.SigmazPackage;
import Sigmaz.S00_Utilitarios.Alterador.SigmazRaiz;
import Sigmaz.S00_Utilitarios.Alterador.SigmazType;
import Sigmaz.S05_PosProcessamento.ProcurandoType;
import Sigmaz.S07_Executor.Alterador;
import Sigmaz.S05_PosProcessamento.PosProcessador;

import java.util.ArrayList;

public class Unificador {

    private PosProcessador mPosProcessador;

    private String CRIADO = "2020 09 20";
    private String ATUALIZADO = "2020 10 25";
    private String VERSAO = "2.0";


    public Unificador(PosProcessador ePosProcessador) {

        mPosProcessador = ePosProcessador;

    }


    public void mensagem(String e) {
        if (mPosProcessador.getDebug_Unificador()) {
            mPosProcessador.mensagem(e);
        }
    }

    public void errar(String e) {
        mPosProcessador.errar(e);
    }

    public void init(ArrayList<AST> mTodos) {

        mensagem("");
        mensagem(" ------------------ FASE UNIFICADOR ----------------------- ");


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

        for (SigmazType eType : mSigmazRaiz.getTypes()) {

            mensagem("\t - TYPE : " + eType.getNome() + " ->> " + eType.getCompletude());

            if (eType.precisaUnir()) {
                processeSigmaz("\t\t\t\t", eType, mSigmazRaiz, mPacotes);
            }

        }


    }

    public void processarPacote(SigmazRaiz mSigmazRaiz, SigmazPackage mSigmazPackage, ArrayList<SigmazPackage> mPacotes) {

        mensagem("-->> PACKAGE : " + mSigmazPackage.getNome());
        mensagem("");


        for (SigmazType eType : mSigmazPackage.getTypes()) {

            mensagem("\t - TYPE : " + eType.getNome() + " ->> " + eType.getCompletude());

            if (eType.precisaUnir()) {

                processePackage("\t\t\t\t", eType, mSigmazRaiz, mSigmazPackage, mPacotes);


            }

        }


    }


    public void processeSigmaz(String ePrefixo, SigmazType eType, SigmazRaiz mSigmazRaiz, ArrayList<SigmazPackage> mPacotes) {

        mensagem(ePrefixo + "------------------- UNIFICACAO ----------------------");

        mensagem(ePrefixo + " - Nome : " + eType.getNome());

        unifiqueSigmaz(ePrefixo, eType, mSigmazRaiz, mPacotes);

        mensagem(ePrefixo + "--------------------------------------------------");

    }


    public void unifiqueSigmaz(String ePrefixo, SigmazType eType, SigmazRaiz mSigmazRaiz, ArrayList<SigmazPackage> mPacotes) {

        AST eBases = eType.getLeitura().getBranch("BASES");

        if (eBases.getASTS().size() == 2) {

            AST eT1 = eBases.getASTS().get(0);
            AST eT2 = eBases.getASTS().get(1);

            mensagem(ePrefixo + " - Unificando : " + eT1.getNome() + " e " + eT2.getNome());


            ProcurandoType mProcurandoT1 = new ProcurandoType(this);
            mProcurandoT1.procurarSigmaz(eT1.getNome(), mSigmazRaiz, mPacotes);

            ProcurandoType mProcurandoT2 = new ProcurandoType(this);
            mProcurandoT2.procurarSigmaz(eT2.getNome(), mSigmazRaiz, mPacotes);


            if (mProcurandoT1.getEncontrado()) {
                mensagem(ePrefixo + " - T1 : " + eT1.getNome() + " ->> Sim :: " + mProcurandoT1.getOrigem());
            } else {
                mensagem(ePrefixo + " - T1 : " + eT1.getNome() + " ->> Nao");
                errar("Type " + eT1.getNome() + " nao encontrada !");
            }

            if (mProcurandoT2.getEncontrado()) {
                mensagem(ePrefixo + " - T2 : " + eT2.getNome() + " ->> Sim :: " + mProcurandoT2.getOrigem());
            } else {
                mensagem(ePrefixo + " - T2 : " + eT2.getNome() + " ->> Nao");
                errar("Type " + eT2.getNome() + " nao encontrada !");
            }

            if (mProcurandoT1.getEncontrado() && mProcurandoT2.getEncontrado()) {



                ArrayList<String> mCompondo = new ArrayList<String>();

                AST eBase_01 = organizarBASE(ePrefixo, eType, mProcurandoT1.getType().getLeitura().copiar(), mCompondo, mProcurandoT1, mSigmazRaiz, mPacotes);

                AST eBase_02 = organizarBASE(ePrefixo, eType, mProcurandoT2.getType().getLeitura().copiar(), mCompondo, mProcurandoT2, mSigmazRaiz, mPacotes);



                ArrayList<String> mCampos = new ArrayList<String>();

                realizarComposicao(ePrefixo, eType, mCampos, eT1, eBase_01);
                realizarComposicao(ePrefixo, eType, mCampos, eT2, eBase_02);

                eType.getAlteravel().getBranch("EXTENDED").setNome("TYPE");

            }


        } else {

            errar("Houve um problema na unificao de " + eType.getNome());

        }

    }

    public AST organizarBASE(String ePrefixo, SigmazType eType, AST eBase_01, ArrayList<String> mCompondo, ProcurandoType mProcurandoT1, SigmazRaiz mSigmazRaiz, ArrayList<SigmazPackage> mPacotes) {

        if (mProcurandoT1.getType().precisaUnir()) {

            eBase_01 = montarBase(ePrefixo, mCompondo, eBase_01, mProcurandoT1, mSigmazRaiz, mPacotes);

            for (AST mBaseado : eBase_01.getBranch("BASES").getASTS()) {

                eType.getAlteravel().getBranch("BASES").getASTS().add(mBaseado.copiar());

            }
        }

        return eBase_01;
    }

    public void unifiquePackage(String ePrefixo, SigmazType eType, SigmazRaiz mSigmazRaiz, SigmazPackage mSigmazPackage, ArrayList<SigmazPackage> mPacotes) {

        AST eBases = eType.getLeitura().getBranch("BASES");

        if (eBases.getASTS().size() == 2) {

            AST eT1 = eBases.getASTS().get(0);
            AST eT2 = eBases.getASTS().get(1);

            mensagem(ePrefixo + " - Unificando : " + eT1.getNome() + " e " + eT2.getNome());


            ProcurandoType mProcurandoT1 = new ProcurandoType(this);
            mProcurandoT1.procurar(eT1.getNome(), mSigmazRaiz, mSigmazPackage, mPacotes);

            ProcurandoType mProcurandoT2 = new ProcurandoType(this);
            mProcurandoT2.procurar(eT2.getNome(), mSigmazRaiz, mSigmazPackage, mPacotes);


            if (mProcurandoT1.getEncontrado()) {
                mensagem(ePrefixo + " - T1 : " + eT1.getNome() + " ->> Sim :: " + mProcurandoT1.getOrigem());
            } else {
                mensagem(ePrefixo + " - T1 : " + eT1.getNome() + " ->> Nao");
                errar("Type " + eT1.getNome() + " nao encontrada !");
            }

            if (mProcurandoT2.getEncontrado()) {
                mensagem(ePrefixo + " - T2 : " + eT2.getNome() + " ->> Sim :: " + mProcurandoT2.getOrigem());
            } else {
                mensagem(ePrefixo + " - T2 : " + eT2.getNome() + " ->> Nao");
                errar("Type " + eT2.getNome() + " nao encontrada !");
            }

            if (mProcurandoT1.getEncontrado() && mProcurandoT2.getEncontrado()) {


                ArrayList<String> mCampos = new ArrayList<String>();

                ArrayList<String> mCompondo = new ArrayList<String>();

                AST eBase_01 = organizarBASE(ePrefixo, eType, mProcurandoT1.getType().getLeitura().copiar(), mCompondo, mProcurandoT1, mSigmazRaiz, mPacotes);

                AST eBase_02 = organizarBASE(ePrefixo, eType, mProcurandoT2.getType().getLeitura().copiar(), mCompondo, mProcurandoT2, mSigmazRaiz, mPacotes);


                realizarComposicao(ePrefixo, eType, mCampos, eT1, eBase_01);
                realizarComposicao(ePrefixo, eType, mCampos, eT2, eBase_02);

                eType.getAlteravel().getBranch("EXTENDED").setNome("TYPE");

            }


        } else {

            errar("Houve um problema na unificao de " + eType.getNome());

        }

    }

    public void processePackage(String ePrefixo, SigmazType eType, SigmazRaiz mSigmazRaiz, SigmazPackage mSigmazPackage, ArrayList<SigmazPackage> mPacotes) {

        mensagem(ePrefixo + "------------------- UNIFICACAO ----------------------");

        mensagem(ePrefixo + " - Nome : " + eType.getNome());

        unifiquePackage(ePrefixo, eType, mSigmazRaiz, mSigmazPackage, mPacotes);

        mensagem(ePrefixo + "--------------------------------------------------");

    }


    public void realizarComposicao(String ePrefixo, SigmazType eType, ArrayList<String> mCampos, AST ePedidoBase, AST eBaseType) {

        String eNomeType = eType.getNome();

        AST eCorpo = eType.getAlteravel().getBranch("BODY");


        mensagem(ePrefixo + " - Compondo : " + ePedidoBase.getNome());

        String mPrefixo = ePrefixo + "\t";

        boolean ok = false;


        if (ePedidoBase.getBranch("GENERIC").mesmoNome("FALSE") && eBaseType.getBranch("GENERIC").mesmoNome("FALSE")) {

            mensagem(mPrefixo + " - Pedido Generico : Nao");
            mensagem(mPrefixo + " - Base Generico : Nao");

            ok = true;

        } else if (ePedidoBase.getBranch("GENERIC").mesmoNome("TRUE") && eBaseType.getBranch("GENERIC").mesmoNome("TRUE")) {

            mensagem(mPrefixo + " - Pedido Generico : Sim");
            mensagem(mPrefixo + " - Base Generico : Sim");

            ok = true;

        } else if (ePedidoBase.getBranch("GENERIC").mesmoNome("FALSE") && eBaseType.getBranch("GENERIC").mesmoNome("TRUE")) {

            mensagem(mPrefixo + " - Pedido Generico : Nao");
            mensagem(mPrefixo + " - Base Generico : Sim");

            mPosProcessador.errar("Type  " + eBaseType.getNome() + " : E generica !");

        } else if (ePedidoBase.getBranch("GENERIC").mesmoNome("TRUE") && eBaseType.getBranch("GENERIC").mesmoNome("FALSE")) {

            mensagem(mPrefixo + " - Pedido Generico : Sim");
            mensagem(mPrefixo + " - Base Generico : Nao");

            mPosProcessador.errar("Type  " + eBaseType.getNome() + " : Nao e generica !");


        }

        if (ok) {

            AST AST_PedidoGenerico = ePedidoBase.getBranch("GENERIC");
            AST AST_BaseGenerico = eBaseType.getBranch("GENERIC");

            if (AST_PedidoGenerico.getASTS().size() == AST_BaseGenerico.getASTS().size()) {

                AST AST_BaseCorpo = eBaseType.getBranch("BODY").copiar();

                if (AST_PedidoGenerico.getASTS().size() == 0) {


                } else {

                    Alterador mAlterador = new Alterador();


                    int i = 0;
                    for (AST eSub : AST_PedidoGenerico.getASTS()) {

                        AST sInit = AST_BaseGenerico.getASTS().get(i);

                        mAlterador.adicionar(sInit.getNome(), eSub);

                    }

                    mAlterador.alterar(AST_BaseCorpo);

                }


                for (AST eCampo : AST_BaseCorpo.getASTS()) {

                    if (!mCampos.contains(eCampo.getNome())) {

                        mCampos.add(eCampo.getNome());

                        eCorpo.getASTS().add(eCampo);


                    } else {

                        mPosProcessador.errar("Type Union " + eNomeType + "." + eCampo.getNome() + " : Campo Duplicado !");

                    }


                }

            } else {

                int pi = AST_BaseGenerico.getASTS().size();
                int ri = AST_PedidoGenerico.getASTS().size();

                mPosProcessador.errar("Type " + eNomeType + " : Tipos genéricos nao correspondentes !");
                mPosProcessador.errar("Type " + eNomeType + " : Precisa de " + pi + " Tipos genericos !");
                mPosProcessador.errar("Type " + eNomeType + " : Recebeu " + ri + " Tipos genericos !");

            }

        }


    }


    public AST montarBase(String ePrefixo, ArrayList<String> mCompondo, AST eSuperAST, ProcurandoType vindoProcurandoStruct, SigmazRaiz mSigmazRaiz, ArrayList<SigmazPackage> mPacotes) {


        SigmazType eSuper = new SigmazType(eSuperAST);

        String mPrefixo = ePrefixo + "\t";

        mensagem(mPrefixo + "------------------- MONTAGEM ----------------------");

        mensagem(mPrefixo + " - TYPE : " + eSuper.getNome());
        mensagem(mPrefixo + " - ORIGEM : " + vindoProcurandoStruct.getLocalOrigem());

        if (vindoProcurandoStruct.isSigmaz()) {

            SigmazType tmp = new SigmazType(eSuper.getLeitura().copiar());

            unifiqueSigmaz(mPrefixo, tmp, mSigmazRaiz, mPacotes);

            eSuperAST = tmp.getAlteravel();


        } else if (vindoProcurandoStruct.isLocal()) {

            mensagem(mPrefixo + " - LOCAL : " + vindoProcurandoStruct.getLocalNome());

            SigmazType tmp = new SigmazType(eSuper.getLeitura().copiar());

            unifiquePackage(mPrefixo, tmp, mSigmazRaiz, vindoProcurandoStruct.getLocalPackage(), mPacotes);

            eSuperAST = tmp.getAlteravel();

        } else if (vindoProcurandoStruct.isPackage()) {

            mensagem(mPrefixo + " - PACKAGE : " + vindoProcurandoStruct.getPacote());

            SigmazType tmp = new SigmazType(eSuper.getLeitura().copiar());

            unifiquePackage(mPrefixo, tmp, mSigmazRaiz, vindoProcurandoStruct.getPackage(), mPacotes);

            eSuperAST = tmp.getAlteravel();

        }


        mensagem(mPrefixo + " --------------------------------------------------");


        return eSuperAST;
    }


}
