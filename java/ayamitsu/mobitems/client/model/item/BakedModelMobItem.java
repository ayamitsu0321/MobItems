package ayamitsu.mobitems.client.model.item;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.ISmartItemModel;
import org.apache.commons.lang3.tuple.Pair;

import javax.vecmath.Matrix4f;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by ayamitsu0321 on 2015/08/17.
 */
public class BakedModelMobItem implements IFlexibleBakedModel, ISmartItemModel, IPerspectiveAwareModel {

    Entity displayEntity = null;
    ItemStack itemStack = null;

    public static RescaleRegistry rescaleRegistry = new RescaleRegistry();

    {
        this.rescaleRegistry.register("Ghast", 0.25F);
        this.rescaleRegistry.register("Giant", 0.167F);// 1.0F / 6.0F
    }

    public BakedModelMobItem() {
    }

    @Override
    public List<BakedQuad> getFaceQuads(EnumFacing side) {
        return Collections.emptyList();
    }

    @Override
    public List<BakedQuad> getGeneralQuads() {
        List<BakedQuad> list = Collections.emptyList();//Lists.newArrayList();
        //list.add(new BakedQuad(new int[]{0, 0, 0}, -1, EnumFacing.NORTH));
        this.displayEntity = null;

        if (itemStack != null) {
            NBTTagCompound nbt = itemStack.getTagCompound();

            if (nbt != null) {
                String name = nbt.getString("Mob");

                if (name != null && name.length() > 0) {
                    this.displayEntity = EntityList.createEntityByName(name, Minecraft.getMinecraft().theWorld);
                }
            }
        }

        if (this.displayEntity != null) {
            Tessellator.getInstance().getWorldRenderer().finishDrawing();

            this.renderMob(this.displayEntity);

            Tessellator.getInstance().getWorldRenderer().startDrawingQuads();
            Tessellator.getInstance().getWorldRenderer().setVertexFormat(DefaultVertexFormats.ITEM);
        }

        return list;
    }

    private void renderMob(Entity entity) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(-180F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5F, 0.0F, -0.5F);

        String name = EntityList.getEntityString(entity);
        if (this.rescaleRegistry.contains(name)) {
            float scale = this.rescaleRegistry.getScale(name);
            GlStateManager.scale(scale, scale, scale);
        }

        Minecraft.getMinecraft().getRenderManager().doRenderEntity(entity, 0, 0, 0, 0, 0, true);
        GlStateManager.popMatrix();
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getTexture() {
        return null;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return ItemCameraTransforms.DEFAULT;
    }

    @Override
    public VertexFormat getFormat() {
        return Attributes.DEFAULT_BAKED_FORMAT;
    }

    @Override
    public Pair<IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        BakedModelMobItem bakeModel = this;//new BakedModelMobItem();

        switch (cameraTransformType) {
            case GUI:
                RenderItem.applyVanillaTransform(getItemCameraTransforms().gui);
                break;
            case FIRST_PERSON:
                RenderItem.applyVanillaTransform(getItemCameraTransforms().firstPerson);
                break;
            case THIRD_PERSON:
                GlStateManager.translate(0 + RenderItem.debugItemOffsetX, 0.1 + RenderItem.debugItemOffsetY, -0.2 + RenderItem.debugItemOffsetZ);
                GlStateManager.rotate(10 + RenderItem.debugItemRotationOffsetY, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(45 + RenderItem.debugItemRotationOffsetX, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(170 + RenderItem.debugItemRotationOffsetZ, 0.0F, 0.0F, 1.0F);
                GlStateManager.scale(0.375 + RenderItem.debugItemScaleX, 0.375 + RenderItem.debugItemScaleY, 0.375 + RenderItem.debugItemScaleZ);
                break;
        }

        return Pair.of((IBakedModel)bakeModel, null);
    }

    @Override
    public IBakedModel handleItemState(ItemStack stack) {
        BakedModelMobItem bakedModel = this;//new BakedModelMobItem();
        bakedModel.itemStack = stack;
        return bakedModel;
    }

    static class RescaleRegistry {
        /**
         * mob name, scale *
         */
        private Map<String, Float> mapping = Maps.newHashMap();

        public void register(String name, float scale) {
            this.mapping.put(name, scale);
        }

        public float getScale(String name) {
            return this.mapping.get(name).floatValue();
        }

        public boolean contains(String name) {
            return this.mapping.containsKey(name);
        }
    }

}
