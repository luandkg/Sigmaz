package Sigmaz.S04_Compilador.Bloco;

import Sigmaz.S04_Compilador.*;
import Sigmaz.S04_Compilador.Fluxo.AST_Corpo;
import Sigmaz.S04_Compilador.Organizador.AST_Argumentos;
import Sigmaz.S04_Compilador.Organizador.AST_Generic;
import Sigmaz.S03_Parser.Token;
import Sigmaz.S03_Parser.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_PrototypeFunctor{

    private CompilerUnit mCompiler;

    public AST_PrototypeFunctor(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        AST AST_Corrente = ASTPai.criarBranch("PROTOTYPE_FUNCTOR");


        AST AST_Generico = AST_Corrente.criarBranch("GENERICS");


        AST_Generic mg = new AST_Generic(mCompiler);
        mg.init_receberProto(AST_Generico);



        Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o nome para o FUNCTOR!");

        AST_Corrente.setNome(TokenC3.getConteudo());


        AST AST_Arguments = AST_Corrente.criarBranch("ARGUMENTS");
        AST AST_BODY = AST_Corrente.criarBranch("BODY");

        AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
        mArgumentos.init(AST_Arguments);

        AST_TYPE mType = new AST_TYPE(mCompiler);
        mType.init(AST_Corrente);

        AST_Corpo mCorpo = new AST_Corpo(mCompiler);
        mCorpo.init(AST_BODY);

    }
}
