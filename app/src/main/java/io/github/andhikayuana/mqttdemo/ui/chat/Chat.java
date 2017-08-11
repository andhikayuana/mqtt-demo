package io.github.andhikayuana.mqttdemo.ui.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.github.andhikayuana.mqttdemo.util.Const;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 8/11/17
 */

public class Chat implements Serializable {

    private int id;
    private String clientId;
    private String message;

    public Chat() {
    }

    public static List<Chat> generateData() {
        ArrayList<Chat> chats = new ArrayList<>();

        Chat chat1 = new Chat();
        chat1.setId(1);
        chat1.setClientId(Const.CLIENT_ID);
        chat1.setMessage("Halo");
        chats.add(chat1);

        Chat chat2 = new Chat();
        chat2.setId(2);
        chat2.setClientId("jarjit_client_id");
        chat2.setMessage("halo juga bro");
        chats.add(chat2);

        Chat chat3 = new Chat();
        chat3.setId(3);
        chat3.setClientId("jarjit_client_id");
        chat3.setMessage("ada apa ya ?");
        chats.add(chat3);

        Chat chat4 = new Chat();
        chat4.setId(4);
        chat4.setClientId(Const.CLIENT_ID);
        chat4.setMessage("itu ada orang lewat :P");
        chats.add(chat4);

        return chats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chat chat = (Chat) o;

        return clientId != null ? clientId.equals(chat.clientId) : chat.clientId == null;

    }

    @Override
    public int hashCode() {
        return clientId != null ? clientId.hashCode() : 0;
    }
}
