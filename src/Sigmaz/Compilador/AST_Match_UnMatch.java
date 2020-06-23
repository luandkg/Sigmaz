package Sigmaz.Compilador;

import Sigmaz.Utils.AST;

public class AST_Match_UnMatch {

    private Compiler mCompiler;

    public AST_Match_UnMatch(Compiler eCompiler) {

        mCompiler = eCompiler;

    }

    public void match_final_argumentando(AST ASTPai) {

        AST ASTEsquerda = ASTPai.copiar();
        ASTEsquerda.setTipo("LEFT");

        ASTPai.limpar();

        AST ASTDireita = new AST("RIGHT");


        ASTPai.setTipo("VALUE");
        ASTPai.criarBranch("MODE").setNome("MATCH");
        ASTPai.setValor("COMPARATOR");

        ASTPai.getASTS().add(ASTEsquerda);
        ASTPai.getASTS().add(ASTDireita);

        AST_Value mAST = new AST_Value(mCompiler);
        mAST.initArgumentandoUltimo(ASTDireita);

        ASTDireita.setTipo("RIGHT");

        //  arrumar(ASTEsquerda,ASTDireita);

    }

    public void unmatch_final_argumentando(AST ASTPai) {

        AST ASTEsquerda = ASTPai.copiar();
        ASTEsquerda.setTipo("LEFT");

        ASTPai.limpar();

        AST ASTDireita = new AST("RIGHT");


        ASTPai.setTipo("VALUE");
        ASTPai.criarBranch("MODE").setNome("MISMATCH");
        ASTPai.setValor("COMPARATOR");

        ASTPai.getASTS().add(ASTEsquerda);
        ASTPai.getASTS().add(ASTDireita);

        AST_Value mAST = new AST_Value(mCompiler);
        mAST.initArgumentandoUltimo(ASTDireita);

        ASTDireita.setTipo("RIGHT");

        //  arrumar(ASTEsquerda,ASTDireita);

    }

    public void match_final_argumento(AST ASTPai) {

        AST ASTEsquerda = ASTPai.copiar();
        ASTEsquerda.setTipo("LEFT");

        ASTPai.limpar();

        AST ASTDireita = new AST("RIGHT");


        ASTPai.setTipo("VALUE");
        ASTPai.criarBranch("MODE").setNome("MATCH");
        ASTPai.setValor("COMPARATOR");

        ASTPai.getASTS().add(ASTEsquerda);
        ASTPai.getASTS().add(ASTDireita);

        AST_Value mAST = new AST_Value(mCompiler);


        mAST.initUltimoArgumento(ASTDireita);

        ASTDireita.setTipo("RIGHT");

        //  arrumar(ASTEsquerda,ASTDireita);

    }


    public void unmatch_final_argumento(AST ASTPai) {

        AST ASTEsquerda = ASTPai.copiar();
        ASTEsquerda.setTipo("LEFT");

        ASTPai.limpar();

        AST ASTDireita = new AST("RIGHT");


        ASTPai.setTipo("VALUE");
        ASTPai.criarBranch("MODE").setNome("MISMATCH");
        ASTPai.setValor("COMPARATOR");

        ASTPai.getASTS().add(ASTEsquerda);
        ASTPai.getASTS().add(ASTDireita);

        AST_Value mAST = new AST_Value(mCompiler);
        mAST.initUltimoArgumento(ASTDireita);

        ASTDireita.setTipo("RIGHT");

        //  arrumar(ASTEsquerda,ASTDireita);

    }


}
