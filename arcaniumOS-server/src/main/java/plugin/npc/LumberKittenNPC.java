/*
 * Copyright (c) 2018.
 */

package plugin.npc;

import org.arcanium.game.content.global.quest.Quest;
import org.arcanium.game.node.entity.npc.AbstractNPC;
import org.arcanium.game.node.entity.player.Player;
import org.arcanium.game.world.GameWorld;
import org.arcanium.game.world.map.Location;
import org.arcanium.tools.RandomFunction;

/**
 * Represents the lumber kittens at the lumber yard.
 * @author 'Vexia
 * @version 1.0
 */
public final class LumberKittenNPC extends AbstractNPC {

    /**
     * Represents if the kitten is hidden.
     */
    private boolean hidden = true;

    /**
     * Represents the next time you can do a speak.
     */
    private int nextSpeak;

    /**
     * Represents the delay of hiding again.
     */
    private int hideDelay;

    /**
     * Constructs a new {@code LumberKittenNPC} {@code Object}.
     */
    public LumberKittenNPC() {
	super(0, null);
    }

    /**
     * Constructs a new {@code LumberKittenNPC} {@code Object}.
     * @param id the id.
     * @param location the location.
     */
    private LumberKittenNPC(int id, Location location) {
	super(id, location, false);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
	return new LumberKittenNPC(id, location);
    }

    @Override
    public void init() {
	setWalks(false);
	super.init();
    }

    @Override
    public void tick() {
	if (nextSpeak < GameWorld.getTicks()) {
	    hidden = false;
	    nextSpeak = GameWorld.getTicks() + RandomFunction.random(10, 40);
	    hideDelay = GameWorld.getTicks() + 4;
	    sendChat("Mew!");
	}
	if (hideDelay < GameWorld.getTicks()) {
	    hidden = true;
	    int rand = RandomFunction.random(20, 40);
	    hideDelay = GameWorld.getTicks() + rand;
	    nextSpeak = GameWorld.getTicks() + rand;
	}
	super.tick();
    }

    @Override
    public boolean isHidden(final Player player) {
	Quest quest = player.getQuestRepository().getQuest("Gertrude's Cat");
	if (hidden) {
	    return true;
	}
	if (quest.getStage() < 20 || quest.getStage() > 50) {
	    return true;
	}
	return false;
    }

    @Override
    public int[] getIds() {
	return new int[] { 767 };
    }

}
