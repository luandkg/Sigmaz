package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_Using {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public Run_Using(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Using";

    }


    public void init(AST eAST) {

        // System.out.println("Oi Using :: " + eAST.ImprimirArvoreDeInstrucoes());


        Item eItem = mEscopo.getItem(eAST.getNome());
        if (!eItem.getNulo()) {

            Run_Struct mRun_GetType = mRunTime.getRun_Struct(eItem.getValor(mRunTime, mEscopo));

            //  mRun_GetType.getEscopo().setAnterior(mEscopo);

            Escopo novoEscopo = new Escopo(mRunTime, mEscopo);

            for (Item gItem : mRun_GetType.getEscopo().getStacks()) {

                if (mRun_GetType.getStack_All().contains(gItem.getNome())) {
                    System.out.println("Alocado na Struct :: " + gItem.getNome());

                    novoEscopo.passarParametroByRefObrigatorio(gItem.getNome(),gItem);
                }

            }

            for (AST mDentro : eAST.getBranch("TYPED").getASTS()) {

                // Run_Apply mRUA = new Run_Apply(mRunTime, mRun_GetType.getEscopo());
                // mRUA.init(mDentro);

                // System.out.println("EXECUTAR DENTRO :: " + mDentro.getTipo());

                // System.out.println("-------- ANTES ---------");
                //  System.out.println(mDentro.ImprimirArvoreDeInstrucoes());
                //// System.out.println("-------- DEPOIS ---------");

                if (mDentro.mesmoTipo("EXECUTE") && mDentro.mesmoValor("FUNCT")) {

                    AST novo = mDentro.copiar();

                    AST modelando = new AST("EXECUTE");
                    modelando.setNome(eAST.getNome());
                    modelando.setValor("STRUCT");

                    novo.setTipo("INTERNAL");
                    novo.setValor("STRUCT_FUNCT");
                    modelando.getASTS().add(novo);

                    //  System.out.println(modelando.ImprimirArvoreDeInstrucoes());

                    Run_Execute mRun = new Run_Execute(mRunTime, novoEscopo);
                    mRun.init(modelando);

                    if (mRunTime.getErros().size() > 0) {
                        break;
                    }

                } else if (mDentro.mesmoTipo("APPLY")) {

                    AST novo = mDentro.copiar();

                    AST modelando = new AST("APPLY");

                    if (mDentro.getBranch("SETTABLE").mesmoValor("ID")) {

                        AST eSettable = modelando.criarBranch("SETTABLE");
                        eSettable.setNome(eAST.getNome());
                        eSettable.setValor("STRUCT");

                        AST eInternal = novo.getBranch("SETTABLE");
                        eInternal.setTipo("INTERNAL");
                        eInternal.setValor("STRUCT_OBJECT");
                        eSettable.getASTS().add(eInternal);

                        modelando.getASTS().add(novo.getBranch("VALUE"));

                        //System.out.println(modelando.ImprimirArvoreDeInstrucoes());

                        Run_Apply mRun = new Run_Apply(mRunTime, novoEscopo);
                        mRun.init(modelando);

                        if (mRunTime.getErros().size() > 0) {
                            break;
                        }


                    } else {
                        mRunTime.errar(mLocal, "Problema com using !");
                    }


                } else {

                    mRunTime.errar(mLocal, "Problema com using !");

                }

            }

        } else {
            mRunTime.errar(mLocal, "Struct " + eAST.getNome() + " : Nao Encontrada !");
        }


    }


}
