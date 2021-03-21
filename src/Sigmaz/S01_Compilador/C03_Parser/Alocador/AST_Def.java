package Sigmaz.S01_Compilador.C03_Parser.Alocador;

import Sigmaz.S01_Compilador.C03_Parser.Errador;
import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Value;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S01_Compilador.Orquestrantes;

public class AST_Def {

    private Parser mCompiler;

    public AST_Def(Parser eCompiler) {
        mCompiler = eCompiler;
    }


    public void init_Def(AST ASTPai) {


        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {


        } else {
            mCompiler.errarCompilacao(Errador.eraEsperadoNomeVariavel(), TokenC);
            return;
        }


        AST AST_Corrente = new AST(Orquestrantes.DEF);
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
            mAST.sePrecisarTipar(AST_Corrente.getBranch(Orquestrantes.TYPE));
            mAST.init(AST_Valor);

        } else {

            mCompiler.errarCompilacao(Errador.eraEsperadoValorVariavel(), TokenC);

        }


    }

    public void init_Define(AST ASTPai, String Visibilidade) {


        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {


        } else {
            mCompiler.errarCompilacao(Errador.eraEsperadoNomeVariavel(), TokenC);
            return;
        }


        AST AST_Corrente = new AST(Orquestrantes.DEFINE);
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);

        AST AST_Visibilidade = AST_Corrente.criarBranch(Orquestrantes.VISIBILITY);
        AST_Visibilidade.setNome(Visibilidade);

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

            mCompiler.errarCompilacao(Errador.eraEsperadoValorVariavel(), TokenC);

        }


    }


}
