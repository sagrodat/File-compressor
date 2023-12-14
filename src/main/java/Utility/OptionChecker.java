package Utility;

import GlobalVariables.Options;

public class OptionChecker {
    String [] args;

    public void checkForOptions(String [] args)
    {
        this.args = args;
        if(args.length == 2)
            return;
        if(isOptionChosen(Options.timerOption))
            Options.startGlobalTimer();
    }

    private boolean isOptionChosen(String option)
    {
        for(int i = 2; i < args.length ; i++)
        {
            if(args[i].equals(option))
                return true;
        }
        return false;
    }

}
