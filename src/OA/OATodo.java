package OA;

import OA.LuanDKG.LuanDKG;
import OA.LuanDKG.Objeto;
import OA.LuanDKG.Pacote;
import Sigmaz.Utils.Texto;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class OATodo {

    private String mArquivo;

    public OATodo(String eArquivo) {
        mArquivo = eArquivo;
    }

    public class Item {

        private String mTexto;
        private String mData;
        private String mStatus;
        public ArrayList<String> mTags;

        public Item(String eTexto, String eData, String eStatus) {

            mTexto = eTexto;
            mData = eData;
            mStatus = eStatus;
            mTags = new ArrayList<String>();

        }

        public String getTexto() {
            return mTexto;
        }

        public String getData() {
            return mData;
        }

        public String getStatus() {
            return mStatus;
        }

        public void setTexto(String eTexto) {
            mTexto = eTexto;
        }

        public void setData(String eData) {
            mData = eData;
        }

        public void setStatus(String eStatus) {
            mStatus = eStatus;
        }

    }


    public ArrayList<Item> getNovos(String eArquivo) {

        ArrayList<Item> mItens = new ArrayList<Item>();

        File arq = new File(eArquivo);

        if (arq.exists()) {
            String Documento = Texto.Ler(eArquivo);

            if (Documento != null) {
                Documento += "\n";

                int i = 0;
                int o = Documento.length();

                String linha = "";


                while (i < o) {
                    String l = Documento.charAt(i) + "";
                    if (l.contentEquals("\n")) {


                        if (linha.length() > 0) {

                            linha = linha.replace("\t", " ");
                            while (linha.contains("  ")) {
                                linha = linha.replace("  ", " ");
                            }
                            if (!linha.contentEquals("null")) {
                                Item ItemC = organizarItem(linha);
                                mItens.add(ItemC);
                            }

                        }

                        linha = "";
                    } else {
                        linha += l;
                    }
                    i += 1;
                }

            }


        }


        return mItens;

    }

    public Item organizarItem(String eTexto) {


        int i = 0;
        int o = eTexto.length();

        String itemTexto = "";
        String itemData = "";
        String itemStatus = "";

        while (i < o) {
            String l = eTexto.charAt(i) + "";
            if (l.contentEquals("-")) {
                i += 1;
                break;
            }
            i += 1;
        }

        while (i < o) {
            String l = eTexto.charAt(i) + "";
            if (l.contentEquals(":")) {
                i += 1;
                break;
            } else if (l.contentEquals("\t")) {

            } else {
                itemTexto += l;
            }
            i += 1;
        }

        boolean temTags = false;

        while (i < o) {
            String l = eTexto.charAt(i) + "";
            if (l.contentEquals("{")) {
                i += 1;
                temTags = true;
                break;
            } else if (l.contentEquals(" ")) {
            } else if (l.contentEquals("\t")) {
            } else {
                itemStatus += l;
            }
            i += 1;
        }

        Item ItemC = new Item(itemTexto, itemData, itemStatus);

        if (temTags) {

            String mTag = "";

            while (i < o) {
                String l = eTexto.charAt(i) + "";
                if (l.contentEquals("}")) {
                    if (mTag.length() > 0) {
                        ItemC.mTags.add(mTag);
                    }
                    mTag = "";
                } else if (l.contentEquals(" ")) {
                    if (mTag.length() > 0) {
                        ItemC.mTags.add(mTag);
                    }
                    mTag = "";
                } else if (l.contentEquals("\t")) {
                    if (mTag.length() > 0) {
                        ItemC.mTags.add(mTag);
                    }
                    mTag = "";
                } else {
                    mTag += l;
                }
                i += 1;
            }

        }


        return ItemC;
    }


    public void sincronizar(String eArquivo) {


        System.out.println("");

        String a = "########################### OA TODO ";

        System.out.println(a + paraFechar(a.length(),80,"#"));

        System.out.println("");
        System.out.println("\t - Iniciar : " + getDataHora());

        ArrayList<Item> mItens = getNovos(eArquivo);


        File arq = new File(mArquivo);
        LuanDKG mTodo = new LuanDKG();

        if (arq.exists()) {
            mTodo.Abrir(mArquivo);
        }

        Pacote OATodo = mTodo.UnicoPacote("OATodo");
        String index = OATodo.Identifique("Indice").getValor();

        int ci = get_string_num(index, 0);


        Pacote OATodos = OATodo.UnicoPacote("Todos");
        // Pacote OABranches = OATodo.UnicoPacote("Branches");

        for (Item ItemC : mItens) {

            //  System.out.println(" Item : " + ItemC.getStatus() + " :: " + ItemC.getData() + " -->> " + ItemC.getTexto());

            boolean existe = false;

            if (ItemC.getStatus().contentEquals("r")) {
                existe = true;

                for (Objeto mObjeto : OATodos.getObjetos()) {
                    if (mObjeto.Identifique("Texto").getValor().contentEquals(ItemC.getTexto())) {
                        OATodos.getObjetos().remove(mObjeto);
                        break;
                    }


                }

            }

            if (!existe) {

                String mTags = "";

                for (String mTag : ItemC.mTags) {
                    mTags += mTag + " ";
                }

                for (Objeto mObjeto : OATodos.getObjetos()) {
                    if (mObjeto.Identifique("Texto").getValor().contentEquals(ItemC.getTexto())) {
                        existe = true;

                        mObjeto.Identifique("Tags").setValor(mTags);

                        if (mObjeto.Identifique("Status").getValor().contentEquals(ItemC.getStatus())) {

                        } else {


                            // Objeto mAction = OABranches.CriarObjeto("ACAO");
                            // mAction.Identifique("Modo", "ALTERACAO");
                            // mAction.Identifique("Item", mObjeto.Identifique("Indice").getValor());
                            // mAction.Identifique("Antes", mObjeto.Identifique("Status").getValor());
                            // mAction.Identifique("Depois", ItemC.getStatus());
                            // //mAction.Identifique("Data", getDataHora());

                            mObjeto.Identifique("Status", ItemC.getStatus());
                            mObjeto.Identifique("Alterado", getDataHora());

                        }


                        break;
                    }
                }


            }


            if (!existe) {


                ci += 1;
                OATodo.Identifique("Indice").setValor(String.valueOf(ci));


                Objeto eObjeto = OATodos.CriarObjeto("Item");
                eObjeto.Identifique("Indice", String.valueOf(ci));

                eObjeto.Identifique("Texto", ItemC.getTexto());
                eObjeto.Identifique("Data", getDataHora());
                eObjeto.Identifique("Alterado", getDataHora());
                eObjeto.Identifique("Status", ItemC.getStatus());

                String mTags = "";

                for (String mTag : ItemC.mTags) {
                    mTags += mTag + " ";
                }

                eObjeto.Identifique("Tags").setValor(mTags);


                // Objeto mAction = OABranches.CriarObjeto("ACAO");
                //  mAction.Identifique("Mode", "NOVO");
                //  mAction.Identifique("Item", ItemC.getTexto());
                //  mAction.Identifique("Data", getDataHora());

            }

        }


        mTodo.Salvar(mArquivo);


        System.out.println("\t - Itens : " +OATodos.getObjetos().size());
        System.out.println("\t - Finalizar : " + getDataHora());
        System.out.println("");

        String b = "########################### ####### ";

        System.out.println(b + paraFechar(b.length(),80,"#"));

    }


    public String paraFechar(int ja,int ate,String c){
        String ret = "";

        if (ja<ate){
            int d = ate-ja;
            for(int i=0;i<d;i++){
                ret+=c;
            }
        }

        return ret;
    }


    public void Listar() {

        System.out.println("");
        System.out.println("########################### OATodo - TODOS ########################### ");
        System.out.println("");

        File arq = new File(mArquivo);
        LuanDKG mTodo = new LuanDKG();

        if (arq.exists()) {
            mTodo.Abrir(mArquivo);
        }

        Pacote OATodo = mTodo.UnicoPacote("OATodo");
        Pacote OATodos = OATodo.UnicoPacote("Todos");
        for (Objeto mObjeto : OATodos.getObjetos()) {


            String eTexto = mObjeto.Identifique("Texto").getValor();

            if (eTexto.length() > 0) {
                String eStatus = mObjeto.Identifique("Status").getValor();
                String eData = mObjeto.Identifique("Data").getValor();

                System.out.println(" \t -> " + eStatus + " :: " + eData + " -->> " + eTexto);

            }

        }

        System.out.println("");

    }

    public void ListarStatus(String mStatus) {

        System.out.println("");
        System.out.println("########################### STATUS : " + mStatus + " ########################### ");
        System.out.println("");


        File arq = new File(mArquivo);
        LuanDKG mTodo = new LuanDKG();

        if (arq.exists()) {
            mTodo.Abrir(mArquivo);
        }

        Pacote OATodo = mTodo.UnicoPacote("OATodo");
        Pacote OATodos = OATodo.UnicoPacote("Todos");
        for (Objeto mObjeto : OATodos.getObjetos()) {


            String eTexto = mObjeto.Identifique("Texto").getValor();

            if (eTexto.length() > 0) {
                String eStatus = mObjeto.Identifique("Status").getValor();

                if (mStatus.contentEquals(eStatus)) {

                    String eData = mObjeto.Identifique("Data").getValor();

                    System.out.println(" \t -> " + eStatus + " :: " + eData + " -->> " + eTexto);


                }

            }

        }
        System.out.println("");


    }

    public void ListarStatus(String mStatus,String mStatusMostrar) {

        System.out.println("");

        String a = "########################### STATUS : " + mStatusMostrar + " " ;

        System.out.println(a + paraFechar(a.length(),80,"#"));

        System.out.println("");


        File arq = new File(mArquivo);
        LuanDKG mTodo = new LuanDKG();

        if (arq.exists()) {
            mTodo.Abrir(mArquivo);
        }

        Pacote OATodo = mTodo.UnicoPacote("OATodo");
        Pacote OATodos = OATodo.UnicoPacote("Todos");
        for (Objeto mObjeto : OATodos.getObjetos()) {


            String eTexto = mObjeto.Identifique("Texto").getValor();

            if (eTexto.length() > 0) {
                String eStatus = mObjeto.Identifique("Status").getValor();

                if (mStatus.contentEquals(eStatus)) {

                    String eData = mObjeto.Identifique("Data").getValor();

                    System.out.println(" \t -> " + mStatusMostrar + " :: " + eData + " -->> " + eTexto);


                }

            }

        }


    }

    public void ListarStatus_Recente(String mStatus,String mStatusMostrar) {

        System.out.println("");

        String a = "########################### STATUS : " + mStatusMostrar + " " ;

        System.out.println(a + paraFechar(a.length(),80,"#"));


        System.out.println(a + paraFechar(a.length(),80,"#"));

        System.out.println("");


        File arq = new File(mArquivo);
        LuanDKG mTodo = new LuanDKG();

        if (arq.exists()) {
            mTodo.Abrir(mArquivo);
        }

        Pacote OATodo = mTodo.UnicoPacote("OATodo");
        Pacote OATodos = OATodo.UnicoPacote("Todos");
        for (Objeto mObjeto : OATodos.getObjetos()) {


            String eTexto = mObjeto.Identifique("Texto").getValor();

            if (eTexto.length() > 0) {
                String eStatus = mObjeto.Identifique("Status").getValor();

                if (mStatus.contentEquals(eStatus)) {

                    String eData = mObjeto.Identifique("Alterado").getValor();

                    System.out.println(" \t -> " + mStatusMostrar + " :: " + eData + " -->> " + eTexto);


                }

            }

        }


    }

    public void ListarStatus_Modificado(String mStatus,String mStatusMostrar) {

        System.out.println("");

        String a = "########################### STATUS : " + mStatusMostrar + " " ;

        System.out.println(a + paraFechar(a.length(),80,"#"));

        System.out.println("");


        File arq = new File(mArquivo);
        LuanDKG mTodo = new LuanDKG();

        if (arq.exists()) {
            mTodo.Abrir(mArquivo);
        }

        Pacote OATodo = mTodo.UnicoPacote("OATodo");
        Pacote OATodos = OATodo.UnicoPacote("Todos");
        for (Objeto mObjeto : OATodos.getObjetos()) {


            String eTexto = mObjeto.Identifique("Texto").getValor();

            if (eTexto.length() > 0) {
                String eStatus = mObjeto.Identifique("Status").getValor();

                if (mStatus.contentEquals(eStatus)) {

                    String eData = mObjeto.Identifique("Data").getValor();
                    String eAlterado = mObjeto.Identifique("Alterado").getValor();

                    System.out.println(" \t -> " + mStatusMostrar + " :: " + eData + " <> " + eAlterado + " -->> " + eTexto);


                }

            }

        }


    }


    public ArrayList<String> obterTags(String eTexto){

        ArrayList<String> Tags = new ArrayList<String>();
        int i = 0;
        int o = eTexto.length();

        String mTag = "";

        while (i < o) {
            String l = eTexto.charAt(i) + "";
            if (l.contentEquals("}")) {
                if (mTag.length() > 0) {
                    Tags.add(mTag);
                }
                mTag = "";
            } else if (l.contentEquals(" ")) {
                if (mTag.length() > 0) {
                    Tags.add(mTag);
                }
                mTag = "";
            } else if (l.contentEquals("\t")) {
                if (mTag.length() > 0) {
                    Tags.add(mTag);
                }
                mTag = "";
            } else {
                mTag += l;
            }
            i += 1;
        }

        return Tags;
    }


    public void ListarTag(String mTag) {

        System.out.println("");

        String a = "########################### TAG : " + mTag + " " ;

        System.out.println(a + paraFechar(a.length(),80,"#"));

        System.out.println("");


        File arq = new File(mArquivo);
        LuanDKG mTodo = new LuanDKG();

        if (arq.exists()) {
            mTodo.Abrir(mArquivo);
        }

        Pacote OATodo = mTodo.UnicoPacote("OATodo");
        Pacote OATodos = OATodo.UnicoPacote("Todos");

        for (Objeto mObjeto : OATodos.getObjetos()) {


            String eTexto = mObjeto.Identifique("Texto").getValor();

            if (eTexto.length() > 0) {
                String eStatus = mObjeto.Identifique("Status").getValor();

                ArrayList<String> mTags = obterTags(mObjeto.Identifique("Tags").getValor());
                if (mTags.contains(mTag)){

                    String eData = mObjeto.Identifique("Data").getValor();

                    System.out.println(" \t -> " + eData + " -->> " + eTexto + "  { " + mObjeto.Identifique("Tags").getValor() + "}");

                }

            }

        }

        System.out.println("");

    }

    public static String getDataHora() {

        Calendar c = Calendar.getInstance();

        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH) + 1;
        int ano = c.get(Calendar.YEAR);

        String sdia = String.valueOf(dia);
        String smes = String.valueOf(mes);

        if (sdia.length() == 1) {
            sdia = "0" + sdia;
        }
        if (smes.length() == 1) {
            smes = "0" + smes;
        }

        return ano + " " + smes + " " + sdia;

    }

    private static int get_string_num(String v, int min) {

        int vi = 0;

        if (v.length() == 0) {
            vi = min;
        }

        try {
            vi = Integer.parseInt(v);
            if (vi < min) {
                vi = min;
            }
        } catch (Exception e) {
            vi = min;
        }

        return vi;

    }

}
