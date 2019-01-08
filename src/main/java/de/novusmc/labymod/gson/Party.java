package de.novusmc.labymod.gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.util.List;

/**
 * Created by Paul
 * on 08.01.2019
 *
 * @author pauhull
 */
public class Party {

    public String owner, prefix;
    public List<String> members, invited;

    public static Party fromJson(JsonElement json) {
        return new GsonBuilder().setPrettyPrinting().create().fromJson(json, Party.class);
    }

}
