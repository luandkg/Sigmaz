package Sigmaz.S05_Executor.Processor;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.RunTime;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

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

            } else if (mAST.mesmoTipo("INT_STRING")) {

                instrucao_INT_STRING(mAST);

            } else if (mAST.mesmoTipo("NUM_STRING")) {

                instrucao_NUM_STRING(mAST);

            } else if (mAST.mesmoTipo("BOOL_STRING")) {

                instrucao_BOOL_STRING(mAST);

            } else if (mAST.mesmoTipo("BOOL_INVERSE")) {

                instrucao_BOOL_INVERSE(mAST);

            } else if (mAST.mesmoTipo("NUM_INT")) {

                instrucao_NUM_INT(mAST);

            } else if (mAST.mesmoTipo("NUM_DEC")) {

                instrucao_NUM_DEC(mAST);


            } else if (mAST.mesmoTipo("PRINT")) {

                instrucao_PRINT(mAST);


            } else if (mAST.mesmoTipo("INVOKE")) {

                instrucao_PROC(mAST);

            } else if (mAST.mesmoTipo("OPEN")) {

                instrucao_OPEN(mAST);

            } else if (mAST.mesmoTipo("OPEN_OR_CREATE")) {

                instrucao_OPEN_OR_CREATE(mAST);

            } else if (mAST.mesmoTipo("CLOSE")) {

                instrucao_CLOSE(mAST);

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
        } else if (eAST.mesmoValor("INT")) {

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

    public void instrucao_INT_STRING(AST eAST) {

        Registrado eConteudo = getConteudo(eAST);

        if (eConteudo.getTipo().contentEquals("Inteiro")) {
            Registrado mRegistrado = new Registrado();
            mRegistrado.atribuir(eConteudo.getConteudo(), "Texto");

            mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mRegistrado);

        } else {
            mRunTime.errar(mLocal, "Processador - A Instrucao INT_STRING so executa com inteiros !");
        }


    }

    public void instrucao_NUM_STRING(AST eAST) {

        Registrado eConteudo = getConteudo(eAST);

        if (eConteudo.getTipo().contentEquals("Real")) {
            Registrado mRegistrado = new Registrado();
            mRegistrado.atribuir(eConteudo.getConteudo(), "Texto");

            mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mRegistrado);

        } else {
            mRunTime.errar(mLocal, "Processador - A Instrucao NUM_STRING so executa com reais !");
        }

    }

    public void instrucao_BOOL_STRING(AST eAST) {

        Registrado eConteudo = getConteudo(eAST);

        if (eConteudo.getTipo().contentEquals("Logico")) {
            Registrado mRegistrado = new Registrado();
            mRegistrado.atribuir(eConteudo.getConteudo(), "Texto");

            mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mRegistrado);

        } else {
            mRunTime.errar(mLocal, "Processador - A Instrucao BOOL_STRING so executa com logicos !");
        }

    }

    public void instrucao_BOOL_INVERSE(AST eAST) {

        Registrado eConteudo = getConteudo(eAST);

        if (eConteudo.getTipo().contentEquals("Logico")) {
            Registrado mRegistrado = new Registrado();

            if (eConteudo.getConteudo().contentEquals("true")) {
                mRegistrado.atribuir("false", "Logico");
            } else {
                mRegistrado.atribuir("true", "Logico");
            }

            mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mRegistrado);

        } else {
            mRunTime.errar(mLocal, "Processador - A Instrucao BOOL_INVERSE so executa com logicos !");
        }

    }


    public void instrucao_NUM_INT(AST eAST) {

        Registrado eConteudo = getConteudo(eAST);

        if (eConteudo.getTipo().contentEquals("Real")) {

            String s = String.valueOf(eConteudo.getConteudo());
            String inteiro = "";

            int index = 0;
            int o = s.length();
            while (index < o) {
                String v = s.charAt(index) + "";
                if (v.contentEquals(".")) {
                    break;
                } else {
                    inteiro += v;
                }
                index += 1;
            }


            Registrado mRegistrado = new Registrado();
            mRegistrado.atribuir(inteiro + ".0", "Real");

            mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mRegistrado);

        } else {
            mRunTime.errar(mLocal, "Processador - A Instrucao NUM_INT so executa com Reais !");
        }

    }

    public void instrucao_NUM_DEC(AST eAST) {

        Registrado eConteudo = getConteudo(eAST);

        if (eConteudo.getTipo().contentEquals("Real")) {


            String p1 = eConteudo.getConteudo();
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            String s = String.valueOf(p1);
            String inteiro = "";

            int index = 0;
            int o = s.length();
            while (index < o) {
                String v = s.charAt(index) + "";
                if (v.contentEquals(".")) {
                    index += 1;
                    break;
                } else {

                }
                index += 1;
            }

            while (index < o) {
                String v = s.charAt(index) + "";

                inteiro += v;

                index += 1;
            }
            if (inteiro.length() == 0) {
                inteiro = "0";
            }


            Registrado mRegistrado = new Registrado();
            mRegistrado.atribuir("0." + inteiro, "Real");

            mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mRegistrado);

        } else {
            mRunTime.errar(mLocal, "Processador - A Instrucao NUM_DEC so executa com Reais !");
        }

    }


    public void instrucao_PRINT(AST eAST) {

        Registrado eConteudo = getConteudo(eAST);

        if (eConteudo.getTipo().contentEquals("Texto")) {

            if (mRunTime.getVisibilidade()) {
                System.out.print(eConteudo.getConteudo());
            }

        } else {
            mRunTime.errar(mLocal, "Processador - A Instrucao NUM_STRING so executa com reais !");
        }

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
            novo.atribuir(eConteudo.getConteudo() + ".0", "Real");

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

        if (eOperador.contentEquals("MATCH")) {
            operador_Logico(eOperador, eConteudo_Esquerda, eConteudo_Direita);
        } else if (eOperador.contentEquals("UNMATCH")) {
            operador_Logico(eOperador, eConteudo_Esquerda, eConteudo_Direita);


        } else if (eOperador.contentEquals("ADD")) {
            operador_Inteiro(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("SUB")) {
            operador_Inteiro(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("DIV")) {
            operador_Inteiro(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("MUX")) {
            operador_Inteiro(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("MOD")) {
            operador_Inteiro(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("ALE")) {
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
        } else if (eOperador.contentEquals("DUM")) {
            operador_Real(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("OZU")) {
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
        } else if (eOperador.contentEquals("CONTENT")) {
            operador_Texto_Logico(eOperador, eConteudo_Direita, eConteudo_Esquerda);
        } else if (eOperador.contentEquals("UNCONTENT")) {
            operador_Texto_Logico(eOperador, eConteudo_Direita, eConteudo_Esquerda);

        } else if (eOperador.contentEquals("DATA")) {
            operador_Inteiro_Texto(eOperador, eConteudo_Direita, eConteudo_Esquerda);


        } else {

            mRunTime.errar(mLocal, "Processador - Operador desconhecido : " + eOperador);

        }


    }

    public void operador_Logico(String eOperador, Registrado eDireita, Registrado eEsquerda) {


        if (eDireita.getTipo().contentEquals("Logico")) {
            if (eEsquerda.getTipo().contentEquals("Logico")) {

                boolean A = false;
                boolean B = false;

                try {
                    A = Boolean.parseBoolean(eDireita.getConteudo());
                } catch (Exception e) {
                    mRunTime.errar(mLocal, "A instrucao " + eOperador + " nao pode ser realizada com valor do tipo : " + eDireita.getTipo());
                    return;
                }

                try {
                    B = Boolean.parseBoolean(eEsquerda.getConteudo());
                } catch (Exception e) {
                    mRunTime.errar(mLocal, "A instrucao " + eOperador + " nao pode ser realizada com valor do tipo : " + eEsquerda.getTipo());
                    return;
                }

                if (eOperador.contentEquals("MATCH")) {

                    boolean C = A == B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Logico");
                    mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);
                } else if (eOperador.contentEquals("UNMATCH")) {

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
                } else if (eOperador.contentEquals("MOD")) {

                    if (B == 0) {
                        mRunTime.errar(mLocal, "Processador - Operante MOD = 0 ");
                        return;
                    }

                    long C = A % B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Inteiro");
                    mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);

                } else if (eOperador.contentEquals("ALE")) {

                    long C = 0;

                    if (A == B) {

                        C = A;

                    } else {

                        if (A > B) {
                            long t = A;
                            A = B;
                            B = t;
                        }


                        long delta = B - A;

                        C = A + (long) (Math.random() * delta);


                    }


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

                } else if (eOperador.contentEquals("DUM")) {

                    if (B == 0) {
                        mRunTime.errar(mLocal, "Processador - Operante DUM = 0 ");
                        return;
                    }

                    float C = A % B;

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Real");
                    mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);
                } else if (eOperador.contentEquals("OZU")) {

                    float C = 0.0F;

                    if (A == B) {

                        C = A;

                    } else {

                        if (A > B) {
                            float t = A;
                            A = B;
                            B = t;
                        }


                        float delta = B - A;

                        C = A + (long) (Math.random() * delta);


                    }


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

    public void operador_Texto_Logico(String eOperador, Registrado eDireita, Registrado eEsquerda) {


        if (eDireita.getTipo().contentEquals("Texto")) {
            if (eEsquerda.getTipo().contentEquals("Texto")) {

                String A = "";
                String B = "";

                try {
                    A = eDireita.getConteudo();
                } catch (Exception e) {
                    mRunTime.errar(mLocal, "A instrucao " + eOperador + " nao pode ser realizada com valor do tipo : " + eDireita.getTipo());
                    return;
                }

                try {
                    B = eEsquerda.getConteudo();
                } catch (Exception e) {
                    mRunTime.errar(mLocal, "A instrucao " + eOperador + " nao pode ser realizada com valor do tipo : " + eEsquerda.getTipo());
                    return;
                }

                if (eOperador.contentEquals("CONTENT")) {

                    boolean C = A.contentEquals(B);

                    Registrado mReg = new Registrado();
                    mReg.atribuir(String.valueOf(C), "Logico");
                    mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);

                } else if (eOperador.contentEquals("UNCONTENT")) {

                    boolean C = A.contentEquals(B);

                    if (C) {
                        C = false;
                    } else {
                        C = true;
                    }
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


    public void operador_Inteiro_Texto(String eOperador, Registrado eDireita, Registrado eEsquerda) {


        if (eDireita.getTipo().contentEquals("Inteiro")) {
            if (eEsquerda.getTipo().contentEquals("Texto")) {

                String A = "";
                String B = "";

                try {
                    A = eDireita.getConteudo();
                } catch (Exception e) {
                    mRunTime.errar(mLocal, "A instrucao " + eOperador + " nao pode ser realizada com valor do tipo : " + eDireita.getTipo());
                    return;
                }

                try {
                    B = eEsquerda.getConteudo();
                } catch (Exception e) {
                    mRunTime.errar(mLocal, "A instrucao " + eOperador + " nao pode ser realizada com valor do tipo : " + eEsquerda.getTipo());
                    return;
                }

                if (eOperador.contentEquals("DATA")) {

                    System.out.println("Bora Escrever :: " + B + " em " + A);

                    mRunTime.getTabelaDeArquivos().escrever(Integer.parseInt(A), B);

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


    public void instrucao_OPEN(AST eAST) {

        Registrado eConteudo = getConteudo(eAST);

        if (eConteudo.getTipo().contentEquals("Texto")) {
            Registrado mRegistrado = new Registrado();

            String eArquivo = eConteudo.getConteudo();

            int eFD = -1;

            if (new File(eArquivo).exists()) {
                eFD = mRunTime.getTabelaDeArquivos().abrir(eArquivo);
                System.out.println("Abrindo arquivo :: " + eArquivo + " -->> " + eFD);
            }

            mRegistrado.atribuir(String.valueOf(eFD), "Inteiro");

            mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mRegistrado);

        } else {
            mRunTime.errar(mLocal, "Processador - A Instrucao OPEN so executa com textos !");
        }

    }

    public void instrucao_OPEN_OR_CREATE(AST eAST) {

        Registrado eConteudo = getConteudo(eAST);

        if (eConteudo.getTipo().contentEquals("Texto")) {
            Registrado mRegistrado = new Registrado();

            String eArquivo = eConteudo.getConteudo();

            int eFD = -1;

            if (new File(eArquivo).exists()) {
                eFD = mRunTime.getTabelaDeArquivos().abrir(eArquivo);
                mRegistrado.atribuir(String.valueOf(eFD), "Inteiro");
                System.out.println("Abrindo arquivo :: " + eArquivo + " -->> " + eFD);
            } else {

                try {
                    new File(eArquivo).createNewFile();
                    eFD = mRunTime.getTabelaDeArquivos().abrir(eArquivo);
                    mRegistrado.atribuir(String.valueOf(eFD), "Inteiro");
                    System.out.println("Abrindo arquivo :: " + eArquivo + " -->> " + eFD);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mRegistrado);

        } else {
            mRunTime.errar(mLocal, "Processador - A Instrucao OPEN_OR_CREATE so executa com texto !");
        }

    }

    public void instrucao_CLOSE(AST eAST) {

        Registrado eConteudo = getConteudo(eAST);

        if (eConteudo.getTipo().contentEquals("Inteiro")) {

            String eArquivo = eConteudo.getConteudo();

            mRunTime.getTabelaDeArquivos().fechar(Integer.parseInt(eArquivo));

            //  mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mRegistrado);

        } else {
            mRunTime.errar(mLocal, "Processador - A Instrucao CLOSE so executa com inteiro !");
        }

    }

    public void instrucao_PROC(AST eAST) {


        if (eAST.mesmoValor("ID")) {

            if (eAST.mesmoNome_SCS("DEBUG")) {

                mRunTime.getProcessador().debug();

            } else if (eAST.mesmoNome_SCS("ZERO")) {

                mRunTime.getProcessador().zerar();

            } else if (eAST.mesmoNome_SCS("CHANGE_LINE")) {

                if (mRunTime.getVisibilidade()) {
                    System.out.print("\n");
                }

            } else if (eAST.mesmoNome_SCS("CHRONOS_DAY")) {


                Calendar c = Calendar.getInstance();

                int dia = c.get(Calendar.DAY_OF_MONTH);


                Registrado mReg = new Registrado();
                mReg.atribuir(String.valueOf(dia), "Inteiro");
                mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);

            } else if (eAST.mesmoNome_SCS("CHRONOS_MONTH")) {


                Calendar c = Calendar.getInstance();

                int mes = c.get(Calendar.MONTH) + 1;

                Registrado mReg = new Registrado();
                mReg.atribuir(String.valueOf(mes), "Inteiro");
                mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);

            } else if (eAST.mesmoNome_SCS("CHRONOS_YEAR")) {


                Calendar c = Calendar.getInstance();

                int ano = c.get(Calendar.YEAR);


                Registrado mReg = new Registrado();
                mReg.atribuir(String.valueOf(ano), "Inteiro");
                mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);
            } else if (eAST.mesmoNome_SCS("CHRONOS_HOUR")) {


                Calendar c = Calendar.getInstance();

                int hora = c.get(Calendar.HOUR);


                Registrado mReg = new Registrado();
                mReg.atribuir(String.valueOf(hora), "Inteiro");
                mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);
            } else if (eAST.mesmoNome_SCS("CHRONOS_MINUTE")) {


                Calendar c = Calendar.getInstance();

                int minutos = c.get(Calendar.MINUTE);


                Registrado mReg = new Registrado();
                mReg.atribuir(String.valueOf(minutos), "Inteiro");
                mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);
            } else if (eAST.mesmoNome_SCS("CHRONOS_SECOND")) {


                Calendar c = Calendar.getInstance();

                int segundos = c.get(Calendar.SECOND);


                Registrado mReg = new Registrado();
                mReg.atribuir(String.valueOf(segundos), "Inteiro");
                mRunTime.getProcessador().aplicar(mRunTime.getProcessador().getApontando(), mReg);


            } else {
                mRunTime.errar(mLocal, "Comando PROC desconhecido : " + eAST.getNome());
            }

        } else {
            mRunTime.errar(mLocal, "Comando PROC Corrompido !");
        }


    }

}
