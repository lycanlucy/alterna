package io.github.lycanlucy.alterna.client.renderer;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.EntityModel;

import java.util.List;

public interface ReplacedModelRenderer {
    List<Pair<EntityModel<?>, String>> alterna$getReplacedModels();
}
