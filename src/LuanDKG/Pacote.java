package LuanDKG;

import java.util.ArrayList;

public class Pacote {

    private String mNome;
    private ArrayList<Identificador> mIdentificadores;
    private ArrayList<Comentario> mComentarios;
    private ArrayList<Lista> mListas;
    private ArrayList<Objeto> mObjetos;

    private ArrayList<Pacote> mPacotes;

    private boolean mLinear;

    public Pacote(String eNome) {
        mNome = eNome;

        mPacotes = new ArrayList<Pacote>();
        mIdentificadores = new ArrayList<Identificador>();
        mComentarios = new ArrayList<Comentario>();
        mObjetos = new ArrayList<Objeto>();

        mListas = new ArrayList<Lista>();

        mLinear = false;
    }

    public void setNome(String eNome) {
        mNome = eNome;
    }

    public String getNome() {
        return mNome;
    }


    public int contagem() {

        int t = 1;


        for (Identificador ePacote : mIdentificadores) {
            t += 2;
        }

        for (Comentario eComentario : mComentarios) {
            t += 2;
        }

        for (Objeto mObjeto : mObjetos) {
            t += mObjeto.contagem();
        }

        for (Lista mLista : mListas) {
            t += mLista.contagem();
        }

        for (Pacote ePacote : mPacotes) {
            t += ePacote.contagem();
        }


        return t;

    }


    public Identificador Identifique(String eNome) {

        boolean enc = false;
        Identificador ret = null;

        for (Identificador mIdentificador : mIdentificadores) {

            if (mIdentificador.getNome().contentEquals(eNome)) {
                enc = true;
                ret = mIdentificador;
                break;
            }

        }

        if (enc == false) {
            ret = new Identificador(eNome, "");
            mIdentificadores.add(ret);
        }

        return ret;

    }

    public Identificador Identifique(String eNome, short eValor) {

        return this.Identifique(eNome, String.valueOf(eValor));

    }

    public Identificador Identifique(String eNome, int eValor) {

        return this.Identifique(eNome, String.valueOf(eValor));

    }

    public Identificador Identifique(String eNome, float eValor) {

        return this.Identifique(eNome, String.valueOf(eValor));

    }

    public Identificador Identifique(String eNome, double eValor) {

        return this.Identifique(eNome, String.valueOf(eValor));

    }

    public Identificador Identifique(String eNome, boolean eValor) {

        return this.Identifique(eNome, String.valueOf(eValor));

    }

    public Identificador Identifique(String eNome, String eValor) {

        boolean enc = false;
        Identificador ret = null;

        for (Identificador mIdentificador : mIdentificadores) {

            if (mIdentificador.getNome().contentEquals(eNome)) {
                enc = true;
                ret = mIdentificador;
                ret.setValor(eValor);
                break;
            }

        }

        if (enc == false) {
            ret = new Identificador(eNome, eValor);
            mIdentificadores.add(ret);
        }

        return ret;
    }

    public ArrayList<Identificador> getIdentificadores() {
        return mIdentificadores;
    }

    public ArrayList<Pacote> getPacotes() {
        return mPacotes;
    }

    public ArrayList<Comentario> getComentarios() {
        return mComentarios;
    }

    public ArrayList<Lista> getListas() {
        return mListas;
    }

    public ArrayList<Objeto> getObjetos() {
        return mObjetos;
    }

    public Pacote CriarPacote(String eNome) {

        Pacote ret = new Pacote(eNome);
        mPacotes.add(ret);

        return ret;
    }

    public Pacote UnicoPacote(String eNome) {

        boolean enc = false;
        Pacote ret = null;

        for (Pacote mPacote : mPacotes) {

            if (mPacote.getNome().contentEquals(eNome)) {
                enc = true;
                ret = mPacote;
                break;
            }

        }

        if (enc == false) {
            ret = new Pacote(eNome);
            mPacotes.add(ret);
        }

        return ret;
    }

    public void RemoverPacote(Pacote ePacote) {

        for (Pacote mPacote : mPacotes) {

            if (mPacote == ePacote) {
                mPacotes.remove(ePacote);
                break;
            }

        }

    }

    public void RemoverPacotePorNome(String eNome) {

        for (Pacote mPacote : mPacotes) {

            if (mPacote.getNome().contentEquals(eNome)) {
                mPacotes.remove(mPacote);
                break;
            }

        }

    }

    public void RemoverIdentificador(Identificador eIdentificador) {

        for (Identificador mIdentificador : mIdentificadores) {

            if (mIdentificador == eIdentificador) {
                mIdentificadores.remove(eIdentificador);
                break;
            }

        }

    }

    public boolean IdentificadorExiste(String eIdentificadorNome) {

        boolean ret = false;

        for (Identificador mIdentificador : mIdentificadores) {

            if (mIdentificador.getNome().contentEquals(eIdentificadorNome)) {
                ret = true;
                break;
            }

        }
        return ret;
    }

    public void RemoverIdentificadorPorNome(String eIdentificador) {

        for (Identificador mIdentificador : mIdentificadores) {

            if (mIdentificador.getNome().contentEquals(eIdentificador)) {
                mIdentificadores.remove(mIdentificador);
                break;
            }

        }

    }

    public Comentario Comentar(String eNome, String eValor) {

        boolean enc = false;
        Comentario ret = null;

        for (Comentario mComentario : mComentarios) {

            if (mComentario.getNome().contentEquals(eNome)) {
                enc = true;
                ret = mComentario;
                ret.setValor(eValor);
                break;
            }

        }

        if (enc == false) {
            ret = new Comentario(eNome, eValor);
            mComentarios.add(ret);
        }

        return ret;
    }

    public void RemoverComentario(Comentario eComentario) {

        for (Comentario mComentario : mComentarios) {

            if (mComentario == eComentario) {
                mComentarios.remove(eComentario);
                break;
            }

        }

    }

    public void RemoverComentarioPorNome(String eComentario) {

        for (Comentario mComentario : mComentarios) {

            if (mComentario.getNome().contentEquals(eComentario)) {
                mComentarios.remove(mComentario);
                break;
            }

        }

    }

    // LISTAS

    public Lista CriarLista(String eNome) {

        Lista ret = new Lista(eNome);
        mListas.add(ret);

        return ret;
    }

    public Lista UnicaLista(String eNome) {

        boolean enc = false;
        Lista ret = null;

        for (Lista mLista : mListas) {

            if (mLista.getNome().contentEquals(eNome)) {
                enc = true;
                ret = mLista;
                break;
            }

        }

        if (enc == false) {
            ret = new Lista(eNome);
            mListas.add(ret);
        }

        return ret;
    }

    public void RemoverLista(Lista eLista) {

        for (Lista mLista : mListas) {

            if (mLista == eLista) {
                mListas.remove(eLista);
                break;
            }

        }

    }

    public void RemoverListaPorNome(String eNome) {

        for (Lista mLista : mListas) {

            if (mLista.getNome().contentEquals(eNome)) {
                mListas.remove(mLista);
                break;
            }

        }

    }

    // OBJETO

    public Objeto CriarObjeto(String eNome) {

        Objeto ret = new Objeto(eNome);
        mObjetos.add(ret);

        return ret;
    }

    public Objeto UnicoObjeto(String eNome) {

        boolean enc = false;
        Objeto ret = null;

        for (Objeto mObjeto : mObjetos) {

            if (mObjeto.getNome().contentEquals(eNome)) {
                enc = true;
                ret = mObjeto;
                break;
            }

        }

        if (enc == false) {
            ret = new Objeto(eNome);
            mObjetos.add(ret);
        }

        return ret;
    }

    public void RemoverObjeto(Objeto eObjeto) {

        for (Objeto mObjeto : mObjetos) {

            if (mObjeto == eObjeto) {
                mObjetos.remove(eObjeto);
                break;
            }

        }

    }

    public void RemoverObjetoPorNome(String eNome) {

        for (Objeto mObjeto : mObjetos) {

            if (mObjeto.getNome().contentEquals(eNome)) {
                mObjetos.remove(mObjeto);
                break;
            }

        }

    }

    // EXTRA

    public Pacote PacoteComAtributoUnico(String eNomePacote, String eNomeIdentificador, String eValor) {

        Pacote ret = null;
        boolean enc = false;

        for (Pacote PacoteC : getPacotes()) {

            if (PacoteC.getNome().contentEquals(eNomePacote)) {

                if (PacoteC.IdentificadorExiste(eNomeIdentificador)) {
                    if (PacoteC.Identifique(eNomeIdentificador).getValor().contentEquals(eValor)) {
                        ret = PacoteC;
                        enc = true;
                        break;
                    }
                }

            }

        }

        if (!enc) {

            ret = new Pacote(eNomePacote);

            ret.Identifique(eNomeIdentificador, eValor);

            getPacotes().add(ret);
        }

        return ret;

    }

    public boolean PacoteComAtributo_Existe(String eNomePacote, String eNomeIdentificador, String eValor) {

        boolean enc = false;

        for (Pacote PacoteC : getPacotes()) {

            if (PacoteC.getNome().contentEquals(eNomePacote)) {

                if (PacoteC.IdentificadorExiste(eNomeIdentificador)) {
                    if (PacoteC.Identifique(eNomeIdentificador).getValor().contentEquals(eValor)) {
                        enc = true;
                        break;
                    }
                }

            }

        }

        return enc;

    }

    public void PacoteComAtributo_Remover(String eNomePacote, String eNomeIdentificador, String eValor) {

        for (Pacote PacoteC : getPacotes()) {

            if (PacoteC.getNome().contentEquals(eNomePacote)) {

                if (PacoteC.IdentificadorExiste(eNomeIdentificador)) {
                    if (PacoteC.Identifique(eNomeIdentificador).getValor().contentEquals(eValor)) {
                        getPacotes().remove(PacoteC);
                        break;
                    }
                }

            }

        }

    }

    public Objeto ObjetoComAtributoUnico(String eNomeObjeto, String eNomeIdentificador, String eValor) {

        Objeto ret = null;
        boolean enc = false;

        for (Objeto ObjetoC : getObjetos()) {

            if (ObjetoC.getNome().contentEquals(eNomeObjeto)) {

                if (ObjetoC.IdentificadorExiste(eNomeIdentificador)) {
                    if (ObjetoC.Identifique(eNomeIdentificador).getValor().contentEquals(eValor)) {
                        ret = ObjetoC;
                        enc = true;
                        break;
                    }
                }

            }

        }

        if (!enc) {

            ret = new Objeto(eNomeObjeto);

            ret.Identifique(eNomeIdentificador, eValor);

            getObjetos().add(ret);
        }

        return ret;

    }

    public boolean ObjetoComAtributo_Existe(String eNomeObjeto, String eNomeIdentificador, String eValor) {

        boolean enc = false;

        for (Objeto ObjetoC : getObjetos()) {

            if (ObjetoC.getNome().contentEquals(eNomeObjeto)) {

                if (ObjetoC.IdentificadorExiste(eNomeIdentificador)) {
                    if (ObjetoC.Identifique(eNomeIdentificador).getValor().contentEquals(eValor)) {
                        enc = true;
                        break;
                    }
                }

            }

        }

        return enc;

    }

    public void ObjetoComAtributo_Remover(String eNomeObjeto, String eNomeIdentificador, String eValor) {

        for (Objeto ObjetoC : getObjetos()) {

            if (ObjetoC.getNome().contentEquals(eNomeObjeto)) {

                if (ObjetoC.IdentificadorExiste(eNomeIdentificador)) {
                    if (ObjetoC.Identifique(eNomeIdentificador).getValor().contentEquals(eValor)) {
                        getObjetos().remove(ObjetoC);
                        break;
                    }
                }

            }

        }

    }


    public void salvarLinear() {
        mLinear = true;
    }

}
