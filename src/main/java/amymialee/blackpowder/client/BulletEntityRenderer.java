package amymialee.blackpowder.client;

import amymialee.blackpowder.guns.BulletEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BulletEntityRenderer extends ProjectileEntityRenderer<BulletEntity> {
    public static final Identifier TEXTURE = new Identifier("textures/entity/arrowd.png");

    public BulletEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public Identifier getTexture(BulletEntity entity) {
        return TEXTURE;
    }
}
