
status -> " -->> Executando MAKE pela primeira vez";

# CONFIGURACOES

    options build {

        stack_process = false;
        object = false;
        pos_process = false;
        analysis_process=false;
        ident_process = false;

    };


# LOCAIS PADROES

    build -> "build";
    source -> "";
    intellisenses -> "intellisenses";

    status -> " -->> SOURCE :: ${SOURCE}";
    status -> " -->> BUILD :: ${BUILD}";
    status -> " -->> INTELLISENSE :: ${INTELLISENSE}";


# REMOVER TEMPORARIOS

    status -> " -->> REMOVENDO ARQUIVOS *.a DA BUILD";
    remove build -> ".a";


# INTELLISENSES


    options intellisense {

        # PERSONALIZACAO DAS CORES DO INTELLISENSE

        background = [255, 255, 255];
        box = [0, 0, 0];
        text = [0, 0, 0];

        package = [0, 150, 136];
        sigmaz = [0, 150, 136];

        struct = [244, 67, 54];
        model = [158, 158, 158];
        stage = [255, 152, 0];
        cast = [205, 220, 57];
        external = [103, 58, 183];
        type = [76, 175, 80];


    };

    status -> " -->> INTELLISENSE -> LIB";

    generate intellisense "dark_" with -> "libs/lib.sigmaz";

# IDENTAR ARQUIVOS

    status -> " -->> IDENTAR -> LIB";

    ident with -> "libs/lib.sigmaz";


# GERAR BIBLIOTECA : NUM

    set author -> "Luan Freitas";
    set version -> "1.0.0";
    set company -> "DKG SOFTWARES";

    make library "num.a" with -> { "libs/neg.sigmaz" , "libs/pos.sigmaz" };

    status -> " -->> ARVORE DA BIBLIOTECA -> NUM";

    ast -> "num.a";

    #generate dependency "LISTA DE DEPENDENCIAS" with -> "libs/lib.sigmaz";
