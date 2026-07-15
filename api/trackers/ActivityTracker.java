package be.moens.schemer.utils.api.trackers;

import dev.twilite.client.eventbus.Subscribe;
import dev.twilite.client.events.DoActionEvent;
import dev.twilite.client.events.ServerTickEvent;
import dev.twilite.game.Game;
import dev.twilite.game.facade.Avatar;
import dev.twilite.game.menu.ActionType;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;

public class ActivityTracker implements ExperienceListener, InventoryListener {
  @Getter private Activity currentActivity;
  @Getter private Activity previousActivity;
  @Getter private int lastActionTick;
  @Getter private int lastExperienceTick;
  @Getter private int lastInventoryChangeTick;
  @Getter @Setter private int lastActionTimeout = 5;
  private final Logger log;
  private final Map<ActionEvent, Activity> actionEventActivityMap;

  public ActivityTracker(Logger log) {
    Trackers.getExperienceTracker().register(this);
    Trackers.getInventoryTracker().register(this);
    currentActivity = Activity.IDLE;
    this.log = log;
    actionEventActivityMap = new HashMap<>();
  }

  public void mapActionEventToActivity(ActionEvent actionEvent, Activity activity) {
    actionEventActivityMap.put(actionEvent, activity);
  }

  public void mapActionEventToActivity(
      ActionType actionType, int id, int itemId, Activity activity) {
    mapActionEventToActivity(new ActionEvent(actionType.opcode(), id, itemId), activity);
  }

  public void mapActionEventToActivity(
      ActionType actionType, int id, int itemId, boolean npcIdInsteadOfIndex, Activity activity) {
    mapActionEventToActivity(
        new ActionEvent(actionType.opcode(), id, itemId, npcIdInsteadOfIndex), activity);
  }

  @Subscribe(priority = Integer.MAX_VALUE - 1)
  private void onDoActionEvent(DoActionEvent event) {
    ActionEvent actionEvent = new ActionEvent(event.opcode(), event.id(), event.itemId());

    Activity activity = actionEventActivityMap.get(actionEvent);
    if (activity != null) {
      changeActivity(activity);
    }
  }

  @Subscribe(priority = Integer.MAX_VALUE - 1)
  private void onServerTickEvent(ServerTickEvent event) {
    checkActivityTimeout();
  }

  public void cleanUp() {
    actionEventActivityMap.clear();
    Trackers.getExperienceTracker().unregister(this);
    Trackers.getInventoryTracker().unregister(this);
  }

  public void changeActivity(Activity activity) {
    if (activity != Activity.IDLE) {
      lastActionTick = Game.tick();
    }

    if (currentActivity == activity) {
      return;
    }

    if (currentActivity != Activity.IDLE) {
      previousActivity = currentActivity;
      log.debug("Changed previous activity to {} at tick {}", previousActivity, Game.tick());
    }

    currentActivity = activity;
    log.debug("Changed activity to {} at tick {}", currentActivity, Game.tick());
  }

  public boolean isCurrentActivity(Activity activity) {
    return isCurrentActivityAnyOf(activity);
  }

  public boolean isCurrentActivityAnyOf(Activity... activities) {
    for (Activity activity : activities) {
      if (currentActivity == activity) {
        return true;
      }
    }

    return false;
  }

  public boolean isPreviousActivity(Activity activity) {
    return isPreviousActivityAnyOf(activity);
  }

  public boolean isPreviousActivityAnyOf(Activity... activities) {
    for (Activity activity : activities) {
      if (previousActivity == activity) {
        return true;
      }
    }

    return false;
  }

  @Override
  public void onExperienceGained(ExperienceGainedEvent event) {
    lastExperienceTick = Game.tick();
  }

  @Override
  public void onInventoryChanged(InventoryChangedEvent event) {
    lastInventoryChangeTick = Game.tick();
  }

  private void checkActivityTimeout() {
    if (currentActivity == Activity.IDLE || Avatar.animating() || Avatar.moving()) {
      return;
    }

    int currentTick = Game.tick();

    if (currentTick - lastExperienceTick < lastActionTimeout
        || currentTick - lastInventoryChangeTick < lastActionTimeout) {
      return;
    }

    if (currentTick - lastActionTick >= lastActionTimeout) {
      changeActivity(Activity.IDLE);
    }
  }
}
