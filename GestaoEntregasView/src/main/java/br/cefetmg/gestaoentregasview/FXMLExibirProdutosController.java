package br.cefetmg.gestaoentregasview;

import br.cefetmg.GestaoEntregasController.ProdutoController;
import br.cefetmg.GestaoEntregasEntidades.Produto;
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

public class FXMLExibirProdutosController implements Initializable {

    @FXML
    private TableView<Produto> tableViewProdutos;

    @FXML
    private TableColumn<Produto, String> colNome;

    @FXML
    private TableColumn<Produto, String> colLocalizacao;


    @FXML
    private Button voltarButton;

    private ObservableList<Produto> pedidosList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colLocalizacao.setCellValueFactory(new PropertyValueFactory<>("localizacao"));
        carregarProdutos();
    }

    private void carregarProdutos() {
        try {
            ProdutoController controller = new ProdutoController();
            List<Produto> pedidos = controller.getAllProdutos();
            ObservableList<Produto> observableProdutos = FXCollections.observableArrayList(pedidos);
            tableViewProdutos.setItems(observableProdutos);
        } catch (Exception e) {
            showAlert("Erro ao carregar produtos", "Não foi possível carregar os produtos.", e.getMessage());
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
