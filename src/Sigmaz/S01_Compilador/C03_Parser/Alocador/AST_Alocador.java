package Sigmaz.S01_Compilador.C03_Parser.Alocador;

import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Value;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S01_Compilador.Orquestrantes;

public class AST_Alocador {

    private Parser mCompiler;

    public AST_Alocador(Parser eCompiler) {
        mCompiler = eCompiler;
    }



    public void init(String eTipo,  AST ASTPai) {


        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

        } else {
            mCompiler.errarCompilacao("Era esperado o nome da Variável !", TokenC);
            return;
        }


        AST AST_Corrente = new AST(eTipo);
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);


        AST_TYPE mType = new AST_TYPE(mCompiler);
        mType.init(AST_Corrente);

        //  System.out.println("To : " + mCompiler.getTokenCorrente().getConteudo());
        AST AST_Valor = AST_Corrente.criarBranch(Orquestrantes.VALUE);


        Token TokenP3 = mCompiler.getTokenAvante();

        if (TokenP3.getTipo() == TokenTipo.PONTOVIRGULA) {
            AST_Valor.setValor(Orquestrantes.NULL);
            return;
        } else if (TokenP3.getTipo() == TokenTipo.IGUAL) {

            AST_Value mAST = new AST_Value(mCompiler);
            mAST.init(AST_Valor);

        } else {

            mCompiler.errarCompilacao("Era esperado o valor da Variável !", TokenC);

        }


    }



    public void initSemTipagem(String eTipo,  AST ASTPai) {


        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {


        } else {
            mCompiler.errarCompilacao("Era esperado o nome da Variável !", TokenC);
            return;
        }



        AST AST_Corrente = new AST(eTipo);
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);




        //  System.out.println("To : " + mCompiler.getTokenCorrente().getConteudo());
        AST AST_Valor = AST_Corrente.criarBranch(Orquestrantes.VALUE);


        Token TokenP3 = mCompiler.getTokenAvante();

        if (TokenP3.getTipo() == TokenTipo.PONTOVIRGULA) {

            mCompiler.errarCompilacao("A definicao de uma Variável com inferencia de tipo precisa receber um valor de atribuicao !", TokenC);

            return;
        } else if (TokenP3.getTipo() == TokenTipo.IGUAL) {

            AST_Value mAST = new AST_Value(mCompiler);

            mAST.init(AST_Valor);

            if (AST_Valor.mesmoNome("null") && AST_Valor.mesmoValor("ID")){

                mCompiler.errarCompilacao("A definicao de uma Variável com inferencia de tipo nao pode ter o valor de atribuicao NULO !", TokenC);

            }

        } else {

            mCompiler.errarCompilacao("Era esperado o valor da Variável !", TokenC);

        }


    }



    public void init(String eTipo,  AST ASTPai,String Visibilidade) {

        String erro = "";

        if (eTipo.contentEquals("DEFINE")) {

            erro = "DEFINE";

        } else if (eTipo.contentEquals("MOCKIZ")) {

            erro = "MOCKIZ";

        }

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {



        } else {
            mCompiler.errarCompilacao("Era esperado o nome da " + erro + " !", TokenC);
            return;
        }


        AST AST_Corrente = new AST(eTipo);
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);

        AST AST_Visibilidade = AST_Corrente.criarBranch("VISIBILITY");
        AST_Visibilidade.setNome(Visibilidade);

        AST_TYPE mType = new AST_TYPE(mCompiler);
        mType.init(AST_Corrente);


        AST AST_Valor = AST_Corrente.criarBranch("VALUE");


        Token TokenP3 = mCompiler.getTokenAvante();

        if (TokenP3.getTipo() == TokenTipo.PONTOVIRGULA) {
            AST_Valor.setValor("NULL");
            return;
        } else if (TokenP3.getTipo() == TokenTipo.IGUAL) {

            AST_Value mAST = new AST_Value(mCompiler);
            mAST.init(AST_Valor);

        } else {
            mCompiler.errarCompilacao("Era esperado o valor da " + erro + " !", TokenC);
        }


    }

    public void initDefine( AST ASTPai,String Visibilidade) {



        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {



        } else {
            mCompiler.errarCompilacao("Era esperado o nome da DEFINE !", TokenC);
            return;
        }


        AST AST_Corrente = new AST("DEFINE");
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);

        AST AST_Visibilidade = AST_Corrente.criarBranch("VISIBILITY");
        AST_Visibilidade.setNome(Visibilidade);

        AST_TYPE mType = new AST_TYPE(mCompiler);
        mType.init(AST_Corrente);


        AST AST_Valor = AST_Corrente.criarBranch("VALUE");


        Token TokenP3 = mCompiler.getTokenAvante();

        if (TokenP3.getTipo() == TokenTipo.PONTOVIRGULA) {
            AST_Valor.setValor("NULL");
            return;
        } else if (TokenP3.getTipo() == TokenTipo.IGUAL) {

            AST_Value mAST = new AST_Value(mCompiler);
            mAST.init(AST_Valor);

        } else {
            mCompiler.errarCompilacao("Era esperado o valor da DEFINE !", TokenC);
        }


    }


    public void initMockiz( AST ASTPai,String Visibilidade) {



        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {



        } else {
            mCompiler.errarCompilacao("Era esperado o nome da MOCKIZ !", TokenC);
            return;
        }


        AST AST_Corrente = new AST("MOCKIZ");
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);

        AST AST_Visibilidade = AST_Corrente.criarBranch("VISIBILITY");
        AST_Visibilidade.setNome(Visibilidade);

        AST_TYPE mType = new AST_TYPE(mCompiler);
        mType.init(AST_Corrente);


        AST AST_Valor = AST_Corrente.criarBranch("VALUE");


        Token TokenP3 = mCompiler.getTokenAvante();

        if (TokenP3.getTipo() == TokenTipo.PONTOVIRGULA) {
            AST_Valor.setValor("NULL");
            return;
        } else if (TokenP3.getTipo() == TokenTipo.IGUAL) {

            AST_Value mAST = new AST_Value(mCompiler);
            mAST.init(AST_Valor);

        } else {
            mCompiler.errarCompilacao("Era esperado o valor da MOCKIZ !", TokenC);
        }


    }


    public void init_DefinicaoMockiz(  AST ASTPai) {

        String erro = "MOCKIZ";



        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {



        } else {
            mCompiler.errarCompilacao("Era esperado o nome da " + erro + " !", TokenC);
            return;
        }


        AST AST_Corrente = new AST("MOCKIZ");
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);


        AST_TYPE mType = new AST_TYPE(mCompiler);
        mType.init(AST_Corrente);


        Token TokenP2 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA,"Era esperado PONTO E VIRGULA !");



    }

    public void init_DefinicaoDefine(  AST ASTPai) {

        String erro = "DEFINE";



        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {



        } else {
            mCompiler.errarCompilacao("Era esperado o nome da " + erro + " !", TokenC);
            return;
        }


        AST AST_Corrente = new AST("DEFINE");
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);


        AST_TYPE mType = new AST_TYPE(mCompiler);
        mType.init(AST_Corrente);


        Token TokenP2 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA,"Era esperado PONTO E VIRGULA !");



    }

}