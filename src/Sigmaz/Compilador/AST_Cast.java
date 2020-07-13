package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Cast {

    private Compiler mCompiler;

    public AST_Cast(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(String eNomePacote,AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("CAST");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);


            if (eNomePacote.length()==0) {
                AST_Corrente.setValor( TokenC.getConteudo());
            } else{
                AST_Corrente.setValor(eNomePacote + "<>" + TokenC.getConteudo());
            }


            Token TokenP = mCompiler.getTokenAvanteStatus(TokenTipo.CHAVE_ABRE, "Era esperado abrir chaves");

            if (TokenP.getTipo() != TokenTipo.CHAVE_ABRE) {
                return;
            }

            boolean saiu = false;

            while (mCompiler.Continuar()) {
                Token TokenD = mCompiler.getTokenAvante();
                if (TokenD.getTipo() == TokenTipo.CHAVE_FECHA) {
                    saiu = true;
                    break;

                } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("getter")) {

                    getter(TokenC.getConteudo(), AST_Corrente, ASTPai);

                } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("setter")) {

                    setter(TokenC.getConteudo(), AST_Corrente, ASTPai);

                } else {
                    mCompiler.errarCompilacao("Comando Deconhecido : " + TokenD.getConteudo(), TokenD);
                }
            }

        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma CAST !", TokenC);
        }


    }

    public void getter(String eNome, AST ASTPai, AST ASTAvo) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("GETTER");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);


            Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado Dois Pontos");
            Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado uma Tipagem");
            Token TokenC4 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado -> ");

            AST_Corrente.setValor(TokenC3.getConteudo());


            AST_Corpo mCorpo = new AST_Corpo(mCompiler);
            mCorpo.init(AST_Corrente);


            // CRIAR UMA FUNC TIPO_CAST ( A : GETTER ) : TIPO_CAST
            AST AST_Func_Get = new AST("FUNCTION");

            AST_Func_Get.setNome(eNome);

            AST AST_Type = AST_Func_Get.criarBranch("TYPE");
            AST_Type.setValor("CONCRETE");
            AST_Type.setNome(eNome);

            AST AST_Visibilidade = AST_Func_Get.criarBranch("VISIBILITY");
            AST_Visibilidade.setNome("ALL");

            AST AST_Func_Args = AST_Func_Get.criarBranch("ARGUMENTS");
            AST AST_Func_Arg = AST_Func_Args.criarBranch("ARGUMENT");
            AST_Func_Arg.setNome("alfa");
            AST_Func_Arg.setValor("VALUE");

            criarTipagemConcreta(AST_Func_Arg,TokenC3.getConteudo());

            AST AST_Func_Body = AST_Func_Get.criarBranch("BODY");


            criarInvoke(AST_Func_Body,"alfa",TokenC3.getConteudo(),eNome);



            AST AST_Func_Ret = AST_Func_Body.criarBranch("RETURN");
            AST_Func_Ret.setNome("alfa");
            AST_Func_Ret.setValor("ID");

            ASTAvo.getASTS().add(AST_Func_Get);


        } else {
            mCompiler.errarCompilacao("Era esperado o nome da GETTER !", TokenC);
        }

    }

    private void criarTipagemConcreta(AST ASTPai, String eTipo) {

        AST ASTTipo = ASTPai.criarBranch("TYPE");
        ASTTipo.setNome(eTipo);
        ASTTipo.setValor("CONCRETE");

    }

    private void criarInvoke(AST ASTPai, String eSaida,String a1,String a2) {

        AST AST_Invoke = ASTPai.criarBranch("INVOKE");
        AST_Invoke.setNome("casting");
        AST_Invoke.setValor("cast_type");

        AST AST_Exit = AST_Invoke.criarBranch("EXIT");
        AST_Exit.setNome(eSaida);

        AST AST_Args = AST_Invoke.criarBranch("ARGUMENTS");

        AST AST_A1 = AST_Args.criarBranch("ARGUMENT");
        AST_A1.setNome(a1);
        AST_A1.setValor("Text");

        AST AST_A2 = AST_Args.criarBranch("ARGUMENT");
        AST_A2.setNome(a2);
        AST_A2.setValor("Text");
    }

    public void setter(String eNome, AST ASTPai, AST ASTAvo) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("SETTER");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);


            Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado Dois Pontos");
            Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado uma Tipagem");
            Token TokenC4 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado -> ");

            AST_Corrente.setValor(TokenC3.getConteudo());


            AST_Corpo mCorpo = new AST_Corpo(mCompiler);
            mCorpo.init(AST_Corrente);

            // CRIAR UMA FUNC SETTER ( A : TIPO_CAST ) : SETTER
            AST AST_Func_Get = new AST("FUNCTION");

            AST_Func_Get.setNome(TokenC3.getConteudo());

            AST AST_Type = AST_Func_Get.criarBranch("TYPE");
            AST_Type.setValor("CONCRETE");
            AST_Type.setNome(TokenC3.getConteudo());

            AST AST_Visibilidade = AST_Func_Get.criarBranch("VISIBILITY");
            AST_Visibilidade.setNome("ALL");

            AST AST_Func_Args = AST_Func_Get.criarBranch("ARGUMENTS");
            AST AST_Func_Arg = AST_Func_Args.criarBranch("ARGUMENT");
            AST_Func_Arg.setNome("alfa");
            AST_Func_Arg.setValor("VALUE");

            criarTipagemConcreta(AST_Func_Arg,eNome);


            AST AST_Func_Body = AST_Func_Get.criarBranch("BODY");



            criarInvoke(AST_Func_Body,"alfa",eNome,TokenC3.getConteudo());

            AST AST_Func_Ret = AST_Func_Body.criarBranch("RETURN");
            AST_Func_Ret.setNome("alfa");
            AST_Func_Ret.setValor("ID");

            ASTAvo.getASTS().add(AST_Func_Get);


        } else {
            mCompiler.errarCompilacao("Era esperado o nome da SETTER !", TokenC);
        }

    }

}