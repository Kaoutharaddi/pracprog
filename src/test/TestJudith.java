package test;

// import Data.Activitat;
import Data.ActivitatOnline;
import Data.ActivitatPeriodica;
import Data.Data;
import Data.LlistaActivitatsOnline; 

/**
 * Programa principal per executar el joc de proves unitari.
 * Es prova: 
 * - la creació correcta d'activitats periòdiques i online
 * - el control d'errors (excepcions) amb dades invàlides
 * - el càlcul automàtic de dates finals i estats (activa/no activa)
 * * @author Judith Cunillera
 */

public class TestJudith {
    public static void main(String[] args) {
        System.out.println("------------------------------------------");
        System.out.println("         JOC DE PROVES JUDITH");
        System.out.println("------------------------------------------\n");
    
    
    Data avui = new Data(3, 1, 2026);
    String[] usuari = {"PDI", "PTGAS", "Estudiants"};
    String[] nomesEstudiants = {"Estudiants"};
    String[] nomesProfesorat = {"PDI", "PTGAS"};

    ActivitatPeriodica actEstudiants = null; 
    ActivitatOnline actOnlineEstudiants = null; 
    ActivitatOnline actOnlineProfes = null;

    System.out.println("        =======================================");
    System.out.println("        ==== PROVES ACTIVITATS PERIODIQUES ====");
    System.out.println("        =======================================");
    System.out.println("\n");
/**
 * PROVA 1 | Crear activitat periodica per estudiants
 */
System.out.println("--- PROVA 1 | Crear activitat periodica ESTUDIANTS ---");
try {
    actEstudiants = new ActivitatPeriodica(
    "Mindfulness per a exàmens", // Nom classe
    nomesEstudiants,     // Usuaris que poden accedir 
    avui,       // Data inici inscripció
    avui.plusDays(7), // Data final inscripció
    "Dilluns",  // dia de la setmana
    "15:00 - 16:00h",     // horari
    avui.plusDays(8), // Data quan começa (8 dies després de inici)
    4,  // durada (en setmanes)
    20,     // preu
    "CRAI -Campus Sascelades-",     // lloc
    "Tarragona",    // ciutat
    40);   //   places

    // visualització
    System.out.println("\n  --- FITXA DE L'ACTIVITAT ---");
    System.out.println(actEstudiants.toString());
    
    System.out.println("\n");       
    System.out.println("Èxit! Has creat la activitat: " + actEstudiants.getNom());
    System.out.println("    Preu per als inscrits: " + actEstudiants.getPreu() + " euros");
} catch (Exception error) {
    System.err.println(" > ERROR: No s'ha pogut crear l'activitat");
    System.err.println(" > Motiu: " + error.getMessage());
}
    System.out.println("-------------------------------------------------------------------");
    System.out.println("\n"); 

/**
 * PROVA 2 | Crear activitat periodica per estudiants ERRONEA (preu negatiu)
 */
System.out.println("--- PROVA 2 | Crear activitat periodica per estudiants ERRONEA (preu negatiu) ---");
try {
    actEstudiants = new ActivitatPeriodica(
    "Repas asignatura Programació", // Nom classe
    nomesEstudiants,     // Usuaris que poden accedir 
    avui,       // Data inici inscripció
    avui.plusDays(4), // Data final inscripció
    "Dimarts",  // dia de la setmana
    "10:00 - 12:00h",     // horari
    avui.plusDays(5), // Data quan começa (5 dies després de inici)
    4,  // durada (en setmanes)
    -20,     // preu
    "L11 Lab 208 -Campus Sascelades-",     // lloc
    "Tarragona",    // ciutat
    50);   //   places

        // visualització
    System.out.println("\n  --- FITXA DE L'ACTIVITAT ---");
    System.out.println(actEstudiants.toString());
        
    System.out.println("\n");       
    System.out.println("Èxit! Has creat la activitat: " + actEstudiants.getNom());
    System.out.println("    Preu per als inscrits: " + actEstudiants.getPreu() + " euros");
} catch (Exception error) {
    System.err.println(" > ERROR: No s'ha pogut crear l'activitat");
    System.err.println(" > Motiu: " + error.getMessage());
}
    System.out.println("-------------------------------------------------------------------");
    System.out.println("\n"); 

/**
 * PROVA 3 | Crear activitat periodica per profesorat
 */
System.out.println("--- PROVA 3 | Crear activitat periodica PROFESORAT ---");
try {
    ActivitatPeriodica actProfes = new ActivitatPeriodica(
    "Tècniques de veu i relaxació", // Nom classe
    nomesProfesorat,     // Usuaris que poden accedir 
    avui,       // Data inici inscripció
    avui.plusDays(2), // Data final inscripció
    "Divendres",  // dia de la setmana
    "20:00 - 21:00h",     // horari
    avui.plusDays(3), // Data quan começa (3 dies després de inici)
    2,  // durada (en setmanes)
    5,     // preu
    "Aula 111 -Campus Sascelades-",     // lloc
    "Tarragona",    // ciutat
    20);   //   places

            // visualització
    System.out.println("\n  --- FITXA DE L'ACTIVITAT ---");
        System.out.println(actProfes.toString());
        
    
    System.out.println("\n");       
    System.out.println("Èxit! Has creat la activitat: " + actProfes.getNom());
    System.out.println("    Preu per als inscrits: " + actProfes.getPreu() + " euros");
} catch (Exception error) {
    System.err.println(" > ERROR: No s'ha pogut crear l'activitat");
    System.err.println(" > Motiu: " + error.getMessage());
}
    System.out.println("-------------------------------------------------------------------");
    System.out.println("\n"); 

/**
 * PROVA 4 | Crear activitat periodica per profesorat ERRONI (nom buit)
 */
System.out.println("--- PROVA 4 | Crear activitat periodica PROFESORAT ---");
try {
    ActivitatPeriodica actProfes = new ActivitatPeriodica(
    "", // Nom classe
    nomesProfesorat,     // Usuaris que poden accedir 
    avui,       // Data inici inscripció
    avui.plusDays(1), // Data final inscripció
    "Dijous",  // dia de la setmana
    "20:00 - 21:00h",     // horari
    avui.plusDays(2), // Data quan começa (2 dies després de inici)
    3,  // durada (en setmanes)
    2,     // preu
    "Aula 105 -Campus Sascelades-",     // lloc
    "Tarragona",    // ciutat
    5);   //   places

            // visualització
    System.out.println("\n  --- FITXA DE L'ACTIVITAT ---");
        System.out.println(actProfes.toString());
        
    
    System.out.println("\n");       
    System.out.println("Èxit! Has creat la activitat: " + actProfes.getNom());
    System.out.println("    Preu per als inscrits: " + actProfes.getPreu() + " euros");
} catch (Exception error) {
    System.err.println(" > ERROR: No s'ha pogut crear l'activitat");
    System.err.println(" > Motiu: " + error.getMessage());
}

    System.out.println("-------------------------------------------------------------------");
    System.out.println("\n"); 


    System.out.println("        ==================================");
    System.out.println("        ==== PROVES ACTIVITATS ONLINE ====");
    System.out.println("        ==================================");
    System.out.println("\n");

/**
 * PROVA 5 | Crear activitat ONLINE correcta (Estudiants)
 */
System.out.println("--- PROVA 5 | Crear activitat ONLINE correcta (Estudiants) ---");
try {
    actOnlineEstudiants = new ActivitatOnline(
    "Curs d'Hàbits d'Estudi Digital", // Nom classe
    nomesEstudiants,     // Usuaris que poden accedir 
    avui,       // Data inici inscripció
    avui.plusDays(10), // Data final inscripció
    avui.plusDays(11), // Data quan começa (11 dies després de inici)
    30, // Dies per veure-ho
    "https://campusvirtual.urv.cat/estudiants/habitsestudidigital");   // Enllaç

        // visualització
    System.out.println("\n  --- FITXA DE L'ACTIVITAT ---");
    System.out.println(actOnlineEstudiants.toString());
        
    
    System.out.println("\n");       
    System.out.println("Èxit! Has creat la activitat online: " + actOnlineEstudiants.getNom());

} catch (Exception error) {
    System.err.println(" > ERROR: No s'ha pogut crear l'activitat");
    System.err.println(" > Motiu: " + error.getMessage());
}
    System.out.println("-------------------------------------------------------------------");
    System.out.println("\n");  

/**
 * PROVA 6 | Crear activitat ONLINE errònia (Durada negativa)
 */
System.out.println("--- PROVA 6 | Crear activitat ONLINE errònia (Durada negativa) ---");
try {
    actOnlineEstudiants = new ActivitatOnline(
        "Curs d'àngles", 
        nomesEstudiants, 
        avui, 
        avui.plusDays(5),
        avui.plusDays(6), 
        -10, 
        "https://campusvirtual.urv.cat/estudiants/cursangles");

        // visualització
    System.out.println("\n  --- FITXA DE L'ACTIVITAT ---");
    System.out.println(actOnlineEstudiants.toString());
        
    
    System.out.println("\n");       
    System.out.println("Èxit! Has creat la activitat online: " + actOnlineEstudiants.getNom());

} catch (Exception error) {
    System.err.println(" > ERROR: No s'ha pogut crear l'activitat");
    System.err.println(" > Motiu: " + error.getMessage());
}
    System.out.println("-------------------------------------------------------------------");
    System.out.println("\n");  

/**
 * PROVA 7 | Crear activitat ONLINE per profesorat
 */
    System.out.println("PROVA 7 | Crear activitat ONLINE per profesorat");  
try {
    actOnlineProfes = new ActivitatOnline(
        "Prevenció de Riscos Laborals (Curs Anual)", 
        nomesProfesorat, 
        avui, 
        avui.plusDays(30),
        avui.plusDays(1), 
        365, 
        "https://campusvirtual.urv.cat/profes/prevencioriscoslaborals");

        // visualització
    System.out.println("\n  --- FITXA DE L'ACTIVITAT ---");
    System.out.println(actOnlineProfes.toString());
        
    
    System.out.println("\n");       
    System.out.println("Èxit! Has creat la activitat online: " + actOnlineProfes.getNom());

} catch (Exception error) {
    System.err.println(" > ERROR: No s'ha pogut crear l'activitat");
    System.err.println(" > Motiu: " + error.getMessage());
}
    System.out.println("-------------------------------------------------------------------");
    System.out.println("\n");  

/**
 * PROVA 8 | Crear activitat ONLINE per profesorat ERRONI (no ellaç)
 */
    System.out.println("PROVA 8 | Crear activitat ONLINE per profesorat ERRONI (no ellaç)");  
try {
    actOnlineProfes = new ActivitatOnline(
        "Reunió global profesorat", 
        nomesProfesorat, 
        avui, 
        avui.plusDays(5),
        avui.plusDays(6), 
        10, 
        "");

        // visualització
    System.out.println("\n  --- FITXA DE L'ACTIVITAT ---");
    System.out.println(actOnlineProfes.toString());
        
    
    System.out.println("\n");       
    System.out.println("Èxit! Has creat la activitat online: " + actOnlineProfes.getNom());

} catch (Exception error) {
    System.err.println(" > ERROR: No s'ha pogut crear l'activitat");
    System.err.println(" > Motiu: " + error.getMessage());
}
    System.out.println("-------------------------------------------------------------------");
    System.out.println("\n");  


System.out.println("===================================");
System.out.println("==== PROVA ELIMINAR ACTIVITATS ====");
System.out.println("===================================");  
// Creem llista i afegim activitats
LlistaActivitatsOnline llista = new LlistaActivitatsOnline(10, "Q1", "2025-2026");

if (actOnlineEstudiants != null) {
    llista.afegirActivitat(actOnlineEstudiants);
}
if (actOnlineProfes != null) {
    llista.afegirActivitat(actOnlineProfes);
}

System.out.println("Activitats actuals: " + llista.getNElem());

// intentem eliminar
String nomEliminar = "Curs d'Hàbits d'Estudi Digital";   // PROVA 5
boolean eliminat = llista.eliminarActivitat(nomEliminar);

if(eliminat) {
    System.out.println("¡Èxit! S'ha eliminat l'activitat" + nomEliminar);
} else {
    System.out.println("¡Error! No s'ha trobat l'activitat" + nomEliminar);
}
// Comprovem comptador
System.out.println("Activitats restants: " + llista.getNElem());

System.out.println("\n");  
System.out.println("=============================");
System.out.println("==== PROVA CERCA PER NOM ====");
System.out.println("=============================");

// PROVA 5
if (llista.cercarActivitat("Curs d'Hàbits d'Estudi Digital") == null && actOnlineEstudiants != null) {
             llista.afegirActivitat(actOnlineEstudiants);
        }
// CAS 1
System.out.println("\n- Buscant: Prevenció de Riscos");
        ActivitatOnline resultat = llista.cercarActivitat("Prevenció de Riscos");
        if (resultat != null) {
            System.out.println("TROBAT: " + resultat.getNom());
            System.out.println(resultat);
        } else {
            System.out.println("NO TROBAT");
        }
// CAS 2
System.out.println("\n- Buscant: curs d'hàbits d'estudi digital");
        resultat = llista.cercarActivitat("curs d'hàbits d'estudi digital");
        
        if (resultat != null) {
            System.out.println("TROBAT: " + resultat.getNom());
        } else {
            System.out.println("NO TROBAT");
        }
// CAS 3
System.out.println("\n- Buscant: Classe de Zumba");
        resultat = llista.cercarActivitat("Classe de Zumba");
        
        if (resultat != null) {
            System.out.println("TROBAT: " + resultat.getNom());
        } else {
            System.out.println("NO TROBAT");
        }
System.out.println("\n");
System.out.println("====================================");
System.out.println("==== PROVA CÀLCUL DATES I ESTAT ====");
System.out.println("====================================");

if (actEstudiants != null) { 
    System.out.println("Activitat: " + actEstudiants.getNom());
    System.out.println("Inici classes: " + actEstudiants.getDataIniClass());
    System.out.println("Durada: " + actEstudiants.getNumSetmanes() + " setmanes");
// provar calcula data final
System.out.println("    > Data Final calculada: " + ((ActivitatPeriodica) actEstudiants).getDataFiClass());

// provar si activa avui
boolean activaAvui = actEstudiants.esActiva(avui);
System.out.println("    > Està activa avui? " + (activaAvui ? "SÍ" : "NO"));   // Esperat no
// provar data futura si activa
Data dataFutura = actEstudiants.getDataIniClass();
// Sumar 10 dies manualmente
int diaFutur = dataFutura.getDia() + 10;
int mesFutur = dataFutura.getMes();
int anyFutur = dataFutura.getAny();
while (diaFutur > Data.diesDelMes(mesFutur, anyFutur)) {
    diaFutur -= Data.diesDelMes(mesFutur, anyFutur);
    mesFutur++;
    if (mesFutur > 12) {
        mesFutur = 1;
        anyFutur++;
    }
}
dataFutura = new Data(diaFutur, mesFutur, anyFutur);
boolean activaFutur = actEstudiants.esActiva(dataFutura);
System.out.println("    > Estarà activa el " + dataFutura + "? " + (activaFutur ? "SÍ" : "NO")); // Esperat si
        }

System.out.println("\n");
System.out.println("=================================");
System.out.println("==== PROVA CONTROL DUPLICATS ====");
System.out.println("=================================");

System.out.println("Intentem afegir 'Curs d'Hàbits'");

// afegit abans, error per consola
if (actOnlineEstudiants != null) {
    llista.afegirActivitat(actOnlineEstudiants); 
}
System.out.println("Activitats després de l'intent: " + llista.getNElem() + " (Ha de ser igual)");


}
}
