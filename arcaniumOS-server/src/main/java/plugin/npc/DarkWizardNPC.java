/*
 * Copyright (c) 2018.
 */

package plugin.npc;

import org.arcanium.game.node.entity.Entity;
import org.arcanium.game.node.entity.combat.BattleState;
import org.arcanium.game.node.entity.combat.CombatSpell;
import org.arcanium.game.node.entity.combat.CombatStyle;
import org.arcanium.game.node.entity.npc.AbstractNPC;
import org.arcanium.game.node.entity.player.link.SpellBookManager;
import org.arcanium.game.world.map.Location;
import org.arcanium.tools.RandomFunction;

/**
 * Represents the abstract representation of a dark wizard.
 * @author 'Vexia
 * @version 1.0
 */
public final class DarkWizardNPC extends AbstractNPC {

    /**
     * Represents the NPC ids of NPCs using this plugin.
     */
    private static final int[] ID = { 172, 174 };

    /**
     * Represents the spells to set.
     */
    private static final int[][] SPELLS = new int[][] { { 6, 7 }, { 4, 2 } };

    /**
     * Constructs a new {@code DarkWizardNPC} {@code Object}.
     */
    public DarkWizardNPC() {
	super(0, null);
	setAggressive(true);
    }

    /**
     * Constructs a new {@code DarkWizardNPC} {@code Object}.
     * @param id The NPC id.
     * @param location The location.
     */
    private DarkWizardNPC(int id, Location location) {
	super(id, location);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
	return new DarkWizardNPC(id, location);
    }

    @Override
    public void init() {
	super.init();
	getProperties().getCombatPulse().setStyle(CombatStyle.MAGIC);
	setDefault();
    }

    @Override
    public void onImpact(final Entity entity, BattleState state) {
	super.onImpact(entity, state);
	if (getAttribute("switched", false)) {
	    removeAttribute("switched");
	    setDefault();
	    return;
	}
	if (RandomFunction.random(6) > 4) {
	    setSpells(getSpells());
	    setAttribute("switched", true);
	}
    }

    /**
     * Sets the default spell.
     */
    private void setDefault() {
	getProperties().setAutocastSpell((CombatSpell) SpellBookManager.SpellBook.MODERN.getSpell(getId() == 172 ? 6 : 4));
    }

    /**
     * Sets random spells.
     * @param ids the ids.
     */
    private void setSpells(int[] ids) {
	getProperties().setAutocastSpell((CombatSpell) SpellBookManager.SpellBook.MODERN.getSpell(ids[RandomFunction.random(ids.length)]));
    }

    /**
     * Gets the spells.
     * @return the spells.
     */
    private int[] getSpells() {
	int index = 0;
	for (int i = 0; i < ID.length; i++) {
	    if (ID[i] == getId()) {
		index = i;
		break;
	    }
	}
	return SPELLS[index];
    }

    @Override
    public int[] getIds() {
	return ID;
    }

}
