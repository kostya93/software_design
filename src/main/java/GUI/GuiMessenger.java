package GUI;/**
 * Created by kostya on 07.01.2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiMessenger extends Application {
    private Stage primaryStage;
    private ControllerMain controllerMain;

    void setControllerMain(ControllerMain controllerMain) {
        this.controllerMain = controllerMain;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("preferences.fxml"));
        Parent root = loader.load();

        ((ControllerPreferences)loader.getController()).setMainApp(this);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("messenger");
        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("icon.png")));
        primaryStage.show();
    }

    Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void stop() throws Exception {
        if (controllerMain != null) {
            controllerMain.stop();
        }
    }

    void showInformationDialog(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("icon.png")));
        alert.showAndWait();
    }
}
