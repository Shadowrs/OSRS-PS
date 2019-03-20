/*
 * Copyright (c) 2018.
 */

package plugin.npc;

import org.arcanium.game.node.entity.npc.AbstractNPC;
import org.arcanium.game.world.map.Location;
import org.arcanium.tools.RandomFunction;

/**
 * Represents the a man npc.
 *
 * @author Null
 * @version 1.0
 */
public final class ManNPC extends AbstractNPC {

    /**
     * Represents the force chat to use.
     */
    private static final String FORCE_CHAT = "Oy, Piss off mate";

    /**
     * Constructs a new {@code ManNPC} {@code Object}.
     */
    public ManNPC() {
        super(3078, null, true);
    }

    /**
     * Constructs a new {@code ManNPC} {@code Object}.
     *
     * @param id       the id.
     * @param location the location.
     */
    public ManNPC(int id, Location location) {
        super(id, location, true);
    }

    @Override
    public String getName() {
        return "Man";
    }

    @Override
    public void tick() {
        if (RandomFunction.random(45) == 5) {
            sendChat(FORCE_CHAT);
        }
        super.tick();
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new ManNPC(id, location);
    }

    @Override
    public int[] getIds() {
        return new int[]{3078, 3080};
    }

}
