package chatClient.view.controller;


import chatClient.model.socket.Member;
import chatClient.model.socket.Message;
import chatClient.view.ViewHandler;
import chatClient.view.viewModel.ChatScreenViewModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.Text;

public class ChatScreenController {
    @FXML
    private TextField chatField;
    @FXML
    private Button sendButton;
    @FXML
    private ListView<Message> chat;
    @FXML
    private ListView<Member> members;
    @FXML
    private Text name;
    private Region root;
    private ViewHandler viewHandler;
    private ChatScreenViewModel viewModel;
    private Member selectedMember;

    public void init(ViewHandler viewHandler, ChatScreenViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        this.chatField.textProperty().bindBidirectional(viewModel.chatFieldProperty());
        this.name.textProperty().bind(viewModel.nameProperty());
        this.chat.itemsProperty().bind(viewModel.chatProperty());
        this.members.itemsProperty().bindBidirectional(viewModel.membersProperty());
        this.members.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Member> observableValue, Member member, Member t1) {
                sendButton.setVisible(true);
                chat.setVisible(true);
                chatField.setVisible(true);

                selectedMember = members.getSelectionModel().getSelectedItem();
                viewModel.selectChat(selectedMember);
            }
        });
        this.chat.itemsProperty().addListener(new ChangeListener<ObservableList<Message>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<Message>> observableValue, ObservableList<Message> messages, ObservableList<Message> t1) {
                chat.scrollTo(chat.getItems().size() - 1);
            }
        });
    }


    public Region getRoot() {
        return root;
    }

    @FXML
    private void onEnter() {
        sendButton();
    }

    @FXML
    private void sendButton() {
        viewModel.sendMessage(selectedMember);
    }

}
