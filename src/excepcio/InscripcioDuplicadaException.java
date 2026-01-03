package excepcio;

/**
 * Excepció pròpia per indicar que ja existeix una inscripció
 * d'un usuari a una activitat determinada.
 * @author Kaouthar Addi
 */
public class InscripcioDuplicadaException extends Exception {

    public InscripcioDuplicadaException() {
        super();
    }

    public InscripcioDuplicadaException(String message) {
        super(message);
    }
}

