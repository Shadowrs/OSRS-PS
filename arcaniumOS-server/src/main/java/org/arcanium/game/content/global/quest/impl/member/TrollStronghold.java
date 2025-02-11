package org.arcanium.game.content.global.quest.impl.member;

import org.arcanium.game.content.global.quest.Quest;
import org.arcanium.game.content.global.quest.QuestState;
import org.arcanium.game.node.entity.player.Player;

/**
 * The main type for the troll stronghold quest.
 * @author Aero
 */
public class TrollStronghold extends Quest {

    /**
     * The name of this quest.
     */
    public static final String NAME = "Troll Stronghold";

    /**
     * Constructs a new {@code TrollStronghold} {@code Object}.
     * @param player The player to construct the class for.
     */
    public TrollStronghold(final Player player) {
	super(player);
    }

    @Override
    public boolean isInDevelopment() {
	return true; // Remove this when finished making the quest!
    }

    @Override
    public void update() {
	super.clear();
    }

    @Override
    public void finish() {
	super.finalize();
    }

    @Override
    public int[] getConfig() {
	return new int[] { 317, getState() == QuestState.COMPLETED ? 50 : getState() == QuestState.NOT_STARTED ? 0 : 1 };
    }

    @Override
    public int getIndex() {
	return 128;
    }

    @Override
    public String getName() {
	return NAME;
    }

    @Override
    public int getQuestPoints() {
	return 1;
    }

}