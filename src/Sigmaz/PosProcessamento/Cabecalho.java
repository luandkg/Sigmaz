package Sigmaz.PosProcessamento;

import Sigmaz.Compilador.Compiler;
import Sigmaz.Utils.AST;
import Sigmaz.Utils.UUID;

import java.util.ArrayList;

public class Cabecalho {


    private ArrayList<String> mAutores;
    private String mVersao;
    private String mCompanhia;

    private String mPreProcessor_Nome;
    private String mLexer_Nome;
    private String mParser_Nome;
    private String mCompiler_Nome;
    private String mPosProcessor_Nome;

    private String mPreProcessor_UUID;
    private String mLexer_UUID;
    private String mParser_UUID;
    private String mCompiler_UUID;
    private String mPosProcessor_UUID;

    public Cabecalho() {

        mAutores = new ArrayList<String>();
        mVersao = "";
        mCompanhia = "";

        mPreProcessor_Nome = "Zettacor";
        mLexer_Nome = "Alfaxer";
        mParser_Nome = "Betagrarser";
        mCompiler_Nome = "Gamaller";
        mPosProcessor_Nome = "Deltaquor";


        mPreProcessor_UUID = "AAZBJ.LLCXZ.47TGC-5QQ";
        mLexer_UUID = "AWRCF.UJ3ML.RLXLX-QZN";
        mParser_UUID = "IXBQV.CNVMZ.NMRH2-59O";
        mCompiler_UUID = "JYJRF.UOKYL.ZASEI-KKS";
        mPosProcessor_UUID = "PDPXQ.HBLAQ.TAGSI-7OC";

    }


    public void autor_adicionar(String eAutor) {

        mAutores.add(eAutor);

    }

    public ArrayList<String> getAutores() {
        return mAutores;
    }

    public void setVersao(String eVersao) {

        mVersao = eVersao;

    }

    public void setCompanhia(String eCompanhia) {

        mCompanhia = eCompanhia;

    }

    public String getVersao() {
        return mVersao;
    }

    public String getCompanhia() {
        return mCompanhia;
    }


    public String getPreProcessor() {
        return mPreProcessor_Nome;
    }

    public String getLexer() {
        return mLexer_Nome;
    }

    public String getParser() {
        return mParser_Nome;
    }

    public String getCompiler() {
        return mCompiler_Nome;
    }

    public String getPosProcessor() {
        return mPosProcessor_Nome;
    }

    public String getPreProcessor_UUID() {
        return mPreProcessor_UUID;
    }

    public String getLexer_UUID() {
        return mLexer_UUID;
    }

    public String getParser_UUID() {
        return mParser_UUID;
    }

    public String getCompiler_UUID() {
        return mCompiler_UUID;
    }

    public String getPosProcessor_UUID() {
        return mPosProcessor_UUID;
    }


}
