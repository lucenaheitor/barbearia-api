package lucenaheitor.io.barbearia.infra.service;

import jakarta.persistence.EntityNotFoundException;
import lucenaheitor.io.barbearia.controler.validation_barbeiros.ValidationBarbeiro;
import lucenaheitor.io.barbearia.domain.barbeiros.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarbeiroService {

    @Autowired
    private BarbeiroRepository barbeiroRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private List<ValidationBarbeiro> validadores;

    public CadastroBarbeiroDTO register(CadastroBarbeiroDTO dto) {
        Barbeiro barbeiro = modelMapper.map(dto, Barbeiro.class);
        validadores.forEach(v -> v.validar(dto));
        barbeiro = barbeiroRepository.save(barbeiro);
        return modelMapper.map(barbeiro, CadastroBarbeiroDTO.class);
    }



    public Page<ListagemBarbeirosDTO> getBarbeiro(Pageable pageable){
        return barbeiroRepository.findAll(pageable)
                .map(barbeiro -> modelMapper.map(barbeiro, ListagemBarbeirosDTO.class));
    }

    public DetailsBarbeiros datails(Long id){
        Barbeiro barbeiro = barbeiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Barbeiro.class.getName()));
        return  modelMapper.map(barbeiro, DetailsBarbeiros.class);
    }

    public  AtualizationoBarbeirosDTO update(AtualizationoBarbeirosDTO dto) {
            Barbeiro barbeiro = modelMapper.map(dto, Barbeiro.class);
            barbeiroRepository.getReferenceById(barbeiro.getId());
            barbeiroRepository.save(barbeiro);
            return modelMapper.map(barbeiro, AtualizationoBarbeirosDTO.class);
    }

    public  void delete(Long id){

        barbeiroRepository.deleteById(id);
    }

}
