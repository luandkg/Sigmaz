package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_TYPE {

    private CompilerUnit mCompiler;

    public AST_TYPE(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {




        Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado Dois Pontos");

        initDireto(ASTPai);

    }

    public void initDireto(AST ASTPai) {

        AST AST_Tipo = ASTPai.criarBranch("TYPE");


        Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado uma Tipagem");


        AST_Tipo.setNome(TokenC3.getConteudo());


        AST_Tipo.setValor("CONCRETE");


        Token TokenFuturo = mCompiler.getTokenFuturo();
        if (TokenFuturo.getTipo() == TokenTipo.ENVIAR) {

            AST_Tipo.setValor("GENERIC");

            AST_Generic mg = new AST_Generic(mCompiler);
            mg.init(AST_Tipo);


        }


    }


        public void init_Definicao(AST ASTPai) {


        AST AST_Tipo = ASTPai.criarBranch("TYPE");

        Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado uma Tipagem");


        AST_Tipo.setNome(TokenC3.getConteudo());

        AST_Tipo.setValor("CONCRETE");


        Token TokenFuturo = mCompiler.getTokenFuturo();
        if (TokenFuturo.getTipo() == TokenTipo.ENVIAR) {

            AST_Tipo.setValor("GENERIC");

            AST_Generic mg = new AST_Generic(mCompiler);
            mg.init(AST_Tipo);


        }

    }

}
