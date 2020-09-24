package Sigmaz;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Compilador.Compiler;
import Sigmaz.PosProcessamento.PosProcessador;
import Sigmaz.Utils.Documentador;
import Sigmaz.Utils.Erro;
import Sigmaz.Utils.GrupoDeErro;

import java.io.File;
import java.util.ArrayList;

public class Sigmaz_CS {


    private Sigmaz mSigmaz;

    public Sigmaz_CS(Sigmaz eSigmaz) {

        mSigmaz=eSigmaz;

    }

    public boolean geral(String eArquivo, String saida, int mOpcao) {

        boolean ret = false;

        File arq = new File(saida);
        String mLocal = arq.getParent() + "/";


        System.out.println("################# SIGMAZ #################");
        System.out.println("");
        System.out.println(" - AUTOR	: LUAN FREITAS");
        System.out.println(" - VERSAO   : 1.0");
        System.out.println(" - STATUS  	: ALPHA");
        System.out.println(" - INICIO  	: 2020.06.12");

        System.out.println("");


        Compiler CompilerC = new Compiler();
        CompilerC.init(eArquivo, mOpcao);

        System.out.println("############## PRE PROCESSAMENTO ###############");
        System.out.println("");

        if (mSigmaz.getStackProcess()) {
            System.out.println(CompilerC.getPreProcessamento());
        }

        System.out.println("################# LEXER ##################");
        System.out.println("");
        System.out.println("\t Iniciado : " + CompilerC.getData().toString());
        System.out.println("\t - Arquivo : " + eArquivo);
        System.out.println("\t - Chars : " + CompilerC.getIChars());
        System.out.println("\t - Tokens : " + CompilerC.getITokens());
        System.out.println("\t - Erros : " + CompilerC.getErros_Lexer().size());
        System.out.println("\t Finalizado : " + CompilerC.getData().toString());
        System.out.println("");


        System.out.println("############### COMPILADOR ###############");
        System.out.println("");
        System.out.println("\t Iniciado : " + CompilerC.getData().toString());
        System.out.println("\t - Instrucoes : " + CompilerC.getInstrucoes());
        System.out.println("\t - Erros : " + CompilerC.getErros_Compiler().size());
        System.out.println("\t - Requisitados : ");

        for (String Req : CompilerC.getRequisitados()) {
            System.out.println("\t\t - " + Req);
        }

        System.out.println("\t Finalizado : " + CompilerC.getData().toString());


        if (CompilerC.getErros_Lexer().size() > 0) {

            System.out.println("\n\t ERROS DE LEXICOGRAFICA : ");

            for (GrupoDeErro eGE : CompilerC.getErros_Lexer()) {
                System.out.println("\t\t" + eGE.getArquivo());
                for (Erro eErro : eGE.getErros()) {
                    System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                }
            }

        }

        if (CompilerC.getErros_Compiler().size() > 0) {

            System.out.println("");
            System.out.println("################ AST - COM DEFEITOS ################");
            System.out.println("");

            if (mSigmaz.getObject()) {
                Documentador DC = new Documentador();
                System.out.println(CompilerC.getArvoreDeInstrucoes());
            }


            System.out.println("\n\t ERROS DE COMPILACAO : ");

            for (GrupoDeErro eGE : CompilerC.getErros_Compiler()) {
                System.out.println("\t\t" + eGE.getArquivo());
                for (Erro eErro : eGE.getErros()) {
                    System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                }
            }
        }


        if (CompilerC.getErros_Lexer().size() == 0 && CompilerC.getErros_Compiler().size() == 0) {


            System.out.println("");
            System.out.println("################ ANALISE ################");
            System.out.println("");


            Analisador AnaliseC = new Analisador();
            String AI = CompilerC.getData().toString();

            if (mSigmaz.getObject()) {
                System.out.println(CompilerC.getArvoreDeInstrucoes());

            }


            AnaliseC.init(CompilerC.getASTS(), mLocal);
            String AF = CompilerC.getData().toString();


            System.out.println("\t - Iniciado : " + AI);
            System.out.println("\t - Finalizado : " + AF);


            System.out.println("\t - Erros : " + AnaliseC.getErros().size());

            if (mSigmaz.getAnalysisProcess()) {
                AnaliseC.MostrarMensagens();
            }


            if (AnaliseC.getErros().size() > 0) {
                System.out.println("\n\t ERROS DE ANALISE : ");

                for (String Erro : AnaliseC.getErros()) {
                    System.out.println("\t\t" + Erro);
                }

            } else {


                System.out.println("");
                System.out.println("################ POS-PROCESSAMENTO ################");
                System.out.println("");

                String AI_PosProcessamento = CompilerC.getData().toString();

                PosProcessador PosProcessadorC = new PosProcessador();

                PosProcessadorC.init(mSigmaz.getCabecalho(), CompilerC.getASTS(), mLocal);

                String AF_PosProcessamento = CompilerC.getData().toString();

                System.out.println("\t - Iniciado : " + AI_PosProcessamento);
                System.out.println("\t - Finalizado : " + AF_PosProcessamento);

                if (mSigmaz.getPosProcess()) {
                    PosProcessadorC.MostrarMensagens();
                }


                if (PosProcessadorC.getErros().size() > 0) {
                    System.out.println("\n\t ERROS DE POS-PROCESSAMENTO : ");


                    for (String Erro : PosProcessadorC.getErros()) {
                        System.out.println("\t\t" + Erro);
                    }
                } else {


                    System.out.println("");
                    System.out.println("################ OBJETO ################");
                    System.out.println("");


                    CompilerC.Compilar(saida);

                    Documentador DC2 = new Documentador();

                    System.out.println("\t Iniciado : " + CompilerC.getData().toString());
                    System.out.println("\t - Tamanho : " + DC2.tamanhoObjeto(saida));
                    System.out.println("\t - Saida : " + saida);
                    System.out.println("\t Finalizado : " + CompilerC.getData().toString());

                    System.out.println("");

                    if (mSigmaz.getObject()) {

                        System.out.println(CompilerC.imprimirArvore());


                    }


                    ret = true;

                }


            }


        } else {


        }

        return ret;
    }

    public boolean geralvarios(ArrayList<String> eArquivos, String saida, int mOpcao) {

        boolean ret = false;

        File arq = new File(saida);
        String mLocal = arq.getParent() + "/";


        System.out.println("################# SIGMAZ #################");
        System.out.println("");
        System.out.println(" - AUTOR	: LUAN FREITAS");
        System.out.println(" - VERSAO   : 1.0");
        System.out.println(" - STATUS  	: ALPHA");
        System.out.println(" - INICIO  	: 2020.06.12");

        System.out.println("");


        Compiler CompilerC = new Compiler();
        CompilerC.initvarios(eArquivos, mOpcao);

        System.out.println("############## PRE PROCESSAMENTO ###############");
        System.out.println("");
        if (mSigmaz.getStackProcess()) {
            System.out.println(CompilerC.getPreProcessamento());
        }
        System.out.println("################# LEXER ##################");
        System.out.println("");
        System.out.println("\t Iniciado : " + CompilerC.getData().toString());

        for (String eArquivo : eArquivos) {
            System.out.println("\t - Arquivo : " + eArquivo);
        }
        System.out.println("\t - Chars : " + CompilerC.getIChars());
        System.out.println("\t - Tokens : " + CompilerC.getITokens());
        System.out.println("\t - Erros : " + CompilerC.getErros_Lexer().size());
        System.out.println("\t Finalizado : " + CompilerC.getData().toString());
        System.out.println("");


        System.out.println("############### COMPILADOR ###############");
        System.out.println("");
        System.out.println("\t Iniciado : " + CompilerC.getData().toString());
        System.out.println("\t - Instrucoes : " + CompilerC.getInstrucoes());
        System.out.println("\t - Erros : " + CompilerC.getErros_Compiler().size());
        System.out.println("\t - Requisitados : ");

        for (String Req : CompilerC.getRequisitados()) {
            System.out.println("\t\t - " + Req);
        }

        System.out.println("\t Finalizado : " + CompilerC.getData().toString());


        if (CompilerC.getErros_Lexer().size() > 0) {

            System.out.println("\n\t ERROS DE LEXICOGRAFICA : ");

            for (GrupoDeErro eGE : CompilerC.getErros_Lexer()) {
                System.out.println("\t\t" + eGE.getArquivo());
                for (Erro eErro : eGE.getErros()) {
                    System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                }
            }

        }

        if (CompilerC.getErros_Compiler().size() > 0) {

            System.out.println("");
            System.out.println("################ AST - COM DEFEITOS ################");
            System.out.println("");

            if (mSigmaz.getObject()) {
                Documentador DC = new Documentador();
                System.out.println(CompilerC.getArvoreDeInstrucoes());
            }

            System.out.println("\n\t ERROS DE COMPILACAO : ");

            for (GrupoDeErro eGE : CompilerC.getErros_Compiler()) {
                System.out.println("\t\t" + eGE.getArquivo());
                for (Erro eErro : eGE.getErros()) {
                    System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                }
            }
        }


        if (CompilerC.getErros_Lexer().size() == 0 && CompilerC.getErros_Compiler().size() == 0) {


            System.out.println("");
            System.out.println("################ ANALISE ################");
            System.out.println("");


            Analisador AnaliseC = new Analisador();
            String AI = CompilerC.getData().toString();

            if (mSigmaz.getObject()) {

                System.out.println(CompilerC.getArvoreDeInstrucoes());

            }


            AnaliseC.init(CompilerC.getASTS(), mLocal);
            String AF = CompilerC.getData().toString();


            System.out.println("\t - Iniciado : " + AI);
            System.out.println("\t - Finalizado : " + AF);


            System.out.println("\t - Erros : " + AnaliseC.getErros().size());

            if (mSigmaz.getAnalysisProcess()) {
                AnaliseC.MostrarMensagens();
            }

            if (AnaliseC.getErros().size() > 0) {
                System.out.println("\n\t ERROS DE ANALISE : ");

                for (String Erro : AnaliseC.getErros()) {
                    System.out.println("\t\t" + Erro);
                }

            } else {


                System.out.println("");
                System.out.println("################ POS-PROCESSAMENTO ################");
                System.out.println("");

                String AI_PosProcessamento = CompilerC.getData().toString();

                PosProcessador PosProcessadorC = new PosProcessador();

                PosProcessadorC.init(mSigmaz.getCabecalho(), CompilerC.getASTS(), mLocal);

                String AF_PosProcessamento = CompilerC.getData().toString();

                System.out.println("\t - Iniciado : " + AI_PosProcessamento);
                System.out.println("\t - Finalizado : " + AF_PosProcessamento);


                if (mSigmaz.getPosProcess()) {
                    PosProcessadorC.MostrarMensagens();
                }

                if (PosProcessadorC.getErros().size() > 0) {
                    System.out.println("\n\t ERROS DE POS-PROCESSAMENTO : ");


                    for (String Erro : PosProcessadorC.getErros()) {
                        System.out.println("\t\t" + Erro);
                    }
                } else {


                    System.out.println("");
                    System.out.println("################ OBJETO ################");
                    System.out.println("");


                    CompilerC.Compilar(saida);

                    Documentador DC2 = new Documentador();

                    System.out.println("\t Iniciado : " + CompilerC.getData().toString());
                    System.out.println("\t - Tamanho : " + DC2.tamanhoObjeto(saida));
                    System.out.println("\t - Saida : " + saida);
                    System.out.println("\t Finalizado : " + CompilerC.getData().toString());

                    System.out.println("");

                    if (mSigmaz.getObject()) {
                        System.out.println(CompilerC.imprimirArvore());
                    }


                    ret = true;

                }


            }


        } else {


        }

        return ret;
    }


}
