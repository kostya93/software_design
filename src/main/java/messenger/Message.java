package messenger;

/**
 * Created by kostya on 07.01.2017.
 */

public class Message {
    private final String name;
    private final String text;
    private final short port;

    public Message(String name, String text, short port) {
        this.name = name;
        this.text = text;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    short getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "name = " + name + "; text = " + text + "; port = " + port;
    }
}