package proxy;

import event.EventProcessor;

import java.lang.reflect.Method;

public class AsyncProxy implements java.lang.reflect.InvocationHandler {

    private Object obj;
    private EventProcessor eventProcessor;

    public static Object newInstance(EventProcessor eventProcessor, Object obj) {
        return java.lang.reflect.Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                new AsyncProxy(eventProcessor, obj));
    }

    private AsyncProxy(EventProcessor eventProcessor, Object obj) {
        this.eventProcessor = eventProcessor;
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName());
        if (method.getName().equals("apply")) {
            System.out.println("enqueue messaging event");
            eventProcessor.enqueue(args[0]);
        } else
            return method.invoke(obj, args);
        return null;
    }
}
