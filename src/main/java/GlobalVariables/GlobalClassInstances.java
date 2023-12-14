package GlobalVariables;

import Utility.Timer;

public class GlobalClassInstances {

    //GLOBAL CLASS INSTANCES
    public Timer globalTimer;

    //Non-multi thread Singleton implementation
    private static GlobalClassInstances instance = null;
    private GlobalClassInstances()
    {
        this.globalTimer = new Timer();
    }
    public static GlobalClassInstances getInstance()
    {
        if(instance == null)
            instance = new GlobalClassInstances();
        return instance;
    }
    //GETTERS
    public Timer getGlobalTimer(){return this.globalTimer;}
}
