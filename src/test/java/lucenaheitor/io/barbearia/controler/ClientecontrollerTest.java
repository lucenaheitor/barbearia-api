package lucenaheitor.io.barbearia.controler;

import lucenaheitor.io.barbearia.domain.clientes.*;
import lucenaheitor.io.barbearia.infra.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientecontrollerTest {
    @Mock
    private ClienteRepository repository;

    @Mock
    private ClienteService service;

    @InjectMocks
   private Clientecontroller controller;

    private CadastroClienteDTO cadastroClienteDTO;
    private AtualizationClientesDTO atualizationClientesDTO;
    private DetailsClientesDTO detailsClientesDTO;
    private ListagemClientesDTO listagemClientesDTO;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cadastroClienteDTO = new CadastroClienteDTO("João", "joao@example.com", "123.456.789-10", "(11) 98765-4321");
        atualizationClientesDTO = new AtualizationClientesDTO(1L, "João Atualizado", "joao.atualizado@example.com", "(11) 91234-5678");
        detailsClientesDTO = new DetailsClientesDTO(1L, "João", "joao@example.com", "123.456.789-10", "(11) 98765-4321");
        listagemClientesDTO = new ListagemClientesDTO(1L, "João", "joao@example.com", "123.456.789-10", "(11) 98765-4321");
        cliente = new Cliente(1L, "João", "joao@example.com", "123.456.789-10", "(11) 98765-4321");
    }

    @Test
    void register() {
        when(service.createCliente(any(CadastroClienteDTO.class))).thenReturn(cadastroClienteDTO);
        ResponseEntity<CadastroClienteDTO> response = controller.register(cadastroClienteDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cadastroClienteDTO, response.getBody());
    }

    @Test
    void list() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ListagemClientesDTO> page = new PageImpl<>(Collections.singletonList(listagemClientesDTO));
        when(service.getClient(pageable)).thenReturn(page);
        ResponseEntity<Page<ListagemClientesDTO>> response = controller.list(pageable);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
    }

    @Test
    void detail() {
        Long id = 1L;
        when(service.detailClient(id)).thenReturn(detailsClientesDTO);
        ResponseEntity<DetailsClientesDTO> response = controller.detail(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(detailsClientesDTO, response.getBody());
    }

    @Test
    void update() {
        when(repository.getReferenceById(anyLong())).thenReturn(cliente);
        ResponseEntity response = controller.update(atualizationClientesDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(repository, times(1)).getReferenceById(atualizationClientesDTO.id());
        verify(repository, times(1)).save(cliente);
    }

    @Test
    void delete() {
        Long id = 1L;
        doNothing().when(service).deleteClient(id);
        ResponseEntity response = controller.delete(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).deleteClient(id);
    }
}