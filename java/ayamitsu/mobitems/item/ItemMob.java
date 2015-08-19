package ayamitsu.mobitems.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ayamitsu0321 on 2015/08/17.
 */
public class ItemMob extends Item {

    public ItemMob() {
        super();
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        NBTTagCompound nbt = stack.getTagCompound();

        if (nbt != null && nbt.hasKey("Mob")) {
            tooltip.add(nbt.getString("Mob"));
        }
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List itemList) {
        for (String name : (Set<String>)getStringToClassMapping().keySet()) {
            if (isLivingName(name) && !isBossName(name)) {
                ItemStack itemStack = new ItemStack(itemIn);
                itemStack.setTagCompound(new NBTTagCompound());
                itemStack.getTagCompound().setString("Mob", name);
                itemList.add(itemStack);
            }
        }
    }

    public static boolean isLivingClass(Class clazz) {
        return clazz != null && (EntityLiving.class).isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers());
    }

    public static boolean isLivingName(String name) {
        Class clazz = getClassFromName(name);
        return isLivingClass(clazz);
    }

    public static boolean isBossClass(Class clazz) {
        return (IBossDisplayData.class).isAssignableFrom(clazz);
    }

    public static boolean isBossName(String name) {
        Class clazz = getClassFromName(name);
        return isBossClass(clazz);
    }

    public static Class getClassFromName(String name) {
        return (Class)getStringToClassMapping().get(name);
    }

    public static Map getStringToClassMapping() {
        try {
            Field field = EntityList.class.getDeclaredFields()[1];
            field.setAccessible(true);
            return (Map)field.get(null);
        } catch (Exception e) {
            return null;
        }
    }
}
