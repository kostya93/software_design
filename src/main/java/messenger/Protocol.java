package messenger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Communication protocol between peers.
 */

class Protocol {
    /**
     * Write message to output stream
     * @param message - message which will be writed
     * @param out - output steam
     * @throws IOException
     */
    static void sendMessage(Message message, DataOutputStream out) throws IOException {
        out.writeUTF(message.getName());
        out.writeUTF(message.getText());
        out.writeShort(message.getPort());
        out.flush();
    }

    /**
     * Read message from input stream
     * @param in input stream
     * @return readed message
     * @throws IOException
     */
    static Message receiveMessage(DataInputStream in) throws IOException {
        return new Message(in.readUTF(), in.readUTF(), in.readShort());
    }
}
