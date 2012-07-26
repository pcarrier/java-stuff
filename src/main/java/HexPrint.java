import com.google.caliper.Param;
import com.google.caliper.Runner;
import com.google.caliper.SimpleBenchmark;

import java.math.BigInteger;
import java.util.Random;

public class HexPrint extends SimpleBenchmark {
    @Param
    private int bits;
    @Param
    private Method method;

    private byte[] value;

    public enum Method {
        @SuppressWarnings("UnusedDeclaration")
        BUILDER {
            @Override
            final String convert(final byte[] in) {
                final StringBuilder builder = new StringBuilder();
                for (final byte b : in)
                    builder.append(String.format("%02x", b));
                return builder.toString();
            }
        },
        @SuppressWarnings("UnusedDeclaration")
        BIGINT {
            @Override
            final String convert(final byte[] in) {
                return new BigInteger(1, in).toString(16);
            }
        };

        abstract String convert(final byte[] in);
    }


    public void timePrimitive(int reps) {
        for (int i = 0; i < reps; i++)
            method.convert(value);
    }

    @Override
    protected void setUp() {
        Random rnd = new Random();
        value= new BigInteger(bits, rnd).toByteArray();
    }

    public static void main(String[] args) throws Exception {
        Runner.main(HexPrint.class, args);
    }
}
