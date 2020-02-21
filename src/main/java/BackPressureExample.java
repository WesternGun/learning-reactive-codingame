import domain.User;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import repository.ReactiveRepository;
import repository.ReactiveUserRepository;

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
    /*
    The demand can be tuned in the StepVerifier as well, by using the relevant parameter to create and
    withVirtualTime for the initial request, then chaining in thenRequest(long) in your expectations for further
    requests.

    In this first example, create a StepVerifier that produces an initial unbounded demand and verifies 4 values to be
    received, before completion. This is equivalent to the way you've been using StepVerifier so far.
     */
    // TODO Create a StepVerifier that initially requests all values and expect 4 values to be received
    StepVerifier requestAllExpectFour(Flux<User> flux) {
        return StepVerifier.create(flux).thenRequest(4).thenCancel();
    }

    //========================================================================================
    /*
    Next we will request values one by one: for that you need an initial request, but also a second single request
    after you've received and asserted the first element.

    Without more request, the source will never complete unless you cancel it. This can be done instead of the terminal
    expectations by using .thenCancel().
     */
    // TODO Create a StepVerifier that initially requests 1 value and expects domain.User.SKYLER
    //  then requests another value and expects domain.User.JESSE.
    StepVerifier requestOneExpectSkylerThenRequestOneExpectJesse(Flux<User> flux) {
        return StepVerifier.create(flux)
                .thenRequest(1L)
                .expectNext(User.SKYLER)
                .thenRequest(1L).expectNext(User.JESSE)
                .thenCancel();
    }

//========================================================================================
    /*
    A note on debugging
How to check that the previous sequence was requested one by one, and that a cancellation happened?

It's important to be able to debug reactive APIs, so in the next example we will make use of the log operator to know exactly what happens in term of signals and events.

Use the repository to get a Flux of all users, then apply a log to it. Observe in the console below how the underlying test requests it, and the other events like subscribe, onNext...
     */
    // TODO Return a Flux with all users stored in the repository that prints automatically logs for all Reactive
    //  Streams signals
    Flux<User> fluxWithLog() {
        return Flux.from(repository.findAll())
                .log();
        /*
        2020-02-21 13:16:51 [main] INFO  reactor.Flux.Zip.1 - onSubscribe(FluxZip.ZipCoordinator)
2020-02-21 13:16:51 [main] INFO  reactor.Flux.Zip.1 - request(1)
2020-02-21 13:16:51 [parallel-1] INFO  reactor.Flux.Zip.1 - onNext(Person{username='swhite', firstname='Skyler', lastname='White'})
2020-02-21 13:16:51 [parallel-1] INFO  reactor.Flux.Zip.1 - request(1)
2020-02-21 13:16:51 [parallel-1] INFO  reactor.Flux.Zip.1 - onNext(Person{username='jpinkman', firstname='Jesse', lastname='Pinkman'})
2020-02-21 13:16:51 [parallel-1] INFO  reactor.Flux.Zip.1 - request(2)
2020-02-21 13:16:51 [parallel-1] INFO  reactor.Flux.Zip.1 - onNext(Person{username='wwhite', firstname='Walter', lastname='White'})
2020-02-21 13:16:51 [parallel-1] INFO  reactor.Flux.Zip.1 - onNext(Person{username='sgoodman', firstname='Saul', lastname='Goodman'})
2020-02-21 13:16:51 [parallel-1] INFO  reactor.Flux.Zip.1 - onComplete()
         */
    }

//========================================================================================
    /*
    If you want to perform custom actions without really modifying the elements in the sequence, you can use the "side effect" methods that start with doOn.

For example, if you want to print "Starting:" upon subscription, use doOnSubscribe.

Each doOn method takes a relevant callback representing the custom action for the corresponding event.

Note that you should not block or invoke operations with latency in these callbacks (which is also true of other
operator callbacks like map): it's more for quick operations.

Go ahead an modify the first two methods in this exercise in order to get some insight into their sequences using log and doOnXXX.
     */
    // TODO Return a Flux with all users stored in the repository that prints "Starring:" on subscribe, "firstname
    //  lastname" for all values and "The end!" on complete
    Flux<User> fluxWithDoOnPrintln() {
        return Flux.from(repository.findAll())
                .doOnSubscribe(user -> {
                    System.out.println("Starring: ");
                })
                .doOnEach(userSignal -> {
                    System.out.println(userSignal.get().getFirstname() + " " + userSignal.get().getLastname());
                })
                .doOnComplete(() -> {
                    System.out.println("The end!");
                });
    }
}
