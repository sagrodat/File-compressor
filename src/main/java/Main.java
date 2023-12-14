import Compressor.Compressor;
import GlobalVariables.Constants;
import GlobalVariables.GlobalClassInstances;
import GlobalVariables.Options;
import Utility.OptionChecker;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        if(!main.inspectArgs(args))
            return;
        main.processFiles(args);
        main.onFinish();
    }

    public boolean inspectArgs(String [] args)
    {
        if(args.length == 0 || args.length == 1)
        {
            System.out.println(Constants.helpInfo);
            return false;
        }
        OptionChecker optionChecker = new OptionChecker();
        optionChecker.checkForOptions(args);
        return true;
    }
    public void processFiles(String [] args)
    {
        Compressor compressor = new Compressor();
        compressor.performAction(args[0],args[1]);
    }
    public void onFinish()
    {
        if(Options.isTimerToggled())
        {
            GlobalClassInstances.getInstance().getGlobalTimer().printMeasurement();
            GlobalClassInstances.getInstance().getGlobalTimer().stopAndReset();
        }
    }

}