package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_StructCorpo {

    private Compiler mCompiler;

    public AST_StructCorpo(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST AST_Corrente, AST AST_Inits,String NomeStruct) {


        AST AST_Corpo = AST_Corrente.criarBranch("BODY");


        Token TokenD= mCompiler.getTokenAvanteStatus(TokenTipo.CHAVE_ABRE,"Era esperado abrir chaves" );

        if (TokenD.getTipo() != TokenTipo.CHAVE_ABRE) {
            return;
        }

        boolean saiu = false;

        while (mCompiler.Continuar()) {
            Token TokenC = mCompiler.getTokenAvante();
            if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {
                saiu = true;
                break;
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("init")) {

                AST_InitStruct mAST = new AST_InitStruct(mCompiler);
                mAST.init(AST_Inits,NomeStruct);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("act")) {

                AST_Action mAST = new AST_Action(mCompiler);
                mAST.init(AST_Corpo);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("func")) {

                AST_Function mAST = new AST_Function(mCompiler);
                mAST.init(AST_Corpo);
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                AST_Mockiz mAST = new AST_Mockiz(mCompiler);
                mAST.init(AST_Corpo);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                AST_Define mAST = new AST_Define(mCompiler);
                mAST.init(AST_Corpo);



            }else{



                mCompiler.errarCompilacao("Comando Deconhecido : " + TokenD.getConteudo(), TokenD.getInicio());


            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante().getInicio());
        }
    }

}
