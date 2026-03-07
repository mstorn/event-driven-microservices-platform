package com.mstorn.platform.inventory.service;

import com.mstorn.platform.inventory.event.OrderCreatedEvent;
import com.mstorn.platform.inventory.messaging.OrderEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    public void reserveStock(OrderCreatedEvent event) {
        log.info("Reserving stock for order {} (qty={})",
                event.orderId(), event.quantity());

        // später:
        // DB prüfen
        // Bestand reservieren
        // Event "stock.reserved" senden
    }
}