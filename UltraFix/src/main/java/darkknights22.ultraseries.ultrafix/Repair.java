package darkknights22.ultraseries.ultrafix;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Set;

public class Repair extends org.bukkit.plugin.java.JavaPlugin {
    public void repairAll(Player player) {
        player.getInventory().forEach(item -> {
            item.setDurability((short) 0);
        });
    }
    public Repair() {
    }

    public static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("Minecraft");

    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.DARK_AQUA + "=================================");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.DARK_PURPLE + "Status:" + ChatColor.BOLD + ChatColor.GREEN + "ENABLED");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.DARK_PURPLE + "Version:" + ChatColor.GOLD + "v1.2.1");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.DARK_PURPLE + "Author:" + ChatColor.GOLD + "DarkKnights22");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.DARK_AQUA + "=================================");
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(" ");
        if (!new java.io.File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.DARK_AQUA + "=================================");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.DARK_PURPLE + "Status:" + ChatColor.BOLD + ChatColor.RED + "DISABLED");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.DARK_PURPLE + "Version:" + ChatColor.GOLD + "v1.2.1");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.DARK_PURPLE + "Author:" + ChatColor.GOLD + "DarkKnights22");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.DARK_AQUA + "=================================");
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(" ");
        saveConfig();
    }

    ArrayList<Player> cooldown = new ArrayList();


    public boolean onCommand(final CommandSender player, org.bukkit.command.Command cmd, String Label, String[] args) {
        if (Label.equalsIgnoreCase("fix")) {
            if (!(player instanceof Player)) {
                player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.RED + "This command can only be run by a player.");
            } else if ((player instanceof Player)) {
                final Player p = (Player) player;
                if (player.hasPermission("ultrafix.fix")) {
                    if (cooldown.contains(p)) {
                        player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.RED + "This command is on cooldown.");
                    } else {
                        Player sender = (Player) player;
                        ItemStack berepaired = sender.getItemInHand();
                        if (itemCheck(berepaired)) {
                            berepaired.setDurability(

                                    (short) (berepaired.getType().getMaxDurability() - berepaired.getType().getMaxDurability()));
                            sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.GREEN + "You have repaired an item!");

                            if (getConfig().getBoolean("cd")) {
                                cooldown.add(p);
                                Bukkit.getServer()
                                        .getScheduler()
                                        .scheduleSyncDelayedTask(this, new Runnable() {

                                            public void run() {
                                                cooldown.remove(p);
                                            }

                                        }, getConfig().getLong("cooldown") * 20L);
                            }
                        } else {
                            sender.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.RED + "This item cannot be repaired.");
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.RED + "You do not have permission.");
                }
            }
        } else if (Label.equalsIgnoreCase("fixall")) {
            if ((player instanceof Player)) {
                if (player.hasPermission(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + "ultrafix.fixall")) {
                    if (cooldown.contains(player)) {
                        player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.RED + "This command is on cooldown.");
                    } else {
                        final Player p = (Player) player;
                        repair(p);

                        ItemStack[] arrayOfItemStack;
                        int localItemStack1 = (arrayOfItemStack = p.getInventory().getArmorContents()).length;
                        for (int ix = 0; ix < localItemStack1; ix++) {
                            ItemStack i = arrayOfItemStack[ix];
                            repair(i);
                        }
                        if (getConfig().getBoolean("cd")) {
                            cooldown.add(p);
                            Bukkit.getServer()
                                    .getScheduler()
                                    .scheduleSyncDelayedTask(this, new Runnable() {

                                        public void run() {
                                            cooldown.remove(p);
                                        }

                                    }, getConfig().getLong("cooldown") * 20L);
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.RED + "You do not have permission.");
                }
            } else {
                player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + "You cannot do that.");
            }
        } else if (Label.equalsIgnoreCase("fixplayer")) {
            if ((player instanceof Player)) {
                final Player p = (Player) player;
                if (player.hasPermission("ultrafix.fixplayer")) {
                    if (cooldown.contains(p)) {
                        player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.RED + "This command is on cooldown.");
                    } else if (args.length == 0) {
                        p.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.DARK_PURPLE + "Who's inventory would you like to fix?");
                    } else if ((args.length == 1) &&
                            (p.getServer().getPlayer(args[0]) != null)) {
                        Player t = p.getServer().getPlayer(args[0]);
                        repair(t);
                        t.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.GREEN + "Your inventory has been fixed by " + p

                                .getName() + ".");
                        p.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.GREEN + "You have fixed " + t
                                .getName() + "'s inventory.");
                        if (getConfig().getBoolean("cd")) {
                            cooldown.add(p);
                            Bukkit.getServer()
                                    .getScheduler()
                                    .scheduleSyncDelayedTask(this, new Runnable() {

                                        public void run() {
                                            cooldown.remove(p);
                                        }

                                    }, getConfig().getLong("cooldown") * 20L);
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.RED + "You don't have permission!");
                }
            }
        } else if (Label.equalsIgnoreCase("fixchest")) {
            Player p = (Player) player;
            if (cooldown.contains(p)) {
                p.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.RED + "This command is on cooldown.");
            } else if (p.hasPermission("ultrafix.fixchest")) {
                org.bukkit.block.Block b = p.getTargetBlock((Set<Material>) null, 5);
                if ((b.getType() == Material.CHEST) ||
                        (b.getType() == Material.TRAPPED_CHEST)) {
                    Chest c = (Chest) b.getState();
                    if (getConfig().getBoolean("cd")) {
                        cooldown.add(p);
                        Bukkit.getServer().getScheduler()
                                .scheduleSyncDelayedTask(this, new Runnable() {
                                    public void run() {
                                        cooldown.remove(player);
                                    }
                                }, getConfig().getLong("cooldown") * 20L);
                    }
                    if (c.getInventory().getSize() == 54) {
                        for (int i = 0; i <= 54; i++) {
                            try {
                                ItemStack w = c.getInventory().getItem(i);
                                if (itemCheck(w)) {

                                    c.getInventory().getItem(i).setDurability(


                                            (short) (w.getType().getMaxDurability() - w.getType().getMaxDurability()));
                                }
                            } catch (Exception localException1) {
                            }
                        }
                    } else {
                        for (int i = 0; i <= 27; i++) {
                            try {

                                ItemStack w = c.getInventory().getItem(i);
                                if (itemCheck(w)) {

                                    c.getInventory().getItem(i).setDurability(


                                            (short) (w.getType().getMaxDurability() - w.getType().getMaxDurability()));
                                }
                            } catch (Exception localException2) {
                                p.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.GREEN + "You have fixed an item in this chest!");
                            }
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.RED + "This block isn't a chest.");
                }
            } else {
                player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.RED + "You don't have permission!");
            }
        } else if (Label.equalsIgnoreCase("fixconfig")) {
            if (((player instanceof Player)) &&
                    (player.hasPermission("uktrafix.config")) && (args.length == 0)) {
                Bukkit.broadcastMessage(ChatColor.GREEN + "Attempting to save and reload...");
                try {
                    saveConfig();
                    Bukkit.reload();
                    player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.GREEN + "Complete!");
                } catch (Exception exc) {
                    player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.RED + "Failure! Stack trace has been printed to console.");

                    log.warning("Stack trace from UltraFix:" + exc
                            .getStackTrace());
                }
            }
        } else if ((Label.equalsIgnoreCase("fixcd")) && ((player instanceof Player))) {
            if ((player.hasPermission("ultrafix.config")) && (args.length == 1)) {
                Integer i = Integer.valueOf(args[0]);
                getConfig().set("cooldown", i);
                player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "UltraFix" + ChatColor.DARK_RED + "]" + ChatColor.GREEN + "Cooldown has been set to " +
                        String.valueOf(i));
            }
        }

        return false;
    }

    private void repair(ItemStack i) {
        try {
            if (itemCheck(i)) {
                i.setDurability((short) 0);
            }
        } catch (Exception localException) {
        }
    }

    public void repair(Player p) {
            }


    public boolean itemCheck(ItemStack stack) {
        return Arrays.asList(256, 257, 258, 259, 261, 267, 268, 269, 270, 271, 272, 273, 274, 275,276, 277, 278, 279, 283, 284, 285, 286, 290, 291, 292, 292, 293, 294, 298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 312, 312, 313, 314, 315, 316, 317, 346, 359)
                .contains(stack.getType().getId());
    }
}
