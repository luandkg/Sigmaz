package Sigmaz.S01_Compilador.C03_Parser;

import Sigmaz.S00_Utilitarios.*;
import Sigmaz.S01_Compilador.C03_Parser.Alocador.AST_Alocador;
import Sigmaz.S01_Compilador.C03_Parser.Bloco.*;
import Sigmaz.S01_Compilador.C03_Parser.Complexo.*;
import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Default;
import Sigmaz.S01_Compilador.C03_Parser.Organizador.AST_Import;
import Sigmaz.S01_Compilador.C03_Parser.Organizador.AST_Refer;
import Sigmaz.S01_Compilador.C03_Parser.Organizador.AST_Require;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;

import java.util.ArrayList;

public class Parser {

    private int mIndex;
    private int mTamanho;


    private ArrayList<GrupoDeErro> mErros_Parser;


    private String mArquivo;
    private String mLocal;

    private int mPosicao;
    private int mFim;
    private int mLinha;

    private int mInstrucoes;

    private ArrayList<Importacao> mFila;
    private ArrayList<Token> mTokens;

    private ArrayList<String> mRequisitados;

    private boolean mIsDebug;
    private int mArquivoDebug;

    public Parser() {

        mArquivo = "";

        mIndex = 0;
        mTamanho = 0;
        mPosicao = 0;

        mInstrucoes = 0;

        mFila = new ArrayList<Importacao>();

        mErros_Parser = new ArrayList<>();
        mIsDebug = false;
        mArquivoDebug = 0;

    }

    public String getArquivo() {
        return mArquivo;
    }

    public String getLocal() {
        return mLocal;
    }

    public ArrayList<Token> getTokens() {
        return mTokens;
    }

    public void limpar() {

        mIndex = 0;

        mArquivo = "";
        mErros_Parser.clear();


    }

    public void proximo() {
        if (mIndex < mTamanho) {
            mIndex += 1;
        }
    }


    public ArrayList<String> getRequisicoes() {
        return mRequisitados;
    }

    public void enfileirar(Importacao eImportacao) {
        mFila.add(eImportacao);
    }

    public ArrayList<Importacao> getFila() {
        return mFila;
    }

    public Token getTokenCorrente() {
        return mTokens.get(mIndex);
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

    public int getObjetos() {
        return mInstrucoes;
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


    public boolean Continuar() {
        return mIndex < mTamanho;
    }

    public void Proximo() {
        mIndex += 1;
    }


    public ArrayList<GrupoDeErro> getErros_Parser() {
        return mErros_Parser;
    }

    public AST getASTDebug(AST AST_Raiz) {
        AST mRet = null;
        boolean mEnc = false;

        for (AST eAST : AST_Raiz.getASTS()) {
            if (eAST.mesmoTipo("DEBUG_MODE")) {
                mRet = eAST;
                mEnc = true;
                break;
            }
        }
        if (!mEnc) {
            mRet = AST_Raiz.criarBranch("DEBUG_MODE");
        }

        return mRet;
    }

    public boolean getIsDebug() {
        return mIsDebug;
    }

    public int getArquivoDebug() {
        return mArquivoDebug;
    }

    public void debug(AST eASTLocal){

        AST eDebug = eASTLocal.criarBranch("IN_DEBUG");
        eDebug.setNome(String.valueOf(getArquivoDebug()));
        eDebug.setValor(getTokenCorrente().getLinha() + ":" + getTokenCorrente().getPosicao());

    }

    public void init(String eArquivo, String eLocal, boolean eDebugar, ArrayList<Token> eTokens, AST AST_Raiz, ArrayList<String> eRequisitados) {

        mArquivo = eArquivo;
        mLocal = eLocal;

        mTokens = eTokens;
        mRequisitados = eRequisitados;

        mIndex = 0;
        mTamanho = mTokens.size();

        mInstrucoes = 0;

        mIsDebug = eDebugar;

        if (mIsDebug) {


            AST mDebugMode = getASTDebug(AST_Raiz);
            mDebugMode.setValor("TRUE");

            mArquivoDebug = mDebugMode.getASTS().size();

            AST eDebug = mDebugMode.criarBranch("DEBUG");
            eDebug.setNome(String.valueOf(mArquivoDebug));
            eDebug.setValor(eArquivo);

        } else {
            getASTDebug(AST_Raiz).setValor("FALSE");
        }

        int mAntes = AST_Raiz.getObjetos();

        while (Continuar()) {

            Token TokenC = this.getTokenCorrente();
            if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("import")) {

                AST_Import mAST = new AST_Import(this);
                mAST.init(AST_Raiz);


                if (mErros_Parser.size() > 0) {
                    break;
                }

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("require")) {

                AST_Require mAST = new AST_Require(this);
                mAST.init(AST_Raiz);


                if (mErros_Parser.size() > 0) {
                    break;
                }
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("package")) {

                AST_Package mAST = new AST_Package(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("refer")) {

                AST_Refer mAST = new AST_Refer(this);
                mAST.init(AST_Raiz);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("act")) {

                AST_Action mAST = new AST_Action(this);
                mAST.init(AST_Raiz, "ALL");

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("func")) {

                AST_Function mAST = new AST_Function(this);
                mAST.init(AST_Raiz, "ALL");

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("call")) {

                AST_Call mAST = new AST_Call(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                AST_Alocador mAST = new AST_Alocador(this);
                mAST.init("MOCKIZ", AST_Raiz, "ALL");

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                AST_Alocador mAST = new AST_Alocador(this);
                mAST.init("DEFINE", AST_Raiz, "ALL");


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("operator")) {

                AST_Operator mAST = new AST_Operator(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("director")) {

                AST_Director mAST = new AST_Director(this);
                mAST.init(AST_Raiz);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("cast")) {

                AST_Cast mAST = new AST_Cast(this);
                mAST.init("", AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("struct")) {

                AST_Struct mAST = new AST_Struct(this);
                mAST.init("", AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("type")) {

                AST_TypeStruct mAST = new AST_TypeStruct(this);
                mAST.init("", AST_Raiz);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("external")) {

                AST_External mAST = new AST_External(this);
                mAST.init(AST_Raiz);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("stages")) {

                AST_Stages mAST = new AST_Stages(this);
                mAST.init("", AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("model")) {

                AST_Model mAST = new AST_Model(this);
                mAST.init("", AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("auto")) {

                AST_PrototypeAuto mAST = new AST_PrototypeAuto(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("functor")) {

                AST_PrototypeFunctor mAST = new AST_PrototypeFunctor(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("default")) {

                AST_Default mAST = new AST_Default(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mark")) {

                AST_Marcador mAST = new AST_Marcador(this);
                mAST.init(AST_Raiz, "ALL");

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo_SemCS("DEBUG")) {

                AST_Debug mAST = new AST_Debug(this);
                mAST.init(AST_Raiz);


            } else {
                errarCompilacao("Token Desconhecido : " + TokenC.getTipo() + " " + TokenC.getConteudo(), TokenC);
                return;
            }

            if (mErros_Parser.size() > 0) {
                return;
            }

            Proximo();
        }

        int mDepois = AST_Raiz.getObjetos();

        mInstrucoes = mDepois - mAntes;


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


    public void errarCompilacao(String eMensagem, Token eToken) {

        boolean enc = false;

        for (GrupoDeErro G : mErros_Parser) {
            if (G.mesmmoArquivo(mArquivo)) {
                G.adicionarErro(eMensagem, eToken.getLinha(), eToken.getPosicao());
                enc = true;
                break;
            }
        }

        if (!enc) {
            GrupoDeErro nG = new GrupoDeErro(mArquivo);
            nG.adicionarErro(eMensagem, eToken.getLinha(), eToken.getPosicao());
            mErros_Parser.add(nG);
        }
    }


}
