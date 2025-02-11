package plugin.combat.special;

import org.arcanium.game.node.entity.Entity;
import org.arcanium.game.node.entity.combat.BattleState;
import org.arcanium.game.node.entity.combat.CombatStyle;
import org.arcanium.game.node.entity.combat.handlers.MeleeSwingHandler;
import org.arcanium.game.node.entity.impl.Animator;
import org.arcanium.game.node.entity.player.Player;
import org.arcanium.game.world.update.flag.context.Animation;
import org.arcanium.game.world.update.flag.context.Graphics;
import org.arcanium.plugin.Plugin;
import org.arcanium.tools.RandomFunction;

/**
 * Handles the puncture special attack combat swing.
 *
 * @author Emperor
 */
public final class PunctureSpecialHandler extends MeleeSwingHandler implements Plugin<Object> {

    /**
     * The special energy required.
     */
    private static final int SPECIAL_ENERGY = 25;

    /**
     * The attack animation.
     */
    private static final Animation ANIMATION = new Animation(1062, Animator.Priority.HIGH);

    /**
     * The graphic.
     */
    private static final Graphics GRAPHIC = new Graphics(252, 96);

    @Override
    public Object fireEvent(String identifier, Object... args) {
        return null;
    }

    @Override
    public void impact(Entity entity, Entity victim, BattleState state) {
        victim.getImpactHandler().handleImpact(entity, state.getEstimatedHit(), CombatStyle.MELEE, state);
        victim.getImpactHandler().handleImpact(entity, state.getSecondaryHit(), CombatStyle.MELEE, state, null, true);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        if (CombatStyle.MELEE.getSwingHandler().register(1215, this) && CombatStyle.MELEE.getSwingHandler().register(1231, this) && CombatStyle.MELEE.getSwingHandler().register(5680, this) && CombatStyle.MELEE.getSwingHandler().register(5698, this) && CombatStyle.MELEE.getSwingHandler().register(13265, this) && CombatStyle.MELEE.getSwingHandler().register(13267, this) && CombatStyle.MELEE.getSwingHandler().register(13269, this) && CombatStyle.MELEE.getSwingHandler().register(13271, this))
            ;
        return this;
    }

    @Override
    public int swing(Entity entity, Entity victim, BattleState state) {
        if (!((Player) entity).getSettings().drainSpecial(SPECIAL_ENERGY)) {
            return -1;
        }
        // First hit
        int hit = 0;
        if (isAccurateImpact(entity, victim, CombatStyle.MELEE, 1.2, 1.0)) {
            hit = RandomFunction.random(calculateHit(entity, victim, 1.1306));
        }
        state.setEstimatedHit(hit);
        // Second hit
        if (isAccurateImpact(entity, victim, CombatStyle.MELEE, 1.2, 1.0)) {
            hit = RandomFunction.random(calculateHit(entity, victim, 1.1306));
        } else {
            hit = 0;
        }
        entity.asPlayer().getAudioManager().send(2537);
        state.setSecondaryHit(hit);
        return 1;
    }

    @Override
    public void visualize(Entity entity, Entity victim, BattleState state) {
        entity.visualize(ANIMATION, GRAPHIC);
    }
}