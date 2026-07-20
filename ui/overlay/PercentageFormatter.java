package be.moens.schemer.utils.ui.overlay;

import dev.twilite.client.ui.overlay.OverlayFormatter;
import java.text.DecimalFormat;

public class PercentageFormatter implements OverlayFormatter {
  public static class Percentage {
    private final int precision;
    private final double percentage;

    public Percentage(int part, int whole) {
      this(part, whole, 2);
    }

    public Percentage(int part, int whole, int precision) {
      if (part < 0 || whole <= 0) {
        percentage = 0;
      } else {
        percentage = (double) part * 100 / whole;
      }
      this.precision = precision;
    }

    public String format() {
      StringBuilder pattern = new StringBuilder("#");

      if (precision > 0) {
        pattern.append(".");
        pattern.repeat("0", precision);
      }

      return new DecimalFormat(pattern.toString()).format(percentage);
    }
  }

  @Override
  public String format(Object value) {
    if (value instanceof Percentage) {
      return ((Percentage) value).format() + "%";
    }

    DecimalFormat df = new DecimalFormat("#.00");
    try {
      return df.format(value) + "%";
    } catch (IllegalArgumentException e) {
      return "NaN";
    }
  }
}
