package GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import messenger.Chat;

/**
 * Created by kostya on 07.01.2017.
 */
public class ChatListItem {
    private final StringProperty name;
    private final Chat chat;

    public ChatListItem(Chat chat) {
        this.name = new SimpleStringProperty(chat.getName());
        this.chat = chat;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public Chat getChat() {
        return chat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatListItem that = (ChatListItem) o;

        return name.get().equals(that.name.get()) && chat.equals(that.chat);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + chat.hashCode();
        return result;
    }
}
