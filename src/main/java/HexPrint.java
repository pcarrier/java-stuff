import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.runner.CaliperMain;

import java.math.BigInteger;
import java.util.Random;

public class HexPrint extends Benchmark {
  static final char[] hexChars =
          {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

  @Param
  private int bits;

  @Param
  private Method method;
  private byte[] value;

  public static void main(String... args) throws Exception {
    CaliperMain.main(HexPrint.class, args);
  }

  public void timeHexPrint(int reps) {
    while (reps > 0) {
      method.convert(value);
      reps--;
    }
  }

  @Override
  protected void setUp() {
    Random rnd = new Random();
    value = new BigInteger(bits, rnd).toByteArray();
  }

  public enum Method {
    @SuppressWarnings("UnusedDeclaration")
    CUSTOM {
      @Override
      final String convert(final byte[] in) {
        final int length = in.length;
        final char[] out = new char[2 * length];
        for (int i = length - 1; i >= 0; i--) {
          final byte inByte = in[i];
          out[2 * i] = hexChars[(inByte & 0xf0) >>> 4];
          out[2 * i + 1] = hexChars[inByte & 0xf];
        }
        return new String(out);
      }
    },
    @SuppressWarnings("UnusedDeclaration")
    BIGINT {
      @Override
      final String convert(final byte[] in) {
        return new BigInteger(1, in).toString(16);
      }
    },
    @SuppressWarnings("UnusedDeclaration")
    BUILDER {
      @Override
      final String convert(final byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for (final byte b : in) {
          builder.append(String.format("%02x", b));
        }
        return builder.toString();
      }
    },;

    abstract String convert(final byte[] in);
  }
}
