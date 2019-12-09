package cloud.frizio.dev.demospringbootreactive.demospringbootreactive.fluxAndMonoPlayground;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * FluxFiltering
 */

public class FluxFilteringTest {

  List<String> namesList = Arrays.asList("Al", "Bob", "Carl", "Dan", "Carol", "Carter");

  @Test
  public void fluxWithFilter() {
    Flux<String> namesFilteredFlux = Flux.fromIterable(this.namesList)
      .filter(s -> s.startsWith("C"));
    StepVerifier.create(namesFilteredFlux.log())
      .expectNext("Carl", "Carol", "Carter")
      .verifyComplete();
  }

  @Test
  public void fluxWithFilterLenght() {
    Flux<String> namesFilteredFlux = Flux.fromIterable(this.namesList)
      .filter(s -> s.length() <= 3);
    StepVerifier.create(namesFilteredFlux.log())
      .expectNext("Al", "Bob", "Dan")
      .verifyComplete();
  }

}
