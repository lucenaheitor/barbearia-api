package lucenaheitor.io.barbearia.domain.agenda;

import java.time.LocalDateTime;

public record DetalhamentoCorteDeCabelo(Long id, Long idBarbeiro, Long idCliente, LocalDateTime date) {

    public DetalhamentoCorteDeCabelo(Agenda agenda){
        this(agenda.getId(), agenda.getBarbeiro().getId(), agenda.getCliente().getId(), agenda.getDate());

    }
}
