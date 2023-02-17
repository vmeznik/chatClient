package chatClient.service;

import chatClient.model.socket.Member;
import chatClient.utility.UnnamedPropertyChangeSubject;

public interface IChatClientService extends UnnamedPropertyChangeSubject {
    void findPassword(String userName, String email);

    boolean login(String userName, String password);

    boolean register(String userName, String password, String email);

    void selectChat(Member member);

    void sendMessage(String text, Member member);
}
