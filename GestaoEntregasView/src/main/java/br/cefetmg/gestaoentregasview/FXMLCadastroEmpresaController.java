package br.cefetmg.gestaoentregasview;

import br.cefetmg.GestaoEntregasController.EmpresaController;
import br.cefetmg.gestaoentregasview.helper.VerificadorDeCampos;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLCadastroEmpresaController {

    @FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldCnpj;

    @FXML
    private TextField textFieldCpf;

    @FXML
    private TextField textFieldComissao;

    @FXML
    private Button cadastrarEmpresa;
    
    @FXML
    private Button voltarButton;
    
    @FXML
    public void cadastrarEmpresa() {
        
        String nome = textFieldNome.getText();
        String cnpj = textFieldCnpj.getText();
        String cpf = textFieldCpf.getText();
        String comissaoStr = textFieldComissao.getText();
        
        double comissao;
        try {
            comissao = Double.parseDouble(comissaoStr);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Comissão deve ser um número válido.");
            alert.show();
            return;
        }

        Map<String, String> campos = Map.of(
                "Nome", nome,
                "CNPJ", cnpj,
                "CPF", cpf,
                "Comissão (%)", comissaoStr
        );

        VerificadorDeCampos verificador = new VerificadorDeCampos();
        if (!verificador.estaCompleto(campos)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Por favor, preencha todos os campos obrigatórios.");
            alert.show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.NONE);
        try {
            EmpresaController controller = new EmpresaController();
            controller.cadastrarEmpresa(nome, cnpj, cpf, comissao);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Empresa cadastrada com sucesso!");
        } catch (Exception ex) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Erro: " + ex.getMessage());
        }
        alert.show();
    }
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
