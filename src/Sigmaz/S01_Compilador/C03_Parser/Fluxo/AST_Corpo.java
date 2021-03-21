package Sigmaz.S01_Compilador.C03_Parser.Fluxo;

import Sigmaz.S01_Compilador.C03_Parser.*;
import Sigmaz.S01_Compilador.C03_Parser.Alocador.AST_Alocador;
import Sigmaz.S01_Compilador.C03_Parser.Alocador.AST_Def;
import Sigmaz.S01_Compilador.C03_Parser.Alocador.AST_Mut;
import Sigmaz.S01_Compilador.C03_Parser.Processador.AST_Reg;
import Sigmaz.S01_Compilador.C03_Parser.Bloco.AST_Local;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S01_Compilador.C03_Parser.Processador.AST_Proc;
import Sigmaz.S01_Compilador.C03_Parser.Testes.AST_Assertive;
import Sigmaz.S01_Compilador.Termos;

public class AST_Corpo {

    private Parser mCompiler;

    public AST_Corpo(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.CHAVE_ABRE, "Era esperado abrir chaves");

        if (TokenC.getTipo() != TokenTipo.CHAVE_ABRE) {
            return;
        }

        dentro(ASTPai);

    }

    public void dentro(AST ASTPai) {

        boolean saiu = false;

        while (mCompiler.Continuar()) {

            Token TokenD = mCompiler.getTokenAvante();


            if (TokenD.getTipo() == TokenTipo.CHAVE_FECHA) {
                saiu = true;
                break;

            } else if (TokenD.getTipo() == TokenTipo.CHAVE_ABRE) {

                AST_Escopo mAST_Escopo = new AST_Escopo(mCompiler);
                mAST_Escopo.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.DEF)) {


                AST_Def mAST = new AST_Def(mCompiler);
                mAST.init_Def(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.MOC)) {

                AST_Alocador mAST = new AST_Alocador(mCompiler);
                mAST.init("MOC", ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.LET)) {

                AST_Alocador mAST = new AST_Alocador(mCompiler);
                mAST.initSemTipagem("LET", ASTPai);
            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.VOZ)) {

                AST_Alocador mAST = new AST_Alocador(mCompiler);
                mAST.initSemTipagem("VOZ", ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.MUT)) {

                AST_Mut mAST = new AST_Mut(mCompiler);
                mAST.init_mut(ASTPai);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.RETURN)) {

                AST_Return mAST = new AST_Return(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.IF)) {

                AST_Condition mAST = new AST_Condition(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.WHILE)) {

                AST_While mAST = new AST_While(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.LOOP)) {

                AST_Loop mAST = new AST_Loop(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.STEP)) {

                AST_Step mAST = new AST_Step(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.WHEN)) {

                AST_When mAST = new AST_When(mCompiler);
                mAST.init(ASTPai);
            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.DAZ)) {

                AST_Daz mAST = new AST_Daz(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.TRY)) {

                AST_Try mAST = new AST_Try(mCompiler);
                mAST.init(ASTPai);
            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.EACH)) {

                AST_Each mAST = new AST_Each(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.CANCEL)) {

                AST mCancel = ASTPai.criarBranch("CANCEL");
                Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado abrir ponto e virgula");

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.CONTINUE)) {

                AST mCancel = ASTPai.criarBranch("CONTINUE");
                Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado abrir ponto e virgula");


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.EXCEPTION)) {

                AST_Exception mAST = new AST_Exception(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.INIT)) {


                mCompiler.voltar();

                AST_Value mASTValue = new AST_Value(mCompiler);
                mASTValue.init(ASTPai.criarBranch("VALUE"));

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.START)) {


                mCompiler.voltar();

                AST_Value mASTValue = new AST_Value(mCompiler);
                mASTValue.init(ASTPai.criarBranch("VALUE"));

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.DELETE)) {

                AST_Delete mAST = new AST_Delete(mCompiler);
                mAST.init(ASTPai);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("extern_refered")) {

                AST_ExternRefered AST_ER = new AST_ExternRefered(mCompiler);
                AST_ER.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.LOCAL)) {

                AST_Local mLocal = new AST_Local(mCompiler);
                mLocal.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.AUTO)) {

                AST_ExecuteAuto mAST = new AST_ExecuteAuto(mCompiler);
                mAST.init(ASTPai);
            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.FUNCTOR)) {

                AST_ExecuteAuto mAST = new AST_ExecuteAuto(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.CHANGE)) {

                AST_Change mAST = new AST_Change(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.USING)) {

                AST_Using mAST = new AST_Using(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo(Termos.REG)) {

                AST_Reg mAST = new AST_Reg(mCompiler);
                mAST.init(ASTPai);
            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS(Termos.PROC)) {

                AST_Proc mAST = new AST_Proc(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS(Termos.DEBUG)) {

                AST_Debug mAST = new AST_Debug(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID) {

                AST_Comando mAST = new AST_Comando(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ARROBA) {

                Token TokenP2 = mCompiler.getTokenAvante();

                if (TokenP2.getTipo() == TokenTipo.SETA) {

                    AST_Assertive mAST = new AST_Assertive(mCompiler);
                    mAST.init(ASTPai);


                } else if (TokenP2.getTipo() == TokenTipo.PARENTESES_ABRE) {

                    AST_ExecuteLocal mAST = new AST_ExecuteLocal(mCompiler);
                    mAST.init(ASTPai);

                } else {
                    mCompiler.errarCompilacao("Era esperdo abrir parenteses ou seta !", TokenP2);
                }


            } else {


                mCompiler.errarCompilacao("Comando Deconhecido : " + TokenD.getConteudo(), TokenD);


            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }

    }
}
