package lucenaheitor.io.barbearia.domain.agenda;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lucenaheitor.io.barbearia.domain.agenda.validacao_agenda.ValidacaoAgendamento;
import lucenaheitor.io.barbearia.domain.agenda.validation_cancel.CancelamentoAgenda;
import lucenaheitor.io.barbearia.domain.barbeiros.Barbeiro;
import lucenaheitor.io.barbearia.domain.barbeiros.BarbeiroRepository;
import lucenaheitor.io.barbearia.domain.clientes.ClienteRepository;
import lucenaheitor.io.barbearia.infra.exception.ValidationExeception;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AgendaDeDeCorteCabelo {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BarbeiroRepository barbeiroRespository;

    @Autowired
    private ClienteRepository clienteRespository;

    @Autowired
    private  AgendaRepository agendaRepository;

    @Autowired
    private List<ValidacaoAgendamento> validadores;

    @Autowired
    private List<CancelamentoAgenda> cancelamentoValidacao;

    public DetalhamentoCorteDeCabelo agendar(AgendamentoCorteDTO data) {
        if (!clienteRespository.existsById(data.getIdCliente())) {
            throw new ValidationException("Id do cliente inválido!");
        }

        if (data.getIdBarbeiro() != null && !barbeiroRespository.existsById(data.getIdBarbeiro())) {
            throw new ValidationException("Id do barbeiro é inválido");
        }

        validadores.forEach(v -> v.validar(data));

        var cliente = clienteRespository.getReferenceById(data.getIdCliente());
        var barbeiro = escolherBarbeiro(data);

        if (barbeiro == null) {
            throw new ValidationException("Nenhum barbeiro disponível nessa data");
        }

        var agenda = modelMapper.map(data, Agenda.class);

        agenda.setDate(LocalDateTime.now());
        agenda.setStatus(Status.REALIZADO);
        agenda.setCliente(cliente);
        agenda.setBarbeiro(barbeiro);

        agendaRepository.save(agenda);

        return modelMapper.map(agenda, DetalhamentoCorteDeCabelo.class);
    }

    private Barbeiro escolherBarbeiro(AgendamentoCorteDTO data) {
        if(data.getIdBarbeiro() != null){
            return  barbeiroRespository.getReferenceById(data.getIdBarbeiro());
        }
        if (data.getEspecialidade() == null){
            throw  new ValidationExeception("Especialidade obrigatoria");
        }
        return  barbeiroRespository.escolherBarbeiroAleatorio(data.getEspecialidade(), data.getDate());
    }

    public void cancelar(CancelamentoDTO data){
        if(!agendaRepository.existsById(data.idAgenda())){
            throw  new ValidationExeception("Agendamento não existe");
        }

        cancelamentoValidacao.forEach(c -> c.cancelar(data));

        var agenda = agendaRepository.getReferenceById(data.idAgenda());
        agenda.cancelar(data.cancelamento());
    }

    public DetalhamentoCorteDeCabelo atualizaStatus(Long id, StatusDto dto) {

        Agenda agenda = agendaRepository.findAgendaById(id)
                .orElseThrow(EntityNotFoundException::new);

        agenda.setStatus(dto.getStatus());
        agendaRepository.updateStatus(dto.getStatus(), agenda);
        return modelMapper.map(agenda, DetalhamentoCorteDeCabelo.class);
    }

    public void aprovaPagamentoPedido(Long id) {

        Agenda agenda = agendaRepository.findAgendaById(id)
                .orElseThrow(EntityNotFoundException::new);

        agenda.setStatus(Status.PAGO);
        agendaRepository.updateStatus(Status.PAGO, agenda);
    }


}
