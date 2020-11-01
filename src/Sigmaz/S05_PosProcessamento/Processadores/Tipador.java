package Sigmaz.S05_PosProcessamento.Processadores;

import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.Mensageiro;
import Sigmaz.S00_Utilitarios.Utilitario;
import Sigmaz.S05_PosProcessamento.EscopoTipos;
import Sigmaz.S08_Executor.Debuggers.Simplificador;
import Sigmaz.S05_PosProcessamento.PosProcessador;
import Sigmaz.S00_Utilitarios.AST;

public class Tipador {

    private PosProcessador mPosProcessador;
    private Mensageiro mMensageiro;

    private ArrayList<String> mPrimitivos;

    private Simplificador mSimplificador;


    public Tipador(PosProcessador ePosProcessador) {

        mPosProcessador = ePosProcessador;
        mMensageiro = mPosProcessador.getMensageiro();
        mSimplificador = mPosProcessador.getSimplificador();


        mPrimitivos = new ArrayList<String>();
        mPrimitivos.add("string");
        mPrimitivos.add("bool");
        mPrimitivos.add("int");
        mPrimitivos.add("num");
        mPrimitivos.add("any");


    }

    public Simplificador getSimplificador() {
        return mSimplificador;
    }


    public boolean getDebug() {
        return mPosProcessador.getDebug_Tipador();
    }

    public void mensagem(String e) {
        if (mPosProcessador.getDebug_Tipador()) {
            mPosProcessador.mensagem(e);
        }
    }

    public void errar(String e) {
        mPosProcessador.errar(e);
    }

    public void init(ArrayList<AST> mASTS) {


        mensagem("");
        mensagem(" ------------------ FASE TIPADOR ----------------------- ");
        mensagem("");


        mensagem("");

        ArrayList<AST> mPacotes = new ArrayList<AST>();

        for (AST mRequisicao : mPosProcessador.getRequisicoes()) {

            mensagem("Biblioteca Externa");


            for (AST eAlgumaCoisa : mRequisicao.getASTS()) {
                if (eAlgumaCoisa.mesmoTipo("PACKAGE")) {
                    mPacotes.add(eAlgumaCoisa);
                }
            }


        }


        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {


                for (AST mAST : ASTCGlobal.getASTS()) {
                    if (mAST.mesmoTipo("PACKAGE")) {
                        mPacotes.add(mAST);
                    }
                }


                EscopoTipos mEscopoTipos_Sigmaz = new EscopoTipos();


                for (String e : mPrimitivos) {
                    mensagem("\t - TIPO PRIMITIVO :  " + e);
                    mEscopoTipos_Sigmaz.adicionar(e);
                }

                // chegarTipagens("\t", mAncestrais, ASTCGlobal);

                chegarTipagens_novo("\t", mPacotes, mEscopoTipos_Sigmaz, ASTCGlobal);

                for (AST mAST : mPacotes) {
                    mensagem("\t" + "Sigmaz Package :  " + mAST.getNome());

                    EscopoTipos mEscopoTipos_Package = mEscopoTipos_Sigmaz.getCopia();


                    chegarTipagens_novo("\t\t", mPacotes, mEscopoTipos_Package, mAST);
                }

            } else {

                mMensageiro.errar("Escopo Desconhecido : " + ASTCGlobal.getTipo());

            }

        }


    }


    public void Usar(ArrayList<AST> mPacotes, String ePrefixo, String eNome, EscopoTipos eEscopoTipos) {


        boolean enc = false;


        for (AST ASTPackage : mPacotes) {

            if (ASTPackage.mesmoNome(eNome)) {

                for (AST mAST : ASTPackage.getASTS()) {

                    if (mAST.mesmoTipo("CAST")) {

                        eEscopoTipos.adicionar(mAST.getNome());

                        mensagem(ePrefixo + "- Tipo CAST : " + ASTPackage.getNome() + "<>" + mAST.getNome());

                    } else if (mAST.mesmoTipo("STRUCT")) {


                        AST mExtended = mAST.getBranch("EXTENDED");

                        if (mExtended.mesmoNome("STRUCT")) {

                            mensagem(ePrefixo + "- Tipo STRUCT : " + ASTPackage.getNome() + "<>" + mAST.getNome());
                            eEscopoTipos.adicionar(mAST.getNome());

                        } else if (mExtended.mesmoNome("STAGES")) {
                            mensagem(ePrefixo + "- Tipo STAGE : " + ASTPackage.getNome() + "<>" + mAST.getNome());
                            eEscopoTipos.adicionar(mAST.getNome());

                        } else if (mExtended.mesmoNome("TYPE")) {
                            mensagem(ePrefixo + "- Tipo TYPE : " + ASTPackage.getNome() + "<>" + mAST.getNome());
                            eEscopoTipos.adicionar(mAST.getNome());

                        } else if (mExtended.mesmoNome("MODEL")) {
                            mensagem(ePrefixo + "- Tipo MODEL : " + ASTPackage.getNome() + "<>" + mAST.getNome());
                            eEscopoTipos.adicionar(mAST.getNome());

                        }

                    }

                }

                enc = true;
                break;
            }

        }

        if (!enc) {
            mMensageiro.errar("Package " + eNome + " : Nao encontrado !");
        }


    }


    public void chegarTipagens_novo(String ePrefixo, ArrayList<AST> mPacotes, EscopoTipos eEscopoTipos, AST ASTPai) {


        // USAR PACKAGES
        for (AST ASTC : ASTPai.getASTS()) {
            if (ASTC.mesmoTipo("REFER")) {
                mensagem(ePrefixo + "REFERENCIANDO PACOTE " + ASTC.getNome());
                Usar(mPacotes, ePrefixo + "\t", ASTC.getNome(), eEscopoTipos);
            }
        }

        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("CAST")) {


                eEscopoTipos.adicionar(mAST.getNome());

                mensagem(ePrefixo + "- Tipo CAST : " + mAST.getNome());

            } else if (mAST.mesmoTipo("STRUCT")) {


                AST mExtended = mAST.getBranch("EXTENDED");

                if (mExtended.mesmoNome("STRUCT")) {

                    mensagem(ePrefixo + "- Tipo STRUCT : " + mAST.getNome());

                    eEscopoTipos.adicionar(mAST.getNome());

                } else if (mExtended.mesmoNome("STAGES")) {
                    mensagem(ePrefixo + "- Tipo STAGE : " + mAST.getNome());

                    eEscopoTipos.adicionar(mAST.getNome());

                } else if (mExtended.mesmoNome("TYPE")) {

                    mensagem(ePrefixo + "- Tipo TYPE : " + mAST.getNome());

                    eEscopoTipos.adicionar(mAST.getNome());

                } else if (mExtended.mesmoNome("MODEL")) {

                    mensagem(ePrefixo + "- Tipo MODEL : " + mAST.getNome());

                    eEscopoTipos.adicionar(mAST.getNome());


                }

            }
        }


        for (AST mAST : ASTPai.getASTS()) {


            if (mAST.mesmoTipo("DEFINE")) {

                alocacao_Define(ePrefixo, mAST, eEscopoTipos);

            } else if (mAST.mesmoTipo("MOCKIZ")) {

                alocacao_Mockiz(ePrefixo, mAST, eEscopoTipos);

            } else if (mAST.mesmoTipo("ACTION")) {

                alocacao_Action(ePrefixo, mAST, eEscopoTipos);
            } else if (mAST.mesmoTipo("CALL")) {

                alocacao_Call(ePrefixo, mAST, eEscopoTipos);
            } else if (mAST.mesmoTipo("PROTOTYPE_AUTO")) {

                alocacao_Auto(ePrefixo, mAST, eEscopoTipos);

            } else if (mAST.mesmoTipo("FUNCTION")) {

                alocacao_Function(ePrefixo, mAST, eEscopoTipos);

            } else if (mAST.mesmoTipo("PROTOTYPE_FUNCTOR")) {

                alocacao_Functor(ePrefixo, mAST, eEscopoTipos);

            } else if (mAST.mesmoTipo("OPERATOR")) {

                alocacao_Operator(ePrefixo, mAST, eEscopoTipos);

            } else if (mAST.mesmoTipo("DIRECTOR")) {

                alocacao_Director(ePrefixo, mAST, eEscopoTipos);


            } else if (mAST.mesmoTipo("CAST")) {

            } else if (mAST.mesmoTipo("STRUCT")) {

                mensagem(ePrefixo + "STRUCT " + mAST.getNome());

                AST mExtended = mAST.getBranch("EXTENDED");

                if (mExtended.mesmoNome("STRUCT")) {

                    EscopoTipos structEscopoTipos = eEscopoTipos.getCopia();

                    if (mAST.getBranch("GENERIC").mesmoNome("TRUE")) {

                        ArrayList<String> genericos_ok = conferirGenericos(mAST.getBranch("GENERIC"), structEscopoTipos);

                        if (genericos_ok.size() > 0) {
                            mensagem(ePrefixo + mAST.getNome() + " -->> TIPAGEM DE GENERICOS INVALIDA ");
                            errar(mAST.getNome() + " -->> TIPAGEM DE GENERICOS INVALIDA ");
                            for (String e : genericos_ok) {
                                mensagem(ePrefixo + "\t" + e);
                                errar(mAST.getNome() + " -->> " + e);
                            }
                        }

                    }

                    conferirCorpoStruct(ePrefixo + "\t", mAST.getBranch("BODY"), structEscopoTipos);

                }


            }

        }


    }


    public void alocacao_Define(String ePrefixo, AST mAST, EscopoTipos eEscopoTipos) {


        String eNome = getSimplificador().getDefine(mAST);


        boolean retorno_ok = conferirTipo(mAST.getBranch("TYPE"), eEscopoTipos);

        if (retorno_ok) {

            mensagem(ePrefixo + "DEFINE " + eNome + " -->> OK ");

        } else {

            String mErro = "DEFINE " + eNome + " -->> TIPAGEM INVALIDA ";

            mensagem(ePrefixo + mErro);
            errar(mErro);
        }


    }

    public void alocacao_Mockiz(String ePrefixo, AST mAST, EscopoTipos eEscopoTipos) {


        String eNome = getSimplificador().getDefine(mAST);


        boolean retorno_ok = conferirTipo(mAST.getBranch("TYPE"), eEscopoTipos);

        if (retorno_ok) {

            mensagem(ePrefixo + "MOCKIZ " + eNome + " -->> OK ");

        } else {

            String mErro = "MOCKIZ " + eNome + " -->> TIPAGEM INVALIDA ";

            mensagem(ePrefixo + mErro);
            errar(mErro);
        }


    }

    public void alocacao_Call(String ePrefixo, AST mAST, EscopoTipos eEscopoTipos) {

        String eNome = "CALL " + getSimplificador().getCall(mAST);


        mensagem(ePrefixo + eNome + " -->> OK ");
        if (mAST.mesmoValor("AUTO")) {
            conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), eEscopoTipos);
        }


    }

    public void alocacao_Action(String ePrefixo, AST mAST, EscopoTipos eEscopoTipos) {

        String eNome = "ACTION " + getSimplificador().getAction(mAST);


        ArrayList<String> argumentos_ok = conferirArgumentos_novo(mAST.getBranch("ARGUMENTS"), eEscopoTipos);


        if (argumentos_ok.size() == 0) {

            mensagem(ePrefixo + eNome + " -->> OK ");

            conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), eEscopoTipos);

        } else {

            status_Argumento(ePrefixo, eNome, argumentos_ok);

        }


    }

    public void alocacao_Auto(String ePrefixo, AST mAST, EscopoTipos eEscopoTipos) {

        String eNome = "AUTO " + getSimplificador().getAction(mAST);


        EscopoTipos auto_EscopoTipos = eEscopoTipos.getCopia();


        ArrayList<String> genericos_ok = conferirGenericos(mAST.getBranch("GENERICS"), auto_EscopoTipos);
        ArrayList<String> argumentos_ok = conferirArgumentos_novo(mAST.getBranch("ARGUMENTS"), auto_EscopoTipos);


        if (genericos_ok.size() == 0 && argumentos_ok.size() == 0) {

            mensagem(ePrefixo + eNome + " -->> OK ");

            conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), auto_EscopoTipos);

        } else {

            status_GenericoArgumento(ePrefixo, eNome, genericos_ok, argumentos_ok);

        }

    }

    public void alocacao_Function(String ePrefixo, AST mAST, EscopoTipos eEscopoTipos) {

        String eNome = "FUNCTION " + getSimplificador().getFuction(mAST);


        ArrayList<String> argumentos_ok = conferirArgumentos_novo(mAST.getBranch("ARGUMENTS"), eEscopoTipos);
        boolean retorno_ok = conferirTipo(mAST.getBranch("TYPE"), eEscopoTipos);

        if (argumentos_ok.size() == 0 && retorno_ok) {

            mensagem(ePrefixo + eNome + " -->> OK ");

            conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), eEscopoTipos);

        } else {

            status_ArgumentoRetorno(ePrefixo, eNome, argumentos_ok, retorno_ok);

        }


    }

    public void alocacao_Functor(String ePrefixo, AST mAST, EscopoTipos eEscopoTipos) {

        String eNome = "FUNCTOR " + getSimplificador().getFunctor(mAST);

        EscopoTipos functor_EscopoTipos = eEscopoTipos.getCopia();

        ArrayList<String> genericos_ok = conferirGenericos(mAST.getBranch("GENERICS"), functor_EscopoTipos);
        ArrayList<String> argumentos_ok = conferirArgumentos_novo(mAST.getBranch("ARGUMENTS"), functor_EscopoTipos);

        boolean retorno_ok = conferirTipo(mAST.getBranch("TYPE"), functor_EscopoTipos);

        if (genericos_ok.size() == 0 && argumentos_ok.size() == 0 && retorno_ok) {

            mensagem(ePrefixo + eNome + " -->> OK ");

            conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), functor_EscopoTipos);

        } else {

            status_GenericoArgumentoRetorno(ePrefixo, eNome, genericos_ok, argumentos_ok, retorno_ok);

        }


    }

    public void alocacao_Director(String ePrefixo, AST mAST, EscopoTipos eEscopoTipos) {

        String eNome = "DIRECTOR " + getSimplificador().getDirector(mAST);


        ArrayList<String> argumentos_ok = conferirArgumentos_novo(mAST.getBranch("ARGUMENTS"), eEscopoTipos);
        boolean retorno_ok = conferirTipo(mAST.getBranch("TYPE"), eEscopoTipos);

        if (argumentos_ok.size() == 0 && retorno_ok) {

            mensagem(ePrefixo + eNome + " -->> OK ");

            conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), eEscopoTipos);

        } else {

            status_ArgumentoRetorno(ePrefixo, eNome, argumentos_ok, retorno_ok);

        }

    }

    public void alocacao_Operator(String ePrefixo, AST mAST, EscopoTipos eEscopoTipos) {

        String eNome = "OPERATOR " + getSimplificador().getOperator(mAST);


        ArrayList<String> argumentos_ok = conferirArgumentos_novo(mAST.getBranch("ARGUMENTS"), eEscopoTipos);
        boolean retorno_ok = conferirTipo(mAST.getBranch("TYPE"), eEscopoTipos);

        if (argumentos_ok.size() == 0 && retorno_ok) {

            mensagem(ePrefixo + eNome + " -->> OK ");

            conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), eEscopoTipos);

        } else {

            status_ArgumentoRetorno(ePrefixo, eNome, argumentos_ok, retorno_ok);

        }


    }


    public void conferirCorpo(String ePrefixo, AST mCorpo, EscopoTipos eEscopoTipos) {

        Utilitario mUtilitario = new Utilitario();

        for (AST mAST : mCorpo.getASTS()) {

            if (mAST.mesmoTipo("MOC")) {

                boolean retorno_ok = conferirTipo(mAST.getBranch("TYPE"), eEscopoTipos);

                if (retorno_ok) {
                    mensagem(ePrefixo + "MOC " + mUtilitario.getDefine_Definicao(mAST) + " -->> OK ");
                } else {
                    mensagem(ePrefixo + "MOC " + mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                    errar("MOC " + mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                }

            } else if (mAST.mesmoTipo("DEF")) {

                boolean retorno_ok = conferirTipo(mAST.getBranch("TYPE"), eEscopoTipos);

                if (retorno_ok) {
                    mensagem(ePrefixo + "DEF " + mUtilitario.getDefine_Definicao(mAST) + " -->> OK ");
                } else {
                    mensagem(ePrefixo + "DEF " + mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                    errar("DEF " + mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                }

            } else if (mAST.mesmoTipo("IF")) {

                conferirCorpo(ePrefixo, mAST.getBranch("BODY"), eEscopoTipos);

                for (AST mSubIF : mAST.getASTS()) {
                    if (mSubIF.mesmoTipo("OTHER")) {

                        conferirCorpo(ePrefixo, mSubIF.getBranch("BODY"), eEscopoTipos);

                    } else if (mSubIF.mesmoTipo("OTHERS")) {

                        conferirCorpo(ePrefixo, mSubIF, eEscopoTipos);

                    }

                }


            } else if (mAST.mesmoTipo("WHILE")) {

                conferirCorpo(ePrefixo, mAST.getBranch("BODY"), eEscopoTipos);

            } else if (mAST.mesmoTipo("STEP")) {

                conferirCorpo(ePrefixo, mAST.getBranch("BODY"), eEscopoTipos);

            }

        }

    }

    public void conferirCorpoStruct(String ePrefixo, AST ASTPai, EscopoTipos eEscopoTipos) {


        for (AST mAST : ASTPai.getASTS()) {


            if (mAST.mesmoTipo("DEFINE")) {

                alocacao_Define(ePrefixo, mAST, eEscopoTipos);

            } else if (mAST.mesmoTipo("MOCKIZ")) {

                alocacao_Mockiz(ePrefixo, mAST, eEscopoTipos);

            } else if (mAST.mesmoTipo("ACTION")) {

                alocacao_Action(ePrefixo, mAST, eEscopoTipos);

            } else if (mAST.mesmoTipo("FUNCTION")) {

                alocacao_Function(ePrefixo, mAST, eEscopoTipos);

            } else if (mAST.mesmoTipo("OPERATOR")) {

                alocacao_Operator(ePrefixo, mAST, eEscopoTipos);

            } else if (mAST.mesmoTipo("DIRECTOR")) {

                alocacao_Director(ePrefixo, mAST, eEscopoTipos);

            }

        }


    }


    public void status_GenericoArgumentoRetorno(String ePrefixo, String eNome, ArrayList<String> genericos_ok, ArrayList<String> argumentos_ok, boolean retorno_ok) {

        if (genericos_ok.size() > 0) {
            mensagem(ePrefixo + eNome + " -->> TIPAGEM DE GENERICOS INVALIDA ");
            errar(eNome + " -->> TIPAGEM DE GENERICOS INVALIDA ");
            for (String e : genericos_ok) {
                mensagem(ePrefixo + "\t" + e);
                errar(eNome + " -->> " + e);
            }
        }

        if (argumentos_ok.size() > 0) {
            mensagem(ePrefixo + eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            errar(eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            for (String e : argumentos_ok) {
                mensagem(ePrefixo + "\t" + e);
                errar(eNome + " -->> " + e);
            }
        }


        if (!retorno_ok) {
            mensagem(ePrefixo + eNome + " -->> TIPAGEM DE RETORNO INVALIDA ");
            errar(eNome + " -->> TIPAGEM DE RETORNO INVALIDA ");
        }

    }

    public void status_GenericoArgumento(String ePrefixo, String eNome, ArrayList<String> genericos_ok, ArrayList<String> argumentos_ok) {

        if (genericos_ok.size() > 0) {
            mensagem(ePrefixo + eNome + " -->> TIPAGEM DE GENERICOS INVALIDA ");
            errar(eNome + " -->> TIPAGEM DE GENERICOS INVALIDA ");
            for (String e : genericos_ok) {
                mensagem(ePrefixo + "\t" + e);
                errar(eNome + " -->> " + e);
            }
        }

        if (argumentos_ok.size() > 0) {
            mensagem(ePrefixo + eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            errar(eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            for (String e : argumentos_ok) {
                mensagem(ePrefixo + "\t" + e);
                errar(eNome + " -->> " + e);
            }
        }


    }

    public void status_Argumento(String ePrefixo, String eNome, ArrayList<String> argumentos_ok) {

        if (argumentos_ok.size() > 0) {
            mensagem(ePrefixo + eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            errar(eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            for (String e : argumentos_ok) {
                mensagem(ePrefixo + "\t" + e);
                errar(eNome + " -->> " + e);
            }
        }

    }

    public void status_ArgumentoRetorno(String ePrefixo, String eNome, ArrayList<String> argumentos_ok, boolean retorno_ok) {


        if (argumentos_ok.size() > 0) {
            mensagem(ePrefixo + eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            errar(eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            for (String e : argumentos_ok) {
                mensagem(ePrefixo + "\t" + e);
                errar(eNome + " -->> " + e);
            }
        }


        if (!retorno_ok) {
            mensagem(ePrefixo + eNome + " -->> TIPAGEM DE RETORNO INVALIDA ");
            errar(eNome + " -->> TIPAGEM DE RETORNO INVALIDA ");
        }

    }


    public ArrayList<String> conferirGenericos(AST mGenericos, EscopoTipos eEscopoTipos) {

        ArrayList<String> gErros = new ArrayList<String>();

        int i = 0;
        int o = mGenericos.getASTS().size();

        ArrayList<String> mIncluir = new ArrayList<String>();

        for (AST oAST : mGenericos.getASTS()) {
            if (conferirTipo(oAST, eEscopoTipos)) {
                gErros.add("Tipo generico " + oAST.getNome() + " : Nome invalido !");
            } else {
                i += 1;
                mIncluir.add(oAST.getNome());
            }
        }

        for (String oAST : mIncluir) {
            eEscopoTipos.adicionar(oAST);
        }

        return gErros;
    }

    public ArrayList<String> conferirArgumentos_novo(AST mArgumetos, EscopoTipos eEscopoTipos) {

        ArrayList<String> gErros = new ArrayList<String>();

        for (AST eArgumento : mArgumetos.getASTS()) {

            if (conferirTipo(eArgumento.getBranch("TYPE"), eEscopoTipos)) {

            } else {
                gErros.add("Argumento Tipo " + eArgumento.getBranch("TYPE").getNome() + " : Deconhecido !");
                break;
            }

        }
        return gErros;
    }


    public boolean conferirTipo(AST mTipo, EscopoTipos eEscopoTipos) {

        boolean ret = false;

        if (mTipo.mesmoValor("CONCRETE")) {

            if (eEscopoTipos.existe(mTipo.getNome())) {
                ret = true;
            } else {
                ret = false;
            }
        } else if (mTipo.mesmoValor("GENERIC")) {


            if (eEscopoTipos.existe(mTipo.getNome())) {
                ret = true;
            } else {
                ret = false;
            }

            for (AST eAST : mTipo.getASTS()) {

                if (eAST.mesmoTipo("TYPE")) {
                    ret = conferirTipo(eAST, eEscopoTipos);
                    if (!ret) {
                        break;
                    }
                }
            }

        } else {
            ret = false;
        }

        return ret;
    }

}
