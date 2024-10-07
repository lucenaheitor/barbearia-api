package lucenaheitor.io.barbearia.domain.agenda;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lucenaheitor.io.barbearia.domain.barbeiros.Especialidade;


import java.time.LocalDateTime;
@Getter
@Setter
public class AgendamentoCorteDTO {
        private Long idBarbeiro;

        @NotNull
        private Long idCliente;

        @NotNull
        @Future
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        private LocalDateTime date;

        private Especialidade especialidade;
        private Status status;

        // Construtor sem argumentos
        public AgendamentoCorteDTO() {
        }

        // Construtor com argumentos
        public AgendamentoCorteDTO(Long idBarbeiro, @NotNull Long idCliente, @NotNull @Future LocalDateTime date, Especialidade especialidade, Status status) {
                this.idBarbeiro = idBarbeiro;
                this.idCliente = idCliente;
                this.date = date;
                this.especialidade = especialidade;
                this.status = status;
        }

        // Getters e Setters
        public Long getIdBarbeiro() {
                return idBarbeiro;
        }

        public void setIdBarbeiro(Long idBarbeiro) {
                this.idBarbeiro = idBarbeiro;
        }

        public Long getIdCliente() {
                return idCliente;
        }

        public void setIdCliente(Long idCliente) {
                this.idCliente = idCliente;
        }

        public LocalDateTime getDate() {
                return date;
        }

        public void setDate(LocalDateTime date) {
                this.date = date;
        }

        public Especialidade getEspecialidade() {
                return especialidade;
        }

        public void setEspecialidade(Especialidade especialidade) {
                this.especialidade = especialidade;
        }

        public Status getStatus() {
                return status;
        }

        public void setStatus(Status status) {
                this.status = status;
        }
}

