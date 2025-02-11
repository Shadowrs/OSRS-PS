/*
 * Copyright (c) 2018.
 */

package plugin.npc;

import org.arcanium.game.node.entity.combat.DeathTask;
import org.arcanium.game.node.entity.npc.AbstractNPC;
import org.arcanium.game.world.map.Location;
import org.arcanium.tools.RandomFunction;

/**
 * Handles the sheep npc's.
 *
 * @author 'Vexia
 */
public class SheepNPC extends AbstractNPC {

    /**
     * The NPC ids of NPCs using this plugin.
     */
    private static final int[] ID = {42, 43, 1271, 1272, 1529, 1762, 1763, 1764, 1765, 2311, 2345, 2346, 2347, 2348, 2377, 2378, 2379, 2380, 3310, 3311, 3579, 5148, 5149, 5150, 5151, 5152, 5153, 5154, 5155, 5156, 5157, 5158, 5159, 5160, 5161, 5162, 5163, 5164, 5165, 5172, 5173};

    /**
     * Constructs a new {@code AlKharidWarriorNPC} {@code Object}.
     *
     * @param id       The NPC id.
     * @param location The location.
     */
    public SheepNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new SheepNPC(id, location);
    }

    @Override
    public void tick() {
        if (getProperties().getCombatPulse().isAttacking() || DeathTask.isDead(this)) {
            super.tick();
            return;
        }
        if (RandomFunction.random(35) == 5) {
            sendChat("Baa!");
        }
        super.tick();
    }

    @Override
    public int[] getIds() {
        return ID;
    }

}
