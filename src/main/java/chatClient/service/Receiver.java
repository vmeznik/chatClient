package chatClient.service;

import chatClient.model.socket.Member;
import chatClient.model.socket.Message;
import chatClient.utility.UnnamedPropertyChangeSubject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Receiver implements Runnable, UnnamedPropertyChangeSubject {
    private final BufferedReader in;
    private final Gson gson;
    private final PropertyChangeSupport property;
    private boolean isRunning;
    private final Type messageType = new TypeToken<ArrayList<Message>>() {
    }.getType();
    private final Type memberType = new TypeToken<ArrayList<Member>>() {
    }.getType();

    public Receiver(BufferedReader in) {
        this.in = in;
        this.gson = new Gson();
        this.property = new PropertyChangeSupport(this);
        isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                String jsonResponse = in.readLine();
                ArrayList<Message> response = gson.fromJson(jsonResponse, messageType);
                if (response.get(0).getSender() != null) {
                    property.firePropertyChange("chat", null, response);
                } else {
                    ArrayList<Member> responseMember = gson.fromJson(jsonResponse, memberType);
                    property.firePropertyChange("members", null, responseMember);
                }
            } catch (IOException e) {
                e.printStackTrace();
                isRunning = false;
            }
        }
    }

    @Override
    public void addListener(PropertyChangeListener listener) {
        property.addPropertyChangeListener(listener);
    }

    @Override
    public void removeListener(PropertyChangeListener listener) {
        property.removePropertyChangeListener(listener);
    }
}
