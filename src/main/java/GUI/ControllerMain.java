package GUI;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import messenger.Chat;
import messenger.Message;
import messenger.Messenger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerMain implements Initializable {
    private final ObservableList<ChatListItem> chatListItems = FXCollections.observableArrayList();
    private Messenger messenger;
    private GuiMessenger mainApp;
    private final ObservableList<MessageItem> messageItems = FXCollections.observableArrayList();
    private Thread updater;

    @FXML
    private TableView<ChatListItem> tableChats;

    @FXML
    private TableColumn<ChatListItem, String> columnChats;

    @FXML
    private TableView<MessageItem> tableMessages;

    @FXML
    private TableColumn<MessageItem, String> columnName;

    @FXML
    private TableColumn<MessageItem, String> columnText;

    @FXML
    private TextArea textInput;

    @FXML
    private Label labelName;

    @FXML
    void handleNewChat(ActionEvent event) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Create new chat");

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("icon.png")));

        ButtonType loginButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField tfHost = new TextField();
        tfHost.setText("localhost");
        TextField tfPort = new TextField();

        grid.add(new Label("Host:"), 0, 0);
        grid.add(tfHost, 1, 0);
        grid.add(new Label("Port:"), 0, 1);
        grid.add(tfPort, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        tfHost.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(tfHost.getText().isEmpty() || tfPort.getText().isEmpty());
        });
        tfPort.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(tfHost.getText().isEmpty() || tfPort.getText().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(tfHost::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(tfHost.getText(), tfPort.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        if (result.isPresent()) {
            Chat chat = null;
            try {
                chat = new Chat(new InetSocketAddress(result.get().getKey(), Integer.parseInt(result.get().getValue())));
                chat.sendMessage(new Message(messenger.getName(), "knock-knock", messenger.getPort()));
            } catch (IOException e) {
                mainApp.showInformationDialog("cant connect to " + chat.getRemoteAddress());
                return;
            } catch (NumberFormatException e) {
                mainApp.showInformationDialog("wrong port number: " + result.get().getValue());
                return;
            }
            messenger.addChat(chat);
            chatListItems.add(new ChatListItem(chat));
        }
    }

    @FXML
    void handleNewName(ActionEvent event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("New name");

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("icon.png")));

        ButtonType loginButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField name = new TextField();

        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        name.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(name::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return name.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            messenger.setName(result.get());
            labelName.setText(messenger.getName());
        }
    }

    @FXML
    void handleSend(ActionEvent event) {
        if ("".equals(textInput.getText())) {
            return;
        }
        if (tableChats.getSelectionModel().getSelectedItem() != null) {
            Chat chat = tableChats.getSelectionModel().getSelectedItem().getChat();
            try {
                Message message = new Message(messenger.getName(), textInput.getText(), messenger.getPort());
                chat.sendMessage(message);
                messageItems.add(new MessageItem(message));
            } catch (IOException e) {
                mainApp.showInformationDialog("cant connect to " + chat.getRemoteAddress());
                return;
            }
            textInput.setText("");
        }
    }

    void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    void setMainApp(GuiMessenger mainApp) {
        this.mainApp = mainApp;
    }

    void stop() {
        try {
            messenger.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
        updater.interrupt();
        updater = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnChats.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableChats.setItems(chatListItems);

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnText.setCellValueFactory(new PropertyValueFactory<>("text"));
        tableMessages.setItems(messageItems);

    }

    void startUpdater() {
        updater = new Thread(this::runUpdate);
        updater.start();
    }

    private void runUpdate() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                update();
                Thread.sleep(500);
            }
        } catch (InterruptedException ignored) {
        }
    }

    private void update() {
        labelName.setText(messenger.getName());
        Map<InetSocketAddress, Chat> chats = messenger.getChats();


        ChatListItem chatListItem = tableChats.getSelectionModel().getSelectedItem();

        chatListItems.clear();
        chatListItems.addAll(chats.values().stream()
                .map(ChatListItem::new)
                .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
                .collect(Collectors.toList()));

        if (chatListItems.size() == 1) {
            tableChats.getSelectionModel().select(0);
        } else {
            tableChats.getSelectionModel().select(chatListItem);
        }

        if (tableChats.getSelectionModel().getSelectedItem() != null) {
            Chat chat = tableChats.getSelectionModel().getSelectedItem().getChat();
            Queue<Message> messages = chat.getMessages();
            messageItems.clear();
            List<MessageItem> messageItems = messages.stream().map(MessageItem::new).collect(Collectors.toList());
            this.messageItems.addAll(messageItems);
        }

        tableChats.refresh();
        tableMessages.refresh();
    }
}
