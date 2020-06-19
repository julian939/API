package de.njsystems.utils;

import java.util.ArrayList;

public enum Times {

    YEARS("Jahr(e)", 1000*60*60*24*30*12, "a"),
    MONTH("Monat(e)", 1000*60*60*24*30, "mon"),
    WEEK("Woche(n)", 1000*60*60*24*7, "w"),
    DAY("Tag(e)", 1000*60*60*24, "d"),
    HOUR("Stunde(n)", 1000*60*60, "h"),
    MINUITS("Minute(n)", 1000*60, "min"),
    SECONDS("SEKUNDEN", 1*1000, "s"),
    MILLISECONDS("Millisekunde(n)", 1L, "ms");

    private String name;
    private long millis;
    private String tag;

    Times(String name, long millis, String tag) {
        this.name = name;
        this.millis = millis;
        this.tag = tag;
    }

    public ArrayList<String> getTags() {
        ArrayList<String> tags = new ArrayList<>();
        for(Times times: Times.values()) {
            tags.add(times.tag);
        }
        return tags;
    }

    public long getMillis() {
        return millis;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }
}
