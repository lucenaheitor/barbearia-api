package lucenaheitor.io.barbearia.infra.controllerException;

public class ValidationExeception extends RuntimeException {
    public ValidationExeception(String msg) {
        super(msg);
    }
}

