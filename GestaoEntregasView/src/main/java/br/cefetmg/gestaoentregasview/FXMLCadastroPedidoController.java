package br.cefetmg.gestaoentregasview;

import br.cefetmg.gestaoentregasview.helper.VerificadorDeCampos;
import br.cefetmg.GestaoEntregasController.PedidoController;
import br.cefetmg.GestaoEntregasEntidades.Pedido;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Map;

public class FXMLCadastroPedidoController implements Initializable {

    @FXML
    private TextField textFieldProduto;

    @FXML
    private TextField textFieldQuantidade;

    @FXML
    private TextField textFieldValorUnitario;

    @FXML
    private TextField textFieldValorTotal;

    @FXML
    private TextField textFieldMarca;

    @FXML
    private TextField textFieldFormaPagamento;

    @FXML
    private ComboBox<String> comboBoxStatus;

    @FXML
    private DatePicker datePickerData;

    @FXML
    private Button cadastrarPedido;

    @FXML
    public void cadastrarPedido() {
        String produto = textFieldProduto.getText();
        String quantidadeStr = textFieldQuantidade.getText();
        String valorUnitarioStr = textFieldValorUnitario.getText();
        String valorTotalStr = textFieldValorTotal.getText();
        String marca = textFieldMarca.getText();
        String formaPagamento = textFieldFormaPagamento.getText();
        String statusStr = comboBoxStatus.getSelectionModel().getSelectedItem();
        Date data = convertToDate(datePickerData.getValue());

        Pedido.Status status;
        try {
            status = Pedido.Status.valueOf(statusStr.replace(" ", "_").toUpperCase());
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Status inválido.");
            return;
        }

        Map<String, String> campos = Map.of(
            "Produto", produto,
            "Quantidade", quantidadeStr,
            "Valor Unitário", valorUnitarioStr,
            "Valor Total", valorTotalStr,
            "Marca", marca,
            "Forma de Pagamento", formaPagamento,
            "Status", statusStr
        );

        VerificadorDeCampos verificador = new VerificadorDeCampos();
        if (verificador.estaCompleto(campos)) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.NONE);
        try {
            int quantidade = Integer.parseInt(quantidadeStr);
            double valorUnitario = Double.parseDouble(valorUnitarioStr);
            double valorTotal = Double.parseDouble(valorTotalStr);

            PedidoController controller = new PedidoController();
            controller.cadastrarPedido(produto, quantidade, valorUnitario, valorTotal, marca, formaPagamento, status, data);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Pedido cadastrado com sucesso!");
        } catch (NumberFormatException ex) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Dados numéricos inválidos.");
        } catch (Exception ex) {
            System.out.println(ex.toString());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Erro: " + ex.getMessage());
        }
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxStatus.setItems(FXCollections.observableArrayList(
            "Em Preparação",
            "Saiu para Entrega",
            "Entregue"
        ));
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
