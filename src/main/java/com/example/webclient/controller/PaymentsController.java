package com.example.webclient.controller;

import com.example.webclient.model.Payment;
import com.example.webclient.proxy.PaymentsProxy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

/* Send query to current app:
   curl -X POST http://localhost:8080/payment -H "Content-Type: application/json"
   -d "{\"amount\": 500}"
   in the application.properties of the app: name.service.url = http://localhost:9090
   The PaymentService app have to run at port e.g. 9090
   - in it application.properties: server.port=9090*/
@RestController
public class PaymentsController {
    private final PaymentsProxy paymentsProxy;

    public PaymentsController(PaymentsProxy paymentsProxy) {
        this.paymentsProxy = paymentsProxy;
    }

    @PostMapping("/payment")
    public Mono<Payment> createPayment(@RequestBody Payment payment) {
        String requestId = UUID.randomUUID().toString();
        return paymentsProxy.createPayment(requestId, payment);
    }
}
