package Sigmaz.S01_Compilador.C03_Parser.Bloco;

import Sigmaz.S01_Compilador.C03_Parser.AST_TYPE;
import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Corpo;
import Sigmaz.S01_Compilador.C03_Parser.Organizador.AST_Argumentos;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Acessadores {

    private Parser mCompiler;

    public AST_Acessadores(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai, String Visibilidade) {



        Token TokenSeta = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA");

        Token TokenID = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado GET ou SET");


        if (TokenID.mesmoConteudo("get")) {

            AST AST_Corrente = new AST("GETTER");
            AST_Corrente.setNome("GETTER");
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_Visibilidade = AST_Corrente.criarBranch("VISIBILITY");
            AST_Visibilidade.setNome(Visibilidade);

            AST AST_Arguments = AST_Corrente.criarBranch("ARGUMENTS");
            AST AST_BODY = AST_Corrente.criarBranch("BODY");

            AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
            mArgumentos.initColchete(AST_Arguments);

            AST_TYPE mType = new AST_TYPE(mCompiler);
            mType.init(AST_Corrente);

            AST_Corpo mCorpo = new AST_Corpo(mCompiler);
            mCorpo.init(AST_BODY);

        } else if (TokenID.mesmoConteudo("set")) {


            AST AST_Corrente = new AST("SETTER");
            AST_Corrente.setNome("SETTER");
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_Visibilidade = AST_Corrente.criarBranch("VISIBILITY");
            AST_Visibilidade.setNome(Visibilidade);

            AST AST_Arguments = AST_Corrente.criarBranch("ARGUMENTS");
            AST AST_Values = AST_Corrente.criarBranch("VALUES");

            AST AST_BODY = AST_Corrente.criarBranch("BODY");

            AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
            mArgumentos.initColchete(AST_Arguments);

            Token TokenSeta2 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA");

            AST_Argumentos mValores = new AST_Argumentos(mCompiler);
            mValores.init(AST_Values);

            AST_Corpo mCorpo = new AST_Corpo(mCompiler);
            mCorpo.init(AST_BODY);


        }else{
            mCompiler.errarCompilacao("Era esperado GET ou SET",TokenID);
        }




    }


}