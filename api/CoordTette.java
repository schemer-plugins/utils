package be.moens.schemer.utils.api;

import dev.twilite.game.common.Coord;
import dev.twilite.game.facade.Avatar;
import dev.twilite.game.navigation.Navigation;

public class CoordTette {
  public static Coord getNearest(Coord... coords) {
    Coord shortest = null;
    int shortestDistance = Integer.MAX_VALUE;

    for (Coord coord : coords) {
      if (Avatar.distanceTo(coord) < shortestDistance) {
        shortestDistance = Avatar.distanceTo(coord);
        shortest = coord;
      }
    }

    return shortest;
  }

  public static Coord getFarthest(Coord... coords) {
    Coord farthest = null;
    int farthestDistance = Integer.MIN_VALUE;

    for (Coord coord : coords) {
      if (Avatar.distanceTo(coord) > farthestDistance) {
        farthestDistance = Avatar.distanceTo(coord);
        farthest = coord;
      }
    }

    return farthest;
  }

  public static boolean isAtOrMovingTo(Coord coord) {
    return Avatar.coord().equals(coord) || Navigation.mapFlag(coord);
  }
}
