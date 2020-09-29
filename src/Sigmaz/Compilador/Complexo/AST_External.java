package Sigmaz.Compilador.Complexo;

import Sigmaz.Compilador.CompilerUnit;
import Sigmaz.Compilador.Complexo.AST_StructCorpo;
import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_External {

    private CompilerUnit mCompiler;

    public AST_External(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("STRUCT");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_Generico = AST_Corrente.criarBranch("GENERIC");
            AST_Generico.setNome("FALSE");

           AST AST_With =  AST_Corrente.criarBranch("WITH");
            AST_With.setValor("FALSE");

            AST AST_Model=  AST_Corrente.criarBranch("MODEL");
            AST_Model.setValor("FALSE");

            AST AST_Stages =  AST_Corrente.criarBranch("STAGES");
            AST_Stages.setValor("FALSE");

            AST mExtended = AST_Corrente.criarBranch("EXTENDED");
            mExtended.setNome("EXTERNAL");

            AST mBases = AST_Corrente.criarBranch("BASES");

            AST mRefers = AST_Corrente.criarBranch("REFERS");


            AST AST_Inits =  AST_Corrente.criarBranch("INITS");


            AST_StructCorpo mAST = new AST_StructCorpo(mCompiler);
            mAST.initExternal(AST_Corrente);


        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma STRUCT !", TokenC);
        }


    }


}