package Sigmaz.S00_Utilitarios;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import Container.Container;
import Container.Creator;
import Container.Arquivo;

import LuanDKG.LuanDKG;
import LuanDKG.Pacote;

public class Documentador {


    public String mapaObjeto(String eArquivo) {

        String saida = "";

        Path dpath = Paths.get(eArquivo);


        try {
            byte[] l = Files.readAllBytes(dpath);

            int li = 0;
            int lo = l.length;

            int d = 0;

            while (li < lo) {
                int novo = (int) l[li];

                saida += " " + novo;


                if (d >= 50) {
                    d = 0;
                    saida += "\n";
                }

                li += 1;
                d += 1;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return saida;

    }

    public String tamanhoObjeto(String eArquivo) {

        File file = new File(eArquivo);

        long t = file.length();


        return t + "";
    }


    public void compilar(ArrayList<AST> lsAST, String eArquivo) {


        LuanDKG DocumentoC = new LuanDKG();

        for (AST a : lsAST) {

            Pacote ePacote = new Pacote(a.getTipo());
            ePacote.Identifique("Nome", a.getNome());
            ePacote.Identifique("Valor", a.getValor());
            DocumentoC.getPacotes().add(ePacote);

            subASTPackage(ePacote, a);

        }

        //DocumentoC.Salvar("res/ASTPackage.txt");

        String eDocumento = DocumentoC.gerarReduzido();


        byte[] bytes = eDocumento.getBytes(StandardCharsets.UTF_8);

        int t = bytes.length;
        //System.out.println("Tam : " + t);

        int i = 0;
        int o = eDocumento.length();

        int auxilador = 53;

        int mCrifrador[] = new int[5];

        mCrifrador[0] = 10;
        mCrifrador[1] = 56;
        mCrifrador[2] = 130;
        mCrifrador[3] = 22;
        mCrifrador[4] = 12;

        int ic = 0;
        int oc = 5;


        while (i < o) {
            int novo = (int) bytes[i];

            //novo += auxilador;

            novo += mCrifrador[ic];
            ic += 1;
            if (ic == oc) {
                ic = 0;
            }

            if (novo >= 255) {
                novo -= 255;
            }


            bytes[i] = (byte) novo;
            i += 1;
        }

        Path path = Paths.get(eArquivo);
        try {


            File arq = new File(eArquivo);
            if (arq.exists()) {
                arq.delete();
            }


            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void compilar_futuro(ArrayList<AST> lsAST, String eArquivo) {


        LuanDKG DocumentoC = new LuanDKG();

        for (AST a : lsAST) {

            Pacote ePacote = new Pacote(a.getTipo());
            ePacote.Identifique("Nome", a.getNome());
            ePacote.Identifique("Valor", a.getValor());
            DocumentoC.getPacotes().add(ePacote);

            subASTPackage(ePacote, a);

        }

        //DocumentoC.Salvar("res/ASTPackage.txt");

        String eDocumento = DocumentoC.gerarReduzido();


        byte[] bytes = eDocumento.getBytes(StandardCharsets.UTF_8);

        int t = bytes.length;
        //System.out.println("Tam : " + t);

        int i = 0;
        int o = eDocumento.length();

        int auxilador = 53;

        int mCrifrador[] = new int[5];

        mCrifrador[0] = 10;
        mCrifrador[1] = 56;
        mCrifrador[2] = 130;
        mCrifrador[3] = 22;
        mCrifrador[4] = 12;

        int ic = 0;
        int oc = 5;


        while (i < o) {
            int novo = (int) bytes[i];

            //novo += auxilador;

            novo += mCrifrador[ic];
            ic += 1;
            if (ic == oc) {
                ic = 0;
            }

            if (novo >= 255) {
                novo -= 255;
            }


            bytes[i] = (byte) novo;
            i += 1;
        }

        Path path = Paths.get(eArquivo);
        // try {


        File arq = new File(eArquivo);
        if (arq.exists()) {
            arq.delete();
        }


        //    Files.write(path, bytes);
        // } catch (IOException e) {
        //      e.printStackTrace();
        //  }

        ArrayList<ArquivoAssociativo> mArquivos = new ArrayList<ArquivoAssociativo>();

        ArquivoAssociativo mSigmaz = new ArquivoAssociativo("SIGMAZ", "res/build/123-456-789.a");

        ArquivoAssociativo m1 = new ArquivoAssociativo("Sigmaz.defines", "res/build/789-456-789.a");
        ArquivoAssociativo m11 = new ArquivoAssociativo("Sigmaz.mockizes", "res/build/789-456-789.a");

        ArquivoAssociativo m2 = new ArquivoAssociativo("Sigmaz.functions", "res/build/456-456-789.a");
        ArquivoAssociativo m3 = new ArquivoAssociativo("Sigmaz.actions", "res/build/789-456-789.a");

        ArquivoAssociativo m4 = new ArquivoAssociativo("Sigmaz.operators", "res/build/789-456-789.a");
        ArquivoAssociativo m5 = new ArquivoAssociativo("Sigmaz.directors", "res/build/789-456-789.a");


        mArquivos.add(mSigmaz);
        mArquivos.add(m1);
        mArquivos.add(m11);
        mArquivos.add(m2);
        mArquivos.add(m3);
        mArquivos.add(m4);
        mArquivos.add(m5);

        for (AST a : lsAST) {
            for (AST mPk : a.getASTS()) {

                if (mPk.mesmoTipo("PACKAGE")) {
                    ArquivoAssociativo m7 = new ArquivoAssociativo(mPk.getNome() + ".package", "res/build/789-456-789.a");
                    mArquivos.add(m7);
                }

            }
        }

        try {
            Files.write(Paths.get(mSigmaz.getArquivo()), bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Creator mCreator = new Creator();
        mCreator.criar(eArquivo, mArquivos);

        Container mContainer = new Container();
        mContainer.abrir(eArquivo);

        for (Arquivo eA : mContainer.getArquivos()) {

            System.out.println("Container Arquivo :: " + eA.getNome() + " :: " + eA.getTamanho());

        }


        for (ArquivoAssociativo eA : mArquivos) {

            File ma = new File(eA.getArquivo());
            if (ma.exists()) {
                ma.delete();
            }
        }


    }


    private void subASTPackage(Pacote ePacotePai, AST eAST) {

        for (AST a : eAST.getASTS()) {

            Pacote ePacote = new Pacote(a.getTipo());
            ePacote.Identifique("Nome", a.getNome());
            ePacote.Identifique("Valor", a.getValor());
            ePacotePai.getPacotes().add(ePacote);

            subASTPackage(ePacote, a);

        }

    }

    public ArrayList<AST> fromASTPackage(String eConteudo) {

        LuanDKG DocumentoC = new LuanDKG();
        DocumentoC.Parser(eConteudo);


        ArrayList<AST> ASTSaida = new ArrayList<AST>();

        for (Pacote mPacote : DocumentoC.getPacotes()) {

            String aNome = mPacote.Identifique("Nome").getValor();
            String aValor = mPacote.Identifique("Valor").getValor();

            AST mAST = new AST(mPacote.getNome());
            mAST.setNome(aNome);
            mAST.setValor(aValor);
            ASTSaida.add(mAST);

            subFromASTPackage(mAST, mPacote);
        }


        return ASTSaida;
    }

    private void subFromASTPackage(AST eAST, Pacote ePacotePai) {

        for (Pacote mPacote : ePacotePai.getPacotes()) {

            String aNome = mPacote.Identifique("Nome").getValor();
            String aValor = mPacote.Identifique("Valor").getValor();

            AST mAST = new AST(mPacote.getNome());
            mAST.setNome(aNome);
            mAST.setValor(aValor);
            eAST.getASTS().add(mAST);


            subFromASTPackage(mAST, mPacote);

        }

    }

    public ArrayList<AST> Decompilar(String eArquivo) {

        ArrayList<AST> ASTSaida = new ArrayList<AST>();


        String saida = "";


        Path dpath = Paths.get(eArquivo);
        int auxilador = 53;


        int mCrifrador[] = new int[5];

        mCrifrador[0] = 10;
        mCrifrador[1] = 56;
        mCrifrador[2] = 130;
        mCrifrador[3] = 22;
        mCrifrador[4] = 12;

        int ic = 0;
        int oc = 5;

        try {
            byte[] l = Files.readAllBytes(dpath);

            int li = 0;
            int lo = l.length;

            while (li < lo) {
                int novo = (int) l[li];

                //	novo -= auxilador;

                novo -= mCrifrador[ic];
                ic += 1;
                if (ic == oc) {
                    ic = 0;
                }


                if (novo < 0) {
                    novo += 256;
                }


                l[li] = (byte) novo;
                li += 1;
            }


            saida = new String(l, StandardCharsets.UTF_8);


            // System.out.println("Decompilado : " + saida);


        } catch (IOException e) {
            e.printStackTrace();
        }


        ASTSaida = fromASTPackage(saida);

        return ASTSaida;

    }

    public ArrayList<AST> DecompilarFuturo(String eArquivo) {

        ArrayList<AST> ASTSaida = new ArrayList<AST>();

        Container mContainer = new Container();
        mContainer.abrir(eArquivo);

        String saida = "";

        for (Arquivo eA : mContainer.getArquivos()) {

            System.out.println("\t - " + eArquivo + " :: " + eA.getNome() + " :: " + eA.getTamanho());

        }

        for (Arquivo eA : mContainer.getArquivos()) {


            if (eA.getNome().contentEquals("SIGMAZ")) {

                //  Path dpath = Paths.get(eArquivo);
                int auxilador = 53;


                int mCrifrador[] = new int[5];

                mCrifrador[0] = 10;
                mCrifrador[1] = 56;
                mCrifrador[2] = 130;
                mCrifrador[3] = 22;
                mCrifrador[4] = 12;

                int ic = 0;
                int oc = 5;

                //  try {
                byte[] l = eA.getBytes();

                int li = 0;
                int lo = l.length;

                while (li < lo) {
                    int novo = (int) l[li];

                    //	novo -= auxilador;

                    novo -= mCrifrador[ic];
                    ic += 1;
                    if (ic == oc) {
                        ic = 0;
                    }


                    if (novo < 0) {
                        novo += 256;
                    }


                    l[li] = (byte) novo;
                    li += 1;
                }


                saida = new String(l, StandardCharsets.UTF_8);


                //	System.out.println("Decompilado : " + saida);


                // } catch (IOException e) {
                //    e.printStackTrace();
                // }


                break;
            }

        }


        ASTSaida = fromASTPackage(saida);

        return ASTSaida;

    }

}