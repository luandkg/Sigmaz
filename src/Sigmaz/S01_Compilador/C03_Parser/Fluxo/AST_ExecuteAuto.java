package Sigmaz.S01_Compilador.C03_Parser.Fluxo;

import Sigmaz.S01_Compilador.C03_Parser.Organizador.AST_Generic;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_ExecuteAuto {

    private Parser mCompiler;

    public AST_ExecuteAuto(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        AST AST_Corrente = ASTPai.criarBranch("EXECUTE_AUTO");
        AST AST_Generico = AST_Corrente.criarBranch("GENERICS");


        AST_Generic mg = new AST_Generic(mCompiler);
        mg.init_receberProto(AST_Generico);


        Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o nome de um AUTO ou FUNCTOR!");

        AST_Corrente.setNome(TokenC3.getConteudo());

        Token TokenC4 = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado abrir paresenteses !");

       // AST_Value mAVA = new AST_Value(mCompiler);
        //mAVA.ReceberArgumentos(AST_Corrente);

        AST_ValueTypes mAVA = new AST_ValueTypes(mCompiler);
        mAVA.ReceberArgumentos(AST_Corrente,false,null);

        //System.out.println("VAL :: " + mCompiler.getTokenCorrente().getConteudo());

        Token TokenC5 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado ponto e vinrgula !");

    }
}
