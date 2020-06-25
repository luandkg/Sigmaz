package Sigmaz.Executor;

import java.util.ArrayList;

public class EscopoStack {

    private Escopo mEscopo;
    private RunTime mRunTime;

    public EscopoStack(RunTime eRunTime, Escopo eEscopo) {
        mRunTime = eRunTime;
        mEscopo = eEscopo;
    }


    public void alocarPrimitivo(String eNome, String eTipo, boolean econstante, boolean possuiValor, String eValor) {

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

            eItem.setValor(eValor);


            mEscopo.getStacks().add(eItem);


        }

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
            Novo.setValor(eRef);
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
            Novo.setModo(0);
            Novo.setTipo(eTipo);
            Novo.setNulo(false);
            Novo.setPrimitivo(false);
            Novo.setIsEstrutura(true);
            Novo.setValor(eRef);
            mEscopo.getStacks().add(Novo);

        }
    }


    public void criarParametro(String eNome, String eTipo, String eValor) {

        boolean enc = existeAqui(eNome, mEscopo.getParametros());

        if (enc) {
            mRunTime.getErros().add("Parametro Duplicado : " + eNome);
        } else {
            Item Novo = new Item(eNome);
            Novo.setModo(0);
            Novo.setTipo(eTipo);
            Novo.setValor(eValor);
            Novo.setNulo(false);

            mEscopo.getParametros().add(Novo);

        }
    }

    public void criarParametroNulo(String eNome, String eTipo, String eValor) {

        boolean enc = existeAqui(eNome, mEscopo.getParametros());

        if (enc) {
            mRunTime.getErros().add("Parametro Duplicado : " + eNome);
        } else {
            Item Novo = new Item(eNome);
            Novo.setModo(0);
            Novo.setTipo(eTipo);
            Novo.setValor(eValor);
            Novo.setNulo(true);

            mEscopo.getParametros().add(Novo);

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

    public void criarParametroStruct(String eNome, String eTipo, String eRef) {

        boolean enc = existeAqui(eNome, mEscopo.getParametros());

        if (enc) {
            mRunTime.getErros().add("Parametro Duplicado : " + eNome);
        } else {
            Item Novo = new Item(eNome);
            Novo.setModo(0);
            Novo.setTipo(eTipo);

            Novo.setNulo(false);
            Novo.setIsEstrutura(true);
            Novo.setPrimitivo(false);
            Novo.setValor(eRef);

            //   System.out.println("\t - Passando Parametro Struct : " + eRef );

            mEscopo.getParametros().add(Novo);

        }
    }

    public void criarParametroStructNulo(String eNome, String eTipo, String eRef) {

        boolean enc = existeAqui(eNome, mEscopo.getParametros());

        if (enc) {
            mRunTime.getErros().add("Parametro Duplicado : " + eNome);
        } else {
            Item Novo = new Item(eNome);
            Novo.setModo(0);
            Novo.setTipo(eTipo);

            Novo.setNulo(true);
            Novo.setIsEstrutura(true);
            Novo.setPrimitivo(false);
            Novo.setValor(eRef);

            //   System.out.println("\t - Passando Parametro Struct : " + eRef );

            mEscopo.getParametros().add(Novo);

        }
    }


    public void setDefinido(String eNome, String eValor) {


        Item mItem = getItem(eNome);
        if (mItem.getModo() == 0) {
            mItem.setValor(eValor);
            mItem.setNulo(false);
            //System.out.println("Aplicando Valor em : " + eNome + " -->> " + eValor);
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
            mItem.setValor(eValor);
            mItem.setNulo(false);
            //System.out.println("Aplicando Valor em : " + eNome + " -->> " + eValor);
        } else {
            mRunTime.getErros().add("A constante nao pode ser alterada : " + eNome);
        }

    }

    public void anularDefinido(String eNome) {

        Item mItem = getItem(eNome);
        if (mItem.getModo() == 0) {
            mItem.setValor("");
            mItem.setNulo(true);
            //System.out.println("Aplicando Valor em : " + eNome + " -->> " + eValor);
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

        for (Item i : mEscopo.getParametros()) {
            if (i.getNome().contentEquals(eNome)) {
                enc = true;
                ret = i;
                return ret;
            }
        }

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
            return t.getValor();
        }

    }

    public String getDefinido(String eNome) {

        Item t = getItem(eNome);

        if (t == null) {
            return "";
        } else {
            return t.getValor();
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

            return t.getValor();
        }

    }

    public Item BuscarAnterior(String eNome) {
        Item IC = null;
        boolean enc = false;

        //	System.out.println("Buscando anterior : " + eNome);

        if (mEscopo.getEscopoAnterior() != null) {

            for (Item i : mEscopo.getEscopoAnterior().getParametros()) {
                if (i.getNome().contentEquals(eNome)) {
                    enc = true;
                    IC = i;
                    break;
                }
            }


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
