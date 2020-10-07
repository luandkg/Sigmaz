package Sigmaz.S04_Compilador.Fluxo;

import Sigmaz.S04_Compilador.CompilerUnit;
import Sigmaz.S06_Executor.Item;
import Sigmaz.S03_Parser.Token;
import Sigmaz.S03_Parser.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Daz {

    private CompilerUnit mCompiler;

    private boolean mRetornado;
    private Item mItem;

    public AST_Daz(CompilerUnit eCompiler) {
        mCompiler = eCompiler;

        mRetornado=false;
        mItem=null;
    }



    public Item getRetorno(){ return mItem; }
    public boolean getRetornado() { return mRetornado; }

    public void Retorne(Item eItem){
        mItem=eItem;
    }

    public void init(AST ASTPai) {


        AST AST_Corrente = new AST("DAZ");
        ASTPai.getASTS().add(AST_Corrente);

       // mCompiler.Proximo();

        AST AST_Arguments = AST_Corrente.criarBranch("CHOOSABLE");
        AST AST_Casos = AST_Corrente.criarBranch("CASES");

        Token TokenPrimeiro = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado Abrir Parenteses");

        AST_Value mAST = new AST_Value(mCompiler);
      //  mAST.initParam(AST_Arguments);
        mAST.setBloco();
        mAST.init(AST_Arguments);


        Token TokenC6 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado -> ");

        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.CHAVE_ABRE, "Era esperado abrir chaves");

        if (TokenC.getTipo() != TokenTipo.CHAVE_ABRE) {
            return;
        }

        boolean saiu = false;


        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.CHAVE_FECHA) {
                saiu = true;
                break;
            } else  if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("inside")) {

                dentro("INSIDE",AST_Casos);

            } else  if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("extrem")) {

                dentro("EXTREM",AST_Casos);

            } else  if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("outside")) {

                dentro("OUTSIDE",AST_Casos);

            } else  if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("start")) {

                dentro("START",AST_Casos);

            } else  if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("end")) {

                dentro("END",AST_Casos);

            } else  if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("others")) {


                AST AST_CASE = AST_Corrente.criarBranch("OTHERS");


                AST_Corpo cAST = new AST_Corpo(mCompiler);
                cAST.init(AST_CASE);



            } else {



                mCompiler.errarCompilacao("Comando Desconhecido :  " + TokenD.getConteudo(), TokenD);
                mCompiler.pularLinha();

            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }


    }

    public void dentro(String eTitulo,AST AST_Casos){

        AST_Value mVal = new AST_Value(mCompiler);

        AST AST_CASE = AST_Casos.criarBranch(eTitulo);

        Token TokenC5 = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado ( ");

       // mVal.setSeta();
     //   mVal.init(AST_CASE);

      //  AST_Value_Argument mAST = new AST_Value_Argument(mCompiler);
       // mVal.ReceberArgumentos(AST_CASE);
        AST_ValueTypes mAVA = new AST_ValueTypes(mCompiler);
        mAVA.ReceberArgumentos(AST_CASE,false,null);

        AST_CASE.setTipo(eTitulo);


        Token TokenC7 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado -> ");

        AST_Corpo cAST = new AST_Corpo(mCompiler);
        cAST.init(AST_CASE.criarBranch("BODY"));


    }

}