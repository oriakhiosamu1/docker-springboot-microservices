package com.oros.inventory_service.config;

import com.oros.inventory_service.model.Inventory;
import com.oros.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryDataLoader implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;

    @Override
    public void run(String... args) {
        if (inventoryRepository.count() > 0) {
            log.info("Inventory data already present, skipping seed");
            return;
        }

        Inventory item1 = new Inventory();
        item1.setSkuCode("iphone_15");
        item1.setQuantity(100);

        Inventory item2 = new Inventory();
        item2.setSkuCode("pixel_8");
        item2.setQuantity(0);

        inventoryRepository.saveAll(java.util.List.of(item1, item2));

        log.info("Inventory seed data loaded");
    }
}