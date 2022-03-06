import java.util.Arrays;
import java.io.*;
import java.util.Arrays;


import static java.util.Collections.replaceAll;

public class Test {
    public static void main(String[] args) {
        String s = "TheCowJumpedOverTheMoon";
        StringVersion shifter = new StringVersion(StringVersion.convertStringToBinary(s).length(), 7);
        shifter.setSeed(s);
        shifter.shift();
        shifter.generate(4);
    }
}
