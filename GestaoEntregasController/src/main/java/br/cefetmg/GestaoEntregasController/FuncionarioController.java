package br.cefetmg.GestaoEntregasController;

import br.cefetmg.GestaoEntregasDAO.FuncionarioDAO;
import br.cefetmg.GestaoEntregasEntidades.Funcionario;
import java.util.Date;
import java.util.List;

public class FuncionarioController {
    private FuncionarioDAO funcionarioDAO;

    public FuncionarioController() {
        this.funcionarioDAO = new FuncionarioDAO();
    }

   public void cadastrarFuncionario(String nome, String senha, String telefone, Funcionario.Perfil perfil) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setSenha(senha);
        funcionario.setTelefone(telefone);
        funcionario.setPerfil(perfil);

        funcionarioDAO.inserirFuncionario(funcionario);
    }

    public Funcionario getFuncionarioById(int id) {
        return funcionarioDAO.findById(id);
    }

    public void atualizarFuncionario(int id, String nome, String senha, String telefone, Funcionario.Perfil perfil) {
        Funcionario funcionario = funcionarioDAO.findById(id);

        if (funcionario != null) {
            funcionario.setNome(nome);
            funcionario.setSenha(senha);
            funcionario.setTelefone(telefone);
            funcionario.setPerfil(perfil);

            funcionarioDAO.atualizarFuncionario(funcionario);
        } else {
            throw new RuntimeException("Funcionario com ID " + id + " não encontrado.");
        }
    }

    public void excluirFuncionario(int id) {
        funcionarioDAO.excluirFuncionario(id);
    }

    public List<Funcionario> getAllFuncionarios() {
        return funcionarioDAO.selecionarTodosFuncionarios();
    }
}
