package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Alocador {

    private Compiler mCompiler;

    public AST_Alocador(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init_Def(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            algum("DEF", TokenC, ASTPai);

        } else {
            mCompiler.errarCompilacao("Era esperado o nome da VARIAVEL !", TokenC.getInicio());
        }


    }

    public void init_Moc(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            algum("MOC", TokenC, ASTPai);

        } else {
            mCompiler.errarCompilacao("Era esperado o nome da CONSTANTE !", TokenC.getInicio());
        }


    }

    private void algum(String eTipo, Token TokenC, AST ASTPai) {

        String erro = "";

        if (eTipo.contentEquals("DEF")) {

            erro = "Variável";

        } else if (eTipo.contentEquals("MOC")) {

            erro = "Constante";

        }

        AST AST_Corrente = new AST(eTipo);
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);

        AST AST_Generico = AST_Corrente.criarBranch("GENERIC");
        AST_Generico.setNome("FALSE");
        AST AST_Valor = AST_Corrente.criarBranch("VALUE");

        Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado Dois Pontos");
        Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado uma Tipagem");

        AST_Corrente.setValor(TokenC3.getConteudo());


        Token TokenFuturo = mCompiler.getTokenFuturo();
        if (TokenFuturo.getTipo() == TokenTipo.ENVIAR) {

            AST_Generico.setNome("TRUE");

            AST_Generic mg = new AST_Generic(mCompiler);
            mg.init(AST_Generico);


        }

        //  System.out.println("To : " + mCompiler.getTokenCorrente().getConteudo());


        Token TokenP3 = mCompiler.getTokenAvante();

        if (TokenP3.getTipo() == TokenTipo.PONTOVIRGULA) {
            AST_Valor.setValor("NULL");
            return;
        } else if (TokenP3.getTipo() == TokenTipo.IGUAL) {

            AST_Value mAST = new AST_Value(mCompiler);

            mAST.init(AST_Valor);
        } else {

            mCompiler.errarCompilacao("Era esperado o valor da " + erro + " !", TokenC.getInicio());

        }


    }


    public void init_Define(AST ASTPai, String Visibilidade) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            algum2("DEFINE",TokenC,ASTPai,Visibilidade);

        } else {
            mCompiler.errarCompilacao("Era esperado o nome da DEFINE !", TokenC.getInicio());
        }


    }

    public void init_Mockiz(AST ASTPai, String Visibilidade) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            algum2("MOCKIZ",TokenC,ASTPai,Visibilidade);

        } else {
            mCompiler.errarCompilacao("Era esperado o nome da MOCKIZ !", TokenC.getInicio());
        }


    }

    private void algum2(String eTipo, Token TokenC, AST ASTPai,String Visibilidade) {

        String erro = "";

        if (eTipo.contentEquals("DEFINE")) {

            erro = "DEFINE";

        } else if (eTipo.contentEquals("MOCKIZ")) {

            erro = "MOCKIZ";

        }

        AST AST_Corrente = new AST(eTipo);
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);

        AST AST_Visibilidade = AST_Corrente.criarBranch("VISIBILITY");
        AST_Visibilidade.setNome(Visibilidade);

        AST AST_Generico = AST_Corrente.criarBranch("GENERIC");
        AST_Generico.setNome("FALSE");

        AST AST_Valor = AST_Corrente.criarBranch("VALUE");

        Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado Dois Pontos");
        Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado uma Tipagem");

        AST_Corrente.setValor(TokenC3.getConteudo());


        Token TokenFuturo = mCompiler.getTokenFuturo();
        if (TokenFuturo.getTipo() == TokenTipo.ENVIAR) {

            AST_Generico.setNome("TRUE");

            AST_Generic mg = new AST_Generic(mCompiler);
            mg.init(AST_Generico);


        }


        Token TokenP3 = mCompiler.getTokenAvante();

        if (TokenP3.getTipo() == TokenTipo.PONTOVIRGULA) {
            AST_Valor.setValor("NULL");
            return;
        } else if (TokenP3.getTipo() == TokenTipo.IGUAL) {

            AST_Value mAST = new AST_Value(mCompiler);
            mAST.init(AST_Valor);
        } else {

            mCompiler.errarCompilacao("Era esperado o valor da " + erro + " !", TokenC.getInicio());

        }


    }

}