package lucenaheitor.io.barbearia.domain.agenda;


import jakarta.persistence.EntityNotFoundException;
import lucenaheitor.io.barbearia.domain.agenda.validacao_agenda.ValidacaoAgendamento;
import lucenaheitor.io.barbearia.domain.agenda.validation_cancel.CancelamentoAgenda;
import lucenaheitor.io.barbearia.domain.barbeiros.Barbeiro;
import lucenaheitor.io.barbearia.domain.barbeiros.BarbeiroRepository;
import lucenaheitor.io.barbearia.domain.clientes.ClienteRepository;
import lucenaheitor.io.barbearia.infra.exception.ValidationExeception;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public  DetalhamentoCorteDeCabelo agendar(AgendamentoCorteDTO data){
        if(!clienteRespository.existsById(data.idCliente())){
            throw  new ValidationExeception("Id do cliente invalido!");
        }

        if(data.idBarbeiro() != null &&  !barbeiroRespository.existsById(data.idBarbeiro())){
            throw  new ValidationExeception("Id do barbeiro é invalido");
        }

        validadores.forEach(v -> v.validar(data));

        var cliente =  clienteRespository.getReferenceById(data.idCliente());
        var barbeiro =  escolherBarbeiro(data);

        if(barbeiro == null){
            throw  new ValidationExeception("Nenhum barbeiro disponivel nessa data");
        }

        var agenda = new Agenda(null, barbeiro, cliente, data.date(), null, null);

        agendaRepository.save(agenda);
        return  new DetalhamentoCorteDeCabelo(agenda);
    }

    private Barbeiro escolherBarbeiro(AgendamentoCorteDTO data) {
        if(data.idBarbeiro() != null){
            return  barbeiroRespository.getReferenceById(data.idBarbeiro());
        }
        if (data.especialidade() == null){
            throw  new ValidationExeception("Esdpecialidade obrigatoria");
        }
        return  barbeiroRespository.escolherBarbeiroAleatorio(data.especialidade(), data.date());
    }

    public void cancelar(CancelamentoDTO data){
        if(!agendaRepository.existsById(data.idAgenda())){
            throw  new ValidationExeception("Agendamento não existe");
        }

        cancelamentoValidacao.forEach(c -> c.cancelar(data));

        var agenda = agendaRepository.getReferenceById(data.idAgenda());
        agenda.cancelar(data.cancelamento());
    }

    public AgendamentoCorteDTO atualizaStatus(Long id, StatusDto dto) {

        var  agenda = agendaRepository.findAgendaById(id);

        if (agenda == null) {
            throw new EntityNotFoundException();
        }

        agenda.set(dto.getStatus());
        agendaRepository.updateStatus(dto.getStatus(), agenda);
        return modelMapper.map(agenda,  AgendamentoCorteDTO.class);
    }

    public void aprovaPagamentoPedido(Long id) {

        Pedido pedido = repository.porIdComItens(id);

        if (pedido == null) {
            throw new EntityNotFoundException();
        }

        pedido.setStatus(Status.PAGO);
        repository.atualizaStatus(Status.PAGO, pedido);
    }

}
