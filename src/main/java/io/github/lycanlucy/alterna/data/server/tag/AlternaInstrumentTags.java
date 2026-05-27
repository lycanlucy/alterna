package io.github.lycanlucy.alterna.data.server.tag;

import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Instrument;

public class AlternaInstrumentTags {
    public static final TagKey<Instrument> CONCH_SHELLS = tag("conch_shells");

    public static TagKey<Instrument> tag(String name) {
        return TagKey.create(Registries.INSTRUMENT, Alterna.modId(name));
    }
}
