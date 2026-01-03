package Data;

/**
 * Classe que representa un usuari del col·lectiu PDI.
 * Es guarda:
 *  - alias (identificador únic)
 *  - prefix del correu institucional (abans de l'@)
 *  - departament assignat
 *  - campus on treballa
 *
 * Segons l'enunciat, el domini del correu serà sempre @urv.cat.
 * @author Paula Arco
 */
public class UsuariPDI extends Usuari{

    private String departament;
    private String campus;

    /**
     * Crea un usuari PDI.
     *
     * @param alias alias identificador (no pot ser nul ni buit)
     * @param correuPrefix prefix del correu (sense @, no pot ser nul ni buit)
     * @param departament departament (no pot ser nul ni buit)
     * @param campus campus on treballa(no pot ser nul no buit)
     */
    public UsuariPDI(String alias, String correuPrefix, String departament, String campus) {
        super(alias, correuPrefix);
        setdepartament(departament);
        setCampus(campus);
    }

    public String getDepartamentt() {
        return departament;
    }

    public void setdepartament(String departament) {
        if (departament == null || departament.trim().isEmpty()) {
            throw new IllegalArgumentException("El departament no pot ser nul ni buit");
        }
        this.departament = departament.trim();
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
    @Override
    public String getCorreuComplet() {
        return correuPrefix + "@urv.cat";
    }

    @Override
    public String toString() {
        return "UsuariPDI{" +
                "alias='" + alias + '\'' +
                ", correu='" + getCorreuComplet() + '\'' +
                ", departament='" + departament + '\'' +
                ", campus=" + campus +
                '}';
    }

    @Override
    public String toFitxer() {
        return "PDI;" + alias + ";" + correuPrefix + ";" + departament + ";" + campus;
    }

}