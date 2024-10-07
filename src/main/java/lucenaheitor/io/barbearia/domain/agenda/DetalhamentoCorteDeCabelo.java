package lucenaheitor.io.barbearia.domain.agenda;

import java.time.LocalDateTime;

public class DetalhamentoCorteDeCabelo {

    private Long id;
    private Long idBarbeiro;
    private String nomeBarbeiro;
    private Long idCliente;
    private String nomeCliente;
    private LocalDateTime date;
    private Status status;

    public DetalhamentoCorteDeCabelo(Long id, Long idBarbeiro, String nomeBarbeiro, Long idCliente, String nomeCliente, LocalDateTime date, Status status) {
        this.id = id;
        this.idBarbeiro = idBarbeiro;
        this.nomeBarbeiro = nomeBarbeiro;
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.date = date;
        this.status = status;
    }

    // Construtor que recebe uma Agenda
    public DetalhamentoCorteDeCabelo(Agenda agenda) {
        this(agenda.getId(),
                agenda.getBarbeiro().getId(),
                agenda.getBarbeiro().getNome(),
                agenda.getCliente().getId(),
                agenda.getCliente().getNome(),
                agenda.getDate(),
                agenda.getStatus());
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdBarbeiro() {
        return idBarbeiro;
    }

    public void setIdBarbeiro(Long idBarbeiro) {
        this.idBarbeiro = idBarbeiro;
    }

    public String getNomeBarbeiro() {
        return nomeBarbeiro;
    }

    public void setNomeBarbeiro(String nomeBarbeiro) {
        this.nomeBarbeiro = nomeBarbeiro;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}



