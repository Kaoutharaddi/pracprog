package Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Llista d'usuaris.
 * Manté els usuaris ordenats per alias en ordre alfabètic.
 * Podem afegir usuaris, cercar un usuari, eliminar un usuari i ampliar la mida de la llista.
 * @author Paula Arco
 */
public class LlistaUsuaris {

    private Usuari[] usuaris;
    private int mida;
    private static final int capacitat = 10;

    public LlistaUsuaris() {
        usuaris = new Usuari[capacitat];
        mida = 0;
    }

    public int getMida() {
        return mida;
    }

    public Usuari getUsuari(int i) { 
        return usuaris[i]; 
    }

    /**
     * Afegeix un usuari mantenint l'ordre alfabètic per alias.
     * Recorregut lineal sense break.
     */
    public void afegir(Usuari u) {
        if (u == null) {
            throw new IllegalArgumentException("Usuari nul");
        }

        // Ampliar capacitat si cal
        if (mida == usuaris.length) {
            ampliarCapacitat();
        }

        int pos = 0;
        boolean continuar = true;

        while (pos < mida && continuar) {
            if (usuaris[pos].getAlias().compareToIgnoreCase(u.getAlias()) < 0) {
                pos++;
            } else {
                continuar = false;
            }
        }

        // Comprovació duplicat
        if (pos < mida &&
                usuaris[pos].getAlias().equalsIgnoreCase(u.getAlias())) {
            throw new IllegalArgumentException("Ja existeix un usuari amb aquest alias");
        }

        // Desplaçar cap a la dreta
        int i = mida;
        while (i > pos) {
            usuaris[i] = usuaris[i - 1];
            i--;
        }

        usuaris[pos] = u;
        mida++;
    }

    /**
     * Cerca lineal d'un usuari pel seu alias.
     * Sense cap break.
     */
    public Usuari cercar(String alias) {
        if (alias == null || alias.isEmpty()) return null;

        int i = 0;
        boolean trobat = false;
        Usuari usuari = null;

        while (i < mida && !trobat) {

            if (usuaris[i].getAlias().compareToIgnoreCase(alias) == 0) {
                usuari = usuaris[i];
                trobat = true;
            } else if (usuaris[i].getAlias().compareToIgnoreCase(alias) > 0) {
                // ja hem passat el punt on podria estar ja quee testa en orde alfabetic
                trobat = true;
            }

            i++;
        }

        return usuari;
    }

    /**
     * Elimina un usuari pel seu alias
     */
    public boolean eliminar(String alias) {
        if (alias == null || alias.isEmpty()) return false;

        int pos = -1;
        int i = 0;
        boolean trobat = false;

        while (i < mida && !trobat) {
            int cmp = usuaris[i].getAlias().compareToIgnoreCase(alias);

            if (cmp == 0) {
                pos = i;
                trobat = true;
            } else if (cmp > 0) {
                trobat = true; // ja no pot estar més endavant
            }

            i++;
        }

        if (pos == -1) return false;

        // Desplaçament cap a l'esquerra
        int j = pos;
        while (j < mida - 1) {
            usuaris[j] = usuaris[j + 1];
            j++;
        }

        usuaris[mida - 1] = null;
        mida--;

        return true;
    }

    /**
     * Amplia la capacitat.
     */
    private void ampliarCapacitat() {
        Usuari[] nou = new Usuari[usuaris.length * 2];

        int i = 0;
        while (i < usuaris.length) {
            nou[i] = usuaris[i];
            i++;
        }

        usuaris = nou;
    }

    /**
     * toString: una línia per usuari amb "alias - correu".
     */
    @Override
    public String toString() {
        String resultat = "";
        int i = 0;
        while (i < mida) {
            resultat = resultat
                    + usuaris[i].getAlias()
                    + " - "
                    + usuaris[i].getCorreuComplet()
                    + "\n";
            i++;
        }
        return resultat;
    }

    /**
     * Llegeix els usuaris del fitxer i els afegeix a la llista.
     */
    public void carregarDades(String nomFitxer) {
        try {
            File f = new File(nomFitxer);
            if (f.exists()) {
                Scanner lector = new Scanner(f);
                while (lector.hasNextLine()) {
                    String linia = lector.nextLine();
                    String[] d = linia.split(";"); // Separem per punts i coma
                    
                    String tipus = d[0];
                    if (tipus.equals("PDI")) {
                        afegir(new UsuariPDI(d[1], d[2], d[3], d[4]));
                    } else if (tipus.equals("PTGAS")) {
                        afegir(new UsuariPTGAS(d[1], d[2], d[3]));
                    } else if (tipus.equals("EST")) {
                        afegir(new UsuariEstudiant(d[1], d[2], d[3], Integer.parseInt(d[4])));
                    }
                }
                lector.close();
            }
        } catch (Exception e) {
            System.out.println("Error al carregar: " + e.getMessage());
        }
    }

    /**
     * Guarda tots els usuaris de la llista al fitxer.
     */
    public void guardarDades(String nomFitxer) {
        try {
            PrintWriter escriure = new PrintWriter(new FileWriter(nomFitxer));
            int i = 0;
            while (i < mida) {
                // Suposem que cada usuari té un mètode toStringFitxer que torna "TIPUS;alias;correu..."
                escriure.println(usuaris[i].toFitxer());
                i++;
            }
            escriure.close();
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }
}
