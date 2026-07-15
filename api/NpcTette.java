package be.moens.schemer.utils.api;

import dev.twilite.game.facade.Avatar;
import dev.twilite.game.menu.MenuOption;
import dev.twilite.game.wrapper.Npc;
import java.util.function.Predicate;

public class NpcTette {
  public static Predicate<Npc> optionFilter(String option) {
    return npc -> {
      for (MenuOption menuOption : npc.options()) {
        if (menuOption.text().equalsIgnoreCase(option)) {
          return true;
        }
      }

      return false;
    };
  }

  public static Predicate<Npc> distanceFilter(int distance) {
    return npc -> Avatar.distanceTo(npc) < distance;
  }
}
