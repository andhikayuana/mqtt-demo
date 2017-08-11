package io.github.andhikayuana.mqttdemo;

import android.app.Application;
import android.content.Context;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 8/11/17
 */

public class App extends Application {

    private static App sContext;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
