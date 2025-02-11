package org.arcanium.game.content.skill.p2p.hunter;

import org.arcanium.game.content.skill.p2p.hunter.bnet.ImplingNode;
import org.arcanium.game.node.entity.Entity;
import org.arcanium.game.node.entity.combat.BattleState;
import org.arcanium.game.node.entity.combat.CombatStyle;
import org.arcanium.game.node.entity.npc.NPC;
import org.arcanium.game.node.entity.player.Player;
import org.arcanium.game.system.mysql.impl.NPCConfigSQLHandler;
import org.arcanium.game.system.task.Pulse;
import org.arcanium.game.world.GameWorld;
import org.arcanium.game.world.map.Location;
import org.arcanium.game.world.map.path.Pathfinder;
import org.arcanium.game.world.update.flag.context.Graphics;
import org.arcanium.tools.RandomFunction;

/**
 * Handles an impling npc.
 *
 * @author Vexia
 */
public final class ImplingNPC extends NPC {

    /**
     * The impling node.
     */
    private final ImplingNode impling;

    /**
     * Constructs a new {@code ImplingNPC} {@code Object}.
     */
    public ImplingNPC() {
        super(0, null);
        this.impling = null;
        this.setWalks(true);
        this.setWalkRadius(20);
    }

    /**
     * Constructs a new {@code ImplingNPC} {@code Object}.
     *
     * @param id       the id.
     * @param location the location.
     */
    public ImplingNPC(int id, Location location, ImplingNode impling) {
        super(id, location);
        this.impling = impling;
        if (impling != null) {
            this.getDefinition().getConfigurations().put(NPCConfigSQLHandler.RESPAWN_DELAY, impling.getRespawnTime());
        }
    }

    @Override
    public void handleTickActions() {
        if (!getLocks().isMovementLocked()) {
            if (isWalks() && !getPulseManager().hasPulseRunning() && nextWalk < GameWorld.getTicks()) {
                setNextWalk();
                Location l = getLocation().transform(-5 + RandomFunction.random(getWalkRadius()), -5 + RandomFunction.random(getWalkRadius()), 0);
                if (canMove(l)) {
                    Pathfinder.find(this, l, true, Pathfinder.PROJECTILE).walk(this);
                }
            }
        }
        if (RandomFunction.random(100) < 4) {
            sendChat("Tee hee!");
        }
        int nextTeleport = getAttribute("nextTeleport", -1);
        if (nextTeleport > -1 && GameWorld.getTicks() > nextTeleport) {
            setAttribute("nextTeleport", GameWorld.getTicks() + 600);
            graphics(new Graphics(590));
            GameWorld.submit(new Pulse(1) {
                @Override
                public boolean pulse() {
                    teleport(ImpetuousImpulses.LOCATIONS[RandomFunction.random(ImpetuousImpulses.LOCATIONS.length)]);
                    return true;
                }
            });
            return;
        }
    }

    @Override
    public boolean isAttackable(Entity entity, CombatStyle style) {
        if (style != CombatStyle.MAGIC) {
            return false;
        }
        if (entity.getProperties().getSpell().getSpellId() == 12 || entity.getProperties().getSpell().getSpellId() == 30 || entity.getProperties().getSpell().getSpellId() == 56) {
            return true;
        }
        return super.isAttackable(entity, style);
    }

    @Override
    public void handleDrops(Player p, Entity killer) {
        getProperties().setTeleportLocation(getProperties().getSpawnLocation());
    }

    @Override
    public void checkImpact(BattleState state) {
    }

    /**
     * Gets the impling.
     *
     * @return The impling.
     */
    public ImplingNode getImpling() {
        return impling;
    }

}
