package chatClient.view.viewModel;


import chatClient.model.socket.Member;
import chatClient.model.socket.Message;
import chatClient.service.IChatClientService;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ChatScreenViewModel implements PropertyChangeListener {
    private final StringProperty chatField;
    private final ListProperty<Member> members;
    private final ListProperty<Message> chat;
    private final IChatClientService iChatClientService;
    private final StringProperty name;

    public ChatScreenViewModel(IChatClientService iChatClientService) {
        this.iChatClientService = iChatClientService;
        this.chatField = new SimpleStringProperty();
        this.members = new SimpleListProperty<>();
        this.chat = new SimpleListProperty<>();
        this.name = new SimpleStringProperty();
        iChatClientService.addListener(this);
    }

    public StringProperty chatFieldProperty() {
        return chatField;
    }

    public ListProperty<Member> membersProperty() {
        return members;
    }

    public ListProperty<Message> chatProperty() {
        return chat;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void selectChat(Member member) {
        iChatClientService.selectChat(member);
    }

    public void sendMessage(Member member) {
        if (chatField.getValue() != null) {
            iChatClientService.sendMessage(chatField.getValue(), member);
            chatField.set(null);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("name")) {
            Platform.runLater(() -> {
                this.name.set(evt.getNewValue().toString());
            });
        } else if (evt.getPropertyName().equals("chat")) {
            Platform.runLater(() -> {
                this.chat.set(FXCollections.observableList((ArrayList<Message>) evt.getNewValue()));
            });
        } else if (evt.getPropertyName().equals("members")) {
            Platform.runLater(() -> {
                this.members.set(FXCollections.observableList((ArrayList<Member>) evt.getNewValue()));
            });
        }
    }
}
