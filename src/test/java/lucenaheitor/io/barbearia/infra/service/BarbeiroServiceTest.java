package lucenaheitor.io.barbearia.infra.service;

import lucenaheitor.io.barbearia.domain.barbeiros.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BarbeiroServiceTest {

    @Mock
    private BarbeiroRepository barbeiroRepository;

    @Spy
    private ModelMapper modelMapper;


    @InjectMocks
    private BarbeiroService barbeiroService;

    private CadastroBarbeiroDTO cadastroBarbeiroDTO;
    private AtualizationoBarbeirosDTO atualizationoBarbeirosDTO;
    private ListagemBarbeirosDTO listagemBarbeirosDTO;
    private DetailsBarbeiros detailsBarbeiros;
    private Barbeiro barbeiro;

    @BeforeEach
    void setUp() {
        cadastroBarbeiroDTO = new CadastroBarbeiroDTO("João", "joao@example.com", "123.456.789-00", "1234567890", Especialidade.CORTE_BARBA);
        atualizationoBarbeirosDTO = new AtualizationoBarbeirosDTO(1L, "teste2", "teste@teste.com", "(11)1234567890");
        listagemBarbeirosDTO = new ListagemBarbeirosDTO(1L, "teste 123", Especialidade.CORTE_BARBA );
        detailsBarbeiros = new DetailsBarbeiros(1L, "João", "joao@example.com", "123.456.789-00", "(11)123456789", Especialidade.CORTE_BARBA);
        barbeiro = new Barbeiro();
    }

    @Test
    void register() {
        when(modelMapper.map(any(CadastroBarbeiroDTO.class), eq(Barbeiro.class))).thenReturn(barbeiro);
        when(barbeiroRepository.save(any(Barbeiro.class))).thenReturn(barbeiro);
        when(modelMapper.map(any(Barbeiro.class), eq(CadastroBarbeiroDTO.class))).thenReturn(cadastroBarbeiroDTO);

        CadastroBarbeiroDTO result = barbeiroService.register(cadastroBarbeiroDTO);

        assertNotNull(result);
        verify(barbeiroRepository, times(1)).save(any(Barbeiro.class));
        verify(modelMapper, times(1)).map(any(CadastroBarbeiroDTO.class), eq(Barbeiro.class));
        verify(modelMapper, times(1)).map(any(Barbeiro.class), eq(CadastroBarbeiroDTO.class));
    }

    @Test
    void getBarbeiro() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Barbeiro> barbeiroPage = new PageImpl<>(Collections.singletonList(barbeiro));
        when(barbeiroRepository.findAll(pageable)).thenReturn(barbeiroPage);
        when(modelMapper.map(any(Barbeiro.class), eq(ListagemBarbeirosDTO.class))).thenReturn(listagemBarbeirosDTO);

        Page<ListagemBarbeirosDTO> result = barbeiroService.getBarbeiro(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(barbeiroRepository, times(1)).findAll(pageable);
        verify(modelMapper, times(1)).map(any(Barbeiro.class), eq(ListagemBarbeirosDTO.class));
    }

    @Test
    void details() {
        when(barbeiroRepository.findById(anyLong())).thenReturn(Optional.of(barbeiro));
        when(modelMapper.map(any(Barbeiro.class), eq(DetailsBarbeiros.class))).thenReturn(detailsBarbeiros);

        DetailsBarbeiros result = barbeiroService.datails(1L);

        assertNotNull(result);
        verify(barbeiroRepository, times(1)).findById(anyLong());
        verify(modelMapper, times(1)).map(any(Barbeiro.class), eq(DetailsBarbeiros.class));
    }

    @Test
    void update() {
        barbeiro.setId(atualizationoBarbeirosDTO.id());
        barbeiro.setNome(atualizationoBarbeirosDTO.nome());
        barbeiro.setEmail(atualizationoBarbeirosDTO.email());
        barbeiro.setTelefone(atualizationoBarbeirosDTO.telefone());
        barbeiro.setEspecialidade(Especialidade.CORTE);

        when(modelMapper.map(any(AtualizationoBarbeirosDTO.class), eq(Barbeiro.class))).thenReturn(barbeiro);

        when(barbeiroRepository.getReferenceById(anyLong())).thenReturn(barbeiro);

        when(barbeiroRepository.save(any(Barbeiro.class))).thenReturn(barbeiro);

        when(modelMapper.map(any(Barbeiro.class), eq(AtualizationoBarbeirosDTO.class)))
                .thenReturn(atualizationoBarbeirosDTO);

        AtualizationoBarbeirosDTO result = barbeiroService.update(atualizationoBarbeirosDTO);

        assertEquals(atualizationoBarbeirosDTO.nome(), result.nome());
        assertEquals(atualizationoBarbeirosDTO.email(), result.email());
        assertEquals(atualizationoBarbeirosDTO.telefone(), result.telefone());

        verify(barbeiroRepository, times(1)).getReferenceById(atualizationoBarbeirosDTO.id());
        verify(barbeiroRepository, times(1)).save(any(Barbeiro.class));
    }

    @Test
    void delete() {
        doNothing().when(barbeiroRepository).deleteById(anyLong());

        barbeiroService.delete(1L);

        verify(barbeiroRepository, times(1)).deleteById(anyLong());
    }
}
