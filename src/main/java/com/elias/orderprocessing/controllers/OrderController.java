package com.elias.orderprocessing.controllers;


import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private final ZeebeClient zeebeClient;

    public OrderController(ZeebeClient zeebeClient){
        this.zeebeClient = zeebeClient;
    }

    @PostMapping
    public ResponseEntity<String> startOrder(@RequestBody OrderRequest request) {
        var event = zeebeClient.newCreateInstanceCommand()
            .bpmnProcessId("order-processing-process")
            .latestVersion()
            .variables(request)
            .send()
            .join();

        return ResponseEntity.ok("Process succesfully started! Instance Key: " + event.getProcessInstanceKey());
    }
}
