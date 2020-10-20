package Sigmaz.S07_Executor;

import java.util.ArrayList;

public class EscopoStack {

    private Escopo mEscopo;
    private RunTime mRunTime;

    public EscopoStack(RunTime eRunTime, Escopo eEscopo) {
        mRunTime = eRunTime;
        mEscopo = eEscopo;
    }


    public Item alocarPrimitivo(String eNome, String eTipo, boolean econstante, boolean possuiValor, String eValor) {

        boolean enc = existeAqui(eNome, mEscopo.getStacks());

        if (enc) {
            mRunTime.getErros().add("Definicao Duplicada : " + eNome);
        } else {


            Item eItem = new Item(eNome);
            eItem.setTipo(eTipo);
            eItem.setPrimitivo(true);
            eItem.setIsEstrutura(false);

            if (possuiValor) {
                eItem.setNulo(false);
            } else {
                eItem.setNulo(true);
            }

            if (econstante) {
                eItem.setModo(1);
            } else {
                eItem.setModo(0);
            }

            eItem.setValor(eValor,mRunTime,mEscopo);


            mEscopo.getStacks().add(eItem);

            return eItem;
        }

        return null;
    }

    public Item alocarMutavelPrimitivo(String eNome, String eTipo,  boolean possuiValor, String eValor) {

        boolean enc = existeAqui(eNome, mEscopo.getStacks());

        if (enc) {
            mRunTime.getErros().add("Definicao Duplicada : " + eNome);
        } else {


            Item eItem = new Item(eNome);
            eItem.setTipo(eTipo);
            eItem.setPrimitivo(true);
            eItem.setIsEstrutura(false);

            if (possuiValor) {
                eItem.setNulo(false);
            } else {
                eItem.setNulo(true);
            }

            eItem.setModo(2);


            eItem.setValor(eValor,mRunTime,mEscopo);


            mEscopo.getStacks().add(eItem);

            return eItem;
        }

        return null;
    }

    public Item alocarMutavelStruct(String eNome, String eTipo,  boolean possuiValor, String eValor) {

        boolean enc = existeAqui(eNome, mEscopo.getStacks());

        if (enc) {
            mRunTime.getErros().add("Definicao Duplicada : " + eNome);
        } else {


            Item eItem = new Item(eNome);
            eItem.setTipo(eTipo);
            eItem.setPrimitivo(false);
            eItem.setIsEstrutura(true);

            if (possuiValor) {
                eItem.setNulo(false);
            } else {
                eItem.setNulo(true);
            }

            eItem.setModo(2);

            eItem.setValor(eValor,mRunTime,mEscopo);


            mEscopo.getStacks().add(eItem);

            return eItem;
        }

        return null;
    }


    public void criarDefinicaoStruct(String eNome, String eTipo, String eRef) {

        boolean enc = existeAqui(eNome, mEscopo.getStacks());

        if (enc) {
            mRunTime.getErros().add("Variavel Duplicada : " + eNome);
        }


        if (!enc) {

            Item IC = BuscarAnterior(eNome);
            if (IC != null) {
                enc = true;
            }

        }

        if (enc) {
            //  mRunTime.getErros().add("Variavel Duplicada : " + eNome);
        } else {
            Item Novo = new Item(eNome);
            Novo.setModo(0);
            Novo.setTipo(eTipo);
            Novo.setNulo(false);
            Novo.setPrimitivo(false);
            Novo.setIsEstrutura(true);
            Novo.setValor(eRef,mRunTime,mEscopo);
            mEscopo.getStacks().add(Novo);

        }
    }


    public void criarDefinicaoStructNula(String eNome, String eTipo) {

        boolean enc = existeAqui(eNome, mEscopo.getStacks());

        if (enc) {
            mRunTime.getErros().add("Variavel Duplicada : " + eNome);
        }


        if (!enc) {

            Item IC = BuscarAnterior(eNome);
            if (IC != null) {
                enc = true;
            }

        }

        if (enc) {
            //  mRunTime.getErros().add("Variavel Duplicada : " + eNome);
        } else {
            Item Novo = new Item(eNome);
            Novo.setModo(0);
            Novo.setTipo(eTipo);
            Novo.setNulo(true);
            Novo.setPrimitivo(false);
            Novo.setIsEstrutura(true);
            Novo.setValor("",mRunTime,mEscopo);
            mEscopo.getStacks().add(Novo);

        }
    }

    public void criarConstanteStruct(String eNome, String eTipo, String eRef) {

        boolean enc = existeAqui(eNome, mEscopo.getStacks());

        if (enc) {
            mRunTime.getErros().add("Variavel Duplicada : " + eNome);
        }

        if (!enc) {

            Item IC = BuscarAnterior(eNome);
            if (IC != null) {
                enc = true;
            }

        }

        if (enc) {
            //   mRunTime.getErros().add("Variavel Duplicada : " + eNome);
        } else {
            Item Novo = new Item(eNome);
            Novo.setModo(1);
            Novo.setTipo(eTipo);
            Novo.setNulo(false);
            Novo.setPrimitivo(false);
            Novo.setIsEstrutura(true);
            Novo.setValor(eRef,mRunTime,mEscopo);
            mEscopo.getStacks().add(Novo);

        }
    }


    public void criarConstanteStructNula(String eNome, String eTipo) {

        boolean enc = existeAqui(eNome, mEscopo.getStacks());

        if (enc) {
            mRunTime.getErros().add("Variavel Duplicada : " + eNome);
        }

        if (!enc) {

            Item IC = BuscarAnterior(eNome);
            if (IC != null) {
                enc = true;
            }

        }

        if (enc) {
            //   mRunTime.getErros().add("Variavel Duplicada : " + eNome);
        } else {
            Item Novo = new Item(eNome);
            Novo.setModo(1);
            Novo.setTipo(eTipo);
            Novo.setNulo(true);
            Novo.setPrimitivo(false);
            Novo.setIsEstrutura(true);
            Novo.setValor("",mRunTime,mEscopo);
            mEscopo.getStacks().add(Novo);

        }
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


        Item mItem = getItem(eNome);
        if (mItem.getModo() == 0) {
            mItem.setValor(eValor,mRunTime,mEscopo);
            mItem.setNulo(false);
            //System.out.println("Aplicando Valor em : " + eNome + " -->> " + eValor);

            if (mItem.getIsReferenciavel()) {
                if (mItem.getReferencia().getModo() == 0) {
                    mItem.getReferencia().setValor(eValor,mRunTime,mEscopo);
                    mItem.getReferencia().setNulo(false);
                } else {
                    mRunTime.getErros().add("A constante referenciada nao pode ser alterada : " + mItem.getReferencia().getNome());
                }
            }

        } else {
            mRunTime.getErros().add("A constante nao pode ser alterada : " + eNome);
        }

    }



    public void setDefinidoStruct(String eNome, String eValor) {

        Item mItem = getItem(eNome);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (mItem.getModo() == 0) {
            mItem.setValor(eValor,mRunTime,mEscopo);
            mItem.setNulo(false);

            if (mItem.getIsReferenciavel()) {
                if (mItem.getReferencia().getModo() == 0) {
                    mItem.getReferencia().setValor(eValor,mRunTime,mEscopo);
                    mItem.getReferencia().setNulo(false);
                } else {
                    mRunTime.getErros().add("A constante referenciada nao pode ser alterada : " + mItem.getReferencia().getNome());
                }
            }

        } else {
            mRunTime.getErros().add("A constante nao pode ser alterada : " + eNome);
        }

    }

    public void anularDefinido(String eNome) {

        Item mItem = getItem(eNome);
        if (mItem.getModo() == 0) {
            mItem.setValor("",mRunTime,mEscopo);
            mItem.setNulo(true);

            if (mItem.getIsReferenciavel()) {
                if (mItem.getReferencia().getModo() == 0) {
                    mItem.getReferencia().setValor("",mRunTime,mEscopo);
                    mItem.getReferencia().setNulo(true);
                } else {
                    mRunTime.getErros().add("A constante referenciada nao pode ser alterada : " + mItem.getReferencia().getNome());
                }
            }

        } else {
            mRunTime.getErros().add("A constante nao pode ser alterada : " + eNome);
        }

    }


    public String getDefinidoTipo(String eNome) {


        Item t = getItem(eNome);

        if (t == null) {
            return "";
        } else {
            return t.getTipo();
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

    public boolean getDefinidoPrimitivo(String eNome) {
        Item t = getItem(eNome);

        if (t == null) {
            return false;
        } else {
            return t.getPrimitivo();
        }

    }

    public boolean getDefinidoNulo(String eNome) {

        Item t = getItem(eNome);

        if (t == null) {
            return false;
        } else {
            //   System.out.println("Nulo " + eNome + " : " + t.getNulo());
            //  System.out.println("Valor " + eNome + " : " + t.getValor());

            return t.getNulo();
        }

    }

    public String getDefinidoConteudo(String eNome) {

        Item t = getItem(eNome);

        if (t == null) {
            return "";
        } else {
            return t.getValor(mRunTime,mEscopo);
        }

    }

    public String getDefinido(String eNome) {

        Item t = getItem(eNome);

        if (t == null) {
            return "";
        } else {
            return t.getValor(mRunTime,mEscopo);
        }

    }


    public String getDefinidoNum(String eNome) {


        Item t = getItem(eNome);

        if (t == null) {
            return "";
        } else {

            if (t.getTipo().contentEquals("num") == false) {
                mRunTime.getErros().add("Era esperado uma variavel do tipo num !");
            }

            return t.getValor(mRunTime,mEscopo);
        }

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


}
