package GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import messenger.Message;

/**
 * Created by kostya on 08.01.2017.
 */

public class MessageItem {
    private final StringProperty name;
    private final StringProperty text;

    public MessageItem(Message message) {
        this.text = new SimpleStringProperty(message.getText());
        this.name = new SimpleStringProperty(message.getName());
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    @Override
    public String toString() {
        return text.get();
    }
}
