package Sigmaz.S01_Compilador.C03_Parser.Bloco;

import Sigmaz.S01_Compilador.C03_Parser.Errador;
import Sigmaz.S01_Compilador.C03_Parser.Organizador.AST_Argumentos;
import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Corpo;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S01_Compilador.Orquestrantes;
import Sigmaz.S08_Utilitarios.AST;

public class AST_Action {

    private Parser mCompiler;

    public AST_Action(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai, String Visibilidade) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST(Orquestrantes.ACTION);
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_Visibilidade = AST_Corrente.criarBranch(Orquestrantes.VISIBILITY);
            AST_Visibilidade.setNome(Visibilidade);

            AST AST_Arguments = AST_Corrente.criarBranch(Orquestrantes.ARGUMENTS);
            AST AST_BODY = AST_Corrente.criarBranch(Orquestrantes.BODY);

            AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
            mArgumentos.init(AST_Arguments);

            AST_Corpo mCorpo = new AST_Corpo(mCompiler);
            mCorpo.init(AST_BODY);


        } else {
            mCompiler.errarCompilacao(Errador.eraEsperadoNomeDaAction(), TokenC);
        }


    }


    public void init_Definicao(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST(Orquestrantes.ACTION);
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_Arguments = AST_Corrente.criarBranch(Orquestrantes.ARGUMENTS);

            AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
            mArgumentos.init_Tipagem(AST_Arguments);


            Token TokenP2 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA,Errador.eraEsperadoPontoEVirgula());


        } else {
            mCompiler.errarCompilacao(Errador.eraEsperadoNomeDaAction(), TokenC);
        }

    }


}