package be.moens.schemer.utils.api.trackers;

import dev.twilite.game.Game;
import dev.twilite.game.wrapper.Npc;

public record ActionEvent(int opcode, int id, int itemId, boolean npcIdInsteadOfIndex) {
  public ActionEvent(int opcode, int id, int itemId) {
    this(opcode, id, itemId, false);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof ActionEvent other)) {
      return false;
    }

    if (npcIdInsteadOfIndex && !other.npcIdInsteadOfIndex) {
      return opcode == other.opcode && id == other.getIdFromIndex() && itemId == other.itemId;
    } else if (!npcIdInsteadOfIndex && other.npcIdInsteadOfIndex) {
      return opcode == other.opcode && getIdFromIndex() == other.id && itemId == other.itemId;
    } else {
      return opcode == other.opcode && id == other.id && itemId == other.itemId;
    }
  }

  public int getIdFromIndex() {
    return Game.npcs().index(id).first().map(Npc::id).orElse(id);
  }
}
