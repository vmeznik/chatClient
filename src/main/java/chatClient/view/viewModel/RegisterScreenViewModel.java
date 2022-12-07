package chatClient.view.viewModel;

import chatClient.model.socket.Message;
import chatClient.service.IChatClientService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class RegisterScreenViewModel implements PropertyChangeListener {
    private final StringProperty userName;
    private final StringProperty password;
    private final StringProperty password2;
    private final StringProperty email;
    private final StringProperty error;
    private final IChatClientService iChatClientService;
    private boolean register;

    public RegisterScreenViewModel(IChatClientService iChatClientService) {
        this.iChatClientService = iChatClientService;
        this.userName = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.password2 = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.error = new SimpleStringProperty();
        iChatClientService.addListener(this);
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty password2Property() {
        return password2;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty errorProperty() {
        return error;
    }

    public boolean register() {
        if (nullChecker() && passwordChecker() && emailChecker() && invalidNameChecker()) {
            this.register = iChatClientService.register(
                    this.userName.getValue(), this.password.getValue(), this.email.getValue());
            this.userName.setValue(null);
            this.password.setValue(null);
            this.password2.setValue(null);
            this.email.setValue(null);
        }
        return this.register;
    }

    private boolean passwordChecker() {
        if (this.password.getValue().equals(this.password2.getValue())) {
            return true;
        }
        this.error.setValue("Passwords are not the same");
        register = false;
        return false;
    }

    private boolean nullChecker() {
        if (this.userName.getValue() != null && this.password.getValue() != null &&
                this.password2.getValue() != null && this.email.getValue() != null) {
            return true;
        }
        this.error.setValue("Some argument is null");
        register = false;
        return false;
    }

    private boolean invalidNameChecker() {
        if (!this.userName.getValue().equalsIgnoreCase("all")) {
            return true;
        }
        this.error.setValue("Invalid username please choose different one");
        return false;
    }

    private boolean emailChecker() {
        if (this.email.getValue().contains("@") && this.email.getValue().contains(".")) {
            return true;
        }
        this.error.setValue("wrong format of an email address");
        register = false;
        return false;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("errorRegister")) {
            Platform.runLater(() -> {
                this.error.set(evt.getNewValue().toString());
            });
        }
    }
}
