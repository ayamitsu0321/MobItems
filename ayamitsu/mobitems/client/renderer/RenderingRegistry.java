package ayamitsu.mobitems.client.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import ayamitsu.mobitems.client.renderer.mobrenderer.MobItemRendererGhast;
import ayamitsu.mobitems.client.renderer.mobrenderer.MobItemRendererMagmaCube;
import ayamitsu.mobitems.client.renderer.mobrenderer.MobItemRendererNormal;
import ayamitsu.mobitems.client.renderer.mobrenderer.MobItemRendererSlime;
import ayamitsu.mobitems.client.renderer.mobrenderer.MobItemRendererSquid;
import ayamitsu.util.entity.EntityUtils;
import ayamitsu.util.reflect.Reflector;
import cpw.mods.fml.client.FMLClientHandler;

public final class RenderingRegistry {

	private static final Set<String> nameList = new HashSet<String>();
	private static final List<IMobItemRenderer> renderers = new ArrayList<IMobItemRenderer>();

	public static void registerRenderer(String name, IMobItemRenderer renderer) {
		if (name != null) {
			nameList.add(name);
		}

		if (renderer != null) {
			renderers.add(renderer);
		}
	}

	public static String[] getNames() {
		return nameList.toArray(new String[0]);
	}

	public static IMobItemRenderer getRenderer(String name) {
		for (IMobItemRenderer renderer : renderers) {
			if (renderer.match(name)) {
				return renderer;
			}
		}

		return MobItemRendererFlexible.singleton;
	}

	private static class MobItemRendererFlexible implements IMobItemRenderer {

		public static MobItemRendererFlexible singleton = new MobItemRendererFlexible();
		private static Map<String, Entity> entityMapping = new HashMap<String, Entity>();

		public boolean match(String name) {
			return name != null;
		}

		public void render(String name) {
			Entity entity = entityMapping.get(name);

			if (entity == null) {
				Class entityClass = (Class)EntityUtils.getStringToClassMapping().get(name);

				if (entityClass == null) {
					return;
				}

				try {
					entity = (Entity)Reflector.getConstructor(entityClass, new Class[]{ World.class }).newInstance(FMLClientHandler.instance().getClient().theWorld);
					entityMapping.put(name, entity);
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
			}

			if (entity == null) {
				return;
			}

			entity.setWorld(FMLClientHandler.instance().getClient().theWorld);

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef(0.0F, entity.yOffset, 0.0F);
			RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	static {
		registerRenderer("Zombie", new MobItemRendererNormal("Zombie", EntityZombie.class));
		registerRenderer("Skeleton", new MobItemRendererNormal("Skeleton", EntitySkeleton.class));
		registerRenderer("Creeper", new MobItemRendererNormal("Creeper", EntityCreeper.class));
		registerRenderer("Spider", new MobItemRendererNormal("Spider", EntitySpider.class));
		registerRenderer("CaveSpider", new MobItemRendererNormal("CaveSpider", EntityCaveSpider.class));
		registerRenderer("Enderman", new MobItemRendererNormal("Enderman", EntityEnderman.class));
		registerRenderer("PigZombie", new MobItemRendererNormal("PigZombie", EntityPigZombie.class));
		registerRenderer("Blaze", new MobItemRendererNormal("Blaze", EntityBlaze.class));
		registerRenderer("Silverfish", new MobItemRendererNormal("Silverfish", EntitySilverfish.class));
		registerRenderer("Cow", new MobItemRendererNormal("Cow", EntityCow.class));
		registerRenderer("MushroomCow", new MobItemRendererNormal("MushroomCow", EntityMooshroom.class));
		registerRenderer("Pig", new MobItemRendererNormal("Pig", EntityPig.class));
		registerRenderer("Sheep", new MobItemRendererNormal("Sheep", EntitySheep.class));
		registerRenderer("Chicken", new MobItemRendererNormal("Chicken", EntityChicken.class));
		registerRenderer("Wolf", new MobItemRendererNormal("Wolf", EntityWolf.class));
		registerRenderer("Ozelot", new MobItemRendererNormal("Ozelot", EntityOcelot.class));
		registerRenderer("Villager", new MobItemRendererNormal("Villager", EntityVillager.class));
		registerRenderer("Witch", new MobItemRendererNormal("Witch", EntityWitch.class));
		registerRenderer("VillagerGolem", new MobItemRendererNormal("VillagerGolem", EntityIronGolem.class));
		registerRenderer("SnowMan", new MobItemRendererNormal("SnowMan", EntitySnowman.class));
		registerRenderer("Ghast", new MobItemRendererGhast());
		registerRenderer("Squid", new MobItemRendererSquid());
		registerRenderer("Slime", new MobItemRendererSlime());
		registerRenderer("LavaSlime", new MobItemRendererMagmaCube());
	}

	// call on MobItems#postInit
	private static void addRendererForMods() {
		Class clazz;
		String name;

		for (Map.Entry<Class, String> entry : (Set<Map.Entry<Class, String>>)EntityUtils.getClassToStringMapping().entrySet()) {
			clazz = entry.getKey();
			name = entry.getValue();

			if (EntityUtils.isLivingClass(clazz) && !nameList.contains(name)) {
				registerRenderer(name, null);
			}
		}
	}

}
