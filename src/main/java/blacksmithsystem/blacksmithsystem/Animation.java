package blacksmithsystem.blacksmithsystem;

import blacksmithsystem.blacksmithsystem.events.BlackSmithInteract;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

public class Animation extends BukkitRunnable {
    ArmorStand s;
    Player p;

    public Animation(ArmorStand stand, Player player) {
        s = stand;
        p = player;
    }

    @Override
    public void run() {
        EulerAngle upd = s.getRightArmPose().add(0.4F, 0, 0);
        if (s.getRightArmPose().getX() >= 5.63908967753178f) {
            EulerAngle sd = s.getRightArmPose().subtract(0.4F, 0, 0);
            s.setRightArmPose(sd);
            s.getLocation().getWorld().playEffect(s.getLocation().add(0, 0.5f, 1), Effect.SMOKE, 1);
            s.getLocation().getWorld().playSound(s.getLocation(), Sound.BLOCK_ANVIL_HIT, 100f, 100f);
        } else {
            s.setRightArmPose(upd);
        }
    }
}
