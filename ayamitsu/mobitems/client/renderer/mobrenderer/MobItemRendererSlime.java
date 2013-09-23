package ayamitsu.mobitems.client.renderer.mobrenderer;

import java.lang.reflect.Method;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntitySlime;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import ayamitsu.mobitems.client.renderer.IMobItemRenderer;
import ayamitsu.util.reflect.Reflector;
import cpw.mods.fml.client.FMLClientHandler;

public class MobItemRendererSlime implements IMobItemRenderer {

	protected EntitySlime slime;

	@Override
	public boolean match(String name) {
		return name.equals("Slime");
	}

	@Override
	public void render(String name) {
		if (!(this.slime instanceof EntitySlime)) {
			this.slime = this.createSlimeInstance();
		}

		if (this.slime == null) {
			return;
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(0.0F, this.slime.yOffset, 0.0F);
		RenderManager.instance.renderEntityWithPosYaw(this.slime, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public EntitySlime createSlimeInstance() {
		EntitySlime slime = new EntitySlime(FMLClientHandler.instance().getClient().theWorld);
		this.setSlimeSize(slime, 2);
		return slime;
	}

	@SuppressWarnings("unchecked")// 1.4.6
	protected void setSlimeSize(EntitySlime entitySlime, int i) {
		try {
			Method method = Reflector.getMethod(net.minecraft.entity.monster.EntitySlime.class, Reflector.isRenameTable() ? "setSlimeSize" : "func_70799_a", Integer.TYPE);
			method.invoke(entitySlime, new Object[] { i });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
