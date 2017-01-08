package messenger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by kostya on 07.01.2017.
 */

class Protocol {
    static void sendMessage(Message message, DataOutputStream out) throws IOException {
        out.writeUTF(message.getName());
        out.writeUTF(message.getText());
        out.writeShort(message.getPort());
        out.flush();
    }

    static Message receiveMessage(DataInputStream in) throws IOException {
        return new Message(in.readUTF(), in.readUTF(), in.readShort());
    }
}
