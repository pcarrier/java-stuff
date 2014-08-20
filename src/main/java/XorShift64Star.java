import static com.google.common.base.Preconditions.checkArgument;

public class XorShift64Star {
  private long x;

  public XorShift64Star(long init) {
    checkArgument(init != 0);
    x = init;
  }

  long next() {
    long l = x;
    l ^= l >>> 12;
    l ^= l << 25;
    l ^= l >>> 27;
    x = l;
    return l * 2685821657736338717L;
  }
}
