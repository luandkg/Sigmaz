package Sigmaz.S04_Compilador.Bloco;

import Sigmaz.S04_Compilador.Organizador.AST_Argumentos;
import Sigmaz.S04_Compilador.Fluxo.AST_Corpo;
import Sigmaz.S04_Compilador.AST_TYPE;
import Sigmaz.S04_Compilador.CompilerUnit;
import Sigmaz.S03_Parser.Token;
import Sigmaz.S03_Parser.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Function {

    private CompilerUnit mCompiler;

    public AST_Function(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai,String Visibilidade) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("FUNCTION");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_Visibilidade = AST_Corrente.criarBranch("VISIBILITY");
            AST_Visibilidade.setNome(Visibilidade);

            AST AST_Arguments = AST_Corrente.criarBranch("ARGUMENTS");




            AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
            mArgumentos.init(AST_Arguments);

            AST_TYPE mType = new AST_TYPE(mCompiler);
            mType.init(AST_Corrente);


            AST AST_BODY = AST_Corrente.criarBranch("BODY");
            AST_Corpo mCorpo = new AST_Corpo(mCompiler);
            mCorpo.init(AST_BODY);


        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma FUNCTION !", TokenC);
        }


    }

    public void init_Definicao(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("FUNCTION");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_Arguments = AST_Corrente.criarBranch("ARGUMENTS");


            AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
            mArgumentos.init_Tipagem(AST_Arguments);


            AST_TYPE mType = new AST_TYPE(mCompiler);
            mType.init(AST_Corrente);

            Token TokenP2 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA,"Era esperado PONTO E VIRGULA !");



        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma FUNCTION !", TokenC);
        }


    }




}