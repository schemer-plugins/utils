package be.moens.schemer.utils.api;

import dev.twilite.game.facade.Avatar;
import dev.twilite.game.menu.MenuOption;
import dev.twilite.game.wrapper.SceneObject;
import java.util.function.Predicate;

public class ObjectTette {
  public static Predicate<SceneObject> optionFilter(String option) {
    return object -> {
      for (MenuOption menuOption : object.options()) {
        if (menuOption.text().equalsIgnoreCase(option)) {
          return true;
        }
      }

      return false;
    };
  }

  public static Predicate<SceneObject> distanceFilter(int distance) {
    return object -> Avatar.distanceTo(object) < distance;
  }
}
