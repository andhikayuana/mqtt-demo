package io.github.andhikayuana.mqttdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import io.github.andhikayuana.mqttdemo.ui.chat.Chat;
import io.github.andhikayuana.mqttdemo.util.Const;
import io.github.andhikayuana.mqttdemo.util.PahoMqqtClient;
import io.github.andhikayuana.mqttdemo.util.SharedPref;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 8/11/17
 */

public class MqttMessageService extends Service {

    public static final String HANDLE_MESSAGE = "handle_message";
    private static final String TAG = MqttMessageService.class.getSimpleName();
    private PahoMqqtClient pahoMqqtClient;
    private MqttAndroidClient mqttAndroidClient;
    private String baseUrl;
    private String clientId;

    public MqttMessageService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        baseUrl = SharedPref.getString(Const.KEY.BASE_URL);
        clientId = SharedPref.getString(Const.KEY.CLIENT_ID);

        pahoMqqtClient = PahoMqqtClient.getInstance();
        mqttAndroidClient = pahoMqqtClient
                .getMqttClient(getApplicationContext(), baseUrl, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

                handleMessage(Chat.parseMessage(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    private void handleMessage(Chat chat) {
        Intent intent = new Intent();
        intent.setAction(HANDLE_MESSAGE);
        intent.putExtra(Const.DATA, chat);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
