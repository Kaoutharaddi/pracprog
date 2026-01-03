package Data;
/**
 * Classe que gestiona la col·lecció d'activitats online mitjançant un array estàtic.
 * Es guarda: 
 * - llista d'activitats (ofertes) ordenades alfabèticament pel nom
 * - informació del context acadèmic (quadrimestre i curs)
 * - control del nombre d'elements actuals (nElem)
 * Implementa l'alta ordenada, la baixa i la cerca d'elements.
 * @author Judith Cunillera
 */

public class LlistaActivitatsOnline {
    private ActivitatOnline[] ofertes;
    private String quatri;
    private String curs;
    private int nElem; 

/**
 * CONSTRUCTOR
 */
public LlistaActivitatsOnline (int capacitat, String quatri, String curs) {
    this.ofertes = new ActivitatOnline[capacitat]; // espai per a memoria 
    this.nElem = 0;
    this.quatri = quatri;
    this.curs = curs;  
}

//---------------------------------------------------
/**
 * GETTERS
 */
    public String getQuatri() { return quatri; }
    public String getCurs() { return curs; }
    public int getNElem() { return nElem; }

//---------------------------------------------------

/**
 * METODE (recuperar activitat concreta per posició)
 */
public ActivitatOnline getActivitat (int index) {
    if (index >= 0 && index < nElem) {
        return ofertes[index];
    }
    return null;
}


/**
 * METODE per recuperar per nom
 */
public ActivitatOnline getActivitat (String nom) {
        for (int i = 0; i < nElem; i++) {
            if (ofertes[i].getNom().equalsIgnoreCase(nom)) {
                return ofertes[i];
            }
        }
        return null;
    }

/**
 * METODE Afegir nova activitat mantenint ordre alfabètic per nom
 * @param activitatNova la nova activitat a afegir
 */
public void afegirActivitat (ActivitatOnline activitatNova) {
    if (nElem >= ofertes.length) { 
        System.err.println("Error: La llista està plena." + activitatNova.getNom());
        return;
    }

    if (getActivitat(activitatNova.getNom()) != null) {
            System.err.println("Error: Ja existeix una activitat amb el nom: " + activitatNova.getNom() + " No s'ha afegit.");
            return;
        }
    // busquem posicio on ha d'anar (i)
    int i = 0;
    while (i < nElem && ofertes[i].getNom().compareToIgnoreCase(activitatNova.getNom()) < 0) {
        i++;
    }

    // desplacem elements cap a la detra per fer forats
    for (int j = nElem; j > i; j--) {
        ofertes[j] = ofertes[j - 1]; 
    }

    // Afegim nova activitat i actualitzem comptador
    ofertes[i] = activitatNova;
    nElem++;
}

/**
 * METODE eliminar una activitat a partir del nom
 * 
 * @param nom 
 * @return true si s'ha eliminat, false si no   
 */
public boolean eliminarActivitat (String nom) {
    int pos = -1;

    // Recorrem array
    for (int i = 0; i < nElem; i++) {
        if (ofertes[i].getNom().equalsIgnoreCase(nom)) {
        pos = i;
        break; 
    }
}
    if (pos == -1) {
        return false;
    } 
    // Desplaçem 
    for (int i = pos; i < nElem - 1; i++) {
        ofertes[i] = ofertes[i + 1];
    }

    // Netegem
    nElem--;
    ofertes[nElem] = null;

    return true;
}

/**
 * METODE per cercar activitat pel nom
 * 
 * @param nom nom que buscar
 * @return l'objecte ActivitatOnline o null
 */
public ActivitatOnline cercarActivitat (String nom) {
    // recorrem llista
    for (int i = 0; i < nElem; i++) {
        if (ofertes[i].getNom().equalsIgnoreCase(nom)) {
            return ofertes[i];
        }
    }
    return null;
}
}
