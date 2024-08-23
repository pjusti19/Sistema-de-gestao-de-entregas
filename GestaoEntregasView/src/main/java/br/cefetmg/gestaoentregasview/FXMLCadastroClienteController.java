package br.cefetmg.gestaoentregasview;

import br.cefetmg.GestaoEntregasController.ClienteController;
import br.cefetmg.gestaoentregasview.helper.VerificadorDeCampos;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class FXMLCadastroClienteController{

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
    private Button cadastrarCliente;

    @FXML
    public void cadastrarCliente() {
        
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
        if (verificador.estaCompleto(campos)) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.NONE);
        try {
            ClienteController controller = new ClienteController();
            controller.cadastrarCliente(nome, endereco, bairro, telefone, cnpj, cpf);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Cliente cadastrado com sucesso!");
        } catch (Exception ex) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Erro: " + ex.getMessage());
        }
        alert.show();
    }

}
