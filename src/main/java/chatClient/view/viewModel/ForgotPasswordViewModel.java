package chatClient.view.viewModel;

import chatClient.service.IChatClientService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class ForgotPasswordViewModel implements PropertyChangeListener {
    private final StringProperty userName;
    private final StringProperty email;
    private final StringProperty password;
    private final IChatClientService iChatClientService;

    public ForgotPasswordViewModel(IChatClientService iChatClientService) {
        this.iChatClientService = iChatClientService;
        this.userName = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        iChatClientService.addListener(this);
    }


    public StringProperty userNameProperty() {
        return userName;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty passwordProperty() {
        return password;
    }


    public void findPassword() {
        if (nullChecker()) {
            iChatClientService.findPassword(this.userName.getValue(), this.email.getValue());
            this.userName.set(null);
            this.email.set(null);
        }
    }

    private boolean nullChecker() {
        if (this.userName.getValue() != null && this.email.getValue() != null) {
            return true;
        }
        this.password.set("Email or Username is null");
        return false;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("forgotPassword")) {
            Platform.runLater(() -> {
                this.password.set(evt.getNewValue().toString());
            });
        }
    }
}
