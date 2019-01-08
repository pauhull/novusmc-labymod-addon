package de.novusmc.labymod;

import de.novusmc.labymod.listener.ServerMessageListener;
import de.novusmc.labymod.module.NovusCategory;
import de.novusmc.labymod.module.PartyModule;
import de.novusmc.labymod.server.ServerSupport;
import de.novusmc.labymod.util.AddonSettings;
import lombok.Getter;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;

import java.util.List;

/**
 * Created by Paul
 * on 06.01.2019
 *
 * @author pauhull
 */
public class NovusLabyModAddon extends LabyModAddon {

    @Getter
    private static NovusLabyModAddon instance;

    @Getter
    private ServerSupport serverSupport;

    @Getter
    private NovusCategory category;

    @Getter
    private PartyModule partyModule;

    @Getter
    private AddonSettings settings;

    @Override
    public void onEnable() {
        instance = this;

        // misc
        this.serverSupport = new ServerSupport(this);
        this.category = new NovusCategory();
        this.partyModule = new PartyModule(this, serverSupport);

        // listeners
        new ServerMessageListener(this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @Override
    public void loadConfig() {
        this.settings = AddonSettings.load(getConfig());
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        this.settings.fillSettings(list);
    }

}

