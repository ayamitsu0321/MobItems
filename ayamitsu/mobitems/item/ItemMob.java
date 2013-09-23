package ayamitsu.mobitems.item;

import java.util.List;

import ayamitsu.mobitems.MobItems;
import ayamitsu.mobitems.client.renderer.RenderingRegistry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class ItemMob extends Item {

	public ItemMob(int id) {
		super(id);
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advancedItemTooltips) {
		if (!itemStack.hasTagCompound()) {
			return;
		}

		String mobName = itemStack.getTagCompound().getString("MobName");

		if (mobName != null) {
			list.add(StatCollector.translateToLocal("entity." + mobName + ".name"));
		}
	}

	@Override
	public void getSubItems(int id, CreativeTabs tab, List list) {
		ItemStack itemStack;

		for (String name : RenderingRegistry.getNames()) {
			itemStack = MobItems.convertMobToItem(name);
			list.add(itemStack);
		}

	}

}
