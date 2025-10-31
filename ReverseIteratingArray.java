package io.github.jvjdi;

import static java.lang.IO.*;
import static java.lang.System.*;

/// @author oberio.stanley@gmail.com
public class ReverseIteratingArray {
    public static void main(String[] commandLineArguments) {
        println("io.github.jvjdi.ReverseIteratingArray\n");

        var thousandInts = new int[1_000];
        final var LENGTH_OF_THOUSAND_INTS = thousandInts.length;
        long nanoseconds, startTime = nanoTime();
        for (var indexIntoThousandInts = 0; indexIntoThousandInts <
                LENGTH_OF_THOUSAND_INTS; indexIntoThousandInts++) {
            print(thousandInts[indexIntoThousandInts]);
        }
        nanoseconds = nanoTime() - startTime;
        out.printf("%n%,d nanoseconds forward iterating%n", nanoseconds);
        startTime = nanoTime();
        for (var indexIntoThousandInts = LENGTH_OF_THOUSAND_INTS - 1;
                indexIntoThousandInts >= 0; indexIntoThousandInts--) {
            print(thousandInts[indexIntoThousandInts]);
        }
        nanoseconds = nanoTime() - startTime;
        out.printf("%n%,d nanoseconds reverse iterating%n", nanoseconds);
    }
}
