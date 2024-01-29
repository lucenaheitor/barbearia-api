package lucenaheitor.io.barbearia.infra.controllerException;

public class EmailExistsExeception  extends RuntimeException {
    public EmailExistsExeception(String msg) {
        super(msg);
    }
}

