package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import messenger.Messenger;

import java.io.IOException;

/**
 * Created by kostya on 07.01.2017.
 */
public class ControllerPreferences {
    private GuiMessenger mainApp;

    @FXML
    private TextField tfLocalPort;
    @FXML
    private TextField tfName;

    @FXML
    void handleStart(ActionEvent event) throws IOException {
        if ("".equals(tfName.getText())) {
            mainApp.showInformationDialog("Enter name");
            return;
        }

        if ("".equals(tfLocalPort.getText())) {
            mainApp.showInformationDialog("Enter port");
            return;
        }

        short port;
        try {
            port = Short.parseShort(tfLocalPort.getText());
        } catch (NumberFormatException e) {
            mainApp.showInformationDialog("Wrong port");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));
        Parent root = loader.load();

        Messenger messenger = new Messenger(port, tfName.getText());
        try {
            messenger.start();
        } catch (IOException e) {
            mainApp.showInformationDialog("Cant start messenger");
            return;
        }
        ((ControllerMain) loader.getController()).setMessenger(messenger);
        ((ControllerMain) loader.getController()).setMainApp(mainApp);
        ((ControllerMain) loader.getController()).startUpdater();
        mainApp.setControllerMain(loader.getController());

        Stage primaryStage = mainApp.getPrimaryStage();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("messenger");
        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("icon.png")));
        primaryStage.show();
    }

    void setMainApp(GuiMessenger mainApp) {
        this.mainApp = mainApp;
    }
}

