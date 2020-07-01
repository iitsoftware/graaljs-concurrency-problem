package proxy;

import event.EventProcessor;

public class Wrapper {
    EventProcessor eventProcessor;

    public Wrapper(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    public Object wrap(String interfaceClassName, String interceptMethodName, Object obj) {
        try {
            Class clazz = Class.forName(interfaceClassName);
            return AsyncProxy.newInstance(eventProcessor, new Class[]{clazz}, obj, interceptMethodName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
