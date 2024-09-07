package br.cefetmg.gestaoentregasview;

import br.cefetmg.GestaoEntregasController.ClienteController;
import br.cefetmg.GestaoEntregasEntidades.Cliente;
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

public class FXMLExibirClientesController implements Initializable {

    @FXML
    private TableView<Cliente> tableViewClientes;

    @FXML
    private TableColumn<Cliente, String> colNome;

    @FXML
    private TableColumn<Cliente, String> colEndereco;

    @FXML
    private TableColumn<Cliente, String> colBairro;

    @FXML
    private TableColumn<Cliente, String> colTelefone;

    @FXML
    private TableColumn<Cliente, String> colCnpj;

    @FXML
    private TableColumn<Cliente, String> colCpf;

    @FXML
    private Button voltarButton;

    @FXML
    private Button deletarButton;

    @FXML
    private Button atualizarButton;

    private ObservableList<Cliente> pedidosList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        carregarClientes();
    }   
    public void atualizarTabela() {
        carregarClientes();
    }
    private void carregarClientes() {
        try {
            ClienteController controller = new ClienteController();
            List<Cliente> pedidos = controller.getAllClientes();
            ObservableList<Cliente> observableClientes = FXCollections.observableArrayList(pedidos);
            tableViewClientes.setItems(observableClientes);
        } catch (Exception e) {
            showAlert("Erro ao carregar clientes", "Não foi possível carregar os clientes.", e.getMessage());
        }
    }

    @FXML
    private void deletarCliente() {
        Cliente clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            try {
                ClienteController controller = new ClienteController();
                controller.excluirCliente(clienteSelecionado.getId());
                tableViewClientes.getItems().remove(clienteSelecionado);
                showAlert(Alert.AlertType.INFORMATION, "Cliente excluído", "O cliente foi excluído com sucesso.");
            } catch (Exception e) {
                showAlert("Erro ao excluir cliente", "Não foi possível excluir o cliente.", e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Nenhum cliente selecionado", "Por favor, selecione um cliente para excluir.");
        }
    }

    @FXML
    private void atualizarCliente() {
        Cliente clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAtualizarCliente.fxml"));
                Parent root = loader.load();

                FXMLAtualizarClienteController atualizarController = loader.getController();
                atualizarController.configurarCampos(
                        clienteSelecionado.getNome(),
                        clienteSelecionado.getEndereco(),
                        clienteSelecionado.getBairro(),
                        clienteSelecionado.getTelefone(),
                        clienteSelecionado.getCnpj(),
                        clienteSelecionado.getCpf(),
                        clienteSelecionado.getId()
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
            showAlert(Alert.AlertType.WARNING, "Nenhum cliente selecionado", "Por favor, selecione um cliente para atualizar.");
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
