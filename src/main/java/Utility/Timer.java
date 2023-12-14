package Utility;

public class Timer {
    private long startTime = - 1;
    private long endTime;
    private long elapsedTime;
    private long offset = 0;

    public void start()
    {
        if(startTime == -1)
            startTime = System.currentTimeMillis();
        else
            offset = System.currentTimeMillis() - endTime;
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
    public void stopAndReset()
    {
        stop();
        reset();
    }
    public void printMeasurement()
    {
        stop();

        elapsedTime = endTime - startTime - offset;
        double secondsWithMilliseconds = elapsedTime / 1000.0;
        System.out.println("Elapsed Time: " + secondsWithMilliseconds + " seconds");

        start();
    }

}
