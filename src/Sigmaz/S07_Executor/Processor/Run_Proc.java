package Sigmaz.S07_Executor.Processor;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.RunTime;

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

            } else if (mAST.mesmoTipo("SIZE")) {

                instrucao_SIZE(mAST);

            } else if (mAST.mesmoTipo("APPEND")) {

                instrucao_APPEND(mAST);

            } else if (mAST.mesmoTipo("INT")) {

                instrucao_INT(mAST);

            } else if (mAST.mesmoTipo("REAL")) {

                instrucao_REAL(mAST);

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

    public void instrucao_SIZE(AST eAST) {

        String eSetada = mRunTime.getProcessador().getApontando();
        Registrado eConteudo = getConteudo(eAST);

        if (eConteudo.getTipo().contentEquals("Texto")) {
            long e = eConteudo.getConteudo().length();

            Registrado mr = new Registrado();
            mr.atribuir(String.valueOf(e), "Inteiro");
            mRunTime.getProcessador().aplicar(eSetada, mr);

        } else {
            mRunTime.errar(mLocal, "Processador - A Instrucao SIZE so executa nos registradores R13 e R14");
        }

    }

    public void instrucao_APPEND(AST eAST) {

        String eSetada = mRunTime.getProcessador().getApontando();
        Registrado eConteudo = getConteudo(eAST);

        Registrado mra = mRunTime.getProcessador().getApontandoConteudo();

        if (mra.getTipo().contentEquals("Texto")) {

            if (eConteudo.getTipo().contentEquals("Texto")) {
                String e = eConteudo.getConteudo();

                Registrado mr = new Registrado();
                mr.atribuir(mra.getConteudo() + eConteudo.getConteudo(), "Texto");


                mRunTime.getProcessador().aplicar(eSetada, mr);

            } else {
                mRunTime.errar(mLocal, "Processador - A Instrucao APPEND so executa nos registradores R13 e R14");
            }

        } else {

            mRunTime.errar(mLocal, "Processador - A Instrucao APPEND so executa nos registradores R13 e R14");

        }


    }


    public void instrucao_INT(AST eAST) {

        String eSetada = mRunTime.getProcessador().getApontando();
        Registrado eConteudo = getConteudo(eAST);

        if (eConteudo.getTipo().contentEquals("Real")) {
            String e = eConteudo.getConteudo();

            Registrado mr = new Registrado();
            mr.atribuir(getInteiro(e), "Inteiro");

            mRunTime.getProcessador().aplicar(eSetada, mr);
        } else if (eConteudo.getTipo().contentEquals("Inteiro")) {

            mRunTime.getProcessador().aplicar(eSetada, eConteudo);

        } else {
            mRunTime.errar(mLocal, "Processador - A Instrucao INT so executa nos registradores Inteiros e Reais");
        }

    }

    public void instrucao_REAL(AST eAST) {


        String eSetada = mRunTime.getProcessador().getApontando();
        Registrado eConteudo = getConteudo(eAST);

        if (eConteudo.getTipo().contentEquals("Real")) {

            mRunTime.getProcessador().aplicar(eSetada, eConteudo);

        } else if (eConteudo.getTipo().contentEquals("Inteiro")) {

            Registrado novo = new Registrado();
            novo.atribuir(eConteudo.getConteudo() + ".0","Real");

            mRunTime.getProcessador().aplicar(eSetada, novo);

        } else {
            mRunTime.errar(mLocal, "Processador - A Instrucao REAL so executa nos registradores Inteiros e Reais");
        }

    }

    public String getInteiro(String e) {

        int i = 0;
        int o = e.length();

        String ret = "";

        while (i < o) {

            String ll = String.valueOf(e.charAt(i));

            if (ll.contentEquals(".")) {
                break;
            } else if (ll.contentEquals(";")) {
                break;
            } else {
                ret += ll;
            }

            i += 1;
        }


        return ret;

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

        } else if (eOperador.contentEquals("LESS")) {
            operador_Inteiro_Logico(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("GREAT")) {
            operador_Inteiro_Logico(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("EQUAL")) {
            operador_Inteiro_Logico(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("DIFF")) {
            operador_Inteiro_Logico(eOperador, eConteudo_Direita, eConteudo_Esquerda);


        } else if (eOperador.contentEquals("SAX")) {
            operador_Real(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("VEZ")) {
            operador_Real(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("PUC")) {
            operador_Real(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("INK")) {
            operador_Real(eOperador, eConteudo_Direita, eConteudo_Esquerda);


        } else if (eOperador.contentEquals("MOZZ")) {
            operador_Real_Logico(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("DROEN")) {
            operador_Real_Logico(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("OPUAL")) {
            operador_Real_Logico(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("TOLL")) {
            operador_Real_Logico(eOperador, eConteudo_Direita, eConteudo_Esquerda);


        } else if (eOperador.contentEquals("CHAR")) {
            operador_Texto(eOperador, eConteudo_Esquerda, eConteudo_Direita);


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


    public void operador_Inteiro_Logico(String eOperador, Registrado eDireita, Registrado eEsquerda) {


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

                if (eOperador.contentEquals("LESS")) {

                    boolean C = A < B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Logico");
                    mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);
                } else if (eOperador.contentEquals("GREAT")) {

                    boolean C = A > B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Logico");
                    mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);
                } else if (eOperador.contentEquals("EQUAL")) {

                    boolean C = A == B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Logico");
                    mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);
                } else if (eOperador.contentEquals("DIFF")) {


                    boolean C = A != B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Logico");
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

    public void operador_Real_Logico(String eOperador, Registrado eDireita, Registrado eEsquerda) {


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

                if (eOperador.contentEquals("MOZZ")) {

                    boolean C = A < B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Logico");
                    mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);
                } else if (eOperador.contentEquals("DROEN")) {

                    boolean C = A > B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Logico");
                    mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);
                } else if (eOperador.contentEquals("OPUAL")) {

                    boolean C = A == B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Logico");
                    mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);
                } else if (eOperador.contentEquals("TOLL")) {


                    boolean C = A != B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Logico");
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


    public void operador_Texto(String eOperador, Registrado eDireita, Registrado eEsquerda) {


        //   System.out.println("Dir : " + eDireita.getTipo() + " = " + eDireita.getConteudo());
        //  System.out.println("Esq : " + eEsquerda.getTipo() + " = " + eEsquerda.getConteudo());

        if (eDireita.getTipo().contentEquals("Inteiro")) {
            if (eEsquerda.getTipo().contentEquals("Texto")) {

                String A = "";
                int B = 0;

                try {
                    A = eEsquerda.getConteudo();
                } catch (Exception e) {
                    mRunTime.errar(mLocal, "A instrucao " + eOperador + " nao pode ser realizada com valor do tipo : " + eDireita.getTipo());
                    return;
                }

                try {
                    B = Integer.parseInt(eDireita.getConteudo());
                } catch (Exception e) {
                    mRunTime.errar(mLocal, "A instrucao " + eOperador + " nao pode ser realizada com valor do tipo : " + eEsquerda.getTipo());
                    return;
                }

                if (eOperador.contentEquals("CHAR")) {


                    int tamanho = A.length();

                    if (tamanho < 0) {
                        mRunTime.errar(mLocal, " A Instrucao CHAR : Possui problemas de Limite Inferior");

                    } else if (tamanho >= 0 && B < tamanho) {

                        String C = String.valueOf(A.charAt(B));

                        Registrado mReg = new Registrado();
                        mReg.atribuir(C, "Texto");
                        mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);

                    } else {

                        mRunTime.errar(mLocal, " A Instrucao CHAR : Possui problemas de Limite Superior ");


                    }


                } else {

                    mRunTime.errar(mLocal, "Processador - Operador desconhecido : " + eOperador);

                }


            } else {
                mRunTime.errar(mLocal, "A instrucao e " + eOperador + " nao pode ser realizada com valor do tipo : " + eEsquerda.getTipo());
            }
        } else {
            mRunTime.errar(mLocal, "A instrucao d " + eOperador + " nao pode ser realizada com valor do tipo : " + eDireita.getTipo());
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
