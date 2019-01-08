package de.novusmc.labymod.gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/**
 * Created by Paul
 * on 08.01.2019
 *
 * @author pauhull
 */
public class NovusCoins {

    public int coins;

    public static NovusCoins fromJson(JsonElement json) {
        return new GsonBuilder().setPrettyPrinting().create().fromJson(json, NovusCoins.class);
    }

}
