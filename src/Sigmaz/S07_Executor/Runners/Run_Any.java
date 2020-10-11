package Sigmaz.S07_Executor.Runners;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Indexador.Index_Action;
import Sigmaz.S07_Executor.Indexador.Index_Function;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.RunTime;

import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.AST;

public class Run_Any {

    private RunTime mRunTime;
    private Run_Arguments mPreparadorDeArgumentos;

    public Run_Any(RunTime eRunTime) {

        mRunTime = eRunTime;
        mPreparadorDeArgumentos = new Run_Arguments();


    }

    public Item init_Function(AST ASTCorrente, Escopo BuscadorDeVariaveis, Escopo mEscopo, String eRetorne, ArrayList<Index_Function> eFunctions) {

        // System.out.println("Procurando FUNC "  + ASTCorrente.getNome() + " com passado DEFAULT " + eAntepassado);


        String mLocal = "Run_Function";

        Item mRet = null;

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));

        String mTipagem = mPreparadorDeArgumentos.getTipagem(mArgumentos);

        if (mRunTime.getErros().size() > 0) {
            return null;
        }

        //   System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //  System.out.println(ASTCorrente.ImprimirArvoreDeInstrucoes());

        //  System.out.println("\t - Argumentos :  " + mArgumentos.size());

        boolean enc = false;
        boolean algum = false;
        boolean realizada = false;

        String sugestao = "";
        int sugestionando = -1;

        // System.out.println("\t - Executando Dentro :  " +this.getNome());

        //   System.out.println(" STRUCT :: "  +this.getNome());

        String eNome = ASTCorrente.getNome();

        Run_Arguments mRunArguments = new Run_Arguments();

        ArrayList<String> mRefers = new ArrayList<String>();
        mRefers.addAll(mEscopo.getRefers());
        mRefers.addAll(BuscadorDeVariaveis.getRefers());


        for (Index_Function mIndex_Function : eFunctions) {

            //  System.out.println("\t - Funcao :  " +mIndex_Function.getNome());
            //  for (AST ArgumentoC : mArgumentos) {
            //    System.out.println("\t\t - Arg :  " +ArgumentoC.getNome());
            // }


            if (mIndex_Function.mesmoNome(eNome)) {

                mIndex_Function.resolverTipagem(mRefers);

                if (mRunTime.getErros().size() > 0) {
                    break;
                }
                enc = true;

                if (mIndex_Function.getArgumentos().size() == mArgumentos.size()) {


                    algum = true;

                    int contagem = mRunArguments.conferirArgumentos(mRunTime, mEscopo, mIndex_Function.getArgumentos(), mArgumentos);

                    if (contagem == mArgumentos.size()) {

                        //  System.out.println(mEscopo.getNome() + " Run Function : " +mIndex_Function.getDefinicao() );

                        realizada = true;
                        if (mRunTime.getErros().size() > 0) {
                            break;
                        }

                        // System.out.println("\t - Executando Dentro :  " +this.getNome());
                        //  mPreparadorDeArgumentos.executar_Action(mRunTime,  mEscopo, mIndex_Function, mArgumentos);

                        mRet = mPreparadorDeArgumentos.executar_Function(mRunTime, mEscopo, mIndex_Function, mArgumentos, eRetorne);
                        break;

                    } else {
                        if (contagem > sugestionando) {
                            sugestionando = contagem;
                            sugestao = mIndex_Function.getDefinicao();
                        }
                    }


                }


            }

        }


        errar("Function", eNome, mTipagem, sugestao, enc, algum, realizada, mLocal);


        return mRet;
    }

    public void init_Action(AST ASTCorrente, Escopo BuscadorDeVariaveis, Escopo mEscopo, String eMensagem, ArrayList<Index_Action> eActions) {

        //   System.out.println(" -->> DENTRO : " + this.getStructNome() );
        //  System.out.println(" -->> Procurando ACTION " + this.getStructNome() + "." + ASTCorrente.getNome());

        String mLocal = "Run_Action";

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));

        String mTipagem = mPreparadorDeArgumentos.getTipagem(mArgumentos);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        // System.out.println("\t - Action Teste :  " + ASTCorrente.getNome() + " Passando Args " + mArgumentos.size());

        //  System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //System.out.println("\t - Argumentos :  " + argumentos);

        boolean enc = false;
        boolean algum = false;
        boolean realizada = false;

        String sugestao = "";
        int sugestionando = -1;

        //System.out.println(" Action --->> " + ASTCorrente.ImprimirArvoreDeInstrucoes());

        // System.out.println("\t - Executando Dentro :  " +this.getNome());

        ArrayList<String> mRefers = new ArrayList<String>();
        mRefers.addAll(mEscopo.getRefers());
        mRefers.addAll(BuscadorDeVariaveis.getRefers());
        mRefers.addAll(BuscadorDeVariaveis.getRefersOcultas());

        String eNome = ASTCorrente.getNome();

        Run_Arguments mRunArguments = new Run_Arguments();

        //  for (Index_Action mIndex_Function : eActions) {
        //          System.out.println("\t - Funcao :  " +mIndex_Function.getNome());
        //  }


        for (Index_Action mIndex_Function : eActions) {

            //  System.out.println("\t - Funcao :  " +mIndex_Function.getNome());
            //  for (AST ArgumentoC : mArgumentos) {
            //    System.out.println("\t\t - Arg :  " +ArgumentoC.getNome());
            // }

            if (mIndex_Function.mesmoNome(eNome)) {

                // System.out.println("Resolver Com :: " + BuscadorDeVariaveis.getNome());

                mIndex_Function.resolverTipagem(mRefers);

                //  System.out.println("\t - Action Teste :  " + mIndex_Function.getNome() + " -> " + mIndex_Function.getParametragem());

                if (mRunTime.getErros().size() > 0) {
                    break;
                }
                enc = true;

                if (mIndex_Function.getArgumentos().size() == mArgumentos.size()) {


                    algum = true;

                    int contagem = mRunArguments.conferirArgumentos(mRunTime, mEscopo, mIndex_Function.getArgumentos(), mArgumentos);

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }

                    if (contagem == mArgumentos.size()) {

                        realizada = true;
                        if (mRunTime.getErros().size() > 0) {
                            break;
                        }

                        // System.out.println("\t - Executando Dentro :  " +this.getNome());
                        //  mPreparadorDeArgumentos.executar_Action(mRunTime,  mEscopo, mIndex_Function, mArgumentos);
                        //   System.out.println(mEscopo.getNome() + " EA -> Structs : " + mEscopo.getStructs().size());

                        mPreparadorDeArgumentos.executar_Action(mRunTime, mEscopo, mIndex_Function, mArgumentos);

                        break;

                    } else {
                        if (contagem > sugestionando) {


                            sugestionando = contagem;
                            sugestao = mIndex_Function.getDefinicao();
                        }
                    }


                } else {


                }


            }

        }

        errar("Action", eNome, mTipagem, sugestao, enc, algum, realizada, mLocal);


    }

    public void init_ActionFunction(AST ASTCorrente, Escopo mEscopo) {


        init_Action(ASTCorrente, mEscopo, mEscopo, ASTCorrente.getNome(), mEscopo.getActionFunctionsCompleto());

    }

    public Item init_Operation(String eNome, Run_Value Esquerda, Run_Value Direita, Escopo mEscopo, String eReturne) {

        String mLocal = "Run_Operator";

        Item mItem = null;

        ArrayList<Item> mArgumentos = new ArrayList<Item>();

        Item ve = new Item("VALUE");
        Item vd = new Item("VALUE");


        ve.setNulo(Esquerda.getIsNulo());
        ve.setPrimitivo(Esquerda.getIsPrimitivo());
        ve.setIsEstrutura(Esquerda.getIsStruct());
        ve.setValor(Esquerda.getConteudo(), mRunTime, mEscopo);
        ve.setTipo(Esquerda.getRetornoTipo());


        vd.setNulo(Direita.getIsNulo());
        vd.setPrimitivo(Direita.getIsPrimitivo());
        vd.setIsEstrutura(Direita.getIsStruct());
        vd.setValor(Direita.getConteudo(), mRunTime, mEscopo);
        vd.setTipo(Direita.getRetornoTipo());


        //  System.out.println("DENTRO OPERADOR DIREITA -> " + vd.getTipo());
        //  System.out.println("DENTRO OPERADOR ESQUERDA -> " + ve.getTipo());
        //  System.out.println("DENTRO OPERADOR DIREITA -> " + vd.getValor());
        // System.out.println("DENTRO OPERADOR ESQUERDA -> " + ve.getValor());


        String mTipagem = ve.getTipo() + " e " + vd.getTipo();

        // System.out.println("TIPAGEM : " +mTipagem );

        mArgumentos.add(ve);
        mArgumentos.add(vd);

        //  System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //System.out.println("\t - Argumentos :  " + argumentos);

        boolean enc = false;
        boolean algum = false;
        boolean realizada = false;

        Run_Context mRun_Context = new Run_Context(mRunTime);

        ArrayList<AST> mOperadores = mRun_Context.getOperatorsContexto(mEscopo);

      //  Debugador mDebugador = new Debugador();
       // mDebugador.listarOperadores(mRunTime,mEscopo);

        //mRunTime.debugOperadores(mEscopo);

        String maisProxima = "";
        int mais = -1;

        Run_Arguments mRunArguments = new Run_Arguments();

        for (AST mAST : mOperadores) {

            // for (AST mAST : mRunTime.getGlobalOperations()) {

            //  System.out.println("\t - Procurando Operador :  " +eNome);

            if (mAST.mesmoNome(eNome)) {

                Index_Function mIndex_Function = new Index_Function(mRunTime, mEscopo, mAST);

                mIndex_Function.resolverTipagem(mEscopo.getRefers());

               //  System.out.println("\t - Resolvendo Operador :  " +mIndex_Function.getDefinicao() + " para " + mTipagem);

                enc = true;

                if (mIndex_Function.getArgumentos().size() == mArgumentos.size()) {

                    //   System.out.println("\t - Passando Funcao :  " +mIndex_Function.getNome() + " " + mIndex_Function.getParametragem());

                    algum = true;

                    int contagem = mRunArguments.conferirArgumentos(mRunTime, mEscopo, mIndex_Function.getArgumentos(), mArgumentos);

                    if (contagem == mArgumentos.size()) {

                        //  System.out.println("\t - Funcao :  " +mIndex_Function.getNome() + " " + mIndex_Function.getParametragem());
                        // System.out.println("\t - Executar :  " +mIndex_Function.getNome());

                        if (mRunTime.getErros().size() > 0) {
                            return null;
                        }

                        mItem = mPreparadorDeArgumentos.executar_FunctionGlobal(mRunTime, mIndex_Function, mArgumentos, eReturne);
                        realizada = true;

                        if (mRunTime.getErros().size() > 0) {
                            return null;
                        }

                        break;
                    } else {
                        if (contagem > mais) {
                            mais = contagem;
                            maisProxima = mIndex_Function.getDefinicao();
                        }
                    }

                }


            }

        }

        errar("Operator", eNome, mTipagem, maisProxima, enc, algum, realizada, mLocal);

        return mItem;

    }


    public Item init_Director(String eNome, Run_Value Esquerda, Escopo mEscopo, String eReturne) {

        String mLocal = "Run_Director";

        Item mItem = null;

        ArrayList<Item> mArgumentos = new ArrayList<Item>();

        Item ve = new Item("VALUE");


        ve.setNulo(Esquerda.getIsNulo());
        ve.setPrimitivo(Esquerda.getIsPrimitivo());
        ve.setIsEstrutura(Esquerda.getIsStruct());
        ve.setValor(Esquerda.getConteudo(), mRunTime, mEscopo);
        ve.setTipo(Esquerda.getRetornoTipo());


        String mTipagem = ve.getTipo();

        // System.out.println("Procurando Diretor : " + eNome + " " +mTipagem );

        mArgumentos.add(ve);

        //  System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //System.out.println("\t - Argumentos :  " + argumentos);

        boolean enc = false;
        boolean algum = false;


        Run_Context mRun_Context = new Run_Context(mRunTime);

        ArrayList<AST> mDirecionadores = mRun_Context.getDirectorsContexto(mEscopo);


        for (AST mAST : mDirecionadores) {


            Index_Function mIndex_Function = new Index_Function(mRunTime, mEscopo, mAST);

            if (mIndex_Function.mesmoNome(eNome)) {


                enc = true;
                if (mIndex_Function.mesmoArgumentos(mEscopo, mArgumentos)) {


                    //   System.out.println("\t - Funcao :  " +mIndex_Function.getNome() + " " + mIndex_Function.getParametragem());

                    // System.out.println("\t - Executar :  " +mIndex_Function.getNome());

                    algum = true;

                    //  if (mIndex_Function.getTipo().contentEquals(eReturne) || eReturne.contentEquals("<<ANY>>")) {

                    if (mRunTime.getErros().size() > 0) {
                        return null;
                    }


                    //  executar_Function(mIndex_Function, mArgumentos, eReturne);

                    mItem = mPreparadorDeArgumentos.executar_FunctionGlobal(mRunTime, mIndex_Function, mArgumentos, eReturne);


                    // } else {
                    //       mRunTime.errar(mLocal,"Director " + eNome + " : Retorno incompativel !");
                    // }

                    break;
                }


            }

        }


        if (enc) {
            if (!algum) {
                mRunTime.errar(mLocal, "Director " + eNome + " : Argumentos incompativeis : " + mTipagem);
            }
        } else {

            String mTipando = "";

            int i = 0;
            int t = mArgumentos.size();

            for (Item ArgumentoC : mArgumentos) {
                i += 1;

                if (i < t) {
                    mTipando += ArgumentoC.getTipo() + " x ";
                } else {
                    mTipando += ArgumentoC.getTipo() + " ";
                }


            }


            mRunTime.errar(mLocal, "Director  " + eNome + " -> " + mTipando + " : Nao Encontrada !");
        }

        return mItem;

    }

    public void init_inits(AST ASTCorrente, Escopo BuscadorDeVariaveis, Escopo mEscopo, ArrayList<Index_Action> eInits) {

        //   System.out.println(" -->> DENTRO : " + this.getStructNome() );
        //  System.out.println(" -->> Procurando ACTION " + this.getStructNome() + "." + ASTCorrente.getNome());

        //  System.out.println("Existem inits : " + eInits.size());

        // for (Index_Action mIndex_Function : eInits) {
        //    System.out.println("\t - Existe init : " + mIndex_Function.getNome());
        //  }


        String mLocal = "Run_Init";

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));

        String mTipagem = mPreparadorDeArgumentos.getTipagem(mArgumentos);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        // System.out.println("\t - Action Teste :  " + ASTCorrente.getNome() + " Passando Args " + mArgumentos.size());

        //  System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //System.out.println("\t - Argumentos :  " + argumentos);

        boolean enc = false;
        boolean algum = false;
        boolean realizada = false;

        String sugestao = "";
        int sugestionando = -1;

        //System.out.println(" Action --->> " + ASTCorrente.ImprimirArvoreDeInstrucoes());

        // System.out.println("\t - Executando Dentro :  " +this.getNome());


        ArrayList<String> mRefers = new ArrayList<String>();
        mRefers.addAll(mEscopo.getRefers());
        mRefers.addAll(BuscadorDeVariaveis.getRefers());
        mRefers.addAll(BuscadorDeVariaveis.getRefersOcultas());

        String eNome = ASTCorrente.getNome();

        Run_Arguments mRunArguments = new Run_Arguments();


        for (Index_Action mIndex_Function : eInits) {

            //  System.out.println("\t - Funcao :  " +mIndex_Function.getNome());
            //  for (AST ArgumentoC : mArgumentos) {
            //    System.out.println("\t\t - Arg :  " +ArgumentoC.getNome());
            // }

            if (mIndex_Function.mesmoNome(eNome)) {

                // System.out.println("Resolver Com :: " + BuscadorDeVariaveis.getNome());

                mIndex_Function.resolverTipagem(mRefers);

                //  System.out.println("\t - Action Teste :  " + mIndex_Function.getNome() + " -> " + mIndex_Function.getParametragem());

                if (mRunTime.getErros().size() > 0) {
                    break;
                }
                enc = true;

                if (mIndex_Function.getArgumentos().size() == mArgumentos.size()) {


                    algum = true;

                    int contagem = mRunArguments.conferirArgumentos(mRunTime, mEscopo, mIndex_Function.getArgumentos(), mArgumentos);

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }

                    if (contagem == mArgumentos.size()) {

                        realizada = true;
                        if (mRunTime.getErros().size() > 0) {
                            break;
                        }

                        // System.out.println("\t - Executando Dentro :  " +this.getNome());
                        //  mPreparadorDeArgumentos.executar_Action(mRunTime,  mEscopo, mIndex_Function, mArgumentos);
                        //   System.out.println(mEscopo.getNome() + " EA -> Structs : " + mEscopo.getStructs().size());

                        mPreparadorDeArgumentos.executar_Action(mRunTime, mEscopo, mIndex_Function, mArgumentos);

                        break;

                    } else {
                        if (contagem > sugestionando) {


                            sugestionando = contagem;
                            sugestao = mIndex_Function.getDefinicao();
                        }
                    }


                } else {


                }


            }

        }

        errar("Init", eNome, mTipagem, sugestao, enc, algum, realizada, mLocal);


    }


    public void errar(String eGrupo, String eNome, String mTipagem, String sugestao, boolean enc, boolean algum, boolean realizada, String mLocal) {

        if (enc) {
            if (algum) {
                if (realizada) {
                } else {
                    mRunTime.errar(mLocal, eGrupo + " " + eNome + " : Argumentos incompativeis : " + mTipagem);
                    mRunTime.errar(mLocal, "\t Sugestao : " + sugestao);
                }
            } else {
                mRunTime.errar(mLocal, eGrupo + "  " + eNome + " " + mTipagem + " : Quantidade de Argumentos incompativeis !");
            }
        } else {
            mRunTime.errar(mLocal, eGrupo + "  " + eNome + " -> " + mTipagem + " : Nao Encontrada !");
        }


    }


    public Item init_Mark(AST ASTCorrente, Escopo BuscadorDeVariaveis, Escopo mEscopo, String eRetorne, ArrayList<Index_Function> eMarcadores) {

        // System.out.println("Procurando FUNC "  + ASTCorrente.getNome() + " com passado DEFAULT " + eAntepassado);


        String mLocal = "Run_Mark";

        String eMarcador = ASTCorrente.getBranch("MARK").getNome();

        Run_Value mRValue = new Run_Value(mRunTime, mEscopo);
        mRValue.init(ASTCorrente.getBranch("VALUE"), eRetorne);

        if (mRunTime.getErros().size() > 0) {
            return null;
        }


        ArrayList<Item> mArgumentos = new ArrayList<Item>();

        Item ve = new Item("VALUE");


        ve.setNulo(mRValue.getIsNulo());
        ve.setPrimitivo(mRValue.getIsPrimitivo());
        ve.setIsEstrutura(mRValue.getIsStruct());
        ve.setValor(mRValue.getConteudo(), mRunTime, mEscopo);
        ve.setTipo(mRValue.getRetornoTipo());


        String mTipagem = ve.getTipo();

        // System.out.println("Procurando Diretor : " + eNome + " " +mTipagem );

        mArgumentos.add(ve);

        Item mRet = null;


        if (mRunTime.getErros().size() > 0) {
            return null;
        }

        //   System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //  System.out.println(ASTCorrente.ImprimirArvoreDeInstrucoes());

        //  System.out.println("\t - Argumentos :  " + mArgumentos.size());

        boolean enc = false;
        boolean algum = false;
        boolean realizada = false;

        String sugestao = "";
        int sugestionando = -1;

        // System.out.println("\t - Executando Dentro :  " +this.getNome());

        //   System.out.println(" STRUCT :: "  +this.getNome());

        String eNome = eMarcador;

        Run_Arguments mRunArguments = new Run_Arguments();

        ArrayList<String> mRefers = new ArrayList<String>();
        mRefers.addAll(mEscopo.getRefers());
        mRefers.addAll(BuscadorDeVariaveis.getRefers());


        for (Index_Function mIndex_Function : eMarcadores) {

            //  System.out.println("\t - Funcao :  " +mIndex_Function.getNome());
            //  for (AST ArgumentoC : mArgumentos) {
            //    System.out.println("\t\t - Arg :  " +ArgumentoC.getNome());
            // }


            if (mIndex_Function.mesmoNome(eNome)) {

                mIndex_Function.resolverTipagem(mRefers);

                if (mRunTime.getErros().size() > 0) {
                    break;
                }
                enc = true;

                if (mIndex_Function.getArgumentos().size() == mArgumentos.size()) {


                    algum = true;

                    int contagem = mRunArguments.conferirArgumentos(mRunTime, mEscopo, mIndex_Function.getArgumentos(), mArgumentos);

                    if (contagem == mArgumentos.size()) {

                        realizada = true;
                        if (mRunTime.getErros().size() > 0) {
                            break;
                        }

                        // System.out.println("\t - Executando Dentro :  " +this.getNome());
                        //  mPreparadorDeArgumentos.executar_Action(mRunTime,  mEscopo, mIndex_Function, mArgumentos);
                        //   System.out.println(mEscopo.getNome() + " EA -> Structs : " + mEscopo.getStructs().size());

                        mRet = mPreparadorDeArgumentos.executar_Function(mRunTime, mEscopo, mIndex_Function, mArgumentos, eRetorne);
                        break;

                    } else {
                        if (contagem > sugestionando) {
                            sugestionando = contagem;
                            sugestao = mIndex_Function.getDefinicao();
                        }
                    }


                }


            }

        }


        errar("Marcador", eNome, mTipagem, sugestao, enc, algum, realizada, mLocal);


        return mRet;
    }


}
