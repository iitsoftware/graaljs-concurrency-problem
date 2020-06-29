processor.register(process);
messaging.register(forwardEventQueue);


function process (event) {
    print(event);
}

function forwardEventQueue(event) {
    processor.enqueue(event);
}