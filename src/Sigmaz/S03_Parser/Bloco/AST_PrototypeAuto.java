package Sigmaz.S03_Parser.Bloco;

import Sigmaz.S03_Parser.Organizador.AST_Argumentos;
import Sigmaz.S03_Parser.Fluxo.AST_Corpo;
import Sigmaz.S03_Parser.Organizador.AST_Generic;
import Sigmaz.S03_Parser.Parser;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_PrototypeAuto {

    private Parser mCompiler;

    public AST_PrototypeAuto(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        AST AST_Corrente = ASTPai.criarBranch("PROTOTYPE_AUTO");


        AST AST_Generico = AST_Corrente.criarBranch("GENERICS");


        AST_Generic mg = new AST_Generic(mCompiler);
        mg.init_receberProto(AST_Generico);



        Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o nome para o AUTO!");

        AST_Corrente.setNome(TokenC3.getConteudo());


        AST AST_Arguments = AST_Corrente.criarBranch("ARGUMENTS");
        AST AST_BODY = AST_Corrente.criarBranch("BODY");

        AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
        mArgumentos.init(AST_Arguments);

        AST_Corpo mCorpo = new AST_Corpo(mCompiler);
        mCorpo.init(AST_BODY);

    }
}
