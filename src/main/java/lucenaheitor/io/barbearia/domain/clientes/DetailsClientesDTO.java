package lucenaheitor.io.barbearia.domain.clientes;

public record DetailsClientesDTO(Long id, String nome, String email, String cpf, String trelefone) {
    public DetailsClientesDTO(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getCpf(), cliente.getTelefone());
    }
}
