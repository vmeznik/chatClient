package chatClient.view;

import chatClient.view.controller.ChatScreenController;
import chatClient.view.controller.ForgotPasswordController;
import chatClient.view.controller.LoginScreenController;
import chatClient.view.controller.RegisterScreenController;
import chatClient.view.viewModel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ViewHandler {
    private Stage primaryStage;
    private Scene currentScene;
    private ChatScreenController chatScreenController;
    private ForgotPasswordController forgotPasswordController;
    private LoginScreenController loginScreenController;
    private RegisterScreenController registerScreenController;
    private final ViewModelFactory viewModelFactory;
    private Region root = null;

    public ViewHandler(ViewModelFactory viewModelFactory) {
        this.viewModelFactory = viewModelFactory;
        currentScene = new Scene(new Region());
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        currentScene = new Scene(new Region());
        openView("login");

    }

    public void openView(String id) {
        switch (id) {
            case "register" -> root = loadRegisterView("/RegisterScreen.fxml");
            case "forgotPassword" -> root = loadForgotPasswordView("/ForgotPassword.fxml");
            case "chat" -> root = loadChatView("/ChatScreen.fxml");
            default -> root = loadLoginView("/LoginScreen.fxml");
        }
        currentScene.setRoot(root);

        String title = "";
        if (root.getUserData() != null) {
            title += root.getUserData();
        }
        primaryStage.setTitle(title);
        primaryStage.setScene(currentScene);
        primaryStage.setWidth(root.getPrefWidth());
        primaryStage.setHeight(root.getPrefHeight());
        primaryStage.show();

    }

    private Region loadRegisterView(String fxmlFile) {
        if (registerScreenController == null) {
            try {
                URL fxmlLocation = RegisterScreenController.class.getResource(fxmlFile);
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                root = loader.load();
                registerScreenController = loader.getController();
                registerScreenController.init(this, viewModelFactory.getRegisterScreenViewModel(), root);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        return registerScreenController.getRoot();
    }

    private Region loadForgotPasswordView(String fxmlFile) {
        if (forgotPasswordController == null) {
            try {
                URL fxmlLocation = ForgotPasswordController.class.getResource(fxmlFile);
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                root = loader.load();
                forgotPasswordController = loader.getController();
                forgotPasswordController.init(this, viewModelFactory.getForgotPasswordViewModel(), root);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        return forgotPasswordController.getRoot();
    }

    private Region loadChatView(String fxmlFile) {
        if (chatScreenController == null) {
            try {
                URL fxmlLocation = ChatScreenController.class.getResource(fxmlFile);
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                root = loader.load();
                chatScreenController = loader.getController();
                chatScreenController.init(this, viewModelFactory.getChatScreenViewModel(), root);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        return chatScreenController.getRoot();
    }

    private Region loadLoginView(String fxmlFile) {
        if (loginScreenController == null) {
            try {
                URL fxmlLocation = LoginScreenController.class.getResource(fxmlFile);
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                root = loader.load();
                loginScreenController = loader.getController();
                loginScreenController.init(this, viewModelFactory.getLoginScreenViewModel(), root);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        return loginScreenController.getRoot();
    }
}
