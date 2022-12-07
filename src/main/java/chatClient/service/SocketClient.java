package chatClient.service;


import chatClient.model.socket.Message;
import chatClient.utility.UnnamedPropertyChangeSubject;
import com.google.gson.Gson;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class SocketClient implements UnnamedPropertyChangeSubject, PropertyChangeListener {
    private final String HOST = "localhost";
    private final int PORT = 5678;
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final Gson gson;
    private final PropertyChangeSupport property;

    public SocketClient() throws IOException {
        this.socket = new Socket(HOST, PORT);
        this.gson = new Gson();
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.property = new PropertyChangeSupport(this);
        Receiver receiver = new Receiver(this.in);
        receiver.addListener(this);
        Thread t1 = new Thread(receiver);
        t1.setDaemon(true);
        t1.start();
    }


    public void sendMessage(Message message) {
        String jsonRequest = gson.toJson(message);
        out.println(jsonRequest);
    }

    public void selectChat(Message message) {
        sendMessage(message);
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



