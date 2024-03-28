package lucenaheitor.io.barbearia.domain.agenda;

import java.time.LocalDateTime;

public record DetalhamentoCorteDeCabelo(Long id, Long idBarbeiro,String nomeBarbeiro, Long idCliente,String nomeCliente, LocalDateTime date) {

    public DetalhamentoCorteDeCabelo(Agenda agenda){
        this(agenda.getId(), agenda.getBarbeiro().getId(), agenda.getBarbeiro().getNome(), agenda.getCliente().getId(),agenda.getCliente().getNome(), agenda.getDate());

    }


}
