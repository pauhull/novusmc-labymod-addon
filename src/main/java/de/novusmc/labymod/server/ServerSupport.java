package de.novusmc.labymod.server;

import com.google.common.collect.ImmutableList;
import de.novusmc.labymod.NovusLabyModAddon;
import de.novusmc.labymod.gson.Party;
import lombok.Setter;
import net.labymod.api.events.TabListEvent.Type;
import net.labymod.ingamegui.moduletypes.ColoredTextModule.Text;
import net.labymod.servermanager.ChatDisplayAction;
import net.labymod.servermanager.Server;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.PacketBuffer;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Paul
 * on 06.01.2019
 *
 * @author pauhull
 */
public class ServerSupport extends Server {

    @Setter
    private String players = null;

    @Setter
    private String server = null;

    @Setter
    private String rank = null;

    @Setter
    private Party party = null;

    @Setter
    private int coins = 0;

    private NovusLabyModAddon addon;

    public ServerSupport(NovusLabyModAddon addon) {
        super("novusmc", "novusmc.de", "play.novusmc.de", "116.203.16.116");
        (this.addon = addon).getApi().registerServerSupport(addon, this);
    }

    @Override
    public void onJoin(ServerData serverData) {
    }

    @Override
    public ChatDisplayAction handleChatMessage(String unformatted, String formatted) {
        return ChatDisplayAction.NORMAL;
    }

    @Override
    public void handlePluginMessage(String channelName, PacketBuffer packetBuffer) {
    }

    @Override
    public void handleTabInfoMessage(Type type, String formatted, String unformatted) {
        if (type == Type.HEADER) {
            String firstLine = unformatted.split("\n")[0];
            server = firstLine.replace("NovusMC.de × ", "");
        } else if (type == Type.FOOTER) {
            String thirdLine = unformatted.split("\n")[2];
            players = thirdLine.replace("Es ist ", "").replace("Es sind ", "").replace(" Spieler auf dem Netzwerk.", "");
        }
    }

    @Override
    public void fillSubSettings(List<SettingsElement> subSettings) {
    }

    @Override
    public void addModuleLines(List<DisplayLine> lines) {

        if (server != null && addon.getSettings().isShowServer()) {
            lines.add(new DisplayLine("Server", ImmutableList.of(Text.getText(server))));
        }

        if (players != null && addon.getSettings().isShowOnline()) {
            lines.add(new DisplayLine("Online", ImmutableList.of(Text.getText(players))));
        }

        if (rank != null && addon.getSettings().isShowRank()) {
            lines.add(new DisplayLine("Rang", ImmutableList.of(Text.getText(rank))));
        }

        if (addon.getSettings().isShowCoins()) {
            lines.add(new DisplayLine("Coins", ImmutableList.of(Text.getText(String.valueOf(coins)))));
        }

        if (party != null && addon.getSettings().isShowParty()) {
            lines.add(new DisplayLine("Party", ImmutableList.of(Text.getText(Arrays.toString(party.members.toArray())
                    .replace("[", "").replace("]", "")))));
        }

        super.addModuleLines(lines);
    }

}
