package Sigmaz.S03_Parser.Bloco;

import Sigmaz.S03_Parser.AST_TYPE;
import Sigmaz.S03_Parser.Fluxo.AST_Corpo;
import Sigmaz.S03_Parser.Organizador.AST_Argumentos;
import Sigmaz.S03_Parser.Parser;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Col {

    private Parser mCompiler;

    public AST_Col(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai, String Visibilidade) {


        AST AST_Corrente = new AST("COL");
        ASTPai.getASTS().add(AST_Corrente);


        Token TokenSeta = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA");

        Token TokenID = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado GET ou SET");


        if (TokenID.mesmoConteudo("get")) {

            AST_Corrente.setTipo("COL_GET");
            AST_Corrente.setNome("COL_GET");

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

            AST_Corrente.setTipo("COL_SET");
            AST_Corrente.setNome("COL_SET");

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