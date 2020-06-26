package AppSigmaz;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Compilador.Compiler;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.Erro;
import Sigmaz.Utils.GrupoDeErro;

import java.util.ArrayList;
import java.util.Calendar;

public class Testes {

    private ArrayList<String> mArquivos;

    public Testes() {

        mArquivos = new ArrayList<String>();


    }

    public void adicionar(String eArquivo) {
        mArquivos.add(eArquivo);
    }

    public void init() {


        System.out.println("################ CSIGMAZ - EM LOTE ################");


        String saida = "res/compilado.sigmad";


        String DDI = getData();

        System.out.println("");
        System.out.println(" - AUTOR	: LUAN FREITAS");
        System.out.println(" - VERSAO   : 1.0");
        System.out.println(" - STATUS  	: ALPHA");

        System.out.println("");
        System.out.println(" - INICIO  	: " + DDI);
        System.out.println("");

        //  ArrayList<String> mSaidas = new ArrayList<String>();
        int Contador = 1;


        for (String Arquivo : mArquivos) {


            boolean passou = false;
            String parou = "";

            Compiler CompilerC = new Compiler();
            CompilerC.init(Arquivo);

            ArrayList<String> mTemp = new ArrayList<String>();

            if (CompilerC.getErros_Lexer().size() == 0) {

                if (CompilerC.getErros_Compiler().size() == 0) {


                    Analisador AnaliseC = new Analisador();
                    AnaliseC.internalizar();

                    AnaliseC.init(CompilerC.getASTS(),"res/");

                    if (AnaliseC.getErros().size() == 0) {

                        CompilerC.Compilar(saida);
                        RunTime RunTimeC = new RunTime();

                        RunTimeC.internalizar();

                        RunTimeC.init(saida);


                        //  System.out.println("################ " + Arquivo + " ################");

                        RunTimeC.run();


                        if (RunTimeC.getErros().size() == 0) {
                            passou = true;
                        } else {

                            for (String Erro : RunTimeC.getErros()) {
                                mTemp.add("\t\t" + Erro);
                            }

                            parou = "Executor";

                        }

                    } else {
                        parou = "Analisador";

                        for (String Erro : AnaliseC.getErros()) {
                            mTemp.add("\t\t" + Erro);
                        }

                    }

                } else {
                    parou = "Compiler";

                    for (GrupoDeErro eGE : CompilerC.getErros_Compiler()) {
                        mTemp.add("\t\t" + eGE.getArquivo());
                        for (Erro eErro : eGE.getErros()) {
                            mTemp.add("\t\t    ->> " + eErro.getMensagem());
                        }
                    }

                }

            } else {
                parou = "Lexer";

                for (GrupoDeErro eGE : CompilerC.getErros_Lexer()) {
                    mTemp.add("\t\t" + eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        mTemp.add("\t\t    ->> " + eErro.getMensagem());
                    }
                }
            }

            String sContador = String.valueOf(Contador);

            if (sContador.length() == 1) {
                sContador = "0" + sContador;
            }

            if (passou) {
                // mSaidas.add(" Arquivo : " + Contador + " -> " + Arquivo + " : SUCESSO ");
                System.out.println(" Arquivo : " + sContador + " -> " + Arquivo + " : SUCESSO ");

            } else {


                System.out.println(" Arquivo : " + sContador + " -> " + Arquivo + " : FALHOU -> " + parou);

                //  mSaidas.add(" Arquivo : " + Contador + " -> " + Arquivo + " : FALHOU -> " + parou);
                for (String Tempando : mTemp) {
                    //mSaidas.add(Tempando);
                    System.out.println(Tempando);
                }
            }

            Contador += 1;
        }

        String DDF = getData();


        System.out.println("");


        System.out.println(" - FIM  	: " + DDF);
        System.out.println("");

        //for (String Saindo : mSaidas) {


        //    System.out.println(Saindo);

        //  }


    }

    public String getData() {

        Calendar c = Calendar.getInstance();

        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH) + 1;
        int ano = c.get(Calendar.YEAR);

        int hora = c.get(Calendar.HOUR);
        int minutos = c.get(Calendar.MINUTE);
        int segundos = c.get(Calendar.SECOND);

        return dia + "/" + mes + "/" + ano + " " + hora + ":" + minutos + ":" + segundos;

    }

}
