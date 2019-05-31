package com.lwukusick.custom_painting.item;

import com.lwukusick.custom_painting.entity.EntityCustomPainting;
import org.jetbrains.annotations.Nullable;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCustomPainting extends Item{

    public ItemCustomPainting(Item.Properties builder) {
        super(builder);
    }

    /**
     * Called when this item is used when targetting a Block
     */
    public EnumActionResult onItemUse(ItemUseContext context) {
        BlockPos blockpos = context.getPos();
        EnumFacing enumfacing = context.getFace();
        BlockPos blockpos1 = blockpos.offset(enumfacing);
        EntityPlayer entityplayer = context.getPlayer();
        if (entityplayer != null && !this.canPlace(entityplayer, enumfacing, context.getItem(), blockpos1)) {
            return EnumActionResult.FAIL;
        } else {
            World world = context.getWorld();
            EntityHanging entityhanging = this.createEntity(world, blockpos1, enumfacing);
            if (entityhanging != null && entityhanging.onValidSurface()) {
                if (!world.isRemote) {
                    entityhanging.playPlaceSound();
                    world.spawnEntity(entityhanging);
                }

                context.getItem().shrink(1);
            }

            return EnumActionResult.SUCCESS;
        }
    }

    protected boolean canPlace(EntityPlayer player, EnumFacing facing, ItemStack itemStack, BlockPos pos) {
        return !facing.getAxis().isVertical() && player.canPlayerEdit(pos, facing, itemStack);
    }

    @Nullable
    private EntityHanging createEntity(World worldIn, BlockPos pos, EnumFacing clickedSide) {
        return new EntityCustomPainting(worldIn, pos, clickedSide);
    }
}
