package cloud.frizio.dev.demospringbootreactive.demospringbootreactive.fluxAndMonoPlayground;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

  @Test
  public void fluxTest() {
    
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
      /*.concatWith(Flux.error(new RuntimeException("Exception occourred"))) */
      /* .map(s -> s.concat(" Flux")) */
      .concatWith(Flux.just("After error"))
      .log()
      ;
    
    stringFlux.subscribe(
      System.out::println,
      (error) -> System.err.println(error),
      () -> System.out.println("Stream completed!!!!!!")
    );

    }

  @Test
  public void FluxStreamWithoutError() {
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
      .log();
    StepVerifier.create(stringFlux)
      .expectNext("Spring")
      .expectNext("Spring Boot")
      .expectNext("Reactive Spring")
      .verifyComplete()     // also perform subscription
    ;
  }

  @Test
  public void FluxStreamWithError() {
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
      .concatWith(Flux.error(new RuntimeException("Exception occourred")))
      .log();
    StepVerifier.create(stringFlux)
      .expectNext("Spring", "Spring Boot", "Reactive Spring")
      .expectError(RuntimeException.class)
      //.expectErrorMessage("Exception occourred")
      .verify()
    ;
  }

  @Test
  public void FluxStreamElementCount() {
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
      .log();
    StepVerifier.create(stringFlux)
      .expectNextCount(3)
      .verifyComplete()
    ;
  }

  @Test
  public void MonoStreamWithoutError() {
    Mono<String> stringFlux = Mono.just("Spring")
      .log();
    StepVerifier.create(stringFlux)
      .expectNext("Spring")
      .verifyComplete()     // also perform subscription
    ;
  }


}