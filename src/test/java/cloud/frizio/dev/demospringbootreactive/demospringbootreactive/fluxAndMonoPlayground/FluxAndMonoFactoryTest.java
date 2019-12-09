package cloud.frizio.dev.demospringbootreactive.demospringbootreactive.fluxAndMonoPlayground;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoFactoryTest {

  List<String> namesList = Arrays.asList("Al", "Bob", "Carl", "Dan");
  
  String[] namesArray = new String[] {"Al", "Bob", "Carl", "Dan"};

  @Test
  public void fluxWithIterable() {
    Flux<String> namesFlux = Flux.fromIterable(this.namesList);
    StepVerifier.create(namesFlux.log())
      .expectNext("Al", "Bob", "Carl", "Dan")
      .verifyComplete();
  }

  @Test
  public void fluxWithArray() {
    Flux<String> namesFlux = Flux.fromArray(this.namesArray);
    StepVerifier.create(namesFlux.log())
      .expectNext("Al", "Bob", "Carl", "Dan")
      .verifyComplete();
  }

  @Test
  public void fluxWithStream() {
    Flux<String> namesFlux = Flux.fromStream(this.namesList.stream());
    StepVerifier.create(namesFlux.log())
      .expectNext("Al", "Bob", "Carl", "Dan")
      .verifyComplete();
  }

  @Test
  public void monoUsingjustOrEmpty() {
    Mono<String> stringMono = Mono.justOrEmpty(null);
    StepVerifier.create(stringMono.log())
      .verifyComplete();
  }
  
  @Test
  public void monoUsingSupplier() {
    Supplier<String> stringSupplier = () -> "Alice";
    Mono<String> stringMono = Mono.fromSupplier(stringSupplier);
    StepVerifier.create(stringMono.log())
      .expectNext("Alice")
      .verifyComplete();
  }

  @Test
  public void fluxWithRange() {
    Flux<Integer> integerFlux = Flux.range(1, 5);
    StepVerifier.create(integerFlux.log())
      .expectNext(1, 2, 3, 4, 5)
      .verifyComplete();
  }

}