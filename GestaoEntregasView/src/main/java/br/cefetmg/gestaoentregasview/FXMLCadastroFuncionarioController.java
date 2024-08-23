/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.cefetmg.gestaoentregasview;

import br.cefetmg.gestaoentregasview.helper.VerificadorDeCampos;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import br.cefetmg.GestaoEntregasController.FuncionarioController;
import br.cefetmg.GestaoEntregasEntidades.Funcionario;
import java.util.Map;
/**
 * FXML Controller class
 *
 * @author pedri
 */
public class FXMLCadastroFuncionarioController implements Initializable {

    @FXML
    private TextField textFieldNomeFuncionario;

    @FXML
    private TextField textFieldTelefoneFuncionario;

    @FXML
    private TextField textFieldSenhaFuncionario;

    @FXML
    private Button cadastrarFuncionario;

    @FXML
    private ComboBox<String> comboBoxOcupacaoFuncionario;
    
    @FXML
    public void cadastrarFuncionario() {
        
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
        if (verificador.estaCompleto(campos)) {
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.NONE);
        try {
            FuncionarioController controller = new FuncionarioController();
            controller.cadastrarFuncionario(nomeFuncionario, telefoneFuncionario, senhaFuncionario, ocupacaoFuncionario);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Cliente cadastrado com sucesso!");
        } catch (Exception ex) {
            System.out.println(ex.toString());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Error: " + ex.getMessage());
        }
        alert.show(); 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxOcupacaoFuncionario.setItems(FXCollections.observableArrayList("Administrador", "Entregador", "Atendente"));
    }    
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
