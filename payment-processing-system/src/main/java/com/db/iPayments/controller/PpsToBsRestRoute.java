package com.db.iPayments.controller;

import com.db.iPayments.model.Payment;
import com.db.iPayments.service.PaymentValidatorService;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Value;


public class PpsToBsRestRoute extends RouteBuilder {

    @Value("${bs.rest.endpoint}")
    private String bsEndpoint;

    @Override
    public void configure() {

        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest("/payments")
                .post()
                .type(Payment.class)
                .outType(String.class)
                .to("direct:process-payment");

        from("direct:process-payment")
                .log("Received payment request: ${body}")
                .bean(PaymentValidatorService.class, "validate")
                .marshal().json(JsonLibrary.Jackson)
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .toD(bsEndpoint) // Dynamic to support placeholder
                .log("Response from Broker System: ${body}");
        // this way we can call JMS using Apache Camel
        rest("/api/payments")
                .post()
                .type(Payment.class)
                .outType(String.class)
                .to("direct:validate-payment");

        from("direct:validate-payment")
                .bean(PaymentValidatorService.class)
                .marshal().json(JsonLibrary.Jackson)
                .to("jms:queue:brokerQueueForFraudCheck");


    }
}

