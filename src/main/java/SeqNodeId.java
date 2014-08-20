import com.google.caliper.Benchmark;
import com.google.caliper.runner.CaliperMain;

import java.util.Random;

public class SeqNodeId extends Benchmark {
  private static final Random NRAND = new Random(1);
  private static final XorShift64Star XRANDR = new XorShift64Star(1);

  static private char[] idToChars(int id) {
    if (id < 0) {
      final char[] res = {'-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'};
      id = -id;
      for (int pos = 10; id > 0; id /= 10, pos--)
        res[pos] = (char) ('0' + id % 10);
      return res;
    } else {
      final char[] res = {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0'};
      for (int pos = 9; id > 0; id /= 10, pos--)
        res[pos] = (char) ('0' + id % 10);
      return res;
    }
  }

  public static void main(String... args) throws Exception {
    CaliperMain.main(SeqNodeId.class, args);
  }

  public void timeConvert(int reps) {
    while (reps > 0) {
      idToChars((int) XRANDR.next());
      reps--;
    }
  }

  public void timeNativeRandom(int reps) {
    while (reps > 0) {
      NRAND.nextLong();
      reps--;
    }
  }

  public void timeXorRandom(int reps) {
    while (reps > 0) {
      XRANDR.next();
      reps--;
    }
  }

  public void timeBranchingAbs(int reps) {
    while (reps > 0) {
      abs0((int) XRANDR.next());
      reps--;
    }
  }

  public void timeDirectAbs(int reps) {
    while (reps > 0) {
      abs1((int) XRANDR.next());
      reps--;
    }
  }

  private int abs0(int x) {
    if (x < 0)
      return x;
    else
      return x;
  }

  private int abs1(int x) {
    final int neg = x >>> 31;
    return (x + neg) ^ neg;
  }
}
