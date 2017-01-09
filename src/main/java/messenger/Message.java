package messenger;

/**
 * Represents one message between two peer
 * Contains name of sender, text and local port
 * where sender listen incoming messages.
 */

public class Message {
    private final String name;
    private final String text;
    private final short port;

    /**
     * Create a message.
     * @param name - sender name
     * @param text - text of message
     * @param port - local senders port
     */
    public Message(String name, String text, short port) {
        this.name = name;
        this.text = text;
        this.port = port;
    }

    /**
     * Return senders name
     * @return senders name
     */
    public String getName() {
        return name;
    }

    /**
     * Return text of message
     * @return text of message
     */
    public String getText() {
        return text;
    }

    /**
     * Return local senders port
     * @return lical senders port
     */
    short getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "name = " + name + "; text = " + text + "; port = " + port;
    }
}