package com.mstorn.platform.inventory.infrastructure.messaging;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProcessedEventStore {

    private final Set<UUID> processedEvents = ConcurrentHashMap.newKeySet();

    public boolean alreadyProcessed(UUID eventId) {
        return processedEvents.contains(eventId);
    }

    public void markProcessed(UUID eventId) {
        processedEvents.add(eventId);
    }
}