package Data;

import excepcio.BlocValidacions;

/**
 * Representa una activitat virtual, asíncrona i gratuïta.
 * Es guarda: 
 * - data en què el contingut es fa accessible
 * - nombre de dies que romandrà visible
 * - enllaç web (URL) per accedir al recurs
 * * @author Judith Cunillera
 */
public class ActivitatOnline extends Activitat {

    private Data dataIniOnline;
    private int diesVisual;
    private String enllac;

    public ActivitatOnline(String nom, String[] usuari, Data dataIniInscrip, 
        Data dataFiInscrip, // dades PARE (Activitat.java) 
        Data dataIniOnline, int diesVisual, String enllac) throws BlocValidacions{
    
        super(nom, usuari, dataIniInscrip, dataFiInscrip);


/**
 *  BLOC DE VALIDACIONS (ERRORS)
 */
if (nom == null || nom.isEmpty()) {

        throw new BlocValidacions("El nom de l'activitat no pot estar buit.");
    }
    if (diesVisual <= 0) {
        throw new BlocValidacions("El període de visualització ha de ser de mínim 1 dia.");
    }
    if (enllac == null || enllac.isEmpty()) {
        throw new BlocValidacions("L'enllaç de l'activitat és obligatori.");
    }
// Dades propies
        this.dataIniOnline = dataIniOnline;
        this.diesVisual = diesVisual;
        this.enllac = enllac;
}


//---------------------------------------------------
    /**
     * GETTER DATAINIONLINE
     */
    public Data getDataIniOnline() {
        return dataIniOnline;
    }
    /**
     * SETTER DATAONLINE???
     */
//---------------------------------------------------
    /**
     * GETTER DIESVISUAL
     */
    public int getDiesVisual() {
        return diesVisual;
    }
    /**
     * SETTER DIESVISUAL???
     */
//---------------------------------------------------
    /**
     * GETTER ENLLAC
     */
    public String getEnllac() {
        return enllac;
    }
    /**
     * SETTER ENLLAC???
     */
//--------------------------------------------------- 
/**
 * MÈTODE TOSTRING
 */


    @Override
    public String toString() {
        return super.toString() + "\n" + 
        "       ActivitatOnline \n" + 
        " | Inici: " + dataIniOnline + 
        "\n | Dies visible: " + diesVisual +
        "\n | Enllaç: " + enllac;
    }

    @Override
    public boolean esActivaElDia(Data data) {
        Data dataFi = getDataFiActivitat();
        return !data.esAbans(dataIniOnline) && !data.esDespres(dataFi);
    }

    @Override
    public boolean teSessioElDia(Data data) {
        // Les activitats online no tenen sessions específiques, es considera que
        // tenen sessió tots els dies que estiguin actives
        return esActivaElDia(data);
    }

    @Override
    public Data getDataIniciActivitat() {
        return dataIniOnline;
    }

    @Override
    public Data getDataFiActivitat() {
        // Suma els dies de visualització a la data d'inici
        int dia = dataIniOnline.getDia() + diesVisual;
        int mes = dataIniOnline.getMes();
        int any = dataIniOnline.getAny();
        
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


}

