package proxy;

import event.EventProcessor;

public class Wrapper {
    EventProcessor eventProcessor;

    public Wrapper(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    public Object wrap(Object obj) {
        return AsyncProxy.newInstance(eventProcessor, obj);
    }
}
