package com.example.examplemod;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomeManager extends SavedData {
    private static final String DATA_NAME = "examplemod_homes";
    private final Map<UUID, HomeData> homes = new HashMap<>();

    public static class HomeData {
        public BlockPos position;
        public ResourceKey<Level> dimension;

        public HomeData(BlockPos position, ResourceKey<Level> dimension) {
            this.position = position;
            this.dimension = dimension;
        }

        public HomeData() {}

        public CompoundTag save() {
            CompoundTag tag = new CompoundTag();
            tag.putLong("pos", this.position.asLong());
            tag.putString("dimension", this.dimension.location().toString());
            return tag;
        }

        public static HomeData load(CompoundTag tag) {
            HomeData data = new HomeData();
            data.position = BlockPos.of(tag.getLong("pos"));
            data.dimension = Level.RESOURCE_KEY_CODEC.parse(
                net.minecraft.nbt.NbtOps.INSTANCE, 
                tag.get("dimension")
            ).resultOrPartial(s -> {}).orElse(Level.OVERWORLD);
            return data;
        }
    }

    public static HomeManager get(ServerLevel level) {
        DimensionDataStorage storage = level.getServer().overworld().getDataStorage();
        return storage.computeIfAbsent(
            new SavedData.Factory<HomeManager>(HomeManager::new, HomeManager::load, DataFixTypes.LEVEL), 
            DATA_NAME
        );
    }

    public static HomeManager load(CompoundTag tag, HolderLookup.Provider provider) {
        HomeManager manager = new HomeManager();
        CompoundTag homesTag = tag.getCompound("homes");
        
        for (String key : homesTag.getAllKeys()) {
            UUID playerUuid = UUID.fromString(key);
            HomeData homeData = HomeData.load(homesTag.getCompound(key));
            manager.homes.put(playerUuid, homeData);
        }
        
        return manager;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        CompoundTag homesTag = new CompoundTag();
        
        for (Map.Entry<UUID, HomeData> entry : homes.entrySet()) {
            homesTag.put(entry.getKey().toString(), entry.getValue().save());
        }
        
        tag.put("homes", homesTag);
        return tag;
    }

    public void setHome(ServerPlayer player, BlockPos position) {
        ResourceKey<Level> dimension = player.level().dimension();
        homes.put(player.getUUID(), new HomeData(position, dimension));
        setDirty();
    }

    public HomeData getHome(ServerPlayer player) {
        return homes.get(player.getUUID());
    }

    public boolean hasHome(ServerPlayer player) {
        return homes.containsKey(player.getUUID());
    }

    public void removeHome(ServerPlayer player) {
        homes.remove(player.getUUID());
        setDirty();
    }
}