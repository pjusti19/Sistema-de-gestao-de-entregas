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
    private TableColumn<Pedido, String> colProduto;

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

    private ObservableList<Pedido> pedidosList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colValorUnitario.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
        colValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colFormaPagamento.setCellValueFactory(new PropertyValueFactory<>("formaPagamento"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));

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
