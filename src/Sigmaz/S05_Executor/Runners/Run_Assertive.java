package Sigmaz.S05_Executor.Runners;

import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.RunTime;

import java.util.ArrayList;

public class Run_Assertive {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;


    public Run_Assertive(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Assertive";


    }


    public void init(AST ASTCorrente) {

        System.out.println("Teste com :: " + ASTCorrente.getNome());
        System.out.println(ASTCorrente.getImpressao());

        Run_Arguments mPreparadorDeArgumentos = new Run_Arguments();


        if (ASTCorrente.mesmoNome("ASSERT_EQUAL")) {

            if (ASTCorrente.getBranch("ARGUMENTS").getASTS().size() == 2) {

                ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, mEscopo, ASTCorrente.getBranch("ARGUMENTS"));

                assert_organizacao(mArgumentos.get(0), mArgumentos.get(1));

                if (!mRunTime.temErros()) {
                    assert_equal(mArgumentos.get(0), mArgumentos.get(1));
                }

            } else {
                mRunTime.errar(mLocal, "Assertiva " + ASTCorrente.getNome() + " precisa ter 2 argumentos !");
            }

        } else if (ASTCorrente.mesmoNome("ASSERT_DIFF")) {

            if (ASTCorrente.getBranch("ARGUMENTS").getASTS().size() == 2) {

                ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, mEscopo, ASTCorrente.getBranch("ARGUMENTS"));

                assert_organizacao(mArgumentos.get(0), mArgumentos.get(1));

                if (!mRunTime.temErros()) {
                    assert_diff(mArgumentos.get(0), mArgumentos.get(1));
                }

            } else {
                mRunTime.errar(mLocal, "Assertiva " + ASTCorrente.getNome() + " precisa ter 2 argumentos !");
            }

        } else if (ASTCorrente.mesmoNome("ASSERT_NULLABLE")) {

            if (ASTCorrente.getBranch("ARGUMENTS").getASTS().size() == 2) {

                ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, mEscopo, ASTCorrente.getBranch("ARGUMENTS"));

                assert_nullable(mArgumentos.get(0), mArgumentos.get(1));


            } else {
                mRunTime.errar(mLocal, "Assertiva " + ASTCorrente.getNome() + " precisa ter 2 argumentos !");
            }
        } else if (ASTCorrente.mesmoNome("ASSERT_NOTNULLABLE")) {

            if (ASTCorrente.getBranch("ARGUMENTS").getASTS().size() == 2) {

                ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, mEscopo, ASTCorrente.getBranch("ARGUMENTS"));

                assert_notnullable(mArgumentos.get(0), mArgumentos.get(1));


            } else {
                mRunTime.errar(mLocal, "Assertiva " + ASTCorrente.getNome() + " precisa ter 2 argumentos !");
            }

        } else {
            mRunTime.errar(mLocal, "Assertiva desconhecida : " + ASTCorrente.getNome());
        }

    }

    private void assert_organizacao(Item a, Item b) {

        a.setNome("A");
        b.setNome("B");

        String eTipo = a.getTipo();

        if (!b.getTipo().contentEquals(eTipo)) {
            mRunTime.errar(mLocal, b.getNome() + " precisa ser do tipo " + eTipo);
            return;
        }

        if (a.getNulo()) {
            mRunTime.errar(mLocal, a.getNome() + " nao pode ser NULO !");
            return;
        }

        if (b.getNulo()) {
            mRunTime.errar(mLocal, b.getNome() + " nao pode ser NULO !");
            return;
        }


    }

    private void assert_equal(Item a, Item b) {


        String eTipo = a.getTipo();

        if (eTipo.contentEquals("int")) {

            int aInt = Integer.parseInt(a.getValor(mRunTime, mEscopo));
            int bInt = Integer.parseInt(b.getValor(mRunTime, mEscopo));

            if (aInt != bInt) {
                mRunTime.errar(mLocal, a.getNome() + " precisa ser igual a " + b.getNome());
            }
        } else if (eTipo.contentEquals("bool")) {

            String aBool = a.getValor(mRunTime, mEscopo);
            String bBool = b.getValor(mRunTime, mEscopo);

            if (!aBool.contentEquals(bBool)) {
                mRunTime.errar(mLocal, a.getNome() + " precisa ser igual a " + b.getNome());
            }

        } else {
            mRunTime.errar(mLocal, "Tipo desconhecido : " + eTipo);
        }

    }

    private void assert_diff(Item a, Item b) {


        String eTipo = a.getTipo();

        if (eTipo.contentEquals("int")) {

            int aInt = Integer.parseInt(a.getValor(mRunTime, mEscopo));
            int bInt = Integer.parseInt(b.getValor(mRunTime, mEscopo));

            if (aInt == bInt) {
                mRunTime.errar(mLocal, a.getNome() + " precisa ser diferente de " + b.getNome());
            }
        } else if (eTipo.contentEquals("bool")) {

            String aBool = a.getValor(mRunTime, mEscopo);
            String bBool = b.getValor(mRunTime, mEscopo);

            if (aBool.contentEquals(bBool)) {
                mRunTime.errar(mLocal, a.getNome() + " precisa ser diferente de " + b.getNome());
            }
        } else {
            mRunTime.errar(mLocal, "Tipo desconhecido : " + eTipo);
        }

    }

    private void assert_nullable(Item a, Item b) {

        a.setNome("A");
        b.setNome("B");

        if (a.getNulo() != b.getNulo()) {
            mRunTime.errar(mLocal, b.getNome() + " e B precisam ser NULOS !");
            return;
        }


    }

    private void assert_notnullable(Item a, Item b) {

        a.setNome("A");
        b.setNome("B");

        if (a.getNulo() == b.getNulo()) {
            mRunTime.errar(mLocal, b.getNome() + " e B precisam ser DIFERENTES !");
            return;
        }


    }

}
