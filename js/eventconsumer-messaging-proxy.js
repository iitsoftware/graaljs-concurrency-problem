processor.register(process);
messaging.register(wrapper.wrap("event.EventListener", process));


function process (event) {
    print(event);
}
