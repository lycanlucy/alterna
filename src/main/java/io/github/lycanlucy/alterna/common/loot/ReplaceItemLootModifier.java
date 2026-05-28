package io.github.lycanlucy.alterna.common.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.lycanlucy.alterna.registry.AlternaLootModifierSerializers;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReplaceItemLootModifier extends LootModifier {
    public static final MapCodec<ReplaceItemLootModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            IGlobalLootModifier.LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(glm -> glm.conditions),
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("old_item").forGetter(ReplaceItemLootModifier::oldItem),
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("new_item").forGetter(ReplaceItemLootModifier::newItem),
            LootItemFunctions.ROOT_CODEC.listOf().optionalFieldOf("functions", List.of()).forGetter(ReplaceItemLootModifier::functions)
    ).apply(instance, ReplaceItemLootModifier::new));
    private final Item oldItem;
    private final Item newItem;
    private final List<LootItemFunction> functions;

    public ReplaceItemLootModifier(LootItemCondition[] conditionsIn, Item oldItem, Item newItem, List<LootItemFunction> functions) {
        super(conditionsIn);
        this.oldItem = oldItem;
        this.newItem = newItem;
        this.functions = functions;
    }

    public Item oldItem() {
        return oldItem;
    }

    public Item newItem() {
        return newItem;
    }

    public List<LootItemFunction> functions() {
        return functions;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext context) {
        for (ItemStack itemStack : generatedLoot) {
            if (itemStack.is(oldItem)) {
                generatedLoot.remove(itemStack);
                ItemStack replacement = new ItemStack(newItem);
                for (LootItemFunction function : functions) {
                    replacement = function.apply(replacement, context);
                }
                generatedLoot.add(replacement);
            }
        }
        return generatedLoot;
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return AlternaLootModifierSerializers.REPLACE_ITEM.get();
    }
}
