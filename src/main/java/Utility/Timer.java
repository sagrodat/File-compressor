package Utility;

public class Timer {
    private long startTime;
    private long endTime;
    private long elapsedTime;

    public void start()
    {
        startTime = System.currentTimeMillis();
    }
    public void stop()
    {
        endTime = System.currentTimeMillis();
    }
    public void reset()
    {
        startTime = 0;
        endTime = 0;
        elapsedTime = 0;
    }
    public void printMeasurement()
    {
        elapsedTime = endTime - startTime;
        double secondsWithMilliseconds = elapsedTime / 1000.0;
        System.out.println("Elapsed Time: " + secondsWithMilliseconds + " seconds");
    }

}
