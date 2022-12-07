package chatClient.view.controller;

import chatClient.view.ViewHandler;
import chatClient.view.viewModel.LoginScreenViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class LoginScreenController {
    @FXML
    private TextField userName;
    @FXML
    private TextField password;
    @FXML
    private Text error;
    private ViewHandler viewHandler;
    private LoginScreenViewModel viewModel;
    private Region root;

    public void init(ViewHandler viewHandler, LoginScreenViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        this.userName.textProperty().bindBidirectional(viewModel.userNameProperty());
        this.password.textProperty().bindBidirectional(viewModel.passwordProperty());
        this.error.textProperty().bind(viewModel.errorProperty());
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    private void registerNewUser() {
        viewHandler.openView("register");
    }

    @FXML
    private void forgotPassword() {
        viewHandler.openView("forgotPassword");
    }

    @FXML
    private void loginButton() {
        if (viewModel.login()) {
            viewHandler.openView("chat");
        }
    }

    @FXML
    private void onEnter() {
        loginButton();
    }
}
