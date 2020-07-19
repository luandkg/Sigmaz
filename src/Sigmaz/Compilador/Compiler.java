package Sigmaz.Compilador;

import Sigmaz.Lexer.Lexer;
import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;


public class Compiler {


    private ArrayList<AST> mASTS;

    private ArrayList<String> mFila;

    private ArrayList<String> mRequisitados;

    private ArrayList<GrupoDeErro> mErros_Lexer;
    private ArrayList<GrupoDeErro> mErros_Compiler;

    private ArrayList<GrupoDeComentario> mComentarios;

    private int mIChars;
    private int mITokens;

    private String mProcessamento;

    public Compiler() {

        mASTS = new ArrayList<>();

        mFila = new ArrayList<String>();

        mRequisitados = new ArrayList<>();

        mErros_Lexer = new ArrayList<>();
        mErros_Compiler = new ArrayList<>();

        mComentarios = new ArrayList<>();


        mIChars = 0;
        mITokens = 0;
        mProcessamento="";

    }


    public ArrayList<AST> getASTS() {
        return mASTS;
    }


    public int getIChars() {
        return mIChars;
    }

    public int getITokens() {
        return mITokens;
    }

    public int getInstrucoes() {
        int t = 0;

        for (AST a : getASTS()) {
            t += a.getInstrucoes();
        }

        return t;
    }

    public ArrayList<String> getRequisitados() {
        return mRequisitados;
    }

    public String getProcessamento(){return mProcessamento;}

    public void init(String eArquivo) {


        AST AST_Raiz = new AST("SIGMAZ");
        mASTS.add(AST_Raiz);

        mFila.add(eArquivo);

        mProcessamento="";

        if (mFila.size() > 0) {


            int o = mFila.size();


            while (o > 0) {

                ArrayList<String> mRemover = new ArrayList<String>();
                ArrayList<String> mAdicionar = new ArrayList<String>();

                String mLocalRequisicao = mFila.get(0);
                mRemover.add(mLocalRequisicao);

                mProcessamento +="PROCESSANDO : " + mLocalRequisicao + " :: " + o + "\n";

                if (!getRequisitados().contains(mLocalRequisicao)) {

                    getRequisitados().add(mLocalRequisicao);

                    File arq = new File(mLocalRequisicao);


                    if (arq.exists()) {


                        CompilerUnit mCompilerUnit = new CompilerUnit();
                        mCompilerUnit.init(mLocalRequisicao, AST_Raiz, mRequisitados);

                        mITokens += mCompilerUnit.getITokens();
                        mIChars += mCompilerUnit.getIChars();


                        for (String a : mCompilerUnit.getFila()) {
                            mAdicionar.add(a);
                        }


                        getErros_Lexer().addAll(mCompilerUnit.getErros_Lexer());
                        getErros_Compiler().addAll(mCompilerUnit.getErros_Compiler());

                        getComentarios().addAll(mCompilerUnit.getComentarios());


                    } else {

                        GrupoDeErro nG = new GrupoDeErro("SIGMAZ");
                        nG.adicionarErro("Importacao nao encontrada : " + mLocalRequisicao, 0, 0);
                        mErros_Compiler.add(nG);

                    }

                }


                ArrayList<String> realAdicionar = new ArrayList<String>();
                ArrayList<String> jaAdicionados = new ArrayList<String>();

                for (String e : mAdicionar) {
                    if (!mFila.contains(e)) {
                        mFila.add(e);
                        realAdicionar.add(e);
                    } else {
                        if (!jaAdicionados.contains(e)) {
                            jaAdicionados.add(e);
                        }
                    }

                }

                if (realAdicionar.size() > 0) {
                    mProcessamento +="\tADICIONAR"+ "\n";
                    for (String e : realAdicionar) {
                        mProcessamento +="\t\t - " + e+ "\n";
                    }
                }

                if (jaAdicionados.size() > 0) {
                    mProcessamento +="\tNAO PROCESSAR"+ "\n";
                    for (String e : jaAdicionados) {
                        mProcessamento +="\t\t - " + e+ "\n";
                    }
                }


                if (mRemover.size() > 0) {
                    mProcessamento +="\tREMOVER"+ "\n";

                    for (String e : mRemover) {
                        while (mFila.contains(e)) {
                            if (mFila.contains(e)) {
                                mFila.remove(e);
                            }
                        }
                        mProcessamento +="\t\t - " + e+ "\n";
                    }
                }

                int a = o;

                o = mFila.size();
                //i+=1;
                mProcessamento +="\tREENFILEIRAR :: " + a + " -->> " + o+ "\n";


            }

        }

    }


    public void Compilar(String eArquivo) {

        Documentador documentadorC = new Documentador();

        documentadorC.compilar(this.getASTS(), eArquivo);


    }


    public ArrayList<GrupoDeErro> getErros_Lexer() {
        return mErros_Lexer;
    }

    public ArrayList<GrupoDeErro> getErros_Compiler() {
        return mErros_Compiler;
    }

    public ArrayList<GrupoDeComentario> getComentarios() {
        return mComentarios;
    }


    public String getArvoreDeInstrucoes() {

        Documento DocumentoC = new Documento();

        DocumentoC.adicionarLinha("");

        for (AST a : getASTS()) {

            if (a.getValor().length() > 0) {
                DocumentoC.adicionarLinha(" " + a.getTipo() + " -> " + a.getNome() + " : " + a.getValor());
            } else {
                DocumentoC.adicionarLinha(" " + a.getTipo() + " -> " + a.getNome());
            }

            SubArvoreDeInstrucoes("   ", a, DocumentoC);

        }

        DocumentoC.adicionarLinha("");

        return DocumentoC.getConteudo();
    }

    private void SubArvoreDeInstrucoes(String ePref, AST ASTC, Documento DocumentoC) {

        for (AST a : ASTC.getASTS()) {

            if (a.getValor().length() > 0) {
                DocumentoC.adicionarLinha(ePref + a.getTipo() + " -> " + a.getNome() + " : " + a.getValor());

            } else {
                DocumentoC.adicionarLinha(ePref + a.getTipo() + " -> " + a.getNome());

            }

            SubArvoreDeInstrucoes(ePref + "   ", a, DocumentoC);

        }

    }


    public String getData() {

        return Tempo.getData();


    }


}
