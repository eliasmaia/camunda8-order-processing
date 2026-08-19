package com.elias.orderprocessing.workers;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificationWorker {

    private static final Logger log = LoggerFactory.getLogger(NotificationWorker.class);

    @JobWorker(type = "notification-worker", autoComplete = true)
    public void handleNotification(@Variable String riskScore) {
        log.info("[NotificationWorker] Sending confirmation email. Risk level: {}", riskScore);
    }
}