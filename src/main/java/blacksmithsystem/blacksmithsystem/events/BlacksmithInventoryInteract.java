package blacksmithsystem.blacksmithsystem.events;

import blacksmithsystem.blacksmithsystem.Animation;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockVector;

public class BlacksmithInventoryInteract implements Listener {

    public static final String LOCKPICK_NAME = ChatColor.RED + "Lockpick";

    @EventHandler
    public void clickLockpick(InventoryClickEvent event) {
        if (!event.getInventory().getTitle().equalsIgnoreCase(BlackSmithInteract.RESULT_SETTER_TITLE)) {
            return;
        }
        if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {

            if (!event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(LOCKPICK_NAME)) {
                return;
            }
            Player player = (Player) event.getWhoClicked();
            BlockVector blockVector = (BlockVector) player.getMetadata("anvil").get(0).value();

            ArmorStand stand = player.getWorld().spawn(blockVector.toLocation(player.getWorld()).add(1, 1, 0), ArmorStand.class);
            stand.setItemInHand(new ItemStack(Material.IRON_AXE));
            new Animation(stand).start(600);
        }
    }
}
