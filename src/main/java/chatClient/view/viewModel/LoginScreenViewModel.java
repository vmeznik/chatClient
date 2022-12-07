package chatClient.view.viewModel;

import chatClient.service.IChatClientService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginScreenViewModel implements PropertyChangeListener {
    private final StringProperty userName;
    private final StringProperty password;
    private final StringProperty error;
    private final IChatClientService iChatClientService;
    private boolean login;

    public LoginScreenViewModel(IChatClientService iChatClientService) {
        this.iChatClientService = iChatClientService;
        this.userName = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.error = new SimpleStringProperty();
        iChatClientService.addListener(this);
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty errorProperty() {
        return error;
    }


    public boolean login() {
        if (nullChecker()) {
            this.login = iChatClientService.login(this.userName.getValue(), this.password.getValue());
            this.userName.setValue(null);
            this.password.setValue(null);
        }
        return this.login;
    }

    private boolean nullChecker() {
        if (this.userName.getValue() != null && this.password.getValue() != null) {
            return true;
        }
        login = false;
        this.error.setValue("username or password is null");
        return false;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("errorLogin")) {
            Platform.runLater(() -> {
                this.error.set(evt.getNewValue().toString());
            });
        }
    }
}
