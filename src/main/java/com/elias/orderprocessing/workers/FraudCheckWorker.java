package com.elias.orderprocessing.workers;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FraudCheckWorker {

    private static final Logger log = LoggerFactory.getLogger(FraudCheckWorker.class);
    private static final double FRAUD_THRESHOLD = 1000.0;

    @JobWorker(type = "fraud-check-worker", autoComplete = true)
    public Map<String, Object> handleFraudCheck(@Variable Double orderValue) {
        log.info("[FraudCheckWorker] Analyzing risk for order value: R$ {}", orderValue);

        if (orderValue == null || orderValue <= 0) {
            log.warn("[FraudCheckWorker] Invalid order amount received.");
            return Map.of("isApproved", false, "riskScore", "REJECTED_INVALID_INPUT");
        }

        boolean isApproved = orderValue < FRAUD_THRESHOLD;
        String riskScore = isApproved ? "LOW" : "HIGH";

        log.info("[FraudCheckWorker] Decision made | Approved: {} | Risk Score: {}", isApproved, riskScore);

        return Map.of("isApproved", isApproved, "riskScore", riskScore);
    }
}