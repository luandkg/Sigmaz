package Sigmaz.Compilador;

import Sigmaz.Executor.Run_When;
import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Struct {

    private Compiler mCompiler;

    public AST_Struct(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("STRUCT");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);



            AST_StructCorpo mAST = new AST_StructCorpo(mCompiler);
            mAST.init(AST_Corrente);


        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma STRUCT !", TokenC.getInicio());
        }


    }



}