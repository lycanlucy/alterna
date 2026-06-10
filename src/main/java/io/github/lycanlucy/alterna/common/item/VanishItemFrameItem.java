package io.github.lycanlucy.alterna.common.item;

import io.github.lycanlucy.alterna.common.entity.VanishItemFrame;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class VanishItemFrameItem extends Item {
    public VanishItemFrameItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos clickedPos = context.getClickedPos();
        Direction clickedFace = context.getClickedFace();
        BlockPos relative = clickedPos.relative(clickedFace);
        Player player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        
        if (player != null && !this.mayPlace(player, clickedFace, itemStack, relative)) {
            return InteractionResult.FAIL;
        } else {
            Level level = context.getLevel();
            VanishItemFrame itemFrame = new VanishItemFrame(level, relative, clickedFace);

            CustomData customData = itemStack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
            if (!customData.isEmpty()) {
                EntityType.updateCustomEntityTag(level, player, itemFrame, customData);
            }

            if (itemFrame.survives()) {
                if (!level.isClientSide) {
                    itemFrame.playPlacementSound();
                    level.gameEvent(player, GameEvent.ENTITY_PLACE, itemFrame.position());
                    level.addFreshEntity(itemFrame);
                }

                itemStack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.CONSUME;
            }
        }
    }

    protected boolean mayPlace(Player player, Direction direction, ItemStack itemStack, BlockPos pos) {
        return !player.level().isOutsideBuildHeight(pos) && player.mayUseItemAt(pos, direction, itemStack);
    }
}
