package com.example.ecommerceprototype.cms;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CMSDummyApplication extends Application {
    private static boolean hasLaunched;

    private static final Lock lock = new ReentrantLock();
    private static final Condition waitForLaunch = lock.newCondition();

    static void initApp() {
        if (!hasLaunched) {
            Thread thread = new Thread(() -> {
                Application.launch();
            });
            thread.start();
            try {
                lock.lock();
                waitForLaunch.await();
            } catch (InterruptedException e) {
                // fall through
            } finally {
                lock.unlock();
            }
            hasLaunched = true;
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            lock.lock();
            waitForLaunch.signalAll();
        } finally {
            lock.unlock();
        }
    }
}