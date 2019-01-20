package blacksmithsystem.blacksmithsystem.events;

import blacksmithsystem.blacksmithsystem.Animation;
import blacksmithsystem.blacksmithsystem.BlacksmithSystem;
import blacksmithsystem.blacksmithsystem.Inventory.BlacksmithInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class BlacksmithInventoryInteract implements Listener {

    ItemStack lockpick = BlacksmithInventory.craftLockpickButton;
    Inventory blacksmithInventory = BlacksmithInventory.inventory;

    @EventHandler
    public void clickLockpick(InventoryClickEvent event) {
        if (!event.getInventory().getTitle().equalsIgnoreCase(blacksmithInventory.getTitle())) {
            return;
        }

        if (!event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(lockpick.getItemMeta().getDisplayName())) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Block b = BlackSmithInteract.getClicked();
        ArmorStand s = b.getLocation().getWorld().spawn(b.getLocation().add(1, 1, 0), ArmorStand.class);

        int animate = Bukkit.getScheduler().scheduleAsyncRepeatingTask(BlacksmithSystem.getPlugin(), new Animation(s, player), 0, 5);
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getScheduler().cancelTask(animate);
                s.remove();
                player.getInventory().addItem(lockpick);
                player.sendMessage(ChatColor.GRAY + "Craft successful! added item: " + ChatColor.BLUE + lockpick.getItemMeta().getDisplayName());
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 100F, 100F);
            }
        }.runTaskLater(BlacksmithSystem.getPlugin(), 600);

        s.setItemInHand(new ItemStack(Material.IRON_AXE));
    }
}
