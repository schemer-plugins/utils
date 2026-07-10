package be.moens.schemer.utils.api.trackers;

public record ActionEvent(int opcode, int id, int itemId) {
  @Override
  public boolean equals(Object obj) {
    return obj instanceof ActionEvent
        && opcode == (((ActionEvent) obj).opcode)
        && id == (((ActionEvent) obj).id)
        && itemId == (((ActionEvent) obj).itemId);
  }
}
