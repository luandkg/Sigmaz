package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Cast {

    private Compiler mCompiler;

    public AST_Cast(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("CAST");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);


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

                }else if (TokenD.getTipo()==TokenTipo.ID && TokenD.mesmoConteudo("getter")){

                    getter(TokenC.getConteudo(),AST_Corrente,ASTPai);

                }else if (TokenD.getTipo()==TokenTipo.ID && TokenD.mesmoConteudo("setter")){

                    setter(TokenC.getConteudo(),AST_Corrente,ASTPai);

                } else {
                    mCompiler.errarCompilacao("Comando Deconhecido : " + TokenD.getConteudo(), TokenD.getInicio());
                }
            }

        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma CAST !", TokenC.getInicio());
        }


    }

    public void getter(String eNome,AST ASTPai,AST ASTAvo){

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
            AST_Func_Get.setValor(eNome);

            AST AST_Visibilidade = AST_Func_Get.criarBranch("VISIBILITY");
            AST_Visibilidade.setNome("ALL");

            AST AST_Func_Args =AST_Func_Get.criarBranch("ARGUMENTS");
            AST AST_Func_Arg =AST_Func_Args.criarBranch("ARGUMENT");
            AST_Func_Arg.setNome("alfa");
            AST_Func_Arg.setValor(TokenC3.getConteudo());

            AST AST_Func_Body =AST_Func_Get.criarBranch("BODY");
            AST AST_Func_Def =AST_Func_Body.criarBranch("DEF");
            AST_Func_Def.setNome("beta");
            AST_Func_Def.setValor(eNome);

            AST_Func_Def.criarBranch("GENERIC").setNome("FALSE");


            AST AST_Func_Val =AST_Func_Def.criarBranch("VALUE");
            AST_Func_Val.setNome("alfa");
            AST_Func_Val.setValor("ID");

            AST AST_Func_Ret =AST_Func_Body.criarBranch("RETURN");
            AST_Func_Ret.setNome("beta");
            AST_Func_Ret.setValor("ID");

            ASTAvo.getASTS().add(AST_Func_Get);



        } else {
            mCompiler.errarCompilacao("Era esperado o nome da GETTER !", TokenC.getInicio());
        }

    }

    public void setter(String eNome,AST ASTPai, AST ASTAvo){

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
            AST_Func_Get.setValor(TokenC3.getConteudo());

            AST AST_Visibilidade = AST_Func_Get.criarBranch("VISIBILITY");
            AST_Visibilidade.setNome("ALL");

            AST AST_Func_Args =AST_Func_Get.criarBranch("ARGUMENTS");
            AST AST_Func_Arg =AST_Func_Args.criarBranch("ARGUMENT");
            AST_Func_Arg.setNome("alfa");
            AST_Func_Arg.setValor(eNome);

            AST AST_Func_Body =AST_Func_Get.criarBranch("BODY");
            AST AST_Func_Def =AST_Func_Body.criarBranch("DEF");
            AST_Func_Def.setNome("beta");
            AST_Func_Def.setValor(TokenC3.getConteudo());

            AST_Func_Def.criarBranch("GENERIC").setNome("FALSE");

            AST AST_Func_Val =AST_Func_Def.criarBranch("VALUE");
            AST_Func_Val.setNome("alfa");
            AST_Func_Val.setValor("ID");

            AST AST_Func_Ret =AST_Func_Body.criarBranch("RETURN");
            AST_Func_Ret.setNome("beta");
            AST_Func_Ret.setValor("ID");

            ASTAvo.getASTS().add(AST_Func_Get);


        } else {
            mCompiler.errarCompilacao("Era esperado o nome da SETTER !", TokenC.getInicio());
        }

    }

}