package org.arcanium.game.content.skill.f2p.cooking.recipe.topping.impl;

import org.arcanium.game.content.skill.Skills;
import org.arcanium.game.content.skill.f2p.cooking.recipe.Recipe;
import org.arcanium.game.interaction.NodeUsageEvent;
import org.arcanium.game.node.entity.player.Player;
import org.arcanium.game.node.item.Item;

/**
 * Represents the mushroom and onion recipe. This recipe consists of using a
 * fried mushroom with a friend onion.
 * @author 'Vexia
 * @date 22/12/2013
 */
public class MushroomAndOnion extends Recipe {

    /**
     * Represents the mushroom and onion item.
     */
    private static final Item MUSHROOM_AND_ONION = new Item(7066);

    /**
     * Represents the fried onions item.
     */
    private static final Item FRIED_ONIONS = new Item(7084);

    /**
     * Represents the fried mushrooms item.
     */
    private static final Item FRIED_MUSHROOMS = new Item(7082);

    @Override
    public void mix(final Player player, final NodeUsageEvent event) {
	if (player.getSkills().getLevel(Skills.COOKING) < 57) {
	    player.getDialogueInterpreter().sendDialogue("You need a Cooking level of at least " + 57 + " in order to do this.");
	    return;
	}
	super.mix(player, event);
	player.getSkills().addExperience(Skills.COOKING, 120, true);
    }

    @Override
    public Item getBase() {
	return FRIED_MUSHROOMS;
    }

    @Override
    public Item getProduct() {
	return MUSHROOM_AND_ONION;
    }

    @Override
    public Item[] getIngredients() {
	return new Item[] { FRIED_ONIONS };
    }

    @Override
    public Item[] getParts() {
	return new Item[] {};
    }

    @Override
    public String getMixMessage(NodeUsageEvent event) {
	return null;
    }

    @Override
    public boolean isSingular() {
	return true;
    }

}
