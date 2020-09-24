package Sigmaz;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Compilador.Compiler;
import Sigmaz.PosProcessamento.PosProcessador;
import Sigmaz.Utils.Erro;
import Sigmaz.Utils.GrupoDeErro;

import java.io.File;
import java.util.ArrayList;

public class Sigmaz_CR {

    private Sigmaz mSigmaz;

    private ArrayList<String> mMensagens;
    private boolean mTemErros;
    private String mLocalErro;


    public Sigmaz_CR(Sigmaz eSigmaz) {

        mSigmaz = eSigmaz;

        mMensagens = new ArrayList<String>();
        mTemErros = false;
        mLocalErro = "";

    }


    public void geral_simples(String eArquivo, String saida, int mOpcao) {

        mTemErros = false;
        mMensagens.clear();


        File arq = new File(saida);
        String mLocal = arq.getParent() + "/";

        mLocalErro = "PROCESSAMENTO";


        Compiler CompilerC = new Compiler();
        CompilerC.init(eArquivo, mOpcao);

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

                    PosProcessadorC.init(mSigmaz.getCabecalho(), CompilerC.getASTS(), mLocal);

                    if (PosProcessadorC.getErros().size() == 0) {

                        CompilerC.Compilar(saida);

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

    public void geralvarios_simples(ArrayList<String> eArquivos, String saida, int mOpcao) {

        mTemErros = false;
        mMensagens.clear();


        File arq = new File(saida);
        String mLocal = arq.getParent() + "/";

        mLocalErro = "PROCESSAMENTO";


        Compiler CompilerC = new Compiler();
        CompilerC.initvarios(eArquivos, mOpcao);

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

                    PosProcessadorC.init(mSigmaz.getCabecalho(), CompilerC.getASTS(), mLocal);

                    if (PosProcessadorC.getErros().size() == 0) {

                        CompilerC.Compilar(saida);

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

    public String getLocalErro() {
        return mLocalErro;
    }

    public ArrayList<String> getMensagens() {
        return mMensagens;
    }
}
