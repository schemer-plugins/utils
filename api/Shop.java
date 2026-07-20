package be.moens.schemer.utils.api;

import dev.twilite.game.Interfaces;
import dev.twilite.game.id.InterfaceId;
import dev.twilite.game.stream.ItemView;
import dev.twilite.game.wrapper.Item;
import java.util.Optional;
import java.util.stream.Stream;

public class Shop {
  public static boolean isOpen() {
    return Interfaces.visible(InterfaceId.Shopmain.FRAME);
  }

  public static void close() {
    Interfaces.component(InterfaceId.Shopmain.FRAME)
        .ifPresent(
            i ->
                i.dynamicChildren()
                    .forEach(
                        c -> {
                          if (c.option("close")) {
                            c.interact("close");
                          }
                        }));
  }

  public static ItemView items() {
    if (Interfaces.component(InterfaceId.Shopmain.ITEMS).isEmpty()) {
      return new ItemView(() -> null);
    }

    Stream<Item> items =
        Interfaces.component(InterfaceId.Shopmain.ITEMS).get().dynamicChildren().stream()
            .map(
                c ->
                    new Item(
                        c.itemId(), c.itemStackSize(), c.dynamicIndex(), item -> Optional.of(c)));

    return new ItemView(() -> items);
  }
}
