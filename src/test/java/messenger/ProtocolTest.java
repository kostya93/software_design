package messenger;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import static org.junit.Assert.*;

/**
 * Created by kostya on 09.01.2017.
 */

public class ProtocolTest {
    private final static Message message = new Message("name", "text", (short) 0);
    @Test
    public void testProtocol() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Protocol.sendMessage(message, new DataOutputStream(out));
        Message newMessage = Protocol.receiveMessage(new DataInputStream(new ByteArrayInputStream(out.toByteArray())));
        assertEquals(message, newMessage);
    }
}