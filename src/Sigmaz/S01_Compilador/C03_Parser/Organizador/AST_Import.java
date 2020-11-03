package Sigmaz.S01_Compilador.C03_Parser.Organizador;

import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Importacao;

public class AST_Import {

    private Parser mCompiler;

    public AST_Import(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.TEXTO) {

            Token TokenP3 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado PONTO E VIRGULA !");

            String mLocalRequisicao = mCompiler.getLocal() + TokenC.getConteudo();

            mCompiler.enfileirar(new Importacao(mCompiler.getArquivo(),mLocalRequisicao,TokenC.getLinha(),TokenC.getPosicao()));


        } else {
            mCompiler.errarCompilacao("Era esperado o caminho de uma Importacao !", TokenC);
        }

    }



}
