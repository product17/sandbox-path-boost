package io.sandbox.path.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.sandbox.path.Main;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class LivingEntityMixin extends LivingEntity {
  protected LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
    super(entityType, world);
  }

  @Inject(at = @At("HEAD"), method = "tickMovement", cancellable = true)
  public void tickMovement(CallbackInfo cb) {
    // addSoulSpeedBoostIfNeeded is only called server side, so no client check
    Vec3d pos = this.getPos();
    BlockPos blockPos = new BlockPos(Math.floor(pos.x), Math.floor(pos.y), Math.floor(pos.z));
    BlockState blockState = this.hasVehicle() ?
      this.world.getBlockState(blockPos.down()) :
      this.world.getBlockState(blockPos);

    if (!blockState.isAir() && blockState.isOf(Blocks.DIRT_PATH)) {
      int boost = 0;
      if (this.hasVehicle()) {
        boost = 1;
      }

      this.addStatusEffect(new StatusEffectInstance(Main.PATH, 60, boost));
    }
  }
}

// @Mixin(LivingEntity.class)
// public abstract class LivingEntityMixin extends Entity {
//   @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

//   public LivingEntityMixin(EntityType<?> type, World world) {
//     super(type, world);
//   }

//   public int count = 0;

//   @Inject(at = @At("HEAD"), method = "addSoulSpeedBoostIfNeeded", cancellable = true)
//   public void addSoulSpeedBoostIfNeeded(CallbackInfo cb) {
//     // addSoulSpeedBoostIfNeeded is only called server side, so no client check
//     BlockState blockState = this.getLandingBlockState();
//     if (!blockState.isAir() && blockState.isOf(Blocks.DIRT_PATH)) {
//       System.out.println("on Block of: Path");
//       this.addStatusEffect(new StatusEffectInstance(Main.PATH));
//     }
//   }
// }
