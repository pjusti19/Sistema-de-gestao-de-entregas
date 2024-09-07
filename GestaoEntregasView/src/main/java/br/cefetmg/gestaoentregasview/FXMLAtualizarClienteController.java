package br.cefetmg.gestaoentregasview;

import br.cefetmg.GestaoEntregasController.ClienteController;
import br.cefetmg.gestaoentregasview.helper.VerificadorDeCampos;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;

public class FXMLAtualizarClienteController {

    @FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldEndereco;

    @FXML
    private TextField textFieldBairro;

    @FXML
    private TextField textFieldTelefone;

    @FXML
    private TextField textFieldCnpj;

    @FXML
    private TextField textFieldCpf;

    @FXML
    private Button atualizarButton;

    @FXML
    private Button voltarButton;

    private ClienteController clienteController;
    private int clienteId;

    @FXML
    public void initialize() {
        clienteController = new ClienteController();
    }

    public void configurarCampos(String nome, String endereco, String bairro, String telefone, String cnpj, String cpf, int clienteId) {
        textFieldNome.setText(nome);
        textFieldEndereco.setText(endereco);
        textFieldBairro.setText(bairro);
        textFieldTelefone.setText(telefone);
        textFieldCnpj.setText(cnpj);
        textFieldCpf.setText(cpf);
        this.clienteId = clienteId;
    }

    @FXML
    public void atualizarCliente() {
        String nome = textFieldNome.getText();
        String endereco = textFieldEndereco.getText();
        String bairro = textFieldBairro.getText();
        String telefone = textFieldTelefone.getText();
        String cnpj = textFieldCnpj.getText();
        String cpf = textFieldCpf.getText();

        Map<String, String> campos = Map.of(
                "Nome", nome,
                "Endereço", endereco,
                "Bairro", bairro,
                "Telefone", telefone,
                "CNPJ", cnpj,
                "CPF", cpf
        );

        VerificadorDeCampos verificador = new VerificadorDeCampos();
        if (!verificador.estaCompleto(campos)) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            try {
                clienteController.atualizarCliente(clienteId, nome, endereco, bairro, telefone, cnpj, cpf);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Cliente atualizado com sucesso!");
            } catch (Exception ex) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Erro: " + ex.getMessage());
            }
            alert.show();
        } else {
            showAlert(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, preencha todos os campos obrigatórios.");
        }
    }

    @FXML
    public void voltarMain() {
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
