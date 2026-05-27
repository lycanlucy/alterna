package io.github.lycanlucy.alterna.common.entity;

import io.github.lycanlucy.alterna.registry.AlternaDataComponents;
import io.github.lycanlucy.alterna.registry.AlternaEntities;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class AlternaThrownTrident extends AbstractArrow {
    private static final EntityDataAccessor<Byte> ID_LOYALTY = SynchedEntityData.defineId(AlternaThrownTrident.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<ItemStack> ID_ITEM_STACK = SynchedEntityData.defineId(AlternaThrownTrident.class, EntityDataSerializers.ITEM_STACK);
    private boolean dealtDamage;
    public int clientSideReturnTridentTickCount;

    public AlternaThrownTrident(EntityType<? extends AlternaThrownTrident> entityType, Level level) {
        super(entityType, level);
    }

    public AlternaThrownTrident(Level level, LivingEntity shooter, ItemStack pickupItemStack) {
        super(AlternaEntities.TRIDENT.get(), shooter, level, pickupItemStack, null);
        this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(pickupItemStack));
        this.entityData.set(ID_ITEM_STACK, pickupItemStack.copy());
    }

    public AlternaThrownTrident(Level level, double x, double y, double z, ItemStack pickupItemStack) {
        super(AlternaEntities.TRIDENT.get(), x, y, z, level, pickupItemStack, pickupItemStack);
        this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(pickupItemStack));
        this.entityData.set(ID_ITEM_STACK, pickupItemStack.copy());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ID_LOYALTY, (byte) 0);
        builder.define(ID_ITEM_STACK, new ItemStack(AlternaItems.TRIDENT.get()));
    }

    public void setItemStack(ItemStack itemStack) {
        this.entityData.set(ID_ITEM_STACK, itemStack);
    }

    public ItemStack getItemStack() {
        return this.entityData.get(ID_ITEM_STACK);
    }

    public boolean returnsToOwnerAfterLanding() {
        return !this.getItemStack().is(AlternaItems.SUNKEN_TRIDENT);
    }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity entity = this.getOwner();
        int returnSpeed = returnsToOwnerAfterLanding() ? this.entityData.get(ID_LOYALTY) + 1 : 0;
        if (returnSpeed > 0 && (this.dealtDamage || this.isNoPhysics()) && entity != null) {
            if (!this.isAcceptableReturnOwner()) {
                if (!this.level().isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 vec3 = entity.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015 * (double) returnSpeed, this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }

                double d0 = 0.05 * (double) returnSpeed;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95).add(vec3.normalize().scale(d0)));
                if (this.clientSideReturnTridentTickCount == 0) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }

                this.clientSideReturnTridentTickCount++;
            }
        }

        super.tick();
    }

    private boolean isAcceptableReturnOwner() {
        Entity entity = this.getOwner();
        return entity != null && entity.isAlive() && (!(entity instanceof ServerPlayer) || !entity.isSpectator());
    }

    @Nullable
    @Override
    protected EntityHitResult findHitEntity(@NotNull Vec3 startVec, @NotNull Vec3 endVec) {
        return this.dealtDamage ? null : super.findHitEntity(startVec, endVec);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        float baseDamage = 8.0F;
        Entity owner = this.getOwner();
        DamageSource damageSource = this.damageSources().trident(this, owner == null ? this : owner);
        if (this.level() instanceof ServerLevel serverLevel) {
            baseDamage = this.getWeaponItem().getOrDefault(AlternaDataComponents.PROJECTILE_BASE_DAMAGE, 8.0F);
            baseDamage = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), entity, damageSource, baseDamage);
        }

        this.dealtDamage = true;
        if (entity.hurt(damageSource, baseDamage)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (this.level() instanceof ServerLevel serverLevel) {
                EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, entity, damageSource, this.getWeaponItem());
            }

            if (entity instanceof LivingEntity livingEntity) {
                this.doKnockback(livingEntity, damageSource);
                this.doPostHurtEffects(livingEntity);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        this.playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.0F);
    }

    @Override
    protected void hitBlockEnchantmentEffects(@NotNull ServerLevel level, BlockHitResult hitResult, @NotNull ItemStack stack) {
        Vec3 hitLocation = hitResult.getBlockPos().clampLocationWithin(hitResult.getLocation());
        EnchantmentHelper.onHitBlock(level, stack, this.getOwner() instanceof LivingEntity livingentity ? livingentity : null, this, null, hitLocation, level.getBlockState(hitResult.getBlockPos()), item -> this.kill());
    }

    @Override
    public @NotNull ItemStack getWeaponItem() {
        return this.getPickupItemStackOrigin();
    }

    @Override
    protected boolean tryPickup(@NotNull Player player) {
        return super.tryPickup(player) || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
    }

    @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return new ItemStack(AlternaItems.TRIDENT.get());
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public void playerTouch(@NotNull Player entity) {
        if (this.ownedBy(entity) || this.getOwner() == null) {
            super.playerTouch(entity);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.dealtDamage = compound.getBoolean("DealtDamage");
        this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(this.getPickupItemStackOrigin()));
        if (compound.contains("ItemStack", 10)) {
            this.setItemStack(ItemStack.parse(this.registryAccess(), compound.getCompound("ItemStack")).orElse(this.getDefaultPickupItem()));
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("DealtDamage", this.dealtDamage);
        compound.put("ItemStack", this.getItemStack().save(this.registryAccess()));
    }

    private byte getLoyaltyFromItem(ItemStack stack) {
        return this.level() instanceof ServerLevel serverlevel ? (byte) Mth.clamp(EnchantmentHelper.getTridentReturnToOwnerAcceleration(serverlevel, stack, this), 0, 127) : 0;
    }

    @Override
    public void tickDespawn() {
        if (this.pickup != AbstractArrow.Pickup.ALLOWED || !this.returnsToOwnerAfterLanding()) {
            super.tickDespawn();
        }
    }

    @Override
    protected float getWaterInertia() {
        return 0.99F;
    }
}
