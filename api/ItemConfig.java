package be.moens.schemer.utils.api;

import dev.twilite.client.config.Config;
import dev.twilite.client.config.annotation.component.TextField;
import dev.twilite.game.common.Text;
import dev.twilite.game.wrapper.Item;
import java.util.List;
import java.util.function.Predicate;
import lombok.Getter;

@Getter
public class ItemConfig extends Config {
  @TextField(
      key = "id",
      label = "ID",
      tooltip = "Item ID. If specified, takes precedence over name.",
      digit = true)
  private int id = 0;

  @TextField(key = "name", label = "name", tooltip = "Item name, case insensitive.")
  private String name = "";

  public String getName() {
    return Text.standardise(name);
  }

  public static Predicate<Item> itemConfigList(List<ItemConfig> itemConfigList) {
    return item -> {
      for (ItemConfig itemConfig : itemConfigList) {
        if (itemConfig.getId() <= 0) {
          if (itemConfig.getName().equals(Text.standardise(item.name()))) {
            return true;
          }
        } else {
          if (itemConfig.getId() == item.id()) {
            return true;
          }
        }
      }

      return false;
    };
  }
}
