package org.arcanium.game.world.update.flag.chunk;

import org.arcanium.game.node.entity.Entity;
import org.arcanium.game.node.entity.player.Player;
import org.arcanium.game.node.item.GroundItem;
import org.arcanium.game.node.item.GroundItemManager;
import org.arcanium.game.world.update.flag.UpdateFlag;
import org.arcanium.net.packet.IoBuffer;
import org.arcanium.net.packet.server.UpdateGroundItemAmount;

/**
 * Handles an item update flag.
 *
 * @author Emperor
 */
public final class ItemUpdateFlag extends UpdateFlag<Object> {

    /**
     * The construct update type.
     */
    public static final int CONSTRUCT_TYPE = 0;

    /**
     * The remove update type.
     */
    public static final int REMOVE_TYPE = 1;

    /**
     * The update amount update type.
     */
    public static final int UPDATE_AMOUNT_TYPE = 2;

    /**
     * The ground item to update.
     */
    private final GroundItem item;

    /**
     * The update type
     */
    private final int type;

    /**
     * The old amount.
     */
    private int oldAmount;

    /**
     * Constructs a new {@code ItemContext} {@code Object}.
     *
     * @param item The ground item to update.
     * @param type The update type.
     */
    public ItemUpdateFlag(GroundItem item, int type) {
        this(item, type, 0);
    }

    /**
     * Constructs a new {@code ItemContext} {@code Object}.
     *
     * @param item The ground item to update.
     * @param type The update type.
     */
    public ItemUpdateFlag(GroundItem item, int type, int oldAmount) {
        super(null);
        this.item = item;
        this.type = type;
        this.oldAmount = oldAmount;
    }

    @Override
    public void writeDynamic(IoBuffer buffer, Entity e) {
        if (!isRemove() && item.droppedBy((Player) e)) {
            return;
        }
        if (e.getName().equals("1")) {
            // System.out.println("Updating... " + type);
        }
        write(buffer);
    }

    @Override
    public void write(IoBuffer buffer) {
        if (isRemove()) {
            //ClearGroundItem.write(buffer, item);
            GroundItemManager.destroy(item);
        } else if (isConstruct()) {
            //ConstructGroundItem.write(buffer, item);
            GroundItemManager.create(item, item.getLocation());
        } else {
            UpdateGroundItemAmount.write(buffer, item, oldAmount);
        }
    }

    /**
     * Checks if we're removing the ground item.
     *
     * @return {@code True} if so.
     */
    public boolean isRemove() {
        return type == REMOVE_TYPE;
    }

    /**
     * Checks if we're constructing the ground item.
     *
     * @return {@code True} if so.
     */
    public boolean isConstruct() {
        return type == CONSTRUCT_TYPE;
    }

    /**
     * Checks if we're removing the ground item.
     *
     * @return {@code True} if so.
     */
    public boolean isAmountUpdate() {
        return type == UPDATE_AMOUNT_TYPE;
    }

    @Override
    public int data() {
        return 0;
    }

    @Override
    public int ordinal() {
        return 1;
    }

}