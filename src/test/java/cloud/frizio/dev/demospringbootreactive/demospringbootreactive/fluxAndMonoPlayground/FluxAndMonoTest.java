package cloud.frizio.dev.demospringbootreactive.demospringbootreactive.fluxAndMonoPlayground;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

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

}