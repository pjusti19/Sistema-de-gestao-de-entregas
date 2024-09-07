package br.cefetmg.gestaoentregasview;

import br.cefetmg.GestaoEntregasController.FuncionarioController;
import br.cefetmg.GestaoEntregasEntidades.Funcionario;
import br.cefetmg.gestaoentregasview.helper.VerificadorDeCampos;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;

public class FXMLAtualizarFuncionarioController {

    @FXML
    private TextField textFieldNomeFuncionario;

    @FXML
    private TextField textFieldTelefoneFuncionario;

    @FXML
    private TextField textFieldSenhaFuncionario;

    @FXML
    private Button cadastrarFuncionario;
    
    @FXML
    private Button voltarButton;

    @FXML
    private ComboBox<String> comboBoxOcupacaoFuncionario;

    private FuncionarioController funcionarioController;
    private int funcionarioId;

    @FXML
    public void initialize() {
        funcionarioController = new FuncionarioController();
    }

    public void configurarCampos(String nome, String telefone, String senha, Funcionario.Perfil perfil,  int funcionarioId) {
        String perfilStr = perfil.name();
        textFieldNomeFuncionario.setText(nome);
        textFieldTelefoneFuncionario.setText(telefone);
        textFieldSenhaFuncionario.setText(senha);
        comboBoxOcupacaoFuncionario.setValue(perfilStr);
        this.funcionarioId = funcionarioId;
    }

    @FXML
    public void atualizarFuncionario() {
        String nomeFuncionario = textFieldNomeFuncionario.getText();
        String telefoneFuncionario = textFieldTelefoneFuncionario.getText();
        String senhaFuncionario = textFieldSenhaFuncionario.getText();
        String ocupacaoFuncionarioStr = comboBoxOcupacaoFuncionario.getSelectionModel().getSelectedItem();
        Funcionario.Perfil ocupacaoFuncionario;
        
        try {
            ocupacaoFuncionario = Funcionario.Perfil.valueOf(ocupacaoFuncionarioStr.replace(" ", "_").toUpperCase());
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Status inválido.");
            return;
        }

        Map<String, String> campos = Map.of(
                "Nome", nomeFuncionario,
                "Telefone", telefoneFuncionario,
                "Senha", senhaFuncionario,
                "Ocupacao", ocupacaoFuncionarioStr
        );

        VerificadorDeCampos verificador = new VerificadorDeCampos();
        if (!verificador.estaCompleto(campos)) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            try {
                FuncionarioController controller = new FuncionarioController();
                controller.atualizarFuncionario(funcionarioId, nomeFuncionario, senhaFuncionario, telefoneFuncionario, ocupacaoFuncionario);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Funcionário cadastrado com sucesso!");
            } catch (Exception ex) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Erro: " + ex.getMessage());
            }
            alert.show();
        } 
        else {
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
