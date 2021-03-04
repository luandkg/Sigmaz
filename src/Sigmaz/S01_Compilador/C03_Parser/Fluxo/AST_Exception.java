package Sigmaz.S01_Compilador.C03_Parser.Fluxo;

import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S01_Compilador.Orquestrantes;

public class AST_Exception {

    private Parser mCompiler;

    public AST_Exception(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        AST AST_Corrente = new AST(Orquestrantes.EXCEPTION);
        ASTPai.getASTS().add(AST_Corrente);

        AST AST_Valor = AST_Corrente.criarBranch(Orquestrantes.VALUE);

        AST_Value mAST = new AST_Value(mCompiler);
        mAST.init(AST_Valor);



    }



}