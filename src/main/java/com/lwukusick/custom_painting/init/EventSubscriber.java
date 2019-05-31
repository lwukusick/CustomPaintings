package com.lwukusick.custom_painting.init;

import com.lwukusick.custom_painting.Reference;
import com.lwukusick.custom_painting.entity.EntityCustomPainting;
import com.lwukusick.custom_painting.entity.render.RenderCustomPainting;
import com.lwukusick.custom_painting.item.ItemCustomPainting;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventSubscriber {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(
                new Item(new Item.Properties().group(ItemGroup.MATERIALS)).setRegistryName(Reference.MODID, "paintbrush"),
                new ItemCustomPainting(new Item.Properties().group(ItemGroup.DECORATIONS).maxStackSize(1)).setRegistryName(Reference.MODID, "custom_painting")

        );
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event){
        ResourceLocation location = new ResourceLocation(Reference.MODID, "custom_painting");
        event.getRegistry().registerAll(
                EntityType.Builder.create(EntityCustomPainting.class, EntityCustomPainting::new)
                        .build(location.toString())
                        .setRegistryName(Reference.MODID, "custom_painting")
        );
    }

    @SubscribeEvent
    public static void registerRenders(final FMLClientSetupEvent event){
        RenderingRegistry.registerEntityRenderingHandler(EntityCustomPainting.class, RenderCustomPainting::new);
    }
}
