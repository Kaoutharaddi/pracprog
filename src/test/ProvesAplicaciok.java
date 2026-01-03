package test;

import Data.Data;
import Data.Inscripcio;
import Data.Llistainscripcions;
import Data.UsuariEstudiant;
import excepcio.InscripcioDuplicadaException;
import excepcio.ValoracioForaDeRangException;
/**
 * Classe per fer proves manuals de les classes de UsuariEstudiant,Inscripcio,
 * Llistainscripcions i les excepcions personalitzades.
 *
* @author Kaouthar Addi
 */

public class ProvesAplicaciok {

    public static void main(String[] args) {
        System.out.println("=== PROVES MANUALS ===");

        provarUsuariEstudiant();
        provarInscripcio();
        provarLlistaInscripcions();
    }

    private static void provarUsuariEstudiant() {
        System.out.println("\n--- Proves UsuariEstudiant ---");
        UsuariEstudiant u = new UsuariEstudiant("jdoe", "john.doe", "GEI", 2023);

        System.out.println("Alias (esperat jdoe): " + u.getAlias());
        System.out.println("Correu complet (esperat john.doe@estudiants.urv.cat): "
                           + u.getCorreuComplet());
        System.out.println("Ensenyament (esperat GEI): " + u.getEnsenyament());
        System.out.println("Any inici (esperat 2023): " + u.getAnyIniciEstudis());

        UsuariEstudiant u2 = new UsuariEstudiant("JDOE", "altres", "GESST", 2024);
        System.out.println("Equals basat en alias (esperat true): " + u.equals(u2));
    }

    private static void provarInscripcio() {
        System.out.println("\n--- Proves Inscripcio ---");
        Data avui = new Data(10, 10, 2025);
        Inscripcio ins = new Inscripcio("Ioga", "jdoe", avui, false);

        System.out.println("Nom activitat (esperat Ioga): " + ins.getNomActivitat());
        System.out.println("Alias (esperat jdoe): " + ins.getAliasUsuari());
        System.out.println("Data (esperat " + avui + "): " + ins.getDataInscripcio());
        System.out.println("En llista espera (esperat false): " + ins.isEnLlistaEspera());
        System.out.println("Ha assistit (esperat false): " + ins.isHaAssistit());
        System.out.println("Té valoració inicialment (esperat false): " + ins.teValoracio());

        try {
            ins.setValoracio(8);
            System.out.println("Valoració posada a 8, teValoracio() (esperat true): "
                               + ins.teValoracio());
            System.out.println("Valoració (esperat 8): " + ins.getValoracio());
        } catch (ValoracioForaDeRangException e) {
            System.out.println("ERROR: no hauria de fallar amb 8");
        }

        try {
            ins.setValoracio(11);
            System.out.println("ERROR: esperàvem excepció amb valoració 11");
        } catch (ValoracioForaDeRangException e) {
            System.out.println("Correcte: excepció amb valoració 11");
        }
    }

    private static void provarLlistaInscripcions() {
        System.out.println("\n--- Proves Llistainscripcions ---");
        Data avui = new Data(10, 10, 2025);
        Llistainscripcions llista = new Llistainscripcions(2);

        Inscripcio ins1 = new Inscripcio("Ioga", "jdoe", avui, false);
        Inscripcio ins2 = new Inscripcio("Running", "maria", avui, true);

        try {
            llista.afegirInscripcio(ins1);
            llista.afegirInscripcio(ins2);
        } catch (InscripcioDuplicadaException e) {
            System.out.println("ERROR: no hauria de llençar InscripcioDuplicadaException aquí");
        }

        System.out.println("Nombre inscripcions (esperat 2): " + llista.getNombreInscripcions());
        System.out.println("get(0) (esperat Ioga/jdoe): " + llista.get(0));
        System.out.println("get(1) (esperat Running/maria): " + llista.get(1));

        System.out.println("Cercar (jdoe, Ioga) (no ha de ser null): "
                           + llista.cercarInscripcio("jdoe", "Ioga"));
        System.out.println("Cercar (altres, Ioga) (esperat null): "
                           + llista.cercarInscripcio("altres", "Ioga"));

        boolean eliminada = llista.eliminarInscripcio("jdoe", "Ioga");
        System.out.println("Eliminar (jdoe, Ioga) (esperat true): " + eliminada);
        System.out.println("Nombre inscripcions després d'eliminar (esperat 1): "
                           + llista.getNombreInscripcions());

        Inscripcio[] deMaria = llista.obtenirInscripcionsUsuari("maria");
        System.out.println("Inscripcions de maria (esperat 1): " + deMaria.length);

        Inscripcio[] deIoga = llista.obtenirInscripcionsActivitat("Ioga");
        System.out.println("Inscripcions d'Ioga (esperat 0 o 1 depenent del moment): "
                           + deIoga.length);

        try {
            Inscripcio insDup = new Inscripcio("Running", "maria", avui, false);
            llista.afegirInscripcio(insDup);
            System.out.println("ERROR: esperàvem InscripcioDuplicadaException amb duplicat");
        } catch (InscripcioDuplicadaException e) {
            System.out.println("Correcte: Excepció amb duplicat");
        }
    }
}
