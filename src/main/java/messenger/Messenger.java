package messenger;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by kostya on 07.01.2017.
 */

public class Messenger {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final short port;
    private String name;
    private final Map<InetSocketAddress, Chat> chats = new ConcurrentHashMap<>();
    private ServerSocket serverSocket;
    private Thread serverThread;
    private ExecutorService executor;

    public Messenger(short port, String name) {
        this.port = port;
        this.name = name;
    }

    private Chat createChat(InetSocketAddress remoteAddress) {
        Chat chat = new Chat(remoteAddress);
        chats.put(remoteAddress, chat);
        return chat;
    }

    public void addChat(Chat chat) {
        chats.put(chat.getRemoteAddress(), chat);
        logger.log(Level.INFO, "add chat: " + chat);
    }

    public Chat getChat(InetSocketAddress remoteAddress) {
        return chats.get(remoteAddress);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<InetSocketAddress, Chat> getChats() {
        return chats;
    }

    public short getPort() {
        return port;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        serverThread = new Thread(this::runServer);
        executor = Executors.newCachedThreadPool();
        serverThread.start();
        logger.log(Level.INFO, "start messenger: " + this);
    }

    private void runServer() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                logger.log(Level.INFO, "accept client: " + clientSocket.getRemoteSocketAddress());
                executor.execute(() -> processClient(clientSocket));
            }
        } catch (IOException ignored) {
        }
    }

    private void processClient(Socket clientSocket) {
        try {
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            Message message = Protocol.receiveMessage(in);
            InetSocketAddress remoteAddress = new InetSocketAddress(clientSocket.getInetAddress(), message.getPort());
            Chat chat = chats.get(remoteAddress);
            if (chat == null) {
                chat = createChat(remoteAddress);
            }
            logger.log(Level.INFO, "receive message: " + message);
            chat.addMessage(message);
            clientSocket.close();
        } catch (IOException e) {
            logger.log(Level.INFO, e.toString());
        }
    }

    public void stop() throws IOException {
        if (serverSocket == null) {
            return;
        }

        serverSocket.close();
        serverThread.interrupt();
        executor.shutdown();

        executor = null;
        serverSocket = null;
        serverThread = null;
    }

    @Override
    public String toString() {
        return "Messenger: name = " + name + "; port = " + port;
    }
}
