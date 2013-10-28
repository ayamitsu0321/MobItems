package ayamitsu.mobitems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.Configuration;
import ayamitsu.mobitems.item.ItemMob;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(
	modid = "ayamitsu.mobitems",
	name = "MobItems",
	version = "1.1.0"
)
@NetworkMod(
	clientSideRequired = true,
	serverSideRequired = false
)
public class MobItems {

	@Mod.Instance("ayamitsu.mobitems")
	public static MobItems instance;

	@SidedProxy(clientSide = "ayamitsu.mobitems.client.ClientProxy", serverSide = "ayamitsu.mobitems.server.ServerProxy")
	public static Proxy proxy;

	public static Item mobItem;

	public static CreativeTabs mobItemsTab = new CreativeTabs("MobItems");

	public static boolean addModsMobs;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration conf = new Configuration(event.getSuggestedConfigurationFile());
		conf.load();

		int mobItemId = conf.getItem("mobItemId", 12500).getInt();
		this.addModsMobs = conf.get("global", "addModsMobs", true).getBoolean(true);

		conf.save();

		this.mobItem = new ItemMob(mobItemId).setUnlocalizedName("ayamitsu.mobitems.mobItem").setTextureName("mobitems:itemmob").setCreativeTab(this.mobItemsTab);

		LanguageRegistry.instance().addNameForObject(this.mobItem, "en_US", "MobItem");
		this.proxy.preLoad();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		this.proxy.load();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		this.proxy.postLoad();
	}

	public static ItemStack convertMobToItem(Entity entity) {
		ItemStack itemStack = new ItemStack(mobItem.itemID, 1, 0);
		itemStack.setTagCompound(convertItemNBT(entity));
		return itemStack;
	}

	public static ItemStack convertMobToItem(String mobName) {
		ItemStack itemStack = new ItemStack(mobItem.itemID, 1, 0);
		itemStack.setTagCompound(convertItemNBT(mobName));
		return itemStack;
	}

	public static NBTTagCompound convertItemNBT(Entity entity) {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setString("MobName", entity.getEntityName());
		return nbttagcompound;
	}

	public static NBTTagCompound convertItemNBT(String mobName) {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setString("MobName", mobName);
		return nbttagcompound;
	}

}
