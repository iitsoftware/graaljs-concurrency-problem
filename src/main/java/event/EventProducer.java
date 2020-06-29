package event;

import java.util.Timer;
import java.util.TimerTask;

public class EventProducer {
    private Timer timer = new Timer();
    private EventProcessor eventProcessor = null;
    private String eventMessage = null;
    private long interval;

    public EventProducer(EventProcessor eventProcessor, String eventMessage, long interval) {
        this.eventProcessor = eventProcessor;
        this.eventMessage = eventMessage;
        this.interval = interval;
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
               eventProcessor.enqueue(eventMessage);
            }
        }, 1000, interval);
    }
}
