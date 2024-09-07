package br.cefetmg.GestaoEntregasController;

import br.cefetmg.GestaoEntregasDAO.EmpresaDAO;
import br.cefetmg.GestaoEntregasEntidades.Empresa;
import java.util.List;

public class EmpresaController {
    private EmpresaDAO empresaDAO;

    public EmpresaController() {
        this.empresaDAO = new EmpresaDAO();
    }

    public void cadastrarEmpresa(String nome, String cnpj, String cpf, double porcentagemComissao) {
        Empresa empresa = new Empresa();
        empresa.setNome(nome);
        empresa.setCnpj(cnpj);
        empresa.setCpf(cpf);
        empresa.setPorcentagemComissaoEntregador(porcentagemComissao);

        empresaDAO.inserirEmpresa(empresa);
    }

    public Empresa getEmpresaById(int id) {
        return empresaDAO.findById(id);
    }

    public void atualizarEmpresa(int id, String nome, String cnpj, String cpf, double PorcentagemComissaoEntregador) {
        Empresa empresa = empresaDAO.findById(id);

        if (empresa != null) {
            empresa.setNome(nome);
            empresa.setCnpj(cnpj);
            empresa.setCpf(cpf);
            empresa.setPorcentagemComissaoEntregador(PorcentagemComissaoEntregador);

            empresaDAO.atualizarEmpresa(empresa);
        } else {
            throw new RuntimeException("Empresa com ID " + id + " não encontrado.");
        }
    }

    public void excluirEmpresa(int id) {
        empresaDAO.excluirEmpresa(id);
    }

    public List<Empresa> getAllEmpresas() {
        return empresaDAO.selecionarTodasEmpresas();
    }
}
