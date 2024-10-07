package lucenaheitor.io.barbearia.domain.agenda;


public class StatusDto {

    private Status status;

    // Construtor sem parâmetros (equivalente ao @NoArgsConstructor)
    public StatusDto() {
    }

    // Construtor com parâmetros (equivalente ao @AllArgsConstructor)
    public StatusDto(Status status) {
        this.status = status;
    }

    // Getter para o campo status
    public Status getStatus() {
        return status;
    }

    // Setter para o campo status
    public void setStatus(Status status) {
        this.status = status;
    }
}
