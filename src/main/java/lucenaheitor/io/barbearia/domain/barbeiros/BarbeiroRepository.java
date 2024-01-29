package lucenaheitor.io.barbearia.domain.barbeiros;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BarbeiroRepository  extends JpaRepository<Barbeiro, Long> {
    boolean existsByEmail(String email);
}
