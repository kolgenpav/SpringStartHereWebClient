package com.example.webclient.proxy;

import com.example.webclient.model.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Task
 */
@Component
public class PaymentsProxy {
    private final WebClient webClient;

    /*URL of the PaymentService app*/
    @Value("${name.service.url}")
    private String url;

    public PaymentsProxy(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Call the PaymentService endpoint with WebClient.
     *
     * @param requestId is of request received from the REST controller
     * @param payment   instance received from the REST controller
     * @return Mono<Payment> instance - Publisher - with payment content
     */
    public Mono<Payment> createPayment(String requestId, Payment payment) {
        return webClient.post() //HTTP method is POST
                .uri(url + "/payment")  //URI=http://localhost:9090/payment
                .header("requestId", requestId)
                .body(Mono.just(payment), Payment.class) //Create a Publisher that emits payment while instantiation
                .retrieve() //send the request and obtain response
                .bodyToMono(Payment.class);  //get response content and put it to Mono<Payment>
        // data flow for reactive processing
    }
}
