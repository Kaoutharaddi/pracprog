package test;

import Data.LlistaActivitats;
/**
 * Classe encarregada de validar una instància de LlistaActivitats.
 * Comprova que la llista no sigui nul·la i que contingui almenys una activitat abans de poder ser utilitzada dins el sistema.
 * @author Judit Hidalgo
 */

public class ValidacioLlistaActivitats {

    /**
     * Valida que la llista d'activitats sigui correcta.
     * 
     * @param llista Llista d'activitats que es vol validar.
     * @throws Exception si la llista és nul·la o buida.
     */

    public static void validar(LlistaActivitats llista) throws Exception {
        if (llista == null)
            throw new Exception("La llista d'activitats és nul·la");

        if (llista.getSize() == 0)
            throw new Exception("La llista d'activitats està buida");
    }
}