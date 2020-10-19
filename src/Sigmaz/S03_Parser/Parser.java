package Sigmaz.S03_Parser;

import Sigmaz.S00_Utilitarios.*;
import Sigmaz.S03_Parser.Alocador.AST_Alocador;
import Sigmaz.S03_Parser.Bloco.*;
import Sigmaz.S03_Parser.Complexo.*;
import Sigmaz.S03_Parser.Fluxo.AST_Default;
import Sigmaz.S03_Parser.Fluxo.AST_Invoke;
import Sigmaz.S03_Parser.Organizador.AST_Import;
import Sigmaz.S03_Parser.Organizador.AST_Refer;
import Sigmaz.S03_Parser.Organizador.AST_Require;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;

import java.util.ArrayList;

public class Parser {

    private int mIndex;
    private int mTamanho;


    private ArrayList<GrupoDeErro> mErros_Compiler;


    private String mArquivo;
    private String mLocal;

    private int mPosicao;
    private int mFim;
    private int mLinha;

    private int mInstrucoes;

    private ArrayList<Importacao> mFila;
    private ArrayList<Token> mTokens;

    private ArrayList<String> mRequisitados;

    public Parser() {

        mArquivo = "";

        mIndex = 0;
        mTamanho = 0;
        mPosicao = 0;

        mInstrucoes = 0;

        mFila = new ArrayList<Importacao>();

        mErros_Compiler = new ArrayList<>();


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
        mErros_Compiler.clear();


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


    public ArrayList<GrupoDeErro> getErros_Compiler() {
        return mErros_Compiler;
    }


    public void init(String eArquivo, String eLocal, ArrayList<Token> eTokens, AST AST_Raiz, ArrayList<String> eRequisitados) {

        mArquivo = eArquivo;
        mLocal = eLocal;

        mTokens = eTokens;
        mRequisitados = eRequisitados;

        mIndex = 0;
        mTamanho = mTokens.size();

        mInstrucoes=0;

        int mAntes = AST_Raiz.getObjetos();

        while (Continuar()) {

            Token TokenC = this.getTokenCorrente();
            if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("import")) {

                AST_Import mAST = new AST_Import(this);
                mAST.init(AST_Raiz);


                if (mErros_Compiler.size() > 0) {
                    break;
                }

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("require")) {

                AST_Require mAST = new AST_Require(this);
                mAST.init(AST_Raiz);


                if (mErros_Compiler.size() > 0) {
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

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("invoke")) {

                AST_Invoke mAST = new AST_Invoke(this);
                mAST.init(AST_Raiz);

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
                mAST.init(AST_Raiz);

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

            if (mErros_Compiler.size() > 0) {
                return;
            }

            Proximo();
        }

        int mDepois = AST_Raiz.getObjetos();

        mInstrucoes=mDepois-mAntes;


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


}
