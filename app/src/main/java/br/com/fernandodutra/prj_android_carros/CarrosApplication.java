package br.com.fernandodutra.prj_android_carros;

import android.app.Application;
import android.util.Log;

import com.squareup.otto.Bus;

/**
 * Created by Fernando Dutra
 * User: Fernando Dutra
 * Date: 10/06/2019
 * Time: 22:17
 * Prj_Android_Carros
 */
public class CarrosApplication extends Application {

    private static final String TAG = "CarrosApplication";
    private static CarrosApplication instance = null;
    private Bus bus = new Bus();

    public static CarrosApplication getInstance() {
        return instance; // Singleton
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "CarrosApplication.onCrete()");
        // Salva a instância para termos acesso como Singleton
        instance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "CarrosApplication.onTerminate()");
    }

    public Bus getBus() {
        return bus;
    }
}
