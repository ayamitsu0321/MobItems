package ayamitsu.mobitems.client.renderer.mobrenderer;

import java.lang.reflect.Constructor;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import ayamitsu.mobitems.client.renderer.IMobItemRenderer;
import ayamitsu.util.reflect.Reflector;
import cpw.mods.fml.client.FMLClientHandler;

public class MobItemRendererNormal implements IMobItemRenderer {

	protected String name;
	protected Class mobClass;
	protected Entity mobInstance; // for entity rendering

	public MobItemRendererNormal(String name, Class mobClass) {
		this.name = name;
		this.mobClass = mobClass;
	}

	@Override
	public boolean match(String name) {
		return this.name.equals(name);
	}

	@Override
	public void render(String name) {
		if (this.mobInstance == null) {
			try {
				Constructor constructor = Reflector.getConstructor(this.mobClass, new Class[]{ World.class });
				this.mobInstance = (Entity)constructor.newInstance(FMLClientHandler.instance().getClient().theWorld);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		if (this.mobInstance == null) {
			return;
		}

		this.mobInstance.setWorld(FMLClientHandler.instance().getClient().theWorld);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(0.0F, this.mobInstance.yOffset, 0.0F);
		RenderManager.instance.renderEntityWithPosYaw(this.mobInstance, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
