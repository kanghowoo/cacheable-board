package com.mide.message;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumingStarter {

    private final MessageConsumerService messageConsumerService;

    @PostConstruct
    public void start() {
        messageConsumerService.consumeAndProcess();
    }
}
