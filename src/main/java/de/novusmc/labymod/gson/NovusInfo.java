package de.novusmc.labymod.gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/**
 * Created by Paul
 * on 08.01.2019
 *
 * @author pauhull
 */
public class NovusInfo {

    public String rank;
    public Party party;

    public static NovusInfo fromJson(JsonElement json) {
        return new GsonBuilder().setPrettyPrinting().create().fromJson(json, NovusInfo.class);
    }

}
