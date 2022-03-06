///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////

import java.util.Arrays;

/**
 * class ShiftRegister
 * @author
 * Description: implements the ILFShiftRegister interface.
 */
public class ShiftRegister implements ILFShiftRegister {
    ///////////////////////////////////
    // Create your class variables here
    ///////////////////////////////////
    // TODO:
    private int[] shiftregister;
    private int tap = 0;
    private int s;
    ///////////////////////////////////
    // Create your constructor here:
    ///////////////////////////////////
    ShiftRegister(int size, int tap) {
        // TODO:
        if (tap < 0 || tap >= size) {
            System.out.println("Wrong Tap Size!");
            return;
        }  else {
            shiftregister = new int[size];
            this.tap = tap;
            this.s = size;
        }
    }

    ///////////////////////////////////
    // Create your class methods here:
    ///////////////////////////////////
    /**
     * setSeed
     * @param seed
     * Description: Checks if seed has bits other than 1 or 0,
     * if seed length and size of shiftregister matches and sets shiftregister to the
     * seed in the correct order from most significant to least significant bit.
     */
    @Override
    public void setSeed(int[] seed) {
        // TODO: Set shiftregister to specified array position and check if it
        // contains only 1 or 0
        if (seed.length != this.s) {
            System.out.println("Error! Seed length does not match shiftregister size!");
            return;
        } else {
            for (int i = 0; i < seed.length; i++) {
                if (seed[i] != 0 && seed[i] != 1) {
                    System.out.println("Error! Seed can only contain 1 or 0");
                     return;
                } else {
                    shiftregister[i] = seed[seed.length - i - 1];
                }
            }
        }
    }

    /**
     * shift
     * @return Least significant bit (Calculated through XOR of most signicant bit and tap bit)
     * Description: Performs one shift step to shiftregister and returns the least
     * significant bit(FeedBackBit)
     */
    @Override
    public int shift() {
        // TODO:
        int TapBit = shiftregister.length - tap - 1;
        int FeedBackBit = shiftregister[0] ^ shiftregister[TapBit];
        for(int i = 0; i < shiftregister.length - 1; i++) {
            shiftregister[i] = shiftregister[i+1];
        }
        shiftregister[shiftregister.length - 1] = FeedBackBit;
        return FeedBackBit;
    }

    /**
     * generate
     * @param k
     * @return Number obtained by binary of the numbers returned by shift() k times
     * Description: Executes the shift() method k times, storing each value returned and
     * eventually returning a number.
     */
    @Override
    public int generate(int k) {
        // TODO:
        int[] stall = new int[k];
        for(int i = 0; i < k; i ++) {
          stall[i] = shift();
        }
        return toBinary(stall);
    }

    /**
     * Returns the integer representation for a binary int array.
     * @param array
     * @return decimal integer
     */
    private int toBinary(int[] array) {
        // TODO:
        int decimal = 0;
        for(int i = 0; i < array.length; i++) {
            decimal += array[i] * Math.pow(2, array.length - i - 1);
        }
        return decimal;
    }
    @Override
    public String toString() {
        return Arrays.toString(shiftregister);
    }



}

