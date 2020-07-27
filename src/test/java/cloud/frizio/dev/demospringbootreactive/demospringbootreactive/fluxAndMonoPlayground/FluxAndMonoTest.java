package cloud.frizio.dev.demospringbootreactive.demospringbootreactive.fluxAndMonoPlayground;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

  @Test
  public void helloMono() {
    // Mono.just("Project").map(s -> s.concat(" Reactor")).subscribe(System.out::println);
    Mono<String> stringFlux = Mono.just("Project")
                                    .map(s -> s.concat(" Flux"));
    stringFlux.subscribe(
      System.out::println,
      (error) -> System.err.println(error),
      () -> System.out.println("Stream completed!!!!!!")
    );
  }


  @Test
  public void helloFlux() {
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                                    .map(s -> s.concat(" Flux"));
    stringFlux.subscribe(
      System.out::println,
      (error) -> System.err.println(error),
      () -> System.out.println("Stream completed!!!!!!")
    );
  }

  @Test
  public void fluxTest() {
    
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot")
      .concatWith(Flux.just("Reactive Spring"))
      /* .concatWith(Flux.error(new RuntimeException("Exception occourred"))) */
      /* .concatWith(Flux.just("After error")) */
      .map(s -> s.concat(" Flux"))
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
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot")
      .concatWith(Flux.just("Reactive Spring"))
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
      //.expectError(RuntimeException.class)
      .expectErrorMessage("Exception occourred")
      .verify()
    ;
  }

  @Test
  public void FluxStreamElementCount() {
    Flux<String> stringFlux = Flux.just("Spring Framework", "Spring Boot", "Reactive Spring")
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

  @Test
  public void MonoStreamWithError() {
    StepVerifier.create( Mono.error(new StackOverflowError("Exception occour")).log() )
      .expectError(StackOverflowError.class)
      .verify();
  }



}