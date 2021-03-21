package Sigmaz.S08_Utilitarios;

import java.util.ArrayList;

public class ASTImpressor {

    private ArrayList<AST> mASTS;

    public ASTImpressor() {
        mASTS = new ArrayList<AST>();
    }

    public void init(ArrayList<AST> eASTS) {
        mASTS = eASTS;
    }

    public ArrayList<AST> getASTS(){return mASTS;}

    public String getArvoreDeInstrucoes() {

        Documento DocumentoC = new Documento();

        DocumentoC.adicionarLinha("");

        for (AST a : getASTS()) {

            if (a.getValor().length() > 0) {
                DocumentoC.adicionarLinha(" " + a.getTipo() + " -> " + a.getNome() + " : " + a.getValor());
            } else {
                DocumentoC.adicionarLinha(" " + a.getTipo() + " -> " + a.getNome());
            }

            SubArvoreDeInstrucoes("   ", a, DocumentoC);

        }

        DocumentoC.adicionarLinha("");

        return DocumentoC.getConteudo();
    }

    private void SubArvoreDeInstrucoes(String ePref, AST ASTC, Documento DocumentoC) {

        for (AST a : ASTC.getASTS()) {

            if (a.getValor().length() > 0) {
                DocumentoC.adicionarLinha(ePref + a.getTipo() + " -> " + a.getNome() + " : " + a.getValor());

            } else {
                DocumentoC.adicionarLinha(ePref + a.getTipo() + " -> " + a.getNome());

            }

            SubArvoreDeInstrucoes(ePref + "   ", a, DocumentoC);

        }

    }


}
