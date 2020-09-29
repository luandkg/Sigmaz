package Sigmaz.Compilador.Bloco;

import Sigmaz.Compilador.Organizador.AST_Argumentos;
import Sigmaz.Compilador.Fluxo.AST_Corpo;
import Sigmaz.Compilador.Organizador.AST_Generic;
import Sigmaz.Compilador.CompilerUnit;
import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_PrototypeAuto {

    private CompilerUnit mCompiler;

    public AST_PrototypeAuto(CompilerUnit eCompiler) {
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
