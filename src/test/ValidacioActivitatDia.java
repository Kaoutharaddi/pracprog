package test;

import Data.ActivitatDia;

/**
* Classe que valida les dades específiques d'una activitat d'un dia
* @author Judit Hidalgo
*/

public class ValidacioActivitatDia {

    /**
    * Valida que una activitat d'un dia sigui correcta.
    * 
    * @param a Instància d'ActivitatDia que es vol validar.
    * @throws Exception si alguna dada no compleix els requisits.
    */

    public static void validar(ActivitatDia a) throws Exception {
        ValidacioActivitat.validar(a);

        if (a.getLimitPlaces() < 0)
            throw new Exception("El límit de places no pot ser negatiu");

        if (a.getPreu() < 0)
            throw new Exception("El preu no pot ser negatiu");

        if (a.getData() == null)
            throw new Exception("La data de l'activitat no és vàlida");
    }
}