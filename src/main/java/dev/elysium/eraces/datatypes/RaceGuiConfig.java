package dev.elysium.eraces.datatypes;

import lombok.Data;

@Data
public class RaceGuiConfig {

    @RaceProperty(path = "name")
    public String name = "name_undefined";

    @RaceProperty(path = "lore")
    public String lore = "";

    @RaceProperty(path = "icon")
    public String icon = "DIRT";
}
