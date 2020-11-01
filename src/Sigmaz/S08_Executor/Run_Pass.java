package Sigmaz.S08_Executor;

public class Run_Pass {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public Run_Pass(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Pass";


    }


    public void passarParametroByValue(String eNome, Item eItem) {

        if (eItem.getIsEstrutura()) {

            if (eItem.getNulo()) {
                mEscopo.criarParametroStructNula(eNome, eItem.getTipo());
            } else {
                mEscopo.criarParametroStruct(eNome, eItem.getTipo(), eItem.getValor(mRunTime, mEscopo));
            }


        } else {

            if (eItem.getNulo()) {
                mEscopo.criarParametroNulo(eNome, eItem.getTipo());
            } else {
                mEscopo.criarParametro(eNome, eItem.getTipo(), eItem.getValor(mRunTime, mEscopo));
            }


        }

    }

    public void passarParametroByRef(String eNome, Item eItem) {

//        System.out.println("Ref :: " + eNome + " de " + eItem.getReferencia().getNome());
        if (!eItem.getIsReferenciavel()) {
            mRunTime.getErros().add("Nao foi possivel referenciar : " + eNome);
            return;
        }

        if (eItem.getIsEstrutura()) {

            if (eItem.getNulo()) {
                if (eItem.getIsReferenciavel()) {
                    mEscopo.criarParametroStructNula(eNome, eItem.getTipo());
                } else {
                    mEscopo.criarParametroStructNula(eNome, eItem.getTipo());
                }
            } else {
                if (eItem.getIsReferenciavel()) {
                    mEscopo.criarParametroStruct(eNome, eItem.getTipo(), eItem.getValor(mRunTime, mEscopo));
                } else {
                    mEscopo.criarParametroStruct(eNome, eItem.getTipo(), eItem.getValor(mRunTime, mEscopo));
                }
            }

        } else {

            if (eItem.getNulo()) {
                if (eItem.getIsReferenciavel()) {
                    mEscopo.criarParametroNulo(eNome, eItem.getTipo());
                } else {
                    mEscopo.criarParametroNulo(eNome, eItem.getTipo());
                }
            } else {
                if (eItem.getIsReferenciavel()) {
                    mEscopo.criarParametro(eNome, eItem.getTipo(), eItem.getValor(mRunTime, mEscopo));
                } else {
                    mEscopo.criarParametro(eNome, eItem.getTipo(), eItem.getValor(mRunTime, mEscopo));
                }
            }


        }

        referenciar(eNome, eItem.getReferencia());

    }


    public void passarParametroByRefObrigatorio(String eNome, Item eItem) {

//        System.out.println("Ref :: " + eNome + " de " + eItem.getReferencia().getNome());

        if (eItem.getIsEstrutura()) {

            if (eItem.getNulo()) {
                if (eItem.getIsReferenciavel()) {
                    mEscopo.criarParametroStructNula(eNome, eItem.getTipo());
                } else {
                    mEscopo.criarParametroStructNula(eNome, eItem.getTipo());
                }
            } else {
                if (eItem.getIsReferenciavel()) {
                    mEscopo.criarParametroStruct(eNome, eItem.getTipo(), eItem.getValor(mRunTime, mEscopo));
                } else {
                    mEscopo.criarParametroStruct(eNome, eItem.getTipo(), eItem.getValor(mRunTime, mEscopo));
                }
            }

        } else {

            if (eItem.getNulo()) {
                if (eItem.getIsReferenciavel()) {
                    mEscopo.criarParametroNulo(eNome, eItem.getTipo());
                } else {
                    mEscopo.criarParametroNulo(eNome, eItem.getTipo());
                }
            } else {
                if (eItem.getIsReferenciavel()) {
                    mEscopo.criarParametro(eNome, eItem.getTipo(), eItem.getValor(mRunTime, mEscopo));
                } else {
                    mEscopo.criarParametro(eNome, eItem.getTipo(), eItem.getValor(mRunTime, mEscopo));
                }
            }


        }

        referenciar(eNome, eItem.getReferencia());

    }


    public void referenciar(String eNome, Item eItem) {

        boolean enc = false;

        for (Item i : mEscopo.getStacks()) {
            if (i.getNome().contentEquals(eNome)) {
                enc = true;

                i.setReferencia(eItem);
                i.setIsReferenciavel(true);

                break;
            }
        }

        if (!enc) {
            mRunTime.getErros().add("Nao foi possivel referenciar : " + eNome);
        }
    }

}
