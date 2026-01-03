package Data;

import java.io.Serializable;

import excepcio.InscripcioDuplicadaException;

/**
 * Classe que representa una llista d'inscripcions del sistema.
 *
 * NO es fan servir col·leccions de Java (ArrayList, etc.). Es fa amb arrays.
 * Es pot fer créixer l'array quan calgui.
 * @author Kaouthar Addi
 */
public class Llistainscripcions implements Serializable {

    private static final long serialVersionUID = 1L;

    private Inscripcio[] inscripcions;
    private int nInscripcions;

    /**
     * Crea una llista d'inscripcions amb una capacitat inicial.
     *
     * @param capacitatInicial nombre màxim inicial d'inscripcions
     *                         (si és <=0, s'usa 10 per defecte)
     */
    public Llistainscripcions(int capacitatInicial) {
        if (capacitatInicial <= 0) {
            capacitatInicial = 10;
        }
        this.inscripcions = new Inscripcio[capacitatInicial];
        this.nInscripcions = 0;
    }

    /**
     * Retorna el nombre d'inscripcions actuals a la llista.
     */
    public int getNombreInscripcions() {
        return nInscripcions;
    }

    /**
     * Retorna la inscripció en una posició concreta.
     *
     * @param index posició (0..nInscripcions-1)
     * @return inscripció a la posició indicada
     * @throws IndexOutOfBoundsException si l'índex és invàlid
     */
    public Inscripcio get(int index) {
        if (index < 0 || index >= nInscripcions) {
            throw new IndexOutOfBoundsException("Índex fora de rang");
        }
        return inscripcions[index];
    }

    /**
     * Afegeix una nova inscripció a la llista.
     * No es permeten duplicats (mateix usuari i mateixa activitat).
     *
     * @param nova inscripció a afegir (no pot ser nul·la)
     * @throws NullPointerException        si nova és nul·la
     * @throws InscripcioDuplicadaException si ja existeix una inscripció
     *                                      per al mateix usuari i activitat
     */
    public void afegirInscripcio(Inscripcio nova) throws InscripcioDuplicadaException {
        if (nova == null) {
            throw new NullPointerException("La inscripció no pot ser nul·la");
        }

        // Comprovar duplicats
        if (cercarInscripcio(nova.getAliasUsuari(), nova.getNomActivitat()) != null) {
            throw new InscripcioDuplicadaException("Ja existeix una inscripció d'aquest usuari a aquesta activitat");
        }

        assegurarCapacitat();
        inscripcions[nInscripcions] = nova;
        nInscripcions++;
    }

    /**
     * Busca una inscripció pel parell (aliasUsuari, nomActivitat).
     *
     * @param aliasUsuari  alias de l'usuari
     * @param nomActivitat nom de l'activitat
     * @return la inscripció trobada o null si no existeix
     */
    public Inscripcio cercarInscripcio(String aliasUsuari, String nomActivitat) {
        if (aliasUsuari == null || nomActivitat == null) {
            return null;
        }
        for (int i = 0; i < nInscripcions; i++) {
            Inscripcio ins = inscripcions[i];
            if (ins.getAliasUsuari().equalsIgnoreCase(aliasUsuari)
                    && ins.getNomActivitat().equalsIgnoreCase(nomActivitat)) {
                return ins;
            }
        }
        return null;
    }

    /**
     * Elimina una inscripció pel parell (aliasUsuari, nomActivitat).
     *
     * @param aliasUsuari  alias de l'usuari
     * @param nomActivitat nom de l'activitat
     * @return true si s'ha eliminat alguna inscripció, false en cas contrari
     */
    public boolean eliminarInscripcio(String aliasUsuari, String nomActivitat) {
        for (int i = 0; i < nInscripcions; i++) {
            Inscripcio ins = inscripcions[i];
            if (ins.getAliasUsuari().equalsIgnoreCase(aliasUsuari)
                    && ins.getNomActivitat().equalsIgnoreCase(nomActivitat)) {
                // moure l'últim element a aquesta posició
                inscripcions[i] = inscripcions[nInscripcions - 1];
                inscripcions[nInscripcions - 1] = null;
                nInscripcions--;
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna totes les inscripcions d'un usuari determinat.
     *
     * @param aliasUsuari alias de l'usuari
     * @return array amb les inscripcions de l'usuari (pot ser buit, però no nul)
     */
    public Inscripcio[] obtenirInscripcionsUsuari(String aliasUsuari) {
        if (aliasUsuari == null) {
            return new Inscripcio[0];
        }
        int comptador = 0;
        for (int i = 0; i < nInscripcions; i++) {
            if (inscripcions[i].getAliasUsuari().equalsIgnoreCase(aliasUsuari)) {
                comptador++;
            }
        }
        Inscripcio[] resultat = new Inscripcio[comptador];
        int idx = 0;
        for (int i = 0; i < nInscripcions; i++) {
            if (inscripcions[i].getAliasUsuari().equalsIgnoreCase(aliasUsuari)) {
                resultat[idx] = inscripcions[i];
                idx++;
            }
        }
        return resultat;
    }

    /**
     * Retorna totes les inscripcions d'una activitat determinada.
     *
     * @param nomActivitat nom de l'activitat
     * @return array amb les inscripcions de l'activitat (pot ser buit, però no nul)
     */
    public Inscripcio[] obtenirInscripcionsActivitat(String nomActivitat) {
        if (nomActivitat == null) {
            return new Inscripcio[0];
        }
        int comptador = 0;
        for (int i = 0; i < nInscripcions; i++) {
            if (inscripcions[i].getNomActivitat().equalsIgnoreCase(nomActivitat)) {
                comptador++;
            }
        }
        Inscripcio[] resultat = new Inscripcio[comptador];
        int idx = 0;
        for (int i = 0; i < nInscripcions; i++) {
            if (inscripcions[i].getNomActivitat().equalsIgnoreCase(nomActivitat)) {
                resultat[idx] = inscripcions[i];
                idx++;
            }
        }
        return resultat;
    }

    /**
     * Assegura que l'array intern tingui capacitat per afegir una nova inscripció.
     * Si no n'hi ha prou, es duplica la mida de l'array.
     */
    private void assegurarCapacitat() {
        if (nInscripcions == inscripcions.length) {
            int novaCapacitat = inscripcions.length * 2;
            Inscripcio[] nouArray = new Inscripcio[novaCapacitat];
            for (int i = 0; i < nInscripcions; i++) {
                nouArray[i] = inscripcions[i];
            }
            inscripcions = nouArray;
        }
    }
    public int getMida() {
    return getNombreInscripcions();
    }
    public Inscripcio getInscripcio(int i) {
    return get(i);
    }

}

