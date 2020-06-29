processor.register(process);
messaging.register(wrapper.wrap(onMessage));


function process (event) {
    print(event);
}

function onMessage(event) {
    print(event);
}