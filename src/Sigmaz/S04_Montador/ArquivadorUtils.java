package Sigmaz.S04_Montador;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ArquivadorUtils {


    private RandomAccessFile mFile;
    private String ALFABETO;
    private int ALFABETO_MAX;

    public ArquivadorUtils(String eArquivo) {


        ALFABETO = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789 ()[]{}-.,<>/\\:;'";
        ALFABETO_MAX = ALFABETO.length();

        try {

            mFile = new RandomAccessFile(new File(eArquivo), "rw");
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    public void close() {

        try {

            mFile.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public byte getString(String l) {

        int procurando = 0;
        boolean enc = false;

        while (procurando < ALFABETO_MAX) {

            String pl = String.valueOf(ALFABETO.charAt(procurando));
            if (pl.contentEquals(l)) {
                enc = true;
                break;

            }
            procurando += 1;
        }

        if (!enc) {
            throw new IllegalArgumentException("Char nao encontrado : " + l);
        }

        return (byte) (procurando + 1);
    }

    public String stringFromByte(Byte b) {

        int procurando = 0;
        int pb = ((int) b) - 1;
        String ret = "";

        boolean enc = false;

        while (procurando < ALFABETO_MAX) {

            if (procurando == pb) {
                ret = String.valueOf(ALFABETO.charAt(procurando));
                enc = true;
                break;
            }

            procurando += 1;
        }

        if (!enc) {
            throw new IllegalArgumentException("Byte Char nao encontrado : " + pb);
        }

        return ret;
    }

    public byte[] stringToBytes(String s) {
        byte[] result = new byte[100];

        int tamanho = s.length();

        if (tamanho > 100) {
            throw new IllegalArgumentException("String alem do limite : " + tamanho);
        } else {

            int procurando = 0;

            while (procurando < tamanho) {

                byte v = getString(String.valueOf(s.charAt(procurando)));
                result[procurando] = v;

                procurando += 1;
            }

        }

        return result;
    }


    public byte[] stringPrefixToBytes(String s) {
        byte[] result = new byte[s.length()];

        int tamanho = s.length();


        int procurando = 0;

        while (procurando < tamanho) {

            byte v = getString(String.valueOf(s.charAt(procurando)));
            result[procurando] = v;

            procurando += 1;
        }


        return result;
    }


    public byte[] stringGrandeToBytes(String s) {
        byte[] result = new byte[1024];

        int tamanho = s.length();

        if (tamanho > 1024) {
            throw new IllegalArgumentException("String alem do limite : " + tamanho);
        } else {

            int procurando = 0;

            while (procurando < tamanho) {

                byte v = getString(String.valueOf(s.charAt(procurando)));
                result[procurando] = v;

                procurando += 1;
            }

        }

        return result;
    }

    public byte[] longToBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) (l & 0xFF);
            l >>= 8;
        }
        return result;
    }

    public byte[] intToByte(int value) {
        return new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
    }

    int bytestoInt(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                ((bytes[3] & 0xFF) << 0);
    }


    public long readLong() {
        long result = 0;
        try {

            byte[] lendo = new byte[8];
            for (int i = 0; i < 8; i++) {
                lendo[i] = mFile.readByte();
            }
            for (int i = 0; i < 8; i++) {
                result <<= Long.BYTES;
                result |= (lendo[i] & 0xFF);
            }

        } catch (IOException e) {

        }
        return result;
    }

    public int readInt() {
        int i = 0;
        try {
            i = ((mFile.readByte() & 0xFF) << 24) |
                    ((mFile.readByte() & 0xFF) << 16) |
                    ((mFile.readByte() & 0xFF) << 8) |
                    ((mFile.readByte() & 0xFF) << 0);
        } catch (IOException e) {

        }
        return i;
    }


    public String organizar(byte b) {
        String si = String.valueOf(b);

        if (si.length() == 1) {
            si = "00" + si;
        } else if (si.length() == 2) {
            si = "0" + si;
        }

        return si;
    }

    public void dump() {

        try {

            long tamanho = mFile.length();
            long index = 0;
            int cur = 0;
            int max = 30;

            mFile.seek(0);

            while (index < tamanho) {
                System.out.print(organizar(mFile.readByte()) + " ");
                index += 1;
                cur += 1;
                if (cur >= max) {
                    cur = 0;
                    System.out.print("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void limpar() {

        try {

            long tamanho = mFile.length();
            long index = 0;

            mFile.seek(0);

            while (index < tamanho) {
                mFile.write(0);
                index += 1;

            }
        } catch (IOException e) {

        }

    }

    public void writeInt(int l) {

        try {
            mFile.write(intToByte(l));
        } catch (IOException e) {

        }

    }


    public void writeLong(long l) {

        try {
            mFile.write(longToBytes(l));
        } catch (IOException e) {

        }

    }

    public void writeString(String s) {

        try {
            mFile.write(stringToBytes(s));
        } catch (IOException e) {

        }

    }

    public void writeStringPrefix(String s) {

        try {
            mFile.write(stringPrefixToBytes(s));
        } catch (IOException e) {

        }

    }


    public void writeGrandeString(String s) {

        try {
            mFile.write(stringGrandeToBytes(s));
        } catch (IOException e) {

        }

    }

    public long getLength() {
        try {
            return mFile.length();
        } catch (IOException e) {
            return -1;
        }
    }

    public String readString() {

        String ret = "";
        byte[] result = new byte[100];

        try {

            long tamanho = mFile.length();
            long index = mFile.getFilePointer();

            int cur = 0;
            int max = 100;

            while (index < tamanho) {

                if (cur < max) {

                    result[cur] = mFile.readByte();

                } else {
                    break;
                }

                // System.out.println("Cur : " + cur + " -->> " + result[cur]);

                index += 1;
                cur += 1;

            }
        } catch (IOException e) {

        }

        int eIndex = 0;
        while (eIndex < 100) {
            byte i = result[eIndex];
            // System.out.println("Passando : " + i);
            if (i == 0) {
                break;
            } else {
                ret += stringFromByte(i);
            }
            eIndex += 1;
        }

        return ret;
    }

    public String readStringPrefix(int eTam) {

        String ret = "";
        byte[] result = new byte[eTam];

        try {

            long tamanho = mFile.length();
            long index = mFile.getFilePointer();

            int cur = 0;
            int max = eTam;

            while (index < tamanho) {

                if (cur < max) {

                    result[cur] = mFile.readByte();

                } else {
                    break;
                }

                // System.out.println("Cur : " + cur + " -->> " + result[cur]);

                index += 1;
                cur += 1;

            }
        } catch (IOException e) {

        }

        int eIndex = 0;
        while (eIndex < eTam) {
            byte i = result[eIndex];
            // System.out.println("Passando : " + i);
            if (i == 0) {
                break;
            } else {
                ret += stringFromByte(i);
            }
            eIndex += 1;
        }

        return ret;
    }

    public String readStringGrande() {

        String ret = "";
        byte[] result = new byte[1024];

        try {

            long tamanho = mFile.length();
            long index = mFile.getFilePointer();

            int cur = 0;
            int max = 1024;

            while (index < tamanho) {

                if (cur < max) {

                    result[cur] = mFile.readByte();

                } else {
                    break;
                }

                // System.out.println("Cur : " + cur + " -->> " + result[cur]);

                index += 1;
                cur += 1;

            }
        } catch (IOException e) {

        }

        int eIndex = 0;
        while (eIndex < 1024) {
            byte i = result[eIndex];
            // System.out.println("Passando : " + i);
            if (i == 0) {
                break;
            } else {
                ret += stringFromByte(i);
            }
            eIndex += 1;
        }

        return ret;
    }

    public void inicio() {
        try {
            mFile.seek(0);
        } catch (IOException e) {

        }
    }

    public byte readByte() {
        try {
            return mFile.readByte();
        } catch (IOException e) {
            return -1;
        }
    }

    public void writeByte(Byte eByte) {
        try {
            mFile.writeByte(eByte);
        } catch (IOException e) {

        }
    }

    public long getPonteiro() {
        try {
            return mFile.getFilePointer();
        } catch (IOException e) {
            return -1;
        }
    }

    public void setPonteiro(long p) {
        try {
            mFile.seek(p);
        } catch (IOException e) {
        }
    }

    public int organizarByteInt(byte b) {

        if (b >= 0) {
            return (int) b;
        } else {
            return 256 + (int) b;
        }

    }
}