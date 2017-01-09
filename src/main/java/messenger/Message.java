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
     * @return local senders port
     */
    short getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "name = " + name + "; text = " + text + "; port = " + port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return port == message.port
                && name.equals(message.name)
                && text.equals(message.text);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + (int) port;
        return result;
    }
}