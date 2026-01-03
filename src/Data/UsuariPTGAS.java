package Data;

/**
 * Classe que representa un usuari del col·lectiu PTGAS.
 * Es guarda:
 *  - alias (identificador únic)
 *  - prefix del correu institucional (abans de l'@)
 *  - campus on treballa
 *
 * Segons l'enunciat, el domini del correu serà sempre @urv.cat.
 * @author Paula Arco
 */
public class UsuariPTGAS extends Usuari{

    private String campus;

    /**
     * Crea un usuari estudiant.
     *
     * @param alias alias identificador (no pot ser nul ni buit)
     * @param correuPrefix prefix del correu (sense @, no pot ser nul ni buit)
     * @param campus campus on treballa (no pot ser nul ni buit)
     * @throws IllegalArgumentException si alguna dada és incorrecta
     */
    public UsuariPTGAS(String alias, String correuPrefix, String campus) {
        super(alias, correuPrefix);
        setCampus(campus);
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        if (campus == null || campus.trim().isEmpty()) {
            throw new IllegalArgumentException("El campus no pot ser nul ni buit");
        }
        this.campus = campus;
    }

    /**
     * Retorna el correu institucional complet de l'estudiant.
     *
     * @return correu complet amb el domini @estudiants.urv.cat
     */
    public String getCorreuComplet() {
        return correuPrefix + "@urv.cat";
    }

    @Override
    public String toString() {
        return "UsuariPTGAS{" +
                "alias='" + alias + '\'' +
                ", correu='" + getCorreuComplet() + '\'' +
                ", campus=" + campus +
                '}';
    }

    @Override
    public String toFitxer() {
        return "PTGAS;" + alias + ";" + correuPrefix + ";" + campus;
    }
}
