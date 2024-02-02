package lucenaheitor.io.barbearia.domain.barbeiros;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface BarbeiroRepository  extends JpaRepository<Barbeiro, Long> {
    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    @Query("""
    select b from Barbeiro b
    where
    b.especialidade = :especialidade
    and
    b.id not in(
        select a.barbeiro.id from Agenda a
        where
        a.date = :date
        and
    a.cancelamento is null
                  
    )
    order by rand()
    limit 1
     """)
    Barbeiro escolherBarbeiroAleatorio(Especialidade especialidade, LocalDateTime date);
}
