import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactiveMerge {
    //========================================================================================
    /*
     * On this first exercise we will begin by merging elements of two Flux as soon as they arrive. The caveat here is
     * that values from flux1 arrive with a delay, so in the resulting Flux we start seeing values from flux2 first.
     */
    // TODO Merge flux1 and flux2 values with interleave
    Flux<User> mergeFluxWithInterleave(Flux<User> flux1, Flux<User> flux2) {
            return flux1.mergeWith(flux2);
    }

    //========================================================================================
    /*
    But if we want to keep the order of sources, we can use the concat operator. Concat will wait for flux1 to
    complete before it can subscribe to flux2, ensuring that all the values from flux1 have been emitted, thus
    preserving an order corresponding to the source.
     */
    // TODO Merge flux1 and flux2 values with no interleave (flux1 values and then flux2 values)
    Flux<User> mergeFluxWithNoInterleave(Flux<User> flux1, Flux<User> flux2) {
        return Flux.concat(flux1, flux2);
    }

//========================================================================================

    // TODO Create a Flux containing the value of mono1 then the value of mono2
    Flux<User> createFluxFromMultipleMono(Mono<User> mono1, Mono<User> mono2) {
        return Flux.concat(mono1, mono2);
    }

}
