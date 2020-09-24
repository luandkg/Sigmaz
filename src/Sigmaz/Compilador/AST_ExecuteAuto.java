package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_ExecuteAuto {

    private CompilerUnit mCompiler;

    public AST_ExecuteAuto(CompilerUnit eCompiler) {
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

        AST_Value_Argument mAVA = new AST_Value_Argument(mCompiler);
        mAVA.ReceberArgumentos(AST_Corrente);

        //System.out.println("VAL :: " + mCompiler.getTokenCorrente().getConteudo());

        Token TokenC5 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado ponto e vinrgula !");

    }
}
