package com.funeralmoondevelopment.autobroadcast;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class AutoBroadcast extends JavaPlugin implements CommandExecutor {


    File file;
    FileConfiguration yml;
    List<String> messages;
    String prefix;
    long delay;


    public static void main(String[] args) {

    }

    @Override
    public void onEnable() {

        file = new File(this.getDataFolder(), "Messages.yml");
        if(!file.exists()) {
            saveResource("Messages.yml", false);
        }

        yml = YamlConfiguration.loadConfiguration(file);

        messages = yml.getStringList("Messages");
        prefix = yml.getString("prefix");
        delay = yml.getLong("delay");

        int size = messages.size();


        new BukkitRunnable() {

            int index = 0;

            @Override
            public void run() {

                Bukkit.broadcastMessage((ChatColor.AQUA + "[" + ChatColor.AQUA + prefix + ChatColor.AQUA + "] ") + (ChatColor.GREEN +  messages.get(index)));
                index++;

                if(index == messages.size()) {
                    index = 0;
                }


            }

        }.runTaskTimer(this, 0, delay);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("autobr")) {
            if(args[0].equalsIgnoreCase("test")) {
                Bukkit.broadcastMessage(messages.get((int)Math.random() * messages.size() - 1));

            }
        }

        return false;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }







}

