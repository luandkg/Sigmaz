package Sigmaz.S03_Parser.Fluxo;

import Sigmaz.S03_Parser.Parser;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Using {

    private Parser mCompiler;

    public AST_Using(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


      //  Token TokenC = mCompiler.getTokenAvante();
      //  if (TokenC.getTipo() == TokenTipo.ID) {
     //   } else {
    //        mCompiler.errarCompilacao("Era esperado uma variavel STRUCT para uma USING !", TokenC);
    //    }


        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado Abrir Parenteses");

        AST AST_Corrente = new AST("USING");


        AST AST_CType = AST_Corrente.criarBranch("STRUCT");

        AST_Value mASTType = new AST_Value(mCompiler);
        mASTType.setBloco();
        mASTType.init(AST_CType);
        AST_CType.setTipo("STRUCT");


        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);
        AST AST_Corpo = AST_Corrente.criarBranch("STRUCTURED");

        Token TokenC1 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado -> ");


        AST_UsingCorpo mAST_UsingCorpo = new AST_UsingCorpo(mCompiler);
        mAST_UsingCorpo.init(AST_Corpo);




    }
}
