package Sigmaz.S08_Ferramentas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.AST;

public class Internal {

    private ArrayList<AST> mRaizes;

    public Internal(ArrayList<AST> eRaiz) {
        mRaizes = eRaiz;

    }


    public void exportarReal(String eLocal, String eNome) {

        for (AST mRaiz : mRaizes) {


            int actions = 0;
            int functions = 0;
            int directors = 0;
            int operators = 0;
            int calls = 0;

            int defines = 0;
            int mockizes = 0;

            int stages = 0;
            int types = 0;
            int structs = 0;
            int models = 0;

            int external = 0;
            int packages = 0;

            for (AST eAST : mRaiz.getASTS()) {


                if (eAST.mesmoTipo("CALL")) {
                    calls += 1;
                } else if (eAST.mesmoTipo("DEFINE")) {
                    defines += 1;
                } else if (eAST.mesmoTipo("MOCKIZ")) {
                    mockizes += 1;
                } else if (eAST.mesmoTipo("ACTION")) {
                    actions += 1;
                } else if (eAST.mesmoTipo("FUNCTION")) {
                    functions += 1;
                } else if (eAST.mesmoTipo("OPERATOR")) {
                    operators += 1;
                } else if (eAST.mesmoTipo("DIRECTOR")) {
                    directors += 1;
                } else if (eAST.mesmoTipo("STRUCT")) {
                    structs += contagemStruct(eAST);
                } else if (eAST.mesmoTipo("MODEL")) {
                    models += contagemModel(eAST);
                } else if (eAST.mesmoTipo("PACKAGE")) {
                    packages += contagemPackage(eAST);
                }


            }


            int todos = defines + mockizes + calls + actions + functions + operators + directors + structs + models + packages;


            int eLargura = 600;
            int eAltura = 100 + 100 + (todos * 50) + 100;

            BufferedImage img = new BufferedImage(4 * eLargura, eAltura, BufferedImage.TYPE_INT_ARGB);
            Graphics g = img.getGraphics();

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 4 * eLargura, eAltura);


            g.setColor(new Color(231, 76, 60));
            g.fillRect(eLargura, 0, eLargura, 100);

            g.setColor(Color.BLACK);
            centerString(g, new Rectangle(eLargura, 0, eLargura, 100), "SIGMAZ", new Font("TimesRoman", Font.BOLD, 50));


            int t = 150;
            int t_oringinal = 150;

            int x = eLargura + 50;

            t += fatia(g, x, t, calls, "CALL", new Color(149, 165, 166));

            t += fatia(g, x, t, defines, "DEFINES", new Color(22, 160, 133));

            t += fatia(g, x, t, mockizes, "MOCKIZES", new Color(41, 128, 185));

            t += fatia(g, x, t, actions, "ACTIONS", new Color(241, 196, 15));

            t += fatia(g, x, t, functions, "FUNCTIONS", new Color(52, 152, 219));

            t += fatia(g, x, t, directors, "DIRECTORS", new Color(155, 89, 182));

            t += fatia(g, x, t, operators, "OPERATORS", new Color(52, 73, 94));

            int ts = t;

            t += fatia(g, x, t, structs, "STRUCTS", new Color(46, 204, 113));

            t += fatia(g, x, t, models, "MODELS", new Color(211, 84, 0));

            int tp = t;
            t += fatia(g, x, t, packages, "PACKAGES", new Color(230, 126, 34));


            fatia(g, 50, ts, structs, "STRUCTS", new Color(46, 204, 113));

            int eStructX = 50;
            int eStructY = ts;

            boolean sim = true;

            for (AST eAST : mRaiz.getASTS()) {
                if (eAST.mesmoTipo("STRUCT")) {

                    int tamanho = contagemStruct(eAST);


                    if (sim) {
                        sim = false;
                        eStructY += fatia(g, eStructX, eStructY, tamanho, eAST.getNome(), new Color(46, 204, 113));

                    } else {
                        sim = true;
                        eStructY += fatia(g, eStructX, eStructY, tamanho, eAST.getNome(), new Color(39, 174, 96));

                    }


                }

            }

            int ePackageX = eLargura + eLargura + 100;
            int ePackageY = tp;

            fatia(g, ePackageX, tp, packages, "PACKAGES", new Color(230, 126, 34));

            sim = true;

            for (AST eAST : mRaiz.getASTS()) {
                if (eAST.mesmoTipo("PACKAGE")) {

                    int tamanho = contagemPackage(eAST);


                    if (sim) {
                        sim = false;
                        ePackageY += fatia(g, ePackageX, ePackageY, tamanho, eAST.getNome(), new Color(230, 126, 34));

                    } else {
                        sim = true;
                        ePackageY += fatia(g, ePackageX, ePackageY, tamanho, eAST.getNome(), new Color(211, 84, 0));

                    }


                }

            }

            sim = true;
            ePackageX = eLargura + eLargura + eLargura + 150;
            ePackageY = tp;
            int subY = ePackageY;

            for (AST eAST : mRaiz.getASTS()) {
                if (eAST.mesmoTipo("PACKAGE")) {

                    int tamanho = contagemPackage(eAST);


                    Color eCor = Color.WHITE;
                    if (sim) {
                        sim = false;
                        eCor = new Color(230, 126, 34);
                    } else {
                        sim = true;
                        eCor = new Color(211, 84, 0);
                    }

                    subY += fatia(g, ePackageX, subY, 1, eAST.getNome(), eCor);

                    boolean struct_sim = true;
                    Color struct_cor = Color.WHITE;

                    for (AST sAST : eAST.getASTS()) {
                        if (sAST.mesmoTipo("STRUCT")) {

                            if (struct_sim) {
                                struct_sim = false;
                                struct_cor = new Color(46, 204, 113);

                            } else {
                                struct_sim = true;
                                struct_cor = new Color(39, 174, 96);

                            }

                            int subtamanho = contagemStruct(sAST);

                            subY += fatia(g, ePackageX, subY, subtamanho, sAST.getNome(), struct_cor);

                        } else if (sAST.mesmoTipo("MODEL")) {

                            int subtamanho = contagemModel(sAST);

                            subY += fatia(g, ePackageX, subY, subtamanho, sAST.getNome(), new Color(142, 68, 173));

                        }

                    }

                }

            }

            g.setColor(Color.BLACK);
            g.drawRect(x, t_oringinal, 500, (todos * 50));


            try {
                File outputfile = new File(eLocal + eNome + ".png");
                ImageIO.write(img, "png", outputfile);
            } catch (IOException o) {

            }
        }
    }

    public void exportarPorcentagem(String eLocal, String eNome) {

        for (AST mRaiz : mRaizes) {

            int actions = 0;
            int functions = 0;
            int directors = 0;
            int operators = 0;
            int calls = 0;

            int defines = 0;
            int mockizes = 0;

            int stages = 0;
            int types = 0;
            int structs = 0;
            int models = 0;

            int external = 0;
            int packages = 0;

            for (AST eAST : mRaiz.getASTS()) {


                if (eAST.mesmoTipo("CALL")) {
                    calls += 1;
                } else if (eAST.mesmoTipo("DEFINE")) {
                    defines += 1;
                } else if (eAST.mesmoTipo("MOCKIZ")) {
                    mockizes += 1;
                } else if (eAST.mesmoTipo("ACTION")) {
                    actions += 1;
                } else if (eAST.mesmoTipo("FUNCTION")) {
                    functions += 1;
                } else if (eAST.mesmoTipo("OPERATOR")) {
                    operators += 1;
                } else if (eAST.mesmoTipo("DIRECTOR")) {
                    directors += 1;
                } else if (eAST.mesmoTipo("STRUCT")) {
                    structs += contagemStruct(eAST);
                } else if (eAST.mesmoTipo("MODEL")) {
                    models += contagemModel(eAST);
                } else if (eAST.mesmoTipo("PACKAGE")) {
                    packages += contagemPackage(eAST);
                }


            }

            int todos = defines + mockizes + calls + actions + functions + operators + directors + structs + models + packages;

            if (todos > 0) {

                float taxa = (float) 100.0 / (float) todos;

                todos = 100;

                if (defines > 0) {
                    defines = inteiro(multistring(defines, taxa));
                    if (defines == 0) {
                        defines = 1;
                    }
                }

                if (mockizes > 0) {
                    mockizes = inteiro(multistring(mockizes, taxa));
                    if (mockizes == 0) {
                        mockizes = 1;
                    }
                }

                if (calls > 0) {
                    calls = inteiro(multistring(calls, taxa));
                    if (calls == 0) {
                        calls = 1;
                    }
                }

                if (actions > 0) {
                    actions = inteiro(multistring(actions, taxa));
                    if (actions == 0) {
                        actions = 1;
                    }
                }

                if (functions > 0) {
                    functions = inteiro(multistring(functions, taxa));
                    if (functions == 0) {
                        functions = 1;
                    }
                }

                if (operators > 0) {
                    operators = inteiro(multistring(operators, taxa));
                    if (operators == 0) {
                        operators = 1;
                    }
                }


                if (directors > 0) {
                    directors = inteiro(multistring(directors, taxa));
                    if (directors == 0) {
                        directors = 1;
                    }
                }
                if (structs > 0) {
                    structs = inteiro(multistring(structs, taxa));
                    if (structs == 0) {
                        structs = 1;
                    }
                }
                if (models > 0) {
                    models = inteiro(multistring(models, taxa));
                    if (models == 0) {
                        models = 1;
                    }
                }
                if (packages > 0) {
                    packages = inteiro(multistring(packages, taxa));
                    if (packages == 0) {
                        packages = 1;
                    }
                }


                todos = defines + mockizes + calls + actions + functions + operators + directors + structs + models + packages;

            }

            int eLargura = 600;
            int eAltura = 100 + 100 + (todos * 50) + 100;

            BufferedImage img = new BufferedImage(eLargura * 3, eAltura, BufferedImage.TYPE_INT_ARGB);
            Graphics g = img.getGraphics();

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, eLargura * 3, eAltura);


            g.setColor(new Color(231, 76, 60));
            g.fillRect(eLargura, 0, eLargura, 100);

            g.setColor(Color.BLACK);
            centerString(g, new Rectangle(eLargura, 0, eLargura, 100), "SIGMAZ", new Font("TimesRoman", Font.BOLD, 50));


            int y = 150;
            int t_oringinal = 150;

            int x = eLargura + 50;

            y += fatia(g, x, y, calls, "CALL", new Color(149, 165, 166));

            y += fatia(g, x, y, defines, "DEFINES", new Color(22, 160, 133));

            y += fatia(g, x, y, mockizes, "MOCKIZES", new Color(41, 128, 185));

            y += fatia(g, x, y, actions, "ACTIONS", new Color(241, 196, 15));

            y += fatia(g, x, y, functions, "FUNCTIONS", new Color(52, 152, 219));

            y += fatia(g, x, y, directors, "DIRECTORS", new Color(155, 89, 182));

            y += fatia(g, x, y, operators, "OPERATORS", new Color(52, 73, 94));

            int ys = y;

            fatia(g, 50, ys, structs, "STRUCTS", new Color(46, 204, 113));


            y += fatia(g, x, y, structs, "STRUCTS", new Color(46, 204, 113));

            y += fatia(g, x, y, models, "MODELS", new Color(211, 84, 0));

            int py = y;

            y += fatia(g, x, y, packages, "PACKAGES", new Color(230, 126, 34));

            fatia(g, x + 50 + eLargura, py, packages, "PACKAGES", new Color(230, 126, 34));


            g.setColor(Color.BLACK);
            g.drawRect(x, t_oringinal, 500, (todos * 50));


            try {
                File outputfile = new File(eLocal + eNome + ".png");
                ImageIO.write(img, "png", outputfile);
            } catch (IOException o) {

            }
        }

    }


    public void exportar(String eLocal) {

        exportarPorcentagem(eLocal, "Sigmaz");
        exportarReal(eLocal, "Sigmaz_Real");

        System.out.println(" -->> EXPORTAR Estrutura Interna - Sigmaz");

    }


    public String multistring(int a, float b) {
        float c = ((float) a * (b));

        return String.valueOf(c);
    }

    public int inteiro(String s) {
        int i = 0;
        int o = s.length();

        String ret = "";
        while (i < o) {
            String l = s.charAt(i) + "";
            if (l.contentEquals(".")) {
                break;
            } else {
                ret += l;
            }
            i += 1;
        }


        return Integer.parseInt(ret);
    }

    public int fatia(Graphics g, int x, int y, int eValor, String eNome, Color eCor) {

        int tamanho = eValor * 50;

        if (eValor > 0) {


            g.setColor(eCor);
            g.fillRect(x, y, 500, tamanho);

            g.setColor(Color.BLACK);
            centerString(g, new Rectangle(x, y, 500, tamanho), eNome, new Font("TimesRoman", Font.BOLD, 30));

            g.setColor(Color.BLACK);
            g.drawRect(x, y, 500, tamanho);


        }
        return tamanho;
    }


    public int contagemModel(AST ASTPai) {
        int ret = 1;
        for (AST eAST : ASTPai.getASTS()) {

            ret += 1;

        }
        return ret;
    }

    public int contagemStruct(AST ASTPai) {

        int ret = 1;

        if (ASTPai.existeBranch("STAGES")) {
            for (AST eAST : ASTPai.getBranch("STAGES").getASTS()) {

                ret += 1;

            }
        }


        if (ASTPai.existeBranch("INITS")) {
            for (AST eAST : ASTPai.getBranch("INITS").getASTS()) {
                ret += 1;
            }
        }


        for (AST eAST : ASTPai.getBranch("BODY").getASTS()) {


            if (eAST.mesmoTipo("ACTION")) {
                ret += 1;
            } else if (eAST.mesmoTipo("FUNCTION")) {
                ret += 1;
            } else if (eAST.mesmoTipo("OPERATOR")) {
                ret += 1;
            } else if (eAST.mesmoTipo("DEFINE")) {
                ret += 1;
            } else if (eAST.mesmoTipo("MOCKIZ")) {
                ret += 1;
            }


        }

        return ret;
    }

    public int contagemPackage(AST ASTPai) {

        int ret = 1;

        for (AST eAST : ASTPai.getASTS()) {


            if (eAST.mesmoTipo("STRUCT")) {
                ret += contagemStruct(eAST);
            } else if (eAST.mesmoTipo("MODEL")) {
                ret += contagemModel(eAST);
            }


        }

        return ret;
    }


    public void centerString(Graphics g, Rectangle r, String s,
                             Font font) {
        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;

        g.setFont(font);
        g.drawString(s, r.x + a, r.y + b);
    }
}
