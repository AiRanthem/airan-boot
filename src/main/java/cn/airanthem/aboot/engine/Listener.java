package cn.airanthem.aboot.engine;

import cn.airanthem.aboot.handler.Handler;

import java.util.concurrent.ExecutorService;

public abstract class Listener implements Runnable {
    ExecutorService executor;

    Handler handler;

    public Listener(Handler handler) {
        this.handler = handler;
    }
}
