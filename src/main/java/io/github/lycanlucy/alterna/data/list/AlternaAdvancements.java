package io.github.lycanlucy.alterna.data.list;

import com.mojang.datafixers.util.Pair;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class AlternaAdvancements {
    public static final ArrayList<AlternaAdvancement> ADVANCEMENTS = new ArrayList<>();
    public static final HashMap<AlternaAdvancement, Pair<String, String>> TRANSLATIONS = new HashMap<>();

    static {
        createAdvancement(
                ResourceLocation.withDefaultNamespace("adventure/root"),
                "adventure.needs_a_touch_up", "Needs a Touch Up", "Obtain a Sunken Trident",
                new ItemStack(AlternaItems.SUNKEN_TRIDENT.get()), AdvancementType.TASK,
                List.of(
                        new Pair<>("get_sunken_trident", InventoryChangeTrigger.TriggerInstance.hasItems(AlternaItems.SUNKEN_TRIDENT))
                )
        );
    }

    public static void createAdvancement(ResourceLocation parent, String name, String title, String description, ItemStack icon, AdvancementType type, List<Pair<String, Criterion<?>>> criteria) {
        AlternaAdvancement advancement = new AlternaAdvancement(Optional.of(parent), Optional.empty(), name, icon, type, criteria);
        ADVANCEMENTS.add(advancement);
        TRANSLATIONS.put(advancement, Pair.of(title, description));
    }

    public static String title(String name) {
        return "advancements.alterna." + name + ".title";
    }

    public static String description(String name) {
        return "advancements.alterna." + name + ".description";
    }

    public record AlternaAdvancement(Optional<ResourceLocation> parent, Optional<ResourceLocation> background,
                                     String name,
                                     ItemStack icon, AdvancementType type,
                                     Collection<Pair<String, Criterion<?>>> criteria) {
    }

}
