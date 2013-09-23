package ayamitsu.mobitems.client.renderer.mobrenderer;

import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import cpw.mods.fml.client.FMLClientHandler;

public class MobItemRendererMagmaCube extends MobItemRendererSlime {

	@Override
	public boolean match(String name) {
		return name.equals("LavaSlime");
	}

	@Override
	public EntitySlime createSlimeInstance() {
		EntityMagmaCube slime = new EntityMagmaCube(FMLClientHandler.instance().getClient().theWorld);
		this.setSlimeSize(slime, 2);
		return slime;
	}
}
