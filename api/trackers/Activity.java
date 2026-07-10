package be.moens.schemer.utils.api.trackers;

import lombok.Value;

@Value
public class Activity {
  public static final Activity BANKING = new Activity("Banking");
  public static final Activity DEPOSITING = new Activity("Depositing");
  public static final Activity EATING = new Activity("Eating");
  public static final Activity IDLE = new Activity("Idle");
  public static final Activity WITHDRAWING = new Activity("Withdrawing");

  String name;
}
