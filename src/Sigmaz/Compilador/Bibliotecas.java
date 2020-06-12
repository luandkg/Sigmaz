package Sigmaz.Compilador;

import Sigmaz.Utils.Documento;

public class Bibliotecas {


    public String operador(){


        Documento operator = new Documento();

        operator.adicionarLinha("function somar {");

        operator.adicionarLinha("	action param {");
        operator.adicionarLinha("		make a");
        operator.adicionarLinha("		make b");
        operator.adicionarLinha("	}");

        operator.adicionarLinha("	action return {");

        operator.adicionarLinha("		def a");
        operator.adicionarLinha("		add b ");

        operator.adicionarLinha("		make c");
        operator.adicionarLinha("		apply c");

        operator.adicionarLinha("		ret c");
        operator.adicionarLinha("	}");

        operator.adicionarLinha("}");

        operator.adicionarLinha("function subtrair {");

        operator.adicionarLinha("	action param {");
        operator.adicionarLinha("		make a");
        operator.adicionarLinha("		make b");
        operator.adicionarLinha("	}");

        operator.adicionarLinha("	action return {");

        operator.adicionarLinha("		def a");
        operator.adicionarLinha("		sub b ");

        operator.adicionarLinha("		make c");
        operator.adicionarLinha("		apply c");

        operator.adicionarLinha("		ret c");
        operator.adicionarLinha("	}");

        operator.adicionarLinha("}");

        operator.adicionarLinha("function multiplicar {");

        operator.adicionarLinha("	action param {");
        operator.adicionarLinha("		make a");
        operator.adicionarLinha("		make b");
        operator.adicionarLinha("	}");

        operator.adicionarLinha("	action return {");

        operator.adicionarLinha("		def a");
        operator.adicionarLinha("		mux b ");

        operator.adicionarLinha("		make c");
        operator.adicionarLinha("		apply c");

        operator.adicionarLinha("		ret c");
        operator.adicionarLinha("	}");

        operator.adicionarLinha("}");

        operator.adicionarLinha("function dividir {");

        operator.adicionarLinha("	action param {");
        operator.adicionarLinha("		make a");
        operator.adicionarLinha("		make b");
        operator.adicionarLinha("	}");

        operator.adicionarLinha("	action return {");

        operator.adicionarLinha("		def a");
        operator.adicionarLinha("		div b ");

        operator.adicionarLinha("		make c");
        operator.adicionarLinha("		apply c");

        operator.adicionarLinha("		ret c");
        operator.adicionarLinha("	}");

        operator.adicionarLinha("}");

        return operator.getConteudo();
    }

    public String area(){


        Documento operator = new Documento();

        operator.adicionarLinha("function quad_area {");

        operator.adicionarLinha("	action param {");
        operator.adicionarLinha("		make a");
        operator.adicionarLinha("	}");

        operator.adicionarLinha("	action return {");

        operator.adicionarLinha("		def a");
        operator.adicionarLinha("		mux a ");

        operator.adicionarLinha("		make c");
        operator.adicionarLinha("		apply c");

        operator.adicionarLinha("		ret c");
        operator.adicionarLinha("	}");

        operator.adicionarLinha("}");


        operator.adicionarLinha("function rect_area {");

        operator.adicionarLinha("	action param {");
        operator.adicionarLinha("		make a");
        operator.adicionarLinha("		make b");
        operator.adicionarLinha("	}");

        operator.adicionarLinha("	action return {");

        operator.adicionarLinha("		def a");
        operator.adicionarLinha("		mux b ");

        operator.adicionarLinha("		make c");
        operator.adicionarLinha("		apply c");

        operator.adicionarLinha("		ret c");
        operator.adicionarLinha("	}");

        operator.adicionarLinha("}");

        return operator.getConteudo();
    }
}
