package co.edu.uptc.utils;

import java.util.Timer;
import java.util.TimerTask;

public class TimerUtils {
    public static void schedule(Runnable action, long delayMs) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                action.run();
                timer.cancel();
            }
        }, delayMs);
    }
}
