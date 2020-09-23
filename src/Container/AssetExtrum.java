package Container;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class AssetExtrum {

    private Container mContainer;
    private boolean mIndexado;

    private ArrayList<Listador> mListadores;
    private ArrayList<Biblioteca> mBibliotecas;
    private ArrayList<ArquivoLink> mArquivosLink;
    private ArrayList<PastaLink> mPastasLink;

    public AssetExtrum(Container eContainer) {

        mContainer = eContainer;
        mIndexado = false;

        mArquivosLink = new ArrayList<ArquivoLink>();
        mPastasLink = new ArrayList<PastaLink>();

        mListadores = new ArrayList<Listador>();
        mBibliotecas = new ArrayList<Biblioteca>();

    }

    public void ler() {

        if (!mIndexado) {
            mIndexado = true;

            mArquivosLink.clear();
            mPastasLink.clear();

            mListadores.clear();
            mBibliotecas.clear();

            //   System.out.println("Iniciar Leitura Extrum");

            try {

                RandomAccessFile raf = new RandomAccessFile(new File(mContainer.getArquivo()), "rw");

                FileBinary fu = new FileBinary(raf);

                if (mContainer.temApendice()) {

                    fu.setPonteiro(mContainer.getExtrumPonteiro());


                    byte b1 = fu.readByte();
                    b1 = fu.readByte();

                    while (b1 != (byte) 55) {

                        // System.out.println("B1 : " + b1);


                        if (b1 == (byte) 51) {

                            Listador eListador = new Listador(mContainer, fu.readString());

                            //System.out.println("Dentro Listador : " + eListador.getNome());

                            b1 = fu.readByte();

                            // System.out.println("Dentro Listador : " + b1);

                            while (b1 != (byte) 13) {

                                if (b1 == (byte) 11) {

                                    long eInicio = fu.readLong();
                                    long eFim = fu.readLong();

                                    eListador.adicionar(new Pasta(this.mContainer, new Ponto(11, eInicio, eFim)));

                                    //  System.out.println("\t - Local " + eLocal);
                                }
                                b1 = fu.readByte();
                                if (b1 == (byte) -1) {
                                    break;
                                }
                            }


                            mListadores.add(eListador);


                        } else if (b1 == (byte) 52) {

                            Biblioteca eListador = new Biblioteca(mContainer, fu.readString());

                            // System.out.println("Dentro Biblioteca : " + eListador.getNome());

                            b1 = fu.readByte();

                            // System.out.println("Dentro Biblioteca : " + b1);

                            while (b1 != (byte) 13) {

                                if (b1 == (byte) 11) {
                                    String eLocal = fu.readStringGrande();
                                    eListador.adicionar(eLocal);

                                    //  System.out.println("\t - Local " + eLocal);
                                }
                                b1 = fu.readByte();
                                if (b1 == (byte) -1) {
                                    break;
                                }
                            }

                            b1 = fu.readByte();

                            // System.out.println("Dentro Biblioteca : " + b1);

                            while (b1 != (byte) 13) {

                                if (b1 == (byte) 11) {
                                    String eLocal = fu.readString();
                                    eListador.adicionarExtensao(eLocal);

                                    //   System.out.println("\t - Extensao " + eLocal);
                                }
                                b1 = fu.readByte();
                                if (b1 == (byte) -1) {
                                    break;
                                }
                            }

                            mBibliotecas.add(eListador);

                        } else if (b1 == (byte) 53) {

                            ArquivoLink eArquivoLink = new ArquivoLink(mContainer, fu.readString(),fu.readString(), fu.readLong(), fu.readLong());
                            mArquivosLink.add(eArquivoLink);


                        } else if (b1 == (byte) 54) {

                            PastaLink eArquivoLink = new PastaLink(mContainer, fu.readString(),fu.readString(), fu.readLong(), fu.readLong());
                            mPastasLink.add(eArquivoLink);


                        }

                        b1 = fu.readByte();
                        if (b1 == (byte) -1) {
                            break;
                        }
                    }


                }
                raf.close();

            } catch (IOException e) {

                e.printStackTrace();
            }

        }

    }

    public void limpar() {

        ler();

        mListadores.clear();
        mBibliotecas.clear();
        mArquivosLink.clear();
        mPastasLink.clear();

    }

    public Listador criarListador(String eNome) {

        ler();

        if (!existe(eNome)) {
            Listador mListadorC = new Listador(mContainer, eNome);

            mListadores.add(mListadorC);

            return mListadorC;
        } else {
            throw new IllegalArgumentException("Ja existe um recurso com esse nome : " + eNome);
        }

    }

    public Biblioteca criarBiblioteca(String eNome) {

        ler();

        if (!existe(eNome)) {
            Biblioteca mBiblioteca = new Biblioteca(mContainer, eNome);

            mBibliotecas.add(mBiblioteca);

            return mBiblioteca;
        } else {
            throw new IllegalArgumentException("Ja existe um recurso com esse nome : " + eNome);
        }

    }

    public ArquivoLink criarLinkArquivo(String eNome, Arquivo eLocal) {


        System.out.println(eLocal.getNome());

        ler();

        if (!existe(eNome)) {

            System.out.println("A : " + eLocal.getNome());

            ArquivoLink mArquivoLinkC = new ArquivoLink(mContainer, eNome,eLocal.getNome(), eLocal.getInicio(), eLocal.getFim());
            mArquivosLink.add(mArquivoLinkC);

            return mArquivoLinkC;
        } else {
            throw new IllegalArgumentException("Ja existe um recurso com esse nome : " + eNome);
        }

    }

    public PastaLink criarLinkPasta(String eNome, Pasta eLocal) {


        ler();

        if (!existe(eNome)) {


            PastaLink mArquivoLinkC = new PastaLink(mContainer, eNome,eLocal.getNome(), eLocal.getInicio(), eLocal.getFim());
            mPastasLink.add(mArquivoLinkC);

            return mArquivoLinkC;
        } else {
            throw new IllegalArgumentException("Ja existe um recurso com esse nome : " + eNome);
        }

    }

    public ArrayList<Listador> getListadores() {
        ler();
        return mListadores;
    }

    public ArrayList<Biblioteca> getBibliotecas() {
        ler();
        return mBibliotecas;
    }

    public ArrayList<ArquivoLink> getArquivosLink() {
        ler();
        return mArquivosLink;
    }

    public ArrayList<PastaLink> getPastasLink() {
        ler();
        return mPastasLink;
    }

    public boolean existe(String eNome) {

        ler();

        boolean ret = false;

        for (Listador mListadorC : getListadores()) {
            if (mListadorC.getNome().contentEquals(eNome)) {
                ret = true;
                break;
            }
        }

        for (Biblioteca mListadorC : getBibliotecas()) {
            if (mListadorC.getNome().contentEquals(eNome)) {
                ret = true;
                break;
            }
        }

        for (ArquivoLink mListadorC : getArquivosLink()) {
            if (mListadorC.getNome().contentEquals(eNome)) {
                ret = true;
                break;
            }
        }

        return ret;

    }


    public void salvar() {


        try {

            RandomAccessFile raf = new RandomAccessFile(new File(mContainer.getArquivo()), "rw");

            FileBinary fu = new FileBinary(raf);

            if (mContainer.temApendice()) {

                fu.setPonteiro(mContainer.getExtrumPonteiro());

                fu.writeByte((byte) 50);


                for (Listador mListadorC : getListadores()) {

                    fu.writeByte((byte) 51);
                    fu.writeString(mListadorC.getNome());

                    fu.writeByte((byte) 10);

                    for (Pasta eLocal : mListadorC.getLocais()) {
                        fu.writeByte((byte) 11);
                        fu.writeLong(eLocal.getInicio());
                        fu.writeLong(eLocal.getFim());
                    }

                    fu.writeByte((byte) 13);

                }

                for (Biblioteca mListadorC : getBibliotecas()) {

                    //System.out.println("Criando Bib " + mListadorC.getNome() + " : " + mListadorC.getLocais().size() + " : " + mListadorC.getExtensoes().size());

                    fu.writeByte((byte) 52);
                    fu.writeString(mListadorC.getNome());

                    fu.writeByte((byte) 10);

                    for (String eLocal : mListadorC.getLocais()) {
                        fu.writeByte((byte) 11);
                        fu.writeGrandeString(eLocal);
                    }

                    fu.writeByte((byte) 13);

                    for (String eLocal : mListadorC.getExtensoes()) {
                        fu.writeByte((byte) 11);
                        fu.writeString(eLocal);
                        // System.out.println("Salvando ext : " + eLocal);
                    }

                    fu.writeByte((byte) 13);

                }

                for (ArquivoLink mListadorC : getArquivosLink()) {

                    //System.out.println("Criando Bib " + mListadorC.getNome() + " : " + mListadorC.getLocais().size() + " : " + mListadorC.getExtensoes().size());

                    fu.writeByte((byte) 53);

                    fu.writeString(mListadorC.getNome());
                    fu.writeString(mListadorC.getArquivo().getNome());

                    System.out.println("Criando Arquivo Link : " + mListadorC.getNome());


                    fu.writeLong(mListadorC.getArquivo().getInicio());
                    fu.writeLong(mListadorC.getArquivo().getFim());


                }

                for (PastaLink mListadorC : getPastasLink()) {

                    //System.out.println("Criando Bib " + mListadorC.getNome() + " : " + mListadorC.getLocais().size() + " : " + mListadorC.getExtensoes().size());

                    fu.writeByte((byte) 54);

                    fu.writeString(mListadorC.getNome());
                    fu.writeString(mListadorC.getPasta().getNome());

                    System.out.println("Criando Pasta Link : " + mListadorC.getNome());


                    fu.writeLong(mListadorC.getPasta().getInicio());
                    fu.writeLong(mListadorC.getPasta().getFim());


                }

                fu.writeByte((byte) 55);


            }

            raf.close();

        } catch (IOException e) {

            e.printStackTrace();
        }


    }


}
