package de.novusmc.labymod.module;

import lombok.Getter;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.settings.elements.ControlElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Paul
 * on 08.01.2019
 *
 * @author pauhull
 */
public class NovusCategory extends ModuleCategory {

    @Getter
    private static ControlElement.IconData iconData = null;

    static {
        try {
            BufferedImage image = ImageIO.read(new URL("http://pauhull.de/icon.png"));
            ResourceLocation location = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("novusmc-icon", new DynamicTexture(image));
            iconData = new ControlElement.IconData(location);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public NovusCategory() {
        super("NovusMC", true, iconData);
        ModuleCategoryRegistry.loadCategory(this);
    }

}