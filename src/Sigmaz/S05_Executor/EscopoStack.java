package Sigmaz.S05_Executor;

import java.util.ArrayList;

public class EscopoStack {

    private Escopo mEscopo;
    private RunTime mRunTime;

    public EscopoStack(RunTime eRunTime, Escopo eEscopo) {
        mRunTime = eRunTime;
        mEscopo = eEscopo;
    }


    public void criarItem(String eNome, Item eItem) {

        Item Novo = new Item(eNome);
        Novo.setTipo(eItem.getTipo());
        Novo.setNulo(eItem.getNulo(), mRunTime);
        Novo.setModo(eItem.getModo());

        Novo.setPrimitivo(eItem.getPrimitivo());
        Novo.setIsEstrutura(eItem.getIsEstrutura());
        Novo.setValor(eItem.getValor(mRunTime, mEscopo), mRunTime, mEscopo);
        mEscopo.getStacks().add(Novo);


    }


    public Item alocar(String eNome, String eTipo, TipoStack eTipoStack, boolean eNulo, String eValor,boolean eStruct) {

        boolean enc = existeAqui(eNome, mEscopo.getStacks());

        if (enc) {
            mRunTime.getErros().add("Definicao Duplicada : " + eNome);
        } else {


            Item eItem = new Item(eNome);
            eItem.setTipo(eTipo);

            eItem.setNulo(eNulo, mRunTime);

            if (eStruct){
                eItem.setPrimitivo(false);
                eItem.setIsEstrutura(true);
            }else{
                eItem.setPrimitivo(true);
                eItem.setIsEstrutura(false);
            }

            if (eTipoStack == TipoStack.Define) {
                eItem.setModo(0);
            } else if (eTipoStack == TipoStack.Mockiz) {
                eItem.setModo(1);
            } else if (eTipoStack == TipoStack.Mutable) {
                eItem.setModo(2);
            }


            eItem.setValor(eValor, mRunTime, mEscopo);


            mEscopo.getStacks().add(eItem);

            return eItem;
        }

        return null;
    }







    public boolean existeAqui(String eNome, ArrayList<Item> mProcurar) {

        boolean enc = false;

        for (Item i : mProcurar) {
            if (i.getNome().contentEquals(eNome)) {
                enc = true;
                break;
            }
        }
        return enc;
    }


    public void setDefinido(String eNome, String eValor) {

       // System.out.println("Aplicando Valor em : " + eNome + " -->> " + eValor);

        Item mItem = getItem(eNome);

      //  System.out.println("Aplicado Valor em : " + eNome + " -->> " + eValor);

        if (mItem.getModo() == 0 || mItem.getModo() == 2 ) {
            mItem.setValor(eValor, mRunTime, mEscopo);
            mItem.setNulo(false, mRunTime);
            //System.out.println("Aplicando Valor em : " + eNome + " -->> " + eValor);

            if (mItem.getIsReferenciavel()) {
                if (mItem.getReferencia().getModo() == 0) {
                    mItem.getReferencia().setValor(eValor, mRunTime, mEscopo);
                    mItem.getReferencia().setNulo(false, mRunTime);
                } else {
                    mRunTime.getErros().add("A constante referenciada nao pode ser alterada : " + mItem.getReferencia().getNome());
                }
            }

        } else {
            mRunTime.getErros().add("A constante nao pode ser alterada : " + eNome);
        }

    }


    public Item getItem(String eNome) {

        boolean enc = false;
        Item ret = null;


        for (Item i : mEscopo.getStacks()) {
            if (i.getNome().contentEquals(eNome)) {
                enc = true;
                ret = i;
                break;
            }
        }

        if (!enc) {

            Item IC = BuscarAnterior(eNome);
            if (IC != null) {
                enc = true;
                ret = IC;

            }

        }

        if (!enc) {
            mRunTime.getErros().add("Variavel nao Encontrada : " + eNome);
        }


        return ret;
    }


    public Item BuscarAnterior(String eNome) {
        Item IC = null;
        boolean enc = false;

        //	System.out.println("Buscando anterior : " + eNome);

        if (mEscopo.getEscopoAnterior() != null) {


            for (Item i : mEscopo.getEscopoAnterior().getStacks()) {
                if (i.getNome().contentEquals(eNome)) {
                    enc = true;
                    IC = i;
                    break;
                }
            }

        }

        if (!enc) {
            if (mEscopo.getEscopoAnterior() != null) {
                return mEscopo.getEscopoAnterior().BuscarAnterior(eNome);
            }

        }

        return IC;
    }


    public void criarExternRefered(String eNome, String mTipagem, String eStruct, String eCampo) {

        if (existeAqui(eNome, mEscopo.getStacks())) {
            mRunTime.getErros().add("Definicao Duplicada : " + eNome);
        }

        Item Novo = new Item(eNome);
        Novo.setNulo(false, mRunTime);

        Novo.setModo(0);
        Novo.setTipo(mTipagem);
        Novo.setPrimitivo(false);
        Novo.setIsEstrutura(false);

        Novo.setRefer(eStruct, eCampo);

        mEscopo.getStacks().add(Novo);


    }

    public void criarExternRefered_Const(String eNome, String mTipagem, String eStruct, String eCampo) {

        if (existeAqui(eNome, mEscopo.getStacks())) {
            mRunTime.getErros().add("Definicao Duplicada : " + eNome);
        }

        Item Novo = new Item(eNome);
        Novo.setNulo(false, mRunTime);

        Novo.setModo(0);
        Novo.setTipo(mTipagem);
        Novo.setPrimitivo(false);
        Novo.setIsEstrutura(false);

        Novo.setReferConst(eStruct, eCampo);

        mEscopo.getStacks().add(Novo);


    }


}
