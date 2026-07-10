package be.moens.schemer.utils.api.trackers;

public interface InventoryListener {
  void onInventoryChanged(InventoryChangedEvent event);
}
