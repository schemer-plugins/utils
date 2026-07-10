package be.moens.schemer.utils.api.trackers;

public record InventoryChangedEvent(InventoryEventType type, int amount, int id, String name) {}
