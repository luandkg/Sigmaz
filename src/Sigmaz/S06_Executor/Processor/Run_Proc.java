package Sigmaz.S06_Executor.Processor;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S06_Executor.Escopo;
import Sigmaz.S06_Executor.RunTime;

import java.util.ArrayList;

public class Run_Proc {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    private ArrayList<String> mADD;

    public Run_Proc(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Proc";

        mADD = new ArrayList<String>();
        for (int i = 5; i < 13; i++) {
            mADD.add("R" + i);
        }
    }


    public void init(AST ASTCorrente) {


        for (AST mAST : ASTCorrente.getASTS()) {

            if (mRunTime.getErros().size() > 0) {
                break;
            }

            if (mAST.mesmoTipo("SET")) {

                mRunTime.getProcessador().apontar(mAST.getNome());

            } else if (mAST.mesmoTipo("MOV")) {

                instrucao_MOV(mAST);


            } else if (mAST.mesmoTipo("OPE")) {

                instrucao_OPE(mAST);

            } else if (mAST.mesmoTipo("PROC")) {

                instrucao_PROC(mAST);

            } else {

                mRunTime.errar(mLocal, "Instrucao desconhecida : " + mAST.getTipo());

            }


        }


    }


    public Registrado getConteudo(AST eAST) {

        Registrado mRegistrado = new Registrado();

        if (eAST.mesmoValor("ID")) {
            if (eAST.mesmoNome_SCS("TRUE") || eAST.mesmoNome_SCS("FALSE")) {

                mRegistrado.atribuir(eAST.getNome().toLowerCase(), "Logico");

            } else {

                mRegistrado = mRunTime.getProcessador().getRegistrador(eAST.getNome().toUpperCase());

            }
        } else if (eAST.mesmoValor("Num")) {

            mRegistrado.atribuir(eAST.getNome(), "Inteiro");


        } else if (eAST.mesmoValor("Float")) {

            mRegistrado.atribuir(eAST.getNome(), "Real");

        } else if (eAST.mesmoValor("Text")) {

            mRegistrado.atribuir(eAST.getNome(), "Texto");

        }


        return mRegistrado;

    }


    public void instrucao_MOV(AST eAST) {

        String eSetada = mRunTime.getProcessador().getApontando();
        Registrado eConteudo = getConteudo(eAST);


        // System.out.println("MOV");
        // System.out.println("\t - Aplicar : " + eSetada);
        //  System.out.println("\t - Tipo : " + eConteudo.getTipo());
        // System.out.println("\t - Valor : " + eConteudo.getConteudo());

        mRunTime.getProcessador().aplicar(eSetada, eConteudo);


    }

    public void instrucao_OPE(AST eAST) {

        String eSetada = mRunTime.getProcessador().getApontando();
        String eOperador = eAST.getNome().toUpperCase();

        Registrado eConteudo_Direita = getConteudo(eAST.getBranch("RIGHT"));
        Registrado eConteudo_Esquerda = getConteudo(eAST.getBranch("LEFT"));


      //  System.out.println("OPE");
      //  System.out.println("\t - Aplicar : " + eSetada);
      //  System.out.println("\t - Operador : " + eOperador);

      //  System.out.println("\t - Direita Tipo : " + eConteudo_Direita.getTipo());
     //   System.out.println("\t - Direita Valor : " + eConteudo_Direita.getConteudo());

      //  System.out.println("\t - Esquerda Tipo : " + eConteudo_Esquerda.getTipo());
     //   System.out.println("\t - Esquerda Valor : " + eConteudo_Esquerda.getConteudo());

        if (eOperador.contentEquals("ADD")) {
            operador_Inteiro(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("SUB")) {
            operador_Inteiro(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("DIV")) {
            operador_Inteiro(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("MUX")) {
            operador_Inteiro(eOperador, eConteudo_Direita, eConteudo_Esquerda);



        } else if (eOperador.contentEquals("SAX")) {
            operador_Real(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("VEZ")) {
            operador_Real(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("PUC")) {
            operador_Real(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("INK")) {
            operador_Real(eOperador, eConteudo_Direita, eConteudo_Esquerda);



        } else {

            mRunTime.errar(mLocal, "Processador - Operador desconhecido : " + eOperador);

        }

        //  mRunTime.getProcessador().aplicar(eSetada,eConteudo);


    }


    public void operador_Inteiro(String eOperador, Registrado eDireita, Registrado eEsquerda) {


        if (eDireita.getTipo().contentEquals("Inteiro")) {
            if (eEsquerda.getTipo().contentEquals("Inteiro")) {

                long A = 0;
                long B = 0;

                try {
                    A = Long.parseLong(eDireita.getConteudo());
                } catch (Exception e) {
                    mRunTime.errar(mLocal, "A instrucao " + eOperador + " nao pode ser realizada com valor do tipo : " + eDireita.getTipo());
                    return;
                }

                try {
                    B = Long.parseLong(eEsquerda.getConteudo());
                } catch (Exception e) {
                    mRunTime.errar(mLocal, "A instrucao " + eOperador + " nao pode ser realizada com valor do tipo : " + eEsquerda.getTipo());
                    return;
                }

                if (eOperador.contentEquals("ADD")) {

                    long C = A + B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Inteiro");
                    mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);
                } else if (eOperador.contentEquals("SUB")) {

                    long C = A - B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Inteiro");
                    mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);
                } else if (eOperador.contentEquals("MUX")) {

                    long C = A * B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Inteiro");
                    mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);
                } else if (eOperador.contentEquals("DIV")) {

                    if (B == 0) {
                        mRunTime.errar(mLocal, "Processador - Operante DIV = 0 ");
                        return;
                    }

                    long C = A / B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Inteiro");
                    mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);


                } else {

                    mRunTime.errar(mLocal, "Processador - Operador desconhecido : " + eOperador);

                }


            } else {
                mRunTime.errar(mLocal, "A instrucao " + eOperador + " nao pode ser realizada com valor do tipo : " + eEsquerda.getTipo());
            }
        } else {
            mRunTime.errar(mLocal, "A instrucao " + eOperador + " nao pode ser realizada com valor do tipo : " + eDireita.getTipo());
        }

    }

    public void operador_Real(String eOperador, Registrado eDireita, Registrado eEsquerda) {


        if (eDireita.getTipo().contentEquals("Real")) {
            if (eEsquerda.getTipo().contentEquals("Real")) {

                float A = 0.0F;
                float B = 0.0F;

                try {
                    A = Float.parseFloat(eDireita.getConteudo());
                } catch (Exception e) {
                    mRunTime.errar(mLocal, "A instrucao " + eOperador + " nao pode ser realizada com valor do tipo : " + eDireita.getTipo());
                    return;
                }

                try {
                    B = Float.parseFloat(eEsquerda.getConteudo());
                } catch (Exception e) {
                    mRunTime.errar(mLocal, "A instrucao " + eOperador + " nao pode ser realizada com valor do tipo : " + eEsquerda.getTipo());
                    return;
                }

                if (eOperador.contentEquals("SAX")) {

                    float C = A + B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Real");
                    mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);
                } else if (eOperador.contentEquals("VEZ")) {

                    float C = A - B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Real");
                    mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);
                } else if (eOperador.contentEquals("PUC")) {

                    float C = A * B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Real");
                    mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);
                } else if (eOperador.contentEquals("INK")) {

                    if (B == 0.0F) {
                        mRunTime.errar(mLocal, "Processador - Operante INK = 0.0 ");
                        return;
                    }

                    float C = A / B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Real");
                    mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);


                } else {

                    mRunTime.errar(mLocal, "Processador - Operador desconhecido : " + eOperador);

                }


            } else {
                mRunTime.errar(mLocal, "A instrucao " + eOperador + " nao pode ser realizada com valor do tipo : " + eEsquerda.getTipo());
            }
        } else {
            mRunTime.errar(mLocal, "A instrucao " + eOperador + " nao pode ser realizada com valor do tipo : " + eDireita.getTipo());
        }

    }


    public void instrucao_PROC(AST eAST) {


        if (eAST.mesmoValor("ID")) {

            if (eAST.mesmoNome_SCS("DEBUG")) {
                mRunTime.getProcessador().debug();

            } else if (eAST.mesmoNome_SCS("ZERO")) {

                mRunTime.getProcessador().zerar();

            } else {
                mRunTime.errar(mLocal, "Comando PROC desconhecido : " + eAST.getNome());
            }

        } else {
            mRunTime.errar(mLocal, "Comando PROC Corrompido !");
        }


    }

}
