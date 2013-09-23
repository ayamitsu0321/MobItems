package ayamitsu.mobitems.client;

import net.minecraftforge.client.MinecraftForgeClient;
import ayamitsu.mobitems.MobItems;
import ayamitsu.mobitems.Proxy;
import ayamitsu.mobitems.client.renderer.ItemMobRenderer;

public class ClientProxy extends Proxy {

	@Override
	public void load() {
		MinecraftForgeClient.registerItemRenderer(MobItems.mobItem.itemID, new ItemMobRenderer());
	}

}
