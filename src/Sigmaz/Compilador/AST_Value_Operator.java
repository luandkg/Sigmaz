package Sigmaz.Compilador;

import Sigmaz.Utils.AST;

public class AST_Value_Operator {

    private CompilerUnit mCompiler;

    public AST_Value_Operator(CompilerUnit eCompiler) {

        mCompiler = eCompiler;

    }


    public void final_argumento(String eTipo,AST ASTPai) {

        AST ASTEsquerda = ASTPai.copiar();
        ASTEsquerda.setTipo("LEFT");

        ASTPai.limpar();

        AST ASTDireita = new AST("RIGHT");


        ASTPai.setTipo("VALUE");
        ASTPai.criarBranch("MODE").setNome(eTipo);
        ASTPai.setValor("OPERATOR");

        ASTPai.getASTS().add(ASTEsquerda);
        ASTPai.getASTS().add(ASTDireita);

        AST_Value mAST = new AST_Value(mCompiler);
        mAST.initUltimoArgumento(ASTDireita);

        ASTDireita.setTipo("RIGHT");

    }

    public void final_argumento_parenteses(String eTipo,AST ASTPai) {

        AST ASTEsquerda = ASTPai.copiar();
        ASTEsquerda.setTipo("LEFT");

        ASTPai.limpar();

        AST ASTDireita = new AST("RIGHT");


        ASTPai.setTipo("VALUE");
        ASTPai.criarBranch("MODE").setNome(eTipo);
        ASTPai.setValor("OPERATOR");

        ASTPai.getASTS().add(ASTEsquerda);
        ASTPai.getASTS().add(ASTDireita);

        AST_Value mAST = new AST_Value(mCompiler);
        mAST.initUltimoArgumentoParenteses(ASTDireita);

        ASTDireita.setTipo("RIGHT");

    }

}
