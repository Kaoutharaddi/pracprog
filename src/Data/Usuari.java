package Data;

/**
 * Classe abstracta que representa un usuari de la URV.
 * Conté els atributs i comportament comú per PDI, PTGAS i Estudiants.
 * @author Paula Arco
 */
public abstract class Usuari {

    protected String alias;
    protected String correuPrefix;

    /**
     * Constructor de la classe Usuari.
     *
     * @param alias alias identificador (no pot ser nul ni buit)
     * @param correuPrefix prefix del correu (sense @, no pot ser nul ni buit)
     */
    public Usuari(String alias, String correuPrefix) {
        setAlias(alias);
        setCorreuPrefix(correuPrefix);
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        if (alias == null || alias.trim().isEmpty()) {
            throw new IllegalArgumentException("L'alias no pot ser nul ni buit");
        }
        this.alias = alias.trim();
    }

    public String getCorreuPrefix() {
        return correuPrefix;
    }

    public void setCorreuPrefix(String correuPrefix) {
        if (correuPrefix == null || correuPrefix.trim().isEmpty()) {
            throw new IllegalArgumentException("El prefix del correu no pot ser nul ni buit");
        }
        if (correuPrefix.contains("@")) {
            throw new IllegalArgumentException("El prefix del correu no pot contenir '@'");
        }
        this.correuPrefix = correuPrefix.trim();
    }

    /**
     * Retorna el correu institucional complet.
     * Cada tipus de usuari especifica el domini.
     */
    public abstract String getCorreuComplet();

    /**
     * Dos usuaris són iguals si tenen el mateix alias (ignorant majúscules).
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Usuari)) return false;
        Usuari altre = (Usuari) obj;
        return alias.equalsIgnoreCase(altre.alias);
    }

    @Override
    public int hashCode() {
        return alias.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return "Usuari{" +
                "alias='" + alias + '\'' +
                ", correu='" + getCorreuComplet() + '\'' +
                '}';
    }

    public abstract String toFitxer();
}
