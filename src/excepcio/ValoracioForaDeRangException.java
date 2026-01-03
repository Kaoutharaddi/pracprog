package excepcio;

/**
 * Excepció pròpia per indicar que una valoració no està dins
 * del rang permès (0..10).
 * @author Kaouthar Addi
 */
public class ValoracioForaDeRangException extends Exception {

    public ValoracioForaDeRangException() {
        super();
    }

    public ValoracioForaDeRangException(String message) {
        super(message);
    }
}
