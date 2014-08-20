import lombok.Data;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.BitSet;

class PrimeSum {
  static Result sumUpTo(final int max) {
    final int sqrt = (int) Math.sqrt(max);
    BitSet isNotPrime = new BitSet(max);
    BigInteger sum = BigInteger.ZERO;
    int last = 0, count = 0;

    for (int i = 2; i <= sqrt; i++) {
      if (!isNotPrime.get(i)) {
        count += 1;
        sum = sum.add(BigInteger.valueOf(i));
        last = i;

        for (int j = 2 * i; j <= max; j += i) {
          isNotPrime.set(j);
        }
      }
    }

    for (int i = sqrt + 1; i <= max; i++) {
      if (!isNotPrime.get(i)) {
        count += 1;
        sum = sum.add(BigInteger.valueOf(i));
        last = i;
      }
    }

    return new Result(count, sum, last);
  }

  public static void main(String... args) {
    int max = 2000000;
    if (args.length > 0) {
      max = Integer.parseInt(args[0]);
    }

    final long startTime = System.currentTimeMillis();
    Result res = sumUpTo(max);
    final long duration = System.currentTimeMillis() - startTime;

    NumberFormat fmt = NumberFormat.getInstance();

    System.out.printf("%s primes <= %s.\nSum: %s.\nLast: %s.\nComputed in %ssec.\n",
            fmt.format(res.getCount()), fmt.format(max), fmt.format(res.getSum()),
            fmt.format(res.getLast()), fmt.format((float) duration / 1000f));
  }

  @Data
  public static class Result {
    private final int count;
    private final BigInteger sum;
    private final int last;
  }
}
