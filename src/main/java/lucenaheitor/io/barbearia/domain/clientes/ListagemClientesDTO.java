package lucenaheitor.io.barbearia.domain.clientes;

public record ListagemClientesDTO(Long id, String nome, String email, String cpf, String telefone){

    public ListagemClientesDTO(Cliente cliente){
        this(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getCpf(), cliente.getTelefone());
    }
}
