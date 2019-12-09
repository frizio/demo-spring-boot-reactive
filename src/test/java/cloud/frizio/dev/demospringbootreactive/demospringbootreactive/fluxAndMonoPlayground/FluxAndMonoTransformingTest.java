package cloud.frizio.dev.demospringbootreactive.demospringbootreactive.fluxAndMonoPlayground;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoTransformingTest {

  List<String> namesList = Arrays.asList("Al", "Bob", "Carl", "Dan");

  @Test
  public void transformUsingMap1() {
    Flux<String> mappedFlux = Flux.fromIterable(this.namesList)
      .map( s -> s.toUpperCase() );
    StepVerifier.create(mappedFlux.log())
      .expectNext("AL", "BOB", "CARL", "DAN")
      .verifyComplete();
  }

  @Test
  public void transformUsingMapAndRepeat() {
    Flux<Integer> namesLengthFlux = Flux.fromIterable(this.namesList)
      .map( s -> s.length() )
      .repeat(1);
    StepVerifier.create(namesLengthFlux.log())
      .expectNext(2, 3, 4, 3, 2, 3, 4, 3)
      .verifyComplete();
  }

  @Test
  public void transformUsingMapAndFilter() {
    Flux<Integer> namesLengthFlux = Flux.fromIterable(this.namesList)
      .filter(s -> s.length() <= 3)
      .map( s -> s.length() )
      .repeat(1);
    StepVerifier.create(namesLengthFlux.log())
      .expectNext(2, 3, 3, 2, 3, 3)
      .verifyComplete();
  }
  
}