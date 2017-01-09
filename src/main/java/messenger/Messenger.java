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
 * Represents a messenger.
 * Messenger manage all chats and processing
 * incoming messages.
 */

public class Messenger {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final short port;
    private String name;
    private final Map<InetSocketAddress, Chat> chats = new ConcurrentHashMap<>();
    private ServerSocket serverSocket;
    private Thread serverThread;
    private ExecutorService executor;

    /**
     * Create a messenger with port and name
     * @param port - local port, where messenger will listen
     *             incoming messages
     * @param name initial user name
     */
    public Messenger(short port, String name) {
        this.port = port;
        this.name = name;
    }

    private Chat createChat(InetSocketAddress remoteAddress) {
        Chat chat = new Chat(remoteAddress);
        chats.put(remoteAddress, chat);
        return chat;
    }

    /**
     * Add new chat in chatlist
     * @param chat - chat to add in chatlist
     */
    public void addChat(Chat chat) {
        chats.put(chat.getRemoteAddress(), chat);
        logger.log(Level.INFO, "add chat: " + chat);
    }

    /**
     * Return a chat by InetSocketAddress.
     * null if chat with this InetSocketAddress doesnt exist.
     * @param remoteAddress address of desired chat
     * @return chat with specified InetSocketAddress or null,
     * if such chat doesnt exist
     */
    public Chat getChat(InetSocketAddress remoteAddress) {
        return chats.get(remoteAddress);
    }

    /**
     * Rteurn current user name
     * @return current user name
     */
    public String getName() {
        return name;
    }

    /**
     * Set user name
     * @param name - desired user name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return all chats
     * @return map from InetSocketAddress to chat
     */
    public Map<InetSocketAddress, Chat> getChats() {
        return chats;
    }

    /**
     * Return local port
     * @return local port
     */
    public short getPort() {
        return port;
    }

    /**
     * Start listen incoming messages
     * @throws IOException
     */
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

    /**
     * Stop listen incoming messages
     * @throws IOException
     */
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
