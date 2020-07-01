processor.register(process);
messaging.register(wrapper.wrap("event.EventListener", "onEvent", process));


function process (event) {
    print(event);
}
