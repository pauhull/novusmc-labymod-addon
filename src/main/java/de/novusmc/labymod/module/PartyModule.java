package de.novusmc.labymod.module;

import de.novusmc.labymod.NovusLabyModAddon;
import de.novusmc.labymod.gson.Party;
import de.novusmc.labymod.server.ServerSupport;
import lombok.Getter;
import lombok.Setter;
import net.labymod.ingamegui.Module;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.ModuleConfig;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paul
 * on 06.01.2019
 *
 * @author pauhull
 */
public class PartyModule extends Module {

    private static final int AVATAR_SCALE = 8;
    private static final int AVATAR_MARGIN = 2;

    @Getter
    private String controlName = "Party",
            settingName = "Party",
            description = "Zeigt alle Mitglieder einer Party an";

    @Getter
    private int sortingId = 0;

    @Getter
    private ControlElement.IconData iconData = new ControlElement.IconData(Material.CAKE);

    @Getter
    private ModuleCategory category;

    @Setter
    private Party party;

    @Getter
    private Map<String, ResourceLocation> loadedAvatars;

    @Getter
    private ServerSupport server;

    @Getter
    private NovusLabyModAddon addon;

    public PartyModule(NovusLabyModAddon addon, ServerSupport server) {
        this.category = addon.getCategory();
        this.loadedAvatars = new HashMap<>();
        this.addon = addon;
        this.server = server;
        addon.getApi().registerModule(this);
    }

    @Override
    public void draw(int x, int y, int ignored) {
        // get colors
        int prefixColor = ModuleConfig.getConfig().getPrefixColor();
        int valueColor = ModuleConfig.getConfig().getValuesColor();
        int bracketColor = ModuleConfig.getConfig().getBracketsColor();

        // start y at 0 relative to the module
        int currentY = y;

        // if party is empty draw empty party string
        if (party == null || party.members.isEmpty()) {
            mc.fontRendererObj.drawString("Keine Party", x, currentY, 0xFF5555, true);
            return;
        }

        for (String player : party.members) {
            // clear color
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

            // draw avatar
            mc.getTextureManager().bindTexture(getAvatar(player));
            Gui.drawModalRectWithCustomSizedTexture(x, currentY, 0, 0, AVATAR_SCALE, AVATAR_SCALE, AVATAR_SCALE, AVATAR_SCALE);

            // draw player name
            mc.fontRendererObj.drawString(player, x + AVATAR_MARGIN + AVATAR_SCALE, currentY, valueColor, true);

            // draw [Leader] suffix
            if (player.equals(party.owner)) {
                int openingBracket = x + AVATAR_MARGIN + AVATAR_SCALE + mc.fontRendererObj.getStringWidth(player + " ");
                int text = openingBracket + mc.fontRendererObj.getCharWidth('[');
                int closingBracket = text + mc.fontRendererObj.getStringWidth("Leader");
                mc.fontRendererObj.drawString("[", openingBracket, currentY, bracketColor, true);
                mc.fontRendererObj.drawString("Leader", text, currentY, prefixColor, true);
                mc.fontRendererObj.drawString("]", closingBracket, currentY, bracketColor, true);
            }

            // increase next y position
            currentY += AVATAR_SCALE + AVATAR_MARGIN;
        }
    }

    @Override
    public double getHeight() {
        return (party == null ? 1 : party.members.size()) * (AVATAR_SCALE + AVATAR_MARGIN);
    }

    @Override
    public double getWidth() {
        return 150;
    }

    @Override
    public void loadSettings() {
    }

    private ResourceLocation getAvatar(String name) {
        if (loadedAvatars.containsKey(name))
            return loadedAvatars.get(name);

        try {
            BufferedImage image = ImageIO.read(new URL("https://minotar.net/avatar/" + name + "/" + AVATAR_SCALE));
            ResourceLocation texture = mc.getTextureManager().getDynamicTextureLocation(name + "-avatar", new DynamicTexture(image));
            loadedAvatars.put(name, texture);
            return texture;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
