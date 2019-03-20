/*
 * Copyright (c) 2018.
 */

package plugin.npc;

import org.arcanium.game.node.entity.Entity;
import org.arcanium.game.node.entity.combat.BattleState;
import org.arcanium.game.node.entity.npc.AbstractNPC;
import org.arcanium.game.world.map.Location;
import org.arcanium.tools.RandomFunction;

/**
 * Rerepsents a barbarian npc.
 *
 * @author 'Vexia
 */
public final class BarbarianNPC extends AbstractNPC {

    /**
     * The NPC ids of NPCs using this plugin.
     */
    private static final int[] ID = {12, 3246, 3247, 3248, 3249, 3250, 3251, 3252, 3253, 3255, 3256, 3257, 3258, 3259, 3260, 3261, 3262, 3263, 5909};

    /**
     * Constructs a new {@code BarbarianNPC} {@code Object}.
     *
     * @param id       the id.
     * @param location the location.
     */
    public BarbarianNPC(int id, Location location) {
        super(id, location, true);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new BarbarianNPC(id, location);
    }

    @Override
    public void onImpact(final Entity entity, final BattleState state) {
        if (RandomFunction.random(8) == 1) {
            sendChat("YEEEEEEEEAARRRGGGGHHHHHHHH");
        }
        super.onImpact(entity, state);
    }

    @Override
    public int[] getIds() {
        return ID;
    }

}
