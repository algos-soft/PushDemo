package com.pushdemo.pushclient;

import android.app.Application;
import android.content.Context;

/**
 *
 */
public class PushClientApp extends Application {

    // Attenzione alle variabili statiche!
    // In Android, le variabili statiche possono essere ritenute
    // tra una sessione e l'altra della applicazione perché
    // il sistema non necessariamente distrugge il processo e ferma la JVM
    private static PushClientApp instance;


    public PushClientApp() {
        instance = this;
        init();
    }

    /**
     * Reinizializza tutte le variabili.
     * In Android, le variabili statiche (in questo caso il Singleton della app,
     * con tutte le sue variabili - anche non statiche) possono essere ritenute
     * tra una sessione e l'altra della applicazione perché
     * il sistema non necessariamente distrugge il processo e ferma la JVM.
     * Quindi all'avvio della app il prima possibile le devo resettare.
     */
    public static void init() {
    }

    public static Context getContext() {
        return getInstance();
    }

    public static PushClientApp getInstance() {
        return instance;
    }


}
