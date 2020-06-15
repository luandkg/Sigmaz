package Sigmaz.Executor;

import java.util.ArrayList;

public class DataTypes {

    private ArrayList<String> mPrimitivos;

    public DataTypes() {
        mPrimitivos = new ArrayList<>();

        mPrimitivos.add("string");
        mPrimitivos.add("num");
        mPrimitivos.add("bool");
        mPrimitivos.add("<<ANY>>");

    }

    public boolean isPrimitivo(String eTipo) {

        return mPrimitivos.contains(eTipo);

    }
}
