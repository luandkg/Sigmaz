package Sigmaz.Ferramentas;

import Sigmaz.Compilador.Compiler;
import Sigmaz.Utils.GrupoDeErro;

import java.util.ArrayList;

public class Dependenciador {

    private ArrayList<GrupoDeErro> mLexer;
    private ArrayList<GrupoDeErro> mCompiler;

    private ArrayList<String> mDependencias;

    public Dependenciador() {

        mLexer = new ArrayList<GrupoDeErro>();
        mCompiler = new ArrayList<GrupoDeErro>();

        mDependencias = new ArrayList<String>();

    }

    public void init_debug(String eArquivo) {

        init(eArquivo);

        System.out.println("");
        System.out.println("############## DEPENDENCIAS ###############");
        System.out.println("");

        if (mLexer.size() == 0 && mCompiler.size() == 0) {

            for (String eDepencia : mDependencias) {

                System.out.println("\t\t - " + eDepencia);


            }

        } else {

            System.out.println("\n\t - ERROS DE PROCESSAMENTO ");


        }

    }

    public void init(String eArquivo) {

        Compiler CompilerC = new Compiler();
        CompilerC.init(eArquivo, 0);

        mDependencias.clear();

        mLexer.clear();
        mCompiler.clear();

        if (CompilerC.getErros_Lexer().size() == 0 && CompilerC.getErros_Compiler().size() == 0) {


            mDependencias.add(eArquivo);


            for (String Req : CompilerC.getRequisitados()) {
                Req = Req.replace("\\", "/");
                if (!mDependencias.contains(Req)) {
                    mDependencias.add(Req);
                }
            }


        }


        mLexer = CompilerC.getErros_Lexer();
        mCompiler = CompilerC.getErros_Compiler();


    }

    public ArrayList<String> getDependencias() {
        return mDependencias;
    }

    public ArrayList<GrupoDeErro> getErros_Lexer() {
        return mLexer;
    }

    public ArrayList<GrupoDeErro> getErros_Compiler() {
        return mCompiler;
    }

    public boolean getTemErros() {

        boolean ret = false;

        if (mLexer.size() == 0 && mCompiler.size() == 0) {
            ret = false;
        } else {
            ret = true;
        }

        return ret;

    }

}
