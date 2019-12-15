import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class ReactiveCreation {
    /*
    * A Mono<T> is a Reactive Streams Publisher, also augmented with a lot of operators that can be used to generate, transform, orchestrate Mono sequences.

      It is a specialization of Flux that can emit at most 1 <T> element: a Mono is either valued (complete with element), empty (complete without element) or failed (error).

      A Mono<Void> can be used in cases where only the completion signal is interesting (the Reactive Streams equivalent of a Runnable task completing).

      Like for Flux, the operators can be used to define an asynchronous pipeline which will be materialized anew for each Subscription.

      Note that some API that change the sequence's cardinality will return a Flux (and vice-versa, APIs that reduce the cardinality to 1 in a Flux return a Mono).
    *
    * Mono.just(1)
    *     .map(integer -> "foo" + integer)
    *     .or(Mono.delay(Duration.ofMillis(100)))
    *     .subscribe(System.out::println);
    *
    */

    // TODO Return an empty Mono
    Mono<String> emptyMono() {
        return Mono.empty();
    }

//========================================================================================

    // TODO Return a Mono that never emits any signal
    Mono<String> monoWithNoSignal() {
        return Mono.never();
    }

//========================================================================================

    // TODO Return a Mono that contains a "foo" value
    Mono<String> fooMono() {
        return Mono.just("foo");
    }

//========================================================================================

    // TODO Create a Mono that emits an IllegalStateException
    Mono<String> errorMono() {
        return Mono.error(new IllegalStateException());
    }

    /*
    * A Flux<T> is a Reactive Streams Publisher, augmented with a lot of operators that can be used to generate, transform, orchestrate Flux sequences.

    * It can emit 0 to n <T> elements (onNext event) then either completes or errors (onComplete and onError terminal events). If no terminal event is triggered, the Flux is infinite.
    *
    * Static factories on Flux allow to create sources, or generate them from several callbacks types.
    * Instance methods, the operators, let you build an asynchronous processing pipeline that will produce an asynchronous sequence.
    * Each Flux#subscribe() or multicasting operation such as Flux#publish and Flux#publishNext will materialize a dedicated instance of the pipeline and trigger the data flow inside it.
    *
    * Flux in action
    *
    * Flux.fromIterable(getSomeLongList())
    *     .delayElements(Duration.ofMillis(100))
    *     .doOnNext(serviceA::someObserver)
    *     .map(d -> d * 2)
    *     .take(3)
    *     .onErrorResumeWith(errorHandler::fallback)
    *     .doAfterTerminate(serviceM::incrementTerminate)
    *     .subscribe(System.out::println);
    */

    // TODO Return an empty Flux
    Flux<String> emptyFlux() {
        return Flux.empty();
    }

//========================================================================================

    // TODO Return a Flux that contains 2 values "foo" and "bar" without using an array or a collection
    Flux<String> fooBarFluxFromValues() {
        return Flux.just("foo", "bar");
    }

//========================================================================================

    // TODO Create a Flux from a List that contains 2 values "foo" and "bar"
    Flux<String> fooBarFluxFromList() {
        return Flux.fromIterable(Arrays.asList("foo", "bar"));
    }

//========================================================================================

    // TODO Create a Flux that emits an IllegalStateException
    Flux<String> errorFlux() {
        return Flux.error(new IllegalStateException());
    }

//========================================================================================

    // TODO Create a Flux that emits increasing values from 0 to 9 each 100ms
    Flux<Long> counter() {
        return Flux.interval(Duration.ofMillis(100)).take(10L);
    }
}
