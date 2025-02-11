package plugin.skills.runecrafting;

import org.arcanium.game.content.skill.f2p.runecrafting.MysteriousRuin;
import org.arcanium.game.content.skill.f2p.runecrafting.Talisman;
import org.arcanium.game.interaction.NodeUsageEvent;
import org.arcanium.game.interaction.UseWithHandler;
import org.arcanium.game.node.entity.player.Player;
import org.arcanium.game.node.object.GameObject;
import org.arcanium.game.system.task.Pulse;
import org.arcanium.game.world.GameWorld;
import org.arcanium.game.world.update.flag.context.Animation;
import org.arcanium.plugin.Plugin;

/**
 * Handles the entering into a mysterious ruin.
 *
 * @author Vexia
 */
public final class MysteriousRuinPlugin extends UseWithHandler {

    /**
     * Represents the animation used.
     */
    private static final Animation ANIMATION = new Animation(827);

    /**
     * Constructs a new {@code RunecraftingOptionPlugin} {@code Object}.
     */
    public MysteriousRuinPlugin() {
        super(1438, 1448, 1444, 1440, 1442, 5516, 1446, 1454, 1452, 1462, 1458, 1456, 1450, 1460);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        for (MysteriousRuin ruin : MysteriousRuin.values()) {
            for (int i : ruin.getObject()) {
                addHandler(i, OBJECT_TYPE, this);
            }
        }
        return this;
    }

    @Override
    public boolean handle(NodeUsageEvent event) {
        final Player player = event.getPlayer();
        final Talisman talisman = Talisman.forItem(event.getUsedItem());
        final MysteriousRuin ruin = MysteriousRuin.forObject(((GameObject) event.getUsedWith()));
        if (talisman != ruin.getTalisman() && talisman != Talisman.ELEMENTAL) {
            return false;
        }
        if (talisman == Talisman.ELEMENTAL && (ruin.getTalisman() != Talisman.AIR && ruin.getTalisman() != Talisman.WATER && ruin.getTalisman() != Talisman.FIRE && ruin.getTalisman() != Talisman.EARTH)) {
            return false;
        }
        teleport(player, event, ruin);
        return true;
    }

    /**
     * Teleports the player to the ruin location.
     *
     * @param player the player.
     * @param event  the event.
     * @param ruin   the ruin.
     */
    private void teleport(final Player player, final NodeUsageEvent event, final MysteriousRuin ruin) {
        player.lock(4);
        player.animate(ANIMATION);
        player.getPacketDispatch().sendMessage("You hold the " + event.getUsedItem().getName() + " towards the mysterious ruins.");
        player.getPacketDispatch().sendMessage("You feel a powerful force take hold of you.");
        GameWorld.submit(new Pulse(3, player) {
            @Override
            public boolean pulse() {
                player.getProperties().setTeleportLocation(ruin.getEnd());
                return true;
            }
        });
    }

}