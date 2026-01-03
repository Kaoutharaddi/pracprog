package test;

import Data.LlistaUsuaris;
import Data.Usuari;
import Data.UsuariPDI;
import Data.UsuariPTGAS;

/**
 * Classe per fer proves de UsuariPDI, UsuariPTGAS i LlistaUsuari
 * Usuari forma part de PDI i PTGAS
 * @author Paula Arco
 */
public class provesAplicaciop {

    public static void main(String[] args) {
    System.out.println("=== PROVES MANUALS ===");

    provarUsuariPDI();
    provarUsuariPTGAS();
    provarLlistaUsuaris();
    }

    private static void provarUsuariPDI() {
        System.out.println("\n--- Proves UsuariPDI ---");
        UsuariPDI u = new UsuariPDI("jane", "jane.doe", "DEIM", "Sescelades");

        System.out.println("Alias (esperat jane): " + u.getAlias());
        System.out.println("Correu complet (esperat jane.doe@urv.cat): " + u.getCorreuComplet());
        System.out.println("Departament (esperat DEIM): " + u.getDepartamentt());
        System.out.println("Campus (esperat Sescelades): " + u.getCampus());

        UsuariPDI u2 = new UsuariPDI("JANE", "jane.dos", "DEEEA", "Catalunya");
        System.out.println("Equals basat en alias (esperat true): " + u.equals(u2));
    }

    private static void provarUsuariPTGAS() {
        System.out.println("\n--- Proves UsuariPTGAS ---");
        UsuariPTGAS u = new UsuariPTGAS("marco", "marco.doe", "Sescelades");

        System.out.println("Alias (esperat marco): " + u.getAlias());
        System.out.println("Correu complet (esperat marco.doe@urv.cat): " + u.getCorreuComplet());
        System.out.println("Campus (esperat Sescelades): " + u.getCampus());

        UsuariPTGAS u2 = new UsuariPTGAS("MARCO", "marco.dos", "Catalunya");
        System.out.println("Equals basat en alias (esperat true): " + u.equals(u2));
    }

    private static void provarLlistaUsuaris() {
        System.out.println("\n--- Proves LlistaUsuaris ---");

        LlistaUsuaris l = new LlistaUsuaris();

        UsuariPDI u1 = new UsuariPDI("pep", "pep.doe", "DEQ", "Sescelades");
        UsuariPTGAS u2 = new UsuariPTGAS("marta", "marta.doe", "Bellisens");

        l.afegir(u1);
        l.afegir(u2);

        System.out.println("Mida (esperat 2): " + l.getMida());
        System.out.println("toString():\n" + l.toString());

        System.out.println("Cercar 'pep' (no null): " + l.cercar("pep"));
        System.out.println("Cercar 'noexisteix' (esperat null): " + l.cercar("noexisteix"));

        // Afegir
        UsuariPDI u3 = new UsuariPDI("carlos", "carlos.doe", "DEEEA", "Sescelades");

        System.out.println("Mida abans d'afegir (esperat 2): " + l.getMida());
        l.afegir(u3);
        System.out.println("Mida després d'afegir (esperat 3): " + l.getMida());

        Usuari u = l.cercar("carlos");
        System.out.println("Usuari cercat 'carlos' (no ha de ser null): " + u);
        System.out.println("Alias nou usuari (esperat carlos): " + u.getAlias());
        System.out.println("Correu nou usuari (esperat carl@urv.cat): " + u.getCorreuComplet());

        System.out.println("\nLlista després d'afegir carlos:\n" + l.toString());

        // Eliminar
        System.out.println("Eliminar 'pep' (esperat true): " + l.eliminar("pep"));
        System.out.println("Mida (esperat 2): " + l.getMida());
    }


}
