package br.cefetmg.gestaoentregasview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Label saudacoes;

    @FXML
    private VBox opcoes;
    
    private String perfil;
    
    public void initialize() {
        String perfil = UserSession.getPerfil();
        setPerfil(perfil);
    }
    
    public void setPerfil(String perfil) {
        perfil = UserSession.getPerfil();
        saudacoes.setText("Bem-vindo, " + perfil + "!");
        opcoes.getChildren().clear();

        if ("Administrador".equals(perfil)) {
            addButton("Cadastrar Pedido", "loadPedidoScene");
            addButton("Cadastrar Produto", "loadProdutoScene");
            addButton("Cadastrar Funcionário", "loadFuncionarioScene");
            addButton("Cadastrar Cliente", "loadClienteScene");
            addButton("Cadastrar Empresa", "loadEmpresaScene");
            addButton("Ver Pedidos", "verPedidos");
            addButton("Ver Produtos", "verProdutos");
            addButton("Ver Clientes", "verClientes");
            addButton("Ver Empresas", "verEmpresas");
            addButton("Ver Funcionários", "verFuncionarios");
        } else if ("Entregador".equals(perfil)) {
            addButton("Ver Pedidos", "verPedidos");
        }
    }
    
    public String getPerfil(){
        return perfil;
    }

    private void addButton(String text, String action) {
        Button button = new Button(text);
        button.setOnAction(event -> handleButtonAction(action));
        opcoes.getChildren().add(button);
    }

    private void handleButtonAction(String action) {
        try {
            switch (action) {
                case "loadPedidoScene":
                    loadScene("FXMLCadastroPedido.fxml");
                    break;
                case "loadProdutoScene":
                    loadScene("FXMLCadastroProduto.fxml");
                    break;
                case "loadFuncionarioScene":
                    loadScene("FXMLCadastroFuncionario.fxml");
                    break;
                case "loadClienteScene":
                    loadScene("FXMLCadastroCliente.fxml");
                    break;
                case "loadEmpresaScene":
                    loadScene("FXMLCadastroEmpresa.fxml");
                    break;
                case "verPedidos":
                    loadScene("FXMLExibirPedido.fxml");
                    break;
                case "verProdutos":
                    loadScene("FXMLExibirProduto.fxml");
                    break;
                case "verClientes":
                    loadScene("FXMLExibirCLiente.fxml");
                    break;
                case "verEmpresas":
                    loadScene("FXMLExibirEmpresa.fxml");
                    break;
                case "verFuncionarios":
                    loadScene("FXMLExibirFuncionario.fxml");
                    break;
                default:
                    System.out.println("Ação desconhecida: " + action);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao carregar a tela.");
        }
    }

    private void loadScene(String fxmlFile) throws Exception {
        Stage stage = (Stage) saudacoes.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
