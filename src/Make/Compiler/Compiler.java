package Make.Compiler;

import Sigmaz.S00_Utilitarios.*;
import Sigmaz.S02_Lexer.Lexer;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;

import java.io.File;
import java.util.ArrayList;

public class Compiler {

    private int mIndex;
    private int mTamanho;

    private ArrayList<Token> mTokens;
    private AST mAST;

    private ArrayList<GrupoDeErro> mErros_Lexer;
    private ArrayList<GrupoDeErro> mErros_Compiler;

    private ArrayList<GrupoDeComentario> mComentarios;


    private String mLocal;

    private int mIChars;
    private int mITokens;

    private String mArquivo;

    private int mPosicao = 0;
    private int mFim = 0;
    private int mLinha = 0;

    private ArrayList<String> mFila;

    public Compiler() {

        mArquivo = "";

        mIndex = 0;
        mTamanho = 0;

        mIChars = 0;
        mITokens = 0;

        mFila = new ArrayList<String>();

        mTokens = new ArrayList<>();
        mAST = null;
        mErros_Lexer = new ArrayList<>();
        mErros_Compiler = new ArrayList<>();
        mLocal = "";

        mComentarios = new ArrayList<>();
    }


    public void init(String eArquivo) {

        mIndex = 0;

        mArquivo = eArquivo;
        mTokens.clear();
        mAST = null;
        mErros_Lexer.clear();
        mErros_Compiler.clear();
        mLocal = "";
        mIChars = 0;
        mITokens = 0;
        mComentarios.clear();


        Lexer LexerC = new Lexer();

        LexerC.init(eArquivo);
        mIChars = LexerC.getChars();
        mTokens = LexerC.getTokens();


        for (Erro mErro : LexerC.getErros()) {
            errarLexer(mErro.getMensagem(), mErro.getLinha(), mErro.getPosicao());
        }


        GrupoDeComentario GrupoDeComentarioC = new GrupoDeComentario(eArquivo);

        mTokens = new ArrayList<Token>();

        for (Token TokenC : LexerC.getTokens()) {

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                GrupoDeComentarioC.adicionarComentario(TokenC);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                GrupoDeComentarioC.adicionarComentario(TokenC);

            } else {

                mTokens.add(TokenC);

            }
        }

        mComentarios.add(GrupoDeComentarioC);

        mIndex = 0;
        mTamanho = mTokens.size();
        mITokens = mTokens.size();


        File arq = new File(eArquivo);
        mLocal = arq.getParent() + "/";

        mAST = new AST("MAKE");

        while (Continuar()) {

            Token TokenC = this.getTokenCorrente();

            if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("status")) {

                AST_Status ePeca = new AST_Status(this);
                ePeca.init(mAST);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("build")) {

                AST_Build ePeca = new AST_Build(this);
                ePeca.init(mAST);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("source")) {

                AST_Source ePeca = new AST_Source(this);
                ePeca.init(mAST);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("highlights")) {

                AST_HighLight ePeca = new AST_HighLight(this);
                ePeca.init(mAST);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("intellisenses")) {

                AST_Intellisenses ePeca = new AST_Intellisenses(this);
                ePeca.init(mAST);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("options")) {

                AST_Options ePeca = new AST_Options(this);
                ePeca.init(mAST);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("ident")) {

                AST_Ident ePeca = new AST_Ident(this);
                ePeca.init(mAST);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("make")) {

                AST_Make ePeca = new AST_Make(this);
                ePeca.init(mAST);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("generate")) {

                AST_Generate ePeca = new AST_Generate(this);
                ePeca.init(mAST);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("tests")) {

                AST_Tests ePeca = new AST_Tests(this);
                ePeca.init(mAST);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("ast")) {

                AST_AST ePeca = new AST_AST(this);
                ePeca.init(mAST);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("set")) {

                AST_Set ePeca = new AST_Set(this);
                ePeca.init(mAST);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("remove")) {

                AST_Remove ePeca = new AST_Remove(this);
                ePeca.init(mAST);

            } else {
                errarCompilacao("Token Desconhecido : " + TokenC.getTipo() + " " + TokenC.getConteudo(), TokenC);
            }


            Proximo();
        }


    }


    public Token getTokenCorrente() {
        return mTokens.get(mIndex);
    }


    public AST getAST() {
        return mAST;
    }

    public Token getTokenAvante() {

        mIndex += 1;

        if (mIndex < mTamanho) {

            mPosicao = mTokens.get(mIndex).getPosicao();
            mFim = mTokens.get(mIndex).getFim();
            mLinha = mTokens.get(mIndex).getLinha();

            return mTokens.get(mIndex);
        } else {
            return new Token(TokenTipo.DESCONHECIDO, "", mPosicao, mFim, mLinha);
        }

    }

    public Token getTokenFuturo() {


        if (mIndex + 1 < mTamanho) {
            return mTokens.get(mIndex + 1);
        } else {
            return new Token(TokenTipo.DESCONHECIDO, "", mPosicao, mFim, mLinha);
        }

    }

    public int getInstrucoes() {
        int t = 0;

        t += getAST().getInstrucoes();


        return t;
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

    public ArrayList<GrupoDeErro> getErros_Lexer() {
        return mErros_Lexer;
    }

    public ArrayList<GrupoDeErro> getErros_Compiler() {
        return mErros_Compiler;
    }

    public ArrayList<GrupoDeComentario> getComentarios() {
        return mComentarios;
    }


    public AST getPackage(AST ASTPai, String eNome) {

        AST AST_Corrente = null;
        boolean enc = false;

        for (AST eAST : ASTPai.getASTS()) {
            if (eAST.mesmoTipo("PACKAGE") && eAST.mesmoNome(eNome)) {
                AST_Corrente = eAST;
                enc = true;
                break;
            }
        }

        if (!enc) {
            AST_Corrente = new AST("PACKAGE");
            AST_Corrente.setNome(eNome);
            ASTPai.getASTS().add(AST_Corrente);
        }

        return AST_Corrente;
    }

    public Token getTokenAvanteStatus(TokenTipo eTokenTipo, String eErro) {
        Token TokenP = getTokenAvante();
        if (TokenP.getTipo() == eTokenTipo) {

        } else {
            errarCompilacao(eErro, TokenP);
            errarCompilacao("Contudo encontrou : " + TokenP.getConteudo(), TokenP);
        }
        return TokenP;
    }

    public Token getTokenAvanteIDStatus(String eQualID, String eErro) {
        Token TokenP = getTokenAvante();
        if (TokenP.getTipo() == TokenTipo.ID) {

            if (TokenP.mesmoConteudo(eQualID)) {

            } else {
                errarCompilacao(eErro + " contudo encontrou : " + TokenP.getConteudo(), TokenP);
            }


        } else {
            errarCompilacao(eErro + " contudo encontrou : " + TokenP.getConteudo(), TokenP);
        }
        return TokenP;
    }


    public void errarLexer(String eMensagem, int eLinha, int ePosicao) {

        boolean enc = false;

        for (GrupoDeErro G : mErros_Lexer) {
            if (G.mesmmoArquivo(mArquivo)) {
                G.adicionarErro(eMensagem, eLinha, ePosicao);
                enc = true;
                break;
            }
        }

        if (!enc) {
            GrupoDeErro nG = new GrupoDeErro(mArquivo);
            nG.adicionarErro(eMensagem, eLinha, ePosicao);
            mErros_Lexer.add(nG);
        }
    }

    public void errarCompilacao(String eMensagem, Token eToken) {

        boolean enc = false;

        for (GrupoDeErro G : mErros_Compiler) {
            if (G.mesmmoArquivo(mArquivo)) {
                G.adicionarErro(eMensagem, eToken.getLinha(), eToken.getPosicao());
                enc = true;
                break;
            }
        }

        if (!enc) {
            GrupoDeErro nG = new GrupoDeErro(mArquivo);
            nG.adicionarErro(eMensagem, eToken.getLinha(), eToken.getPosicao());
            mErros_Compiler.add(nG);
        }
    }


    public String getData() {

        return Tempo.getData();


    }

}
