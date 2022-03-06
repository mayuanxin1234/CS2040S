import static org.junit.Assert.*;

public class WiFiTest {

    @org.junit.Test
    public void computeDistance() {
        int[] houses = {1,3,10};
        int numAccessPoints = 2;
        assertEquals(1.0, WiFi.computeDistance(houses, numAccessPoints), 0.5);
    }
    @org.junit.Test
    public void computeDistance1() {
        int[] houses = {1, 1,5,7,9,11,15};
        int numAccessPoints = 3;
        assertEquals(2.0, WiFi.computeDistance(houses, numAccessPoints), 0.5);
    }

    @org.junit.Test
    public void computeDistance2() {
        int[] houses = {1, 3, 10};
        int numAccessPoints = 1;
        assertEquals(4.5, WiFi.computeDistance(houses, numAccessPoints), 0.5);
    }
    @org.junit.Test
    public void computeDistance3() {
        int[] houses = {100,1};
        int numAccessPoints = 2;
        assertEquals(0, WiFi.computeDistance(houses, numAccessPoints), 0.5);
    }

    @org.junit.Test
    public void coverable1() {
        int[] houses = {1, 3, 10};
        int numAccessPoints = 2;
        assertTrue(WiFi.coverable(houses, numAccessPoints, 1.0));
    }

    @org.junit.Test
    public void coverable2() {
        int[] houses = {1, 3, 10};
        int numAccessPoints = 2;

        assertFalse(WiFi.coverable(houses, numAccessPoints, 0.5));
    }
    @org.junit.Test
    public void coverable3() {
        int[] houses = {1, 3,5,7,9,11,15};
        int numAccessPoints = 4;

        assertTrue(WiFi.coverable(houses, numAccessPoints, 1));
    }
    @org.junit.Test
    public void coverable4() {
        int[] houses = {};
        int numAccessPoints = 0;

        assertTrue(WiFi.coverable(houses, numAccessPoints, 1));
    }
    @org.junit.Test
    public void coverable5() {
        int[] houses = {1};
        int numAccessPoints = 1;

        assertTrue(WiFi.coverable(houses, numAccessPoints, 1));
    }
    @org.junit.Test
    public void coverable6() {
        int[] houses = {10,1,3};
        int numAccessPoints = 2;

        assertTrue(WiFi.coverable(houses, numAccessPoints, 1));
    }
    @org.junit.Test
    public void coverable7() {
        int[] houses = {1,2,3,4,5,6,7};
        int numAccessPoints = 3;

        assertTrue(WiFi.coverable(houses, numAccessPoints, 1));
    }
    @org.junit.Test
    public void coverable8() {
        int[] houses = {0,1,2,3,4};
        int numAccessPoints = 2;

        assertTrue(WiFi.coverable(houses, numAccessPoints, 1));
    }
    @org.junit.Test
    public void coverable9() {
        int[] houses = {1,1,1,1,1,2,3,4};
        int numAccessPoints = 4;

        assertTrue(WiFi.coverable(houses, numAccessPoints, 0));
    }
    @org.junit.Test
    public void coverable10() {
        int[] houses = {-5,-4,-3,-2,1,2,3,4};
        int numAccessPoints = 4;

        assertTrue(WiFi.coverable(houses, numAccessPoints, 1));
    }
}