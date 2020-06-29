package event;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SimpleEventProcessor extends Thread implements EventProcessor {
    private EventListener eventListener = null;
    private BlockingQueue<Object> queue = new LinkedBlockingQueue<Object>();

    public SimpleEventProcessor(String name) {
        super(name);
    }

    public void register(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void enqueue(Object event) {
        queue.add(event);
    }

    @Override
    public void run() {
        if (eventListener == null)
            return;
        while (true) {
            try {
                eventListener.onEvent(queue.take());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
