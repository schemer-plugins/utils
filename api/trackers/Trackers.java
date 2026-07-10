package be.moens.schemer.utils.api.trackers;

import dev.twilite.client.eventbus.EventBus;
import lombok.Getter;
import org.slf4j.Logger;

public class Trackers {
  private static EventBus eventBus;
  @Getter private static ExperienceTracker experienceTracker;
  @Getter private static InventoryTracker inventoryTracker;
  @Getter private static ActivityTracker activityTracker;

  public static void initialize(EventBus eventBus, Logger log) {
    Trackers.eventBus = eventBus;

    experienceTracker = new ExperienceTracker();
    eventBus.register(experienceTracker);

    inventoryTracker = new InventoryTracker(log);
    eventBus.register(inventoryTracker);

    activityTracker = new ActivityTracker(log);
    eventBus.register(activityTracker);
  }

  public static void cleanUp() {
    eventBus.unregister(activityTracker);
    activityTracker.cleanUp();
    activityTracker = null;

    eventBus.unregister(experienceTracker);
    experienceTracker = null;

    eventBus.unregister(inventoryTracker);
    inventoryTracker = null;
  }
}
