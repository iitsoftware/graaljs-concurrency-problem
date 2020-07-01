package proxy;

import event.EventProcessor;

import java.lang.reflect.Method;

public class AsyncProxy implements java.lang.reflect.InvocationHandler {

    private Object obj;
    private EventProcessor eventProcessor;
    private String ifMethod;

    public static Object newInstance(EventProcessor eventProcessor, Class[] ifClasses, Object obj, String ifMethod) {
        return java.lang.reflect.Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                ifClasses,
                new AsyncProxy(eventProcessor, obj, ifMethod));
    }

    private AsyncProxy(EventProcessor eventProcessor, Object obj, String ifMethod) {
        this.eventProcessor = eventProcessor;
        this.obj = obj;
        this.ifMethod = ifMethod;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if (method.getName().equals(ifMethod))
            eventProcessor.enqueue(args[0]);
        else
            result = method.invoke(obj, args);
        return result;
    }
}
