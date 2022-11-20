import java.text.DecimalFormat;

//Följande använder sig av System.nanoTime() i stället för importen av J3DTimer.

public class SleepAcc {
    private static DecimalFormat df;

    public static void main(String args[]) {
        df = new DecimalFormat("0.##"); // 2 dp
        // test various sleep values
        sleepTest(1000);
        sleepTest(500);
        sleepTest(200);
        sleepTest(100);
        sleepTest(50);
        sleepTest(20);
        sleepTest(10);
        sleepTest(5);
        sleepTest(1);
    } // end of main( )

    private static void sleepTest(int delay) {
        long timeStart = System.nanoTime();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
        double timeDiff = ((double) (System.nanoTime() - timeStart)) / (1000000L);
        double err = ((delay - timeDiff) / timeDiff) * 100;
        System.out.println("Slept: " + delay + " ms J3D: " +
                df.format(timeDiff) + " ms err: " +
                df.format(err) + " %");
    }
}
