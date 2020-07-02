package AppSigmaz;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Compilador.Compiler;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.Erro;
import Sigmaz.Utils.GrupoDeErro;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SigmazTestes {

    private ArrayList<String> mArquivos;
    private String mSaida;

    public SigmazTestes() {

        mArquivos = new ArrayList<String>();
        mSaida="Compiled.sigmad";

    }

    public void setSaida(String eSaida){
        mSaida=eSaida;
    }

    public void adicionar(String eArquivo) {
        mArquivos.add(eArquivo);
    }

    public void init() {


        System.out.println("################ SIGMAZ - TESTES UNITARIOS ################");


        String DDI = getData();
        long start = System.currentTimeMillis();

        System.out.println("");
        System.out.println(" - AUTOR	: LUAN FREITAS");
        System.out.println(" - VERSAO   : 1.0");
        System.out.println(" - STATUS  	: ALPHA");

        System.out.println("");
        System.out.println(" - INICIO  	: " + DDI);
        System.out.println("");

        //  ArrayList<String> mSaidas = new ArrayList<String>();
        int Contador = 1;

        int mQuantidade = mArquivos.size();
        int mSucesso = 0;
        int mProblema = 0;

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

                        CompilerC.Compilar(mSaida);
                        RunTime RunTimeC = new RunTime();

                        RunTimeC.internalizar();

                        RunTimeC.init(mSaida);


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
                            mTemp.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                        }
                    }

                }

            } else {
                parou = "Lexer";

                for (GrupoDeErro eGE : CompilerC.getErros_Lexer()) {
                    mTemp.add("\t\t" + eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        mTemp.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                    }
                }
            }

            String sContador = String.valueOf(Contador);

            if (sContador.length() == 1) {
                sContador = "0" + sContador;
            }

            if (passou) {
                System.out.println(" Arquivo : " + sContador + " -> " + Arquivo + " : SUCESSO ");
                mSucesso+=1;
            } else {

                System.out.println(" Arquivo : " + sContador + " -> " + Arquivo + " : FALHOU -> " + parou);
                for (String Tempando : mTemp) {
                    System.out.println(Tempando);
                }
                mProblema+=1;

            }

            Contador += 1;
        }

        String DDF = getData();
        long end = System.currentTimeMillis();

        System.out.println("");


        System.out.println(" - FIM  	: " + DDF);
        System.out.println("");

        float sec = (end - start) / 1000F;

        System.out.println(" - TEMPO  	: " + sec + " segundos");
        System.out.println("");



        NumberFormat formatarFloat= new DecimalFormat("0.00");

        if (mQuantidade>0){

            float s = ((float)mSucesso/(float)mQuantidade)*100.0f;
            float f =( (float)mProblema/(float)mQuantidade)*100.0f;

            System.out.println(" - TESTES  	: " + mQuantidade + " -> 100.00 % ");
            System.out.println("\t - SUCESSO  : " + mSucesso + " -> " +  formatarFloat.format(s).replace(",", ".") + " % ");
            System.out.println("\t - FALHOU  	: " + mProblema+ " -> " + formatarFloat.format(f).replace(",", ".")+ " % ");

        }



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
