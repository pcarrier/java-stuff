import java.util.Iterator;

public final class XorShift64Star
        implements Iterator<Long>, Iterable<Long> {
  private long x;

  public XorShift64Star(final long seed) {
    reset(seed);
  }

  public XorShift64Star() {
    reset();
  }

  @Override
  public final boolean hasNext() {
    return true;
  }

  @Override
  public final Long next() {
    return get();
  }

  @Override
  public final void remove() {
    throw new UnsupportedOperationException();
  }

  public final long get() {
    long l = x;
    l ^= l >>> 12;
    l ^= l << 25;
    l ^= l >>> 27;
    x = l;
    return l * 2685821657736338717L;
  }

  public final void reset(final long seed) {
    if (seed == 0)
      throw new IllegalArgumentException();
    x = seed;
  }

  public final void reset() {
    final long t = System.currentTimeMillis();
    x = t == 0 ? 1 : t;
  }

  @Override
  public final Iterator<Long> iterator() {
    return this;
  }
}
