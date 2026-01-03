package Data;

/**
 * Classe que representa un usuari del col·lectiu ESTUDIANT.
 * Es guarda:
 *  - alias (identificador únic)
 *  - prefix del correu institucional (abans de l'@)
 *  - ensenyament on està matriculat
 *  - any d'inici dels estudis
 *
 * Segons l'enunciat, el domini del correu serà sempre @estudiants.urv.cat.
 * @author Kaouthar Addi
 */
public class UsuariEstudiant extends Usuari {

    private String ensenyament;
    private int anyIniciEstudis;

    /**
     * Crea un usuari estudiant.
     *
     * @param alias alias identificador (no pot ser nul ni buit)
     * @param correuPrefix prefix del correu (sense @, no pot ser nul ni buit)
     * @param ensenyament ensenyament (no pot ser nul ni buit)
     * @param anyIniciEstudis any d'inici (>0)
     * @throws IllegalArgumentException si alguna dada és incorrecta
     */
    public UsuariEstudiant(String alias,
                           String correuPrefix,
                           String ensenyament,
                           int anyIniciEstudis) {
        super(alias, correuPrefix);
        setEnsenyament(ensenyament);
        setAnyIniciEstudis(anyIniciEstudis);
    }

    public String getEnsenyament() {
        return ensenyament;
    }

    public void setEnsenyament(String ensenyament) {
        if (ensenyament == null || ensenyament.trim().isEmpty()) {
            throw new IllegalArgumentException("L'ensenyament no pot ser nul ni buit");
        }
        this.ensenyament = ensenyament.trim();
    }

    public int getAnyIniciEstudis() {
        return anyIniciEstudis;
    }

    public void setAnyIniciEstudis(int anyIniciEstudis) {
        if (anyIniciEstudis <= 0) {
            throw new IllegalArgumentException("L'any d'inici ha de ser positiu");
        }
        this.anyIniciEstudis = anyIniciEstudis;
    }

    /**
     * Retorna el correu institucional complet de l'estudiant.
     *
     * @return correu complet amb el domini @estudiants.urv.cat
     */
    @Override
    public String getCorreuComplet() {
        return correuPrefix + "@estudiants.urv.cat";
    }

    @Override
    public String toString() {
        return "UsuariEstudiant{" +
                "alias='" + alias + '\'' +
                ", correu='" + getCorreuComplet() + '\'' +
                ", ensenyament='" + ensenyament + '\'' +
                ", anyIniciEstudis=" + anyIniciEstudis +
                '}';
    }

    /**
     * Retorna representació per fitxer
     */
    @Override
    public String toFitxer() {
        return "EST;" + alias + ";" + correuPrefix + ";" + ensenyament + ";" + anyIniciEstudis;
    }

    /**
     * Dos usuaris estudiants es consideren iguals si tenen el mateix alias.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UsuariEstudiant)) return false;
        UsuariEstudiant altre = (UsuariEstudiant) obj;
        return alias.equalsIgnoreCase(altre.alias);
    }

    @Override
    public int hashCode() {
        return alias.toLowerCase().hashCode();
    }
}
