
package com.obscuria.aquamirae.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class GhostParticle {

	@OnlyIn(Dist.CLIENT)
	public static class Instance extends SpriteTexturedParticle {
		private final IAnimatedSprite spriteSet;

		protected Instance(ClientWorld world, double x, double y, double z, double vx, double vy, double vz, IAnimatedSprite spriteSet) {
			super(world, x, y, z);
			this.spriteSet = spriteSet;
			this.setSize((float) 0.2, (float) 0.2);
			this.quadSize *= (float) 1.2999999999999998;
			this.lifetime = 40;
			this.gravity = (float) -0.3;
			this.hasPhysics = false;
			this.xd = vx * 0.1;
			this.yd = vy * 0.1;
			this.zd = vz * 0.1;
			this.setSpriteFromAge(spriteSet);
		}

		@Override
		protected int getLightColor(float partialTick) {
			return 15728880;
		}

		@Override
		@Nonnull
		public IParticleRenderType getRenderType() {
			return IParticleRenderType.PARTICLE_SHEET_LIT;
		}

		@Override
		public void tick() {
			super.tick();
			if (this.isAlive()) this.setSprite(this.spriteSet.get((this.age / 3) % 12 + 1, 12));
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;

		public Factory(IAnimatedSprite spriteSet) {
			this.spriteSet = spriteSet;
		}

		public Particle createParticle(@Nonnull BasicParticleType typeIn, @Nonnull ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed,
									 double zSpeed) {
			return new Instance(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
		}
	}
}
