package com.vomiter.offcutting.mixin;

import com.vomiter.offcutting.Config;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;

@Mixin(value = CuttingBoardBlockEntity.class, remap = false)
public class CuttingBoardBlockEntityMixin {
    @Shadow
    private boolean isItemCarvingBoard;

    @Shadow
    @Final
    private ItemStackHandler inventory;

    @Inject(method = "addItem", at = @At("HEAD"), cancellable = true)
    private void offcutting$addItem(ItemStack addedStack, CallbackInfoReturnable<ItemStack> cir){
        if (addedStack.getMaxStackSize() <= Config.CUTTING_BOARD_INSERT_COUNT) return;
        if (isItemCarvingBoard) return;
        int insertCount = Math.min(Config.CUTTING_BOARD_INSERT_COUNT, addedStack.getCount());
        ItemStack remainder = inventory.insertItem(0, addedStack.copyWithCount(insertCount), false);
        cir.setReturnValue(addedStack.copyWithCount(addedStack.getCount() - insertCount + remainder.getCount()));
    }
}
