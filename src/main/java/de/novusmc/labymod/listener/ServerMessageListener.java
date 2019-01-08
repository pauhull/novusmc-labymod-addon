package de.novusmc.labymod.listener;

import com.google.gson.JsonElement;
import de.novusmc.labymod.NovusLabyModAddon;
import de.novusmc.labymod.gson.NovusCoins;
import de.novusmc.labymod.gson.NovusInfo;
import net.labymod.api.events.ServerMessageEvent;

/**
 * Created by Paul
 * on 06.01.2019
 *
 * @author pauhull
 */
public class ServerMessageListener implements ServerMessageEvent {

    private NovusLabyModAddon addon;

    public ServerMessageListener(NovusLabyModAddon addon) {
        this.addon = addon;
        addon.getApi().getEventManager().register(this);
    }

    @Override
    public void onServerMessage(String channel, JsonElement jsonElement) {
        if (channel.equals("NovusInfo")) {
            NovusInfo novusInfo = NovusInfo.fromJson(jsonElement);
            addon.getServerSupport().setRank(novusInfo.rank);
            addon.getServerSupport().setParty(novusInfo.party);
            addon.getPartyModule().setParty(novusInfo.party);
        } else if (channel.equals("NovusCoins")) {
            NovusCoins novusCoins = NovusCoins.fromJson(jsonElement);
            addon.getServerSupport().setCoins(novusCoins.coins);
        }
    }

}
