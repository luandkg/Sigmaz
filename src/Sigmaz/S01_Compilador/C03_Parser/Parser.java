package Sigmaz.S01_Compilador.C03_Parser;

import Sigmaz.S08_Utilitarios.*;
import Sigmaz.S01_Compilador.C03_Parser.Alocador.AST_Alocador;
import Sigmaz.S01_Compilador.C03_Parser.Bloco.*;
import Sigmaz.S01_Compilador.C03_Parser.Complexo.*;
import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Debug;
import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Default;
import Sigmaz.S01_Compilador.C03_Parser.Organizador.AST_Import;
import Sigmaz.S01_Compilador.C03_Parser.Organizador.AST_Refer;
import Sigmaz.S01_Compilador.C03_Parser.Organizador.AST_Require;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S01_Compilador.C03_Parser.Testes.AST_Test;
import Sigmaz.S01_Compilador.Termos;

import java.util.ArrayList;

public class Parser {

    private int mIndex;
    private int mTamanho;


    private ArrayList<Erro> mErros_Parser;


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

    private long mTempo;

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
        mTempo = 0;

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
        mTempo = 0;


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


    public ArrayList<Erro> getErros_Parser() {
        return mErros_Parser;
    }


    public boolean getIsDebug() {
        return mIsDebug;
    }

    public int getArquivoDebug() {
        return mArquivoDebug;
    }

    public void debug(AST eASTLocal) {

        AST eDebug = eASTLocal.criarBranch("IN_DEBUG");
        eDebug.setNome(String.valueOf(getArquivoDebug()));
        eDebug.setValor(getTokenCorrente().getLinha() + ":" + getTokenCorrente().getPosicao());

    }

    public void init(String eArquivo, String eLocal, boolean eDebugar, ArrayList<Token> eTokens, AST AST_Raiz, AST mAST_DEBUGGER, ArrayList<String> eRequisitados) {

        mArquivo = eArquivo;
        mLocal = eLocal;

        mTokens = eTokens;
        mRequisitados = eRequisitados;

        mIndex = 0;
        mTamanho = mTokens.size();

        mInstrucoes = 0;

        mIsDebug = eDebugar;
        mTempo = 0;


        long mInicio = System.nanoTime();

        if (mIsDebug) {

            mAST_DEBUGGER.setValor("TRUE");

            mArquivoDebug = mAST_DEBUGGER.getASTS().size();

            AST eDebug = mAST_DEBUGGER.criarBranch(Termos.DEBUG);
            eDebug.setNome(String.valueOf(mArquivoDebug));
            eDebug.setValor(eArquivo);

        } else {
            mAST_DEBUGGER.setValor("FALSE");
        }

        int mAntes = AST_Raiz.getObjetos();

        while (Continuar()) {

            Token TokenC = this.getTokenCorrente();
            if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.IMPORT)) {

                AST_Import mAST = new AST_Import(this);
                mAST.init(AST_Raiz);


                if (mErros_Parser.size() > 0) {
                    break;
                }

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.REQUIRE)) {

                AST_Require mAST = new AST_Require(this);
                mAST.init(AST_Raiz);


                if (mErros_Parser.size() > 0) {
                    break;
                }
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.PACKAGE)) {

                AST_Package mAST = new AST_Package(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.REFER)) {

                AST_Refer mAST = new AST_Refer(this);
                mAST.init(AST_Raiz);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.ACT)) {

                AST_Action mAST = new AST_Action(this);
                mAST.init(AST_Raiz, "ALL");

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.FUNC)) {

                AST_Function mAST = new AST_Function(this);
                mAST.init(AST_Raiz, "ALL");

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.CALL)) {
                AST_Call mAST = new AST_Call(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.MOCKIZ)) {

                AST_Alocador mAST = new AST_Alocador(this);
                mAST.init("MOCKIZ", AST_Raiz, "ALL");

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.DEFINE)) {

                AST_Alocador mAST = new AST_Alocador(this);
                mAST.init("DEFINE", AST_Raiz, "ALL");


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.OPERATOR)) {

                AST_Operator mAST = new AST_Operator(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.DIRECTOR)) {

                AST_Director mAST = new AST_Director(this);
                mAST.init(AST_Raiz);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.CAST)) {

                AST_Cast mAST = new AST_Cast(this);
                mAST.init("", AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.STRUCT)) {

                AST_Struct mAST = new AST_Struct(this);
                mAST.init("", AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.TYPE)) {

                AST_TypeStruct mAST = new AST_TypeStruct(this);
                mAST.init("", AST_Raiz);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.EXTERNAL)) {

                AST_External mAST = new AST_External(this);
                mAST.init(AST_Raiz);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.STAGES)) {

                AST_Stages mAST = new AST_Stages(this);
                mAST.init("", AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.MODEL)) {

                AST_Model mAST = new AST_Model(this);
                mAST.init("", AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.AUTO)) {

                AST_PrototypeAuto mAST = new AST_PrototypeAuto(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.FUNCTOR)) {

                AST_PrototypeFunctor mAST = new AST_PrototypeFunctor(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.DEFAULT)) {

                AST_Default mAST = new AST_Default(this);
                mAST.init(AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.MARK)) {

                AST_Marcador mAST = new AST_Marcador(this);
                mAST.init(AST_Raiz, "ALL");

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.TEST)) {

                AST_Test mAST = new AST_Test(this);
                mAST.init(AST_Raiz);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo_SemCS(Termos.DEBUG)) {

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

        long mFim = System.nanoTime();

        mTempo = mFim - mInicio;

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

        mErros_Parser.add(new Erro(eMensagem, eToken.getLinha(), eToken.getPosicao()));

    }


    public long getTempo() {
        return mTempo;
    }
}
