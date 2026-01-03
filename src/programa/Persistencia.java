package programa;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import Data.*;
import excepcio.BlocValidacions;
/**
 * @author Kaouthar Addi
 */
public class Persistencia {

    public static final String DIR = "fitxers";
    public static final String FIT_ACT = DIR + "/activitats.txt";
    public static final String FIT_USU = DIR + "/usuaris.txt";
    public static final String FIT_INS = DIR + "/inscripcions.ser";

    /**
     * Assegura que la carpeta de fitxers existeix. Si no existeix, la crea.
     */
    public static void assegurarCarpeta() {
        try { Files.createDirectories(Paths.get(DIR)); } catch (IOException ignored) {}
    }

    /**
     * Converteix una cadena de text amb format dd/mm/aaaa a un objecte Data.
     * 
     * @param s la cadena a analitzar en format dd/mm/aaaa
     * @return l'objecte Data corresponent
     */
    private static Data parseData(String s) {
        // dd/mm/aaaa
        String[] p = s.trim().split("/");
        int d = Integer.parseInt(p[0]);
        int m = Integer.parseInt(p[1]);
        int a = Integer.parseInt(p[2]);
        return new Data(d, m, a);
    }

    /**
     * Divideix una cadena de col·lectius separats per comes en un array de Strings.
     * Elimina els espais en blanc de cada col·lectiu.
     * 
     * @param s la cadena amb col·lectius separats per comes
     * @return un array amb els col·lectius processats
     */
    private static String[] parseCollectius(String s) {
        String[] p = s.split(",");
        for (int i = 0; i < p.length; i++) p[i] = p[i].trim();
        return p;
    }

    /**
     * Carrega les activitats des del fitxer activitats.txt i les afegeix a la llista proporcionada.
     * Suporta tres tipus d'activitats: ONL (online), PER (periòdica) i DIA (d'un dia).
     * Les línies incorrectes o que generin errors de validació es salten i es mostra un missatge.
     * 
     * @param out la llista on s'afegiran les activitats carregades
     */
    public static void carregarActivitats(LlistaActivitats out) {
        assegurarCarpeta();
        File f = new File(FIT_ACT);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String lin;
            while ((lin = br.readLine()) != null) {
                lin = lin.trim();
                if (lin.isEmpty() || lin.startsWith("#")) continue;

                String[] c = lin.split(";");
                String tipus = c[0].trim();

                try {
                    if (tipus.equalsIgnoreCase("ONL")) {
                        // ONL;nom;cols;dIniIns;dFiIns;dIniOnline;diesVisual;enllac
                        String nom = c[1].trim();
                        String[] cols = parseCollectius(c[2]);
                        Data dIniIns = parseData(c[3]);
                        Data dFiIns = parseData(c[4]);
                        Data dIniOnline = parseData(c[5]);
                        int diesVisual = Integer.parseInt(c[6].trim());
                        String enllac = c[7].trim();

                        ActivitatOnline a = new ActivitatOnline(
                                nom, cols, dIniIns, dFiIns, dIniOnline, diesVisual, enllac
                        );
                        out.afegir(a);

                    } else if (tipus.equalsIgnoreCase("PER")) {
                        // PER;nom;cols;dIniIns;dFiIns;diaSetmana;horari;dIniClass;numSetmanes;preu;centre;ciutat;maxPlaces
                        String nom = c[1].trim();
                        String[] cols = parseCollectius(c[2]);
                        Data dIniIns = parseData(c[3]);
                        Data dFiIns = parseData(c[4]);
                        String diaSetmana = c[5].trim();
                        String horari = c[6].trim();
                        Data dIniClass = parseData(c[7]);
                        int numSetmanes = Integer.parseInt(c[8].trim());
                        int preu = Integer.parseInt(c[9].trim());
                        String centre = c[10].trim();
                        String ciutat = c[11].trim();
                        int maxPlaces = Integer.parseInt(c[12].trim());

                        ActivitatPeriodica a = new ActivitatPeriodica(
                                nom, cols, dIniIns, dFiIns,
                                diaSetmana, horari, dIniClass, numSetmanes, preu,
                                centre, ciutat, maxPlaces
                        );
                        out.afegir(a);

                    } else if (tipus.equalsIgnoreCase("DIA")) {
                        // AJUSTA a tu constructor real de ActivitatDia:
                        // DIA;nom;cols;dIniIns;dFiIns;data;ciutat;maxPlaces;preu
                        String nom = c[1].trim();
                        String[] cols = parseCollectius(c[2]);
                        Data dIniIns = parseData(c[3]);
                        Data dFiIns = parseData(c[4]);
                        Data data = parseData(c[5]);
                        String ciutat = c[6].trim();
                        int maxPlaces = Integer.parseInt(c[7].trim());
                        double preu = Double.parseDouble(c[8].trim());

                        ActivitatDia a = new ActivitatDia(nom, cols, dIniIns, dFiIns, data, ciutat, maxPlaces, preu);
                        out.afegir(a);
                    }

                } catch (BlocValidacions e) {
                    System.out.println("Línia activitat incorrecta (validació): " + lin);
                } catch (Exception e) {
                    System.out.println("Línia activitat incorrecta: " + lin);
                }
            }

        } catch (IOException e) {
            System.out.println("Error llegint activitats: " + e.getMessage());
        }
    }

    /**
     * Carrega els usuaris des del fitxer usuaris.txt i els afegeix a la llista proporcionada.
     * Suporta tres tipus d'usuaris: PDI, PTGAS i EST (estudiant).
     * Les línies incorrectes es salten i es mostra un missatge d'error.
     * 
     * @param out la llista on s'afegiran els usuaris carregats
     */
    public static void carregarUsuaris(LlistaUsuaris out) {
        assegurarCarpeta();
        File f = new File(FIT_USU);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String lin;
            while ((lin = br.readLine()) != null) {
                lin = lin.trim();
                if (lin.isEmpty() || lin.startsWith("#")) continue;

                String[] c = lin.split(";");
                String tipus = c[0].trim();

                try {
                    if (tipus.equalsIgnoreCase("PDI")) {
                        // PDI;alias;prefix;departament;campus
                        out.afegir(new UsuariPDI(c[1].trim(), c[2].trim(), c[3].trim(), c[4].trim()));

                    } else if (tipus.equalsIgnoreCase("PTGAS")) {
                        // PTGAS;alias;prefix;campus
                        out.afegir(new UsuariPTGAS(c[1].trim(), c[2].trim(), c[3].trim()));

                    } else if (tipus.equalsIgnoreCase("EST")) {
                        // EST;alias;prefix;ensenyament;anyInici
                        out.afegir(new UsuariEstudiant(c[1].trim(), c[2].trim(), c[3].trim(), Integer.parseInt(c[4].trim())));
                    }
                } catch (Exception e) {
                    System.out.println("Línia usuari incorrecta: " + lin);
                }
            }

        } catch (IOException e) {
            System.out.println("Error llegint usuaris: " + e.getMessage());
        }
    }
}
