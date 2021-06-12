/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cyberiantiger.minecraft.unsafe.v1_17_R1;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.cyberiantiger.minecraft.nbt.ByteArrayTag;
import org.cyberiantiger.minecraft.nbt.ByteTag;
import org.cyberiantiger.minecraft.nbt.CompoundTag;
import org.cyberiantiger.minecraft.nbt.DoubleTag;
import org.cyberiantiger.minecraft.nbt.FloatTag;
import org.cyberiantiger.minecraft.nbt.IntArrayTag;
import org.cyberiantiger.minecraft.nbt.IntTag;
import org.cyberiantiger.minecraft.nbt.ListTag;
import org.cyberiantiger.minecraft.nbt.LongArrayTag;
import org.cyberiantiger.minecraft.nbt.LongTag;
import org.cyberiantiger.minecraft.nbt.ShortTag;
import org.cyberiantiger.minecraft.nbt.StringTag;
import org.cyberiantiger.minecraft.nbt.Tag;
import org.cyberiantiger.minecraft.nbt.TagType;

/**
 *
 * @author antony
 */
public final class NBTTools implements org.cyberiantiger.minecraft.unsafe.NBTTools {

    public net.minecraft.nbt.CompoundTag toNativeCompound(CompoundTag tag) {
        net.minecraft.nbt.CompoundTag result = new net.minecraft.nbt.CompoundTag();
        for (@SuppressWarnings("rawtypes")
        Map.Entry<String, Tag> e : tag.getValue().entrySet()) {
            net.minecraft.nbt.Tag base;
            String name = e.getKey();
            Tag<?> t = e.getValue();
            switch (t.getType()) {
                case BYTE:
                    base = net.minecraft.nbt.ByteTag.valueOf(((ByteTag) t).getRawValue());
                    break;
                case BYTE_ARRAY:
                    base = new net.minecraft.nbt.ByteArrayTag(((ByteArrayTag) t).getValue());
                    break;
                case COMPOUND:
                    base = toNativeCompound((CompoundTag) t);
                    break;
                case DOUBLE:
                    base = net.minecraft.nbt.DoubleTag.valueOf(((DoubleTag) t).getRawValue());
                    break;
                case FLOAT:
                    base = net.minecraft.nbt.FloatTag.valueOf(((FloatTag) t).getRawValue());
                    break;
                case INT:
                    base = net.minecraft.nbt.IntTag.valueOf(((IntTag) t).getRawValue());
                    break;
                case INT_ARRAY:
                    base = new net.minecraft.nbt.IntArrayTag(((IntArrayTag) t).getValue());
                    break;
                case LIST:
                    base = toNativeList((ListTag) t);
                    break;
                case LONG:
                    base = net.minecraft.nbt.LongTag.valueOf(((LongTag) t).getRawValue());
                    break;
                case SHORT:
                    base = net.minecraft.nbt.ShortTag.valueOf(((ShortTag) t).getRawValue());
                    break;
                case STRING:
                    base = net.minecraft.nbt.StringTag.valueOf(((StringTag) t).getValue());
                    break;
                case LONG_ARRAY:
                    base = new net.minecraft.nbt.LongArrayTag(((LongArrayTag) t).getValue());
                    break;
                default:
                    // Can't be reached.
                    throw new IllegalArgumentException();
            }
            result.put(name, base);
        }
        return result;
    }

    public net.minecraft.nbt.ListTag toNativeList(ListTag tag) {
        net.minecraft.nbt.ListTag result = new net.minecraft.nbt.ListTag();

        switch (tag.getListType()) {
            case BYTE:
                for (ByteTag t : (ByteTag[]) tag.getValue()) {
                    result.add(net.minecraft.nbt.ByteTag.valueOf(t.getRawValue()));
                }
                break;
            case BYTE_ARRAY:
                for (ByteArrayTag t : (ByteArrayTag[]) tag.getValue()) {
                    result.add(new net.minecraft.nbt.ByteArrayTag(t.getValue()));
                }
                break;
            case COMPOUND:
                for (CompoundTag t : (CompoundTag[]) tag.getValue()) {
                    result.add(toNativeCompound(t));
                }
                break;
            case DOUBLE:
                for (DoubleTag t : (DoubleTag[]) tag.getValue()) {
                    result.add(net.minecraft.nbt.DoubleTag.valueOf(t.getRawValue()));
                }
                break;
            case FLOAT:
                for (FloatTag t : (FloatTag[]) tag.getValue()) {
                    result.add(net.minecraft.nbt.FloatTag.valueOf(t.getRawValue()));
                }
                break;
            case INT:
                for (IntTag t : (IntTag[]) tag.getValue()) {
                    result.add(net.minecraft.nbt.IntTag.valueOf(t.getRawValue()));
                }
                break;
            case INT_ARRAY:
                for (IntArrayTag t : (IntArrayTag[]) tag.getValue()) {
                    result.add(new net.minecraft.nbt.IntArrayTag(t.getValue()));
                }
                break;
            case LIST:
                for (ListTag t : (ListTag[]) tag.getValue()) {
                    result.add(toNativeList(t));
                }
            case LONG:
                for (LongTag t : (LongTag[]) tag.getValue()) {
                    result.add(net.minecraft.nbt.LongTag.valueOf(t.getRawValue()));
                }
                break;
            case SHORT:
                for (ShortTag t : (ShortTag[]) tag.getValue()) {
                    result.add(net.minecraft.nbt.ShortTag.valueOf(t.getRawValue()));
                }
                break;
            case STRING:
                for (StringTag t : (StringTag[]) tag.getValue()) {
                    result.add(net.minecraft.nbt.StringTag.valueOf(t.getValue()));
                }
                break;
            case LONG_ARRAY:
                for (LongArrayTag t : (LongArrayTag[]) tag.getValue()) {
                    result.add(new net.minecraft.nbt.LongArrayTag(t.getValue()));
                }
                break;
            case END:
                break;
        }

        return result;
    }

    public CompoundTag fromNativeCompound(net.minecraft.nbt.CompoundTag tag) {
        try {
            @SuppressWarnings("rawtypes")
            Map<String, Tag> result = new HashMap<>();
            for (String name : tag.getAllKeys()) {
                net.minecraft.nbt.Tag b = tag.get(name);
                switch (TagType.values()[b.getId()]) {
                    case BYTE:
                        result.put(name, new ByteTag(((net.minecraft.nbt.ByteTag) b).getAsByte()));
                        break;
                    case BYTE_ARRAY:
                        result.put(name, new ByteArrayTag(((net.minecraft.nbt.ByteArrayTag) b).getAsByteArray()));
                        break;
                    case COMPOUND:
                        result.put(name, fromNativeCompound((net.minecraft.nbt.CompoundTag) b));
                        break;
                    case DOUBLE:
                        result.put(name, new DoubleTag(((net.minecraft.nbt.DoubleTag) b).getAsDouble()));
                        break;
                    case FLOAT:
                        result.put(name, new FloatTag(((net.minecraft.nbt.FloatTag) b).getAsFloat()));
                        break;
                    case INT:
                        result.put(name, new IntTag(((net.minecraft.nbt.IntTag) b).getAsInt()));
                        break;
                    case INT_ARRAY:
                        result.put(name, new IntArrayTag(((net.minecraft.nbt.IntArrayTag) b).getAsIntArray()));
                        break;
                    case LIST:
                        result.put(name, fromNativeList((net.minecraft.nbt.ListTag) b));
                        break;
                    case LONG:
                        result.put(name, new LongTag(((net.minecraft.nbt.LongTag) b).getAsLong()));
                        break;
                    case SHORT:
                        result.put(name, new ShortTag(((net.minecraft.nbt.ShortTag) b).getAsShort()));
                        break;
                    case STRING:
                        result.put(name, new StringTag(((net.minecraft.nbt.StringTag) b).getAsString()));
                        break;
                    case LONG_ARRAY:
                        result.put(name, new LongArrayTag(((net.minecraft.nbt.LongArrayTag) b).getAsLongArray()));
                        break;
                    case END:
                        break;
                }
            }
            return new CompoundTag(result);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NBTTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ListTag fromNativeList(net.minecraft.nbt.ListTag tag) {
        try {
            TagType type = TagType.values()[tag.getElementType()];
            if (type == TagType.END) {
                type = TagType.BYTE;
            }
            @SuppressWarnings("rawtypes")
            Tag[] t = (Tag[]) Array.newInstance(type.getTagClass(), tag.size());
            switch (type) {
                case BYTE:
                case END:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new ByteTag(((net.minecraft.nbt.ByteTag) tag.get(i)).getAsByte());
                    }
                    break;
                case BYTE_ARRAY:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new ByteArrayTag(((net.minecraft.nbt.ByteArrayTag) tag.get(i)).getAsByteArray());
                    }
                    break;
                case COMPOUND:
                    for (int i = 0; i < tag.size(); i++) {
                        if (tag.get(i) == null) {
                            t[i] = new CompoundTag();
                        } else {
                            t[i] = fromNativeCompound((net.minecraft.nbt.CompoundTag) tag.get(i));
                        }
                    }
                    break;
                case DOUBLE:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new DoubleTag(((net.minecraft.nbt.DoubleTag) tag.get(i)).getAsDouble());
                    }
                    break;
                case FLOAT:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new FloatTag(((net.minecraft.nbt.FloatTag) tag.get(i)).getAsFloat());
                    }
                    break;
                case INT:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new IntTag(((net.minecraft.nbt.IntTag) tag.get(i)).getAsInt());
                    }
                    break;
                case INT_ARRAY:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new IntArrayTag(((net.minecraft.nbt.IntArrayTag) tag.get(i)).getAsIntArray());
                    }
                    break;
                case LIST:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = fromNativeList((net.minecraft.nbt.ListTag) tag.get(i));
                    }
                    break;
                case LONG:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new LongTag(((net.minecraft.nbt.LongTag) tag.get(i)).getAsLong());
                    }
                    break;
                case SHORT:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new ShortTag(((net.minecraft.nbt.ShortTag) tag.get(i)).getAsShort());
                    }
                    break;
                case STRING:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new StringTag(((net.minecraft.nbt.StringTag) tag.get(i)).getAsString());
                    }
                    break;
                case LONG_ARRAY:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new LongArrayTag(((net.minecraft.nbt.LongArrayTag) tag.get(i)).getAsLongArray());
                    }
                    break;
            }
            return new ListTag(type, t);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NBTTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void writeTileEntity(Block block, CompoundTag tag) {
        CraftWorld craftWorld = (CraftWorld) block.getWorld();
        BlockPos pos = new BlockPos(block.getX(), block.getY(), block.getZ());
        BlockEntity tileEntity = craftWorld.getHandle().getBlockEntity(pos);
        if (tileEntity == null) {
            return;
        }
        ServerLevel handle = craftWorld.getHandle();
        BlockState blockData = handle.getBlockState(pos);
        tileEntity.load(toNativeCompound(tag));
        tileEntity.setChanged();
        craftWorld.getHandle().sendBlockUpdated(pos, blockData, blockData, 3);
    }

    @Override
    public CompoundTag readTileEntity(Block block) {
        CraftWorld craftWorld = (CraftWorld) block.getWorld();
        BlockPos pos = new BlockPos(block.getX(), block.getY(), block.getZ());
        BlockEntity tileEntity = craftWorld.getHandle().getBlockEntity(pos);
        if (tileEntity == null) {
            return null;
        }
        net.minecraft.nbt.CompoundTag tag = new net.minecraft.nbt.CompoundTag();
        tileEntity.save(tag);
        return fromNativeCompound(tag);
    }

    @Override
    public CompoundTag readItemStack(ItemStack stack) {
        net.minecraft.world.item.ItemStack nativeStack = CraftItemStack.asNMSCopy(stack);
        if (nativeStack == null) {
            return null;
        }
        net.minecraft.nbt.CompoundTag compound = new net.minecraft.nbt.CompoundTag();
        nativeStack.save(compound);
        return fromNativeCompound(compound);
    }

    @Override
    public ItemStack createItemStack(CompoundTag tag) {
        net.minecraft.world.item.ItemStack nativeStack = net.minecraft.world.item.ItemStack.of(toNativeCompound(tag));
        return CraftItemStack.asCraftMirror(nativeStack);
    }

    @Override
    public CompoundTag readEntity(Entity e) {
        net.minecraft.world.entity.Entity handle = ((CraftEntity) e).getHandle();
        net.minecraft.nbt.CompoundTag compound = new net.minecraft.nbt.CompoundTag();
        handle.save(compound);
        return fromNativeCompound(compound);
    }

    @Override
    public void updateEntity(Entity entity, CompoundTag tag) {
        CraftEntity craftEntity = (CraftEntity) entity;
        craftEntity.getHandle().load(toNativeCompound(tag));
    }

    @Override
    public Entity getEntityByUUID(World world, UUID id) {
        CraftWorld craftWorld = (CraftWorld) world;
        net.minecraft.world.entity.Entity entity = craftWorld.getHandle().getEntity(id);
        if (entity == null) {
            return null;
        }
        return entity.getBukkitEntity();
    }

    @Override
    public boolean isEntityByIdSupported() {
        return false;
    }

    @Override
    public boolean isEntityByUuidSupported() {
        return true;
    }

    @Override
    public Entity getEntityById(World world, int id) {
        throw new UnsupportedOperationException("Not supported");
    }
}
