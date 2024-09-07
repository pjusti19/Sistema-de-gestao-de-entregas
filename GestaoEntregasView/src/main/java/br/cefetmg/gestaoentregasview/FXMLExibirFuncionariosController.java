package br.cefetmg.gestaoentregasview;

import br.cefetmg.GestaoEntregasController.FuncionarioController;
import br.cefetmg.GestaoEntregasEntidades.Funcionario;
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

public class FXMLExibirFuncionariosController implements Initializable {

    @FXML
    private TableView<Funcionario> tableViewFuncionarios;

    @FXML
    private TableColumn<Funcionario, String> colNome;

    @FXML
    private TableColumn<Funcionario, Integer> colSenha;

    @FXML
    private TableColumn<Funcionario, Double> colTelefone;
    
    @FXML
    private TableColumn<Funcionario, String> colPerfil;

    @FXML
    private Button voltarButton;
    
    @FXML
    private Button deletarButton;
    
    @FXML
    private Button atualizarButton;

    private ObservableList<Funcionario> pedidosList;
    
    public void atualizarTabela() {
        carregarFuncionarios();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colPerfil.setCellValueFactory(new PropertyValueFactory<>("perfil"));

        carregarFuncionarios();
    }

    private void carregarFuncionarios() {
        try {
            FuncionarioController controller = new FuncionarioController();
            List<Funcionario> pedidos = controller.getAllFuncionarios();
            ObservableList<Funcionario> observableFuncionarios = FXCollections.observableArrayList(pedidos);
            tableViewFuncionarios.setItems(observableFuncionarios);
        } catch (Exception e) {
            showAlert("Erro ao carregar pedidos", "Não foi possível carregar os pedidos.", e.getMessage());
        }
    }
    
    @FXML
    private void deletarFuncionario() {
        Funcionario funcionarioSelecionado = tableViewFuncionarios.getSelectionModel().getSelectedItem();
        if (funcionarioSelecionado != null) {
            try {
                FuncionarioController controller = new FuncionarioController();
                controller.excluirFuncionario(funcionarioSelecionado.getId());
                tableViewFuncionarios.getItems().remove(funcionarioSelecionado);
                showAlert(Alert.AlertType.INFORMATION, "Funcionario excluído", "O funcionario foi excluído com sucesso.");
            } catch (Exception e) {
                showAlert("Erro ao excluir funcionario", "Não foi possível excluir o funcionario.", e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Nenhum funcionario selecionado", "Por favor, selecione um funcionario para excluir.");
        }
    }
    
    @FXML
    private void atualizarFuncionario() {
        Funcionario funcionarioSelecionado = tableViewFuncionarios.getSelectionModel().getSelectedItem();
        if (funcionarioSelecionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAtualizarFuncionario.fxml"));
                Parent root = loader.load();

                FXMLAtualizarFuncionarioController atualizarController = loader.getController();
                atualizarController.configurarCampos(
                        funcionarioSelecionado.getNome(),
                        funcionarioSelecionado.getTelefone(),
                        funcionarioSelecionado.getSenha(),
                        funcionarioSelecionado.getPerfil(),
                        funcionarioSelecionado.getId()
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
            showAlert(Alert.AlertType.WARNING, "Nenhum funcionario selecionado", "Por favor, selecione um funcionario para atualizar.");
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
