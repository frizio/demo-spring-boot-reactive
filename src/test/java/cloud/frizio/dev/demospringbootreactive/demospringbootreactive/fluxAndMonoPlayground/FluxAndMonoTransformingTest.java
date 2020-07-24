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
    Flux<String> mappedFlux = Flux.fromIterable(this.namesList).map(s -> s.toUpperCase());
    StepVerifier.create(mappedFlux.log()).expectNext("AL", "BOB", "CARL", "DAN").verifyComplete();
  }

  @Test
  public void transformUsingMapAndRepeat() {
    Flux<Integer> namesLengthFlux = Flux.fromIterable(this.namesList).map(s -> s.length()).repeat(1);
    StepVerifier.create(namesLengthFlux.log()).expectNext(2, 3, 4, 3, 2, 3, 4, 3).verifyComplete();
  }

  @Test
  public void transformUsingMapAndFilter() {
    Flux<Integer> namesLengthFlux = Flux.fromIterable(this.namesList).filter(s -> s.length() <= 3).map(s -> s.length())
        .repeat(1);
    StepVerifier.create(namesLengthFlux.log()).expectNext(2, 3, 3, 2, 3, 3).verifyComplete();
  }

  @Test
  public void transformUsingFlaMap() {
    List<String> letters = Arrays.asList("A","B","C","D","E","F");
    Flux<String> stringFlux = Flux.fromIterable(letters)
      .flatMap(
        // Use case: call db or external service for each flux
        s -> {
          return Flux.fromIterable(this.convertToList(s));
        }
      )
      .log();
      StepVerifier
        .create(stringFlux)
        .expectNextCount(12)
        .verifyComplete();
  }
  
  @Test
  public void transformUsingFlaMap_usingParallel() {
    List<String> letters = Arrays.asList("A","B","C","D","E","F");
    Flux<String> stringFlux = Flux.fromIterable(letters)
      //.window(2)
      .log();
      StepVerifier
        .create(stringFlux)
        .expectNextCount(12)
        .verifyComplete();
  }

  private List<String> convertToList(String s) {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return Arrays.asList(s, " newValue");
  }

}