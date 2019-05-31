package com.lwukusick.custom_painting.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.commons.lang3.Validate;

public class EntityCustomPainting extends EntityHanging { //implements IEntityAdditionalSpawnData {

    @ObjectHolder("custom_painting:custom_painting")
    public static final EntityType<EntityCustomPainting> paintingEntity = null;

    @ObjectHolder("custom_painting:custom_painting")
    public static final Item paintingItem = null;

    public EntityCustomPainting(World worldIn) {
        super(paintingEntity, worldIn);
    }

    public EntityCustomPainting(World worldIn, BlockPos pos, EnumFacing facing) {
        super(paintingEntity, worldIn, pos);
        this.updateFacingWithBoundingBox(facing);
        LOGGER.info("Created painting entity - long");
        LOGGER.info("Facing: " + facing.getName());
        LOGGER.info(this.getBoundingBox().minX + " " + this.getBoundingBox().minY + " " + this.getBoundingBox().minZ);
        LOGGER.info(this.getBoundingBox().maxX + " " + this.getBoundingBox().maxY + " " + this.getBoundingBox().maxZ);
   }

   @Override
    protected void updateFacingWithBoundingBox(EnumFacing facingDirectionIn) {
        Validate.notNull(facingDirectionIn);
        Validate.isTrue(facingDirectionIn.getAxis().isHorizontal());
        this.facingDirection = facingDirectionIn;
        this.rotationYaw = (float)(this.facingDirection.getHorizontalIndex() * 90);
        this.prevRotationYaw = this.rotationYaw;
        this.updateBoundingBox();
    }

    @Override
    public int getWidthPixels() {
        return 16;
    }

    @Override
    public int getHeightPixels() {
        return 16;
    }

    @Override
    public void onBroken(Entity brokenEntity) {
        if (this.world.getGameRules().getBoolean("doEntityDrops")) {
            this.playSound(SoundEvents.ENTITY_PAINTING_BREAK, 1.0F, 1.0F);
            if (brokenEntity instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer)brokenEntity;
                if (entityplayer.abilities.isCreativeMode) {
                    return;
                }
            }

            this.entityDropItem(paintingItem);
        }
    }

    @Override
    public void playPlaceSound() {
        this.playSound(SoundEvents.ENTITY_PAINTING_PLACE, 1.0F, 1.0F);
    }

    @Override
    public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
        this.setPosition(x, y, z);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        BlockPos blockpos = this.hangingPosition.add(x - this.posX, y - this.posY, z - this.posZ);
        this.setPosition((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ());
    }

    @Override
    public void setBoundingBox(AxisAlignedBB bb){
        LOGGER.info("Updating bounding box:\n"+
        bb.minX + " " + bb.minY + " " + bb.minZ + "\n"+
        bb.maxX + " " + bb.maxY + " " + bb.maxZ);
        super.setBoundingBox(bb);
    }

    /*
   @Override
   public void writeSpawnData(PacketBuffer buffer) {
       buffer.writeVarInt(this.getEntityId());
       buffer.writeUniqueId(this.getUniqueID());
       buffer.writeBlockPos(this.getPosition());
       buffer.writeByte(this.facingDirection.getHorizontalIndex());
   }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        this.setEntityId(additionalData.readVarInt());
        this.setUniqueId(additionalData.readUniqueId());
        BlockPos pos = additionalData.readBlockPos();
        this.setPosition(pos.getX(), pos.getY(), pos.getZ());
        this.facingDirection = EnumFacing.byHorizontalIndex(additionalData.readUnsignedByte());
    }
     */
}
