package Sigmaz.Executor;

import java.util.ArrayList;

public class EscopoStack {

    private Escopo mEscopo;
    private RunTime mRunTime;

    public EscopoStack(RunTime eRunTime, Escopo eEscopo) {
        mRunTime = eRunTime;
        mEscopo = eEscopo;
    }


    public void criarDefinicaoNula(String eNome, String eTipo) {
        boolean enc = false;

        for (Item i : mEscopo.getStacks()) {
            if (i.getNome().contentEquals(eNome)) {
                enc = true;
                break;
            }
        }

        if (!enc) {

            Item IC = BuscarAnterior(eNome);
            if (IC != null) {
                enc = true;
            }

        }

        if (enc) {
            mRunTime.getErros().add("Variavel Duplicada : " + eNome);
        } else {
            Item eItem = new Item(eNome);
            eItem.setTipo(eTipo);
            eItem.setNulo(true);

            mEscopo.getStacks().add(eItem);

        }
    }

    public void criarConstanteNula(String eNome, String eTipo) {
        boolean enc = false;

        for (Item i : mEscopo.getStacks()) {
            if (i.getNome().contentEquals(eNome)) {
                enc = true;
                break;
            }
        }

        if (!enc) {

            Item IC = BuscarAnterior(eNome);
            if (IC != null) {
                enc = true;
            }

        }

        if (enc) {
            mRunTime.getErros().add("Constante Duplicada : " + eNome);
        } else {
            Item eItem = new Item(eNome);
            eItem.setModo(1);
            eItem.setTipo(eTipo);
            eItem.setNulo(true);

            mEscopo.getStacks().add(eItem);

        }
    }

    public void criarDefinicao(String eNome, String eTipo, String eValor) {
        boolean enc = false;

        for (Item i : mEscopo.getStacks()) {
            if (i.getNome().contentEquals(eNome)) {
                enc = true;
                break;
            }
        }

        if (!enc) {

            Item IC = BuscarAnterior(eNome);
            if (IC != null) {
                enc = true;
            }

        }

        if (enc) {
            mRunTime.getErros().add("Variavel Duplicada : " + eNome);
        } else {
            Item Novo = new Item(eNome);
            Novo.setModo(0);
            Novo.setTipo(eTipo);
            Novo.setValor(eValor);
            Novo.setNulo(false);

            mEscopo.getStacks().add(Novo);

        }
    }


    public void criarConstante(String eNome, String eTipo, String eValor) {
        boolean enc = false;

        for (Item i : mEscopo.getStacks()) {
            if (i.getNome().contentEquals(eNome)) {
                enc = true;
                break;
            }
        }

        if (!enc) {

            Item IC = BuscarAnterior(eNome);
            if (IC != null) {
                enc = true;
            }

        }

        if (enc) {
            mRunTime.getErros().add("Constante Duplicada : " + eNome);
        } else {
            Item Novo = new Item(eNome);
            Novo.setModo(1);
            Novo.setTipo(eTipo);
            Novo.setValor(eValor);
            Novo.setNulo(false);
            mEscopo.getStacks().add(Novo);

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

        boolean enc = false;
        String ret = "";


        for (Item i : mEscopo.getStacks()) {
            if (i.getNome().contentEquals(eNome)) {
                enc = true;
                ret = i.getTipo();
                break;
            }
        }

        if (!enc) {

            Item IC = BuscarAnterior(eNome);
            if (IC != null) {
                enc = true;
                ret = IC.getTipo();

            }

        }

        if (!enc) {
            mRunTime.getErros().add("Variavel nao Encontrada : " + eNome);
        }

        return ret;

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

        if(t==null){
            return false;
        }else{
            return t.getPrimitivo();
        }

    }
    public boolean getDefinidoNulo(String eNome) {

        Item t = getItem(eNome);

        if(t==null){
            return false;
        }else{
            return t.getNulo();
        }

    }

    public String getDefinidoConteudo(String eNome) {

        Item t = getItem(eNome);

        if(t==null){
            return "";
        }else{
            return t.getValor();
        }

    }

    public String getDefinido(String eNome) {
        boolean enc = false;
        String ret = "";


        for (Item i : mEscopo.getStacks()) {
            if (i.getNome().contentEquals(eNome)) {
                enc = true;
                ret = i.getValor();
                break;
            }
        }

        if (!enc) {

            Item IC = BuscarAnterior(eNome);
            if (IC != null) {
                enc = true;
                ret = IC.getValor();

            }

        }

        if (!enc) {
            mRunTime.getErros().add("Variavel nao Encontrada : " + eNome);
        }

        return ret;
    }


    public String getDefinidoNum(String eNome) {
        boolean enc = false;
        String ret = "";


        for (Item i : mEscopo.getStacks()) {
            if (i.getNome().contentEquals(eNome)) {
                enc = true;
                ret = i.getValor();
                if (i.getTipo().contentEquals("num") == false) {
                    mRunTime.getErros().add("Era esperado uma variavel do tipo num !");
                }
                break;
            }
        }

        if (!enc) {

            Item IC = BuscarAnterior(eNome);
            if (IC != null) {
                enc = true;
                ret = IC.getValor();
                if (IC.getTipo().contentEquals("num") == false) {
                    mRunTime.getErros().add("Era esperado uma variavel do tipo num !");
                }
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


}
