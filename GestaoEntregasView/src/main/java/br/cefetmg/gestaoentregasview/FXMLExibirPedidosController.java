package br.cefetmg.gestaoentregasview;

import br.cefetmg.GestaoEntregasController.PedidoController;
import br.cefetmg.GestaoEntregasEntidades.Pedido;
import br.cefetmg.gestaoentregasview.helper.VerificadorDeCampos;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.*;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class FXMLExibirPedidosController implements Initializable {

    @FXML
    private TableView<Pedido> tableViewPedidos;

    @FXML
    private TableColumn<Pedido, String> colNome;

    @FXML
    private TableColumn<Pedido, Integer> colQuantidade;

    @FXML
    private TableColumn<Pedido, Double> colValorUnitario;

    @FXML
    private TableColumn<Pedido, Double> colValorTotal;

    @FXML
    private TableColumn<Pedido, String> colMarca;

    @FXML
    private TableColumn<Pedido, String> colFormaPagamento;

    @FXML
    private TableColumn<Pedido, String> colStatus;

    @FXML
    private TableColumn<Pedido, String> colData;

    @FXML
    private Button voltarButton;
    
    @FXML
    private Button deletarButton;
    
    @FXML
    private Button atualizarButton;

    private ObservableList<Pedido> pedidosList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colValorUnitario.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
        colValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colFormaPagamento.setCellValueFactory(new PropertyValueFactory<>("formaPagamento"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));

        carregarPedidos();
    }
    
    public void atualizarTabela() {
        carregarPedidos();
    }

    private void carregarPedidos() {
        try {
            PedidoController controller = new PedidoController();
            List<Pedido> pedidos = controller.getAllPedidos();
            ObservableList<Pedido> observablePedidos = FXCollections.observableArrayList(pedidos);
            tableViewPedidos.setItems(observablePedidos);
        } catch (Exception e) {
            showAlert("Erro ao carregar pedidos", "Não foi possível carregar os pedidos.", e.getMessage());
        }
    }
    
    @FXML
    private void deletarPedido() {
        Pedido pedidoSelecionado = tableViewPedidos.getSelectionModel().getSelectedItem();
        if (pedidoSelecionado != null) {
            try {
                PedidoController controller = new PedidoController();
                controller.excluirPedido(pedidoSelecionado.getId());
                tableViewPedidos.getItems().remove(pedidoSelecionado);
                showAlert(Alert.AlertType.INFORMATION, "Pedido excluído", "O pedido foi excluído com sucesso.");
            } catch (Exception e) {
                showAlert("Erro ao excluir pedido", "Não foi possível excluir o pedido.", e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Nenhum pedido selecionado", "Por favor, selecione um pedido para excluir.");
        }
    }
    
    @FXML
    private void atualizarPedido() {
        Pedido pedidoSelecionado = tableViewPedidos.getSelectionModel().getSelectedItem();
        if (pedidoSelecionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAtualizarPedido.fxml"));
                Parent root = loader.load();

                FXMLAtualizarPedidoController atualizarController = loader.getController();
                atualizarController.configurarCampos(
                        pedidoSelecionado.getNomeProduto(),
                        pedidoSelecionado.getQuantidade(),
                        pedidoSelecionado.getValorUnitario(),
                        pedidoSelecionado.getValorTotal(),
                        pedidoSelecionado.getMarca(),
                        pedidoSelecionado.getFormaPagamento(),
                        pedidoSelecionado.getStatus(),
                        pedidoSelecionado.getData(),
                        pedidoSelecionado.getId()
                );

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                stage.setOnHidden(event -> atualizarTabela());
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar a tela de atualização.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Nenhum pedido selecionado", "Por favor, selecione um pedido para atualizar.");
        }
    }
    
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void voltarMain() {
        try {
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();
            String perfil = UserSession.getPerfil();
            UserSession.setPerfil(perfil);
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar a tela principal.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
