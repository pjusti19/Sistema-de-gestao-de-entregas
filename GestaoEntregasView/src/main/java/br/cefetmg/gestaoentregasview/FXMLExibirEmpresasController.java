package br.cefetmg.gestaoentregasview;

import br.cefetmg.GestaoEntregasController.EmpresaController;
import br.cefetmg.GestaoEntregasEntidades.Empresa;
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

public class FXMLExibirEmpresasController implements Initializable {

    @FXML
    private TableView<Empresa> tableViewEmpresas;

    @FXML
    private TableColumn<Empresa, String> colNome;

    @FXML
    private TableColumn<Empresa, String> colCnpj;

    @FXML
    private TableColumn<Empresa, String> colCpf;

    @FXML
    private TableColumn<Empresa, Double> colPorcentagemComissaoEntregador;

    @FXML
    private Button voltarButton;
    
    @FXML
    private Button deletarButton;
    
    private ObservableList<Empresa> pedidosList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colPorcentagemComissaoEntregador.setCellValueFactory(new PropertyValueFactory<>("PorcentagemComissaoEntregador"));

        carregarEmpresas();
    }

    private void carregarEmpresas() {
        try {
            EmpresaController controller = new EmpresaController();
            List<Empresa> pedidos = controller.getAllEmpresas();
            ObservableList<Empresa> observableEmpresas = FXCollections.observableArrayList(pedidos);
            tableViewEmpresas.setItems(observableEmpresas);
        } catch (Exception e) {
            showAlert("Erro ao carregar empresas", "Não foi possível carregar as empresas.", e.getMessage());
        }
    }
    
    public void atualizarTabela() {
        carregarEmpresas();
    }
    
    @FXML
    private void deletarEmpresa() {
        Empresa empresaSelecionada = tableViewEmpresas.getSelectionModel().getSelectedItem();
        if (empresaSelecionada != null) {
            try {
                EmpresaController controller = new EmpresaController();
                controller.excluirEmpresa(empresaSelecionada.getId());
                tableViewEmpresas.getItems().remove(empresaSelecionada);
                showAlert(Alert.AlertType.INFORMATION, "Empresa excluído", "A empresa foi excluído com sucesso.");
            } catch (Exception e) {
                showAlert("Erro ao excluir empresa", "Não foi possível excluir a empresa.", e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Nenhuma empresa selecionada", "Por favor, selecione uma empresa para excluir.");
        }
    }
    
    @FXML
    private void atualizarEmpresa() {
        Empresa empresaSelecionada = tableViewEmpresas.getSelectionModel().getSelectedItem();
        if (empresaSelecionada != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAtualizarEmpresa.fxml"));
                Parent root = loader.load();

                FXMLAtualizarEmpresaController atualizarController = loader.getController();
                atualizarController.configurarCampos(
                        empresaSelecionada.getNome(),
                        empresaSelecionada.getCnpj(),
                        empresaSelecionada.getCpf(),
                        empresaSelecionada.getPorcentagemComissaoEntregador(),
                        empresaSelecionada.getId()
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
            showAlert(Alert.AlertType.WARNING, "Nenhuma empresa selecionado", "Por favor, selecione uma empresa para atualizar.");
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
