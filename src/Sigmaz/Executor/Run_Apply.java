package Sigmaz.Executor;

import Sigmaz.Utils.AST;

public class Run_Apply {

    private RunTime mRunTime;
    private Escopo mEscopo;


    public Run_Apply(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }


    public void init(AST ASTCorrente) {


        AST mSettable = ASTCorrente.getBranch("SETTABLE");
        AST mValue = ASTCorrente.getBranch("VALUE");

        if (mSettable.mesmoValor("ID")) {

            Run_Value mAST = new Run_Value(mRunTime, mEscopo);
            mAST.init(mValue, mEscopo.getDefinidoTipo(mSettable.getNome()));

            atribuir(mAST,mEscopo,mSettable.getNome());

        } else if (mSettable.getValor().contentEquals("STRUCT")) {

            Struct(mSettable,mValue);

        } else if (mSettable.getValor().contentEquals("STRUCT_EXTERN")) {


            Struct_Extern(mSettable,mValue);

        } else {
            mRunTime.getErros().add("Nao é possível realizar essa atribuicao !");
        }


    }


    public void Struct(AST mSettable, AST mValue){


        Item mItem = mEscopo.getItem(mSettable.getNome());

        //  System.out.println("Atribuindo para : " +mSettable.getNome() );
        if (mRunTime.getErros().size() > 0) {
            return;
        }

        Run_Struct mEscopoStruct = mRunTime.getRun_Struct(mItem.getValor());



        AST eInternal = mSettable.getBranch("INTERNAL");

        if (eInternal.mesmoValor("STRUCT_FUNCT")) {

            Item eItem = mEscopoStruct.init_Function(eInternal, mEscopo, "<<ANY>>");


            //   mRunTime.getErros().add("STRUCT : " + mEscopoStruct.getNome() + " -> " + eInternal.getNome() + " : " + eItem.getValor());

        } else if (eInternal.mesmoValor("STRUCT_ACT")) {

            mEscopoStruct.init_Action(eInternal, mEscopo);

        } else if (eInternal.mesmoValor("OBJECT")) {

            // mRunTime.getErros().add("STRUCT : " + mEscopoStruct.getNome());

            Item eItem = mEscopoStruct.init_Object(eInternal, mEscopo, "<<ANY>>");

            if (mRunTime.getErros().size() > 0) {
                return;
            }

            Run_Value mAST = new Run_Value(mRunTime, mEscopo);
            mAST.init(mValue, mEscopo.getDefinidoTipo(mSettable.getNome()));

            atribuir(mAST,mEscopoStruct.getEscopo(),eInternal.getNome());


        } else {

            mRunTime.getErros().add("AST_Value --> STRUCTURED VALUE !");

        }

    }

    public void Struct_Extern(AST mSettable, AST mValue){



        //  System.out.println("Atribuindo para : " +mSettable.getNome() );
        if (mRunTime.getErros().size() > 0) {
            return;
        }

        Run_Extern mEscopoStruct = mRunTime.getRun_Extern(mSettable.getNome());


        AST eInternal = mSettable.getBranch("INTERNAL");


        if (eInternal.mesmoValor("STRUCT_FUNCT")) {


            Item eItem = mEscopoStruct.init_Function_Extern(eInternal, mEscopo, "<<ANY>>");


            //   mRunTime.getErros().add("STRUCT : " + mEscopoStruct.getNome() + " -> " + eInternal.getNome() + " : " + eItem.getValor());

      //  } else if (eInternal.mesmoValor("STRUCT_ACT")) {

         //   mEscopoStruct.init_Action(eInternal, mEscopo);

        } else if (eInternal.mesmoValor("OBJECT")) {

            // mRunTime.getErros().add("STRUCT : " + mEscopoStruct.getNome());


            Item eItem = mEscopoStruct.init_ObjectExtern(eInternal, mEscopo, "<<ANY>>");


            if (mRunTime.getErros().size() > 0) {
                return;
            }


            Run_Value mAST = new Run_Value(mRunTime, mEscopo);
            mAST.init(mValue, "<<ANY>>");


            atribuir(mAST,mEscopoStruct.getEscopo(),eInternal.getNome());


        } else {

            mRunTime.getErros().add("AST_Value --> STRUCTURED VALUE !");

        }

    }






    public void atribuir( Run_Value mAST,Escopo gEscopo,String eVarNome){

      //  System.out.println("Aplicando " +eVarNome + " = " + mAST.getConteudo() + " ERROS -> " + mRunTime.getErros().size());

        // System.out.println("Aplicando " +eVarNome + " = " + mAST.getConteudo() );
      //  System.out.println("Estrutura " + mAST.getIsStruct() );

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (mAST.getIsNulo()) {
            gEscopo.anularDefinido(eVarNome);
        } else if (mAST.getIsStruct()) {

            String eTipo = gEscopo.getDefinidoTipo(eVarNome);

            if (eTipo.contentEquals(mAST.getRetornoTipo())) {
                gEscopo.setDefinidoStruct(eVarNome, mAST.getConteudo());
            }else{
                mRunTime.getErros().add("Retorno incompativel : Era esperado " + eTipo + " mas retornou " + mAST.getRetornoTipo());
            }

        } else {


            String eValor = mAST.getConteudo();
            String eTipo = gEscopo.getDefinidoTipo(eVarNome);

            if (eTipo.contentEquals(mAST.getRetornoTipo())) {

                gEscopo.setDefinido(eVarNome, eValor);

            } else {

                if (gEscopo.existeCast(eTipo)) {

                    Run_Cast mCast = new Run_Cast(mRunTime, gEscopo);
                    String res = mCast.realizarSetterCast(eTipo, mAST.getRetornoTipo(), mAST.getConteudo());

                    if (res == null) {
                        gEscopo.anularDefinido(eVarNome);
                    } else {
                        gEscopo.setDefinido(eVarNome, res);
                    }


                } else {
                    mRunTime.getErros().add("Retorno incompativel : Era esperado " + eTipo + " mas retornou " + mAST.getRetornoTipo());
                }


            }
        }

    }

}
