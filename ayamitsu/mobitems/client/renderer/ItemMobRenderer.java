package ayamitsu.mobitems.client.renderer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import ayamitsu.mobitems.item.ItemMob;

public class ItemMobRenderer implements IItemRenderer {

	public ItemMobRenderer() {
	}

	@Override
	public boolean handleRenderType(ItemStack itemStack, ItemRenderType type) {
		if (!(itemStack.getItem() instanceof ItemMob) || type == ItemRenderType.INVENTORY) {
			return false;
		}

		return itemStack.hasTagCompound() && EntityList.stringToClassMapping.containsKey(itemStack.getTagCompound().getString("MobName"));
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack itemStack, ItemRendererHelper helper) {
		if (type == ItemRenderType.ENTITY) {
			switch (helper) {
				case ENTITY_ROTATION: return true;
				case ENTITY_BOBBING: return true;
			}
		} else if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.FIRST_PERSON_MAP) {
			return true;
		} else if (type == ItemRenderType.INVENTORY) {
			if (helper == ItemRendererHelper.INVENTORY_BLOCK) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data) {
		Entity entity = null;
		String entityName = itemStack.getTagCompound().getString("MobName");

		IMobItemRenderer renderer = RenderingRegistry.getRenderer(entityName);

		if (renderer != null) {
			GL11.glPushMatrix();

			if (type == ItemRenderType.EQUIPPED) {
				GL11.glRotatef(-180F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(-0.5F, 0.0F, -0.75F);
			}

			renderer.render(entityName);
			GL11.glPopMatrix();
		}
	}

}
