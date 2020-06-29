package event;

public interface EventProcessor {
    void register(EventListener eventListener);
    void enqueue(Object event);
    void start();
    void stop();
}
