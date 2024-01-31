package lucenaheitor.io.barbearia.domain.agenda;


import lucenaheitor.io.barbearia.domain.barbeiros.Barbeiro;
import lucenaheitor.io.barbearia.domain.barbeiros.BarbeiroRepository;
import lucenaheitor.io.barbearia.domain.clientes.ClienteRepository;
import lucenaheitor.io.barbearia.infra.exception.ValidationExeception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeDeCorteCabelo {

    @Autowired
    private BarbeiroRepository barbeiroRespository;

    @Autowired
    private ClienteRepository clienteRespository;

    @Autowired
    private  AgendaRepository agendaRepository;

    public  DetalhamentoCorteDeCabelo agendar(AgendamentoCorteDTO data){
        if(!clienteRespository.existsById(data.idCliente())){
            throw  new ValidationExeception("Id do cliente invalido!");
        }

        if(data.idBarbeiro() != null &&  !barbeiroRespository.existsById(data.idBarbeiro())){
            throw  new ValidationExeception("Id do barbeiro é invalido");
        }

        var cliente =  clienteRespository.getReferenceById(data.idCliente());
        var barbeiro =  escolherBarbeiro(data);

        if(barbeiro == null){
            throw  new ValidationExeception("Nenhum barbeiro disponivel nessa data");
        }
        var agenda = new Agenda( null, barbeiro, cliente, data.date());

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

}
