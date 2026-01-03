package Data;

import java.io.Serializable;
import excepcio.ValoracioForaDeRangException;

/**
 * Classe que representa una inscripció d'un usuari a una activitat.
 *
 * Es guarda:
 *  - nom de l'activitat
 *  - alias de l'usuari
 *  - data d'inscripció
 *  - si està en llista d'espera o és participant confirmat
 *  - si ha assistit (només té sentit per participants, no per llista d'espera)
 *  - valoració de l'activitat (entre 0 i 10, només participants; null si no valorada)
 * @author Kaouthar Addi
 */
public class Inscripcio implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nomActivitat;
    private String aliasUsuari;
    private Data dataInscripcio;
    private boolean enLlistaEspera;
    private boolean haAssistit;
    private Integer valoracio; // null si encara no ha valorat

    /**
     * Crea una nova inscripció.
     *
     * @param nomActivitat nom de l'activitat (no pot ser nul ni buit)
     * @param aliasUsuari alias de l'usuari (no pot ser nul ni buit)
     * @param dataInscripcio data de la inscripció (no pot ser nul·la)
     * @param enLlistaEspera indica si l'usuari entra ja com a llista d'espera
     * @throws IllegalArgumentException si alguna dada és incorrecta
     */
    public Inscripcio(String nomActivitat,
                      String aliasUsuari,
                      Data dataInscripcio,
                      boolean enLlistaEspera) {
        setNomActivitat(nomActivitat);
        setAliasUsuari(aliasUsuari);
        setDataInscripcio(dataInscripcio);
        this.enLlistaEspera = enLlistaEspera;
        this.haAssistit = false;
        this.valoracio = null;
    }

    public String getNomActivitat() {
        return nomActivitat;
    }

    public void setNomActivitat(String nomActivitat) {
        if (nomActivitat == null || nomActivitat.trim().isEmpty()) {
            throw new IllegalArgumentException("El nom de l'activitat no pot ser nul ni buit");
        }
        this.nomActivitat = nomActivitat.trim();
    }

    public String getAliasUsuari() {
        return aliasUsuari;
    }

    public void setAliasUsuari(String aliasUsuari) {
        if (aliasUsuari == null || aliasUsuari.trim().isEmpty()) {
            throw new IllegalArgumentException("L'alias de l'usuari no pot ser nul ni buit");
        }
        this.aliasUsuari = aliasUsuari.trim();
    }

    public Data getDataInscripcio() {
        return dataInscripcio;
    }

    public void setDataInscripcio(Data dataInscripcio) {
        if (dataInscripcio == null) {
            throw new IllegalArgumentException("La data d'inscripció no pot ser nul·la");
        }
        this.dataInscripcio = dataInscripcio;
    }

    public boolean isEnLlistaEspera() {
        return enLlistaEspera;
    }

    public void setEnLlistaEspera(boolean enLlistaEspera) {
        this.enLlistaEspera = enLlistaEspera;
    }

    public boolean isHaAssistit() {
        return haAssistit;
    }

    public void setHaAssistit(boolean haAssistit) {
        this.haAssistit = haAssistit;
    }

    public Integer getValoracio() {
        return valoracio;
    }

    public boolean teValoracio() {
        return valoracio != null;
    }

    /**
     * Assigna una valoració a l'activitat per part de l'assistent.
     * Es controla que la nota estigui entre 0 i 10 (inclosos).
     *
     * @param nota valoració entre 0 i 10
     * @throws ValoracioForaDeRangException si la nota no és vàlida
     */
    public void setValoracio(int nota) throws ValoracioForaDeRangException {
        if (nota < 0 || nota > 10) {
            throw new ValoracioForaDeRangException("La valoració ha d'estar entre 0 i 10");
        }
        this.valoracio = nota;
    }

    /**
     * Dos inscripcions es consideren iguals si fan referència al mateix usuari
     * i a la mateixa activitat.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Inscripcio)) return false;
        Inscripcio altra = (Inscripcio) obj;
        return nomActivitat.equalsIgnoreCase(altra.nomActivitat)
                && aliasUsuari.equalsIgnoreCase(altra.aliasUsuari);
    }

    @Override
    public int hashCode() {
        return (nomActivitat.toLowerCase() + "#" + aliasUsuari.toLowerCase()).hashCode();
    }

    @Override
    public String toString() {
        return "Inscripcio{" +
                "nomActivitat='" + nomActivitat + '\'' +
                ", aliasUsuari='" + aliasUsuari + '\'' +
                ", dataInscripcio=" + dataInscripcio +
                ", enLlistaEspera=" + enLlistaEspera +
                ", haAssistit=" + haAssistit +
                ", valoracio=" + valoracio +
                '}';
    }
}
