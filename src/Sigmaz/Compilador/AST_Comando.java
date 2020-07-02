package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Comando {

    private Compiler mCompiler;

    public AST_Comando(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    private AST mGuardeASTSub;


    public void init(AST ASTPai) {

        mGuardeASTSub = null;

        AST ASTCorrente = ASTPai.criarBranch("EXECUTE");


        Token TokenD = mCompiler.getTokenCorrente();
        if (TokenD.getTipo() == TokenTipo.NUMERO) {

            ASTCorrente.setNome(TokenD.getConteudo());
            ASTCorrente.setValor("Num");

            Token P2 = mCompiler.getTokenAvante();

            if (P2.getTipo() == TokenTipo.PONTOVIRGULA) {

            } else {
                mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P2.getConteudo(),P2);
            }
        } else if (TokenD.getTipo() == TokenTipo.TEXTO) {


            ASTCorrente.setNome(TokenD.getConteudo());
            ASTCorrente.setValor("Text");

            Token P2 = mCompiler.getTokenAvante();

            if (P2.getTipo() == TokenTipo.PONTOVIRGULA) {

            } else {
                mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P2.getConteudo(),P2);
            }

        } else if (TokenD.getTipo() == TokenTipo.ID) {


            ASTCorrente.setNome(TokenD.getConteudo());
            ASTCorrente.setValor("ID");

            Token P2 = mCompiler.getTokenAvante();

            if (P2.getTipo() == TokenTipo.IGUAL) {

                AST ASTDireita = ASTCorrente.copiar();
                ASTDireita.setTipo("SETTABLE");

                ASTCorrente.getASTS().clear();
                ASTCorrente.setNome("");
                ASTCorrente.setValor("");

                ASTCorrente.setTipo("APPLY");
                ASTCorrente.getASTS().add(ASTDireita);

                AST ASTC = ASTCorrente.criarBranch("VALUE");

                AST_Value mAST = new AST_Value(mCompiler);

                mAST.init(ASTC);

            } else if (P2.getTipo() == TokenTipo.PONTO) {

                ASTCorrente.setValor("STRUCT");

                AST_Value mA = new AST_Value(mCompiler) ;
                mA.ReceberNovoEscopo(ASTCorrente);

                Token P3 = mCompiler.getTokenAvante();

                if (P3.getTipo() == TokenTipo.PONTOVIRGULA) {

                    if (mGuardeASTSub != null) {
                        mGuardeASTSub.setValor("STRUCT_ACT");
                    }

                } else if (P3.getTipo() == TokenTipo.IGUAL) {

                    AST ASTDireita = ASTCorrente.copiar();
                    ASTDireita.setTipo("SETTABLE");

                    ASTCorrente.getASTS().clear();
                    ASTCorrente.setNome("");
                    ASTCorrente.setValor("");

                    ASTCorrente.setTipo("APPLY");
                    ASTCorrente.getASTS().add(ASTDireita);

                    AST ASTC = ASTCorrente.criarBranch("VALUE");

                    AST_Value mAST = new AST_Value(mCompiler);

                    mAST.init(ASTC);

                    ASTDireita.setValor("STRUCT");

                } else {
                    mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P3.getConteudo(), P3);
                }
            } else if (P2.getTipo() == TokenTipo.SETA) {

                ASTCorrente.setValor("STRUCT_EXTERN");


                AST_Value mA = new AST_Value(mCompiler) ;
                mA.ReceberNovoEscopo(ASTCorrente);

                Token P3 = mCompiler.getTokenAvante();

                if (P3.getTipo() == TokenTipo.PONTOVIRGULA) {

                    if (mGuardeASTSub != null) {
                        mGuardeASTSub.setValor("STRUCT_ACT");
                    }

                } else if (P3.getTipo() == TokenTipo.IGUAL) {

                    AST ASTDireita = ASTCorrente.copiar();
                    ASTDireita.setTipo("SETTABLE");

                    ASTCorrente.getASTS().clear();
                    ASTCorrente.setNome("");
                    ASTCorrente.setValor("");

                    ASTCorrente.setTipo("APPLY");
                    ASTCorrente.getASTS().add(ASTDireita);

                    AST ASTC = ASTCorrente.criarBranch("VALUE");

                    AST_Value mAST = new AST_Value(mCompiler);

                    mAST.init(ASTC);

                    ASTDireita.setValor("STRUCT_EXTERN");

                } else {
                    mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P3.getConteudo(), P3);
                }



            } else if (P2.getTipo() == TokenTipo.PARENTESES_ABRE) {

                ASTCorrente.setValor("FUNCT");

                AST_Value_Argument mAVA = new AST_Value_Argument(mCompiler);
                mAVA.ReceberArgumentos(ASTCorrente);

                Token P3 = mCompiler.getTokenAvante();

                if (P3.getTipo() == TokenTipo.PONTOVIRGULA) {



                } else if (P3.getTipo() == TokenTipo.IGUAL) {

                    AST ASTDireita = ASTCorrente.copiar();
                    ASTDireita.setTipo("SETTABLE");

                    ASTCorrente.getASTS().clear();
                    ASTCorrente.setNome("");
                    ASTCorrente.setValor("");

                    ASTCorrente.setTipo("APPLY");
                    ASTCorrente.getASTS().add(ASTDireita);

                    AST ASTC = ASTCorrente.criarBranch("VALUE");

                    AST_Value mAST = new AST_Value(mCompiler);

                    mAST.init(ASTC);

                } else {
                    mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P3.getConteudo(), P3);
                }

            } else {
                mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P2.getConteudo(), P2);
            }

        } else {
            mCompiler.errarCompilacao("Era esperado um valor : " + TokenD.getConteudo(), TokenD);

        }


    }





}
