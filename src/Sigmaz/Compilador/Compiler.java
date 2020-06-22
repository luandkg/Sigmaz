package Sigmaz.Compilador;

import Sigmaz.Lexer.Lexer;
import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;


public class Compiler {

    private int mIndex;
    private int mTamanho;

    private ArrayList<Token> mTokens;
    private ArrayList<AST> mASTS;

    private ArrayList<GrupoDeErro> mErros_Lexer;
    private ArrayList<GrupoDeErro> mErros_Compiler;

    private ArrayList<String> mRequisitados;

    private String mLocal;
    private String mArquivo;

    private int mIChars;
    private int mITokens;

    public Compiler() {

        mArquivo = "";

        mIndex = 0;
        mTamanho = 0;

        mIChars = 0;
        mITokens = 0;

        mTokens = new ArrayList<>();
        mASTS = new ArrayList<>();
        mErros_Lexer = new ArrayList<>();
        mErros_Compiler = new ArrayList<>();
        mLocal = "";
        mRequisitados = new ArrayList<>();


    }

    public int getIChars() {
        return mIChars;
    }

    public int getITokens() {
        return mITokens;
    }

    public boolean Continuar() {
        return mIndex < mTamanho;
    }

    public void Proximo() {
        mIndex += 1;
    }

    public String getLocal() {
        return mLocal;
    }


    public Token getTokenCorrente() {
        return mTokens.get(mIndex);
    }

    public Token getTokenAvante() {
        mIndex += 1;

        if (mIndex < mTamanho) {
            return mTokens.get(mIndex);
        } else {
            return new Token(TokenTipo.DESCONHECIDO, "", mIndex, mIndex);
        }

    }

    public Token getTokenFuturo() {


        if (mIndex + 1 < mTamanho) {
            return mTokens.get(mIndex + 1);
        } else {
            return new Token(TokenTipo.DESCONHECIDO, "", mIndex + 1, mIndex + 1);
        }

    }

    public void pularLinha() {

        mIndex += 1;
        while (mIndex < mTamanho) {
            Token TokenC = mTokens.get(mIndex);
            if (TokenC.getTipo() == TokenTipo.PONTOVIRGULA) {
                mIndex += 1;
                break;
            }
            mIndex += 1;
        }


    }

    public void voltar() {
        mIndex -= 1;
    }


    public ArrayList<AST> getASTS() {
        return mASTS;
    }

    public AST getAST(String eAST) {
        AST mRet = null;

        for (AST mAST : mASTS) {
            if (mAST.mesmoTipo(eAST)) {
                mRet = mAST;
                break;
            }
        }

        return mRet;
    }

    public ArrayList<GrupoDeErro> getErros_Lexer() {
        return mErros_Lexer;
    }

    public ArrayList<GrupoDeErro> getErros_Compiler() {
        return mErros_Compiler;
    }

    public ArrayList<String> getRequisitados() {
        return mRequisitados;
    }

    public int Instrucoes() {
        int t = 0;

        for (AST a : getASTS()) {
            t += a.getInstrucoes();
        }

        return t;
    }


    public void init(String eArquivo) {
        mIndex = 0;

        mArquivo = eArquivo;
        mTokens.clear();
        mASTS.clear();
        mErros_Lexer.clear();
        mErros_Compiler.clear();
        mRequisitados.clear();
        mLocal = "";
        mIChars = 0;
        mITokens = 0;

        Lexer LexerC = new Lexer();

        LexerC.init(eArquivo);
        mIChars = LexerC.getChars();
        mITokens = LexerC.getTokens().size();


        for (Erro mErro : LexerC.getErros()) {
            errarLexer(mErro.getMensagem(), mErro.getPosicao());
        }


        for (Token TokenC : LexerC.getTokens()) {
            if (TokenC.getTipo() != TokenTipo.COMENTARIO) {
                mTokens.add(TokenC);
            }
        }


        mTamanho = mTokens.size();

        File arq = new File(eArquivo);
        mLocal = arq.getParent() + "/";


        compilando();


    }


    public void requisitando(String eArquivo, ArrayList<String> eRequisitados) {
        mIndex = 0;

        mArquivo = eArquivo;

        mTokens.clear();
        mASTS.clear();
        mErros_Lexer.clear();
        mErros_Compiler.clear();
        mRequisitados = eRequisitados;
        mLocal = "";
        mIChars = 0;
        mITokens = 0;

        Lexer LexerC = new Lexer();

        LexerC.init(eArquivo);
        mIChars = LexerC.getChars();
        mITokens = LexerC.getTokens().size();

        if (LexerC.getErros().size() > 0) {
            GrupoDeErro Ge = new GrupoDeErro(eArquivo);
            for (Erro mErro : LexerC.getErros()) {
                Ge.getErros().add(mErro);
            }
            mErros_Lexer.add(Ge);
        }


        for (Token TokenC : LexerC.getTokens()) {
            if (TokenC.getTipo() != TokenTipo.COMENTARIO) {
                mTokens.add(TokenC);
            }

            //  System.out.println(" TOKEN : " + TokenC.getConteudo());
        }


        mTamanho = mTokens.size();

        File arq = new File(eArquivo);
        mLocal = arq.getParent() + "/";

        if (LexerC.getErros().size() == 0) {
            compilando();
        }

    }


    private void compilando() {

        AST AST_Raiz = new AST("SIGMAZ");
        mASTS.add(AST_Raiz);


        while (Continuar()) {

            Token TokenC = this.getTokenCorrente();
            if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("require")) {

                AST_Requisicao mAST = new AST_Requisicao(this);
                mAST.init(AST_Raiz);

                if (mErros_Lexer.size() > 0) {
                    break;
                }
                if (mErros_Compiler.size() > 0) {
                    break;
                }
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("act")) {

                AST_Action mAST = new AST_Action(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("func")) {

                AST_Function mAST = new AST_Function(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("call")) {

                AST_Call mAST = new AST_Call(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                AST_Mockiz mAST = new AST_Mockiz(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                AST_Define mAST = new AST_Define(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("invoke")) {

                AST_Invoke mAST = new AST_Invoke(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("operation")) {

                AST_Operation mAST = new AST_Operation(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("cast")) {

                AST_Cast mAST = new AST_Cast(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("struct")) {

                AST_Struct mAST = new AST_Struct(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("stages")) {

                AST_Stages mAST = new AST_Stages(this);
                mAST.init(AST_Raiz);

            } else {
                errarCompilacao("Token Desconhecido : " + TokenC.getTipo() + " " + TokenC.getConteudo(), TokenC.getInicio());
                return;
            }

            Proximo();
        }


    }

    public Token getTokenAvanteStatus(TokenTipo eTokenTipo, String eErro) {
        Token TokenP = getTokenAvante();
        if (TokenP.getTipo() == eTokenTipo) {

        } else {
            errarCompilacao(eErro, TokenP.getInicio());
            errarCompilacao("Contudo encontrou : " + TokenP.getConteudo(), TokenP.getInicio());
        }
        return TokenP;
    }

    public void errarLexer(String eMensagem, int ePosicao) {

        boolean enc = false;

        for (GrupoDeErro G : mErros_Lexer) {
            if (G.mesmmoArquivo(mArquivo)) {
                G.adicionarErro(eMensagem, ePosicao);
                enc = true;
                break;
            }
        }

        if (!enc) {
            GrupoDeErro nG = new GrupoDeErro(mArquivo);
            nG.adicionarErro(eMensagem, ePosicao);
            mErros_Lexer.add(nG);
        }
    }

    public void errarCompilacao(String eMensagem, int ePosicao) {

        boolean enc = false;

        for (GrupoDeErro G : mErros_Compiler) {
            if (G.mesmmoArquivo(mArquivo)) {
                G.adicionarErro(eMensagem, ePosicao);
                enc = true;
                break;
            }
        }

        if (!enc) {
            GrupoDeErro nG = new GrupoDeErro(mArquivo);
            nG.adicionarErro(eMensagem, ePosicao);
            mErros_Compiler.add(nG);
        }
    }


    public void Compilar(String eArquivo) {

        Documentador documentadorC = new Documentador();

        documentadorC.compilar(this.getASTS(), eArquivo);


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
