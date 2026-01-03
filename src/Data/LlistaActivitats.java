package Data;

/**
 * Classe que gestiona una llista d'activitats.
 * Permet afegir activitats, cercar-ne una pel seu nom i obtenir informació sobre la quantitat d'activitats guardades.
 * @author Judit Hidalgo
 */

public class LlistaActivitats {

    private Activitat[] activitats;
    private int nActivitats;

    /**
     * Constructor que crea una llista d'activitats amb una capacitat màxima.
     *
     * @param max Nombre màxim d'activitats que pot contenir la llista.
     */

    public LlistaActivitats(int max) {
        activitats = new Activitat[max];
        nActivitats = 0;
    }

    /**
     * Afegeix una activitat a la llista si encara hi ha espai disponible.
     *
     * @param a Activitat que es vol afegir.
     * @return true si l'activitat s'ha afegit correctament, false si la llista és plena.
     */

    public boolean afegir(Activitat a) {
        if (nActivitats >= activitats.length) 
            return false;
        
        activitats[nActivitats++] = a;

        return true;
    }

    /**
     * Cerca una activitat dins la llista pel seu nom (ignorant majúscules).
     *
     * @param nom Nom de l'activitat que es busca.
     * @return L'activitat trobada o null si no existeix.
     */

    public Activitat cercar(String nom) {
        for (int i = 0; i < nActivitats; i++) {
            if (activitats[i].getNom().equalsIgnoreCase(nom)) 
                return activitats[i];
        }

        return null;
    }

    /**
     * Retorna el nombre actual d'activitats emmagatzemades.
     *
     * @return Quantitat d'activitats dins la llista.
     */

    public int getSize() { 
        return nActivitats; 
    }

    /**
     * Retorna l'activitat que es troba en la posició indicada.
     *
     * @param pos Índex de l'activitat dins l'array.
     * @return Activitat en la posició especificada.
     */

    public Activitat get(int pos) { 
        return activitats[pos]; 
    }

    /**
     * Retorna una llista amb totes les activitats que estan actives
     * en una data concreta.
     *
     * @param data data a comprovar
     * @return llista d'activitats actives aquell dia
     */

    public LlistaActivitats activitatsActivesElDia(Data data) {
        LlistaActivitats resultat = new LlistaActivitats(nActivitats);

        for (int i = 0; i < getSize(); i++) {
            Activitat a = get(i);
            if (a.esActivaElDia(data)) {
                resultat.afegir(a);
            }
        }
        return resultat;
    }

    /**
     * Retorna una llista amb totes les activitats que tenen sessió
     * en una data concreta.
     *
     * @param data data a comprovar
     * @return llista d'activitats amb sessió aquell dia
     */

    public LlistaActivitats activitatsAmbSessioElDia(Data data) {
        LlistaActivitats resultat = new LlistaActivitats(nActivitats);

        for (int i = 0; i < getSize(); i++) {
            Activitat a = get(i);
            if (a.teSessioElDia(data)) {
                resultat.afegir(a);
            }
        }
        return resultat;
    }

    /**
     * Retorna una llista amb totes les activitats que es troben
     * en període d'inscripció en una data concreta.
     *
     * @param data data a comprovar
     * @return llista d'activitats en període d'inscripció
     */

    public LlistaActivitats activitatsEnPeriodeInscripcio(Data data) {
        LlistaActivitats resultat = new LlistaActivitats(nActivitats);

        for (int i = 0; i < getSize(); i++) {
            Activitat a = get(i);
            if (a.enPeriodeInscripcio(data)) {
                resultat.afegir(a);
            }
        }
        return resultat;
    }

}