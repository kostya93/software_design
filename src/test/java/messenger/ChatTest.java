package messenger;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Created by kostya on 09.01.2017.
 */
public class ChatTest {
    private static final String HOST = "localhost";
    private static final short PORT = 4444;

    @Test
    public void getRemoteAddress() throws Exception {
        Chat chat = new Chat(new InetSocketAddress(HOST, PORT));
        InetSocketAddress address = chat.getRemoteAddress();
        assertEquals(HOST, address.getHostName());
        assertEquals(PORT, address.getPort());
    }

    @Test
    public void getMessages() throws Exception {
        Chat chat = new Chat(new InetSocketAddress(HOST, PORT));
        assertTrue(chat.getMessages().isEmpty());
        chat.addMessage(new Message("", "", (short) 0));
        assertEquals(1, chat.getMessages().size());
    }

    @Test
    public void addMessage() throws Exception {
        Chat chat = new Chat(new InetSocketAddress(HOST, PORT));
        final String name = "Ivan";
        final Message firstMessage = new Message(name, "some text message", PORT);
        final Message secondMessage = new Message(name, "another message", PORT);

        chat.addMessage(firstMessage);
        chat.addMessage(secondMessage);

        Queue<Message> messages = chat.getMessages();
        assertEquals(firstMessage, messages.poll());
        assertEquals(secondMessage, messages.poll());
    }

    @Test
    public void getName() throws Exception {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(HOST, PORT);
        Chat chat = new Chat(inetSocketAddress);
        assertEquals(inetSocketAddress.toString(), chat.getName());
    }
}
