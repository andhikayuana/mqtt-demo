package io.github.andhikayuana.mqttdemo.ui.chat;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.UnsupportedEncodingException;

import io.github.andhikayuana.mqttdemo.R;
import io.github.andhikayuana.mqttdemo.receiver.MessageReceiver;
import io.github.andhikayuana.mqttdemo.service.MqttMessageService;
import io.github.andhikayuana.mqttdemo.util.Const;
import io.github.andhikayuana.mqttdemo.util.PahoMqqtClient;
import io.github.andhikayuana.mqttdemo.util.SharedPref;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 8/11/17
 */

public class ChatActivity extends AppCompatActivity implements MessageReceiver.MessageReceiverListener {

    private EditText etInputMessage;
    private ImageView ivSend;
    private ListView lvChat;
    private ChatAdapter chatAdapter;
    private PahoMqqtClient pahoMqttClient;
    private MqttAndroidClient mqttClient;
    private String baseUrl;
    private String clientId;
    private String topic;
    private Button btnSubscribe;
    private IntentFilter intentFilter;
    private MessageReceiver messageReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        initData();
        initMqttClient();
        initAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();

        messageReceiver = new MessageReceiver();
        messageReceiver.setHandleMessage(this);
        intentFilter = new IntentFilter(MqttMessageService.HANDLE_MESSAGE);
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (intentFilter != null) {
            try {
                unregisterReceiver(messageReceiver);
                intentFilter = null;
            } catch (Exception e) {

            }
        }
    }

    private void initData() {
        baseUrl = SharedPref.getString(Const.KEY.BASE_URL);
        clientId = SharedPref.getString(Const.KEY.CLIENT_ID);
        topic = SharedPref.getString(Const.KEY.TOPIC);
    }

    private void initMqttClient() {
        pahoMqttClient = PahoMqqtClient.getInstance();
        mqttClient = pahoMqttClient.getMqttClient(getApplicationContext(), baseUrl, clientId);
    }

    private void initView() {
        getSupportActionBar().setTitle(SharedPref.getString(Const.KEY.CLIENT_ID));
        btnSubscribe = (Button) findViewById(R.id.btnSubscribe);
        lvChat = (ListView) findViewById(R.id.lvChat);
        etInputMessage = (EditText) findViewById(R.id.etInputMessage);
        ivSend = (ImageView) findViewById(R.id.ivSend);

        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSubcribe();
            }
        });
        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    sendMessage();
                }
            }
        });
    }

    private boolean isValid() {
        boolean valid = true;
        if (TextUtils.isEmpty(etInputMessage.getText().toString())) {
            valid = false;
            Toast.makeText(this, R.string.txt_cant_empty, Toast.LENGTH_SHORT).show();
        }
        return valid;
    }

    private void initAdapter() {
        chatAdapter = new ChatAdapter(this);
        lvChat.setAdapter(chatAdapter);
    }

    private void sendMessage() {

        Chat chat = new Chat();
        chat.setId(1);
        chat.setClientId(clientId);
        chat.setMessage(etInputMessage.getText().toString());

        etInputMessage.setText(null);

        try {
            pahoMqttClient.publishMessage(mqttClient, chat.getMessage(), 1, topic);
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        scrollToBottom();
    }

    private void scrollToBottom() {
        lvChat.post(new Runnable() {
            @Override
            public void run() {
                lvChat.setSelection(chatAdapter.getCount() - 1);
            }
        });
    }


    private void initSubcribe() {
        if (!topic.isEmpty()) {
            try {
                pahoMqttClient.subscribe(mqttClient, topic, 1);
                Toast.makeText(this, "Subscribe success", Toast.LENGTH_SHORT).show();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handleMessage(Chat chat) {
        chatAdapter.add(chat);
        scrollToBottom();
    }
}
