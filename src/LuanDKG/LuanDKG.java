package LuanDKG;

import java.util.ArrayList;

public class LuanDKG {

    private ArrayList<Pacote> mPacotes;
    private ArrayList<String> mErros;

    public LuanDKG() {

        mPacotes = new ArrayList<Pacote>();
        mErros = new ArrayList<String>();

    }


    public ArrayList<Pacote> getPacotes() {
        return mPacotes;
    }

    public ArrayList<String> getErros() {
        return mErros;
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

    public String toString() {

        ITexto ITextoC = new ITexto();
        Salvamento SalvamentoC = new Salvamento();

        SalvamentoC.Pacote_Listar(ITextoC, "   ", this.getPacotes());

        return ITextoC.toString();
    }


    public int contagem() {

        int t = 0;

        for (Pacote ePacote : mPacotes) {
            t += ePacote.contagem();
        }


        return t;

    }


    public String gerarReduzido() {

        ITexto ITextoC = new ITexto();
        SalvamentoReduzido SalvamentoC = new SalvamentoReduzido();

        SalvamentoC.Pacote_Listar(ITextoC, this.getPacotes());

        return ITextoC.toString();
    }

    // IO

    public void Abrir(String eArquivo) {

        Parser ParserC = new Parser();

        ParserC.Parse(Texto.Ler(eArquivo), this);

    }


    public void Parser(String eConteudo) {

        mErros.clear();

        Parser ParserC = new Parser();

        ParserC.Parse(eConteudo, this);

        for (String e : ParserC.getErros()) {
            mErros.add(e);
        }
    }

    public void Salvar(String eArquivo) {

        Texto.Escrever(eArquivo, this.toString());

    }

    public void SalvarReduzido(String eArquivo) {

        Texto.Escrever(eArquivo, this.gerarReduzido());

    }

    public String GerarString() {
        return this.toString();
    }

    // HIERARQUIZE

    public String Hierarquize() {
        ITexto ITextoC = new ITexto();
        Hierarquizador HierarquizadorC = new Hierarquizador();

        HierarquizadorC.Hierarquizar(ITextoC, "   ", this.getPacotes());

        return ITextoC.toString();

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

}
