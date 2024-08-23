package br.cefetmg.GestaoEntregasController;

import br.cefetmg.GestaoEntregasDAO.ProdutoDAO;
import br.cefetmg.GestaoEntregasEntidades.Produto;
import java.util.List;

public class ProdutoController {
    private ProdutoDAO produtoDAO;

    public ProdutoController() {
        this.produtoDAO = new ProdutoDAO();
    }

    public void cadastrarProduto(String nome, String localizacao) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setLocalizacao(localizacao);

        produtoDAO.inserirProduto(produto);
    }

    public Produto getProdutoById(int id) {
        return produtoDAO.findById(id);
    }

    public void atualizarProduto(Produto produto) {
        produtoDAO.atualizarProduto(produto);
    }

    public void excluirProduto(int id) {
        produtoDAO.excluirProduto(id);
    }

    public List<Produto> getAllProdutos() {
        return produtoDAO.selecionarTodosProdutos();
    }
}
