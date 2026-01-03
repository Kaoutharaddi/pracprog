package Data;
import excepcio.BlocValidacions;

/**
 * Representa una activitat presencial que es repeteix setmanalment.
 * Es guarda: 
 * - dia de la setmana i franja horària
 * - data d'inici de les classes i durada en setmanes
 * - preu de l'activitat i límit de places (aforament)
 * - ubicació on es realitza (centre i ciutat)
 * * @author Judith Cunillera
 */

public class ActivitatPeriodica extends Activitat {

    private String diaSetmana;
    private String horari;
    private Data dataIniClass;
    private int numSetmanes;
    private int preu;
    private String nomCentre;
    private String ciutat;
    private int maxPlaces;

    /**
     * CONSTRUCTOR
     * 
     */

    public ActivitatPeriodica(String nom, String[] usuari, Data dataIniInscrip, 
        Data dataFiInscrip, // dades PARE (Activitat.java)
        String diaSetmana, String horari, Data dataIniClass, int numSetmanes, int preu, 
        String nomCentre, String ciutat, int maxPlaces) throws BlocValidacions {

        super(nom, usuari, dataIniInscrip, dataFiInscrip);
        this.diaSetmana = diaSetmana;
        this.horari = horari;
        this.dataIniClass = dataIniClass;
        this.numSetmanes = numSetmanes;  
        this.preu = preu;
        this.nomCentre = nomCentre; 
        this.ciutat = ciutat;
        this.maxPlaces = maxPlaces;

/**
 *  BLOC DE VALIDACIONS (ERRORS)
 */
    if (nom == null || nom.isEmpty()) {
            throw new BlocValidacions("El nom de l'activitat no pot estar buit");
        }
        if (preu < 0) {
            throw new BlocValidacions("El preu no pot ser negatiu!");
        }
        if (numSetmanes <= 0) {
            throw new BlocValidacions("La durada ha de ser de mínim 1 setmana");
        }
        if (maxPlaces <= 0) {
            throw new BlocValidacions("El nombre de places ha de ser positiu");
        }
        if (dataFiInscrip.esAbans(dataIniInscrip)) {
            throw new BlocValidacions("La data final d'inscripció no pot ser anterior a la inicial");
        }
        }    



//---------------------------------------------------
    /**
     * GETTER DIASETMANA
     */
    public String getDiaSetmana() {
        return diaSetmana;
    }
    /**
     * SETTER DIASETMANA???
     */

//---------------------------------------------------
    /**
     * GETTER HORARI
     */
    public String getHorari() {
        return horari;
    }
    /**
     * SETTER HORARI???
     */
//---------------------------------------------------
    /**
     * GETTER DATAINICLASS
     */
    public Data getDataIniClass() {
        return dataIniClass;
    }
    /**
     * SETTER DATAINICLASS???
     */
//---------------------------------------------------
    /**
     * GETTER NUMSETMANES
     */    
    public int getNumSetmanes() {
        return numSetmanes;
    }
    /**
     * SETTER NUMSETMANES???
     */
//---------------------------------------------------
    /**
     * GETTER PREU
     */
    public int getPreu() {
        return preu;
    }
    /**
     * SETTER PREU???
     */
//---------------------------------------------------
    /**
     * GETTER NOMCENTRE
     */
    public String getNomCentre() {
        return nomCentre;
    }
    /**
     * SETTER NOMCENTRE???
     */
//---------------------------------------------------
    /**
     * GETTER CIUTAT
     */
    public String getCiutat() {
        return ciutat;
    }
    /**
     * SETTER CIUTAT
     */
//---------------------------------------------------
    /**
     * GETTER MAXPLACES
     */
    public int getMaxPlaces() {
        return maxPlaces;
    }

/**
 * Calcula la data que acaben les classes sumant les setmanes de durada
 * @return Data amb la data final
 */
public Data getDataFiClass() {
    // Sumem numSetmanes * 7 dies a dataIniClass
    int diesTotal = this.numSetmanes * 7;
    return sumarDies(this.dataIniClass, diesTotal);
}

private static Data sumarDies(Data data, int dies) {
    int dia = data.getDia() + dies;
    int mes = data.getMes();
    int any = data.getAny();
    
    while (dia > Data.diesDelMes(mes, any)) {
        dia -= Data.diesDelMes(mes, any);
        mes++;
        if (mes > 12) {
            mes = 1;
            any++;
        }
    }
    return new Data(dia, mes, any);
}
/**
 * METODE per saber si activitat esta en curs en data concreta
 * @param data a comprovar
 * @return true si esta entre l'inici i el final de classes
 */
public boolean esActiva(Data data) {
        Data finalClasses = getDataFiClass();
        return !data.esAbans(dataIniClass) && !data.esDespres(finalClasses);
}

/**
 * toString
 */
    @Override
    public String toString() {
        return super.toString() + "\n" +
               "Tipus: Periòdica\n" +
               "Dia i Hora: " + diaSetmana + " (" + horari + ")\n" +
               // Mostrem el rang complet calculat
               "Durada Classes: del " + dataIniClass + " al " + getDataFiClass() + "\n" + 
               "Setmanes: " + numSetmanes + "\n" +
               "Lloc: " + nomCentre + " (" + ciutat + ")\n" +
               "Preu: " + preu + "€\n" +
               "Places: " + maxPlaces;
    }

    @Override
    public boolean esActivaElDia(Data data) {
        return esActiva(data);
    }

    @Override
    public boolean teSessioElDia(Data data) {
        // Comprovem si la data està dins del període i si coincideix amb el dia de la setmana
        // Per simplificar, assumim que té sessió si està activa
        return esActiva(data);
    }

    @Override
    public Data getDataIniciActivitat() {
        return dataIniClass;
    }

    @Override
    public Data getDataFiActivitat() {
        return getDataFiClass();
    }


}