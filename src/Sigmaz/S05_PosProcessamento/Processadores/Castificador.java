package Sigmaz.S05_PosProcessamento.Processadores;

import Sigmaz.S05_PosProcessamento.PosProcessador;
import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class Castificador {

    private PosProcessador mPosProcessador;

    public Castificador(PosProcessador ePosProcessador) {

        mPosProcessador = ePosProcessador;

    }

    public void init(ArrayList<AST> mTodos) {

        mPosProcessador.mensagem("");
        mPosProcessador.mensagem(" ------------------ FASE CAST ----------------------- ");


        for (AST mAST : mTodos) {

            if (mAST.mesmoTipo("SIGMAZ")) {


                mPosProcessador.mensagem("");

                for (AST ePacote : mAST.getASTS()) {
                    if (ePacote.mesmoTipo("PACKAGE")) {

                        processar(ePacote);

                    }
                }

                processar(mAST);


            }
        }

    }

    public void processar(AST eASTPai) {


        ArrayList<AST> eCasts = new ArrayList<AST>();


        for (AST mAST : eASTPai.getASTS()) {

            if (mAST.mesmoTipo("CAST")) {

                eCasts.add(mAST);

            }
        }

        for (AST mAST : eCasts) {

            mPosProcessador.mensagem("\t - CAST : " + mAST.getNome());

            for (AST gAST : mAST.getASTS()) {

                if (gAST.mesmoTipo("GETTER")) {

                    processar_Getter(mAST.getNome(), gAST, eASTPai);

                } else if (gAST.mesmoTipo("SETTER")) {

                    processar_Setter(mAST.getNome(), gAST, eASTPai);

                }

            }
        }

    }

    private void criarTipagemConcreta(AST ASTPai, String eTipo) {

        AST ASTTipo = ASTPai.criarBranch("TYPE");
        ASTTipo.setNome(eTipo);
        ASTTipo.setValor("CONCRETE");

    }

    public void processar_Getter(String eCast, AST eCastGetter, AST ASTAvo) {


        mPosProcessador.mensagem("\t\t - GETTER " + eCast + " :: " + eCastGetter.getValor());

        String eNome = eCast;
        String eNomeVar = eCastGetter.getNome();
        String eTipoVar = eCastGetter.getValor();


        for (AST iAST : eCastGetter.getASTS()) {

            if (iAST.mesmoTipo("RETURN")) {
                mPosProcessador.errar("Cast " + eCast + " Getter " + eTipoVar + " : Nao pode possuir RETURN ");
                return;
            }

        }

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

        criarTipagemConcreta(AST_Func_Arg, eTipoVar);

        AST AST_Func_Body = AST_Func_Get.criarBranch("BODY");


        for (AST iAST : eCastGetter.getASTS()) {
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


        AST AST_MOVEDATA = AST_Func_Body.criarBranch("MOVE_DATA");
        AST_MOVEDATA.setNome(aNome);
        AST_MOVEDATA.setValor(eNomeVar);


        AST AST_Func_Ret = AST_Func_Body.criarBranch("RETURN");

        AST AST_Func_Ret_Value = AST_Func_Ret.criarBranch("VALUE");

        AST_Func_Ret_Value.setNome(aNome);
        AST_Func_Ret_Value.setValor("ID");

        ASTAvo.getASTS().add(AST_Func_Get);

        // System.out.println(AST_Func_Get.ImprimirArvoreDeInstrucoes());


        // Colocar Retorno no Getter;

        AST mGetter_Return = eCastGetter.criarBranch("RETURN");
        AST mGetter_ReturnValue = mGetter_Return.criarBranch("VALUE");
        mGetter_ReturnValue.setNome(eNome);
        mGetter_ReturnValue.setValor("FUNCT");
        AST mGetter_ReturnArgs = mGetter_ReturnValue.criarBranch("ARGUMENTS");
        AST mGetter_ReturnArg = mGetter_ReturnArgs.criarBranch("ARGUMENT");
        mGetter_ReturnArg.setNome(eNomeVar);
        mGetter_ReturnArg.setValor("ID");


        //   System.out.println(AST_Corrente.ImprimirArvoreDeInstrucoes());


    }

    public void processar_Setter(String eCast, AST eCastSetter, AST ASTAvo) {


        mPosProcessador.mensagem("\t\t - SETTER " + eCast + " :: " + eCastSetter.getValor());

        String eNome = eCast;
        String eNomeVar = eCastSetter.getNome();
        String eTipoVar = eCastSetter.getValor();


        String g = "__CAST__" + eTipoVar + "__";

        for (AST iAST : eCastSetter.getASTS()) {

            if (iAST.mesmoTipo("RETURN")) {
                mPosProcessador.errar("Cast " + eCast + " Setter " + eTipoVar + " : Nao pode possuir RETURN ");
                return;
            }

        }


        // CRIAR UMA FUNC SETTER ( A : TIPO_CAST ) : SETTER
        AST AST_Func_Get = new AST("FUNCTION");

        AST_Func_Get.setNome(eTipoVar);

        AST AST_Type = AST_Func_Get.criarBranch("TYPE");
        AST_Type.setValor("CONCRETE");
        AST_Type.setNome(eTipoVar);

        AST AST_Visibilidade = AST_Func_Get.criarBranch("VISIBILITY");
        AST_Visibilidade.setNome("ALL");

        AST AST_Func_Args = AST_Func_Get.criarBranch("ARGUMENTS");
        AST AST_Func_Arg = AST_Func_Args.criarBranch("ARGUMENT");
        AST_Func_Arg.setNome("alfa");
        AST_Func_Arg.setValor("VALUE");

        criarTipagemConcreta(AST_Func_Arg, eNome);


        AST AST_Func_Body = AST_Func_Get.criarBranch("BODY");


        AST AST_beta = AST_Func_Body.criarBranch("DEF");
        AST_beta.setNome(eNomeVar);
        AST AST_betatype = AST_beta.criarBranch("TYPE");
        AST_betatype.setNome(eTipoVar);
        AST_betatype.setValor("CONCRETE");

        AST AST_betavalue = AST_beta.criarBranch("VALUE");
        AST_betavalue.setNome("");
        AST_betavalue.setValor("NULL");


        //AST AST_Invoke = AST_Func_Body.criarBranch("INVOKE");
     //   AST_Invoke.setNome("casting");
     //   AST_Invoke.setValor("move_content");

     //   AST AST_Invoke_Exit = AST_Invoke.criarBranch("EXIT");
     //   AST_Invoke_Exit.setNome(eNomeVar);
     //   AST AST_Invoke_Arguments = AST_Invoke.criarBranch("ARGUMENTS");

      //  AST AST_Invoke_Argument_1 = AST_Invoke_Arguments.criarBranch("ARGUMENT");
      //  AST_Invoke_Argument_1.setNome("alfa");
      //  AST_Invoke_Argument_1.setValor("ID");


        AST AST_MOVEDATA = AST_Func_Body.criarBranch("MOVE_DATA");
        AST_MOVEDATA.setNome(eNomeVar);
        AST_MOVEDATA.setValor("alfa");


        for (AST iAST : eCastSetter.getASTS()) {
            AST_Func_Body.getASTS().add(iAST);
        }


        AST AST_Func_Ret = AST_Func_Body.criarBranch("RETURN");

        AST AST_Func_Ret_Value = AST_Func_Ret.criarBranch("VALUE");

        AST_Func_Ret_Value.setNome(eNomeVar);
        AST_Func_Ret_Value.setValor("ID");


        ASTAvo.getASTS().add(AST_Func_Get);


        AST Setter_Ret = AST_Func_Body.criarBranch("RETURN");

        AST Setter_Ret_Value = Setter_Ret.criarBranch("VALUE");

        Setter_Ret_Value.setNome(eNomeVar);
        Setter_Ret_Value.setValor("ID");


        eCastSetter.getASTS().add(Setter_Ret);

        //   System.out.println(eCastSetter.ImprimirArvoreDeInstrucoes());

        //  System.out.println(AST_Func_Get.ImprimirArvoreDeInstrucoes());

    }

}
