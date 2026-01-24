package api.testdata;

import api.models.Store;

public class TestDataStore {
    public static final Store DEFAULT_ORDER = Store.builder()
            .id(9)
            .petId(1)
            .quantity(3)
            .shipDate("2026-01-23T11:41:44.533Z")
            .status("placed")
            .complete(true)
            .build();
}
