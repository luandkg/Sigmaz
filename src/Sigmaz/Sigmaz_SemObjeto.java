package Sigmaz;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Compilador.Compiler;
import Sigmaz.Intellisenses.Intellisense;
import Sigmaz.Intellisenses.IntellisenseTheme;
import Sigmaz.PosProcessamento.Cabecalho;
import Sigmaz.PosProcessamento.PosProcessador;
import Sigmaz.Utils.AST;
import Sigmaz.Utils.Erro;
import Sigmaz.Utils.GrupoDeErro;
import Sigmaz.Utils.Tempo;

import java.io.File;
import java.util.ArrayList;

public class Sigmaz_SemObjeto {

    private ArrayList<String> mMensagens;
    private boolean mTemErros;
    private String mLocalErro;

    private Cabecalho mCabecalho;
    private ArrayList<AST> mASTS;

    public Sigmaz_SemObjeto() {

        mMensagens = new ArrayList<String>();
        mTemErros = false;
        mLocalErro = "";

        mCabecalho = new Cabecalho();
        mASTS = new ArrayList<AST>();

    }

    public void init(String eArquivo, String saida, boolean mostrarDebug) {

        ArrayList<String> lista = new ArrayList<String>();
        lista.add(eArquivo);

        initvarios(lista, saida, mostrarDebug);

    }

    public void initvarios(ArrayList<String> eArquivos, String saida, boolean mostrarDebug) {

        mTemErros = false;
        mMensagens.clear();


        File arq = new File(saida);
        String mLocal = arq.getParent() + "/";

        mLocalErro = "PROCESSAMENTO";


        Compiler CompilerC = new Compiler();
        CompilerC.initvarios(eArquivos, 1);

        mLocalErro = "PROCESSAMENTO";

        mLocalErro = "LEXER";

        if (CompilerC.getErros_Lexer().size() == 0) {

            mLocalErro = "COMPILADOR";


            if (CompilerC.getErros_Compiler().size() == 0) {

                mLocalErro = "ANALISADOR";

                Analisador AnaliseC = new Analisador();
                AnaliseC.init(CompilerC.getASTS(), mLocal);

                if (AnaliseC.getErros().size() == 0) {

                    mLocalErro = "POS-PROCESSADOR";

                    PosProcessador PosProcessadorC = new PosProcessador();

                    PosProcessadorC.init(mCabecalho, CompilerC.getASTS(), mLocal);

                    if (PosProcessadorC.getErros().size() == 0) {


                        //CompilerC.Compilar(saida);


                        //  System.out.println("");
                        // System.out.println("################ INTELISENSE ################");
                        //  System.out.println("");
                        //  System.out.println("\t - Executando : " + saida);
                        //   System.out.println("");


                        String DI = Tempo.getData();


                        mASTS = CompilerC.getASTS();

                        //  System.out.println("");
                        //  System.out.println("----------------------------------------------");
                        //  System.out.println("");

                        //   String DF = Tempo.getData();

                        //  System.out.println("\t - Iniciado : " + DI);
                        //  System.out.println("\t - Finalizado : " + DF);


                        // System.out.println("");
                        // System.out.println("----------------------------------------------");


                        mTemErros = false;

                    } else {


                        mTemErros = true;
                        mMensagens.add("\n\t ERROS DE POS-PROCESSAMENTO : ");


                        for (String Erro : PosProcessadorC.getErros()) {
                            mMensagens.add("\t\t" + Erro);
                        }

                    }

                } else {

                    mTemErros = true;

                    mMensagens.add("\n\t ERROS DE ANALISE : ");

                    for (String Erro : AnaliseC.getErros()) {
                        mMensagens.add("\t\t" + Erro);
                    }

                }

            } else {


                mTemErros = true;

                mMensagens.add("\n\t ERROS DE COMPILACAO : ");

                for (GrupoDeErro eGE : CompilerC.getErros_Compiler()) {
                    mMensagens.add("\t\t" + eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        mMensagens.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                    }
                }
            }


        } else {

            mTemErros = true;

            mMensagens.add("\n\t ERROS DE LEXER : ");

            for (GrupoDeErro eGE : CompilerC.getErros_Lexer()) {
                mMensagens.add("\t\t" + eGE.getArquivo());
                for (Erro eErro : eGE.getErros()) {
                    mMensagens.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                }
            }
        }


    }

    public boolean temErros() {
        return mTemErros;
    }

    public ArrayList<AST> getASTS(){ return mASTS;}
}
