/*
 * Copyright (c) 2018.
 */

package plugin.npc;

import org.arcanium.game.node.entity.Entity;
import org.arcanium.game.node.entity.npc.AbstractNPC;
import org.arcanium.game.node.entity.player.Player;
import org.arcanium.game.node.item.GroundItemManager;
import org.arcanium.game.node.item.Item;
import org.arcanium.game.world.map.Location;

/**
 * Represents the highway man npc.
 * @author 'Vexia
 * @version 1.0
 */
public final class HighwayManNPC extends AbstractNPC {

    /**
     * Represents the cape item.
     */
    private static final Item CAPE = new Item(1019);

    /**
     * Constructs a new {@code HighwayManNPC} {@code Object}.
     * @param id the id.
     * @param location the location.
     */
    public HighwayManNPC(int id, Location location) {
	super(id, location);
    }

    /**
     * Constructs a new {@code HighwayManNPC} {@code Object}.
     */
    public HighwayManNPC() {
	super(0, null);
    }

    @Override
    public void finalizeDeath(Entity killer) {
	super.finalizeDeath(killer);
	if (getId() == 180) {
	    GroundItemManager.create(new Item(526), getLocation(), (Player) killer);
	    GroundItemManager.create(CAPE, getLocation(), (Player) killer);
	}
    }

    @Override
    public void onAttack(Entity target) {
	sendChat("Stand and deliver!");
    }

    @Override
    public int[] getIds() {
	return new int[] { 180, 2677 };
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
	return new HighwayManNPC(id, location);
    }

}
