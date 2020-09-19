package Sigmaz.Compilador;

import java.io.File;
import java.util.ArrayList;

import Sigmaz.Utils.AST;
import Sigmaz.Utils.GrupoDeErro;
import Sigmaz.Utils.GrupoDeComentario;
import Sigmaz.Utils.Enfileirador;
import Sigmaz.Utils.Documentador;
import Sigmaz.Utils.Documento;
import Sigmaz.Utils.Tempo;

public class Compiler {


    private ArrayList<AST> mASTS;


    private ArrayList<String> mRequisitados;

    private ArrayList<GrupoDeErro> mErros_Lexer;
    private ArrayList<GrupoDeErro> mErros_Compiler;

    private ArrayList<GrupoDeComentario> mComentarios;

    private int mIChars;
    private int mITokens;

    private String mProcessamento;

    public Compiler() {

        mASTS = new ArrayList<>();


        mRequisitados = new ArrayList<>();

        mErros_Lexer = new ArrayList<>();
        mErros_Compiler = new ArrayList<>();

        mComentarios = new ArrayList<>();


        mIChars = 0;
        mITokens = 0;
        mProcessamento = "";

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

    public String getProcessamento() {
        return mProcessamento;
    }

    public void init(String eArquivo, int mOpcao) {


        AST AST_Raiz = new AST("SIGMAZ");
        mASTS.add(AST_Raiz);


        if (mOpcao == 1) {

            AST_Raiz.criarBranch("SIGMAZ_TYPE").setNome("EXECUTABLE");

        } else if (mOpcao == 2) {

            AST_Raiz.criarBranch("SIGMAZ_TYPE").setNome("LIBRARY");

        }


        Enfileirador mFila = new Enfileirador();

        mFila.adicionar(eArquivo);

        mFila.iniciar();

        while (mFila.estaExecutando()) {


            String mLocalRequisicao = mFila.processar();

            if (mLocalRequisicao != null) {


                getRequisitados().add(mLocalRequisicao);

                File arq = new File(mLocalRequisicao);


                if (arq.exists()) {

                    CompilerUnit mCompilerUnit = new CompilerUnit();
                    mCompilerUnit.init(mLocalRequisicao, AST_Raiz, mRequisitados);

                    mITokens += mCompilerUnit.getITokens();
                    mIChars += mCompilerUnit.getIChars();


                    for (String a : mCompilerUnit.getFila()) {
                        mFila.momentoAdicionar(a);
                    }


                    getErros_Lexer().addAll(mCompilerUnit.getErros_Lexer());
                    getErros_Compiler().addAll(mCompilerUnit.getErros_Compiler());
                    getComentarios().addAll(mCompilerUnit.getComentarios());


                } else {

                    GrupoDeErro nG = new GrupoDeErro("SIGMAZ");
                    nG.adicionarErro("Importacao nao encontrada : " + mLocalRequisicao, 0, 0);
                    mErros_Compiler.add(nG);
                    break;
                }

            }


            mFila.organizar();


        }

        mProcessamento = mFila.getProcessamento();

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

    public String imprimirArvore() {
        String e = "";

        for (AST eAST : mASTS) {
            e += eAST.ImprimirArvoreDeInstrucoes() + "\n";
        }

        return e;
    }


}
