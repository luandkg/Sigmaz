package Sigmaz.S06_Executor.Runners;

import Sigmaz.S06_Executor.*;
import Sigmaz.S06_Executor.Indexador.Index_Action;
import Sigmaz.S06_Executor.Indexador.Index_Argument;
import Sigmaz.S06_Executor.Indexador.Index_Function;

import java.util.ArrayList;
import Sigmaz.S00_Utilitarios.AST;

public class Run_Arguments {


    public ArrayList<Item> preparar_argumentos(RunTime mRunTime, Escopo mBuscadorDeVariaveis, AST ASTCorrente) {

        ArrayList<Item> mArgumentos = new ArrayList<Item>();

        for (AST a : ASTCorrente.getASTS()) {
            if (a.mesmoTipo("ARGUMENT")) {


                Run_Value mAST = new Run_Value(mRunTime, mBuscadorDeVariaveis);
                mAST.init(a, "<<ANY>>");

             //   System.out.println("\t - Iniciando Parametro : " + a.ImprimirArvoreDeInstrucoes());

                //  Run_Valoramento mRun_Valoramento = new Run_Valoramento(mRunTime, mBuscadorDeVariaveis);
                // Run_Value mAST = mRun_Valoramento.init(a.getNome(), a, "<<ANY>>");


                //System.out.println("\t - Recebendo Parametro  " + a.getValor() + " : " + " -> " + mAST.getRetornoTipo() + " :: " + mAST.getIsStruct());

                // if (mAST.getIsReferenciavel()) {
                //      System.out.println("\t Ref Anterior : " + mAST.getReferencia().getNome());
                //  }



                //System.out.println("Argumento : " + mAST.getRetornoTipo());

                Item v = new Item("");
                v.setModo(0);
                v.setNulo(mAST.getIsNulo());
                v.setPrimitivo(mAST.getIsPrimitivo());
                v.setIsEstrutura(mAST.getIsStruct());
                v.setTipo(mAST.getRetornoTipo());
                v.setValor(mAST.getConteudo(),mRunTime,mBuscadorDeVariaveis);

                if (mAST.getIsReferenciavel()) {
                    v.setIsReferenciavel(true);
                    v.setReferencia(mAST.getReferencia());
                }

                mArgumentos.add(v);


            }

        }

        return mArgumentos;

    }


    public String getTipagem(ArrayList<Item> mArgumentos){


        String mTipagem = "";


        int i = 0;
        int o = mArgumentos.size();
        for (Item ie : mArgumentos) {

            i += 1;

            if (i < o) {
                mTipagem += ie.getTipo() + " , ";
            } else {
                mTipagem += ie.getTipo() + " ";
            }


        }

       return " ( " + mTipagem + " ) ";

    }


    public void passarParametros(Escopo mEscopoInterno, ArrayList<Index_Argument> eParametros, ArrayList<Item> mArgumentos) {

        if (eParametros.size() > 0) {

            for (int argC = 0; argC < eParametros.size(); argC++) {

                // System.out.println("\t - Passando Parametro : " + eParametrosNomes.get(argC) + " -> " + mArgumentos.get(argC).getTipo() + " :: " + mArgumentos.get(argC).getValor());

                // System.out.println("Passando : " + eParametrosNomes.get(argC) + " Est : " + mArgumentos.get(argC).getIsEstrutura() + " -> " +mArgumentos.get(argC).getNulo());

                //  System.out.println("\t - Passando Parametro : " + eParametrosNomes.get(argC) + " " + eParametrosModos.get(argC) + " -> " + mArgumentos.get(argC).getTipo() + " :: " + mArgumentos.get(argC).getValor());

                if (eParametros.get(argC).getModo().contentEquals("VALUE")) {
                    mEscopoInterno.passarParametroByValue(eParametros.get(argC).getNome(), mArgumentos.get(argC));
                } else if (eParametros.get(argC).getModo().contentEquals("REF")) {
                    mEscopoInterno.passarParametroByRef(eParametros.get(argC).getNome(), mArgumentos.get(argC));
                } else {
                    mEscopoInterno.getRunTime().getErros().add("Passagem de parametro desconhcida : " + eParametros.get(argC).getModo());
                    return;
                }


            }

        }

    }

    public Item executar_Function(RunTime mRunTime, Escopo mStructEscopo, Index_Function mFunction, ArrayList<Item> mArgumentos, String eReturne ) {


        Item Saida = new Item("");

        Saida.setTipo(mFunction.getTipo());

         //  System.out.println("EXECUTAR Function Indo -> " + mFunction.getNome() + " : " + mFunction.getTipo());


        Escopo mEscopoInterno = new Escopo(mRunTime, mStructEscopo);
        mEscopoInterno.setNome(mFunction.getNome());


        for (String eRefer : mStructEscopo.getRefers()) {


          //  System.out.println("REFER " + eRefer);

            mEscopoInterno.adicionarRefer(eRefer);

        }


        passarParametros(mEscopoInterno, mFunction.getArgumentos(), mArgumentos);

        AST mASTBody = mFunction.getPonteiro().getBranch("BODY");

        Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
        mAST.init(mASTBody);

        if (mRunTime.getErros().size() > 0) {
            return null;
        }

      //  if (mFunction.getTipo().contentEquals(mAST.getRetorno().getTipo())){
      //  }else{
          //  System.out.println("Voltando Tipo : " + mAST.getRetorno().getTipo());
           // System.out.println("Esperava Tipo : " + mFunction.getTipo());
        //    mRunTime.errar("Run_Arguments","Era esperado retornar " + mFunction.getTipo() + " mas retornou " + mAST.getRetorno().getTipo());
        //    mRunTime.errar("RA",mFunction.getPonteiro().getImpressao());
       // }

        Saida = mAST.getRetorno();

        if (mRunTime.getErros().size() > 0) {
            return null;
        }

        if (Saida.getNulo()) {
            Saida.setTipo(eReturne);
        }

       //   System.out.println("EXECUTAR Function Retorando -> " + mFunction.getNome() + " " + Saida.getValor(mRunTime,mEscopoInterno) + " :: " +Saida.getTipo() );


        if (mRunTime.getErros().size() > 0) {
            return null;
        }


        if (Saida.getTipo().contentEquals("bool")) {
            Saida.setPrimitivo(true);
        } else if (Saida.getTipo().contentEquals("num")) {
            Saida.setPrimitivo(true);
        } else if (Saida.getTipo().contentEquals("string")) {
            Saida.setPrimitivo(true);
        }


        // System.out.println("  Retornando -> " + mAST.getConteudo());

        return Saida;
    }

    public Item executar_ActionComRetorno(RunTime mRunTime, Escopo mStructEscopo, Index_Action mFunction, ArrayList<Item> mArgumentos, String eReturne ) {


        Item Saida = new Item("");

       // Saida.setTipo(mFunction.getTipo());

        //   System.out.println(" EXECUTAR Function " + mFunction.getNome() + " : " + mFunction.getTipo());


        Escopo mEscopoInterno = new Escopo(mRunTime, mStructEscopo);
        mEscopoInterno.setNome(mFunction.getNome());


        for (String eRefer : mStructEscopo.getRefers()) {


            //  System.out.println("REFER " + eRefer);

            mEscopoInterno.adicionarRefer(eRefer);

        }


        passarParametros(mEscopoInterno, mFunction.getArgumentos(), mArgumentos);

        AST mASTBody = mFunction.getPonteiro().getBranch("BODY");

        Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
        mAST.init(mASTBody);

       // Saida.setNulo(mAST.getIsNulo());
       // Saida.setPrimitivo(mAST.getIsPrimitivo());
     //   Saida.setValor(mAST.getConteudo(),mRunTime,mEscopoInterno);
       // Saida.setTipo(mAST.getRetornoTipo());

        Saida = mAST.getRetorno();

        if (Saida.getNulo()) {
            Saida.setTipo(eReturne);
        }

        //  System.out.println( mFunction.getNome() + "  Retornando -> " + mAST.getConteudo() + " :: " +mAST.getRetornoTipo() );


        if (mRunTime.getErros().size() > 0) {
            return null;
        }


        if (Saida.getTipo().contentEquals("bool")) {
            Saida.setPrimitivo(true);
        } else if (Saida.getTipo().contentEquals("num")) {
            Saida.setPrimitivo(true);
        } else if (Saida.getTipo().contentEquals("string")) {
            Saida.setPrimitivo(true);
        }


        // System.out.println("  Retornando -> " + mAST.getConteudo());

        return Saida;
    }


    public void executar_Action(RunTime mRunTime, Escopo mStructEscopo, Index_Action mFunction, ArrayList<Item> mArgumentos) {

        //   System.out.println(" -->> ACTION CALL : " + mFunction.getNome() );
        //  System.out.println("\t - Escopo : " + mStructEscopo.getNome() );

        //  System.out.println(mStructEscopo.getNome() + " -> Structs : " + mStructEscopo.getStructs().size() );


        Escopo mEscopoInterno = new Escopo(mRunTime, mStructEscopo);
        mEscopoInterno.setNome(mFunction.getNome());

        passarParametros(mEscopoInterno, mFunction.getArgumentos(), mArgumentos);


        //  mEscopoInterno.ListarAll();

        AST mASTBody = mFunction.getPonteiro().getBranch("BODY");

        Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
        mAST.init(mASTBody);


        //   System.out.println( mFunction.getNome() + "  Retornando -> " + mAST.getConteudo() + " :: " +mRetornoTipo );

    }

    public Item executar_FunctionGlobal(RunTime mRunTime, Index_Function mFunction, ArrayList<Item> mArgumentos, String eReturne ) {


        return executar_Function(mRunTime, mRunTime.getEscopoGlobal(), mFunction, mArgumentos, eReturne);


    }


    public boolean mesmoArgumentos(RunTime mRunTime, Escopo mEscopo, ArrayList<Index_Argument> mParametros, ArrayList<Item> eArgumentos) {
        boolean ret = false;

        //  System.out.println("\t - Inicio da Checagem :  " + mTipoArgumentos.size() + " e " + eArgumentos.size());

      //  for (Index_Argument eArg : mParametros) {

   //     System.out.println("Preciso :: " +eArg.getTipo() );


      //  }

        if (eArgumentos.size() == mParametros.size()) {
            int i = 0;
            int v = 0;
            for (Item mArgumentos : eArgumentos) {


                if (mArgumentos.getTipo().contentEquals(mParametros.get(i).getTipo())) {
                    v += 1;
                } else {
                    if (mParametros.get(i).getTipo().contentEquals("any")) {
                        v += 1;
                    } else if (mParametros.get(i).getTipo().contentEquals("<<ANY>>")) {
                        v += 1;
                    } else if (mArgumentos.getTipo().contentEquals("<<ANY>>")) {
                        v += 1;
                    } else {

                        if (mArgumentos.getTipo().contains("<>")) {

                            if (mArgumentos.getTipo().contains(mParametros.get(i).getTipo())) {
                               // mArgumentos.setTipo(mParametros.get(i).getTipo());
                                v += 1;
                            }

                        }

                    }
                }

             //   System.out.println("\t - Conferindo Tipo :  " +  mArgumentos.getTipo() + " e " + mParametros.get(i).getTipo());

                //System.out.println("\t - Checando Tipo :  " + mArgumentos.getTipo() + " e " + mTipoArgumentos.get(i));
                //    System.out.println("\t - Entrando Tipo :  " +mArgumentos.getTipo() + " : " + mArgumentos.getValor() );

                i += 1;


            }


            if (v == i) {
                ret = true;
            }

            //  System.out.println("\t - Contagem Tipo :  " + i + " e " + v + " -> " + ret);

        }

        return ret;
    }

    public int conferirArgumentos(RunTime mRunTime, Escopo mEscopo, ArrayList<Index_Argument> mParametros, ArrayList<Item> eArgumentos) {
        int v = 0;

        // System.out.println("\t - Inicio da Checagem :  " + mTipoArgumentos.size() + " e " + eArgumentos.size());


        if (eArgumentos.size() == mParametros.size()) {
            int i = 0;

            for (Item mArgumentos : eArgumentos) {


                if (mArgumentos.getTipo().contentEquals(mParametros.get(i).getTipo())) {
                    v += 1;
                } else {
                    if (mParametros.get(i).getTipo().contentEquals("any")) {
                        v += 1;
                    } else if (mParametros.get(i).getTipo().contentEquals("<<ANY>>")) {
                        v += 1;
                    } else if (mArgumentos.getTipo().contentEquals("<<ANY>>")) {
                        v += 1;
                    } else {

                        if (mArgumentos.getTipo().contains("<>")) {

                            if (mArgumentos.getTipo().contains(mParametros.get(i).getTipo())) {
                             //   mArgumentos.setTipo(mParametros.get(i).getTipo());
                              //  v += 1;
                            }

                        }

                    }
                }


            //    System.out.println("\t - Conferindo Tipo :  " + mArgumentos.getValor(mRunTime,mEscopo) + " de " + mArgumentos.getTipo() + " e " + mParametros.get(i).getTipo());

                i += 1;

                if (mRunTime.getErros().size() > 0) {
                    return v;
                }

            }


            //  System.out.println("\t - Contagem Tipo :  " + i + " e " + v + " -> " + ret);

        }

        return v;
    }



}
