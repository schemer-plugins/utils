package be.moens.schemer.utils.api.trackers;

import dev.twilite.client.eventbus.Subscribe;
import dev.twilite.client.events.ServerTickEvent;
import dev.twilite.game.facade.Stat;
import dev.twilite.game.facade.Stats;
import java.util.HashMap;
import java.util.Map;

public class ExperienceTracker {
  private final Map<ExperienceListener, Stat[]> listeners;
  private final Map<Stat, Integer> lastExperienceMap;

  public ExperienceTracker() {
    listeners = new HashMap<>();
    lastExperienceMap = new HashMap<>();

    for (Stat skill : Stat.values()) {
      lastExperienceMap.put(skill, Stats.exp(skill));
    }
  }

  @Subscribe(priority = Integer.MAX_VALUE)
  private void onServerTick(ServerTickEvent event) {
    for (Stat skill : Stat.values()) {
      int lastExperience = lastExperienceMap.get(skill);
      int currentExperience = Stats.exp(skill);

      if (currentExperience > lastExperience) {
        lastExperienceMap.put(skill, currentExperience);

        // only notify listeners if lastExperience is not 0 in case it was initialized while logged
        // out
        if (lastExperience != 0) {
          notifyListeners(new ExperienceGainedEvent(skill, currentExperience - lastExperience));
        }
      }
    }
  }

  public void register(ExperienceListener listener) {
    register(listener, new Stat[] {});
  }

  public void register(ExperienceListener listener, Stat... skills) {
    listeners.put(listener, skills);
  }

  public void unregister(ExperienceListener listener) {
    listeners.remove(listener);
  }

  private void notifyListeners(ExperienceGainedEvent event) {
    for (Map.Entry<ExperienceListener, Stat[]> entry : listeners.entrySet()) {
      if (entry.getValue() == null || entry.getValue().length == 0) {
        entry.getKey().onExperienceGained(event);
      } else {
        for (Stat skill : entry.getValue()) {
          if (skill == event.skill()) {
            entry.getKey().onExperienceGained(event);
            break;
          }
        }
      }
    }
  }
}
