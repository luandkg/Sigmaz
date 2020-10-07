package Sigmaz.S05_PosProcessamento.Processadores;

import Sigmaz.S06_Executor.Alterador;
import Sigmaz.S05_PosProcessamento.PosProcessador;
import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class Unificador {

    private PosProcessador mPosProcessador;

    public Unificador(PosProcessador ePosProcessador) {

        mPosProcessador = ePosProcessador;

    }

    public void init(ArrayList<AST> mTodos) {

        mPosProcessador.mensagem("");
        mPosProcessador.mensagem(" ------------------ FASE TYPE UNION ----------------------- ");


        for (AST mAST : mTodos) {

            if (mAST.mesmoTipo("SIGMAZ")) {


                mPosProcessador.mensagem("");

                for (AST ePacote : mAST.getASTS()) {
                    if (ePacote.mesmoTipo("PACKAGE")) {

                        processar(ePacote);

                    }
                }

                processar(mAST);


            }
        }

    }

    public void processar(AST eASTPai) {


        ArrayList<AST> eTypes = new ArrayList<AST>();
        ArrayList<AST> eUnions = new ArrayList<AST>();


        for (AST mAST : eASTPai.getASTS()) {

            if (mAST.mesmoTipo("STRUCT")) {

                if (mAST.getBranch("EXTENDED").mesmoNome("TYPE_UNION")) {
                    eUnions.add(mAST);

                } else if (mAST.getBranch("EXTENDED").mesmoNome("TYPE")) {
                    eTypes.add(mAST);
                }


            }
        }

        for (AST mAST : eTypes) {

            mPosProcessador.mensagem("\t - TYPE : " + mAST.getNome());

        }

        for (AST mAST : eUnions) {

            mPosProcessador.mensagem("\t - TYPE UNION : " + mAST.getNome());


            AST eBases = mAST.getBranch("BASES");
            AST eCorpo = mAST.getBranch("BODY");

            ArrayList<String> mCampos = new ArrayList<String>();

            for (AST bAST : eBases.getASTS()) {
                mPosProcessador.mensagem("\t\t - BASE : " + bAST.getNome());


                boolean enc = false;
                AST eBaseType = null;

                for (AST eType : eTypes) {
                    if (eType.mesmoNome(bAST.getNome())) {
                        enc = true;
                        eBaseType = eType;
                        break;
                    }
                }


                if (enc) {
                    mPosProcessador.mensagem("\t\t\t - Existe : Sim");

                    boolean ok = false;


                    if (bAST.getBranch("GENERIC").mesmoNome("FALSE") && eBaseType.getBranch("GENERIC").mesmoNome("FALSE")) {

                        mPosProcessador.mensagem("\t\t\t - Pedido Generico : Nao");
                        mPosProcessador.mensagem("\t\t\t - Base Generico : Nao");

                        ok = true;

                    } else if (bAST.getBranch("GENERIC").mesmoNome("FALSE") && eBaseType.getBranch("GENERIC").mesmoNome("TRUE")) {

                        mPosProcessador.mensagem("\t\t\t - Pedido Generico : Nao");
                        mPosProcessador.mensagem("\t\t\t - Base Generico : Sim");

                        mPosProcessador.errar("Type  " + bAST.getNome() + " : E generica !");

                    } else if (bAST.getBranch("GENERIC").mesmoNome("TRUE") && eBaseType.getBranch("GENERIC").mesmoNome("FALSE")) {

                        mPosProcessador.mensagem("\t\t\t - Pedido Generico : Sim");
                        mPosProcessador.mensagem("\t\t\t - Base Generico : Nao");

                        mPosProcessador.errar("Type  " + bAST.getNome() + " : Nao e generica !");

                    } else if (bAST.getBranch("GENERIC").mesmoNome("TRUE") && eBaseType.getBranch("GENERIC").mesmoNome("TRUE")) {

                        mPosProcessador.mensagem("\t\t\t - Pedido Generico : Sim");
                        mPosProcessador.mensagem("\t\t\t - Base Generico : Sim");

                        ok = true;

                    }

                    if (ok) {

                        AST AST_PedidoGenerico = bAST.getBranch("GENERIC");
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

                                    mPosProcessador.errar("Type Union " + mAST.getNome() + "." + eCampo.getNome() + " : Campo Duplicado !");

                                }


                            }

                        } else {

                            int pi = AST_BaseGenerico.getASTS().size();
                            int ri = AST_PedidoGenerico.getASTS().size();

                            mPosProcessador.errar("Type " + eBaseType.getNome() + " : Tipos genéricos nao correspondentes !");
                            mPosProcessador.errar("Type " + eBaseType.getNome() + " : Precisa de " + pi + " Tipos genericos !");
                            mPosProcessador.errar("Type " + eBaseType.getNome() + " : Recebeu " + ri + " Tipos genericos !");

                        }

                    }



                } else {
                    mPosProcessador.mensagem("\t\t\t - Existe : Nao");
                    mPosProcessador.errar("Base Type " + bAST.getNome() + " : Nao encontrada !");
                }


            }

            mAST.getBranch("EXTENDED").setNome("TYPE");

        }

    }


}
