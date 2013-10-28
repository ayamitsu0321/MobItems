package ayamitsu.mobitems.client;

import java.lang.reflect.Method;

import net.minecraftforge.client.MinecraftForgeClient;
import ayamitsu.mobitems.MobItems;
import ayamitsu.mobitems.Proxy;
import ayamitsu.mobitems.client.renderer.ItemMobRenderer;
import ayamitsu.mobitems.client.renderer.RenderingRegistry;

public class ClientProxy extends Proxy {

	@Override
	public void preLoad() {}

	@Override
	public void load() {
		MinecraftForgeClient.registerItemRenderer(MobItems.mobItem.itemID, new ItemMobRenderer());
	}

	@Override
	public void postLoad() {
		if (MobItems.addModsMobs) {
			try {
				Method method = RenderingRegistry.class.getDeclaredMethod("addRendererForMods", (Class[])null);
				method.setAccessible(true);
				method.invoke(null, (Object[])null);
				method.setAccessible(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
