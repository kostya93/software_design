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
 * Class Chat represents a p2p chat between two users.
 * Each peer contains a InetSocketAddress other peer
 * and all messages.
 */

public class Chat {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final Queue<Message> messages = new ConcurrentLinkedDeque<>();
    private final InetSocketAddress remoteAddress;

    /**
     * Create a Chat with peer with remoteAddress address.
     * @param remoteAddress - address of other peer
     */
    public Chat(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    /**
     * Send message to other peer
     * @param message - message to send
     * @throws IOException throw if cant connect to other peer
     */
    public void sendMessage(Message message) throws IOException {
        try (Socket socket = new Socket(remoteAddress.getAddress(), remoteAddress.getPort())) {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Protocol.sendMessage(message, out);
            addMessage(message);
            logger.log(Level.INFO, "send message: " + message.toString());
        }
    }

    /**
     * @return address of other peer
     */
    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    /**
     * @return message history
     */
    public Queue<Message> getMessages() {
        return messages;
    }

    /**
     * Add message to message history
     * @param message message to add to message history
     */
    void addMessage(Message message) {
        messages.add(message);
    }

    /**
     * Return a chat name
     * @return chat name
     */
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
