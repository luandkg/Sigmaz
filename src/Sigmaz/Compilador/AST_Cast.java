package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Cast {

    private CompilerUnit mCompiler;

    public AST_Cast(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(String eNomePacote, AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("CAST");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);


            if (eNomePacote.length() == 0) {
                AST_Corrente.setValor(TokenC.getConteudo());
            } else {
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

                } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("default")) {

                    defaultx(AST_Corrente, ASTPai);

                } else {
                    mCompiler.errarCompilacao("Comando Deconhecido : " + TokenD.getConteudo(), TokenD);
                }
            }

            if (!saiu) {
                mCompiler.errarCompilacao("Era esperado fechar parenteses !", TokenC);
            }

        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma CAST !", TokenC);
        }


    }


    private void criarTipagemConcreta(AST ASTPai, String eTipo) {

        AST ASTTipo = ASTPai.criarBranch("TYPE");
        ASTTipo.setNome(eTipo);
        ASTTipo.setValor("CONCRETE");

    }


    public void getter(String eNome, AST ASTPai, AST ASTAvo) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("GETTER");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);

            String eNomeVar = TokenC.getConteudo();


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
            AST_Func_Arg.setNome(eNomeVar);
            AST_Func_Arg.setValor("VALUE");

            criarTipagemConcreta(AST_Func_Arg, TokenC3.getConteudo());

            AST AST_Func_Body = AST_Func_Get.criarBranch("BODY");


            for (AST iAST : AST_Corrente.getASTS()) {
                AST_Func_Body.getASTS().add(iAST);
            }

            String aNome = "__CAST__" + eNome + "__";

            AST AST_beta = AST_Func_Body.criarBranch("DEF");
            AST_beta.setNome(aNome);
            AST AST_betatype = AST_beta.criarBranch("TYPE");
            AST_betatype.setNome(eNome);
            AST_betatype.setValor("CONCRETE");

            AST AST_betavalue = AST_beta.criarBranch("VALUE");
            AST_betavalue.setNome("");
            AST_betavalue.setValor("NULL");


            AST AST_Invoke = AST_Func_Body.criarBranch("INVOKE");
            AST_Invoke.setNome("casting");
            AST_Invoke.setValor("move_content");

            AST AST_Invoke_Exit = AST_Invoke.criarBranch("EXIT");
            AST_Invoke_Exit.setNome(aNome);
            AST AST_Invoke_Arguments = AST_Invoke.criarBranch("ARGUMENTS");

            AST AST_Invoke_Argument_1 = AST_Invoke_Arguments.criarBranch("ARGUMENT");
            AST_Invoke_Argument_1.setNome(eNomeVar);
            AST_Invoke_Argument_1.setValor("ID");


            AST AST_Func_Ret = AST_Func_Body.criarBranch("RETURN");

            AST AST_Func_Ret_Value = AST_Func_Ret.criarBranch("VALUE");

            AST_Func_Ret_Value.setNome(aNome);
            AST_Func_Ret_Value.setValor("ID");

            ASTAvo.getASTS().add(AST_Func_Get);

           // System.out.println(AST_Func_Get.ImprimirArvoreDeInstrucoes());


            // Colocar Retorno no Getter;

            AST mGetter_Return = AST_Corrente.criarBranch("RETURN");
            AST mGetter_ReturnValue = mGetter_Return.criarBranch("VALUE");
            mGetter_ReturnValue.setNome(eNome);
            mGetter_ReturnValue.setValor("FUNCT");
            AST mGetter_ReturnArgs = mGetter_ReturnValue.criarBranch("ARGUMENTS");
            AST mGetter_ReturnArg = mGetter_ReturnArgs.criarBranch("ARGUMENT");
            mGetter_ReturnArg.setNome(eNomeVar);
            mGetter_ReturnArg.setValor("ID");


         //   System.out.println(AST_Corrente.ImprimirArvoreDeInstrucoes());

        } else {
            mCompiler.errarCompilacao("Era esperado o nome da GETTER !", TokenC);
        }

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

            String aTipo = TokenC3.getConteudo();
            AST_Corrente.setValor(aTipo);


            String eNomeVar = TokenC.getConteudo();
            String aNome = TokenC.getConteudo();
            String g = "__CAST__" + aTipo + "__";


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

            criarTipagemConcreta(AST_Func_Arg, eNome);


            AST AST_Func_Body = AST_Func_Get.criarBranch("BODY");


            AST AST_beta = AST_Func_Body.criarBranch("DEF");
            AST_beta.setNome(aNome);
            AST AST_betatype = AST_beta.criarBranch("TYPE");
            AST_betatype.setNome(aTipo);
            AST_betatype.setValor("CONCRETE");

            AST AST_betavalue = AST_beta.criarBranch("VALUE");
            AST_betavalue.setNome("");
            AST_betavalue.setValor("NULL");


            AST AST_Invoke = AST_Func_Body.criarBranch("INVOKE");
            AST_Invoke.setNome("casting");
            AST_Invoke.setValor("move_content");

            AST AST_Invoke_Exit = AST_Invoke.criarBranch("EXIT");
            AST_Invoke_Exit.setNome(aNome);
            AST AST_Invoke_Arguments = AST_Invoke.criarBranch("ARGUMENTS");

            AST AST_Invoke_Argument_1 = AST_Invoke_Arguments.criarBranch("ARGUMENT");
            AST_Invoke_Argument_1.setNome("alfa");
            AST_Invoke_Argument_1.setValor("ID");


            for (AST iAST : AST_Corrente.getASTS()) {
                AST_Func_Body.getASTS().add(iAST);
            }


            AST AST_Func_Ret = AST_Func_Body.criarBranch("RETURN");

            AST AST_Func_Ret_Value = AST_Func_Ret.criarBranch("VALUE");

            AST_Func_Ret_Value.setNome(aNome);
            AST_Func_Ret_Value.setValor("ID");


            ASTAvo.getASTS().add(AST_Func_Get);


            AST Setter_Ret = AST_Func_Body.criarBranch("RETURN");

            AST  Setter_Ret_Value = Setter_Ret.criarBranch("VALUE");

            Setter_Ret_Value.setNome(aNome);
            Setter_Ret_Value.setValor("ID");


            AST_Corrente.getASTS().add(Setter_Ret);



        //    System.out.println(AST_Corrente.ImprimirArvoreDeInstrucoes());




       //     System.out.println(AST_Func_Get.ImprimirArvoreDeInstrucoes());

        } else {
            mCompiler.errarCompilacao("Era esperado o nome da SETTER !", TokenC);
        }

    }

    public void defaultx(AST ASTPai, AST ASTAvo) {


        AST AST_Corrente = new AST("DEFAULT");
        ASTPai.getASTS().add(AST_Corrente);


        Token TokenC4 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado -> ");


        AST_Corpo mCorpo = new AST_Corpo(mCompiler);
        mCorpo.init(AST_Corrente);


    }

}