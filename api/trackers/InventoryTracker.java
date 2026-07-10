package be.moens.schemer.utils.api.trackers;

import dev.twilite.client.eventbus.Subscribe;
import dev.twilite.client.events.ServerTickEvent;
import dev.twilite.game.facade.Inventory;
import dev.twilite.game.wrapper.Item;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

public class InventoryTracker {
  private final List<InventoryListener> listeners;
  private Item[] snapshotInventory;
  private Logger log;

  private Item[] inventoryToArray() {
    Item[] inventory = new Item[28];

    for (int i = 0; i < 28; i++) {
      inventory[i] = Inventory.items().slot(i).first().orElse(null);
    }

    return inventory;
  }

  public InventoryTracker(Logger log) {
    this.log = log;
    listeners = new ArrayList<>();
    snapshotInventory = inventoryToArray();
  }

  @Subscribe(priority = Integer.MAX_VALUE)
  private void onServerTick(ServerTickEvent event) {
    if (snapshotInventory == null) {
      snapshotInventory = inventoryToArray();
    }

    Item[] currentInventory = inventoryToArray();

    for (int i = 0; i < 28; i++) {
      Item currentItem = currentInventory[i];
      Item snapshotItem = snapshotInventory[i];

      if (currentItem != null && snapshotItem != null) {
        if (currentItem.id() != snapshotItem.id()) {
          int amount = currentItem.stackable() ? currentItem.stackSize() : 1;
          notifyListeners(
              new InventoryChangedEvent(
                  InventoryEventType.ADDED, currentItem.id(), amount, currentItem.name()));

          amount = snapshotItem.stackable() ? snapshotItem.stackSize() : 1;
          notifyListeners(
              new InventoryChangedEvent(
                  InventoryEventType.REMOVED, snapshotItem.id(), amount, snapshotItem.name()));
        } else if (currentItem.stackable()) {
          int stackSizeDelta = currentItem.stackSize() - snapshotItem.stackSize();

          if (stackSizeDelta > 0) {
            notifyListeners(
                new InventoryChangedEvent(
                    InventoryEventType.ADDED,
                    currentItem.id(),
                    stackSizeDelta,
                    currentItem.name()));
          } else if (stackSizeDelta < 0) {
            notifyListeners(
                new InventoryChangedEvent(
                    InventoryEventType.REMOVED,
                    currentItem.id(),
                    stackSizeDelta,
                    currentItem.name()));
          }
        }
      } else if (currentItem == null && snapshotItem != null) {
        int amount = snapshotItem.stackable() ? snapshotItem.stackSize() : 1;
        notifyListeners(
            new InventoryChangedEvent(
                InventoryEventType.REMOVED, snapshotItem.id(), amount, snapshotItem.name()));
      } else if (currentItem != null) {
        int amount = currentItem.stackable() ? currentItem.stackSize() : 1;
        notifyListeners(
            new InventoryChangedEvent(
                InventoryEventType.ADDED, currentItem.id(), amount, currentItem.name()));
      }
    }

    snapshotInventory = currentInventory;
  }

  public void register(InventoryListener listener) {
    listeners.add(listener);
  }

  public void unregister(InventoryListener listener) {
    listeners.remove(listener);
  }

  private void notifyListeners(InventoryChangedEvent event) {
    for (InventoryListener listener : listeners) {
      listener.onInventoryChanged(event);
    }
  }
}
