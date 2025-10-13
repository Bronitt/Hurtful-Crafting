package com.bronit.hurtfulcrafting;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

    private final Configuration configuration;

    public final int damage;
    public final int heal;

    public Config(File file) {
        final Configuration config = new Configuration(file);

        String category;

        try  {
            category = "settings";
            config.addCustomCategoryComment(category, "Hurtful Crafting settings");
            this.damage = config.getInt("damageAmount", category, 1, 1, Integer.MAX_VALUE, "The damage that will be inflicted on the player during crafting");
            this.heal = config.getInt("healAmount", category, 1, 1, Integer.MAX_VALUE, "The amount of hp that Healer restores when used");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.configuration = config;
            if (this.configuration.hasChanged()) {
                this.configuration.save();
            }
        }

    }
}
