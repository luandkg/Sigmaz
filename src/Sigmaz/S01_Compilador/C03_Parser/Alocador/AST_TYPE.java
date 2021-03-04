package Sigmaz.S01_Compilador.C03_Parser.Alocador;

import Sigmaz.S01_Compilador.C03_Parser.Organizador.AST_Generic;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.Orquestrantes;

public class AST_TYPE {

    private Parser mCompiler;

    public AST_TYPE(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {




        Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado Dois Pontos");

        initDireto(ASTPai);

    }

    public void initDireto(AST ASTPai) {

        AST AST_Tipo = ASTPai.criarBranch(Orquestrantes.TYPE);


        Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado uma Tipagem");


        AST_Tipo.setNome(TokenC3.getConteudo());


        AST_Tipo.setValor(Orquestrantes.CONCRETE);


        Token TokenFuturo = mCompiler.getTokenFuturo();
        if (TokenFuturo.getTipo() == TokenTipo.ENVIAR) {

            AST_Tipo.setValor(Orquestrantes.GENERIC);

            AST_Generic mg = new AST_Generic(mCompiler);
            mg.init(AST_Tipo);


        }


    }


        public void init_Definicao(AST ASTPai) {


        AST AST_Tipo = ASTPai.criarBranch(Orquestrantes.TYPE);

        Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado uma Tipagem");


        AST_Tipo.setNome(TokenC3.getConteudo());

        AST_Tipo.setValor(Orquestrantes.CONCRETE);


        Token TokenFuturo = mCompiler.getTokenFuturo();
        if (TokenFuturo.getTipo() == TokenTipo.ENVIAR) {

            AST_Tipo.setValor(Orquestrantes.GENERIC);

            AST_Generic mg = new AST_Generic(mCompiler);
            mg.init(AST_Tipo);


        }

    }

}
