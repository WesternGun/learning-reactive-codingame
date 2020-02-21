package repository;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Copied from <a href="https://github.com/reactor/lite-rx-api-hands-on/">lite rx api repo</a>.
 * @param <T>
 */
public interface ReactiveRepository<T> {

    Mono<Void> save(Publisher<T> publisher);

    Mono<T> findFirst();

    Flux<T> findAll();

    Mono<T> findById(String id);
}
