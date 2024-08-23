package br.cefetmg.GestaoEntregasController;

import br.cefetmg.GestaoEntregasDAO.PedidoDAO;
import br.cefetmg.GestaoEntregasEntidades.Pedido;
import br.cefetmg.GestaoEntregasEntidades.Produto;
import java.util.Date;
import java.util.List;

public class PedidoController {
    private PedidoDAO pedidoDAO;

    public PedidoController() {
        this.pedidoDAO = new PedidoDAO();
    }

     public void cadastrarPedido(String nome_produto, int quantidade, double valorUnitario, double valorTotal, String marca, String formaPagamento, Pedido.Status status, Date data) {
        Pedido pedido = new Pedido();
        pedido.setNomeProduto(nome_produto);
        pedido.setQuantidade(quantidade);
        pedido.setValorUnitario(valorUnitario);
        pedido.setValorTotal(valorTotal);
        pedido.setMarca(marca);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setStatus(status);
        pedido.setData(data);

        pedidoDAO.inserirPedido(pedido);
    }

    public Pedido getPedidoById(int id) {
        return pedidoDAO.findById(id);
    }

    public void atualizarPedido(Pedido pedido) {
        pedidoDAO.atualizarPedido(pedido);
    }

    public void excluirPedido(int id) {
        pedidoDAO.excluirPedido(id);
    }

    public List<Pedido> getAllPedidos() {
        return pedidoDAO.selecionarTodosPedidos();
    }
}
