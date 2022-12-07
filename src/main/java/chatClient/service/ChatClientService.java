package chatClient.service;

import chatClient.model.api.ForgotPassword;
import chatClient.model.api.Login;
import chatClient.model.api.Register;
import chatClient.model.api.RequestConfirmation;
import chatClient.model.socket.Member;
import chatClient.model.socket.Message;
import chatClient.utility.LineSplitter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

public class ChatClientService implements IChatClientService, PropertyChangeListener {
    private final PropertyChangeSupport property;
    private final ApiClient apiClient;
    private final SocketClient socketClient;
    private String userName;


    public ChatClientService() throws IOException {
        this.property = new PropertyChangeSupport(this);
        this.apiClient = new ApiClient();
        this.socketClient = new SocketClient();
        socketClient.addListener(this);
    }


    @Override
    public void findPassword(String userName, String email) {
        String password = apiClient.forgotPasswordRequest(new ForgotPassword(userName, email));
        if (password != null) {
            property.firePropertyChange("forgotPassword", null, password);
        }
    }

    @Override
    public boolean login(String userName, String password) {
        RequestConfirmation requestConfirmation = apiClient.loginRequest(new Login(userName, password));
        if (requestConfirmation.isSuccess()) {
            this.userName = userName;
            property.firePropertyChange("name", null, userName);
            socketClient.sendMessage(new Message("server", this.userName, ""));
            return true;
        } else {
            property.firePropertyChange("errorLogin", null, requestConfirmation.getError());
            return false;
        }
    }

    @Override
    public boolean register(String userName, String password, String email) {
        RequestConfirmation requestConfirmation = apiClient.registerRequest(new Register(userName, password, email));
        if (requestConfirmation.isSuccess()) {
            return true;
        } else {
            property.firePropertyChange("errorRegister", null, requestConfirmation.getError());
            return false;
        }
    }

    @Override
    public void selectChat(Member member) {
        try {
            socketClient.selectChat(new Message(member.getUserName(), userName, ""));
        } catch (NullPointerException ignored) {
        }
    }

    @Override
    public void sendMessage(String text, Member member) {
        String s = LineSplitter.lineLength(text, 40);
        socketClient.sendMessage(new Message(member.getUserName(), this.userName, s));
    }

    @Override
    public void addListener(PropertyChangeListener listener) {
        property.addPropertyChangeListener(listener);
    }

    @Override
    public void removeListener(PropertyChangeListener listener) {
        property.removePropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        property.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
    }
}
