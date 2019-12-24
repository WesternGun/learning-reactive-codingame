import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class ReactiveProcessTest {

    private final ReactiveProcess process = new ReactiveProcess();
    private final User user1 = new User("foo", "bar", "doo");
    private final User user2 = new User("johndoe", "john", "doe");

    @Test
    void capitalizeOne() {
        StepVerifier.create(process.capitalizeOne(Mono.just(user1)))
                .assertNext(result -> {
                    assertThat(result.getFirstname(), is("BAR"));
                    assertThat(result.getLastname(), is("DOO"));
                    assertThat(result.getUsername(), is("FOO"));
                })
                .verifyComplete();
    }

    @Test
    void capitalizeMany() {
        StepVerifier.create(process.capitalizeMany(Flux.just(user1, user2)))
                .assertNext(result -> {
                    assertThat(result.getFirstname(), is("BAR"));
                    assertThat(result.getLastname(), is("DOO"));
                    assertThat(result.getUsername(), is("FOO"));
                })
                .assertNext(result -> {
                    assertThat(result.getFirstname(), is("JOHN"));
                    assertThat(result.getLastname(), is("DOE"));
                    assertThat(result.getUsername(), is("JOHNDOE"));
                })
                .verifyComplete();
    }

    @Test
    void asyncCapitalizeMany() {
        StepVerifier.create(process.asyncCapitalizeMany(Flux.just(user1, user2)))
                .assertNext(result -> {
                    assertThat(result.getFirstname(), is("BAR"));
                    assertThat(result.getLastname(), is("DOO"));
                    assertThat(result.getUsername(), is("FOO"));
                })
                .assertNext(result -> {
                    assertThat(result.getFirstname(), is("JOHN"));
                    assertThat(result.getLastname(), is("DOE"));
                    assertThat(result.getUsername(), is("JOHNDOE"));
                })
                .verifyComplete();
    }
}