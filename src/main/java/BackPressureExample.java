import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class BackPressureExample {
    /*
    There's one aspect to it that we didn't cover: the volume control. In Reactive Streams terms this is called
    backpressure. It is a feedback mechanism that allows a Subscriber to signal to its Publisher how much data it is
    prepared to process, limiting the rate at which the Publisher produces data.

    This control of the demand is done at the Subscription level: a Subscription is created for each subscribe() call
    and it can be manipulated to either cancel() the flow of data or tune demand with request(long).

    Making a request(Long.MAX_VALUE) means an unbounded demand, so the Publisher will emit data at its fastest pace.
     */

    /*
    The demand can be tuned in the StepVerifier as well, by using the relevant parameter to create and
    withVirtualTime for the initial request, then chaining in thenRequest(long) in your expectations for further
    requests.

    In this first example, create a StepVerifier that produces an initial unbounded demand and verifies 4 values to be
    received, before completion. This is equivalent to the way you've been using StepVerifier so far.
     */
    ReactiveRepository<User> repository = new ReactiveUserRepository();

//========================================================================================

    // TODO Create a StepVerifier that initially requests all values and expect 4 values to be received
    StepVerifier requestAllExpectFour(Flux<User> flux) {
        return StepVerifier.create()
    }

//========================================================================================

    // TODO Create a StepVerifier that initially requests 1 value and expects User.SKYLER then requests another value and expects User.JESSE.
    StepVerifier requestOneExpectSkylerThenRequestOneExpectJesse(Flux<User> flux) {
        return null;
    }

//========================================================================================

    // TODO Return a Flux with all users stored in the repository that prints automatically logs for all Reactive Streams signals
    Flux<User> fluxWithLog() {
        return null;
    }

//========================================================================================

    // TODO Return a Flux with all users stored in the repository that prints "Starring:" on subscribe, "firstname lastname" for all values and "The end!" on complete
    Flux<User> fluxWithDoOnPrintln() {
        return null;
    }
}
