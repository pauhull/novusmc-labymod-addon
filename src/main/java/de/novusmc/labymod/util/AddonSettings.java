package de.novusmc.labymod.util;

import com.google.gson.JsonObject;
import de.novusmc.labymod.NovusLabyModAddon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;

import java.util.List;

/**
 * Created by Paul
 * on 08.01.2019
 *
 * @author pauhull
 */
@AllArgsConstructor
public class AddonSettings {

    private static NovusLabyModAddon addon = NovusLabyModAddon.getInstance();

    @Getter
    private boolean showServer, showOnline, showRank, showCoins, showParty;

    public static AddonSettings load(JsonObject config) {
        return new AddonSettings(
                getBoolean(config, "showServer", true),
                getBoolean(config, "showOnline", true),
                getBoolean(config, "showRank", true),
                getBoolean(config, "showCoins", true),
                getBoolean(config, "showParty", true)
        );
    }

    private static boolean getBoolean(JsonObject config, String name, boolean _default) { // default is a java keyword :(
        return config.has(name) ? config.get(name).getAsBoolean() : _default;
    }

    public void fillSettings(List<SettingsElement> subSettings) {
        subSettings.add(new BooleanElement("Momentanen Server anzeigen", addon, new ControlElement.IconData(Material.NETHER_STAR), "showServer", this.showServer));
        subSettings.add(new BooleanElement("Online Spieler anzeigen", addon, new ControlElement.IconData(Material.EMERALD), "showOnline", this.showOnline));
        subSettings.add(new BooleanElement("Rang anzeigen", addon, new ControlElement.IconData(Material.SKULL_ITEM), "showRank", this.showRank));
        subSettings.add(new BooleanElement("Coins anzeigen", addon, new ControlElement.IconData(Material.GOLD_INGOT), "showCoins", this.showCoins));
        subSettings.add(new BooleanElement("Party anzeigen", addon, new ControlElement.IconData(Material.CAKE), "showParty", this.showParty));
    }

}
