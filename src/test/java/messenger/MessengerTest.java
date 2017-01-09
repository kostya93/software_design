package messenger;

import org.junit.Test;

import java.net.InetSocketAddress;

import static org.junit.Assert.*;

/**
 * Created by kostya on 09.01.2017.
 */
public class MessengerTest {
    private static final String NAME = "Ivan";
    private static final short PORT = 4444;
    private static final String REMOTE_HOST = "localhost";
    private static final short REMOTE_PORT = 5555;
    private static final String NAME_TWO = "Pert";
    @Test
    public void addAndGetChat() throws Exception {
        Messenger messenger = new Messenger(PORT, NAME);
        InetSocketAddress remoteAddress = new InetSocketAddress(REMOTE_HOST, REMOTE_PORT);
        assertNull(messenger.getChat(remoteAddress));
        Chat chat  = new Chat(remoteAddress);
        messenger.addChat(chat);
        assertEquals(chat, messenger.getChat(remoteAddress));
    }

    @Test
    public void getName() throws Exception {
        Messenger messenger = new Messenger(PORT, NAME);
        assertEquals(NAME, messenger.getName());
    }

    @Test
    public void setName() throws Exception {
        Messenger messenger = new Messenger(PORT, NAME);
        messenger.setName(NAME_TWO);
        assertEquals(NAME_TWO, messenger.getName());
    }

    @Test
    public void getPort() throws Exception {
        Messenger messenger = new Messenger(PORT, NAME);
        assertEquals(PORT, messenger.getPort());
    }

    @Test
    public void stopAndStopMessenger() throws Exception {
        Messenger messenger = new Messenger(PORT, NAME);
        assertTrue(messenger.start());

        Chat fakeChat = new Chat(new InetSocketAddress("localhost", PORT));
        Message message = new Message(NAME_TWO, "test message", REMOTE_PORT);
        fakeChat.sendMessage(message);
        Thread.sleep(1000);
        Chat chat = messenger.getChat(new InetSocketAddress(REMOTE_HOST, REMOTE_PORT));
        assertEquals(message, chat.getMessages().poll());

        assertTrue(messenger.stop());
    }
}