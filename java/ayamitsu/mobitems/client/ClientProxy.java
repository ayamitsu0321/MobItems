package ayamitsu.mobitems.client;

import ayamitsu.mobitems.AbstractProxy;
import ayamitsu.mobitems.MobItems;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;

/**
 * Created by ayamitsu0321 on 2015/08/17.
 */
public class ClientProxy extends AbstractProxy {

    public void preInit() {
        ModelLoader.setCustomMeshDefinition(MobItems.mobItem, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(new ResourceLocation(MobItems.MODID, "mobitem"), "inventory");
            }
        });

        ModelLoaderRegistry.registerLoader(new ModelLoaderMobItems());
    }

}
