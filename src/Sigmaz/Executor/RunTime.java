package Sigmaz.Executor;

import Sigmaz.Utils.AST;
import Sigmaz.Utils.Documentador;
import Sigmaz.Utils.Documento;

import java.util.ArrayList;

public class RunTime {

    private ArrayList<AST> mASTS;
    private ArrayList<String> mErros;
    private DataTypes mDataTypes;

    private Escopo mEscopoGlobal;

    public RunTime() {

        mASTS = new ArrayList<>();

        mErros = new ArrayList<>();

        mDataTypes = new DataTypes();

        mEscopoGlobal=null;

    }

    public ArrayList<AST> getASTS() {
        return mASTS;
    }

    public ArrayList<String> getErros() {
        return mErros;
    }

    public void init(String eArquivo) {

        mASTS.clear();
        mErros.clear();

        Documentador DC = new Documentador();

        mASTS = DC.Decompilar(eArquivo);


    }


    public Escopo getEscopoGlobal(){
        return mEscopoGlobal;
    }

    public void run() {


        Escopo Global = new Escopo(this, null);
        Global.setNome("GLOBAL");

        mEscopoGlobal=Global;

        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                for (AST ASTC : ASTCGlobal.getASTS()) {


                    if (ASTC.mesmoTipo("FUNCTION")) {
                        Global.guardar(ASTC);
                    } else if (ASTC.mesmoTipo("ACTION")) {
                        Global.guardar(ASTC);
                    }
                }

                for (AST ASTC : ASTCGlobal.getASTS()) {

                    if (this.getErros().size() > 0) {
                        return;
                    }

                    if (ASTC.mesmoTipo("DEFINE")) {

                        Run_Def mAST = new Run_Def(this, Global);
                        mAST.init(ASTC);


                    } else if (ASTC.mesmoTipo("MOCKIZ")) {


                        Run_Moc mAST = new Run_Moc(this, Global);
                        mAST.init(ASTC);

                    } else if (ASTC.mesmoTipo("INVOKE")) {

                        Run_Invoke mAST = new Run_Invoke(this, Global);
                        mAST.init(ASTC);

                    }

                }

                for (AST ASTC : ASTCGlobal.getASTS()) {

                    if (ASTC.mesmoTipo("CALL")) {


                        AST mSending = ASTC.getBranch("SENDING");

                        Run_Action mAST = new Run_Action(this, Global);
                        mAST.init(mSending);

                    }


                    if (mErros.size() > 0) {
                        break;
                    }

                }


            }
        }

        if (mASTS.size() == 0) {
            mErros.add("Sigmaz Vazio !");
        }


    }

    public boolean isPrimitivo(String eTipo) {
        return mDataTypes.isPrimitivo(eTipo);
    }

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
