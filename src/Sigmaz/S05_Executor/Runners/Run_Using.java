package Sigmaz.S05_Executor.Runners;

import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Escopos.Run_Struct;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.RunTime;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S05_Executor.Run_Pass;

import java.util.ArrayList;

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


        Run_Value eRV = new Run_Value(mRunTime, mEscopo);
        eRV.init(eAST.getBranch("STRUCT"), "<<ANY>>");


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        ArrayList<String> aRefers = mEscopo.getRefers();
        aRefers.addAll(mEscopo.getRefersOcultas());
        Run_Context mRun_Context = new Run_Context(mRunTime);

        String eQualificador = mRun_Context.getQualificador(eRV.getRetornoTipo(), mEscopo);


        if (eQualificador.contentEquals("STRUCT")) {


            if (!eRV.getIsNulo()) {

                Run_Struct mRun_GetType = mRunTime.getHeap().getRun_Struct(eRV.getConteudo());

                //  mRun_GetType.getEscopo().setAnterior(mEscopo);

                Escopo novoEscopo = new Escopo(mRunTime, mEscopo);

                String eParam = "{{USING_" + eRV.getRetornoTipo() + "}}";

                novoEscopo.criarParametroStruct(eParam, eRV.getRetornoTipo(), eRV.getConteudo());

              //  novoEscopo.getDebug().mapear_stack();


                for (Item gItem : mRun_GetType.getEscopo().getStacks()) {

                    if (mRun_GetType.getStack_All().contains(gItem.getNome())) {
                        //   System.out.println("Alocado na Struct :: " + gItem.getNome());

                        Run_Pass  mRun_Pass = new Run_Pass(mRunTime, novoEscopo);
                        mRun_Pass.passarParametroByRefObrigatorio(gItem.getNome(), gItem);

                    }

                }

                for (AST mDentro : eAST.getBranch("STRUCTURED").getASTS()) {

                    // Run_Apply mRUA = new Run_Apply(mRunTime, mRun_GetType.getEscopo());
                    // mRUA.init(mDentro);

                    // System.out.println("EXECUTAR DENTRO :: " + mDentro.getTipo());

                    // System.out.println("-------- ANTES ---------");
                    //  System.out.println(mDentro.ImprimirArvoreDeInstrucoes());
                    //// System.out.println("-------- DEPOIS ---------");

                    if (mDentro.mesmoTipo("EXECUTE") && mDentro.mesmoValor("FUNCT")) {

                        AST novo = mDentro.copiar();

                        AST modelando = new AST("EXECUTE");
                        modelando.setNome(eParam);
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
                            eSettable.setNome(eParam);
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
                mRunTime.errar(mLocal, "Struct " + eRV.getConteudo() + " : Nao Encontrada !");
            }


        } else {


            mRunTime.errar(mLocal, "Nao e possivel realizar Using em : " + eRV.getRetornoTipo());

        }


    }


}
