package lucenaheitor.io.barbearia.infra.service;

import lucenaheitor.io.barbearia.domain.clientes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    private DetailsClientesDTO detailsClientesDTO;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente(1l, "test", "test@test.com", "123.456.789", "(11)12345-6789");
        cadastroClienteDTO = new CadastroClienteDTO("test", "test@test.com", "123.456.789", "(11)12345-6789");
        atualizationClientesDTO = new AtualizationClientesDTO(1L,"test","test@test.com","(11)12345-6789");
        detailsClientesDTO = new DetailsClientesDTO(1L, "test","test@testmail.com","123.456.789-00","(11)12345-67890");
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
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(modelMapper.map(any(DetailsClientesDTO.class), eq(Cliente.class))).thenReturn(cliente);

        clienteService.detailClient(anyLong());

        verify(clienteRepository).findById(anyLong());
        verify(modelMapper).map(any(Cliente.class), eq(DetailsClientesDTO.class));

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
    void clientService_DetailClient() {

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(modelMapper.map(any(), any())).thenReturn(detailsClientesDTO);

        clienteService.detailClient(anyLong());

        assertEquals(detailsClientesDTO, clienteService.detailClient(anyLong()));
        verify(clienteRepository, times(2)).findById(anyLong());

    }

    @Test
    void deleteClient() {
        doNothing().when(clienteRepository).deleteById(anyLong());

        clienteService.deleteClient(1l);

        verify(clienteRepository).deleteById(anyLong());
    }
}