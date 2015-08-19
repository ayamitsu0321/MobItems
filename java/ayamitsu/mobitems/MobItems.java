package ayamitsu.mobitems;

import ayamitsu.mobitems.item.ItemMob;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(
        modid = MobItems.MODID,
        name = MobItems.NAME,
        version = MobItems.VERSION
)
public class MobItems {

    public static final String MODID = "mobitems";
    public static final String NAME = "MobItems";
    public static final String VERSION = "1.0.0";

    @Mod.Instance("mobitems")
    public static MobItems instance;

    @SidedProxy(clientSide = "ayamitsu.mobitems.client.ClientProxy", serverSide = "ayamitsu.mobitems.server.ServerProxy")
    public static AbstractProxy proxy;

    public static CreativeTabs tabMobItem = new CreativeTabs("mobitems") {
        @Override
        public Item getTabIconItem() {
            return Items.egg;
        }
    };

    public static Item mobItem;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        mobItem = new ItemMob().setUnlocalizedName("mobitems.mobitem").setCreativeTab(tabMobItem);
        GameRegistry.registerItem(mobItem, "mobitem");
        this.proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        this.proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        this.proxy.postInit();
    }

}