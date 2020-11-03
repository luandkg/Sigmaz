package Sigmaz.S05_Executor.Runners;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.RunTime;

public class Run_Return {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public Run_Return(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Return";


    }

    public Item init(AST ASTCorrente, String mEsperaRetornar) {

        Item mItem = new Item("RETURNABLE");

        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(ASTCorrente.getBranch("VALUE"), mEsperaRetornar);

        if (mRunTime.getErros().size() > 0) {
            return mItem;
        }


        mItem.setNulo(mAST.getIsNulo(),mRunTime);
        mItem.setPrimitivo(mAST.getIsPrimitivo());
        mItem.setIsEstrutura(mAST.getIsStruct());
        mItem.setValor(mAST.getConteudo(), mRunTime, mEscopo);
        mItem.setTipo(mAST.getRetornoTipo());

        if (mItem.getIsEstrutura()) {
            if (mItem.getTemValor()) {
                mRunTime.getHeap().aumentar(mItem.getValor(mRunTime, mEscopo));
            }
        }

        return mItem;
    }

}
