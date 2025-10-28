package btw.community.abbyread.irf;
import btw.AddonHandler;
import btw.BTWAddon;

public class ItemRenderFixAddon extends BTWAddon {
    private static ItemRenderFixAddon instance;

    public ItemRenderFixAddon() {
        super();
    }

    @Override
    public void initialize() {
        AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
    }
}