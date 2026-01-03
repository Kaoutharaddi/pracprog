package test;

import Data.Activitat;

/**
 * Classe encarregada de validar instàncies de la classe Activitat.
 * Comprova que les dades essencials de l'activitat siguin correctes.
 * @author Judit Hidalgo
 */

public class ValidacioActivitat {

    /**
     * Valida que una activitat sigui correcta.
     * @param a Activitat que es vol validar.
     * @throws Exception si alguna dada és invàlida.
     */

    public static void validar(Activitat a) throws Exception {
        if (a == null)
            throw new Exception("Activitat nul·la");

        if (a.getNom() == null || a.getNom().isEmpty())
            throw new Exception("El nom de l'activitat no pot estar buit");

        if (a.getDataInscripcioInicial() == null || a.getDataInscripcioFinal() == null)
            throw new Exception("El període d'inscripció no pot ser null");

        if (a.getDataInscripcioInicial().esDespres(a.getDataInscripcioFinal()))
            throw new Exception("El període d'inscripció és incorrecte");
    }
}