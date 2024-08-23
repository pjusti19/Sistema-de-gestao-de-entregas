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

    public void atualizarEmpresa(Empresa empresa) {
        empresaDAO.atualizarEmpresa(empresa);
    }

    public void excluirEmpresa(int id) {
        empresaDAO.excluirEmpresa(id);
    }

    public List<Empresa> getAllEmpresas() {
        return empresaDAO.selecionarTodasEmpresas();
    }
}
