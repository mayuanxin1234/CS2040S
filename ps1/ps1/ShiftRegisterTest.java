import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;

/**
 * ShiftRegisterTest
 * @author dcsslg
 * Description: set of tests for a shift register implementation
 */
public class ShiftRegisterTest {

    /**
     * Returns a shift register to test.
     *
     * @param size
     * @param tap
     * @return a new shift register
     */
    ILFShiftRegister getRegister(int size, int tap) {

        return new ShiftRegister(size, tap);
    }


    /**
     * Tests shift with simple example.
     */
    @Test
    public void testShift1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = {0, 1, 0, 1, 1, 1, 1, 0, 1};
        r.setSeed(seed);
        System.out.println(toString());
         // System.out.println(r.toString());
        int[] expected = {1, 1, 0, 0, 0, 1, 1, 1, 1, 0};
        for (int i = 0; i < 10; i++) {
            assertEquals(expected[i], r.shift());
        }
    }

    /**
     * Tests generate with simple example.
     */
    @Test
    public void testGenerate1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = {0, 1, 0, 1, 1, 1, 1, 0, 1};
        r.setSeed(seed);
        int[] expected = {6, 1, 7, 2, 2, 1, 6, 6, 2, 3};
        for (int i = 0; i < 10; i++) {
            assertEquals("GenerateTest", expected[i], r.generate(3));
        }
    }

    /**
     * Tests register of length 1.
     */
    @Test
    public void testOneLength() {
        ILFShiftRegister r = getRegister(1, 0);
        int[] seed = {1};
        r.setSeed(seed);
        int[] expected = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
        for (int i = 0; i < 10; i++) {
            assertEquals(expected[i], r.generate(3));
        }
    }

    /**
     * Tests with erroneous seed.
     */
    @Test
    public void testError() {
        ILFShiftRegister r = getRegister(4, 1);
        int[] seed = {1, 0, 0, 0, 1, 1, 0};
        r.setSeed(seed);
        r.shift();
        r.generate(4);
    }


    //The proper response should be "Error! Seed length does not match shiftregister size!"
    //The right way to test is to implement a check in the setSeed method?

    /**
     * Tests with greater tap than size (wrong tap)
     */
    @Test
    public void testTap() {
        ILFShiftRegister r = getRegister(4, 8);
        int[] seed = {1, 0, 0, 0};
        r.setSeed(seed);
    }
    /**
     * Tests with seed of wrong value (not 1 or 0)
     */
    @Test
    public void testWrong() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = {0, 2, 4, 5, 1, 1, 1, 8, 1};
        r.setSeed(seed);
        r.shift();
        r.generate(4);
    }
}


