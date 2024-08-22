package br.cefetmg.GestaoEntregasController;

import br.cefetmg.GestaoEntregasDAO.ClienteDAO;
import br.cefetmg.GestaoEntregasEntidades.Cliente;
import java.util.List;

public class ClienteController {
    private ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    public void inserirCliente(String nome, String endereço, String bairro, String telefone, String cnpj, String cpf) {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setEndereço(endereço);
        cliente.setBairro(bairro);
        cliente.setTelefone(telefone);
        cliente.setCnpj(cnpj);
        cliente.setCpf(cpf);

        clienteDAO.inserirCliente(cliente);
    }

    public Cliente getClienteById(int id) {
        return clienteDAO.findById(id);
    }

    public void atualizarCliente(Cliente cliente) {
        clienteDAO.atualizarCliente(cliente);
    }

    public void excluirCliente(int id) {
        clienteDAO.excluirCliente(id);
    }

    public List<Cliente> getAllClientes() {
        return clienteDAO.selecionarTodosClientes();
    }
}
