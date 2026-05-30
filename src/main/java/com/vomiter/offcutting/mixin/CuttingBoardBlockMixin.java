package com.vomiter.offcutting.mixin;

import com.vomiter.offcutting.common.OffCuttingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;

@Mixin(CuttingBoardBlock.class)
public class CuttingBoardBlockMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void offcutting$use(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit,
            CallbackInfoReturnable<InteractionResult> cir
    ){
        if(level.getBlockEntity(pos) instanceof CuttingBoardBlockEntity cuttingBoard){
            if(cuttingBoard.isItemCarvingBoard()) return; // item carving board return to vanilla
            if(cuttingBoard.isEmpty()){
                ItemStack mainHandItem = player.getMainHandItem();
                ItemStack offHandItem = player.getOffhandItem();
                InteractionHand handWithStackToPut;
                ItemStack stackToPut;
                if(OffCuttingUtil.hasCuttingRecipe(level, mainHandItem)){
                    stackToPut = mainHandItem;
                    handWithStackToPut = InteractionHand.MAIN_HAND;
                } else if(OffCuttingUtil.hasCuttingRecipe(level, offHandItem)){
                    stackToPut = offHandItem;
                    handWithStackToPut = InteractionHand.OFF_HAND;
                } else {
                    stackToPut = ItemStack.EMPTY;
                    handWithStackToPut = null;
                }

                if(!stackToPut.isEmpty() && cuttingBoard.canAddItem(stackToPut) && handWithStackToPut != null){
                    if(level.isClientSide()){
                        player.swing(handWithStackToPut);
                        cir.setReturnValue(InteractionResult.CONSUME);
                    } else {
                        ItemStack remainder = cuttingBoard.addItem(stackToPut);
                        if(!player.getAbilities().instabuild) stackToPut.setCount(remainder.getCount());
                        cir.setReturnValue(InteractionResult.CONSUME);
                    }
                }
            }

        }

    }
}
