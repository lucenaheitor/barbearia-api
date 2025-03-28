package lucenaheitor.io.barbearia.infra.service;

import lucenaheitor.io.barbearia.domain.barbeiros.CadastroBarbeiroDTO;
import lucenaheitor.io.barbearia.domain.clientes.AtualizationClientesDTO;
import lucenaheitor.io.barbearia.domain.clientes.CadastroClienteDTO;
import lucenaheitor.io.barbearia.domain.clientes.Cliente;
import lucenaheitor.io.barbearia.domain.clientes.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ClienteServiceTest {


    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private Cliente cliente;

    @Mock
    private ModelMapper modelMapper;

    private CadastroClienteDTO cadastroClienteDTO;
    private AtualizationClientesDTO atualizationClientesDTO;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        cliente = new Cliente(1l, "test", "test@test.com", "123.456.789", "(11)12345-6789");
        cadastroClienteDTO = new CadastroClienteDTO("test", "test@test.com", "123.456.789", "(11)12345-6789");
        atualizationClientesDTO = new AtualizationClientesDTO(1L,"test","test@test.com","(11)12345-6789");


    }

    @Test
    void createCliente() {
        when(modelMapper.map(any(CadastroClienteDTO.class), eq(Cliente.class))).thenReturn(cliente);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(modelMapper.map(any(Cliente.class), eq(CadastroClienteDTO.class))).thenReturn(cadastroClienteDTO);

        CadastroClienteDTO result = clienteService.createCliente(cadastroClienteDTO);

        assertEquals(cadastroClienteDTO, result);
        verify(modelMapper).map(any(CadastroClienteDTO.class), eq(Cliente.class));
        verify(clienteRepository).save(any(Cliente.class));
        verify(modelMapper).map(any(CadastroClienteDTO.class), eq(Cliente.class));

    }

    @Test
    void getClient() {
    }

    @Test
    void detailClient() {
    }

    @Test
    void updateClient() {
        when(modelMapper.map(any(AtualizationClientesDTO.class), eq(Cliente.class))).thenReturn(cliente);
        when(clienteRepository.getReferenceById(anyLong())).thenReturn(cliente);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(modelMapper.map(any(Cliente.class), eq(AtualizationClientesDTO.class))).thenReturn(atualizationClientesDTO);

        AtualizationClientesDTO result = clienteService.updateClient(atualizationClientesDTO);


         assertNotNull(result);
         assertEquals(atualizationClientesDTO, result);
         verify(modelMapper).map(any(AtualizationClientesDTO.class), eq(Cliente.class));
         verify(clienteRepository).getReferenceById(anyLong());
         verify(clienteRepository).save(any(Cliente.class));
         verify(modelMapper).map(any(Cliente.class), eq(AtualizationClientesDTO.class));

    }

    @Test
    void deleteClient() {
        doNothing().when(clienteRepository).deleteById(anyLong());

        clienteService.deleteClient(1l);

        verify(clienteRepository).deleteById(anyLong());
    }
}