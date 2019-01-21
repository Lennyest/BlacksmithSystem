package blacksmithsystem.blacksmithsystem;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

public class Animation extends BukkitRunnable {
    private ArmorStand stand;
    private int duration;

    public Animation(ArmorStand stand) {
        this.stand = stand;
    }

    @Override
    public void run() {
        if (duration <= 0) {
            this.cancel();
            return;
        }
        EulerAngle upd = stand.getRightArmPose().add(0.4F, 0, 0);
        if (stand.getRightArmPose().getX() >= 5.63908967753178f) {
            EulerAngle sd = stand.getRightArmPose().subtract(0.4F, 0, 0);
            stand.setRightArmPose(sd);
            stand.getLocation().getWorld().playEffect(stand.getLocation().add(0, 0.5f, 1), Effect.SMOKE, 1);
            stand.getLocation().getWorld().playSound(stand.getLocation(), Sound.BLOCK_ANVIL_HIT, 100f, 100f);
        } else {
            stand.setRightArmPose(upd);
        }
        duration -= 5;
    }

    public void start(int duration) {
        this.runTaskTimer(BlacksmithSystem.INSTANCE, 0, 5);
        this.duration = duration;
    }
}
