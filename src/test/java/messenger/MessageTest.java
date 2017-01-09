package messenger;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kostya on 09.01.2017.
 */
public class MessageTest {
    private static final String NAME = "Ivan";
    private static final String TEXT = "Text";
    private static final short PORT = 4444;
    private final Message message = new Message(NAME, TEXT, PORT);

    @Test
    public void getName() throws Exception {
        assertEquals(NAME, message.getName());
    }

    @Test
    public void getText() throws Exception {
        assertEquals(TEXT, message.getText());
    }

    @Test
    public void getPort() throws Exception {
        assertEquals(PORT, message.getPort());
    }

}