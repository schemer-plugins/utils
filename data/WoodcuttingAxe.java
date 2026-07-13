package be.moens.schemer.utils.data;

import dev.twilite.game.facade.EquipmentSlot;
import dev.twilite.game.id.ItemId;
import dev.twilite.game.loadout.LoadoutItem;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WoodcuttingAxe {
  BRONZE_AXE(ItemId.BRONZE_AXE, 1, Type.NORMAL),
  IRON_AXE(ItemId.IRON_AXE, 1, Type.NORMAL),
  STEEL_AXE(ItemId.STEEL_AXE, 6, Type.NORMAL),
  BLACK_AXE(ItemId.BLACK_AXE, 11, Type.NORMAL),
  MITHRIL_AXE(ItemId.MITHRIL_AXE, 21, Type.NORMAL),
  ADAMANT_AXE(ItemId.ADAMANT_AXE, 31, Type.NORMAL),
  RUNE_AXE(ItemId.RUNE_AXE, 41, Type.NORMAL),
  DRAGON_AXE(ItemId.DRAGON_AXE, 61, Type.NORMAL),
  CRYSTAL_AXE(ItemId.CRYSTAL_AXE, 71, Type.NORMAL),
  GILDED_AXE(ItemId.TRAIL_GILDED_AXE, 41, Type.NORMAL),
  INFERNAL_AXE(ItemId.INFERNAL_AXE, 61, Type.NORMAL),
  _3RD_AGE_AXE(ItemId._3A_AXE, 61, Type.NORMAL),
  BRONZE_FELLING_AXE(ItemId.BRONZE_AXE_2H, 1, Type.FELLING),
  IRON_FELLING_AXE(ItemId.IRON_AXE_2H, 1, Type.FELLING),
  STEEL_FELLING_AXE(ItemId.STEEL_AXE_2H, 6, Type.FELLING),
  BLACK_FELLING_AXE(ItemId.BLACK_AXE_2H, 11, Type.FELLING),
  MITHRIL_FELLING_AXE(ItemId.MITHRIL_AXE_2H, 21, Type.FELLING),
  ADAMANT_FELLING_AXE(ItemId.ADAMANT_AXE_2H, 31, Type.FELLING),
  RUNE_FELLING_AXE(ItemId.RUNE_AXE_2H, 41, Type.FELLING),
  DRAGON_FELLING_AXE(ItemId.DRAGON_AXE_2H, 61, Type.FELLING),
  CRYSTAL_FELLING_AXE(ItemId.CRYSTAL_AXE_2H, 71, Type.FELLING),
  _3RD_AGE_FELLING_AXE(ItemId._3A_AXE_2H, 61, Type.FELLING),
  ;

  public enum Type {
    NORMAL,
    FELLING,
    ;
  }

  private final int id;
  private final int requiredLevel;
  private final Type type;

  /**
   * Returns a list of axes sorted by required level, high to low.
   *
   * @return List of axes sorted by required level, high to low
   */
  public static List<WoodcuttingAxe> getSortedAxes() {
    return Arrays.stream(WoodcuttingAxe.values())
        .sorted(Comparator.comparingInt(WoodcuttingAxe::getRequiredLevel).reversed())
        .toList();
  }

  /**
   * Returns a list of axes sorted by required level, high to low.
   *
   * @param type Normal or felling
   * @return List of axes sorted by required level, high to low
   */
  public static List<WoodcuttingAxe> getSortedAxes(Type type) {
    return Arrays.stream(WoodcuttingAxe.values())
        .filter(axe -> axe.getType() == type)
        .sorted(Comparator.comparingInt(WoodcuttingAxe::getRequiredLevel).reversed())
        .toList();
  }

  public static int[] getIds() {
    return getSortedAxes().stream().mapToInt(WoodcuttingAxe::getId).toArray();
  }

  public static int[] getIds(Type type) {
    return getSortedAxes(type).stream().mapToInt(WoodcuttingAxe::getId).toArray();
  }

  public static LoadoutItem getAxe() {
    return LoadoutItem.of(getIds()).slot(EquipmentSlot.MAINHAND).amount(1).build();
  }

  public static LoadoutItem getAxe(Type type) {
    return LoadoutItem.of(getIds(type)).slot(EquipmentSlot.MAINHAND).amount(1).build();
  }

  public static WoodcuttingAxe getAxeById(int id) {
    return Arrays.stream(WoodcuttingAxe.values())
        .filter(axe -> axe.getId() == id)
        .findFirst()
        .orElse(null);
  }
}
