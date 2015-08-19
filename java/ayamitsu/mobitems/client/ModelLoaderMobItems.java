package ayamitsu.mobitems.client;

import ayamitsu.mobitems.MobItems;
import ayamitsu.mobitems.client.model.item.ModelMobItem;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

import java.io.IOException;

/**
 * Created by ayamitsu0321 on 2015/08/17.
 */
public class ModelLoaderMobItems implements ICustomModelLoader {
    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getResourceDomain().equals(MobItems.MODID);
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws IOException {
        return new ModelMobItem();
    }

    @Override
    public void onResourceManagerReload(IResourceManager p_110549_1_) {
    }
}
