package Data;

/**
 * Representa una data del calendari formada per dia, mes i any.
 * Proporciona les operacions bàsiques de validació i comparació necessàries per a la gestió d'activitats.
 * @author Judit Hidalgo
 */

public class Data {

    private int dia;
    private int mes;
    private int any;

    /**
     * Crea una nova data.
     *
     * @param dia dia del mes
     * @param mes mes de l'any
     * @param any any
     * @throws IllegalArgumentException si la data no és vàlida
     */

    public Data(int dia, int mes, int any) {

        if (!esDataValida(dia, mes, any)) {

            throw new IllegalArgumentException("Data invàlida: " + dia + "/" + mes + "/" + any);
        }

        this.dia = dia;
        this.mes = mes;
        this.any = any;
    }

    /**
     * Retorna el dia del mes.
     * @return dia
     */

    public int getDia() {

        return dia;
    }

    /**
     * Retorna el mes de l'any.
     * @return mes
     */

    public int getMes() {

        return mes;
    }

    /**
     * Retorna l'any.
     * @return any
     */

    public int getAny() {

        return any;
    }

    /**
     * Indica si aquesta data és anterior a una altra.
     *
     * @param altra data a comparar
     * @return true si aquesta data és anterior
     */

    public boolean esAbans(Data altra) {

        if (this.any < altra.any) 
            return true;

        if (this.any > altra.any) 
            return false;

        if (this.mes < altra.mes) 
            return true;

        if (this.mes > altra.mes) 
            return false;

        return this.dia < altra.dia;
    }

    /**
     * Indica si aquesta data és posterior a una altra.
     *
     * @param altra data a comparar
     * @return true si aquesta data és posterior
     */

    public boolean esDespres(Data altra) {

        if (this.any > altra.any) 
            return true;

        if (this.any < altra.any) 
            return false;

        if (this.mes > altra.mes) 
            return true;

        if (this.mes < altra.mes) 
            return false;

        return this.dia > altra.dia;
    }

    /**
     * Indica si dues dates són iguals.
     *
     * @param altra data a comparar
     * @return true si són la mateixa data
     */

    public boolean esIgual(Data altra) {
        return this.dia == altra.dia
                && this.mes == altra.mes
                && this.any == altra.any;
    }

    /**
     * Retorna la data en format dd/mm/aaaa.
     */

    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", dia, mes, any);
    }

    /**
     * Mètode que indica quants dies té el mes.
     * @param mes
     * @param any
     * @return els dies que té el més en aquell determinat any
     */

    public static int diesDelMes(int mes, int any) {

        if (mes == 2) {
            if (esBixest(any)) {
                return 29;
            } else {
                return 28;
            }
        }

        if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
            return 30;
        }

        return 31;
    }

    /**
     * Comprova si una data és vàlida.
     * 
     * @param dia
     * @param mes
     * @param any
     * @return false si la data no és vàlida
     */

    public static boolean esDataValida(int dia, int mes, int any) {

        if (any < 1) 
            return false;

        if (mes < 1 || mes > 12) 
            return false;

        if (dia < 1) 
            return false;

        return dia <= diesDelMes(mes, any);
    }

    /**
     * Indica si un any és bixest.
     * 
     * @param any
     * @return true si l'any és bixest o false si no ho és
     */

    public static boolean esBixest(int any) {

        if (any % 400 == 0) 
            return true;

        if (any % 100 == 0) 
            return false;

        return any % 4 == 0;
    }
}

