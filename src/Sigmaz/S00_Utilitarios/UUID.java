package Sigmaz.S00_Utilitarios;

import java.util.Random;

public class UUID {

    private final String mALFABETO = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private int mTam;

    public UUID() {
        mTam = mALFABETO.length();
    }

    public String getUUID() {
        String ret = "";

        Random rd = new Random();

        for (int i = 0; i < 5; i++) {

            ret += mALFABETO.charAt(rd.nextInt(mTam));

        }

        ret+=".";

        for (int i = 0; i < 5; i++) {

            ret += mALFABETO.charAt(rd.nextInt(mTam));

        }

        ret+=".";

        for (int i = 0; i < 5; i++) {

            ret += mALFABETO.charAt(rd.nextInt(mTam));

        }


        ret+="-";

        for (int i = 0; i < 3; i++) {

            ret += mALFABETO.charAt(rd.nextInt(mTam));

        }


        return ret;

    }

}
