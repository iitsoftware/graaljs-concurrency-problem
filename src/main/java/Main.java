import event.EventListener;
import event.EventProcessor;
import event.EventProducer;
import event.SimpleEventProcessor;
import proxy.Wrapper;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.SimpleScriptContext;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Missing argument: js-filename");
            System.exit(-1);
        }
        try {
            EventProcessor mainEventProcessor = new SimpleEventProcessor("mainLoop");
            EventProcessor messagingProvider = new SimpleEventProcessor("messaging");
            Wrapper wrapper = new Wrapper(mainEventProcessor);

            ScriptEngine engine = GraalSetup.engine();
            ScriptContext scriptContext = new SimpleScriptContext();
            Bindings bindings = engine.createBindings();
            bindings.put("processor", mainEventProcessor);
            bindings.put("messaging", messagingProvider);
            bindings.put("wrapper", wrapper);
            scriptContext.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
            engine.eval(new FileReader(args[0]), scriptContext);

            mainEventProcessor.start();
            messagingProvider.start();

            EventProducer mainEventProducer = new EventProducer(mainEventProcessor, "main loop event", 1000);
            mainEventProducer.start();
            EventProducer messagingEventProducer = new EventProducer(messagingProvider, "incoming message", 1000);
            messagingEventProducer.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
