package Sigmaz.S06_Ferramentas;

import Sigmaz.S08_Utilitarios.GrupoDeErro;
import Sigmaz.Sigmaz_Compilador;

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

    public void init_debug(String eArquivo,String mLocallibs) {

        init(eArquivo,mLocallibs);

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

    public void init(String eArquivo,String mLocalLibs) {

        Sigmaz_Compilador CompilerC = new Sigmaz_Compilador();
        CompilerC.initSemObjeto(eArquivo, mLocalLibs,0);

        mDependencias.clear();

        mLexer.clear();
        mCompiler.clear();

        if (CompilerC.getErros_Lexer().size() == 0 && CompilerC.getErros_Parser().size() == 0) {


            mDependencias.add(eArquivo);


            for (String Req : CompilerC.getRequisitados()) {
                Req = Req.replace("\\", "/");
                if (!mDependencias.contains(Req)) {
                    mDependencias.add(Req);
                }
            }


        }


        mLexer = CompilerC.getErros_Lexer();
        mCompiler = CompilerC.getErros_Parser();


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
