package Make.Compiler;

import Sigmaz.S03_Parser.Token;
import Sigmaz.S03_Parser.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Tests {

    private Compiler mCompiler;

    public AST_Tests(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenTexto =mCompiler.getTokenAvanteStatus(TokenTipo.TEXTO,"Era esperado um texto !");

        Token TokenDoisPontos =mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS,"Era esperado Ponto Duplo !");

        Token TokenBuild =mCompiler.getTokenAvanteStatus(TokenTipo.TEXTO,"Era esperado um texto com o nome do arquivo de teste !");

        Token TokenSeta =mCompiler.getTokenAvanteStatus(TokenTipo.SETA,"Era esperado a SETA !");


        AST AST_Corrente = new AST("TESTS");

        AST_Corrente.setNome(TokenTexto.getConteudo());
        AST_Corrente.setValor(TokenBuild.getConteudo());

        ASTPai.getASTS().add(AST_Corrente);


        AST_List ePeca = new AST_List(mCompiler);
        ePeca.init(AST_Corrente);



        Token TokenVirgula =mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA,"Era esperado um ponto e virgula !");


    }




}