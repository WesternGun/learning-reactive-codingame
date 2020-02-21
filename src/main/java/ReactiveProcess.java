import domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactiveProcess {

    User capitalizeUser(User u) {
        return new User(u.getUsername().toUpperCase(), u.getFirstname().toUpperCase(),
                u.getLastname().toUpperCase());
    }

    Mono<User> asyncCapitalizeUser(User u) {
        return Mono.just(capitalizeUser(u));
    }
    /*
    * In the first place, we will capitalize a String. Since this is a simple 1-1 transformation with no expected latency,
    *  we can use the map operator with a lambda transforming a T into a U.
    * */
    // TODO Capitalize the user username, firstname and lastname
    Mono<User> capitalizeOne(Mono<User> mono) {
        return mono.map(this::capitalizeUser);
    }

//========================================================================================

    // TODO Capitalize the users username, firstName and lastName
    Flux<User> capitalizeMany(Flux<User> flux) {
        return flux.map(this::capitalizeUser);
    }

    /*
    * Now imagine that we have to call a webservice to capitalize our String. This new call can have latency so we cannot
    * use the synchronous map anymore. Instead, we want to represent the asynchronous call as a Flux or Mono, and use a
    * different operator: flatMap.
    *
    * flatMap takes a transformation Function that returns a Publisher<U> instead of a U. This publisher represents
    * the asynchronous transformation to apply to each element. If we were using it with map, we'd obtain a stream of
    * Flux<Publisher<U>>. Not very useful.

    * But flatMap on the other hand knows how to deal with these inner publishers: it will subscribe to them then
    * merge all of them into a single global oKutput, a much more useful Flux<U>. Note that if values from inner
    * publishers
    * arrive at different times, they can interleave in the resulting Flux.
    * */
//========================================================================================

    // TODO Capitalize the users username, firstName and lastName using #asyncCapitalizeUser
    Flux<User> asyncCapitalizeMany(Flux<User> flux) {
        return flux.flatMap(this::asyncCapitalizeUser); // flatten the Flux<Mono<domain.User>> to Flux<domain.User>
    }



}
