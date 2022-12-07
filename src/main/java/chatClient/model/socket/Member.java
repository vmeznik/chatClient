package chatClient.model.socket;

import java.io.Serializable;

public class Member implements Serializable {
    private String userName;
    private String password;
    private String email;
    private int id;

    public Member(String userName, String password, String email, int id) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.id = id;
    }

    public Member() {
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return userName;
    }

}
