package GlobalVariables;

public class Options {
    //TIMER
    public static final String timerOption = "-t";
    private static boolean timerOptionToggled = false;
    public static void startGlobalTimer()
    {
        timerOptionToggled = !timerOptionToggled;
        GlobalClassInstances.getInstance().getGlobalTimer().start();
    }
    public static boolean isTimerToggled(){return timerOptionToggled;}

}
