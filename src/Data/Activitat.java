package Data;

/**
 * Classe base per a classes ActivitatDia, ActivitatPeriodica i ActivitatOnline.
 * @author Judit Hidalgo
 */

public abstract class Activitat {
    private String nom;
    private String[] colectiusOferits;
    private Data dataInscripcioInicial;
    private Data dataInscripcioFinal;

    /**
     * Constructor per crear una instància d'Activitat.
     * @param nom Nom de l'activitat.
     * @param colectiusOferits Llistat de col·lectius destinataris (PDI, PTGAS, Estudiants).
     * @param dataInscripcioInicial La data d'inici del període d'inscripció.
     * @param dataInscripcioFinal La data final del període d'inscripció.
     */

    public Activitat(String nom, String[] colectiusOferits, Data dataInscripcioInicial, Data dataInscripcioFinal) {
        this.nom = nom;
        this.colectiusOferits = colectiusOferits;
        this.dataInscripcioInicial = dataInscripcioInicial;
        this.dataInscripcioFinal = dataInscripcioFinal;
    }

    /**
     * Retorna el nom de l'activitat.
     * @return nom 
     */

    public String getNom() { 
        return nom; 
    }

    /**
     * Retorna el llistat de col·lectius destinataris.
     * @return llistat de col·lectius
     */

    public String[] getColectiusOferits() { 
        return colectiusOferits; 
    }

    /**
     * Retorna la data d'inici del període d'inscripció.
     * @return data d'inici
     */

    public Data getDataInscripcioInicial() { 
        return dataInscripcioInicial; 
    }

    /**
     * Retorna la data final del període d'inscripció.
     * @return la data final del període d'inscripció.
     */

    public Data getDataInscripcioFinal() { 
        return dataInscripcioFinal; 
    }

    /**
     * Indica si l'activitat està activa en una data concreta.
     *
     * @param data data a comprovar
     * @return true si l'activitat està activa aquell dia
     */

    public abstract boolean esActivaElDia(Data data);

    /**
     * Indica si l'activitat té sessió en una data concreta.
     *
     * @param data data a comprovar
     * @return true si l'activitat té sessió aquell dia
     */

    public abstract boolean teSessioElDia(Data data);

    /**
     * Retorna la data d'inici de l'activitat.
     *
     * @return data d'inici de l'activitat
     */

    public abstract Data getDataIniciActivitat();

    /**
     * Retorna la data de fi de l'activitat.
     *
     * @return data de fi de l'activitat
     */

    public abstract Data getDataFiActivitat();

    /**
     * Indica si una data es troba dins del període d'inscripció de l'activitat.
     *
     * @param data data a comprovar
     * @return true si la data està dins del període d'inscripció
     */

    public boolean enPeriodeInscripcio(Data data) {
        if (data == null || dataInscripcioInicial == null || dataInscripcioFinal == null) {
            return false;
        }

        return !data.esAbans(dataInscripcioInicial) && !data.esDespres(dataInscripcioFinal);
    }
}