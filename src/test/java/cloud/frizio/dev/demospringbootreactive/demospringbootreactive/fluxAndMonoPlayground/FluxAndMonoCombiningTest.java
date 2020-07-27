package cloud.frizio.dev.demospringbootreactive.demospringbootreactive.fluxAndMonoPlayground;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoCombiningTest {
  
  @Test
  public void combiningUsingMerge() {

    // Use case: two db call or call to external service..
    Flux<String> flux1 = Flux.just("A", "B", "C");
    Flux<String> flux2 = Flux.just("D", "E", "F");

    Flux<String> combinedFlux = Flux.merge(flux1, flux2);

    StepVerifier.create(combinedFlux.log())
                  .expectSubscription()
                  .expectNext("A", "B", "C", "D", "E", "F")
                  .verifyComplete();
  }

  @Test
  public void combiningUsingMerge_withDelay() {

    Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
    Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));

    Flux<String> combinedFlux = Flux.merge(flux1, flux2);

    StepVerifier.create(combinedFlux.log())
                  .expectSubscription()
                  .expectNextCount(6)
                  //.expectNext("D", "A", "B", "E", "F", "C")
                  .verifyComplete();
  }

  @Test
  public void combiningUsingConcat_withDelay() {

    Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
    Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));

    Flux<String> combinedFlux = Flux.concat(flux1, flux2);

    StepVerifier.create(combinedFlux.log())
                  .expectSubscription()
                  .expectNext("A", "B", "C", "D", "E", "F")
                  .verifyComplete();
  }

  @Test
  public void combiningUsingZip() {

    // Use case: two db call or call to external service..
    Flux<String> flux1 = Flux.just("A", "B", "C");
    Flux<String> flux2 = Flux.just("D", "E", "F");

    Flux<String> combinedFlux = Flux.zip(flux1, flux2, (s1, s2)->s1.concat(s2));

    StepVerifier.create(combinedFlux.log())
                  .expectSubscription()
                  .expectNext("AD", "BE", "CF")
                  .verifyComplete();

  }


}