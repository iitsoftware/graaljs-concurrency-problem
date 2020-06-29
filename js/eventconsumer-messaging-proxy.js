processor.register(process);
messaging.register(wrapper.wrap(process));


function process (event) {
    print(event);
}
