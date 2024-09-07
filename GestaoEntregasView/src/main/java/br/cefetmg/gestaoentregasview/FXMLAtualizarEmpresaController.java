package br.cefetmg.gestaoentregasview;

import br.cefetmg.GestaoEntregasController.EmpresaController;
import br.cefetmg.gestaoentregasview.helper.VerificadorDeCampos;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;

public class FXMLAtualizarEmpresaController {

    @FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldCnpj;

    @FXML
    private TextField textFieldCpf;

    @FXML
    private TextField textFieldPorcentagemComissaoEntregador;

    @FXML
    private Button atualizarButton;

    @FXML
    private Button voltarButton;

    private EmpresaController empresaController;
    private int empresaId;

    @FXML
    public void initialize() {
        empresaController = new EmpresaController();
    }

    public void configurarCampos(String nome, String cnpj, String cpf, double PorcentagemComissaoEntregador, int empresaId) {
        String PorcentagemComissaoString = Double.toString(PorcentagemComissaoEntregador);
        textFieldNome.setText(nome);
        textFieldCnpj.setText(cnpj);
        textFieldCpf.setText(cpf);
        textFieldPorcentagemComissaoEntregador.setText(PorcentagemComissaoString);
        this.empresaId = empresaId;
    }

    @FXML
    public void atualizarEmpresa() {
        String nome = textFieldNome.getText();
        String cnpj = textFieldCnpj.getText();
        String cpf = textFieldCpf.getText();
        String porcentagemStr = textFieldPorcentagemComissaoEntregador.getText();
        double PorcentagemComissaoEntregador = Double.parseDouble(porcentagemStr);

        Map<String, String> campos = Map.of(
                "Nome", nome,
                "CNPJ", cnpj,
                "CPF", cpf,
                "Comissao (%)", porcentagemStr
        );

        VerificadorDeCampos verificador = new VerificadorDeCampos();
        if (!verificador.estaCompleto(campos)) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            try {
                empresaController.atualizarEmpresa(empresaId, nome, cnpj, cpf, PorcentagemComissaoEntregador);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Empresa atualizada com sucesso!");
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
