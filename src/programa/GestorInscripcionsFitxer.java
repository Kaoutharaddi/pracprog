package programa;

import java.io.*;

import Data.Llistainscripcions;

/**
 * Gestiona la persistència de dades de les inscripcions en fitxer.
 * Permet guardar i carregar la llista d'inscripcions usant serialització d'objectes Java.
 * 
 * @author Kaouthar Addi
 */
public class GestorInscripcionsFitxer {

    public static void guardar(String nomFitxer, Llistainscripcions llista) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomFitxer))) {
            oos.writeObject(llista);
        }
    }

    public static Llistainscripcions carregar(String nomFitxer, int capacitatPerDefecte)
            throws IOException, ClassNotFoundException {

        File f = new File(nomFitxer);
        if (!f.exists()) {
            return new Llistainscripcions(capacitatPerDefecte);
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return (Llistainscripcions) ois.readObject();
        }
    }
}
