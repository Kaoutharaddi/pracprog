package Data;

/**
 * Representa una activitat d'un sol dia.
 * Inclou la data de realització, la ciutat, el límit de places i el preu total de l'activitat.
 * @author Judit Hidalgo
 */

public class ActivitatDia extends Activitat {
    private Data data;
    private String ciutat;
    private int limitPlaces;
    private double preu;

    /**
     * Constructor per crear una activitat d'un sol dia.
     *
     * @param nom Nom de l'activitat.
     * @param colectius Llista de col·lectius als quals s'ofereix l'activitat (PDI, PTGAS, Estudiants).
     * @param dataIniciInscripcio Data d'inici del període d'inscripció.
     * @param dataFiInscripcio Data final del període d'inscripció.
     * @param data Data exacta en què es realitza l'activitat.
     * @param ciutat Ciutat on té lloc l'activitat.
     * @param limitPlaces Nombre màxim de participants.
     * @param preu Cost total de l'activitat.
     */

    public ActivitatDia(String nom, String[] colectius, Data dataIniciInscripcio, Data dataFiInscripcio, Data data, String ciutat, int limitPlaces, double preu) {
        super(nom, colectius, dataIniciInscripcio, dataFiInscripcio);
        this.data = data;
        this.ciutat = ciutat;
        this.limitPlaces = limitPlaces;
        this.preu = preu;
    }

    /**
     * Retorna la data de realització de l'activitat.
     * @return Data de l'activitat.
     */

    public Data getData() { 
        return data; 
    }

    /**
     * Retorna la ciutat on es duu a terme l'activitat.
     * @return Ciutat de realització.
     */

    public String getCiutat() { 
        return ciutat; 
    }

    /**
     * Retorna el nombre màxim de places disponibles.
     * @return Límit de participants.
     */

    public int getLimitPlaces() { 
        return limitPlaces; 
    }

    /**
     * Retorna el preu total de l'activitat.
     * @return Preu en euros.
     */

    public double getPreu() { 
        return preu; 
    }

    /* Implementació dels mètodes abstractes */

    /**
     * Indica si l'activitat està activa el dia indicat.
     *
     * @param d data a comprovar
     * @return true si l'activitat està activa aquell dia
     */

    @Override
    public boolean esActivaElDia(Data d) {
        if (d == null) 
            return false;

        return data.esIgual(d);
    }

    /**
     * Indica si l'activitat té sessió el dia indicat.
     *
     * @param d data a comprovar
     * @return true si l'activitat té sessió aquell dia
     */

    @Override
    public boolean teSessioElDia(Data d) {
        if (d == null) 
            return false;

        return data.esIgual(d);
    }

    /**
     * Retorna la data d'inici de l'activitat.
     *
     * @return data d'inici de l'activitat
     */

    @Override
    public Data getDataIniciActivitat() {
        return data;
    }

    /**
     * Retorna la data de fi de l'activitat.
     *
     * @return data de fi de l'activitat
     */
    
    @Override
    public Data getDataFiActivitat() {
        return data;
    }
}