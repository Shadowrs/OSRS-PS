package org.arcanium.game.content.skill;

import org.arcanium.game.content.holiday.HolidayEvent;
import org.arcanium.game.node.entity.Entity;
import org.arcanium.game.node.entity.combat.ImpactHandler;
import org.arcanium.game.node.entity.npc.NPC;
import org.arcanium.game.node.entity.player.Player;
import org.arcanium.game.world.update.flag.player.AppearanceFlag;
import org.arcanium.net.packet.PacketRepository;
import org.arcanium.net.packet.context.SkillContext;
import org.arcanium.net.packet.server.SkillLevel;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Represents an entity's skills.
 * @author Emperor
 */
public final class Skills {

	/**
	 * Represents the constant modifier of experience.
	 */
	public static final double EXPERIENCE_MULTIPLIER = 1;

	/**
	 * Represents an array of skill names.
	 */
	public static final String[] SKILL_NAME = {"Attack", "Defence", "Strength", "Hitpoints", "Ranged", "Prayer", "Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting", "Smithing", "Mining", "Herblore", "Agility", "Thieving", "Slayer", "Farming", "Runecrafting", "Hunter", "Construction"};

	/**
	 * Constants for the skill ids.
	 */
	public static final int ATTACK = 0, DEFENCE = 1, STRENGTH = 2, HITPOINTS = 3, RANGE = 4, PRAYER = 5, MAGIC = 6, COOKING = 7, WOODCUTTING = 8, FLETCHING = 9, FISHING = 10, FIREMAKING = 11, CRAFTING = 12, SMITHING = 13, MINING = 14, HERBLORE = 15, AGILITY = 16, THIEVING = 17, SLAYER = 18, FARMING = 19, RUNECRAFTING = 20, HUNTER = 21, CONSTRUCTION = 22;

	public static final int MAX_SKILL = 23; //24 was for summoning.
	/**
	 * Represents the entity instance.
	 */
	private final Entity entity;

	/**
	 * An array containing all the player's experience.
	 */
	private final double[] experience;

	/**
	 * An array containing all the maximum levels.
	 */
	private final int[] staticLevels;

	/**
	 * An array containing all the current levels.
	 */
	private final int[] dynamicLevels;

	/**
	 * Represents the amount of prayer points left.
	 */
	private double prayerPoints = 1.;

	/**
	 * The player's life-points.
	 */
	private int lifepoints = 10;

	/**
	 * The amount of increased maximum lifepoints.
	 */
	private int lifepointsIncrease = 0;

	/**
	 * The total experience gained.
	 */
	private int experienceGained = 0;

	/**
	 * The restoration pulses.
	 */
	private final SkillRestoration[] restoration;

	/**
	 * If a lifepoints update should occur.
	 */
	private boolean lifepointsUpdate;

	/**
	 * A {@link Queue} of skills whose level ups need to be delayed.
	 */
	private final Queue<Integer> delayedLevelUps = new ArrayDeque<>();

	/**
	 * Constructs a new {@code Skills} {@code Object}.
	 *
	 * @param entity The entity.
	 */
	public Skills(Entity entity) {
		this.entity = entity;
		this.experience = new double[MAX_SKILL];
		this.staticLevels = new int[MAX_SKILL];
		this.dynamicLevels = new int[MAX_SKILL];
		this.restoration = new SkillRestoration[MAX_SKILL];
		for (int i = 0; i < MAX_SKILL; i++) {
			this.staticLevels[i] = 1;
			this.dynamicLevels[i] = 1;
		}
		this.experience[HITPOINTS] = 1154;
		this.dynamicLevels[HITPOINTS] = 10;
		this.staticLevels[HITPOINTS] = 10;
		entity.getProperties().setCombatLevel(3);
	}

	/**
	 * Configures the skills.
	 */
	public void configure() {
		updateCombatLevel();
		int max = MAX_SKILL;
		if (entity instanceof NPC) {
			max = 7;
		}
		for (int i = 0; i < max; i++) {
			if (i != PRAYER && restoration[i] == null) {
				configureRestorationPulse(i);
			}
		}
	}

	/**
	 * Called every pulse (600ms).
	 */
	public void pulse() {
		if (lifepoints < 1) {
			return;
		}
		for (int i = 0; i < restoration.length; i++) {
			if (restoration[i] != null) {
				restoration[i].restore(entity);
			}
		}
	}

	/**
	 * Configures a restoration pulse for the given skill id.
	 *
	 * @param skillId The skill id.
	 */
	private void configureRestorationPulse(final int skillId) {
		restoration[skillId] = new SkillRestoration(skillId);
	}

	/**
	 * Copies the skills data.
	 *
	 * @param skills The skills.
	 */
	public void copy(Skills skills) {
		for (int i = 0; i < MAX_SKILL; i++) {
			this.staticLevels[i] = skills.staticLevels[i];
			this.dynamicLevels[i] = skills.dynamicLevels[i];
			this.experience[i] = skills.experience[i];
		}
		prayerPoints = skills.prayerPoints;
		lifepoints = skills.lifepoints;
		lifepointsIncrease = skills.lifepointsIncrease;
		experienceGained = skills.experienceGained;
	}

	/**
	 * Adds experience to a skill.
	 *
	 * @param slot       The skill slot.
	 * @param experience The experience.
	 */
	public void addExperience(int slot, double experience, boolean playerMod) {
		double mod = EXPERIENCE_MULTIPLIER;
		Player p = null;
		if (entity instanceof Player) {
			p = (Player) entity;
			if (entity.getViewport().getRegion().getRegionId() == 10536) {
				mod *= .5;
			}
			if (HolidayEvent.getCurrent() != null) {
				HolidayEvent.getCurrent().addExperience(p, slot, experience);
			}
			if (playerMod && p.getExperienceMod() != 0.0 && slot != MAGIC && slot != STRENGTH && slot != ATTACK && slot != DEFENCE && slot != HITPOINTS) {
				mod *= p.getExperienceMod();
			}
		}
		final double experienceAdd = (int) (experience * mod);//the experience with the mod.
		this.experience[slot] += experienceAdd;
		if (this.experience[slot] > 200000000) {
			this.experience[slot] = 200000000;
		}
		experienceGained = experienceGained + (int) experienceAdd;
		int newLevel = getStaticLevelByExperience(slot);
		if (newLevel > staticLevels[slot]) {
			if (dynamicLevels[slot] < newLevel) {
				dynamicLevels[slot] += newLevel - staticLevels[slot];
			}
			if (slot == HITPOINTS) {
				lifepoints += newLevel - staticLevels[slot];
			}
			staticLevels[slot] = newLevel;
			if (entity instanceof Player) {
				p = (Player) entity;
				if (updateCombatLevel()) {
					p.getUpdateMasks().register(new AppearanceFlag(p));
				}
				if (p.getInterfaceManager().hasQuestCompletionInterfaceOpened()) {
					delayedLevelUps.add(slot);
				} else {
					LevelUp.levelup(p, slot);
				}
			}
		}
		if (entity instanceof Player) {
			PacketRepository.send(SkillLevel.class, new SkillContext((Player) entity, slot));
		}
	}

	/**
	 * Adds experience to the skills.
	 */
	public void addExperience(final int slot, double experience) {
		addExperience(slot, experience, false);
	}

	/**
	 * Gets the highest combat skill id.
	 *
	 * @return The id of the highest combat skill.
	 */
	public int getHighestCombatSkill() {
		int id = 0;
		int last = 0;
		for (int i = 0; i < 5; i++) {
			if (staticLevels[i] > last) {
				last = staticLevels[i];
				id = i;
			}
		}
		return id;
	}

	/**
	 * Returns the dynamic levels to the static levels
	 */
	public void restore() {
		for (int i = 0; i < MAX_SKILL; i++) {
			int staticLevel = getStaticLevel(i);
			setLevel(i, staticLevel);
		}
		if (entity instanceof Player) {
			entity.asPlayer().getAudioManager().send(2674);
		}
		rechargePrayerPoints();
	}

	/**
	 * Parses the skill data from the buffer.
	 *
	 * @param buffer The byte buffer.
	 */
	public void parse(ByteBuffer buffer) {
		for (int i = 0; i < MAX_SKILL; i++) {
			experience[i] = ((double) buffer.getInt() / 10D);
			dynamicLevels[i] = buffer.get() & 0xFF;
			if (i == HITPOINTS) {
				lifepoints = dynamicLevels[i];
			} else if (i == PRAYER) {
				prayerPoints = dynamicLevels[i];
			}
			staticLevels[i] = buffer.get() & 0xFF;
		}
		experienceGained = buffer.getInt();
	}

	/**
	 * Saves the skill data on the buffer.
	 *
	 * @param buffer The byte buffer.
	 */
	public void save(ByteBuffer buffer) {
		for (int i = 0; i < MAX_SKILL; i++) {
			buffer.putInt((int) (experience[i] * 10));
			if (i == HITPOINTS) {
				buffer.put((byte) lifepoints);
			} else if (i == PRAYER) {
				buffer.put((byte) Math.ceil(prayerPoints));
			} else {
				buffer.put((byte) dynamicLevels[i]);
			}
			buffer.put((byte) staticLevels[i]);
		}
		buffer.putInt(experienceGained);
	}

	/**
	 * Refreshes all the skill levels.
	 */
	public void refresh() {
		if (!(entity instanceof Player)) {
			return;
		}
		Player player = (Player) entity;
		for (int i = 0; i < MAX_SKILL; i++) {
			PacketRepository.send(SkillLevel.class, new SkillContext(player, i));
		}
	}

	/**
	 * Gets the static level.
	 *
	 * @param slot The skill's slot.
	 * @return The level.
	 */
	public int getStaticLevelByExperience(int slot) {
		double exp = experience[slot];

		int points = 0;
		int output = 0;
		for (byte lvl = 1; lvl < 100; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if ((output - 1) >= exp) {
				return lvl;
			}
		}
		return 99;
	}

	/**
	 * Gets the experience for a certain level.
	 *
	 * @param level The level.
	 * @return The experience needed.
	 */
	public int getExperienceByLevel(int level) {
		int points = 0;
		int output = 0;
		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor(lvl + 300.0
					* Math.pow(2.0, lvl / 7.0));
			if (lvl >= level) {
				return output;
			}
			output = (int) Math.floor(points / 4);
		}
		return 0;
	}

	/**
	 * Updates the combat level.
	 *
	 * @return {@code True} if the combat level changed.
	 */
	@SuppressWarnings("deprecation")
	public boolean updateCombatLevel() {
		boolean update = false;
		int level = calculateCombatLevel();
		update = level != entity.getProperties().getCombatLevel();
        /*if (entity instanceof Player) {
			Player player = (Player) entity;
			int summon = staticLevels[SUMMONING] / 8;
			if (summon != player.getFamiliarManager().getSummoningCombatLevel()) {
				player.getFamiliarManager().setSummoningCombatLevel(summon);
				update = true;
			}
		}*/ //TODO MAKE SURE THIS IS OKAY TO BE GONE RYAN... LOL.
		entity.getProperties().setCombatLevel(level);
		return update;
	}

	/**
	 * Gets the combat level (ignoring summoning).
	 *
	 * @return The combat level.
	 */
	private int calculateCombatLevel() {
		if (entity instanceof NPC) {
			return ((NPC) entity).getDefinition().getCombatLevel();
		}
		int combatLevel = 0;
		int melee = staticLevels[ATTACK] + staticLevels[STRENGTH];
		int range = (int) (1.5 * staticLevels[RANGE]);
		int mage = (int) (1.5 * staticLevels[MAGIC]);
		if (melee > range && melee > mage) {
			combatLevel = melee;
		} else if (range > melee && range > mage) {
			combatLevel = range;
		} else {
			combatLevel = mage;
		}
		combatLevel = staticLevels[DEFENCE] + staticLevels[HITPOINTS] + (staticLevels[PRAYER] / 2) + (int) (1.3 * combatLevel);
		return combatLevel / 4;
	}

	/**
	 * @return the player
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * Gets the experience.
	 *
	 * @param slot The slot.
	 * @return The experience.
	 */
	public double getExperience(int slot) {
		return experience[slot];
	}

	/**
	 * Gets the static skill level.
	 *
	 * @param slot The slot.
	 * @return The static level.
	 */
	public int getStaticLevel(int slot) {
		return staticLevels[slot];
	}

	/**
	 * Sets the experience gained.
	 *
	 * @param experienceGained The experience gained.
	 */
	public void setExperienceGained(int experienceGained) {
		this.experienceGained = experienceGained;
	}

	/**
	 * Gets the experience gained.
	 *
	 * @return The experience gained.
	 */
	public int getExperienceGained() {
		return experienceGained;
	}

	/**
	 * Sets a dynamic level.
	 *
	 * @param slot  The skill id.
	 * @param level The level.
	 */
	public void setLevel(int slot, int level) {
		if (slot == HITPOINTS) {
			lifepoints = level;
		} else if (slot == PRAYER) {
			prayerPoints = level;
		}
		dynamicLevels[slot] = level;
		if (restoration[slot] != null) {
			restoration[slot].restart();
		}
		if (entity instanceof Player) {
			PacketRepository.send(SkillLevel.class, new SkillContext((Player) entity, slot));
		}
	}

	/**
	 * Gets a dynamic level.
	 *
	 * @param slot The skill id.
	 * @return The dynamic level.
	 */
	public int getLevel(int slot) {
		return dynamicLevels[slot];
	}

	/**
	 * Sets the current amount of lifepoints.
	 *
	 * @param lifepoints The lifepoints.
	 */
	public void setLifepoints(int lifepoints) {
		this.lifepoints = lifepoints;
		if (this.lifepoints < 0) {
			this.lifepoints = 0;
		}
		lifepointsUpdate = true;
	}

	/**
	 * Gets the lifepoints.
	 *
	 * @return The lifepoints.
	 */
	public int getLifepoints() {
		return lifepoints;
	}

	/**
	 * Gets the maximum amount of lifepoints.
	 *
	 * @return The maximum amount.
	 */
	public int getMaximumLifepoints() {
		return staticLevels[HITPOINTS] + lifepointsIncrease;
	}

	/**
	 * Sets the amount of lifepoints increase.
	 *
	 * @param amount The amount.
	 */
	public void setLifepointsIncrease(int amount) {
		this.lifepointsIncrease = amount;
	}

	/**
	 * Adds lifepoints to the entity.
	 *
	 * @param health The amount to add.
	 * @return The amount of overflow.
	 */
	public int heal(int health) {
		lifepoints += health;
		int left = 0;
		if (lifepoints > getMaximumLifepoints()) {
			left = lifepoints - getMaximumLifepoints();
			lifepoints = getMaximumLifepoints();
		}
		lifepointsUpdate = true;
		return left;
	}

	/**
	 * @param damage The amount to remove.
	 * @return The amount of overflow.
	 * @Deprecated Use {@link ImpactHandler#manualHit(Entity, int, ImpactHandler.HitsplatType)} or <br>
	 * the <b>hitsplat WILL NOT show and combat will be desynchronized!</b>
	 */
	public int hit(int damage) {
		lifepoints -= damage;
		int left = 0;
		if (lifepoints < 0) {
			left = -lifepoints;
			lifepoints = 0;
		}
		lifepointsUpdate = true;
		return left;
	}

	/**
	 * Gets the prayer points.
	 *
	 * @return The prayer points.
	 */
	public double getPrayerPoints() {
		return prayerPoints;
	}

	/**
	 * Recharges the prayer points.
	 */
	public void rechargePrayerPoints() {
		prayerPoints = staticLevels[PRAYER];
		if (entity instanceof Player) {
			PacketRepository.send(SkillLevel.class, new SkillContext((Player) entity, PRAYER));
		}
	}

	/**
	 * Updates the current amount of prayer points (by decrementing).
	 *
	 * @param amount The amount to decrement with.
	 */
	public void decrementPrayerPoints(double amount) {
		prayerPoints -= amount;
		if (prayerPoints < 0) {
			prayerPoints = 0;
		}
		//		if (prayerPoints > staticLevels[PRAYER]) {
		//			prayerPoints = staticLevels[PRAYER];
		//		} TODO IS THIS SUPPOSED TO BE OUT?
		if (entity instanceof Player) {
			PacketRepository.send(SkillLevel.class, new SkillContext((Player) entity, PRAYER));
		}
	}

	/**
	 * Updates the current amount of prayer points (by incrementing)
	 *
	 * @param amount The amount to decrement with.
	 */
	public void incrementPrayerPoints(double amount) {
		prayerPoints += amount;
		if (prayerPoints < 0) {
			prayerPoints = 0;
		}
		if (prayerPoints > staticLevels[PRAYER]) {
			prayerPoints = staticLevels[PRAYER];
		}
		if (entity instanceof Player) {
			PacketRepository.send(SkillLevel.class, new SkillContext((Player) entity, PRAYER));
		}
	}

	/**
	 * Sets the current prayer points (<b>without checking for being higher than max</b>)
	 *
	 * @param amount The amount.
	 */
	public void setPrayerPoints(double amount) {
		prayerPoints = amount;
		if (entity instanceof Player) {
			PacketRepository.send(SkillLevel.class, new SkillContext((Player) entity, PRAYER));
		}
	}

	/**
	 * Updates the current skill level (by incrementing the current amount with the given amount, up to the given maximum).
	 *
	 * @param skill   The skill id.
	 * @param amount  The amount to increment.
	 * @param maximum The maximum amount the skill can be.
	 * @return The amount of "overflow".
	 */
	public int updateLevel(int skill, int amount, int maximum) {
		if (amount > 0 && dynamicLevels[skill] > maximum) {
			return -amount;
		}
		int left = (dynamicLevels[skill] + amount) - maximum;
		int level = dynamicLevels[skill] += amount;
		if (level < 0) {
			dynamicLevels[skill] = 0;
		} else if (amount < 0 && level < maximum) {
			dynamicLevels[skill] = maximum;
		} else if (amount > 0 && level > maximum) {
			dynamicLevels[skill] = maximum;
		}
		if (restoration[skill] != null) {
			restoration[skill].restart();
		}
		if (entity instanceof Player) {
			PacketRepository.send(SkillLevel.class, new SkillContext((Player) entity, skill));
		}
		return left;
	}

	/**
	 * Updates a level.
	 *
	 * @param skill  the skill.
	 * @param amount the amount.
	 * @return the left.
	 */
	public int updateLevel(int skill, int amount) {
		return updateLevel(skill, amount, amount >= 0 ? getStaticLevel(skill) + amount : getStaticLevel(skill) - amount);
	}

	/**
	 * Drains a certain percentage of a level.
	 *
	 * @param skill                  The skill.
	 * @param drainPercentage        The drain percentage (0.05 indicates 5% drain).
	 * @param maximumDrainPercentage The maximum drain percentage (0.05 indicates 5%).
	 */
	public void drainLevel(int skill, double drainPercentage, double maximumDrainPercentage) {
		int drain = (int) (dynamicLevels[skill] * drainPercentage);
		int minimum = (int) (staticLevels[skill] * (1.0 - maximumDrainPercentage));
		updateLevel(skill, -drain, minimum);
	}

	/**
	 * Sets the static level.
	 *
	 * @param skill The skill id.
	 * @param level The level to set.
	 */
	public void setStaticLevel(int skill, int level) {
		experience[skill] = getExperienceByLevel(staticLevels[skill] = dynamicLevels[skill] = level);
		if (entity instanceof Player) {
			PacketRepository.send(SkillLevel.class, new SkillContext((Player) entity, skill));
		}
	}

	/**
	 * Gets the restoration pulses.
	 *
	 * @return The restoration pulse array.
	 */
	public SkillRestoration[] getRestoration() {
		return restoration;
	}

	/**
	 * Gets the amount of mastered skills.
	 *
	 * @return The amount of mastered skills.
	 */
	public int getMasteredSkills() {
		int count = 0;
		for (int i = 0; i < MAX_SKILL - 1; i++) { //TODO IS THIS OKAY?!? Was at 23 wtf.
			if (getStaticLevel(i) >= 99) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Method used to get the skill by the name.
	 *
	 * @param name the name.
	 * @return the skill.
	 */
	public static int getSkillByName(final String name) {
		for (int i = 0; i < SKILL_NAME.length; i++) {
			if (SKILL_NAME[i].equalsIgnoreCase(name)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Gets the total level.
	 *
	 * @return the total level.
	 */
	public int getTotalLevel() {
		int level = 0;
		for (int i = 0; i < MAX_SKILL; i++) {
			level += getStaticLevel(i);
		}
		return level;
	}

	/**
	 * Gets the lifepointsUpdate.
	 *
	 * @return The lifepointsUpdate.
	 */
	public boolean isLifepointsUpdate() {
		return lifepointsUpdate;
	}

	/**
	 * Sets the lifepointsUpdate.
	 *
	 * @param lifepointsUpdate The lifepointsUpdate to set.
	 */
	public void setLifepointsUpdate(boolean lifepointsUpdate) {
		this.lifepointsUpdate = lifepointsUpdate;
	}

	/**
	 * Gets the statis levels.
	 *
	 * @return the level.
	 */
	public int[] getStaticLevels() {
		return staticLevels;
	}

	/**
	 * Checks if the player has the required level.
	 *
	 * @param skillId the skill id.
	 * @param i       the level.
	 * @return {@code True} if so.
	 */
	public boolean hasLevel(int skillId, int i) {
		return getStaticLevel(skillId) >= i;
	}

	/**
	 * Gets the delayedLevelUps.
	 *
	 * @return the delayedLevelUps
	 */
	public Queue<Integer> getDelayedLevelUps() {
		return delayedLevelUps;
	}

}