package be.moens.schemer.utils.api.trackers;

public record InventoryChangedEvent(InventoryEventType type, int id, int amount, String name) {}
