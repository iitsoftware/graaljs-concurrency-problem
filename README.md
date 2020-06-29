# graaljs-concurrency-problem

Executable example to demonstrate a Graal JS concurrency problem.

## Problem

GraalVM does not allow multi-threaded access to JavaScript. Even if the app takes explicit responsibility by using
an event loop that ensures single-threaded access, it strictly forbids that. For example, it is not possible to
register a callback on a 3rd party messaging provider that delivers messages asynchronously and then forward the 
received message to an event queue to process it synchronously. 

Nashorn allows this. JS scripts that use this way of processing are not portable. The only way to solve that is by wrapping 
each external library (i.e. all existing messaging clients like RabbitMQ, ActiveMQ, SwiftMQ, Paho, Pulsar ...) with 
a Java class that forwards the received message at the Java side to an event queue for processing, before the Graal runtime
is called. 

This makes it impossible to fully port existing JS scripts of Nashorn-based frameworks like SwiftMQ Streams, Flow Director, vert.x.

## The Examples

This is a Maven project, so build it accordingly. It creates a fat-jar under `target/graaljs-concurrency-problem_fat.jar`.

The example JS scripts are under `js/`.

### EventLoop only

This example just shows the event loop only, without an async messaging provider:

`java -cp target/graaljs-concurrency-problem_fat.jar Main js/eventconsumer.js`

It logs the received events to system.out.

### EventLoop plus async Messaging

This example registers at the event loop and a messaging provider. Async received messages are directly forwarded to the
event queue.  

`java -cp target/graaljs-concurrency-problem_fat.jar Main js/eventconsumer-messaging.js`

This example fails with `IllegalStateException` due to concurrent access of the JS context.

### EventLoop plus async Messaging plus Proxy

This example registers at the event loop and a messaging provider. The callback is wrapped in an `AsyncProxy` object that
forwards the received message to the event queue

`java -cp target/graaljs-concurrency-problem_fat.jar Main js/eventconsumer-messaging-proxy.js`

This example also fails with `IllegalStateException` due to concurrent access of the JS context. The proxy isn't even
called. We would expect that the proxy is called before JS runtime checks occur.

## How to solve it

- Introduce a switch at the `Context` to enable context locking instead of throwing `IllegalStateExceptions` to ensure 
synchronous access. Establish a lock on `Context.enter` and release it on `Context.leave` if the switch is enabled. Let
developers take responsibility for their actions.
- Ensure that a `java.lang.reflect.Proxy` can be used to wrap any JS object and ensure that the `Proxy` is called before
Graal runtime is called.
