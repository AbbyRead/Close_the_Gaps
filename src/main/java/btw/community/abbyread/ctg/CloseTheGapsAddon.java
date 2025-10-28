package btw.community.abbyread.ctg;
import btw.AddonHandler;
import btw.BTWAddon;

public class CloseTheGapsAddon extends BTWAddon {
    @SuppressWarnings("unused")
    private static CloseTheGapsAddon instance;

    public CloseTheGapsAddon() {
        super();
    }

    @Override
    public void initialize() {
        AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
    }
}