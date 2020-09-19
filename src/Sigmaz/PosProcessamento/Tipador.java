package Sigmaz.PosProcessamento;

import java.util.ArrayList;
import Sigmaz.Utils.AST;
import Sigmaz.Utils.Utilitario;

public class Tipador {

    private PosProcessador mPosProcessador;
    private ArrayList<AST> mPacotes;
    private ArrayList<String> mPrimitivos;
    private ArrayList<String> mAncestrais;

    public Tipador(PosProcessador eAnalisador) {

        mPosProcessador = eAnalisador;
        mPacotes = new ArrayList<AST>();

        mAncestrais = new ArrayList<String>();

        mPrimitivos = new ArrayList<String>();
        mPrimitivos.add("num");
        mPrimitivos.add("string");
        mPrimitivos.add("bool");
        mPrimitivos.add("int");
        mPrimitivos.add("any");

    }

    public void init(ArrayList<AST> mASTS) {


        mensagem("");
        mensagem(" ------------------ FASE TIPADOR ----------------------- ");
        mensagem("");


        for(String e : mPrimitivos){
            mensagem("\t - TIPO PRIMITIVO :  " + e);
        }

        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {


                for (AST mAST : ASTCGlobal.getASTS()) {
                    if (mAST.mesmoTipo("PACKAGE")) {
                        mPacotes.add(mAST);
                    }
                }


                chegarTipagens("",mAncestrais, ASTCGlobal);

                for (AST mAST : mPacotes) {
                    mensagem("Sigmaz Package :  " + mAST.getNome());
                    chegarTipagens("\t",mAncestrais, mAST);
                }

            } else {

                errar("Escopo Desconhecido : " + ASTCGlobal.getTipo());

            }

        }


    }

    private void errar(String eErro) {
        mPosProcessador.getErros().add(eErro);
    }

    private void mensagem(String eMensagem) {
        mPosProcessador.mensagem(eMensagem);
    }

    public void chegarTipagens(String ePrefixo,ArrayList<String> mTipos_Ancestrais, AST ASTPai) {

        ArrayList<String> mTipos = new ArrayList<String>();

        for (String e : mPrimitivos) {
            mTipos.add(e);
        }

        for (String e : mTipos_Ancestrais) {
            mTipos.add(e);
        }

        // USAR PACKAGES
        for (AST ASTC : ASTPai.getASTS()) {
            if (ASTC.mesmoTipo("REFER")) {
                mensagem("REFERENCIANDO PACOTE " + ASTC.getNome());
                Usar(ASTC.getNome(), mTipos);
            }
        }

        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("CAST")) {

                mTipos.add(mAST.getNome());
                mensagem(ePrefixo + "\t - Tipo CAST : " + mAST.getNome());

            } else if (mAST.mesmoTipo("STRUCT")) {

                mTipos.add(mAST.getNome());

                AST mExtended = mAST.getBranch("EXTENDED");

                if (mExtended.mesmoNome("STRUCT")) {
                    mensagem(ePrefixo +"\t - Tipo STRUCT : " + mAST.getNome());
                } else if (mExtended.mesmoNome("STAGES")) {
                    mensagem(ePrefixo +"\t - Tipo STAGE : " + mAST.getNome());
                }

            }
        }

        Utilitario mUtilitario = new Utilitario();


        for (AST mAST : ASTPai.getASTS()) {


            if (mAST.mesmoTipo("DEFINE")) {

                boolean retorno_ok = checarTipagem(mTipos, mAST.getBranch("TYPE"));

                if (retorno_ok) {
                    mensagem(ePrefixo +"DEFINE " + mUtilitario.getDefine_Definicao(mAST) + " -->> OK ");
                } else {
                    mensagem(ePrefixo +"DEFINE " + mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                    errar("DEFINE " + mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                }

            } else if (mAST.mesmoTipo("MOCKIZ")) {

                boolean retorno_ok = checarTipagem(mTipos, mAST.getBranch("TYPE"));

                if (retorno_ok) {
                    mensagem(ePrefixo +"MOCKIZ " + mUtilitario.getDefine_Definicao(mAST) + " -->> OK ");
                } else {
                    mensagem(ePrefixo +"MOCKIZ " + mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                    errar("MOCKIZ " + mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");

                 //   for(String e : mTipos){
                 //       mensagem(ePrefixo +" -->> " + e);
                   // }



                }

            } else if (mAST.mesmoTipo("FUNCTION")) {


                if (mAST.existeBranch("TYPE")) {

                    boolean argumentos_ok = checarArgumentos(mTipos, mAST.getBranch("ARGUMENTS"));
                    boolean retorno_ok = checarTipagem(mTipos, mAST.getBranch("TYPE"));

                    if (argumentos_ok && retorno_ok) {
                        mensagem(ePrefixo +"FUNCTION " + mUtilitario.getFunction_Definicao(mAST) + " -->> OK ");

                        checarCorpo(ePrefixo+ "\t",mTipos, mAST.getBranch("BODY"));

                    } else {

                        if (!argumentos_ok) {
                            mensagem(ePrefixo +"FUNCTION " + mUtilitario.getFunction_Definicao(mAST) + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
                            errar("FUNCTION " + mUtilitario.getFunction_Definicao(mAST) + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
                        }

                        if (!retorno_ok) {
                            mensagem(ePrefixo +"FUNCTION " + mUtilitario.getFunction_Definicao(mAST) + " -->> TIPAGEM DE RETORNO INVALIDA ");
                            errar("FUNCTION " + mUtilitario.getFunction_Definicao(mAST) + " -->> TIPAGEM DE RETORNO INVALIDA ");
                        }

                    }


                } else {
                    errar("FUNCTION " + mAST.getNome() + " : SEM TIPAGEM DE RETORNO ");
                }
            } else if (mAST.mesmoTipo("OPERATOR")) {


                if (mAST.existeBranch("TYPE")) {

                    boolean argumentos_ok = checarArgumentos(mTipos, mAST.getBranch("ARGUMENTS"));
                    boolean retorno_ok = checarTipagem(mTipos, mAST.getBranch("TYPE"));

                    if (argumentos_ok && retorno_ok) {
                        mensagem(ePrefixo +"OPERATOR " + mUtilitario.getFunction_Definicao(mAST) + " -->> OK ");

                        checarCorpo(ePrefixo+ "\t",mTipos, mAST.getBranch("BODY"));

                    } else {

                        if (!argumentos_ok) {
                            mensagem(ePrefixo +"OPERATOR " + mUtilitario.getFunction_Definicao(mAST) + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
                            errar("OPERATOR " + mUtilitario.getFunction_Definicao(mAST) + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
                        }

                        if (!retorno_ok) {
                            mensagem(ePrefixo +"OPERATOR " + mUtilitario.getFunction_Definicao(mAST) + " -->> TIPAGEM DE RETORNO INVALIDA ");
                            errar("OPERATOR " + mUtilitario.getFunction_Definicao(mAST) + " -->> TIPAGEM DE RETORNO INVALIDA ");
                        }

                    }


                } else {
                    errar("OPERATOR " + mAST.getNome() + " : SEM TIPAGEM DE RETORNO ");
                }
            } else if (mAST.mesmoTipo("DIRECTOR")) {


                if (mAST.existeBranch("TYPE")) {

                    boolean argumentos_ok = checarArgumentos(mTipos, mAST.getBranch("ARGUMENTS"));
                    boolean retorno_ok = checarTipagem(mTipos, mAST.getBranch("TYPE"));

                    if (argumentos_ok && retorno_ok) {
                        mensagem(ePrefixo +"DIRECTOR " + mUtilitario.getFunction_Definicao(mAST) + " -->> OK ");

                        checarCorpo(ePrefixo+ "\t",mTipos, mAST.getBranch("BODY"));

                    } else {

                        if (!argumentos_ok) {
                            mensagem(ePrefixo +"DIRECTOR " + mUtilitario.getFunction_Definicao(mAST) + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
                            errar("DIRECTOR " +mUtilitario.getFunction_Definicao(mAST) + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
                        }

                        if (!retorno_ok) {
                            mensagem(ePrefixo +"DIRECTOR " + mUtilitario.getFunction_Definicao(mAST) + " -->> TIPAGEM DE RETORNO INVALIDA ");
                            errar("DIRECTOR " + mUtilitario.getFunction_Definicao(mAST) + " -->> TIPAGEM DE RETORNO INVALIDA ");
                        }

                    }


                } else {
                    errar("DIRECTOR " + mAST.getNome() + " : SEM TIPAGEM DE RETORNO ");
                }

            } else if (mAST.mesmoTipo("ACTION")) {

                boolean argumentos_ok = checarArgumentos(mTipos, mAST.getBranch("ARGUMENTS"));
                if (argumentos_ok) {

                    mensagem(ePrefixo +"ACTION " + mUtilitario.getAction_Definicao(mAST) + " -->> OK ");

                    checarCorpo(ePrefixo + "\t",mTipos, mAST.getBranch("BODY"));

                } else {

                    mensagem(ePrefixo +"ACTION " + mUtilitario.getAction_Definicao(mAST) + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
                    errar("ACTION " + mUtilitario.getAction_Definicao(mAST) + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");


                }


            } else if (mAST.mesmoTipo("CAST")) {

            } else if (mAST.mesmoTipo("STRUCT")) {

                mensagem(ePrefixo +"STRUCT " + mAST.getNome());

                AST mExtended = mAST.getBranch("EXTENDED");

                if (mExtended.mesmoNome("STRUCT")) {
                    ArrayList<String> Struct_Tipos = new ArrayList<String>();
                    Struct_Tipos.addAll(mTipos);

                    if (mAST.getBranch("GENERIC").mesmoNome("TRUE")) {



                        for (AST oAST : mAST.getBranch("GENERIC").getASTS()) {
                            Struct_Tipos.add(oAST.getNome());
                            mensagem(ePrefixo +"\t - TIPO GENERICO : " + oAST.getNome());
                        }

                    }

                    chegarTipagens(ePrefixo + "\t" ,Struct_Tipos, mAST.getBranch("BODY"));

                }


            }

        }


    }

    public boolean checarArgumentos(ArrayList<String> mTipos, AST mArgumetos) {

        boolean tudook = true;
        for (AST eArgumento : mArgumetos.getASTS()) {

            if (checarTipagem(mTipos, eArgumento.getBranch("TYPE"))) {

            } else {
                tudook = false;
                break;
            }

        }
        return tudook;
    }

    public void checarCorpo(String ePrefixo,ArrayList<String> mTipos, AST mCorpo) {

        Utilitario mUtilitario = new Utilitario();

        for (AST mAST : mCorpo.getASTS()) {

            if (mAST.mesmoTipo("MOC")) {

                boolean retorno_ok = checarTipagem(mTipos, mAST.getBranch("TYPE"));

                if (retorno_ok) {
                    mensagem(ePrefixo+ "MOC " + mUtilitario.getDefine_Definicao(mAST) + " -->> OK ");
                } else {
                    mensagem(ePrefixo+ "MOC " + mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                    errar("MOC " + mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                }

            } else if (mAST.mesmoTipo("DEF")) {

                boolean retorno_ok = checarTipagem(mTipos, mAST.getBranch("TYPE"));

                if (retorno_ok) {
                    mensagem(ePrefixo+ "DEF " +mUtilitario. getDefine_Definicao(mAST) + " -->> OK ");
                } else {
                    mensagem(ePrefixo+ "DEF " +mUtilitario. getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                    errar("DEF " +mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                }

            } else if (mAST.mesmoTipo("IF")) {

                checarCorpo(ePrefixo,mTipos, mAST.getBranch("BODY"));

                for (AST mSubIF : mAST.getASTS()) {
                    if (mSubIF.mesmoTipo("OTHER")) {

                        checarCorpo(ePrefixo,mTipos, mSubIF.getBranch("BODY"));

                    } else if (mSubIF.mesmoTipo("OTHERS")) {

                        checarCorpo(ePrefixo,mTipos, mSubIF);

                    }

                }


            } else if (mAST.mesmoTipo("WHILE")) {

                checarCorpo(ePrefixo,mTipos, mAST.getBranch("BODY"));
            } else if (mAST.mesmoTipo("STEP")) {

                checarCorpo(ePrefixo,mTipos, mAST.getBranch("BODY"));

            }

        }

    }

    public void Usar(String eNome, ArrayList<String> mTipos) {


        boolean enc = false;


        for (AST ASTPackage : mPacotes) {

            if (ASTPackage.mesmoNome(eNome)) {

                for (AST mAST : ASTPackage.getASTS()) {

                    if (mAST.mesmoTipo("CAST")) {

                        mTipos.add(mAST.getNome());
                        mensagem("\t - Tipo CAST : " + ASTPackage.getNome() + "<>" + mAST.getNome());

                    } else if (mAST.mesmoTipo("STRUCT")) {

                        mTipos.add(mAST.getNome());

                        AST mExtended = mAST.getBranch("EXTENDED");

                        if (mExtended.mesmoNome("STRUCT")) {
                            mensagem("\t - Tipo STRUCT : " +ASTPackage.getNome() + "<>" + mAST.getNome());
                        } else if (mExtended.mesmoNome("STAGES")) {
                            mensagem("\t - Tipo STAGE : " + ASTPackage.getNome() + "<>" +mAST.getNome());
                        }

                    }

                }

                enc = true;
                break;
            }

        }

        if (!enc) {
            errar("Package " + eNome + " : Nao encontrado !");
        }


    }



    public boolean checarTipagem(ArrayList<String> mTipos, AST mTipo) {

        boolean ret = false;

        if (mTipo.mesmoValor("CONCRETE")) {

            if (mTipos.contains(mTipo.getNome())) {
                ret = true;
            } else {

                ret = false;
            }

        } else {
            ret = true;
        }

        return ret;
    }


}
