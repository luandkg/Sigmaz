package Sigmaz.S01_Compilador.C03_Parser.Bloco;

import Sigmaz.S01_Compilador.C03_Parser.Errador;
import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Corpo;
import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_ValueTypes;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S01_Compilador.Orquestrantes;

public class AST_Call {

    private Parser mCompiler;

    public AST_Call(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST(Orquestrantes.CALL);
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_SENDING = AST_Corrente.criarBranch(Orquestrantes.SENDING);
            AST AST_Corpo = AST_Corrente.criarBranch(Orquestrantes.BODY);

            Token TokenP = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, Errador.eraEsperadoUmaSeta());

            Token TokenFuturo = mCompiler.getTokenFuturo();
            if (TokenFuturo.getTipo() == TokenTipo.CHAVE_ABRE) {

                AST_Corrente.setValor(Orquestrantes.AUTO);

                Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Corpo mAST = new AST_Corpo(mCompiler);
                mAST.init(AST_Corpo);

                AST_SENDING.criarBranch(Orquestrantes.ARGUMENTS);

            } else {

                AST_Corrente.setValor(Orquestrantes.REFER);

                Token TokenP2 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, Errador.eraEsperadoUmaActionOuFunction());
                AST_SENDING.setNome(TokenP2.getConteudo());


                AST_ValueTypes mAVA = new AST_ValueTypes(mCompiler);
                mAVA.ReceberArgumentos_AbrirParenteses(AST_SENDING, false, null);


                Token TokenP3 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, Errador.eraEsperadoPontoEVirgula());
            }


        } else {
            mCompiler.errarCompilacao(Errador.eraEsperadoNomeDaAction(), TokenC);
        }


    }


}