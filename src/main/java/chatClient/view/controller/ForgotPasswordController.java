package chatClient.view.controller;

import chatClient.view.ViewHandler;
import chatClient.view.viewModel.ForgotPasswordViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class ForgotPasswordController {

    @FXML
    private TextField userName;
    @FXML
    private TextField email;
    @FXML
    private Text showPassword;
    private Region root;
    private ViewHandler viewHandler;
    private ForgotPasswordViewModel viewModel;

    public void init(ViewHandler viewHandler, ForgotPasswordViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        this.userName.textProperty().bindBidirectional(viewModel.userNameProperty());
        this.email.textProperty().bindBidirectional(viewModel.emailProperty());
        this.showPassword.textProperty().bind(viewModel.passwordProperty());
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    private void backButton() {
        viewHandler.openView("login");
    }

    @FXML
    private void sendButton() {
        viewModel.findPassword();
    }

    @FXML
    private void onEnter() {
        sendButton();
    }
}
