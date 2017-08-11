package io.github.andhikayuana.mqttdemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.github.andhikayuana.mqttdemo.service.MqttMessageService;
import io.github.andhikayuana.mqttdemo.ui.chat.Chat;
import io.github.andhikayuana.mqttdemo.util.Const;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 8/12/17
 */

public class MessageReceiver extends BroadcastReceiver {

    private MessageReceiverListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(MqttMessageService.HANDLE_MESSAGE)) {
            Chat chat = (Chat) intent.getSerializableExtra(Const.DATA);
            listener.handleMessage(chat);
        }
    }

    public void setHandleMessage(MessageReceiverListener listener) {
        this.listener = listener;
    }

    public interface MessageReceiverListener {
        void handleMessage(Chat chat);
    }
}
