package proxy;

import event.EventProcessor;

import java.lang.reflect.Method;

public class AsyncProxy implements java.lang.reflect.InvocationHandler {

    private Object obj;
    private EventProcessor eventProcessor;
    private int hashcode;
    private String toString;

    public static Object newInstance(EventProcessor eventProcessor, Class[] ifClasses, Object obj) {
        return java.lang.reflect.Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                ifClasses,
                new AsyncProxy(eventProcessor, obj));
    }

    private AsyncProxy(EventProcessor eventProcessor, Object obj) {
        this.eventProcessor = eventProcessor;
        this.obj = obj;
        this.hashcode = obj.hashCode();
        this.toString = obj.toString();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        eventProcessor.enqueue(args[0]);
        return null;
    }

    @Override
    public int hashCode() {
        return hashcode;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.equals(this.obj);
    }

    @Override
    public String toString() {
        return toString;
    }
}
