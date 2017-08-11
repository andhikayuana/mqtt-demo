package io.github.andhikayuana.mqttdemo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.github.andhikayuana.mqttdemo.R;
import io.github.andhikayuana.mqttdemo.service.MqttMessageService;
import io.github.andhikayuana.mqttdemo.ui.chat.ChatActivity;
import io.github.andhikayuana.mqttdemo.util.Const;
import io.github.andhikayuana.mqttdemo.util.SharedPref;

public class MainActivity extends AppCompatActivity {

    private EditText etTopic;
    private Button btnGo;
    private EditText etBaseUrl;
    private EditText etClientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        etBaseUrl = (EditText) findViewById(R.id.etBaseUrl);
        etClientId = (EditText) findViewById(R.id.etClientId);
        etTopic = (EditText) findViewById(R.id.etTopic);
        btnGo = (Button) findViewById(R.id.btnGo);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                initService();
                openChat();
            }
        });
    }

    private void initService() {
        Intent intent = new Intent(this, MqttMessageService.class);
        startService(intent);
    }

    private void saveData() {
        SharedPref.putString(Const.KEY.BASE_URL, etBaseUrl.getText().toString());
        SharedPref.putString(Const.KEY.CLIENT_ID, etClientId.getText().toString());
        SharedPref.putString(Const.KEY.TOPIC, etTopic.getText().toString());
    }

    private void initData() {
        etBaseUrl.setText(Const.BASE_URL);
        etClientId.setText(Const.CLIENT_ID);
        etTopic.setText(Const.TOPIC);
    }

    private void openChat() {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }
}
