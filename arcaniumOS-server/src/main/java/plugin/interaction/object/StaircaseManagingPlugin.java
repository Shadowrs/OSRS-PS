package plugin.interaction.object;

import org.arcanium.cache.def.impl.ObjectDefinition;
import org.arcanium.game.content.global.action.DoorActionHandler;
import org.arcanium.game.interaction.MovementPulse;
import org.arcanium.game.interaction.OptionHandler;
import org.arcanium.game.node.Node;
import org.arcanium.game.node.entity.impl.ForceMovement;
import org.arcanium.game.node.entity.player.Player;
import org.arcanium.game.node.object.GameObject;
import org.arcanium.game.system.task.Pulse;
import org.arcanium.game.world.GameWorld;
import org.arcanium.game.world.map.Location;
import org.arcanium.game.world.map.RegionManager;
import org.arcanium.plugin.Plugin;

import java.util.logging.Logger;

/**
 * Plugin used for handling the opening/closing of (double)
 * doors/gates/fences/...
 *
 * @author Emperor
 */
public final class StaircaseManagingPlugin extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ObjectDefinition.setOptionHandler("climb-up", this);
        ObjectDefinition.setOptionHandler("climb-down", this);
        return this;
    }

    /*
    TODO: Figure out why client side gets corrupted when TPing across Z axis
     */

    @Override
    public boolean handle(Player player, Node node, String option) {
        if (option.compareTo("climb-up")==0) {
            Location dest = player.getLocation();
            dest.setZ(dest.getZ()+1);
            player.getProperties().setTeleportLocation(Location.create(3213, 3213, 1));
            System.out.println("Should have went up");
            return true;
        } else if (!node.asObject().getDefinition().hasAction("climb-up")){
            player.getPulseManager().run(new MovementPulse(player, node) {
                @Override
                public boolean pulse() {
                    player.faceLocation(node.getLocation( ));
                    Location dest = player.getLocation();
                    dest.setZ(dest.getZ()-1);
                    player.getProperties().setTeleportLocation(dest);
                    //player.getLocation().setZ(player.getLocation().getZ()-1);
                    return true;
                }
            }, "movement");
            return true;
        } else {
            System.out.println(option);
            System.out.println(option.compareTo("climb-up"));
            player.getPulseManager().run(new MovementPulse(player, node) {
                @Override
                public boolean pulse() {
                    player.faceLocation(node.getLocation( ));
                    player.getDialogueInterpreter().sendDialogue("Climb up", "Climb down");
                    //player.getLocation().setZ(player.getLocation().getZ()-1);
                    return true;
                }
            }, "movement");
            return true;
        }
    }


}