package programa;

import java.util.Scanner;

import Data.Activitat;
import Data.ActivitatDia;
import Data.Data;
import Data.Inscripcio;
import Data.LlistaActivitats;
import Data.Llistainscripcions;
import Data.LlistaUsuaris;
import Data.Usuari;
import java.io.IOException;
import excepcio.InscripcioDuplicadaException;
import excepcio.ValoracioForaDeRangException;
import excepcio.LlistaEsperaPlenaException;
import excepcio.ValoracioNoPermesaException;
/**
 * Programa principal de consola del sistema Benestar URV.
 */
public class ProgramaBenestarURV {

    // Llistes globals (com a la part de la Paula)
    private static LlistaUsuaris llistaUsuaris = new LlistaUsuaris();
    private static Llistainscripcions llistaInscripcions = new Llistainscripcions(100);
    private static LlistaActivitats llistaActivitats = new LlistaActivitats(100);
    private static final String FITXER_INSCRIPCIONS = "fitxers/inscripcions.ser";


    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        

        Data avui = new Data(1, 9, 2025);
        // Carregar inscripcions (fitxer serialitzat)
     try {
     llistaInscripcions = GestorInscripcionsFitxer.carregar(FITXER_INSCRIPCIONS, 100);
     System.out.println("Inscripcions carregades de " + FITXER_INSCRIPCIONS);
     } catch (IOException | ClassNotFoundException e) {
     System.out.println("No s'han pogut carregar les inscripcions. Es crea una llista buida.");
     llistaInscripcions = new Llistainscripcions(100);
   }
   
   System.out.println("\n--- Carregant dades dels fitxers ---");
   Persistencia.carregarActivitats(llistaActivitats);
   System.out.println("Activitats carregades: " + llistaActivitats.getSize());
   
   Persistencia.carregarUsuaris(llistaUsuaris);
   System.out.println("Usuaris carregats: " + llistaUsuaris.getMida());
   System.out.println("-----------------------------------\n");

        int opcio = -1;
        while (opcio != 22 && opcio != 0) {
            menu(avui);

            System.out.print("Opció: ");
            String txt = entrada.nextLine().trim();
            if (txt.isEmpty()) continue;
            opcio = Integer.parseInt(txt);

            switch (opcio) {

                case 1: // Canviar data actual
                    avui = llegirData(entrada);
                    System.out.println("Data actual canviada a: " + avui);
                    break;

                case 2: // Mostrar dades de les llistes (USUARIS o ACTIVITATS) + filtres
                    System.out.println("De quina llista vols la informació? (1: Usuaris, 2: Activitats)");
                    int llistaTriada = Integer.parseInt(entrada.nextLine().trim());

                    if (llistaTriada == 1) {
                        System.out.println("Filtre: 0-Tots, 1-PDI, 2-PTGAS, 3-Estudiants");
                        int filtre = Integer.parseInt(entrada.nextLine().trim());

                        String nomClasse = "";
                        if (filtre == 1) nomClasse = "UsuariPDI";
                        else if (filtre == 2) nomClasse = "UsuariPTGAS";
                        else if (filtre == 3) nomClasse = "UsuariEstudiant";

                        System.out.println("--- LLISTA D'USUARIS ---");
                        for (int i = 0; i < llistaUsuaris.getMida(); i++) {
                            Usuari u = llistaUsuaris.getUsuari(i);
                            if (filtre == 0 || u.getClass().getSimpleName().equals(nomClasse)) {
                                System.out.println(u.toString());
                            }
                        }

                    } else if (llistaTriada == 2) {
                        System.out.println("Filtre: 0-Totes, 1-Un dia, 2-Periòdiques, 3-Online");
                        int filtreAct = Integer.parseInt(entrada.nextLine().trim());

                        String nomClasseAct = "";
                        if (filtreAct == 1) nomClasseAct = "ActivitatDia";
                        else if (filtreAct == 2) nomClasseAct = "ActivitatPeriodica";
                        else if (filtreAct == 3) nomClasseAct = "ActivitatOnline";

                        System.out.println("--- LLISTA D'ACTIVITATS ---");
                        for (int i = 0; i < llistaActivitats.getSize(); i++) {
                            Activitat a = llistaActivitats.get(i);
                            if (filtreAct == 0 || a.getClass().getSimpleName().equals(nomClasseAct)) {
                                System.out.println(a.toString());
                            }
                        }
                    } else {
                        System.out.println("Opció de llista no vàlida.");
                    }
                    break;

                case 3: { // Activitats en període d'inscripció
                    LlistaActivitats res = llistaActivitats.activitatsEnPeriodeInscripcio(avui);
                    System.out.println("Activitats en període d'inscripció a " + avui + ":");
                    mostrarActivitats(res);
                    break;
                }

                case 4: { // Activitats amb sessió avui
                    LlistaActivitats res = llistaActivitats.activitatsAmbSessioElDia(avui);
                    System.out.println("Activitats amb sessió a " + avui + ":");
                    mostrarActivitats(res);
                    break;
                }

                case 5: { // Activitats actives avui
                    LlistaActivitats res = llistaActivitats.activitatsActivesElDia(avui);
                    System.out.println("Activitats actives a " + avui + ":");
                    mostrarActivitats(res);
                    break;
                }

                case 7: { // Detall activitat per nom
                    System.out.print("Introdueix el nom de l'activitat: ");
                    String nom = entrada.nextLine().trim();

                    Activitat a = llistaActivitats.cercar(nom);
                    if (a == null) {
                        System.out.println("No s'ha trobat cap activitat amb aquest nom.");
                    } else {
                        mostrarDetallActivitat(a);
                    }
                    break;
                }

                case 8: { // Detall usuari per alias
                    System.out.print("Introdueix l'alias (nom) de l'usuari a consultar: ");
                    String aliasCerca = entrada.nextLine().trim();

                    Usuari usuariTrobat = llistaUsuaris.cercar(aliasCerca);

                    if (usuariTrobat != null) {
                        System.out.println("Informació detallada de l'usuari:");
                        System.out.println(usuariTrobat.toString());
                    } else {
                        System.out.println("Error: No s'ha trobat cap usuari amb l'alias '" + aliasCerca + "'.");
                    }
                    break;
                }

                case 9: { // Activitats d'un usuari
                    System.out.print("Introdueix l'alias de l'usuari per veure les seves activitats: ");
                    String alias = entrada.nextLine().trim();

                    Usuari usuariExisteix = llistaUsuaris.cercar(alias);

                    if (usuariExisteix != null) {
                        System.out.println("Activitats de l'usuari " + alias + ":");
                        boolean teActivitats = false;

                        for (int i = 0; i < llistaInscripcions.getMida(); i++) {
                            Inscripcio ins = llistaInscripcions.getInscripcio(i);

                            if (ins.getAliasUsuari().equalsIgnoreCase(alias)) {
                                System.out.println("- " + ins.getNomActivitat());
                                teActivitats = true;
                            }
                        }

                        if (!teActivitats) {
                            System.out.println("Aquest usuari no està inscrit a cap activitat hores d'ara.");
                        }
                    } else {
                        System.out.println("Error: No existeix cap usuari amb l'alias '" + alias + "'.");
                    }
                    break;

                }
                case 10: { // Inscriure’s a una activitat
                Inscriure(avui, entrada);
                break;
            }
            case 11: { // Mostrar usuaris apuntats i llista d’espera
                LlistatActivitat(entrada);
                break;
            }
            case 12: { // Eliminar usuari d’una activitat i promocionar llista d’espera
                EliminarInscripcio(entrada);
                break;
            }
            case 16: { // Valorar activitat (0..10) si ha acabat i ha assistit
            Valorar(avui, entrada);
             break;
            }
            case 17: { // Resum valoracions activitats acabades
            ResumActivitatsAcabades(avui);
             break;
            }
            case 18: { // Resum valoracions d’un usuari
            ResumUsuari(entrada);
            break;
            }
            case 19: { // Mitja valoracions per col·lectiu
            MitjaPerCollectiu();
            break;
           }
           case 20: {
            UsuariMesActiu(entrada);
            break;
         }


                case 22:
                case 0:
                    System.out.print("Vols guardar les inscripcions a fitxer? (s/n): ");
                    String resp = entrada.nextLine().trim();
                    if (resp.equalsIgnoreCase("s") || resp.equalsIgnoreCase("si") || resp.equalsIgnoreCase("sí")) {
                         try {
                            GestorInscripcionsFitxer.guardar(FITXER_INSCRIPCIONS, llistaInscripcions);
                            System.out.println("Inscripcions guardades a " + FITXER_INSCRIPCIONS);
                        } catch (IOException e) {
                            System.out.println("Error guardant les inscripcions: " + e.getMessage());
                        }
                    }
                    System.out.println("Fins aviat!");
                     break;



                default:
                    System.out.println("Opció no vàlida.");
                    break;
            }
        }

        entrada.close();
    }

    private static void menu(Data avui) {
        System.out.println("\n--- MENU PROGRAMA BENESTAR ---");
        System.out.println("Data actual: " + avui);
        System.out.println("1. Canviar data actual");
        System.out.println("2. Mostrar llistes (usuaris/activitats) amb filtre");
        System.out.println("3. Activitats en període d'inscripció");
        System.out.println("4. Activitats amb sessió avui");
        System.out.println("5. Activitats actives avui");
        System.out.println("7. Detall activitat per nom");
        System.out.println("8. Detall usuari per alias");
        System.out.println("9. Activitats d'un usuari");
        System.out.println("10. Inscriure's a una activitat");
        System.out.println("11. Mostrar usuaris d'una activitat (i llista d'espera)");
        System.out.println("12. Eliminar un usuari d'una activitat (promociona llista d'espera)");
        System.out.println("16. Valorar una activitat (0..10)");
        System.out.println("17. Resum de valoracions d'activitats acabades");
        System.out.println("18. Resum de valoracions d'un usuari");
        System.out.println("19. Mitja de valoracions per col·lectiu");
        System.out.println("20. Usuari més actiu d'un col·lectiu");
        System.out.println("22. Sortir");
    }

    private static Data llegirData(Scanner entrada) {
        System.out.print("Introdueix una data (dd/mm/aaaa): ");
        String txt = entrada.nextLine().trim();

        String[] parts = txt.split("/");
        int d = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        int a = Integer.parseInt(parts[2]);

        return new Data(d, m, a);
    }

    private static void mostrarActivitats(LlistaActivitats llista) {
        if (llista == null || llista.getSize() == 0) {
            System.out.println("(cap activitat)");
            return;
        }

        for (int i = 0; i < llista.getSize(); i++) {
            Activitat a = llista.get(i);
            System.out.println("- " + a.getNom());
        }
    }

    private static void mostrarDetallActivitat(Activitat activitat) {
        System.out.println("---- Detall activitat ----");
        System.out.println("Nom: " + activitat.getNom());
        System.out.println("Inscripció: " + activitat.getDataInscripcioInicial()
                + " - " + activitat.getDataInscripcioFinal());
        System.out.println("Inici activitat: " + activitat.getDataIniciActivitat());
        System.out.println("Fi activitat: " + activitat.getDataFiActivitat());

        if (activitat instanceof ActivitatDia) {
            ActivitatDia actdia = (ActivitatDia) activitat;
            System.out.println("Tipus: ActivitatDia");
            System.out.println("Data: " + actdia.getData());
            System.out.println("Ciutat: " + actdia.getCiutat());
            System.out.println("Places: " + actdia.getLimitPlaces());
            System.out.println("Preu: " + actdia.getPreu());
        }

    }

    // ---------- Helpers de Data ----------
private static int compareData(Data a, Data b) {
    if (a.getAny() != b.getAny()) return Integer.compare(a.getAny(), b.getAny());
    if (a.getMes() != b.getMes()) return Integer.compare(a.getMes(), b.getMes());
    return Integer.compare(a.getDia(), b.getDia());
}

private static boolean entreInclusiu(Data x, Data ini, Data fi) {
    return compareData(x, ini) >= 0 && compareData(x, fi) <= 0;
}

// ---------- Comptadors d'inscripcions ----------
private static int comptarParticipants(String nomActivitat) {
    int c = 0;
    for (int i = 0; i < llistaInscripcions.getMida(); i++) {
        Inscripcio ins = llistaInscripcions.getInscripcio(i);
        if (ins.getNomActivitat().equalsIgnoreCase(nomActivitat) && !ins.isEnLlistaEspera()) c++;
    }
    return c;
}

private static int comptarEnEspera(String nomActivitat) {
    int c = 0;
    for (int i = 0; i < llistaInscripcions.getMida(); i++) {
        Inscripcio ins = llistaInscripcions.getInscripcio(i);
        if (ins.getNomActivitat().equalsIgnoreCase(nomActivitat) && ins.isEnLlistaEspera()) c++;
    }
    return c;
}

private static Inscripcio buscarPrimerEnEspera(String nomActivitat) {
    Inscripcio millor = null;
    for (int i = 0; i < llistaInscripcions.getMida(); i++) {
        Inscripcio ins = llistaInscripcions.getInscripcio(i);
        if (ins.getNomActivitat().equalsIgnoreCase(nomActivitat) && ins.isEnLlistaEspera()) {
            if (millor == null || compareData(ins.getDataInscripcio(), millor.getDataInscripcio()) < 0) {
                millor = ins;
            }
        }
    }
    return millor;
}

// ---------- Places màximes ----------
private static int obtenirLimitPlaces(Activitat act) {
    // Online: no té límit
    if (act.getClass().getSimpleName().equals("ActivitatOnline")) return Integer.MAX_VALUE;

    // 
    try {
        java.lang.reflect.Method m = act.getClass().getMethod("getLimitPlaces");
        Object res = m.invoke(act);
        if (res instanceof Integer) return (Integer) res;
    } catch (Exception e) {
        // 
    }
    return Integer.MAX_VALUE;
}

// ---------- Col·lectiu usuari ----------
private static String collectiuDe(Usuari u) {
    String cn = u.getClass().getSimpleName();
    if (cn.equalsIgnoreCase("UsuariPDI")) return "PDI";
    if (cn.equalsIgnoreCase("UsuariPTGAS")) return "PTGAS";
    if (cn.equalsIgnoreCase("UsuariEstudiant")) return "Estudiants";
    return "";
}

private static boolean activitatOfereixA(Activitat act, String col) {
    String[] cols = act.getColectiusOferits();

    for (int i = 0; i < cols.length; i++) {
        if (cols[i].equalsIgnoreCase(col)) return true;
    }
    return false;
}

// ---------- OPCIÓ 10 ----------
private static void Inscriure(Data avui, Scanner entrada) {
    System.out.print("Nom activitat: ");
    String nomAct = entrada.nextLine().trim();
    Activitat act = llistaActivitats.cercar(nomAct);

    if (act == null) {
        System.out.println("Error: activitat no trobada.");
        return;
    }

    if (!entreInclusiu(avui, act.getDataInscripcioInicial(), act.getDataInscripcioFinal())) {
        System.out.println("Error: fora del termini d'inscripció.");
        return;
    }

    System.out.print("Alias usuari: ");
    String alias = entrada.nextLine().trim();
    Usuari u = llistaUsuaris.cercar(alias);

    if (u == null) {
        System.out.println("Usuari no existeix.");
        return;
    }

    String col = collectiuDe(u);
    if (!activitatOfereixA(act, col)) {
        System.out.println("Error: aquesta activitat no s'ofereix al teu col·lectiu.");
        return;
    }

    boolean enEspera = false;
    int limit = obtenirLimitPlaces(act);

    try {
        if (limit != Integer.MAX_VALUE) {
            int participants = comptarParticipants(nomAct);
            if (participants >= limit) {
                int espera = comptarEnEspera(nomAct);
                if (espera >= 10) {
                    throw new LlistaEsperaPlenaException(
                        "Llista d'espera plena (10). No es pot inscriure."
                    );
                }
                enEspera = true;
            }
        }

        Inscripcio nova = new Inscripcio(nomAct, alias, avui, enEspera);
        llistaInscripcions.afegirInscripcio(nova); // aquí salta InscripcioDuplicadaException si toca

        System.out.println(enEspera ? "Inscripció feta a LLISTA D'ESPERA." : "Inscripció CONFIRMADA.");

    } catch (LlistaEsperaPlenaException e) {
        System.out.println("Error: " + e.getMessage());
    } catch (InscripcioDuplicadaException e) {
        System.out.println("Error: " + e.getMessage());
    } catch (RuntimeException e) {
        System.out.println("Error inesperat: " + e.getMessage());
    }
}


// ---------- OPCIÓ 11 ----------
private static void LlistatActivitat(Scanner entrada) {
    System.out.print("Nom activitat: ");
    String nomAct = entrada.nextLine().trim();

    Inscripcio[] ins = llistaInscripcions.obtenirInscripcionsActivitat(nomAct);
    if (ins.length == 0) {
        System.out.println("No hi ha inscripcions per aquesta activitat.");
        return;
    }

    System.out.println("Participants:");
    boolean capParticipant = true;
    for (int i = 0; i < ins.length; i++) {
        if (!ins[i].isEnLlistaEspera()) {
            System.out.println("- " + ins[i].getAliasUsuari());
            capParticipant = false;
        }
    }
    if (capParticipant) System.out.println("(cap)");

    System.out.println("Llista d'espera:");
    boolean capEspera = true;
    for (int i = 0; i < ins.length; i++) {
        if (ins[i].isEnLlistaEspera()) {
            System.out.println("- " + ins[i].getAliasUsuari());
            capEspera = false;
        }
    }
    if (capEspera) System.out.println("(cap)");
}

// ---------- OPCIÓ 12 ----------
private static void EliminarInscripcio(Scanner entrada) {
    System.out.print("Nom activitat: ");
    String nomAct = entrada.nextLine().trim();

    System.out.print("Alias usuari: ");
    String alias = entrada.nextLine().trim();

    Inscripcio ins = llistaInscripcions.cercarInscripcio(alias, nomAct);
    if (ins == null) {
        System.out.println("Error: no existeix aquesta inscripció.");
        return;
    }

    boolean eraParticipant = !ins.isEnLlistaEspera();

    boolean ok = llistaInscripcions.eliminarInscripcio(alias, nomAct);
    if (!ok) {
        System.out.println("Error eliminant la inscripció.");
        return;
    }

    System.out.println("Inscripció eliminada.");

    // Si era participant, promociona el primer de la llista d'espera 
    if (eraParticipant) {
        Inscripcio promo = buscarPrimerEnEspera(nomAct);
        if (promo != null) {
            promo.setEnLlistaEspera(false);
            System.out.println("Promocionat des de llista d'espera: " + promo.getAliasUsuari());
        }
    }
}

// ---------- OPCIÓ 16 ----------
private static void Valorar(Data avui, Scanner entrada) {
    try {
        System.out.print("Nom activitat: ");
        String nomAct = entrada.nextLine().trim();
        Activitat act = llistaActivitats.cercar(nomAct);

        if (act == null) {
            throw new ValoracioNoPermesaException("Activitat no trobada.");
        }

        if (compareData(avui, act.getDataFiActivitat()) <= 0) {
            throw new ValoracioNoPermesaException("L'activitat encara no ha acabat.");
        }

        System.out.print("Alias usuari: ");
        String alias = entrada.nextLine().trim();

        Inscripcio ins = llistaInscripcions.cercarInscripcio(alias, nomAct);
        if (ins == null) {
            throw new ValoracioNoPermesaException("L'usuari no està inscrit a l'activitat.");
        }
        if (ins.isEnLlistaEspera()) {
            throw new ValoracioNoPermesaException("Els usuaris en llista d'espera no poden valorar.");
        }

        System.out.print("Ha assistit a l'activitat? (s/n): ");
        String ha = entrada.nextLine().trim();
        boolean assistit = ha.equalsIgnoreCase("s") || ha.equalsIgnoreCase("si") || ha.equalsIgnoreCase("sí");
        ins.setHaAssistit(assistit);

        if (!ins.isHaAssistit()) {
            throw new ValoracioNoPermesaException("Per valorar, l'usuari ha d'haver assistit.");
        }

        System.out.print("Nota (0..10): ");
        int nota = Integer.parseInt(entrada.nextLine().trim());

        ins.setValoracio(nota); // pot llançar ValoracioForaDeRangException
        System.out.println("Valoració guardada.");

    } catch (ValoracioNoPermesaException e) {
        System.out.println("Error: " + e.getMessage());
    } catch (ValoracioForaDeRangException e) {
        System.out.println("Error: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.out.println("Error: la nota ha de ser un número.");
    }
}


// ---------- OPCIÓ 17 ----------
private static void ResumActivitatsAcabades(Data avui) {
    System.out.println("Resum valoracions (activitats acabades):");

    for (int i = 0; i < llistaActivitats.getSize(); i++) {
        Activitat act = llistaActivitats.get(i);

        if (compareData(avui, act.getDataFiActivitat()) > 0) { 
            double suma = 0;
            int n = 0;

            for (int j = 0; j < llistaInscripcions.getMida(); j++) {
                Inscripcio ins = llistaInscripcions.getInscripcio(j);
                if (ins.getNomActivitat().equalsIgnoreCase(act.getNom()) && ins.teValoracio()) {
                    suma += ins.getValoracio();
                    n++;
                }
            }

            if (n == 0) {
                System.out.println("- " + act.getNom() + ": (sense valoracions)");
            } else {
                System.out.println("- " + act.getNom() + ": mitja=" + (suma / n) + " (" + n + " valoracions)");
            }
        }
    }
}

// ---------- OPCIÓ 18 ----------
private static void ResumUsuari(Scanner entrada) {
    System.out.print("Alias usuari: ");
    String alias = entrada.nextLine().trim();

    Usuari u = llistaUsuaris.cercar(alias);
    if (u == null) {
        System.out.println("Error: usuari no existeix.");
        return;
    }

    double suma = 0;
    int n = 0;

    for (int i = 0; i < llistaInscripcions.getMida(); i++) {
        Inscripcio ins = llistaInscripcions.getInscripcio(i);
        if (ins.getAliasUsuari().equalsIgnoreCase(alias) && ins.teValoracio()) {
            System.out.println("- " + ins.getNomActivitat() + ": " + ins.getValoracio());
            suma += ins.getValoracio();
            n++;
        }
    }

    if (n == 0) System.out.println("(Aquest usuari no ha valorat cap activitat)");
    else System.out.println("Mitja usuari: " + (suma / n));
}

// ---------- OPCIÓ 19 ----------
private static void MitjaPerCollectiu() {
    double sumaPDI = 0; int nPDI = 0;
    double sumaPTGAS = 0; int nPTGAS = 0;
    double sumaEST = 0; int nEST = 0;

    for (int i = 0; i < llistaInscripcions.getMida(); i++) {
        Inscripcio ins = llistaInscripcions.getInscripcio(i);
        if (!ins.teValoracio()) continue;

        Usuari u = llistaUsuaris.cercar(ins.getAliasUsuari());
        if (u == null) continue;

        String col = collectiuDe(u);
        if (col.equals("PDI")) { sumaPDI += ins.getValoracio(); nPDI++; }
        else if (col.equals("PTGAS")) { sumaPTGAS += ins.getValoracio(); nPTGAS++; }
        else if (col.equals("Estudiants")) { sumaEST += ins.getValoracio(); nEST++; }
    }

    System.out.println("Mitja valoracions per col·lectiu:");
    System.out.println("- PDI: " + (nPDI == 0 ? "(sense dades)" : (sumaPDI / nPDI)));
    System.out.println("- PTGAS: " + (nPTGAS == 0 ? "(sense dades)" : (sumaPTGAS / nPTGAS)));
    System.out.println("- Estudiants: " + (nEST == 0 ? "(sense dades)" : (sumaEST / nEST)));
}
// ---------- OPCIÓ 20 ----------
private static void UsuariMesActiu(Scanner entrada) {
    System.out.println("Tria col·lectiu: 1-PDI, 2-PTGAS, 3-Estudiants");
    String txt = entrada.nextLine().trim();

    String nomClasse = "";
    if (txt.equals("1")) nomClasse = "UsuariPDI";
    else if (txt.equals("2")) nomClasse = "UsuariPTGAS";
    else if (txt.equals("3")) nomClasse = "UsuariEstudiant";
    else {
        System.out.println("Opció no vàlida.");
        return;
    }

    String millorAlias = null;
    int max = -1;

    // Recorremos usuarios del colectivo
    for (int i = 0; i < llistaUsuaris.getMida(); i++) {
        Usuari u = llistaUsuaris.getUsuari(i);

        if (!u.getClass().getSimpleName().equals(nomClasse)) continue;

        String alias = u.getAlias();

        int comptador = 0;
        for (int j = 0; j < llistaInscripcions.getMida(); j++) {
            Inscripcio ins = llistaInscripcions.getInscripcio(j);
            if (ins.getAliasUsuari().equalsIgnoreCase(alias)) {
                comptador++;
            }
        }

        if (comptador > max) {
            max = comptador;
            millorAlias = alias;
        }
    }

    if (millorAlias == null) {
        System.out.println("No hi ha usuaris d'aquest col·lectiu.");
        return;
    }
    if (max == 0) {
    System.out.println("Cap usuari d'aquest col·lectiu està inscrit a cap activitat.");
    return; 
}

    System.out.println("Usuari més actiu: " + millorAlias + " (" + max + " inscripcions)");
}


}
