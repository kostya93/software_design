package messenger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by kostya on 07.01.2017.
 */

public class Chat {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final Queue<Message> messages = new ConcurrentLinkedDeque<>();
    private final InetSocketAddress remoteAddress;

    public Chat(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public void sendMessage(Message message) throws IOException {
        try (Socket socket = new Socket(remoteAddress.getAddress(), remoteAddress.getPort())) {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Protocol.sendMessage(message, out);
            addMessage(message);
            logger.log(Level.INFO, "send message: " + message.toString());
        }
    }

    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    public Queue<Message> getMessages() {
        return messages;
    }

    void addMessage(Message message) {
        messages.add(message);
    }

    public String getName() {
        return remoteAddress.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chat chat = (Chat) o;

        return remoteAddress.equals(chat.remoteAddress);

    }

    @Override
    public int hashCode() {
        return remoteAddress.hashCode();
    }

    @Override
    public String toString() {
        return "Chat: " + remoteAddress.toString();
    }
}
